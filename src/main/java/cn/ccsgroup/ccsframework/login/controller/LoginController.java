package cn.ccsgroup.ccsframework.login.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.login.controller.LoginController.java]  
 * @ClassName:    [LoginController]   
 * @Description:  [处理登录的Action]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2013-11-27 上午11:34:15]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2013-11-27 上午11:34:15]   
 * @UpdateRemark: [创建]  
 * @Version:      [v1.0] 
 */
@Controller
@RequestMapping("/Login")
public class LoginController {
	
	@RequestMapping(value = "/index")  
	public String gotoSuc(HttpServletRequest  request,HttpServletResponse response) throws IOException{ 
		
		
		
		return "main";  
	}
}
