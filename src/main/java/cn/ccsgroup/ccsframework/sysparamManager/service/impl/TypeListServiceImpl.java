package cn.ccsgroup.ccsframework.sysparamManager.service.impl;

import java.util.HashMap;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.ccsgroup.ccsframework.entity.TypeList;
import cn.ccsgroup.ccsframework.sysparamManager.dao.TypeListDao;
import cn.ccsgroup.ccsframework.sysparamManager.service.TypeListService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
@Service("TypeListService")
public class TypeListServiceImpl implements TypeListService {
	
	@Resource(name="TypeListDao")
	private TypeListDao typeListDao;

	public void setTypeListDao(TypeListDao typeListDao) {
		this.typeListDao = typeListDao;
	}
	
	/* (非 Javadoc)
	 * <p>Title: getTypeList</p>
	 * <p>Description: </p>
	 * @param page
	 * @return
	 * @see cn.ccsgroup.ccsframework.sysparamManager.service.TypeListService#getTypeList(cn.ccsgroup.ccsframework.utils.EasyUIPage)
	 */
	@Override
	public EasyUIPage getTypeList(EasyUIPage page){
		page.setRows(typeListDao.queryTypelist(page));
		page.setTotal(typeListDao.queryTypeListCounts());
		return page;
	}
	
	/* (非 Javadoc)
	 * <p>Title: saveTypeList</p>
	 * <p>Description: </p>
	 * @param typeList
	 * @return
	 * @see cn.ccsgroup.ccsframework.sysparamManager.service.TypeListService#saveTypeList(cn.ccsgroup.ccsframework.entity.TypeList)
	 */
	@Override
	public int saveTypeList(TypeList typeList) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeList", typeList);
//		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		int num = (Integer) typeListDao.insertForObject("TypeList.InsertTypeList", map);
		return num;
	}
	
	/* (非 Javadoc)
	 * <p>Title: queryTypeListById</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * @see cn.ccsgroup.ccsframework.sysparamManager.service.TypeListService#queryTypeListById(java.lang.Integer)
	 */
	@Override
	public TypeList queryTypeListById(Integer id){
		return  (TypeList) typeListDao.queryForObject("TypeList.queryTypeListById", id);
	}
	
	/* (非 Javadoc)
	 * <p>Title: updateTypeList</p>
	 * <p>Description: </p>
	 * @param typeList
	 * @return
	 * @see cn.ccsgroup.ccsframework.sysparamManager.service.TypeListService#updateTypeList(cn.ccsgroup.ccsframework.entity.TypeList)
	 */
	@Override
	public int updateTypeList(TypeList typeList) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeList", typeList);
//		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		int num = (Integer) typeListDao.update("TypeList.updateTypeList", typeList);
		return num;
	}
	
	/* (非 Javadoc)
	 * <p>Title: deleteTypeList</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * @see cn.ccsgroup.ccsframework.sysparamManager.service.TypeListService#deleteTypeList(java.lang.String)
	 */
	@Override
	public int deleteTypeList(String id) {
		// TODO Auto-generated method stub
		int i = typeListDao.delete("TypeList.deleteTypelist", id);
		return i;
	}
}
