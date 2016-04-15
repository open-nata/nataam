package com.nata.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Calvin Meng
 * Blog: mclspace.com  Email: rdmclin2@gamil.com
 * Update: 2016-04-13 22:24
 */
public class HttpUtil {

    public static void post(String url,ArrayList<String> messages) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList<>();
        for (String message: messages
             ) {
            params.add(new BasicNameValuePair("message", message));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse response = client.execute(httpPost);
        client.close();
    }


    public static void postTestResult(ArrayList<String> messages) throws IOException {
        post("http://localhost:3000/test",messages);
    }

    public static void postLive(String message) throws IOException {
        ArrayList<String> messages = new ArrayList<>();
        messages.add(message);
        post("http://localhost:3000/live",messages);
    }

    public static void postSummary(String summary) throws IOException {
        ArrayList<String> messages = new ArrayList<>();
        messages.add(summary);
        post("http://localhost:3000/summary",messages);
    }

    public static void postActivity(String activity) throws IOException {
        ArrayList<String> messages = new ArrayList<>();
        messages.add(activity);
        post("http://localhost:3000/activity",messages);
    }

}
