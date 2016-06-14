package cn.ccsgroup.ccsframework.sysManager.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccsgroup.ccsframework.httpService.HttpRequestBody;
import cn.ccsgroup.ccsframework.httpService.HttpResponseBody;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysManager.service.SmsService.java]  
 * @ClassName:    [SmsService]   
 * @Description:  [短信service]   
 * @Author:       [lenovo_e430]   
 * @CreateDate:   [2014-5-23 上午10:20:04]   
 * @UpdateUser:   [lenovo_e430(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-5-23 上午10:20:04，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public interface SmsService {
	
	public EasyUIPage getSMSByLoginidAndSysName(String loginid,String systemTpye,EasyUIPage page) ;
	
	public int getSMSCountByLoginidAndSysName(String loginid,String systemTpye);
	
	//修改短信(status)处理状态
	public Boolean updateSmsStatus(int smsID);
	//检查短信状态
	public Integer checkSmsStatus(int smsID);
}
