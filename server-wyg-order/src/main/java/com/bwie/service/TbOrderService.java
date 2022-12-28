package com.bwie.service;

import com.alipay.api.AlipayApiException;
import com.bwie.pojo.TbOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bwie.utils.ResultResponse;
import com.bwie.vo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author 魏阳光
* @description 针对表【tb_order(订单表)】的数据库操作Service
* @createDate 2022-12-21 19:56:52
*/
public interface TbOrderService extends IService<TbOrder> {

    ResultResponse buyProduct(BuyOrderVo buyOrderVo);

    ResultResponse cancelOrder(IdVo idVo);

    ResultResponse listOrder(PageInfoVo pageInfoVo);

    ResultResponse getOrderNo();

    ResultResponse getPayCode(PayWeChatOrderVo payWeChatOrderVo);

    ResultResponse getOrderInfo(OrderNoInfoVo orderNoInfoVo);

    ResultResponse updateState(OrderNoInfoVo orderNoInfoVo);

    ResultResponse aliPay(PayWeChatOrderVo payWeChatOrderVo) throws AlipayApiException;

    void aliPayResult(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, IOException;
}
