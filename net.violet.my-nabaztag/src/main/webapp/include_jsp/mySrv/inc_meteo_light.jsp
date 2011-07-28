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
	<!--
	
	-->
		<div class="main-cadre-content">
			<hr class="spacer"/>
			<div class="srv-main-config">
				<div id="keskidit" class="keskidit" >
					<div class="inner" ></div>
				</div>		
		
			</div>
			<hr class="spacer"/>	
		</div>
</div>

<script type="text/javascript">
	
	addPreview("srv-meteo-soleil", 		"<%=DicoTools.dico(dico_lang , "srv_meteo_free/sunshine")%>" );
	addPreview("srv-meteo-nuage", 		"<%=DicoTools.dico(dico_lang , "srv_meteo_free/cloud")%>" );	
	addPreview("srv-meteo-pluie", 		"<%=DicoTools.dico(dico_lang , "srv_meteo_free/rain")%>" );	
	addPreview("srv-meteo-orage", 		"<%=DicoTools.dico(dico_lang , "srv_meteo_free/storm")%>" );	
	addPreview("srv-meteo-brouillard", 	"<%=DicoTools.dico(dico_lang , "srv_meteo_free/smog")%>" );	
	addPreview("srv-meteo-neige", 		"<%=DicoTools.dico(dico_lang , "srv_meteo_free/snow")%>" );	
				
</script>