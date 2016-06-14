package cn.ccsgroup.ccsframework.entity;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.Information.java]  
 * @ClassName:    [Information]   
 * @Description:  [公告实体类]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2014-1-6 下午04:38:00]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2014-1-6 下午04:38:00]   
 * @UpdateRemark: [新建]  
 * @Version:      [v1.0] 
 */
public class Information {

	/**
	 * @Fields id : 流水号
	 */
	private Integer id;
	
	/**
	 * @Fields infotype : 信息类型
	 */
	private String infotype;
	/**
	 * @Fields infocontent : 信息内容
	 */
	private String infocontent;
	/**
	 * @Fields infotitle : 信息标题
	 */
	private String infotitle;
	/**
	 * @Fields createruser : 创建人
	 */
	private String createruser;
	/**
	 * @Fields sysinsttime : 创建时间
	 */
	private String sysinsttime;
	/**
	 * @Fields sysedittime : 修改时间
	 */
	private String sysedittime;
	/**
	 * @Fields sysdeltime : 删除时间
	 */
	private String sysdeltime;
	/**
	 * @Fields toolurl : 工具URL
	 */
	private String toolurl;
	/**
	 * @Fields status : 状态
	 */
	private Integer status;
	/**
	 * @Fields regioncode : 区域编码
	 */
	private String regioncode;
	
	/**
	 * @Fields sendstatus : 发送状态
	 */
	private String sendstatus;
	/**
	 * @Fields filecounts : 附件
	 */
	private String filecounts;
	
	
	
	public String getSendstatus() {
		return sendstatus;
	}
	public void setSendstatus(String sendstatus) {
		this.sendstatus = sendstatus;
	}
	
	public String getFilecounts() {
		return filecounts;
	}
	public void setFilecounts(String filecounts) {
		this.filecounts = filecounts;
	}
	public String getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInfotype() {
		return infotype;
	}
	public void setInfotype(String infotype) {
		this.infotype = infotype;
	}
	public String getInfocontent() {
		return infocontent;
	}
	public void setInfocontent(String infocontent) {
		this.infocontent = infocontent;
	}
	public String getInfotitle() {
		return infotitle;
	}
	public void setInfotitle(String infotitle) {
		this.infotitle = infotitle;
	}
	public String getCreateruser() {
		return createruser;
	}
	public void setCreateruser(String createruser) {
		this.createruser = createruser;
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
	public String getToolurl() {
		return toolurl;
	}
	public void setToolurl(String toolurl) {
		this.toolurl = toolurl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
