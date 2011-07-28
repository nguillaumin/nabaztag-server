<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<bean:define name="mySrvWebRadioFreePlayerForm" property="wradioId" id="wradioId" type="Integer"/>
<bean:define name="mySrvWebRadioFreePlayerForm" property="dispatch" id="dispatch" type="String"/>
<bean:define name="mySrvWebRadioFreePlayerForm" property="rabbitName" id="rabbitName" type="String"/> 
 <bean:define name="mySrvWebRadioFreePlayerForm" property="isPlaying" id="isPlaying" type="String"/> 
 
<div class="srv-player"> 
	<div class="srv-player-info">
		<%=DicoTools.dico(dico_lang , "services/listeningRadio")%><span class="srv-nabname"> <%=rabbitName %> </span><%=DicoTools.dico(dico_lang , "services/now")%>
	</div>
	<div class="srv-player-form">
		<html:form action="/action/srvWebRadioFreePlayer" styleId="srvWebRadioFreePlayer">
			<html:hidden styleId="dispatchPlayer" property ="dispatch" value="load" />
			<html:hidden property ="wradioId" value="<%=wradioId.toString() %>" />
			<%-- default value for the dispatch variable. Has to be modified through JAVAScript for any action by loading to take place --%>
			<!-- html:hidden property ="dispatch" value="load"/ -->
			<%-- PlayNow --%>		
			<!-- <html:submit value="<%=DicoTools.dico(dico_lang , "services/listen_radio_button")%>" onclick="goDispatch('playNow', 'dispatchPlayer')" styleClass="webRadioPlayNow" title="Click here to play" /><br /> -->
			<a title="<%=DicoTools.dico(dico_lang , "services/listen_radio_button")%>" href="javascript:;" class="webRadioPlayNow" onclick="goDispatch('playNow', 'dispatchPlayer'); $('#srvWebRadioFreePlayer').submit()">&nbsp;</a>
			<%-- Stop --%>				
			<!--<html:submit value="<%=DicoTools.dico(dico_lang , "services/stop_radio_button")%>" onclick="goDispatch('stopPlay', 'dispatchPlayer')" styleClass="webRadioStopNow" title="Click here to stop" />-->
			<a title="<%=DicoTools.dico(dico_lang , "services/stop_radio_button")%>" href="javascript:;" class="webRadioStopNow" onclick="goDispatch('stopPlay', 'dispatchPlayer'); $('#srvWebRadioFreePlayer').submit()">&nbsp;</a>
			<hr class="spacer">

					
		</html:form>
	</div>
</div>

<script type="text/javascript">

	$("#srvWebRadioFreePlayer").submit(function(){
			return actionRadio();
	});


</script>