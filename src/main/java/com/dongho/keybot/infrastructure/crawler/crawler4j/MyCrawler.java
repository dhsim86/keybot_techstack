package com.dongho.keybot.infrastructure.crawler.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Set;
import java.util.regex.Pattern;

@Component
public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");
    private final static Pattern GOOGLE_FILTERS = Pattern.compile("(http:\\/\\/|https:\\/\\/)?([a-z\\.]*)?google\\S*");

    public void test() throws Exception {
        String crawlStorageFolder = "D:\\workspace\\tmp";
        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setMaxDepthOfCrawling(2);
        config.setCrawlStorageFolder(crawlStorageFolder);

        // Instantiate the controller for this crawl.
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);

        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        // For each crawl, you need to add some seed urls. These are the first
        // URLs that are fetched and then the crawler starts following links
        // which are found in these pages
        //controller.addSeed("https://dhsim86.github.io/web/");
        //controller.addSeed("https://dhsim86.github.io/blog/");
        controller.addSeed("https://www.google.com/search?q=스프링");
        controller.addSeed("https://www.google.com/search?q=스프링&start=10");

        // The factory which creates instances of crawlers.
        CrawlController.WebCrawlerFactory<WebCrawler> factory = MyCrawler::new;

        // Start the crawl. This is a blocking operation, meaning that your code
        // will reach the line after this only when crawling is finished.
        controller.start(factory, numberOfCrawlers);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        AntPathMatcher pathMatcher = new AntPathMatcher();

        return !FILTERS.matcher(href).matches()
                && !GOOGLE_FILTERS.matcher(href).matches();
                //&& href.startsWith("https://dhsim86.github.io/");
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("META TAG: " + htmlParseData.getMetaTags());
            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());
        }
    }
}
