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
	<logic:messagesPresent message="errors" property="emptyIntro">
		<p class="clear error">Please fill the intro.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="emptyBody">
		<p class="clear error">Please fill the body.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="invalidDatePub">
		<strong>The publication date seems incorrect.</strong>
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
	
	
	<fieldset><legend>Search News</legend>
		<label><strong>Search news by product and lang</strong></label> 
	
		<!-- languages and products combobox -->
		<html:form action="/action/adminSearchNews"	styleId="SearchLangByType" styleClass="srvConfigForm">
			<html:hidden styleId="dispatchConfigNews" property="dispatch" />
		
			<label>Language :</label>
			<html:select name="myAdminSearchNewsForm" property="language">
				<html:optionsCollection name="myAdminSearchNewsForm" property="langList" label="name" value="iso_code" />
			</html:select>
		
			<label>Product :</label>
			<bean:define id="names" name="myAdminSearchNewsForm" property="namesList" />
			<html:select property="product">
				<html:options collection="names" property="value" labelProperty="label"/> 
			</html:select>
	
			<html:submit property="submit" value="Search contents" onclick="goDispatch('displayContent', 'dispatchConfigNews')" />
		</html:form>
	</fieldset>

	<!--NEWS -->
	<logic:equal name="myAdminSearchNewsForm" property="display" value="displayNews">
		<logic:notEmpty name="myAdminSearchNewsForm" property="newsList">
		<bean:define id="nameProduct" name="myAdminSearchNewsForm" property="product" />
		<bean:define id="theLanguage" name="myAdminSearchNewsForm" property="language" />
			<div class="results">
				<table>
					<thead>
						<tr>
							<th>News Title</th>
							<th>News abstract</th>
							<th>News intro</th>
							<th>News body</th>
							<th>News date publication</th>
							<th>News date expiration</th>
							<th>Edit</th>
							<th>Delete</th>
							<th>News small image</th>
						</tr>
					</thead>
					<tbody>
						<logic:iterate name="myAdminSearchNewsForm" property="newsList" id="theNews">
							<bean:define id="idNews" name="theNews" property="id" />
							<bean:define id="titleNews" name="theNews" property="title" />
							<bean:define id="abstractNews" name="theNews" property="newsabstract" />
							<bean:define id="introNews" name="theNews" property="intro" />
							<bean:define id="bodyNews" name="theNews" property="body" />
							<bean:define id="datePubNews" name="theNews" property="date_pub" />
							<bean:define id="dateExpNews" name="theNews" property="date_exp" />
							<bean:define id="smallImage" name="theNews" property="picture_small" />
							<bean:define id="smallPath" name="theNews" property="picture_smallURL" />
							
							<tr>
								<td><%=titleNews%></td>
								<td><%=abstractNews%></td>
								<td><%=introNews%></td>
								<td><%=bodyNews%></td>
								<td><%=datePubNews%></td>
								<td><%=dateExpNews%></td>
								<td><a href="adminSearchNews.do?dispatch=displayInfo&idNews=<%=idNews%>&product=<%=nameProduct%>&language=<%=theLanguage%>">View/Modify</a></td>
								<td><a href="adminSearchNews.do?dispatch=delete&idNews=<%=idNews%>&product=<%=nameProduct%>&language=<%=theLanguage%>">Delete</a></td>
							    <td><a href="<%=smallPath%>"><img src="<%=smallPath%>" alt="<%=smallImage%>" class="picture"/></a></td>
							</tr>
						</logic:iterate>
					</tbody>
				</table>
			</div>
		</logic:notEmpty>
		<logic:empty name="myAdminSearchNewsForm" property="newsList">
			<logic:messagesPresent message="errors" property="invalidParam">
				<fieldset><legend class="clear error">invalidParam :-(</legend>
					<p class="error">Language, Product...♪♪♪♪♪</p>
				</fieldset>
			</logic:messagesPresent>
			<logic:messagesPresent message="errors" property="APIException1">
				<fieldset><legend class="clear error">APIException1 :-(</legend>
					<p class="error">???...♪♪♪♪♪</p>
				</fieldset>
			</logic:messagesPresent>
			<logic:messagesPresent message="errors" property="APIException2">
				<fieldset><legend class="clear error">APIException2 :-(</legend>
					<p class="error">???...♪♪♪♪♪</p>
				</fieldset>
			</logic:messagesPresent>
			<logic:messagesPresent message="errors" property="noSuchProduct">
				<fieldset><legend class="clear error">noSuchProduct :-(</legend>
				<bean:define id="product" name="myAdminSearchNewsForm" property="product" />
					<p class="error"><%=product %> noSuchProduct...♪♪♪♪♪</p>
				</fieldset>
			</logic:messagesPresent>
			<fieldset><legend class="clear error">ごめんなさい。。(￣▽￣;)</legend>
					<p class="error">No match...♪</p>
			</fieldset>
		</logic:empty>
	</logic:equal>

	<!--INFO NEWS -->
	<logic:equal name="myAdminSearchNewsForm" property="display" value="infoNews">
		<bean:define id="nameProduct" name="myAdminSearchNewsForm" property="product" />
		<bean:define id="theLanguage" name="myAdminSearchNewsForm" property="language" />
		<bean:define id="theNews" name="myAdminSearchNewsForm" property="theNewsData" />
		
		<bean:define id="idNews" name="theNews" property="id" />
		<bean:define id="titleNews" name="theNews" property="title" />
		<bean:define id="abstractNews" name="theNews" property="newsabstract" />
		<bean:define id="introNews" name="theNews" property="intro" />
		<bean:define id="bodyNews" name="theNews" property="body" />
		<bean:define id="pubYear" name="theNews" property="pubYear" />
		<bean:define id="pubMonth" name="theNews" property="pubMonth" />
		<bean:define id="pubDay" name="theNews" property="pubMonth" />
		<bean:define id="expYear" name="theNews" property="expYear" />
		<bean:define id="expMonth" name="theNews" property="expMonth" />
		<bean:define id="expDay" name="theNews" property="expDay" />
		<bean:define id="langNews" name="theNews" property="lang" />
		<bean:define id="productNews" name="theNews" property="product" />
		<bean:define id="smallPictureNews" name="theNews" property="picture_small" />
		<bean:define id="smallPath" name="theNews" property="picture_smallURL" />
		<bean:define id="bigPictureNews" name="theNews" property="picture_big" />
		<bean:define id="bigPath" name="theNews" property="picture_bigURL" />
		
		<html:form action="/action/adminSearchNews"  styleId="updateSearchConfig" styleClass="updateSearchForm" enctype="multipart/form-data">
			<html:hidden styleId="dispatchConfigUpdate" property ="dispatch" />
			<html:hidden property ="idNews" value="<%=idNews.toString()%>"/>	
			<fieldset><legend>Update News</legend>
				
				<label>News title :</label>
				<input type="text" id="newsTitle" name="title" value="<%=titleNews.toString()%>"/>	
				<br />
			
				<label>News language :</label>
				<html:select name="myAdminSearchNewsForm" property="language">
					<html:optionsCollection name="myAdminSearchNewsForm" property="langList" label="name" value="iso_code" />
				</html:select>
			
				<label>News product :</label>
				<bean:define id="names" name="myAdminSearchNewsForm" property="namesList" />
				<html:select name="myAdminSearchNewsForm" property="product">
					<html:options collection="names" property="value" labelProperty="label"/> 
				</html:select>
				<br />
				
				<label>News Abstract :</label>
				<textarea rows="5" cols="50" name="summary" ><%=abstractNews%></textarea>
				<br />
				
				<label>News Intro :</label>
				<input type="text" id="newsIntro" name="intro" value="<%=introNews%>"/>	
				<br />
				
				<label>News Body :</label>
					<textarea rows="5" cols="50" name="body"><%=bodyNews%></textarea>
				<br /><br />
				
				<label>News Publication Date :&nbsp;</label>
				<input type="text" id="newsPubYear" name="pubYear" value="<%=pubYear %>" size="4"/>	
				<input type="text" id="newsPubMonth" name="pubMonth" value="<%=pubMonth %>" size="2" />	
				<input type="text" id="newsPubDay" name="pubDay" value="<%=pubDay %>" size="2" />	
				<br />
				<label>News Expiration Date :&nbsp;</label>
				<input type="text" id="newsExpYear" name="expYear" value="<%=expYear %>" size="4" />	
				<input type="text" id="newsExpMonth" name="expMonth" value="<%=expMonth %>" size="2" />	
				<input type="text" id="newsExpDay" name="expDay" value="<%=expDay %>" size="2" />	
				<br /><br /><hr /><br />
				
				<label>Press Small Picture :</label>
				<html:file property="smallImageFile"/>&nbsp;
				<a href="<%=smallPath%>"><img src="<%=smallPath%>" alt="<%=smallPictureNews%>" class="picture"/></a>
				<br />
				
				<label>Press Big Picture :</label>
				<html:file property="bigImageFile"/>&nbsp; 
				<a href="<%=bigPath%>"><img src="<%=bigPath%>" alt="<%=bigPictureNews%>" class="picture"/></a>
				<br />
				
				<html:submit property="submit" styleClass="update" value="update" onclick="goDispatch('update', 'dispatchConfigUpdate')" />
			</fieldset>
		</html:form>
		
		<html:form action="/action/adminSearchNews"  styleId="deleteSearchConfig" styleClass="deleteSearchForm" onsubmit="return confirmDeletion()">
			<html:hidden styleId="dispatchConfigDelete" property ="dispatch" />	
			<html:hidden property ="idNews" value="<%=idNews.toString()%>"/>		
			<html:hidden property ="product" value="<%=nameProduct.toString()%>"/>		
			<html:hidden property ="language" value="<%=theLanguage.toString()%>"/>		
			<html:submit property="submit" styleClass="back" value="delete" onclick="goDispatch('delete', 'dispatchConfigDelete')" />
		</html:form>
	</logic:equal>

	<logic:messagesPresent message="errors" property="updateSucceeded">
		<p style="color:green; font-weight:bold;">The News was updated \(^o^)/</p>
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