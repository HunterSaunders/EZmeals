package com.example.ezmeals;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Properties;

public class RecipeData {

    // Create arraylist for recipe hits?
    //JSON Format for search hits:
    // "hits": [
        // {
        // "recipe": {
            // "label": "string",
            // "image": "string
            // }
        //]

    //JSON format for recipe info:
        //

    ArrayList<String> recipes = new ArrayList<String>();

    //Get key and app id from properties and elements to build the url to search the api

    Properties properties = new Properties();

    private final String key = properties.getProperty("api_key");
    private final String appId = properties.getProperty("app_id");
    private final String searchTerms = "chicken%20soup"; //Have this value here for testing purposes
    private final String baseUrl = "https://api.edamam.com/api/recipes/v2?type=public&q=";

    private Gson gson;
    private HTTPHelper httpHelper;

    public RecipeData() {
        gson = new Gson();
        httpHelper = new HTTPHelper();
    }

    public Recipe loadRecipe (String searchTerms) {
        final String url = baseUrl + searchTerms + "&app_id=" + appId + "&app_key=" + key;
        String data = httpHelper.readHTTP(url);

        //Convert JSON to Recipe object
        return gson.fromJson(data, Recipe.class);

    }
}
