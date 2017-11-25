package com.liushiyao.crawler.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {


  /**
   * 获取文本信息
   *
   * @param path 文件路径
   */
  public static String fileReader(String path) {

    String result = "";
    BufferedReader bufferedReader = null;
    try {
      bufferedReader = new BufferedReader(new FileReader(path));
      String string;
      while ((string = bufferedReader.readLine()) != null) {
        result += string;
      }
    } catch (FileNotFoundException e) {
      System.out.println("文件不存在");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }

  /**
   * 获取文本信息
   *
   * @param file 文件对象
   */
  public static String fileReader(File file) {
    BufferedReader bufferedReader = null;
    String result = "";
    try {

      bufferedReader = new BufferedReader(new FileReader(file));
      String string;
      while ((string = bufferedReader.readLine()) != null) {
        result += string;
      }
    } catch (FileNotFoundException e) {
      System.out.println("文件不存在");
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }

}
