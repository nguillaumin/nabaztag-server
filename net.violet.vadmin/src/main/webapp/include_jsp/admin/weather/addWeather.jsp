<%@ page pageEncoding="UTF-8" %>

<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/admin/header.jsp" %>

<%@ include file="/include_jsp/admin/navigation.jsp" %>

<!-- DEBUT DIV CONTENT -->
<div id="content">

	<logic:messagesPresent message="errors" property="emptyUrl">
		<p class="clear error">Please feel the URL</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="emptyDicoKey">
		<p class="clear error">Please feel the dico key</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="incorrectURL">
		<p class="clear error">The URL seems incorrect</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="errorSQL">
		<p class="clear error">Woups. An error occurred</p>
	</logic:messagesPresent>
 
 	<fieldset><legend>Add a new weather city</legend>
		<!-- Search by continent -->
		<html:form action="/action/adminAddWeather" styleId="SearchByContinent" styleClass="srvConfigForm">
			<html:hidden styleId="dispatchConfigCountries" property="dispatch" />
		
			<label>Continent :</label>
			<html:select property="continent">
				<html:optionsCollection name="myAdminAddWeatherForm" property="continents" label="name" value="name" /> 
			</html:select>
		
			<html:submit property="submit" value="Search countries" onclick="goDispatch('displayCountry', 'dispatchConfigCountries')" />
		</html:form>
		
		<!-- List of Countries -->
		<logic:notEmpty name="myAdminAddWeatherForm" property="countries">
			
			<bean:define id="continent" name="myAdminAddWeatherForm" property="continent" />
			<html:form action="/action/adminAddWeather" styleId="SearchByCountries" styleClass="srvConfigForm">
				<html:hidden styleId="dispatchConfigStores" property="dispatch" />
				<html:hidden property="continent" value="<%=continent.toString()%>"/>
	
				<label>Countries :</label>
				<html:select name="myAdminAddWeatherForm" property="country">
					<html:optionsCollection name="myAdminAddWeatherForm" property="countries" label="name" value="isoCode" />
				</html:select>
		
				<html:submit property="submit" value="Search" onclick="goDispatch('displayCities', 'dispatchConfigStores')" />
			</html:form>
		</logic:notEmpty>

		<!-- List of Cities -->
		<logic:notEmpty name="myAdminAddWeatherForm" property="cities">
		<bean:define id="continent" name="myAdminAddWeatherForm" property="continent" />
		<bean:define id="country" name="myAdminAddWeatherForm" property="country" />
		<bean:define id="city" name="myAdminAddWeatherForm" property="city" />
		<bean:define id="newCity" name="myAdminAddWeatherForm" property="newCity" />
		<bean:define id="url" name="myAdminAddWeatherForm" property="url" />
		
		<html:form action="/action/adminAddWeather" styleId="SearchByCountries" styleClass="srvConfigForm">
			<fieldset><legend>Add a new city</legend>
				<html:hidden styleId="dispatchConfigAdd" property="dispatch" />
				<html:hidden property="continent" value="<%=continent.toString()%>"/>
				<html:hidden property="country" value="<%=country.toString()%>"/>
				
				<label>Weather City :</label>
				<html:select name="myAdminAddWeatherForm" property="city">
					<html:optionsCollection name="myAdminAddWeatherForm" property="cities" label="name" value="name" />
				</html:select>
				<strong>OR</strong>
				<input type="text" id="newCity" name="newCity" value="<%=newCity%>" size="15" title="The city musn't contains any accent."/>
				<em>Please don't put any accent and use english format.</em>	
				<br />
				
				<label>City URL :</label>
				<input type="text" id="urlWeather" name="url" value="<%=url %>" size="20"/>	
				<br />
				
			</fieldset>
			<html:submit property="submit" value="Create" onclick="goDispatch('add', 'dispatchConfigAdd')" />
		</html:form>
		</logic:notEmpty>
	</fieldset>
	
	<logic:messagesPresent message="errors" property="success">
		<bean:define id="city" name="myAdminAddWeatherForm" property="city" />
		<bean:define id="dicoKey" name="myAdminAddWeatherForm" property="dicoCreated" />
		<p style="color:green; font-weight:bold;">This city of <%=city %> for the weather service was created! よかったね! \(^o^)/</p>
		<p style="color:green; font-weight:bold;">The dico key generated is: <%= dicoKey%></p>
	</logic:messagesPresent>
	
</div>
<!-- FIN DIV CONTENT -->

<script type="text/javascript">
	function goDispatch(where, dispatchField){
		document.getElementById(dispatchField).value = where;
	}
</script>

<%@ include file="/include_jsp/admin/footer.jsp" %>