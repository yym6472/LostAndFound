package com.yymstaygold.lostandfound.client.entity;

import java.util.Date;

/**
 * Created by yanyu on 2018/4/16.
 */
public class Found {
    private int foundId;
    private String foundName;
    private int userId;
    private String phoneNumber;
    private Item item;
    private Date foundTime;
    private double foundPositionX;
    private double foundPositionY;

    public Found() {}

    public int getFoundId() {
        return foundId;
    }

    public void setFoundId(int foundId) {
        this.foundId = foundId;
    }

    public String getFoundName() {
        return foundName;
    }

    public void setFoundName(String foundName) {
        this.foundName = foundName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    public double getFoundPositionX() {
        return foundPositionX;
    }

    public void setFoundPositionX(double foundPositionX) {
        this.foundPositionX = foundPositionX;
    }

    public double getFoundPositionY() {
        return foundPositionY;
    }

    public void setFoundPositionY(double foundPositionY) {
        this.foundPositionY = foundPositionY;
    }
}
