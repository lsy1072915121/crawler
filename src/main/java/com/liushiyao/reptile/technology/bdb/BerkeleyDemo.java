package com.liushiyao.reptile.technology.bdb;


import java.util.ArrayList;

/**
 * Berkeley BD
 * 是一种嵌入式数据库，适用于管理海量的，简单的数据。
 * （1）key/value是Berkeley DB用来管理数据的基础，每个key/value对代表一条记录
 * （2）Berkeley DB 是在底层采用B树。可以看成能够存储大量数据的HashMap。
 * （3）嵌入式：它直接连接到应用程序中，与应用程序运行于同样的地址空间中，
 * 因此无论是在网络上不同的计算机之间还是同一台计算机的不同进程之间，数据库操作并不要求进程间的通信。
 *
 *
 *
 *
 */
public class BerkeleyDemo  {

  public static void main(String []a){

    BerkeleyDBUtil berkeleyDBUtil  = new
        BerkeleyDBUtil("/home/liushiyao/MyCode/IDEA/whiletrue/crawl");

//    String name = berkeleyDBUtil.readFromDatabase("ba");
   /* for (int i = 0; i < 100; i++) {
      berkeleyDBUtil.writeToDatabase("k"+i,"liushiyao"+i,false);
    }*/
    ArrayList<String > list = berkeleyDBUtil.getEveryItem();
    for(String string : list){
      System.out.println(string );
    }

  }

}
