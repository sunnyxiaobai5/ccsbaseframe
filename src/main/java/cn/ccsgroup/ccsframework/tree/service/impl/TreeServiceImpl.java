package cn.ccsgroup.ccsframework.tree.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.ccsgroup.ccsframework.base.entity.SessionBean;
import cn.ccsgroup.ccsframework.base.entity.TreeNode;
import cn.ccsgroup.ccsframework.base.entity.Ztree;
import cn.ccsgroup.ccsframework.entity.Org;
import cn.ccsgroup.ccsframework.entity.Permission;
import cn.ccsgroup.ccsframework.entity.Position;
import cn.ccsgroup.ccsframework.tree.dao.TreeDao;
import cn.ccsgroup.ccsframework.tree.service.TreeService;

@Service("treeService")
public class TreeServiceImpl implements TreeService {

	@Resource(name="treeDao")
	private TreeDao treeDao;
	
	private Map<String,String> roleSysNodes ;
	/**
	 * 获取组织机构树
	 * icon-region   区域      icon-department  单位部 icon-user
	 */
	public List<TreeNode> findOrgAndRegionTree(Integer userId , Integer childRows) {
		List<TreeNode> vList = (List<TreeNode>)treeDao.queryForList("Tree.selectRegionListByUserId", userId);
		List<TreeNode> regionList = createRegionList(childRows, vList);
		for(TreeNode node : regionList)
		{
			String regionCode = node.getAttributes().get("REGIONCODE").toString();
			List<TreeNode> orgList = treeDao.queryForList("Tree.selectRegionAndOrgTree", regionCode);
			List<TreeNode> children = node.getChildren();
			children.addAll(0, orgList);
		}
		return regionList; 
	}
	
	/**
	 * 
	 * @Title: createRegionList
	 * @Description: TODO(封装区域)
	 * @param @param value    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private List<TreeNode> createRegionList(Integer index ,List<TreeNode> value)
	{
		// 封装组织机构到区域
		for(TreeNode node : value)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("AID", node.getAttributes().get("AID")); // 区域表Id
			List<TreeNode> children = (List<TreeNode>)treeDao.queryForList("Tree.selectRegionListByRid", map);
			node.setChildren(children);
		}
		if(index == null || index == 0 )
		{
			return value;
		}
		return createRegionList(index -- , value);
	}
	
	/**
	 * 获取标准组织机构树
	 */
	public List<TreeNode> findCurrentOrgTree(Integer userId, Integer childRows) {
		List<TreeNode> list = treeDao.findCurrentOrg(userId);
		return packageChildList(childRows, list , null);
	}
	
	/**
	 * @Title: packageChildList
	 * @Description: TODO(封装子类组织机构)
	 * @param @param index
	 * @param @param value
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	private List<TreeNode> packageChildList(Integer index, List<TreeNode> value, List<String> codeList)
	{
		for(TreeNode node : value)
		{
			Integer pid = Integer.valueOf(node.getAttributes().get("ID").toString());
			String orgCode = node.getAttributes().get("ID").toString(); // 设置选中
			if(codeList != null && codeList.size() > 0)
			{
				if(codeList.indexOf(orgCode) != -1) {
					node.setBoo(1); 
					codeList.remove(orgCode);
				}else node.setBoo(0);
			}
			if(index < 0)
			{
				List<TreeNode> children = treeDao.findAllChildByPid(pid);
				if(codeList != null && codeList.size() > 0){
					for(TreeNode treeNode : children){
						setListBoo(treeNode, codeList);
					}
				}
				node.setChildren(children);
			}else
				node.setChildren(treeDao.findChildOrgByPid(pid));
		}
		if(index  == null || index == 0 || index < 0)
			return value;
		return packageChildList(index -- , value , codeList);
	}
	
	/**
	 * @Title: setListBoo
	 * @Description: TODO(判断选中)
	 * @param @param vList
	 * @param @param codeList
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	private void setListBoo(TreeNode node, List<String> codeList)
	{
		String orgCode = node.getAttributes().get("ID").toString(); // 设置选中
		if(codeList.indexOf(orgCode) != -1) {
			node.setBoo(1); 
		}else node.setBoo(0);
		if(node.getChildren()!=null && !node.getChildren().isEmpty() && node.getChildren().size()>0)
		{
			for(TreeNode keyNode : node.getChildren())
			{
				setListBoo(keyNode, codeList);
			}
		}
	}
	
	
	@Override
	public List<TreeNode> findRegionTree(Integer code) {
		// TODO Auto-generated method stub
		return treeDao.queryForList("Tree.selectRegionTree", code);
	}

	@Override
	public List<TreeNode> sysTree(SessionBean sessionBean) {
		// TODO Auto-generated method stub
//		return treeDao.queryForList("Tree.queryRolePermissionTree", sessionBean.getRoleId());
		if(sessionBean.getLoginid().endsWith("admin")) return treeDao.queryForList("Tree.queryAdminLogiSystem");
		return new InnerEvenIterator().removeDuplicate((treeDao.queryForList("Tree.queryRolePermissionTree", sessionBean.getRoleId())));
	}
	
	class InnerEvenIterator{
		private Map<String,Object> sysNodes = new HashMap<String,Object>();
		private Map<String,Object> modNodes = new HashMap<String,Object>();
		
		/**系统树剔重*/
		private List<TreeNode> removeDuplicate(List<TreeNode> list){
			List<TreeNode> result = new ArrayList<TreeNode>();
			TreeNode node = null ;
			String tempKey = "";
			for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext();) {
				node = iterator.next();
				tempKey = node.getAttributes().get("KEY").toString()+"-"+node.getAttributes().get("NODEID").toString();
//				if(node.getAttributes().get("NODELEVEL").toString().equals("0"))modNodes.clear();
				if(!sysNodes.containsKey(tempKey)&&!modNodes.containsKey(tempKey)){
					if(node.getAttributes().get("NODELEVEL").toString().equals("0"))
						sysNodes.put(tempKey,node);
					else 
						modNodes.put(tempKey,node);
					result.add(node);
					if(node.getChildren() != null && !node.getChildren().isEmpty()){
						node.setChildren(removeDuplicate(node.getChildren()));
					}
				}else{
					TreeNode node2 = node.getAttributes().get("NODELEVEL").toString().equals("0")?(TreeNode) sysNodes.get(tempKey):(TreeNode) modNodes.get(tempKey);
					if(node.getChildren() != null && !node.getChildren().isEmpty()){
						List<TreeNode> list2 = removeDuplicate(node.getChildren());
						for (Iterator<TreeNode> iterator2 = list2.iterator(); iterator2.hasNext();) {
							TreeNode object = iterator2.next();
//							if(!sysNodes.containsKey(node.getAttributes().get("NODEID").toString())&&!modNodes.containsKey(node.getAttributes().get("NODEID").toString())){
								node2.getChildren().add(object);
//							}
						}
					}
				}
			}
			return result;
		}
	}
	

	@Override
	public List<TreeNode> sysTree(Integer roleId, SessionBean sessionBean) {
		// TODO Auto-generated method stub
		List<TreeNode> list = null ;
		if(sessionBean.getLoginid().endsWith("admin")) list = treeDao.queryForList("Tree.queryAdminLogiSystem");
		else list = treeDao.queryForList("Tree.queryRolePermissionTree", sessionBean.getRoleId());
		list = new InnerEvenIterator().removeDuplicate(list);
		roleSysNodes = treeDao.getSqlMapClientTemplate().queryForMap("Tree.queryRolePermission", roleId, "key","value");
		setSysCheck(list);
		return list;
	}

	public List<TreeNode> findChoosedRolePermissions(List<Integer> roleIds)
	{
		List<TreeNode> list = null ;
		list = treeDao.queryForList("Tree.queryRolePermissionTree", roleIds);
		list =  new InnerEvenIterator().removeDuplicate(list);
		return list;
	}
	
	
	@Override
	public List<TreeNode> findRegionTreeByRoleId(Integer roldId , Integer userId) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("roldId", roldId);
		return treeDao.queryForList("Tree.selectRegionTreeByUserId", userId);
	}

	@Override
	public List<TreeNode> findRegionTreeByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return treeDao.queryForList("Tree.selectRegionTreeByUserId", userId);
	}
	
	@Override
	public List<TreeNode> findSysTreeByOrgId(String[] orgIds) {
		// TODO Auto-generated method stub
		List<String> regions = treeDao.queryForList("Tree.queryOrgRegion", orgIds);
		return treeDao.queryForList("Tree.queryLogiSystem", regions);
	}

	@Override
	public List<TreeNode> findOrgTreeByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return treeDao.queryForList("Tree.queryOrgTreeByUserId", userId);
	}

	@Override
	public List<TreeNode> findCurrentOrgTree(Integer userId, Integer roleId,
			int childRows) {
		List<TreeNode> list = treeDao.findCurrentOrg(userId);
		List<String> roleOrg = treeDao.queryForList("Tree.queryOrgTreeByRoleId", roleId);
		return packageChildList(childRows, list ,roleOrg);
	}

	@Override
	public List<TreeNode> findSysTreeByRoleOrg(String[] orgIds, Integer roleId) {
		// TODO Auto-generated method stub
		List<String> regions = treeDao.queryForList("Tree.queryOrgRegion", orgIds);
		List<TreeNode> list = treeDao.queryForList("Tree.queryLogiSystem", regions);
		roleSysNodes = treeDao.getSqlMapClientTemplate().queryForMap("Tree.queryRolePermission", roleId, "key","value");
		setSysCheck(list);
		return list;
	}
	private void setSysCheck(List<TreeNode> list){
		for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext();) {
			TreeNode treeNode = iterator.next();
			if (treeNode.getChildren() != null && treeNode.getChildren().size()>0){
				setSysCheck(treeNode.getChildren());
			}else{
//				System.out.println(treeNode.getAttributes().get("KEY").toString());
				if(roleSysNodes.containsKey(treeNode.getAttributes().get("KEY").toString())){
					treeNode.setBoo(1);
				}
			}
		}
	}
	/**
	 * 获取用户区域
	 */
	@SuppressWarnings("unchecked")
	public List<TreeNode> findCurrentRegionByUser(Integer userId,List<String> values , Integer index) {
		// 如果index < 0 则全部取出
		List<TreeNode> codeList = null;
		if(index != null && index < 0)
		{
			if(userId != null)
				codeList = treeDao.findAllRegionByUser(userId);
			else
				codeList = treeDao.findAllRegionCodeCombo();
		}else
		{
			List<TreeNode> vList = treeDao.findFirstRegionByUser(userId);
			codeList =  packageCombotree(index, vList);
		}
		for(TreeNode node : codeList)
		{
			if(values.indexOf(node.getId()) != -1) node.setBoo(1);
			else node.setBoo(0);
			setComboBoo(node.getChildren(), values);
		}
		return codeList;
	}
	
	/**
	 * @Title: setComboBoo
	 * @Description: TODO()
	 * @param @param tree
	 * @param @param values    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	private void setComboBoo(List<TreeNode> nodes , List<String> values)
	{
		if(values.isEmpty()) return;
		for(TreeNode node : nodes)
		{
			if(values.indexOf(node.getId()) != -1) node.setBoo(1);
			else node.setBoo(0);
			if(node.getChildren()!=null&&!node.getChildren().isEmpty()) setComboBoo(node.getChildren(), values);
		}
	}
	
	/**
	 * 
	 * @Title: packageCombotree
	 * @Description: TODO(封装combo)
	 * @param @param index
	 * @param @param value
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	private List<TreeNode> packageCombotree(Integer index , List<TreeNode> value)
	{
		if(index == null || index == 0)
			return value;
		for(TreeNode node : value)
		{
			node.setChildren(treeDao.findChildrenCombo(Integer.valueOf(node.getId())));
		}
		return packageCombotree(index--, value);
	}
	
	/**
	 * @Title: setListBoo
	 * @Description: TODO(根据用户和子节点数获取区域树)
	 * @param @param vList
	 * @param @param codeList
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<TreeNode> regManTreeByRegionCodeAndChildRows(String regionCode,Integer childRows) {
		// TODO Auto-generated method stub
		List<TreeNode> list = treeDao.regManTreeByRegionCodeAndChildRows(regionCode);
		return regManTreeChildList(childRows, list);
	}
	
	/**
	 * @Title: packageChildList
	 * @Description: TODO(封装区域管理树子节点)
	 * @param @param index
	 * @param @param value
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	private List<TreeNode> regManTreeChildList(Integer index, List<TreeNode> value)
	{
		for(TreeNode node : value)
		{
			String regionPid = node.getAttributes().get("REGIONCODE").toString();
			if(index < 0)
			{
				List<TreeNode> children = treeDao.regManTreeAllChildByRegionPid(regionPid);
				node.setChildren(children);
			}else{
				node.setChildren(treeDao.regManTreeChildByRegionPid(regionPid));
			}
		}
		if(index  == null || index == 0 || index < 0){
			return value;			
		}
		return regManTreeChildList(--index , value);
	}
	public List<TreeNode> findOrgTreeByUserIdOwner(Integer userId, int i) {
		// TODO Auto-generated method stub
		return treeDao.findOrgTreeByUserIdOwner(userId);
	}
	
	
	//之前的查询所有的区域
	public List<TreeNode> findAllRegionCodeCombo()
	{
		return treeDao.findAllRegionCodeCombo();
	}
	
	//现在的查询所有的区域
	public List<Ztree> findAllRegions(){
//		List<Ztree> returnztrees = new ArrayList<Ztree>();
		List<Ztree> ztrees = treeDao.findAllRegions();
		
//		for(int i = 0; i < ztrees.size(); i++){
//			returnztrees.add(ztrees.get(i));
//			
//			List<Ztree> streets = treeDao.findStreet(ztrees.get(i).getTid());
//			for(int j = 0; j < streets.size(); j++){
//				returnztrees.add(streets.get(j));
//				List<Ztree> communitys = treeDao.findCommunity(streets.get(j).getTid());
//				for(int z = 0; z < communitys.size(); z++){
//					returnztrees.add(communitys.get(z));
//				}
//			}
//		}
//		return returnztrees;
		return ztrees;
	}
	
	//根据区域查询所有的街道
	public List<Ztree> findAllStreetByRegionCode(String regionCode){
		List<Ztree> streetss = treeDao.queryForList("Tree.findAllStreetByRcode", regionCode);
		return streetss;
	}
	
	//根据街道查询所有的社区
	public List<Ztree> findAllCommunityByStreetCode(String streetCode){
		List<Ztree> communitys = treeDao.queryForList("Tree.finAllCommunityByScode", streetCode);
		return communitys;
	}

    public List<TreeNode> findAllOrgExistsOwnerCombo(Map<String, Object> map)
	{
		return treeDao.findAllOrgExistsOwnerCombo(map);
	}

	/**
	 * 获取当前组织机构和职位
	 */
	public List<TreeNode> findAllOrgAndPost(Map<String, Object> map,
			Integer rows) {
		List<TreeNode> list = treeDao.findCurrentOrg((Integer)map.get("userId"));
		return packageChildOrgAndPost(rows, list);
	}
	
	/**
	 * @Title: packageChildOrgAndPost
	 * @Description: TODO(递归下级组织机构跟职位)
	 * @param @param rows
	 * @param @param fList
	 * @param @return    设定文件
	 * @return List<TreeNode>    返回类型
	 * @throws
	 */
	public List<TreeNode> packageChildOrgAndPost(Integer rows , List<TreeNode> fList)
	{
		for(TreeNode node : fList)
		{
			Integer oid = Integer.valueOf(node.getAttributes().get("ID").toString());
			if(rows < 0) // 取出全部区域
			{
				List<TreeNode> children = treeDao.findAllOrgAndPost(oid);
				node.setChildren(children);
			}else
			{
				
			}
		}
		if(rows == 0 || rows < 0) return fList;			
		return packageChildOrgAndPost(rows --, fList);
	}
	
	//获取组织机构树--zTree  lp3
	public List<Org> getOrgzTree(int orgid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgid", orgid);
		List<Org> orgList = treeDao.queryForList("Tree.getOrgzTree",map);
		return orgList;
	}

    public List<Org> getSearchableOrgzTree(int orgid){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgid", orgid);
        List<Org> orgList = treeDao.queryForList("Tree.getSearchableOrgzTree",map);
        return orgList;
    }
	
	//获取组织机构和职位树(变更组织机构用)--zTree  lp3
	public List<Ztree> getOrgAndPosition(int orgid){
		List<Ztree> ztrees = new ArrayList<Ztree>();
		List<Ztree> orgs = treeDao.queryForList("Tree.queryOrg",orgid);
		
		List<Position> positions = treeDao.queryForList("Tree.queryPosition");
		
		for(int i = 0; i < positions.size(); i++){
			for(int j = 0; j < orgs.size(); j++){
				if(positions.get(i).getOrgid().equals(Integer.parseInt(orgs.get(j).getId()))){
					Ztree temp = new Ztree();
					temp.setFlag("P");
					temp.setIcon("../images/icons/position.png");
					temp.setId(positions.get(i).getId()+"");
					temp.setParentId(positions.get(i).getOrgid()+"");
					temp.setText(positions.get(i).getPname());
					
					ztrees.add(temp);
				}
			}
		}
		
		for(int i = 0; i < orgs.size(); i++){
			orgs.get(i).setIcon("../images/icon-department.png");
			ztrees.add(orgs.get(i));
		}
		
		return ztrees;
	}
	
	//获取所有系统权限树
	public List<Ztree> getSysTree(SessionBean sessionBean){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Ztree> ztrees = new ArrayList<Ztree>();
		//getSys
		List<Ztree> syss = treeDao.queryForList("Tree.querySys");
		for(int i = 0; i < syss.size(); i++){
			//addsys
			ztrees.add(syss.get(i));
			map.put("sysid", syss.get(i).getId());
			//getMods
			List<Ztree> mods = treeDao.queryForList("Tree.queryMod",map);
			for(int j = 0; j < mods.size(); j++){
				//addMod
				ztrees.add(mods.get(j));
				map.put("modid", mods.get(j).getId());
				//getFuncs
				List<Ztree> funcs = treeDao.queryForList("Tree.queryFunc",map);
				for(int k = 0; k < funcs.size(); k++){
					//addFunc
					ztrees.add(funcs.get(k));
					map.put("funcid", funcs.get(k).getId());
					//getOps
					List<Ztree> opss = treeDao.queryForList("Tree.query_Ops",map);
					for(int z = 0; z < opss.size(); z++){
						//addOps
						ztrees.add(opss.get(z));
					}
				}
			}
		}
		
		return ztrees;
	}
	
	public List<Ztree> getUserSysTreeUtil(List<Integer> roleids){
		List<Ztree> ztrees = new ArrayList<Ztree>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleids", roleids);
		//getSys
		List<Ztree> syss = treeDao.queryForList("Tree.querySys");
		
		map.put("nodelevel", 0);
		List<Integer> sysids = treeDao.queryForList("Tree.queryTheUserPermission",map);
		for(int i = 0; i < syss.size(); i++){
			for(int ii = 0; ii < sysids.size(); ii++){
				//addsys
				if(syss.get(i).getId().equals(sysids.get(ii)+"")){
					ztrees.add(syss.get(i));
				}
			}
			map.put("sysid", syss.get(i).getId());
			map.put("nodelevel", 1);
			map.put("pid", syss.get(i).getTid());
			List<Integer> modids = treeDao.queryForList("Tree.queryTheUserPermission",map);
			
			//getMods
			List<Ztree> mods = treeDao.queryForList("Tree.queryMod",map);
			for(int j = 0; j < mods.size(); j++){
				for(int jj = 0; jj < modids.size(); jj++){
					//addMod
					if(mods.get(j).getId().equals(modids.get(jj)+"")){
						ztrees.add(mods.get(j));
					}
				}
				map.put("modid", mods.get(j).getId());
				map.put("nodelevel", 2);
				map.put("pid", mods.get(j).getTid());
				List<Integer> funcids = treeDao.queryForList("Tree.queryTheUserPermission",map);
				
				//getFuncs
				List<Ztree> funcs = treeDao.queryForList("Tree.queryFunc",map);
				for(int k = 0; k < funcs.size(); k++){
					for(int kk = 0; kk < funcids.size(); kk++){
						//addFunc
						if(funcs.get(k).getId().equals(funcids.get(kk)+"")){
							ztrees.add(funcs.get(k));
						}
					}
					map.put("funcid", funcs.get(k).getId());
					map.put("nodelevel", 3);
					map.put("pid", funcs.get(k).getTid());
					List<Integer> opsids = treeDao.queryForList("Tree.queryTheUserPermission",map);
					
					//getOps
					List<Ztree> opss = treeDao.queryForList("Tree.query_Ops",map);
					for(int z = 0; z < opss.size(); z++){
						for(int zz = 0; zz < opsids.size(); zz++){
							//addOps
							if(opss.get(z).getId().equals(opsids.get(zz)+"")){
								ztrees.add(opss.get(z));
							}
						}
					}
				}
			}
		}
		return ztrees;
	}
	
	//获取当前用户的权限树(新增角色用)--zTree  lp3
	public List<Ztree> getUserSysTree(SessionBean sessionBean){
		List<Ztree> ztrees = new ArrayList<Ztree>();
		//admin登录
		if(sessionBean.getLoginid().endsWith("admin")){
			ztrees = getSysTree(sessionBean);
			return ztrees;
		}
		//其他用户登录
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> roleids = sessionBean.getRoleId();
		
		ztrees = getUserSysTreeUtil(roleids);
		
		return ztrees;
	}
	
	//根据系统id获取权限树--zTree  lp3
	public List<Ztree> getSysTreeById(int sysid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysid", sysid);
		List<Ztree> ztrees = new ArrayList<Ztree>();
		//getSys
		List<Ztree> syss = treeDao.queryForList("Tree.querySysById",map);
		//addsys
		ztrees.add(syss.get(0));
		map.put("sysid", syss.get(0).getId());
		//getMods
		List<Ztree> mods = treeDao.queryForList("Tree.queryMod",map);
		for(int j = 0; j < mods.size(); j++){
			//addMod
			ztrees.add(mods.get(j));
			map.put("modid", mods.get(j).getId());
			//getFuncs
			List<Ztree> funcs = treeDao.queryForList("Tree.queryFunc",map);
			for(int k = 0; k < funcs.size(); k++){
				//addFunc
				ztrees.add(funcs.get(k));
				map.put("funcid", funcs.get(k).getId());
				//getOps
				List<Ztree> opss = treeDao.queryForList("Tree.query_Ops",map);
				for(int z = 0; z < opss.size(); z++){
					//addOps
					ztrees.add(opss.get(z));
				}
			}
		}
		return ztrees;
	}
	
	//被选中的权限(编辑角色用)
	public List<Ztree> getRoleSysTree(int roleid,SessionBean sessionBean){
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Ztree> roleSysTree = getUserSysTree(sessionBean);
		
		map.put("roleid", roleid);
		List<Permission> permissions = treeDao.queryForList("Tree.queryPermissionByRoleId",map);//得到该角色有哪些权限
		//checked ztree
		if(permissions.size()>0){
			roleSysTree = checkedZtree(permissions,roleSysTree);
		}
		return roleSysTree;
	}

	private List<Ztree> checkedZtree(List<Permission> permissions,List<Ztree> roleSysTree) {
		
		List<Permission> permissionsTemp = new ArrayList<Permission>();
		for(Permission permission:permissions){
			if(permission.getNodeLevel()==0){
				permissionsTemp.add(permission);
			}
		}
		
		int fromIndex = 0;
		int toIndex = 0;
		for(int j = 0; j<permissionsTemp.size();j++){
			List<Permission> groupPermissions = new ArrayList<Permission>();
			boolean sysFlag = false;
			for(int i=0;i<permissions.size();i++){
				if(permissionsTemp.get(j).getNodeId()==permissions.get(i).getNodeId()&&permissionsTemp.get(j).getNodeLevel().equals(permissions.get(i).getNodeLevel())){
					fromIndex = i;
					if(i==permissions.size()-1){
						toIndex = i+1;	
					}
					sysFlag = true;
					continue;
				}
				if(sysFlag){
					if(permissions.get(i).getNodeLevel()==0){
						if(i==permissions.size()-1){
							toIndex = i+1;	
						}else{
							toIndex = i;
						}
						break;
					}else{
						if(i==permissions.size()-1){
							toIndex = i+1;	
						}
					}
				}
				
			}
			
			groupPermissions = permissions.subList(fromIndex, toIndex);
			boolean flag = false;
			for(int k=0;k<roleSysTree.size();k++){
				if(Integer.toString(groupPermissions.get(0).getNodeId()).equals(roleSysTree.get(k).getId())&&Integer.toString(groupPermissions.get(0).getNodeLevel()).equals(roleSysTree.get(k).getNodelevel())){
					roleSysTree.get(k).setChecked("true");
					flag = true;
					continue;
				}
				
				if(flag){
					if("0".equals(roleSysTree.get(k).getNodelevel())){
						break;
					}
					for(Permission p:groupPermissions){
						System.out.println("------"+p.getNodeId());
					}
					for(int l = 0;l< groupPermissions.size();l++){
						System.out.println(roleSysTree.get(k).getId()+":"+groupPermissions.get(l).getNodeId());
						if(roleSysTree.get(k).getId().equals(Integer.toString(groupPermissions.get(l).getNodeId()))&&roleSysTree.get(k).getNodelevel().equals(Integer.toString(groupPermissions.get(l).getNodeLevel()))){
							roleSysTree.get(k).setChecked("true");
							break;
						}
					}
				}
			}
			
		}
		return roleSysTree;
	}


    @Override
    public Integer getSysTopOrgId(Integer id,Integer topOrgId) {
        Integer parentOrgId = getParentOrgId(id);
        if(id.toString().equals(topOrgId.toString())){
            parentOrgId = id;
        }else{
            if(parentOrgId.toString().equals(topOrgId.toString())){
                parentOrgId = id;
            }else{
                parentOrgId = getSysTopOrgId(parentOrgId,topOrgId);
            }
        }
        return parentOrgId;
    }

    @Override
    public Integer getTopOrgId() {
        Integer topOrgId = (Integer) this.treeDao.queryForObject("Tree.getTopOrgId",new HashMap<String,String>());
        return topOrgId;
    }

    private Integer getParentOrgId(Integer id) {
        Integer parentOrgId = (Integer) this.treeDao.queryForObject("Tree.getParentOrgId",id);
        return parentOrgId;
    }
}















