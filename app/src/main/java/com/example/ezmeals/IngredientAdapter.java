package com.example.ezmeals;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class IngredientAdapter extends ArrayAdapter<Ingredients> {
    private ArrayList<Ingredients> ingredientsList;

    public IngredientAdapter(@NonNull Context context, int resource, ArrayList<Ingredients> ingredientsListItems) {
        super(context, resource);
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        int phraseIndex = position;
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_row, parent, false);
        }
        TextView ingredientTitle = convertView.findViewById(R.id.ingredient_textview);
        Button addIngredientBtn = convertView.findViewById(R.id.add_btn);

        ingredientTitle.setText(ingredientsList.get(position).getIngredientName());

        System.out.println(ingredientTitle.getText().toString());

        addIngredientBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println(ingredientTitle.getText().toString());
            }
        });

        return convertView;
    }


}
