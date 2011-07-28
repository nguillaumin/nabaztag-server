<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools"%>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@page import="net.violet.platform.datamodel.Lang"%>
<% response.setContentType("text/html;charset=UTF-8");%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="/include_jsp/utils/inc_dico.jsp"%>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<%-- Si l'action me renvoit un message d'erreur ou de succès --%>
<ul class="general-msg" >
	<logic:messagesPresent message="errors" property="badLogin">
		<li class="error"><%=DicoTools.dico(dico_lang , "srv_flickr/bad_login")%> <html:errors property="badLogin"/></li>
	</logic:messagesPresent> 
	
	<logic:messagesPresent message="errors" property="scenarioOwned">
		<li class="error"><%=DicoTools.dico(dico_lang , "srv_podcast/scenario_owned")%> <html:errors property="scenarioOwned"/></li>
	</logic:messagesPresent>  

	<logic:messagesPresent message="errors" property="scenarioNotUpdated"> 
		<li class="error"><%=DicoTools.dico(dico_lang , "srv_flickr/not_updated")%> <html:errors property="scenarioNotUpdated"/></li>
	</logic:messagesPresent> 
	
	<logic:messagesPresent message="errors" property="scenarioNotDeleted"> 
		<li class="error"><%=DicoTools.dico(dico_lang , "srv_podcast/scenario_not_deleted")%> <html:errors property="scenarioNotDeleted"/></li>
	</logic:messagesPresent> 
	
	<logic:messagesPresent message="errors" property="scenarioNotCreated"> 
		<li class="error"><%=DicoTools.dico(dico_lang , "srv_podcast/scenario_not_created")%> <html:errors property="scenarioNotCreated"/></li>
	</logic:messagesPresent>
	
	<logic:messagesPresent message="errors" property="registerSucceed"> 
		<li><html:errors property="registerSucceed"/></li>
	</logic:messagesPresent>
</ul>
<%-- --%>

<div id="flickr-config-holder" >
<div id="flickrConfig" class="main-cadre-contener">	
	<div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2>
	</div>
	
	<div class="main-cadre-content">
		<!-- ******************************************** CONTENT ***************************************************** --> 
			<html:form action="/action/srvFlickrConfig" styleId="srvFlickrConfig" styleClass="srvConfigForm">

				<div>
					<label><%=DicoTools.dico(dico_lang , "srv_flickr/login")%> </label>
					<input style="width:30%;" type="text" id="login" name="login" value="<bean:write name="mySrvFlickrForm" property="login"/>" />
				</div>
						
				<hr class="spacer">
				
					<label><%=DicoTools.dico(dico_lang, "srv_rss/feed_lang")%></label> 
					<html:select property="flickrLang" style="width:150px;">
					<logic:iterate name="mySrvFlickrForm" property="langList" id="langData">
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
				<logic:notEmpty name="mySrvFlickrForm" property="login">
					<%-- Delete --%>			
					<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%></html:button>
					<%-- Mise a jour --%>	
					<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_clock/update")%>" styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_clock/update")%></html:submit>	
				</logic:notEmpty>
				<logic:empty name="mySrvFlickrForm" property="login" >
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
	$("#srvFlickrConfig").ajaxForm({
		target	: "#flickr-config-holder",
		beforeSubmit: function(a,f,o){
			var valid = nablifeValidateFlickrConfig(a,f,o);
			if (valid) {
				$("#srvFlickrConfig").parents("#flickrConfig").block();
			}
			return valid;
		},
		success: function(data) {
			$("#srvFlickrConfig").parents("#flickrConfig").unblock();
			// Deuxième requête!!
			// $("#srvFlickrConfig").resetForm();

			msgHandling.errorMsgShow();	
			// On recharge le bloc à gauche.
			blocLoad("bloc-MyServices");
		}
	});

	$("span.delay-display").delayDisplay();
</script>

