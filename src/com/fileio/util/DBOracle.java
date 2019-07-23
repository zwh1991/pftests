package com.fileio.util;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
  
public class DBOracle{
	//数据库连接对象  
    public static Connection conn = null; 
  
    public static String exctQuery(String sql, String key){
    	PreparedStatement pstmt = null;  
        ResultSet rs = null;  

        //创建该连接下的PreparedStatement对象  
        try {
			pstmt = conn.prepareStatement(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
          
        //执行查询语句，将数据保存到ResultSet对象中  
        try {
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //将指针移到下一行，判断rs中是否有数据  
        try {
			if(rs.next()){ 
			    //输出返回结果  
				if (key.length() > 0){
					return rs.getString(key);
				}else{
					return null;
				}
				
			}else{  
			    //输出查询结果  
			    System.out.println("未查询到信息");
			    return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
    }
    
    public static Connection connect(){
    	//设定数据库驱动，数据库连接地址、端口、名称，用户名，密码  
        String driverName = "oracle.jdbc.driver.OracleDriver";  
        String url = "jdbc:oracle:thin:@oramst.kktv2.com:1521:tshow";  //test为数据库名称，1521为连接数据库的默认端口  
        String user = "tshowtest";   //aa为用户名  
        String password = "tshowpwd";  //123为密码  

        //反射Oracle数据库驱动程序类  
        try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return conn;
    }
      
    public static void main(String[] args) throws SQLException{
    	Connection conn = connect();
    	System.err.println(exctQuery("SELECT * FROM USER_PROP WHERE USERID = 7526968 AND PROPID = 100001", "endtime"));
    }
}

