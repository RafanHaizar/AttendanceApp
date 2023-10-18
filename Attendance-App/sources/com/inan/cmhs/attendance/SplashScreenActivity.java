package com.inan.cmhs.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1397R.C1401layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                SplashScreenActivity.this.finish();
            }
        }, 6000);
    }
}
