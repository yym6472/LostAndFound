package com.example.cheatgz.lostandfoundsystem.application;

import android.app.Application;

public class ThisApplication extends Application {

    private Integer userId;
    private String phoneNumber;

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

    @Override
    public void onCreate() {
        super.onCreate();
        userId = -1;
        phoneNumber = "";
    }
}
