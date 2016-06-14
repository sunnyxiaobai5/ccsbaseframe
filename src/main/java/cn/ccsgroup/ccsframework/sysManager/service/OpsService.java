
package cn.ccsgroup.ccsframework.sysManager.service;

import java.util.List;
import java.util.Map;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.entity.Ops;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;



/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.samples.business.service.UserService.java]  
 * @ClassName:    [UserService]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [zhiqian.wang]   
 * @CreateDate:   [2013-11-15 上午10:56:09]   
 * @UpdateUser:   [zhiqian.wang(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15 上午10:56:09，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@TypeAnnotation("操作管理服务")
public interface OpsService {
	
	/**
	 * 新增操作
	 * @param arg0 操作对象
	 * @return 已保存的操作对象数据
	 */
	@MethodAnnotation("新增操作")
	public boolean saveOps(Ops arg0, Integer funcId);
	
	/**
	 * 删除操作
	 * @param arg0 当前删除对象
	 * @return 是否成功
	 */
	@MethodAnnotation("删除操作")
	public boolean deleteOps(Map<String, Object> map);
	
	/**
	 * 修改操作
	 * @param arg0
	 * @return 修改完成对象
	 */
	@MethodAnnotation("修改操作")
	public boolean updateOps(Ops o);
	
	/**
	 * 获取操作列表
	 * @param funcId 功能Id
	 * @return 功能下所有操作
	 */
	public List<Ops> getOpsList(String funcId);
	
	/**
	 * 查询所有的系统操作
	 * @return
	 */
	public EasyUIPage findAllOpsByPage(Map<String, Object> map, EasyUIPage page,SessionBean bean);
	
	public Ops selectOpsById(Integer opsId);
	
	/**
	 * 
	 * @Title: findOpsTreeList
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findOpsTreeList(Integer funcId);
}
