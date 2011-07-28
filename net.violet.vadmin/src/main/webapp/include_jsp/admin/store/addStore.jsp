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
		<strong>Fill the name.</strong>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="incorrectSize">
		<p class="clear error">the file size does not exceeded 10MB.</p>
	</logic:messagesPresent>
 
 	<fieldset><legend>Add a Store</legend>
		<!-- Search by continent -->
		<html:form action="/action/adminAddStore" styleId="SearchByContinent" styleClass="srvConfigForm">
			<html:hidden styleId="dispatchConfigCountries" property="dispatch" />
		
			<label>Continent :</label>
			<html:select property="continent">
				<html:optionsCollection name="myAdminAddStoreForm" property="continents" label="name" value="name" /> 
			</html:select>
		
			<html:submit property="submit" value="Search countries" onclick="goDispatch('displayCountry', 'dispatchConfigCountries')" />
		</html:form>
		
		<!-- List of Countries -->
		<logic:notEmpty name="myAdminAddStoreForm" property="countries">
			
			<bean:define id="continent" name="myAdminAddStoreForm" property="continent" />
			<html:form action="/action/adminAddStore" styleId="SearchByCountries" styleClass="srvConfigForm">
				<html:hidden styleId="dispatchConfigStores" property="dispatch" />
				<html:hidden property="continent" value="<%=continent.toString()%>"/>
	
				<label>Countries :</label>
				<html:select name="myAdminAddStoreForm" property="country">
					<html:optionsCollection name="myAdminAddStoreForm" property="countries" label="name" value="isoCode" />
				</html:select>
		
				<html:submit property="submit" value="Search" onclick="goDispatch('displayCities', 'dispatchConfigStores')" />
			</html:form>
		</logic:notEmpty>

		<!-- List of Cities -->
		<logic:notEmpty name="myAdminAddStoreForm" property="cities">
		<bean:define id="continent" name="myAdminAddStoreForm" property="continent" />
		<bean:define id="country" name="myAdminAddStoreForm" property="country" />
		<bean:define id="types" name="myAdminAddStoreForm" property="typeList" />
		<bean:define id="statusList" name="myAdminAddStoreForm" property="statusList" />
		<bean:define id="nameStore" name="myAdminAddStoreForm" property="name" />
		<bean:define id="addressStore" name="myAdminAddStoreForm" property="address" />
		<bean:define id="zipCodeStore" name="myAdminAddStoreForm" property="zipCode" />
		<bean:define id="urlStore" name="myAdminAddStoreForm" property="url" />
		<bean:define id="rankStore" name="myAdminAddStoreForm" property="rank" />
		<bean:define id="commentStore" name="myAdminAddStoreForm" property="comment" />
		
		<html:form action="/action/adminAddStore" styleId="SearchByCountries" styleClass="srvConfigForm"  enctype="multipart/form-data">
			<fieldset><legend>Add Store</legend>
				<html:hidden styleId="dispatchConfigAdd" property="dispatch" />
				<html:hidden property="continent" value="<%=continent.toString()%>"/>
				<html:hidden property="country" value="<%=country.toString()%>"/>
				
				<label>Store name :</label>
				<input type="text" id="nameStore" name="name" value="<%=nameStore %>"/>	
				<br />
				
				<label>Store type :</label>
				<html:select name="myAdminAddStoreForm" property="type">
					<html:options collection="types" property="value" labelProperty="label"/> 
				</html:select>
					
				<label>Store status :</label>
				<html:select name="myAdminAddStoreForm" property="status">
					<html:options collection="statusList" property="value" labelProperty="label"/> 
				</html:select>
				<br />
		
				<label>Store address :</label>
				<input type="text" id="addressStore" name="address" width="100" value="<%=addressStore %>"/>	
				<br />
				
				<label>Store zip code :</label>
				<input type="text" id="zipCodeStore" name="zipCode" value="<%=zipCodeStore %>" />	
		
				<label>Store city :</label>
				<html:select name="myAdminAddStoreForm" property="city">
					<html:optionsCollection name="myAdminAddStoreForm" property="cities" label="name" value="name" />
				</html:select>
				<br />
				
				<label>Store rank :</label>
				<input type="text" id="rankStore" name="rank" width="4" value="<%=rankStore %>"/>	
				
				<label>Store comment :</label>
				<textarea rows="10" cols=50" name="comment"><%=commentStore %></textarea>
				<br />
				
				<label>Store Picture :</label>
				<html:file property="imageFile"/> 
				<br />
			</fieldset>
			<html:submit property="submit" value="Create" onclick="goDispatch('add', 'dispatchConfigAdd')" />
		</html:form>
		</logic:notEmpty>
	</fieldset>
	
	<logic:messagesPresent message="errors" property="storeCreated">
		<p style="color:green; font-weight:bold;">The Store was created \(^o^)/</p>
	</logic:messagesPresent>
	
</div>
<!-- FIN DIV CONTENT -->

<script type="text/javascript">
	function goDispatch(where, dispatchField){
		document.getElementById(dispatchField).value = where;
	}
</script>

<%@ include file="/include_jsp/admin/footer.jsp" %>