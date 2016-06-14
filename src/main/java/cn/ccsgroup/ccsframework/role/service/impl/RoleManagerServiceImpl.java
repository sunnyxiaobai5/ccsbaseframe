package cn.ccsgroup.ccsframework.role.service.impl;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.components.AppConst;
import cn.ccsgroup.ccsframework.entity.Role;
import cn.ccsgroup.ccsframework.entity.RolesPermissions;
import cn.ccsgroup.ccsframework.entity.UserManager;
import cn.ccsgroup.ccsframework.role.dao.RoleManagerDao;
import cn.ccsgroup.ccsframework.role.service.RoleManagerService;
import cn.ccsgroup.ccsframework.utils.EasyUIPage;
import cn.ccsgroup.ccsframework.utils.JsonUtil;
import cn.ccsgroup.ccsframework.utils.PageHelper;
import cn.ccsgroup.ccsframework.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("roleManagerService")
@Transactional
public class RoleManagerServiceImpl implements RoleManagerService {

	@Resource(name="roleManagerDao")
	private RoleManagerDao roleManagerDao;
	
	private Map<String,String> roleSysNodes ;
	private int level = 0 ;
	
	@Override
	public List<Role> roleList(PageHelper pager,SessionBean sessionBean) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pager", pager);
		if(sessionBean.getLoginid().equals("admin")){
			pager.setTotalCount((Integer) roleManagerDao.queryForObject("Role.queryCountAdmin",map), pager);
			return roleManagerDao.queryForList("Role.queryAdmin",map);
		}
		map.put("sessionBean", sessionBean);
		pager.setTotalCount((Integer) roleManagerDao.queryForObject("Role.queryCount",map), pager);
		return roleManagerDao.queryForList("Role.query",map);
	}

	/**
	@Override
	public int saveRole(Role role,List<TreeNodeBean> list,  List<TreeNodeBean> org, SessionBean sessionBean) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("entity", role);
		map.put("sessionBean", sessionBean);
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		int num = (Integer) roleManagerDao.insertForObject("Role.insertRole", map);
		map.put("roleId", num);
		
		for (int i = 0; i < sessionBean.getRoleId().size(); i++) {
			map.put("pid", sessionBean.getRoleId().get(i));
			roleManagerDao.insert("Role.insertRolesContrast", map);
		}
		TreeNodeBean tree = null;
//		if(org != null){ 
//			for (int i = 0; i < org.size(); i++) {
//				tree = org.get(i);
//				map.put("rr",tree);
//				roleManagerDao.insertForObject("Role.insertRolesRegion", map);
//			}
//		}
		if(list != null){
			int pid = 0;
			for (int i = 0; i < list.size(); i++) {
				tree = list.get(i);
				map.put("per", tree);
				pid = (Integer) roleManagerDao.insertForObject("Role.insertPermission", map);
				map.put("pid", pid);
				roleManagerDao.insert("Role.insertRolePer", map);
			}
		}
		return num;
	}*/

	@Override
	public List<TreeNode> sysTree(SessionBean sessionBean) {
		// TODO Auto-generated method stub
		return roleManagerDao.queryForList("Role.queryLogiSystem", sessionBean.getRegionCode());
	}

	@Override
	public List<TreeNode> sysTree(Integer id, SessionBean sessionBean) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId", id);
//		map.put("regionCode", sessionBean.getRegionCode());
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		List<TreeNode> list = roleManagerDao.queryForList("Role.queryLogiSystem", sessionBean.getRegionCode());
		roleSysNodes = (Map) roleManagerDao.getSqlMapClientTemplate().queryForMap("Role.queryRolePermission", map, "key","value");
		level = 0 ;
		setSysCheck(list,"");
		return list;
	}
	
	private void setSysCheck(List<TreeNode> list,String level2id){
		for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext();) {
			TreeNode treeNode = iterator.next();
			if (treeNode.getChildren() != null && treeNode.getChildren().size()>0){
				String temp2 = level2id;
				if(level == 2){
					temp2 =   treeNode.getAttributes().get("ID").toString();
				}
				level ++ ;
				setSysCheck(treeNode.getChildren(),temp2);
			}else{
				String temp = treeNode.getAttributes().get("ID").toString();
				if(!StringUtil.isBlank(level2id)){
					temp  = temp +":" +level2id;
				}
				if(roleSysNodes.containsKey(temp)){
					treeNode.setBoo(1);
				}
			}
		}
		level -- ;
	}
	@Override
	public List<TreeNode> queryUsersByRoleId(Integer id) {
		// TODO Auto-generated method stub
		List<TreeNode> list = new ArrayList<TreeNode>();
		TreeNode node = new TreeNode();
		Role role = (Role) roleManagerDao.queryForObject("Role.queryRoleById", id);
		node.setText(role.getrName());
		node.setChildren(roleManagerDao.queryForList("Role.queryUsersTree", id));
		list.add(node);
		return list;
	}

	@Override
	public int deleteRole(Integer roldId) {
		roleManagerDao.delete("Role.deleteRoleUsers", roldId);
		
		roleManagerDao.delete("Role.deleteRolesContrast",roldId);
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<RolesPermissions> rps = roleManagerDao.queryForList("Role.queryRPByRoleid",roldId);
		
		if(rps.size() != 0){
			roleManagerDao.delete("Role.deleteRPById",roldId);
			
			List<Integer> pids = new ArrayList<Integer>();
			for(int i = 0; i < rps.size(); i++){
				pids.add(rps.get(i).getPermissionid());
			}
			map.put("pids", pids);
			roleManagerDao.delete("Role.deletePermissionByids",map);//删除permissions表的角色信息
		}
		
		int i = roleManagerDao.delete("Role.deleteRole",roldId);
		
		return i;
	}

	/**
	 * 弃用
	 
	@Override
	public int updateRole(Role role ,List<TreeNodeBean> list , List<TreeNodeBean> org ,SessionBean sessionBean ,List<TreeNode> list2) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("entity", role);
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		int num = (Integer) roleManagerDao.update("Role.updateRole", role);
		map.put("roleId", role.getId());
		
		roleManagerDao.delete("Role.deletePermission", role.getId());
		roleManagerDao.delete("Role.deleteRolePer", role.getId());
		//roleManagerDao.delete("Role.deleteRoleRegion", role.getId());
		roleManagerDao.delete("Role.deleteRolesContrast", role.getId());
		
		for (int i = 0; i < sessionBean.getRoleId().size(); i++) {
			map.put("pid", sessionBean.getRoleId().get(i));
			roleManagerDao.insert("Role.insertRolesContrast", map);
		}
		
		TreeNode tree = null;
//		if(org != null){
//			for (int i = 0; i < org.size(); i++) {
//				tree = org.get(i);
//				map.put("rr",tree);
//				roleManagerDao.insertForObject("Role.insertRolesRegion", map);
//			}
//		}
		if(list2 != null){
			int pid = 0;
			for (int i = 0; i < list2.size(); i++) {
				tree = list2.get(i);
				map.putAll(tree.getAttributes());
				map.put("PARENTIDS", tree.getAttributes().get("PARENTID").toString());
				map.put("pid", map.containsKey(map.get("PARENTIDS"))?map.get(map.get("PARENTIDS")):0);
				pid = (Integer) roleManagerDao.insertForObject("Role.insertPermission", map);
				map.put(map.get("ID").toString(), pid);
				roleManagerDao.insert("Role.insertRolePer", map);
			}
		}
		return num;
	}*/

	@Override
	public Role queryRoleById(Integer id) {
		// TODO Auto-generated method stub
		return  (Role) roleManagerDao.queryForObject("Role.queryRoleById", id);
	}

	@Override
	public EasyUIPage roleList(EasyUIPage page, SessionBean sessionBean) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		map.put("orgid", sessionBean.getOrganization().getId());
		map.put("roleids", sessionBean.getRoleId());
		if(sessionBean.getLoginid().equals("admin")){
			page.setTotal((Integer) roleManagerDao.queryForObject("Role.queryCountAdmin",map));
			page.setRows(roleManagerDao.queryForList("Role.queryAdmin",map));
		}else{
			page.setTotal((Integer) roleManagerDao.queryForObject("Role.queryCount",map));
			page.setRows(roleManagerDao.queryForList("Role.query",map));
		}
		return page;
	}
	
	@Override
	public EasyUIPage roleListByOrgid(EasyUIPage page, SessionBean sessionBean,int orgid,String roleName) {
        Integer realOrgId = queryRealOrgId(orgid);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		map.put("orgid", realOrgId);
		map.put("roleids", sessionBean.getRoleId());
        map.put("roleName",roleName);
		
		page.setTotal((Integer) roleManagerDao.queryForObject("Role.queryCountAdminByOrgid",map));
		page.setRows(roleManagerDao.queryForList("Role.queryAdminByOrgid",map));

		return page;
	}

	@Override
	public List<UserManager> queryUsersListByRoleId(Integer id) {
		// TODO Auto-generated method stub
		return roleManagerDao.queryForList("Role.queryUsersByRoleId", id);
	}

	@Override
	public List<TreeNode> queryOrgUserTree(Integer roleid,
			SessionBean sessionBean) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId",roleid);
		map.put("parentId",26);
		map.put(AppConst.STATUS,AppConst.STATUS_ENABLE);
//		map.put("orgId", sessionBean.get);f
		List<TreeNode> list = roleManagerDao.queryForList("Role.selectOrgTreeByPid", map);
		setUserCheck(list, map);
		return list;
	}
	/**
	 * 
	 * @Title: setUserCheck
	 * @Description: TODO(设置选中用户)
	 * @param @param list
	 * @param @param map    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	private void setUserCheck(List<TreeNode> list ,Map<String,Object> map){
		TreeNode node = null;
		for (int i = 0; i < list.size(); i++) {
			node = list.get(i);
			if(node.getAttributes() != null && node.getBoo()==3){
				map.put("userId", node.getAttributes().get("ID"));
				node.setBoo(isChecked(node,map));
			}
			if(node.getChildren() != null && node.getChildren().size()>0){
				this.setUserCheck(node.getChildren(), map);
			}
		}
	}
	
	private int isChecked(TreeNode node,Map<String,Object> map){
		return ((Integer)roleManagerDao.queryForObject("Role.isInRole",map)) > 0 ? 1:0;
	}

	@Override
	public EasyUIPage queryOrgUserList(EasyUIPage page,Integer orgId,Integer roleId) {
		List<UserManager> checkusers = this.queryUsersListByRoleId(roleId);//改角色已有的人员
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		map.put("orgid",orgId);
		
		page.setTotal((Integer) roleManagerDao.queryForObject("Role.queryCountOrgUserList",map));
		List<UserManager> users = roleManagerDao.queryForList("Role.queryOrgUserList",map);
		if(checkusers.size() != 0){
			for(int i = 0; i < users.size(); i++){
				for(int j = 0; j < checkusers.size(); j++){
					if(users.get(i).getId().equals(checkusers.get(j).getId())){
						users.get(i).setFlag("true");
					}
				}
			}
		}
		page.setRows(users);
		return page;
	}
	
	//带条件
	public EasyUIPage queryOrgUserList(EasyUIPage page,Integer orgid,Integer roleid,String userName,String loginid) {
		List<UserManager> checkusers = this.queryUsersListByRoleId(roleid);//该角色已有的人员
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orgid",orgid);
		map.put("userName",userName);
		map.put("loginid",loginid);
		map.put("begin", page.getBegin());
		map.put("end", page.getEnd());
		
		page.setTotal((Integer) roleManagerDao.queryForObject("Role.queryCountOrgUserList",map));
		List<UserManager> users = roleManagerDao.queryForList("Role.queryOrgUserList",map);
		if(checkusers.size() != 0){
			for(int i = 0; i < users.size(); i++){
				for(int j = 0; j < checkusers.size(); j++){
					if(users.get(i).getId().equals(checkusers.get(j).getId())){
						users.get(i).setFlag("true");
					}
				}
			}
		}
		page.setRows(users);
		return page;
	}

	@Override
	public int deleteRoleUser(Integer roleId, Integer userId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId",roleId);
		map.put("userId",userId);
		return roleManagerDao.update("Role.delRoleUser",map);
	}

	@Override
	public UserManager queryUserById(Integer userId) {
		// TODO Auto-generated method stub
		return (UserManager) roleManagerDao.queryForObject("Role.queryUserById",userId);
	}

	@Override
	public void saveRoleUser(Integer roleId, String[] ids,String[] currentPageIds) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId", roleId);
        map.put("currentPageIds",currentPageIds);
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);

        this.roleManagerDao.delete("Role.deleteUsersRoles",map);
        for(int k = 0; k < ids.length; k++){
            map.put("userId", ids[k]);
            roleManagerDao.insert("Role.insertRoleUser", map);
		}

//		List<String> idss = new ArrayList<String>();
//		List<String> uids = new ArrayList<String>();
//		List<UserManager> users = this.queryUsersListByRoleId(roleId);//该角色已有的人员
//
//
//		for(int i = 0; i < ids.length; i++){
//			idss.add(ids[i]);
//		}
//		for(int j = 0; j < users.size(); j++){
//			uids.add(users.get(j).getId().toString());
//		}
//		//比较
//		for(int k = 0; k < idss.size(); k++){
//			Boolean flag1 = uids.contains(idss.get(k));
//			if(!flag1){
//				map.put("userId", ids[k]);
//				roleManagerDao.insert("Role.insertRoleUser", map);
//			}
//		}
//
//		for(int z = 0; z < uids.size(); z++){
//			Boolean flag2 = idss.contains(uids.get(z));
//			if(!flag2){
//				deleteRoleUser(roleId,users.get(z).getId());
//			}
//		}
	}

	/*@Override
	public int updateUserPerm(Role role, List<TreeNodeBean> list, Integer userId,List<TreeNodeBean> org,SessionBean sessionBean) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("entity", role);
		map.put("roleId",role.getId());
		map.put("userId",userId);
		map.put("sessionBean", sessionBean);
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		
		roleManagerDao.update("Role.delRoleUser",map);
		
		int num = (Integer) roleManagerDao.insertForObject("Role.insertRole", map);
		map.put("roleId", num);
		roleManagerDao.insert("Role.insertRoleUser", map);
		
		for (int i = 0; i < sessionBean.getRoleId().size(); i++) {
			map.put("pid", sessionBean.getRoleId().get(i));
			roleManagerDao.insert("Role.insertRolesContrast", map);
		}
		TreeNodeBean tree = null;
//		if(org != null){
//			for (int i = 0; i < org.size(); i++) {
//				tree = org.get(i);
//				map.put("rr",tree);
//				roleManagerDao.insertForObject("Role.insertRolesRegion", map);
//			}
//		}
		if(list != null){
			int pid = 0;
			for (int i = 0; i < list.size(); i++) {
				tree = list.get(i);
				map.put("per", tree);
				pid = (Integer) roleManagerDao.insertForObject("Role.insertPermission", map);
				map.put("pid", pid);
				roleManagerDao.insert("Role.insertRolePer", map);
			}
		}
		return num;
	}*/

	@Override
	public Map<String, String> queryRoleUrl(SessionBean sessionBean) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.putAll(roleManagerDao.getSqlMapClientTemplate().queryForMap("Role.queryRoleFunc", sessionBean, "key", "value"));
		map.putAll(roleManagerDao.getSqlMapClientTemplate().queryForMap("Role.queryRoleOps", sessionBean, "key", "value"));
		return map;
	}

//	@Override
//	public Map<String, String> queryModOps(Integer[] modIds) {
//		// TODO Auto-generated method stub
//		return roleManagerDao.getSqlMapClientTemplate().queryForMap("Role.queryModOps", modIds, "key", "value");
//	}

	@Override
	public int queryPriOps(String opsUrl, SessionBean sessionBean) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("opsUrl", opsUrl);
		map.put("sessionBean", sessionBean);
		return (Integer) roleManagerDao.queryForObject("Role.queryPriOps", map);
	}

	
	//lp3  修改角色
	public int updateTheRole(Role role, SessionBean sessionBean,List<Ztree> list){
		Map<String,Object> map = new HashMap<String,Object>();
		//先修改角色基本信息
		int num = (Integer) roleManagerDao.update("Role.updateRole", role);
		
		List<RolesPermissions> rps = roleManagerDao.queryForList("Role.queryRPByRoleid",role.getId());
		//该角色有权限就删
		if(rps.size() != 0){
			//删除rolespermissions表的角色信息
			roleManagerDao.delete("Role.deleteRPById",role.getId());
			
			List<Integer> pids = new ArrayList<Integer>();
			for(int i = 0; i < rps.size(); i++){
				pids.add(rps.get(i).getPermissionid());
			}
			map.put("pids", pids);
			roleManagerDao.delete("Role.deletePermissionByids",map);//删除permissions表的角色信息
		}
		//权限配置不为空时添加
		if(list.size() != 0){
			//新增permissions表
			List<Integer> pIdList = savePermissionsUtil(list);
			
			 //新增rolespermissions表
			saveRolePermissionUtil(pIdList,role.getId());
		}
		return num;
	}		
	
	
	//lp3 新增角色
	public int saveRole(String orgid,List<Ztree> ztreeList,SessionBean sessionBean,Role role){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("role", role);
		map.put("sessionBean", sessionBean);
		map.put("orgId", orgid);
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		int newRoleId = (Integer) roleManagerDao.insertForObject("Role.insertRole", map);//新增角色
		map.put("roleId", newRoleId);
		//新增rolescontrast表信息
		for (int i = 0; i < sessionBean.getRoleId().size(); i++) {
			map.put("pid", sessionBean.getRoleId().get(i));
			roleManagerDao.insert("Role.insertRolesContrast", map);
		}
		
		//权限配置不为空时添加
		if(ztreeList.size() != 0){
			//新增permissions表
			List<Integer> pIdList = savePermissionsUtil(ztreeList);
			
			 //新增rolespermissions表
			saveRolePermissionUtil(pIdList,newRoleId);
		}
		
		return newRoleId;
	}
	
	//lp3   新增rolespermissions表Util
	public void saveRolePermissionUtil(List<Integer> pIdList,int roleId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("roleId", roleId);
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		for(int b = 0; b < pIdList.size(); b++){
			map.put("perId", pIdList.get(b));
			roleManagerDao.insert("Role.insertRolePer", map);
		}
	}
	
	//lp3  新增permissions表Util
	public List savePermissionsUtil(List<Ztree> ztreeList){
		List<Integer> pIdList = new ArrayList<Integer>();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		
		for(int i = 0; i < ztreeList.size(); i++){
			
			Ztree ztree = ztreeList.get(i);
			
			if(ztree.getNodelevel().equals("0")){//系统
				map.put("parentid","0");
				tempMap = new HashMap<String, Object>();
			}else{
				String pidStr = ztree.getParentId();
				int startIndex = pidStr.lastIndexOf("_");
				String key = pidStr.substring(startIndex + 1,pidStr.length());
				int nodeLevel = Integer.parseInt(ztree.getNodelevel());
				key += "_" + (nodeLevel - 1);
				map.put("parentid",tempMap.get(key));
			}
			
			map.put("text", ztree.getText());
			map.put("tid", ztree.getTid());
			if(ztree.getParentId() == null){
				map.put("pid", 0);
			}else{
				map.put("pid", ztree.getParentId());
			}
			map.put("nodeid", ztree.getId());
			map.put("nodelevel", ztree.getNodelevel());
			map.put("status", 1);
			
			int permissionId = (Integer) roleManagerDao.insertForObject("Role.insertPermission", map);
			tempMap.put(ztree.getId() + "_" + ztree.getNodelevel(), permissionId);
			
			pIdList.add(permissionId);
		}
		return pIdList;
	}

	@Override
	public int updateUserPerm(Role role, List<TreeNode> list, Integer userId,
			SessionBean sessionBean) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("entity", role);
		map.put("roleId",role.getId());
		map.put("userId",userId);
		map.put("sessionBean", sessionBean);
		map.put(AppConst.STATUS, AppConst.STATUS_ENABLE);
		
		roleManagerDao.update("Role.delRoleUser",map);
		
		int num = (Integer) roleManagerDao.insertForObject("Role.insertRole", map);
		map.put("roleId", num);
		roleManagerDao.insert("Role.insertRoleUser", map);
		
		savePermisson(list,map,"");
//		TreeNode tree = null;
//		if(list != null){
//			int pid = 0;
//			for (int i = 0; i < list.size(); i++) {
//				tree = list.get(i);
//				map.putAll(tree.getAttributes());
//				map.put("PARENTIDS", tree.getAttributes().get("NODEPID").toString());
//				map.put("pid", map.containsKey(map.get("PARENTIDS"))?map.get(map.get("PARENTIDS")):0);
//				pid = (Integer) roleManagerDao.insertForObject("Role.insertPermission", map);
//				map.put(map.get("ID").toString(), pid);
//				map.put("perId", pid);
//				roleManagerDao.insert("Role.insertRolePer", map);
//			}
//		}
		return num;
	}

	public void savePermisson(List<TreeNode> list,Map<String,Object> map , String nodepid){
		for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext();) {
			TreeNode treeNode = (TreeNode) iterator.next();
			map.putAll(treeNode.getAttributes());
			/**拼接 NODEPIDS 组成 0-001-002-003  第一步*/
			map.put("NODEPIDS", (nodepid.equals("")?"":(nodepid))+ (nodepid.endsWith(map.get("NODEPID").toString())?"":"-"+map.get("NODEPID").toString()));
			map.put("PARENTIDS", treeNode.getAttributes().get("NODEPID").toString());
			map.put("pid", map.containsKey(map.get("PARENTIDS"))?map.get(map.get("PARENTIDS")):0);
			/**拼接 NODEPIDS 组成 0-001-002-003  第二步*/
			if(map.get("NODEPIDS").toString().startsWith("-"))
				map.put("NODEPIDS", map.get("NODEPIDS").toString().substring(1, map.get("NODEPIDS").toString().length()));
			else if(map.get("NODEPIDS").toString().startsWith("0-"))
				map.put("NODEPIDS", map.get("NODEPIDS").toString().substring(2, map.get("NODEPIDS").toString().length()));
			int pid = (Integer) roleManagerDao.insertForObject("Role.insertPermission", map);
			map.put(map.get("ID").toString(), pid);
			map.put("perId", pid);
			roleManagerDao.insert("Role.insertRolePer", map);
			if(treeNode.getChildren() != null){
				/**拼接 NODEPIDS 组成 0-001-002-003 第三步*/
				nodepid  = map.get("NODEPIDS").toString();
				treeNode.setChildren(JsonUtil.jsonToTreeNodeList(treeNode.getChildren()));
				savePermisson(treeNode.getChildren(),map,nodepid);
			}
			/**拼接 NODEPIDS 组成 0-001-002-003 第四步*/
			if(StringUtil.isNotBlank(nodepid) && nodepid.lastIndexOf("-")>0 && nodepid.endsWith(map.get("NODEPID").toString()))
				nodepid = nodepid.substring(0, nodepid.lastIndexOf("-"));
		}
	}

	@Override
	public boolean queryRoleName(String getrName) {
		int a = (Integer) roleManagerDao.queryForObject("Role.queryRoleName", getrName);
		return (a)>0;
	}

    /**
     * 如果传入的是虚拟组织机构，就找上级组织机构，直到找到真实的组织机构
     * 如果传入的是真实的组织机构，则返回自身id
     * @param orgId
     * @return
     */
    public Integer queryRealOrgId(Integer orgId){
        boolean isVirtual = isVirtual(orgId);
        if(!isVirtual){
            return orgId;
        }else{
            orgId = queryParentRealOrgId(orgId);
            return orgId;
        }

    }

    /**
     * 往上级查询，直到找到第一个真实的组织机构
     * @param orgId
     * @return
     */
    private Integer queryParentRealOrgId(Integer orgId) {
        Integer parentOrgId = queryParentOrgId(orgId);
        if (isVirtual(parentOrgId)) {
            parentOrgId = queryParentRealOrgId(parentOrgId);
        }
        return parentOrgId;
    }

    /**
     * 查询父组织机构的id
     * @param orgId
     * @return
     */
    private Integer queryParentOrgId(Integer orgId) {
        Integer parentOrgId = (Integer) this.roleManagerDao.queryForObject("Role.queryParentOrgId", orgId);
        return parentOrgId;
    }

    /**
     * 检查组织机构是否是虚拟组织机构
     * @param orgId
     * @return
     */
    private boolean isVirtual(Integer orgId) {
        Integer isVirtual = (Integer) this.roleManagerDao.queryForObject("Role.isVirtual",orgId);
        if (1==isVirtual){
            return true;
        }else{
            return false;
        }
    }
}
