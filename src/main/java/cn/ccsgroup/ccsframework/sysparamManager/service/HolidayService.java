/**********************************************************************   
 * <p>文件名：HolidayService.java </p>
 * <p>文件描述：TODO(描述该文件做什么) 
 * @project_name：CCSBaseFrame
 * @author Alenfive  
 * @date 2014-1-26 下午12:20:21 
 * @history
 * @department：政务事业部
 * Copyright ChengDu Funi Cloud Code Technology Development CO.,LTD 2013 
 *                    All Rights Reserved.
*/
package cn.ccsgroup.ccsframework.sysparamManager.service;

import java.util.List;

import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.entity.Holiday;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.service.HolidayService.java]  
 * @ClassName:    [HolidayService]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [Alenfive]   
 * @CreateDate:   [2014-1-26 下午12:20:21]   
 * @UpdateUser:   [Alenfive(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-26 下午12:20:21，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
@TypeAnnotation("节假日管理")
public interface HolidayService {

	List<Holiday> getHolidayMonthList(String date);

	void saveUpateHoliday(List<Holiday> holidays);

	boolean isExistHoliday(Holiday holiday);

	List<Holiday> getHolidayForList(List<String> list);

}
