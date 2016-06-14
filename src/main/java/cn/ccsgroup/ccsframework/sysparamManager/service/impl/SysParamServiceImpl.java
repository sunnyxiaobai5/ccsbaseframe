package cn.ccsgroup.ccsframework.sysparamManager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.SysParam;
import cn.ccsgroup.ccsframework.sysparamManager.dao.SysParamDao;
import cn.ccsgroup.ccsframework.sysparamManager.service.SysParamService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
@Service("SysParamService")
public class SysParamServiceImpl implements SysParamService {

	@Resource(name="SysParamDao")
	private SysParamDao sysparamdao;
	public void setSysparamdao(SysParamDao sysparamdao) {
		this.sysparamdao = sysparamdao;
	}
	
	@Override
	public EasyUIPage querySysParamList(EasyUIPage page) {
		page.setRows(sysparamdao.querySysParamList(page));
		page.setTotal(sysparamdao.querySysParamListCounts());
		return page;
	}

	@Override
	public SysParam querySysParamById(Integer id) {
		return sysparamdao.querySysParamById(id);
	}

	@Override
	public List<LogiSystem> queryLoginSystemList() {
		return sysparamdao.queryLoginSystemList();
	}

	@Override
	public boolean insertSysParam(SysParam param) {
		return sysparamdao.insertSysParam(param);
	}

	@Override
	public boolean updateSysParam(SysParam param) {
		return sysparamdao.updateSysParam(param);
	}

	@Override
	public boolean deleteSysParam(Integer id) {
		return sysparamdao.deleteSysParam(id);
	}
	
}
