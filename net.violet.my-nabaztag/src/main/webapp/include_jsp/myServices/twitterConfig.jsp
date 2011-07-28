<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools"%>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8");%>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="/include_jsp/utils/inc_dico.jsp"%>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<%-- Si l'action me renvoit un message d'erreur ou de succès --%>
<ul class="general-msg" >
	<logic:messagesPresent message="errors" property="badLogin">
		<li class="error"><html:errors property="badLogin"/></li>
	</logic:messagesPresent> 
	
	<logic:messagesPresent message="errors" property="scenarioOwned">
		<li class="error"><html:errors property="scenarioOwned"/></li>
	</logic:messagesPresent>  

	<logic:messagesPresent message="errors" property="scenarioNotUpdated"> 
		<li class="error"><html:errors property="scenarioNotUpdated"/></li>
	</logic:messagesPresent> 
	
	<logic:messagesPresent message="errors" property="scenarioNotDeleted"> 
		<li class="error"><html:errors property="scenarioNotDeleted"/></li>
	</logic:messagesPresent> 
	
	<logic:messagesPresent message="errors" property="scenarioNotCreated"> 
		<li class="error"><html:errors property="scenarioNotCreated"/></li>
	</logic:messagesPresent>
	
	<logic:messagesPresent message="errors" property="registerSucceed"> 
		<li><html:errors property="registerSucceed"/></li>
	</logic:messagesPresent>
</ul>
<%-- --%>

<div id="twitter-config-holder" >
<div id="twitterConfig" class="main-cadre-contener">	
	<div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2>
	</div>
	
	<div class="main-cadre-content">
		<!-- ******************************************** CONTENT ***************************************************** --> 
			<html:form action="/action/srvTwitterConfig" styleId="srvTwitterConfig" styleClass="srvConfigForm">

				<div>
					<label><%=DicoTools.dico(dico_lang , "srv_twitter/login")%></label>
					<input style="width:30%;" type="text" id="login" name="login" value="<bean:write name="mySrvTwitterForm" property="login"/>" />
				</div>
						
				<hr class="spacer">
				
				<div>
					<label><%=DicoTools.dico(dico_lang , "srv_twitter/password")%> </label>
					<input style="width:30%; padding:2px;" type="password" id="password" name="password" value="<bean:write name="mySrvTwitterForm" property="password"/>" />
				</div>
						
				<hr class="spacer">

					<label><%=DicoTools.dico(dico_lang, "srv_rss/feed_lang")%></label> 
					<html:select property="twitterLang" style="width:150px;">
					<logic:iterate name="mySrvTwitterForm" property="langList" id="langData">
						<bean:define name="langData" property="lang_id" id="lang_id" />
						<bean:define name="langData" property="lang_title" id="lang_title" />
						<bean:define name="langData" property="lang_type" id="lang_type" />
				
						<html:option value="<%=lang_id.toString()%>"><%=lang_title.toString()%></html:option>
					</logic:iterate>
					</html:select>
					
					<hr class="spacer">
				
			
			<hr class="spacer">
			
			<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
			
			<div align="center">
				<logic:notEmpty name="mySrvTwitterForm" property="login">
					<%-- Delete --%>			
					<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%></html:button>
					<%-- Mise a jour --%>	
					<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_clock/update")%>" styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_clock/update")%></html:submit>	
				</logic:notEmpty>
				<logic:empty name="mySrvTwitterForm" property="login" >
					<%-- Activate --%>				
					<html:submit property="submit" value="<%=DicoTools.dico(dico_lang , "srv_clock/activate")%>"  styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_clock/activate")%></html:submit>
				</logic:empty>
			</div>
			
		</html:form>
	<%-- ************************************************************************************************************* --%>
	</div><!-- End of main content -->
</div><!-- End of main contener -->
</div>

<script type="text/javascript">
	$("#srvTwitterConfig").ajaxForm({
		target	: "#twitter-config-holder",
		beforeSubmit: function(a,f,o){
			var valid = nablifeValidateTwitterConfig(a,f,o);
			if (valid) {
				$("#srvTwitterConfig").parents("#twitterConfig").block();
			}
			return valid;
		},
		success: function(data) {
			$("#srvTwitterConfig").parents("#twitterConfig").unblock();
			// Deuxième requête!!
			// $("#srvTwitterConfig").resetForm();

			msgHandling.errorMsgShow();	
			
			// On recharge le bloc à gauche.
			blocLoad("bloc-MyServices");
		}
	});

	$("span.delay-display").delayDisplay();
</script>

