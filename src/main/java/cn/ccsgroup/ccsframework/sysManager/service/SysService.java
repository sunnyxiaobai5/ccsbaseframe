
package cn.ccsgroup.ccsframework.sysManager.service;

import java.util.HashMap;
import java.util.List;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Modfuncs;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;


/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.samples.business.service.UserService.java]  
 * @ClassName:    [UserService]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-11-15 上午10:56:09]   
 * @UpdateUser:   [hr(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15 上午10:56:09，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@TypeAnnotation("系统管理")
public interface SysService {
	
	
	/**
	 * 获取系统信息
	 * @param sysId
	 * @return 系统实体对象
	 */
	
	public LogiSystem getSysMod(String sysid);
	
	/**
	 * 添加系统功能
	 * @param map
	 * @return
	 */
	@MethodAnnotation("新增系统")
	public Integer saveSys(LogiSystem logiSystem);
	
	
	/**
	 * 修改系统功能
	 * @param map
	 * @return
	 */
	@MethodAnnotation("修改系统")
	public Integer updateSys(LogiSystem logiSystem);
	
	
	/**
	 * 删除系统
	 * @param sysid
	 * @return
	 */
	@MethodAnnotation("删除系统")
	public void deleteSys(Integer sysid);
	
	/**
	 * 查询logiSys信息
	 * @param map 
	 * @return
	 */
	public List<LogiSystem> getAllSystem(HashMap<String, Object> map);
	
	/**
	 * 模糊查询logiSys信息
	 * @param map 
	 * @return
	 */
	public EasyUIPage getLogiSystemBycondition(EasyUIPage page,HashMap<String, Object> map);
	
	/**
	 * 调动模块
	 * @param map
	 * @return
	 */
	public String distriModel(HashMap<String, Object> map);
	
	
	/**
	 * 临时获取所有系统
	 * @return
	 */
	public List<LogiSystem> getUserSysPower();
	
	
	/**
	 * 
	 * 获取待分配的模块
	 * @Title: getAllModle
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return List<Model>    返回类型
	 * @throws
	 */
	public List<Model> getAllModle();
	
	/**
	 * 获取模块功能关联关系
	 * @Title: selectModFuncs
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param map (sysid,status)
	 * @param @return    设定文件
	 * @return List<Modfuncs>    返回类型
	 * @throws
	 */
	public List<Modfuncs> selectModFuncs(String sysid);
	
	
	/**
	 *  获取功能节点
	 * @Title: selectAllFuncs
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return List<Func>    返回类型
	 * @throws
	 */
	public List<Func> selectAllFuncs();
	
	/**
	 * 获取已经使用树结构
	 * @Title: getSysUsedTreeList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param id
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> getSysUsedTreeList(Integer id);
	
	/**
	 * 获取系统logo
	 * @Title: getSysLogo
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param id
	 * @param @return    设定文件
	 * @return Byte[]    返回类型
	 * @throws
	 */
	public Object getSysLogo(Integer id);
	
	/**
	 * 
	 * @Title: getAssignModels
	 * @Description: 根据系统id获取已分配的模块
	 * @param sysId
	 * @return List<Ztree>
	 * @throws
	 */
	public List<Ztree> getAssignModels(Integer sysId);
	
	/**
	 * 
	 * @Title: getModelsWithChecked
	 * @Description: 获取所有模块，并带上checked状态
	 * @param page
	 * @param map
	 * @return EasyUIPage
	 * @throws
	 */
	public EasyUIPage getModelsWithChecked(EasyUIPage page,HashMap<String, Object> map);
	
	/**
	 * 
	 * @Title: saveAssignModels
	 * @Description: 保存分配给系统的模块
	 * @param ztrees
	 * @return Integer
	 * @throws
	 */
	@MethodAnnotation("保存分配模块")
	public Integer saveAssignModels(List<Ztree> ztrees,String id);
	
	public Model getModelById(String id);
	
	public List<Ztree> getAssignFuncs(String modId,String sysId);
	
	/**
	 * 
	 * @Title: getFuncs
	 * @Description: 查询所有功能
	 * @param page
	 * @param map
	 * @return EasyUIPage
	 * @throws
	 */
	public EasyUIPage getFuncs(EasyUIPage page,HashMap<String, Object> map);
	
	@MethodAnnotation("保存分配功能")
	public Integer saveAssignFuncs(List<Ztree> ztrees,String modId,String sysId);
	
	//删除系统模块和功能的关系
	@MethodAnnotation("删除已分配模块")
	public Integer deleteSysMod(Integer modid,Integer sysid);
	
}
