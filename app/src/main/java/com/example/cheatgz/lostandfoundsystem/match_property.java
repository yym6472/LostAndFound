package com.example.cheatgz.lostandfoundsystem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class match_property extends BaseActivity {

    private static final int REQUEST_CODE_ASK_SINGLE_PERMISSION=1;
    private ImageView imageView1;//物品图片
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private ImageView imageView2;//点击打电话
    private String string1;//从数据库提出的物品描述
    private String string2;//从数据库提出的捡到失物的人姓名
    private String string3;//从数据库提出的捡到失物的人手机号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        string1 = getIntent().getStringExtra("description");
        string3 = getIntent().getStringExtra("phoneNumber");

        setContentView(R.layout.match_property);
        textView1=(TextView)findViewById(R.id.describe);
        textView2=(TextView)findViewById(R.id.name);
        textView3=(TextView)findViewById(R.id.phone);
        imageView1=(ImageView)findViewById(R.id.image);
        imageView2=(ImageView)findViewById(R.id.call);
        string1="红色的钱包，皮革材料";
        string2="王先生";
        string3="15600737700";
        textView1.setText(string1);
        textView2.setText(string2);
        textView3.setText(string3);
        imageView1.setImageResource(R.mipmap.icon);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:"+string3);
                Intent intent = new Intent(Intent.ACTION_CALL, uri);
                //读取联系人的权限
                String permission = Manifest.permission.CALL_PHONE;
                //检查权限是否已授权
                int hasPermission = checkSelfPermission(permission);

                //如果没有授权
                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    //请求权限，此方法会弹出权限请求对话框，让用户授权，并回调 onRequestPermissionsResult 来告知授权结果
                    ActivityCompat.requestPermissions(match_property.this,new String[]{permission}, REQUEST_CODE_ASK_SINGLE_PERMISSION);
                }else {//已经授权过
                    startActivity(intent);
                }

            }
        });
    }
}
