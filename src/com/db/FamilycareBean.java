package common.db;
/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年11月7日 上午10:46:58 
 * 类说明 
 */
public class FamilycareBean {
	private String idcard ;
	private String medicard ;
	private int status ;
	private String sessionid ;
	private String reporttime;
	private int noneselfcount ;

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
	public int getstatus() {
		return status;
	}
	public void setstatus(int status) {
		this.status =status;
	}	
	public String getsessionid() {
		return sessionid;
			}
	public void setsessionid(String sessionid) {
		this.sessionid = sessionid;
			}
	public String getreportime() {
		return reporttime;
			}
	public void setreporttime(String reporttime) {
		this.reporttime = reporttime;
			}
	public int getnoneselfcount() {
	return noneselfcount;
    }
  public void setnoneselfcount(int noneselfcount) {
	this.noneselfcount =noneselfcount;
}
}
