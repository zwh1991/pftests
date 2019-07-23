package common.db;

/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年10月30日 下午1:37:59 
 * 类说明 
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import api.KK;
import api.Base64;

public class FeatureDAO {

	private static Connection conn;
	private static Statement st;
	private static String sql;

	/**
	 * 
	 * 用于查找LibmedicalBean相关的记录，返回值打包成List<LibmedicalBean>形式返回
	 * 
	 * @param sql
	 *            //查找的语句需要自己编写
	 * @return
	 * @throws IOException
	 */
	public static String select(String sql) throws IOException {
		List<LibmedicalBean> result = new ArrayList<LibmedicalBean>();
		// List<LibmedicalBean> expect_null = new ArrayList<LibmedicalBean>();
		// System.out.println("init---------"+result);
		// if(result.equals(expect_null))
		// System.out.println("1111111111");
		conn = MySql.getConnection(KK.Servicedburl, KK.Servicedbname,
				KK.Serviceusername, KK.Servicepassword);// 开启连接
		// System.out.println(sql);
		byte[] feature1 = null;
		byte[] feature = null;
		byte[] feature2 = null;
		try {

			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql); // 执行sql语句，并rs存储结果集
			//System.out.println("connect success-------------");
			if (rs.next()) {
				feature1 = rs.getBytes("facefeature");
				//System.out.println("feature1-------------" + feature1);
				
				feature = Base64.encode(feature1);
				conn.close();
				//System.out.println("feature-------------" + feature);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询出错");

		}
		String s = new String(feature, "GB2312");
		return s;
	}


}
