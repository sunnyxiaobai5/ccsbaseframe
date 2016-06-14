/**
 * 
 */
package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;
import java.util.List;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu]  
 * @ClassName:    [Model]   
 * @Description:  [系统模块名称]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-11-15]   
 * @UpdateUser:   [hr，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class Model extends BaseBean implements Serializable{

	private static final long serialVersionUID = -123211L;
	
	/**
	 * 模块主键
	 */
	private String id;
	
	/**
	 * 模块名称
	 */
	private String modName;
	
	/**
	 * 模块描述
	 */
	private String description;
	
	/**
	 * 是否有效
	 */
	private Integer isActive;
	
	/**
	 * 是否能修改
	 */
	private Integer isInside;
	
	/**
	 * 生成时间
	 */
	private String sysinsttime;
	
	/**
	 * 修改时间
	 */
	private String sysedittime;
	
	/**
	 * 删除时间
	 */
	private String sysdeltime;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 
	 * 标识
	 */
	private String alias;
	
	/**
	 * 临时标识
	 */
	private Integer sortNo;
	
	
	private Integer isChecked;

	public Integer getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Integer isChecked) {
		this.isChecked = isChecked;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	private List<Func> fList;
	
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




	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
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

	public List<Func> getfList() {
		return fList;
	}

	public void setfList(List<Func> fList) {
		this.fList = fList;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
}
