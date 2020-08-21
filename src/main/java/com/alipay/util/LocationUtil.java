package com.alipay.util;

import java.math.BigDecimal;

/**
 * @author lovelyhedong
 * @date 2020年8月21日 22:49:53
 */
public class LocationUtil {
    // 赤道半径
    private static final double EARTH_RADIUS = 6378137;

    /**
     * 根据经纬度获取两点的距离(单位米)
     */
    public static Double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据经纬度获取两点的距离(单位米)
     */
    public static Double getDistance(BigDecimal lng1, BigDecimal lat1, BigDecimal lng2, BigDecimal lat2) {
        return getDistance(lng1.doubleValue(), lat1.doubleValue(), lng2.doubleValue(), lat2.doubleValue());
    }
}