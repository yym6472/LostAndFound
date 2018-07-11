package com.example.cheatgz.lostandfoundsystem;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.io.File;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class home_page extends AppCompatActivity implements View.OnClickListener{
    private Button btn1;//个人信息
    private Button btn2;//我的钱包
    private Button btn3;//我的物品
    private Button btn4;//实名认证
    private Button btn5;//退出登录
    private TextView textView1;//姓名
    private TextView textView2;//手机号
    private ImageView imageView1;//头像
    private LinearLayout linearLayout1;
    private String string1="李先森";//姓名
    private String string2="12345678911";//手机号

    private static final int CROP_PHOTO = 2;
    private static final int REQUEST_CODE_PICK_IMAGE=3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private File output;
    private PopupWindow popupWindow;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        linearLayout1=(LinearLayout)findViewById(R.id.linearlayout);
        buttonEvent();//点击按钮事件
        setImageView();
        setTextView();

        //解决相机闪退
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
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
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow();
            }
        });
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

        ed.putBoolean("state",false);
        ed.commit();
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(home_page.this).inflate(R.layout.popupwindow, null);
        popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(contentView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setTouchable(true);
        Button takephoto = (Button)contentView.findViewById(R.id.btn_take_photo);
        Button pickphoto = (Button) contentView.findViewById(R.id.btn_pick_photo);
        Button cancel = (Button) contentView.findViewById(R.id.btn_cancel);
        takephoto.setOnClickListener(this);
        pickphoto.setOnClickListener(this);
        cancel.setOnClickListener(this);

        //显示popupwidnow
        View rootview = LayoutInflater.from(home_page.this).inflate(R.layout.home_page, null);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_take_photo:{
                takePhone(v);
                Toast.makeText(this,"打开相机",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
            break;
            case R.id.btn_pick_photo:{
                choosePhone(v);
                Toast.makeText(this,"从相册选择",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
            break;
            case R.id.btn_cancel:{
                popupWindow.dismiss();
            }
            break;
        }
    }

    public void takePhone(View view){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);

        }else {
            takePhoto();
        }

    }

    public void choosePhone(View view){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);

        }else {
            choosePhoto();
        }
    }

    /**
     * 拍照
     */
    void takePhoto(){
        /**
         * 最后一个参数是文件夹的名称，可以随便起
         */
        File file=new File(Environment.getExternalStorageDirectory(),"拍照");
        if(!file.exists()){
            file.mkdir();
        }
        /**
         * 这里将时间作为不同照片的名称
         */
        output=new File(file,System.currentTimeMillis()+".jpg");

        /**
         * 如果该文件夹已经存在，则删除它，否则创建一个
         */
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
         */
        imageUri = Uri.fromFile(output);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);

    }

    /**
     * 从相册选取图片
     */
    void choosePhoto(){
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            /**
             * 拍照的请求标志
             */
            case CROP_PHOTO:
                if (res==RESULT_OK) {
                    try {
                        /**
                         * 该uri就是照片文件夹对应的uri
                         */
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView1.setImageBitmap(bit);
                    } catch (Exception e) {
                        Toast.makeText(this,"程序崩溃",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Log.i("tag", "失败");
                }

                break;
            /**
             * 从相册中选取图片的请求标志
             */

            case REQUEST_CODE_PICK_IMAGE:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri是上一个Activity返回的
                         */
                        Uri uri = data.getData();
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        imageView1.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("tag",e.getMessage());
                        Toast.makeText(this,"程序崩溃",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Log.i("liang", "失败");
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                takePhoto();
            } else
            {
                // Permission Denied
                Toast.makeText(home_page.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                choosePhoto();
            } else
            {
                // Permission Denied
                Toast.makeText(home_page.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
