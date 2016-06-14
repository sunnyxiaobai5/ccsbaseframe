package cn.ccsgroup.ccsframework.userManager.service;


import cn.ccsgroup.ccsframework.annotation.MethodAnnotation;
import cn.ccsgroup.ccsframework.annotation.TypeAnnotation;
import cn.ccsgroup.ccsframework.entity.Position;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;


/******************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.userManager.service.PositionService.java]  
 * @ClassName:    [PositionService]   
 * @Description:  [职位业务层接口]   
 * @Author:       [安宗源]   
 * @CreateDate:   [2013-12-30 下午04:20:02]   
 * @UpdateUser:   [安宗源]   
 * @UpdateDate:   [2013-12-30 下午04:20:02]   
 * @UpdateRemark: [新建]  
 * @Version:      [v1.0] 
 */
@TypeAnnotation("职位管理")
public interface PositionService {

	/**
	 * @Title: addPosition
	 * @Description: 新增职位
	 *  @param position 职位对象
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("新增职位")
	public boolean savePosition(Position position);
	/**
	 * @Title: updatePosition
	 * @Description: 修改职位
	 *  @param position 职位对象
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("修改职位")
	public boolean updatePosition(Position position);
	/**
	 * @Title: deletePosition
	 * @Description: 删除职位
	 *  @param id 职位ID
	 * @return boolean    返回类型
	 * @throws
	 */
	@MethodAnnotation("删除职位")
	public boolean deletePosition(Integer id);

	/**
	 * @Title: queryPositions
	 * @Description: TODO(通过组织ID查询职位列表)
	 *  @param orgid 组织机构ID
	 *  @param page 翻页对象
	 *  @return   设定文件
	 * @return EasyUIPage    返回类型
	 * @throws
	 */
	public EasyUIPage queryPositions(Integer orgid,EasyUIPage page);
	
	/**
	 * @Title: queryPosition
	 * @Description: TODO(通过ID查询职位)
	 *  @param id 职位ID
	 *  @return   设定文件
	 * @return Position    返回类型
	 * @throws
	 */
	public Position queryPosition(Integer id);
}
