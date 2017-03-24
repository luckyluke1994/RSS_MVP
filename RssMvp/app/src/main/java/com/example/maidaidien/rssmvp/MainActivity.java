package com.example.maidaidien.rssmvp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.maidaidien.rssmvp.adapter.ViewPagerAdapter;
import com.example.maidaidien.rssmvp.fragment.AllNewsFragment;
import com.example.maidaidien.rssmvp.fragment.FootballNewsFragment;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPagerContainerOfActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPagerContainerOfActivity = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewPager();
        mTabLayout.setupWithViewPager(mViewPagerContainerOfActivity);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllNewsFragment(), AllNewsFragment.TITLE);
        adapter.addFragment(new FootballNewsFragment(), FootballNewsFragment.TITLE);
        mViewPagerContainerOfActivity.setAdapter(adapter);
    }
}
