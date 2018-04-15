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
    private EditText editText1;//手机号
    private EditText editText2;//密码
    private EditText editText3;//重复密码
    private Button btn1;
    private Button btn2;
    private String string1;//手机号
    private String string2;//密码
    private String string3;//重复密码

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        editText1=(EditText)findViewById(R.id.email);
        editText2=(EditText)findViewById(R.id.pd);
        editText3=(EditText)findViewById(R.id.repeatPd);
        btn1=(Button)findViewById(R.id.sign_up);
        btn2=(Button)findViewById(R.id.backSignIn);

        //点击注册按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1=editText1.getText().toString();//邮箱
                string2=editText2.getText().toString();//密码
                string3=editText3.getText().toString();//重复密码
                if(string1==null||string1.length()<=0){
                    android.widget.Toast.makeText(sign_up.this, "账号不可为空", android.widget.Toast.LENGTH_SHORT).show();
                }else if(phoneExisted(string1)){
                    android.widget.Toast.makeText(sign_up.this, "账号已存在", android.widget.Toast.LENGTH_SHORT).show();
                }else if(string2.length()<= 5||string2.length()>18){
                    android.widget.Toast.makeText(sign_up.this, "密码不少于6位不大于18位", android.widget.Toast.LENGTH_SHORT).show();
                }else if(!string2.equals(string3)){
                    android.widget.Toast.makeText(sign_up.this, "两次密码不一致", android.widget.Toast.LENGTH_SHORT).show();
                }else
                    {
                        android.widget.Toast.makeText(sign_up.this, "注册成功", android.widget.Toast.LENGTH_SHORT).show();
                        finish();
                    }
            }
        });

        //点击返回登录按钮
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    protected Boolean phoneExisted(String s1){
        return true;
    }
}
