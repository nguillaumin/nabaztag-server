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

<bean:define property="isReg" name="mySrvHumeurForm" id="isReg"/>
<bean:define name="mySrvHumeurForm" property="checkListLang" id="checkListLang"/>

<div class="main-cadre-contener">
	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<!-- ******************************************** CONTENT ***************************************************** --> 
		<html:form action="/action/srvHumeurConfig" styleId="srvHumeurConfig" styleClass="srvConfigForm">
			<label style=" width:185px;"><%=DicoTools.dico(dico_lang , "srv_humeur/choose_frequency")%></label>
			<html:select name="mySrvHumeurForm" property="freqSrv" styleId="freqSrv" >
				<option value=""></option>
				<!--  FrequenceData -->
				<html:optionsCollection name="mySrvHumeurForm" property="freqSrvList" label="label" value="id"/>
			</html:select>
			
			<hr class="spacer" />
	
			<label><%=DicoTools.dico(dico_lang , "srv_humeur/choose_language")%></label>
				<div class='spoken-lang-service'>
				<ul class="inline-list">
							<logic:iterate name="mySrvHumeurForm" property="langList" id="langData">
								<bean:define name="langData" property="lang_id" id="lang_id"/>
								<bean:define name="langData" property="lang_title" id="lang_title"/>
								<bean:define name="langData" property="lang_iso_code" id="lang_iso_code"/>
								<bean:define name="langData" property="lang_type" id="lang_type"/>
								
								<logic:lessEqual name="lang_type" value="0">
									<li><label title="<%=lang_iso_code.toString()%>">
										<html:multibox property="checkListLang">
											<bean:write name="lang_id"/>
										</html:multibox>
										<bean:write name="lang_title"/>
									</label></li>
								</logic:lessEqual>
							</logic:iterate>
							</ul>
				</div>
			<hr class="spacer" />
				
			<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
			
				<div align="center">
					<%/* Supression */%>		
					<logic:notEqual name="isReg" value="0">
						<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_humeur/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_humeur/deactivate")%></html:button>
					</logic:notEqual>
					
					<%/* Creation */%>
					<logic:equal name="isReg" value="0">
						<html:submit property="activate" value="<%=DicoTools.dico(dico_lang , "srv_humeur/activate")%>" styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_humeur/activate")%></html:submit>	
					</logic:equal>
					
					<%/* Mise a jour */%>			
					<logic:notEqual name="isReg" value="0">
						<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_humeur/update")%>"  styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_humeur/update")%></html:submit>
					</logic:notEqual>
				</div>
	
		</html:form>
	</div>
</div>
<script type="text/javascript">
	$("form.srvConfigForm").submit(function(){
		return nablifeValidateHumeurConfig(<logic:equal name="isReg" value="1">true</logic:equal>);
	});
</script>