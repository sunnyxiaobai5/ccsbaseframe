/**********************************************************************
 * <p>文件名：MailDao.java </p>
 * <p>文件描述：
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/11/17 11:36
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.sysManager.dao;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.entity.Mail;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ***************************************************************************
 *
 * @Package: [cn.ccsgroup.ccsframework.sysManager.dao]
 * @ClassName: [MailDao]
 * @Description: [消息功能dao]
 * @Author: [yangfan]
 * @CreateDate: [2014/11/17 11:36]
 * @UpdateUser: [yangfan]
 * @UpdateDate: [2014/11/17 11:36]
 * @UpdateRemark: [新建]
 * @Version: [v1.0]
 */
@Repository("mailDao")
public class MailDao extends BaseDaoImpl {

    /**
     * 查询消息
     * @param map
     * @return
     */
    public List<Mail> getMails(Map<String, Object> map) {
        return this.queryForList("Mail.getMails", map);
    }

    /**
     * 查询消息总数
     * @param queryMap
     * @return
     */
    public Integer getMailsCount(Map<String, Object> queryMap) {
        return (Integer) this.queryForObject("Mail.getMailsCount", queryMap);
    }

    /**
     * 发送消息
     * @param mail
     * @return
     */
    public Integer sendMail(Mail mail) {
        return (Integer) this.insertForObject("Mail.sendMail", mail);
    }

    /**
     * 关联消息和文件
     * @param id
     * @param fileName
     */
    public void saveMailFiles(Integer id, String[] fileName) {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (String file : fileName) {
            Map<String, Object> map = Maps.newHashMap();
            String []idAndName = file.split(":");
            map.put("mailId",id);
            map.put("fileId",idAndName[0]);
            map.put("filename",idAndName[1]);
            list.add(map);
        }

        this.batchInsert("Mail.saveMailFiles",list);
    }

    /**
     * 更新消息
     * @param paraMap
     */
    public void updateMail(Map<String, Object> paraMap) {
        this.update("Mail.updateMail",paraMap);
    }

    /**
     * 查询消息附件
     * @param id
     * @return
     */
    public List<Map<String, Object>> getMailFile(int id) {
        return this.queryForList("Mail.getMailFile",id);
    }

    public int deleteMail(Integer id) {

        return this.delete("Mail.deleteMail", id);
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