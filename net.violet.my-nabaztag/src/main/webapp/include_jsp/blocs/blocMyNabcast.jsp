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



<h1>
	<%=DicoTools.dico(dico_lang , "bloc/my_nabcasts_title")%>
</h1>

<div class="block-content"><div class="inner">
  <ul>
	<logic:notEmpty name="BlocForm" property="result">
	<logic:iterate name="BlocForm" property="result" id="nabcastData" >
		<bean:define id="nabcast_titre" name="nabcastData" property="nabcast_titre" />
		<bean:define id="nabcast_categ" name="nabcastData" property="nabcast_categ" />
		<bean:define id="nabcast_nbr_abonne" name="nabcastData" property="nabcast_nbr_abonne" />
		<bean:define id="nabcast_id" name="nabcastData" property="nabcast_id" />
			<li id="myNabcastsList_<%=nabcast_id%>"><a href='javascript:;' title='<%=DicoTools.dico(dico_lang , "bloc/my_nabcasts_configure")%>' ><%=nabcast_titre%></a><!-- /<%=nabcast_categ%>/<%=nabcast_nbr_abonne%>--></li>
	</logic:iterate>
	</logic:notEmpty>
  </ul>
</div></div>		

<script type="text/javascript">
	blocMyNabcast_Init();
</script>