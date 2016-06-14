package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.OperationLog.java]  
 * @ClassName:    [OperationLog]   
 * @Description:  [日志列表Bean，对应表格]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-3 下午5:15:25]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-3 下午5:15:25，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class OperationLog implements Serializable {

	private static final long serialVersionUID = 497915217123142117L;
	public OperationLog(){}
	public OperationLog(String opeMod, String opeFunc, String flag, String ip) {
		this.opeMod = opeMod;
		this.opeFunc = opeFunc;
		this.flag = flag;
		this.ip = ip;
	}
	// 日志编号
	private long id = 0;
	
	private String opeMod = null;
	
	// 操作功能 
	private String opeFunc = null;
	
	// 操作返回值  成功：1 ，失败：0   
	private String flag = null;

	// 操作人员ID
	private String loginId = null;
	
	private long orgId ;
	
	private String regionCode = null;
	
	private String opeParam = null;

	// 操作时间
	private String opeDate = null;

	// 操作IP
	private String ip = null;
	
	private long sysId  ;
	
	private String sysInstTime = null;
	
	private String sysEditTime = null;
	
	private String sysDelTime = null;
	
	private int status = 0;
	
	private String type = null;
	
	public void setUserInfo(SessionBean sb) {
		this.setLoginId(sb.getLoginid());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOpeMod() {
		return opeMod;
	}

	public void setOpeMod(String opeMod) {
		this.opeMod = opeMod;
	}

	public String getOpeFunc() {
		return opeFunc;
	}

	public void setOpeFunc(String opeFunc) {
		this.opeFunc = opeFunc;
	}


	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getOpeParam() {
		return opeParam;
	}

	public void setOpeParam(String opeParam) {
		this.opeParam = opeParam;
	}

	public String getOpeDate() {
		return opeDate;
	}

	public void setOpeDate(String opeDate) {
		this.opeDate = opeDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getSysId() {
		return sysId;
	}

	public void setSysId(long sysId) {
		this.sysId = sysId;
	}

	public String getSysInstTime() {
		return sysInstTime;
	}

	public void setSysInstTime(String sysInstTime) {
		this.sysInstTime = sysInstTime;
	}

	public String getSysEditTime() {
		return sysEditTime;
	}

	public void setSysEditTime(String sysEditTime) {
		this.sysEditTime = sysEditTime;
	}

	public String getSysDelTime() {
		return sysDelTime;
	}

	public void setSysDelTime(String sysDelTime) {
		this.sysDelTime = sysDelTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
