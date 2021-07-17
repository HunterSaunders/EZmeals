package com.example.ezmeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    //initialize home screen display and format
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Home");

        Button recipeBtn = (Button) findViewById(R.id.recipe_btn);
        Button listBtn = (Button) findViewById(R.id.list_btn);
        //set up onclick action for the two navigation buttons to SearchAPI.class and GroceryList.class
        recipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchAPI.class));
                overridePendingTransition(0,0);
            }
        });

        listBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GroceryList.class));
                overridePendingTransition(0,0);
            }
        });
        //create the bottom navigation bar view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(android.R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            //add switch/case instances for selecting an item from the navigation bar and opening that activity.class
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchAPI.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.list:
                        startActivity(new Intent(getApplicationContext(), GroceryList.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}