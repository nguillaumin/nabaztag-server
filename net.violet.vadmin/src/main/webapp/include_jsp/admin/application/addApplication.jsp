<%@ page pageEncoding="UTF-8" %>

<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/admin/header.jsp" %>

<%@ include file="/include_jsp/admin/navigation.jsp" %>

<!-- DEBUT DIV CONTENT -->
<div id="content">

	<p class="clear error"><html:errors property="emptyName" /></p>
	<p class="clear error"><html:errors property="emptyTTS" /></p>
	<p class="clear error"><html:errors property="emptyUrl" /></p>	
	<p class="clear error"><html:errors property="emptyTitle" /></p>	
	<p class="clear error"><html:errors property="emptyDescription" /></p>
	<p class="clear error"><html:errors property="emptyLanguages" /></p>
	<p class="clear error"><html:errors property="emptyHardware" /></p>
	<p class="clear error"><html:errors property="incorrectSize" /></p>
	<p class="clear error"><html:errors property="ttsFailed" /></p>
	<p class="clear error"><html:errors property="existingName" /></p>
	<p class="clear error"><html:errors property="badUrl" /></p>
	<p class="clear error"><html:errors property="uploadFailed" /></p>
	<p class="clear error"><html:errors property="createFailed" /></p>
	<p class="clear error"><html:errors property="noFile" /></p>
	<p class="clear error"><html:errors property="invalidParameter" /></p>


	<html:form action="/action/adminAddApplication"	styleId="SearchLangByType" styleClass="srvConfigForm"  enctype="multipart/form-data"> 
		<bean:define id="trueFalse" name="myAdminApplicationForm" property="trueFalse" />
		<bean:define id="categories" name="myAdminApplicationForm" property="categoriesList" />
		<bean:define id="voices" name="myAdminApplicationForm" property="voices" />
		<bean:define id="service" name="myAdminApplicationForm" property="namesList" />
		<bean:define id="name" name="myAdminApplicationForm" property="name" />
		<bean:define id="url" name="myAdminApplicationForm" property="url" />
		<bean:define id="title" name="myAdminApplicationForm" property="title" />
		<bean:define id="howTo" name="myAdminApplicationForm" property="howTo" />
		<bean:define id="description" name="myAdminApplicationForm" property="description" />
		<bean:define id="TTSText" name="myAdminApplicationForm" property="TTSText" />
		<bean:define id="TTSLanguage" name="myAdminApplicationForm" property="TTSLanguage" />

		<html:hidden styleId="dispatchConfigAdd" property="dispatch" />
		
		<fieldset><legend><bean:message key="application.add"/></legend>
			
			<fieldset><legend><bean:message key="application.info"/></legend>
				<label><bean:message key="common.name"/><bean:message key="common.column"/></label>
				
				<html:select styleId="serviceId" property="nameService" onchange="checkHidding()">
					<html:options collection="service" property="value" labelProperty="label"/> 
				</html:select>
				<input type="text" id="appliName" name="name" value="<%=name%>" />
				<br />
				
				<div id="tts">
					<label><bean:message key="common.voice"/><bean:message key="common.column"/></label>
					<input type="text" id="TTSText" name="TTSText" value="<%=TTSText%>" />
					<label>&nbsp;<bean:message key="common.in"/></label>
					<html:select styleId="voiceId" property="TTSLanguage">
						<html:options collection="voices" property="id" labelProperty="title"/> 
					</html:select>
					<a href="#" onclick="listen()">Listen</a>
					<br />
				</div>
				
				<label><bean:message key="common.category"/><bean:message key="common.column"/></label>
				<html:select property="cat">
					<html:options collection="categories" property="value" labelProperty="label"/> 
				</html:select>
				<br />
		
				<label><bean:message key="common.visibility"/><bean:message key="common.column"/></label>
				<html:select property="isVisible">
					<html:options collection="trueFalse" property="value" labelProperty="label"/> 
				</html:select>
				<br />
			</fieldset>
	
			<fieldset><legend><bean:message key="application.info"/></legend>
		        <label><bean:message key="common.languages"/><bean:message key="common.column"/></label>
				<br />
				<logic:iterate name="myAdminApplicationForm" property="langList" id="lang">	
					<html:multibox property="languages">
						<bean:write name="lang" property="iso_code" />
					</html:multibox>
					<bean:write name="lang" property="name" />
				</logic:iterate>
				<br />
				<br />
	
	        	<label><bean:message key="application.language_hardware"/><bean:message key="common.column"/></label>
				<br />		
				<logic:iterate name="myAdminApplicationForm" property="hardwareList" id="hw">	
					<html:multibox property="hardware">
	            		<bean:write name="hw" property="code" />		
					</html:multibox>
				 	<bean:write name="hw" property="name" />&nbsp;&nbsp;
				</logic:iterate>	
				<br />
		</fieldset>	
	
		<fieldset><legend><bean:message key="application.profile"/></legend>
	        <label><bean:message key="common.title"/><bean:message key="common.column"/></label>	
			<input type="text" id="appliTitle" name="title" value="<%=title%>"/>
			<a href="#" onclick="javascript:window.open('adminAddDico.do?dispatch=load')"><bean:message key="common.create_dico"/></a>
			<br />
			
			<label><bean:message key="common.description"/><bean:message key="common.column"/></label>
			<input type="text" id="appliDesc" name="description" value="<%=description%>"/>	
			<a href="#" onclick="javascript:window.open('adminAddDico.do?dispatch=load')"><bean:message key="common.create_dico"/></a>
			<br />
			
			<div id="instruction">
			<label><bean:message key="common.instruction"/><bean:message key="common.column"/></label>	
			<input type="text" id="appliInstruction" name="howTo" value="<%=howTo%>"/>
			<a href="#" onclick="javascript:window.open('adminAddDico.do?dispatch=load')"><bean:message key="common.create_dico"/></a>
			<br />
			</div>
			 
			<label><bean:message key="common.icon"/></label>
			<html:file property="iconFile"/> 
			<br />
			 
			<label><bean:message key="common.picture"/></label>
			<html:file property="pictureFile"/>
			<br />
			
			<label><bean:message key="common.url"/><bean:message key="common.column"/></label>	
			<input type="text" id="appliURL" name="url" value="<%=url%>" />	
		</fieldset>	
	
		<html:submit property="submit" value="Create" onclick="goDispatch('callAPI', 'dispatchConfigAdd')" />
	</html:form>
	
	
	<p style="color:green; font-weight:bold;"><html:errors property="success" /></p>
	
</div>
<!-- FIN DIV CONTENT -->

<script type="text/javascript">
	function goDispatch(where, dispatchField){
		document.getElementById(dispatchField).value = where;
	}

	function checkHidding(){
		if(document.getElementById('serviceId').value == 'net.violet.external.'){
			document.getElementById('tts').style.display = 'none';
			document.getElementById('instruction').style.display = 'block';
		}
		else{
			document.getElementById('instruction').style.display = 'none';
			document.getElementById('tts').style.display = 'block';
		}
	}

	function listen(){
		var text = document.getElementById('TTSText').value;
		var language = document.getElementById('voiceId').value;
		if(text == ''){
			alert('Please fill the test to synthetise.');
			return;
		}
		window.open('adminAddApplication.do?dispatch=listen&TTSText='+text+'&TTSLanguage='+language);
		return;
	}
	

</script>

<%@ include file="/include_jsp/admin/footer.jsp" %>