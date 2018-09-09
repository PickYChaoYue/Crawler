package crawler4jDemo.HttpClient;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class TestHttpClient {
    public static void main(String[] args) throws IOException {
//        创建默认的客户端实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        创建get请求实例
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        System.out.println("executing request:"+httpGet.getURI());
        try {
//            客户端执行get请求，返回响应
            CloseableHttpResponse response = httpClient.execute(httpGet);
//          服务器响应状态行
            System.out.println(response.getStatusLine().toString());
            Header[] headers = response.getAllHeaders();
            System.out.println(response.getHeaders("Content-Type").toString());
//            打印所有响应头
            for (Header header : headers) {
                System.out.println(header.getName()+"  "+header.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
    }
}
