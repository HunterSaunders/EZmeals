package com.example.ezmeals;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.ezmeals.BuildConfig.api_key;
import static com.example.ezmeals.BuildConfig.app_id;

public class RecipeDisplayActivity extends AppCompatActivity {

    JSONObject recipeData;
    JSONArray recipeIngredients;

    private final String key = api_key;
    private final String appId = app_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_screen);
        getSupportActionBar().setTitle("Recipe");


        Intent intent = getIntent();
        String recipeLink = intent.getStringExtra(SearchAPI.SRC_LINK);


        TextView recipeName = (TextView) findViewById(R.id.recipe_name);
        TextView cuisineType = (TextView) findViewById(R.id.textView5);
        TextView dishType = (TextView) findViewById(R.id.textView8);
        TextView meal = (TextView) findViewById(R.id.textView10);
        TextView ingredientList = (TextView) findViewById(R.id.textView12);
        ImageView recipeImage = (ImageView) findViewById(R.id.recipe_screen_img);


        RequestQueue queue = Volley.newRequestQueue(RecipeDisplayActivity.this);
        String url = "https://api.edamam.com/api/recipes/v2/" + recipeLink + "?type=public&app_id=" + appId + "&app_key=" + key;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    recipeData = response.getJSONObject("recipe");
                    JSONArray recipeIngredients = recipeData.getJSONArray("ingredientLines");
                    List<String> ingredients = new ArrayList<String>();


                    if (recipeIngredients != null) {
                        int len = recipeIngredients.length();
                        for (int i=0;i<len;i++){
                            ingredients.add(recipeIngredients.get(i).toString());
                        }
                    }


                    System.out.println(ingredients);


                    RequestOptions requestOptions=new RequestOptions();
                    requestOptions.placeholder(R.drawable.ic_ez_logo);
                    requestOptions.error(R.drawable.ic_ez_logo);
                    Glide.with(RecipeDisplayActivity.this)
                            .load(recipeData.getString("image"))
                            .apply(requestOptions)
                            .into(recipeImage);
                    recipeName.setText(recipeData.getString("label"));
                    cuisineType.setText(recipeData.getString("cuisineType"));
                    dishType.setText(recipeData.getString("dishType"));
                    meal.setText(recipeData.getString("mealType"));

                    StringBuilder builder = new StringBuilder();
                    for (String details : ingredients) {
                        builder.append(details + "\n" + "\n");
                    }
                    ingredientList.setText(builder.toString());
                    ingredientList.setMovementMethod(new ScrollingMovementMethod());







                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error");
            }
        });
        queue.add(request);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(android.R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchAPI.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.list:
                        startActivity(new Intent(getApplicationContext(), GroceryList.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

}