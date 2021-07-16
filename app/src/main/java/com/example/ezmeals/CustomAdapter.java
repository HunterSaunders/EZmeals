package com.example.ezmeals;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Integer> {
        private ListView listView;

        public CustomAdapter(Context context, int textViewResourceId, List list, ListView listView) {
            super(context, textViewResourceId, list);
            this.listView = listView;
        }


        static class ViewHolder {
            TextView text;
            Button btn;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Integer color = getItem(position);

            View rowView = convertView;

            if (rowView == null) {
                LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
                rowView = inflater.inflate(R.layout.ingredient_row, parent, false);
                ViewHolder h = new ViewHolder();
                h.text = (TextView) rowView.findViewById(R.id.ingredient_textview);
                h.btn = rowView.findViewById(R.id.add_btn);
                rowView.setTag(h);
            }

            ViewHolder h = (ViewHolder) rowView.getTag();

            h.text.setText(color);
            h.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DO what you want to recieve on btn click there.
                }
            });

            return rowView;
        }
    }
