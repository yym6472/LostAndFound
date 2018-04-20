package com.example.cheatgz.lostandfoundsystem;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;

public class LocateService extends Service {
    private Database database;
    public LocateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        database = new Database(this, "UserLocation.db", null, 1);
//        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ThisApplication application = (ThisApplication) getApplication();
        locationlatitudelontitude location=new locationlatitudelontitude(this, application.getUserId());
        System.out.println(123124);
        location.location();
//        //System.out.println(location.locationlist.get(1).getTime());
//        ContentValues values=new ContentValues();
//        values.put("UserID",101);
//        values.put("XLocate", 123);
//        values.put("YLocate",456);
//        values.put("Time","2015-03-18-20-20");
//        sqLiteDatabase.insert("user_location",null,values);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
