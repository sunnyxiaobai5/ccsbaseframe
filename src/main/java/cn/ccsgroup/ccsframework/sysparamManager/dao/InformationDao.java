package cn.ccsgroup.ccsframework.sysparamManager.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Annex;
import cn.ccsgroup.ccsframework.entity.Information;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.dao.InformationDao.java]  
 * @ClassName:    [InformationDao]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [lenovo]   
 * @CreateDate:   [2014-1-6 下午04:32:48]   
 * @UpdateUser:   [lenovo(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-6 下午04:32:48，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@Repository("InformationDao")
public class InformationDao extends BaseDaoImpl{

	public List<Information> queryInformations(EasyUIPage page){
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		return this.getSqlMapClientTemplate().queryForList("Information.queryInformations",map);
	} 
	public Integer queryInformationsCounts(){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("Information.queryInformationsCounts");
	}
	public Integer addInformations(Information information){
		information.setStatus(AppConst.STATUS_ENABLE);
		return (Integer)this.getSqlMapClientTemplate().insert("Information.insertInformation",information);
	}
	public boolean deleteInformation(Integer id){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", AppConst.STATUS_DISABLE);
		int c = (Integer)this.getSqlMapClientTemplate().delete("Information.deleteInformation",map);
		if(c > 0){
			this.getSqlMapClientTemplate().delete("Information.deleteAnnex",map);
			this.getSqlMapClientTemplate().delete("Information.deleteInformationmapping",id);
		}
		return  c > 0 ? true : false;
	}
	public Integer addInformationmapping(Integer infoid,String[] appids,String creater){
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(int i = 0 ; i < appids.length ; i++){
			map.clear();
			map.put("sysid", appids[i]);
			map.put("infoid", infoid);
			map.put("createruser",creater);
			int id = (Integer)this.getSqlMapClientTemplate().insert("Information.insertInformationmapping",map);
		}
		 return 0;
	}
	public void deleteInformationmapping(Integer infoid){
		this.getSqlMapClientTemplate().delete("Information.deleteInformationmapping",infoid);
	}
	public List getSendAppList(Integer infoid){
		List appList = this.getSqlMapClientTemplate().queryForList("Information.querySendAppList",infoid);
		return appList;
	}
	public void addAnnex(List<Annex> annexList){
		for(int i = 0 ; i < annexList.size() ; i++){
			Annex annex = annexList.get(i);
			annex.setStatus(AppConst.STATUS_ENABLE);
			this.getSqlMapClientTemplate().insert("Information.addAnnex",annex);
		}
	}
	public List getAppList(){
		List appList = this.getSqlMapClientTemplate().queryForList("Information.queryAppList");
		return appList;
	}
	public Information getInformationById(Integer id){
		return (Information)this.getSqlMapClientTemplate().queryForObject("Information.queryInformationById",id);
	}
	public Annex getAnnexById(Integer id){
		return (Annex)this.getSqlMapClientTemplate().queryForObject("Information.queryAnnexById",id);
	}
	public List<Annex> getAnnexByInfoid(Integer id){
		return this.getSqlMapClientTemplate().queryForList("Information.queryAnnexByInfoid",id);
	}
	public boolean updateInformation(Information information){
		int c = (Integer)this.getSqlMapClientTemplate().update("Information.updateInformation",information);
		return c > 0 ? true : false;
	}
	public boolean deleteAnnex(Integer id){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", AppConst.STATUS_DISABLE);
		int k = (Integer)this.getSqlMapClientTemplate().delete("Information.deleteAnnexById",map);
		return k > 0 ? true : false;
	}
	public List<Information> getInformationListBySentSys(Integer[] sysid){
		return this.getSqlMapClientTemplate().queryForList("Information.queryNotice",sysid);
	}
}
