package URLDemo;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLDemo {
    public static void main(String[] args) {
        String strUrl = "https://www.taobao.com/";
        List<String> strList = new ArrayList<>();
        Pattern pattern = Pattern.compile("<.*href=.+</a>");
        try {
            URL url = new URL(strUrl);
            java.net.URLConnection uConn = url.openConnection();
            InputStream is = uConn.getInputStream();
//            System.out.println(uConn.getContentEncoding());
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String lineText = null;
            while ((lineText = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(lineText);
                while (matcher.find()) {
//           Returns the input subsequence matched by the previous match.
                    String value = matcher.group(0);
                    strList.add(value);
                }
            }
            br.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String strings : strList) {
            System.out.println(strings);
        }
    }
}
