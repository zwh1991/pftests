package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mysql.jdbc.Statement;

public class mongo {
	public static DB getConnection(String url,int port,String dbName) {  
	      try{   
	          // 连接到 mongodb 服务
	            MongoClient mongoClient = new MongoClient( url , port );
	          
	            // 连接到数据库
	            DB mongoDatabase = mongoClient.getDB(dbName);  
	          System.out.println("Connect to database successfully");
	          return mongoDatabase;
	           
	         }catch(Exception e){
	           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	           return null;
	        }
	 }  
	
	public static MongoDatabase getConnection(String url,int port,String dbName,String username,String password) {  
		try {  
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
            //ServerAddress()两个参数分别为 服务器地址 和 端口  
            ServerAddress serverAddress = new ServerAddress(url,port);  
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
            addrs.add(serverAddress);  
              
            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
            MongoCredential credential = MongoCredential.createScramSha1Credential(username, dbName, password.toCharArray());  
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
            credentials.add(credential);  
              
            //通过连接认证获取MongoDB连接  
            MongoClient mongoClient = new MongoClient(addrs,credentials);  
              
            //连接到数据库  
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);  
            System.out.println("Connect to database successfully");  
            return mongoDatabase;
        } catch (Exception e) {  
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );  
            return null;
        }  
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
