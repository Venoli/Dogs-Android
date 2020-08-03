package com.example.dogs.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.dogs.R;

public class SplashActivity extends Activity {
    //splash screen code : https://stackoverflow.com/questions/5486789/how-do-i-make-a-splash-screen
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int halfSecondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, halfSecondsDelayed * 500);
    }
}
