package com.example.maidaidien.rssmvp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maidaidien.rssmvp.R;
import com.example.maidaidien.rssmvp.model.RSSItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mai.dai.dien on 28/03/2017.
 */

public class NewsAdapter extends ArrayAdapter<RSSItem> {
    public NewsAdapter(Context context, int resource, ArrayList<RSSItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (null == listItemView) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_news_item, parent, false);
        }

        RSSItem currentItem = getItem(position);

        // find views
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_textview);
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.description_textview);
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_textview);
        ImageView thumbImage = (ImageView) listItemView.findViewById(R.id.thumbnail_image);

        // dum data
        titleTextView.setText(currentItem.getTitle());
        descriptionTextView.setText(currentItem.getDescription());
        dateTextView.setText(currentItem.getDate());
        Picasso.with(getContext())
                .load(currentItem.getImage())
                .into(thumbImage);
        return listItemView;
    }
}
