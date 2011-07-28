<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>
 
<div class="intro-cadre-contener">
	<div class="intro-cadre-top">
		<h2><%=DicoTools.dico(dico_lang, "myTerrier/friends_titre")%></h2>
	</div>
	<div class="intro-cadre-content">
		<div class="intro">
			<%=DicoTools.dico(dico_lang, "myTerrier/friends_intro")%>
		</div>
	</div>
</div>

<hr class="clearer" /> 
 
 
<div id="friends-contener"><tiles:insert attribute="friends-bloc"/></div>
<div id="black-contener"><tiles:insert attribute="black-bloc"/></div>

<script language="javascript">
	friends.init_forms();
</script>