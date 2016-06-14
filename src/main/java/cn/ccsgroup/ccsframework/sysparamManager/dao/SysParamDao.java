package cn.ccsgroup.ccsframework.sysparamManager.dao;



import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.entity.LogiSystem;
import cn.ccsgroup.ccsframework.entity.Model;
import cn.ccsgroup.ccsframework.entity.SysParam;
import cn.ccsgroup.ccsframework.entity.TypeList;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.dao.TypeListDao.java]  
 * @ClassName:    [TypeListDao]   
 * @Description:  [系统参数控制DAO]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2014-1-8 下午01:55:57]   
 * @UpdateUser:   [安宗源(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-8 下午01:55:57，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [新增]  
 * @Version:      [v1.0] 
 */
@Repository("SysParamDao")
public class SysParamDao extends BaseDaoImpl {
	
	public List<TypeList> querySysParamList(EasyUIPage page){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("begin", page.getBegin());
		map.put("end",page.getEnd());
		return this.getSqlMapClientTemplate().queryForList("SysParam.querySysParam", map);
	}
	
	public Integer querySysParamListCounts(){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("SysParam.querySysParamCounts");
	}
	
	public SysParam querySysParamById(Integer id){
		return (SysParam)this.getSqlMapClientTemplate().queryForObject("SysParam.querySysParamById",id);
	}
	
	public List<LogiSystem> queryLoginSystemList(){
		return (List<LogiSystem>)this.getSqlMapClientTemplate().queryForList("SysParam.queryLoginSystemList");
	}
	
	public boolean insertSysParam(SysParam param)
	{
		SysParam sysParam = (SysParam)this.getSqlMapClientTemplate().insert("SysParam.insertSysParam", param);
		return sysParam==null;
	}
	
	public boolean updateSysParam(SysParam param){
		int i = this.getSqlMapClientTemplate().update("SysParam.updateSysParam", param);
		return i>0;
	}
	
	public boolean deleteSysParam(Integer id){
		int i=this.getSqlMapClientTemplate().update("SysParam.deleteSysParam", id);
		return i>0;
	}
}
