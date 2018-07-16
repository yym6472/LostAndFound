package com.example.cheatgz.lostandfoundsystem.baidupush;

import android.content.Context;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

public class BaiduPushService {
    private static final String API_KEY = "VpN7bFzyHygI1FiTm9Ggpl6o";
    public static void startWork(Context context) {
        PushManager.startWork(context.getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, API_KEY);
    }
}
