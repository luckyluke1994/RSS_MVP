package com.example.maidaidien.rssmvp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maidaidien.rssmvp.Constant;
import com.example.maidaidien.rssmvp.R;
import com.squareup.picasso.Picasso;

/**
 * Created by mai.dai.dien on 28/03/2017.
 */

public class NewsAdapter extends CursorAdapter {
    private Uri mUri;

    public NewsAdapter(Context context, Cursor c, int flags, Uri uri) {
        super(context, c, flags);
        mUri = uri;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        String title, description, date, image;
        title = cursor.getString(Constant.COL_TITLE);
        description = cursor.getString(Constant.COL_DESCRIPTION);
        date = cursor.getString(Constant.COL_DATE);
        image = cursor.getString(Constant.COL_IMAGE);
        viewHolder.titleView.setText(Html.fromHtml(title));
        viewHolder.descriptionView.setText(Html.fromHtml(description));
        viewHolder.dateView.setText(date);
        Picasso.with(context)
                .load(image)
                .into(viewHolder.iconView);
    }

    private static class ViewHolder {
        ImageView iconView;
        TextView titleView;
        TextView descriptionView;
        TextView dateView;

        ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.thumbnail_image);
            titleView = (TextView) view.findViewById(R.id.title_textview);
            descriptionView = (TextView) view.findViewById(R.id.description_textview);
            dateView = (TextView) view.findViewById(R.id.date_textview);
        }
    }
}
