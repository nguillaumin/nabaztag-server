<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"language="java"%>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="net.violet.platform.util.Constantes"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>Nathan Admin</title>
</head>

<body>

<h1>NATHAN ADMIN</h1>

<p>
	Cette page permet de modérer les versions Nathan proposées par les utilisateurs.
</p>

<a href="nathanadmin?action=list">Lister les versions en attente de validation</a> : affiche les 
versions partagées en attente de validation, pour l'instant ces versions ne sont accessibles que par leur créateur.

<br/><hr/><br/>

<%-- Affiche les versions trouvees --%>
<logic:notEmpty name="resultsList">
<bean:define id="resultsList" name="resultsList" />

<ul>
<logic:iterate name="resultsList" id="version">
	<bean:define id="versionId" name="version" property="id" />
	<li><a href="nathanadmin?action=info&versionId=<%=versionId %>">Version <%=versionId%></a></li>
</logic:iterate>
</ul>

</logic:notEmpty>
<%-- Fin de la liste --%>

<br/><hr/><br/>

<%-- Affichage des infos sur la version --%>
<logic:notEmpty name="theVersion">
<bean:define id="theVersion" name="theVersion"/>
<bean:define id="mp3List" name="mp3List"/>
<bean:define id="theAuthor" name="theAuthor"/>

<bean:define id="versionId" name="theVersion" property="id"/>
<bean:define id="description" name="theVersion" property="description"/>

<bean:define id="authorLogin" name="theAuthor" property="object_login"/>
<bean:define id="authorId" name="theAuthor" property="id"/>

<h2>Fiche détaillée : <%=versionId%></h2>

<p>
Auteur : <%=authorLogin%> (<%=authorId %>)<br/>
Description : <%=description%><br/>
Contenu : <br/>

<ul>
	<%int count = 1; %>
	<logic:iterate name="mp3List" id="mp3">
		<bean:define id="file" name="mp3" property="file" />
		<bean:define id="path" name="file" property="path2mp3"/>
		<li><a href="http://<%=Constantes.STREAM_SERVER%><%=path.toString().replaceAll("broadcast","")%>">Partie <%=count++%></a></li>
	</logic:iterate>
</ul>


<a href="nathanadmin?action=accept&versionId=<%=versionId%>">Accepter cette version</a> cette version sera accessible pour
tous les utilisateurs.<br/>
<a href="nathanadmin?action=refuse&versionId=<%=versionId%>">Refuser cette version</a> cette version ne sera accessible que pour
son auteur et n'apparaîtra plus dans la liste des versions à modérer.
</p>

</logic:notEmpty>



</body>

</html>