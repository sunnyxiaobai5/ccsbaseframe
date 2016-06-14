package cn.ccsgroup.ccsframework.operationLog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.base.dao.MultiDataSource;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.OperationLog;
import cn.ccsgroup.ccsframework.operationLog.dao.OperationLogDao;
import cn.ccsgroup.ccsframework.operationLog.service.OperationLogService;
import cn.ccsgroup.ccsframework.utils.DateTimeHelper;


@Service("operationLogService")
@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
public class OperationLogServiceImpl implements OperationLogService {
	
	@Resource(name="operationLogDao")
	private OperationLogDao operationLogDao;


	@Override
	public void removeOperationLogById(String logId) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public Integer insertOperationLog(String moduleId, String operationId,String operationType,
			String paras, Boolean result, SessionBean sessionBean ,String exMessage) {
		// TODO Auto-generated method stub
		OperationLog log = new OperationLog(moduleId,operationId,result?AppConst.SUCCESS:AppConst.FAIL,sessionBean.getUserIp() );
		log.setOpeParam(paras);
		log.setLoginId(sessionBean.getLoginid());
		log.setOrgId(Long.valueOf(sessionBean.getOrganization().getId()));
		log.setRegionCode(sessionBean.getRegionCode());
		log.setStatus(AppConst.STATUS_ENABLE);
		log.setSysId(Long.valueOf(sessionBean.getSystem().getDm()));
		log.setOpeDate(DateTimeHelper.getNowTime_21());
		log.setType(operationType);
		if(MultiDataSource.defaultDataSource())
			operationLogDao.getSqlMapClientTemplate().setDataSource(MultiDataSource.getDefaultDataSource());
		return (Integer) operationLogDao.insertForObject("OpeLog.insertLog", log);
		
	}

}
