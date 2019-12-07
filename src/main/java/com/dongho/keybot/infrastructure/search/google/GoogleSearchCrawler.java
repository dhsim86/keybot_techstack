package com.dongho.keybot.infrastructure.search.google;

import com.dongho.keybot.infrastructure.crawler.crawler4j.MyCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleSearchCrawler {

    @Autowired
    private MyCrawler myCrawler;

    public void google_results(String keyword, int offset, int limit) throws Exception {
//Replace space by + in the keyword as in the google search url
        keyword = keyword.replace(" ", "+");
        String url = "https://www.google.com/search?q=" + keyword + "&start=" + String.valueOf(offset) +"&num=" + String.valueOf(limit);
//Connect to the url and obain HTML response
        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .get();
//parsing HTML after examining DOM
        Elements els = doc.select(".rc .r");

        List<String> links = new ArrayList<>();

        for (Element el : els) {
//Print title, site and abstract
            System.out.println("Title : " + el.getElementsByTag("h3").text());

            System.out.println("Site : " + el.getElementsByTag("a").first().attr("href"));
            links.add(el.getElementsByTag("a").first().attr("href"));

            System.out.println("Abstract : " + el.getElementsByTag("span").text() + "\n");
        }

        System.out.println("----------------------------------------------");
        System.out.println("Crawl links");
        myCrawler.test(links);
    }

}
