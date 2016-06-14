package cn.ccsgroup.ccsframework.base.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import cn.ccsgroup.ccsframework.annotation.FieldDesc;
import cn.ccsgroup.ccsframework.base.service.GennericQueryService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;


/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.base.controller.GenericQueryController.java]  
 * @ClassName:    [GenericQueryController]   
 * @Description:  [通用查询控制器]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-3 下午3:12:12]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-3 下午3:12:12，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Controller
@Scope("prototype")
public class GenericQueryController extends BaseController{
	
	@Resource(name="gennericQueryService")
	private GennericQueryService  queryService ;

	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST} ,value="/service/{method}")
	public ModelAndView handleRequest(@PathVariable(value="method")String methodName,
			@RequestParam(value="view",required = false) String viewName,
			@RequestParam(value="rows",required = false)Integer rows,EasyUIPage page,
			//@RequestParam(value="page",required = false) String pagination,PageHelper pg,
			HttpServletResponse resp) throws Exception {
		Map<String,Object> params = getParams(super.getRequest());
		Map<String,Object> model = new HashMap<String,Object>();
		//PageHelper page = null;
//		if(pagination != null){
//			page = new PageHelper(super.getRequest(),pg);
//			params.put("pager",  page);
//		}
		if(rows != null){
			page.setPagePara(rows);
			params.put("EasyUIPage",  page);
		}
		if(methodName != null){
			try{	
				
//				model.put("total", MethodUtils.invokeMethod(queryService, methodName+"Count", params));
//				model.put("rows", MethodUtils.invokeMethod(queryService, methodName, params));
				model.put("ccsdata", MethodUtils.invokeMethod(queryService, methodName, params));
				if(rows != null){
					page.setTotal((Integer)MethodUtils.invokeMethod(queryService, methodName+"Count", params));
					model.put("end", page.getEnd());
					model.put("begin", page.getBegin());
					model.put("page", page.getPage());
					model.put("total", page.getTotal());
				}
//				model.put("fields", getFieldDesc(MethodUtils.getAccessibleMethod(GennericQueryService.class, methodName, Map.class)));
			}catch(NoSuchMethodException e){
			}catch(InvocationTargetException inv){
				throw new Exception(inv.getTargetException().getCause().getLocalizedMessage(), inv.getTargetException().getCause());
			}
		}
//		if(pagination != null && model.get("count") != null){
//			page.setTotalCount((Integer)model.get("count"), page);
//			model.put("pager", viewName == null?page.paginate2():page.paginate());
//			model.put("output", page.getOutput());
//		}
//		if(rows != null && model.get("total") != null){
//			page.setTotalCount((Integer)model.get("count"), page);
//			model.put("pager", viewName == null?page.paginate2():page.paginate());
//			model.put("output", page.getOutput());
//		}
		if(viewName == null){
			resp.setContentType("text/plain;charset=UTF-8");
			resp.getWriter().write(new JSONObject(model).toString());
			return null;
		}
		ModelAndView mv = new ModelAndView(viewName);
		mv.addAllObjects(model);
//		mv.addObject("data", model.get("data"));
//		mv.addObject("fields", model.get("fields"));
//		mv.addObject("output", page.getOutput());
//		mv.addObject("pager", page.paginate());
		return mv;
	}

	private Map<String,Object> getParams(HttpServletRequest req) throws UnsupportedEncodingException{
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = req.getParameterNames();
		Map<String,Object> paramMap = new HashMap<String,Object>();
		String paramName = null;
		
		
		while(paramNames.hasMoreElements()){
			paramName = paramNames.nextElement();
			paramMap.put(paramName, req.getParameter(paramName));
		}
		return paramMap;
	}

	private List<String[]> getFieldDesc(Class<?> clazz){
		List<String[]> fieldList = new ArrayList<String[]>(); 
		for(Field f : clazz.getDeclaredFields()){
			if(f.isAnnotationPresent(FieldDesc.class))
				fieldList.add(new String[]{f.getName(),f.getAnnotation(FieldDesc.class).value()});
			else
				fieldList.add(new String[]{f.getName()});
		}
		return fieldList;
	}

	@SuppressWarnings("unused")
	private List<String[]> getFieldDesc(Method m){
		Type type = m.getGenericReturnType();
		if(type instanceof ParameterizedType){
			return getFieldDesc((Class<?>)((ParameterizedType)type).getActualTypeArguments()[0]);
		}else{
			return getFieldDesc(m.getReturnType());
		}
	}

}
