<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<tiles:insert attribute="description"/>
<tiles:insert attribute="setUpSrv"/>
<tiles:insert attribute="extraSrv"/>
<tiles:insert attribute="rabbitTalk"/>
<tiles:insert attribute="manual"/>
<tiles:insert attribute="links"/>

<div class="bottom-bar-outer">
	<div class="bottom-bar-inner">
		<a onclick="nablife.returnToSrvList()" href="javascript:;" class="srv-back" ><span>&lt;<%=DicoTools.dico(dico_lang , "services/back_to_list")%></span></a>
	</div>
</div>
