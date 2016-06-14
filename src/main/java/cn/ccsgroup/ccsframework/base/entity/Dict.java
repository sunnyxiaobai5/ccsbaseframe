package cn.ccsgroup.ccsframework.base.entity;

import java.io.Serializable;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.base.entity.Dict.java]  
 * @ClassName:    [Dict]   
 * @Description:  [字典]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-1-9 下午4:56:17]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-9 下午4:56:17，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class Dict implements Serializable {

	private static final long serialVersionUID = -2953551438333054670L;
	private Integer id;
	private String dm;
	private String mc;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}

}
