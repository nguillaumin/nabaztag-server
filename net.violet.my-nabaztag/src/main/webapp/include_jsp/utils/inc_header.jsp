<%@ page import="net.violet.platform.datamodel.Lang"%>
<%@page import="net.violet.platform.datamodel.factories.Factories"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.dataobjects.VObjectData" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
	
<% String redirectUrl = request.getParameter("goTo"); %>
<% String thisIsBatard = "false";%>
<% String id = Long.toString( SessionTools.getRabbitIdFromSession(session) ); %>		
	

<%@page import="net.violet.platform.dataobjects.VObjectData"%><bean:define id="isBatard" type="String" value="<%=thisIsBatard%>"/>
<bean:define id="idLapin" type="String" value="<%=id%>"/>
	
	<%-- un peu crado de mettre �a ici....--%>
	<logic:greaterThan name="idLapin" value="0"> 
		<script language="javascript">
			$(document).ready(function(){
				var myLink = $("div.copyright a[@href*='m-12']");
				$(myLink).attr("href", "myMessages.do?action=messages_TellFriend");
				$(myLink).removeAttr("target");
			});
		</script>
	</logic:greaterThan>

	<logic:equal name="page_title" value="myNewAccount">
		<bean:define name="myNewAccountForm" property="userData" id="userData" />
	</logic:equal>	
	<logic:equal name="page_title" value="myMessages">	
		<bean:define name="myMessagesForm" property="userData" id="userData" />
	</logic:equal>			
	<logic:equal name="page_title" value="myTerrier">			
		<bean:define name="myTerrierForm" property="userData" id="userData" />
	</logic:equal>
	<logic:equal name="page_title" value="myNablife">
		<bean:define name="myNablifeForm" property="userData" id="userData" />
	</logic:equal>
	<logic:equal name="page_title" value="myTools">
		<bean:define name="myToolsForm" property="userData" id="userData" />
	</logic:equal>

	<!-- ********************* LOGIN BOX ********************* -->


	<div id="loginBox" 
		<logic:equal name="page_title" value="myNewAccount"><logic:equal name="userData" property="user_id" value="0">style="display:none;"</logic:equal></logic:equal>
		<logic:equal name="page_title" value="myNablife"><logic:equal name="userData" property="user_id" value="0">style="display:none;"</logic:equal></logic:equal>
	>
		
		<%-- BATARD! --%>
		<logic:match scope="page" name="isBatard" value="true">
			<div class="bat"><a  href='myTerrier.do?onglet=Profil'><img border="0" title="<%=DicoTools.dico(dico_lang, "header/waiting_connexion")%>" src="../include_img/rotating_arrow.gif" /></a></div>
		</logic:match>

		<div class="main">

			<input type="hidden" value="<bean:write name='userData' property='user_lang'/>" id="user_lang" />
				
			
			<bean:define name='userData' property='user_24' id="user_24"/>
			
			<bean:define name='userData' property='user_lang' id="user_lang"/>
			
			<bean:define id="user_id" name="userData" property="user_id" />
			
			<logic:greaterThan name="userData" property="user_id" value="0"> <% /* User connect� */%>
				<div class="profil">
					<div class="photo">
						<logic:equal name="userData" property="user_image" value="0">
							<img class="photo user_picture" align="left" src="../photo/default_S.jpg" height="42" border="0">
						</logic:equal>
						<logic:equal name="userData" property="user_image" value="1">
							<img class="photo user_picture" align="left" src="../photo/<%=user_id%>_S.jpg" height="42" border="0">
						</logic:equal>
					</div>
						<div class="pseudo">
							<%= VObjectData.getData(SessionTools.getRabbitFromSession(session)).getObject_login() %>
						</div>
				</div>
				<input type="hidden" value="<%=user_id%>" id="user_id" />
	
			</logic:greaterThan>
	
			<logic:equal name="userData" property="user_id" value="0">
			<%/* User NON connect�*/%>
				<html:form action="/action/mySession" styleId="login_form">
					<logic:equal name="page_title" value="myHome">
						<html:hidden name="mySessionForm" property="url" value="goNablife" />
					</logic:equal>	
					<logic:equal name="page_title" value="myMessages">	
						<html:hidden name="mySessionForm" property="url" value="goMessages" />
					</logic:equal>			
					<logic:equal name="page_title" value="myTerrier">
						<html:hidden name="mySessionForm" property="url" value="goTerrier" />		
					</logic:equal>
					<logic:equal name="page_title" value="myNablife">
						<html:hidden name="mySessionForm" property="url" value="goNablife" />	
					</logic:equal>
	
					<html:hidden name="mySessionForm" property="action" value="connect"/>
					<html:hidden name="mySessionForm" property="redirectUrlBadLogin" value="myNablife.do?badLogin=1"/>
					<label><%=DicoTools.dico(dico_lang, "header/login_nabname")%> </label><html:text name="mySessionForm" property="pseudo"/><br />
					<label><%=DicoTools.dico(dico_lang, "header/login_password")%> </label><html:password name="mySessionForm" property="password"/>
					<input type="hidden" name="redirectUrl" value="<%= (redirectUrl==null) ? "myNablife.do" : redirectUrl %>"/>
					<label>&nbsp;</label><input style="width:84px;" class="genericBt" type="submit" value="<%=DicoTools.dico(dico_lang, "header/login_button")%>" class="genericBt"/>
					<hr class="clearer" />
				</html:form>
			</logic:equal>
			
		</div>
	</div>

	<%--
		Les liens quand on est logu� en haut a droite sont normalement dans #loginBox
		MAIS comme notre ami IE6 a beaucoup de mal avec les div qui ont un png transparent ET des liens dedans
		le tout positionn� en absolue, la "solution" est de sortir le ces liens du div en question
	--%>
	<logic:greaterThan name="userData" property="user_id" value="0"> <% /* User connect� */%>
		<div class="functions">
			<ul>
				<li class="lien-profil"><a href="myTerrier.do?onglet=Profil"><span><%=DicoTools.dico(dico_lang, "header/my_profile")%></span></a></li>
				<li class="rcv_message"><a title="Messages" href='myMessages.do?onglet=Recu' ><span style="display:block;" id="nbMessagesReceivedHeader"></span></a></li>
				<li class="preference"><a href='myTerrier.do?onglet=MesPreferences' ><span><%=DicoTools.dico(dico_lang, "header/my_preferences")%></span></a></li>
				<li class="unlog"><a title="D�connecter" href='<html:rewrite forward="goSession"/>?action=disconnect&forward=home' ><span><%=DicoTools.dico(dico_lang, "header/disconnect")%></span></a></li>
			</ul>
		</div>
	</logic:greaterThan>
	
	<!-- *********************  /LOGIN BOX ********************* -->
<%@ include file="/include_jsp/utils/inc_top_warning.jsp" %>
	

	<div id="mainLogo" ><a href="../action/myNablife.do"><span><%=DicoTools.dico(dico_lang, "header/nabaztag")%></span></a></div>
	<div id="mainBigMenu">
		<ul>
			<li><a class="myNablife<logic:equal name="page_title" value="myNablife">ON</logic:equal>" href="../action/myNablife.do"><span><%=DicoTools.dico(dico_lang, "header/section_nablife")%></span></a></li>
			<li><a class="myMessages<logic:equal name="page_title" value="myMessages">ON</logic:equal>" href="../action/myMessages.do"><span><%=DicoTools.dico(dico_lang, "header/section_messages")%></span></a></li>
			<li><a class="myTerrier<logic:equal name="page_title" value="myTerrier">ON</logic:equal>" href="../action/myTerrier.do"><span><%=DicoTools.dico(dico_lang, "header/section_terrier")%></span></a></li>
			<%-- <li><a class="myTools<logic:equal name="page_title" value="myTools">ON</logic:equal>" href="../action/myTools.do"><span><%=DicoTools.dico(dico_lang, "header/section_outils")%></span></a></li> --%>
			<li><a class="myHelp" href="http://help.nabaztag.com/index.php?langue=<%= dico_lang.getHelpLangId() %>"><span><%=DicoTools.dico(dico_lang, "header/section_aide")%></span></a></li>						
		</ul>
	</div>
