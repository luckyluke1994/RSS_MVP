package com.example.maidaidien.rssmvp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.maidaidien.rssmvp.Constant;
import com.example.maidaidien.rssmvp.R;
import com.example.maidaidien.rssmvp.Utils;
import com.example.maidaidien.rssmvp.adapter.NewsAdapter;
import com.example.maidaidien.rssmvp.model.ModelHelper;
import com.example.maidaidien.rssmvp.model.NewsContract;
import com.example.maidaidien.rssmvp.presenter.Callbacks;
import com.example.maidaidien.rssmvp.presenter.NewsPresenter;

/**
 * Created by mai.dai.dien on 24/03/2017.
 */

public class FootballNewsFragment extends Fragment implements Callbacks.NewsView {
    public static final String TITLE = "Football News";
    public static final String FOOTBALL_NEWS_LINK = "http://www.24h.com.vn/upload/rss/bongda.rss";

    private NewsAdapter mNewsAdapter;
    private ListView mNewsListView;
    private NewsPresenter mNewsPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mNewsPresenter = new NewsPresenter(this,
                new ModelHelper(FOOTBALL_NEWS_LINK,
                        NewsContract.FootballNewsEntry.CONTENT_URI,
                        getLoaderManager(),
                        Constant.FootballNewsLoaderId));
        mProgressDialog = Utils.getProgressDialog(getActivity(), R.style.TransparentDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football_news, container, false);
        mNewsListView = (ListView) view.findViewById(R.id.news_listview);
        mNewsAdapter = new NewsAdapter(getActivity(), null, 0, NewsContract.FootballNewsEntry.CONTENT_URI);
        mNewsListView.setAdapter(mNewsAdapter);
        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refresh() {
        if (Utils.isNetworkAvailable(getActivity())) {
            mProgressDialog.show();
            mNewsPresenter.refresh();
        }
    }

    @Override
    public Context getAppContext() {
        return getActivity();
    }

    @Override
    public void onLoadFinish(Cursor data) {
        mNewsAdapter.swapCursor(data);
        mProgressDialog.dismiss();
    }
}
