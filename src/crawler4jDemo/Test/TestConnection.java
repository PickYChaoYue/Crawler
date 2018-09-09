package crawler4jDemo.Test;

import politics_crawler.JDBCTools;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) throws Exception {

        Connection connection = JDBCTools.getConnection();
        System.out.println(connection);
    }
}
