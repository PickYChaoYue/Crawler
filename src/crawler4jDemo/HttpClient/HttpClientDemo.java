package crawler4jDemo.HttpClient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientDemo {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.baidu.com");
        //添加头部信息模拟浏览器访问
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml," +
                "application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
//
        try {
//            客户端执行模拟浏览器的信息，并返回响应
            CloseableHttpResponse response = httpClient.execute(httpGet);
//            得到服务响应状态码
            if (response.getStatusLine().getStatusCode() == 200) {
                String entity = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(entity);
            } else {
                System.out.println("其他代码乱入.....");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
