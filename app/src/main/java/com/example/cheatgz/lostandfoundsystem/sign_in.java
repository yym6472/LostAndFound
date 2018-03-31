package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class sign_in extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private Button btn1;
    private Button btn2;
    private String string1;//输入的账号
    private String string2;//输入的密码
    private String string3;//从数据库提出的账号
    private String string4;//从数据库中提出的对应账号的密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        buttonEvent();//点击按钮事件
    }

    protected void editTextEvent(){
        editText1=(EditText)findViewById(R.id.email);
        editText2=(EditText)findViewById(R.id.pd);
        string1=editText1.getText().toString();//账号
        string2=editText2.getText().toString();//密码
    }
    protected void buttonEvent(){
        btn1=(Button)findViewById(R.id.sign_in);
        btn2=(Button)findViewById(R.id.sign_up);

        //点击登录按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(sign_in.this,main.class);
                startActivity(intent1);
            }
        });

        //点击注册按钮
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(sign_in.this,sign_up.class);
                startActivity(intent2);
            }
        });

    }
}
