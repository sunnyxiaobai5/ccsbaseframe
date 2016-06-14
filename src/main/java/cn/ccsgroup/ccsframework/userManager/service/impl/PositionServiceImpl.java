package cn.ccsgroup.ccsframework.userManager.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ccsgroup.ccsframework.entity.Position;
import cn.ccsgroup.ccsframework.userManager.dao.PositionDao;
import cn.ccsgroup.ccsframework.userManager.service.PositionService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;

/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.userManager.service.impl.PositionServiceImpl.java]  
 * @ClassName:    [PositionServiceImpl]   
 * @Description:  [职位业务层实现类]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2013-12-30 下午04:23:48]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2013-12-30 下午04:23:48]   
 * @UpdateRemark: [新建]  
 * @Version:      [v1.0] 
 */
@Service("PositionService")
@Transactional
public class PositionServiceImpl implements PositionService{

	private PositionDao positionDao;
	@Resource(name="PositionDao")
	public void setPositiondao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	@Override
	public boolean savePosition(Position position) {
		positionDao.addPosition(position);
		return false;
	}

	@Override
	public boolean deletePosition(Integer id) {
		return positionDao.deletePosition(id);
	}

	@Override
	public EasyUIPage queryPositions(Integer orgid,EasyUIPage page) {
		page.setRows(positionDao.queryPositions(orgid,page));
		page.setTotal(positionDao.queryPositionsCounts(orgid));
		return page;
	}

	@Override
	public boolean updatePosition(Position position) {
		
		return positionDao.updatePosition(position);
	}

	@Override
	public Position queryPosition(Integer id) {
		return positionDao.queryPosition(id);
	}

}
