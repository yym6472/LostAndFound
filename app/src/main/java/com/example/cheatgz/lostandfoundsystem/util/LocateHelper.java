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

import java.util.Date;

public class LocateHelper implements AMapLocationListener {
    private Context context;
    private ThisApplication application;

    public LocateHelper(Context context, ThisApplication application) {
        this.context = context;
        this.application = application;
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
                    values.put("time", aMapLocation.getTime());
                    values.put("positionX", aMapLocation.getLongitude());
                    values.put("positionY", aMapLocation.getLatitude());
                    db.insert(LocationInfoHelper.LocationInfoTable.TABLE_NAME,
                            null, values);
                    System.out.println("TIME: " + new Date(aMapLocation.getTime()));
                    System.out.println("POSITION_X: " + aMapLocation.getLongitude());
                    System.out.println("POSITION_Y: " + aMapLocation.getLatitude());
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
}
