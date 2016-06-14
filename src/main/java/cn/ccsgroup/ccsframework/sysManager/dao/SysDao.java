/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.dao;

import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Modfuncs;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.dao]  
 * @ClassName:    [SysDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-11-18]   
 * @UpdateUser:   [hr，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-18，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Repository("sysDao")
public class SysDao extends BaseDaoImpl{

	
	/**
	 * 获取系统信息
	 * @param sysId
	 * @return 系统实体对象
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public LogiSystem getSysMod(HashMap<String,Object> map)
	{
		
		LogiSystem sys = (LogiSystem)this.getSqlMapClientTemplate().queryForObject("LogiSystem.getlogiSystemByID",map);
		
//		sysMod = (LogiSystem)this.getSqlMapClientTemplate().queryForObject("SysMod.querySysMod",sysId);
//		// 特殊处理logo
//		HashMap<String, String> map = (HashMap<String, String>)this.getSqlMapClientTemplate().queryForObject("SysMod.querySysLogo",sysId);
//		if(map.get("LOGO") != null && !"".equals(map.get("LOGO"))) // 存在logo
//		{
//			sysMod.setSysIcon(toLogo(null));
//		}else
//		{
//			sysMod.setSysIcon("-100"); // 无图片状态
//		}
//		List<Model> m_list = this.getSqlMapClientTemplate().queryForList("SysMod.queryCacheModel",sysId);
//		if(m_list != null)
//		{
//			sysMod.setmList(m_list);
//		}else
//		{
//			sysMod.setmList(new ArrayList<Model>());
//		}
		return sys;
	}
	
	/**
	 * 新增系统
	 * @Title: addSysMod
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param SysMod
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public Integer addSysMod(LogiSystem logiSystem)
	{
		System.out.println("SysDao addSysMod working.......");
		Integer i=(Integer)this.getSqlMapClientTemplate().insert("LogiSystem.insertSys",logiSystem);
		return i;
	}
	
	/**
	 * 获取已存在的树结构
	 * @Title: getSysUsedTreeList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param id
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> getSysUsedTreeList(Integer id)
	{
		return (List<TreeNode>)this.getSqlMapClientTemplate().queryForList("LogiSystem.getSysUsedTreeList" , id);
	}
	
	
	/**
	 * 添加系统模块关系
	 * @Title: addSysModlist
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public Integer addSysMod(Map<String, Object> map){
		Integer count = (Integer) this.getSqlMapClientTemplate().insert("LogiSystem.insertSysmod", map);
		return count;
	}
	
	/**
	 * 添加模块功能关系
	 * @Title: addModFunclist
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param mflist
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public Integer addModFunc(Map<String, Object> map){
		Integer id = (Integer) this.getSqlMapClientTemplate().insert("LogiSystem.insertModfuncs", map);
		return id;
	}
	/**
	 * 修改系统功能
	 * @param map
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Integer updateSys(LogiSystem logiSystem)
	{
		
		Integer k = this.getSqlMapClientTemplate().update("LogiSystem.updateSys", logiSystem);
		return k;
	}
	
	/**
	 * 查询logiSys信息
	 * @param map 系统Id,status
	 * @return
	 */
	public LogiSystem getSystem(HashMap<String, String> map)
	{
		LogiSystem logisys = (LogiSystem)this.getSqlMapClientTemplate().queryForObject("LogiSystem.getlogiSystemByID", map);
		return logisys;
	}
	
	
	/**
	 * 查询logiSys信息
	 * @param map 
	 * @return
	 */
	public List<LogiSystem> getAllSystem(HashMap<String, Object> map)
	{
		List<LogiSystem> syslist=(List<LogiSystem>)this.getSqlMapClientTemplate().queryForList("LogiSystem.getAllLogiSystem", map);
		return syslist;
	}
	
	
	/**
	 * 模糊查询logiSys信息
	 * @param map 系统name
	 * @return
	 */
	public List<LogiSystem> getlogiSystemBycondition(HashMap<String, Object> map)
	{
		List<LogiSystem> syslist=(List<LogiSystem>)this.getSqlMapClientTemplate().queryForList("LogiSystem.getlogiSystemBycondition", map);
		return syslist;
	}
	
	
	/**
	 * 
	 * @Title: deleteSysMod
	 * @Description: TODO(删除关系)
	 * @param @param sysId
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public Integer deleteSysModBySysId(Integer sysId)
	{
		int k = this.getSqlMapClientTemplate().delete("LogiSystem.deleteSysModBySysId", sysId);
		return k;
	}
	
	/**
	 * @Title: deleteLogiSys
	 * @Description: TODO(删除系统)
	 * @param @param map
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean deleteLogiSys(Map<String, Object> map)
	{
		int  k = this.getSqlMapClientTemplate().update("LogiSystem.deleteSys", map);
		return k >0?true:false;
	}
	
	
	/**
	 * 查询模块列表
	 * @return
	 */
	public List<Model> selectModel(Integer status)
	{
		List<Model> vList = (List<Model>)this.getSqlMapClientTemplate().queryForList("Model.getAllModle",status);
		return vList;
	}
	
	
	/**
	 * 查询模块功能关联关系
	 * @return
	 */
	public List<Modfuncs> selectModFuncs(HashMap<String, Object> map)
	{
		List<Modfuncs> vList = (List<Modfuncs>)this.getSqlMapClientTemplate().queryForList("modfuncs.getModFuncs",map);
		return vList;
	}
	
	/**
	 * 查询模块功能关联关系
	 * @return
	 */
	public List<Func> selectAllFuncs()
	{
		List<Func> vList = (List<Func>)this.getSqlMapClientTemplate().queryForList("Func.selectAllFuncs");
		return vList;
	}
	
	
	/**
	 * 将数据库图片转换为string
	 * @param b
	 * @return
	 */
	private String toLogo(Blob b)
	{
		return "";
	}
	
	public List<LogiSystem> getUserSysPower()
	{
		return null;
	}
	
	public List<Ztree> getModelsBySystemId(Integer sysId){
		
		List<Ztree> assignModels = (List<Ztree>)this.getSqlMapClientTemplate().queryForList("LogiSystem.getAssignModels",sysId);
		return assignModels;
	}
	
	public List<Model> getModelsWithChecked(HashMap<String, Object> map)
	{
		List<Model> models=(List<Model>)this.getSqlMapClientTemplate().queryForList("LogiSystem.getModelsWithChecked", map);
		return models;
	}
	
	public Model getModelById(String modId){
		Model model = (Model) this.getSqlMapClientTemplate().queryForObject("LogiSystem.getModelById", modId);
		return model;
	}
	
	public List<Ztree> getFucsByModId(HashMap<String, Object> map){
		
		List<Ztree> assignFuncs = (List<Ztree>)this.getSqlMapClientTemplate().queryForList("LogiSystem.getFucsByModId",map);
		return assignFuncs;
	}
	
	public List<Func> getFuncs(HashMap<String, Object> map)
	{
		List<Func> funcs=(List<Func>)this.getSqlMapClientTemplate().queryForList("LogiSystem.getFuncs", map);
		return funcs;
	}
	
	public Integer deleteModFuncs(Map<String, Object> map)
	{
		int k = this.getSqlMapClientTemplate().delete("LogiSystem.deleteModfuncs", map);
		return k;
	}
	
	public Integer deleteModFuncsNotInModels(Map<String, Object> map)
	{
		int k = this.getSqlMapClientTemplate().delete("LogiSystem.deleteModFuncsNotInModels", map);
		return k;
	}
	
	public Integer deleteBySysIdModId(Map<String, Object> map)
	{
		int k = this.getSqlMapClientTemplate().delete("LogiSystem.deleteSysMod", map);
		return k;
	}
}
