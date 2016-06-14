package cn.ccsgroup.ccsframework.entity;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.Position.java]  
 * @ClassName:    [Position]   
 * @Description:  [职位的实体类]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2013-12-30 下午02:21:00]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2013-12-30 下午02:21:00]   
 * @UpdateRemark: [新建]  
 * @Version:      [v1.0] 
 */
public class Position {
	
	/**
	 * @Fields id : 流水号
	 */
	private Integer id;
	/**
	 * @Fields pname : 职位名称
	 */
	private String pname;
	/**
	 * @Fields pno : 职位编号
	 */
	private String pno;
	/**
	 * @Fields orgid : 组织机构ID
	 */
	private Integer orgid;
	/**
	 * @Fields description : 职位描述
	 */
	private String description;
	/**
	 * @Fields sysinsttime : 系统创建时间
	 */
	private String sysinsttime;
	/**
	 * @Fields sysedittime : 系统修改时间
	 */
	private String sysedittime;
	/**
	 * @Fields sysdeltime : 系统删除时间
	 */
	private String sysdeltime;
	/**
	 * @Fields status : 状态
	 */
	private Integer status;
	/**
	 * @Fields orgname : 组织机构名称
	 */
	private String orgname;
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public Integer getOrgid() {
		return orgid;
	}
	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSysinsttime() {
		return sysinsttime;
	}
	public void setSysinsttime(String sysinsttime) {
		this.sysinsttime = sysinsttime;
	}
	public String getSysedittime() {
		return sysedittime;
	}
	public void setSysedittime(String sysedittime) {
		this.sysedittime = sysedittime;
	}
	public String getSysdeltime() {
		return sysdeltime;
	}
	public void setSysdeltime(String sysdeltime) {
		this.sysdeltime = sysdeltime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
