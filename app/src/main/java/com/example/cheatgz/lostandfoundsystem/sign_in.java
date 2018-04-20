package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.yymstaygold.lostandfound.client.ClientDelegation;

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
    private CheckBox checkBox1;
    private LocateService startService;

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
                string1 = editText1.getText().toString();//手机号
                string2 = editText2.getText().toString();//密码
                if (string1 == null || string1.length() <= 0) {
                    android.widget.Toast.makeText(sign_in.this, "账号不可为空", android.widget.Toast.LENGTH_SHORT).show();
                } else if (string2 == null || string2.length() <= 0) {
                    android.widget.Toast.makeText(sign_in.this, "密码不能为空", android.widget.Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        private Handler handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                if (msg.arg1 == -1) {
                                    android.widget.Toast.makeText(sign_in.this, "账号或密码错误", android.widget.Toast.LENGTH_SHORT).show();
                                } else {

                                    Intent startIntent=new Intent(sign_in.this,LocateService.class);
                                    stopService(startIntent);
                                    startService(startIntent);

                                    ThisApplication application = (ThisApplication) getApplication();
                                    application.setUserId(msg.arg1);

                                    Intent intent1 = new Intent(sign_in.this, main.class);
                                    startActivity(intent1);
                                    android.widget.Toast.makeText(sign_in.this, "登录成功，用户Id为" + msg.arg1, android.widget.Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        };
                        @Override
                        public void run() {
                            int userId = ClientDelegation.checkPassword(string1, string2);
                            Message msg = new Message();
                            msg.arg1 = userId;
                            handler.sendMessage(msg);
                        }
                    }).start();
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
