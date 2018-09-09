package politics_crawler;

import org.apache.commons.dbutils.QueryRunner;

import java.io.IOException;
import java.sql.*;

public class ConnectionUtil {

    public static int insert(Politic politic) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = null;
        QueryRunner queryRunner = new QueryRunner();
        int i = 0;
        String sql = "insert into politic(publishedAt,source,title,content) values(?,?,?,?)";
//        PreparedStatement pstmt;
        try {
            conn=JDBCTools.getConnection();
            System.out.println(conn);
            i = queryRunner.execute(conn, sql, politic.getPublishedAt(), politic.getSource
                    (), politic.getTitle(), politic.getContent());

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
//            JDBCTools.release(null,null,conn);
        }
        return i;
    }

    public static int select(String title) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = null;
        int i = 0;
        String sql = "SELECT count(*) record FROM politic WHERE title=?";
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = JDBCTools.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, title);
            rs = pstmt.executeQuery();
            while(rs.next()){
                i = rs.getInt("record");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
//            JDBCTools.release(rs,pstmt,conn);
        }
        return i;
    }
}
