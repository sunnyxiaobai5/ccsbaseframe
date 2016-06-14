package cn.ccsgroup.ccsframework.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.utils.StringUtil.java]  
 * @ClassName:    [StringUtil]   
 * @Description:  [字符串处理方法类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-2 下午5:19:29]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-2 下午5:19:29，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class StringUtil
{
	private static Logger logger = Logger.getLogger(StringUtil.class);

	public static final String CONTENT_TYPE = "text/html;charset=UTF-8";
	static final int[] wi = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1}; 
	// verify digit 
	static final int[] vi = {1,0,'X',9,8,7,6,5,4,3,2}; 
	static private int[] ai = new int[18]; 
	/**
	 * <判断字符串是否为空或者空字符串。>
	 * <功能详细描述>
	 * @param str
	 * @return [参数说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static final boolean isBlank(String str)
	{
		return ((str == null) || ("".equals(str.trim())));
	}

	/**
	 * 判断一个字符串是否为整数
	 * @param str，字符串
	 * @return boolean，如果是则返回true，否则返回false.
	 */
	public static final boolean isNum(String str)
	{
		return Pattern.matches("\\d+", str);
	}

	/**
	 * <判断字符串是否为空或者空字符串。不为空返回true>
	 * <功能详细描述>
	 * @param str
	 * @return [参数说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static final boolean isNotBlank(String str)
	{
		return !isBlank(str);
	}

	public static boolean isCh(String str)
	{
		for (int i = 0; i < str.length(); i++)
		{
			if (str.substring(i, i + 1).matches("[\\u4e00-\\u9fa5]+"))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * <全角转半角>
	 * @param dbc 全角字符串
	 * @return 半角字符串
	 */
	public static final String SBCToDBC(String sbc)
	{
		StringBuffer outStr = new StringBuffer();
		String _str = "";
		byte[] b = null;
		try
		{
			for (int i = 0; i < sbc.length(); i++)
			{
				_str = sbc.substring(i, i + 1);
				b = _str.getBytes("unicode");

				if (b[3] == -1)
				{
					b[2] = (byte) (b[2] + 32);
					b[3] = 0;

					outStr.append(new String(b, "unicode"));

				}
				else
				{
					outStr.append(_str);
				}
			}
			//对空格全角字符的处理
			char[] c = outStr.toString().toCharArray();
			outStr = null;
			for (int i = 0; i < c.length; i++)
			{
				if (c[i] == 12288)
				{
					c[i] = (char) 32;
					continue;
				}
				if (c[i] > 65280 && c[i] < 65375)
				{
					c[i] = (char) (c[i] - 65248);
				}
			}
			return new String(c);
		}
		catch (Exception e)
		{
			//TODO
		}
		return null;
	}

	/**
	 * Get a replaced string by the specified message.
	 * @param source  The original message
	 * @param pattern The key message for replacing
	 * @param replace The message to place in the key variable - 'pattern'
	 * @return The replaced message
	 */
	public static String replaceAll(String source, String pattern,
			String replace)
	{
		int i = 0;
		boolean ret = false;
		StringBuffer buf = new StringBuffer();

		int lenSource = source.length();
		int lenPattern = pattern.length();

		for (i = 0; i < lenSource; i++)
		{
			ret = source.regionMatches(i, pattern, 0, lenPattern);
			if (ret)
			{
				buf.append(source.substring(0, i));
				buf.append(replace);
				buf.append(source.substring(i + lenPattern));
				source = replaceAll(buf.toString(), pattern, replace);
				break;
			}
		}
		return source;
	}

	/**
	 * 转移字符还原为逗号
	 */
	public static String revertStr(String str)
	{
		return str.replace("&#8218;", ",");
	}

	/**
	 * 逗号替换为转移字符
	 */
	public static String conversStr(String str)
	{
		return str.replace(",", "&#8218;");
	}

	/**
	 * 从文件路径中，获得文件名
	 * @param filePath 文件路径
	 * @return 文件名
	 * 
	 * @return String 字符串
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static String getFileName(String filePath)
	{
		String fileName = "";
		String separator = "/";
		if (filePath.lastIndexOf("\\") > 0)
		{
			separator = "\\";
		}
		else
		{
			separator = "/";
		}
		fileName = filePath.substring(filePath.lastIndexOf(separator) + 1);
		return fileName;
	}

	/**
	 * <获取不重复的文件名>
	 * <调用getFile(String filePath, int index)>
	 * @param filePath
	 * @return [参数说明]
	 * 
	 * @return File [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static File getFile(String filePath)
	{
		return getFile(filePath, 0, 0, null);
	}

	/**
	 * <获取不重复的文件名>
	 * <如果不是第一次校验，换掉最后三个字符，否则最后添加“（1）”，
	 * 如果全路径名超过限制返回null,
	 * 如果文件存在且为文件，递归调用，否则返回文件>
	 * @param filePath 文件全路径
	 * @param index 例如：新建文件夹（1）的最后三个字符，作为累加器
	 * @return [参数说明]
	 * 
	 * @return File [返回类型说明]
	 * @exception throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	private static File getFile(String filePath, int index, int lastIndex,
			String lastName)
	{

		if (index != 0)
		{
			lastIndex = filePath.lastIndexOf(".");
			if (lastIndex > 0)
			{
				lastName = filePath.substring(lastIndex);
				filePath = filePath.substring(0, lastIndex);
			}
			if (index != 1)
			{
				filePath = filePath.substring(0, filePath.length() - 3) + "("
				+ index + ")";
			}
			else
			{
				filePath += "(" + index + ")";
			}
			if (lastName != null)
			{
				filePath += lastName;
				lastName = null;
			}
		}
		if (filePath.length() > 255)
		{
			return null;
		}
		File file = new File(filePath);
		if (file != null)
		{
			if (file.exists() && file.isFile())
			{
				return getFile(filePath, ++index, lastIndex, lastName);
			}
		}
		else
		{
			return null;
		}
		return file;
	}

	/**
	 * 检查Email格式
	 * @param mail，EMail地址
	 * @return boolean [返回类型说明]
	 */
	public static boolean checkEmail(String mail)
	{
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		java.util.regex.Matcher m = p.matcher(mail);
		return m.find();
	}

	public static java.util.Date getSystemDate(){
		return new java.util.Date();
	}


	/**
	 * 
	 * <li>方法名：createJspFileName
	 * <li>@param source
	 * <li>@return
	 * <li>返回类型：String
	 * <li>说明：根据传入的字符串创建用下划线分隔的全小写字母组成的文件名
	 * <li>创建人：
	 * <li>创建日期：2008-10-14
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static String createJspFileName(String source){

		StringBuffer sb = new StringBuffer();		
		sb.append(Character.toLowerCase(source.charAt(0)));
		for(int i = 1; i < source.length(); ++i){
			if(Character.isUpperCase(source.charAt(i))){		
				sb.append('_');
			}
			sb.append(Character.toLowerCase(source.charAt(i)));
		}

		return sb.toString();
	}

	/**
	 * <li>方法名：Long2Int
	 * <li>@param ldata
	 * <li>@return
	 * <li>返回类型：int
	 * <li>说明：数据类型转换
	 * <li>创建人：
	 * <li>创建日期：2008-10-21
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static int Long2Int(long ldata)
	{	
		return Integer.parseInt(String.valueOf(ldata));
	}

	/**
	 * <li>方法名：Int2Long
	 * <li>@param ldata
	 * <li>@return
	 * <li>返回类型：int
	 * <li>说明：数据类型转换
	 * <li>创建人：
	 * <li>创建日期：2008-11-10
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static long Int2Long(Integer intS)
	{	
		return Long.parseLong(intS.toString());
	}

	/**
	 * <li>方法名：Str2Long
	 * <li>@param str
	 * <li>@return
	 * <li>返回类型：Long
	 * <li>说明：数据类型转换
	 * <li>创建人：
	 * <li>创建日期：2008-11-10
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static long Str2Long(String str){
		return Long.parseLong(str.trim());
	}

	/**
	 * <li>方法名：string2BigDecimal
	 * <li>@param str
	 * <li>@return
	 * <li>返回类型：BigDecimal
	 * <li>说明：数据类型转换
	 * <li>创建人：
	 * <li>创建日期：2008-10-21
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static BigDecimal String2BigDecimal(String str) 
	{
		BigDecimal bigDecimal = null;
		if (str != null && str.trim().length() != 0)
		{
			bigDecimal = new BigDecimal(str);
		}
		return bigDecimal;
	}

	/**
	 * <li>方法名：Str2Int
	 * <li>@param str
	 * <li>@return
	 * <li>返回类型：int
	 * <li>说明：数据类型转换
	 * <li>创建人：
	 * <li>创建日期：2008-10-21
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static int Str2Int(String str)
	{
		if (str == null || "".equals(str))
			return 0;
		return Integer.parseInt(str);
	}

	/**
	 * <li>方法名：StrFill
	 * <li>@param fillStr 用来补位的字符
	 * <li>@param oldStr 需要补位的字符串
	 * <li>@param length 补位后的总长度
	 * <li>@param place 补位位置:left or right
	 * <li>@return
	 * <li>返回类型：String
	 * <li>说明：用指定的字符为需要补位的字符串补位
	 * <li>创建人：
	 * <li>创建日期：2008-10-21
	 * <li>修改人： jxfeng
	 * <li>修改日期：2010-4-28 11:15
	 */
	public static String StrFill(String fillStr ,String oldStr ,int length ,String place)
	{
		StringBuffer sb =  new StringBuffer();
		//加入需填充字符串的空值判断，当为空时返回空字符串
		if(!isBlank2(oldStr)){
			if("right".equals(place)){
				sb.append(oldStr);
			}
			for(int i=0; i < (length - oldStr.length());i++){
				sb.append(fillStr);
			}
			if("left".equals(place)){
				sb.append(oldStr);
			}
		}
		return sb.toString();
	}

	/**
	 * <li>方法名：isBlank
	 * <li>@param str
	 * <li>@return
	 * <li>返回类型：boolean
	 * <li>说明：判断字符串是否为空,为空就返回true,不为空返回false
	 * <li>创建人：
	 * <li>创建日期：2008-11-26
	 * <li>修改人： 
	 * <li>修改日期：
	 */
	public static boolean isBlank(Object str){
		if(str==null){
			return true;
		}
		if(String.valueOf(str).length()<1){
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为空,匹配多个空格
	 * @param str
	 * @return
	 */
	public static boolean isBlank2(String str){
		if(str==null){
			return true;
		}
		if (str.matches("\\s*")){
			return true;
		}else{
			return false;
		}
	}



	/**
	 * <li>@方法说明:转为UTF-8字符
	 * <li>@详细说明:
	 * <li>@param str
	 * <li>@return
	 * <li>@创建人:王剑
	 * <li>@创建日期:2009-3-26
	 * <li>@修改人:
	 * <li>@修改日期:
	 */
	public static String toUTF8(String str){
		if(str == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i=0; i< str.length(); i++) {
			char c = str.charAt(i);
			if(c >= 0 && c <= 256){
				sb.append(c);
			}
			else{
				try{
					byte[] b = Character.toString(c).getBytes("UTF-8");
					for (int j = 0; j < b.length; j++) {
						int k = b[j];
						if(k<0){
							k = k + 256;
						}
						sb.append("%" + Integer.toHexString(k).toUpperCase());
					}
				}
				catch (Exception e) {
					System.out.println(e);
				}
			}
		}

		return sb.toString();
	}

	/**
	 * <li>@方法说明:解UTF-8为8859_1
	 * <li>@详细说明:
	 * <li>@param s
	 * <li>@return
	 * <li>@创建人:王剑
	 * <li>@创建日期:2009-3-26
	 * <li>@修改人:
	 * <li>@修改日期:
	 */
	public static String decodeUTF8(String s) {
		if (s == null)
			return "";

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					// 将16进制的数转化为十进制
					sb.append((char) Integer.parseInt(
							s.substring(i + 1, i + 3), 16));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				}
				i += 2;
				break;
			default:
				sb.append(c);
				break;
			}
		}

		String result = sb.toString();
		// 将UTF-8转换为GBK java+%E7%BC%96%E7%A8%8B
		// byte[] inputBytes = result.getBytes("8859_1"); //UTF8
		// return new String(inputBytes,"GB2312");
		try {
			result = new String(result.getBytes("8859_1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * <li>@方法说明:转换为SQL字符参数
	 * <li>@详细说明:
	 * <li>@param str
	 * <li>@return
	 * <li>@创建人:王剑
	 * <li>@创建日期:2009-3-26
	 * <li>@修改人:
	 * <li>@修改日期:
	 */
	public static String toSqlParam(String str){
		if (str==null) return "";
		str = str.trim();
		StringBuffer buf = new StringBuffer();
		buf.append("'");
		buf.append(str.replaceAll("'", "''"));
		buf.append("'");
		return buf.toString();
	}

	/**
	 * <li>@方法说明:
	 * <li>@详细说明:
	 * <li>@param strs
	 * <li>@param split
	 * <li>@return
	 * <li>@创建人:王剑
	 * <li>@创建日期:2009-3-26
	 * <li>@修改人:
	 * <li>@修改日期:
	 */
	public static String getStringFromArray(String[] strs,String split){
		StringBuffer buf = new StringBuffer();
		for (int i = 0;i<strs.length;i++){
			buf.append(strs[i]);
			if (i!=(strs.length-1)){
				buf.append(split);
			}
		}
		return buf.toString();
	}

	/**
	 * 
	 * tableName2Class StringUtil
	 * 
	 * 表名转化为类名
	 * @param tableName
	 * @return
	 *
	 * @date Mar 17, 2009 10:47:51 PM
	 *
	 */
	public static String tableName2Class(String tableName){
		StringBuffer sb = new StringBuffer();
		String[] str = tableName.split("_");
		for(int i = 0, j = str.length; i < j; i++){
			sb.append(str[i].substring(0, 1).toUpperCase());
			sb.append(str[i].substring(1, str[i].length()).toLowerCase());
		}
		return sb.toString();
	}

	/**
	 * 
	 * <li> 功能描述：表名转换为小写的名称
	 * <li> 创建日期：2009-6-19
	 * @param tableName
	 * @return
	 * @author fhway
	 */
	public static String tableName2LowerCase(String tableName){
		return tableName2Class(tableName).toLowerCase();
	}


	/**
	 * Description: 中文字符串转换(解决中文乱码问题)
	 * 
	 * @param str
	 *            要转换的源字符串
	 * @return String
	 */
	public static String chineseStr(String str) {
		try {
			if (str == null) {
				return "";
			}
			String tempStr = str;
			byte[] tempArray = tempStr.getBytes("ISO8859-1");
			String temp = new String(tempArray, "UTF-8");
			return temp;
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		return "";
	}

	/**
	 * Description: 把null转换为 ""
	 * 
	 * @param temp
	 *            要转换的源字符串
	 * @return String
	 */

	public static String nullToStr(String temp) {
		if (temp == null || "NaN".equals(temp)||"null".equals(temp)) {
			temp = "";
		}
		return temp;
	}
	public static String nullToStr2(String temp) {
		if (temp == null) {
			temp = "无";
		}
		return temp.trim();
	}

	/**
	 * Description: 取得字符串长度(一个汉字为两个字符长度)
	 * 
	 * @param sourceStr
	 *            要计算长度的字符串
	 * @return String
	 */
	public static long getStringLength(String sourceStr) {
		long returnValue = 0;
		if (sourceStr == null) {
			return (returnValue);
		}
		for (int i = 0; i < sourceStr.length(); i++) {
			char[] tempChar = sourceStr.substring(i, i + 1).toCharArray();
			if ((int) tempChar[0] > 255) {
				returnValue += 2;
			} else {
				returnValue++;
			}
		}
		return (returnValue);
	}

	/**
	 * Description: 取得重复次数的字符串
	 * 
	 * @param sourceStr
	 *            要重复的字符串
	 * @param repeatTimes
	 *            重复次数
	 * @return String
	 */

	public static String getRepeatString(String sourceStr, long repeatTimes) {
		StringBuffer returnStr = new StringBuffer();
		for (int i = 0; i < repeatTimes; i++) {
			returnStr.append(sourceStr);
		}
		return (returnStr.toString());
	}

	/**
	 * Description: 取得指定长度的字符串,不足长度的以replaceString填充(一个汉字为两个字符长度)
	 * 
	 * @param sourceStr
	 *            源字符串
	 * @param specityLength
	 *            新字符串长度
	 * @param replaceString
	 *            不足部分要填充的字符
	 * @return String
	 */
	public static String getSpecifyLengthString(String sourceStr,
			long specityLength, String replaceString) {
		if (sourceStr == null) {
			return (getRepeatString(replaceString, specityLength));
		}
		long realLength = getStringLength(sourceStr);
		StringBuffer returnStr = new StringBuffer();
		if (realLength < specityLength) {
			returnStr.append(sourceStr);
			returnStr.append(getRepeatString(replaceString, specityLength
					- realLength));
		} else {
			returnStr.append(getLeftString(sourceStr, specityLength,
					replaceString));
		}
		return (returnStr.toString());
	}

	/**
	 * Description: 取得其中包含字符串中从左边算起指定数量的字符(一个汉字为两个字符长度)
	 * 
	 * @param sourceStr
	 *            源字符串
	 * @param leftLength
	 *            从左边第几位开始
	 * @param replaceString
	 *            要重复的字符
	 * @return String
	 */

	public static String getLeftString(String sourceStr, long leftLength,
			String replaceString) {
		StringBuffer returnStr = new StringBuffer();
		long tempLength = 0;
		for (int i = 0; i < sourceStr.length(); i++) {
			String tempStr = sourceStr.substring(i, i + 1);
			char[] tempChar = tempStr.toCharArray();
			if ((int) tempChar[0] > 255) {
				tempLength += 2;
			} else {
				tempLength++;
			}
			if (tempLength >= leftLength) {
				if (tempLength == leftLength) {
					returnStr.append(tempStr);
				} else {
					returnStr.append(getRepeatString(replaceString, tempLength
							- leftLength));
				}
				break;
			}
			returnStr.append(tempStr);
		}
		return (returnStr.toString());
	}

	/**
	 * Description: 取得指定长度的字符串(一个汉字为两个字符长度,targetlength为汉字个数),超过以…代替
	 * 
	 * @param sourceString
	 *            源字符串
	 * @param targetlength
	 *            汉字个数
	 * @return String
	 */

	public static String displayTitle(String sourceString, long targetlength) {
		String returnValue = "";
		if (sourceString != null) {
			if (getStringLength(sourceString) <= targetlength * 2) {
				returnValue = sourceString;
			} else {
				returnValue = getLeftString(sourceString,
						(targetlength - 1) * 2, "")
						+ "…";
			}
		}
		return (returnValue);
	}

	/**
	 * Description: 中文字符串转换，用于 js 中 encodeURI() 方式提交的参数转换
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return String
	 */
	public static String chineseStr3(String str) {
		try {
			if (str == null) {
				return "";
			}
			String tempStr = str;
			byte[] tempArray = tempStr.getBytes("ISO8859-1");
			String temp = new String(tempArray, "UTF8");
			return temp;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return "";
	}

	// URL中的中文字符串转换
	public static String URLEncode(String str) {
		String returnValue = "";
		try {
			if (str != null) {
				returnValue = java.net.URLEncoder.encode(str, "UTF-8");
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return returnValue;
	}


	/**
	 * 
	 * @param abyte
	 *            字节流数组
	 * @param i
	 *            起点 第一个位子从0开始计算
	 * @param j
	 *            长度
	 * @return
	 */
	public static String getByte(byte[] abyte, int i, int j)
	{
		try
		{
			byte[] byte1 = new byte[j];
			int l = 0;
			for (int k = i; k < i + j; k++)
			{
				byte1[l] = abyte[k];
				l++;
			}
			return new String(byte1, "GBK");
		} catch (Exception e)
		{

		}
		return null;
	}

	/**
	 * 取得指定长度的字符串
	 * 
	 * @param str
	 *            字符串
	 * 
	 * 
	 * 
	 * @param len
	 *            以byte为单位的，一个汉字是2个byte
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getLimitLengthString(String str, int len)
	{
		int counterOfDoubleByte = 0;
		byte[] b = null;
		try
		{
			b = str.getBytes("GBK");
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (b.length <= len)
			return str;
		for (int i = 0; i < len; i++)
		{
			if (b[i] < 0)
				counterOfDoubleByte++;
		}

		if (counterOfDoubleByte % 2 == 0)
			try
		{
				return (new String(b, 0, len, "GBK")) + "...";
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		else
			try
		{
				return (new String(b, 0, len - 1, "GBK")) + "...";
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}



	public static String getUrl(String u)
	{
		Pattern p = Pattern
		.compile("/{0,1}[&\\?]{1}(onecgy=){1}[\\.a-zA-Z0-9_\\-]+");
		Matcher m = p.matcher(u);
		String t = "";
		StringBuffer tu = new StringBuffer();
		if (m.find())
		{
			t = m.group();
			p = Pattern.compile("/{0,1}[&\\?]{1}(onecgy=){1}");
			m = p.matcher(t);
			tu.append(m.replaceAll(""));
			tu.append("/");
		}
		p = null;
		m = null;
		p = Pattern.compile("[\\?&]{0,1}(twocgy=){1}[\\.a-zA-Z0-9_\\-]+");
		m = p.matcher(u);
		if (m.find())
		{
			t = m.group();
			p = Pattern.compile("[\\?&]{0,1}(twocgy=){1}");
			m = p.matcher(t);
			tu.append(m.replaceAll(""));
			tu.append("/");
		}
		p = null;
		m = null;
		// System.out.println(u);
		p = Pattern.compile("(/log\\.jsp\\?file=){1}[\\.a-zA-Z0-9_\\-]+");
		m = p.matcher(u);
		if (m.find())
		{
			t = m.group();
			p = Pattern.compile("(/log\\.jsp\\?file=){1}");
			m = p.matcher(t);
			tu.append(m.replaceAll(""));
			tu.append(".htm");
		}
		return "/" + tu.toString();
	}

	public static String getCurUri(HttpServletRequest request)
	{
		String qStr = request.getQueryString();
		String rtn = request.getRequestURI();
		// rtn.append(request.getRequestURI());
		if (qStr != null)
		{
			rtn += ("?" + qStr);
		}
		String ur = getCurUr(request);
		if (qStr != null && !qStr.equals(""))
		{
			if (qStr.indexOf("&") != -1)
				rtn = ur.substring(ur.indexOf("/"), ur.indexOf("&")).toString();
			else
				rtn = ur;
		}
		return rtn.toString();
	}

	public static String getCurUr(HttpServletRequest request)
	{
		String qStr = request.getQueryString();
		StringBuffer rtn = new StringBuffer();
		rtn.append(request.getRequestURI());
		if (qStr != null)
		{
			rtn.append("?" + qStr);
		}
		return rtn.toString();
	}

	/**
	 * 
	 * <li> 功能描述：插入数据库时替换(转义字符)
	 * <li> 创建日期：2009-4-1
	 * @param	String str
	 * @return	String str
	 */
	public static String replaceInStr(String str)
	{
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\n", "<br/>");
		str = str.replaceAll(" ", "&nbsp;");

		return str;
	}

	/**
	 * 
	 * <li> 功能描述：取出数据时替换(转义字符)
	 * <li> 创建日期：2009-4-1
	 */
	public static String replaceOutStr(String str)
	{
		str = str.replaceAll("<br/>", "\n");
		str = str.replaceAll("<br>", "\n");
		str = str.replaceAll("&lt;", "<");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("&nbsp;", " ");
		str = str.replaceAll("&amp;", "&");
		str = str.replaceAll("&quot;", "\"");
		return str;
	}

	/**
	 * 
	 * get18BitSfzh
	 * <p>
	 * 取得 18 位身份证号码
	 * </P>
	 * @param String <br>
	 * 	sfzh 16位身份证号
	 * @return String <br>
	 *  18位身份证号
	 *
	 * @author fhway <br>
	 * 2008-11-12 上午09:57:23
	 */
	public static String get18BitSfzh(String sfzh) {
		String returnValue = sfzh;
		try {
			if (sfzh.length() == 15) {
				String tempStr1 = sfzh.substring(0, 6);
				String tempStr2 = "19" + sfzh.substring(6);
				String tempStrAll = tempStr1 + tempStr2;
				int lastAt = 0;
				for (int i = 0; i < 17; i++) {
					int bitInt = Integer.parseInt(tempStrAll.substring(i, i + 1));
					int bitIntTemp = 1;
					for (int j = 0; j < 17 - i; j++) {
						bitIntTemp = (bitIntTemp * 2) % 11;
					}
					lastAt += bitInt * bitIntTemp;
				}
				lastAt = lastAt % 11;
				returnValue = tempStrAll + "10X98765432".substring(lastAt, lastAt + 1);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return returnValue;
	}



	/**
	 * 
	 * <li> 功能描述：判断是否是身份证号码
	 * <li> 创建日期：2009-10-22
	 * @param idcard
	 * @return
	 * @author fhway
	 */
	public static boolean isIDCard(String idcard) {
		if (idcard.length() == 15) {
			idcard = uptoeighteen(idcard);
		}
		if (idcard.length() != 18) {
			return false;
		}
		String verify = idcard.substring(17, 18);
		if (verify.equals(getVerify(idcard))) {
			return true;
		}
		return false;
	}

	// get verify
	public static String getVerify(String eightcardid) {
		int remaining = 0;

		if (eightcardid.length() == 18) {
			eightcardid = eightcardid.substring(0, 17);
		}

		if (eightcardid.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eightcardid.substring(i, i + 1);
				ai[i] = Integer.parseInt(k);
			}

			for (int i = 0; i < 17; i++) {
				sum = sum + wi[i] * ai[i];
			}
			remaining = sum % 11;
		}
		return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
	}

	// 15 update to 18
	public static String uptoeighteen(String fifteencardid) {
		String eightcardid = fifteencardid.substring(0, 6);
		eightcardid = eightcardid + "19";
		eightcardid = eightcardid + fifteencardid.substring(6, 15);
		eightcardid = eightcardid + getVerify(eightcardid);
		return eightcardid;
	} 


	/**
	 * 
	 * <li> 功能描述： 字符串切分
	 * <li> 创建日期：2009-5-20
	 * @param str
	 * @param simpleFormat
	 * @return
	 * @author fhway
	 */
	public static String[] getArrayFromString(String basicStr, String simpleFormat){
		if (simpleFormat.equals("") || simpleFormat == null) {
			return null;
		}
		String newStr = "";
		basicStr = basicStr + simpleFormat;
		while (basicStr.indexOf(simpleFormat) > -1) {
			newStr += basicStr.substring(0, basicStr.indexOf(simpleFormat)) + ",";
			basicStr = basicStr.substring(basicStr.indexOf(simpleFormat) + 1);
		}
		newStr.substring(0, newStr.lastIndexOf(","));
		return newStr.split(",");
	}

	/**
	 * 
	 *
	 *	<P>字符串转换为年月日格式的字符串</p>
	 *
	 *	DT_S_CSRQ<BR>
	 *		满足格式：<BR>
	 *				1.YYYYMMDD<BR>
	 *				2.YYYYMMDDHHmmSS =>YYYY年MM月DD日<BR>
	 *	DT_S_DATE8<BR>
	 *		满足格式：<BR>
	 *				1.DATE = > YYYY-MM-DD HH:mm:SS => YYYY年MM月DD日<BR>
	 *	DT_S_DATE<BR>
	 *		满足格式：<BR>
	 *				1.DATE = > YYYY-MM-DD HH:mm:SS => 1985年07月08日01时01分01秒<BR>
	 *	DT_S_RQ14<BR>
	 *		满足格式：<BR>
	 *				1.YYYYMMDDHHmmSS =>1985年07月08日01时01分01秒<BR>
	 *				2.YYYY-MM-DD HH:mm:SS => 1985年07月08日01时01分01秒<BR>
	 *				3.YYYY-MM-DD => 1985年07月08日<BR>
	 *
	 * @param str String 待处理字符串
	 * @param format String 待处理字符串格式
	 * @return String 已格式化字符串
	 *
	 * @author fhway
	 * 2008-7-30 下午01:47:10
	 */
	public static String getDate(String str, String format) {

		if (str == null || str.equals("")) {
			return "";
		} else {
			if(format.equals("DT_S_CSRQ")){
				str = str.substring(0, 4) + "年"
				+ str.substring(4, 6) + "月"
				+ str.substring(6, 8) + "日";
			}else if(format.equals("DT_S_DATE8")){
				str = str.substring(0, 4) + "年"
				+ str.substring(5, 7) + "月"
				+ str.substring(8, 10) + "日" ;
			}else if(format.equals("DT_S_DATE")){
				str = str.substring(0, 4) + "年"
				+ str.substring(5, 7) + "月"
				+ str.substring(8, 10) + "日" 
				+ str.substring(11, 13) + "时"
				+ str.substring(14, 16) + "分"
				+ str.substring(17, 19) + "秒";
			}else if(format.equals("DT_S_RQ14")){
				char[] ch=str.toCharArray(); 
				if((Character.isDigit(ch[4])) ){
					if(str.length() > 12){
						str = str.substring(0, 4) + "年"
						+ str.substring(4, 6) + "月"
						+ str.substring(6, 8) + "日" 
						+ str.substring(8, 10) + "时"
						+ str.substring(10, 12) + "分"
						+ str.substring(12, 14) + "秒";
					}else if(str.length() == 12){
						str = str.substring(0, 4) + "年"
						+ str.substring(4, 6) + "月"
						+ str.substring(6, 8) + "日" 
						+ str.substring(8, 10) + "时"
						+ str.substring(10, 12) + "分";
					}else{
						str = str.substring(0, 4) + "年"
						+ str.substring(4, 6) + "月"
						+ str.substring(6, 8) + "日"; 
					}
				}else if(str.length() > 18){
					str = str.substring(0, 4) + "年"
					+ str.substring(5, 7) + "月"
					+ str.substring(8, 10) + "日"
					+ str.substring(11, 13) + "时"
					+ str.substring(14, 16) + "分"
					+ str.substring(17, 19) + "秒";
				}else{
					str = str.substring(0, 4) + "年"
					+ str.substring(5, 7) + "月"
					+ str.substring(8, 10) + "日";
				}
			}
		}
		return str;
	}

	/**
	 * 
	 * @param colstr
	 * @param metatype
	 * @return
	 */
	public static String getDateSQLByMetaType(String colstr, String metatype) {
		StringBuffer result = new StringBuffer();
		if (StringUtil.isBlank(metatype)) {
			return colstr;
		} else {
			if(metatype.equals("DT_S_CSRQ")){
				result.append("to_date(");
				result.append(colstr);
				result.append(", 'YYYYMMDD')");
			}else{
				return colstr;
			}
		}
		return result.toString();
	}

	/**
	 * 
	 * GetSQLToken getDate8
	 *
	 * DATE数据字符串转化为8位日期格式
	 * eg: DATE --> 2008年05月05日
	 * @param str
	 * @return
	 *
	 * @author fhway
	 * 2008-10-15 下午03:15:48
	 */
	public static String getDate8(String str) {

		if (str == null || str.equals("")) {
			return "";
		} else {
			str = str.substring(0, 4) + "年"
			+ str.substring(5, 7) + "月"
			+ str.substring(8, 10) + "日"; 
		}
		return str;
	}

	/**
	 * 
	 * GetSQLToken getMoney
	 *
	 *	字符串转换为货币表示(使用本地默认格式输出货币值)
	 *
	 * eg：123456  ---> 123,456
	 *
	 * @param str
	 * @return
	 *
	 * @author fhway
	 * 2008-8-29 下午02:50:29
	 */
	public static String getNumberFormat(String str, String format) {
		double dou = new Double(str).doubleValue();
		String numberString = "";
		if (str == null || str.equals("")) {
			return "";
		} else {
			if(format.equals("DT_S_MONEY")){
				/* 使用本地默认格式输出货币值 */ 
				NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
				numberString = numberFormat.format(dou);
			}else if(format.equals("DT_S_PERCENT")){
				/* 使用本地默认格式输出百分数 */ 
				NumberFormat numberFormat = NumberFormat.getPercentInstance();
				numberString = numberFormat.format(dou);
			}else if(format.equals("DT_S_NUMBER")){
				/* 使用本地默认格式输出数 */ 
				NumberFormat numberFormat = NumberFormat.getNumberInstance();
				numberString = numberFormat.format(dou);
			}
		}
		return numberString;
	}

	/**
	 * 查询子字符串出现的位置
	 * @param str 字符串
	 * @param regex 正则表达式
	 * @return
	 */
	public static List getIndexList(String str,String regex){
		List list = new ArrayList();
		Pattern pattern = Pattern.compile(regex); 
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()){
			list.add(matcher.start()+1);
		}
		return list;
	}


	/*将日期字符串解析为日期格式*/
	public static String getData2(String str){
		if (StringUtil.isBlank2(str)){
			return str;
		}
		str = str.trim();
		StringBuffer buf = new StringBuffer();
		/*类型:YYYYMMDD*/
		if (str.matches("\\d{8,}")){
			str = str.substring(0,8);
			buf.append(str.substring(0,4)+"年");
			buf.append(str.substring(4, 6)+"月");
			buf.append(str.substring(6)+"日");
			str = buf.toString();
		}
		else
			/*类型yyyy-MM-dd*/
			if (str.matches("\\d{4}-\\d{2}-\\d{2}")){
				buf.append(str.substring(0,4)+"年");
				buf.append(str.substring(5, 7)+"月");
				buf.append(str.substring(8)+"日");
				str = buf.toString();
			}
		return str;
	}

	public static String formatDateTime(Date basicDate, String strFormat) {
		if(basicDate==null){
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(strFormat);
		return df.format(basicDate);
	}
	public static String getLessLenStr(String str,int len){
		if (StringUtil.isBlank(str)){
			return " ";
		}
		if (str.length()>len){
			str = str.substring(0, len);
		}
		return str;
	}

	/**
	 * 将字符数组转换为格式如: ('aa','bb','cc')
	 * @param ids 数组为空返回''
	 * @return
	 */
	public static String getSqlFromArray(String[] ids){
		if (ids==null||ids.length==0){
			return "";
		}
		StringBuffer buf = new StringBuffer("(");
		for (int i=0,j=ids.length,k=j-1;i<j;i++){
			buf.append(StringUtil.toSqlParam(ids[i]));
			if (k!=0&&i!=k){
				buf.append(",");
			}
		}
		buf.append(")");
		return buf.toString();
	}

	/**
	 * 序列化字符数组，转换为字符: aaa,bbb,ccc
	 * @param arrays 字符数组
	 * @param regex 字符间隔符
	 * @return
	 */
	public static String serializeArray(String[] arrays,String regex){
		if (arrays==null){
			return "";
		}
		StringBuffer buf = new StringBuffer();
		for (int i=0,j=arrays.length,k=j-1;i<j;i++){
			buf.append(arrays[i]);
			if (k!=0&&i!=k){
				buf.append(regex);
			}
		}
		return buf.toString();
	}

	public static String getGklx(String bm){
		Map<String,String> gkMap = new HashMap<String,String>();
		gkMap.put("", "1");
		gkMap.put("RK_ZYDX", "2");
		gkMap.put("", "3");
		gkMap.put("RK_JGDX", "4");
		gkMap.put("RK_ZDRK", "5");
		gkMap.put("B_XDRY", "6");
		gkMap.put("B_SDRY", "7");
		return gkMap.get(bm);
	}


	/**
	 * 
	 * <li> 功能描述：根据身份证取得性别
	 * <li> 创建日期：Aug 20, 2009
	 * @return String
	 * @author Lee
	 */
	public static String getGenderBySfzh(String sfzh) {
		if(StringUtil.isBlank(sfzh))
			return null;
		if(sfzh.length() < 18)
			return null;
		String s = String.valueOf(sfzh.charAt(16));
		int i = Integer.parseInt(s);
		return (i % 2 == 0) ? "女" : "男";
	}
	public static String formatDateTime(String basicDate,String oldFormat, String newFormat) throws Exception {
		if(basicDate==null||"".equals(basicDate)){
			return "";
		}
		SimpleDateFormat odf = new SimpleDateFormat(oldFormat);
		SimpleDateFormat ndf = new SimpleDateFormat(newFormat);
		Date tmpDate = null;
		try {
			tmpDate = odf.parse(basicDate);
		} catch (Exception e) {
			throw e;
		}
		return ndf.format(tmpDate);
	}
	public static String getDateTime(String basicDate){
		try {
			if(basicDate.trim().length()>=14){
				return formatDateTime(basicDate.substring(0,14), "yyyyMMddHHmmss", "yyyy年MM月dd日 HH时mm分");
			}else if(basicDate.trim().length()>=8&&basicDate.trim().length()<14){
				return formatDateTime(basicDate.substring(0,8), "yyyyMMdd", "yyyy年MM月dd日");
			}
		}catch (Exception e) {
			return "";
		}
		return "";
	}
	public static String getDateTime2(String basicDate){
		try {
			if(basicDate.trim().length()>=14){
				return formatDateTime(basicDate.substring(0,14), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");
			}else if(basicDate.trim().length()>=8&&basicDate.trim().length()<14){
				return formatDateTime(basicDate.substring(0,8), "yyyyMMdd", "yyyy-MM-dd");
			}
		}catch (Exception e) {
			return "";
		}
		return "";
	}
	/**
	 * 将字符解析为字符数组
	 * @param str
	 * @param regex 字符间隔符
	 * @return
	 */
	public static String[] unSerializeArray(String str,String regex){
		if (StringUtil.isBlank2(str)){
			return new String[]{};
		}
		return str.split(regex);
	}

	public static String getBlankStr(Object obj){
		if (obj==null){
			return "";
		}
		return obj.toString();
	}

	/**
	 * 
	 * <p>创建时间：2012 2012-5-20 下午04:56:03</p>
	 * <p>说明:获取IP地址</p>
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() ==0 ||"unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() ==0 ||"unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() ==0 ||"unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
