package com.nata.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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

    public static final String remoteUrl = "http://localhost:3000/api/v1/records/";
    public static final String actPathUrl= "http://localhost:3000/api/v1/apks/";

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

    public static void put(String url,HashMap<String,String> props) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut= new HttpPut(url);
//        httpPost.addHeader("charset", HTTP.UTF_8);

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String,String> entry: props.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        httpPut.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));

        CloseableHttpResponse response = client.execute(httpPut);
        client.close();
    }

    public static void postSummary(String recordId, HashMap<String,String> props) throws IOException {
        String url = remoteUrl+recordId+"/summary";
        post(url,props);
    }

    public static void postActPath(String package_name,HashMap<String,String> props) throws IOException {
        String url = actPathUrl+package_name+"/actpath";
        post(url,props);
    }


    public static void postAction(String recordId,String message) throws IOException {
        HashMap<String,String > messages = new HashMap<>();
        messages.put("message",message);
        String url = remoteUrl+recordId+"/action";
        post(url, messages);
    }

    public static void postState(String recordId,String message) throws IOException {
        HashMap<String,String > messages = new HashMap<>();
        messages.put("message",message);
        String url = remoteUrl+recordId+"/state";
        post(url, messages);
    }

    public static void postActivity(String recordId,String message) throws IOException {
        HashMap<String,String > messages = new HashMap<>();
        messages.put("message",message);
        String url = remoteUrl+recordId+"/activity";
        post(url ,messages);
    }

    public static void postWidget(String recordId,String message) throws IOException {
        HashMap<String,String > messages = new HashMap<>();
        messages.put("message",message);
        String url = remoteUrl+recordId+"/widget";
        post(url, messages);
    }

    public static void postFinish(String recordId) throws IOException {
        HashMap<String,String > messages = new HashMap<>();
        messages.put("message","finish");
        String url = remoteUrl+recordId+"/finish";
        put(url, messages);
    }



}
