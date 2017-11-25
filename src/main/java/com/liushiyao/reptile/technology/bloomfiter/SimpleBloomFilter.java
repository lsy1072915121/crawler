package com.liushiyao.reptile.technology.bloomfiter;

import com.liushiyao.reptile.CrawlUrl;
import java.util.BitSet;

/**
 * 布隆过滤器算法（BloomFilter） 用于判断一个元素是否存在于一个集合中
 */
public class SimpleBloomFilter implements VisitedFrontier {


  private static final int DEFAULT_SIZE = 2 << 24;
  private static final int[] seeds = new int[]{7, 11, 13, 31, 37, 61,};
  private SimpleHash[] func = new SimpleHash[seeds.length];
  private BitSet bitSet = new BitSet(DEFAULT_SIZE);//  大小可动态改变, 取值为true或false的位集合。用于表示一组布尔标志。

  public SimpleBloomFilter() {
    for (int i = 0; i < seeds.length; i++) {
      func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
    }
  }


  @Override
  public void add(CrawlUrl value) {
    if (value != null) {
      add(value.getOriUrl());
    }
  }



  @Override
  public void add(String value) {
    for (SimpleHash f : func) {
      bitSet.set(f.hash(value), true);
    }
  }

  @Override
  public boolean contains(CrawlUrl value) {
    return contains(value.getOriUrl());
  }

  @Override
  public boolean contains(String value) {
    if (value == null) {
      return false;
    }
    boolean ret = true;
    for (SimpleHash f : func) {
      ret = ret && bitSet.get(f.hash(value));
    }
    return ret;
  }


  public static void main(String[] a) {

    String value = "1072915121@qq.com";
    SimpleBloomFilter simpleBloomFilter = new SimpleBloomFilter();
    System.out.println(simpleBloomFilter.contains(value));
    simpleBloomFilter.add(value);
    System.out.println(simpleBloomFilter.contains(value));

  }

public static class SimpleHash {

  private int cap;
  private int seed;

  public SimpleHash(int cap, int seed) {
    this.cap = cap;
    this.seed = seed;
  }

  public int hash(String value) {
    int result = 0;
    int len = value.length();
    for (int i = 0; i < len; i++) {
      result = seed * result + value.charAt(i);
    }
    return (cap - 1) & result;
  }

}


}
