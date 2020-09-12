package com.pay.paydemo;

import com.alipay.util.LocationUtil;

import java.math.BigDecimal;

/**
 * 需要的依赖
 * <dependency>
 * 　　<groupId>org.gavaghan</groupId>
 * 　　<artifactId>geodesy</artifactId>
 * 　　<version>1.1.3</version>
 * </dependency>
 * public static double getDistance(double longitudeFrom, double latitudeFrom, double longitudeTo, double latitudeTo) {
 * GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
 * GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);
 * return new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, source, target).getEllipsoidalDistance();
 * }
 */
public class MapTest {


    public static void main(String[] args) {
        //相差25米 大约等于0.024公里
//        Double fristLng = 104.02436160776520;//第一个经度
//        Double fristLat = 30.75109533912993;//第一个纬度
//        Double secoundLng = 104.02270936701203;//第二个经度
//        Double secoundLat = 30.75073113557945;//第二个纬度
        //        System.out.println(Math.round(getDistance(fristLng,
//                fristLat,
//                secoundLng,
//                secoundLat)) / 1000d);//这里除以1000,换算成了公里,如果不除以1000就是米数
        BigDecimal fristLng = new BigDecimal(104.02436160776520);//第一个经度
        BigDecimal fristLat = new BigDecimal(30.75109533912993);//第一个纬度
        BigDecimal secoundLng = new BigDecimal(104.02270936701203);//第二个经度
        BigDecimal secoundLat = new BigDecimal(30.75073113557945);//第二个纬度
        Double distance = LocationUtil.getDistance(fristLng,
                fristLat,
                secoundLng,
                secoundLat);
        System.out.println(distance);
    }


}
