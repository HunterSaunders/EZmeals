package com.example.ezmeals;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.ezmeals.BuildConfig.api_id;
import static com.example.ezmeals.BuildConfig.api_key;

public class RecipeDisplayActivity extends AppCompatActivity {

    JSONObject recipeData;
    JSONArray recipeIngredients;

    private final String key = api_key;
    private final String appId = api_id;
    private Button alertButton;
    private TextView alertTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_screen);
        getSupportActionBar().setTitle("Recipe");

        ListView lv = (ListView) findViewById(R.id.ingredientListView);
        List<String> ingredientList = new ArrayList<String>();




        //alertButton = (Button) findViewById(R.id.save_ingredients);
        //alertTextView = (TextView) findViewById(R.id.AlertTextView);

//        alertButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                AlertDialog.Builder builder = new AlertDialog.Builder(RecipeDisplayActivity.this);
//
//                builder.setCancelable(true);
//                builder.setTitle("EZmeals Alert");
//                builder.setMessage("Ingredients saved");
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//
//                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int i) {
//                        startActivity(new Intent(getApplicationContext(), GroceryList.class));
//                        overridePendingTransition(0, 0);
//                    }
//                });
//                builder.show();
//            }
//        });


        Intent intent = getIntent();
        String recipeLink = intent.getStringExtra(SearchAPI.SRC_LINK);


        TextView recipeName = (TextView) findViewById(R.id.recipe_name);
        TextView cuisineType = (TextView) findViewById(R.id.textView5);
        TextView dishType = (TextView) findViewById(R.id.textView8);
        TextView meal = (TextView) findViewById(R.id.textView10);
        //TextView ingredients = (TextView) findViewById(R.id.textView12);
        ImageView recipeImage = (ImageView) findViewById(R.id.recipe_screen_img);


        RequestQueue queue = Volley.newRequestQueue(RecipeDisplayActivity.this);
        String url = "https://api.edamam.com/api/recipes/v2/" + recipeLink + "?type=public&app_id=" + appId + "&app_key=" + key;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    recipeData = response.getJSONObject("recipe");
                    recipeIngredients = recipeData.getJSONArray("ingredientLines");
                    JSONArray recipeIngredients = recipeData.getJSONArray("ingredientLines");



                    if (recipeIngredients != null) {
                        int len = recipeIngredients.length();
                        for (int i=0;i<len;i++){
                            ingredientList.add(recipeIngredients.get(i).toString());
                        }
                    }



                    RequestOptions requestOptions=new RequestOptions();
                    requestOptions.placeholder(R.drawable.ic_ez_logo);
                    requestOptions.error(R.drawable.ic_ez_logo);
                    Glide.with(RecipeDisplayActivity.this)
                            .load(recipeData.getString("image"))
                            .apply(requestOptions)
                            .into(recipeImage);
                    recipeName.setText(recipeData.getString("label"));
                    cuisineType.setText(recipeData.getString("cuisineType").replace("/","").replace("[","").replace("]","").replace("\\"," or ").replace("\"",""));
                    dishType.setText(recipeData.getString("dishType").replace("/","").replace("[","").replace("]","").replace("\\"," or ").replace("\"",""));
                    meal.setText(recipeData.getString("mealType").replace("/","").replace("[","").replace("]","").replace("\\","/").replace("\"",""));
                    //ingredients.setText(recipeData.getString("ingredientLines").replace("\"","\n").replace(",","").replace("[","").replace("]","").replace("\\",""));
                    StringBuilder builder = new StringBuilder();
                    for (String details : ingredientList) {
                        builder.append(details + "\n" + "\n");
                    }
                    //ingredients.setText(builder.toString());


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

        //Custom Adapter from CustomAdapter class -- ingredient_row.xml is textview with button layout for the row of the ingredient listview

        CustomAdapter customAdapter = new CustomAdapter(this, R.id.ingredient_textview, ingredientList, lv);

        lv.setAdapter(customAdapter);
        System.out.println(ingredientList);

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
