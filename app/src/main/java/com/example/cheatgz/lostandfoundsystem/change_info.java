package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class change_info extends AppCompatActivity {
    private Button btn1;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private String string1;
    private String string2;
    private String string3;
    private String string4;
    private String string5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_info);

        buttonEvent();//点击按钮事件
    }
    protected void buttonEvent(){
        btn1=(Button)findViewById(R.id.refer);
        //提交按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(change_info.this,home_page.class);
                startActivity(intent1);
            }
        });
    }

    protected void editTextEvent(){
        editText1=(EditText)findViewById(R.id.name);
        editText2=(EditText)findViewById(R.id.company);
        editText3=(EditText)findViewById(R.id.homeAddress);
        editText4=(EditText)findViewById(R.id.phone);
        editText5=(EditText)findViewById(R.id.email);
        string1=editText1.getText().toString();
        string2=editText2.getText().toString();
        string3=editText3.getText().toString();
        string4=editText4.getText().toString();
        string5=editText5.getText().toString();
    }
}
