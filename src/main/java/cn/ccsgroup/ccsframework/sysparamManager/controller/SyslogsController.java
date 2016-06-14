package cn.ccsgroup.ccsframework.sysparamManager.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccsgroup.ccsframework.entity.SysLogs;
import cn.ccsgroup.ccsframework.entity.TypeList;
import cn.ccsgroup.ccsframework.sysparamManager.service.SysLogsService;
import cn.ccsgroup.ccsframework.sysparamManager.service.TypeListService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.controller.SyslogsController.java]  
 * @ClassName:    [SyslogsController]   
 * @Description:  [操作日志控制类]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2014-1-23 下午05:29:46]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2014-1-23 下午05:29:46]   
 * @UpdateRemark: [新建]  
 * @Version:      [v1.0] 
 */
@Controller
@RequestMapping("/SysLogsController")
public class SyslogsController {

	@Resource(name="SysLogsService")
	private SysLogsService sysLogsService;
	public void setSysLogsService(SysLogsService sysLogsService) {
		this.sysLogsService = sysLogsService;
	}

	@RequestMapping(value="/viewLogs")
	public String viewLogs(){
		return "syslogsManager/syslogs";
	}
	
	@RequestMapping(value="/getLogsList")
	public @ResponseBody EasyUIPage getLogsList(@RequestParam(value="rows")Integer rows,
			EasyUIPage page){
		page.setPagePara(rows);
		page = sysLogsService.queryLogsList(page);
		return page;
	}
	@RequestMapping(value="/view",method = RequestMethod.GET)
	public ModelAndView viewLogs(@RequestParam(value="id")Integer id){
		ModelAndView mv = new ModelAndView("syslogsManager/viewlogs");
		SysLogs logs = sysLogsService.queryLogsById(id);
		mv.addObject("logs", logs);
		return mv;
	}
}
