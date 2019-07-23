package com.fileio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * . 与数据库相连接
 * 
 * @author cln8596
 */
public final class DBMYSQL {
    /** . 私有构造方法 */
    private DBMYSQL() {
    }

    static {
        PropertyConfigurator.configure("Log4j.properties");
    }
    private static Logger logger = Logger.getLogger(DBMYSQL.class.getName());
    private static Connection con;
    private static String mDBName;
    private static String muser;
    private static String mpassword;

    /**
     * . 连接数据库
     * 
     * @param dbName
     *            数据库名
     * @param user
     *            用户名
     * @param password
     *            密码
     * @return boolean
     */
    public static boolean connectDB(final String dbName, final String user,
            final String password) {
        mDBName = dbName;
        muser = user;
        mpassword = password;
        String driver = "com.mysql.jdbc.Driver"; // "com.mysql.jdbc.Driver"
        String url = "jdbc:mysql:" + dbName;

        try {
            Class.forName(driver);
        } catch (Exception e) {
            logger.error("driver fail:" + driver);
            return false;
            // System.out.print("driver fail:" + driver);
        }

        try {
            con = DriverManager.getConnection(url + "?user=" + user
                    + "&password=" + password
                    + "&useUnicode=true&characterEncoding=GBK");
            if (!con.isClosed()) {
                logger.info("connected with DB");
                return true;
            }
        } catch (SQLException e) {
            logger.error(e.toString());
            return false;
        }
        return true;
    }

    /**.
     * 执行sql语句 .
     * @param sql 数据库执行命令
     */
    public static void excute(final String sql) {
        if (con == null) {
            return;
        }
        try {
            Statement smt = con.createStatement();
            smt.execute(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            try {
                if (con.isClosed()) {
                    System.out.println("con is closed");
                }
                connectDB(mDBName, muser, mpassword);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
