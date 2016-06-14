package cn.ccsgroup.ccsframework.utils;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;


/**
 * ****************************************************************************
 * @Package:      [cn.ccsgroup.ccsframework.utils.PageHelper.java]  
 * @ClassName:    [PageHelper]   
 * @Description:  [分页类]   
 * @Author:       [weiwen ]   
 * @CreateDate:   [2013-12-10 上午9:55:38]   
 * @UpdateUser:   [weiwen(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateDate:   [2013-12-20 上午9:55:38，(如多次修改保留历史记录，增加修改记录)]   
 * @UpdateRemark: [完善代码]  
 * @Version:      [v1.0]
 */
public class PageHelper{
	/**ajax翻页用*/
	private String pageName;
	/**ajax翻页用*/
	private String pagingJsName;
	
	private String fromAcrion = "";
	/**	 * 默认翻页的form名称 */
	private static String formName = "pageform";
	
	/**	 * 总页数 */
	private int pageCount = 0;

	/**	 * 当前显示的页	 */
	private int currentPage = 1;

	/**	 * 总记录数量	 */
	private int totalCount = 0;

	/**	 * 每页显示条数	 */
	private int rowPage = 10;

	private int begin = 0;
	
	private int end = 0;
	/**	 * 保存输出	 */
	private String output;
	/**	 * 指定记录所在的rownum	 */
	private int rownum = 0;
	
	/**	 * 排序字段+排序方式（ASC or DESC）	 */
	private String sortStr;
	private String sortField;
	private boolean sortFlag = false;
	private HttpServletRequest request = null;
	private String linkUrl = null;
	public PageHelper() {

	}
	public PageHelper(HttpServletRequest request, PageHelper pager){
		this.request = request;
		int rowPage = pager.getRowPage();
		int currentPage = pager.getCurrentPage();
		String sortStr = pager.getSortStr();
		String sortField = pager.getSortField();
		boolean sortFlag = pager.getSortFlag();
		if(rowPage==0)
			rowPage = this.rowPage;
		if(StringUtil.isBlank(pager.getPagingJsName())){
			this.init(request,rowPage,currentPage,sortStr,sortField,sortFlag);
		}else{
			this.init_ajax(request,rowPage,currentPage,sortStr,sortField,sortFlag,pager.getPagingJsName());
		}
		
	}
	
	private void init_ajax(HttpServletRequest request, int rowPage,
			int currentPage, String sortStr, String sortField,
			boolean sortFlag, String pagingJsName) {
		// TODO Auto-generated method stub
		this.request = request;
		this.pagingJsName = pagingJsName;
		this.currentPage = currentPage;
		this.rowPage = rowPage;
		this.sortStr = sortStr;
		this.sortField = sortField;
		this.sortFlag = sortFlag;
		if (currentPage > 1) {
			this.begin = rowPage * (this.currentPage - 1);
		}
		this.end = rowPage * currentPage;
		
	}
	private void init(HttpServletRequest request, int rowPage,
			int currentPage, String sortStr, String sortField,
			boolean sortFlag) {
		this.request = request;
		this.currentPage = currentPage;
		this.rowPage = rowPage;
		this.sortStr = sortStr;
		this.sortField = sortField;
		this.sortFlag = sortFlag;
		if (currentPage > 1) {
			this.begin = rowPage * (this.currentPage - 1);
		}
		this.end = rowPage * currentPage;
	}
	
	private void init_output(int totalCount, PageHelper pager) {
		// TODO Auto-generated method stub
		this.request = pager.getRequest();
		this.totalCount = totalCount;
		this.rowPage = pager.getRowPage();
		this.currentPage = pager.getCurrentPage();
		this.sortStr = pager.getSortStr();
		this.sortField = pager.getSortField();
		
		if(((currentPage-1)*rowPage)>=totalCount && currentPage>1){//页码前移
			this.currentPage = pager.getCurrentPage()-1;
			this.begin = rowPage * (this.currentPage - 1);
		}
		
		pageCount = totalCount % rowPage > 0 ? (totalCount / rowPage + 1)
				: (totalCount / rowPage);

		if(currentPage>pageCount){
			currentPage=pageCount;
			
		}
		linkUrl = request.getRequestURI();
//		if(!StringUtil.isBlank(requestUrl)&&!StringUtil.isBlank(pagerUrl)){
//			linkUrl = linkUrl.replaceAll(requestUrl, pagerUrl);
//		}
		
		Enumeration enumeration;
		String name;
		String value;
		String haveInput = "";
		boolean havePageInput = false;
		StringBuffer paramBuf = new StringBuffer();
		enumeration = request.getParameterNames();
		paramBuf.append("<form name='"+formName+"' action='"+linkUrl+"' method='post'>");
		while (enumeration.hasMoreElements()) {
			name = (String) enumeration.nextElement();
			value = ParamUtil.getParameter(request, name);

			if("sortStr".equals(name)){
				value = sortStr;
			}
			if("currentPage".equals(name)){
				value = currentPage+"";
			}
			if("dataId".equals(name))
				continue;
			paramBuf.append("<input id='" + name + "' type='hidden' name='"
					+ name + "' value='" + value + "'>\n");
			haveInput += name+",";
		}
		if(!"".equals(haveInput)){
			String[] array = haveInput.split(",");
			for(int i=0;i<array.length;i++){
				if(array[i].equals("currentPage")){
					havePageInput = true;
					break;
				}
			}
		}
		if(!havePageInput){
		  paramBuf.append("<input id='currentPage' type='hidden' name='currentPage' value='" + currentPage + "'>\n");
		  paramBuf.append("<input id='sortStr' type='hidden' name='sortStr' value=''>\n");
		  paramBuf.append("<input id='sortField' type='hidden' name='sortField' value=''>\n");
		  //paramBuf.append("<input id='sortFlag' type='hidden' name='sortFlag' value=''>\n");
		  paramBuf.append("<input id='pageCount' type='hidden' name='pageCount' value='" + pageCount + "'>\n");
		}
		paramBuf.append("</form>");
		output = paramBuf.toString();
	}
	/**
	 * 
	 * @Title: paginate
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return    设定文件
	 * @return String   « 首页« 上一页 第 1 页 共4 页 下一页 »末页 »
	 * @throws
	 */
	public String paginate() {
		// TODO Auto-generated method stub
		StringBuffer st = new StringBuffer();
		String disabled = "";
		if( currentPage == 1) disabled = "disabled class=\"noContinue\"";
		st.append("<input  type=\"button\"  title=\"\u9996\u9875\"  "+ disabled +" value=\"&laquo; \u9996\u9875\" ");
		st.append("onclick=\"javascript:setValue(1);document." + formName + ".action='"+ linkUrl+"';document." + formName+ ".submit();return false;\">");
		
		if(currentPage > 1 && pageCount > 1) disabled = "";
		else disabled = "disabled class=\"noContinue\"";
		st.append("<input  type=\"button\" title=\"\u4E0A\u4E00\u9875\" "+ disabled +"  value=\"&laquo; \u4E0A\u4E00\u9875\" ");
		st.append("onclick=\"javascript:setValue("+(this.currentPage-1)+");document." + formName + ".action='"+ linkUrl+"';document." + formName+ ".submit();return false;\">");
		
		st.append("<span>\u7B2C <input  name=\"userPage\" id=\"userPage\" type=\"text\" class=\"pagesInput\" value=\""+ this.currentPage +"\"");
		st.append("onkeyup=\"javascript:checkUserPage("+pageCount+","+currentPage+",this.value);\"" +
				" onBlur=\"javascript:setValue(this.value);pageSubmit("+currentPage+",this.value,document." + formName
				+ ",'"+linkUrl+"');return false;\" onkeypress=\"javascript:if(event.keyCode==13){setValue(this.value);pageSubmit("+currentPage+",this.value,document." + formName
				+ ",'"+linkUrl+"');return false;}\"/> \u9875 \u5171"+this.pageCount+" \u9875</span>");
		
		if (currentPage < pageCount) disabled = "";
		else disabled = "disabled class=\"noContinue\"";
		st.append("<input  type=\"button\" title=\"\u4E0B\u4E00\u9875\" "+ disabled +"  value=\"\u4E0B\u4E00\u9875 &raquo;\" ");
		st.append("onclick=\"javascript:setValue("+(this.currentPage+1)+");document." + formName + ".action='"	+ linkUrl + "';document." + formName + ".submit();return false;\">");
		
		if (currentPage == pageCount) disabled = "disabled class=\"noContinue\"";
		else disabled = "";
		st.append("<input  type=\"button\" title=\"\u672B\u9875\"  "+ disabled +"  value=\"\u672B\u9875 &raquo;\" ");
		st.append("onclick=\"javascript:setValue("+(this.pageCount)+");document." + formName + ".action='"+ linkUrl+"';document." + formName+ ".submit();return false;\">");
		
		return st.toString();
	}
	
	public String paginate2() {
		// TODO Auto-generated method stub
		StringBuffer st = new StringBuffer();
		String disabled = "";
		if( currentPage == 1) disabled = "disabled class=\"noContinue\"";
		
		st.append("<input  type=\"button\"  title=\"\u9996\u9875\"  "+ disabled +" value=\"&laquo; \u9996\u9875\" ");
		st.append("onclick=\"javascript:setValue(1);" + pagingJsName + "();return false;\">");
		
		if(currentPage > 1 && pageCount > 1) disabled = "";
		else disabled = "disabled class=\"noContinue\"";
		st.append("<input  type=\"button\" title=\"\u4E0A\u4E00\u9875\" "+ disabled +"  value=\"&laquo; \u4E0A\u4E00\u9875\" ");
		st.append("onclick=\"javascript:setValue("+(this.currentPage-1)+");" + pagingJsName + "();return false;\">");
		
		st.append("<span>\u7B2C <input  name=\"userPage\" id=\"userPage\" type=\"text\" class=\"pagesInput\" value=\""+ this.currentPage +"\"");
		st.append("onkeyup=\"javascript:checkUserPage("+pageCount+","+currentPage+",this.value);\"" +
				" onBlur=\"javascript:setValue(this.value);" + pagingJsName + "();return false;\" " +
				" onkeypress=\"javascript:if(event.keyCode==13){setValue(this.value);" + pagingJsName + "();return false;}\"/> \u9875 \u5171"+this.pageCount+" \u9875</span>");
		
		if (currentPage < pageCount) disabled = "";
		else disabled = "disabled class=\"noContinue\"";
		st.append("<input  type=\"button\" title=\"\u4E0B\u4E00\u9875\" "+ disabled +"  value=\"\u4E0B\u4E00\u9875 &raquo;\" ");
		st.append("onclick=\"javascript:setValue("+(this.currentPage+1)+");" + pagingJsName + "();return false;\">");
		
		if (currentPage == pageCount) disabled = "disabled class=\"noContinue\"";
		else disabled = "";
		st.append("<input  type=\"button\" title=\"\u672B\u9875\"  "+ disabled +"  value=\"\u672B\u9875 &raquo;\" ");
		st.append("onclick=\"javascript:setValue("+(this.pageCount)+");" + pagingJsName + "();return false;\">");
		
		return st.toString();
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPagingJsName() {
		return pagingJsName;
	}
	public void setPagingJsName(String pagingJsName) {
		this.pagingJsName = pagingJsName;
	}
	public String getFromAcrion() {
		return fromAcrion;
	}
	public void setFromAcrion(String fromAcrion) {
		this.fromAcrion = fromAcrion;
	}
	public static String getFormName() {
		return formName;
	}
	public static void setFormName(String formName) {
		PageHelper.formName = formName;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount,PageHelper pager) {
		this.totalCount = totalCount;
		if(StringUtil.isBlank(pager.getPagingJsName())){
			this.init_output(this.totalCount,pager);
		}else{
			this.init_outputAjax(this.totalCount,pager);
		}
	}
	private void init_outputAjax(int totalCount, PageHelper pager) {
		// TODO Auto-generated method stub
		this.totalCount = totalCount;
		this.rowPage = pager.getRowPage();
		this.currentPage = pager.getCurrentPage();
		this.pageName = pager.getPageName();
		this.pagingJsName = pager.getPagingJsName();
		this.sortStr = pager.getSortStr();
		this.sortField = pager.getSortField();
		
		if(((currentPage-1)*rowPage)>=totalCount && currentPage>1){//页码前移
			this.currentPage = pager.getCurrentPage()-1;
			this.begin = rowPage * (this.currentPage - 1);
		}
		
		pageCount = totalCount % rowPage > 0 ? (totalCount / rowPage + 1)
				: (totalCount / rowPage);

		if(currentPage > pageCount){
			currentPage=pageCount;
		}
	}
	public int getRowPage() {
		return rowPage;
	}
	public void setRowPage(int rowPage) {
		this.rowPage = rowPage;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public String getSortStr() {
		return sortStr;
	}
	public void setSortStr(String sortStr) {
		this.sortStr = sortStr;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public boolean getSortFlag() {
		return sortFlag;
	}
	public void setSortFlag(boolean sortFlag) {
		this.sortFlag = sortFlag;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	

}
