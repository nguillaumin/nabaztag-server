<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>




<div class="intro-cadre-contener intro-cadre-simple service-introduction">
	
	<div class="intro-cadre-top">
		<h2>
			<%=DicoTools.dico(dico_lang, "generic/noRabbitMessages_title")%>
		</h2>
	</div>
	

	<div class="intro-cadre-content">
		
			<div class="image">
				<img src="../include_img/layout/lapin-message-big.gif"/>		
			</div>

			<div class="description">
				<div>
					<%=DicoTools.dico(dico_lang, "generic/noRabbitMessages")%>	
				</div>
			</div>
		
	</div>
	
</div>
