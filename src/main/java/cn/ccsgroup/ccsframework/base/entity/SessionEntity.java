package cn.ccsgroup.ccsframework.base.entity;

import javax.xml.bind.annotation.XmlRootElement;


/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.entity.SessionEntity.java]  
 * @ClassName:    [SessionEntity]   
 * @Description:  []   
 * @Author:       [kundian.huo]   
 * @CreateDate:   [2014-2-14 下午3:44:25]   
 * @UpdateUser:   [kundian.huo(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-2-14 下午3:44:25，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@XmlRootElement
public class SessionEntity {

	private  int  status ;
	
	private  SessionBean  sessionBean ;

	public SessionEntity(){}

	public SessionEntity(Integer status){
		this.status = status;
	}
	
	public SessionEntity(Integer status ,SessionBean sessionBean){
		this.sessionBean = sessionBean;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
