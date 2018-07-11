package com.example.cheatgz.lostandfoundsystem.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.example.cheatgz.lostandfoundsystem.R;
import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.example.cheatgz.lostandfoundsystem.main;
import com.example.cheatgz.lostandfoundsystem.util.LocateHelper;
import com.yymstaygold.lostandfound.client.test.TestActivity;

import java.util.Date;

public class UserLocateService extends Service {

    private IntentFilter intentFilter;

    private TrackRecordReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();

        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        receiver = new TrackRecordReceiver(new TrackRecordReceiver.OnTrackedListener() {
            @Override
            public void OnTracked(double lat, double lng, Date time) {
                startForeground(1, createTrackNotification(false, time, lat, lng));
            }
        });

        startForeground(1, createTrackNotification(true, new Date(), 0.0, 0.0));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(receiver, intentFilter);
        return super.onStartCommand(intent, flags, startId);
    }

    private Notification createTrackNotification(boolean isFirstCreate, Date time, double lat, double lng) {
        String info = "";
        if (isFirstCreate) {
            info = "请等待下一次定位...";
        } else {
            info = "最新位置：[lat: " + lat + "; lng: " + lng + "]";
        }

        Intent intent = new Intent(this, TestActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this, "default")
                .setContentTitle("LFS 持续定位中...")
                .setContentText(info)
                .setWhen(time.getTime())
                .setSmallIcon(R.mipmap.icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.icon))
                .setContentIntent(pi)
                .build();
        return notification;
    }
}
