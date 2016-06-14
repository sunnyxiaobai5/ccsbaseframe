package cn.ccsgroup.ccsframework.base.entity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

import cn.ccsgroup.ccsframework.utils.JsonDateSerializer;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.BaseBean.java]  
 * @ClassName:    [BaseBean]   
 * @Description:  [基础Bean 用于统一对象ID，便于拦截]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-2 下午5:22:42]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-3 下午4:22:42，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [增加属性用于日志操作]  
 * @Version:      [v1.0]
 */
public class BaseBean implements Serializable {

	private static final long serialVersionUID = 4480183933256919398L;

	//操作返回值   默认为true
	private Boolean result = true;
	
	// 创建者ID
	private String createrUser = null;

	private Integer createrId;
	
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	// 创建时间
	private Date createrTime = null;

	// 创建IP
	private String createrIp = null;

	// 更新者ID
	private String updateUser = null;

	// 更新时间
	private Date updateTime = null;

	// 更新IP
	private String updateIp = null;
	
	private Date sysInstTime;
	
	private Date sysEditTime;
	
	private Date sysDelTime;

	// 更新次数
	private int modifyCount = 0;

	// 删除标识
	private String delFlg = null;
	//查询时间起
	private String cxsjq = null;
	//查询时间止
	private String cxsjz = null;
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getCreaterUser() {
		return createrUser;
	}
	public void setCreaterUser(String createrUser) {
		this.createrUser = createrUser;
	}
	public Date getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(Date createrTime) {
		this.createrTime = createrTime;
	}
	public String getCreaterIp() {
		return createrIp;
	}
	public void setCreaterIp(String createrIp) {
		this.createrIp = createrIp;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateIp() {
		return updateIp;
	}
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public int getModifyCount() {
		return modifyCount;
	}
	public void setModifyCount(int modifyCount) {
		this.modifyCount = modifyCount;
	}
	public String getDelFlg() {
		return delFlg;
	}
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}
	public String getCxsjq() {
		return cxsjq;
	}
	public void setCxsjq(String cxsjq) {
		this.cxsjq = cxsjq;
	}
	public String getCxsjz() {
		return cxsjz;
	}
	public void setCxsjz(String cxsjz) {
		this.cxsjz = cxsjz;
	}
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getSysInstTime() {
		return sysInstTime;
	}
	public void setSysInstTime(Date sysInstTime) {
		this.sysInstTime = sysInstTime;
	}
	public Date getSysEditTime() {
		return sysEditTime;
	}
	public void setSysEditTime(Date sysEditTime) {
		this.sysEditTime = sysEditTime;
	}
	public Date getSysDelTime() {
		return sysDelTime;
	}
	public void setSysDelTime(Date sysDelTime) {
		this.sysDelTime = sysDelTime;
	}
	
	
}
