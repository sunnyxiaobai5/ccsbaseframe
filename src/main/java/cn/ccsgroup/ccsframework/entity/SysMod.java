package cn.ccsgroup.ccsframework.entity;

import java.io.Serializable;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.SysMod.java]  
 * @ClassName:    [SysMod]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-12-16 下午02:50:00]   
 * @UpdateUser:   [hr(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-16 下午02:50:00，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class SysMod extends BaseBean implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -6321261688012437225L;
	
	/**
	 * 主键
	 */
	private String id;
		
	/**
	 * 
	 * 系统ID
	 */
	private String sysid;
	
	
	/**
	 * 模块ID
	 */
	private String modid;
	
	/**
	 * 序号
	 */
	private String sortno;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getModid() {
		return modid;
	}

	public void setModid(String modid) {
		this.modid = modid;
	}

	public String getSortno() {
		return sortno;
	}

	public void setSortno(String sortno) {
		this.sortno = sortno;
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
	
	
	

}
