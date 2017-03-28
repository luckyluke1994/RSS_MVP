package com.example.maidaidien.rssmvp.service;

import android.os.AsyncTask;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by mai.dai.dien on 27/03/2017.
 */

public class DownloadTask extends AsyncTask<String, Void, List<RSSItem>> {
    private OnLoadFinish mOnLoadFinish;

    public void setOnLoadFinish(OnLoadFinish onLoadFinish) {
        this.mOnLoadFinish = onLoadFinish;
    }

    @Override
    protected List<RSSItem> doInBackground(String... params) {
        return parseXml(params[0]);
    }

    private List<RSSItem> parseXml(String link) {
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
            List<RSSItem> newsList = new ArrayList<>();

            for (int i = 0; i < length; i++) {
                // each article of news we need to get title, description, image, date
                Node currentNode = nl.item(i);
                RSSItem _item = new RSSItem();

                NodeList nChild = currentNode.getChildNodes();
                int clength = nChild.getLength();

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
                // add news article into list
                newsList.add(_item);
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
