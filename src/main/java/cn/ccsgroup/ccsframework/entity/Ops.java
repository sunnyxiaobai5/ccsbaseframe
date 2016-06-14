package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.bean]  
 * @ClassName:    [Operation]   
 * @Description:  [操作]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-11-15]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class Ops extends BaseBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 操作Id
	 */
	private Integer id;

	/**
	 * 操作名称
	 */
	private String opsName;
	
	/**
	 * 操作描述
	 */
	private String description;
	
	/**
	 * 功能Id
	 */
	private String funcId;
	
	/**
	 * 是否有效
	 */
	private Integer isActive;
	
	/**
	 * 是否启用特殊标识
	 */
	private String isStart;

	/**
	 * 标识是否删除 
	 */
	private Integer status;
	
	/**
	 * 权限控制标识
	 */
	private String opsUrl;
	
	public String getOpsUrl() {
		return opsUrl;
	}


	public void setOpsUrl(String opsUrl) {
		this.opsUrl = opsUrl;
	}


	/**
	 * 
	 * @Title: getIsStartStr
	 * @Description: TODO(获取isstart中文值)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public String getIsStartStr()
	{
		if("1".equalsIgnoreCase(isStart)) return "是";
		return "否";
	}
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getOpsName() {
		return opsName;
	}

	public void setOpsName(String opsName) {
		this.opsName = opsName;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}
	
}
