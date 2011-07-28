<%@page pageEncoding="UTF-8" %>
<%@ page import="net.violet.platform.datamodel.Application" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@page import="net.violet.platform.datamodel.Lang"%>
<%
	response.setContentType("text/html;charset=UTF-8");
%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%
	Lang dico_lang =	SessionTools.getLangFromSession(session, request);
	String action = request.getParameter("action"); // récup de l'action dans l'url
%>

<%
	session.setAttribute("page_title","myTerrier");
%>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>
<bean:define name="myTerrierForm" property="langUser" id="lang" type="String"/>
<bean:define name="myTerrierForm" property="onglet" id="onglet" />
<bean:define name="myTerrierForm" property="userId" id="user_id" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15" /> -->

<title><%=DicoTools.dico(dico_lang , "myTerrier/page_title")%></title>


<%@ include file="/include_jsp/utils/inc_css.jsp" %>
<%@ include file="/include_jsp/utils/inc_javascripts.jsp" %>

</head>
<body onload='getNewMessages();'>

	<div id="container">
		<div id="header">
			<%@ include file="/include_jsp/utils/inc_header.jsp" %>
		</div>
		<div id="wrapper"> <%-- ******************************************************************************************************** --%>
			<!-- COLONNE CENTRALE -->  
			<div id="centercontent" >
				
				<div class="tabNavContener">
					<h1 class="icoTitle_Terrier"></h1>
					<ul class="tabNav">
						<li id="MesPreferences"><a href="myTerrierInfo.do"><span><%=DicoTools.dico(dico_lang , "myTerrier/tab_preferences")%></span></a></li>			
						<li id="MaMusique"><a href="myListeMp3.do"><span><%=DicoTools.dico(dico_lang , "myTerrier/tab_music")%></span></a></li>
						<li id="Profil"><a href="myTerrierProfil.do?dispatch=load"><span><%=DicoTools.dico(dico_lang , "myTerrier/tab_profile")%></span></a></li>
						<li id="MesAmis"><a href="myTerrierFriends.do?dispatch=load"><span><%=DicoTools.dico(dico_lang , "myTerrier/tab_friends")%></span></a></li>
					</ul>
			    </div>				


				<div class="mainTab">
					<div class="mainTabBody">

						<!-- Profil -->
						<div id="contentProfil" class="contentMainTab myTerrier" style="display:block;">
							<div id="myInfo" ></div>
							<div id="rabbitSignature" ></div>				
						</div>
						<!-- /Profil -->

						<!-- Ma musique -->
						<div id="contentMaMusique" class="contentMainTab myTerrier">
							&nbsp;
						</div>
						<!-- /Ma musique -->

						<!-- Mes amis -->
						<div id="contentMesAmis" class="contentMainTab myTerrier">
							&nbsp;
						</div>
						<!-- /Mes amis -->

						<!-- Abonnement -->
						<div id="contentMonAbonnement" class="contentMainTab myTerrier">
							&nbsp;
						</div>
						<!-- /Abonnement -->

						<!-- Préférences -->
						<div id="contentMesPreferences" class="contentMainTab myTerrier">
							&nbsp;
						</div>
						<!-- /Préférences -->			
							
						<hr class="clearer" />
					</div>
				</div>
			</div>
			<hr class="clearer" />
			<!--/ COLONNE CENTRALE -->
		</div>

		<div id="leftcontent"> <%-- ******************************************************************************************************** --%>
			<!-- COLONNE GAUCHE -->
			<div class="contener"></div>
			<!-- /COLONNE GAUCHE -->
		</div>
		
		<div id="rightcontent"> <%-- ******************************************************************************************************** --%>
			<!-- COLONNE DROITE -->
			<div class="contener"></div>
			<!-- /COLONNE DROITE -->
		</div>	
		<div id="footer">
			<div class="copyright"><%=DicoTools.dico(dico_lang , "footer/copyright")%> | <a href="#"><%=DicoTools.dico(dico_lang , "footer/contact_link")%></a></div>
		</div>
	</div>								
			  

<script language="javascript">

	$(document).ready(function(){
				if (nabaztag.constantes.ISLOG) {
					<logic:notPresent parameter="onglet" >
						mainTab_GoTab();
					</logic:notPresent>
				} else {
					divChangeUrl("contentProfil", "../include_jsp/utils/inc_notlogged.jsp");
				}
				
				
				
				<logic:present parameter="action" >
					performAction("<%=action%>");
				</logic:present>

				<logic:notPresent parameter="action" >
					var hourMode = <%=SessionTools.getHourModFromSession(request)%>;
				//tabId, url, blockRefresh, afterCallback
				//preferences.init_forms(<%=SessionTools.getHourModFromSession(request)%>);
					<logic:equal name="myTerrierForm" property="onglet" value="Profil">
						mainTab_GoTab('Profil', 'myTerrierProfil.do?dispatch=load');
					</logic:equal>
	
					<logic:equal name="myTerrierForm" property="onglet" value="MesPreferences">
						mainTab_GoTab('MesPreferences', 'myTerrierInfo.do' );
					</logic:equal>
					
					<logic:equal name="myTerrierForm" property="onglet" value="MesAmis">
						mainTab_GoTab('MesAmis', 'myTerrierFriends.do?dispatch=load');		
					</logic:equal>	
					
					<logic:equal name="myTerrierForm" property="onglet" value="blackList">
						mainTab_GoTab('MesAmis', 'myTerrierFriends.do?dispatch=load');		
					</logic:equal>
				</logic:notPresent>

				


				
				<logic:notEmpty name="myTerrierForm" property="popup">
					TB_show('','../action/srvDialogConfig.do?dispatch=load&applicationId=<%=Application.NativeApplication.EARS_COMMUNION.getApplication().getId()%>&height=1&width=400');
				</logic:notEmpty>

					

	});
</script>

</body>
</html>