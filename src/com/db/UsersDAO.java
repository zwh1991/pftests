package common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import api.KK;

/** 
 * @author 作者 llzhu: 
 * @version 创建时间：2014年10月30日 下午1:37:59 
 * 类说明 
 */
public class UsersDAO {
	private static Connection conn;
	private static Statement st;
	private static String sql;
	/**
	 * @param sql
	 * @return
	 */
	public static List<UsersBean> select(String sql){
		System.out.println(sql);
		List<UsersBean> result =  new ArrayList<UsersBean>();
		conn = MySql.getConnection(KK.Infodburl, KK.Infodbname, KK.Infousername,KK.Infopassword);
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
            	UsersBean bean = new UsersBean();
            	bean.setId(rs.getInt(1));
            	bean.setIsadmin(rs.getInt(2));
            	bean.setName(rs.getString(3));
            	bean.setPasswd(rs.getString(4));
            	bean.setRealname(rs.getString(5));
            	result.add(bean);
            }  
            conn.close();
            return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询出错");
			return null;
		}
	}
	
	public static void delete (String name){
		conn = MySql.getConnection(KK.Infodburl, KK.Infodbname, KK.Infousername,KK.Infopassword);
//		System.out.println(KK.Infodburl+KK.Infodbname+KK.Infousername+KK.Infopassword);
		sql = "delete from users where name = "+"'"+ name+"'";
		System.out.println(sql);
		try {
			st = (Statement) conn.createStatement();
			int rs = st.executeUpdate(sql);
			System.out.println("删除记录数----->" + rs);
			conn.close();
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("删除users数据出错");
		}
	}
}
