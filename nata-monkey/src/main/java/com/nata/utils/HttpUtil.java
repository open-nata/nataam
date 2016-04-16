package com.nata.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-13 22:24
 */
public class HttpUtil {

    public static void post(String url,HashMap<String,String> props) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader("charset", HTTP.UTF_8);

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String,String> entry: props.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

        CloseableHttpResponse response = client.execute(httpPost);
        client.close();
    }

    public static void postSummary(HashMap<String,String> props) throws IOException {
        String summaryUrl = "http://localhost:3000/summary";
        post(summaryUrl,props);
    }

    public static void postAction(String message) throws IOException {
        HashMap<String,String > messages = new HashMap<>();
        messages.put("message",message);
        post("http://localhost:3000/action",messages);
    }

    public static void postState(String message) throws IOException {
        HashMap<String,String > messages = new HashMap<>();
        messages.put("message",message);
        post("http://localhost:3000/state",messages);
    }

    public static void postActivity(String message) throws IOException {
        HashMap<String,String > messages = new HashMap<>();
        messages.put("message",message);
        post("http://localhost:3000/activity",messages);
    }

    public static void postWidget(String message) throws IOException {
        HashMap<String,String > messages = new HashMap<>();
        messages.put("message",message);
        post("http://localhost:3000/widget",messages);
    }



}
