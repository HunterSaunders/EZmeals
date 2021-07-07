package com.example.ezmeals;

// Custom adapter to show recipe names and photos in recipe search
// - made to test the test list for listView on activity_search_api - may not be needed
// -followed tutorial at abhiandroid.com/ui/listview

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String testArray[];
    int photos[];
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, String[] testArray/*, int[] photos*/) {
        this.context = context;
        this.testArray = testArray;
//        this.photos = photos;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return testArray.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.textview_recipelist, null);
        TextView recipe = (TextView) view.findViewById(R.id.recipe_textview);
//        ImageView icon = (ImageView) view.findViewById(R.id.image);
        recipe.setText(testArray[i]);
//        icon.setImageResource(photos[i]);
        return view;
    }
}
