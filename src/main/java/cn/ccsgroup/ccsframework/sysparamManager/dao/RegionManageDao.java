/**
 * 
 */
package cn.ccsgroup.ccsframework.sysparamManager.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.RegionCode;

/**
 * @Package:      [cn.ccsgroup.ccsframework.workflow.modelMenu.dao.regionManageDao]  
 * @ClassName:    [regionManageDao]   
 * @Description:  [dao层负责向数据库提交更新、查询等数据请求，并向service层数据操作结果]   
 * @Author:       [tuliangyu]   
 * @CreateDate:   [2013-12-10]   
 * @UpdateUser:   [tuliangyu，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-10，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Repository("regionManageDao")
public class RegionManageDao extends BaseDaoImpl{


	/**
	 * @Title: insertRegionCode
	 * @Description: TODO(向数据库中插入区域数据对象)
	 * @param @param regionCode
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	
	
	/**
	 * @Title: getRegionCode
	 * @Description: TODO(获取某个id下的区域对象)
	 * @param @param map
	 * @param @return    设定文件
	 * @return RegionCode    返回类型
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public RegionCode getRegionCode(String id)
	{
		HashMap<String, Object> map=new  HashMap<String, Object>();
		map.put("id",Integer.parseInt(id));
		List<RegionCode> regionCodeList=(List<RegionCode>)this.getSqlMapClientTemplate().queryForList("RegionManage.getRegionCode", map);
		RegionCode regiCodeObj=new RegionCode();
		if(regionCodeList.size()==1){
			regiCodeObj=regionCodeList.get(0);			
		}
		return regiCodeObj;
	}
	
	
	/**
	 * @Title: getAllRegionCode
	 * @Description: TODO(获取所有有效的区域)
	 * @param @return    设定文件
	 * @return List<RegionCode>    返回类型
	 * @throws
	 */
	public List<TreeNode> getAllRegionCode()
	{
		return (List<TreeNode>)this.getSqlMapClientTemplate().queryForList("RegionManage.getAllRegionCode", null);
	}
	
	/**
	 * @Title: getAllSubRegionCode
	 * @Description: TODO(获取该节点下所有子节点)
	 * @param @param regionPId
	 * @param @return    设定文件
	 * @return List<RegionCode>    返回类型
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<TreeNode> getAllSubRegionCode(String regionPId)
	{
		HashMap<String, Object> map=new  HashMap<String, Object>();
		map.put("regionPId", regionPId);
		List<TreeNode> regionCodeList=(List<TreeNode>)this.getSqlMapClientTemplate().queryForList("RegionManage.getAllRegionCode", map);
		return regionCodeList;
	}
	
	/**
	 * @Title: getAllSubRegionCode
	 * @Description: TODO(获取该regiconde的所有节点)
	 * @param @param regionPId
	 * @param @return    设定文件
	 * @return List<RegionCode>    返回类型
	 * @throws
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<RegionCode> findRegionCode(String id,String regionCodeStr)
	{
		HashMap<String, Object> map=new  HashMap<String, Object>();
		map.put("id", id);
		map.put("regionCode", regionCodeStr);
		List<RegionCode> regionCodeList=(List<RegionCode>)this.getSqlMapClientTemplate().queryForList("RegionManage.findRegionCode", map);
		return regionCodeList;
	}
	/**
	 * @Title: updateRegionCode
	 * @Description: TODO(更新单个子节点区域)
	 * @param @param regionCode
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */

	public String updateRegionCode(RegionCode regionCode)
	{
		String _result = "success";
		int k = this.getSqlMapClientTemplate().update("RegionManage.updateRegionCode", regionCode);
		if(k > 0)
		{
			
		}else
		{
			_result = "failed";
		}
		return _result;
	}
	
	/**
	 * @Title: updateSubRegionCode
	 * @Description: TODO(更新某个节点下所有子节点区域编码)
	 * @param @param regionCode
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public String updateSubRegionCode(RegionCode regionCode)
	{
		String _result = "success";
		int k = this.getSqlMapClientTemplate().update("RegionManage.updateSubRegionCode", regionCode);
		if(k > 0)
		{
			
		}else
		{
			_result = "failed";
		}
		return _result;
	}
}
