package cn.ccsgroup.ccsframework.base.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.base.entity.SessionBean.java]  
 * @ClassName:    [SessionBean]   
 * @Description:  [Session中存储值的JavaBean]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-3 下午5:17:21]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-3 下午5:17:21，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class SessionBean implements Serializable {

	private static final long serialVersionUID = 4480183933256919398L;

	private Integer id;
	private String userIp;
	private String loginid;
	private String password;
	private String userName;
	private List<String> regionCodes;
	private String regionCode;
	private List<Integer> roleId;
	private String ownerid;
	private Dict organization;
	private List<Dict> systems;
	private Dict system;
	private List<Dict> positions;
	private Map<String,String>  powerUrls = new HashMap<String,String>();
	private Dict parentOrg;
	private List<Dict> childOrgs;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<String> getRegionCodes() {
		return regionCodes;
	}
	public void setRegionCodes(List<String> regionCodes) {
		this.regionCodes = regionCodes;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public List<Integer> getRoleId() {
		return roleId;
	}
	public void setRoleId(List<Integer> roleId) {
		this.roleId = roleId;
	}
	public String getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
	public List<Dict> getSystems() {
		return systems;
	}
	public void setSystems(List<Dict> systems) {
		this.systems = systems;
	}
	public List<Dict> getPositions() {
		return positions;
	}
	public void setPositions(List<Dict> positions) {
		this.positions = positions;
	}
	public Dict getOrganization() {
		return organization;
	}
	public void setOrganization(Dict organization) {
		this.organization = organization;
	}
	public Dict getSystem() {
		return system;
	}
	public void setSystem(Dict system) {
		this.system = system;
	}
	public Map<String, String> getPowerUrls() {
		return powerUrls;
	}
	public void setPowerUrls(Map<String, String> powerUrls) {
		this.powerUrls = powerUrls;
	}
	public Dict getParentOrg() {
		return parentOrg;
	}
	public void setParentOrg(Dict parentOrg) {
		this.parentOrg = parentOrg;
	}
	public List<Dict> getChildOrgs() {
		return childOrgs;
	}
	public void setChildOrgs(List<Dict> childOrgs) {
		this.childOrgs = childOrgs;
	}
}
