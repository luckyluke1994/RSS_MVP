package com.example.maidaidien.rssmvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maidaidien.rssmvp.R;

/**
 * Created by mai.dai.dien on 24/03/2017.
 */

public class FootballNewsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football_news, container, false);
        return view;
    }
}
