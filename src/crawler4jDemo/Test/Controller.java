package crawler4jDemo.Test;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 1;

        CrawlConfig config = new CrawlConfig();
//        config.setFollowRedirects(false);
        config.setCrawlStorageFolder(crawlStorageFolder);
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        String url = "http://tieba.baidu.com/hottopic/browse/hottopic?topic_id=255054&topic_name=%E6%BB%B4%E6%BB%B4%E4%BB%8A%E8%B5%B7%E5%85%A8%E7%A8%8B%E5%BD%95%E9%9F%B3";
        controller.addSeed(url);
        controller.start(MyCrawler.class,numberOfCrawlers);
    }
}
