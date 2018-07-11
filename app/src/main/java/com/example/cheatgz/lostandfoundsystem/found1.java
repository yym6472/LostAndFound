package com.example.cheatgz.lostandfoundsystem;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yymstaygold.lostandfound.client.ClientDelegation;
import com.yymstaygold.lostandfound.client.entity.Found;
import com.yymstaygold.lostandfound.client.entity.Item;

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class found1 extends BaseActivity implements View.OnClickListener{
    private Button btn1;
    private ImageView imageView1;
    private EditText editText1;
    private EditText editText2;
    private String string1;//备注名
    private String string2;// 描述
    private int itemType;//分类
    private Spinner spinner1;
    private LinearLayout linearLayout1;

    private static final int CROP_PHOTO = 2;
    private static final int REQUEST_CODE_PICK_IMAGE=3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private File output;
    private PopupWindow popupWindow;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.found1);
        imageView1=(ImageView)findViewById(R.id.image);
        editText1=(EditText)findViewById(R.id.nickname);
        editText2=(EditText)findViewById(R.id.describe);
        btn1=(Button)findViewById(R.id.refer);
        spinner1=(Spinner)findViewById(R.id.kind_spinner_btn) ;
        linearLayout1=(LinearLayout)findViewById(R.id.pop_layout);

        //解决相机闪退
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        //提交按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1=editText1.getText().toString();
                string2=editText2.getText().toString();
                if(string1==null||string1.length()<=0){
                    android.widget.Toast.makeText(found1.this, "请添加备注名", android.widget.Toast.LENGTH_SHORT).show();
                }else if(string2==null||string2.length()<=0){
                    android.widget.Toast.makeText(found1.this, "请添加描述", android.widget.Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Found found = new Found();
                            Item item = new Item();
                            item.setCustomTypeName(null);
                            // TODO: implementation of image taking or selection
                            item.setImagePath("");
                            item.setType(itemType);
                            Map<String, String> properties = new HashMap<>();
                            String[] propertyKeyValuePairs = string2.split(";");
                            for (String propertyKeyValuePair : propertyKeyValuePairs) {
                                String[] keyValue = propertyKeyValuePair.trim().split(":");
                                assert keyValue.length == 2;
                                String key = keyValue[0];
                                String value = keyValue[1];
                                properties.put(key, value);
                            }
                            item.setProperties(properties);
                            found.setItem(item);
                            found.setFoundName(string1);
                            ThisApplication application = (ThisApplication) getApplication();
                            found.setUserId(application.getUserId());
                            Database database=new Database(found1.this, "UserLocation.db", null, 1);
                            SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
                            Cursor cursor = sqLiteDatabase.query("user_location",
                                    new String[]{"XLocate, YLocate"},
                                    "userId = ?",
                                    new String[]{"" + application.getUserId()},
                                    null,
                                    null,
                                    "Time desc");
                            if (cursor.moveToNext()) {
                                found.setFoundPositionX(cursor.getDouble(cursor.getColumnIndex("XLocate")));
                                found.setFoundPositionY(cursor.getDouble(cursor.getColumnIndex("YLocate")));
                            }
                            cursor.close();
                            found.setFoundTime(new Date(System.currentTimeMillis()));

                            ClientDelegation.uploadFound(found);
                        }
                    }).start();
                    android.widget.Toast.makeText(found1.this, "提交成功", android.widget.Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
       imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow();
            }
        });

        final String[] propertykind=getResources().getStringArray(R.array.kinds);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, propertykind);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

         spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                 itemType = position + 1;
             }

             @Override
             public void onNothingSelected(AdapterView<?> adapterView) {

             }
         });
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(found1.this).inflate(R.layout.popupwindow, null);
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
        View rootview = LayoutInflater.from(found1 .this).inflate(R.layout.found1 , null);
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
                Toast.makeText(found1.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(found1.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
