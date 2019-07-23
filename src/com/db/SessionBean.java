package common.db;
/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年10月30日 下午1:37:59 
 * 类说明 
 */
//archospital数据库里的session表，封装成一个SessionBean类
public class SessionBean {
	private String sessionid;
	private String reqtime;
	private int errorcode;
	private int status;
	private long simifid;
	private int diffage;
	private int arcage;
	private int diffgender;
	private String imgsrcurl;
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getReqtime() {
		return reqtime;
	}
	public void setReqtime(String reqtime) {
		this.reqtime = reqtime;
	}
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getSimifid() {
		return simifid;
	}
	public void setSimifid(long simifid) {
		this.simifid = simifid;
	}
	public int getDiffage() {
		return diffage;
	}
	public void setDiffage(int diffage) {
		this.diffage = diffage;
	}
	public int getArcage() {
		return arcage;
	}
	public void setArcage(int arcage) {
		this.arcage = arcage;
	}
	public int getDiffgender() {
		return diffgender;
	}
	public void setDiffgender(int diffgender) {
		this.diffgender = diffgender;
	}
	public String getImgsrcurl() {
		return imgsrcurl;
	}
	public void setImgsrcurl(String imgsrcurl) {
		this.imgsrcurl = imgsrcurl;
	}
	
}
