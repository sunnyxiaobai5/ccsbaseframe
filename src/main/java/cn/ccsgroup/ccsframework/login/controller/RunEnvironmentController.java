package cn.ccsgroup.ccsframework.login.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.login.controller.RunEnvironmentController.java]  
 * @ClassName:    [RunEnvironmentController]   
 * @Description:  [处理界面运行环境类]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2014-5-23 下午5:33:46]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2014-5-23 下午5:33:46]   
 * @UpdateRemark: [新增]  
 * @Version:      [v1.0] 
 */
@Controller
@RequestMapping("/run")
public class RunEnvironmentController {
	
	
	/**
	 * @Title: runEnvironment
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param path
	 * @param response
	 * @return HttpServletResponse
	 * @throws
	 */
	@RequestMapping(value="/environment",method=RequestMethod.GET)
	public void runEnvironment(HttpServletResponse response, HttpServletRequest request) {
		try {
			String path = request.getSession().getServletContext().getRealPath("")+"/hosts.bat";
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			String ext = filename.substring(filename.lastIndexOf(".") + 1)
					.toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes()));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
