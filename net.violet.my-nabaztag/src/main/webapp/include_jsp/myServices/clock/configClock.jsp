<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>

<% Lang dico_lang = SessionTools.getLangFromSession(session,request);%>

<%-- Si l'action me renvoit un message d'erreur ou de succès --%>
<ul class="general-msg">

	<logic:messagesPresent message="errors" property="choix_type_horloge">
		<li class="error"><html:errors property="choix_type_horloge"/></li>
	</logic:messagesPresent>  

	<logic:messagesPresent message="errors" property="choix_langue">
		<li class="error"><html:errors property="choix_langue"/></li>
	</logic:messagesPresent> 
	
	<logic:messagesPresent message="errors" property="registerSucceed"> 
		<li><html:errors property="registerSucceed"/></li>
	</logic:messagesPresent>
</ul>

<div id="clockConfig-holder" >
<div id="clockConfig" class="main-cadre-contener">
	
	<div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2>
	</div>
	

	<bean:define property="isReg" name="mySrvClockForm" id="isReg"/>

	<div class="main-cadre-content">
		<%-- ************************************************************************************************************* --%>

	<bean:define id="userLang" name="mySrvClockForm" property="userLang" />
	
	<html:form action="/action/srvClockConfig" styleId="srvHorlogeFreeConfig" styleClass="srvConfigForm">

	<html:hidden property ="userLang" value="<%=userLang.toString() %>" />	
	
	<label><%=DicoTools.dico(dico_lang , "srv_clock/type")%></label>
			<ul class="inline-list" >
				<logic:iterate name="mySrvClockForm" property="listCategories" id="ClockCategoriesData">
					<bean:define name="ClockCategoriesData" property="id" id="type_id"/>
					<bean:define name="ClockCategoriesData" property="dico_key" id="type_dico_key"/>

						<li>
							
								<html:multibox name="mySrvClockForm" property="checkListClockType">
									<bean:write name="type_id"/>
								</html:multibox>
								
								<%=DicoTools.dico(dico_lang , type_dico_key.toString())%>
							
						</li>
				</logic:iterate>
			</ul>
	
	<hr class="spacer" />
	
	<label><%=DicoTools.dico(dico_lang , "srv_clock/language")%></label>
	

	<ul  class="inline-list"  >
		
		<logic:iterate name="mySrvClockForm" property="langList" id="langData">
			<bean:define name="langData" property="lang_id" id="lang_id"/>
			<bean:define name="langData" property="lang_title" id="lang_title"/>
			<bean:define name="langData" property="lang_type" id="lang_type"/>
			
			<logic:lessEqual name="lang_type" value="0">
				<li>
					<html:multibox property="checkListLang">
						<bean:write name="lang_id"/>
					</html:multibox>
					<bean:write name="lang_title"/>
				</li>
			</logic:lessEqual>
			
		</logic:iterate>
		
	</ul>

	
	<hr class="spacer" />

	<div align="center">

	<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />	
		
		<logic:equal name="isReg" value="1">
		<%-- Supression --%>			
		<html:button styleClass="genericDeleteBt" property ="delete" value="<%=DicoTools.dico(dico_lang , "srv_clock/deactivate")%>" onclick="serviceDelete();" />
		
		<%-- Mise a jour --%>	
		<html:submit styleClass="genericBt" property ="submit" value="<%=DicoTools.dico(dico_lang , "srv_clock/update")%>" onclick="goDispatch('update', 'dispatchConfig')" /> 
		
		</logic:equal>
	
		<logic:notEqual name="isReg" value="1" >	
		<%-- Creation --%>				
		<html:submit styleClass="genericBt" property ="create" value="<%=DicoTools.dico(dico_lang , "srv_clock/activate")%>" onclick="goDispatch('create', 'dispatchConfig');" /> 
		</logic:notEqual>

	</div>
			
	</html:form>



		<%-- ************************************************************************************************************* --%>	
	</div>
</div>

</div>

<script type="text/javascript">

	$("#srvHorlogeFreeConfig").ajaxForm({
				target	: "#clockConfig-holder",
				beforeSubmit: function(a,f,o){
				var valid = nablifeValidateHorlogeFreeConfig(a,f,o);
				if (valid) {
					$("#srvHorlogeFreeConfig").parents("#clockConfig").block();
				}
				return valid;
			},
			success: function(data) {
				$("#srvHorlogeFreeConfig").parents("#clockConfig").unblock();
				msgHandling.errorMsgShow();
				blocLoad("bloc-MyServices");
			}
		});
		
</script>
