package com.bwie.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.bwie.service.TbOrderService;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.BuyOrderVo;
import com.bwie.vo.OrderNoInfoVo;
import com.bwie.vo.PageInfoVo;
import com.bwie.vo.PayWeChatOrderVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author Sun
 * @Version 1.0
 * @description: TODO
 * @date 2022/12/21 19:57:40
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Resource
    private TbOrderService tbOrderService;

    /**
     * 购买商品生成订单
     * @param buyOrderVo
     * @return
     */
    @PostMapping("buy-product")
    public ResultResponse buyProduct(@RequestBody BuyOrderVo buyOrderVo){
        return tbOrderService.buyProduct(buyOrderVo);
    }

    /**
     * 订单列表
     * @param pageInfoVo
     * @return
     */
    @PostMapping("list-order")
    public ResultResponse listOrder(@RequestBody PageInfoVo pageInfoVo){
        return tbOrderService.listOrder(pageInfoVo);
    }


    /**
     * 得到订单号
     * @param
     * @return
     */
    @PostMapping("get-orderNo")
    public ResultResponse getOrderNo(){
        return tbOrderService.getOrderNo();
    }

    /**
     * 下单接口
     * @param
     * @return
     */
    @PostMapping("get-weCharPayCode")
    public ResultResponse getPayCode(@RequestBody PayWeChatOrderVo payWeChatOrderVo){
        return tbOrderService.getPayCode(payWeChatOrderVo);
    }

    /**
     * 得到支付结果
     * @param
     * @return
     */
    @PostMapping("get-orderInfo")
    public ResultResponse getOrderInfo(@RequestBody OrderNoInfoVo orderNoInfoVo){
        return tbOrderService.getOrderInfo(orderNoInfoVo);
    }

    /**
     * 支付成功修改订单状态
     * @param
     * @return
     */
    @PostMapping("update-state")
    public ResultResponse updateState(@RequestBody OrderNoInfoVo orderNoInfoVo){
        return tbOrderService.updateState(orderNoInfoVo);
    }

    /**
     * 支付宝支付
     * @param payWeChatOrderVo
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("alipay-pay")
    public ResultResponse pay(@RequestBody PayWeChatOrderVo payWeChatOrderVo) throws AlipayApiException {
        return tbOrderService.aliPay(payWeChatOrderVo);
    }

    /**
     * 功能：支付宝服务器同步通知页面
     * 日期：2017-03-30
     * 说明：
     * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
     * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考
     * ************************页面功能说明*************************
     * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
     */
    @RequestMapping("/getResult")
    public void getResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
         tbOrderService.aliPayResult(request,response);
    }


}
