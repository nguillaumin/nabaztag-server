<%@page pageEncoding="UTF-8"%>
<%@ page import="java.lang.String" %>
<%@ page import="java.util.Map" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %> 
<%@ page import="net.violet.platform.dataobjects.NabcastData" %>
<%@ page import="net.violet.platform.datamodel.User" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>
	
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>

<% Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>
<% String user_main = Long.toString(SessionTools.getRabbitIdFromSession(session));%>
<%
    final User theUser = SessionTools.getUserFromSession(request);
    final String user_id;
    if (theUser == null) {
       user_id = "0";
    } else {
       user_id = theUser.getId().toString();
    }
%>

<bean:define id="search" value="<%= (request.getSession().getAttribute("search")==null) ? "" : (String)request.getSession().getAttribute("search") %>"/>	 
<% String srvNameGlobal = ""; %>
<% String srvShortCut = ""; %>

<bean:define name="myNablifeForm" property="badLogin" id="badLogin" />


<logic:notEmpty name="myNablifeForm" property="srvListData">
	<bean:define name="myNablifeForm" property="srvListData" id="nablifeServicesData" />
	<bean:define name="nablifeServicesData" property="desc" id="srvDesc"/>
	<bean:define name="nablifeServicesData" property="name" id="srvName"/>
	<bean:define name="nablifeServicesData" property="link" id="srvLink"/>
	<% srvNameGlobal = DicoTools.dico_if(dico_lang, srvName.toString()); %>
	<bean:define name="nablifeServicesData" property="srvImg" id="srvImg"/>
	<bean:define name="nablifeServicesData" property="srvShortCut" id="srvShortCutValue"/>
	<% srvShortCut = "/action/myNablife.do?service="+srvShortCutValue.toString();%>
</logic:notEmpty>

<logic:empty name="myNablifeForm" property="srvListData">
	<logic:notEmpty name="myNablifeForm" property="nabcastData">
		<logic:iterate id="nabcastData" name="myNablifeForm" property="nabcastData">
			<%--<bean:define name="nabcastData" property="nabcast_titre" id="srvName"/>--%>
			<% srvNameGlobal = ((NabcastData)nabcastData).getNabcast_titre(); %>
			<bean:define name="nabcastData" property="nabcast_description" id="srvDesc"/>
			<bean:define name="nabcastData" property="nabcast_shortcut" id="nabcastValue"/>
			<bean:define id="srvImg" value=""/>			
			<% srvShortCut = "/action/myNablife.do?nabcast="+nabcastValue.toString();%>
		</logic:iterate>
	</logic:notEmpty>
</logic:empty>
	
	<html:form action="/action/mySession" styleId="shortcut_login_form">

	<logic:lessEqual name="user_main" value="0">					
		<logic:notEmpty name="search">
			<input type="hidden" name="redirectUrl" value="/action/myNablife.do?search=<%=search%>" />
			<script language="javascript">
				$("input[@name=redirectUrl]").attr("value","/action/myNablife.do?search=<%=search%>");
			</script>
		</logic:notEmpty>
	</logic:lessEqual>

	<div class="thirdCol" style="border:1px solid white">
		
		<%-- Flash du lapin--%>
		<div id="rabbitFlash" style="position:absolute; right:-30px; z-index:250; ">
			<img src="../include_img/template/visuel_lapin.gif"/>
		</div>		
		
	</div>

	<%-- ****************************************************************************** --%>

	<div class="twoThirdCol" style="border:1px solid white float:left;">
	
		<div class="partner-content">
			<logic:notEqual name="srvImg" value="">
				<img src="<bean:write name="srvImg" />" style="float:left; margin-right:5px;" />
			</logic:notEqual>
			<h1><%=DicoTools.dico(dico_lang , "srv_Shortcut/want_to_listen1")%> <%=srvNameGlobal%> <%=DicoTools.dico(dico_lang , "srv_Shortcut/want_to_listen2")%></h1>
			<hr class="clearer" />
		</div>
		
		
		<!-- patch pour les users sans lapin -->
		<logic:greaterThan name="user_id" value="0">
			<logic:lessEqual name="user_main" value="0">
			
			<div class="main-cadre-contener" id="shortcut-norabbit">
				<div class="main-cadre-top">
					<h2>
						<%=DicoTools.dico(dico_lang , "srv_Shortcut/norabbit_teaser")%>
					</h2>
				</div>
			
				<div class="main-cadre-content">
					<%-- **************** --%>
					<p><%=DicoTools.insertBR(dico_lang , "srv_Shortcut/norabbit_text", srvNameGlobal)%></p>
					<p><a href="http://www.nabaztag.com"><%=DicoTools.dico(dico_lang , "srv_Shortcut/norabbit_discover")%></a></p>
					<%-- **************** --%>
				</div>
			</div>
			</logic:lessEqual>
		</logic:greaterThan>
		
		
		<logic:lessEqual name="user_id" value="0">





			<div class="main-cadre-contener" id="shortcut-what-is-nabaztag">
				<div class="main-cadre-top">
					<h2>
						<%=DicoTools.dico(dico_lang , "srv_Shortcut/nabaztag_teaser")%>
					</h2>
				</div>
			
				<div class="main-cadre-content">
					<%-- **************** --%>
					
					<p><%=DicoTools.insertBR(dico_lang , "srv_Shortcut/nabaztag_teaser_text", srvNameGlobal)%></p>
					<p><a href="http://www.nabaztag.com"><%=DicoTools.dico(dico_lang , "srv_Shortcut/discover_nabaztag")%></a></p>	
					<%-- **************** --%>
				</div>
			</div>
			
			
			<div class="flat-block"  > 
			   
				<div class="flat-block-top" >
					<h3 class="no-icone">
						<%=DicoTools.dico(dico_lang , "srv_Shortcut/have_rabbit_connect")%>
					</h3>
				</div>
			
				<div class="flat-block-content" >
					<div class="flat-block-content-inner" >
						<%-- ******************** --%>

							<bean:define id="pseudo" name="mySessionForm" property="pseudo"/>
							<bean:define id="password" name="mySessionForm" property="password"/>
							
							<input type="hidden" name="action" value="connect" />
							<input type="hidden" name="redirectUrl" value="<%=srvShortCut%>" />
		
		
							<div class="twoCol-left" style="width:66%">
								<label style="width:100%; text-align:right;">
									<%= DicoTools.dico(dico_lang , "srv_Shortcut/nabname")%>
									<html:text name="mySessionForm" property="pseudo" styleId="pseudo" />
								</label>
								
								<hr class="spacer" />
								
								<label style="width:100%; text-align:right;">
									<%= DicoTools.dico(dico_lang , "srv_Shortcut/password")%>
									<html:password name="mySessionForm" property="password" styleId="password"/>
								</label>
							</div>
							<div class="twoCol-right" style="width:32%">
								
								<div class="generic-button" style="float: left; width: 60px; text-align: center; margin-top: 13px; margin-left: 29px;" >
							        <div>
							            <div>
							            	<a class="form-submit" href='#' onclick="$(this).parents('form').submit();">OK</a>
							            </div>
							        </div>
							    </div>
		
							</div>
							
							
							
							<hr class="spacer" />
												
						<%-- ******************** --%>
					</div>
				</div>
			</div>			
				
<%--
			<div class="login-container">
				<div class="login-top"><h2>&nbsp;</h2></div>
				<div class="login-content">
					<h2><%=DicoTools.dico(dico_lang , "srv_Shortcut/have_rabbit_connect")%></h2>	
					<input type="hidden" name="action" value="connect" />
					
					<bean:define id="pseudo" name="mySessionForm" property="pseudo"/>
					<bean:define id="password" name="mySessionForm" property="password"/>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><html:text name="mySessionForm" property="pseudo" styleId="pseudo" /></td>
						<td>&nbsp;</td>
	          			<td>&nbsp;</td>
	         			</tr>
	         			<tr>
	         				<td><font style="font-size: 10px;"><%= DicoTools.dico(dico_lang , "srv_Shortcut/nabname")%></font></td>
						<td>&nbsp;</td>
	          			<td>&nbsp;</td>
	          		</tr>
	          		<tr>
	         				<td><html:password name="mySessionForm" property="password" styleId="password"/></td>
	         				<td>&nbsp;</td>
	          			<td>&nbsp;</td>
	          		</tr>
					<tr>
						<td><font style="font-size: 10px;"><%= DicoTools.dico(dico_lang , "srv_Shortcut/password")%></font></td>
						<td>&nbsp;</td>
	          			<td><input type="submit" value="OK" class="genericShortcutBt"/></td>
					</tr>
					</table>
				</div>
			</div>
--%>			
		</logic:lessEqual>
				
	</div>

<hr class="clearer" />
<div style="width:550px; margin-left:auto; margin-right:auto; display:none;">	
	<div style="float:left;">
	<%--
	<div id="rabbitFlash">
		<img src="../include_img/template/visuel_lapin.gif"/>
	</div>
	--%>
	
	</div>
	
	<div style="margin-left:298px;">
		<div class="partner-container">
			<div class="partner-top"><h2>&nbsp;</h2></div>
			<div class="partner-content">
			<logic:notEqual name="srvImg" value=""><img src="<bean:write name="srvImg" />" style="float:right; " /></logic:notEqual>
			<%=DicoTools.dico(dico_lang , "srv_Shortcut/want_to_listen1")%> <%=srvNameGlobal%> <%=DicoTools.dico(dico_lang , "srv_Shortcut/want_to_listen2")%>
			</div>
		</div>
		<logic:lessEqual name="user_id" value="0">
			<div class="infotag-container">
				<div class="infotag-top"><h2>&nbsp;</h2></div>
				<div class="infotag-content">
				<h2><%=DicoTools.dico(dico_lang , "srv_Shortcut/nabaztag_teaser")%></h2>
				<p><%=DicoTools.dico(dico_lang , "srv_Shortcut/nabaztag_teaser_text")%></p>
				<p><a href="http://www.nabaztag.com"><%=DicoTools.dico(dico_lang , "srv_Shortcut/discover_nabaztag")%></a></p>			
				</div>
			</div>

			<div class="login-container">
				<div class="login-top"><h2>&nbsp;</h2></div>
				<div class="login-content">
					<h2><%=DicoTools.dico(dico_lang , "srv_Shortcut/have_rabbit_connect")%></h2>	
					<input type="hidden" name="action" value="connect" />
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					
					
					<bean:define id="pseudo" name="mySessionForm" property="pseudo"/>
					<bean:define id="password" name="mySessionForm" property="password"/>
					<tr>
						<td><html:text name="mySessionForm" property="pseudo" styleId="pseudo" /></td>
						<td>&nbsp;</td>
	          			<td>&nbsp;</td>
	         			</tr>
	         			<tr>
	         				<td><font style="font-size: 10px;"><%= DicoTools.dico(dico_lang , "srv_Shortcut/nabname")%></font></td>
						<td>&nbsp;</td>
	          			<td>&nbsp;</td>
	          		</tr>
	          		<tr>
	         				<td><html:password name="mySessionForm" property="password" styleId="password"/></td>
	         				<td>&nbsp;</td>
	          			<td>&nbsp;</td>
	          		</tr>
					<tr>
						<td><font style="font-size: 10px;"><%= DicoTools.dico(dico_lang , "srv_Shortcut/password")%></font></td>
						<td>&nbsp;</td>
	          			<td><input type="submit" value="OK" class="genericShortcutBt"/></td>
					</tr>
					</table>
				</div>
			</div>
		</logic:lessEqual>
	</div>
</div>	
	</html:form>
	

<script type="text/javascript">
<!--
	$("#contentAllServices").hide();	// ne devrait pas etre là, mais j'ignore ce qui a été fait en amon, le div n'est pas caché quand on arrive là....
	var so = new SWFObject("../include_flash/nabaz_attente.swf", "mymovie", "290", "290", "7");
	so.addParam("wmode", "transparent");
	if (!so.write("rabbitFlash")) {
		var container = document.getElementById("rabbitFlash");
		if (container) container.innerHTML = so.getSWFHTML();
	}
//-->
</script>

<logic:equal name="badLogin" value="1">
	
	
	<script language="javascript">
		customAlertN("<%=DicoTools.dico(dico_lang , "myHome/error_login")%>");
	</script>

</logic:equal>