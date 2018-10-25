package com.mdlink;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Toolbar toolbar;
    TextView tvThree,tvThree2,tvThree23,tvThree234,tvThree2345,tvThree23456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        setSupportActionBar(toolbar);

        tvThree = findViewById(R.id.tvThree);
        tvThree.setOnClickListener(this);
        tvThree2 = findViewById(R.id.tvOne2);
        tvThree2.setOnClickListener(this);
        tvThree23 = findViewById(R.id.tvOne23);
        tvThree23.setOnClickListener(this);
        tvThree234 = findViewById(R.id.tvOne234);
        tvThree234.setOnClickListener(this);
        tvThree2345 = findViewById(R.id.tvOne2345);
        tvThree2345.setOnClickListener(this);
        tvThree23456 = findViewById(R.id.tvOne23456);
        tvThree23456.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);


        } else if (id == R.id.doctor_portal) {
            Intent intent = new Intent(MainActivity.this, Doctor_portal_Activity.class);
            startActivity(intent);

        } else if (id == R.id.patient_portal) {
            Intent intent = new Intent(MainActivity.this, Patient_portal_Activity.class);
            startActivity(intent);

        } else if (id == R.id.contactus) {
            Intent intent = new Intent(MainActivity.this, ContactUs_Activity.class);
            startActivity(intent);

        } else if (id == R.id.aboutus) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.userguide) {
            Intent intent = new Intent(MainActivity.this, User_Guide_Activity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v) {

        switch(v.getId()){
            case R.id.tvThree:
                Intent intent=new Intent(MainActivity.this,Urgent_Care.class);
                startActivity(intent);
                break;
            case R.id.tvThree2:
                Intent intent2=new Intent(MainActivity.this,Medical_Advice.class);
                startActivity(intent2);
                break;
            case R.id.tvThree23:
                Intent intent3=new Intent(MainActivity.this,Labs_and_Screening.class);
                startActivity(intent3);
                break;
            case R.id.tvThree234:
                Intent intent4=new Intent(MainActivity.this,Addiction_Activity.class);
                startActivity(intent4);
                break;
            case R.id.tvThree2345:
                Intent intent5=new Intent(MainActivity.this,Addiction_Activity.class);
                startActivity(intent5);
                break;
            case R.id.tvThree23456:
                Intent intent6=new Intent(MainActivity.this,Mood_Activity.class);
                startActivity(intent6);
                break;

        }
    }
}
