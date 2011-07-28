<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@page import="net.violet.platform.datamodel.Lang"%>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>

<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<!-- Title -->
<bean:define property="title" name="myServiceConfigForm" id="title" />
<bean:define property="image" name="myServiceConfigForm" id="image" />
<bean:define property="introduction" name="myServiceConfigForm" id="introduction" />

<div class="intro-cadre-contener intro-cadre-simple service-introduction">
	<div class="intro-cadre-top">
		<h2><%=title%></h2>
	</div>
	
	<div class="intro-cadre-content">
			<div class="image"><img src="<%=image%>" /></div>

			<div class="description">
				<div><h3><%=introduction%></h3></div>
				
				<logic:notEmpty property="description" name="myServiceConfigForm">
					<bean:define property="description" name="myServiceConfigForm" id="description" />
					<a class="bCollapseLink simple-link closed-arrow" href="#" target="#more"><%=DicoTools.dico(dico_lang , "generic/showMore")%></a>
					<div class="more-desc" id="more" style="display:none;"><%=description%></div>
				</logic:notEmpty>				
			</div>

	</div>		
</div>	

<hr class="spacer" />
	
<div class="bottom-bar-outer">
	<div class="bottom-bar-inner">
		<a onclick="nablife.returnToSrvList()" href="javascript:;" class="srv-back" ><span>&lt;<%=DicoTools.dico(dico_lang , "services/back_to_list")%></span></a>
	</div>
</div>

<hr class="spacer" />

<script language="javascript">
	tools.init_collapseLink();
</script>

<!-- ****** -->


<!-- howto -->

<logic:notEmpty name="howTo" name="myServiceConfigForm">
<bean:define property="howTo" name="myServiceConfigForm" id="howTo" />
<div class="main-cadre-contener">

	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/how_does_it_work")%></h2></div>
	
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<div class="srv-main-config">
			<p><%=howTo%></p>		
		</div>
		<hr class="spacer"/>	
	</div>
</div>
</logic:notEmpty>


<!-- ****** -->


<!-- shortcut -->
<logic:notEmpty property="shortcut" name="myServiceConfigForm">
<bean:define property="shortcut" name="myServiceConfigForm" id="shortcut" />

<div class="main-cadre-contener">

	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/direct_link")%></h2></div>
	
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<div class="srv-main-config">
			<p align="center"><input class="auto-select-field" type="text" value="http://my.nabaztag.com/<%=shortcut%>" /></p>		
		</div>
		<hr class="spacer"/>
			
	</div>
</div>

<script>
	$("input.auto-select-field").click(function(){
		$(this).select();
	});
	$("input.auto-select-field").keydown(function(){
		return false;
	});
</script>
</logic:notEmpty>


<!-- ******* -->

<div class="bottom-bar-outer">
	<div class="bottom-bar-inner">
		<a onclick="nablife.returnToSrvList()" href="javascript:;" class="srv-back" ><span>&lt;<%=DicoTools.dico(dico_lang , "services/back_to_list")%></span></a>
	</div>
</div>
