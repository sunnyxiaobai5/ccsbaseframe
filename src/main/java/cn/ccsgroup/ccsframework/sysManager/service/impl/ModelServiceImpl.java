
package cn.ccsgroup.ccsframework.sysManager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.sysManager.dao.ModelDao;
import cn.ccsgroup.ccsframework.sysManager.service.ModelService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.samples.business.service.impl.UserServiceImpl.java]  
 * @ClassName:    [UserServiceImpl]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-11-15 上午10:58:09]   
 * @UpdateUser:   [hr(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15 上午10:58:09，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Transactional
@Service("modelService")
public class ModelServiceImpl implements ModelService {
	
	
	/**
	 * @Fields modelDao : TODO(用一句话描述这个变量表示什么)
	 */
	private ModelDao modelDao;


	/**
	 * @param modelDao the modelDao to set
	 */
	@Resource(name="modelDao")
	public void setModelDao(ModelDao modelDao) {
		this.modelDao = modelDao;
	}


	@Override
	public boolean saveModel(Model model) {
		return modelDao.insertModel(model);
	}


	@Override
	public Model getModel(String modelId) {
		HashMap<String,String> map=new HashMap<String, String>();
		map.put("status",AppConst.STATUS_DISABLE.toString());
		map.put("id",modelId);
		return modelDao.getModel(map);
	}


	@Override
	public List<Model> selectModelByPage(HashMap<String, Object> map) {
		return modelDao.selectModelByPage(map);
	}


	@Override
	public boolean deleteModel(String modelId) {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("status",AppConst.STATUS_DISABLE);
		map.put("id", modelId);
		return modelDao.deleteModel(map);
	}


	/**
	 * 分页查询模块
	 */
	public EasyUIPage getModleByCondition(EasyUIPage page, HashMap<String, Object> map) {
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		page.setTotal((Integer) modelDao.queryForObject("Model.queryCount",map));
		page.setRows(modelDao.getModleByCondition(map));
		return page;
	}


	@Override
	public boolean updateModel(Model model) {
		return modelDao.updateModel(model);
	}


	/**
	 * 返回临时的treeNode
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> selectShowTree(Map<Integer, ArrayList<Integer>> map) {
		
		List<TreeNode> list = new ArrayList<TreeNode>();
		// 封装树结构
		for(Integer key : map.keySet())
		{
			TreeNode tree =  (TreeNode)modelDao.queryForObject("Model.queryModelById", key);
			ArrayList<Integer> vList = map.get(key);
			if(vList != null && vList.size() > 0)
			{
				List<TreeNode> fTree = (List<TreeNode> )modelDao.queryForList("Func.queryFuncByList", vList);
				if(fTree != null && fTree.size() > 0) tree.setChildren(fTree);
			}
			list.add(tree);
		}
		return list;
	}


	public EasyUIPage getSysUpdateModel(EasyUIPage page, HashMap<String,Object> map) {
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		page.setTotal((Integer) modelDao.queryForObject("Model.queryCount",map));
		page.setRows(modelDao.getSysUpdateModel(map));
		return page;
	}

}
