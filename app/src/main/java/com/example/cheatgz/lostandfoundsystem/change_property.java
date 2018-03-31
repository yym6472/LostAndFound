package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class change_property extends AppCompatActivity {
    private ImageView imageView1;
    private TextView textView1;
    private Button btn1;
    private String string1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_property);
        setTextView();
        buttonEvent();
        setImageView();
    }
    protected void setTextView(){
        textView1=(TextView)findViewById(R.id.describe);
        string1=textView1.getText().toString();
    }
    protected void buttonEvent(){
        btn1=(Button)findViewById(R.id.refer);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(change_property.this,property.class);
                startActivity(intent1);
            }
        });
    }
    protected void setImageView(){
        imageView1=(ImageView)findViewById(R.id.image);
        imageView1.setImageResource(R.mipmap.wallet);
    }
}
