package cn.ccsgroup.ccsframework.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cn.ccsgroup.ccsframework.base.dao.GenericQueryDao;
import cn.ccsgroup.ccsframework.entity.Position;
import cn.ccsgroup.ccsframework.entity.RegionCode;
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.entity.Information;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.entity.TypeList;
import cn.ccsgroup.ccsframework.entity.UserManager;

@Service(value="gennericQueryService")
public class GennericQueryService {
	
	@Resource(name="genericQueryDao")
	private GenericQueryDao genericQueryDao;
	
	
	
	public int queryTestCount(Map map){
		return (Integer) genericQueryDao.getSqlMapClientTemplate().queryForObject("Test.testQueryCount", map);
	}
	
	public List<TypeList> getTypeList(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("TypeList.getTypyListByKind",map);
	}
	
	public List<Position> getUserPostion(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("UserManager.getUserPostion",map);
	}
	
	public List<RegionCode> getCurrentRegionCode(Map map)
	{
		return genericQueryDao.getSqlMapClientTemplate().queryForList("RegionManage.getCurrentRegionCodeList",map);
	}
	public List<RegionCode> getFatherRegionCode(Map map)
	{
		return genericQueryDao.getSqlMapClientTemplate().queryForList("RegionManage.getFatherRegionCodeList",map);
	}
	
	/**
	 * @Title: getTypeListSearch
	 * @Description: 通用标准设置搜索
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<TypeList>    返回类型
	 * @throws
	 */
	public List<TypeList> getTypeListSearch(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("TypeList.getTypyListSearch",map);
	}
	
	/**
	 * @Title: getTypeListSearchCount
	 * @Description: 通用标准设置搜索查询条数
	 * @param @param map
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int getTypeListSearchCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("TypeList.getTypyListSearchCount",map);
	}
	
	/**
	 * @Title: getInformation
	 * @Description: 公告搜索
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<Information>    返回类型
	 * @throws
	 */
	public List<Information> getInformation(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("Information.getInformationSearch",map);
	}
	
	/**
	 * @Title: getInformationCount
	 * @Description: 公告所有查询条数
	 * @param @param map
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int getInformationCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("Information.getInformationSearchCount",map);
	}
	
	/**
	 * @Title: getUserList
	 * @Description: 用户管理搜索
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<UserManager>    返回类型
	 * @throws
	 */
	public List<UserManager> getUserList(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("UserManager.getUserListSearch",map);
	}
	
	/**
	 * @Title: getUserListCount
	 * @Description: 用户管理收查询条数
	 * @param @param map
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int getUserListCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("UserManager.getUserListCount",map);
	}
	
	/**
	 * @Title: getModel
	 * @Description: 模块管理搜索
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<Model>    返回类型
	 * @throws
	 */
	public List<Model> getModel(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("Model.getModsSearch",map);
	}
	
	/**
	 * @Title: getModelCount
	 * @Description: 模块管理搜索查询条数
	 * @param @param map
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int getModelCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("Model.getModelCount",map);
	}
	
	/**
	 * @Title: getSystem
	 * @Description: 系统管理搜索
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<Model>    返回类型
	 * @throws
	 */
	public List<LogiSystem> getSystem(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("LogiSystem.getSystemSearch",map);
	}
	
	/**
	 * @Title: getSystemCount
	 * @Description: 系统管理搜索查询条数
	 * @param @param map
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int getSystemCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("LogiSystem.getSystemCount",map);
	}
	
	/**
	 * @Title: getFunc
	 * @Description: 功能管理搜索
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<LogiSystem>    返回类型
	 * @throws
	 */
	public List<Func> getFunc(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("Func.getFuncSearch",map);
	}
	
	/**
	 * @Title: getFuncCount
	 * @Description: 功能管理搜索查询条数
	 * @param @param map
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int getFuncCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("Func.getFuncCount",map);
	}
	
	/**
	 * @Title: getRole
	 * @Description: 角色管理搜索
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<Role>    返回类型
	 * @throws
	 */
	public List<Role> getRole(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("Role.getRoleSearch",map);
	}
	
	/**
	 * @Title: getRoleCount
	 * @Description: 角色管理搜索查询条数
	 * @param @param map
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int getRoleCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("Role.getRoleCount",map);
	}
	
	/**
	 * @Title: getPosition
	 * @Description: 职位搜索
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<Role>    返回类型
	 * @throws
	 */
	public List<Position> getPosition(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("Position.getPositionSearch",map);
	}
	
	/**
	 * @Title: getPositionCount
	 * @Description: 职位搜索查询条数
	 * @param @param map
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int getPositionCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("Position.getPositionCount",map);
	}
	
	/**
	 * @Title: getsyslogs
	 * @Description: 操作日志搜索
	 * @param @param map
	 * @param @return    设定文件
	 * @return List<Position>    返回类型
	 * @throws
	 */
	public List<Position> getsyslogs(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("SysLogs.getsyslogsSearch",map);
	}
	
	/**
	 * @Title: getsyslogsCount
	 * @Description: 操作日志搜索查询条数
	 * @param @param map
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	public int getsyslogsCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("SysLogs.getsyslogsCount",map);
	}
	
	public List<Model> getModelsWithChecked(Map map){
		return genericQueryDao.getSqlMapClientTemplate().queryForList("Model.getModsSearch",map);
	}
	
	public int getModelsWithCheckedCount(Map map){
		return (Integer)genericQueryDao.getSqlMapClientTemplate().queryForObject("Model.getModelCount",map);
	}
}
