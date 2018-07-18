package com.example.cheatgz.lostandfoundsystem.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.AbsListView;

import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.example.cheatgz.lostandfoundsystem.util.LocateHelper;

import java.util.Calendar;
import java.util.Date;


public class IsMovingService extends Service {
    private static final String TAG="IsmovinService";
    private SensorManager sensorManager;
    private Sensor sensor ;
    private AlarmManager alarmManager;
    private Calendar calendar;
    private MsensorEventListener sensorEventListener;
    private float mX,mY,mZ;
    private long lastTime;
    private int interval;
    private int intiInterval;
    private int timeInterval;
    private int count;
    private static final int MAX_COUNT=4;
    private PendingIntent pendingIntent;
    private Intent intent;
    private Intent intent2;
    private LocateHelper locateHelper1;
    private LocateHelper locateHelper2;
    private int mark;
    static double EARTH_RADIUS = 6371.0;

    private class MsensorEventListener implements SensorEventListener {
        private float cX,cY,cZ;
        private long currentTime;

        public float getcX() {
            return cX;
        }

        public float getcY() {
            return cY;
        }

        public float getcZ() {
            return cZ;
        }

        public long getCurrentTime() {
            return currentTime;
        }

        public float MaxValue(float a,float b, float c){
            if(a>b){
                if(a>c)
                    return a;
                else
                    return c;
            }
            else{
                if(b>c)
                    return b;
                else
                    return c;
            }
        }
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if(sensorManager==null){
                Log.d(TAG,"SenseorManager is null");
                startService(intent2);
            }
            else {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
                    cX = Math.abs(sensorEvent.values[0]);
                    cY = Math.abs(sensorEvent.values[1]);
                    cZ = Math.abs(sensorEvent.values[2]);
                    currentTime = calendar.getTimeInMillis();
                    if (MaxValue(cX, cY, cZ) > 1 && currentTime - lastTime > 30 * 1000) {
                        Locate();
                    } else if (count == MAX_COUNT) {
                        Locate();
                        compareLocates();
                    } else
                        count = count + 1;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    @Override
    public void onCreate() {
        intent=new Intent(IsMovingService.this,IsMovingService.class);
        pendingIntent=PendingIntent.getService(IsMovingService.this,0,intent,0);
        mark=1;
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        mX=0;
        mY=0;
        mZ=0;
        intiInterval=2*60;
        interval=2*60;
        count=0;
        intent2=new Intent(IsMovingService.this,UserLocateService.class);
        lastTime=calendar.getTimeInMillis();
        sensorEventListener=new MsensorEventListener();
        locateHelper1 = new LocateHelper(IsMovingService.this ,
                                           ( ThisApplication) IsMovingService.this.getApplicationContext()
                                            , new Date((new Date()).getTime() / 300000 * 300000));
        locateHelper1.locateOnce();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_GAME);
        timeInterval=intiInterval+count*interval;
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeInterval*1000,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    public IsMovingService() {
    }
    public void Locate(){
        if(mark==1) {
            locateHelper2 = new LocateHelper(IsMovingService.this,
                    (ThisApplication) IsMovingService.this.getApplicationContext()
                    , new Date((new Date()).getTime() / 300000 * 300000));
            locateHelper2.locateOnce();
            mark=2;
        }
        if(mark==2){
            locateHelper1= new LocateHelper(IsMovingService.this,
                    (ThisApplication) IsMovingService.this.getApplicationContext()
                    , new Date((new Date()).getTime() / 300000 * 300000));
            locateHelper1.locateOnce();
            mark=1;
        }

    }

    private static double getRad(double d) {
        return d * Math.PI / 180.0;
    }


    public void compareLocates(){
         double radLat1 = getRad(locateHelper1.getLatitude());
         double radLat2 = getRad(locateHelper2.getLatitude());
         double dy = radLat1 - radLat2; //
         double dx = getRad(locateHelper1.getLongitude()) - getRad(locateHelper2.getLongitude()); // b
         double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dy / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(dx / 2), 2)));
         s = s * EARTH_RADIUS;
         s = Math.round(s * 10000) / 10000.0;
         if(s>50)
             count=0;
     }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
