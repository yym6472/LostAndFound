package com.yymstaygold.lostandfound.client.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yanyu on 2018/4/16.
 */
public class Lost {
    private int lostId;
    private String lostName;
    private int userId;
    private Item item;
    private ArrayList<Date> lostPositionInfoTime;
    private ArrayList<Double> lostPositionInfoPositionX;
    private ArrayList<Double> lostPositionInfoPositionY;

    public Lost() {}

    public int getLostId() {
        return lostId;
    }

    public void setLostId(int lostId) {
        this.lostId = lostId;
    }

    public String getLostName() {
        return lostName;
    }

    public void setLostName(String lostName) {
        this.lostName = lostName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ArrayList<Date> getLostPositionInfoTime() {
        return lostPositionInfoTime;
    }

    public void setLostPositionInfoTime(ArrayList<Date> lostPositionInfoTime) {
        this.lostPositionInfoTime = lostPositionInfoTime;
    }

    public ArrayList<Double> getLostPositionInfoPositionX() {
        return lostPositionInfoPositionX;
    }

    public void setLostPositionInfoPositionX(ArrayList<Double> lostPositionInfoPositionX) {
        this.lostPositionInfoPositionX = lostPositionInfoPositionX;
    }

    public ArrayList<Double> getLostPositionInfoPositionY() {
        return lostPositionInfoPositionY;
    }

    public void setLostPositionInfoPositionY(ArrayList<Double> lostPositionInfoPositionY) {
        this.lostPositionInfoPositionY = lostPositionInfoPositionY;
    }
}
