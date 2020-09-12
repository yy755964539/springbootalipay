package com.pay.paydemo;

import com.alipay.util.AppPay;

public class PayTest {

    public static void main(String[] args) {
        AppPay appPay=new AppPay();
        String order = appPay.order();
        System.out.println(order);
    }
}
