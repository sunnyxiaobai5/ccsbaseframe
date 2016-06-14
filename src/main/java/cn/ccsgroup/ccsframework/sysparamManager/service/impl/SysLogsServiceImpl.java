package cn.ccsgroup.ccsframework.sysparamManager.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ccsgroup.ccsframework.entity.SysLogs;
import cn.ccsgroup.ccsframework.sysparamManager.dao.SysLogsDao;
import cn.ccsgroup.ccsframework.sysparamManager.service.SysLogsService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
@Service("SysLogsService")
public class SysLogsServiceImpl implements SysLogsService {

	@Resource(name="SysLogsDao")
	private SysLogsDao sysLogsDao;
	public void setSysLogsDao(SysLogsDao sysLogsDao) {
		this.sysLogsDao = sysLogsDao;
	}


	@Override
	public EasyUIPage queryLogsList(EasyUIPage page) {
		page.setRows(sysLogsDao.queryLogsList(page));
		page.setTotal(sysLogsDao.queryLogsListCounts());
		return page;
	}


	@Override
	public SysLogs queryLogsById(Integer id) {
		return sysLogsDao.queryLogsById(id);
	}
	
}
