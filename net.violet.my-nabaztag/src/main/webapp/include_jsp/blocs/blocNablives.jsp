<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.DicoTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@page import="net.violet.platform.datamodel.Lang"%>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %> 


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request); %>


<h1><%=DicoTools.dico(dico_lang , "bloc/nablives_title")%></h1>

<bean:define id="dico_key" name="BlocForm" property="nablives" />

<div class="block-content"><div class="inner">
<%= DicoTools.dico(dico_lang , (String)dico_key)%>
</div></div>

<script>
	<%-- quand on est pas logué, on vire les liens, c'est moche, mais vu comment sont fait les nablives, on a pas trop le choix --%>
	if(!nabaztag.constantes.ISLOG){
		$("#bloc-nablives a").bind("click", function(){ 
			$(this).css("cursor", "pointer");
			return false;
		});
	}
</script>