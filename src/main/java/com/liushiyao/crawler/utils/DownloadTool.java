package com.liushiyao.crawler.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * @Author: liushiyao
 * @Description: 下载工具类
 * @Date: Created Time 2017/11/10
 */
public class DownloadTool {





  /**
   * @param url html的URL
   * @param path 保存的路径
   * @param name 命名前缀
   */
  public static void downloadHtml(String url, String name, String path) {

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(url);
    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(50000)
        .setSocketTimeout(50000)
        .setConnectionRequestTimeout(5000)
        .build();
    httpGet.setConfig(requestConfig);
    try {
      CloseableHttpResponse response = httpClient.execute(httpGet);
      if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
        return;
      }
      String result = EntityUtils.toString(response.getEntity(), "utf-8");
      saveStrFileLocal(result, name, path);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      httpGet.releaseConnection();
    }


  }

  @Test
  public void downloadHtmlTest() {
    downloadHtml("https://www.baidu.com", "baidu2.html", "/test");
  }

  /**
   * 将字符串以文件的形式存储在本地
   *
   * @param data 字符串数据
   * @param name 文件名.文件格式
   * @param path 文件存储路径
   */
  public static void saveStrFileLocal(String data, String name, String path) {

    BufferedWriter bufferedWriter = null;
    try {
      bufferedWriter = new BufferedWriter(new FileWriter(new File(path + "/" + name)));
      bufferedWriter.write(data);
      bufferedWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bufferedWriter != null) {
          bufferedWriter.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void saveStrFileTest() {
    saveStrFileLocal("六安市地方hi舒服哈搜凤凰网东方", "temp.html", "/test");
  }


}
