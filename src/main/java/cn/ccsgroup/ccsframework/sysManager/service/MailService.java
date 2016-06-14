/**********************************************************************
 * <p>文件名：MailService.java </p>
 * <p>文件描述：
 * @project_name：CCSBaseFrame
 * @author yangfan
 * @date 2014/11/14 13:12
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2014
 *                    All Rights Reserved.
 */
package cn.ccsgroup.ccsframework.sysManager.service;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.entity.Mail;
import cn.ccsgroup.ccsframework.exception.BussinessException;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * ***************************************************************************
 *
 * @Package: [cn.ccsgroup.ccsframework.sysManager.service]
 * @ClassName: [MailService]
 * @Description: [功能]
 * @Author: [yangfan]
 * @CreateDate: [2014/11/14 13:12]
 * @UpdateUser: [yangfan]
 * @UpdateDate: [2014/11/14 13:12]
 * @UpdateRemark: [新建]
 * @Version: [v1.0]
 */
@TypeAnnotation("消息")
public interface MailService {
    /**
     * 查询消息列表
     * @param map 查询参数
     * @param page
     * @param sessionBean
     * @return
     */
    EasyUIPage list(Map<String, Object> map, EasyUIPage page, SessionBean sessionBean);

    /**
     * 查询消息列表
     * @param map 查询参数
     * @return
     */
    List<Mail> getMails(Map<String, Object> map);

    /**
     * 总数
     * @param queryMap 查询参数
     * @return
     */
    int getMailsCount(Map<String, Object> queryMap);

    /**
     * 保存附件到MongoDB
     * @param fileName 文件名称
     * @param file 文件
     */
    @MethodAnnotation("保存附件到MongoDB")
    void saveFile(String fileName, MultipartFile file) throws BussinessException;

    /**
     * 发送消息（邮件）
     * @param mail 消息实体
     */
    @MethodAnnotation("发送消息")
    void sendMail(Mail mail);

    /**
     * 关联消息和附件
     * @param id 消息id
     * @param fileName 文件名称
     */
    void saveMailFiles(Integer id, String[] fileName);

    /**
     * 通过id查询消息
     * @param id 消息id
     * @return
     */
    Mail getMailById(int id);

    /**
     * 查看消息详情
     *
     * @param id 消息id
     * @return
     */
    Mail viewMail(int id);

    /**
     * 查看消息（改变消息状态为已读）
     * @param id
     */
    void changeMailState(int id);

    /**
     * 更新消息
     * @param paraMap 参数
     */
    @MethodAnnotation("更新消息")
    void updateMail(Map<String, Object> paraMap);

    /**
     * 查询消息对应的附件
     * @param id 消息id
     * @return
     */
    List<Map<String, Object>> getMailFile(int id);

    /**
     * 从mongodb下载文件
     * @param file
     * @param outputStream
     */
    void getFileFromMongo(String file, ServletOutputStream outputStream) throws BussinessException;

    /**
     * 删除消息
     * @param id 消息id
     * @return
     */
    @MethodAnnotation("删除消息")
    boolean deleteMail(Integer id);
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