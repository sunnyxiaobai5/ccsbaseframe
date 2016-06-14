
package cn.ccsgroup.ccsframework.sysManager.service;

import java.util.List;
import java.util.Map;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.entity.Func;
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
@TypeAnnotation("功能管理")
public interface FuncService {
	
	/**新增功能*/
	@MethodAnnotation("新增功能")
	public boolean saveFunc(Func func);
	
	/**
	 * 初始化功能
	 * @param funcId 功能Id
	 * @return 功能对象
	 */
	public Func findFuncById(Integer funcId);
	
	/**
	 * 删除FUNC
	 * @param funcId
	 * @return
	 */
	@MethodAnnotation("删除功能")
	public boolean deleteFunc(Integer funcId);
	
	/**
	 * 分页查询功能列表
	 */
	public EasyUIPage selectFuncByPage(EasyUIPage page,Map<String, Object> map);
	
	/**
	 * 修改功能
	 */
	@MethodAnnotation("修改功能")
	public boolean updateFunc(Func func);
	
	/**
	 * 
	 * @Title: getFuncOpsTree
	 * @Description: TODO(获取功能树)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> getFuncOpsTree(List<Integer> list);
	
	/**
	 * 分配操作
	 * @Title: addDisFuncOps
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param map
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("分配功能操作")
	public boolean updateDisFuncOps(Map<Integer, List<Integer>> map);
}
