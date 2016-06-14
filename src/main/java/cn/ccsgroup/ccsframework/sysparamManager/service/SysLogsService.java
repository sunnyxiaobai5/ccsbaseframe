package cn.ccsgroup.ccsframework.sysparamManager.service;




import java.util.List;

import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.entity.SysLogs;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;


@TypeAnnotation("操作日志")
public interface SysLogsService {
	
	public EasyUIPage queryLogsList(EasyUIPage page);
	public SysLogs queryLogsById(Integer id);
}
