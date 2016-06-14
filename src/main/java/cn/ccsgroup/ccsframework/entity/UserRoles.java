/**
 * 
 */
package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;



/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.UserManager.java]  
 * @ClassName:    [UserManager]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [zengheng]   
 * @CreateDate:   [2013-12-10 下午03:22:51]   
 * @UpdateUser:   [zengheng(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-10 下午03:22:51，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
public class UserRoles extends BaseBean implements Serializable{

	
	private static final long serialVersionUID = 213211L;
	
	private Integer id;
	private Integer userid;
	private Integer roleid;
	private Integer status;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
