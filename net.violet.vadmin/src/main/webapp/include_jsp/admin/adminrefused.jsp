<%@page pageEncoding="UTF-8" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Admin - Access denied</title>
<link href="../include_jsp/admin/css/style.css" rel="stylesheet" type="text/css">
</head>

<body class="login-error">
	<div id="refused">
		<p class="error">Access denied : User rights<br/>You don't have permission to access to this page.</p>
    <a href="admin.do?dispatch=load">Try to log in again</a>

	</div>
	
</body>
</html>