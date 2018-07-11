package com.example.cheatgz.lostandfoundsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class match_property extends BaseActivity {
    private ImageView imageView1;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private String string1="红色的钱包，皮革材料";//从数据库提出的物品描述
    private String string2="王先生";//从数据库提出的捡到失物的人姓名
    private String string3="112315532135";//从数据库提出的捡到失物的人手机号或者邮箱
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_property);
        textView1=(TextView)findViewById(R.id.describe);
        textView2=(TextView)findViewById(R.id.name);
        textView3=(TextView)findViewById(R.id.phone);
        imageView1=(ImageView)findViewById(R.id.image);

        textView1.setText(string1);
        textView2.setText(string2);
        textView3.setText(string3);
        imageView1.setImageResource(R.mipmap.wallet);
    }
}
