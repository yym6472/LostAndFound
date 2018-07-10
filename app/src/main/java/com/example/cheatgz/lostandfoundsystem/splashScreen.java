package com.example.cheatgz.lostandfoundsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class splashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            Intent intent1 = new Intent(splashScreen.this, sign_in.class);
            startActivity(intent1);
            finish();
    }
}
