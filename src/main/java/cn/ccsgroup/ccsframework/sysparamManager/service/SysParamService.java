package cn.ccsgroup.ccsframework.sysparamManager.service;

import java.util.List;

import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.SysParam;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

@TypeAnnotation("系统参数控制")
public interface SysParamService {
	
	public EasyUIPage querySysParamList(EasyUIPage page);
	
	public SysParam querySysParamById(Integer id);
	
	public List<LogiSystem> queryLoginSystemList();
	
	public boolean insertSysParam(SysParam param);
	
	public boolean updateSysParam(SysParam param);
	
	public boolean deleteSysParam(Integer id);
}
