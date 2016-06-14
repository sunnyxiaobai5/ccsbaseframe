package cn.ccsgroup.ccsframework.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.report.service.ReportService;
import cn.ccsgroup.ccsframework.userManager.service.UserManagerService;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.ccsgroup.ccsframework.annotation.RestPrivilegeAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.httpService.HttpRequestBody;
import cn.ccsgroup.ccsframework.httpService.HttpResponseBody;
import cn.ccsgroup.ccsframework.sysManager.service.UserPowerService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

@Controller
@RequestMapping("/rest")
public class WSCCSFrameWorkService extends BaseController {
	private final static Logger LOGGER = LoggerFactory.getLogger(WSCCSFrameWorkService.class);
	
	@Resource(name="userPowerService")
	private UserPowerService userPowerService;
    @Autowired
    private ReportService reportService;
	@Autowired
	private UserManagerService userManagerService;
	
	@RestPrivilegeAnnotation
	@ResponseBody
	@RequestMapping(value="/findRolesListByLogin",method = RequestMethod.POST)
	public HttpResponseBody findRolesListByLogin(@RequestBody HttpRequestBody httpRequestBody) {
		HttpResponseBody httpResponseBody = new HttpResponseBody();
		List<Role> userRoleList = new ArrayList<Role>();
		try {
			String loginId = httpRequestBody.getLoginid();
			userRoleList = userPowerService.findRolesListByLogin(loginId);//用户的角色list
			
			Map<Object, Object> rolesMap = new HashMap<Object, Object>();
			rolesMap.put("userRoleList", userRoleList);
			
			httpResponseBody.setResult(rolesMap);
			httpResponseBody.setStatus(AppConst.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			httpResponseBody.setStatus(AppConst.FAIL);
			httpResponseBody.setMessage("获取角色列表发生异常");
		}
		return httpResponseBody;
	}
	
	@RestPrivilegeAnnotation
	@ResponseBody
	@RequestMapping(value="/findAllRoles",method = RequestMethod.POST)
	public HttpResponseBody findAllRoles(@RequestBody HttpRequestBody httpRequestBody) {
		HttpResponseBody httpResponseBody = new HttpResponseBody();
		Map<String, Object> params = (Map<String, Object>) httpRequestBody.getData();
		EasyUIPage page = JSON.toJavaObject((JSONObject)params.get("page"),EasyUIPage.class);
		Integer rows = (Integer) params.get("rows");
		page.setPagePara(rows);
		
		try {
			page = userPowerService.findAllRoles(page);			//系统中所有的有效角色list
			
			Map<Object, Object> rolesMap = new HashMap<Object, Object>();
			rolesMap.put("allRoleList", page);
			
			httpResponseBody.setResult(rolesMap);
			httpResponseBody.setStatus(AppConst.SUCCESS);
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
			httpResponseBody.setStatus(AppConst.FAIL);
			httpResponseBody.setMessage("获取所有角色列表发生异常");
		}
		return httpResponseBody;
	}

    /**
     * 查询报表
     * @param httpRequestBody
     * @return
     */
    @RestPrivilegeAnnotation
    @ResponseBody
    @RequestMapping(value="/findReport",method = RequestMethod.POST)
    public HttpResponseBody findReport(@RequestBody HttpRequestBody httpRequestBody) {
        HttpResponseBody httpResponseBody = new HttpResponseBody();
        Map<String, Object> params = httpRequestBody.convertData2Map();

        try {
            List<Map<String,Object>> reportSet = reportService.findReport(params);

            httpResponseBody.setResult(reportSet);
            httpResponseBody.setStatus(AppConst.SUCCESS);
        } catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(),e);
            httpResponseBody.setStatus(AppConst.FAIL);
            httpResponseBody.setMessage("查询报表失败");
        }
        return httpResponseBody;
    }

	/**
	 * 通过机构ID名称查询所有用户
	 * @param httpRequestBody
	 * @return
	 */
	@RestPrivilegeAnnotation
	@RequestMapping(value = "/getUsersByOrgId", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponseBody getUsersByOrgId(@RequestBody HttpRequestBody httpRequestBody) {
		HttpResponseBody httpResponseBody = new HttpResponseBody();
		Map<String, Object> params = httpRequestBody.convertData2Map();

		try {
			List<Map<String, String>> users = userManagerService.getUsersByOrgId(MapUtils.getIntValue(params,"orgId"));

			httpResponseBody.setResult(users);
			httpResponseBody.setStatus(AppConst.SUCCESS);
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(),e);
			httpResponseBody.setStatus(AppConst.FAIL);
			httpResponseBody.setMessage("获取用户失败:"+e.getLocalizedMessage());
		}
		return httpResponseBody;
	}
	
}
