package cn.ccsgroup.ccsframework.sysManager.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.sysManager.service.SmsService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/**
 * ****************************************************************************
 * 
 * @Package: [cn.ccsgroup.ccsframework.sysManager.controller.SmsController.java]
 * @ClassName: [SmsController]
 * @Description: [一句话描述该类的功能]
 * @Author: [linpeng]
 * @CreateDate: [2014-5-27 下午2:13:38]
 * @UpdateUser: [lenovo_e430(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2014-5-27 下午2:13:38，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]
 * @Version: [v1.0]
 */
@Controller
@RequestMapping("/sms")
public class SmsController extends BaseController {

	@Resource(name = "smsService")
	private SmsService smsService;

	@RequestMapping(value = "/findSms")
	public ModelAndView findSms() {

		ModelAndView mv = new ModelAndView("smsList");

		return mv;
	}

	/**
	 * l
	 * 
	 * @Title: getSMSList
	 * @Description: TODO(分页查询(相关)短信)
	 * @param page
	 * @param rows
	 * @return EasyUIPage
	 * @throws
	 */
	@RequestMapping(value = "/getSMSList/{rows}", method = RequestMethod.GET)
	public @ResponseBody
	EasyUIPage getSMSList(EasyUIPage page,
			@RequestParam(value = "rows") Integer rows) {

		page.setPagePara(rows);

		SessionBean sessionBean = getSessionBean();

		page = smsService.getSMSByLoginidAndSysName(sessionBean.getLoginid(),
				sessionBean.getSystem().getMc(), page);

		return page;
	}

	/**
	 * l
	 * 
	 * @Title: checkSmsStatus
	 * @Description: TODO(验证短信status)
	 * @param smsId
	 * @param response
	 *            void
	 * @throws
	 */
	@RequestMapping(value = "/checkSmsStatus", method = RequestMethod.POST)
	public void checkSmsStatus(int smsId, HttpServletResponse response) {

		int status = smsService.checkSmsStatus(smsId);

		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getWriter(), status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * l
	 * 
	 * @Title: updateSMSStatus
	 * @Description: TODO(修改短信状态)
	 * @param smsId
	 * @param response
	 *            void
	 * @throws
	 */
	@RequestMapping(value = "/updateSMSStatus", method = RequestMethod.POST)
	public void updateSmsStatus(int smsId, HttpServletResponse response) {
		String temp = "";
		Boolean flag = smsService.updateSmsStatus(smsId);
		if(flag){
			temp = "0";
		}else{
			temp = "1";
		}
		//0成功  1失败
		ObjectMapper om = new ObjectMapper();	
		
		try {
			om.writeValue(response.getWriter(), temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
