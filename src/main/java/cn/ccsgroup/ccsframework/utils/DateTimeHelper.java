package cn.ccsgroup.ccsframework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.utils.DateTimeHelper.java]  
 * @ClassName:    [DateTimeHelper]   
 * @Description:  [对时间日期的操作]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-2 下午5:18:36]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-2 下午5:18:36，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class DateTimeHelper {
	public DateTimeHelper() {
	}

	public static Date addSeconds(Date dtIn, int seconds) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dtIn);
		gc.add(GregorianCalendar.SECOND, seconds);
		return gc.getTime();
	}

	/** 在dtIn后加iDays天 */
	public static Date addDays(Date dtIn, int iDays) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dtIn);
		gc.add(GregorianCalendar.DAY_OF_YEAR, iDays);
		return gc.getTime();
	}

	/** 取得大于当前日期时间的从dtIn开始的加iDays天的日期时间 */
	public static Date getDateForDateByNowTime(Date dtIn, int iDays) {
		Date now = new Date();
		Date nextDate = dtIn;
		while (nextDate.compareTo(now) < 0) {
			nextDate = addDays(nextDate, iDays);
		}
		return nextDate;
	}

	/** 增加星期数 */
	public static Date addWeeks(Date dtIn, int iWeeks) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dtIn);
		gc.add(GregorianCalendar.WEEK_OF_YEAR, iWeeks);
		return gc.getTime();
	}

	/** 加几个月 */
	public static Date addMonths(Date dtIn, int iMonths) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dtIn);
		gc.add(GregorianCalendar.MONTH, iMonths);
		return gc.getTime();
	}

	/** 取得dtIn后iMonths月iDays日的日期 */
	public static Date addMonths_setDays(Date dtIn, int iMonths, int iDays) {
		/** 月的最大日期值 */
		int maxDate = 0;
		Date dtOut = addMonths(dtIn, iMonths);
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dtOut);
		maxDate = gc.getActualMaximum(GregorianCalendar.DATE);
		if (iDays > maxDate) {
			iDays = maxDate;
		}
		gc.set(GregorianCalendar.DATE, iDays);
		return gc.getTime();
	}

	/** 取得大于当前时间的从dtIn开始后iMonths月iDays日的日期 */
	public static Date getDateForMonthByNowTime(Date dtIn, int iMonths,
			int iDays) {
		Date now = new Date();
		Date nextDate = dtIn;
		while (nextDate.compareTo(now) < 0) {
			nextDate = addMonths_setDays(nextDate, iMonths, iDays);
		}
		return nextDate;
	}

	/** 设置时间的星期数为星期几 星期天（1）－星期六（7） */
	public static Date computWeekDate(Date dtIn, int iWeek) {
		Date dtOut = null;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dtIn);
		gc.set(GregorianCalendar.DAY_OF_WEEK, iWeek);
		dtOut = gc.getTime();
		return dtOut;
	}

	/** 是否是一年的同一周 */
	public static boolean isSameWeek(Date dtSrc, Date dtDesc) {
		GregorianCalendar gc = new GregorianCalendar();
		int iSrcWeek;
		int iDescWeek;

		gc.setTime(dtSrc);
		iSrcWeek = gc.get(GregorianCalendar.WEEK_OF_YEAR);
		gc.setTime(dtDesc);
		iDescWeek = gc.get(GregorianCalendar.WEEK_OF_YEAR);
		return iSrcWeek == iDescWeek;
	}

	/** 是否是同一个月 */
	public static boolean isSameMonth(Date dtSrc, Date dtDesc) {
		GregorianCalendar gc = new GregorianCalendar();
		int iSrcMonth;
		int iDescMonth;
		gc.setTime(dtSrc);
		iSrcMonth = gc.get(GregorianCalendar.MONTH);
		gc.setTime(dtDesc);
		iDescMonth = gc.get(GregorianCalendar.MONTH);
		return iSrcMonth == iDescMonth;
	}

	/** 取前几天 */
	public static Date beforeDate(Date dtSrc, int day) {
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(dtSrc);
		gc.set(GregorianCalendar.DAY_OF_YEAR, gc
				.get(GregorianCalendar.DAY_OF_YEAR)
				- day);

		return gc.getTime();
	}

	/** 取后几天 */
	public static Date afterDate(Date dtSrc, int day) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dtSrc);
		gc.set(GregorianCalendar.DAY_OF_YEAR, gc
				.get(GregorianCalendar.DAY_OF_YEAR)
				+ day);
		return gc.getTime();
	}

	/** 获取dtIn是所属周的周几 星期天（1）－星期六（7） */
	public static int getDateIndexOfWeek(Date dtIn) {
		int index = 0;
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(dtIn);
		index = gc.get(GregorianCalendar.DAY_OF_WEEK);
		return index;
	}

	/** 返回两个时间之间相差的天数 */
	public static int dayMinusday(Date date1, Date date2) {
		int returnValue = 0;
		long min1 = date1.getTime();
		long min2 = date2.getTime();
		returnValue = (int) ((min1 - min2) / (1000 * 60 * 60 * 24));
		returnValue = Math.abs(returnValue);
		return returnValue;
	}

	/**
	 * 比较两个时间的大小（只比较时间，不比较日期）
	 * 
	 * "大于0前面的大,后面的小 小于0后面的大，前面的小 ==0相等"
	 */
	public static int compareTime(Date time1, Date time2) {
		int returnValue = 0;
		GregorianCalendar gc1 = new GregorianCalendar();
		GregorianCalendar gc2 = new GregorianCalendar();
		gc1.setTime(time1);
		gc2.setTime(time2);
		GregorianCalendar gc3 = new GregorianCalendar();
		GregorianCalendar gc4 = new GregorianCalendar();

		gc3.set(GregorianCalendar.HOUR, gc1.get(GregorianCalendar.HOUR));
		gc3.set(GregorianCalendar.MINUTE, gc1.get(GregorianCalendar.MINUTE));
		gc3.set(GregorianCalendar.SECOND, gc1.get(GregorianCalendar.SECOND));
		gc3.set(GregorianCalendar.AM_PM, gc1.get(GregorianCalendar.AM_PM));
		gc4.set(GregorianCalendar.HOUR, gc2.get(GregorianCalendar.HOUR));
		gc4.set(GregorianCalendar.MINUTE, gc2.get(GregorianCalendar.MINUTE));
		gc4.set(GregorianCalendar.SECOND, gc2.get(GregorianCalendar.SECOND));
		gc4.set(GregorianCalendar.AM_PM, gc2.get(GregorianCalendar.AM_PM));
		returnValue = gc3.compareTo(gc4);
		return returnValue;
	}

	/**
	 * 
	 * <li>功能描述：比较日期 <li>创建日期：Aug 2, 2009 compareDate
	 * 
	 * @return int
	 * @author Lee
	 */
	public static int compareDate(Date time1, Date time2) {
		GregorianCalendar gc1 = new GregorianCalendar();
		GregorianCalendar gc2 = new GregorianCalendar();
		gc1.setTime(time1);
		gc2.setTime(time2);
		if (gc1.get(Calendar.YEAR) == gc2.get(Calendar.YEAR)) {
			if (gc1.get(Calendar.MONTH) == gc2.get(Calendar.MONTH)) {
				return gc1.get(Calendar.DAY_OF_MONTH)
						- gc2.get(Calendar.DAY_OF_MONTH);
			}
			return gc1.get(Calendar.MONTH) - gc2.get(Calendar.MONTH);
		}
		return gc1.get(Calendar.YEAR) - gc2.get(Calendar.YEAR);
	}

	/**
	 * 用日期时间和新日数组成一个新日期时间
	 * 
	 * 当新日期小于原日期，则加一个月
	 */
	public static Date setDate(Date date, int day) {
		/** 月的最大日期值 */
		int maxDate = 0;
		GregorianCalendar oldgc = new GregorianCalendar();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		oldgc.setTime(date);
		maxDate = gc.getActualMaximum(GregorianCalendar.DATE);
		if (day > maxDate) {
			day = maxDate;
		}
		gc.set(GregorianCalendar.DATE, day);
		if (gc.compareTo(oldgc) < 0) {
			gc.add(GregorianCalendar.MONTH, 1);
		}
		return gc.getTime();

	}

	/** 用日期和时间组成一个新的日期时间 */
	public static Date setDateTime(Date date, Date time) {
		GregorianCalendar gc1 = new GregorianCalendar();
		GregorianCalendar gc2 = new GregorianCalendar();
		gc1.setTime(time);
		gc2.setTime(date);
		gc2.set(GregorianCalendar.HOUR, gc1.get(GregorianCalendar.HOUR));
		gc2.set(GregorianCalendar.MINUTE, gc1.get(GregorianCalendar.MINUTE));
		gc2.set(GregorianCalendar.SECOND, gc1.get(GregorianCalendar.SECOND));
		gc2.set(GregorianCalendar.AM_PM, gc1.get(GregorianCalendar.AM_PM));
		return gc2.getTime();
	}

	/**
	 * 取得一个从指定日期时间开始（startDateTime）
	 * 
	 * 属于星期列表的日期
	 */
	public static Date getDateTimeByStandardDateTimeForWeek(Date startDateTime,
			List<Integer> weeks) {
		Date returnDateTime = startDateTime;

		while (weekOfWeeks(getDateIndexOfWeek(returnDateTime), weeks) == false) {
			returnDateTime = addDays(returnDateTime, 1);
		}
		return returnDateTime;
	}

	/** 判断week是属于weeks */
	private static boolean weekOfWeeks(int week, List<Integer> weeks) {
		boolean returnValues = false;
		for (int i = 0; i < weeks.size(); i++) {
			if (week == ((Integer) weeks.get(i)).intValue()) {
				returnValues = true;
			}
		}
		return returnValues;
	}

	/**
	 * 取得一个从指定日期时间开始（startDateTime）
	 * 
	 * 大于现在且刚刚大于
	 * 
	 * 属于星期列表而且按照每addweek周递增的一个的日期
	 */
	public static Date getDateTimeByStandardDateTimeForWeek(Date startDateTime,
			List<Integer> weeks, int addweek) {
		/** 现在的时间 */
		Date now = new Date();
		Date returnDateTime = startDateTime;
		/** 获得第一次执行的日期时间 */

		while (weekOfWeeks(getDateIndexOfWeek(returnDateTime), weeks) == false
				|| returnDateTime.compareTo(now) < 0) {
			// System.out.println(getDateIndexOfWeek(returnDateTime));
			returnDateTime = addDays(returnDateTime, 1);
			if (getDateIndexOfWeek(returnDateTime) == 2) {// 如果下一天是星期一
				returnDateTime = addWeeks(returnDateTime, addweek - 1);// 加X个星期
			}
		}
		return returnDateTime;
	}

	/** 把日期时间的时间设置为0 */
	public static Date setTimeToZero(Date datetime) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(datetime);
		gc.set(GregorianCalendar.HOUR, 0);
		gc.set(GregorianCalendar.MINUTE, 0);
		gc.set(GregorianCalendar.SECOND, 0);
		gc.set(GregorianCalendar.AM_PM, GregorianCalendar.AM);
		return gc.getTime();
	}

	/** 把日期时间的日期设置为0 */
	public static Date setDateToZero(Date datetime) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(datetime);
		gc.set(GregorianCalendar.YEAR, 1970);
		gc.set(GregorianCalendar.MONTH, 0);
		gc.set(GregorianCalendar.DATE, 1);
		return gc.getTime();
	}

	/** 取得月最大天数 */
	public static int getMaxdays(Date date) {
		Calendar time = Calendar.getInstance();
		time.setTime(date);
		time.clear();
		time.set(Calendar.YEAR, date.getYear());
		time.set(Calendar.MONTH, date.getMonth());// 注意,Calendar对象默认一月为0
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
		return day;
	}

	/**
	 * 设置为星期几
	 * 
	 * @param date
	 * @param week
	 * @return
	 */
	public static Date setWeek(Date date, int week) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		switch (week) {
		case 1:
			calendar.set(GregorianCalendar.DAY_OF_WEEK,
					GregorianCalendar.MONDAY);
		case 2:
			calendar.set(GregorianCalendar.DAY_OF_WEEK,
					GregorianCalendar.TUESDAY);
		case 3:
			calendar.set(GregorianCalendar.DAY_OF_WEEK,
					GregorianCalendar.WEDNESDAY);
		case 4:
			calendar.set(GregorianCalendar.DAY_OF_WEEK,
					GregorianCalendar.THURSDAY);
		case 5:
			calendar.set(GregorianCalendar.DAY_OF_WEEK,
					GregorianCalendar.FRIDAY);
		case 6:
			calendar.set(GregorianCalendar.DAY_OF_WEEK,
					GregorianCalendar.SATURDAY);
		case 7:
			calendar.set(GregorianCalendar.DAY_OF_WEEK,
					GregorianCalendar.SUNDAY);
		}
		return calendar.getTime();
	}

	/**
	 * @see 取得指定时间的给定格式()
	 * @return String
	 * @throws ParseException
	 */
	public static String SetDateFormat(String myDate, String strFormat)
			throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String sDate = sdf.format(sdf.parse(myDate));

		return sDate;
	}

	/*****************************************
	 * @功能 判断某年是否为闰年
	 * @return boolean
	 * @throws ParseException
	 ****************************************/
	public static boolean isLeapYear(int yearNum) {
		boolean isLeep = false;
		/** 判断是否为闰年，赋值给一标识符flag */
		if ((yearNum % 4 == 0) && (yearNum % 100 != 0)) {
			isLeep = true;
		} else if (yearNum % 400 == 0) {
			isLeep = true;
		} else {
			isLeep = false;
		}
		return isLeep;
	}

	/*****************************************
	 * @功能 计算当前日期某年的第几周
	 * @return interger
	 * @throws ParseException
	 ****************************************/
	public static int getWeekNumOfYear() {
		Calendar calendar = Calendar.getInstance();
		int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
		return iWeekNum;
	}

	/*****************************************
	 * @功能 计算指定日期某年的第几周
	 * @return interger
	 * @throws ParseException
	 ****************************************/
	public static int getWeekNumOfYearDay(String strDate) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = format.parse(strDate);
		calendar.setTime(curDate);
		int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
		return iWeekNum;
	}

	/*****************************************
	 * @功能 计算某年某周的开始日期
	 * @return interger
	 * @throws ParseException
	 ****************************************/
	public static String getYearWeekFirstDay(int yearNum, int weekNum)
			throws ParseException {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return SetDateFormat(tempDate, "yyyy-MM-dd");

	}

	/*****************************************
	 * @功能 计算某年某周的结束日期
	 * @return interger
	 * @throws ParseException
	 ****************************************/
	public static String getYearWeekEndDay(int yearNum, int weekNum)
			throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum + 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(cal.get(Calendar.MONTH) + 1);
		String tempDay = Integer.toString(cal.get(Calendar.DATE));
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return SetDateFormat(tempDate, "yyyy-MM-dd");
	}

	/*****************************************
	 * @功能 计算某年某月的开始日期
	 * @return interger
	 * @throws ParseException
	 ****************************************/
	public static String getYearMonthFirstDay(int yearNum, int monthNum)
			throws ParseException {

		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "1";
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return SetDateFormat(tempDate, "yyyy-MM-dd");

	}

	/*****************************************
	 * @功能 计算某年某月的结束日期
	 * @return interger
	 * @throws ParseException
	 ****************************************/
	public static String getYearMonthEndDay(int yearNum, int monthNum)
			throws ParseException {

		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "31";
		if (tempMonth.equals("1") || tempMonth.equals("3")
				|| tempMonth.equals("5") || tempMonth.equals("7")
				|| tempMonth.equals("8") || tempMonth.equals("10")
				|| tempMonth.equals("12")) {
			tempDay = "31";
		}
		if (tempMonth.equals("4") || tempMonth.equals("6")
				|| tempMonth.equals("9") || tempMonth.equals("11")) {
			tempDay = "30";
		}
		if (tempMonth.equals("2")) {
			if (isLeapYear(yearNum)) {
				tempDay = "29";
			} else {
				tempDay = "28";
			}
		}
		// System.out.println("tempDay:" + tempDay);
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return SetDateFormat(tempDate, "yyyy-MM-dd");

	}


	//字符串转换成日期
	public static java.util.Date stringToDate(String dateString,
			String formatString) {
		java.util.Date returnValue = null;
		try {
			if (dateString != null && !"".equals(dateString)) {
				SimpleDateFormat formatter = new SimpleDateFormat(formatString);
				returnValue = formatter.parse(dateString);
			}
		} catch (Exception ex) {
			System.out.println("字符串转换成日期出错："+ex.getMessage());
		}
		return (returnValue);
	}
	//日期转换成字符串
	public static String DateToString(Date date,
			String formatString) {
		String dateStr = null;
		try {
			if (date != null ) {
				SimpleDateFormat formatter = new SimpleDateFormat(formatString);
				dateStr = formatter.format(date);
			}
		} catch (Exception ex) {
			System.out.println("字符串转换成日期出错："+ex.getMessage());
		}
		return (dateStr);
	}
	/**
	 * 把格式化的日期转换为没有分隔符的字数日期
	 * @param s
	 * @return
	 */
	public static String changeDate(String s){
		String time = "";
		if(!"".equals(s)){
			String[] ss = s.split(" ");

			if(ss.length == 2){
				String[] sss = ss[0].split("-");
				String[] sss2 = ss[1].split(":");
				for (int i = 0 ; i < sss.length ; i++){
					time = time + sss[i];
				}
				for (int i = 0 ; i < sss2.length ; i++){
					time = time + sss2[i];
				}
			}else if(ss.length == 1){
				String[] sss = ss[0].split("-");
				if(sss.length == 1){
					time = sss[0];
				}else{
					for (int i = 0 ; i < sss.length ; i++){
						time = time + sss[i];
					}
				}
			}	
		}
		return time;
	}

	/**
	 * 把格式化的时间转换为没有分隔符的字数时间
	 * @param s
	 * @return
	 */
	public static String changeTime(String s){
		String mytime = "";
		String[] date;
		String[] time;
		if(!"".equals(s)){
			String[] dateTime = s.split(" ");

			if(dateTime.length == 2){
				date = dateTime[0].split("-");
				time = dateTime[1].split(":");
				for (int i = 0 ; i < date.length ; i++){
					mytime = mytime + date[i];
				}
				for (int i = 0 ; i < time.length ; i++){
					mytime = mytime + time[i];
				}
			}else if(dateTime.length == 1){

				date = dateTime[0].split("-");
				time = dateTime[0].split(":");
				if(date.length > 1){
					for (int i = 0; i < date.length; i++) {
						mytime = mytime + date[i];
					}
				}else if(time.length > 1){
					for (int i = 0 ; i < time.length ; i++){
						mytime = mytime + time[i];
					}
				}
			}	
		}
		return mytime;
	}

	/**
	 * 传入格式为20100805000000或者20100805
	 * 返回格式2010-08-05 00:00:00或者2010-08-05
	 * @param s
	 * @return
	 */
	public static String changeTimeToFormat(String s){
		String returnValue = "";

		if(s!=null && !s.equals("")){
			if(s.length()==8){
				returnValue = s.substring(0,4) + "-" + s.substring(4,6) + "-" +
						s.substring(6,8);// + " 00:00:00";
			}else if(s.length()==10){
				returnValue = s.substring(0,4) + "-" + s.substring(4,6) + "-" +
						s.substring(6,8) + " " + s.substring(8,10) +":00:00";
			}else if(s.length()==12){
				returnValue = s.substring(0,4) + "-" + s.substring(4,6) + "-" +
						s.substring(6,8) + " " + s.substring(8,10) + ":" +
						s.substring(10,12) + ":00";
			}else if(s.length()==14){
				returnValue = s.substring(0,4) + "-" + s.substring(4,6) + "-" +
						s.substring(6,8) + " " + s.substring(8,10) + ":" +
						s.substring(10,12) + ":" + s.substring(12,14);
			}
		}
		return returnValue;
	}

	//获取当前系统时间，以yyyyMMddHHmmss格式返回
	public static String getNowTime(){
		Date d = new Date();
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");

		return sdf.format(d.getTime());
	}

	/**
	 * 
	 * @Title: getNowTime_21
	 * @Description: TODO(yyyy/MM/dd HH:mm:ss)
	 * @param @return    设定文件
	 * @return String    返回类型
	 * @throws
	 */
	public static String getNowTime_21(){
		Date d = new Date();
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(d.getTime());
	}
	public static void main(String[] args) {
		/*
		 * Date date = new Date(); List<Integer> a = new ArrayList<Integer>();
		 * a.add(new Integer(2));
		 * System.out.println(getDateTimeByStandardDateTimeForWeek(addDays(date,
		 * -10), a, 2));
		 */

		//System.out.println(getWeekNumOfYear());
		System.out.println(changeTimeToFormat("20100801101010"));
	}

}
