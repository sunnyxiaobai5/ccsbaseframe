package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.TypeList.java]  
 * @ClassName:    [TypeList]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [kuan]   
 * @CreateDate:   [2014-1-8 下午02:11:19]   
 * @UpdateUser:   [kuan(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-8 下午02:11:19，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
public class TypeList extends BaseBean implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -7038744693995357907L;
	private Integer id;
	private Integer regioncode;
	private String kind;
	private String typename;
	private String type;
	private String fullname;
	private String typecode;
	private String ident;
	private Integer porder;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(Integer regioncode) {
		this.regioncode = regioncode;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getIdent() {
		return ident;
	}
	public void setIdent(String ident) {
		this.ident = ident;
	}
	public Integer getPorder() {
		return porder;
	}
	public void setPorder(Integer porder) {
		this.porder = porder;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
