package com.example.cheatgz.lostandfoundsystem.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.example.cheatgz.lostandfoundsystem.db.LocationInfoHelper;
import com.example.cheatgz.lostandfoundsystem.service.TrackRecordReceiver;

import java.util.Date;

public class LocateHelper implements AMapLocationListener {

    private static final String TAG = "LocateHelper";
    private Context context;
    private ThisApplication application;
    private TrackRecordReceiver.OnTrackedListener listener;
    private Date locateTime;
    private double longitude;
    private double latitude;

    public LocateHelper(Context context, ThisApplication application, TrackRecordReceiver.OnTrackedListener listener, Date locateTime) {
        this.context = context;
        this.application = application;
        this.listener = listener;
        this.locateTime = locateTime;
    }
    public LocateHelper(Context context, ThisApplication application, Date locateTime) {
        this.context = context;
        this.application = application;
        this.listener = null;
        this.locateTime = locateTime;
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation == null) return;

        if (aMapLocation.getErrorCode() == 0) {
            if (aMapLocation == null) return;

            if (aMapLocation.getErrorCode() == 0) {
                int userId = application.getUserId();
                if (userId > 0) {
                    LocationInfoHelper helper = LocationInfoHelper.getInstance(context);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("userId", userId);
                    values.put("time", locateTime.getTime());
                    values.put("positionX", aMapLocation.getLongitude());
                    values.put("positionY", aMapLocation.getLatitude());
                    latitude=aMapLocation.getLatitude();
                    longitude=aMapLocation.getLongitude();
                    db.insert(LocationInfoHelper.LocationInfoTable.TABLE_NAME,
                            null, values);
                    Log.d(TAG, "onLocationChanged: TIME: " + new Date(locateTime.getTime()));
                    Log.d(TAG, "onLocationChanged: POSITION_X: " + aMapLocation.getLongitude());
                    Log.d(TAG, "onLocationChanged: POSITION_Y: " + aMapLocation.getLatitude());

//                    if (listener != null) {
//                        listener.OnTracked(aMapLocation.getLatitude(), aMapLocation.getLongitude(), locateTime);
//                    }
                }
            } else {
                Log.e("AmapError", "location Error, ErrCode:" +
                        aMapLocation.getErrorCode() + ", errInfo:" +
                        aMapLocation.getErrorInfo());
            }
        } else {
            Log.e("AmapError", "location Error, ErrCode:" +
                    aMapLocation.getErrorCode() + ", errInfo:" +
                    aMapLocation.getErrorInfo());
        }
    }

    public void locateOnce() {
        AMapLocationClient locationClient = new AMapLocationClient(context);
        locationClient.setLocationListener(this);
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setOnceLocation(true);
        locationOption.setOnceLocationLatest(false);
        locationOption.setLocationCacheEnable(false);
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
