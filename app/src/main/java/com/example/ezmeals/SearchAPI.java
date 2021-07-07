package com.example.ezmeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class SearchAPI extends AppCompatActivity {

    //Get key and app id from properties

    Properties properties = new Properties();

    private final String key = properties.getProperty("api_key");
    private final String appId = properties.getProperty("app_id");
    private final String searchTerms = "chicken%20soup"; //How do you add multiple search terms? Have this value here for testing purposes
    private final String baseUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=";
    final String url = baseUrl + searchTerms + "&app_id=" + appId + "&app_key=" + key;

    private Gson gson;
    private HTTPHelper httpHelper;

    ////////
    String[] testArray = {"Chicken","Carrots"};//test listview
    int photos[] = {R.drawable.ic_ez_logo, R.drawable.ic_ez_logo};
    ListView testList;
    String recipe;
//    String photo;
    String JSON_URL = url;
    ArrayList<HashMap<String,String>> recipeList;
    ////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_api);

        // Create listView for recipe search results. Set up to show recipe title and photo
        recipeList = new ArrayList<>();
        testList = (ListView) findViewById(R.id.recipe_search_list);
//        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),testArray, photos);
//        testList.setAdapter(customAdapter);

        RecipeData getData = new RecipeData();
        getData.execute();


        //

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