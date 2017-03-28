package com.example.maidaidien.rssmvp.presenter;

import com.example.maidaidien.rssmvp.model.RSSItem;

import java.util.List;

/**
 * Created by mai.dai.dien on 28/03/2017.
 */

public interface OnLoadFinish {
    public void onLoadFinish(List<RSSItem> rssItemList);
}
