c<%@ page pageEncoding="UTF-8" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%@ include file="/include_jsp/admin/header.jsp" %>
<%@ include file="/include_jsp/admin/navigation.jsp" %>

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
	<p class="clear error"><html:errors property="invalidParameter" /></p>
	<p class="clear error"><html:errors property="badUrl" /></p>
	<p class="clear error"><html:errors property="uploadFailed" /></p>
	<p class="clear error"><html:errors property="createFailed" /></p>
	<p class="clear error"><html:errors property="noFile" /></p>
	<p class="clear error"><html:errors property="invalidParameter" /></p>
	<p class="clear error"><html:errors property="existingName" /></p>

	<bean:define id="categories" name="myAdminApplicationForm" property="categoriesList" />
	
	<!-- Two first comboboxes -->
	<fieldset>
  	<legend>Search Application</legend>
		<p><strong>Search Applications by type and lang</strong></p>
	
		<!-- languages and products combobox -->
		<html:form action="/action/adminSearchApplication"	styleId="SearchLangByType" styleClass="srvConfigForm">
			<bean:define id="names" name="myAdminApplicationForm" property="namesList" />
			<html:hidden styleId="dispatchConfigService" property="dispatch" />
			
      <div class="field">
        <label>
          <span>Application Type :</span>
          <html:select property="nameService">
            <html:options collection="names" property="value" labelProperty="label"/> 
          </html:select>
        </label>
			</div>
      
      <div class="field">
        <label>
          <span>Language :</span>
          <html:select name="myAdminApplicationForm" property="language">
            <html:optionsCollection name="myAdminApplicationForm" property="langList" label="name" value="iso_code" />
          </html:select>
        </label>
			</div>
      
      <div class="call2action">
        <html:submit property="submit" value="Search contents" onclick="goDispatch('displayContent', 'dispatchConfigService')" />
      </div>
		</html:form>
		<br />
		
		<!-- Final combobox --> 
	<logic:notEmpty  name="myAdminApplicationForm" property="applicationList">
		<bean:define id="nameService" name="myAdminApplicationForm"	property="nameService" />
		<bean:define id="language" name="myAdminApplicationForm"	property="language" />
		<html:form action="/action/adminSearchApplication" styleId="SearchContentByType" styleClass="srvConfigForm">
			<html:hidden styleId="dispatchConfigContent" property="dispatch" />
			<html:hidden styleId="dispatchConfigContent" property="nameService" value="<%=nameService.toString() %>" />
			<html:hidden styleId="dispatchConfigContent" property="language" value="<%=language.toString() %>" />
			
      <div class="field">
        <label>
          <span>List of content :</span>
          <html:select name="myAdminApplicationForm" property="applicationId">
            <html:optionsCollection name="myAdminApplicationForm" property="applicationList" label="name" value="id" />
          </html:select>
        </label>
      </div>
      
      <div class="call2action">
        <html:submit property="submit" value="Display Application"	onclick="goDispatch('displayApplication', 'dispatchConfigContent')" />
      </div>
      
		</html:form>
	</logic:notEmpty> 
	
	<logic:messagesPresent message="errors" property="noApplicationMatch">
		<fieldset><legend class="clear error">ごめんなさい。。(￣▽￣;)</legend>
			<p class="clear error"><bean:write name="myAdminApplicationForm" property="nameService" />"の勤務と"<bean:write	name="myAdminApplicationForm" property="language" />"の言語は応用ではありません。。</p>
			<p class="clear error">The "<bean:write	name="myAdminApplicationForm" property="nameService" />" service and the "<bean:write name="myAdminApplicationForm"	property="language" />" language don't match with any application. Sorry.</p>
		</fieldset>
	</logic:messagesPresent> 
	</fieldset>

	<!-- update APPLICATION -->
	<logic:equal name="myAdminApplicationForm" property="display" value="displayApplication">
		<logic:notEmpty  name="myAdminApplicationForm" property="appliInfo">
			<html:form action="/action/adminSearchApplication" styleId="update" styleClass="srvConfigForm" enctype="multipart/form-data">
				<bean:define id="trueFalse" name="myAdminApplicationForm" property="trueFalse" />
				<bean:define id="applicationId" name="myAdminApplicationForm" property="applicationId" />
				<bean:define id="theApplication" name="myAdminApplicationForm" property="appliInfo" />
				<bean:define id="nameService" name="myAdminApplicationForm"	property="nameService" />
				<bean:define id="language" name="myAdminApplicationForm"	property="language" />
				<bean:define id="voices" name="myAdminApplicationForm" property="voices" />
				<bean:define id="service" name="myAdminApplicationForm" property="namesList" />
			
				<fieldset><legend><bean:message key="application.info"/></legend>
					<bean:define id="name" name="theApplication" property="name" />
					<bean:define id="url" name="theApplication" property="url" />
					<bean:define id="title" name="theApplication" property="title" />
					<bean:define id="description" name="theApplication" property="description" />
				    <bean:define id="category" name="theApplication" property="category" />
				    <bean:define id="picture" name="theApplication" property="picture" />
				    <bean:define id="picturePath" name="theApplication" property="pictureURL" />
				    <bean:define id="icon" name="theApplication" property="icon" />
				    <bean:define id="iconPath" name="theApplication" property="iconURL" />
				    <bean:define id="announcement" name="theApplication" property="announcement" />

					<html:hidden styleId="dispatchUpdate" property="dispatch" />
					<html:hidden styleId="applicationId" property="applicationId" value="<%=applicationId.toString()%>"/>
					<html:hidden styleId="nameService" property="nameService" value="<%=nameService.toString()%>"/>
					<html:hidden styleId="language" property="language" value="<%=language.toString()%>"/>
					<html:hidden styleId="announcement" property="announcement" value="<%=announcement.toString()%>"/>
					<html:hidden styleId="title" property="title" value="<%=title.toString()%>"/>
					<html:hidden styleId="description" property="description" value="<%=description.toString()%>"/>
					<html:hidden styleId="iconId" property="iconFileId" value="<%=icon.toString()%>"/>
					<html:hidden styleId="pictureId" property="pictureFileId" value="<%=picture.toString()%>"/>
					<html:hidden styleId="formerURL" property="formerURL" value="<%=url.toString()%>"/>
									    
          <fieldset>
            <legend>Application:</legend>
            <div class="field">
              <label>
              	<span>Type:</span>
                <html:text property="nameService" readonly="true"><%=nameService%></html:text>
                <input type="text" id="appliName" name="name" value="<%=name%>" />
              </label>
            </div>
            
            <div class="field">
              <div id="tts">
                <label>
                  <span><bean:message key="common.voice"/></span>
                  <input type="text" id="TTSText" name="TTSText" />
                  <label>&nbsp;<bean:message key="common.in"/></label>
                  <html:select property="TTSLanguage">
                  <html:options collection="voices" property="id" labelProperty="title"/> 
                  </html:select>
                </label>
              </div>
            </div>
            
            <div class="field">
              <label>
                <span><strong><bean:message key="common.category"/></strong></span>
                <html:select property="cat">
                <html:options collection="categories" property="value" labelProperty="label"/> 
                </html:select>
              </label>
            </div>
            
            <div class="field">
              <label>
              	<span><strong><bean:message key="common.visibility"/></strong></span>
              	<html:select name="myAdminApplicationForm" property="isVisible">
              	<html:options collection="trueFalse" property="value" labelProperty="label"/> 
              	</html:select>
							</label>
            </div>
          </fieldset>				
			
					<fieldset>
          	<legend><bean:message key="application.language_hardware"/></legend>
						<p><bean:message key="common.languages"/></p>
						<logic:iterate name="myAdminApplicationForm" property="langList" id="lang">	
							<label>
                <html:multibox property="languages">
                  <bean:write name="lang" property="iso_code" />
                </html:multibox>
                <bean:write name="lang" property="name" />
              </label>
						</logic:iterate>
						<br />
						
						<p><bean:message key="common.hardware"/></p>
						<logic:iterate name="myAdminApplicationForm" property="hardwareList" id="hw">
            	<label>
                <html:multibox property="hardware">
                  <bean:write name="hw" property="code" />
                </html:multibox>
                <bean:write name="hw" property="name"/>
              </label>
						</logic:iterate>
					</fieldset>				
					
					<fieldset>
          	<legend><bean:message key="application.profile"/></legend>
						<div class="field">
              <label>
                <span><bean:message key="common.url"/></span>
                <input type="text" id="url" name="url" value="<%=url%>"/>	
              </label>
            </div>
						
            <div class="field">
              <a href="<%=iconPath%>"><img src="<%=iconPath%>" alt="<%=icon%>" class="picture2"/></a>&nbsp;
              <label>
              	<strong><bean:message key="common.icon"/></strong>
                <html:file property="iconFile"/>
							</label>
            </div>

						<div class="field">
              <a href="<%=picturePath%>"><img src="<%=picturePath%>" alt="<%=picture%>" class="picture2"/></a>
              <label>
              	<strong><bean:message key="common.picture"/></strong>
                <html:file property="pictureFile"/>
							</label>
						</div> 
					</fieldset>
					
          <div class="call2action">
            <html:submit property="submit" value="Update application"	onclick="goDispatch('callAPI', 'dispatchUpdate')" />
          </div>
					
				</fieldset>
			</html:form>
			
			<p style="color:green; font-weight:bold;"><html:errors property="success" /></p> 
		</logic:notEmpty>
	</logic:equal>

</div>
<!-- FIN APPLICATION -->

<script type="text/javascript">
	function goDispatch(where, dispatchField){
		document.getElementById(dispatchField).value = where;
	}

	function confirmDeletion(){
		if (window.confirm('Do you really want to do that ?')) {
				return true;
		}
		return false;
	}

	function checkHidding(){
		if(document.getElementById('serviceId').value == 'net.violet.external.'){
			document.getElementById('tts').style.display = 'none';
		}
		else{
			document.getElementById('tts').style.display = 'block';
		}
	}
</script>
<%@ include file="/include_jsp/admin/footer.jsp" %>