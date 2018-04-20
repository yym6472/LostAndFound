package com.example.cheatgz.lostandfoundsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.lang.*;
import java.io.*;

public class locationlatitudelontitude implements AMapLocationListener, LocationSource {
    private int temp;
    private AMap aMap;
    //AMap是地图对象
    //private MapView mapView;
    private AMapLocationClient mLocationClient = null;
    //声明AMapLocationClient类对象，定位发起端
    public AMapLocationClientOption mLocationOption = null;
    //声明mLocationOption对象，定位参数
    private OnLocationChangedListener mListener = null;
    //声明mListener对象，定位监听器
    private boolean isFirstLoc = true;

    //标识，用于判断是否只显示一次定位信息和用户重新定位
    Context context;
    public SQLiteDatabase sqLiteDatabase ;
   // public  ArrayList<locationobject> locationlist;
    private locationobject loc;
    private  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String mytime=new String();
    Database database;

    private int userId;

    public locationlatitudelontitude(Context context, int userId){
        this.userId = userId;
        database = new Database(context , "UserLocation.db", null, 1);

        sqLiteDatabase = database.getWritableDatabase();
        sqLiteDatabase.execSQL("drop  table user_location");
        sqLiteDatabase.execSQL("create table user_location (UserID integer , XLocate double,YLocate double,Time datetime primary key)");
    //     locationlist = new ArrayList<locationobject>();
        loc=new locationobject();
        this.context=context;
    }



//    public locationlatitudelontitude() {
//        locationlist = new ArrayList<locationobject>();
//}
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //获取地图控件引用
//        //mapView = (MapView)findViewById(R.id.map_view);
//        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
//        //mapView.onCreate(savedInstanceState);
////        if(aMap == null){
////            aMap = mapView.getMap();
////            UiSettings settings = aMap.getUiSettings();
////            aMap.setLocationSource(this);
////            settings.setMyLocationButtonEnabled(true);
////            aMap.setMyLocationEnabled(true);
////        }
//        location();
//    }

    public  void location() {
        mLocationClient = new AMapLocationClient(context);
;       mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(false);
        mLocationOption.setWifiActiveScan(true);
        mLocationOption.setMockEnable(false);
        mLocationOption.setInterval(2000);

        mLocationClient.setLocationOption(mLocationOption);

        System.out.println("定位1");
        mLocationClient.startLocation();
    }
//    @Override
//    protected void onResume(){
//        super.onResume();
//        //mapView.onResume();
//    }
//    @Override
//    protected void onPause(){
//        super.onPause();
//        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
//        //mapView.onPause();
//    }
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        //mapView.onDestroy();
//        mLocationClient.stopLocation();
//        //停止定位
//        mLocationClient.onDestroy();
//        //销毁定位客户端
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState){
//        super.onSaveInstanceState(outState);
//        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
//        //mapView.onSaveInstanceState(outState);
//    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //System.out.println("定位  3");
        if(aMapLocation != null){
            if(aMapLocation.getErrorCode()==0) {
                aMapLocation.getLocationType();//获取当前定位结果来源
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息

//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//              Date date = new Date(aMapLocation.getTime());
                Date date = new Date(System.currentTimeMillis());
                mytime = df.format(date);//定位时间
              //  loc = new locationobject(aMapLocation.getLongitude(),aMapLocation.getLatitude(),mytime);
             //   locationlist.add(loc);
                loc.setLatitude(aMapLocation.getLatitude());
                loc.setLongtitude(aMapLocation.getLongitude());
                loc.setTime(mytime);
                ContentValues values=new ContentValues();
                values.put("UserID", userId);
                values.put("XLocate", loc.getLatitude());
                values.put("YLocate",loc.getLongtitude());
                values.put("Time",loc.getTime());
                sqLiteDatabase.insert("user_location",null,values);
               // System.out.println(loc.getTime());

//                if (locationlist.size() == 10)
//                {
//                    for(int i=0;i<locationlist.size()-1;i++)
//                    {System.out.println("定位2");
//                    System.out.println(locationlist.get(i).getLatitude()+locationlist.get(i).getTime());}
//
//                }

//                aMapLocation.getAddress();
//                aMapLocation.getCountry();
//                aMapLocation.getProvince();
//                aMapLocation.getCity();
//                aMapLocation.getDistrict();
//                aMapLocation.getStreet();
//                aMapLocation.getStreetNum();
//                aMapLocation.getCityCode();
//                aMapLocation.getAdCode();


//                if (isFirstLoc) {
//                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
//                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
//                    mListener.onLocationChanged(aMapLocation);
//
//
//                    StringBuffer buffer = new StringBuffer();
//                    buffer.append(aMapLocation.getCountry() + ""
//                            + aMapLocation.getLatitude() + ""
//                            + aMapLocation.getLongitude() + ""
//                            + aMapLocation.getCity() + ""
//                            + aMapLocation.getDistrict() + ""
//                            + aMapLocation.getStreet() + ""
//                            + aMapLocation.getStreetNum());
//                    Toast.makeText(context, buffer.toString(), Toast.LENGTH_LONG).show();
//                    isFirstLoc = false;
//                }
            }
                else {
                    Log.e("AmapError","location Error,Errcode:"
                    +aMapLocation.getErrorCode()+".errinfo:"
                    +aMapLocation.getErrorInfo());
                    //Toast.makeText(getApplicationContext(),"定位失败",Toast.LENGTH_LONG).show();
                }
            }
        }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    public AMapLocationClient getmLocationClient() {
        return mLocationClient;
    }
}
