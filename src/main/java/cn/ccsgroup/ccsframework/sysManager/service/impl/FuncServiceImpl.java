
package cn.ccsgroup.ccsframework.sysManager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Func;
import cn.ccsgroup.ccsframework.sysManager.dao.FuncDao;
import cn.ccsgroup.ccsframework.sysManager.service.FuncService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.samples.business.service.impl.UserServiceImpl.java]  
 * @ClassName:    [UserServiceImpl]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [zhiqian.wang]   
 * @CreateDate:   [2013-11-15 上午10:58:09]   
 * @UpdateUser:   [zhiqian.wang(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15 上午10:58:09，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Transactional
@Service("FuncService")
public class FuncServiceImpl implements FuncService {
	
	
	@Resource(name="funcDao")
	private FuncDao funcDao;

	/**
	 * 新增功能
	 * @param func 功能对象
	 */
	public boolean saveFunc(Func func) {
		return funcDao.addFunc(func);
	}

	/**
	 * 初始化Func
	 */
	public Func findFuncById(Integer funcId) {
		return funcDao.findFuncById(funcId);
	}


	/**
	 * 分页查询功能列表
	 */
	public EasyUIPage selectFuncByPage(EasyUIPage page,Map<String, Object >map) {
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		page.setTotal((Integer) funcDao.queryForObject("Func.selectFuncByCount", map));
		page.setRows(funcDao.selectFuncByPage(map));
		return page;
	}

	/**
	 * 删除功能
	 */
	public boolean deleteFunc(Integer funcId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", funcId);
		map.put("status", AppConst.STATUS_DISABLE);
		return funcDao.deleteFunc(map);
	}

	/**
	 * 修改功能
	 */
	public boolean updateFunc(Func func) {
		return funcDao.updateFunc(func);
	}

	/**
	 * 获取功能树
	 */
	public List<TreeNode> getFuncOpsTree(List<Integer> list) {
		return funcDao.getFuncOpsTree(list);
	}

	/**
	 * 分配操作
	 */
	public boolean updateDisFuncOps(Map<Integer, List<Integer>> map) {
		boolean result =  false;
		if(!map.isEmpty())
		{
			for(Integer key : map.keySet())
			{
				result = funcDao.delDisFuncOps(key); // 保存之前先删除配置
				List<Integer> opsList  =  map.get(key);
				/// 特殊处理<未选择任何操作的情况下>
				if(opsList.size() <= 0) result = true;
				for(int index = 0; index < opsList.size() ; index ++)
				{
					HashMap<String, Object> related = new HashMap<String, Object>();
					related.put("funcId", key); // 功能id
					related.put("opsId", opsList.get(index)); // 操作id
					related.put("sortNo", index + 1); // 当前序号
					result = funcDao.addDisFuncOps(related);
				}
			}
		}
		return result;
	}
}
