package politics_crawler;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCTools {
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
//        读取配置文件
        InputStream inputStream = JDBCTools.class.getClassLoader().getResourceAsStream
                ("politics_crawler/jdbc.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String driverClass = properties.getProperty("driver");
        String jdbcUrl = properties.getProperty("jdbcUrl");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
//        加载数据库驱动程序
        Class.forName(driverClass);
//        通过DriverManager获取数据库连接
        Connection connection =
                DriverManager.getConnection(jdbcUrl, user, password);
        return connection;
    }

    public static void release(ResultSet resultSet, Statement statement, Connection
            connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
