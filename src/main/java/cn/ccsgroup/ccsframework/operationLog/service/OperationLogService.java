package cn.ccsgroup.ccsframework.operationLog.service;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.operationLog.service.OperationLogService.java]  
 * @ClassName:    [OperationLogService]   
 * @Description:  [操作日志Service接口]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-3 下午4:43:16]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-3 下午4:43:16，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public interface OperationLogService {

	
	/**
	 * 
	 * @Title: removeOperationLogById
	 * @Description: TODO(根据ID删除日志)
	 * @param @param logId    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void removeOperationLogById(String logId);

	/**
	 * @param retVal 
	 * 
	 * @Title: insertOperationLog
	 * @Description: TODO(保存操作日志)
	 * @param moduleId   模块ID
	 * @param operationId 操作ID
	 * @param @param paras 传入参数
	 * @param @param result 是否成功
	 * @param @param sessionBean  复制的用户session
	 * @param exMessage 错误信息
	 * @param retVal 返回值
	 * @return void    返回类型
	 * @throws
	 */
	public Integer insertOperationLog(String moduleId, String operationId,String operationType,
			String paras, Boolean result, SessionBean sessionBean , String exMessage);

}
