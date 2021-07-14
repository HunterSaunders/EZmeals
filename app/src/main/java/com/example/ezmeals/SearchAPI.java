package com.example.ezmeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.ezmeals.BuildConfig.api_id;
import static com.example.ezmeals.BuildConfig.api_key;

public class SearchAPI extends AppCompatActivity {

    public static final String SRC_LINK = "com.example.application.example.SRC_LINK";

    private final String key = api_key;
    private final String appId = api_id;

    Button searchAPIBtn;
    EditText apiSearch;
    ListView recipeDisplayList;
    ArrayList<String> recipeItems;
    ArrayList<String> recipeItemsLinks;
    String searchTerm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_api);


        //Assign Values
        apiSearch = findViewById(R.id.apiSearch);
        searchAPIBtn = findViewById(R.id.searchBtn);
        recipeDisplayList = findViewById(R.id.recipe_search_list);

        //Click Listener
        searchAPIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchTerm = apiSearch.getText().toString();
                //System.out.println(searchTerm.replaceAll("\\s", "%20"));
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(SearchAPI.this);
                String url = "https://api.edamam.com/api/recipes/v2?type=public&q=" + searchTerm + "&app_id=" + appId + "&app_key=" + key;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            recipeItems = new ArrayList<String>();
                            recipeItemsLinks = new ArrayList<String>();
                            JSONArray foundRecipes = response.getJSONArray("hits");

                            for (int i = 0; i < 20; i++) {
                                JSONObject recipeInfo = foundRecipes.getJSONObject(i);
                                JSONObject recipeItem = recipeInfo.getJSONObject(String.valueOf("recipe"));

                                String recipeName = recipeItem.getString("label");
                                String recipeUri = recipeItem.getString("uri");
                                String recipeLink = recipeUri.replaceAll("http://www.edamam.com/ontologies/edamam.owl#recipe_", "");

                                recipeItems.add(recipeName);
                                recipeItemsLinks.add(recipeLink);

//                                System.out.println(recipeLink);
//                                System.out.println(recipeItems);
                            }

                            //System.out.println(recipeItems);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(SearchAPI.this, android.R.layout.simple_list_item_1, recipeItems);
                            recipeDisplayList.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
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
            }
        });

        recipeDisplayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                System.out.println(recipeItems.get(i));
                Intent RecipeDisplayActivity = new Intent(SearchAPI.this, RecipeDisplayActivity.class);
                RecipeDisplayActivity.putExtra(SRC_LINK, recipeItemsLinks.get(i));
                startActivity(RecipeDisplayActivity);
            }
        });























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