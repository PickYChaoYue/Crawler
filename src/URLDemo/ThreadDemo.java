package URLDemo;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadDemo {
    private static List<String> waitUrl = new ArrayList<>();
    private static Set<String> allOverUrl = new HashSet<>();
    private static Map<String, Integer> allUrlDepth = new HashMap<>();
    private static int max_depth = 2;
    private static int max_thread = 5;
    private static int count = 0;
    private static Object object = new Object();
    public static void main(String[] args) {
        String strUrl = "https://www.baidu.com/";
        addUrl(strUrl,0);
        for (int i = 0; i < max_thread; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (waitUrl.size() > 0) {
                            String url = getUrl();
                            workUrl(url, allUrlDepth.get(url));
                        } else {
                            System.out.println("当前线程等待爬取。。。");
                            count++;
                            synchronized (object) {
                                try {
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            count--;
                        }
                    }
                }
            }).start();
        }


    }

    public static void workUrl(String strUrl, int depth) {
        if (!(allOverUrl.contains(strUrl) || depth > max_depth)) {
            try{
                URL url = new URL(strUrl);
                URLConnection uConn = url.openConnection();
                InputStream is = uConn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String lineText = null;
                Pattern pattern = Pattern.compile("<a.*href=.+</a>");
                while ((lineText = br.readLine()) != null) {
                    Matcher matcher = pattern.matcher(lineText);
                    while (matcher.find()) {
                        String href = matcher.group();
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
                                href = href.substring(0, href.indexOf(">"));
                            } catch (Exception e1) {
                                href = href.substring(0, href.indexOf(" "));
                            }
                        }
                        addUrl(href,depth);
                    }
                }
                br.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            allOverUrl.add(strUrl);
        }
        if (waitUrl.size() > 0) {
            synchronized (object) {
                object.notify();
            }
        } else {
            System.out.println("爬取结束。。。");
        }

    }

    public static synchronized void addUrl(String href, int depth) {
        waitUrl.add(href);
        if (!allUrlDepth.containsKey(href)) {
            allUrlDepth.put(href, depth + 1);
        }
    }

    public static synchronized String getUrl() {
        String nextUrl = waitUrl.get(0);
        waitUrl.remove(0);
        return nextUrl;
    }
}
