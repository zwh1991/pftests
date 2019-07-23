package com.fileio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * . 与数据库相连接
 * 
 * @author 
 */
public final class DBPgSql {
    /** . 私有构造方法 */
    public DBPgSql() {
    }

    static {
        PropertyConfigurator.configure("Log4j.properties");
    }
    private static Logger logger = Logger.getLogger(DBPgSql.class.getName());
    private static Connection con;
    private static String mDBName;
    private static String muser;
    private static String mpassword;

    /**
     * . 连接数据库
     */
    public static void connectDB(
//    		String mDBName, String muser, 
//    		String mpassword, String url
    		) {
        mDBName = "mydb";
        muser = "tshow";
        mpassword = "tshow2014";
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://pg1.kktv2.com:6531/tshow";

        try {
        	Class.forName("org.postgresql.Driver");
        	con = DriverManager
              .getConnection(url, muser, mpassword);
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    /**.
     * 执行sql语句 .
     * @param sql 数据库执行命令
     */
    public static String excute(final String sql, final String key) {
    	ResultSet resultSet = null;
        if (con == null) {
            return null;
        }
        try {
            Statement smt = con.createStatement();
            /**
             * 关于ResultSet的理解：Java程序中数据库查询结果的展现形式，或者说得到了一个结果集的表
             * 在文档的开始部分有详细的讲解该接口中应该注意的问题，请阅读JDK
             * */ 
            //执行查询语句，将数据保存到ResultSet对象中  
            try {
            	resultSet = smt.executeQuery(sql);
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            //将指针移到下一行，判断rs中是否有数据
            try {
            	//输出返回结果  
				if (key.length() > 0){
					if(resultSet.next()){ 
	    			    return resultSet.getString(key);
	    			}else{  
	    			    //输出查询结果  
	    			    System.out.println("未查询到信息");
	    			    return null;
	    			}
				}else{
					return null;
				}
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            try {
                if (con.isClosed()) {
                    System.out.println("con is closed");
                }
                connectDB();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
		return null;
    }
    
    public static void main(String[] args) throws SQLException{
    	connectDB();
    	System.err.println(excute("SELECT * FROM res_user_virtualid WHERE user_id = 7527654 AND luck_id = 494819", "end_time"));
    } 
}
