package cn.ccsgroup.ccsframework.authentication.service;

import cn.ccsgroup.ccsframework.httpService.HttpRequestBody;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.authentication.service.IAuthenticationService.java]  
 * @ClassName:    [IAuthenticationService]   
 * @Description:  [rest权限验证服务接口]   
 * @Author:       [wei]   
 * @CreateDate:   [2014-5-28 下午5:33:19]   
 * @UpdateUser:   [wei(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-5-28 下午5:33:19，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public interface IAuthenticationService {
	
	public boolean validLoginIdPassword(HttpRequestBody httpRequestBody);
	
	public boolean validSignature(HttpRequestBody httpRequestBody);

}
