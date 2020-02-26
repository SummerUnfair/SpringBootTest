//package com.worm;
//
//import com.sun.webkit.WebPage;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//
//import java.io.IOException;
//
//
//public class CrawlerThread {
//
//    private boolean fetchHtml(WebPage webPage) throws IOException {
//        Connection.Response response = Jsoup.connect(webPage.getUrl()).timeout(3000).execute();
//        webPage.setHtml(response.body());
//        return response.statusCode() / 100 == 2 ? true : false;
//    }
//
//    public static void main(String[] args) throws Exception {
//        WebPage playlists = new WebPage("http://music.163.com/#/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=0", PageType.playlists);
//        CrawlerThread crawlerThread = new CrawlerThread();
//        crawlerThread.fetchHtml(playlists);
//        System.out.println(playlists.getHtml());
//    }
//}
