package common.db;
/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年11月7日 上午10:46:58 
 * 类说明 
 */
public class PatientsBean {
	private int patid ;
	private String idcard ;
	private String medicard ;
	private int recount ;
	public int getPatid() {
		return patid;
	}
	public void setPatid(int patid) {
		this.patid = patid;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getMedicard() {
		return medicard;
	}
	public void setMedicard(String medicard) {
		this.medicard = medicard;
	}
	public int getRecount() {
		return recount;
	}
	public void setRecount(int recount) {
		this.recount = recount;
	}
}
