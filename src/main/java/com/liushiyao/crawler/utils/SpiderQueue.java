package com.liushiyao.crawler.utils;


import java.util.HashSet;
import java.util.Set;

/**
 * @Author: liushiyao
 * @Description:  爬虫队列
 *     1.Visited表：已经访问过的URL列表，使用普通的HashSet集合即可
 *      2.unVisited表：未访问的URL列表，要使用Queue队列存储
 * @Date: Created Time 2017/11/10
 */

public class SpiderQueue {


  //存储Visited表
  private  Set<String> visitedUrl = new HashSet<String>();
  //存储unVisited表
  private  Queue<String> unVisitedURL = new Queue<String>();

  //添加至Visited表
  public  void addVisitedURL(String object){
    visitedUrl.add(object);
  }
  //删除一个Visited表
  public  void removeVisitedURL(String object){
    visitedUrl.remove(object);
  }
  //获取Visited表中的元素个数
  public  Integer getVisitedURLNum(){
    return visitedUrl.size();
  }

  //unVisited出列
  public String outUnVisitedURL(){
    return unVisitedURL.out();
  }

  //unVisited入列
  //visited表中不存在该元素，而且unVisited表中也不存在该元素
  public void inUnVisitedURL(String url){

    if(url != null && !url.trim().equals("") && !visitedUrl.contains(url)
        && !unVisitedURL.contains(url)){
      unVisitedURL.in(url);
    }
  }
  //unVisited是否为空？
  public boolean unVisitedIsEmpty(){
    return unVisitedURL.isEmpty();
  }

}
