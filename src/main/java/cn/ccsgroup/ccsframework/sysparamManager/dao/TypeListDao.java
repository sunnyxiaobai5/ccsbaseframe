package cn.ccsgroup.ccsframework.sysparamManager.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.entity.TypeList;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.dao.TypeListDao.java]  
 * @ClassName:    [TypeListDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [kuan]   
 * @CreateDate:   [2014-1-8 下午01:55:57]   
 * @UpdateUser:   [kuan(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-8 下午01:55:57，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@Repository("TypeListDao")
public class TypeListDao extends BaseDaoImpl {
	/**
	 * @Title: queryTypelist
	 * @Description: 获取主页面列表数据
	 * @param @param page
	 * @param @return    设定文件
	 * @return List<TypeList>    返回类型
	 * @throws
	 */
	public List<TypeList> queryTypelist (EasyUIPage page){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("begin", page.getBegin());
		map.put("end",page.getEnd());
		return this.getSqlMapClientTemplate().queryForList("TypeList.queryTypeList", map);
	}
	
	/**
	 * @Title: queryTypeListCounts
	 * @Description: 获取主页面列表条数
	 * @param @return    设定文件
	 * @return Integer    返回类型
	 * @throws
	 */
	public Integer queryTypeListCounts(){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("TypeList.queryTypeListCounts");
	}
}
