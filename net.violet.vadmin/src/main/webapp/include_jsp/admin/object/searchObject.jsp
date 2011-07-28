<%@ page pageEncoding="UTF-8" %>
<%@ page import="net.violet.vadmin.objects.data.ObjectData"%>
<%@ page import="net.violet.vadmin.objects.data.UserData"%>
<%@ page import="net.violet.vadmin.objects.data.ServiceData"%>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ include file="/include_jsp/admin/header.jsp" %>

<%@ include file="/include_jsp/admin/navigation.jsp" %>

<div id="content">
	
	<div id="waiting"></div>
	
    <p class="clear error"><html:errors property="exception" /></p>
	<p class="clear error"><html:errors property="serialEmpty" /></p>
   	<p class="clear error"><html:errors property="serialWithoutObject" /></p>
    <p class="clear error"><html:errors property="serialInTagTmp" /></p>
    <p class="clear error"><html:errors property="mailEmpty" /></p>
    <p class="clear error"><html:errors property="mailWithoutUser" /></p>
    <p class="clear error"><html:errors property="existingSerial" /></p>
    <p class="clear error"><html:errors property="existingMail" /></p>
    <p class="clear error"><html:errors property="invalidSerial" /></p>
    <p class="clear error"><html:errors property="invalidMail" /></p>
    <p class="clear error"><html:errors property="objectUndeleted" /></p>
    <p class="clear error"><html:errors property="objectUnallowed" /></p>
    <p class="clear error"><html:errors property="userUnallowed" /></p>
   	<p style="color:green; font-weight:bold;"><html:errors property="updated" /></p>
   	<p style="color:green; font-weight:bold;"><html:errors property="deleted" /></p>
		    
	<!-- FORM -->
		<fieldset>
		<legend><bean:message key="label.search_object"/></legend>
			<html:form action="/action/adminSearchObject"  styleId="SearchObjectConfig" styleClass="srvConfigForm">
				<html:hidden styleId="dispatchConfig" property ="dispatch" />
				
				<div class="field">
					<span><bean:message key="label.mac_address"/></span>
					<input type="text" id="macAddress" name="macAddress" value="" />
				</div>
		    
			    <div class="call2action">
			      <html:submit property="submit" value="Search" onclick="goDispatch('searchByMacAddress', 'dispatchConfig')">Search</html:submit>
			    </div>
							
				<div class="field">
					<span><bean:message key="common.email"/></span>
					<input type="text" id="mailAddress" name="mailAddress" value="" />
				</div>
			    
			    <div class="call2action">
			      <html:submit property="submit" value="Search" onclick="goDispatch('searchByMailAddress', 'dispatchConfig')">Search</html:submit>
			    </div>
				
				<div class="field">
					<span><bean:message key="common.username"/></span>
					<input type="text" id="userName" name="name" value="" />
				</div>
			    
			    <div class="call2action">
			    	<html:submit property="submit" value="Search" onclick="goDispatch('searchByUserName', 'dispatchConfig')">Search</html:submit>
			    </div>
		    
			</html:form>
		</fieldset>
	<!-- END FORM -->

	
	<!-- LIST OBJECTS -->
	<logic:equal name="myAdminSearchObjectForm"  property="display" value="displayList">
	
		<fieldset>
		<legend><bean:message key="label.user_information"/></legend>
			<nested:form action="/action/adminSearchObject"  styleId="updateMailSearchConfig" styleClass="updateSearchForm">
				
				<nested:hidden property ="mailAddress"><nested:write property="userData.user_mail" /></nested:hidden>
				<nested:hidden styleId="dispatchConfigUpdateUserMail" property ="dispatch" />
				<nested:hidden property="userData.user_id" />
				<nested:hidden property="userData.user_firstName" />
				<nested:hidden property="userData.user_lastName" />
				<nested:hidden property="userData.user_password" />
				<nested:hidden property="userData.user_lang" />
				<nested:hidden property="userData.user_creationDate" />
				
				<div class="field info">
					<span><bean:message key="common.username"/></span>
					<nested:write property="userData.user_firstName"/>&nbsp;<nested:write property="userData.user_lastName"/>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.email"/></span>
					<nested:text property="userData.user_mail" />
					<nested:submit property="submit" styleClass="update" value="update" onclick="goDispatchWithConfirmation('updateUserMail', 'dispatchConfigUpdateUserMail', false, this)" />
					<br class="clear"/>
				</div>
			
					<div class="field info">
						<span><bean:message key="common.password"/></span>
						<nested:write property="userData.user_password"/>
						<br class="clear"/>
					</div>
				
					<div class="field info">
						<span><bean:message key="common.language"/></span>
						<nested:write property="userData.user_lang"/>
						<br class="clear"/>
					</div>
					
					<div class="field info">
						<span><bean:message key="label.registering_date"/></span>
						<nested:write property="userData.user_creationDate"/>
						<br class="clear"/>
					</div>
				<div class="call2action">
					<nested:submit property="submit" styleClass="delete" value="delete messages" onclick="goDispatchWithConfirmation('deleteMessages', 'dispatchConfigUpdateUserMail', true, this)" />
					<nested:submit property="submit" styleClass="delete" value="delete the user" onclick="goDispatchWithConfirmation('deleteUser', 'dispatchConfigUpdateUserMail', true, this)" />
				</div>
			</nested:form>
    </fieldset>
    
	<nested:notEmpty name="myAdminSearchObjectForm"  property="userData.user_objects">
		<fieldset>
		<legend><bean:message key="label.objects_list"/></legend>
			
			<div class="results">
				<nested:form action="/action/adminSearchObject"  styleId="MailSearchConfig" styleClass="deleteSearchForm" >
					<nested:hidden property ="mailAddress"><nested:write property="userData.user_mail" /></nested:hidden>
					<nested:hidden styleId="dispatchConfigDeleteObject" property ="dispatch" />
					<table>
						<thead>
							<tr>
								<th><bean:message key="common.name"/></th>
								<th><bean:message key="common.hardware"/></th>
								<th><bean:message key="common.serial"/></th>
								<th><bean:message key="label.detail"/></th>
								<th><bean:message key="label.delete"/></th>
							</tr>
						</thead>
						<tbody>
							<nested:iterate name="myAdminSearchObjectForm" property="userData.user_objects" id="test" >
					
								<tr>
									<td><nested:write property="object_login"/></td>
									<td><nested:write property="object_hardware"/></td>
									<td><nested:write property="object_serial"/></td>
									<td><a href="adminSearchObject.do?dispatch=searchByMacAddress&macAddress=<nested:write property="object_serial"/>"><bean:message key="label.view_modify"/></a></td>
									<td>
										<html:multibox property="objectChecked">
											<nested:write property="object_id" />
										</html:multibox>
									</td>
								</tr>
							</nested:iterate>
						</tbody>
					</table>
					<div class="call2action">
					  <nested:submit property="submit" styleClass="delete" value="delete all" onclick="goDispatchWithConfirmation('deleteObject', 'dispatchConfigDeleteObject', true, this)" />
					</div>
				</nested:form>	
			</div>
			</fieldset>
		</nested:notEmpty>
	
		<logic:empty name="myAdminSearchObjectForm" property="userData.user_objects">
			<p class="error"><bean:message key="message.user_without_object"/></p>
		</logic:empty>

	</logic:equal>
	<!-- END LIST OBJECTS -->	


	<!-- USER LIST -->
	<logic:equal name="myAdminSearchObjectForm"  property="display" value="displayUserList">
		<div class="results">
			<fieldset>
			<legend><bean:message key="label.users_list"/></legend>
			<table>
				<thead>
					<tr>
						<th><bean:message key="common.firstname"/></th>
						<th><bean:message key="common.lastname"/></th>
						<th><bean:message key="common.email"/></th>
						<th><bean:message key="label.view_modify"/></th>
					</tr>
				</thead>
				<tbody>
					<logic:iterate name="myAdminSearchObjectForm" property="listUserData" id="userList">
						<bean:define id="userFirst" name="userList" property="user_firstName" />
						<bean:define id="userLast" name="userList" property="user_lastName" />
						<bean:define id="userMail" name="userList" property="user_mail" />
			
						<tr>
							<td><%=userFirst%></td>
							<td><%=userLast%></td>
							<td><%=userMail%></td>
							<td><a href="adminSearchObject.do?dispatch=searchByMailAddress&mailAddress=<%=userMail%>"><bean:message key="label.view_modify"/></a></td>
						</tr>
					</logic:iterate>
				</tbody>
			</table>
			</fieldset>
		</div>
	</logic:equal>
	<!-- END USER LIST -->	

	<!-- OBJECT DETAIL -->
	<logic:equal name="myAdminSearchObjectForm"  property="display" value="displayDetail">
		
		<nested:form action="/action/adminSearchObject"  styleId="updateSearchConfig" styleClass="updateSearchForm" >
			<html:hidden styleId="dispatchConfigUpdate" property ="dispatch" />
			<nested:hidden property="userData.user_id" />
			<nested:hidden property="userData.user_mail" />	
			
			<nested:hidden property="objectData.object_id" />
			<nested:hidden property="objectData.object_login" />
			<nested:hidden property="objectData.object_hardware" />
			<nested:hidden property="objectData.object_ping" />
			<nested:hidden property="objectData.object_timezone" />
			<nested:hidden property="objectData.object_private" />
			<nested:hidden property="objectData.object_visible" />
			
			<fieldset>
				<nested:hidden property="objectData.object_id" />
				<legend><bean:message key="application.info"/></legend>
				
				<div class="field info">
					<span><bean:message key="common.login"/></span>
					<nested:write property="objectData.object_login"/>
					<br class="clear"/>
				</div>
						
				<div class="field info">
					<span><bean:message key="common.hardware"/></span>
					<nested:write property="objectData.object_hardware"/>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.serial"/></span>
					<nested:text property="objectData.object_serial" />
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.last_ping"/></span>
					<nested:write property="objectData.object_ping"/>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.timezone"/></span>
					<nested:write property="objectData.object_timezone"/>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.private"/></span>
					<nested:write property="objectData.object_private"/>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.visibility"/></span>
					<nested:write property="objectData.object_visible"/>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.username"/></span>
					<nested:write property="userData.user_firstName"/>&nbsp;<nested:write property="userData.user_lastName"/>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.email"/></span>
					<nested:write property="userData.user_mail"/>
					&nbsp;<a href="adminSearchObject.do?dispatch=searchByMailAddress&mailAddress=<nested:write property="userData.user_mail"/>"><bean:message key="label.view_modify"/></a>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.password"/></span>
					<nested:write property="userData.user_password"/>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="common.language"/></span>
					<nested:write property="userData.user_lang"/>
					<br class="clear"/>
				</div>
				
				<div class="field info">
					<span><bean:message key="label.registering_date"/></span>
					<nested:write property="userData.user_creationDate" />
					<br class="clear"/>
				</div>
								
				<a href="adminSearchObject.do?dispatch=findServices&objectId=<nested:write property="objectData.object_id"/>"><bean:message key="label.check_service"/></a>
				<nested:submit property="submit" styleClass="delete" value="delete messages" onclick="goDispatchWithConfirmation('deleteMessages', 'dispatchConfigUpdate', true, this)" />
				<nested:submit property="submit" styleClass="update" value="update" onclick="goDispatchWithConfirmation('updateObject', 'dispatchConfigUpdate', false, this)" />
			</fieldset>
		</nested:form>
		
		<nested:form action="/action/adminSearchObject"  styleId="deleteSearchConfig" styleClass="deleteSearchForm">
			<nested:hidden styleId="dispatchConfigDelete" property ="dispatch" />
			<nested:hidden property="userData.user_id" />
			<nested:hidden property="userData.user_firstName" />
			<nested:hidden property="userData.user_lastName" />
			<nested:submit property="submit" styleClass="back" value="delete" onclick="goDispatchWithConfirmation('deleteUser', 'dispatchConfigDelete', true, this)" />
		</nested:form>
		
	</logic:equal>
	<!-- END OF OBJECT DETAIL -->

	<!-- LIST SERVICES -->
		
		<logic:equal name="myAdminSearchObjectForm"  property="display" value="displayServices">
			<fieldset>
				<legend><bean:message key="label.change_rss_language"/></legend>
				<logic:iterate name="myAdminSearchObjectForm" property="listServicesData" id="theServiceData" type="ServiceData">
					<bean:define id="serviceId" name="theServiceData" property="id" />
					<bean:define id="serviceLang" name="theServiceData" property="language" />
					<bean:define id="serviceUrl" name="theServiceData" property="url" />
					<bean:define id="objectId" name="myAdminSearchObjectForm" property="objectId" />
					
					<html:form action="/action/adminSearchObject"  styleId="changeLangSearchConfig" styleClass="changeLangSearchForm">
						<html:hidden property ="dispatch" value="changeLang"/>	
						<html:hidden property ="actionId" value="<%=serviceId.toString()%>" />
						<html:hidden property ="objectId" value="<%=objectId.toString()%>" />
					
						- <%=serviceUrl %><span>&nbsp;en&nbsp;</span>
					
						<html:select name="myAdminSearchObjectForm" property="language" value="<%=serviceLang.toString() %>">
							<html:optionsCollection name="myAdminSearchObjectForm" property="langList" label="name" value="iso_code" />
						</html:select>
					
						<div class="call2action">
							<html:submit property="submit" styleClass="update" value="update" />
						</div>
					</html:form>
		
					<br />		
				</logic:iterate>
			</fieldset>
		</logic:equal>	
		
	<!-- END OF LIST SERVICES -->
	
<script language="javascript">
	function goDispatch(where, dispatchField){
		document.getElementById(dispatchField).value = where;
	}
	
	function goDispatchWithConfirmation(where, dispatchField, processing, current){
		if(confirmDeletion()){
			if(processing){
				current.value='Processing. Please wait...';
				current.disabled = true;
				document.getElementById('waiting').innerHTML = "<p class='clear error'>Please wait during the processing.</p>";
			}
			goDispatch(where, dispatchField);
		}
	}
	
	function confirmDeletion(){
		if (window.confirm('Do you really want to do that ?')) {
				return true;
		}
		return false;
	}
	
</script>

<p class="back"><a href="adminHome.do">Back to home page</a></p>
</div>
<!-- FIN DIV CONTENT -->

<%@ include file="/include_jsp/admin/footer.jsp" %>