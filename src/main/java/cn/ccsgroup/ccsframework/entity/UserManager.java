/**
 * 
 */
package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
public class UserManager extends BaseBean implements Serializable{

	
	private static final long serialVersionUID = 213211L;
	
	private Integer id;
	private String loginid;
	private String userName;
	private String gender;
	private String certifykind;
	private String password;
	private String usertype;
	private String usercode;
	private String idtype;
	private String ownerid;
	private String certaddr;
	private String nationality;
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date birthdate;
	private String phone;
	private String knowledge;
	private String email;
	private String address;
	private String fax;
	private String note;
	private Integer isactive;
	private Integer status;
	
	private String flag;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCertifykind() {
		return certifykind;
	}
	public void setCertifykind(String certifykind) {
		this.certifykind = certifykind;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getIdtype() {
		return idtype;
	}
	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}
	public String getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(String ownerid) {
		this.ownerid = ownerid;
	}
	public String getCertaddr() {
		return certaddr;
	}
	public void setCertaddr(String certaddr) {
		this.certaddr = certaddr;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public Date getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getIsactive() {
		return isactive;
	}
	public void setIsactive(Integer isactive) {
		this.isactive = isactive;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
