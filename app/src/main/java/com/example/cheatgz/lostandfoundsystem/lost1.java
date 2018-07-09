package com.example.cheatgz.lostandfoundsystem;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.yymstaygold.lostandfound.client.ClientDelegation;
import com.yymstaygold.lostandfound.client.entity.Item;
import com.yymstaygold.lostandfound.client.entity.Lost;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class lost1 extends AppCompatActivity {
    private Button btn1;
    private Button btn2;//close
    private ImageView imageView1;
    private EditText editText1;//备注名
    private EditText editText2;//物品详细描述
    private EditText editText3;//悬赏金额
    private String string1;//备注名
    private String string2;//物品详细描述
    private String reward;//悬赏金额
    private String[] string3={"香蕉","橘子","苹果"};//我的失物集
    private int itemType;//物品分类
    private Spinner spinner1;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;//匹配结果弹框
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost1);
        imageView1=(ImageView)findViewById(R.id.image);
        editText1=(EditText)findViewById(R.id.nickname);
        editText2=(EditText)findViewById(R.id.describe);
        editText3=(EditText)findViewById(R.id.reward);
        btn1=(Button)findViewById(R.id.refer);
        btn2=(Button)findViewById(R.id.close);
        spinner1=(Spinner)findViewById(R.id.kind_spinner_btn) ;
        linearLayout1=(LinearLayout)findViewById(R.id.main);
        linearLayout2=(LinearLayout)findViewById((R.id.dialog));
        linearLayout2.setVisibility(View.GONE);
        //提交按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1=editText1.getText().toString();
                string2=editText2.getText().toString();
                reward=editText3.getText().toString();
                if(string1==null||string1.length()<=0){
                    android.widget.Toast.makeText(lost1.this, "请添加备注名", android.widget.Toast.LENGTH_SHORT).show();
                }else if(string2==null||string2.length()<=0){
                    android.widget.Toast.makeText(lost1.this, "请添加描述", android.widget.Toast.LENGTH_SHORT).show();
                }else if(reward==null||reward.length()<=0){
                    android.widget.Toast.makeText(lost1.this, "悬赏金额数值不能为空", android.widget.Toast.LENGTH_SHORT).show();
                }else{
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
                            String[] propertyKeyValuePairs = string2.split(";");
                            for (String propertyKeyValuePair : propertyKeyValuePairs) {
                                String[] keyValue = propertyKeyValuePair.trim().split(":");
                                assert keyValue.length == 2;
                                String key = keyValue[0];
                                String value = keyValue[1];
                                properties.put(key, value);
                            }
                            item.setProperties(properties);
                            lost.setItem(item);

                            ArrayList<Date> lostPositionInfoTime = new ArrayList<>();
                            ArrayList<Double> lostPositionInfoPositionX = new ArrayList<>();
                            ArrayList<Double> lostPositionInfoPositionY = new ArrayList<>();

                            Database database = new Database(lost1.this , "UserLocation.db", null, 1);
                            SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
                            // TODO: to change
                            Cursor cursor = sqLiteDatabase.query(
                                    "user_location",
                                    new String[]{"Time", "XLocate", "YLocate"},
                                    "userId = ? and Time < ? and Time > ?",
                                    new String[]{application.getUserId()+"", "?", "?"}, null, null, "Time asc");

                            while (cursor.moveToNext()) {
                                // TODO: to change
                                lostPositionInfoTime.add(new Date(cursor.getString(cursor.getColumnIndex("Time"))));
                                lostPositionInfoPositionX.add(cursor.getDouble(cursor.getColumnIndex("XLocate")));
                                lostPositionInfoPositionY.add(cursor.getDouble(cursor.getColumnIndex("YLocate")));
                            }
                            lost.setLostPositionInfoTime(lostPositionInfoTime);
                            lost.setLostPositionInfoPositionX(lostPositionInfoPositionX);
                            lost.setLostPositionInfoPositionY(lostPositionInfoPositionY);

                            ArrayList<Integer> matchResults = ClientDelegation.uploadLost(lost);
                            // TODO: handle matched founds.
                        }
                    }).start();
                    android.widget.Toast.makeText(lost1.this, "提交成功", android.widget.Toast.LENGTH_SHORT).show();
                    setListView();
                    linearLayout1.setBackgroundColor(0xFF969696);
                    linearLayout1.setAlpha((float) 0.1);
                    linearLayout2.setVisibility(View.VISIBLE);
                }
            }
        });
        imageView1.setImageResource(R.mipmap.icon);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        });
    }
    protected void setListView(){
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(lost1.this,R.layout.text_view,string3);
        listView=(ListView)findViewById(R.id.matchResult);
        listView.setAdapter(adapter1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent1=new Intent(lost1.this,match_property.class);
                startActivity(intent1);
            }
        });
    }
    }
