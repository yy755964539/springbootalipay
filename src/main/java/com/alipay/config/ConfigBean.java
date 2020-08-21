package com.alipay.config;

import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xinyan
 * @title: ConfigBean
 * @projectName paydemo
 * @description: TODO
 * @date 2020/3/8 14:05
 */
@Configuration
public class ConfigBean {
    //构建客户端
    @Bean
    public DefaultAlipayClient defaultAlipayClient(@Qualifier("retAlipayConfig")AlipayConfig alipayConfig){
        return new DefaultAlipayClient(
                AlipayConfig.URL,
                AlipayConfig.APPID,
                AlipayConfig.RSA_PRIVATE_KEY,
                AlipayConfig.FORMAT,
                AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.SIGNTYPE);
    }

    //阿里支付的配置config
    @Bean
    public AlipayConfig retAlipayConfig(){
        return new AlipayConfig();
    }


}
