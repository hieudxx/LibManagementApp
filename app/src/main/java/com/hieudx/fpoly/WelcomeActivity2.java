package com.hieudx.fpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WelcomeActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(WelcomeActivity2.this, MainActivity.class));
//                finish();
//
//            }
//        }, 3000);


    }
}