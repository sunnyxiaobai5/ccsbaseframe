/**********************************************************************
 * <p>文件名：ReportSet.java </p>
 * <p>文件描述：
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/9/4 15:09
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
 * @ClassName: [ReportSet]
 * @Description: [报表实体类]
 * @Author: [yangfan]
 * @CreateDate: [2014/9/4 15:09]
 * @UpdateUser: [yangfan]
 * @UpdateDate: [2014/9/4 15:09]
 * @UpdateRemark: [新建]
 * @Version: [v1.0]
 */
public class ReportSet extends BaseBean implements Serializable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 报表名称
     */
    private String rtype;
    /**
     * 报表名称
     */
    private String rname;
    /**
     * 报表文件
     */
    private String rfile;
    /**
     * 报表URL
     */
    private String rfileurl;
    /**
     * 打印参数
     */
    private String rparam;
    /**
     * 打印方向
     */
    private String rposition;
    /**
     * 能否批量打印
     */
    private String isbatch;
    /**
     * 系统标识
     */
    private Integer rabb;
    private String sysName;
    /**
     * 区域编码
     */
    private String regionCode;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 描述
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRfile() {
        return rfile;
    }

    public void setRfile(String rfile) {
        this.rfile = rfile;
    }

    public String getRfileurl() {
        return rfileurl;
    }

    public void setRfileurl(String rfileurl) {
        this.rfileurl = rfileurl;
    }

    public String getRparam() {
        return rparam;
    }

    public void setRparam(String rparam) {
        this.rparam = rparam;
    }

    public String getRposition() {
        return rposition;
    }

    public void setRposition(String rposition) {
        this.rposition = rposition;
    }

    public String getIsbatch() {
        return isbatch;
    }

    public void setIsbatch(String isbatch) {
        this.isbatch = isbatch;
    }

    public Integer getRabb() {
        return rabb;
    }

    public void setRabb(Integer rabb) {
        this.rabb = rabb;
    }


    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
}
