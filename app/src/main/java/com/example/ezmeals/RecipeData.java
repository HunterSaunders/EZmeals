package com.example.ezmeals;

import android.os.AsyncTask;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

//retrieve JSON data - got help from https://www.youtube.com/watch?v=v4X0y6-VOtM
public class RecipeData extends AsyncTask<String, String, String> {

    SearchAPI search = new SearchAPI();

    @Override
    protected String doInBackground(String... strings) {
        String current = "";

        try {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                SearchAPI search = new SearchAPI();
                String jsonUrl = search.JSON_URL;
                url = new URL(jsonUrl);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                int data = isr.read();
                while (data != -1) {
                    current += (char) data;
                    data = isr.read();
                }

                return current;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }
            return current;
    }

    @Override
    protected void onPostExecute(String s) {
        //collection of JSON objects
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("hits");

            for (int i = 0; i< jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                SearchAPI search = new SearchAPI();
                String recipe = search.recipe;
//                String photo = search.photo;
                ArrayList recipeList = search.recipeList;

                recipe = jsonObject1.getString("recipe");
//                photo = jsonObject1.getString("image");


                //Hashmap
                HashMap<String, String> hits = new HashMap<>();

                hits.put("recipe", recipe);
//                hits.put("image", photo);

                recipeList.add(hits);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Display the results -- can't figure out error here for first parameter -- commenting out so program runs

//        CustomAdapter adapter = new CustomAdapter(search.getApplicationContext(),search.testArray/*, photos*/);
//                search.testList.setAdapter(adapter);

    }



}
