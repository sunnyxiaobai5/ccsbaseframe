package cn.ccsgroup.ccsframework.sysparamManager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import cn.ccsgroup.ccsframework.annotation.PrivilegeAnnotation;
import cn.ccsgroup.ccsframework.base.controller.BaseController;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Holiday;
import cn.ccsgroup.ccsframework.sysparamManager.service.HolidayService;
import cn.ccsgroup.ccsframework.sysparamManager.service.impl.HolidayServiceImpl;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.sysparamManager.controller.HolidayController.java]  
 * @ClassName:    [InformationController]   
 * @Description:  [节假日Controller]   
 * @Author:       [alen]   
 * @CreateDate:   [2014-1-23 下午 22:27:23]   
 * @UpdateUser:   [lenovo(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-1-23 下午 22:27:23，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */

@Controller
@RequestMapping("/HolidayController")
public class HolidayController extends BaseController{
	
	@Resource
	private HolidayService holidayService;
	public void setHolidayService(HolidayService holidayService) {
		this.holidayService = holidayService;
	}

	@RequestMapping(value="/viewHoliday")
	public String viewHoliday(){
		return "holidayManager/holiday";
	}
	
	/**
	 * @Title: listHolidayByMonth
	 * @Description: TODO(根据月份，返回整月数据，如果没有则是当前日期)
	 * @param @param date
	 * @param @return    设定文件
	 * @return List<Holiday>    返回类型
	 * @throws
	 */
	@RequestMapping(value="/listHolidayByMonth")
	public @ResponseBody List<Holiday> listHolidayByMonth(@RequestParam(value="date",required=false)String date){
		if(date==null){
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.format(d);
		}
		return holidayService.getHolidayMonthList(date);
	}
	
	@PrivilegeAnnotation()//进行拦截
	@RequestMapping(value="/saveUpdateHoliday")
	public @ResponseBody Map<String,String> saveUpdateHoliday(@RequestParam(value="holiday",required=false)String holiday){
		Map<String,String> map = new HashMap<String,String>();
		List<Holiday> holidays = new Gson().fromJson(holiday,new TypeToken<List<Holiday>>(){}.getType());
		List<Holiday> fullHoliday = new ArrayList<Holiday>();
		List<Holiday> exist = null;
		List<String> days = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		for(Holiday h:holidays){
			try{
				days.add(sdf1.format(sdf1.parse(h.getDay())));
				h.setDay(sdf.format(sdf.parse(h.getDay())));
			}catch(Exception e){continue;}
		}
		exist = holidayService.getHolidayForList(days);			//找到存在的日期集合
		try{
			for(int j=0;j<holidays.size();j++){
				//System.out.println(h.getDay()+"="+h.getCause()+"="+h.getIsholiday());
				Holiday h = holidays.get(j);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sdf.parse(h.getDay()));
				if(fullHoliday.contains(h)){		//判定此对象是否存在
					continue;
				}
				//取得当月最大值
				int maxD = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				int d = calendar.get(Calendar.DAY_OF_MONTH);
				int m = calendar.get(Calendar.MONTH);
				int y = calendar.get(Calendar.YEAR);
				for(int k=1;k<=maxD;k++){
					Holiday hk = new Holiday();
					calendar.set(y,m,k);
					hk.setDay(sdf.format(calendar.getTime()));
					int l = holidays.indexOf(hk);
					if(l != -1){
						hk = holidays.get(l);
					}
					if(h.getStatus()==2 && !exist.contains(h)){		//操作状态码为2时且该数据未进入数据库作默认处理，如默认此月的星期天与星期六为礼拜天
						Integer week = calendar.get(Calendar.DAY_OF_WEEK);
						if(week==1)week = 7;
						else{
							week -= 1;
						}
						if(week==7 || week ==6){
							hk.setCause("礼拜天");
							hk.setIsholiday("是");
						}
						fullHoliday.add(hk);
						
					}else if(hk.getStatus()==0){
						if(exist.contains(hk)){
							hk.setCause(null);
							hk.setStatus(3);
							fullHoliday.add(hk);
						}
					}else{
						int index = holidays.indexOf(hk);
						if(index != -1){		//当前列表中有此对象
							hk = holidays.get(index);
							if(exist.contains(hk)){		//存在就标记为更新
								hk.setStatus(3);
							}
							hk.setIsholiday("是");
							fullHoliday.add(hk);
						}else{
							if(!exist.contains(hk)){		//数据库中如果不存在
								fullHoliday.add(hk);
							}
						}
					}
				}
			}
			holidayService.saveUpateHoliday(fullHoliday);
			map.put(AppConst.STATUS,AppConst.SUCCESS);
		}catch(Exception e){
			map.put(AppConst.STATUS,AppConst.FAIL);
			map.put(AppConst.MESSAGES,e.toString());
			e.printStackTrace();
		}
		
		for(Holiday h:fullHoliday){
			System.out.println(h.getDay()+"-"+h.getCause()+"-"+h.getIsholiday()+"-"+h.getStatus());
		}
		return map;
	}
	
	
	//Test
	public static void main(String args[]) throws ParseException{
		
	/*	HolidayController hc = new HolidayController();
		hc.setHolidayService(new HolidayServiceImpl()); 
		hc.saveUpdateHoliday("[{'cause':'我是好人','day':'2014-1-1','isholiday':'no'},{'cause':'xxx','day':'2014-1-4','isholiday':'是'},{'day':'2014-2-3'}]");*/
		Calendar calendar = Calendar.getInstance();
		String d = "2014-1-25";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		calendar.setTime(sdf.parse(d));
		Integer week = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println(week+"##");
		
	}
}
