package com.mdlink.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.mdlink.MainActivity;
import com.mdlink.R;

public class SplashActivity extends AppCompatActivity {
    private AppCompatImageView appCompatImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_);
        appCompatImageView = findViewById(R.id.logo);

        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(2500);
        appCompatImageView.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
