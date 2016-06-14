package cn.ccsgroup.ccsframework.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.ccsgroup.ccsframework.base.entity.Dict;
import cn.ccsgroup.ccsframework.base.entity.SessionBean;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.utils.XmlSessionBeanUtils.java]  
 * @ClassName:    [XmlSessionBeanUtils]   
 * @Description:  [解析SessionBean,支持内部常见所有属性]   
 * @Author:       [kundian.huo]   
 * @CreateDate:   [2014-2-20 下午12:48:50]   
 * @UpdateUser:   [kundian.huo(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-2-20 下午12:48:50，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0] 
 */
public class XmlSessionBeanUtils {
	
	public static String createXML(Map<String,Object> sessionBeanMap) throws Exception{
		Document document = DocumentHelper.createDocument();
		Element  root = document.addElement("sessionBean");
		for(Map.Entry<String, Object> entry : sessionBeanMap.entrySet()){
			String  key = entry.getKey() ;
			if(!exist(key)) continue ; 
			Object  value = entry.getValue() ;
			Element  element = root.addElement(key);
			createNode(element, value);
		}
		return document.asXML();
	}
	/**判断sessionBean中是否存在所需要修改的属性**/
	private static boolean exist(String property){
		Field[] fields = SessionBean.class.getDeclaredFields();
		for(Field field : fields ){
			if(field.getName().equals(property)) return true ;
		}
		return false ;
	}
	/**
	 * 处理所有数据类型
	 */
	private static void createNode(Element  element , Object value) throws Exception{
		if(null == value) {
			element.setText("null");
		}else{
			element.addAttribute("type", value.getClass().getName());
			if(value instanceof List){
				Element elist = element.addElement("list");
				for(Object  o : (List<?>)value ){
					createNode(elist.addElement("value"),o);
				}
			}else if(value instanceof Map){
				Element emap = element.addElement("props");
				Map<String,Object> _map = (Map<String,Object>)value ;
				for(String  key : _map.keySet()){
					Element eprop = emap.addElement("prop").addAttribute("type",_map.get(key).getClass().getName()).addAttribute("key", key);
					createNode(eprop ,_map.get(key));
				}
			}else {
				//基本类型和自定义类型
				createBaseNode(value,element);
			}
		}
	}
	/**
	 * 处理基本类型和自定义类型
	 */
	private static void createBaseNode(Object value , Element element) throws Exception{
		if(isSimpleType(value)){
			element.setText(Obj2Str(value));
		}else{
			//自定义类型
			Element ele = element.addElement(value.getClass().getSimpleName()) ;
			Field[] fields = value.getClass().getDeclaredFields();
			for(Field  field : fields){
				field.setAccessible(true);
				if("serialVersionUID".equals(field.getName()))continue;
				createNode(ele.addElement(field.getName()), field.get(value));
			}
		}
	}
	/**将对象转换为字符串（设置文本使用）*/
	private static String Obj2Str(Object o){
		return o==null?"null":o.toString();
	}
	
	
	/**
	 * 解析XML
	 * */
	public static void parseXML(SessionBean sessionBean , String xml) throws Exception{
		Document document = DocumentHelper.parseText(xml);
		Element  root = document.getRootElement() ;
		Field[] fields = SessionBean.class.getDeclaredFields() ;
		for(Field field :fields) {
			field.setAccessible(true);
			Element node = (Element)document.selectSingleNode(root.getPath() + "/" + field.getName());
			if(node==null) continue ;
			String className = node.attributeValue("type") ;
			Class<?>  clazz  = null ;
			if(className!=null &&!"".equals(className)) {
				clazz = Class.forName(className) ;
			}
			parseNode(clazz ,node , document , sessionBean , field);
		}
	}
	
	/**
	 * 解析所有类型数据
	 */
	private static void parseNode(Class<?> clazz, Element element,Document document, Object obj ,Field field) throws Exception{
		if(clazz == null){
			 field.set(obj, null);
		}else if(isListType(clazz)) {
			List<?> list = parseNodeForList(element,document);
			field.set(obj, list);
		}else if(isMapType(clazz)){
			Map<String,Object> map = parseNodeForMap(element,document);
			field.set(obj, map);
		}else {
			//基本类型
			 if(isSimpleType(clazz)){
				 field.set(obj, Str2T(clazz, element.getText()));
			 }else { //自定义类型
				 Object o = parseBaseNode(element,document);
				 field.set(obj, o);
			 }
		}
	}
	/**
	 * 解析List属性
	 * */
	private static List<Object>  parseNodeForList(Element element ,Document document) throws Exception{
		List<Object> list = new ArrayList<Object>();
		List<Element> nodeList = ((Element)element.elements("list").get(0)).elements("value");
		if(nodeList == null || nodeList.isEmpty()) return null ;
		//获取List属性存放的内容类型
		for(Element node : nodeList){
			if(node==null) continue ;
			String clazzName = node.attributeValue("type") ;
			Class<?> clazz  = null ;
			if(null!=clazzName && !"".equals(clazzName)){
				clazz = Class.forName(clazzName) ;
			}
			if(clazz == null){
				list.add(null);
			}else if(isSimpleType(clazz)){
				list.add(Str2T(clazz, node.getText()));
			}else if(isListType(clazz)){ 
				list.add(parseNodeForList(node , document ));
			}else if(isMapType(clazz)){
				list.add(parseNodeForMap(node,document));
			}else {//自定义类型
				list.add(parseBaseNode(node , document));
			}
		}
		return list ;
	}
	private static Map<String,Object> parseNodeForMap(Element element ,Document document) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		List<Element> nodeList = ((Element)element.elements("props").get(0)).elements("prop");
		//List<Element> nodeList = document.selectNodes(element.getPath() +"/props/prop");
		if(nodeList == null || nodeList.isEmpty()) return null ;
		
		for(Element node : nodeList){
			String clazzName = node.attributeValue("type") ;
			Class<?> clazz  = null ;
			if(null!=clazzName && !"".equals(clazzName)){ clazz = Class.forName(clazzName) ;}
			String  key = node.attributeValue("key") ;
			if(clazz == null ) map.put(key , null);
			else if(isListType(clazz)) {
				 map.put(key , parseNodeForList(node,document));
			}else if(isMapType(clazz)){
				map.put(key, parseNodeForMap(node, document));
			}else if(isSimpleType(clazz)){
				map.put(key , Str2T(clazz, node.getText()));
			}else {//自定义类型
				map.put(key, parseBaseNode(node, document));
			}
		}
		return map ;
	}
	
	/**
	 * 解析自定义数据
	 */
	private static Object parseBaseNode(Element element,Document document) throws Exception{
		//获取数据类型
		String clazzName = element.attributeValue("type") ;
		Class<?> cla = null ;
		if(null!=clazzName && !"".equals(clazzName)){ cla = Class.forName(clazzName) ;}
		if(cla==null) return null ;
		Object o = cla.newInstance() ; 
		Field[]  fields =  cla.getDeclaredFields() ;
		
		List<Element> list = element.elements();
		if(list!=null &&!list.isEmpty()) {
			Element e = list.get(0);
			List<Element> elist = e.elements() ;
			for(Element el  : elist) {
				for(Field field : fields) {
					field.setAccessible(true);
					if(el.getName().equals(field.getName())) {
						/*获取xml中当前属性的类型*/
						String clazzName1 = el.attributeValue("type") ;
						Class<?> clazz = null ;
						if(null!=clazzName1 && !"".equals(clazzName1)){ clazz = Class.forName(clazzName1) ;}
						if(clazz==null) field.set(o,null);
						else if(isListType(clazz)){
							parseNodeForList(el ,document);
						}else if(isMapType(clazz)){
							parseNodeForMap(el ,document);
						}else if(isSimpleType(clazz)){
							field.set(o ,Str2T(field.getType(), el.getText()));
						}else{
							parseBaseNode(el ,document);
						}
						break ;
					}
				}
			}
		}
		return o ;
	}
	
	/**
	 * 将Str转换为指定类型
	 */
	private static <T> Object Str2T(Class<T> clazz,String text){
		if(clazz == Byte.class || clazz==byte.class) 			return Byte.parseByte(text);
		else if(clazz == Short.class || clazz==short.class)		return Short.parseShort(text);
		else if(clazz == Integer.class || clazz==int.class)		return Integer.parseInt(text);
		else if(clazz == Long.class || clazz==long.class)		return Long.parseLong(text);
		else if(clazz == Float.class || clazz==float.class)		return Float.parseFloat(text);
		else if(clazz == Double.class || clazz==double.class)	return Double.parseDouble(text);
		else if(clazz == Boolean.class || clazz==boolean.class) return Boolean.parseBoolean(text);
		else if(clazz == Character.class || clazz==char.class)	return text.charAt(0);
		else if(clazz == String.class )							return text;
		return null ;
	}
	/**是否是简单类型，包含String*/
	private static boolean isSimpleType(Class<?> clazz){
		return	clazz==Byte.class || clazz==byte.class || clazz==Short.class ||
				clazz==short.class || clazz==Integer.class || clazz==int.class||
				clazz==Long.class||clazz==long.class||clazz==float.class||
				clazz==Float.class||clazz==Double.class||clazz==double.class||
				clazz==Character.class||clazz==char.class ||clazz==String.class;
	}
	/**是否是自定义类型*/
	private static boolean isSelfType(Class<?> clazz){
		return !isSimpleType(clazz) && !isListType(clazz) && !isMapType(clazz);
	}
	/**是否是简单类型*/
	private static boolean isSimpleType(Object value){
		return  value instanceof Short|| value instanceof Long || 
				value instanceof Byte || value instanceof Integer || 
				value instanceof Double || value instanceof Float || 
				value instanceof Boolean || value instanceof Character || 
				value instanceof String ;
	}
	/**是否是list类型*/
	private static boolean isListType(Class<?> clazz){
		return clazz==List.class || clazz==ArrayList.class ;
	}
	/**是否是map类型*/
	private static boolean isMapType(Class<?> clazz){
		return clazz==Map.class || clazz==HashMap.class;
	}
	
	
	public static void main(String[] args) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", 1000);
		map.put("userIp", "192.168.1.1");
		
		List<String> list = new ArrayList<String>();
		list.add("5100000");
		list.add("5100001");
		list.add(null);
		list.add("5100003");
		map.put("regionCodes", list);
		
		Map<String,String> mapUrl = new HashMap<String,String>();
		mapUrl.put("100", "http://www.baidu.com");
		mapUrl.put("101", "http://www.sina.com");
		map.put("powerUrls", mapUrl);
		
		
		List<Dict> dlist = new ArrayList<Dict>();
		Dict dict = new Dict();
		dict.setDm("aaa");
		dict.setId(111);
		dict.setMc("bbb");
		dlist.add(dict);
		
		List<Map<String,String>> test1 = new ArrayList<Map<String,String>>();
		test1.add(mapUrl);
		map.put("test1", test1);
		
		
		
		List<List<Dict>> test = new ArrayList<List<Dict>>();
		test.add(dlist);
		map.put("test", test);
		
		map.put("system", null);
			
		dict = new Dict();
		dict.setDm("cccc");
		dict.setId(null);
		dict.setMc("dddd");
		dlist.add(dict);
		map.put("positions", dlist);
		
		List<Integer> tlist = new ArrayList<Integer>();
		tlist.add(111);
		tlist.add(222);
		map.put("roleId",tlist);
		
		Map<String,Object> test2 = new HashMap<String,Object>();
		test2.put("test1111" , test);
		test2.put("test2222" , test1);
		map.put("test2",test2);
	
		String xml = null;
		try {
			xml = createXML(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(xml);
		
		SessionBean sb = new SessionBean();
		try{	
			parseXML(sb,xml);
			System.out.println(sb.getUserIp());
			System.out.println(sb.getSystem());
			System.out.println(sb.getPowerUrls());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
