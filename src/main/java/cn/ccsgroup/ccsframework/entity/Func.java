/**
 * 
 */
package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.bean]  
 * @ClassName:    [Func]   
 * @Description:  [系统功能Bean]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-11-15]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class Func extends BaseBean implements Serializable{

	
	private static final long serialVersionUID = 213211L;

	/**
	 * 功能Id
	 */
	private Integer id;
	
	/**
	 * 功能名称
	 */
	private String funcName;
	
	/**
	 * 父类功能ID
	 */
	private Integer funcPid;
	
	/**
	 * 功能URL
	 */
	private String funcUrl;
	
	/**
	 * 功能描述
	 */
	private String description;
	
	/**
	 * 是否有效
	 */
	private Integer isActive;
	
	/**
	 * 功能类型
	 */
	private Integer funcType;
	
	/**
	 * 是否可修改
	 */
	private Integer isInside;
	
	/**
	 * 状态
	 */
	private Integer status;

	
	/**
	 * 转换类型字符串
	 * @return
	 */
	public String getFuncTypeStr()
	{
		String value = "";
		if(funcType == null || "".equals(funcType)) return "";
		switch(funcType)
		{
			case 1 :
				value = "系统";
				break;
			case 2 :
				value = "框架";
				break;
			case 3 :
				value = "接口";
				break;
			default :
				value = "系统";
				break;
		}
		return value;
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public void setFuncUrl(String funcUrl) {
		this.funcUrl = funcUrl;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getFuncPid() {
		return funcPid;
	}

	public void setFuncPid(Integer funcPid) {
		this.funcPid = funcPid;
	}

	public Integer getFuncType() {
		return funcType;
	}

	public void setFuncType(Integer funcType) {
		this.funcType = funcType;
	}

	public Integer getIsInside() {
		return isInside;
	}

	public void setIsInside(Integer isInside) {
		this.isInside = isInside;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFuncUrl() {
		return funcUrl;
	}

}
