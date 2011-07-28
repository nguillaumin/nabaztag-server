<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>

<div class="main-cadre-contener">
	
	<div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2>
	</div>
	

	<div class="main-cadre-content">
		<%-- ************************************************************************************************************* --%>	
		<bean:define id="subscriptionId" name="mySrvPodcastFullForm" property="subscriptionId" />
		<bean:define id="dispatch" name="mySrvPodcastFullForm" property="dispatch" type="String"/>
		<bean:define id="subscribed" name="mySrvPodcastFullForm" property="subscribed" />
		<bean:define id="name" name="mySrvPodcastFullForm" property="name" type="String"/>
		<bean:define id="podcastNbNews" name="mySrvPodcastFullForm" property="srvNbNews" type="Integer"/>
		
		<html:form action="/action/srvPodcastFullConfig" styleId="srvPodcastFreeConfig" styleClass="srvConfigForm" >
			<html:hidden property ="subscriptionId" value="<%=subscriptionId.toString() %>" />	
			<html:hidden property ="name" value="<%=name.toString() %>" />	
			
			<hr class="spacer">
				
			<%-- <label><%=DicoTools.dico(dico_lang, "srv_rss/nb_news")%></label> --%>
			<%-- <input type="text" id="podcastNbNews" name="podcastNbNews" size="2" value="<%=nbNews %>" /> --%>
			
			<input type="hidden" id="podcastNbNews" name="srvNbNews" size="2" value="1" />
			
			<%--
			<%=DicoTools.dico(dico_lang, "srv_rss/msg_max_news")%>	
			<hr class="spacer" /> 
			--%>
			
			<h2><%=DicoTools.dico(dico_lang, "srv_rss/listen_type")%></h2>
			<ul class="radio-chooser">
				<li>
					<html:radio styleId="confHeure" name="mySrvPodcastFullForm" property="srvModeListener" value="1" /><span><%=DicoTools.dico(dico_lang, "srv_rss/week_and_week_end")%></span>
					<label class="big"><%=DicoTools.dico(dico_lang, "srv_rss/choose_time_week")%></label>
					<input id="time1" class="hourPicker" type="hidden" name="scheduleW" value="<bean:write name="mySrvPodcastFullForm" property="scheduleW"/>">
					
					<hr class="spacer">
					
					<label class="big"><%=DicoTools.dico(dico_lang, "srv_rss/choose_time_week_end")%></label>									
					<input id="time2" class="hourPicker" type="hidden" name="scheduleWE" value="<bean:write name="mySrvPodcastFullForm" property="scheduleWE"/>"><br/>				
				</li>
				<li>
					<html:radio  styleId="confDelai"  name="mySrvPodcastFullForm" property="srvModeListener" value="2" /><%=DicoTools.dico(dico_lang, "srv_rss/everytime_new")%>
					<html:select name="mySrvPodcastFullForm" property="srvFrequencyListening">
						<html:optionsCollection name="mySrvPodcastFullForm" property="periodList" label="label" value="id"/>
					</html:select>				
				</li>
			</ul>
			<hr class="spacer">
			
			<html:hidden styleId="dispatchConfig" property ="dispatch" />
			
			<div align="center">
				<%-- Delete --%>			
				<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%></html:button>
				<%-- Mise a jour --%>	
				<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_clock/update")%>" styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_clock/update")%></html:submit>	
			</div>
		</html:form>
	
		<%-- ************************************************************************************************************* --%>	
	</div>
</div>

<script type="text/javascript">
	$("input.hourPicker").hourSelect_Init( $("#_user_24").val(), false );
	$("ul.radio-chooser").radioChooser();
	
	$("#srvPodcastFreeConfig").submit(function(){
		return nablifeValidatePodcastFreeConfig(true);		
	});
	
	<logic:equal name="mySrvPodcastFullForm" property="isV1" value="true">
		blockUI("show", "<%=DicoTools.dico(dico_lang, "srv_podcast/notAV2")%>", "div.mainTabBody");
	</logic:equal>
	
</script>

		