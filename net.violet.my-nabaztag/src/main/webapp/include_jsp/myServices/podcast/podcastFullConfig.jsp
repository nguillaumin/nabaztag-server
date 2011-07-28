<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools"%>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8");%>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<%@ include file="/include_jsp/utils/inc_dico.jsp"%>

<%-- Si l'action me renvoit un message d'erreur ou de succès --%>
<ul class="general-msg">
	<logic:messagesPresent message="errors" property="scenarioOwned">
		<li class="error"><html:errors property="scenarioOwned"/></li>
	</logic:messagesPresent>  

	<logic:messagesPresent message="errors" property="crappyFeed">
		<li class="error"><html:errors property="crappyFeed"/></li>
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

<div id="podcastConfig-holder" >
<div id="podcastConfig" class="main-cadre-contener">
	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<!-- ******************************************** CONTENT ***************************************************** --> 
		<bean:define id="podcastVoice" name="mySrvPodcastFullForm" property="srvVoice" />
		<bean:define id="applicationId" name="mySrvPodcastFullForm" property="applicationId" />
	
		<div class="srv-config" style="float:none;">
			
			<html:form action="/action/srvPodcastFullConfig" styleId="srvPodcastFullConfig" styleClass="srvConfigForm">
			<html:hidden property ="applicationId" value="<%=applicationId.toString() %>" />
				
			<fieldset>
			
				<legend><%=DicoTools.dico(dico_lang, "srv_podcast/add_podcast")%></legend>
				
				<hr class="spacer">
				
					<div><label><%=DicoTools.dico(dico_lang, "srv_podcast/title_URL")%></label>
						<input style="width:60%;" type="text" id="podcastName" name="name" value="<bean:write name="mySrvPodcastFullForm" property="name"/>" />
					</div>
						
					<hr class="spacer">

					<label><%=DicoTools.dico(dico_lang, "srv_rss/feed_lang")%></label> 
					<html:select name="mySrvPodcastFullForm" property="srvVoice" style="width:150px;">
					<logic:iterate name="mySrvPodcastFullForm" property="langList" id="langData">
						<bean:define name="langData" property="lang_id" id="lang_id" />
						<bean:define name="langData" property="lang_title" id="lang_title" />
						<bean:define name="langData" property="lang_type" id="lang_type" />
				
						<html:option value="<%=lang_id.toString()%>"><%=lang_title.toString()%></html:option>
					</logic:iterate>
					</html:select>
					
					<hr class="spacer">

				<div id="fluxAlert"><label><%=DicoTools.dico(dico_lang, "srv_rss/feed_URL")%></label>
					<input style="width:60%;" type="text" id="podcastUrl" name="url" value="<bean:write name="mySrvPodcastFullForm" property="url"/>" />
				</div>
				
				<hr class="spacer">
			</fieldset>

			<fieldset>
				<legend><%=DicoTools.dico(dico_lang, "srv_rss/listen_type")%></legend>

				<ul class="radio-chooser" style="width:75%; ">
					<li>
						<html:radio name="mySrvPodcastFullForm" property="srvModeListener" value="1" /><span><%=DicoTools.dico(dico_lang, "srv_rss/week_and_week_end")%></span>
						
						<hr class="clearer">
						
						<label class="big"><%=DicoTools.dico(dico_lang, "srv_rss/choose_time_week")%></label>
						<input type="hidden" class="hourPicker" name="scheduleW" id="podcastScheduleW"	value="<bean:write name="mySrvPodcastFullForm" property="scheduleW"/>">
			
						<hr class="spacer">
			
						<label class="big"><%=DicoTools.dico(dico_lang, "srv_rss/choose_time_week_end")%></label>
						<input type="hidden" class="hourPicker" name="scheduleWE" id="scheduleWE" value="<bean:write name="mySrvPodcastFullForm" property="scheduleWE"/>">
					</li>
					<li>
						<html:radio name="mySrvPodcastFullForm" property="srvModeListener" value="2" /><%=DicoTools.dico(dico_lang, "srv_rss/everytime_new")%>
						<html:select name="mySrvPodcastFullForm" property="srvFrequencyListening">
							<html:optionsCollection name="mySrvPodcastFullForm" property="periodList" label="label" value="id" />
						</html:select>
					</li>
				</ul>
			</fieldset>
			
			<hr class="spacer">
			<html:hidden styleId="dispatchConfig" property ="dispatch" />
			<div align="center">
				<%-- Activate --%>
				<html:submit property="submit" value="<%=DicoTools.dico(dico_lang , "srv_clock/activate")%>" styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang, "srv_clock/activate")%></html:submit>
			</div>
		</html:form>
	</div>
	<%-- ************************************************************************************************************* --%>
	<hr class="spacer" />
	</div><!-- End of main content -->
</div><!-- End of main contener -->
</div>
<script type="text/javascript">


	$("input.hourPicker").hourSelect_Init( $("#_user_24").val(), false );
	$("span.userTime").userTimeConvert( $("#_user_24").val() );
	
	$("ul.radio-chooser").radioChooser();	
	
	$("#srvPodcastFullConfig").ajaxForm({
				target	: "#podcastConfig-holder",
				beforeSubmit: function(a,f,o){
				var valid = nablifeValidatePodcastFullConfig(a,f,o);
				if (valid) {
					$("#srvPodcastFullConfig").parents("#podcastConfig").block();
				}
				return valid;
			},
			success: function(data) {
				$("#srvPodcastFullConfig").parents("#podcastConfig").unblock();
				// $("#srvPodcastFullConfig").resetForm();
				msgHandling.errorMsgShow();
				// On recharge le bloc à gauche.
				blocLoad("bloc-MyServices");
			}
		});
		
					
	$("span.delay-display").delayDisplay();


		
	<logic:equal name="mySrvPodcastFullForm" property="isV1" value="true">
		setTimeout(function(){
			blockUI("show", "<%=DicoTools.dico(dico_lang, "srv_podcast/notAV2")%>", "div.mainTabBody");
		}, 50);
	</logic:equal>

</script>