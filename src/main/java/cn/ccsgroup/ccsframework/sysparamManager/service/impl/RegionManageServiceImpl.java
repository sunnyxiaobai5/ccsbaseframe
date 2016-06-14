
package cn.ccsgroup.ccsframework.sysparamManager.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.RegionCode;
import cn.ccsgroup.ccsframework.exception.BussinessException;
import cn.ccsgroup.ccsframework.sysparamManager.dao.RegionManageDao;
import cn.ccsgroup.ccsframework.sysparamManager.service.RegionManageService;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.samples.business.service.impl.UserServiceImpl.java]  
 * @ClassName:    [UserServiceImpl]   
 * @Description:  [regionManage的services层实现类,实现接口RegionManageService]   
 * @Author:       [tuliangyu]   
 * @CreateDate:   [2013-11-15 上午10:58:09]   
 * @UpdateUser:   [tuliangyu(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-11-15 上午10:58:09，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
@Transactional
@Service("RegionManageService")
public class RegionManageServiceImpl implements RegionManageService {
	
	
	/**
	 * @Fields funcDao : TODO(spring托管依赖注入dao-regionManageDao)
	 */
	@Resource(name="regionManageDao")
	private RegionManageDao regionManageDao;

	/**
	 * @Title: insertRegionCode
	 * @Description: TODO(向数据库中插入区域数据对象)
	 * @param @param regionCode 区域对象
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean saveRegionCode(RegionCode regionCode){
		boolean flag=false;
		try {
			regionManageDao.insert("RegionManage.insertRegionCode", regionCode);
			flag=true;
		} catch (Exception e) {
			// TODO: handle exception
			flag=false;
		}
		 return flag;
	}
	
	
	/**
	 * @Title: getRegionCode
	 * @Description: TODO(获取某个id下的区域对象)
	 * @param @param map
	 * @param @return    设定文件
	 * @return RegionCode    返回某个id下的区域对象
	 * @throws
	 */
	public RegionCode getRegionCode(String id)
	{
		return regionManageDao.getRegionCode(id);
	}
	
	
	/**
	 * @Title: getAllRegionCode
	 * @Description: TODO(获取所有有效的区域)
	 * @param @return    设定文件
	 * @return List<RegionCode>    返回所有有效区域的list对象
	 * @throws
	 */
	public List<TreeNode> getAllRegionCode()
	{
		return regionManageDao.getAllRegionCode();
	}
	
	/**
	 * @Title: getAllSubRegionCode
	 * @Description: TODO(获取该节点下所有子节点)
	 * @param @param regionPId
	 * @param @return    设定文件
	 * @return List<RegionCode>    返回节点下所有子节点区域的list对象
	 * @throws
	 */
	public List<TreeNode> getAllSubRegionCode(String regionPId)
	{
		return regionManageDao.getAllSubRegionCode(regionPId);
	}
	
	/**
	 * @Title: updateRegionCode
	 * @Description: TODO(更新单个子节点区域)
	 * @param @param regionCode
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	public String updateRegionCode(RegionCode regionCode)
	{
		return regionManageDao.updateRegionCode(regionCode);
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
		return regionManageDao.updateSubRegionCode(regionCode);
	}
	
	@Override
	public int deleteRegionCode(RegionCode regionCode) {
		// TODO Auto-generated method stub
		int i = regionManageDao.update("RegionManage.updateRegionCode", regionCode);
		return i;
	}
	
	
	public int deleteRegionCodeById(String id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("status",AppConst.STATUS_DISABLE);
		map.put("id", id);
		int i = regionManageDao.update("RegionManage.deleteRegionCodeById", map);
		return i;
	}
	/**
	 * @Title: updateSubRegionCode
	 * @Description: TODO(获取区域编码)
	 * @param @param regionCode
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public List<TreeNode> findAllRegionCode(String regionCodeStr){
		List<TreeNode> regionCodeList = regionManageDao.getAllSubRegionCode(regionCodeStr);//getAllRegionCode();
		if(regionCodeList.size()<1){
			RegionCode regionCode=new RegionCode();
			regionCode.setRegionCode("");
			regionCode.setRegionName("ROOT");
			regionCode.setStatus(1);
			regionCode.setRegionPId("-1");
			saveRegionCode(regionCode);//插入最顶层的数据
			regionCodeList = getAllRegionCode();
		}
		return regionCodeList;
	}
	
	
	/**
	 * @Title: updateSubRegionCode
	 * @Description: TODO(获取区域编码)
	 * @param @param regionCode
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	 public List<RegionCode> findRegionCode(String id,String regionCodeStr){
		List<RegionCode> regionCodeList = regionManageDao.findRegionCode(id,regionCodeStr);
		return regionCodeList;
	}
}
