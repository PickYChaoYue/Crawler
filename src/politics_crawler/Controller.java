package politics_crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/data/crawl/root";//保存爬取数据的目录
        int numberOfCrawlers = 5; //同时开启线程数(并发线程数)
        CrawlConfig config = new CrawlConfig(); //在start之前进行一些配置，爬取速度优化
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(1); //种子爬取深度
        config.setMaxPagesToFetch(1); //最多能爬取的页面
        PageFetcher pageFetcher = new PageFetcher(config);//从网上提取内容
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();//robots协议类
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);//读取robots协议，获取是否可以抓取的信息
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);//爬行控制器   监听各个爬虫的运行状态并向URL队列添加新的URL
        controller.addSeed("http://politics.people.com.cn/");//添加种子URL的方法
        controller.start(MyCrawler.class, numberOfCrawlers);//开启爬虫的方法
    }
}
