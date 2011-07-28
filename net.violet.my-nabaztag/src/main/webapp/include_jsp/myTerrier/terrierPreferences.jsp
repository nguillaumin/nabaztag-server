<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	String dico_lang =	Long.toString(SessionTools.getLangFromSession(session, request).getId());%>

<div id="account-contener"><tiles:insert attribute="account-bloc"/></div>
<div id="display-contener"><tiles:insert attribute="display-bloc"/></div>
<div id="alertes-contener"><tiles:insert attribute="alertes-bloc"/></div>
<div id="prive-contener"><tiles:insert attribute="prive-bloc"/></div>
<div id="api-contener"><tiles:insert attribute="api-bloc"/></div>
<div id="couche-contener"><tiles:insert attribute="couche-bloc"/></div>


<script type="text/javascript">
		preferences.init_forms(<%=SessionTools.getHourModFromSession(request)%>);
</script>