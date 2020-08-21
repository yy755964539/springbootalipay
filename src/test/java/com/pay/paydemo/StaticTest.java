package com.pay.paydemo;

/**
 * @author xinyan
 * @title: StaticTest
 * @projectName paydemo
 * @description: TODO
 * @date 2020/4/14 17:34
 */
public class StaticTest {


    static int j=6;


    public void noStaticMethod(){
        StaticTest.staticMethod();
    }

    public static void staticMethod(){
        System.out.println(j);
    }
    public static void main(String[] args) {
        new StaticTest().noStaticMethod();
    }
}
