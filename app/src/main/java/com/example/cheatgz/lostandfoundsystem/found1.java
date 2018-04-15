package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

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
    private String string3;//分类
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
                    android.widget.Toast.makeText(found1.this, "提交成功", android.widget.Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        imageView1.setImageResource(R.mipmap.wallet);

        final String[] propertykind=getResources().getStringArray(R.array.kinds);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, propertykind);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

         spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                 string3=propertykind[position];
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });
    }
}
