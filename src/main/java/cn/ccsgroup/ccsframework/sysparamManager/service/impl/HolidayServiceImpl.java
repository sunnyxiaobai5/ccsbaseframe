/**********************************************************************   
 * <p>文件名：HolidayServiceImpl.java </p>
 * <p>文件描述：TODO(描述该文件做什么) 
 * @project_name：CCSBaseFrame
 * @author Alenfive  
 * @date 2014-1-26 下午12:22:17 
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2013 
 *                    All Rights Reserved.
*/
package cn.ccsgroup.ccsframework.sysparamManager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.entity.Holiday;
import cn.ccsgroup.ccsframework.sysparamManager.dao.HolidayDao;
import cn.ccsgroup.ccsframework.sysparamManager.dao.InformationDao;
import cn.ccsgroup.ccsframework.sysparamManager.service.HolidayService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.service.impl.HolidayServiceImpl.java]  
 * @ClassName:    [HolidayServiceImpl]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [Alenfive]   
 * @CreateDate:   [2014-1-26 下午12:22:17]   
 * @UpdateUser:   [Alenfive(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-26 下午12:22:17，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */

@Service("HolidayService")
@Transactional
public class HolidayServiceImpl implements HolidayService{
	@Resource(name="HolidayDao")
	private HolidayDao holidayDao;
	public void setHolidayDao(HolidayDao holidayDao) {
		this.holidayDao = holidayDao;
	}
	
	@Override
	public List<Holiday>  getHolidayMonthList(String date) {
		return holidayDao.queryHolidayMonthList(date);
	}
	
	@Override
	public void saveUpateHoliday(List<Holiday> holidays) {
		holidayDao.saveOrUpdate(holidays);
	}
	
	@Override
	public boolean isExistHoliday(Holiday holiday){
		return  holidayDao.isExistHoliday(holiday);
	}
	
	@Override
	public List<Holiday> getHolidayForList(List<String> list){
		return  holidayDao.queryHolidayList(list);
	}
}
