package com.liushiyao.reptile.technology.bloomfiter;

import com.liushiyao.reptile.CrawlUrl;

public interface VisitedFrontier {

  void add(CrawlUrl value);
  void add(String value);
  boolean contains(CrawlUrl value);
  boolean contains(String value);


}
