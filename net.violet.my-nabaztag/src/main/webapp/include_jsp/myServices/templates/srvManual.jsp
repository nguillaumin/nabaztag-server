<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>
<%	int user_main = (int)SessionTools.getRabbitIdFromSession(session);%>

<%
Map<String, String> srv = new HashMap<String, String>();

if (request.getParameter("applicationId")!=null && !request.getParameter("applicationId").equals("")){
	srv = StaticTools.getServiceFromApplication(request.getParameter("applicationId"), dico_lang);
}

%>

<bean:define id="srv_manual" value="<%=(srv.get("srv_manual")==null) ? "" : srv.get("srv_manual")%>"/>

<logic:notEmpty name="srv_manual">
<logic:notEqual name="srv_manual" value="NOLOC">
<div class="main-cadre-contener">

	<div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang , "services/how_does_it_work")%></h2>
	</div>
	
		<div class="main-cadre-content">
			<hr class="spacer"/>
			<div class="srv-main-config">
						<p><%=srv.get("srv_manual")%></p>		
			</div>
			<hr class="spacer"/>
			
		</div>
</div>
</logic:notEqual>
</logic:notEmpty>