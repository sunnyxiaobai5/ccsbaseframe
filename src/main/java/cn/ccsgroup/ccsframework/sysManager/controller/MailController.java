/**********************************************************************
 * <p>文件名：MailController.java </p>
 * <p>文件描述：
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/11/13 17:02
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.sysManager.controller;

import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Mail;
import cn.ccsgroup.ccsframework.exception.BussinessException;
import cn.ccsgroup.ccsframework.sysManager.service.MailService;
import cn.ccsgroup.ccsframework.utils.DateTimeHelper;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ***************************************************************************
 *
 * @Package: [cn.ccsgroup.ccsframework.sysManager.controller]
 * @ClassName: [MailController]
 * @Description: [消息功能]
 * @Author: [yangfan]
 * @CreateDate: [2014/11/13 17:02]
 * @UpdateUser: [yangfan]
 * @UpdateDate: [2014/11/13 17:02]
 * @UpdateRemark: [新建]
 * @Version: [v1.0]
 */
@Controller
@RequestMapping("/mail")
public class MailController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(MailController.class);
    @Autowired
    private MailService mailService;

    /**
     * 跳转到消息列表页面
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView page() {
        String loginId = getSessionBean().getLoginid();
        return new ModelAndView("mailManager/mail", "loginId", loginId);
    }

    /**
     * 统计未读消息
     *
     * @return
     */
    @RequestMapping(value = "/mailCount", method = RequestMethod.GET)
    @ResponseBody
    public int mailCount() {
        Map<String, Object> queryMap = ImmutableMap.<String, Object>of(
                "loginid", getSessionBean().getLoginid(), "status", Mail.ACTIVE,"isRead",Mail.UNREAD);
        return mailService.getMailsCount(queryMap);
    }


    /**
     * 查询消息列表
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public EasyUIPage list(EasyUIPage page, Integer rows) {
        Map<String, Object> map = new HashMap<String, Object>();
        page.setPagePara(rows);
        return mailService.list(map, page, getSessionBean());
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView addPage() {
        return new ModelAndView("mailManager/mailDetail");
    }

    /**
     * 发送消息
     *
     * @param mail
     * @param fileNum
     * @return
     */
    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
    public ModelAndView sendMail(Mail mail, int fileNum) {
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = Maps.newHashMap();
        try {
            // 验证用户的附件是否已经上传完成
            if (fileNum > 0 && (mail.getFileName() == null || fileNum != mail.getFileName().length)) {
                throw new Exception("请等待文件上传完成");
            }
            mail.setSender(getSessionBean().getLoginid());
            mail.setUserName(getSessionBean().getUserName());
            mailService.sendMail(mail);

            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, "发送成功");
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);

            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, "发送失败:" + e.getLocalizedMessage());
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }


    /**
     * 跳转到查看消息页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/view")
    public ModelAndView view(int id) {
        Mail mail = mailService.viewMail(id);
        return new ModelAndView("mailManager/viewMail", "mail", mail);
    }

    /**
     * 跳转到回复页面
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/reply", method = RequestMethod.GET)
    public ModelAndView reply(int id) {
        Mail mail = mailService.viewMail(id);
        return new ModelAndView("mailManager/mailDetail", "mail", mail);
    }


    /**
     * 删除消息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteMail(Integer id) {
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            boolean db = mailService.deleteMail(id);
            if (db) {
                model.put(AppConst.STATUS, AppConst.SUCCESS);
                model.put(AppConst.MESSAGES, getDeleteSuccess());
            } else {
                model.put(AppConst.STATUS, AppConst.FAIL);
                model.put(AppConst.MESSAGES, getDeleteFail());
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, getDeleteFail());
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }

    /**
     * 上传文件
     *
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(HttpServletRequest request, MultipartFile file) {
        String fileName = getSessionBean().getLoginid() + "-" + DateTimeHelper.getNowTime() + file.getSize();
        try {
            mailService.saveFile(fileName, file);
        } catch (BussinessException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return fileName;
    }

    /**
     * 下载附件
     *
     * @param file     附件Id
     * @param response
     */
    @RequestMapping(value = "/download/{filename}/{file}")
    public void download(@PathVariable String file, @PathVariable String filename, HttpServletResponse response) {
        try {
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "ISO8859-1"));
            response.setContentType("application/octet-stream");
            mailService.getFileFromMongo(file, response.getOutputStream());
        } catch (BussinessException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }

    }


}

/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
*/