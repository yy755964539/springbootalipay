package com.pay.paydemo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.config.AlipayConfig;
import com.pay.paydemo.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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

    @RequestMapping("/")
    @ResponseBody
    public String test(){
        return "hello";
    }

    @RequestMapping("/502")
    public String error(){
        return "error.html";
    }

    @RequestMapping("/200")
    public String success(){
        return "success.html";
    }

    //下单购买
    @RequestMapping("/goods")
    @ResponseBody
    public String pay(@RequestParam("out_trade_no")String outTradeNo,@RequestParam("subject")String subject,
                      @RequestParam("total_amount")String totalAmount,@RequestParam("body")String body)throws AlipayApiException {
        HashMap<String,String>parm=new HashMap<>(6);
        parm.put("subject",subject);
        parm.put("out_trade_no",outTradeNo);
       // parm.put("timeout_express","1583655720");//支付超时时间
        parm.put("total_amount",totalAmount);
        parm.put("body",body);
        parm.put("product_code","FAST_INSTANT_TRADE_PAY");
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
    @RequestMapping("/refund")
    @ResponseBody
    public String refund(){
        HashMap<String,String>parm=new HashMap<>(6);
        String out_request_no = new String(UUID.randomUUID().toString());
        //out_trade_no商家订单号
        //trade_no支付订单号
        //refund_amount退款金额
        //refund_currency人命币CNY
        //refund_reason正常退款
        parm.put("out_trade_no","123456111");
        parm.put("refund_amount","500.00");
      //  parm.put("refund_currency","CNY");
        parm.put("refund_reason","正常退款");
       // parm.put("out_request_no",out_request_no);
        return alipayService.refund(parm);
    }
}
