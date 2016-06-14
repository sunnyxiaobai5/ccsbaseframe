package cn.ccsgroup.ccsframework.httpService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.utils.StringUtil;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.httpService.HttpClient.java]  
 * @ClassName:    [HttpClient]   
 * @Description:  [Http调用基类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-2-11 上午9:52:54]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-2-11 上午9:52:54，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public abstract class CCSHttpClient {

	private Logger logger = Logger.getLogger(this.getClass());

	private Object reqParam ;  
	private HttpClientResultBean result = new HttpClientResultBean();
	private String resResult ;
	private String type ;
	private Cookie sessionCookie = null;
	public static final String POST ="POST";
	public static final String GET ="GET";
	/**
	 * 
	 * @Title: bulidParam
	 * @Description: TODO(构造入参)
	 * @param @param arr
	 * @param @return    设定文件
	 * @return Object    返回类型 GET为String 例：userid=1&pwd=2  POST为 map<string,string>
	 * @throws
	 */
	protected abstract Object bulidParam(Object[] arr);
	/**
	 * 
	 * @Title: formatResults
	 * @Description: TODO(解析返回结果)
	 * @param @param result
	 * @param @return    设定文件
	 * @return HttpClientResultBean    返回类型
	 * @throws
	 */
	protected abstract HttpClientResultBean formatResults(HttpClientResultBean result);
	/**
	 * 
	 * @Title: getUrl
	 * @Description: TODO(设置调用URL)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	protected abstract String getUrl();

	/**
	 * 
	 * @Title: getResult
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param arr 
	 * @param @param type POST GET
	 * @param @return    设定文件
	 * @return HttpClientResultBean    返回类型
	 * @throws
	 */
	public HttpClientResultBean getResult(Object[] arr,String type){
		setType(type);
		try {
			reqParam =  bulidParam(arr);
			if (StringUtil.isBlank(reqParam)){
				result.setErrorStatus(500);
				result.setErrorMsg("未知的请求地址！");
			}else{
				send(getUrl(), reqParam);
				if(resResult!=null && resResult != "")
					resResult = URLDecoder.decode(resResult, "UTF-8");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
		return formatResults(result);
	}

	private void setType(String type) {
		// TODO Auto-generated method stub
		this.type = type;
	}
	public String getType(){
		return this.type;
	}
	private HttpMethod getMethod(String url,String param) throws IOException{  
		GetMethod get = new GetMethod(url + "?" + param ); 
		get.setRequestHeader("Connection", "close"); 
		//get.addRequestHeader("Accept", "text/plain");
		get.addRequestHeader("Content-Type",  "application/x-www-form-urlencoded;charset=UTF-8");
		//get.releaseConnection();  
		return get;  
	}  

	private  HttpMethod postMethod(String url ,Map<String,String> param) throws IOException{   
		PostMethod post = new PostMethod(url);  
		post.setRequestHeader("Connection", "close");
		post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");    
		for(Map.Entry<String, String> entry : param.entrySet()){
			post.addParameter(entry.getKey(), entry.getValue());
		}
		//post.releaseConnection();  
		return post;  
	}  

	@SuppressWarnings("unchecked")
	private void send(String url,Object param) throws IOException {
		HttpClient httpClient = new HttpClient();  
		HttpMethod method = null;
		try {
			if(POST.equals(type.toUpperCase())){
				method = postMethod(url, (Map<String, String>) param);
			}else if(GET.equals(type.toUpperCase())){
				method = getMethod(url, (String) param);
			}
			httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false); 
			httpClient.executeMethod(method);
			Cookie[] c = httpClient.getState().getCookies();
			for (int i = 0; i < c.length; i++) {
				Cookie cookie = c[i];
				if(AppConst.SESSION_KEY.equals(cookie.getName())){
					this.sessionCookie = cookie;
					break ;
				}
			}
			int statusCode = method.getStatusCode();
			switch (statusCode) {
			case HttpStatus.SC_OK:
				resResult = inputStream2String(method.getResponseBodyAsStream());
				result.setSuccess(true);
				break;
			case HttpStatus.SC_NOT_FOUND:
				result.setErrorStatus(404);
				result.setErrorMsg("未知的请求地址！");
				break;
			case HttpStatus.SC_REQUEST_TIMEOUT:
				result.setErrorStatus(500);
				result.setErrorMsg("网络连接超时,请稍后再试！！");
				break;
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				result.setErrorStatus(500);
				result.setErrorMsg("系统内部错误！");
				break;
			default:
				result.setErrorStatus(500);
				result.setErrorMsg("系统内部错误！");
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e ;
		}finally{
			if (method != null) 
				method.releaseConnection();
		}
	}
	public Cookie getSessionCookie() {
		return sessionCookie;
	}
	public String getResResult() {
		return resResult;
	}
	public HttpClientResultBean getResult() {
		return result;
	}
	
	String inputStream2String(InputStream is) throws IOException{
	    BufferedReader in = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	    StringBuffer buffer = new StringBuffer();
	    String line = "";
	    while ((line = in.readLine()) != null){
	      buffer.append(line);
	    }
	    return buffer.toString();
	}
}
