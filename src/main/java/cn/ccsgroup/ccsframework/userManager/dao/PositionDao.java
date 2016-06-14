package cn.ccsgroup.ccsframework.userManager.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.ccsgroup.ccsframework.base.dao.BaseDaoImpl;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Position;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.userManager.dao.PositionDao.java]  
 * @ClassName:    [PositionDao]   
 * @Description:  [职位持久层]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2013-12-30 下午03:51:47]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2013-12-30 下午03:51:47]   
 * @UpdateRemark: [新建]  
 * @Version:      [v1.0] 
 */
@Repository("PositionDao")
public class PositionDao extends BaseDaoImpl {

	
	/**
	 * @Title: addPosition
	 * @Description: 新增职位
	 *  @param position 职位对象
	 * @return boolean    返回类型
	 * @throws
	 */
	public void addPosition(Position position){
		position.setStatus(AppConst.STATUS_ENABLE);
		this.getSqlMapClientTemplate().insert("Position.insertPosition",position);
	}
	/**
	 * @Title: updatePosition
	 * @Description: 修改职位
	 * @param position 职位对象
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean updatePosition(Position position){
		int c = (Integer)this.getSqlMapClientTemplate().update("Position.updatePosition",position);
		return  c > 0 ? true : false;
	}
	/**
	 * @Title: deletePosition
	 * @Description: 删除职位
	 * @param id 职位ID
	 * @return boolean    返回类型
	 * @throws
	 */
	public boolean deletePosition(Integer id){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", AppConst.STATUS_DISABLE);
		int c = (Integer)this.getSqlMapClientTemplate().delete("Position.deletePosition",map);
		return  c > 0 ? true : false;
	}
	/**
	 * @Title: queryPosition
	 * @Description: 查询职位
	 * @param orgid 组织ID
	 * @return List<Position>    返回类型
	 * @throws
	 */
	public List<Position> queryPositions(Integer orgid,EasyUIPage page){
		HashMap<String, Object> map = new HashMap<String, Object> ();
		map.put("orgid", orgid);
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		return this.getSqlMapClientTemplate().queryForList("Position.queryPositions",map);
	}
	/**
	 * @Title: queryPositionsCounts
	 * @Description: 通过组织机构ID查询职位总条数
	 *  @param orgid
	 *  @return   设定文件
	 * @return Integer    返回类型
	 * @throws
	 */
	public Integer queryPositionsCounts(Integer orgid){
		
		return (Integer)this.getSqlMapClientTemplate().queryForObject("Position.queryPositionsCounts",orgid);
	}
	
	/**
	 * @Title: queryPosition
	 * @Description: TODO(通过ID查询职位)
	 *  @param id
	 *  @return   设定文件
	 * @return Position    返回类型
	 * @throws
	 */
	public Position queryPosition(Integer id){
		
		return (Position)this.getSqlMapClientTemplate().queryForObject("Position.queryPosition", id);
	}
}
