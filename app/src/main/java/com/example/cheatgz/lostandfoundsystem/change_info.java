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

public class change_info extends BaseActivity {
    private Button btn1;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private String string1;
    private String string2;
    private String string3;
    private String string4;
    private String string5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_info);
        editText1=(EditText)findViewById(R.id.name);
        editText2=(EditText)findViewById(R.id.company);
        editText3=(EditText)findViewById(R.id.homeAddress);
        editText4=(EditText)findViewById(R.id.phone);
        editText5=(EditText)findViewById(R.id.email);
        btn1=(Button)findViewById(R.id.refer);

        //提交按钮
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1=editText1.getText().toString();
                string2=editText2.getText().toString();
                string3=editText3.getText().toString();
                string4=editText4.getText().toString();
                string5=editText5.getText().toString();
                android.widget.Toast.makeText(change_info.this, "提交成功", android.widget.Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
