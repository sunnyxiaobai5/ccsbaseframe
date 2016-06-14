/**********************************************************************
 * <p>文件名：MailServiceImpl.java </p>
 * <p>文件描述：
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/11/14 13:12
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.sysManager.service.impl;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.entity.Mail;
import cn.ccsgroup.ccsframework.exception.BussinessException;
import cn.ccsgroup.ccsframework.sysManager.dao.MailDao;
import cn.ccsgroup.ccsframework.sysManager.service.MailService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import cn.ccsgroup.ccsframework.utils.MongoUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ***************************************************************************
 *
 * @Package: [cn.ccsgroup.ccsframework.sysManager.service.impl]
 * @ClassName: [MailServiceImpl]
 * @Description: [功能]
 * @Author: [yangfan]
 * @CreateDate: [2014/11/14 13:12]
 * @UpdateUser: [yangfan]
 * @UpdateDate: [2014/11/14 13:12]
 * @Updateemark: [新建]
 * @Version: [v1.0]
 */
@Transactional
@Service("mailService")
public class MailServiceImpl implements MailService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);
    @Autowired
    private MailDao mailDao;

    /**
     * 查询消息列表
     *
     * @param queryMap
     * @param page
     * @param sessionBean
     * @return
     */
    @Override
    public EasyUIPage list(Map<String, Object> queryMap, EasyUIPage page, SessionBean sessionBean) {
        queryMap.put(EasyUIPage.BEGIN, page.getBegin());
        queryMap.put(EasyUIPage.END, page.getEnd());
        queryMap.put("loginid", sessionBean.getLoginid());
        queryMap.put("status",1);

        //数据总数
        page.setTotal(this.getMailsCount(queryMap));
        //数据
        page.setRows(this.getMails(queryMap));
        return page;
    }

    /**
     * 查询消息
     *
     * @param map
     * @return
     */
    @Override
    public List<Mail> getMails(Map<String, Object> map) {
        return mailDao.getMails(map);
    }

    /**
     * 消息总数
     *
     * @param queryMap
     * @return
     */
    @Override
    public int getMailsCount(Map<String, Object> queryMap) {
        return mailDao.getMailsCount(queryMap);
    }

    /**
     * 发送消息
     *
     * @param fileName 文件名称
     * @param file     文件
     * @throws BussinessException
     */
    @Override
    public void saveFile(String fileName, MultipartFile file) throws BussinessException {
        try {
            Mongo client = MongoUtil.getClient();
            DB db = client.getDB("ccs");//数据库名
            GridFS gridFS = new GridFS(db, "files");//集合名
            GridFSInputFile gif = gridFS.createFile(file.getInputStream(), fileName);
            gif.save();//保存文件
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new BussinessException("file.fail");
        }

    }

    /**
     * 关联消息和文件
     *
     * @param id       消息id
     * @param fileName 文件名称
     */
    @Override
    public void saveMailFiles(Integer id, String[] fileName) {
        mailDao.saveMailFiles(id, fileName);
    }

    /**
     * 通过id查询消息
     *
     * @param id 消息id
     * @return
     */
    @Override
    public Mail getMailById(int id) {
        //构造参数
        Map<String, Object> queryMap = ImmutableMap.<String, Object>of("id", id);
        Mail mail = this.getMails(queryMap).get(0);
        List<Map<String, Object>> files = this.getMailFile(id);
        mail.setFiles(files);
        return mail;
    }

    /**
     * 查看消息
     *
     * @param id 消息id
     * @return
     */
    @Override
    public Mail viewMail(int id) {
        //查询消息
        Mail mail = this.getMailById(id);
        //改变消息状态为已读
        this.changeMailState(id);
        return mail;
    }

    /**
     * 改变消息状态为已读
     *
     * @param id
     */
    @Override
    public void changeMailState(int id) {
        //构造参数
        Map<String, Object> paraMap = ImmutableMap.<String, Object>builder()
                .put("id", id)
                .put("isRead", Mail.READ).build();
        this.updateMail(paraMap);
    }

    /**
     * 更新消息
     *
     * @param paraMap 参数
     */
    @Override
    public void updateMail(Map<String, Object> paraMap) {
        mailDao.updateMail(paraMap);
    }

    /**
     * 查询消息对应的附件
     *
     * @param id 消息id
     * @return
     */
    @Override
    public List<Map<String, Object>> getMailFile(int id) {
        return mailDao.getMailFile(id);
    }

    @Override
    public void getFileFromMongo(String file, ServletOutputStream outputStream) throws BussinessException {
        try {
            Mongo client = MongoUtil.getClient();
            DB db = client.getDB("ccs");
            GridFS gridFS = new GridFS(db, "files");//集合名
            GridFSDBFile gfsFile = gridFS.findOne(file);
            gfsFile.writeTo(outputStream);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new BussinessException("file.fail");
        }

    }

    @Override
    public boolean deleteMail(Integer id) {

        return mailDao.deleteMail(id) > 0;
    }

    /**
     * 发送消息
     *
     * @param mail 消息实体
     */
    @Override
    public void sendMail(Mail mail) {
        String[] receivers = mail.getReceivers().split(",");
        for (String receiver : receivers) {

            mail.setReceivers(receiver);
            //发送消息
            Integer id = mailDao.sendMail(mail);
            //如果有附件，则关联附件
            if (mail.getFileName() != null) {
                this.saveMailFiles(id, mail.getFileName());
            }
            //推送到目标客户端
            CometEngine engine = CometContext.getInstance().getEngine();
            Map<String, String> message = ImmutableMap.<String, String>builder()
                    .put("title", mail.getTitle())
                    .put("content", mail.getContent())
                    .build();
            engine.sendToAll(mail.getReceivers(), JSON.toJSON(message));
        }
    }

}
