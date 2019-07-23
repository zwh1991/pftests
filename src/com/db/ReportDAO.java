package common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import api.KK;

/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年11月27日 下午4:43:27 
 * 类说明 
 */
public class ReportDAO {
	
	private static Connection conn;
	private static Statement st;
	
	public static List<ReportBean> select(String sql){
		List<ReportBean> result = new ArrayList<ReportBean>();
		conn = MySql.getConnection(KK.Servicedburl, KK.Servicedbname, KK.Serviceusername,KK.Servicepassword);//开启连接
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);  //执行sql语句，并rs存储结果集
            while (rs.next()) {//组装结果集
            	ReportBean bean = new ReportBean();
            	bean.setIp(rs.getString(1));
            	bean.setStatus(rs.getInt(2));
            	bean.setReporttime(rs.getString(3));
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
	
	public static void print(List<ReportBean> bean){
		System.out.println("ip\t\treporttime\t\tstatus");
		for(int i = 0 ; i < bean.size(); i++){
			System.out.println(bean.get(i).getIp()+ "\t" + bean.get(i).getReporttime() + "\t" + bean.get(i).getStatus());
		}
	}
}
