package com.liushiyao.crawler.utils;

import org.junit.Test;

/**
 * @author: liushiyao
 * @description:
 * @date: Created Time 2017/11/11
 */
public class StringUtils {

  /**
   * 获取URL存储文件名称 以URL为命名词根，去掉特殊字符，contentType添加在后面
   *
   * @param url URL
   * @param contentType 文件类型
   */
  public static String getFileNameByURL(String url, String contentType) {

    //除去"http://"这七个字符
    url = url.substring(7);
    //文件类型判断捕捉HTML文件
    if (contentType.indexOf("html") != -1) {
      url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
    } else {
      url = url.replaceAll("[\\?/:*|<>\"]", "_") + "."
          + contentType.substring(contentType.lastIndexOf("/") + 1);
    }
    return url;
  }

  @Test
  public void getFIleNameByURLTest() {
    String name = getFileNameByURL("http://www.baid.com/login/abc", "text/html");
    System.out.println(name);
  }

}
