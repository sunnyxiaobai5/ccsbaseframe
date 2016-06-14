
package cn.ccsgroup.ccsframework.sysManager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Ops;
import cn.ccsgroup.ccsframework.sysManager.dao.OpsDao;
import cn.ccsgroup.ccsframework.sysManager.service.OpsService;
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
@Service("OpsService")//注册为服务层，BEAN名称为在sysService
@Transactional//支持自动事务-到每个方法级
public class OpsServiceImpl implements OpsService {
	
	
	/**
	 * @Fields opsDao : TODO(操作的数据存储类)
	 */
	private OpsDao opsDao;


	/**
	 * @param opsDao the opsDao to set
	 */
	@Resource(name="opsDao")
	public void setOpsDao(OpsDao opsDao) {
		this.opsDao = opsDao;
	}


	public boolean saveOps(Ops arg0, Integer funcId) {
		boolean flag = false;
		Integer opsId = opsDao.saveOps(arg0);
		if(opsId != null)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("funcId", funcId); // 功能id
			map.put("opsId", opsId); // 操作id
			map.put("sortNo", (Integer)opsDao.queryForObject("Ops.queryFuncOpsSort", funcId)); // 当前序号
			opsDao.insert("Func.addDisFuncOps", map);
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除关系数据
	 */
	public boolean deleteOps(Map<String, Object> map) {
		return opsDao.deleteOps(map);
	}


	@Override
	public List<Ops> getOpsList(String funcId) {
		return opsDao.getOpsList(funcId);
	}


	/**
	 * 修改操作功能
	 */
	public boolean updateOps(Ops o) {
//		// 修改操作之前验证名字是否重复
//		/**isRepeat  
//		 * true 标识重复
//		 * false 可以修改
//		 * */
//		boolean isRepeat = opsDao.isRepeat(o.getOpsName(), o.getId());
//		if(!isRepeat)
//		{
//			// 封装修改的map
//			isRepeat = opsDao.updateOps(o); // 验证成功
//		}else
//		{
//			return !isRepeat; // 验证失败
//		}
//		return isRepeat;
		
		boolean isRepeat = opsDao.updateOps(o); // 验证成功
		return isRepeat;
	}
	
	/**
	 * 查询已有操作
	 */
	public EasyUIPage findAllOpsByPage(Map<String, Object> map,EasyUIPage page, SessionBean bean) {
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		page.setTotal((Integer) opsDao.queryForObject("Ops.queryCount", map));
		page.setRows(opsDao.findAllOpsByPage(map));
		return page;
	}


	/**
	 * 详细分配操作方法
	 * @param arg0 对应分配键值对
	 * @return 是否分配成功
	 */
	public List<TreeNode> findOpsTreeList(Integer funcId) {
		return opsDao.findOpsTreeList(funcId);
	}


	@Override
	public Ops selectOpsById(Integer opsId) {
		return opsDao.selectOpsById(opsId);
	}

}
