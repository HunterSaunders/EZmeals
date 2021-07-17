package com.example.ezmeals;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //display load screen with a timeout
    private static int SPLASH_SCREEN_TIME_OUT = 4000;

    //create and initialize the main activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            //start Home.class to transition the display and functionality to the Home activity
            @Override
            public void run(){
                Intent homeIntent = new Intent(MainActivity.this, Home.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }


}