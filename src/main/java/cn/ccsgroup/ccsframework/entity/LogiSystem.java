/**
 * 
 */
package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;
import java.util.List;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.entity]  
 * @ClassName:    [SysMod]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-11-18]   
 * @UpdateUser:   [hr，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-16，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
public class LogiSystem extends BaseBean implements Serializable {

	private static final long serialVersionUID = -123231L;

	/**
	 * 系统Id
	 */
	private Integer id;
	
	/**
	 * 系统名称
	 */
	private String sysName;
	
	/**
	 * 排序号
	 */
	private String sortNo;

	/**
	 * 系统URL
	 */
	private String sysUrl;
	
	/**
	 * 是否有效
	 */
	private Integer isActive;
	
	/**
	 * 区域编码
	 */
	private String regionCode;
	
	/**
	 * 系统描述
	 */
	private String description;
	
	/**
	 * 系统简称
	 */
	private String briefName;
	
	/**
	 * 系统图标
	 */
	private byte[] sysIcon;
	
	/**
	 * 是否可修改
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





	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}



	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public byte[] getSysIcon() {
		return sysIcon;
	}

	public void setSysIcon(byte[] sysIcon) {
		this.sysIcon = sysIcon;
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

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
		
		
	}
	
	

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}



	public String getBriefName() {
		return briefName;
	}

	public void setBriefName(String briefName) {
		this.briefName = briefName;
	}

	public List<SysMod> getmList() {
		return mList;
	}

	public void setmList(List<SysMod> mList) {
		this.mList = mList;
	}

	
	
	public List<Modfuncs> getMfList() {
		return mfList;
	}

	public void setMfList(List<Modfuncs> mfList) {
		this.mfList = mfList;
	}



	private List<SysMod> mList;
	
	private List<Modfuncs> mfList;
	
}
