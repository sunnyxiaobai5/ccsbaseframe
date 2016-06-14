package cn.ccsgroup.ccsframework.sysparamManager.dao;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.entity.SysLogs;
import cn.ccsgroup.ccsframework.entity.TypeList;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.dao.TypeListDao.java]  
 * @ClassName:    [TypeListDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2014-1-8 下午01:55:57]   
 * @UpdateUser:   [安宗源(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-8 下午01:55:57，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [新增]  
 * @Version:      [v1.0] 
 */
@Repository("SysLogsDao")
public class SysLogsDao extends BaseDaoImpl {
	
	public List<TypeList> queryLogsList (EasyUIPage page){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("begin", page.getBegin());
		map.put("end",page.getEnd());
		return this.getSqlMapClientTemplate().queryForList("SysLogs.queryLogs", map);
	}
	public Integer queryLogsListCounts(){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("SysLogs.queryLogsCounts");
	}
	public SysLogs queryLogsById(Integer id){
		return (SysLogs)this.getSqlMapClientTemplate().queryForObject("SysLogs.queryLogsById",id);
	}
}
