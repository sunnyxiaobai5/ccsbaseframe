/**********************************************************************
 * <p>文件名：ReportController.java </p>
 * <p>文件描述：TODO(描述该文件做什么)
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/9/4 14:08
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.report.controller;

import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.ReportSet;
import cn.ccsgroup.ccsframework.entity.ReportSetPara;
import cn.ccsgroup.ccsframework.entity.UserPrintSet;
import cn.ccsgroup.ccsframework.report.service.ReportService;
import cn.ccsgroup.ccsframework.sysparamManager.service.SysParamService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ***************************************************************************
 *
 * @Package: [cn.ccsgroup.ccsframework.report.controller]
 * @ClassName: [ReportController]
 * @Description: [报表管理]
 * @Author: [yangfan]
 * @CreateDate: [2014/9/4 14:08]
 * @UpdateUser: [yangfan(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2014/9/4 14:08，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [创建]
 * @Version: [v1.0]
 */

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

@Controller
@RequestMapping("/report")
public class ReportController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);
    @Autowired
    private ReportService reportService;
    @Autowired
    private SysParamService sysParamService;

    /**
     * 跳转到报表设置页面
     * @return
     */
    @RequestMapping(value = "/reportSet", method = RequestMethod.GET)
    public ModelAndView toReportSetPage(ModelMap model){
        List<LogiSystem> systems = sysParamService.queryLoginSystemList();
        model.put("systems",systems);
        return new ModelAndView("reportManager/reportSetPage");
    }

    /**
     *查询报表列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value="/reportSet/list",method = RequestMethod.POST)
    @ResponseBody
    public EasyUIPage reportSetList(EasyUIPage page,
                               @RequestParam(value="rows")Integer rows,ReportSet reportSet) {

        page.setPagePara(rows);
        return reportService.reportSetList(page,reportSet);
    }


    /**
     * 跳转到添加报表页面
     * @return
     */
    @RequestMapping(value="/reportSet/add",method = RequestMethod.GET)
    public ModelAndView toAddReportSet(ModelMap model){
        List<LogiSystem> systems = sysParamService.queryLoginSystemList();
        model.put("systems",systems);
        model.put(AppConst.OPT,AppConst.ADD);
        return new ModelAndView("reportManager/reportSet");
    }

    /**
     * 新增报表
     * @return
     */
    @RequestMapping(value="/reportSet/add",method = RequestMethod.POST)
    public ModelAndView addReportSet(ReportSet reportSet){
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = new HashMap<String, Object>();
        try {

            reportService.saveReportSet(reportSet);

            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, getAddSuccess());
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage(),e);
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, getAddFail() + e.getLocalizedMessage());
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }

    /**
     * 跳转到编辑报表页面
     * @return
     */
    @RequestMapping(value="/reportSet/update",method = RequestMethod.GET)
    public ModelAndView toUpdateReportSet(ModelMap model, Integer id){
        ReportSet reportSet = reportService.queryReportSetById(id);
        List<LogiSystem> systems = sysParamService.queryLoginSystemList();
        model.put("systems",systems);
        model.put("reportSet",reportSet);
        model.put(AppConst.OPT,AppConst.UPDATE);
        return new ModelAndView("reportManager/reportSet");
    }


    /**
     * 修改报表
     * @param reportSet
     * @return
     */
    @RequestMapping(value="/reportSet/update",method = RequestMethod.POST)
    public ModelAndView updateReportSet(ReportSet reportSet){
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            reportService.updateReportSet(reportSet);
            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, getUpdateSuccess());
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage(),e);
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, getUpdateFail()+e.getLocalizedMessage());
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }


    /**
     * 删除报表
     * @param reportSet
     * @return
     */
    @RequestMapping(value="/reportSet/delete",method = RequestMethod.POST)
    public ModelAndView deleteReportSet(ReportSet reportSet){
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            reportService.deleteReportSet(reportSet);
            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, getDeleteSuccess());
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage(),e);
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, getDeleteFail());
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }

    /**
     * 跳转到用户报表设置页面
     * @return
     */
    @RequestMapping(value = "/userPrintSet", method = RequestMethod.GET)
    public ModelAndView toUserPrintSetPage(ModelMap model){
        return new ModelAndView("reportManager/userPrintSetPage");
    }

    /**
     * 查询报表列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value="/userPrintSet/list",method = RequestMethod.POST)
    @ResponseBody
    public EasyUIPage userPrintSetList(EasyUIPage page,
                                    @RequestParam(value="rows")Integer rows,ReportSet reportSet) {
        page.setPagePara(rows);
        return reportService.userPrintSetList(page,reportSet,getSessionBean());
    }

    /**
     * 跳转到编辑用户报表设置页面
     * @return
     */
    @RequestMapping(value="/userPrintSet/update",method = RequestMethod.GET)
    public ModelAndView toUpdateUserPrintSet(ModelMap model, @RequestParam(value = "ID")Integer id){
        Map<String,Object> userPrintSet = reportService.queryUserPrintSetById(id);
        model.put("userPrintSet",userPrintSet);
        model.put(AppConst.OPT,AppConst.UPDATE);
        return new ModelAndView("reportManager/userPrintSet");
    }


    /**
     * 修改报表设置
     * @param userPrintSet
     * @return
     */
    @RequestMapping(value="/userPrintSet/update",method = RequestMethod.POST)
    public ModelAndView updateUserPrintSet(UserPrintSet userPrintSet){
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            reportService.updateUserPrintSet(userPrintSet);
            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, getUpdateSuccess());
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage(),e);
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, getUpdateFail());
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }

    /**
     * 删除所有用户自定义的配置，重新导入
     * @return
     */
    @RequestMapping(value="/userPrintSet/synReport",method = RequestMethod.POST)
    public ModelAndView synReport(){
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            reportService.synReport(getSessionBean());
            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, "同步成功");
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage(),e);
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, "同步失败");
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }


    /**
     * 跳转到参数管理页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value="/reportSet/para",method = RequestMethod.GET)
    public ModelAndView toReportParaPage(ModelMap model, Integer id){
        model.put("reportId",id);
        return new ModelAndView("reportManager/reportParaPage");
    }

    /**
     * 查询报表参数列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value="/reportSet/para/list",method = RequestMethod.POST)
    @ResponseBody
    public EasyUIPage reportSetParaList(EasyUIPage page,
                                       @RequestParam(value="rows")Integer rows,Integer reportId) {
        page.setPagePara(rows);
        return reportService.reportSetParaList(page, reportId);
    }

    /**
     * 添加参数
     * @param model
     * @param id 报表id
     * @return
     */
    @RequestMapping(value="/reportSet/para/add",method = RequestMethod.GET)
    public ModelAndView toAddReportParaPage(ModelMap model, Integer id){
        model.put("reportId",id);
        return new ModelAndView("reportManager/reportPara");
    }

    /**
     * 新增报表参数
     * @return
     */
    @RequestMapping(value="/reportSet/para/add",method = RequestMethod.POST)
    public ModelAndView addReportSetPara(ReportSetPara reportSetPara){
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = new HashMap<String, Object>();
        try {

            reportService.saveReportSetPara(reportSetPara);

            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, getAddSuccess());
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage(),e);
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, getAddFail());
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }


    /**
     * 编辑参数页面
     * @param model
     * @param id 参数id
     * @return
     */
    @RequestMapping(value="/reportSet/para/update",method = RequestMethod.GET)
    public ModelAndView toUpdateReportSetParaPage(ModelMap model, Integer id){
        ReportSetPara reportSetPara = reportService.queryReportSetParaById(id);
        model.put("reportSetPara",reportSetPara);
        return new ModelAndView("reportManager/reportPara");
    }

    /**
     * 编辑参数
     * @param reportSetPara
     * @return
     */
    @RequestMapping(value="/reportSet/para/update",method = RequestMethod.POST)
    public ModelAndView updateReportSetPara(ReportSetPara reportSetPara){
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            reportService.updateReportSetPara(reportSetPara);
            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, getUpdateSuccess());
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage(),e);
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, getUpdateFail());
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }

    /**
     * 删除参数
     * @param id
     * @return
     */
    @RequestMapping(value="/reportSet/para/delete",method = RequestMethod.POST)
    public ModelAndView deleteReportSetPara(Integer id){
        ModelAndView mv = new ModelAndView(AppConst.FORWORD);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            reportService.deleteReportSetPara(id);
            model.put(AppConst.STATUS, AppConst.SUCCESS);
            model.put(AppConst.MESSAGES, getDeleteSuccess());
        }catch (Exception e){
            LOGGER.error(e.getLocalizedMessage(),e);
            model.put(AppConst.STATUS, AppConst.FAIL);
            model.put(AppConst.MESSAGES, getDeleteFail());
        }
        mv.addObject(AppConst.MESSAGES, JSON.toJSON(model));
        return mv;
    }

}
