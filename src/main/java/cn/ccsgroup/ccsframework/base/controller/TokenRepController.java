package cn.ccsgroup.ccsframework.base.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccsgroup.ccsframework.annotation.TokenAnnotation;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.base.controller.TokenRepController.java]  
 * @ClassName:    [TokenRepController]   
 * @Version:      [v1.01]
 */
@Controller
@Scope("prototype")
@RequestMapping(value="/submitToken")
public class TokenRepController extends BaseController {
	/**
	 * 获取新的token
	 * author：tly
	 * */
	@TokenAnnotation(save=true)
	@RequestMapping(value="/new",method=RequestMethod.GET)
	public @ResponseBody Map<String,String> getNewToken(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("token", getRequest().getAttribute("token").toString());
		return map;
	}
}
