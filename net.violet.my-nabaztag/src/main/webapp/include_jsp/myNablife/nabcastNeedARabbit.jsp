<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.dataobjects.NabcastData" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);
	String redirectUrl = request.getParameter("goTo");
	NabcastData nabcastData = (NabcastData) request.getAttribute("nabcastData"); %>

<script language="javascript">
	 $('input[@name=redirectUrl]').val("<%=redirectUrl%>");
</script>

<bean:define id="n_id" name="nabcastData" property="nabcast_id"/>
<bean:define id="n_title" name="nabcastData" property="nabcast_titre"/>
<bean:define id="n_categ" name="nabcastData" property="nabcast_categ"/>
<bean:define id="n_desc" name="nabcastData" property="nabcast_description"/>

<div class="srv-cadre-contener">
	
	<div class="main-cadre-top">
		<h2 class="srv-title" style="margin-left:11px;" ><%=n_categ%> - <span class="nabcast-title"><%=n_title%></span></h2>
	</div>
	
	<div class="srv-background">	
		<div class="srv-cadre-content">

			<%-- ************************************************************************************************************* --%>

			<div class="srv-description">
					<div class="srv-desc-text">
						<a class="thickbox link-more-options" href="<html:rewrite forward="goNabcastHisto"/>?idNabcast=<%=n_id%>&width=350&height=430"  ><%=DicoTools.dico(dico_lang , "bloc/myservices_nabcast_histo")%></a>
						<p><%=n_desc%></p>
					</div>
			</div>

			<hr class="clearer" />

			<div class="srv-bottom-links">
				<div class="srv-bottom-subscribefull">&nbsp;</div>					
				<a onclick="nablife.returnToSrvList()" href="javascript:;" class="srv-back" ><span><%=DicoTools.dico(dico_lang, "srv_nabcast_subscribe/back")%></span></a>
			</div>

			<%-- ************************************************************************************************************* --%>

			<hr class="clearer" />

		</div>
	</div>
	
</div>
<br/>
<ul class="genericBigList">
	<li>
		<%=DicoTools.dico(dico_lang , "main/please_buy_nabaztag")%>
	</li>
</ul>