package com.hieudx.fpoly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

//        nếu sử dụng Imageview như bth thì gif sẽ k động đậy,
//        nên ta sử import thêm thư viện glide vào trong Gradle Scripts - > build.gradle -> lấy code trong trang https://github.com/bumptech/glide dán vào dependencies
        ImageView imgBook = findViewById(R.id.imgBook);
        Glide.with(this).load(R.drawable.book).into(imgBook);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();

            }
        }, 3000);

//        new CountDownTimer(1000,3000){
//
//            @Override
//            public void onTick(long l) {
////                do sth trong vòng bnh giây
//            }
//
//            @Override
//            public void onFinish() {
////                sau khi kết thúc 3s sẽ làm gì
//                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
//                finish();
//
//            }
//        }.start();
    }
}