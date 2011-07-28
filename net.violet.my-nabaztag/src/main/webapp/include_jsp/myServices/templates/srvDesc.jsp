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
<%	int user_main = (int) SessionTools.getRabbitIdFromSession(session); %>

<%
Map<String, String> srv = new HashMap<String, String>();

if( request.getParameter("subscriptionId")!=null && !request.getParameter("subscriptionId").equals("")){
	srv = StaticTools.getServiceFromSubscription(request.getParameter("subscriptionId"), dico_lang);
}
else if( request.getParameter("applicationId")!=null && !request.getParameter("applicationId").equals("")){
	srv = StaticTools.getServiceFromApplication(request.getParameter("applicationId"), dico_lang);
}
%>

<bean:define id="searched" value="<%=(request.getParameter("searched")==null) ? "" : request.getParameter("searched")%>" />

<bean:define id="srv_img" value="<%=(srv.get("srv_img")==null) ? "" : srv.get("srv_img")%>"/>
<bean:define id="srv_desc_long" value="<%=srv.get("srv_desc_long") %>"/>

<div class="intro-cadre-contener intro-cadre-simple service-introduction">
	
	<div class="intro-cadre-top">
		<img class="icone-service" src="<%=srv.get("srv_icone")%>" alt="<%=DicoTools.dico_if(dico_lang,srv.get("srv_name"))%>" />
		<h2>
			<%=srv.get("srv_name")%>
		</h2>
	</div>
	

	<div class="intro-cadre-content">
		<%-- ------------------------------------- --%>
			<logic:notEmpty name="srv_img">
				<div class="image">
					<img src="<%=srv_img %>" />		
				</div>
			</logic:notEmpty>
			<div class="description">
				<div><h3><%=srv.get("srv_desc")%></h3></div>
				
				<logic:notEmpty name="srv_desc_long">
					<logic:notEqual name="srv_desc_long" value="NOLOC">
	
					
						<a class="bCollapseLink simple-link closed-arrow" href="#" target="#more">
							<%=DicoTools.dico(dico_lang , "generic/showMore")%>
						</a>
						
						
						<div class="more-desc" id="more" style="display:none;">
							<%=srv.get("srv_desc_long")%>
						</div>
					
					</logic:notEqual>
				</logic:notEmpty>				
			</div>

		<%-- ------------------------------------- --%>
	</div>
	
</div>	

<hr class="spacer" />
	
<div class="bottom-bar-outer">
	<div class="bottom-bar-inner">
		<a onclick="nablife.returnToSrvList()" href="javascript:;" class="srv-back" ><span>&lt;<%=DicoTools.dico(dico_lang , "services/back_to_list")%></span></a>
	</div>
</div>

<hr class="spacer" />

<script language="javascript">
	tools.init_collapseLink();
</script>

