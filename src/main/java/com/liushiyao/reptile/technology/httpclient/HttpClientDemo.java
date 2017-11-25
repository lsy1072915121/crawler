package com.liushiyao.reptile.technology.httpclient;


import com.google.gson.Gson;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * Apache HttpClient的使用
 *
 *
 */
public class HttpClientDemo {


  /**
   * 模拟发送GET请求
   */
  public static void httpClientGetDemo(){

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet("http://www.while-true.cn/wechat/wxController");
    try {
      HttpResponse httpResponse = httpClient.execute(httpGet);
      System.out.println(httpResponse.getStatusLine().getStatusCode());
      if(httpResponse.getStatusLine().getStatusCode() == 200){
        String result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
        System.out.println(result);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      httpGet.releaseConnection();
    }

  }

  /**
   * 模拟发送Post请求
   * 使用post请求，设置参数。获取返回结果
   * 1.添加参数
   *  使用List，添加NameValuePair对象，插入BasicNameValuePair对象
   */
  public static void httpClientPostDemo(){

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost httpPost = new HttpPost("http://localhost:8081/crawler");
    List<NameValuePair> list = new ArrayList<NameValuePair>();
    list.add(new BasicNameValuePair("data","123123"));
    try {
      httpPost.setEntity(new UrlEncodedFormEntity(list));
      HttpResponse httpResponse = httpClient.execute(httpPost);
      if(httpResponse.getStatusLine().getStatusCode() == 200){
        String result = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(result);
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * 模拟发送Json数据
   * 使用Post请求，设置json。获取返回结果
   * 1.添加Json对象,并转成json
   * 2.创建StringEntity，设置StringEntity Content类型为json
   * 3.设置Post请求的Entity
   */
  public static void httpClientJsonDemo(){

    User user = new User();
    user.setAge(22);
    user.setName("刘石尧");
    user.setNickName("北岭山下");
    String json = new Gson().toJson(user);
    StringEntity stringEntity = new StringEntity(json,"utf-8");
    stringEntity.setContentType("UTF-8");
    stringEntity.setContentType("application/json");
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpPost httpPost = new HttpPost("http://localhost:8081/crawler/json");

    httpPost.setEntity(stringEntity);

    try {
      HttpResponse httpResponse = httpClient.execute(httpPost);
      if (httpResponse.getStatusLine().getStatusCode() == 200){
        String reuslt  = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(reuslt);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 配置HttpClient连接参数
   * 1.设置请求的超时时间（目的是为了防止访问其他http时，由于超时导致自己的应用受影响。）
   *
   * 注：时间超时参数设置已经抽象到RequestConfig中，然后在HttpGet中设置Config
   *    使用CloseableHttpResponse，自动关闭功能，所以不用捕捉异常
   *
   *
   */
  public static void HttpClientPramDemo() throws IOException {

    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet("http://localhost:8081/crawler?data=123");
    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectionRequestTimeout(5000)
        .setSocketTimeout(5000)
        .setConnectTimeout(5000)
        .build();

    httpGet.setConfig(requestConfig);
    CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(httpGet);
    if(response.getStatusLine().getStatusCode() == 200){

      String restul = EntityUtils.toString(response.getEntity());
      System.out.println(restul);

    }

  }

  public static void main(String []a) throws IOException {

    HttpClientPramDemo();


  }


}
