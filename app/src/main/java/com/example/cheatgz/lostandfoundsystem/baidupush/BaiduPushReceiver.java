package com.example.cheatgz.lostandfoundsystem.baidupush;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.example.cheatgz.lostandfoundsystem.ActivityCollector;
import com.example.cheatgz.lostandfoundsystem.R;
import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.example.cheatgz.lostandfoundsystem.change_info;
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
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<LoginResult>() {
                        @Override
                        public void call(LoginResult loginResult) {
                            Log.d(TAG, "userId: " + loginResult.getResult().getUserId());
                            ((ThisApplication)context.getApplicationContext()).setUserId(loginResult.getResult().getUserId());
                            if (loginResult.getResult().isFirstLogin()) {
                                Toast.makeText(context, "系统检测到你是第一次登录，请设置个人信息。", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(context, change_info.class);
                                context.startActivity(intent);
                            }
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
        Log.d(TAG, "onNotificationClicked: " + s + " " + s1 + " " + s2);
    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {
        Log.d(TAG, "onNotificationArrived: " + s + " " + s1 + " " + s2);
    }
}
