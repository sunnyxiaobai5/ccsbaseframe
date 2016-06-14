/**********************************************************************
 * <p>文件名：UserPrintSet.java </p>
 * <p>文件描述：
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/9/9 11:01
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.entity;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

import java.io.Serializable;

/**
 * ***************************************************************************
 *
 * @Package: [cn.ccsgroup.ccsframework.entity]
 * @ClassName: [UserPrintSet]
 * @Description: [用户打印设置实体类]
 * @Author: [yangfan]
 * @CreateDate: [2014/9/9 11:01]
 * @UpdateUser: [yangfan]
 * @UpdateDate: [2014/9/9 11:01]
 * @UpdateRemark: [新建]
 * @Version: [v1.0]
 */
public class UserPrintSet extends BaseBean implements Serializable {
    /**主键*/
    private Integer id;
    /**报表id*/
    private Integer reportId;
    /**用户id*/
    private String userId;
    /**是否预览*/
    private Integer isPreview;
    /**打印序列*/
    private Integer printIndex;
    /**打印机*/
    private String printer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getIsPreview() {
        return isPreview;
    }

    public void setIsPreview(Integer isPreview) {
        this.isPreview = isPreview;
    }

    public Integer getPrintIndex() {
        return printIndex;
    }

    public void setPrintIndex(Integer printIndex) {
        this.printIndex = printIndex;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }
}
