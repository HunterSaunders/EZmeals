package com.example.ezmeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchAPI extends AppCompatActivity {

    ////////
    String[] testArray = {"Chicken","Carrots"};//test listview
    int photos[] = {R.drawable.ic_ez_logo, R.drawable.ic_ez_logo};
    ListView testList;
    ////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_api);

        // Create listView for recipe search results. Set up to show recipe title and photo
        testList = (ListView) findViewById(R.id.recipe_search_list);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),testArray, photos);
        testList.setAdapter(customAdapter);

        getSupportActionBar().setTitle("Find A Recipe");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(android.R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
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