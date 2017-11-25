package com.liushiyao.reptile.technology.dom;

//import com.liushiyao.common.utils.FileUtils;

import com.liushiyao.crawler.utils.FileUtils;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*

  DOM相关功能练习

 */
public class DOMDemo {


  /**
   * 解析xml文件,获取节点
   *
   * @param node 节点
   * @param path 文件路径
   */
  public static void getNode(String node, String path) {

    try {
      //1.解析器工厂类：DocumentBuilderFactory
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      //2.解析器：DocumentBuilder
      DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
      //3.文档树模型Document
      Document document = documentBuilder.parse(path);
      NodeList nodeList = (NodeList) document.getElementsByTagName(node);
      for (int i = 0; i < nodeList.getLength(); i++) {
        Element element = (Element) nodeList.item(i);
        System.out.println(
            "Name: " + element.getElementsByTagName("Name").item(0).getFirstChild()
                .getNodeValue());
        System.out.println(
            "Num: " + element.getElementsByTagName("Num").item(0).getFirstChild()
                .getNodeValue());
        System.out.println(
            "Classes: " + element.getElementsByTagName("Classes").item(0).getFirstChild()
                .getNodeValue());
        System.out.println(
            "Address: " + element.getElementsByTagName("Address").item(0).getFirstChild()
                .getNodeValue());
        System.out.println(
            "Tel: " + element.getElementsByTagName("Tel").item(0).getFirstChild()
                .getNodeValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 从HTML中解析出链接
   */
  public static void getLIinkInHtml(String path){

    try{
        //解析器工厂类
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = builderFactory.newDocumentBuilder();
      Document document = builder.parse(path);
      NodeList nodeList = document.getElementsByTagName("a");
      for (int i = 0; i < nodeList.getLength(); i++) {
        Element element = (Element) nodeList.item(i);
        System.out.println(element.getAttribute("href"));
      }
    }catch(Exception e){
        e.printStackTrace();
    }

  }

  /**
   * 获取HTML文件中的某个节点属性
   * 使用Jsoup解析HTML文件
   * @param node
   * @return
   */
  public List<String> getNodeAttr(String node,String attr,String path){

    List<String> list = new ArrayList<String>();
    String src = FileUtils.fileReader(path);
    org.jsoup.nodes.Document document = Jsoup.parse(src);
    Elements elements = document.getElementsByTag(node);
    for (int i = 0; i < elements.size(); i++) {
      list.add(elements.get(i).getAllElements().attr(attr));
    }
    return list;
  }


  public static void main(String[] a) {

    getNode("Student",
        "/home/liushiyao/MyCode/IDEA/whiletrue/reptile/src/main/resources/file/name.xml");

  }


}
