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
	<logic:messagesPresent message="errors" property="updateFailed">
		<p class="clear error">Woups ! An internal error occured and the press wasn't updated.</p>
	</logic:messagesPresent>

	<fieldset><legend>Search a Press</legend>
	<label><strong>Search press by lang and product</strong></label> 

	<!-- Search form -->
	<html:form action="/action/adminSearchPress" styleId="SearchPressByLangAndProduct" styleClass="srvConfigForm">
		<html:hidden styleId="dispatchConfigService" property="dispatch" />
	
		<label>Language :</label>
		<html:select name="myAdminSearchPressForm" property="language">
			<html:optionsCollection name="myAdminSearchPressForm" property="langList" label="name" value="iso_code" />
		</html:select>
	
	<label>Product :</label>
	<bean:define id="names" name="myAdminSearchPressForm" property="namesList" />
	<html:select property="productPress">
		<html:options collection="names" property="value" labelProperty="label"/> 
	</html:select>
		<html:submit property="submit" value="Search contents" onclick="goDispatch('displayContent', 'dispatchConfigService')" />
	</html:form>

	<!--PRESS -->
	<logic:equal name="myAdminSearchPressForm" property="display" value="displayPress">
		<logic:notEmpty name="myAdminSearchPressForm" property="pressList">
		<bean:define id="nameProduct" name="myAdminSearchPressForm" property="productPress" />
		<bean:define id="theLanguage" name="myAdminSearchPressForm" property="language" />
		
			<div class="results">
				<table>
					<thead>
						<tr>
							<th>Press Title</th>
							<th>Press abstract</th>
							<th>Press Url</th>
							<th>Edit</th>
							<th>Delete</th>
							<th>Press Image</th>
						</tr>
					</thead>
					<tbody>
						<logic:iterate name="myAdminSearchPressForm" property="pressList" id="thePress">
							<bean:define id="idPress" name="thePress" property="id" />
							<bean:define id="titlePress" name="thePress" property="title" />
							<bean:define id="abstractPress" name="thePress" property="summary" />
							<bean:define id="urlPress" name="thePress" property="url" />
							<bean:define id="imagePress" name="thePress" property="picture" />
							<bean:define id="imagePath" name="thePress" property="pictureURL" />
							<tr>
								<td><%=titlePress%></td>
								<td><%=abstractPress%></td>
								<td><%=urlPress%></td>
								<td><a href="adminSearchPress.do?dispatch=displayInfo&idPress=<%=idPress%>&productPress=<%=nameProduct%>&language=<%=theLanguage%>">View/Modify</a></td>
								<td><a href="adminSearchPress.do?dispatch=delete&idPress=<%=idPress%>&productPress=<%=nameProduct%>&language=<%=theLanguage%>">Delete</a></td>
								<td><a href="<%=imagePath%>"><img src="<%=imagePath%>" alt="<%=imagePress%>" class="picture"/></a></td>
							</tr>
						</logic:iterate>
					</tbody>
				</table>
			</div>
		</logic:notEmpty>
		<logic:empty name="myAdminSearchPressForm" property="pressList">
			<fieldset><legend class="clear error">ごめんなさい。。(￣▽￣;)</legend>
				<p class="error">No match...♪</p>
			</fieldset>
		</logic:empty>
	</logic:equal>


	<!--INFO Press -->
	<logic:equal name="myAdminSearchPressForm" property="display" value="infoPress">
		<bean:define id="nameProduct" name="myAdminSearchPressForm" property="productPress" />
		<bean:define id="theLanguage" name="myAdminSearchPressForm" property="language" />
		<bean:define id="thePress" name="myAdminSearchPressForm" property="thePressData" />
		
		<bean:define id="idPress" name="thePress" property="id" />
		<bean:define id="titlePress" name="thePress" property="title" />
		<bean:define id="abstractPress" name="thePress" property="summary" />
		<bean:define id="urlPress" name="thePress" property="url" />
		<bean:define id="langPress" name="thePress" property="lang" />
		<bean:define id="productPress" name="thePress" property="product" />
		<bean:define id="picturePress" name="thePress" property="picture" />
		<bean:define id="imagePath" name="thePress" property="pictureURL" />
		
		<html:form action="/action/adminSearchPress"  styleId="updateSearchConfig" styleClass="updateSearchForm" enctype="multipart/form-data">
			<html:hidden styleId="dispatchConfigUpdate" property ="dispatch" />
			<html:hidden property ="idPress" value="<%=idPress.toString()%>"/>	
			<fieldset><legend>Update Press</legend>
			
				<label>Press title :</label>
				<input type="text" id="PressTitle" name="title" value="<%=titlePress%>"/>	
				<br />
			
				<label>Press language :</label>
				<html:select name="myAdminSearchPressForm" property="language">
					<html:optionsCollection name="myAdminSearchPressForm" property="langList" label="name" value="iso_code" />
				</html:select>
			
				<label>Press product :</label>
				<bean:define id="names" name="myAdminSearchPressForm" property="namesList" />
				<html:select name="myAdminSearchPressForm" property="productPress">
					<html:options collection="names" property="value" labelProperty="label"/> 
				</html:select>
				<br />
				
				<label>Press Abstract :</label>
				<textarea rows="5" cols="50" name="summary" align="center"><%=abstractPress%></textarea>
				<br />
				
				<label>Press URL :</label>
				<input type="text" id="urlTitle" name="url" value="<%=urlPress%>"/>	
				<br />
				
				<label>Press Picture :</label>
				<html:file property="imageFile"/>&nbsp; 
				<a href="<%=imagePath%>"><img src="<%=imagePath%>" alt="<%=picturePress%>" class="picture"/></a>
				<br />
				
				<html:submit property="submit" styleClass="update" value="update" onclick="goDispatch('update', 'dispatchConfigUpdate')" />
			</fieldset>
		</html:form>
		
		<html:form action="/action/adminSearchPress"  styleId="deleteSearchConfig" styleClass="deleteSearchForm" onsubmit="return confirmDeletion()">
			<html:hidden styleId="dispatchConfigDelete" property ="dispatch" />	
			<html:hidden property ="idPress" value="<%=idPress.toString()%>"/>		
			<html:hidden property ="product" value="<%=nameProduct.toString()%>"/>		
			<html:hidden property ="language" value="<%=theLanguage.toString()%>"/>		
			<html:submit property="submit" styleClass="back" value="delete" onclick="goDispatch('delete', 'dispatchConfigDelete')" />
		</html:form>
	</logic:equal>
	
	<logic:messagesPresent message="errors" property="updateSucceeded">
		<p style="color:green; font-weight:bold;">The Press was updated \(^o^)/</p>
	</logic:messagesPresent>

</div>
<!-- FIN DIV CONTENT -->

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
</script>

<%@ include file="/include_jsp/admin/footer.jsp" %>