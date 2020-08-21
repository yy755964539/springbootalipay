package com.pay.paydemo;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

public class MapTest {

    public static void main(String[] args) {
        //相差25米 大约等于0.024公里
        System.out.println(Math.round(getDistance(104.03405, 30.754414, 104.034113, 30.7542))/1000d);
    }

    public static double getDistance(double longitudeFrom, double latitudeFrom, double longitudeTo, double latitudeTo) {
        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
        GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);

        return new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, source, target).getEllipsoidalDistance();
    }




}
