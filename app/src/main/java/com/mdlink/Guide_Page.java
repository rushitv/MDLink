package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Guide_Page extends AppCompatActivity {

    ViewPager viewPager;
    int[] imgdata = {R.drawable.doct, R.drawable.wom, R.drawable.computer};
    int i_position;
    TextView btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide__page);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        CustomView customView = new CustomView(Guide_Page.this, imgdata);
        viewPager.setAdapter(customView);
        btn_finish =  findViewById(R.id.view_finish_btn);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                i_position = position;
                int i = imgdata.length - 1;
                if (i == position) {

                    btn_finish.setText("Book Appointment");

                } else {
                    btn_finish.setText("Continue");

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = imgdata.length - 1;
                if (i == i_position) {
                    //btn_finish.setText("Finish");
                    Intent intent = new Intent(Guide_Page.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    btn_finish.setText("Next");
                    viewPager.setCurrentItem(i_position + 1,true);
                }


            }
        });
    }
}





