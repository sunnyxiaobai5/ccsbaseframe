package cn.ccsgroup.ccsframework.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;
/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.utils.ClassUtil.java]  
 * @ClassName:    [ClassUtil]   
 * @Description:  [对象取属性与值工具类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-5 下午3:44:22]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-5 下午3:44:22，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class ClassUtil {
	
	private static Logger logger = Logger.getLogger(ClassUtil.class);

	/**
	 * 根据属性名获取属性值
	 * */
	public static Object getFieldValueByName(String fieldName, Object o) {
		try {  
			String firstLetter = fieldName.substring(0, 1).toUpperCase();  
			String getter = "get" + firstLetter + fieldName.substring(1);  
			Method method = o.getClass().getMethod(getter, new Class[] {});  
			Object value = method.invoke(o, new Object[] {});  
			return value;  
		} catch (Exception e) {  
			logger.error(e.getMessage(),e);  
			return null;  
		}  
	} 

	/**
	 * 获取属性名数组
	 * */
	public static String[] getFiledName(Object o){
		Field[] fields=o.getClass().getDeclaredFields();
		String[] fieldNames=new String[fields.length];
		for(int i=0;i<fields.length;i++){
			System.out.println(fields[i].getType());
			fieldNames[i]=fields[i].getName();
		}
		return fieldNames;
	}

	/**
	 * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 * */
	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static List getFiledsInfo(Object o){
		Field[] fields=o.getClass().getDeclaredFields();
		String[] fieldNames=new String[fields.length];
		List list = new ArrayList();
		Map infoMap=null;
		for(int i=0;i<fields.length;i++){
			infoMap = new HashMap();
			infoMap.put("type", fields[i].getType().toString());
			infoMap.put("name", fields[i].getName());
			infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
			list.add(infoMap);
		}
		return list;
	}

	/**
	 * 获取对象的所有属性值，返回一个对象数组
	 * */
	public static Object[] getFiledValues(Object o){
		String[] fieldNames= getFiledName(o);
		Object[] value=new Object[fieldNames.length];
		for(int i=0;i<fieldNames.length;i++){
			value[i]= getFieldValueByName(fieldNames[i], o);
		}
		return value;
	}	

	public static String map(Map map,String val) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Iterator iter = map.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Map.Entry entry = (Map.Entry) iter.next(); 
			val += entry.getKey()+"="; 
			if(entry.getValue() instanceof BaseBean){
				val += "{";
				val += baseVo((Object)entry.getValue(),"");
				val += "};"; 
			}else if  (entry.getValue().getClass().isArray()){
				val += array(entry.getValue(), "");
			}else if  (entry.getValue() instanceof List){
				val += list( (List) entry.getValue(), "");
			}else{
				val += entry.getValue()+";";
			}
		} 
		return val;
	}
	public static String baseVo(Object vo,String val) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Method[] methods = vo.getClass().getDeclaredMethods();//获得类的方法集合
		for(int i =0 ;i<methods.length;i++){
			if(methods[i].getName().startsWith("get")){
				Object object = methods[i].invoke(vo, null);
				if(null != object)val +=methods[i].getName().substring(3)+"="+object+",";
			}
		}
		return val.substring(0, val.length()-1);
	}
	
	public static String array(Object arr,String val){
		Class type = arr.getClass();
		Class elementType = type.getComponentType();
		int len = Array.getLength(arr);
		val += "[";
		for (int i = 0; i < len; i++) {
			val += Array.get(arr, i)+",";
		}
		val = val.substring(0, val.length()-1);
		val += "];";
		return val;
	}

	public static String list(List list,String val) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		val += "[";
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object obj = (Object) iterator.next();
			if(obj instanceof BaseBean){
				val += obj.getClass().getName().substring(obj.getClass().getName().lastIndexOf(".")+1)+"={";
				val += baseVo(obj ,"");
				val += "},";
			}else{
				val += obj +",";
			}
		}
		val = val.substring(0, val.length()-1);
		val += "];"; 
		return val;
	}

}
