package cn.ccsgroup.ccsframework.entity;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.SYSLOGS.java]  
 * @ClassName:    [SYSLOGS]   
 * @Description:  [系统日志]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2014-1-23 下午05:14:42]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2014-1-23 下午05:14:42]   
 * @UpdateRemark: [新建] 
 * @Version:      [v1.0] 
 */
public class SysLogs {

	/**
	 * @Fields id : 流水号
	 */
	private Integer id;
	/**
	 * @Fields loginid : 操作帐号
	 */
	private String loginid;
	/**
	 * @Fields regioncode : 操作区域
	 */
	private String regioncode;
	/**
	 * @Fields orgid : 操作组织
	 */
	private Integer orgid;
	/**
	 * @Fields opeparam : 操作参数
	 */
	private String opeparam;
	/**
	 * @Fields opedate : 操作时间
	 */
	private String opedate;
	/**
	 * @Fields opefunc : 操作功能
	 */
	private String opefunc;
	/**
	 * @Fields flag : 操作标识
	 */
	private String flag;
	/**
	 * @Fields sysid : 系统ID
	 */
	private Integer sysid;
	/**
	 * @Fields opemod : 操作类
	 */
	private String opemod;
	/**
	 * @Fields sysinsttime : 创建时间
	 */
	private String sysinsttime;
	/**
	 * @Fields status : 状态
	 */
	private Integer status;
	/**
	 * @Fields ip : 操作IP
	 */
	private String ip;
	
	/**
	 * @Fields orgname : 组织机构名称
	 */
	private String orgname;
	/**
	 * @Fields sysname : 系统名称
	 */
	private String sysname;
	
	
	
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	public Integer getOrgid() {
		return orgid;
	}
	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
	public String getOpeparam() {
		return opeparam;
	}
	public void setOpeparam(String opeparam) {
		this.opeparam = opeparam;
	}
	public String getOpedate() {
		return opedate;
	}
	public void setOpedate(String opedate) {
		this.opedate = opedate;
	}
	public String getOpefunc() {
		return opefunc;
	}
	public void setOpefunc(String opefunc) {
		this.opefunc = opefunc;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getSysid() {
		return sysid;
	}
	public void setSysid(Integer sysid) {
		this.sysid = sysid;
	}
	public String getOpemod() {
		return opemod;
	}
	public void setOpemod(String opemod) {
		this.opemod = opemod;
	}
	public String getSysinsttime() {
		return sysinsttime;
	}
	public void setSysinsttime(String sysinsttime) {
		this.sysinsttime = sysinsttime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	
	
	
}
