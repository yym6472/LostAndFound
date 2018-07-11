package com.example.cheatgz.lostandfoundsystem.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.example.cheatgz.lostandfoundsystem.util.LocateHelper;

import java.util.Calendar;
import java.util.Date;

public class TrackRecordReceiver extends BroadcastReceiver {

    private OnTrackedListener listener = null;

    public TrackRecordReceiver() {}

    public TrackRecordReceiver(OnTrackedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (Calendar.getInstance().get(Calendar.MINUTE) % 5 == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LocateHelper locateHelper = new LocateHelper(context, (ThisApplication) context.getApplicationContext(), listener, new Date((new Date()).getTime() / 300000 * 300000));
                    locateHelper.locateOnce();
                }
            }).start();
        }
    }

    public interface OnTrackedListener {
        void OnTracked(double lat, double lng, Date time);
    }
}
