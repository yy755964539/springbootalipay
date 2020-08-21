package com.pay.paydemo.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.config.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author xinyan
 * @title: alipayservice
 * @projectName paydemo
 * @description: TODO
 * @date 2020/3/8 14:28
 */
@Service
public class AlipayService {

    @Autowired
    private DefaultAlipayClient defaultAlipayClient;


    public String alipay(HashMap<String,String>data)throws AlipayApiException{
        try{
            //        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();// APP支付
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();  // 网页支付
//        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();  //移动h5
            request.setNotifyUrl(AlipayConfig.notify_url);
            request.setReturnUrl(AlipayConfig.return_url);
            request.setBizContent(JSON.toJSONString(data));
            String response = defaultAlipayClient.pageExecute(request).getBody();
            return response;
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String refund(HashMap<String,String>data){
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JSON.toJSONString(data));
        try {
            AlipayTradeRefundResponse response = defaultAlipayClient.execute(request);
            if (response.isSuccess()){
                System.out.println("成功");
            }else{System.out.println("失败");}
            return response.getMsg();
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
