<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<div title="组织职位及信息">
	        <div class="searchFormBox" style="padding-top: 10px">
	          <span class="nameBas"><b>单位名称：</b><input class="easyui-validatebox" type="text" name="orgName" value="${Org.orgName}" disabled="true"/></span>
		      <span class="nameBas"><b>组织机构代码：</b><input class="easyui-validatebox" type="text" name="orgCode" value="${Org.orgCode}" disabled="true"/></span>
		      <span class="nameBas"><b>单位简称：</b><input class="easyui-validatebox" type="text" name="shortName" value="${Org.shortName}" disabled="true"/></span>
	      	  <span class="nameBas"><b>单位类型：</b><input class="easyui-validatebox" type="text" name="orgType" value="${Org.orgType}" disabled="true"/></span>
	          <span class="nameBas"><b>经济类型：</b><input class="easyui-validatebox" type="text" name="economicType" value="${Org.economicType}" disabled="true"/></span>
	          <span class="nameBas"><b>营业执照：</b><input class="easyui-validatebox" type="text" name="patentNumber" value="${Org.patentNumber}" disabled="true"/></span>
		      <span class="nameBas"><b>发证机关：</b><input class="easyui-validatebox" type="text" name="registingOrgan" value="${Org.registingOrgan}" disabled="true"/></span>
		      <span class="nameBas"><b>工商注册日期：</b><input class="easyui-datebox" value="2013/01/10" type="text" name="registerDate" disabled="true"/></span>
		      <span class="nameBas"><b>税务证号：</b><input class="easyui-validatebox" type="text" name="taxNumber" /></span>
		      <span class="nameBas"><b>执照经营期限&nbsp;&nbsp;&nbsp;<br />起始日：</b><input class="easyui-datebox" value="2013/01/10" type="text" name="registerBDate" disabled="true"/></span>
		      <span class="nameBas"><b>执照经营期限&nbsp;&nbsp;&nbsp;<br />到期日：</b><input class="easyui-datebox" value="2013/01/10" type="text" name="registerEDate" disabled="true"/></span>
		      <span class="nameBas"><b>登记日期：</b><input class="easyui-datebox" value="2013/01/10" type="text" name="rDate" disabled="true"/></span>
		      <span class="nameBas"><b>工商注册地址：</b><input class="easyui-validatebox" type="text" name="registerAddress" value="${Org.registerAddress}" disabled="true"/></span>
		      <span class="nameBas"><b>公司所在区：</b><input class="easyui-validatebox" type="text" name="registerRegion" value="${Org.registerRegion}" disabled="true"/></span>
		      <span class="nameBas"><b>邮政编码：</b><input class="easyui-validatebox" type="text" name="postCode" value="${Org.postCode}" disabled="true"/></span>
		      <span class="nameBas"><b>办公地址：</b><input class="easyui-validatebox" type="text" name="address" value="${Org.address}" disabled="true"/></span>
		      <span class="nameBas"><b>传真：</b><input class="easyui-validatebox" type="text" name="fix" value="${Org.fix}" disabled="true"/></span>
		      <span class="nameBas"><b>电子邮件：</b><input class="easyui-validatebox" type="text" name="email" value="${Org.email}" disabled="true"/></span>
		      <span class="nameBas"><b>网站：</b><input class="easyui-validatebox" type="text" name="webSite" value="${Org.webSite}" disabled="true"/></span>
	          <span class="nameBas"><b>联系人：</b><input class="easyui-validatebox" type="text" name="contact" value="${Org.contact}" disabled="true"/></span>
	          <span class="nameBas"><b>联系电话：</b><input class="easyui-validatebox" type="text" name="phone" value="${Org.phone}" disabled="true"/></span>
	      	  <span class="nameBas"><b>注册资本单位：</b><input class="easyui-validatebox" type="text" name="currencyUnit" value="${Org.currencyUnit}" disabled="true"/></span>
	          <span class="nameBas"><b>注册资本：</b><input class="easyui-validatebox" type="text" name="registerMoney" value="${Org.registerMoney}" disabled="true"/></span>
	          <span class="nameBas"><b>开户银行：</b><input class="easyui-validatebox" type="text" name="registerBank" value="${Org.registerBank}" disabled="true"/></span>
	          <span class="nameBas"><b>注册账户：</b><input class="easyui-validatebox" type="text" name="account" value="${Org.account}" disabled="true"/></span>
	          <span class="nameBas"><b>帐号：</b><input class="easyui-validatebox" type="text" name="accountNum" value="${Org.accountNum}" disabled="true"/></span>
	          <span class="nameBas"><b>法定代表人：</b><input class="easyui-validatebox" type="text" name="legalMan" value="${Org.legalMan}" disabled="true"/></span>
	      	  <span class="nameBas"><b>法人性别：</b> <input class="easyui-validatebox" type="text" name="legalSex" value="${Org.legalSex}" disabled="true"/></span>
	          <span class="nameBas"><b>法人手机：</b><input class="easyui-validatebox" type="text" name="legalMobile" value="${Org.legalMobile}" disabled="true"/></span>
	          <span class="nameBas"><b>法人电话：</b><input class="easyui-validatebox" type="text" name="legalPhone" value="${Org.legalPhone}" disabled="true"/></span>
	          <span class="nameBas"><b>传真：</b><input class="easyui-validatebox" type="text" name="legalFix" value="${Org.legalFix}" disabled="true"/></span>
	          <span class="nameBas"><b>电子邮件：</b><input class="easyui-validatebox" type="text" name="legalEmail" value="${Org.legalEmail}" disabled="true"/></span>
	          <span class="nameBas"><b>法人证件号：</b><input class="easyui-validatebox" type="text" name="legalOwnerId" disabled="true"/></span>
	        </div>
	</div>
				