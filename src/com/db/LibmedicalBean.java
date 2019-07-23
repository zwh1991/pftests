package common.db;
/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年10月30日 下午1:37:59 
 * 类说明 
 */
//archospital数据库里的libmedical表，封装成一个LibmedicalBean类
public class LibmedicalBean {
	private int fid;
	private long faceid;
	private int featureid;
	private String featuretbl;
	private String createtime;
	private String idcard;
	private String medicalcard;
	private int mark;
	private String imgurl;
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public long getFaceid() {
		return faceid;
	}
	public void setFaceid(long faceid) {
		this.faceid = faceid;
	}
	public int getFeatureid() {
		return featureid;
	}
	public void setFeatureid(int featureid) {
		this.featureid = featureid;
	}
	public String getFeaturetbl() {
		return featuretbl;
	}
	public void setFeaturetbl(String featuretbl) {
		this.featuretbl = featuretbl;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getMedicalcard() {
		return medicalcard;
	}
	public void setMedicalcard(String medicalcard) {
		this.medicalcard = medicalcard;
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
