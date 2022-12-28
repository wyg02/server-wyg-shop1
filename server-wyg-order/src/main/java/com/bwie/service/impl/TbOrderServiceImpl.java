package com.bwie.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bwie.config.AlipayConfig;
import com.bwie.eunm.OrderStateEnum;
import com.bwie.mapper.*;
import com.bwie.pojo.*;
import com.bwie.service.TbOrderService;
import com.bwie.utils.IdWorker;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Conversion;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author 魏阳光
 * @description 针对表【tb_order(订单表)】的数据库操作Service实现
 * @createDate 2022-12-21 19:56:52
 */
@Service
@Slf4j
public class TbOrderServiceImpl extends ServiceImpl<TbOrderMapper, TbOrder>
        implements TbOrderService {

    @Resource
    private TbUserMapper tbUserMapper;

    @Resource
    private TbProductMapper tbProductMapper;

    @Resource
    private TbWareMapper tbWareMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private TbProductSkuMapper tbProductSkuMapper;

    /**
     * 购买商品 创建订单
     *
     * @param buyOrderVo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultResponse buyProduct(BuyOrderVo buyOrderVo) {
        RLock lock = redissonClient.getLock("");
        //---1校验　　TODO没有测试
        TbUser tbUser = tbUserMapper.selectById(buyOrderVo.getUserId());
        if (tbUser == null) {
            return ResultResponse.FAILED().message("用户不存在");
        }

        TbProduct tbProduct = tbProductMapper.selectById(buyOrderVo.getProductId());
        if (tbProduct == null) {
            return ResultResponse.FAILED().message("商品不存在");
        }
        lock.lock();
        TbProductSku tbProductSku = tbProductSkuMapper.selectOne(new LambdaQueryWrapper<TbProductSku>().eq(TbProductSku::getSpuCode, buyOrderVo.getSpu()));
        TbWare tbWare = tbWareMapper.selectOne(new LambdaQueryWrapper<TbWare>().eq(TbWare::getSku, tbProductSku.getSkuCode()).last("limit 1"));
        if (tbWare == null || tbWare.getWareCount() - buyOrderVo.getProductCount() <= 0) {
            return ResultResponse.FAILED().message("商品暂无存货。。。");
        }

        try {
            //---2扣减库存
            tbWare.setWareCount(tbWare.getWareCount()-buyOrderVo.getProductCount());
            tbWare.setUpdateTime(null);
            tbWareMapper.updateById(tbWare);

            //---3构建订单
            TbOrder tbOrder = new TbOrder();
            tbOrder.setOrderNo(IdWorker.getId());
            tbOrder.setOrderAmount(buyOrderVo.getAmount());
            tbOrder.setProductCount(buyOrderVo.getProductCount());
            tbOrder.setProductName(tbProduct.getProductName());
            tbOrder.setProductSku(tbWare.getSku());
            tbOrder.setProductId(tbProduct.getProductId());
            tbOrder.setUserName(tbUser.getUserName());
            tbOrder.setUserId(tbUser.getUserId());

            save(tbOrder);


            //--4延时队列取消订单
            String mes = JSON.toJSONString(tbOrder);
            rabbitTemplate.convertAndSend("orderQueue",mes);
        } catch (AmqpException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

        return ResultResponse.SUCCESS();
    }

    /**
     * 取消订单
     * @param idVo
     * @return
     */
    @Override
    public ResultResponse cancelOrder(IdVo idVo) {
        //判断订单状态
        TbOrder tbOrder = baseMapper.selectById(idVo.getId());
        if (tbOrder == null) {
            return ResultResponse.FAILED().message("订单不存在");
        }

        //取消订单
        if (tbOrder.getOrderState().equals(OrderStateEnum.KILL_NO_PAY.getCode())) {
            //如果订单未支付修改订单状态 为取消支付
            tbOrder.setOrderState(OrderStateEnum.KILL_CANCE_PAY.getCode());
            tbOrder.setUpdateTime(null);
            baseMapper.updateById(tbOrder);
        }
        //回归库存 根据产品SKU取得库存
        LambdaQueryWrapper<TbWare> war = new LambdaQueryWrapper<>();
        war.eq(TbWare::getSku, tbOrder.getProductSku());
        TbWare tbWare = tbWareMapper.selectOne(war);
        tbWare.setWareCount(tbWare.getWareCount() + tbOrder.getProductCount());
        baseMapper.updateById(tbOrder);

        return ResultResponse.SUCCESS();
    }

    /**
     * 订单列表
     * @param pageInfoVo
     * @return
     */
    @Override
    public ResultResponse listOrder(PageInfoVo pageInfoVo) {
        Page<TbOrder> orderPage = new Page<>(pageInfoVo.getPageNum(), pageInfoVo.getPageSize());
        Page<TbOrder> page = page(orderPage, new LambdaQueryWrapper<TbOrder>().orderByDesc(TbOrder::getUpdateTime));
        return ResultResponse.SUCCESS().data("page",page);
    }

    /**
     * 得到订单编号
     * @return
     */
    @Override
    public ResultResponse getOrderNo() {
        //1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.生成一个get请求
        HttpGet httpget = new HttpGet("https://www.shenmazong.com/weixin/getOrderNo");
        CloseableHttpResponse response = null;
        try {
            //3.执行get请求并返回结果
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            //4.处理结果，这里将结果返回为字符串
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log.info("result==",result);
        ResultVo resultVo = JSON.parseObject(result, ResultVo.class);

        return ResultResponse.SUCCESS().data("orderNo", resultVo.getData());
    }

    /**
     * 下订单
     * @return
     */
    @Override
    public ResultResponse getPayCode(PayWeChatOrderVo payWeChatOrderVo) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        String result="";
        try {
            // 创建客户端连接对象
            client = HttpClients.createDefault();
            // 构建Post请求对象
            HttpPost post = new HttpPost("https://www.shenmazong.com/weixin/postOrderObj");
            // 设置传送的内容类型是json格式
            post.setHeader("Content-Type", "application/json;charset=utf-8");
            // 接收的内容类型也是json格式
            post.setHeader("Accept", "application/json;charset=utf-8");
            String jsonString = JSON.toJSONString(payWeChatOrderVo);
            // 设置请求体
            post.setEntity(new StringEntity(jsonString, "UTF-8"));
            // 获取返回对象
            response = client.execute(post);
            // 整理返回值
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResultVo resultVo = JSON.parseObject(result, ResultVo.class);

        return ResultResponse.SUCCESS().data("data", resultVo.getData());
    }

    /**
     * 查看支付结果
     * @return
     */
    @Override
    public ResultResponse getOrderInfo(OrderNoInfoVo orderNoInfoVo) {
        //1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.生成一个get请求
        HttpGet httpget = new HttpGet("https://www.shenmazong.com/weixin/getOrderInfo?orderNo="+orderNoInfoVo.getOrderNo());
        CloseableHttpResponse response = null;
        try {
            //3.执行get请求并返回结果
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            //4.处理结果，这里将结果返回为字符串
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        log.info("result==",result);
        ResultResponse resultResponse = JSON.parseObject(result, ResultResponse.class);
        ResultResponse resultVo = JSON.parseObject(result, ResultResponse.class);

        return resultVo;
    }

    /**
     * 修改订单状态
     * @param orderNoInfoVo
     * @return
     */
    @Override
    public ResultResponse updateState(OrderNoInfoVo orderNoInfoVo) {
        LambdaQueryWrapper<TbOrder> wrapper = new LambdaQueryWrapper<TbOrder>().eq(TbOrder::getOrderNo, orderNoInfoVo.getOrderNo());
        TbOrder tbOrder = baseMapper.selectOne(wrapper.last("limit 1"));
        tbOrder.setOrderState(1);
        updateById(tbOrder);
        return ResultResponse.SUCCESS();
    }

    @Override
    public ResultResponse aliPay(PayWeChatOrderVo payWeChatOrderVo) throws AlipayApiException {
//获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        Long out_trade_no = payWeChatOrderVo.getOutTradeNo();
        //付款金额，必填
        Long total_amount = payWeChatOrderVo.getAmount();
        //订单名称，必填
        String subject = payWeChatOrderVo.getAttach();
        //商品描述，可空
        String body = payWeChatOrderVo.getDescription();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return ResultResponse.SUCCESS().data("result", result);
    }

    /**
     * 得到支付结果
     * @param request
     * @param response
     * @return
     * @throws AlipayApiException
     */
    @Override
    public void aliPayResult(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException {
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        String out_trade_no="";
        if(signVerified) {
            //商户订单号
             out_trade_no = request.getParameter("out_trade_no");

            //支付宝交易号
            String trade_no = request.getParameter("trade_no");

            //付款金额
            String total_amount = request.getParameter("amount");

            System.out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
            log.info("成功转发到产品列表");
            //支付成功修改状态
            OrderNoInfoVo orderNoInfoVo = new OrderNoInfoVo();
            orderNoInfoVo.setOrderNo(out_trade_no);
            updateState(orderNoInfoVo);
            response.sendRedirect("http://localhost:8080/#/order?orderNo="+out_trade_no);
        }else {
            System.out.println("验签失败");
            log.error("失败转发到订单列表");
            //000=失败订单编号
            response.sendRedirect("http://localhost:8080/#/order?orderNo="+000);
        }
    }
}




