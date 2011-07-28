<%@ page pageEncoding="UTF-8" %>

<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/admin/header.jsp" %>

<%@ include file="/include_jsp/admin/navigation.jsp" %>

<!-- DEBUT DIV CONTENT -->
<div id="content">

	<logic:messagesPresent message="errors" property="emptyTitle">
		<p class="clear error">Please fill the title.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="emptySummary">
		<p class="clear error">Please fill the summary.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="emptyUrl">
		<p class="clear error">Please fill the url.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="incorrectSize">
		<p class="clear error">the file size does not exceeded 10MB.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="uploadFailed">
		<p class="clear error">Upload failed.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="pressNotCreated">
		<p class="clear error">Woups ! An internal error occured and the press wasn't created.</p>
	</logic:messagesPresent>
 
	<html:form action="/action/adminAddPress" styleClass="srvConfigForm" enctype="multipart/form-data">
		<bean:define id="names" name="myAdminAddPressForm" property="namesList" />
		<bean:define id="pressTitle" name="myAdminAddPressForm" property="title" />
		<bean:define id="pressSummary" name="myAdminAddPressForm" property="summary" />
		<bean:define id="pressURL" name="myAdminAddPressForm" property="url" />
		<html:hidden styleId="dispatchConfigAdd" property="dispatch" />
	
		<fieldset><legend>Add a new press</legend>
			
			<label>Press title :</label>
			<input type="text" id="pressTitle" name="title" value="<%=pressTitle%>" />	
			<br />
	
			<label>Press language :</label>
			<html:select name="myAdminAddPressForm" property="language">
				<html:optionsCollection name="myAdminAddPressForm" property="langList" label="name" value="iso_code" />
			</html:select>
	
			<label>Press product :</label>
			<html:select property="product">
				<html:options collection="names" property="value" labelProperty="label"/> 
			</html:select>
			<br />
			
			<label>Press Summary :</label>
			<textarea rows="5" cols="50" name="summary"><%=pressSummary %></textarea>
			<br />
			
			<label>Press URL :</label>
			<input type="text" id="pressTitle" name="url" value="<%=pressURL%>" />	
			<br />
			
			<label>Press Picture :</label>
			<html:file property="imageFile"/> 
			<br />
			
			<div class="update">
				<html:submit property="submit" value="Create" onclick="goDispatch('addPress', 'dispatchConfigAdd')" />
			</div>
			
		</fieldset>
	</html:form>

	<logic:messagesPresent message="errors" property="pressCreated">
		<p style="color:green; font-weight:bold;">The Press was created \(^o^)/</p>
	</logic:messagesPresent>

</div>
<!-- FIN DIV CONTENT -->

<script type="text/javascript">
	function goDispatch(where, dispatchField){
		document.getElementById(dispatchField).value = where;
	}
</script>

<%@ include file="/include_jsp/admin/footer.jsp" %>