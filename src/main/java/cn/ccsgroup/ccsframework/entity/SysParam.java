package cn.ccsgroup.ccsframework.entity;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.SysParam.java]  
 * @ClassName:    [SysParam]   
 * @Description:  [系统功能配置实体类]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2014-1-24 上午10:48:09]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2014-1-24 上午10:48:09]   
 * @UpdateRemark: [新建]  
 * @Version:      [v1.0] 
 */
public class SysParam {

	/**
	 * @Fields id : 流水号
	 */
	private Integer id;
	/**
	 * @Fields paramtype : 类型：基本；功能控制；
	 */
	private String paramtype;
	/**
	 * @Fields paramname :参数名称
	 */
	private String paramname;
	/**
	 * @Fields paramvalue :参数值
	 */
	private String paramvalue;
	/**
	 * @Fields sysid : 系统ID
	 */
	private Integer sysid;
	/**
	 * @Fields status : 状态
	 */
	private Integer status;
	/**
	 * @Fields sysinsttime : 创建时间
	 */
	private String sysinsttime;
	/**
	 * @Fields sysedittime :修改时间
	 */
	private String sysedittime;
	/**
	 * @Fields sysdeltime : 注销时间
	 */
	private String sysdeltime;
	/**
	 * @Fields sysname : 系统名称
	 */
	private String sysname;
	
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
	public String getParamtype() {
		return paramtype;
	}
	public void setParamtype(String paramtype) {
		this.paramtype = paramtype;
	}
	public String getParamname() {
		return paramname;
	}
	public void setParamname(String paramname) {
		this.paramname = paramname;
	}
	public String getParamvalue() {
		return paramvalue;
	}
	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}
	public Integer getSysid() {
		return sysid;
	}
	public void setSysid(Integer sysid) {
		this.sysid = sysid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
	
}
