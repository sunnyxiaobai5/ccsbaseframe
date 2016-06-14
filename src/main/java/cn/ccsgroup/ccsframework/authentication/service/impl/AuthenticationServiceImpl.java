package cn.ccsgroup.ccsframework.authentication.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ccsgroup.ccsframework.authentication.service.IAuthenticationService;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.httpService.HttpRequestBody;
import cn.ccsgroup.ccsframework.sysManager.dao.UserPowerDao;
import cn.ccsgroup.ccsframework.utils.EncryptUtil;
/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.authentication.service.impl.AuthenticationServiceImpl.java]  
 * @ClassName:    [AuthenticationServiceImpl]   
 * @Description:  [rest权限验证服务]   
 * @Author:       [wei]   
 * @CreateDate:   [2014-5-29 下午3:52:46]   
 * @UpdateUser:   [wei(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-5-29 下午3:52:46，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Service("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements IAuthenticationService {
	
	private static final String token = "FZHNNS1T7QPNMPVLNRQ5YKZNSSAVY6BD";
	
	@Resource(name="userPowerDao")
	private UserPowerDao userPowerDao;

	@Override
	public boolean validLoginIdPassword(HttpRequestBody httpRequestBody) {
		// TODO Auto-generated method stub
		String loginId = httpRequestBody.getLoginid();
		String password = httpRequestBody.getPassword();
		
		UserManager user = userPowerDao.checkUser(loginId, password);
		
		if(user != null){
			return true;
		}
		return false;
	}

	@Override
	public boolean validSignature(HttpRequestBody httpRequestBody) {
		// TODO Auto-generated method stub
		String signature = httpRequestBody.getSignature();
		String timestamp = httpRequestBody.getTimestamp();	
		String nonce = httpRequestBody.getNonce();
		System.out.println(signature+","+timestamp+","+nonce);
		return EncryptUtil.validate(signature, timestamp, nonce, token, EncryptUtil.SHA1);		
	}

}
