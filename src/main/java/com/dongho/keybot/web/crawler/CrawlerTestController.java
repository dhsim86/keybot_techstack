package com.dongho.keybot.web.crawler;

import com.dongho.keybot.infrastructure.crawler.crawler4j.MyCrawler;
import com.dongho.keybot.infrastructure.search.google.GoogleSearchCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerTestController {

    @Autowired
    private MyCrawler myCrawler;

    @Autowired
    private GoogleSearchCrawler searchCrawler;

    @GetMapping("/test/web-crawler")
    public void testWebCrawler() throws Exception {

    }

    @GetMapping("/test/search-crawler")
    public void searchCrawler(@RequestParam("keyword") String keyword,
                              @RequestParam("offset") int offset,
                              @RequestParam("limit") int limit) throws Exception{
        searchCrawler.google_results(keyword, offset, limit);
    }

}
