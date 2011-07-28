<%@ page pageEncoding="UTF-8" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Admin - Authentification</title>
<link href="../include_jsp/admin/css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="login">

<div id="content">

	<div id="internationalization">
		<ul>
			<li><a href="adminChangeLocale.do?dispatch=english&redirect=authentification"><img src="../include_img/flags/en.gif" border="0"/></a></li>
			<li><a href="adminChangeLocale.do?dispatch=french&redirect=authentification"><img src="../include_img/flags/fr.gif" border="0"/></a></li>
			<li><a href="adminChangeLocale.do?dispatch=japanese&redirect=authentification"><img src="../include_img/flags/jp.gif" border="0"/></a></li>
		</ul>
	</div>
	
	<p class="clear error"><html:errors property="wrongValue" /></p>
	<p class="clear error"><html:errors property="inexistingFile" /></p>
	
	<fieldset><legend>Admin Authentification:</legend>
		<html:form action="/action/admin" styleId="login_form">
			<html:hidden styleId="dispatchAuth" property="dispatch" value="connect"/>
      <div class="field">
				<label for="email"><span><bean:message key="common.email"/><bean:message key="common.column"/></span><input type="text" id="email" name="email"/></label>
      </div>
      <div class="field">
        <label for="password"><span><bean:message key="common.password"/><bean:message key="common.column"/></span><input type="password" id="password" name="password"/></label>
      </div>
			<html:submit property="submit" value="Log me" />
		</html:form>
	</fieldset>
	
</div>

</body>
</html>