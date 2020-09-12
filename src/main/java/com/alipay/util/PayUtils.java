package com.alipay.util;

import com.google.gson.JsonParser;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

public class PayUtils {
    //银联测试环境支付地址:https://qr-test2.chinaums.com/netpay-route-server/api/
    //生产环境:https://qr.chinaums.com/netpay-route-server/api/
    public static String makeOrderURL = "https://qr.chinaums.com/netpay-route-server/api/";

    //测试商户号:898310148160568
    //生产环境:898652849000092
    public static String mid = "898652849000092";

    //测试终端号:00000001
    //生产环境:65396196
    public static String tid = "65396196";

    //测试环境密钥，生产环境请以分配的参数为准
    //测试环境密钥:fcAmtnx7MwismjWNhNKdHC44mNXtnEQeJkRrhKJwyrW2ysRR
    //生产环境密钥:htQpM67BbWiTDWQFazEN8i5trJND3ZiGXzhn3fCcQHB5EDj8
    public static String md5Key = "htQpM67BbWiTDWQFazEN8i5trJND3ZiGXzhn3fCcQHB5EDj8";
    //测试环境:WWW.TEST.COM
    //生产环境:WWW.KELHJKJ.COM
    public static String msgSrc = "WWW.KELHJKJ.COM";

    //测试环境:3194
    //生产环境:4856
    public static String msgId = "4856";

    public static String instMid = "APPDEFAULT";

    //交易类型
    public static String tradeType = "APP";

    private static RestTemplate restTemplate = new RestTemplate();

    private static HttpHeaders httpHeaders = new HttpHeaders();

    //交易类型
    public static String subAppId = "wx98369e085c6db913";

    /**
     * 下单接口
     */
    public static String placeOnOrder(Integer payType) {

        // 组织请求报文，具体参数请参照接口文档，以下仅作模拟
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        switch (payType) {
            case 1:
                json.put("msgType", "wx.appPreOrder");
                //敲黑板：subAppId微信支付必传，用于调起微信小程序;该字段为移动应用的APPID
                // json.put("subAppId","wx1299333999444");
                json.put("subAppId", subAppId);
                json.put("instMid", instMid);
                break;
            case 3:
                json.put("msgType", "wx.unifiedOrder");
                break;
            case 2:
                json.put("msgType", "trade.precreate");
                break;
            case 4:
                json.put("msgType", "uac.appOrder");
                break;
            default:
                //暂时只有这几个值,测试如果没有里面的值,直接返回报错
                throw new RuntimeException();
        }
        /**
         * msgType取值：wx.appPreOrder --APP微信支付，暂不支持测试环境测试，到生产环境测试1
         *              wx.unifiedOrder--微信（直连）2
         *              trade.precreate--支付宝3
         *              uac.appOrder   --云闪付4
         */
        json.put("msgSrc", msgSrc);
        //json.put("instMid",instMid);
        json.put("signType", "SHA256");
        //订单号
        String orderId = Util.genMerOrderId(msgId);
        json.put("merOrderId", orderId);//Util.genMerOrderId(msgSrcId)); //账单号
        json.put("totalAmount", 1); //订单金额：单位为分，测试环境勿使用大额，建议1分钱
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("tradeType", tradeType);
        //TODO 账单添加到redis(orderId)
        // json.put("expireTime", "2020-07-17 18:40:00"); //账单过期时间,默认30分钟
        //      支付结果通知地址
        json.put("notifyUrl", "http://15892449006.zicp.vip/test-notify");
        //      网页跳转地址(暂时不需要,如果有网页支付)
//        json.put("returnUrl", "http://175.42.1.180/payOrder/returnPage");
        json.put("sign", Util.makeSign(md5Key, Util.jsonToMap(json), json.optString("signType")));
        //注意：http post请求报文头为json格式,编码为UTF-8
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(json, httpHeaders);
        System.out.println(request.getBody());
        ResponseEntity<String> response = restTemplate.postForEntity(makeOrderURL, request, String.class);
        //表示返回成功
        String responseStr = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            responseStr = response.getBody();
            System.out.println("银商返回报文>>>>>>>>：" + responseStr);
            // responseStr = JsonParser.parseString(response.getBody()).getAsJsonObject().get("appPayRequest").getAsJsonObject().get("qrCode").getAsString();
        }
        //下单失败返回空
        return responseStr;
    }

    /**
     * @param merOrderId
     * @param totalAmount
     * @return
     */
    public static boolean refund(String merOrderId, long totalAmount) {
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", "refund"); //订单退款
        json.put("msgSrc", msgSrc);
        json.put("merOrderId", merOrderId);
        json.put("signType", "SHA256");
        //敲黑板：refundOrderId非必传项；但是当一笔订单分多次退款，该字段必传
        //json.put("refundOrderId",billNo+"_1"); 退款订单号
        json.put("refundAmount", totalAmount); //单位是：分
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("sign", Util.makeSign(md5Key, Util.jsonToMap(json), json.optString("signType")));
        //注意：http post请求报文头为json格式,编码为UTF-8
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(json, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(makeOrderURL, request, String.class);
        //判断退款是否成功
        boolean isSuccessed = false;
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseJson = JSONObject.fromObject(response.getBody());
            if (responseJson.optString("errCode").equalsIgnoreCase("SUCCESS")) {
                //退款成功
                isSuccessed = true;
            }
        }
        //退款失败返回false
        return isSuccessed;

    }


    /**
     * 支付结果查询
     */
    public static String queryOrder(String merOrderId) {
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", "query");
        json.put("msgSrc", msgSrc);
        json.put("merOrderId", merOrderId);
        json.put("signType", "SHA256");
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("sign", Util.makeSign(md5Key, Util.jsonToMap(json), json.optString("signType")));
        //注意：http post请求报文头为json格式,编码为UTF-8
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(json, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(makeOrderURL, request, String.class);
        //表示返回成功
        String responseStr = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            String errCode = JsonParser.parseString(response.getBody()).getAsJsonObject().get("errCode").getAsString();
            if (!errCode.equals("SUCCESS")) {
                //返回空,查询失败
                return null;
            } else {
                responseStr = response.getBody();
            }
        }
        //如果没有查询到返回空
        return responseStr;
    }

    /**
     * 关闭订单
     *
     * @param merOrderId
     * @return
     */
    public static String closeOrder(String merOrderId) {
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", "close");
        json.put("msgSrc", msgSrc);
        json.put("signType", "SHA256");
        json.put("merOrderId", merOrderId); //此处填入的是退款订单号
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("sign", Util.makeSign(md5Key, Util.jsonToMap(json), json.optString("signType")));

        //注意：http post请求报文头为json格式,编码为UTF-8
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(json, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(makeOrderURL, request, String.class);
        //表示返回成功
        String responseStr = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseJson = JSONObject.fromObject(responseStr);
            if (responseJson.optString("errCode").equalsIgnoreCase("SUCCESS")) {
                responseStr = response.getBody();
            }
        }
        //关闭失败返回空
        return responseStr;
    }

    /**
     * 退款查询
     */
    public static String refundQuery(String refundOrderId) {
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", "refundQuery");
        json.put("msgSrc", msgSrc);
        json.put("signType", "SHA256");
        json.put("merOrderId", refundOrderId); //此处填入的是退款订单号
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("sign", Util.makeSign(md5Key, Util.jsonToMap(json), json.optString("signType")));

        //注意：http post请求报文头为json格式,编码为UTF-8
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(json, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(makeOrderURL, request, String.class);
        //表示返回成功
        String responseStr = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject responseJson = JSONObject.fromObject(responseStr);
            if (responseJson.optString("errCode").equalsIgnoreCase("SUCCESS")) {
                responseStr = response.getBody();
            }
        }
        return responseStr;
    }

}



