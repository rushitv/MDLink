package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class Splash_Activity extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);


        logo= (ImageView) findViewById(R.id.logo);


        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(2500);
        logo.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Activity.this,Guide_Page.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
