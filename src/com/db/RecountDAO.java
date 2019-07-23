package common.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import api.KK;

/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年11月11日 下午3:17:46 
 * 类说明 
 */
public class RecountDAO {
	private static Connection conn;
	private static Statement st;
	
	public static int count(String sql){
		conn = MySql.getConnection(KK.Infodburl, KK.Infodbname, KK.Infousername,KK.Infopassword);
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int count = 0;
			if(rs.next()){
				count = rs.getInt(1);
			}
			conn.close();
			return count;
		}
		catch(SQLException e){
			e.printStackTrace();
			return 0;
		}
	}
}
