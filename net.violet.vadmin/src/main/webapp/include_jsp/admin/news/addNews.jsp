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
		<strong>Please fill the title.</strong>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="emptyAbstract">
		<strong>Please fill the abstract.</strong>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="emptyBody">
		<strong>Please fill the body.</strong>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="emptyIntro">
		<strong>Please fill the intro.</strong>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="invalidDatePub">
		<strong>The publication date seems incorrect.</strong>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="incorrectSize">
		<p class="clear error">the file size does not exceeded 10MB.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="uploadFailed">
		<strong>Upload failed.</strong>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="newsNotCreated">
		<strong>Woups ! An internal error occured and the news wasn't created.</strong>
	</logic:messagesPresent>
	
	<html:form action="/action/adminAddNews" styleClass="srvConfigForm" enctype="multipart/form-data">
	
	<html:hidden styleId="dispatchConfigAdd" property="dispatch" />
	<bean:define id="names" name="myAdminAddNewsForm" property="namesList" />
	<bean:define id="newsTitle" name="myAdminAddNewsForm" property="title" />
	<bean:define id="newsBody" name="myAdminAddNewsForm" property="body" />
	<bean:define id="newsIntro" name="myAdminAddNewsForm" property="intro" />
	<bean:define id="newsSummary" name="myAdminAddNewsForm" property="extract" />
	<bean:define id="newsPubYear" name="myAdminAddNewsForm" property="pubYear" />
	<bean:define id="newsPubMonth" name="myAdminAddNewsForm" property="pubMonth" />
	<bean:define id="newsPubDay" name="myAdminAddNewsForm" property="pubDay" />
	<bean:define id="newsExpYear" name="myAdminAddNewsForm" property="expYear" />
	<bean:define id="newsExpMonth" name="myAdminAddNewsForm" property="expMonth" />
	<bean:define id="newsExpDay" name="myAdminAddNewsForm" property="expDay" />
		
	<fieldset><legend>Add a news</legend>
		
		<label>News title :&nbsp;</label>
		<input type="text" id="newsTitle" name="title" value="<%=newsTitle %>"/>	
		<br />

		<label>News Body :&nbsp;</label>
			<textarea rows="5" cols="50" name="body"><%=newsBody %></textarea>
		<br />
		
		<label>News language :&nbsp;</label>
		<html:select name="myAdminAddNewsForm" property="language">
			<html:optionsCollection name="myAdminAddNewsForm" property="langList" label="name" value="iso_code" />
		</html:select>

		<label>News product :&nbsp;</label>
		<html:select property="product">
			<html:options collection="names" property="value" labelProperty="label"/> 
		</html:select>
		<br />
		
		<label>News Abstract :&nbsp;</label>
		<textarea rows="5" cols="50" name="extract"><%=newsSummary %></textarea>
		<br />
		
		<label>News Intro :&nbsp;</label>
		<input type="text" id="newsIntro" name="intro" value="<%=newsIntro %>" />	
		<br />
		
		<label>News Publication Date :&nbsp;</label>
		<input type="text" id="newsPubYear" name="pubYear" value="<%=newsPubYear %>" size="4"/>	
		<input type="text" id="newsPubMonth" name="pubMonth" value="<%=newsPubMonth %>" size="2" />	
		<input type="text" id="newsPubDay" name="pubDay" value="<%=newsPubDay %>" size="2" />	
		<br />
		<label>News Expiration Date :&nbsp;</label>
		<input type="text" id="newsExpYear" name="expYear" value="<%=newsExpYear %>" size="4" />	
		<input type="text" id="newsExpMonth" name="expMonth" value="<%=newsExpMonth %>" size="2" />	
		<input type="text" id="newsExpDay" name="expDay" value="<%=newsExpDay %>" size="2" />	
		<br />
		
		<label>News Small Picture :&nbsp;</label>
		<html:file property="smallImageFile"/> 
		<br />
		
		<label>News Big Picture :&nbsp;</label>
		<html:file property="bigImageFile"/> 
		<br />
		<p>Voir si les données intro/body/abstract sont des "textfield" ou des "textarea"</p>
		
		<html:submit property="submit" value="Create" onclick="goDispatch('addNews', 'dispatchConfigAdd')" />
	</fieldset>
	</html:form>

	<logic:messagesPresent message="errors" property="newsCreated">
		<p style="color:green; font-weight:bold;">The News was created \(^o^)/</p>
	</logic:messagesPresent>

</div>
<!-- FIN DIV CONTENT -->

<script type="text/javascript">
	function goDispatch(where, dispatchField){
		document.getElementById(dispatchField).value = where;
	}
</script>
<%@ include file="/include_jsp/admin/footer.jsp" %>