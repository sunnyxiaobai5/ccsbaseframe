package cn.ccsgroup.ccsframework.sysparamManager.service;

import java.util.List;

import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.entity.RegionCode;

@TypeAnnotation("区域管理")
public interface RegionManageService {

	/**
	 * @Title: insertRegionCode
	 * @Description: TODO(向数据库中插入区域数据对象)
	 * @param @param regionCode 区域对象
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("新增区域")
	public abstract boolean saveRegionCode(RegionCode regionCode);

	/**
	 * @Title: getRegionCode
	 * @Description: TODO(获取某个id下的区域对象)
	 * @param @param map
	 * @param @return    设定文件
	 * @return RegionCode    返回某个id下的区域对象
	 * @throws
	 */
	public abstract RegionCode getRegionCode(String id);

	/**
	 * @Title: getAllRegionCode
	 * @Description: TODO(获取所有有效的区域)
	 * @param @return    设定文件
	 * @return List<RegionCode>    返回所有有效区域的list对象
	 * @throws
	 */
	public abstract List<TreeNode> getAllRegionCode();

	/**
	 * @Title: getAllSubRegionCode
	 * @Description: TODO(获取该节点下所有子节点)
	 * @param @param regionPId
	 * @param @return    设定文件
	 * @return List<RegionCode>    返回节点下所有子节点区域的list对象
	 * @throws
	 */
	public abstract List<TreeNode> getAllSubRegionCode(String regionPId);

	/**
	 * @Title: updateRegionCode
	 * @Description: TODO(更新单个子节点区域)
	 * @param @param regionCode
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@MethodAnnotation("更新区域")
	public abstract String updateRegionCode(RegionCode regionCode);

	/**
	 * @Title: updateSubRegionCode
	 * @Description: TODO(更新某个节点下所有子节点区域编码)
	 * @param @param regionCode
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@MethodAnnotation("更新子区域")
	public abstract String updateSubRegionCode(RegionCode regionCode);

	@MethodAnnotation("删除区域")
	public int deleteRegionCode(RegionCode regionCode);
	
	@MethodAnnotation("根据区域ID删除区域")
	public int deleteRegionCodeById(String id);

	/**
	 * 
	 * @Title: findOpsTreeList
	 * @Description: TODO(根据登录人获取区域树)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> findAllRegionCode(String regionCodeStr);
	
	/**
	 *  * @Title: findOpsTreeList
	 * @Description: TODO(根据regionCodeStr获取区域list)
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 * */
	public List<RegionCode> findRegionCode(String id,String regionCodeStr);
}