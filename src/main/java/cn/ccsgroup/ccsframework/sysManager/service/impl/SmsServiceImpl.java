package cn.ccsgroup.ccsframework.sysManager.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccsgroup.ccsframework.httpService.HttpRequestBody;
import cn.ccsgroup.ccsframework.httpService.HttpResponseBody;
import cn.ccsgroup.ccsframework.sysManager.dao.SmsDao;
import cn.ccsgroup.ccsframework.sysManager.service.SmsService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

@Service("smsService")
@Transactional(readOnly=true)
public class SmsServiceImpl implements SmsService{
	
	@Resource(name="smsDao")
	private  SmsDao smsDao;

	@Override//修改短信(status)处理状态
	public Boolean updateSmsStatus(int smsID) {
		return smsDao.updateSMSStatus(smsID, 0);
	}

	@Override//检查短信的status
	public Integer checkSmsStatus(int smsID) {
		return smsDao.checkSmsStatus(smsID);
	}
	
	@Override
	public EasyUIPage getSMSByLoginidAndSysName(String loginid,String systemTpye,EasyUIPage page) {
		page.setRows(smsDao.getSMSByLoginidAndSysName(loginid,systemTpye,page));
		page.setTotal(smsDao.getSMSCountByLoginidAndSysName(loginid,systemTpye));
		
		return page;
	}

	@Override
	public int getSMSCountByLoginidAndSysName(String loginid, String systemTpye) {
		return smsDao.getSMSCountByLoginidAndSysName(loginid,systemTpye);
	}
}
