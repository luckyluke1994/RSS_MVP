package com.example.maidaidien.rssmvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.maidaidien.rssmvp.model.RSSItem;

public class DetailsActivity extends AppCompatActivity {
    private RSSItem mRSSItem;
    private WebView mWebView;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please wait...");

        Bundle args = getIntent().getExtras();
        if (args != null) {
            mRSSItem = (RSSItem) args.get(Constant.NEWS_EXTRA);
        }

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebViewClient(new Client());
        if (mRSSItem != null) {
            mWebView.loadUrl(mRSSItem.getLink());
            mProgressDialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
