package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class found2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found2);

        MapView mapView=(MapView)findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        AMap aMap=mapView.getMap();

        Button btn1=(Button)findViewById(R.id.refer);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(found2.this,main.class);
                startActivity(intent1);
            }
        });
    }
}
