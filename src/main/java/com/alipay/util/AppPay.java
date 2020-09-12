package com.alipay.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * @Author YM
 * @Time 2020/5/26 19:37
 * @Version 1.0     APP支付使用这个类
 **/
@Component
public class AppPay {
    /**
     * 生产环境地址请使用：https://qr.chinaums.com/netpay-route-server/api/
     */
    final String makeOrderURL = "https://qr-test2.chinaums.com/netpay-route-server/api/";
    /**
     * 仅测试分账功能时使用（主商户号：898201612345678    子商户号：988460101800201 / 988460101800202）
     */
    //测试商户号
    String mid = "898310148160568";
    //测试终端号
    String tid = "00000001";
    //测试系统来源
    String msgSrc = "WWW.TEST.COM";
    //注意：全民付C扫B instMid为QRPAYDEFAULT，悦单C扫B instMid为QRPAYYUEDAN
    String instMid = "APPDEFAULT";
    //测试环境消息来源编号为3194，生产环境以分配的参数为准
    String msgSrcId = "3194";
    //测试环境密钥，生产环境请以分配的参数为准
    String md5Key = "fcAmtnx7MwismjWNhNKdHC44mNXtnEQeJkRrhKJwyrW2ysRR";
    //交易类型
    String tradeType = "APP";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders httpHeaders = new HttpHeaders();


    public String miniOrder() {
        // 组织请求报文，具体参数请参照接口文档，以下仅作模拟
        JSONObject json = new JSONObject();
        json.put("msgSrcId", "6190");
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", "wx.unifiedOrder");
        json.put("msgSrc", msgSrc);
        //MINIDEFAULT
        json.put("instMid", instMid);
        json.put("signType", "SHA256");
        //"619092020071011012900007"
        json.put("merOrderId", Util.genMerOrderId(msgSrcId));//Util.genMerOrderId(msgSrcId)); //账单号
        json.put("totalAmount", 1); //订单金额：单位为分，测试环境勿使用大额，建议1分钱
        //"2020-07-10 11:01:30"
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));//DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("tradeType", "APP");//MINI
        json.put("attachedData", "{\"id\":500000013}");
        // json.put("expireTime", "2019-07-29 10:34:00"); //账单过期时间,默认30分钟
        //敲黑板：subAppId微信支付必传，用于调起微信小程序;该字段为移动应用的APPID
        // json.put("subAppId","wx1299333999444");

        /*
        分账情况使用下列代码，增加subOrders参数 ，其中总金额totalAmount = platformAmount(平台金额)+suborders.totalAmount(各子商户订单金额)
        divisionFlag是否分账字段，分账的情况上送true,否则请勿上送
        */

//        json.put("divisionFlag", true);
//        json.put("platformAmount",0);
//        JSONArray ja = new JSONArray();
//        JSONObject jo = new JSONObject();
//        jo.put("mid", "898127210280002");
//        jo.put("totalAmount", 1);
//        ja.add(jo);
//        json.put("subOrders", ja);

        //wxba69955ff177df88
        json.put("subAppId","wx98369e085c6db913");
//        json.put("subOpenId", "oOY_r0PRPI5YUhSg8xP5JhfnGh3Q");
        //      支付结果通知地址
        json.put("notifyUrl", "http://15892449006.zicp.vip/test-refund");
        //      网页跳转地址
        //json.put("returnUrl", "https://qdhzapi.31huiyi.com/api/wireless/Pay/Notify/199");

        //注意：是否担保交易，非担保交易请勿上送该字段
        //json.put("secureTransaction", true);
        String md5Key = "i78BX6Gw7mWAwb3K4HfH5ypBZyyzjGNFbajaAjGzwQdhySNj";
        json.put("sign", Util.makeSign(md5Key, Util.jsonToMap(json), json.optString("signType")));

        //注意：http post请求报文头为json格式,编码为UTF-8
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(json, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(makeOrderURL, request, String.class);
        //表示返回成功
        String responseStr = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            responseStr = response.getBody();
            System.out.println("银商返回报文>>>>>>>>：" + responseStr);
        } else {
            System.out.println("请求异常，异常代码>>>>>>：" + response.getStatusCode());
        }
        return responseStr;
    }

    /**
     * 下单接口
     */
    public String order() {

        // 组织请求报文，具体参数请参照接口文档，以下仅作模拟
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        /**
         * msgType取值：wx.appPreOrder --APP微信支付，暂不支持测试环境测试，到生产环境测试
         *              wx.unifiedOrder--微信（直连）
         *              trade.precreate--支付宝
         *              uac.appOrder   --云闪付
         */
        json.put("msgType", "trade.precreate");
        json.put("msgSrc", "WWW.TEST.COM");
        //json.put("instMid",instMid);
        json.put("signType", "SHA256");
        json.put("merOrderId", Util.genMerOrderId("3194"));//Util.genMerOrderId(msgSrcId)); //账单号
        json.put("totalAmount", 1); //订单金额：单位为分，测试环境勿使用大额，建议1分钱
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("tradeType", tradeType);
        // json.put("expireTime", "2020-07-17 18:40:00"); //账单过期时间,默认30分钟
        //敲黑板：subAppId微信支付必传，用于调起微信小程序;该字段为移动应用的APPID
        // json.put("subAppId","wx1299333999444");

        /*
        分账情况使用下列代码，增加subOrders参数 ，其中总金额totalAmount = platformAmount(平台金额)+suborders.totalAmount(各子商户订单金额)
        divisionFlag是否分账字段，分账的情况上送true,否则请勿上送
        */
        //以下参数是分账情况需要添加的,如果不分账就不用添加以下参数
//        json.put("divisionFlag", true);
//        json.put("platformAmount", 1);
//        JSONArray ja = new JSONArray();
//        JSONObject jo = new JSONObject();
//        jo.put("mid", "898127210280001");
//        jo.put("totalAmount", 1);
//
//        JSONObject jo1 = new JSONObject();
//        jo1.put("mid", "898127210280002");
//        jo1.put("totalAmount", 1);
//        ja.add(jo);
//        ja.add(jo1);
//        json.put("subOrders", ja);
        //      支付结果通知地址http://60.12.37.147:44444/payOrder/payBack
        json.put("notifyUrl", "http://15892449006.zicp.vip/test-refund");
        //      网页跳转地址
        json.put("returnUrl", "http://175.42.1.180/payOrder/returnPage");

        //注意：是否担保交易，非担保交易请勿上送该字段
        //不需要天担保交易
//        json.put("secureTransaction", true);
//        String md5Key = "6CimBjBBwTypJ5sKRJHRS7tPGeNfa8TBsTJseEkwXPXm5DF4";
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
        } else {
            System.out.println("请求异常，异常代码>>>>>>：" + response.getStatusCode());
        }
        return responseStr;
    }

    /**
     * 支付结果查询
     */
    public String queryOrder(String merOrderId) {
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
            responseStr = response.getBody();
//            JSONObject responseJson = JSONObject.fromObject(responseStr);
//            if(responseJson.optString("errCode").equalsIgnoreCase("SUCCESS")){
//                isSuccessed = true;
//            }
            System.out.println("银商返回报文>>>>>>>>：" + responseStr);
        } else {
            System.out.println("请求异常，异常代码>>>>>>：" + response.getStatusCode());
        }
        return responseStr;
    }


    /**
     * 7.2订单退款请求
     */
    public String refund(String merOrderId, long totalAmount) {
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
        //表示返回成功
        String responseStr = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            responseStr = response.getBody();
//            JSONObject responseJson = JSONObject.fromObject(responseStr);
//            if(responseJson.optString("errCode").equalsIgnoreCase("SUCCESS")){
//                isSuccessed = true;
//            }
            System.out.println("银商返回报文>>>>>>>>：" + responseStr);
        } else {
            System.out.println("请求异常，异常代码>>>>>>：" + response.getStatusCode());
        }
        return responseStr;

    }

    /**
     * 接收银商的通知，商户作为服务端接收消息，该方法对应的地址就是商户下单接口中上送的notifyUrl地址
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/returnUrl.do", method = RequestMethod.GET)
    public String goReturnUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = Util.getRequestParams(request);
        System.out.println("params:" + params);
        // 验签
        boolean checkRet = Util.checkSign(md5Key, params);

        if (checkRet) {
            //跳转到你们自己的商城页面
            return "/WEB-INF/views/yourself.jsp";
        }
        return "/WEB-INF/views/yourselfErrorSign.jsp";
    }


    /**
     * 撤销担保交易：只有下单接口中上送了担保交易字段，才可以调用此方法撤销担保
     */
    public void cancelGuarantee(String merOrderId) {
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", "secureCancel"); //账单查询，悦单C扫B此处传secureCancel
        json.put("msgSrc", msgSrc);
        json.put("merOrderId", merOrderId);
        json.put("instMid", instMid);
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("sign", Util.makeSign(md5Key, Util.jsonToMap(json), json.optString("signType")));

        //注意：http post请求报文头为json格式,编码为UTF-8
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(json, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(makeOrderURL, request, String.class);
        //表示返回成功
        String responseStr = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            responseStr = response.getBody();
//            JSONObject responseJson = JSONObject.fromObject(responseStr);
//            if(responseJson.optString("errCode").equalsIgnoreCase("SUCCESS")){
//                isSuccessed = true;
//            }
            System.out.println("银商返回报文>>>>>>>>：" + responseStr);
        } else {
            System.out.println("请求异常，异常代码>>>>>>：" + response.getStatusCode());
        }

    }

    /**
     * 担保完成交易：只有下单接口中上送了担保交易字段，才可以调用此方法执行担保完成
     */
    public void completeGuarantee(String merOrderId, long completedAmount) {
        JSONObject json = new JSONObject();
        json.put("mid", mid);
        json.put("tid", tid);
        json.put("msgType", "secureComplete"); //账单查询，悦单C扫B此处传secureComplete
        json.put("msgSrc", msgSrc);
        json.put("merOrderId", merOrderId);
        json.put("signType", "SHA256");
        json.put("completedAmount", completedAmount);//单位是：分
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        json.put("sign", Util.makeSign(md5Key, Util.jsonToMap(json), json.optString("signType")));

        //注意：http post请求报文头为json格式,编码为UTF-8
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity request = new HttpEntity(json, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(makeOrderURL, request, String.class);
        //表示返回成功
        String responseStr = null;
        if (response.getStatusCode() == HttpStatus.OK) {
            responseStr = response.getBody();
//            JSONObject responseJson = JSONObject.fromObject(responseStr);
//            if(responseJson.optString("errCode").equalsIgnoreCase("SUCCESS")){
//                isSuccessed = true;
//            }
            System.out.println("银商返回报文>>>>>>>>：" + responseStr);
        } else {
            System.out.println("请求异常，异常代码>>>>>>：" + response.getStatusCode());
        }

    }

    /**
     * 退款查询
     */
    public String refundQuery(String refundOrderId) {
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
            responseStr = response.getBody();
//            JSONObject responseJson = JSONObject.fromObject(responseStr);
//            if(responseJson.optString("errCode").equalsIgnoreCase("SUCCESS")){
//                isSuccessed = true;
//            }
            System.out.println("银商返回报文>>>>>>>>：" + responseStr);
        } else {
            System.out.println("请求异常，异常代码>>>>>>：" + response.getStatusCode());
        }
        return responseStr;
    }

    public String closeOrder(String merOrderId) {
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
            responseStr = response.getBody();
//            JSONObject responseJson = JSONObject.fromObject(responseStr);
//            if(responseJson.optString("errCode").equalsIgnoreCase("SUCCESS")){
//                isSuccessed = true;
//            }
            System.out.println("银商返回报文>>>>>>>>：" + responseStr);
        } else {
            System.out.println("请求异常，异常代码>>>>>>：" + response.getStatusCode());
        }
        return responseStr;
    }
}
