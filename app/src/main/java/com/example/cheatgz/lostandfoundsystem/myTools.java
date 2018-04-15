package com.example.cheatgz.lostandfoundsystem;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class myTools  extends AppCompatActivity{
    public Integer readAccount(){
        int id_phone;
        SharedPreferences sp=getSharedPreferences("identification",MODE_PRIVATE);

        id_phone=sp.getInt("phone",1);
        return id_phone;
    }
}
