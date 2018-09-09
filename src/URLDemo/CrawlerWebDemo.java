package URLDemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlerWebDemo {
    private static List<String> waitUrlList = new ArrayList<>();
    private static Set<String> overUrlSet = new HashSet<>();
    private static Map<String, Integer> allUrlDepthMap = new HashMap<>();
    private static int Max_Depth = 2;

    public static void main(String[] args) {
        String strUrl = "https://blog.csdn.net/laoyang360/article/details/81589459";
        crawlerUrl(strUrl, 1);
    }

    public static void crawlerUrl(String strUrl, int depth) {
            if (strUrl != null) {
                if (!(overUrlSet.contains(strUrl) || depth > Max_Depth)) {
                try {
                    URL url = new URL(strUrl);
                    URLConnection urlConnection = url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String textLine = null;
                    Pattern pattern = Pattern.compile("<.*href=.+</a>");
                    while ((textLine = br.readLine()) != null) {
                        Matcher matcher = pattern.matcher(textLine);
                        while (matcher.find()) {
                            String href = matcher.group(0);
                            href = href.substring(href.indexOf("href="));
                            if (href.charAt(5) == '\"') {
                                href = href.substring(6);
                            } else {
                                href = href.substring(5);
                            }
                            try {
                                href = href.substring(0, href.indexOf("\""));
                            } catch (Exception e) {
                                try {
                                    href = href.substring(0, href.indexOf(" "));
                                } catch (Exception e1) {
                                    href = href.substring(0, href.indexOf(">"));
                                }
                            }
                            if (href.startsWith("http:") || href.startsWith("https:")) {
                                waitUrlList.add(href);
                                allUrlDepthMap.put(href,depth+1);
                            }
                        }
                    }
                    br.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                overUrlSet.add(strUrl);
                String nextUrl = waitUrlList.get(0);
                    System.out.println(strUrl + "网页爬取完成,已爬取数量：" + overUrlSet.size() +
                            "," + "剩余爬取数量：" + waitUrlList.size()+"  "+waitUrlList.get(0));
                    System.out.println(nextUrl);

                    waitUrlList.remove(0);

                    crawlerUrl(nextUrl, allUrlDepthMap.get(nextUrl));
                }
            }


            }
    }

