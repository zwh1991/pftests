package com.db;
//prod

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class pgsql {

	
	public static Connection getCon(String url,String port,String db,String username,String password){
		
	      Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://"+url+":"+port+"/"+db,
	            		username, password);
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
		return c;
	}

	
	 public static void query(Connection conn,String sql){
		 Statement st = null ;
		 try{
			 st = conn.createStatement();
			 st.executeUpdate(sql);
			// System.out.println(count);
		 }catch (SQLException e) {
			 System.out.println(e.getMessage());  
	     } 
	 }
	 
	 public static ResultSet queryGet(Connection conn,String sql) throws SQLException{
		 		Statement stmt = conn.createStatement(); 
	        try {  
	            ResultSet rs = stmt.executeQuery(sql);
	            return rs;

	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	        return null;  
	 }
}
