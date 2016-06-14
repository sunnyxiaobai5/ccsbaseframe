/**********************************************************************   
 * <p>文件名：HttpRequestBody.java </p>
 * <p>文件描述：TODO(描述该文件做什么) 
 * @project_name：CCSInterface
 * @author yangfan  
 * @date 2014年4月29日 上午9:53:59 
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014 
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.httpService;

import java.io.Serializable;
import java.util.Map;

/******************************************************************************
 * @Package: [cn.ccsgroup.ccsframework.httpService.HttpRequestBody.java]
 * @ClassName: [HttpRequestBody]
 * @Description: [REST API 公共请求头]
 * @Author: [yangfan]
 * @CreateDate: [2014年4月29日 上午9:53:59]
 * @UpdateUser: [yangfan(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2014年4月29日 上午9:53:59，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]
 * @Version: [v1.0]
 */

public class HttpRequestBody implements Serializable {

	private static final long serialVersionUID = -6634653494662614388L;

	// 用户名
	private String loginid;
	// 密码
	private String password;
	// 请求体
	private Object data;
	// 区域编码
	private String regioncode;
	// usbKey
	private String key;
	// 加密需要验证的签名
	private String signature;
	// 时间戳
	private String timestamp;
	// 随机数
	private String nonce;
	// base64编码后的字符串
	private String authenticationKey;

	public String getAuthenticationKey() {
		return authenticationKey;
	}

	public void setAuthenticationKey(String authenticationKey) {
		this.authenticationKey = authenticationKey;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public Map<String, Object> convertData2Map() {
		Map<String, Object> map = (Map<String, Object>) data;
		map.put("loginid", this.getLoginid());
		map.put("regioncode", this.getRegioncode());
		return map;
	}

}
