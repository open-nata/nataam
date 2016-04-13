//package com.nata.http;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.fluent.Form;
//import org.apache.http.client.fluent.Request;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Author: Calvin Meng
// * Blog: mclspace.com  Email: rdmclin2@gamil.com
// * Update: 2016-04-13 21:29
// */
//public class HttpClientExample {
//
//    public void whenPostRequestUsingHttpClient_thenCorrect()
//            throws  IOException {
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://localhost:3000/hello");
//
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("message", "hello"));
//        httpPost.setEntity(new UrlEncodedFormEntity(params));
//
//        CloseableHttpResponse response = client.execute(httpPost);
//        client.close();
//    }
//
//
//    public void whenPostJsonUsingHttpClient_thenCorrect()
//            throws ClientProtocolException, IOException {
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://localhost:3000/hello");
//
//        String json = "{\"id\":1,\"name\":\"John\"}";
//        StringEntity entity = new StringEntity(json);
//        httpPost.setEntity(entity);
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("Content-type", "application/json");
//
//        CloseableHttpResponse response = client.execute(httpPost);
////        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
//        client.close();
//    }
//
////    public void whenPostFormUsingHttpClientFluentAPI_thenCorrect()
////            throws ClientProtocolException, IOException {
////        HttpResponse response =
////                Request.Post("http://localhost:3000/hello").
////                        .bodyForm(
////                        Form.form().add("message", "hello").build())
////                        .execute().returnResponse();
////
////    }
//
////    public void whenSendMultipartRequestUsingHttpClient_thenCorrect()
////            throws ClientProtocolException, IOException {
////        CloseableHttpClient client = HttpClients.createDefault();
////        HttpPost httpPost = new HttpPost("http://www.example.com");
////
////        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
////        builder.addTextBody("username", "John");
////        builder.addTextBody("password", "pass");
////        builder.addBinaryBody("file", new File("test.txt"),
////                ContentType.APPLICATION_OCTET_STREAM, "file.ext");
////
////        HttpEntity multipart = builder.build();
////        httpPost.setEntity(multipart);
////
////        CloseableHttpResponse response = client.execute(httpPost);
////        client.close();
////    }
//
//    public static void main(String[] args) throws IOException {
//        HttpClientExample example = new HttpClientExample();
//        example.whenPostRequestUsingHttpClient_thenCorrect();
////        example.whenPostJsonUsingHttpClient_thenCorrect();
//    }
//}
