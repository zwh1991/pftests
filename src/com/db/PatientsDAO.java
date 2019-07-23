package common.db;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import api.KK;

/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年11月7日 上午11:15:25 
 * 类说明 
 */
public class PatientsDAO {
	private static Connection conn;
	private static Statement st;
	
	public static List<PatientsBean> select(String sql){
		List<PatientsBean> result =  new ArrayList<PatientsBean>();
		conn = MySql.getConnection(KK.Infodburl, KK.Infodbname, KK.Infousername,KK.Infopassword);
		try {
			st = (Statement) conn.createStatement();
			ResultSet rs = st.executeQuery(sql);  
            while (rs.next()) {
            	PatientsBean bean = new PatientsBean();
            	bean.setPatid(rs.getInt(1));
            	bean.setIdcard(rs.getString(2));
            	bean.setMedicard(rs.getString(3));
            	bean.setRecount(rs.getInt(4));
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
	
	public static List<PatientsBean> getPatients(){
		List<PatientsBean> patientsBean = new ArrayList<PatientsBean>();
		List<LibmedicalBean> libmedicalBean =LibmedicalDAO.getLibmedical();
		PatientsBean temp = new PatientsBean();
		temp.setPatid(0);
		temp.setIdcard("0");
		temp.setMedicard("0");
		temp.setRecount(0);
		patientsBean.add(temp);
		for(int i = 0 ; i < libmedicalBean.size() ; i ++ ){
			for(int j = 0 ; j < patientsBean.size() ; j ++){
				if(isExist(patientsBean, libmedicalBean.get(i))){
					break;
				}
				else{
				PatientsBean bean = new PatientsBean();
				bean.setPatid(patientsBean.size());
				bean.setIdcard(libmedicalBean.get(i).getIdcard());
				bean.setMedicard(libmedicalBean.get(i).getMedicalcard());
				bean.setRecount(0);
				patientsBean.add(bean);
				}
			}
		}
		patientsBean.remove(0);
		return patientsBean;
	}
	
	public static boolean isExist(List<PatientsBean> patientsList , LibmedicalBean bean ){
		for(int i =0 ; i < patientsList.size() ; i ++ ){
			if(bean.getIdcard().equals(patientsList.get(i).getIdcard()) )//&& bean.getMedicalcard().equals(patientsList.get(i).getMedicard())
				return true;
		}
		return false;
	}
	
	public static List<PatientsBean> getPatientsByErrorcode (){//simifid=0
		List<SessionBean> sessionBean = new ArrayList<SessionBean>();
		List<PatientsBean> patientsBean = new ArrayList<PatientsBean>();
		sessionBean = SessionDAO.getSessionOutlierByErrorcode();
		for(int  i = 0 ; i < sessionBean.size(); i++){
			String url = sessionBean.get(i).getImgsrcurl();
			String idcard = url.substring(url.indexOf("idcard:")+7, url.indexOf(";medi"));
			String medicalcard = url.substring(url.indexOf("medicalcard:")+12, url.length());
//			System.out.println(url);
//			System.out.println("idcard ------------"+idcard);
//			System.out.println("medicalcard ------------"+medicalcard);
			PatientsBean bean = new PatientsBean();
			bean.setPatid(i+1);
			bean.setIdcard(idcard);
			bean.setMedicard(medicalcard);
			bean.setRecount(0);
			patientsBean.add(bean);
		}
		return patientsBean;
	}
	
	public static List<PatientsBean> getPatientsByStatus(){//simifid>0
		List<SessionBean> sessionBean = new ArrayList<SessionBean>();
		List<PatientsBean> patientsBean = new ArrayList<PatientsBean>();
		sessionBean = SessionDAO.getSessionOutlierByStatus();
		for(int i = 0 ; i < sessionBean.size() ; i++){
			Long simifid = sessionBean.get(i).getSimifid();
//			System.out.println(simifid);
			String idcard = LibmedicalDAO.select("select * from libmedical where fid = '"+ simifid +"'").get(0).getIdcard();
			String medicalcard = LibmedicalDAO.select("select * from libmedical where fid = '"+ simifid +"'").get(0).getMedicalcard();
			PatientsBean bean = new PatientsBean();
			bean.setPatid(i+1);
			bean.setIdcard(idcard);
			bean.setMedicard(medicalcard);
			bean.setRecount(0);
			patientsBean.add(bean);
		}
		return patientsBean;
	}
	
	public static List<PatientsBean> getMyPatientTable(){
		List<PatientsBean> result = new ArrayList<PatientsBean>();
		List<PatientsBean> statusBean =  new ArrayList<PatientsBean>();
		List<PatientsBean> errorBean =  new ArrayList<PatientsBean>();
		result = getPatients();
		statusBean = getPatientsByStatus();
		errorBean = getPatientsByErrorcode();
		correctPatientsBeanList(result, statusBean);
		correctPatientsBeanList(result, errorBean);
//		print(result);
		return result;
	}
	
	public static void correctPatientsBeanList(List<PatientsBean> result , List<PatientsBean> bean){
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < bean.size(); j++) {
				if (bean.get(j).getIdcard().equals(result.get(i).getIdcard()) && bean.get(j).getMedicard().equals(result.get(i).getMedicard())) {
//					System.out.println("find the same");
					PatientsBean pbean = new PatientsBean();
					pbean.setPatid(result.get(i).getPatid());
					pbean.setIdcard(result.get(i).getIdcard());
					pbean.setMedicard(result.get(i).getMedicard());
					pbean.setRecount(result.get(i).getRecount()+1);
					result.set(i, pbean);
//					print(result.get(i));
				}
				else  if(isExist(result, bean.get(j))==null){
					PatientsBean pbean = new PatientsBean();
					pbean.setPatid(result.size()+1);
					pbean.setIdcard(bean.get(j).getIdcard());
					pbean.setMedicard(bean.get(j).getMedicard());
					pbean.setRecount(0);
					result.add(pbean);
				}
			}
		}
	}
	
	public static Boolean compareResult(List<PatientsBean> db , List<PatientsBean> myresult){
		for(int i =0 ; i < db.size() ; i ++){
			PatientsBean temp = isExist(myresult, db.get(i));
			if(temp==null){
				print(db.get(i));
				print(temp);
				System.out.println("idcard not exist");
				return false;
			}
			else{
				if(temp.getMedicard().equals(db.get(i).getMedicard())==false){
					print(db.get(i));
					print(temp);
					System.out.println("medicalcard difference");
					return false;
				}
				if(temp.getRecount()!=db.get(i).getRecount()){
					print(db.get(i));
					print(temp);
					System.out.println("recount difference");
					return false;
				}
			}
		}
	    return true;
	}
	
	private static PatientsBean isExist(List<PatientsBean> myresult,PatientsBean patientsBean) {
		for(int i =0 ; i < myresult.size() ; i ++ ){
			if(patientsBean.getIdcard().equals(myresult.get(i).getIdcard()) )//&& bean.getMedicalcard().equals(patientsList.get(i).getMedicard())
				return myresult.get(i);
		}
//		System.out.println("not exist idcard :"+patientsBean.getIdcard());
		return null;
	}

	public static void print(PatientsBean bean){
		System.out.println(bean.getPatid() + "\t" + bean.getIdcard() + "\t" + bean.getMedicard() + "\t"+ bean.getRecount());
	}
	
	public static void print(List<PatientsBean> bean){
		System.out.println("--------------begin print---------------");
//		WriteToFile("PatientsLog.txt","id" + "        " + "idcard" + "      " + "medicard" + "    "+ "recount"+"\r\n");
		for(int i = 0 ; i < bean.size() ; i++){
			String content = bean.get(i).getPatid() + "        " + bean.get(i).getIdcard() + "      " + bean.get(i).getMedicard() + "    "+ bean.get(i).getRecount();
//			WriteToFile("PatientsLog.txt",content+"\r\n");
			System.out.println(content);
		}
		System.out.println("-----------------end print--------------");
	}
	
	public static void WriteToFile(String fileName, String content) {
		try {
			String path = System.getProperty("user.dir");
			String fullPath = path+"\\"+fileName;
			FileWriter fw = new FileWriter(fullPath, true);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeNormalPatients(List<PatientsBean> myresult){
		for(int i = 0 ; i < myresult.size() ; i ++){
			if(myresult.get(i).getRecount()==0)
				myresult.remove(i);
		}
	}
	
	public static void main(String[] args) {
		
		String sql = "select * from patients";
		String sqlCount = "select count(*) as count from records_1";
		int dbCount = RecountDAO.count(sqlCount);
		List<PatientsBean>  db = select(sql);
		List<PatientsBean> myresult = getMyPatientTable();
		List<PatientsBean> error = getPatientsByErrorcode();
		List<PatientsBean> status = getPatientsByStatus();
//		List<PatientsBean> patients = getPatients();
		int mycount = status.size() + error.size();
//		print(myresult);
//		System.out.println(patients.size());
//		System.out.println(db.size());
		System.out.println(compareResult(db, myresult));
		System.out.println(dbCount==mycount);
	}
}
