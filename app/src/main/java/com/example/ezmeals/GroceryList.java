package com.example.ezmeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GroceryList extends AppCompatActivity {

    ////////
    ArrayList<String> groceryList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;

    private SharedPreferences appPreferences;
    private SharedPreferences.Editor preferenceEditor;
    ////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        getSupportActionBar().setTitle("My Grocery List");

        loadData();

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

        groceryList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.grocery_view_layout, groceryList);
        listView = findViewById(R.id.grocery_list_view);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;

                if(!textView.getPaint().isStrikeThruText()){
                    textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    textView.setTextColor(Color.argb(50,0,0,0));
                } else{
                    textView.setPaintFlags(textView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    textView.setTextColor(Color.rgb(0,0,0));
                }
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
            AddNewItemPopUp();
        }
        if (id == R.id.clear_list){
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

                groceryList.add(new_item_name.getText().toString());
                saveData();
                new_item_name.setText("");
                dialog.dismiss();
            }
        });
        cancel_item_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void saveData(){
        appPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        preferenceEditor = appPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(groceryList);
        preferenceEditor.putString("Grocery List", json);
        preferenceEditor.apply();

        loadData();
        arrayAdapter.notifyDataSetChanged();
    }

    public void loadData(){
        appPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = appPreferences.getString("Grocery List", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();


//       groceryList = Collections.singletonList(gson.toJson(json, type));
//
//        if (groceryList == null){
//            groceryList = new ArrayList<>();
//        }

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
                groceryList.clear();
                dialog.dismiss();
                arrayAdapter.notifyDataSetChanged();
                System.out.println(groceryList);
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
