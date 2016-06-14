/**********************************************************************
 * <p>文件名：ReportServiceImpl.java </p>
 * <p>文件描述：TODO(描述该文件做什么)
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/9/4 15:04
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.report.service.impl;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.ReportSet;
import cn.ccsgroup.ccsframework.entity.ReportSetPara;
import cn.ccsgroup.ccsframework.entity.UserPrintSet;
import cn.ccsgroup.ccsframework.report.dao.ReportDao;
import cn.ccsgroup.ccsframework.report.service.ReportService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * ***************************************************************************
 *
 * @Package: [cn.ccsgroup.ccsframework.report.service.impl]
 * @ClassName: [ReportServiceImpl]
 * @Description: [报表管理实现类]
 * @Author: [yangfan]
 * @CreateDate: [2014/9/4 15:04]
 * @UpdateUser: [yangfan(如多次修改保留历史记录，增加修改记录)]
 * @UpdateDate: [2014/9/4 15:04，(如多次修改保留历史记录，增加修改记录)]
 * @UpdateRemark: [说明本次修改内容, (如多次修改保留历史记录，增加修改记录)]
 * @Version: [v1.0]
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;

    @Override
    public EasyUIPage reportSetList(EasyUIPage page, ReportSet reportSet) {
        Map<String, Object> map = Maps.newHashMap();
        // 分页数据
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());

        // --查询条件
        map.put("rname", reportSet.getRname());
        map.put("rabb", reportSet.getRabb());
        map.put("rtype", reportSet.getRtype());
        // --查询条件

        Integer total = (Integer) reportDao.queryForObject("Report.reportSetListCount", map);
        page.setTotal(total);
        List<ReportSet> reportSets = this.reportSetList(map);
        page.setRows(reportSets);
        return page;
    }

    @Override
    public List<ReportSet> reportSetList(Map<String, Object> map) {
        return reportDao.queryForList("Report.reportSetList", map);
    }

    @Override
    public Integer saveReportSet(ReportSet reportSet) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("rname",reportSet.getRname());
        map.put("status", AppConst.STATUS_ENABLE);
        ReportSet reportSet1 = this.queryReportSet(map);
        Preconditions.checkState(reportSet1 == null,"报表名称已存在，请换一个名称");
        return (Integer) reportDao.insertForObject("Report.saveReportSet", reportSet);
    }

    @Override
    public ReportSet queryReportSetById(int id) {
        return (ReportSet) reportDao.queryForObject("Report.queryReportSetById", id);
    }

    @Override
    public int updateReportSet(ReportSet reportSet) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("rname",reportSet.getRname());
        map.put("notid",reportSet.getId());
        map.put("status", AppConst.STATUS_ENABLE);
        ReportSet reportSet1 = this.queryReportSet(map);
        Preconditions.checkState(reportSet1 == null,"报表名称已存在，请换一个名称");
        return reportDao.update("Report.updateReportSet", reportSet);
    }

    @Override
    public EasyUIPage userPrintSetList(EasyUIPage page, ReportSet reportSet2Search, SessionBean sessionBean) {

        Map<String, Object> searchPara = Maps.newHashMap();
        searchPara.put("rabb", sessionBean.getSystem().getDm());
        searchPara.put("userid", sessionBean.getLoginid());
        //首先查询是否已经有报表，如果没有则会进行首次同步
        List<Map<String, Object>> userPrintSets = this.userPrintSetList(searchPara);
        if (userPrintSets.isEmpty()) {
            pullUserPrintSet(sessionBean, searchPara);
        }
        Map<String, Object> map = Maps.newHashMap();
        // --查询条件
        map.put("userid", sessionBean.getLoginid());
        map.put("rname", reportSet2Search.getRname());
        map.put("rabb", reportSet2Search.getRabb());
        map.put("rtype", reportSet2Search.getRtype());
        // 分页数据
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        Integer total = (Integer) reportDao.queryForObject("Report.userPrintSetListCount", map);
        page.setTotal(total);
        userPrintSets = this.userPrintSetList(map);
        page.setRows(userPrintSets);
        return page;
    }

    /**
     * 同步用户的报表
     *
     * @param sessionBean
     * @param map
     */
    public void pullUserPrintSet(SessionBean sessionBean, Map<String, Object> map) {
        //先查询系统对应报表
        List<ReportSet> reportSets = this.reportSetList(map);
        List<UserPrintSet> userPrintSet2Save = Lists.newArrayList();
        for (ReportSet reportSet : reportSets) {
            UserPrintSet userPrintSet = buildUserPrintSet(reportSet.getId(),sessionBean.getLoginid());
            userPrintSet2Save.add(userPrintSet);
        }
        //再批量导入
        this.saveUserPrintSet(userPrintSet2Save);
    }

    @Override
    public void synReport(SessionBean sessionBean) {
        Map<String, Object> searchPara = Maps.newHashMap();
        searchPara.put("rabb", sessionBean.getSystem().getDm());
        searchPara.put("userid", sessionBean.getLoginid());
        searchPara.put("isPresent",1);// 为了查出报表已经被删除的情况
        //先查询系统对应报表
        List<ReportSet> reportSets = this.reportSetList(searchPara);
        //用户已有报表
        List<Map<String, Object>> userPrintSets = this.userPrintSetList(searchPara);
        // 进行对比，进行增加或者删除
        //删除
        deleteDiffUserPrintSets(reportSets, userPrintSets);
        //增加
        saveNewUserPrintSets(sessionBean, reportSets, userPrintSets);
    }

    @Override
    public void deleteUserPrintSet(List<Map<String, Object>> deleteList) {
        reportDao.batchDelete("Report.deleteUserPrintSet", deleteList);
    }

    @Override
    public void deleteReportSet(ReportSet reportSet) {
        reportDao.delete("Report.deleteReportSetParaByReport",reportSet);
        //不做物理删除
        reportDao.update("Report.deleteReportSet",reportSet);
    }

    @Override
    public EasyUIPage reportSetParaList(EasyUIPage page, Integer reportId) {
        Map<String, Object> map = Maps.newHashMap();
        // --查询条件
        map.put("reportId", reportId);
        // 分页数据
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        Integer total = (Integer) reportDao.queryForObject("Report.reportSetParaListCount", map);
        page.setTotal(total);
        List<ReportSetPara> reportSetParas = this.reportSetParaList(map);
        page.setRows(reportSetParas);
        return page;
    }

    @Override
    public List<ReportSetPara> reportSetParaList(Map<String, Object> map) {
        return reportDao.queryForList("Report.reportSetParaList",map);
    }

    @Override
    public void saveReportSetPara(ReportSetPara reportSetPara) {
        reportDao.insertForObject("Report.saveReportSetPara",reportSetPara);
    }

    @Override
    public ReportSetPara queryReportSetParaById(Integer id) {
        return (ReportSetPara) reportDao.queryForObject("Report.queryReportSetParaById",id);
    }

    @Override
    public void updateReportSetPara(ReportSetPara reportSetPara) {
        reportDao.update("Report.updateReportSetPara",reportSetPara);
    }

    @Override
    public void deleteReportSetPara(Integer id) {
        reportDao.delete("Report.deleteReportSetPara",id);
    }

    @Override
    public List<Map<String, Object>> findReport(Map<String, Object> params) {
        List<Map<String, Object>> userPrintSets = this.userPrintSetList(params);
        return userPrintSets;
    }

    @Override
    public ReportSet queryReportSet(Map<String,Object> map) {
        return (ReportSet) reportDao.queryForObject("Report.queryReportSet",map);
    }

    /**
     * 新增报表
     * @param sessionBean
     * @param reportSets
     * @param userPrintSets
     */
    private void saveNewUserPrintSets(SessionBean sessionBean, List<ReportSet> reportSets, List<Map<String, Object>> userPrintSets) {
        final List<UserPrintSet> newUserPrintSets = Lists.newArrayList();
        for (ReportSet reportSet : reportSets) {
            boolean isPresent = false;
            // 如果用户的报表列表中存在的，最新报表设置中也存在，则标记为true，表示已经存在，如果到最后flag都为false，说明不存在，需要新增
            for (Map<String, Object> userPrintSet : userPrintSets) {
                if (reportSet.getId().equals(MapUtils.getInteger(userPrintSet, "REPORTID")) ) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                UserPrintSet userPrintSet = buildUserPrintSet(reportSet.getId(),sessionBean.getLoginid());
                newUserPrintSets.add(userPrintSet);
            }
        }
        this.saveUserPrintSet(newUserPrintSets);
    }


    /**
     * 删除已经不存在的报表
     * @param reportSets
     * @param userPrintSets
     */
    private void deleteDiffUserPrintSets(List<ReportSet> reportSets, List<Map<String, Object>> userPrintSets) {

        final List<Map<String, Object>> deleteList = Lists.newArrayList();
        for (Map<String, Object> userPrintSet : userPrintSets) {
            boolean isPresent = false;
            for (ReportSet reportSet : reportSets) {
                // 如果用户的报表列表中存在的，最新报表设置中也存在，则标记为true，表示还存在，如果到最后flag都为false，说明已经不存在，需要删除
                if (reportSet.getId().equals(MapUtils.getInteger(userPrintSet, "REPORTID")) ) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                deleteList.add(userPrintSet);
            }
        }
        this.deleteUserPrintSet(deleteList);
    }

    /**
     * 构建UserPrintSet对象
     * @param reportId
     * @param userId
     * @return
     */
    private UserPrintSet buildUserPrintSet(Integer reportId, String userId) {
        UserPrintSet userPrintSet = new UserPrintSet();
        userPrintSet.setIsPreview(1);
        userPrintSet.setPrinter("-1");
        userPrintSet.setPrintIndex(0);
        userPrintSet.setReportId(reportId);
        userPrintSet.setUserId(userId);
        return userPrintSet;
    }

    @Override
    public List<Map<String, Object>> userPrintSetList(Map<String, Object> map) {
        return reportDao.queryForList("Report.userPrintSetList", map);
    }

    @Override
    public Map<String, Object> queryUserPrintSetById(Integer id) {
        return (Map<String, Object>) reportDao.queryForObject("Report.queryUserPrintSetById", id);
    }

    @Override
    public int updateUserPrintSet(UserPrintSet userPrintSet) {
        return reportDao.update("Report.updateUserPrintSet", userPrintSet);
    }

    @Override
    public void saveUserPrintSet(List<UserPrintSet> userPrintSets) {
        reportDao.batchInsert("Report.saveUserPrintSet", userPrintSets);
    }
}
