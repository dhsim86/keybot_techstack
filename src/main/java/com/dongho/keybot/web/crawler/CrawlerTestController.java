package com.dongho.keybot.web.crawler;

import com.dongho.keybot.infrastructure.crawler.crawler4j.MyCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerTestController {

    @Autowired
    private MyCrawler myCrawler;

    @GetMapping("/test/web-crawler")
    public void testWebCrawler() throws Exception {
        myCrawler.test();
    }

}
