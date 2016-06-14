
package cn.ccsgroup.ccsframework.sysManager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;


/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.samples.business.service.UserService.java]  
 * @ClassName:    [UserService]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [hr]   
 * @CreateDate:   [2013-11-15 上午10:56:09]   
 * @UpdateUser:   [hr(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15 上午10:56:09，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@TypeAnnotation("模块管理")
public interface ModelService {
	
	/**新增模块*/
	@MethodAnnotation("新增模块")
	public boolean saveModel(Model model);
	
	/**
	 * 获取模块
	 * @param model 模块Id
	 * @return 功能对象
	 */
	public Model getModel(String modelId);
	
	/**
	 * 分页查询模块列表
	 */
	public List<Model> selectModelByPage(HashMap<String, Object> map);
	
	
	/**
	 * 模糊查询模块列表
	 */
	public EasyUIPage getModleByCondition(EasyUIPage page, HashMap<String, Object> map);
	
	/**
	 * 删除模块
	 * @Title: delModel
	 * @param @param modelId
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("删除模块")
	public boolean deleteModel(String modelId);
	
	/**
	 * 修改模块
	 * @Title: updateModel
	 * @param @param Model
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("修改模块")
	public boolean updateModel(Model model);
	
	public List<TreeNode> selectShowTree(Map<Integer,ArrayList<Integer>> map);
	
	public EasyUIPage getSysUpdateModel(EasyUIPage page ,HashMap<String,Object> map);
}
