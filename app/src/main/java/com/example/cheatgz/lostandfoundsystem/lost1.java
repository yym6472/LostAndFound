package com.example.cheatgz.lostandfoundsystem;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
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
import android.os.Handler;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cheatgz.lostandfoundsystem.application.ThisApplication;
import com.example.cheatgz.lostandfoundsystem.db.LocationInfoHelper;
import com.example.cheatgz.lostandfoundsystem.util.Uri2File;
import com.yymstaygold.lostandfound.client.ClientDelegation;
import com.yymstaygold.lostandfound.client.entity.Found;
import com.yymstaygold.lostandfound.client.entity.Item;
import com.yymstaygold.lostandfound.client.entity.Lost;
import com.yymstaygold.lostandfound.client.entity.UploadImageResult;
import com.yymstaygold.lostandfound.client.util.retrofit.LFSApiService;
import com.yymstaygold.lostandfound.client.util.retrofit.RetrofitServiceManager;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class lost1 extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "lost1";
    private Button btn1;
    private Button btn2;//close
    private ImageView imageView1;
    private EditText editText1;//备注名
    private EditText editText2;//物品详细描述
    private EditText editText3;//悬赏金额
    private String string1;//备注名
    private String string2;//物品详细描述
    private String[] string3={};//我的失物集
    private Found[] founds;
    private String reward;//悬赏金额
    private int itemType;//物品分类
    private Spinner spinner1;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;//匹配结果弹框
    private ListView listView;
    private Handler handler = null;
    private Bitmap bit;

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
        setContentView(R.layout.lost1);
        handler = new Handler();
        imageView1=(ImageView)findViewById(R.id.image);
        editText1=(EditText)findViewById(R.id.nickname);
        editText2=(EditText)findViewById(R.id.describe);
        editText3=(EditText)findViewById(R.id.reward);
        btn1=(Button)findViewById(R.id.refer);
        btn2=(Button)findViewById(R.id.close);
        spinner1=(Spinner)findViewById(R.id.kind_spinner_btn) ;
        linearLayout1=(LinearLayout)findViewById(R.id.main);
        linearLayout2=(LinearLayout)findViewById((R.id.dialog));
        linearLayout2.setVisibility(View.GONE);

        //解决相机闪退
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow();
            }
        });
        //提交按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1=editText1.getText().toString();
                string2=editText2.getText().toString();
                reward=editText3.getText().toString();
                if(string1==null||string1.length()<=0){
                    android.widget.Toast.makeText(lost1.this, "请添加备注名", android.widget.Toast.LENGTH_SHORT).show();
                }else if(string2==null||string2.length()<=0){
                    android.widget.Toast.makeText(lost1.this, "请添加描述", android.widget.Toast.LENGTH_SHORT).show();
                }else if(reward==null||reward.length()<=0){
                    android.widget.Toast.makeText(lost1.this, "悬赏金额数值不能为空", android.widget.Toast.LENGTH_SHORT).show();
                }else{
                    RetrofitServiceManager.getInstance().create(LFSApiService.class)
                            .uploadImage(RequestBody.create(null, Uri2File.getFileByUri(imageUri, lost1.this)))
                            .map(new Func1<UploadImageResult, String>() {
                                @Override
                                public String call(UploadImageResult uploadImageResult) {
                                    if (uploadImageResult.getCode() == 0) {
                                        return uploadImageResult.getResult().getImageUrl();
                                    } else {
                                        return null;
                                    }
                                }
                            })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    Lost lost = new Lost();
                                    ThisApplication application = (ThisApplication) getApplication();
                                    lost.setUserId(application.getUserId());
                                    lost.setLostName(string1);
                                    Item item = new Item();
                                    item.setType(itemType);
                                    item.setImagePath(s);
                                    item.setCustomTypeName(null);
                                    Map<String, String> properties = new HashMap<>();
                                    if (string2 != null && !string2.trim().equals("")) {
                                        String[] propertyKeyValuePairs = string2.split(";");
                                        for (String propertyKeyValuePair : propertyKeyValuePairs) {
                                            String[] keyValue = propertyKeyValuePair.trim().split(":");
                                            assert keyValue.length == 2;
                                            String key = keyValue[0].trim();
                                            String value = keyValue[1].trim();
                                            properties.put(key, value);
                                        }
                                    }
                                    item.setProperties(properties);
                                    lost.setItem(item);

                                    ArrayList<Date> lostPositionInfoTime = new ArrayList<>();
                                    ArrayList<Double> lostPositionInfoPositionX = new ArrayList<>();
                                    ArrayList<Double> lostPositionInfoPositionY = new ArrayList<>();

                                    LocationInfoHelper helper = LocationInfoHelper.getInstance(lost1.this);
                                    SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                                    // TODO: to change
                                    Cursor cursor = sqLiteDatabase.query(
                                            LocationInfoHelper.LocationInfoTable.TABLE_NAME,
                                            new String[]{"time", "positionX", "positionY"},
                                            "userId = ? and time > ? and time < ?",
                                            new String[]{application.getUserId() + "", (new Date().getTime() - 3600000) + "", new Date().getTime() + ""}, null, null, "time asc");

                                    while (cursor.moveToNext()) {
                                        lostPositionInfoTime.add(new Date(cursor.getLong(cursor.getColumnIndex("time"))));
                                        lostPositionInfoPositionX.add(cursor.getDouble(cursor.getColumnIndex("positionX")));
                                        lostPositionInfoPositionY.add(cursor.getDouble(cursor.getColumnIndex("positionY")));
                                    }
                                    lost.setLostPositionInfoTime(lostPositionInfoTime);
                                    lost.setLostPositionInfoPositionX(lostPositionInfoPositionX);
                                    lost.setLostPositionInfoPositionY(lostPositionInfoPositionY);

                                    ArrayList<Integer> matchResults = ClientDelegation.uploadLost(lost);
                                    System.out.println("MATCH_RESULT" + matchResults);
                                    if (matchResults != null && matchResults.size() > 0) {
                                        string3 = new String[matchResults.size()];
                                        founds = new Found[matchResults.size()];
                                        for (int i = 0; i < matchResults.size(); ++i) {
                                            founds[i] = ClientDelegation.downloadFoundInfo(matchResults.get(i));
                                            string3[i] = founds[i].getFoundName();
                                        }
                                    }
                                    handler.post(runnableUi);
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.d(TAG, "onError: " + throwable.toString());
                                }
                            });
                    android.widget.Toast.makeText(lost1.this, "提交成功", android.widget.Toast.LENGTH_SHORT).show();
                    linearLayout1.setBackgroundColor(0xFF969696);
                    linearLayout1.setAlpha((float) 0.1);
                    linearLayout2.setVisibility(View.VISIBLE);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
    protected void setListView(){
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(lost1.this,R.layout.text_view,string3);
        listView=(ListView)findViewById(R.id.matchResult);
        listView.setAdapter(adapter1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent1=new Intent(lost1.this,match_property.class);
                intent1.putExtra("foundId", founds[position].getFoundId());
                intent1.putExtra("phoneNumber", founds[position].getPhoneNumber());
                String description = "";
                Map<String, String> properties = founds[position].getItem().getProperties();
                for (String key : properties.keySet()) {
                    description = description + key + ": " + properties.get(key) + ";";
                }
                description = description.substring(0, description.length() - 1);
                intent1.putExtra("description", description);
                startActivity(intent1);
            }
        });
    }

    Runnable runnableUi=new Runnable(){
        @Override
        public void run() {
            setListView();
        }

    };

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(lost1.this).inflate(R.layout.popupwindow, null);
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
        View rootview = LayoutInflater.from(lost1.this).inflate(R.layout.lost1, null);
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
                        bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
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
                        imageUri = uri;
                        bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
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
                Toast.makeText(lost1.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(lost1.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




}
