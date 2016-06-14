package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.bean]  
 * @ClassName:    [Operation]   
 * @Description:  [操作]   
 * @Author:       [tuliangyu]   
 * @CreateDate:   [2013-12-10]   
 * @UpdateUser:   [tuliangyu，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-10，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class RegionCode implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 流水号Id
	 */
	private String id;

	/**
	 * 地区编码
	 */
	private String regionCode;
	
	/**
	 * 地区名称
	 */
	private String regionName;
	
	/**
	 * 地区父ID
	 */
	private String regionPId;
	
	/**
	 * 地区简称
	 */
	private String regionAbb;
	
	/**
	 * 拼音全拼
	 * */
	private String retrievalA;
	
	/**
	 * 五笔全拼
	 * */
	private String retrievalB;
	
	/**
	 * 状态
	 */
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionPId() {
		return regionPId;
	}

	public void setRegionPId(String regionPId) {
		this.regionPId = regionPId;
	}

	public String getRegionAbb() {
		return regionAbb;
	}

	public void setRegionAbb(String regionAbb) {
		this.regionAbb = regionAbb;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRetrievalA() {
		return retrievalA;
	}

	public void setRetrievalA(String retrievalA) {
		this.retrievalA = retrievalA;
	}

	public String getRetrievalB() {
		return retrievalB;
	}

	public void setRetrievalB(String retrievalB) {
		this.retrievalB = retrievalB;
	}	
	
}
