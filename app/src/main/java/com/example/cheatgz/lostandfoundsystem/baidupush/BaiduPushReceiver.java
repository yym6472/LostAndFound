package com.example.cheatgz.lostandfoundsystem.baidupush;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.yymstaygold.lostandfound.client.entity.LoginResult;
import com.yymstaygold.lostandfound.client.util.retrofit.LFSApiService;
import com.yymstaygold.lostandfound.client.util.retrofit.RetrofitServiceManager;

import java.util.List;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class BaiduPushReceiver extends PushMessageReceiver {
    @Override
    public void onBind(final Context context, int i, String s, String s1, String s2, String s3) {
        Log.d(TAG, "onBind: " + i + " " + s + " " + s1 + " " + s2 + " " + s3);

        if (i == 0) {
            RetrofitServiceManager.getInstance().create(LFSApiService.class)
                    .login(((ThisApplication)context.getApplicationContext()).getPhoneNumber(), s2)
                    .map(new Func1<LoginResult, Integer>() {
                        @Override
                        public Integer call(LoginResult loginResult) {
                            if (loginResult.getCode() == 0) {
                                return loginResult.getResult().getUserId();
                            } else {
                                return -1;
                            }
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Integer>() {
                        @Override
                        public void call(Integer integer) {
                            Log.d(TAG, "userId: " + integer);
                            ((ThisApplication)context.getApplicationContext()).setUserId(integer);
                        }
                    });
        }
    }

    @Override
    public void onUnbind(Context context, int i, String s) {
        Log.d(TAG, "onUnbind: " + i + " " + s);
    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Log.d(TAG, "onSetTags");
    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {
        Log.d(TAG, "onDelTags");
    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {
        Log.d(TAG, "onListTags");
    }

    @Override
    public void onMessage(Context context, String s, String s1) {
        Log.d(TAG, "onMessage: " + s + " " + s1);

        if (s.equals("FORCE_OFFLINE")) {
            Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
            context.sendBroadcast(intent);
        }
    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {
        Log.d(TAG, "onNotificationClicked");
    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {
        Log.d(TAG, "onNotificationArrived");
    }
}
