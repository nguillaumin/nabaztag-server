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
	addPreview("srv-trafic-0",		"<%=DicoTools.dico(dico_lang , "srv_traffic/state_0")%>" );
	addPreview("srv-trafic-20a40",	"<%=DicoTools.dico(dico_lang , "srv_traffic/state_20a40")%>" );
	addPreview("srv-trafic-40a60", 	"<%=DicoTools.dico(dico_lang , "srv_traffic/state_40a60")%>" );
	addPreview("srv-trafic-60a80", 	"<%=DicoTools.dico(dico_lang , "srv_traffic/state_60a80")%>" );				
</script>