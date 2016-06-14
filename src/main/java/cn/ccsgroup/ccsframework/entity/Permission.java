package cn.ccsgroup.ccsframework.entity;

import cn.ccsgroup.ccsframework.base.entity.BaseBean;

public class Permission extends BaseBean {
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -6406919493470186185L;
	private Integer id;
	private Integer nodeId;
	private Integer parentId;
	private Integer nodeLevel;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getNodeLevel() {
		return nodeLevel;
	}
	public void setNodeLevel(Integer nodeLevel) {
		this.nodeLevel = nodeLevel;
	}

}
