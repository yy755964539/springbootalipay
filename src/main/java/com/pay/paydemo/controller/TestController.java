package com.pay.paydemo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AppPay;
import com.alipay.util.PayUtils;
import com.alipay.util.Util;
import com.google.gson.Gson;
import com.pay.paydemo.service.AlipayService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xinyan
 * @title: TestController
 * @projectName paydemo
 * @description: TODO
 * @date 2020/3/8 13:03
 */
@Controller
public class TestController {


    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private AlipayService alipayService;

    @GetMapping("/")
    @ResponseBody
    public String test() {
        return "hello";
    }

    @GetMapping("/502")
    public String error() {
        return "error.html";
    }

    @GetMapping("/200")
    public String success() {
        return "success.html";
    }

    //下单购买
    @GetMapping("/goods")
    @ResponseBody
    public String pay(@RequestParam("out_trade_no") String outTradeNo, @RequestParam("subject") String subject,
                      @RequestParam("total_amount") String totalAmount, @RequestParam("body") String body) throws AlipayApiException {
        HashMap<String, String> parm = new HashMap<>(6);
        parm.put("subject", subject);
        parm.put("out_trade_no", outTradeNo);
        // parm.put("timeout_express","1583655720");//支付超时时间
        parm.put("total_amount", totalAmount);
        parm.put("body", body);
        parm.put("product_code", "FAST_INSTANT_TRADE_PAY");
        return alipayService.alipay(parm);
    }


    //退款

    /**
     * 沙箱退款报错
     * ^_^{"alipay_trade_refund_response":{"code":"10000","msg":"Success","buyer_logon_id":"vde***@sandbox.com","buyer_user_id":"2088102178196852","fund_change":"Y","gmt_refund_pay":"2020-03-08 22:44:12","out_trade_no":"1234567","refund_fee":"500.00","send_back_fee":"0.00","trade_no":"2020030822001496850500817079"},"sign":"XmNsBZdzY5FmmWu11RljPaR86X/hJtHmWJsSuuNZfnzfDC2bDmGQ5iFUh0Z0JxzhQMiTnjpmzGxJGTGiygCSLd6KBwmn+mlEu0YXF949abAkI0WeiseMl+Duq2EYiPDjM9LFGEpGFTq9ktBtgL/QmJk2n6wmZdGBubibD9CvUBGz+iwasIoanVUCsLj7vHK/jFw4Q5l5xrQZnfHz8csB611vGE2obpNJUvsQOzdqaswp/I1p1NkoUKXO6QNibcT/iOBUPYAOPUTmLEDLpOt5zjwRZvGAgTG90LUAzNabYl/Q4nQQIo2hDuXuiGQo/htsBl5xFDDYsfUMpGl2PR8d5A=="}^_^324ms,1622ms,nullms
     * 实际退款成功
     * 2020年3月8日 23:03:25
     *
     * @return
     */
    // @RequestMapping("/refund")
    @ResponseBody
    public String refund() {
        HashMap<String, String> parm = new HashMap<>(6);
        String out_request_no = new String(UUID.randomUUID().toString());
        //out_trade_no商家订单号
        //trade_no支付订单号
        //refund_amount退款金额
        //refund_currency人命币CNY
        //refund_reason正常退款
        parm.put("out_trade_no", "123456111");
        parm.put("refund_amount", "500.00");
        //  parm.put("refund_currency","CNY");
        parm.put("refund_reason", "正常退款");
        // parm.put("out_request_no",out_request_no);
        return alipayService.refund(parm);
    }


    //下单
    @GetMapping("/place-on-order")
    @ResponseBody
    /* 方法注解 */
    @ApiOperation(value = "下单", notes = "下单")
    public String placeOnOrder(@RequestParam("payType") Integer payType) {
        String placeOnOrder = PayUtils.placeOnOrder(payType);
        if (!StringUtils.isEmpty(placeOnOrder)) {
            return placeOnOrder;
        }
        return "FAIL";
    }


    //退款
    @GetMapping("/refund")
    @ResponseBody
    @ApiOperation(value = "退款", notes = "退款")
    public String refund(@RequestParam("orderId") String oderId, @RequestParam("amount") Long amount) {
        boolean refund = PayUtils.refund(oderId, amount);
        if (refund) {
            return "SUCCESS";
        }
        return "FAIL";
    }

    //支付结果查询
    @GetMapping("/query-order")
    @ResponseBody
    @ApiOperation(value = "支付结果查询", notes = "支付结果查询")
    public String queryOrder(@RequestParam("orderId") String oderId) {
        String respBody = PayUtils.queryOrder(oderId);
        if (StringUtils.isEmpty(respBody)) {
            return "FAIL";
        }
        return respBody;
    }


    //关闭支付订单
    @GetMapping("/close-order")
    @ResponseBody
    @ApiOperation(value = "关闭支付订单", notes = "关闭支付订单")
    public String closeOrder(@RequestParam("orderId") String oderId) {
        String respBody = PayUtils.closeOrder(oderId);
        if (StringUtils.isEmpty(respBody)) {
            return "FAIL";
        }
        return respBody;
    }

    //退款查询
    @GetMapping("/refund-query")
    @ResponseBody
    @ApiOperation(value = "退款查询", notes = "退款查询")
    public String refundQuery(@RequestParam("orderId") String oderId) {
        String respBody = PayUtils.refundQuery(oderId);
        if (StringUtils.isEmpty(respBody)) {
            return "FAIL";
        }
        return respBody;
    }

    //获取回调的参数
    @PostMapping("/test-notify")
    @ApiOperation(value = "获取回调的参数", notes = "获取回调的参数")
    public void refund(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> stringObjectMap = Util.request2Map(request);
        System.out.println("params:" + stringObjectMap);
        System.out.println(new Gson().toJson(stringObjectMap));
    }

    //wx98369e085c6db913
    //3c1a19b6fef2ae329a8bb02d6270e38d

}
