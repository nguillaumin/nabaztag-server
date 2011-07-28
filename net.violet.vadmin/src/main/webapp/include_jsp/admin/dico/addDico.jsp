<%@ page pageEncoding="UTF-8" %>

<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ include file="/include_jsp/admin/header.jsp" %>

<%@ include file="/include_jsp/admin/navigation.jsp" %>

<!-- DEBUT DIV CONTENT -->
<div id="content">

	<logic:messagesPresent message="errors" property="emptyKey">
		<p class="clear error">Please fill the key.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="invalidParameter">
		<p class="clear error">A parameter seems invalid.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="keyExisting">
		<p class="clear error">This key already exists.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="dicoNotCreated">
		<p class="clear error">Woups ! An internal error occured and the dico wasn't created.</p>
	</logic:messagesPresent>
	
	<nested:form action="/action/adminAddDico" styleClass="srvConfigForm">
		<html:hidden styleId="dispatchConfigAdd" property="dispatch" />
		<fieldset><legend>Add a new dico key</legend>
		
			<nested:root name="myAdminDicoForm">
      	<div class="field">
					<label><span>Dico key:</span>&nbsp;<nested:text property="dicoName" /></label>
				</div>
        
				<nested:iterate property="values" scope="request" type="net.violet.vadmin.objects.data.DicoData">
        	<div class="field">
            <bean:define id="iso" name="values" property="iso_code" />
            <nested:hidden property="iso_code" value="<%=iso.toString()%>"/>
            <label><span><nested:write property="iso_code" /></span><nested:textarea property="value"/></label>
					</div>
				</nested:iterate>
			</nested:root>
			
			<div class="call2action">
				<nested:submit property="submit" value="Create" onclick="goDispatch('addDico', 'dispatchConfigAdd')" />
			</div>
		</fieldset>
	</nested:form>

	<logic:messagesPresent message="errors" property="dicoCreated">
		<p style="color:green; font-weight:bold;">This entry was created \(^o^)/</p>
	</logic:messagesPresent>

</div>
<!-- FIN DIV CONTENT -->

<script type="text/javascript">
	function goDispatch(where, dispatchField){
		document.getElementById(dispatchField).value = where;
	}
</script>

<%@ include file="/include_jsp/admin/footer.jsp" %>