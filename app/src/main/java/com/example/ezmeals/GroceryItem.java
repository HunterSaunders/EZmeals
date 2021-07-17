
package com.example.ezmeals;

public class GroceryItem {


    // Creates an individual item that can be manipulated in the grocery list

    private String itemName;

    public GroceryItem(String name) {
        itemName = name;
    }

    public String getItemName() {
        return itemName;
    }
}