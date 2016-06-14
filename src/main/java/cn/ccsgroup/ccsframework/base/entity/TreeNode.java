package cn.ccsgroup.ccsframework.base.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.base.entity.TreeNode.java]  
 * @ClassName:    [TreeNode]   
 * @Description:  [树形节点]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-27 下午2:59:53]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-27 下午2:59:53，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class TreeNode implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -3244129026028081904L;
	static final String STATE_OPEN = "open";
	private String id;
	private String text;
	private String state = "";
	private boolean checked = false;
	private int boo = 0 ; //0 false 1 true
	private List<TreeNode> children;
	private Map<String, Object> attributes;
	private String iconCls;
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean getChecked() {
		return checked;
	}
	public List<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	public void setBoo(int boo) {
		if(boo ==0) this.checked = false;
		else if (boo ==1) this.checked = true;
		this.boo = boo;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public int getBoo() {
		return boo;
	}
}
