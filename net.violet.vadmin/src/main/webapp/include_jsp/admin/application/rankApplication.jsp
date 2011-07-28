<%@ page pageEncoding="UTF-8" %>

<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ include file="/include_jsp/admin/header.jsp" %>

<%@ include file="/include_jsp/admin/navigation.jsp" %>

<!-- DEBUT DIV CONTENT -->
<div id="content">

	<p class="clear error"><html:errors property="wrongValue" /></p>
	
	<html:form action="/action/adminRankApplication" styleId="rankApplication" styleClass="srvConfigForm">
		<bean:define id="categories" name="myAdminApplicationForm" property="categoriesList" />
		<html:hidden property ="dispatch" value="displayApplications"/>	
		
		<fieldset><legend>Search applications by</legend>
		
			<label><bean:message key="common.category"/><bean:message key="common.column"/></label>
			<html:select name="myAdminApplicationForm" property="cat">
				<html:options name="myAdminApplicationForm" collection="categories" property="value" labelProperty="label"/> 
			</html:select>
					
			<label>Application lang :</label>
			<html:select name="myAdminApplicationForm" property="language">
				<html:optionsCollection name="myAdminApplicationForm" property="langList" label="name" value="iso_code" />
			</html:select>
		<br />
		<html:submit property="submit" value="Search" />

		</fieldset>
	</html:form>
	
	
	<logic:notEmpty  name="myAdminApplicationForm" property="applicationList">
		<bean:define id="language" name="myAdminApplicationForm" property="language" />
		<bean:define id="cat" name="myAdminApplicationForm" property="cat" />
		<nested:form action="/action/adminRankApplication" styleClass="srvConfigForm">
			<html:hidden property ="dispatch" value="updateRank" />
			<html:hidden styleId="language" property="language" value="<%=language.toString()%>"/>
			<html:hidden styleId="cat" property="cat" value="<%=cat.toString()%>"/>
			<nested:root name="myAdminApplicationForm">
				<nested:iterate name="myAdminApplicationForm" property="applicationList" scope="request" type="net.violet.vadmin.objects.data.GetApplicationData">
					<bean:define id="appId" name="applicationList" property="id" />
					<bean:define id="appName" name="applicationList" property="name" />
          			<nested:hidden property="id" value="<%=appId.toString()%>"/>
          			<nested:hidden property="name" value="<%=appName.toString()%>"/>
					<p>&nbsp;-&nbsp;<nested:write property="name" />&nbsp;<nested:text property="rank" /></p>
				</nested:iterate>
			</nested:root>
			<nested:submit property="submit" value="Update" />
		</nested:form>
	</logic:notEmpty>
	
	<p style="color:green; font-weight:bold;"><html:errors property="success" /></p>
	
</div>
<!-- FIN DIV CONTENT -->

<%@ include file="/include_jsp/admin/footer.jsp" %>