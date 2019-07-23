package common.db;
/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年10月30日 下午1:37:59 
 * 类说明 
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import api.KK;

public class FamilycareDAO {

	private static Connection conn;
	private static Statement st;
	private static String sql;
	
	/**
	 * 
	 * 用于查找 Familycare相关的记录，返回值打包成List< Familycare>形式返回
	 * @param sql//查找的语句需要自己编写
	 * @return
	 */
	public static List<FamilycareBean> select(String sql){
		List<FamilycareBean> result = new ArrayList<FamilycareBean>();
//		List<LibmedicalBean> expect_null = new ArrayList<LibmedicalBean>();
//		System.out.println("init---------"+result);
//		if(result.equals(expect_null))
//				System.out.println("1111111111");
		conn = MySql.getConnection(KK.Servicedburl, KK.Servicedbname, KK.Serviceusername,KK.Servicepassword);//开启连接
		//System.out.println(sql);
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);  //执行sql语句，并rs存储结果集
            while (rs.next()) {//组装结果集
            	FamilycareBean bean = new FamilycareBean();
            	bean.setIdcard(rs.getString(1));
            	bean.setMedicard(rs.getString(2));
            	bean.setstatus(rs.getInt(3));
            	bean.setsessionid(rs.getString(4));
//            	Timestamp temp = rs.getTimestamp(5);
//            	String test = rs.getString(5);
            	bean.setreporttime(rs.getString(5));
            	bean.setnoneselfcount(rs.getInt(6));
            	result.add(bean);
            }  
            conn.close();
            //System.out.println("result-------------" + result);
            return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询出错");
			return result;
		}
	}
	//用于打印List<LibmedicalBean>里的每一条记录的每一个字段
	public static void LibmedicalInfo(List<LibmedicalBean> list){
		if(list==null){
			System.out.println("没有libmedical打印，，，");
			return;
		}
		System.out.println("**************medicalInfo begin*************");
		System.out.println("fid\tfaceid\tfeatureid\tfeaturebl\tcreatetime\tidcard\tmedicalcard\tmark\t\timgurl");
		for(int i=0;i<list.size();i++){
			LibmedicalBean bean = (LibmedicalBean)list.get(i);
			System.out.println(bean.getFid()+"\t"+bean.getFeatureid()+"\t"+bean.getFeaturetbl()+"\t"+bean.getCreatetime()+"\t"+bean.getIdcard()+"\t"+bean.getMedicalcard()+"\t"+bean.getMark()+"\t"+bean.getImgurl());
		}
		System.out.println("**************medicalInfo end***************");
	}
	//查询libmedical表记录总数
	public static int count (){
		conn = MySql.getConnection(KK.Servicedburl, KK.Servicedbname, KK.Serviceusername,KK.Servicepassword);
		sql = "select *  from libmedical";
		try {
			st = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = st.executeQuery(sql);
			int size = 0;
			while(rs.next()){
				size++;
			}
			System.out.println("libmedical记录总数----->" + size);
			conn.close();
			return size;
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("查询libmedical记录出错");
			return -1;
		}
	}
	//通过idcard删除libmedical表的记录
	public static void delete(String idcard){
		conn = MySql.getConnection(KK.Servicedburl, KK.Servicedbname, KK.Serviceusername,KK.Servicepassword);
		sql = "delete from libmedical where idcard ='"+ idcard+"'";
//		System.out.println(sql);
		try {
			st = (Statement) conn.createStatement();
			int rs = st.executeUpdate(sql);
			System.out.println("删除记录数----->" + rs);
			conn.close();
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("删除libmedical数据出错");
		}
	}
	

}
