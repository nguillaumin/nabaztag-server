<%@ page pageEncoding="UTF-8" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/admin/header.jsp" %>

<%@ include file="/include_jsp/admin/navigation.jsp" %>

<!-- DEBUT DIV CONTENT -->
<div id="content">
	
	<logic:messagesPresent message="errors" property="emptyName">
		<p class="clear error">Please fill the name.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="incorrectSize">
		<p class="clear error">the file size does not exceeded 10MB.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="uploadFailed">
		<p class="clear error">Upload failed.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="updateFailed">
		<p class="clear error">Woups ! An internal error occured and the store wasn't updated.</p>
	</logic:messagesPresent>
	
	
	<fieldset><legend>Search Store</legend>
	<label><strong>Search store by location</strong></label> 

	<html:form action="/action/adminSearchStore" styleId="SearchByContinent" styleClass="srvConfigForm">
		<html:hidden styleId="dispatchConfigCountries" property="dispatch" />
	
		<label>Continent :</label>
		<html:select property="continent">
			<html:optionsCollection name="myAdminSearchStoreForm" property="continents" label="name" value="name" /> 
		</html:select>
	
		<html:submit property="submit" value="Search countries" onclick="goDispatch('displayCountry', 'dispatchConfigCountries')" />
	</html:form>
	
	
	<!-- List of Countries -->
	<logic:notEmpty name="myAdminSearchStoreForm" property="countries">
		<bean:define id="continent" name="myAdminSearchStoreForm" property="continent" />
		<html:form action="/action/adminSearchStore" styleId="SearchByCountries" styleClass="srvConfigForm">
			<html:hidden styleId="dispatchConfigStores" property="dispatch" />
			<html:hidden property="continent" value="<%=continent.toString()%>"/>

			<label>Countries :</label>
			<html:select name="myAdminSearchStoreForm" property="country">
				<html:optionsCollection name="myAdminSearchStoreForm" property="countries" label="name" value="isoCode" />
			</html:select>
	
			<html:submit property="submit" value="Search" onclick="goDispatch('displayStores', 'dispatchConfigStores')" />
		</html:form>
	</logic:notEmpty>
	
	
	<!-- List of Stores -->
	<logic:equal name="myAdminSearchStoreForm" property="display" value="displayStores">
		<bean:define id="continent" name="myAdminSearchStoreForm" property="continent" />
		<bean:define id="country" name="myAdminSearchStoreForm" property="country" />
		<logic:notEmpty name="myAdminSearchStoreForm" property="storeList">
			<div class="results">
				<table>
					<thead>
						<tr>
							<th>Stores name</th>
							<th>Stores type</th>
							<th>Stores city</th>
							<th>Stores status</th>
							<th>Edit</th>
							<th>Delete</th>
							<th>Picture</th>
						</tr>
					</thead>
					<tbody>
						<logic:iterate name="myAdminSearchStoreForm" property="storeList" id="theStore">
							<bean:define id="storeId" name="theStore" property="id" />
							<bean:define id="storeName" name="theStore" property="name" />
							<bean:define id="storeType" name="theStore" property="type" />
							<bean:define id="storeCity" name="theStore" property="city" />
							<bean:define id="storeStatus" name="theStore" property="status" />
							<bean:define id="imagePath" name="theStore" property="pictureURL" />
							<bean:define id="imageStore" name="theStore" property="picture" />
							<tr>
								<td><%=storeName%></td>
								<td><%=storeType%></td>
								<td><%=storeType%></td>
								<td><%=storeStatus%></td>
								<td><a href="adminSearchStore.do?dispatch=displayInfo&idStore=<%=storeId%>&continent=<%=continent%>&country=<%=country%>">View/Modify</a></td>
								<td><a href="adminSearchStore.do?dispatch=delete&idStore=<%=storeId%>&continent=<%=continent%>&country=<%=country%>">Delete</a></td>
								<td><a href="<%=imagePath%>"><img src="<%=imagePath%>" alt="<%=imageStore%>" class="picture"/></a></td>
			
							</tr>
						</logic:iterate>
					</tbody>
				</table>
			</div>
		</logic:notEmpty>
		<logic:empty name="myAdminSearchStoreForm" property="storeList">
			<fieldset><legend class="clear error">ごめんなさい。。(￣▽￣;)</legend>
				<p class="error">No match for <%=country%>...♪</p>
			</fieldset>
		</logic:empty>
	</logic:equal>
	</fieldset>	

	<!-- STORE INFO -->
	<logic:equal name="myAdminSearchStoreForm" property="display" value="infoStore">
		<bean:define id="continent" name="myAdminSearchStoreForm" property="continent" />
		<bean:define id="country" name="myAdminSearchStoreForm" property="country" />
		<bean:define id="theStore" name="myAdminSearchStoreForm" property="theStoreData" />
		
		<bean:define id="idStore" name="theStore" property="id" />
		<bean:define id="nameStore" name="theStore" property="name" />
		<bean:define id="addressStore" name="theStore" property="address" />
		<bean:define id="zipCodeStore" name="theStore" property="zipCode" />
		<bean:define id="cityStore" name="theStore" property="city" />
		<bean:define id="urlStore" name="theStore" property="url" />
		<bean:define id="statusStore" name="theStore" property="status" />
		<bean:define id="typeStore" name="theStore" property="type" />
		<bean:define id="rankStore" name="theStore" property="rank" />
		<bean:define id="commentStore" name="theStore" property="comment" />
		<bean:define id="pictureStore" name="theStore" property="picture" />
		<bean:define id="imagePath" name="theStore" property="pictureURL" />
		
		<html:form action="/action/adminSearchStore"  styleId="updateSearchConfig" styleClass="updateSearchForm" enctype="multipart/form-data">
		<html:hidden styleId="dispatchConfigUpdate" property ="dispatch" />
		<html:hidden property ="idStore" value="<%=idStore.toString()%>"/>	
		<html:hidden property ="continent" value="<%=continent.toString()%>"/>		
		<html:hidden property ="country" value="<%=country.toString()%>"/>	
			<fieldset><legend>Info Store</legend>
				
				<label>Store name :</label>
				<input type="text" id="nameStore" name="name" value="<%=nameStore%>"/>	
				<br />
				
				<label>Store type :</label>
				<bean:define id="types" name="myAdminSearchStoreForm" property="typeList" />
				<html:select name="myAdminSearchStoreForm" property="type">
					<html:options collection="types" property="value" labelProperty="label"/> 
				</html:select>
					
				<label>Store status :</label>
				<bean:define id="statusList" name="myAdminSearchStoreForm" property="statusList" />
				<html:select name="myAdminSearchStoreForm" property="status">
					<html:options collection="statusList" property="value" labelProperty="label"/> 
				</html:select>
				<br />
			
				<label>Store address :</label>
				<input type="text" id="addressStore" name="address" width="100" value="<%=addressStore%>"/>	
				<br />
				
				<label>Store zip code :</label>
				<input type="text" id="zipCodeStore" name="zipCode"  value="<%=zipCodeStore%>"/>	
			
				<label>Store city :</label>
				<html:select name="myAdminSearchStoreForm" property="status">
					<html:optionsCollection name="myAdminSearchStoreForm" property="cities" label="name" value="name" />
				</html:select>
				<br />
			
				<label>Store url :</label>
				<input type="text" id="urlStore" name="url" value="<%=urlStore%>"/>	
				<br />
				
				<label>Store comment :</label>
				<textarea rows="10" cols=50" name="comment"><%=commentStore%></textarea>
					
				<label>Store rank :</label>
				<input type="text" id="rankStore" name="rank" size="2" value="<%=rankStore%>"/>	
				<br />
				
				<label>Store Picture :</label>
				<html:file property="imageFile"/>&nbsp; 
				<a href="<%=imagePath%>"><img src="<%=imagePath%>" alt="picture <%=pictureStore%> indisponible" class="picture"/></a>
				<br />
				
				<html:submit property="submit" styleClass="update" value="update" onclick="goDispatch('update', 'dispatchConfigUpdate')" />
			</fieldset>
		</html:form>
		<html:form action="/action/adminSearchStore"  styleId="deleteSearchConfig" styleClass="deleteSearchForm" onsubmit="return confirmDeletion()">
			<html:hidden styleId="dispatchConfigDelete" property ="dispatch" />	
			<html:hidden property ="idStore" value="<%=idStore.toString()%>"/>		
			<html:hidden property ="continent" value="<%=continent.toString()%>"/>		
			<html:hidden property ="country" value="<%=country.toString()%>"/>		
			<html:submit property="submit" styleClass="back" value="delete" onclick="goDispatch('delete', 'dispatchConfigDelete')" />
		</html:form>
	</logic:equal>

	<logic:messagesPresent message="errors" property="updateSucceeded">
		<p style="color:green; font-weight:bold;">The store was updated \(^o^)/</p>
	</logic:messagesPresent>
<!-- END OF STORE INFO -->

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