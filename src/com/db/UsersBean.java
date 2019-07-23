package common.db;
/** 
 * @author 作者 zql: 
 * @version 创建时间：2014年10月30日 下午1:39:23 
 * 类说明 
 */
public class UsersBean {
	private int id ;
	private int isadmin ;
	private String name ;
	private String realname ;
	private String passwd ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIsadmin() {
		return isadmin;
	}
	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
