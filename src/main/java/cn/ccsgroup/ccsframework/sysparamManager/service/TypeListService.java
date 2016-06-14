package cn.ccsgroup.ccsframework.sysparamManager.service;




import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.entity.TypeList;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;


@TypeAnnotation("通用标准设置")
public interface TypeListService {
	/**
	 * @Title: getTypeList
	 * @Description: 获取主页面列表数据
	 * @param @param page
	 * @param @return    设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	public EasyUIPage getTypeList(EasyUIPage page);
	
	/**
	 * @Title: saveTypeList
	 * @Description: 新增通用标准
	 * @param @param typelist
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	@MethodAnnotation("新增通用标准")
	public int saveTypeList(TypeList typelist);
	
	/**
	 * @Title: queryTypeListById
	 * @Description: 修改通用标准时根据id查询数据
	 * @param @param id
	 * @param @return    设定文件
	 * @return TypeList    返回类型
	 * @throws
	 */
	public TypeList queryTypeListById(Integer id);
	
	/**
	 * @Title: updateTypeList
	 * @Description: 修改通用标准
	 * @param @param typelist
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	@MethodAnnotation("修改通用标准")
	public int updateTypeList(TypeList typelist);
	
	/**
	 * @Title: deleteTypeList
	 * @Description: 批量删除通用标准
	 * @param @param id
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	 */
	@MethodAnnotation("删除通用标准")
	public int deleteTypeList(String id);
}
