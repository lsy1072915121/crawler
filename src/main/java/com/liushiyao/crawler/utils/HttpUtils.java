package com.liushiyao.crawler.utils;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * @author: liushiyao
 * @description: Http工具类
 * @date: Created Time 2017/11/10
 */
public class HttpUtils {


  /**
   * @param params get请求URL
   * @return 返回信息
   */
  public static String sendGet(String url, Map<String, String> params) {

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    String paraString = "";
    String result = null;
    if (params != null && !params.isEmpty()) {
      for (Map.Entry<String, String> entry : params.entrySet()) {
        paraString += entry.getKey() + "=" + entry.getValue() + "&";
      }
    }
    HttpGet httpGet = new HttpGet(url + "?" + paraString);
    try {
      HttpResponse httpResponse = httpClient.execute(httpGet);
      if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        return result;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      httpGet.releaseConnection();
    }
    return result;

  }


  /**
   * 发送Post请求（非Json）
   */
  public static String sendPost(String url, List<BasicNameValuePair> params) {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost httpPost = new HttpPost(url);
    String result = null;
    try {
      httpPost.setEntity(new UrlEncodedFormEntity(params));
      HttpResponse httpResponse = httpClient.execute(httpPost);
      if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      httpPost.releaseConnection();
    }
    return result;
  }

  /**
   * 发送Post请求（Json）
   */
  public static String sendPost(String url, JsonObject jsonObject) {

    String result = null;
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost post = new HttpPost(url);
    StringEntity stringEntity = new StringEntity(jsonObject.toString(),"utf-8");
    stringEntity.setContentType("UTF-8");
    stringEntity.setContentType("application/json");
    post.setEntity(stringEntity);
    try{
        HttpResponse httpResponse = httpClient.execute(post);
        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
          result = EntityUtils.toString(httpResponse.getEntity());
        }
    }catch(Exception e){
        e.printStackTrace();
    }finally{
      post.releaseConnection();
    }
    return result;
  }


  @Test
  public void sendPostJsonTest(){

    List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
    String url = "http://localhost:8080/json";
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("name","liushiyao");
    jsonObject.addProperty("age","123");
    String result = sendPost(url,jsonObject);
    System.out.println(result);

  }


  @Test
  public void sendGetTest() {
    String url = "http://localhost:8080/MyServlet";
    Map<String, String> params = new HashMap<String, String>();
    params.put("name", "liushiyao");
    params.put("age", "23");
    String result = sendGet(url, params);
    System.out.println(result);
  }

  @Test
  public void basicNameValuePairTest() {
    List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
    list.add(new BasicNameValuePair("name", "liushiyao"));
    list.add(new BasicNameValuePair("age", "22"));

    System.out.println(Arrays.toString(list.toArray()));
  }

  @Test
  public void sendPostTest(){
    List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
    String url = "http://localhost:8080/MyServlet";
    list.add(new BasicNameValuePair("name","liushiyao"));
    list.add(new BasicNameValuePair("age","23"));
    String result = sendPost(url,list);
    System.out.println(result);
  }
}
