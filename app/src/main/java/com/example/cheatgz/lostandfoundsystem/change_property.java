package com.example.cheatgz.lostandfoundsystem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class change_property extends BaseActivity {
    private ImageView imageView1;
    private EditText editText1;
    private Button btn1;
    private String string1;//描述
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_property);
        btn1=(Button)findViewById(R.id.refer);
        editText1=(EditText)findViewById(R.id.describe);
        imageView1=(ImageView)findViewById(R.id.image);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string1=editText1.getText().toString();
                if(string1==null||string1.length()<=0){
                    android.widget.Toast.makeText(change_property.this, "物品描述不能为空", android.widget.Toast.LENGTH_SHORT).show();
                }else{
                    android.widget.Toast.makeText(change_property.this, "修改成功", android.widget.Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        imageView1.setImageResource(R.mipmap.icon);
    }
}
