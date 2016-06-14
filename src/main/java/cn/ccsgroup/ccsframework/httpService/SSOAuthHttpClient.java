package cn.ccsgroup.ccsframework.httpService;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.SessionEntity;
import cn.ccsgroup.ccsframework.utils.PropertitiesManagement;
import cn.ccsgroup.ccsframework.utils.StringUtil;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.httpService.SSOAuthHttpClient.java]  
 * @ClassName:    [SSOAuthHttpClient]   
 * @Description:  [SSO登录权限认证服务 调用client]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-2-18 下午3:21:54]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-2-18 下午3:21:54，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class SSOAuthHttpClient extends CCSHttpClient{

	private static final String  servicePath = PropertitiesManagement.getResouceString("CASServer");
	private static Map<String,String> map = new HashMap<String,String>();
	static {
		map.put("S001", servicePath + "/rest/SSOAuth/getSession");
		map.put("S002", servicePath + "/rest/SSOAuth/check");
		map.put("S003", servicePath + "/rest/SSOAuth/update");
		map.put("L001", servicePath + "/rest/SSOAuth/login");
		map.put("L002", servicePath + "/rest/SSOAuth/logout");
	}
	private String url ;
	private String type;

	/**
	 * arr[0] 存放S001,S002....
	 * arr[1] 存放值 GET用STRING POST用MAP
	 * 	 */
	@Override
	protected Object bulidParam(Object[] arr) {
		// TODO Auto-generated method stub
		type = (String) arr[0];
		url = map.get(type);
		if(StringUtil.isBlank(url))
			return "";
		else
			return arr[1];
	}

	@Override
	protected HttpClientResultBean formatResults(HttpClientResultBean result) {
		// TODO Auto-generated method stub
		if(type.equals("L001")){
			if(result.isSuccess())
				result.setResult(new GsonBuilder().create().fromJson(getResResult(), new TypeToken<SessionEntity>() {
				}.getType()));
		}else if(type.equals("S001")){
			if(result.isSuccess())
				result.setResult(new GsonBuilder().create().fromJson(getResResult(), new TypeToken<SessionBean>() {
				}.getType()));
		}else{
			result.setResult(getResResult());
		}
		return result;
	}

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return this.url;
	}

}
