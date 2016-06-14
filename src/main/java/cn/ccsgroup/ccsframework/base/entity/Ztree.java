package cn.ccsgroup.ccsframework.base.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.base.entity.Ztree.java]  
 * @ClassName:    [Ztree]   
 * @Description:  [一句话描述该类的功能]   
 * @Author:       [linpeng]   
 * @CreateDate:   [2014-8-23 下午4:32:28]   
 * @UpdateUser:   [lenovo_e430(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2014-8-23 下午4:32:28，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [说明本次修改内容,(如多次修改保留历史记录，增加修改记录)]  
 * @Version:      [v1.0]
 */
public class Ztree implements Serializable{

	private String id;
	private String tid;
	private String text;
	private String parentId;
	
	//树是否打开
	private String open;
	//复选框是否被选中
	private String checked;
	//表示该数据(系统,模块,功能,操作)
	private String nodelevel;
	//表示该数据pid(系统,模块,功能,操作)
	private String nodepid;
	//拖拽时不能成为父节点
	private Boolean dropInner;
	//标识
	private String flag;
	//图标
	private String icon;
	
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getNodelevel() {
		return nodelevel;
	}
	public void setNodelevel(String nodelevel) {
		this.nodelevel = nodelevel;
	}
	public String getNodepid() {
		return nodepid;
	}
	public void setNodepid(String nodepid) {
		this.nodepid = nodepid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Boolean getDropInner() {
		return dropInner;
	}
	public void setDropInner(Boolean dropInner) {
		this.dropInner = dropInner;
	}
}
