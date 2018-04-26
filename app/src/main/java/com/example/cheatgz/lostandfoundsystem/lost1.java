package com.example.cheatgz.lostandfoundsystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.example.cheatgz.lostandfoundsystem.db.LocationInfoHelper;
import com.yymstaygold.lostandfound.client.ClientDelegation;
import com.yymstaygold.lostandfound.client.entity.Item;
import com.yymstaygold.lostandfound.client.entity.Lost;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class lost1 extends AppCompatActivity {
    private Button btn1;
    private ImageView imageView1;
    private EditText editText1;//备注名
    private EditText editText2;//物品详细描述
    private String string1;//备注名
    private String string2;//物品详细描述
    private int itemType;//物品分类
    private Spinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost1);
        imageView1=(ImageView)findViewById(R.id.image);
        editText1=(EditText)findViewById(R.id.nickname);
        editText2=(EditText)findViewById(R.id.describe);
        btn1=(Button)findViewById(R.id.refer);
        spinner1=(Spinner)findViewById(R.id.kind_spinner_btn) ;
        //下一页按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1=editText1.getText().toString();
                string2=editText2.getText().toString();
                if(string1==null||string1.length()<=0){
                    android.widget.Toast.makeText(lost1.this, "请添加备注名", android.widget.Toast.LENGTH_SHORT).show();
                }else if(string2==null||string2.length()<=0){
                    android.widget.Toast.makeText(lost1.this, "请添加描述", android.widget.Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Lost lost = new Lost();
                            ThisApplication application = (ThisApplication) getApplication();
                            lost.setUserId(application.getUserId());
                            lost.setLostName(string1);
                            Item item = new Item();
                            item.setType(itemType);
                            item.setImagePath("");
                            item.setCustomTypeName(null);
                            Map<String, String> properties = new HashMap<>();
                            if (string2 != null && !string2.trim().equals("")) {
                                String[] propertyKeyValuePairs = string2.split(";");
                                for (String propertyKeyValuePair : propertyKeyValuePairs) {
                                    String[] keyValue = propertyKeyValuePair.trim().split(":");
                                    assert keyValue.length == 2;
                                    String key = keyValue[0].trim();
                                    String value = keyValue[1].trim();
                                    properties.put(key, value);
                                }
                            }
                            item.setProperties(properties);
                            lost.setItem(item);

                            ArrayList<Date> lostPositionInfoTime = new ArrayList<>();
                            ArrayList<Double> lostPositionInfoPositionX = new ArrayList<>();
                            ArrayList<Double> lostPositionInfoPositionY = new ArrayList<>();

                            LocationInfoHelper helper = LocationInfoHelper.getInstance(lost1.this);
                            SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                            // TODO: to change
                            Cursor cursor = sqLiteDatabase.query(
                                    LocationInfoHelper.LocationInfoTable.TABLE_NAME,
                                    new String[]{"time", "positionX", "positionY"},
                                    "userId = ? and time > ? and time < ?",
                                    new String[]{application.getUserId() + "", (new Date().getTime() - 3600000) + "",new Date().getTime() + ""}, null, null, "time asc");

                            while (cursor.moveToNext()) {
                                lostPositionInfoTime.add(new Date(cursor.getLong(cursor.getColumnIndex("time"))));
                                lostPositionInfoPositionX.add(cursor.getDouble(cursor.getColumnIndex("positionX")));
                                lostPositionInfoPositionY.add(cursor.getDouble(cursor.getColumnIndex("positionY")));
                            }
                            lost.setLostPositionInfoTime(lostPositionInfoTime);
                            lost.setLostPositionInfoPositionX(lostPositionInfoPositionX);
                            lost.setLostPositionInfoPositionY(lostPositionInfoPositionY);

                            ArrayList<Integer> matchResults = ClientDelegation.uploadLost(lost);
                            System.out.println(matchResults);
                            // TODO: handle matched founds.
                        }
                    }).start();
                    android.widget.Toast.makeText(lost1.this, "提交成功", android.widget.Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        imageView1.setImageResource(R.mipmap.icon);

        final String[] propertykind=getResources().getStringArray(R.array.kinds);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, propertykind);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                itemType = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });;
    }
}
