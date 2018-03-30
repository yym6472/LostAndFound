package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.MyLocationStyle;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class main extends AppCompatActivity {
    private MapView mMapView;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initMap(savedInstanceState);//加载地图

        buttonEvent();
    }

    protected void buttonEvent(){

        btn1=(Button)findViewById(R.id.set);
        btn2=(Button)findViewById(R.id.message);
        btn3=(Button)findViewById(R.id.lost);
        btn4=(Button)findViewById(R.id.found);

        //点击设置按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(main.this,home_page.class);
                startActivity(intent1);
            }
        });

        //点击匹配信息按钮
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(main.this,message.class);
                startActivity(intent2);
            }
        });

        //点击捡到物品按钮
        btn3.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(main.this,lost1.class);
                startActivity(intent3);
            }
        }));

        //点击丢失物品按钮
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(main.this,found1.class);
                startActivity(intent4);
            }
        });
    }
    protected void initMap(Bundle savedInstanceState){
        AMap aMap = null;
        UiSettings mUiSettings;//定义一个UiSettings对象

        mMapView=(MapView)findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象

        //实现定位蓝点
        MyLocationStyle myLocationStyle;
        myLocationStyle=new MyLocationStyle();
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationEnabled(true);//设置定位蓝点是否可见

        //地图初始显示设置
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));//地图初始显示比例
        myLocationStyle.showMyLocation(true);//地图初始位置

        //控件设置
        mUiSettings.setCompassEnabled(true);//指南针
        mUiSettings.setScaleControlsEnabled(true);//比例尺
        mUiSettings.setMyLocationButtonEnabled(true);//定位按钮
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}

