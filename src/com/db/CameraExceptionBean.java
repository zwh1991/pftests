package com.db;
/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年11月28日 上午9:28:00 
 * 类说明 
 */
public class CameraExceptionBean {
	private int cid ; 
	private String ip ;
	private int status ;
	private String reporttime ;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getReporttime() {
		return reporttime;
	}
	public void setReporttime(String reporttime) {
		this.reporttime = reporttime;
	}
}
