package cn.ccsgroup.ccsframework.entity;

import java.sql.Date;


public class SMS {

	private Integer id;
	private String loginId;
	private Integer bid;
	private String companyName;
	private Integer status;
	private String btype;
	private String cateGory;
	private String phone;
	private String project;
	private String issend;
	private Date sendTime;
	private String statusName;
	private String systemtype;
	private String smsContent;
	private String tmpStatus;
	private Date sysInstTime;
	private Date sysDitTime;
	private Date sysDelTime;
	private String url;
	private String regioncode;//区域代码
	private String permission;//权限字段
	private Integer tableId;//房屋唯一号
	
	public SMS() {

	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}

	public String getCateGory() {
		return cateGory;
	}

	public void setCateGory(String cateGory) {
		this.cateGory = cateGory;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getIssend() {
		return issend;
	}

	public void setIssend(String issend) {
		this.issend = issend;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getSystemtype() {
		return systemtype;
	}

	public void setSystemtype(String systemtype) {
		this.systemtype = systemtype;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getTmpStatus() {
		return tmpStatus;
	}

	public void setTmpStatus(String tmpStatus) {
		this.tmpStatus = tmpStatus;
	}

	public Date getSysInstTime() {
		return sysInstTime;
	}

	public void setSysInstTime(Date sysInstTime) {
		this.sysInstTime = sysInstTime;
	}

	public Date getSysDitTime() {
		return sysDitTime;
	}

	public void setSysDitTime(Date sysDitTime) {
		this.sysDitTime = sysDitTime;
	}

	public Date getSysDelTime() {
		return sysDelTime;
	}

	public void setSysDelTime(Date sysDelTime) {
		this.sysDelTime = sysDelTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}


	public String getRegioncode() {
		return regioncode;
	}


	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}


	public String getPermission() {
		return permission;
	}


	public void setPermission(String permission) {
		this.permission = permission;
	}


	public Integer getTableId() {
		return tableId;
	}


	public void setTableId(Integer tableId) {
		this.tableId = tableId;
	}

}
