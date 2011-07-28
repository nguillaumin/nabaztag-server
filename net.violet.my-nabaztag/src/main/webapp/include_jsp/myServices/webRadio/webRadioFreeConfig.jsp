<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@page import="net.violet.platform.datamodel.Lang"%>
<%@ page import="net.violet.mynabaztag.form.MySrvWebRadioFreeConfigForm" %>
<%
	response.setContentType("text/html;charset=UTF-8");
%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<bean:define name="mySrvWebRadioFreeConfigForm" property="applicationId" id="applicationId" type="Integer"/>
<bean:define name="mySrvWebRadioFreeConfigForm" property="subscriptionId" id="subscriptionId" type="Integer"/>
<%
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
	Map<String, String> srv = new HashMap<String, String>();

	if (applicationId>0)
		srv = StaticTools.getServiceFromApplication(applicationId.toString(), dico_lang);
	if (subscriptionId>0)
		srv = StaticTools.getServiceFromSubscription(subscriptionId.toString(), dico_lang);
%>
<bean:define name="mySrvWebRadioFreeConfigForm" property="wradioId" id="wradioId" type="Integer"/>
<bean:define name="mySrvWebRadioFreeConfigForm" property="dispatch" id="dispatch" type="String"/>
<bean:define name="mySrvWebRadioFreeConfigForm" property="isRegistered" id="isRegistered" type="String"/>
<bean:define name="mySrvWebRadioFreeConfigForm" id="mySrvWebRadioFreeConfigForm" type="net.violet.mynabaztag.form.MySrvWebRadioFreeConfigForm"/>


<div class="main-cadre-contener">
	
	<div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2>
	</div>
	

	<div class="main-cadre-content">
		<%-- ************************************************************************************************************* --%>
 		
 		

			<html:form action="/action/srvWebRadioFreeConfig" styleId="srvWebRadioFreeConfig" styleClass="srvConfigForm">
 				<div class="srv-inner" >
					<html:hidden property ="wradioId" value="<%=wradioId.toString() %>" />	
					<html:hidden property ="applicationId" value="<%=applicationId.toString() %>" />	
					
						<logic:iterate name="mySrvWebRadioFreeConfigForm" property="listHours" id="listHours">
							<bean:define id="agenda_id" name="listHours" property="agenda_id"/>
							<bean:define id="agenda_key" name="listHours" property="agenda_key" type="String"/>
							
							<bean:define id="modulo" value="<%= String.valueOf(Integer.parseInt(agenda_id.toString())%2) %>"/>
				
							<div class="srv-line-horaire 
								<logic:equal name="modulo" value="0" >srv-line-odd</logic:equal><logic:equal name="modulo" value="1" >srv-line-even</logic:equal>
							" >
								
								<div class="srv-line-delete" >
									<a class="suppr" href="javascript:;" onclick="srvClearReveil(<%=agenda_id %>);"><span>x</span></a>
								</div>
								<div class="srv-line-day" >
									<%=DicoTools.dico(dico_lang , "services/"+agenda_key)%>
								</div>
								<div class="srv-line-time" >
									<input id="time<%=agenda_id %>" name="checkListHours" type="hidden" class="hourPicker" value="<%=mySrvWebRadioFreeConfigForm.getCheckListHours()[Integer.parseInt(agenda_id.toString())-1]%>" />
								</div>
								<div class="srv-line-duration" >
									<%=DicoTools.dico(dico_lang , "services/ListeningPeriod")%>
									<html:select name="mySrvWebRadioFreeConfigForm" property="listeningPeriodeSelected" value="<%=mySrvWebRadioFreeConfigForm.getListeningPeriodeSelected()[Integer.parseInt(agenda_id.toString())-1] %>">
										<html:optionsCollection name="mySrvWebRadioFreeConfigForm" property="periodList" label="label" value="id"/>
									</html:select>
								</div>
							
							</div>
							
						</logic:iterate>
				</div>
				
				<bean:define id="srv_vocal" value="<%=srv.get("srv_vocal")%>"/>
				
				<logic:notEqual name="srv_vocal" value="NOLOC">
				<hr class="spacer">
				
				<div>
					<label><%=DicoTools.dico(dico_lang , "services/vocal")%></label>
					<span style="font-size:16px; font-weight:bold;"><%=srv.get("srv_vocal")%></span> <%=DicoTools.dico(dico_lang , "srv_all/reco_link")%>
				</div>
				</logic:notEqual>
						
				<hr class="spacer">
				<div align="center">
				<%-- default value for the dispatch variable. Has to be modified through JAVAScript for any action by loading to take place --%>
				<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
				
				<logic:notEmpty name="isRegistered">
						<%-- Delete --%>			
						<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%></html:button>
						<%-- Mise a jour --%>	
						<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_clock/update")%>" styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_clock/update")%></html:submit>	
				</logic:notEmpty>
				<logic:empty name="isRegistered" >
					<%-- Activate --%>				
					<html:submit property="submit" value="<%=DicoTools.dico(dico_lang , "srv_clock/activate")%>"  styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_clock/activate")%></html:submit>
				</logic:empty>	
				</div>		
			</html:form>
		<%-- ************************************************************************************************************* --%>	
	</div>
</div>

<script type="text/javascript">

	$("input.hourPicker").hourSelect_Init( $("#_user_24").val(), false );
	
	$("#srvWebRadioFreeConfig").submit(function(){
		return nablifeValidateWebRadioFreeConfig(<logic:notEmpty name="dispatch" >true</logic:notEmpty>);		
	});

	<logic:equal name="mySrvWebRadioFreeConfigForm" property="isV1" value="true">
		setTimeout(function(){
			blockUI("show", "<%=DicoTools.dico(dico_lang, "srv_podcast/notAV2")%>", "div.mainTabBody");
		}, 50);
	</logic:equal>
	
</script>