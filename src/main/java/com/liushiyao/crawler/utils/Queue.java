package com.liushiyao.crawler.utils;


import java.util.LinkedList;
import org.junit.Test;

/**
 * @author: liushiyao
 * @description: 使用LinkedList创建队列
 * @date: Created Time 2017/11/10
 */

public class Queue<T> {

  private LinkedList<T> linkedList;

  public Queue() {
    this.linkedList = new LinkedList<T>();
  }

  /**
   * 添加入队列
   * @param element
   */
  public void in(T element){
    linkedList.addLast(element);
  }

  /**
   * 出列
   * @return
   */
  public T out(){
    return linkedList.removeFirst();
  }

  /**
   * 判断空
   * @return
   */
  public boolean isEmpty(){
    return linkedList.isEmpty();
  }

  /**
   * 判断是否存在该元素
   * @param obj
   * @return
   */
  public boolean contains(T obj){
    return linkedList.contains(obj);
  }

  /**
   * 队列长度
   * @return
   */
  public Integer height(){
    return linkedList.size();
  }

  @Test
  public void queueTest(){

    Queue<Integer > queue = new Queue<Integer>();
    for (int i = 0; i < 100; i++) {
      queue.in(i);
    }
    System.out.println("队列中时候包含“50”：？"+queue.contains(50));
    for (int i = 0; i < 50; i++) {
      queue.out();
    }
    for (int i =queue.height(); i > 0 ; i--) {
      System.out.println(queue.out());
    }

  }
}
