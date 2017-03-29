package com.example.maidaidien.rssmvp.service;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.maidaidien.rssmvp.model.NewsContract;
import com.example.maidaidien.rssmvp.model.NewsProvider;
import com.example.maidaidien.rssmvp.model.RSSItem;
import com.example.maidaidien.rssmvp.presenter.OnLoadFinish;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by mai.dai.dien on 27/03/2017.
 */

public class DownloadTask extends AsyncTask<String, Void, List<RSSItem>> {
    private OnLoadFinish mOnLoadFinish;
    private Context mContext;
    private Uri mUri;

    public void setOnLoadFinish(OnLoadFinish onLoadFinish) {
        this.mOnLoadFinish = onLoadFinish;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    public DownloadTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected List<RSSItem> doInBackground(String... params) {
        return parseXml(params[0], mUri);
    }

    private List<RSSItem> parseXml(String link, Uri uri) {
        URL url = null;
        try {
            url = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            // Create required instances
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setCoalescing(true);//convert any CDATA node to text node
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Parse the xml
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            // Get all tags

            NodeList nl = doc.getElementsByTagName("item");
            int length = nl.getLength();
            Vector<ContentValues> cVVector = new Vector<>(length);
            List<RSSItem> newsList = new ArrayList<>();

            for (int i = 0; i < length; i++) {
                // each article of news we need to get title, description, image, date
                Node currentNode = nl.item(i);
                RSSItem _item = new RSSItem();

                NodeList nChild = currentNode.getChildNodes();
                int clength = nChild.getLength();
                // Get the required elements from each item
                ContentValues newsValues = new ContentValues();

                for (int j = 1; j < clength; j += 2) {
                    Node thisNode = nChild.item(j);
                    String nodeName = thisNode.getNodeName();
                    String value = nChild.item(j).getFirstChild().getNodeValue();
                    if (value != null) {
                        if ("title".equals(nodeName)) {
                            _item.setTitle(value);
                        } else if ("description".equals(nodeName)) {
                            // Parse the html description to get the image url
                            String html = value;
                            org.jsoup.nodes.Document docHtml = Jsoup.parse(html);
                            Elements imgEle = docHtml.select("img");
                            _item.setImage(imgEle.attr("src"));

                            // Set Description from CDATA
                            _item.setDescription(docHtml.text());
                        } else if ("pubDate".equals(nodeName)) {
                            _item.setDate(value);
                        } else if ("link".equals(nodeName)) {
                            _item.setLink(value);
                        }
                    }
                }// end for loop 2

                switch (NewsProvider.sUriMatcher.match(uri)) {
                    case NewsProvider.ALLNEWS:
                        newsValues.put(NewsContract.AllNewsEntry.COLUMN_TITLE, _item.getTitle());
                        newsValues.put(NewsContract.AllNewsEntry.COLUMN_DESCRIPTION, _item.getDescription());
                        newsValues.put(NewsContract.AllNewsEntry.COLUMN_DATE, _item.getDate());
                        newsValues.put(NewsContract.AllNewsEntry.COLUMN_IMAGE, _item.getImage());
                        newsValues.put(NewsContract.AllNewsEntry.COLUMN_LINK, _item.getLink());
                        break;
                    default:
                        throw new UnsupportedOperationException("Unknown uri: " + mUri);
                }
                cVVector.add(newsValues);
                // add news article into list
                newsList.add(_item);
            }

            int inserted = 0;
            // delete old data
            this.mContext.getContentResolver().delete(mUri, null, null);
            // add to database
            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                this.mContext.getContentResolver().bulkInsert(uri, cvArray);
            }
            return newsList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<RSSItem> rssItems) {
        if (mOnLoadFinish != null) {
            mOnLoadFinish.onLoadFinish(rssItems);
        }
    }
}
