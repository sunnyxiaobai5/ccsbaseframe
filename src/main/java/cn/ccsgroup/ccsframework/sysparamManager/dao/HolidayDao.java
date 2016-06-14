package cn.ccsgroup.ccsframework.sysparamManager.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.entity.Holiday;
import cn.ccsgroup.ccsframework.entity.Information;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [ccn.ccsgroup.ccsframework.sysparamManager.dao.HolidayDao.java]  
 * @ClassName:    [HolidayDao]   
 * @Description:  [节假日dao层]   
 * @Author:       [alenfive]   
 * @CreateDate:   [2014-1-26 下午04:32:48]   
 * @UpdateUser:   [alenfive(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-26 下午04:32:48，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@Repository("HolidayDao")
public class HolidayDao  extends BaseDaoImpl{

	/**
	 * @Title: main
	 * @Description: TODO(获取参数该月节假日列表)
	 * @param @param args    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public List<Holiday> queryHolidayMonthList(String date){
		return this.getSqlMapClientTemplate().queryForList("Holiday.getHolidayMonthSearch",date);
	}
	
	/**
	 * 
	 * @Title: addUpdate
	 * @Description: TODO(保存或者添加节假日)
	 * @param @param holidays    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public void saveOrUpdate(List<Holiday> holidays){
		for(Holiday h:holidays){
			if(h.getStatus()==3){
				this.getSqlMapClientTemplate().update("Holiday.updateHoliday",h);
			}else{
				this.getSqlMapClientTemplate().insert("Holiday.addHoliday",h);
			}
		}
	}
	
	/**
	 * 
	 * @Title: isExistHoliday
	 * @Description: TODO(该节假日是否存在)
	 * @param @param holiday
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean isExistHoliday(Holiday holiday){
		return ((Integer) this.getSqlMapClientTemplate().queryForObject("Holiday.holidayCount",holiday.getDay()))>0?true:false;
	}
	
	/**
	 * 
	 * @Title: queryHolidayMonthList
	 * @Description: TODO(根据List<String>查找存在的节假日)
	 * @param @param list
	 * @param @return    设定文件
	 * @return List<Holiday>    返回类型
	 * @throws
	 */
	public List<Holiday> queryHolidayList(List<String> list){
		return this.getSqlMapClientTemplate().queryForList("Holiday.getHolidayForList",list);
	}

}
