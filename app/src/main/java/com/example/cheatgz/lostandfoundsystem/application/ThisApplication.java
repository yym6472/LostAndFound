package com.example.cheatgz.lostandfoundsystem.application;

import android.app.Application;

public class ThisApplication extends Application {

    private Integer userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        userId = -1;
    }
}
