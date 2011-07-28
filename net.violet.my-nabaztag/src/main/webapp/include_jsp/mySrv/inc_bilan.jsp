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

<bean:define property="serviceName" name="mySrvBilanForm" id="serviceName" type="String"/>
<bean:define property="isReg" name="mySrvBilanForm" id="isReg"/>

<div class="main-cadre-contener">
	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<!-- ******************************************** CONTENT ***************************************************** --> 
		<html:form action="/action/srvBilanConfig" styleId="srvBilanConfig" styleClass="srvConfigForm">
			
			<label><%=DicoTools.dico(dico_lang , "srv_bilan/day")%></label>
			<html:select name="mySrvBilanForm" property="freqSrv" styleId="freqSrv" style="width:120px;">
				<option value=""></option>		
				<!--  FrequenceData -->	
				<html:optionsCollection name="mySrvBilanForm" property="freqSrvList" label="label" value="id"/>
			</html:select>
			
			<hr class="spacer" />
			
			<label><%=DicoTools.dico(dico_lang , "srv_bilan/time")%></label>
			<bean:define name="mySrvBilanForm" property="horraire" id="heures" />
			<input id="time" name="horraire" type="hidden" class="hourPicker" value="<%=heures%>" />
	
			<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
			
			
			<div align="center">
				<%/* Supression */%>		
				<logic:notEqual name="isReg" value="0">
					<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_bilan/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_bilan/deactivate")%></html:button>
				</logic:notEqual>
				
				<%/* Creation */%>
				<logic:equal name="isReg" value="0">
					<html:submit property="activate" value="<%=DicoTools.dico(dico_lang , "srv_bilan/activate")%>" styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_bilan/activate")%></html:submit>	
				</logic:equal>
				
				<%/* Mise a jour */%>			
				<logic:notEqual name="isReg" value="0">
					<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_bilan/update")%>"  styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_bilan/update")%></html:submit>
				</logic:notEqual>
			</div>
	
		</html:form>
	
	</div>	
</div>

<script type="text/javascript">
	$("input.hourPicker").hourSelect_Init(false );
	
	$("#contentSrvConfig form").submit(function(){
		return nablifeValidateBilanConfig(<logic:equal name="isReg" value="1">true</logic:equal>); 
	});
</script>