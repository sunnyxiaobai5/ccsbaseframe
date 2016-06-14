/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.entity.Func;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.dao]  
 * @ClassName:    [FuncDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [dl]   
 * @CreateDate:   [2013-11-20]   
 * @UpdateUser:   [dl，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-20，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */
@Repository("funcDao")
public class FuncDao extends BaseDaoImpl {

	/**
	 * 新增功能
	 * @param map
	 * @return
	 */
	public boolean addFunc(Func func)
	{
		Func vCheck = (Func)this.getSqlMapClientTemplate().insert("Func.insertFunc", func);
		return true;
	}
	
	/**
	 * 查询Func信息
	 * @param funcId 功能Id
	 * @return
	 */
	public Func findFuncById(Integer funcId)
	{
		return (Func)this.getSqlMapClientTemplate().queryForObject("Func.findFuncById", funcId);
	}
	
	/**
	 * 删除功能操作
	 * @param funcId
	 * @return
	 */
	public boolean deleteFunc(Map<String, Object> map)
	{
		int k = (int)this.getSqlMapClientTemplate().update("Func.deleteFunc", map);
		return k > 0 ? true : false;
	}
	
	/**
	 * 修改对应字段
	 * @param map 配置可修改的map
	 * @return 是否成功
	 */
	public boolean updateFunc(Func func)
	{
		int k =(int)this.getSqlMapClientTemplate().update("Func.updateFunc",func);
		return k > 0 ? true : false;
	}
	
	
	/**
	 * 分页查询功能列表
	 * @return
	 */
	public List<Func> selectFuncByPage(Map<String, Object> map)
	{
		List<Func> vList = null;
		vList = (List<Func>)this.getSqlMapClientTemplate().queryForList("Func.selectFuncByPage", map);
		return vList;
	}
	
	/**
	 * 查询功能分页总数
	 * @param map
	 * @return
	 */
	public Integer selectFuncByCount(Map<String, Object> map)
	{
		return (Integer)this.getSqlMapClientTemplate().queryForObject("Func.selectFuncByCount", map);
	}
	
	/**
	 * 
	 * @Title: getFuncOpsTree
	 * @Description: TODO(获取功能操作树)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> getFuncOpsTree(List<Integer> list)
	{
		if(list != null && list.size() > 0)
			return (List<TreeNode>)this.getSqlMapClientTemplate().queryForList("Func.selectFuncOpsTreeByList",list);
		else	
			return (List<TreeNode>)this.getSqlMapClientTemplate().queryForList("Func.selectFuncOpsTree");
	}
	
	/**
	 * 
	 * @Title: addDisFuncOps
	 * @Description: TODO(添加结构数据)
	 * @param     设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public boolean addDisFuncOps(Map<String, Object> map)
	{
		this.getSqlMapClientTemplate().insert("Func.addDisFuncOps", map);
		return true;
	}
	
	/**
	 * 删除结构数据
	 * @Title: delDisFuncOps
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param funcId
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean delDisFuncOps(Integer funcId)
	{
		int k = (int)this.getSqlMapClientTemplate().delete("Func.delDisFuncOps", funcId);
		return k > 0 ? true : false;
	}
}
