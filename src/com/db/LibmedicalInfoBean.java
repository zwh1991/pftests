package common.db;
/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年11月7日 上午10:55:07 
 * 类说明 
 */
public class LibmedicalInfoBean {
	private int picid ;
	private int patid ;
	private int hosid ;
	private int faceid ;
	private String createtime ;
	private int mark ;
	private String imgurl ;
	public int getPicid() {
		return picid;
	}
	public void setPicid(int picid) {
		this.picid = picid;
	}
	public int getPatid() {
		return patid;
	}
	public void setPatid(int patid) {
		this.patid = patid;
	}
	public int getHosid() {
		return hosid;
	}
	public void setHosid(int hosid) {
		this.hosid = hosid;
	}
	public int getFaceid() {
		return faceid;
	}
	public void setFaceid(int faceid) {
		this.faceid = faceid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	
}
