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
import java.util.ArrayList;
import java.util.List;

import api.KK;
import common.db.MySql;

public class SessionDAO {

	private static Connection conn;
	private static Statement st;
	private static String sql;
	//用于查找SessionBean相关的记录，返回值打包成List<SessionBean>形式返回
	//查找语句需要完全自己编写
	public static List<SessionBean> select(String sql){
		//System.out.println("sql________________"+ sql);
		List<SessionBean> result =  new ArrayList<SessionBean>();
		conn = MySql.getConnection(KK.Servicedburl, KK.Servicedbname, KK.Serviceusername,KK.Servicepassword);
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);  
//			if(rs.next()){
//				return null;
//			}
//			rs.first();
            while (rs.next()) {
            	SessionBean bean = new SessionBean();
            	bean.setSessionid(rs.getString(2));
            	bean.setReqtime(rs.getString(3));
            	bean.setErrorcode(rs.getInt(4));
            	bean.setStatus(rs.getInt(5));
            	bean.setSimifid(rs.getInt(6));
            	bean.setDiffage(rs.getInt(7));
            	bean.setArcage(rs.getInt(8));
            	bean.setDiffgender(rs.getInt(9));
            	bean.setImgsrcurl(rs.getString(10));
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
	//打印List<SessionBean>里的每一条记录的每个字段
	public static void SessionInfo(List<SessionBean> list ){
		if(list == null){
			System.out.println("没有session打印，，，");
			  return;
			  }
		  System.out.println("---------sessioninfo begin to print--------");
		  System.out.println();
		  for(int i=0;i<list.size();i++){
			  int j =i+1;
			  SessionBean session = (SessionBean)list.get(i);
			  System.out.println("---------第 "+ j +" 条session记录----------");
			  System.out.println("sessionid -->" + session.getSessionid());
			  System.out.println("reqtime -->" + session.getReqtime());
			  System.out.println("errorcode -->" + session.getErrorcode());
			  System.out.println("status -->" + session.getStatus());
			  System.out.println("simifid -->" + session.getSimifid());
			  System.out.println("diffage -->" + session.getDiffage());
			  System.out.println("arcage -->" + session.getArcage());
			  System.out.println("diffgender -->" + session.getDiffgender());
			  System.out.println("imgsrcurl -->" + session.getImgsrcurl());
			  System.out.println();
		  }
		  System.out.println("---------sessioninfo end----------");
	}
	
	public static List<SessionBean> getSessionOutlierByStatus (){
		sql ="select * from session where (status >= 1 and status <= 3 or errorcode <450000)and simifid >0 ";
		List<SessionBean> result =  new ArrayList<SessionBean>();
		result = select(sql);
		return result;
	}
	
	public static List<SessionBean> getSessionOutlierByErrorcode (){
		sql ="select * from session where errorcode <450000 and simifid =0";
		List<SessionBean> result =  new ArrayList<SessionBean>();
		result = select(sql);
		return result;
	}
	
	public static List<SessionAutoBean> getSessionAuto(String sessionid,List<SessionAutoBean> auto){
		sql = "select * from session where sessionid = '" + sessionid +"'";
		List<SessionBean> session = new ArrayList<SessionBean>();
		session = select(sql);
		SessionAutoBean bean = new SessionAutoBean();
		bean.setId(auto.size()+1);
		bean.setSessionid(session.get(0).getSessionid());
		bean.setReqtime(session.get(0).getReqtime());
		bean.setErrorcode(session.get(0).getErrorcode());
		bean.setStatus(session.get(0).getStatus());
		bean.setSimifid(session.get(0).getSimifid());
		bean.setDiffage(session.get(0).getDiffage());
		bean.setArcage(session.get(0).getArcage());
		bean.setDiffgender(session.get(0).getDiffgender());
		bean.setImgsrcurl(session.get(0).getImgsrcurl());
		auto.add(bean);
		return auto;
	}
	
	public static void print(List<SessionAutoBean> auto){
		if(auto == null){
			System.out.println("没有session打印，，，");
			  return;
			  }
		  System.out.println("---------sessioninfo begin to print--------");
		  System.out.println();
		  for(int i=0;i<auto.size();i++){
			  int j =i+1;
			  SessionAutoBean session = (SessionAutoBean)auto.get(i);
			  System.out.println("---------第 "+ j +" 条session记录----------");
			  System.out.println("id -->" + session.getId());
			  System.out.println("sessionid -->" + session.getSessionid());
			  System.out.println("reqtime -->" + session.getReqtime());
			  System.out.println("errorcode -->" + session.getErrorcode());
			  System.out.println("status -->" + session.getStatus());
			  System.out.println("simifid -->" + session.getSimifid());
			  System.out.println("diffage -->" + session.getDiffage());
			  System.out.println("arcage -->" + session.getArcage());
			  System.out.println("diffgender -->" + session.getDiffgender());
			  System.out.println("imgsrcurl -->" + session.getImgsrcurl());
			  System.out.println();
		  }
		  System.out.println("---------sessioninfo end----------");
	}
	
	public static void delete(SessionAutoBean auto){
		conn = MySql.getConnection(KK.Servicedburl, KK.Servicedbname, KK.Serviceusername,KK.Servicepassword);
		sql = "delete from session where sessionid = '" + auto.getSessionid() +"'";
		try{
			st = (Statement) conn.createStatement();
			int rs = st.executeUpdate(sql);
			System.out.println("删除记录数----->" + rs);
			conn.close();
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println("删除session数据出错");
		}
	}
	
	public static void deleteList(List<SessionAutoBean> auto){
		for(int i =0 ; i < auto.size() ;  i++){
			delete(auto.get(i));
		}
	}
	
	public static void main(String[] args) {
		/*sql = "select * from session where sessionid ='5d3979e0-ef05-4ce7-95c5-aa9ffcd854a3'";
		List<SessionBean> l=select(sql);
		if(l!=null){
			System.out.println(l.get(0).getSessionid());
		}*/
		List<SessionBean> l  = getSessionOutlierByErrorcode();
		System.out.println(l.size());
	}
}
