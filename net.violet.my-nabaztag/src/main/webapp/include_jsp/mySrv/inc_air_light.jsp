<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%> 

<div class="main-cadre-contener">

	<div class="main-cadre-top" id="keskiditTop">
		<h2><%=DicoTools.dico(dico_lang , "srv_all/light_title")%></h2>
	</div>
	
		<div class="main-cadre-content">
			<hr class="spacer"/>
			<div class="srv-main-config">
				<div id="keskidit" class="keskidit">
					<div class="inner"></div>
				</div>		
		
			</div>
			<hr class="spacer"/>	
		</div>
</div>

<script type="text/javascript">
	
	addPreview("srv-air-grave",	"<%=DicoTools.dico(dico_lang , "srv_air/state_grave")%>" );
	addPreview("srv-air-moyen",	"<%=DicoTools.dico(dico_lang , "srv_air/state_moyen")%>" );
	addPreview("srv-air-ok", 	"<%=DicoTools.dico(dico_lang , "srv_air/state_ok")%>" );
				
</script>