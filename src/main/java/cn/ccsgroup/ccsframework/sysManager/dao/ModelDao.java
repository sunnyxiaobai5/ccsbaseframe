/**
 * 
 */
package cn.ccsgroup.ccsframework.sysManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.entity.Model;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.dao]  
 * @ClassName:    [ModelDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-11-18]   
 * @UpdateUser:   [hr，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-18，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [版本号]
 */

@Repository("modelDao")
public class ModelDao extends BaseDaoImpl{

	/**
	 * 新增模块信息
	 * @param map
	 * @return
	 */
	public boolean insertModel(Model arg0)
	{
		
		Model model = (Model)this.getSqlMapClientTemplate().insert("Model.insertModel", arg0);
		return model==null?true:false;
	}
	
	
	/**
	 * 删除功能操作
	 * @param funcId
	 * @return
	 */
	public boolean deleteModel(HashMap<String, Object> map)
	{
		String result = "success";
		int k = this.getSqlMapClientTemplate().delete("Model.deleteMod", map);
		
		return k>0?true:false;
	}
	
	/**
	 * 修改模块信息
	 * @param map
	 * @return
	 */
	public boolean updateModel(Model model)
	{
		int k = this.getSqlMapClientTemplate().update("Model.updateModel", model);
		return k>0?true:false;
	}
	
	
	/**
	 * 获取model信息
	 * @param map  
	 * @return
	 */
	public Model getModel(HashMap<String, String> map)
	{
		
		Model model=(Model)this.getSqlMapClientTemplate().queryForObject("Model.getModleByID",map);
		
		if(model!=null){
			
			return model;
		}else{
			return null;
		}
	}
	
	
	/**
	 * 分页查询模块列表
	 * @return
	 */
	public List<Model> selectModelByPage(HashMap<String, Object> map)
	{
		List<Model> vList = null;
		vList = (List<Model>)this.getSqlMapClientTemplate().queryForList("Model.getAllModleByPage",map);
		return vList;
	}
	
	/**
	 * 分页查询模糊模块
	 * @return
	 */
	public List<Model> getModleByCondition(HashMap<String, Object> map)
	{
		List<Model> vList = null;
		
		vList = (List<Model>)this.getSqlMapClientTemplate().queryForList("Model.getModleByCondition",map);
		return vList;
	}
	
	public List<Model> getSysUpdateModel(HashMap<String, Object> map)
	{
		return (List<Model>)this.getSqlMapClientTemplate().queryForList("Model.getSysUpdateModel",map);
	}
	
}
