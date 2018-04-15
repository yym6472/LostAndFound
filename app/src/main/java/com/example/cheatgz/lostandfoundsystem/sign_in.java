package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class sign_in extends AppCompatActivity {
    private EditText editText1;//手机号
    private EditText editText2;//密码
    private Button btn1;
    private Button btn2;
    private String string1;//输入的手机号
    private String string2;//输入的密码
    private String string3;//从数据库提出的手机号
    private String string4="000";//从数据库中提出的对应账号的密码
    private CheckBox checkBox1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        editText1=(EditText)findViewById(R.id.phone);
        editText2=(EditText)findViewById(R.id.pd);
        checkBox1=(CheckBox)findViewById(R.id.rememberState);
        btn1=(Button)findViewById(R.id.sign_in);
        btn2=(Button)findViewById(R.id.sign_up);

        //点击登录按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1=editText1.getText().toString();//手机号
                string2=editText2.getText().toString();//密码
                if (string1 == null || string1.length() <= 0) {
                    android.widget.Toast.makeText(sign_in.this, "账号不可为空", android.widget.Toast.LENGTH_SHORT).show();
                } else if (!string4.equals(string2)) {
                    android.widget.Toast.makeText(sign_in.this, "账号或密码错误", android.widget.Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = new Intent(sign_in.this, main.class);
                    startActivity(intent1);
                    android.widget.Toast.makeText(sign_in.this, "登录成功", android.widget.Toast.LENGTH_SHORT).show();
                    finish();
                }
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
        writeAccount();
    }
    protected void writeAccount(){
        SharedPreferences sp1=getSharedPreferences("identification",MODE_PRIVATE);
        SharedPreferences.Editor ed=sp1.edit();

         if(checkBox1.isChecked()){
             ed.putString("phone",string1);
             ed.putBoolean("state",true);
             ed.commit();
         }
    }
    /* 判断是否登录状态 */
    protected Boolean isLogIn(){
        Boolean state=false;
        SharedPreferences sp=getSharedPreferences("identification",MODE_PRIVATE);
        state=sp.getBoolean("state",false);
        return state;
    }

}
