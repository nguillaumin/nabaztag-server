<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<h1><%=DicoTools.dico(dico_lang , "bloc/about_nabaztag_title")%></h1>

<div class="block-content">
	<div class="inner">
		<div align="center">
			<a href="http://www.nabaztag.com" target="_blank">
				<img border="0" src="../include_img/services/visuels_services/humeur.gif" />
			</a>
		</div>
		<a href="http://www.nabaztag.com" target="_blank">
			<%=DicoTools.dico(dico_lang , "bloc/about_nabaztag_description")%>
		</a>
	
		
	</div>								
</div>