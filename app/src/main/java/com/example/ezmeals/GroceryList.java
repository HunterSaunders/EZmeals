package com.example.ezmeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GroceryList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        getSupportActionBar().setTitle("My Grocery List");

        //This is for the list view in the grocery list activity

        String[] groceryList = {"Bread","Milk","Eggs","Sugar","Apples","Carrots","Yogurt","Broccoli","Celery","Ice Cream",
                                "Lettuce","Bacon"}; //this list used to test the list view in grocery list activity

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.textview_grocerylist, groceryList);

        ListView listView = (ListView) findViewById(R.id.groceryList);
        listView.setAdapter(adapter);




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
                        startActivity(new Intent(getApplicationContext(), SearchAPI.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.list:
                        return true;
                }
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drop_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem){
        int id = menuItem.getItemId();
        if (id == R.id.add_item){
            //Toast.makeText(getApplicationContext(),"Add Item", Toast.LENGTH_SHORT).show();
            AddNewItemPopUp();
        }
        if (id == R.id.clear_list){
            //Toast.makeText(getApplicationContext(),"Clear List", Toast.LENGTH_SHORT).show();
            ClearGroceryList();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView item_title;
    private TextView clear_title;
    private EditText new_item_name;
    private Button save_new_item;
    private Button cancel_item_window;
    private Button clear_items;
    private Button cancel_clear_window;


    public void AddNewItemPopUp(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View newItemPopUp = getLayoutInflater().inflate(R.layout.item_popup, null);

        item_title = (TextView) newItemPopUp.findViewById(R.id.clear_title);
        new_item_name = (EditText) newItemPopUp.findViewById(R.id.new_item_name);
        save_new_item = (Button) newItemPopUp.findViewById(R.id.save_new_item);
        cancel_item_window = (Button) newItemPopUp.findViewById(R.id.cancel_item_window);

        dialogBuilder.setView(newItemPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        save_new_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create Save Method Here
            }
        });
        cancel_item_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void ClearGroceryList(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View clearListPopUp = getLayoutInflater().inflate(R.layout.clear_popup, null);

        clear_title = (TextView) clearListPopUp.findViewById(R.id.clear_title);
        clear_items = (Button) clearListPopUp.findViewById(R.id.clear_items);
        cancel_clear_window = (Button) clearListPopUp.findViewById(R.id.cancel_clear_window);

        dialogBuilder.setView(clearListPopUp);
        dialog = dialogBuilder.create();
        dialog.show();

        clear_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create Clear Method Here
            }
        });
        cancel_clear_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
