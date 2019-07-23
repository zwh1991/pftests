package common.db;
/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年11月27日 下午3:54:05 
 * 类说明 
 */
public class ReportBean {
	private String ip ; 
	private int status ;
	private String reporttime ;
	
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
