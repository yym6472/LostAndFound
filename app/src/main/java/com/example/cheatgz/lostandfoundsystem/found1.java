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
import com.yymstaygold.lostandfound.client.entity.Found;
import com.yymstaygold.lostandfound.client.entity.Item;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class found1 extends AppCompatActivity {
    private Button btn1;
    private ImageView imageView1;
    private EditText editText1;
    private EditText editText2;
    private String string1;//备注名
    private String string2;// 描述
    private int itemType;//分类
    private Spinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found1);
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
                    android.widget.Toast.makeText(found1.this, "请添加备注名", android.widget.Toast.LENGTH_SHORT).show();
                }else if(string2==null||string2.length()<=0){
                    android.widget.Toast.makeText(found1.this, "请添加描述", android.widget.Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Found found = new Found();
                            Item item = new Item();
                            item.setCustomTypeName(null);
                            // TODO: implementation of image taking or selection
                            item.setImagePath("");
                            item.setType(itemType);
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
                            found.setItem(item);
                            found.setFoundName(string1);
                            ThisApplication application = (ThisApplication) getApplication();
                            found.setUserId(application.getUserId());
                            LocationInfoHelper helper = LocationInfoHelper.getInstance(found1.this);
                            SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                            Cursor cursor = sqLiteDatabase.query(
                                    LocationInfoHelper.LocationInfoTable.TABLE_NAME,
                                    new String[]{"positionX, positionY"},
                                    "userId = ?",
                                    new String[]{"" + application.getUserId()},
                                    null,
                                    null,
                                    "time desc");
                            if (cursor.moveToNext()) {
                                found.setFoundPositionX(cursor.getDouble(cursor.getColumnIndex("positionX")));
                                found.setFoundPositionY(cursor.getDouble(cursor.getColumnIndex("positionY")));
                            }
                            cursor.close();
                            found.setFoundTime(new Date(System.currentTimeMillis()));

                            ClientDelegation.uploadFound(found);
                        }
                    }).start();
                    android.widget.Toast.makeText(found1.this, "提交成功", android.widget.Toast.LENGTH_SHORT).show();
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
         });
    }
}
