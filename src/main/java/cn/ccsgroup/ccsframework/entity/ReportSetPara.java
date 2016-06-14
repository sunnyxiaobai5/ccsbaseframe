/**********************************************************************
 * <p>文件名：ReportSetPara.java </p>
 * <p>文件描述：
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/9/10 14:20
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
 * @ClassName: [ReportSetPara]
 * @Description: [报表参数实体类]
 * @Author: [yangfan]
 * @CreateDate: [2014/9/10 14:20]
 * @UpdateUser: [yangfan]
 * @UpdateDate: [2014/9/10 14:20]
 * @UpdateRemark: [新建]
 * @Version: [v1.0]
 */
public class ReportSetPara extends BaseBean implements Serializable {
    /**主键*/
    private Integer id;
    /**名称*/
    private String name;
    /**参数名称*/
    private String paraName;
    /**报表id*/
    private Integer reportId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
}
