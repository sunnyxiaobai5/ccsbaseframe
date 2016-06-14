package cn.ccsgroup.ccsframework.utils;

import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.utils.XmlUtils.java]  
 * @ClassName:    [XmlUtils]   
 * @Description:  [xml常用工具类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-2 下午5:20:36]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-2 下午5:20:36，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class XmlUtils {

	public static Document loadXMLClassPath(String xmlStr) throws  IOException, DocumentException {
		SAXReader reader = new SAXReader();     
		Document doc = reader.read(XmlUtils.class.getClassLoader().getResourceAsStream("/"+xmlStr));     
		return doc;
	}
}