/**********************************************************************
 * <p>文件名：Mail.java </p>
 * <p>文件描述：
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/11/14 13:44
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.entity;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * ***************************************************************************
 *
 * @Package: [cn.ccsgroup.ccsframework.entity]
 * @ClassName: [Mail]
 * @Description: [消息实体类]
 * @Author: [yangfan]
 * @CreateDate: [2014/11/14 13:44]
 * @UpdateUser: [yangfan]
 * @UpdateDate: [2014/11/14 13:44]
 * @UpdateRemark: [新建]
 * @Version: [v1.0]
 */
public class Mail extends BaseBean implements Serializable{
    public static final int READ = 0;
    public static final int UNREAD = 1;
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 0;

    /**主键*/
    private Integer id;
    /**发送者*/
    private String sender;
    /**接收者们*/
    private String receivers;
    /**标题*/
    private String title;
    /**消息内容*/
    private String content;
    /**附件名称*/
    private String[] fileName;
    /**发送时间*/
    private Date sendTime;
    /**业务状态，1有效，0无效*/
    private Integer status;
    /**发送者ID*/
    private String userName;
    /**阅读状态 1未读，0已读*/
    private Integer isRead;
    /**附件*/
    private List<Map<String, Object>> files;


    public List<Map<String, Object>> getFiles() {
        return files;
    }

    public void setFiles(List<Map<String, Object>> files) {
        this.files = files;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getFileName() {
        return fileName;
    }

    public void setFileName(String[] fileName) {
        this.fileName = fileName;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
