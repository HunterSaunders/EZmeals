package com.example.ezmeals;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 4000;
    private static final String TAG = "MainActivity";
//    private final String key = getString(R.string.api_key); //api key from resource file: res/values/secrets.xml
//    private final String appId = getString(R.string.app_id); //app id from resource file: res/values/secrets.xml
//    private final String searchTerms = "chicken%20soup"; //How do you add multiple search terms? Add them to a list? This is here for testing purposes
//    private final String baseUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=";
//    private final String url = baseUrl + searchTerms + "&app_id=" + appId + "&app_key=" + key;
    private Gson gson;
    private HTTPHelper httpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent homeIntent = new Intent(MainActivity.this, Home.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }


    }