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

public class authentication extends BaseActivity{
    private Button btn1;
    private EditText editText1;
    private EditText editText2;
    private String string1;
    private String string2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);
        btn1 = (Button) findViewById(R.id.refer);
        editText1=(EditText)findViewById(R.id.name);
        editText2=(EditText)findViewById(R.id.identify);

        /* 点击提交认证按钮 */
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1=editText1.getText().toString();
                string2=editText2.getText().toString();
                android.widget.Toast.makeText(authentication.this, "认证成功", android.widget.Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
