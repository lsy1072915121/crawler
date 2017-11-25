package com.liushiyao.reptile.bfs;

import java.util.Set;


/**
 * 宽度优先遍历
 * 目的：使用宽度优先遍历，爬取某个种子URL的相关的URL
 *
 *  图的宽度优先遍历：图的宽度优先遍历（BFS）算法是一个分层搜索的过程，和树的层序遍历算法相同。
 *      再图中选取一个节点，作为起始节点，然后按照层次遍历的方式，一层一层地进行访问。
 *
 *  图的深度优先遍历：与宽度优先遍历不同，深度优先遍历是把某一个节点进行深度遍历，等遍历所有元素后在遍历其他节点。
 *
 *
 * 组成：
 *  1.TODO表
 *      TODO表用于存储待遍历的URL.通过宽度优先遍历的原理可知，TODO表应该采取队列的数据结构，即使用“先进先出”的存储方式
 *      所有定义了一个Queue类来存储TODO表
 *  2.Visited表
 *      Visited表用于存放已经访问的URL，在宽度优先遍历中，需要遍历Visited表中的数据，所有Visited表应该采用便于查询的数据结构，
 *      所有使用Set集合来存储该表
 * 具体实现：把网络看成一个巨大的树结构，从一个种子节点出发，解析出链接，与Visited表中的内容对比，把新的链接存入TODO表中，然后遍历解析，从
 *      TODO表中获取URL。重复以上操作，最终获取种子URL相关的链接。
 *
 *
 *
 */

public class BfsSpider {


    private static final int URL_MAX_SIZE = 1000;

    /**
     * 使用种子初始化URL队列
     */
    private void initCrawlerWithSeeds ( String[] seeds ) {
        for ( int i = 0 ; i < seeds.length ; i++ )
            SpiderQueue.addUnvisitedUrl ( seeds[ i ] );
    }

    // 定义过滤器，提取以 http://www.xxxx.com开头的链接
    public void crawling ( String[] seeds ) {
        LinkFilter filter = new LinkFilter ( ) {
            public boolean accept ( String url ) {
                if ( url.startsWith ( "http://www.baidu.com" ) )
                    return true;
                else
                    return false;
            }
        };
        // 初始化 URL 队列
        initCrawlerWithSeeds ( seeds );
        // 循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while ( ! SpiderQueue.unVisitedUrlsEmpty ( )
                && SpiderQueue.getVisitedUrlNum ( ) <= URL_MAX_SIZE ) {
            // 队头 URL 出队列
            String visitUrl = ( String ) SpiderQueue.unVisitedUrlDeQueue ( );
            if ( visitUrl == null )
                continue;
            DownTool downLoader = new DownTool ( );
            // 下载网页
            System.out.println (visitUrl );
            downLoader.downloadFile ( visitUrl );
            // 该 URL 放入已访问的 URL 中
            SpiderQueue.addVisitedUrl ( visitUrl );
            // 提取出下载网页中的 URL
            Set <String> links = HtmlParserTool.extracLinks ( visitUrl , filter );
            // 新的未访问的 URL 入队
            for ( String link : links ) {
                SpiderQueue.addUnvisitedUrl ( link );
            }
        }
    }

    // main 方法入口
    public static void main ( String[] args ) {
        BfsSpider crawler = new BfsSpider ( );
        crawler.crawling ( new String[] { "http://www.baidu.com" } );
    }
}