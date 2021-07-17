package com.example.ezmeals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.ezmeals.BuildConfig.api_id;
import static com.example.ezmeals.BuildConfig.api_key;

public class RecipeDisplayActivity extends AppCompatActivity {

    //JSON objects of recipe info to be used throughout
    JSONObject recipeData;
    JSONArray recipeIngredients;

    //private variables for the api key and id so that the app is able to us the api
    private final String key = api_key;
    private final String appId = api_id;

    private Button alertButton;
    private TextView alertTextView;

    @Override
    //load the saved previous state of the application
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //display the basic layout of the recipe screen
        setContentView(R.layout.recipe_screen);

        //add a title to the page
        getSupportActionBar().setTitle("Recipe");

        //list view of the ingredients to make each ingredient able to separate
        ListView lv = (ListView) findViewById(R.id.ingredientListView);
        List<String> ingredientList = new ArrayList<String>();

        Intent intent = getIntent();
        //original recipe link from api data
        String recipeLink = intent.getStringExtra(SearchAPI.SRC_LINK);

        //a few basic variables put into specific spots on the recipe screen
        TextView recipeName = (TextView) findViewById(R.id.recipe_name);
        TextView cuisineType = (TextView) findViewById(R.id.textView5);
        TextView dishType = (TextView) findViewById(R.id.textView8);
        TextView meal = (TextView) findViewById(R.id.textView10);
        ImageView recipeImage = (ImageView) findViewById(R.id.recipe_screen_img);

        //button for the original recipe to open into google chrome
        Button urlBtn = (Button) findViewById(R.id.instructions_btn);

        //here is the link to the api where all of the information on a recipe is sourced
        RequestQueue queue = Volley.newRequestQueue(RecipeDisplayActivity.this);
        String url = "https://api.edamam.com/api/recipes/v2/" + recipeLink + "?type=public&app_id=" + appId + "&app_key=" + key;

//        JSON Object to Array and populate the list of recipes with the array
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    recipeData = response.getJSONObject("recipe");
                    recipeIngredients = recipeData.getJSONArray("ingredientLines");
                    JSONArray recipeIngredients = recipeData.getJSONArray("ingredientLines");

                    //loop to add each ingredient one by on into a list
                    if (recipeIngredients != null) {
                        int len = recipeIngredients.length();

                        for (int i=0;i<len;i++){
                            ingredientList.add(recipeIngredients.get(i).toString());
                        }
                    }

                    RequestOptions requestOptions=new RequestOptions();
                    requestOptions.placeholder(R.drawable.ic_ez_logo);
                    requestOptions.error(R.drawable.ic_ez_logo);

                    //add the recipes image onto the recipe screen
                    Glide.with(RecipeDisplayActivity.this)
                            .load(recipeData.getString("image"))
                            .apply(requestOptions)
                            .into(recipeImage);

                    //Here are the specific categories that will show up on the recipe screen
                    recipeName.setText(recipeData.getString("label"));
                    cuisineType.setText(recipeData.getString("cuisineType").replace("/","").replace("[","").replace("]","").replace("\\"," or ").replace("\"",""));
                    dishType.setText(recipeData.getString("dishType").replace("/","").replace("[","").replace("]","").replace("\\"," or ").replace("\"",""));
                    meal.setText(recipeData.getString("mealType").replace("/","").replace("[","").replace("]","").replace("\\","/").replace("\"",""));

                    //little function to open the recipe link
                    urlBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            try {
                                intent.setData(Uri.parse(recipeData.getString("url")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(intent);
                        }
                    });


                    ArrayAdapter arrayAdapter = new ArrayAdapter(RecipeDisplayActivity.this, android.R.layout.simple_list_item_1, ingredientList);
                    lv.setAdapter(arrayAdapter);

                    //function to add specific ingredients when clicked to the grocery list
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ArrayList<String> savedItems = new ArrayList<>();
                            ArrayList<GroceryItem> savedItemsList = new ArrayList<>();
                            savedItems.add(ingredientList.get(position));

                            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                            String jsonList = sharedPreferences.getString("Grocery List", null);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Gson gson = new Gson();

                            if (jsonList != null){
                                try {
                                    JSONArray jsonArray = new JSONArray(jsonList);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject objectItem = jsonArray.getJSONObject(i);
                                        String addedItem = objectItem.getString("itemName");
                                        savedItems.add(addedItem);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            for (int i = 0; i < savedItems.toArray().length; i++){
                                savedItemsList.add(new GroceryItem(savedItems.get(i)));
                            }

                            String jsonItems = gson.toJson(savedItemsList);
                            editor.putString("Grocery List", jsonItems);
                            editor.apply();

                            //notification to say that you have added an ingredient to the list
                            AlertDialog.Builder builder = new AlertDialog.Builder(RecipeDisplayActivity.this);

                            builder.setCancelable(true);
                            builder.setTitle("EZmeals Alert");
                            builder.setMessage("Ingredients saved");

                            //cancel added ingredient
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                            //view added ingredient on list
                            builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    startActivity(new Intent(getApplicationContext(), GroceryList.class));
                                    overridePendingTransition(0, 0);
                                }
                            });
                            builder.show();
                        }
                    });
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



        //basic layout for the bottom navigation view and on click to take you to each part
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