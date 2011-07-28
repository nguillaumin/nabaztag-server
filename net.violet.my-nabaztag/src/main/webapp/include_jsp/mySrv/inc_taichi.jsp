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

<bean:define property="isReg" name="mySrvTaichiForm" id="isReg"/>
<div class="main-cadre-contener">
	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<!-- ******************************************** CONTENT ***************************************************** --> 
		<html:form action="/action/srvTaichiConfig" styleId="srvTaichiConfig" styleClass="srvConfigForm">
			
			<label style="width:200px;" ><%=DicoTools.dico(dico_lang , "srv_taichi/choose_frequency")%></label>
			<html:select name="mySrvTaichiForm" property="freqSrv" styleId="freqSrv">
				<option value=""></option>
				<!--  FrequenceData -->
				<html:optionsCollection name="mySrvTaichiForm" property="freqSrvList" label="label" value="id"/>
			</html:select>
			
			<html:hidden name="mySrvTaichiForm" property="add" value="1"/>	
			
			<hr class="spacer" />
			
			<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
			
			<div align="center">
				<%/* Supression */%>		
				<logic:notEqual name="isReg" value="0">
					<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_taichi/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_taichi/deactivate")%></html:button>
				</logic:notEqual>
				
				<%/* Creation */%>
				<logic:equal name="isReg" value="0">
					<html:submit property="activate" value="<%=DicoTools.dico(dico_lang , "srv_taichi/activate")%>" styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_taichi/activate")%></html:submit>	
				</logic:equal>
				
				<%/* Mise a jour */%>			
				<logic:notEqual name="isReg" value="0">
					<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_taichi/update")%>"  styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_taichi/update")%></html:submit>
				</logic:notEqual>
			</div>
						
		</html:form>
	
	</div>
</div>

<script language="javascript">
	$("#contentSrvConfig form").submit(function(){
		return nablifeValidateTaichiConfig(<logic:equal name="isReg" value="1">true</logic:equal>);
	});
</script>