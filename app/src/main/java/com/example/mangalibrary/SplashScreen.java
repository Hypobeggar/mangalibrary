package com.example.mangalibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent startHome = new Intent(SplashScreen.this,HomeActivity.class);
                startActivity(startHome);
                finish();
            }

        }, 3000);

    }
    @Override
    protected void onStart() {
        super.onStart();


    }
}