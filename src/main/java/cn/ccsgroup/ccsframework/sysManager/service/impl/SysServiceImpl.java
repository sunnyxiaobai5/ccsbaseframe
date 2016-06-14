
package cn.ccsgroup.ccsframework.sysManager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import oracle.sql.BLOB;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.Modfuncs;
import cn.ccsgroup.ccsframework.sysManager.dao.SysDao;
import cn.ccsgroup.ccsframework.sysManager.service.SysService;
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
@Transactional//支持自动事务-到每个方法级
@Service("sysService")//注册为服务层，BEAN名称为在sysService
public class SysServiceImpl implements SysService {
	
	
	@Resource(name="sysDao")
	private SysDao sysDao;

	/**
	 * 添加系统
	 */
	public Integer saveSys(LogiSystem logiSystem) {
		Integer sysId = 0;
		sysId = sysDao.addSysMod(logiSystem);
		return sysId;
	}
	
	/**
	 * 修改系统
	 */
	@Transactional
	public Integer updateSys(LogiSystem logiSystem) {
		Integer up = 0;
		up = sysDao.updateSys(logiSystem);
		return up;
	}
	
	/**
	 * 获取已经存在的树
	 */
	public List<TreeNode> getSysUsedTreeList(Integer id)
	{
		return sysDao.getSysUsedTreeList(id);
	}
	
	
	/**
	 * 转换格式
	 * @Title: changeFly
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param list
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	private List<TreeNode> changeFly(List list)
	{
		List<TreeNode> value = new ArrayList<TreeNode>();
		JSONArray tojsa = new JSONArray();
		tojsa.addAll(list);
		value= (List<TreeNode> )tojsa.toCollection(tojsa, TreeNode.class);
		return value;
	}
	

	/**
	 * 删除系统
	 */
	public void deleteSys(Integer sysid) {
		HashMap<String,Object> map=new HashMap<String, Object>();
		map.put("status",AppConst.STATUS_DISABLE.toString());
		map.put("id",sysid);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sysId", sysid);
		this.sysDao.deleteModFuncsNotInModels(map);
		sysDao.deleteSysModBySysId(sysid);
		sysDao.deleteLogiSys(map);
	}




	@Override
	public LogiSystem getSysMod(String sysid) {
		HashMap<String,Object> map=new HashMap<String, Object>();
		map.put("status",AppConst.DISABLE);
		map.put("id",sysid);
		return sysDao.getSysMod(map);
	}

	@Override
	public String distriModel(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogiSystem> getAllSystem(HashMap<String, Object> map) {
		return sysDao.getAllSystem(map);
	}


	@Override
	public EasyUIPage getLogiSystemBycondition(EasyUIPage page,HashMap<String, Object> map) {
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		page.setTotal((Integer) sysDao.queryForObject("LogiSystem.queryCount", map));
		page.setRows(sysDao.getlogiSystemBycondition(map));
		return page;
	}


	@Override
	public List<LogiSystem> getUserSysPower() {
		return sysDao.getUserSysPower();
	}
	@Override
	public List<Model> getAllModle() {
		Integer status=AppConst.STATUS_DISABLE;
		return sysDao.selectModel(status);
	}

	@Override
	public List<Modfuncs> selectModFuncs(String sysid) {
		HashMap<String,Object> map=new HashMap<String, Object>();
		map.put("sysid", sysid);
		map.put("status",AppConst.DISABLE);
		 
		return sysDao.selectModFuncs(map);
	}


	@Override
	public List<Func> selectAllFuncs() {
		return sysDao.selectAllFuncs();
	}

	
	/**
	 * 获取系统logo
	 */
	public Object getSysLogo(Integer id) {
		Map map = (Map)sysDao.queryForObject("LogiSystem.getSysLogo", id);
		return map;		
	}
	
	@Override
	public List<Ztree> getAssignModels(Integer sysId) {
		List<Ztree> assignModels = new ArrayList<Ztree>();
		assignModels = this.sysDao.getModelsBySystemId(sysId);
		
		for(int i = 0; i < assignModels.size(); i++){
			assignModels.get(i).setDropInner(false);
		}
		
		return assignModels;
	}

	@Override
	public EasyUIPage getModelsWithChecked(EasyUIPage page,
			HashMap<String, Object> map) {
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		page.setTotal((Integer) sysDao.queryForObject("LogiSystem.getModelsCount", map));
		page.setRows(sysDao.getModelsWithChecked(map));
		return page;
	}

	@Override
	public Integer saveAssignModels(List<Ztree> ztrees,String id) {
		int count = this.sysDao.deleteSysModBySysId(Integer.parseInt(id));
		Integer sysModId = 0;
		List<Integer> modIds = new ArrayList<Integer>();
		if(ztrees!=null&&ztrees.size()>0){
			Map<String, Object> params = new HashMap<String, Object>();
			for(int i=0;i<ztrees.size();i++){
				params.put("sysId", id);
				params.put("modId", ztrees.get(i).getTid());
				params.put("sortNo", i);
				sysModId = this.sysDao.addSysMod(params);
				modIds.add(Integer.parseInt(ztrees.get(i).getTid()));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysId", id);
		map.put("modIds", modIds);
		this.sysDao.deleteModFuncsNotInModels(map);
		return sysModId;
	}

	@Override
	public Model getModelById(String id) {
		Model model = null;
		model = this.sysDao.getModelById(id);
		return model;
	}

	@Override
	public List<Ztree> getAssignFuncs(String modId,String sysId) {
		List<Ztree> assignFuncs = new ArrayList<Ztree>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("modId", modId);
		params.put("sysId", sysId);
		assignFuncs = this.sysDao.getFucsByModId(params);
		
		for(int i = 0; i < assignFuncs.size(); i++){
			assignFuncs.get(i).setDropInner(false);
		}
		
		return assignFuncs;
	}

	@Override
	public EasyUIPage getFuncs(EasyUIPage page, HashMap<String, Object> map) {
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		page.setTotal((Integer) sysDao.queryForObject("LogiSystem.getFuncsCount", map));
		page.setRows(sysDao.getFuncs(map));
		return page;
	}

	@Override
	public Integer saveAssignFuncs(List<Ztree> ztrees, String modId,
			String sysId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modId", modId);
		map.put("sysId", sysId);
		int count = this.sysDao.deleteModFuncs(map);
		Integer modFuncId = 0;
		if(ztrees!=null&&ztrees.size()>0){
			Map<String, Object> params = new HashMap<String, Object>();
			for(int i=0;i<ztrees.size();i++){
				params.put("modId", modId);
				params.put("sysId", sysId);
				params.put("funcId", ztrees.get(i).getTid());
				params.put("sortNo", i);
				modFuncId = this.sysDao.addModFunc(params);
			}
		}
		return modFuncId;
	}
	
	public Integer deleteSysMod(Integer modid,Integer sysid){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("modId", modid);
		map.put("sysId", sysid);
		int count1 = this.sysDao.deleteBySysIdModId(map);
		int count2 = this.sysDao.deleteModFuncs(map);
		
		return count2;
	}
	
}
