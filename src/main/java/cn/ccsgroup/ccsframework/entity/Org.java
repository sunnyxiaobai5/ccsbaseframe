/**
 * 
 */
package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

/**
 * @Package:      [cn.ccsgroup.ccsframework.entity]  
 * @ClassName:    [Org]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-12-25]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-25，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
public class Org extends BaseBean implements Serializable{

	private static final long serialVersionUID = -12231L;
	
	/*主键*/
	private Integer id;

	/*父类ID*/
	private Integer parentId;
	
	/*机构名称*/
	private String orgName;
	
	/*机构编码*/
	private String orgCode;
	
	/*机构简称*/
	private String shortName;
	
	/*机构类型*/
	private String orgType;
	
	/*经济类型*/
	private String economicType;
	
	/*营业执照*/
	private String patentNumber;
	
	/*发证机构*/
	private String registingOrgan;
	
	/*工商注册时间*/
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date registerDate;
	
	/*开始经营期限时间*/
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date registerBDate;
	
	/*结束经营期限时间*/
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date registerEDate;
	
	/*工商注册地址*/
	private String registerAddress;
	
	/*登记时间*/
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date rDate;
	
	/*税务证号*/
	private String taxNumber;
	
	/*机构所在区域*/
	private String registerRegion;
	
	/*办公地址*/
	private String address;
	
	/*邮编*/
	private String postCode;
	
	/*传真*/
	private String fix;
	
	/*电子邮件*/
	private String email;
	
	/*网站*/
	private String webSite;
	
	/*联系人*/
	private String contact;
	
	/*联系电话*/
	private String phone;
	
	/*注册资本单位*/
	private String currencyUnit;
	
	/*注册资本*/
	private Long registerMoney;
	
	/*经营范围*/
	private String dealScope;
	
	/*开户银行*/
	private String registerBank;
	
	/*账户*/
	private String account;
	
	/*帐号*/
	private String accountNum;
	
	/*法人代表*/
	private String legalMan;
 	
	/*法人手机*/
	private String legalMobile;
	
	/*法人电话*/
	private String legalPhone;
	
	/*法人性别*/
	private String legalSex;
	
	/*法人传真*/
	private String legalFix;
	
	/*法人证件号码*/
	private String legalOwnerId;
	
	/*法人邮件*/
	private String legalEmail;
	
	/*区域编码*/
	private String regionCode;
	
	/*创建人*/
	private String createrUser;
	
	/*状态*/
	private Integer status;
	
	/*组织机构顺序*/
	private Integer sort;
	
	/*业务属性   树是否打开*/
	private String open;
	
	/*业务属性   树的图标*/
	private String icon = "../images/icon-realdepartment.png";

    //设置节点的 checkbox / radio 是否禁用 [setting.check.enable = true 时有效]
    private String chkDisabled = "false";

    private Integer isVirtual = 0;

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(String chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getEconomicType() {
		return economicType;
	}

	public void setEconomicType(String economicType) {
		this.economicType = economicType;
	}

	public String getPatentNumber() {
		return patentNumber;
	}

	public void setPatentNumber(String patentNumber) {
		this.patentNumber = patentNumber;
	}

	public String getRegistingOrgan() {
		return registingOrgan;
	}

	public void setRegistingOrgn(String registingOrgan) {
		this.registingOrgan = registingOrgan;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getRegisterBDate() {
		return registerBDate;
	}

	public void setRegisterBDate(Date registerBDate) {
		this.registerBDate = registerBDate;
	}

	public Date getRegisterEDate() {
		return registerEDate;
	}

	public void setRegisterEDate(Date registerEDate) {
		this.registerEDate = registerEDate;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public Date getrDate() {
		return rDate;
	}

	public void setrDate(Date rDate) {
		this.rDate = rDate;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getRegisterRegion() {
		return registerRegion;
	}

	public void setRegisterRegion(String registerRegion) {
		this.registerRegion = registerRegion;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getFix() {
		return fix;
	}

	public void setFix(String fix) {
		this.fix = fix;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public Long getRegisterMoney() {
		return registerMoney;
	}

	public void setRegisterMoney(Long registerMoney) {
		this.registerMoney = registerMoney;
	}

	public String getDealScope() {
		return dealScope;
	}

	public void setDealScope(String dealScope) {
		this.dealScope = dealScope;
	}

	public String getRegisterBank() {
		return registerBank;
	}

	public void setRegisterBank(String registerBank) {
		this.registerBank = registerBank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getLegalMan() {
		return legalMan;
	}

	public void setLegalMan(String legalMan) {
		this.legalMan = legalMan;
	}

	public String getLegalMobile() {
		return legalMobile;
	}

	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}

	public String getLegalPhone() {
		return legalPhone;
	}

	public void setLegalPhone(String legalPhone) {
		this.legalPhone = legalPhone;
	}

	public String getLegalSex() {
		return legalSex;
	}

	public void setLegalSex(String legalSex) {
		this.legalSex = legalSex;
	}

	public String getLegalFix() {
		return legalFix;
	}

	public void setLegalFix(String legalFix) {
		this.legalFix = legalFix;
	}

	public String getLegalOwnerId() {
		return legalOwnerId;
	}

	public void setLegalOwnerId(String legalOwnerId) {
		this.legalOwnerId = legalOwnerId;
	}

	public String getLegalEmail() {
		return legalEmail;
	}

	public void setLegalEmail(String legalEmail) {
		this.legalEmail = legalEmail;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getCreaterUser() {
		return createrUser;
	}

	public void setCreaterUser(String createrUser) {
		this.createrUser = createrUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

}
