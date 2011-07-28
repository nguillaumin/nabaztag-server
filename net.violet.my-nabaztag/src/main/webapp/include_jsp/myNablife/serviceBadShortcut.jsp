<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>
<%--
partner/partner_discover

partner/partner_left_title
partner/partner_left_text
partner/partner_middle_title
partner/partner_middle_text
partner/partner_right_title
partner/partner_right_text
--%>

<%=DicoTools.dico(dico_lang , "partner/bad_shortcut" )%>

<div class="intro-cadre-contener">
	
	<div class="intro-cadre-top" style="position:relative;">
		<h2>
			<%=DicoTools.dico(dico_lang , "partner/partner_discover" )%>
		</h2>
	</div>
	
</div>
	
<div class="threeCol-left">
	<div class="flat-block">
		<div class="flat-block-top">
		  <h3 class="no-icone"><%=DicoTools.dico(dico_lang , "partner/partner_left_title")%></h3>
		</div>
	
		<div class="flat-block-content">
			<div class="flat-block-content-inner" >
				<%=DicoTools.dico(dico_lang , "partner/partner_left_text")%>
				<hr class="clearer" />
			</div>
		</div>
	</div>
</div>

<div class="threeCol-middle">
	<div class="flat-block">
		<div class="flat-block-top">
		  <h3 class="no-icone"><%=DicoTools.dico(dico_lang , "partner/partner_middle_title")%></h3>
		</div>
	
		<div class="flat-block-content">
			<div class="flat-block-content-inner" >
				<%=DicoTools.dico(dico_lang , "partner/partner_middle_text")%>
				<hr class="clearer" />
			</div>
		</div>
	</div>
</div>

<div class="threeCol-right">
	<div class="flat-block">
		<div class="flat-block-top">
		  <h3 class="no-icone"><%=DicoTools.dico(dico_lang , "partner/partner_right_title")%></h3>
		</div>
	
		<div class="flat-block-content">
			<div class="flat-block-content-inner" >
				<%=DicoTools.dico(dico_lang , "partner/partner_right_text")%>
				<hr class="clearer" />
			</div>
		</div>
	</div>
</div>	