package crawler4jDemo.HttpClient;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpClientDemo21 {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.baidu.com");

        httpGet.setHeader("Accept","text/html,application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding","gzip, deflate, br");
        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        String cookie ="BAIDUID=A5C8B903252880804AF2B9E2B0B5B748:FG=1; " +
                "BIDUPSID=A5C8B903252880804AF2B9E2B0B5B748; PSTM=1536377429; BDRCVFR[Fc9oatPmwxn]=G01CoNuskzfuh-zuyuEXAPCpy49QhP8; H_PS_PSSID=1435_26909_21103_22075; HOSUPPORT=1; UBI=fi_PncwhpxZ%7ETaKAYfhPCNkmUfd-Wgqz3c6ym1Rz6GDU80qqheMzOoOJCHdfWbG7jgenq90jkToruACNuxw; HISTORY=29f794df44b911c88484e221ca2768c16f1f4a; USERNAMETYPE=3; SAVEUSERID=8a3e0963a6b1c3de60d980d03dde59; DVID=1536389462755%7C90533c11-b53a-4fc4-a927-34f43975fed3; PSINO=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; STOKEN=a3ec7d841b2994285e10f84f0854b66477eccb72e7f42346f744f136f2d97bad; BDUSS=mFvVHF6YU5naTVYRUVCNThiVU5lc2Ntc3o5QmpBUEc1amFKb3pVeWNySX5DTHRiQVFBQUFBJCQAAAAAAAAAAAEAAAAIc2OlV0FOR1hpYW9fVGFpAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD97k1s~e5NbZ; PTOKEN=a0828035fd494f09ac14427132d0c759";
        httpGet.setHeader("Cookie",cookie);
        httpGet.setHeader("User-Agent","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                    System.out.println(header.getName()+"  "+header.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClient.close();
        }
    }
}
