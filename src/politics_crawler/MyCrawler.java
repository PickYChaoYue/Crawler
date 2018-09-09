package politics_crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.crawler.exceptions.ParseException;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class MyCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");

    private final static String URL = "http://politics.people.com.cn";

    /**
     * 这方法决定了要抓取的URL及其内容
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.contains(URL+"/n1");
    }

    /**
     * 当URL下载完成会调用这个方法，可以获取下载页面的URL，文本，链接，HTML和唯一ID等内容
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Document doc = Jsoup.parse(html);
            List<String> list = new ArrayList<String>();
            Element element1 = doc.select("[h1.clear,red]").first();
            System.out.println(element1);
//            return null;
            Elements elements1 = element1.getElementsByAttribute("href");
            list.add(elements1.attr("href"));//获取链接属性的值
            Element element2 = doc.select("[div.center,fr]").first();
            Elements elements2 = element2.getElementsByAttribute("href");
            for (int i =0;i<9;i++) {
                list.add(elements2.get(i).attr("href"));//获取链接属性的值
            }
            try {
                for (int j = 0;j<list.size();j++) {
                    Politic politic = new Politic();
                    Document document = Jsoup.connect(URL+list.get(j)).get();//爬取此时的URL数据
                    Elements elements3 = document.select("#rwb_zw");
                    politic.setContent(elements3.select("p").text());//内容
                    Elements elements4 = document.select("div.clearfix,w1000_320,text_title");
                    politic.setTitle(elements4.select("h1").text());//标题
                    String str = elements4.select("div.box01 .fl").text();
                    //String publishedAt = str.substring(0,4)+"-"+str.substring(5,7)+"-"+str.substring(8,10)+" "+str.substring(11,16);//时间格式
                    String publishedAt = str.substring(0, 17);
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日HH:mm"); //定义时间格式
                    Date date = sf.parse(publishedAt);  //转换成date类型
                    politic.setPublishedAt(new Timestamp(date.getTime()));//时间 ：//date类型转换成Timestamp类型
                    politic.setSource(str.substring(21));//来源
                    if(ConnectionUtil.select(politic.getTitle()) == 0){//判断title是否重复:若为0(不重复)则插入数据
                        int  i = ConnectionUtil.insert(politic);
                        if(i == 0){//判断是否插入成功
                            throw new Exception("新增时政要闻资料失败！");
                        }
                    }else{
                        System.out.println("资料已经存在！");
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

