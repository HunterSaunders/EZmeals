package com.example.ezmeals;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.gson.Gson;

import java.util.List;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 4000;

    //Get key and app id from properties

    Properties properties = new Properties();

    private final String key = properties.getProperty("api_key");
    private final String appId = properties.getProperty("app_id");
    private final String searchTerms = "chicken%20soup"; //How do you add multiple search terms? Have this value here for testing purposes
    private final String baseUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=";
    final String url = baseUrl + searchTerms + "&app_id=" + appId + "&app_key=" + key;

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