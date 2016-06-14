package cn.ccsgroup.ccsframework.entity;

import com.alibaba.fastjson.annotation.JSONField;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.Role.java]  
 * @ClassName:    [Role]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-26 下午5:10:32]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-26 下午5:10:32，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class Role extends BaseBean{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 7990343841962945412L;
	private int id;
	private String remark;
	private String regionCode;
	private String rName;
	private int issysdef = 0;
	private int status = 1;
	private Integer orgid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}		
	@JSONField(name="rName")
	public String getrName() {
		return rName;
	}
	public void setrName(String rName) {
		this.rName = rName;
	}
	public int getIssysdef() {
		return issysdef;
	}
	public void setIssysdef(int issysdef) {
		this.issysdef = issysdef;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Integer getOrgid() {
		return orgid;
	}
	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
}
