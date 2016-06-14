/**********************************************************************
 * <p>文件名：ReportService.java </p>
 * <p>文件描述：TODO(描述该文件做什么)
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/9/4 14:59
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.report.service;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.entity.ReportSet;
import cn.ccsgroup.ccsframework.entity.ReportSetPara;
import cn.ccsgroup.ccsframework.entity.UserPrintSet;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

import java.util.List;
import java.util.Map;

/**
 * ***************************************************************************
 *
 * @Package: [cn.ccsgroup.ccsframework.report.service]
 * @ClassName: [ReportService]
 * @Description: [报表管理]
 * @Author: [yangfan]
 * @CreateDate: [2014/9/4 14:59]
 * @UpdateUser: [yangfan(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2014/9/4 14:59，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: []
 * @Version: [v1.0]
 */
public interface ReportService {

    /**
     * EasyUI:获得报表列表
     *
     * @param page
     * @return
     */
    EasyUIPage reportSetList(EasyUIPage page, ReportSet reportSet);

    /**
     * 获得报表列表
     *
     * @param map
     * @return
     */
    List<ReportSet> reportSetList(Map<String, Object> map);

    /**
     * 新增报表
     *
     * @param reportSet
     */
    Integer saveReportSet(ReportSet reportSet);

    /**
     * @param id
     * @return
     */
    ReportSet queryReportSetById(int id);

    /**
     * 修改薄白哦
     *
     * @param reportSet
     */
    int updateReportSet(ReportSet reportSet);

    /**
     * 查询用户设置的报表，首次查询会自动更新列表
     *
     * @param page
     * @param reportSet
     * @return
     */
    EasyUIPage userPrintSetList(EasyUIPage page, ReportSet reportSet, SessionBean sessionBean);

    /**
     * 查询用户列表
     *
     * @param map
     * @return
     */
     List<Map<String, Object>> userPrintSetList(Map<String, Object> map);

    /**
     * 查询报表用户的设置
     *
     * @param id
     * @return
     */
    Map<String,Object> queryUserPrintSetById(Integer id);

    /**
     * 更新报表的设置
     *
     * @param userPrintSet
     * @return
     */
    int updateUserPrintSet(UserPrintSet userPrintSet);

    /**
     * 导入用户对应报表
     * @param userPrintSets
     * @return
     */
    void saveUserPrintSet(List<UserPrintSet> userPrintSets);

    /**
     * 获得最新报表列表
     * @param sessionBean
     * @param map
     */
    public void pullUserPrintSet(SessionBean sessionBean, Map<String, Object> map);

    /**
     * 同步用户的报表
     * @param sessionBean
     */
    void synReport(SessionBean sessionBean);

    /**
     * 删除用户设置
     * @param deleteList
     */
    void deleteUserPrintSet(List<Map<String,Object>> deleteList);

    /**
     * 删除报表
     * @param reportSet
     */
    void deleteReportSet(ReportSet reportSet);

    /**
     * 查询报表参数Easyui
     * @param page
     * @param reportId
     * @return
     */
    EasyUIPage reportSetParaList(EasyUIPage page, Integer reportId);

    /**
     * 查询报表参数列表
     * @param map
     * @return
     */
    List<ReportSetPara> reportSetParaList(Map<String, Object> map);

    /**
     * 保存报表参数
     * @param reportSetPara
     */
    void saveReportSetPara(ReportSetPara reportSetPara);

    /**
     * 通过id查询报表参数
     * @param id 参数id
     * @return
     */
    ReportSetPara queryReportSetParaById(Integer id);

    /**
     * 编辑参数
     * @param reportSetPara
     */
    void updateReportSetPara(ReportSetPara reportSetPara);

    /**
     * 删除参数
     * @param id
     */
    void deleteReportSetPara(Integer id);

    /**
     * 查询报表
     * @param params
     * @return
     */
    List<Map<String,Object>> findReport(Map<String, Object> params);

    /**
     * 通过报表明晨查询报表
     * @param map
     * @return
     */
    ReportSet queryReportSet(Map<String,Object> map);

}
