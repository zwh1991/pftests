package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Statement;

public class MySql {
	public static Connection getConnection(String url,String dbName,
			 String username,String password) {  
		 Connection con = null;  //
	     try{  
	    	 Class.forName("com.mysql.jdbc.Driver");//
	    	 con = DriverManager.getConnection(
	    			 "jdbc:mysql://" + url + "/" + dbName,username,password);// 
	     } catch (Exception e){
	    	 System.out.println("Fail to connection" + e.getMessage());  
	     }
	     return con; //
	 }  
	 
	 public static JSONArray query(Connection conn,String sql){	
		 Statement st = null ;
		 JSONArray jArray = new JSONArray();
		 try{
			 st = (Statement) conn.createStatement();
			 ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData rsm = rs.getMetaData();
            Map<Object,Object> map = null;
            while (rs.next()) {
                     map = new HashMap<Object,Object>();
                     for (int i = 1; i <= rsm.getColumnCount(); i++) {
                               map.put(rsm.getColumnName(i), rs.getObject(rsm.getColumnName(i)));
                     }
                     jArray.add(JSONObject.parseObject(JSONObject.toJSONString(map)));
              
            }
		 } catch (SQLException e) {
			 System.out.println(e.getMessage());  
	     } 

	     return jArray;
	 }
	 
	 public static void update(Connection conn,String sql){
		 Statement st = null ;
		 try{
			 st = (Statement) conn.createStatement();
			 st.executeUpdate(sql);
			// System.out.println(count);
		 }catch (SQLException e) {
			 System.out.println(e.getMessage());  
	     } 
	 }
	 public static void select(Connection conn , String sql ){
		 Statement st = null;
		 try {
			st = (Statement) conn.createStatement();
			 st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 public static void other(Connection conn , String sql ){
		 Statement st = null;
		 try {
			st = (Statement) conn.createStatement();
			 st.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
}
