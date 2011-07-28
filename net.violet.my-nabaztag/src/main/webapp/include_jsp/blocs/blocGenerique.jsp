<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	String dico_lang =	Long.toString(SessionTools.getLangFromSession(session, request).getId());%>

<logic:notEmpty name="BlocForm" property="mainTitle">
	<h1>
		<bean:write name="BlocForm" property="mainTitle" />
	</h1>
</logic:notEmpty>

<div class="block-content">
<div class="inner">
	<logic:iterate name="BlocForm" property="contentGenericBloc" id="contentGenericBloc">
		<bean:define name="contentGenericBloc" property="title" id="title"/>
		<bean:define name="contentGenericBloc" property="content" id="content"/>
		<bean:define name="contentGenericBloc" property="DicoKey" id="DicoKey"/>
	

		<logic:notMatch name="title" value="NOLOC">
			<h3 id="<%=DicoKey.toString()%>">
				<bean:write name="contentGenericBloc" property="title" />
			</h3>
		</logic:notMatch>
		
		<div id="<%=DicoKey.toString()%>">
				<%=content.toString() %>
		</div>

	</logic:iterate>

</div>
</div>