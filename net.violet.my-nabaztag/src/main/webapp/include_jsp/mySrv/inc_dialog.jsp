<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.datamodel.Application" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>
<%@page import="net.violet.platform.datamodel.Application"%>
<bean:define property="hasSomethingToDisplay" name="mySrvDialogForm" id="hasSomethingToDisplay"/>
<bean:define property="isMarried" name="mySrvDialogForm" id="isMarried"/>
<bean:define property="isWaiting" name="mySrvDialogForm" id="isWaiting"/>
<bean:define property="error_dne" name="mySrvDialogForm" id="error_dne"/>
<bean:define property="error_same" name="mySrvDialogForm" id="error_same"/>
<bean:define property="serviceName" name="mySrvDialogForm" id="serviceName" type="String"/>

<!-- Affichage des differentes Listes et demandes -->
<logic:greaterThan name="hasSomethingToDisplay" value="0">
	<logic:notEmpty name="mySrvDialogForm" property="refusedInfo">
		<bean:define name="mySrvDialogForm" property="refusedInfo" id="rInfo" />
		<bean:define name="rInfo" property="friend_name" id="rName" />
		<bean:define name="rInfo" property="notification" id="notification"/>
		<bean:define name="notification" property="id" id="nId" />
		<br/><span class="bigWord"><%=rName%></span> <%=DicoTools.dico(dico_lang , "srv_communion/refused_your_demand")%>
		<hr class="spacer" />
		<input type="button" class="genericBt" value="<%=DicoTools.dico(dico_lang , "srv_communion/refused_your_demand_button")%>" onclick="simpleAjaxRequest('srvDialogConfig.do?applicationId=<%=Application.NativeApplication.EARS_COMMUNION.getApplication().getId()%>&dispatch=delete&notificationId=<%=nId%>', function(){ endSrvDialog('accept') } )" />		
		<!-- <input type="button" class="genericBt" value="<%=DicoTools.dico(dico_lang , "srv_communion/refused_your_demand_button")%>" onclick="endSrvDialog('refused')" /> -->		
	</logic:notEmpty>
	
	<logic:notEmpty name="mySrvDialogForm" property="acceptedInfo">
		<bean:define name="mySrvDialogForm" property="acceptedInfo" id="rInfo" />
		<bean:define name="rInfo" property="friend_name" id="rName" />
		<bean:define name="rInfo" property="notification" id="notification"/>
		<bean:define name="notification" property="id" id="nId" />
		<br/><span class="bigWord"><%=rName%></span> <%=DicoTools.dico(dico_lang , "srv_communion/message_accept")%>
		<hr class="spacer" />
		<input type="button" class="genericBt" value="OK" onclick="simpleAjaxRequest('srvDialogConfig.do?applicationId=<%=Application.NativeApplication.EARS_COMMUNION.getApplication().getId()%>&dispatch=delete&notificationId=<%=nId%>', function(){ endSrvDialog('accept') } )" />		
	</logic:notEmpty>

	<logic:notEmpty name="mySrvDialogForm" property="separatedInfo">
		<bean:define name="mySrvDialogForm" property="separatedInfo" id="sInfo" />
		<bean:define name="sInfo" property="friend" id="friend"/>
		<bean:define name="friend" property="object_login" id="sName" />
		<bean:define name="friend" property="id" id="sId" />
		<bean:define name="sInfo" property="notification" id="notification"/>
		<bean:define name="notification" property="id" id="nId" />
		<span class="bigWord"><%=sName%></span><%=DicoTools.dico(dico_lang , "srv_communion/divorced_from_you")%>

		<hr class="spacer" />
		<input type="button" class="genericBt" value="<%=DicoTools.dico(dico_lang , "srv_communion/divorced_from_you_button")%>" onclick= "simpleAjaxRequest('srvDialogConfig.do?applicationId=<%=Application.NativeApplication.EARS_COMMUNION.getApplication().getId()%>&dispatch=delete&notificationId=<%=nId%>', function(){ endSrvDialog('accept') } )" />
	</logic:notEmpty>
	
	<logic:notEmpty name="mySrvDialogForm" property="cancelList">
		<logic:iterate name="mySrvDialogForm" property="cancelList" id="srvDialogData">
			<bean:define name="srvDialogData" property="friend" id="vObjectData"/>
			<bean:define name="vObjectData" property="id" id="cId"/>
			<bean:define name="vObjectData" property="object_login" id="cName"/>
			<bean:define name="srvDialogData" property="notification" id="notification"/>
			<bean:define name="notification" property="id" id="nId" />
			<span class="bigWord"><%=cName%></span> <%=DicoTools.dico(dico_lang , "srv_communion/cancelled_his_demand")%>
			<hr class="spacer" />
			<input type="button" class="genericBt" value="<%=DicoTools.dico(dico_lang , "srv_communion/cancelled_his_demand_button")%>" onclick= "simpleAjaxRequest('srvDialogConfig.do?applicationId=<%=Application.NativeApplication.EARS_COMMUNION.getApplication().getId()%>&dispatch=delete&notificationId=<%=nId%>', function(){ endSrvDialog('accept') } )" />
			
		</logic:iterate>
	</logic:notEmpty>
	
	<logic:notEmpty name="mySrvDialogForm" property="waitingList">
		<hr class="spacer" />	
		<logic:iterate name="mySrvDialogForm" property="waitingList" id="srvDialogData">
			<bean:define name="srvDialogData" property="object" id="vObjectData"/>
			<bean:define name="vObjectData" property="id" id="wId"/>
			<bean:define name="vObjectData" property="object_login" id="wName"/>
			<input type="button" class="genericBt" value="<%=DicoTools.dico(dico_lang , "srv_communion/button_accept")%>" onclick="startSrvDialogAction('srvDialogConfig.do?applicationId=<%=Application.NativeApplication.EARS_COMMUNION.getApplication().getId()%>&dispatch=accept&friendId=<%=wId%>&friendName=<%=wName%>', function(){ endSrvDialog('accept') } )" />
			<span class="bigWord"><%=wName%></span> <%=DicoTools.dico(dico_lang , "srv_communion/proposes_to_you")%>
			<hr class="spacer" />	
		</logic:iterate>
		<div align="center"><input type="button" class="genericDeleteBt" value="<%=DicoTools.dico(dico_lang , "srv_communion/button_refuse_all")%>" onclick= "simpleAjaxRequest('srvDialogConfig.do?applicationId=<%=Application.NativeApplication.EARS_COMMUNION.getApplication().getId()%>&dispatch=denyAll', function(){ endSrvDialog('accept') } )" /></div>
	</logic:notEmpty>
	
</logic:greaterThan>
		
<logic:lessEqual name="hasSomethingToDisplay" value="0">
	<div class="main-cadre-contener">
		<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
		<div class="main-cadre-content">
			<hr class="spacer"/>
			<!-- ******************************************** CONTENT ***************************************************** --> 
			
			<!-- Affichage de la page de gestion du service -->
			<logic:equal name="hasSomethingToDisplay" value="0">
			
				<script type="text/javascript">
					gSrvErrorFlag = true;	// on force le flag a true, pour �viter les messages
				</script>		
				<html:form action="action/srvDialogConfig" styleId="srvDialogConfig" styleClass="srvConfigForm" >
					<bean:define id="subscriptionId" name="mySrvDialogForm" property="subscriptionId" />
					<html:hidden property ="subscriptionId" value="<%=subscriptionId.toString() %>" />
					<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
					<logic:equal name="error_dne" value="1">
						<script type="text/javascript">		
							gSrvErrorFlag = true;
							var errorMsg = msg_txt['dialog_bad_pseudo'];
							customAlertN(errorMsg);
						</script>
					</logic:equal>
					
					<logic:equal name="error_same" value="1">
						<script type="text/javascript">		
							gSrvErrorFlag = true;
							var errorMsg = msg_txt['dialog_cest_toi'];
							customAlertN(errorMsg);				
						</script>
					</logic:equal>
					
					<logic:equal name="isMarried" value="0">
						<logic:equal name="isWaiting" value="0">
							<%=DicoTools.dico(dico_lang , "srv_communion/enter_name")%>
							<html:text name="mySrvDialogForm" property="friendName" styleId="friendName"/>
							<html:submit property="activate" value="<%=DicoTools.dico(dico_lang , "srv_communion/button_marry_me")%>" styleClass="genericBt" onclick="goDispatch('add', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_communion/button_marry_me")%></html:submit>	
						</logic:equal>
					</logic:equal>
					
					<logic:equal name="isMarried" value="1">
						<bean:define name="mySrvDialogForm" property="friendName" id="friendName"/>
						<%=DicoTools.dico(dico_lang , "srv_communion/youre_married_with")%><span class="bigWord"><%=friendName%></span>
						<hr class="spacer" />
						<html:submit property="delete" value="<%=DicoTools.dico(dico_lang , "srv_communion/button_divorce")%>" styleClass="genericBt" onclick="goDispatch('delete', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_communion/button_divorce")%></html:submit>	
					</logic:equal>
					
					<logic:equal name="isWaiting" value="1">
						<bean:define name="mySrvDialogForm" property="friendName" id="friendName" />
						<%=DicoTools.dico(dico_lang , "srv_communion/demand_done")%> <span class="bigWord"><%=friendName%></span>
						<hr class="spacer" />
						<html:submit property="cancel" value="<%=DicoTools.dico(dico_lang , "srv_communion/button_changed_mind")%>" styleClass="genericBt" onclick="goDispatch('cancel', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_communion/button_changed_mind")%></html:submit>	
					</logic:equal>
					
				</html:form>
			</logic:equal>

		</div>
	</div>

			<script type="text/javascript">
				$("#contentSrvConfig form").submit(function(){
					return nablifeValidateDialogConfig(); 
				});
			</script>

</logic:lessEqual>