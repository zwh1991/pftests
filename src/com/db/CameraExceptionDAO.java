package com.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import api.KK;

/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年11月28日 上午9:30:32 
 * 类说明 
 */
public class CameraExceptionDAO {
	private static Connection conn;
	private static Statement st;
	
	public static List<CameraExceptionBean> select(String sql){
		List<CameraExceptionBean> result = new ArrayList<CameraExceptionBean>();
		conn = MySql.getConnection(KK.Servicedburl, KK.Servicedbname, KK.Serviceusername,KK.Servicepassword);//开启连接
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);  //执行sql语句，并rs存储结果集
            while (rs.next()) {//组装结果集
            	CameraExceptionBean bean = new CameraExceptionBean();
            	bean.setCid(rs.getInt(1));
            	bean.setIp(rs.getString(2));
            	bean.setReporttime(rs.getString(3));
            	bean.setStatus(rs.getInt(4));
            	result.add(bean);
            }  
            conn.close();
            return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("查询出错");
			return result;
		}
	}
	
	public static void print(List<CameraExceptionBean> bean){
		System.out.println("cid\tip\t\treporttime\tstatus");
		for(int i = 0 ; i < bean.size(); i++){
			System.out.println(bean.get(i).getCid() + "\t" + bean.get(i).getIp() + "\t" + bean.get(i).getReporttime() + "\t" + bean.get(i).getStatus());
		}
	}
}
