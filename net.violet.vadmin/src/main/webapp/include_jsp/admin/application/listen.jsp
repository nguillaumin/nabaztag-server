<%@ page pageEncoding="UTF-8" %>

<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/admin/header.jsp" %>

<%@ include file="/include_jsp/admin/navigation.jsp" %>

<!-- DEBUT DIV CONTENT -->
<div id="content">

	<bean:define id="url" name="myAdminApplicationForm" property="url" />
	
	<strong>And the Winner is: <a href="http://<%=url%>">クリックして下さい!</a></strong>
	
</div>
<!-- FIN DIV CONTENT -->

<%@ include file="/include_jsp/admin/footer.jsp" %>