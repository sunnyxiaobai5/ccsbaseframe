/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.entity.Ops;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.dao]  
 * @ClassName:    [OpsDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-11-19]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-19，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
@Repository("opsDao")
public class OpsDao extends BaseDaoImpl{
	
	
	/**
	 * 新增操作
	 * @return
	 */
	public Integer saveOps(Ops arg0)
	{
		Integer key =  (Integer)this.getSqlMapClientTemplate().insert("Ops.insertOps",arg0);
		return key;
	}
	
	
	/**
	 * 修改操作
	 * @param arg0 修改对象
	 * @return
	 */
	public boolean updateOps(Ops o)
	{
		int k = (int)this.getSqlMapClientTemplate().update("Ops.updateOps", o);
		return k > 0 ? true : false;
	}
	
	/**
	 * 名字是否重复
	 * @return
	 */
	public boolean isRepeat(String opsName,Integer opsId)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("opsId", opsId);
		map.put("opsName", opsName);
		List<Ops> vList = this.getSqlMapClientTemplate().queryForList("Ops.findIsRepeatByName",map);
		return vList != null && vList.size() > 0 ? true : false;
	}
	
	
	
	/**
	 * 判断ops是否已经被使用
	 * @return
	 */
	public boolean isUsedByOpsId(Integer opsId)
	{
		List<Ops> vList = this.getSqlMapClientTemplate().queryForList("Ops.findIsUsedByOpsId",opsId);
		return vList != null && vList.size() > 0 ? true : false;
	}
	
	/**
	 * @param arg0删除对象
	 * @return
	 */
	public boolean deleteOps(Map<String, Object> map)
	{
		int k = (int)this.getSqlMapClientTemplate().update("Ops.deleteFuncOps", map);
		return k > 0 ? true : false;
	}
	
	/**
	 * 查询操作
	 * @param map
	 * @return
	 */
	public Ops selectOpsById(Integer opsId)
	{
		return (Ops)this.getSqlMapClientTemplate().queryForObject("Ops.findOps",opsId);
	}
	
	
	/**
	 * 获取OPS列表
	 * @param funcId 功能Id
	 * @return
	 */
	public List<Ops> getOpsList(String funcId)
	{
		return this.getSqlMapClientTemplate().queryForList("Ops.findOpsByFuncId", funcId);
	}
	
	/**
	 * 查询所有操作
	 * @return
	 */
	public List<Ops> findAllOpsByPage(Map<String, Object> map)
	{
		return this.getSqlMapClientTemplate().queryForList("Ops.findAllOpsByPage", map);
	}
	
	
	/**
	 * 获取现有所有操作树
	 * @Title: findOpsTreeList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findOpsTreeList(Integer funcId)
	{
		if(funcId != null && !"".equals(funcId)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("funcId", funcId);
			return this.getSqlMapClientTemplate().queryForList("Ops.findOpsTreeListByFuncId", map);
		}
		else
			return this.getSqlMapClientTemplate().queryForList("Ops.findOpsTreeList");
	}
	
	/**
	 * 添加关系
	 * @return
	 */
	public boolean addRelation()
	{
		return false;
	}
}
