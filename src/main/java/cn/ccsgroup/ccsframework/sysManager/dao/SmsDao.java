package cn.ccsgroup.ccsframework.sysManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.entity.SMS;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

@Repository("smsDao")
public class SmsDao extends BaseDaoImpl{
	
	public List<SMS> getSMSByLoginidAndSysName(String loginid,String systemTpye,EasyUIPage page) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("loginid", loginid);
		map.put("systemTpye", systemTpye);
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());

		return this.queryForList("SMS.cutpage",map);
	}
	
	public int getSMSCountByLoginidAndSysName(String loginid,String systemTpye){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("loginid", loginid);
		map.put("systemTpye", systemTpye);
		String count = this.queryForObject("SMS.finCount",map).toString();
		int smsCount = Integer.parseInt(count);
		return smsCount;
	}
	
	//修改短信(status)处理状态
	public Boolean updateSMSStatus(int smsID,int status){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("smsID", smsID);
		map.put("status", status);
		int i = (Integer)this.getSqlMapClientTemplate().update("SMS.updateStatus", map);
		
		return i>0?true:false;
	}

	//检查短信状态
	public Integer checkSmsStatus(int smsID){
		int status = (Integer)this.getSqlMapClientTemplate().queryForObject("SMS.checkSmsStatus",smsID);
		
		return status;
	}
}
