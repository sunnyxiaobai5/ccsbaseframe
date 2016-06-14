package cn.ccsgroup.ccsframework.sysparamManager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.entity.Annex;
import cn.ccsgroup.ccsframework.entity.Information;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.service.InformationService.java]  
 * @ClassName:    [InformationService]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [lenovo]   
 * @CreateDate:   [2014-1-6 下午04:30:17]   
 * @UpdateUser:   [lenovo(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-6 下午04:30:17，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@TypeAnnotation("公告管理")
public interface InformationService {

	/**
	 * @Title: getInformationList
	 * @Description: TODO(获取公告列表带翻页)
	 *  @param page
	 *  @return   设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	public EasyUIPage getInformationList(EasyUIPage page);
	/**
	 * @Title: saveInformation
	 * @Description: TODO(保存公告)
	 *  @param information
	 *  @param appids
	 *  @return   设定文件
	 * @return Integer    返回类型
	 * @throws
	 */
	@MethodAnnotation("新增公告")
	public Integer saveInformation(Information information,String[] appids);
	/**
	 * @Title: saveAnnex
	 * @Description: TODO(保存附件)
	 *  @param annexList   设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void saveAnnex(List<Annex> annexList);
	/**
	 * @Title: deleteInformation
	 * @Description: TODO(删除公告)
	 *  @param id
	 *  @return   设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("删除公告")
	public boolean deleteInformation(int id);
	/**
	 * @Title: getSendAppList
	 * @Description: TODO(获取已发送系统的列表)
	 *  @param infoid
	 *  @return   设定文件
	 * @return List    返回类型
	 * @throws
	 */
	public List getSendAppList(Integer infoid);
	/**
	 * @Title: getAppList
	 * @Description: TODO(获取需发送系统的列表)
	 *  @return   设定文件
	 * @return List    返回类型
	 * @throws
	 */
	public List getAppList();
	/**
	 * @Title: getInformationsById
	 * @Description: TODO(获取公告的详情)
	 *  @param id
	 *  @return   设定文件
	 * @return Information    返回类型
	 * @throws
	 */
	public Information getInformationsById(Integer id);
	/**
	 * @Title: getAnnextListById
	 * @Description: TODO(通过公告ID获取附件列表)
	 *  @param id
	 *  @return   设定文件
	 * @return List<Annex>    返回类型
	 * @throws
	 */
	public List<Annex> getAnnextListById(Integer id);
	/**
	 * @Title: saveInformationMapping
	 * @Description: TODO(关联需发送的系统)
	 *  @param id
	 *  @param appids
	 *  @return   设定文件
	 * @return Integer    返回类型
	 * @throws
	 */
	public Integer saveInformationMapping(Integer id,String[] appids,String updateUser);
	/**
	 * @Title: updateInformation
	 * @Description: TODO(修改公告)
	 *  @param information
	 *  @return   设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("修改公告")
	public boolean updateInformation(Information information);
	/**
	 * @Title: deleteAnnex
	 * @Description: TODO(删除附件)
	 *  @param annexList
	 *  @return   设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean deleteAnnex(List<Annex> annexList);
	/**
	 * @Title: queryNoticeList
	 * @Description: TODO(首页获取公告列表)
	 *  @param sysid
	 *  @return   设定文件
	 * @return List<Information>    返回类型
	 * @throws
	 */
	public List<Information> queryNoticeList(Integer[] sysid);
	/**
	 * @Title: queryAnnexById
	 * @Description: TODO(通过ID查询附件详情)
	 *  @param id
	 *  @return   设定文件
	 * @return Annex    返回类型
	 * @throws
	 */
	public Annex queryAnnexById(Integer id);
}
