package com.example.cheatgz.lostandfoundsystem;

import java.util.Date;

/**
 * Created by Administrator on 2018/4/18.
 */

public class locationobject {
    private double latitude;
    private double longtitude;
    private String time;
    public locationobject(double longtitude,double latitude,String time){
        this.longtitude=longtitude;
        this.latitude=latitude;
        this.time=time;
    }
    public locationobject(){
        this.longtitude=0;
        this.latitude=0;
        this.time="null";
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
