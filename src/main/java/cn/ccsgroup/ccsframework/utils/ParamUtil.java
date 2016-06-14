package cn.ccsgroup.ccsframework.utils;

import javax.servlet.ServletRequest;

public class ParamUtil
{
	public static String getParameter(ServletRequest request, String paramName)
	{
		String temp = request.getParameter(paramName);
		if (temp != null && !temp.equals(""))
		{
			try
			{
				temp = java.net.URLDecoder.decode(temp, "UTF-8");
			} catch (Exception e)
			{
				return "";
			}
			return temp;
		} else
		{
			return "";
		}
	}

	public static int getIntParameter(ServletRequest request, String paramName,
			int defaultNum)
	{
		String temp = request.getParameter(paramName);
		if (temp != null && !temp.equals(""))
		{
			int num = defaultNum;
			try
			{
				num = Integer.parseInt(temp);
			} catch (Exception ignored)
			{
			}
			return num;
		} else
		{
			return defaultNum;
		}
	}
	
	public static String sortStr(PageHelper pager){
		  String nowSortStr = pager.getSortStr();
		  String sortField = pager.getSortField();
		  boolean  sortFlag = pager.getSortFlag();
		  if(sortFlag){
		  if(!"".equals(sortField) && sortField!=null){
			  String field_temp = "";
			  int blankIndex = nowSortStr.indexOf(" ");
			  if(blankIndex!=-1)
			       field_temp = nowSortStr.substring(0,blankIndex);
			  else
				  field_temp = nowSortStr;
		  	if(field_temp.equals(sortField)){//�ֶ�����ͬ��
		  	 	  if(nowSortStr.indexOf("ASC")!=-1){
		  	 	  	  nowSortStr = sortField+" DESC";
		  	 	  	}else{
		  	 	  		nowSortStr = sortField+" ASC";
		  	 	  	}
		  	 	}else{//�ֶβ���ͬ
		  	 		 nowSortStr = sortField+" ASC";
		  	 		}
		  	return nowSortStr;
		  	}
		  }else
			  return nowSortStr;
		  return "";
		}
}
