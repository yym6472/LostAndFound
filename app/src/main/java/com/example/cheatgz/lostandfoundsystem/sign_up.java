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

public class sign_up extends AppCompatActivity {
    private EditText editText1;//邮箱
    private EditText editText2;
    private EditText editText3;
    private Button btn1;
    private Button btn2;
    private String string1;
    private String string2;
    private String string3;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        buttonEvent();//点击按钮事件
    }
    protected void buttonEvent(){
        btn1=(Button)findViewById(R.id.sign_up);
        btn2=(Button)findViewById(R.id.backSignIn);

        //点击注册按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(sign_up.this,sign_in.class);
                startActivity(intent1);
            }
        });

        //点击返回登录按钮
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(sign_up.this,sign_in.class);
                startActivity(intent2);
            }
        });
    }
    protected void editTextEvent(){
        editText1=(EditText)findViewById(R.id.email);
        editText2=(EditText)findViewById(R.id.pd);
        editText3=(EditText)findViewById(R.id.repeatPd);
        string1=editText1.getText().toString();//邮箱
        string2=editText2.getText().toString();//密码
        string3=editText3.getText().toString();//重复密码
    }
}
