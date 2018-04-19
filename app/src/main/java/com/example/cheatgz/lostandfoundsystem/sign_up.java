package com.example.cheatgz.lostandfoundsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yymstaygold.lostandfound.client.ClientDelegation;

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
                }else if(string2.length()<= 5||string2.length()>18){
                    android.widget.Toast.makeText(sign_up.this, "密码不少于6位不大于18位", android.widget.Toast.LENGTH_SHORT).show();
                }else if(!string2.equals(string3)){
                    android.widget.Toast.makeText(sign_up.this, "两次密码不一致", android.widget.Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {

                        private Handler handler = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                if (msg.arg1 == 1) {
                                    android.widget.Toast.makeText(sign_up.this, "注册成功", android.widget.Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    android.widget.Toast.makeText(sign_up.this, "注册失败，账号已存在", android.widget.Toast.LENGTH_SHORT).show();
                                }
                            }
                        };

                        @Override
                        public void run() {
                            boolean result = ClientDelegation.register(string1, string2);
                            Message message = new Message();
                            message.arg1 = result ? 1 : 0;
                            handler.sendMessage(message);
                        }
                    }).start();
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
}
