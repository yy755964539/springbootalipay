package com.pay.paydemo;

import java.util.Calendar;
import java.util.Date;

/**
 * @author xinyan
 * @title: StringFormatTest
 * @projectName paydemo
 * @description: TODO
 * @date 2020/4/15 20:14
 */
public class StringFormatTest {

    public static void main(String[] args) {
        Date date=new Date();
        String format = String.format("%tc", new Date());
        System.out.println(format);
        String format1 = String.format("%tr", new Date());
        System.out.println(format1);
        String format2 = String.format("%tA %tB %tC %tY", date,date,date,date);
        System.out.println(format2);
        String format3 = String.format("%tA %<tB %<tC %<tY", date);
        System.out.println(format3);
        Calendar calendar=Calendar.getInstance();
        calendar.set(2007,1,1,18,6);
        long timeInMillis = calendar.getTimeInMillis();
        Date time = calendar.getTime();
        System.out.println(time);
        calendar.roll(Calendar.DATE,55);
        Date time1 = calendar.getTime();
        System.out.println(time1);
    }
}
