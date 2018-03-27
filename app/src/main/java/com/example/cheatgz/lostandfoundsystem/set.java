package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

/**
 * Created by CheatGZ on 2018/3/26.
 */

public class set extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);

        Button btn1=(Button)findViewById(R.id.personInfo);
        Button btn2=(Button)findViewById(R.id.wallet);
        Button btn3=(Button)findViewById(R.id.property);
        Button btn4=(Button)findViewById(R.id.authentication);
        Button btn5=(Button)findViewById(R.id.logOut);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(set.this,info.class);
                startActivity(intent1);
            }
        });

        btn2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(set.this,wallet.class);
                startActivity(intent2);
            }
        }));

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(set.this,property.class);
                startActivity(intent3);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(set.this,authentication.class);
                startActivity(intent4);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5=new Intent(set.this,sign_in.class);
                startActivity(intent5);
            }
        });
    }
}
