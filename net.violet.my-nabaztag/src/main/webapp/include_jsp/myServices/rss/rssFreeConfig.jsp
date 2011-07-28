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
		<bean:define id="applicationId" name="mySrvRssFreeForm" property="applicationId" />
		<bean:define id="subscriptionId" name="mySrvRssFreeForm" property="subscriptionId" />
		<bean:define id="dispatch" name="mySrvRssFreeForm" property="dispatch" type="String"/>
		<bean:define id="subscribed" name="mySrvRssFreeForm" property="subscribed" />
		<bean:define id="name" name="mySrvRssFreeForm" property="name" type="String"/>
		<bean:define id="rssNbNews" name="mySrvRssFreeForm" property="srvNbNews" type="Integer"/>
		
		<html:form action="/action/srvRssFreeConfig" styleId="srvRssFreeConfig" styleClass="srvConfigForm" >
			<html:hidden property ="applicationId" value="<%=applicationId.toString() %>" />	
			<html:hidden property ="subscriptionId" value="<%=subscriptionId.toString() %>" />	
			<html:hidden property ="name" value="<%=name %>" />	
			
			<hr class="spacer">
				
			<label><%=DicoTools.dico(dico_lang, "srv_rss/nb_news")%></label>
			<input type="text" id="rssNbNews" name="srvNbNews" size="2" value="<%=rssNbNews %>" /> 
			<%=DicoTools.dico(dico_lang, "srv_rss/msg_max_news")%>	
			<hr class="spacer" />
			
			<h2><%=DicoTools.dico(dico_lang, "srv_rss/listen_type")%></h2>
			<ul class="radio-chooser">
				<li>
					<html:radio styleId="confHeure" name="mySrvRssFreeForm" property="srvModeListener" value="1" /><span><%=DicoTools.dico(dico_lang, "srv_rss/week_and_week_end")%></span>
					<label class="big"><%=DicoTools.dico(dico_lang, "srv_rss/choose_time_week")%></label>
					<input id="time1" class="hourPicker" type="hidden" name="scheduleW" value="<bean:write name="mySrvRssFreeForm" property="scheduleW"/>">
					
					<hr class="spacer">
					
					<label class="big"><%=DicoTools.dico(dico_lang, "srv_rss/choose_time_week_end")%></label>									
					<input id="time2" class="hourPicker" type="hidden" name="scheduleWE" value="<bean:write name="mySrvRssFreeForm" property="scheduleWE"/>"><br/>				
				</li>
				<li>
					<html:radio  styleId="confDelai"  name="mySrvRssFreeForm" property="srvModeListener" value="2" /><%=DicoTools.dico(dico_lang, "srv_rss/everytime_new")%>
					<html:select name="mySrvRssFreeForm" property="srvFrequencyListening">
						<html:optionsCollection name="mySrvRssFreeForm" property="periodList" label="label" value="id"/>
					</html:select>				
				</li>
			</ul>
			<hr class="spacer">
			
			<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
			
			<div align="center">
				<logic:equal value="true" name="subscribed">
					<%-- Delete --%>			
					<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%></html:button>
					<%-- Mise a jour --%>	
					<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_clock/update")%>" styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_clock/update")%></html:submit>	
				</logic:equal>
				<logic:equal value="false" name="subscribed" >
					<%-- Activate --%>				
					<html:submit property="submit" value="<%=DicoTools.dico(dico_lang , "srv_clock/activate")%>"  styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_clock/activate")%></html:submit>
				</logic:equal>
			</div>
		</html:form>
	
		<%-- ************************************************************************************************************* --%>	
	</div>
</div>

<script type="text/javascript">
	$("input.hourPicker").hourSelect_InitN( false );
	$("ul.radio-chooser").radioChooser();
	
	$("#srvRssFreeConfig").submit(function(){
		return nablifeValidateRssFreeConfig(<logic:equal name="subscribed" value="true" >true</logic:equal>);		
	});
	
</script>

		