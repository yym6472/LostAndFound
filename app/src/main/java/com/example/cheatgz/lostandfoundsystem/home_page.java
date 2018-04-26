package com.example.cheatgz.lostandfoundsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class home_page extends AppCompatActivity {
    private Button btn1;//个人信息
    private Button btn2;//我的钱包
    private Button btn3;//我的物品
    private Button btn4;//实名认证
    private Button btn5;//退出登录
    private TextView textView1;//姓名
    private TextView textView2;//手机号
    private ImageView imageView1;
    private String string1="李先森";//姓名
    private String string2="12345678911";//手机号


    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        buttonEvent();//点击按钮事件
        setImageView();
        setTextView();
    }
    protected void buttonEvent(){
        btn1=(Button)findViewById(R.id.personInfo);
        btn2=(Button)findViewById(R.id.wallet);
        btn3=(Button)findViewById(R.id.property);
        btn4=(Button)findViewById(R.id.authentication);
        btn5=(Button)findViewById(R.id.logOut);

        //点击个人信息按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(home_page.this,info.class);
                startActivity(intent1);
            }
        });

        //点击我的钱包按钮
        btn2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(home_page.this,wallet.class);
                startActivity(intent2);
            }
        }));

        //点击我的物品按钮
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(home_page.this,property.class);
                startActivity(intent3);
            }
        });

        //点击个人认证按钮
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(home_page.this,authentication.class);
                startActivity(intent4);
            }
        });

        //点击退出登录按钮
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });
    }
    protected void setTextView(){
        textView1=(TextView)findViewById(R.id.name);
        textView2=(TextView)findViewById(R.id.phone);

        textView1.setText(string1);
        textView2.setText(string2);
    }
    protected void setImageView(){

        imageView1=(ImageView)findViewById(R.id.image);
        imageView1.setImageResource(R.mipmap.icon);
    }
    protected void showDialog(View view){
        //Toast.makeText(this,"clickme",Toast.LENGTH_LONG).show();
    AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(this);
    alertdialogbuilder.setMessage("您确认要退出登录");
    alertdialogbuilder.setPositiveButton("确定", click1);
    alertdialogbuilder.setNegativeButton("取消", click2);
    AlertDialog alertDialog1=alertdialogbuilder.create();
    alertDialog1.show();
    }
    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            writeNull();
            Intent intent=new Intent(home_page.this,sign_in.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    };
    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    };
    public void writeNull(){
        SharedPreferences sp1=getSharedPreferences("identification",MODE_PRIVATE);
        SharedPreferences.Editor ed=sp1.edit();

        ed.putString("phone",null);
        ed.putString("pd",null);
        ed.commit();
    }
}
