/**********************************************************************   
 * <p>文件名：Holiday.java </p>
 * <p>文件描述：TODO(描述该文件做什么) 
 * @project_name：CCSBaseFrame
 * @author Alenfive  
 * @date 2014-1-26 上午10:27:55 
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2013 
 *                    All Rights Reserved.
*/
package cn.ccsgroup.ccsframework.entity;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.Holiday.java]  
 * @ClassName:    [Holiday]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [Alenfive]   
 * @CreateDate:   [2014-1-26 上午10:27:55]   
 * @UpdateUser:   [Alenfive(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-26 上午10:27:55，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */


public class Holiday {
	private Integer id;
	private String regioncode;
	private String sysinsttime;
	private String sysedittime;
	private String sysdeltime;
	private String day;
	private String cause;
	private String isholiday = "否";
	private Integer status = 1;		//操作码，如果为0删除,2该月作默认傎操作
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getIsholiday() {
		return isholiday;
	}
	public void setIsholiday(String isholiday) {
		if(isholiday!=null)
			this.isholiday = isholiday;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
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
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		return result;
	}*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Holiday other = (Holiday) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		return true;
	}
	
	
}
