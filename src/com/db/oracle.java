package com.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.Statement;

/**
 * 一个非常标准的连接Oracle数据库的示例代码
 */

public class oracle {
	public static Connection getConnection(String url,
			 String user,String password) {  
		Connection con = null;// 创建一个数据库连接

	    try
	    {
	        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
	        System.out.println("开始尝试连接数据库！");
	//        String url = "jdbc:oracle:" + "thin:@127.0.0.1:1521:XE";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
	        con = DriverManager.getConnection(url, user, password);// 获取连接
	        System.out.println("连接成功！");
	        return con;
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	        return null;
	    }
	    finally
	    {

	    }

	}
	 public static JSONArray query_excuteQuery(Connection con,String sql) throws SQLException{
		 	JSONArray jArray = new JSONArray();
		    PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		    ResultSet result = null;// 创建一个结果集对象
	        pre = con.prepareStatement(sql);// 实例化预编译语句
	        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
	        ResultSetMetaData rsm = result.getMetaData();
            Map<Object,Object> map = null;
            try{
                while (result.next()) {
                    map = new HashMap<Object,Object>();
                    for (int i = 1; i <= rsm.getColumnCount(); i++) {
                              map.put(rsm.getColumnName(i), result.getObject(rsm.getColumnName(i)));
                    }
                    jArray.add(JSONObject.parseObject(JSONObject.toJSONString(map)));
             
           }
            }
            catch(Exception e){
            	
            }


            return jArray;

	 }
	 public static int query_update(Connection con,String sql) throws SQLException{
		    PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		    int result = 0;// 创建一个结果集对象
	        pre = con.prepareStatement(sql);// 实例化预编译语句
	        result = pre.executeUpdate();// 执行查询，注意括号中不需要再加参数



         return result;

	 }
}
