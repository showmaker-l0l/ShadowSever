package db;

import java.sql.*;

public class DBUtil {
    // table
    public static final String TABLE_USER = "user";
    public static final String TABLE_MESSAGE = "message";
    private static Connection mConnection;

    public static Connection getConnect() {
        String url="jdbc:mysql://localhost:3306/shadow?&useSSL=false&serverTimezone=UTC"; // 数据库的Url
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // java反射，固定写法
            mConnection = DriverManager.getConnection(url, "root", "1997");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return mConnection;
    }

    /**
     * 查询操作
     *
     * @param querySql
     *            查询操作SQL语句
     * @return 查询
     * @throws SQLException
     */
    public static ResultSet query(String querySql) throws SQLException {
        Statement stateMent = (Statement) getConnect().createStatement();
        return stateMent.executeQuery(querySql);
    }

    /**
     * 插入、更新、删除操作
     *
     * @param insertSql
     *            插入操作的SQL语句
     * @return
     * @throws SQLException
     */
    public static int update(String insertSql) throws SQLException {
        Statement stateMent = (Statement) getConnect().createStatement();
        return stateMent.executeUpdate(insertSql);
    }


    public static void closeConnection() {
        if (mConnection != null) {
            try {
                mConnection.close();
                mConnection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
