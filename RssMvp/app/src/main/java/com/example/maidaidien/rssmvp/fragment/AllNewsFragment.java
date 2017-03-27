package com.example.maidaidien.rssmvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maidaidien.rssmvp.R;
import com.example.maidaidien.rssmvp.service.DownloadTask;

/**
 * Created by mai.dai.dien on 24/03/2017.
 */

public class AllNewsFragment extends Fragment {
    public static final String TITLE = "All News";
    public static final String ALL_NEWS_LINK = "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DownloadTask().execute(ALL_NEWS_LINK);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_news, container, false);
        return view;
    }
}
