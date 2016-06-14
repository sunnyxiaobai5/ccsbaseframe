package cn.ccsgroup.ccsframework.httpService;

import java.io.Serializable;
/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.httpService.HttpClientResultBean.java]  
 * @ClassName:    [HttpClientResultBean]   
 * @Description:  [自定义httpClient返回Bean]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2014-2-18 下午3:06:12]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-2-18 下午3:06:12，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class HttpClientResultBean implements Serializable{
	
	public HttpClientResultBean() {
	}

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5467856455882220908L;

	private boolean isSuccess = false;
	
	private Object result;
	
	private String errorMsg;
	
	private int errorStatus;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(int errorStatus) {
		this.errorStatus = errorStatus;
	}

}
