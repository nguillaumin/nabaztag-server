<%@ page pageEncoding="UTF-8" %>

<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ include file="/include_jsp/admin/header.jsp" %>

<%@ include file="/include_jsp/admin/navigation.jsp" %>

<!-- DEBUT DIV CONTENT -->


<div id="content" class="dico">

	<logic:messagesPresent message="errors" property="emptyKey">
		<p class="clear error">Please fill the key.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="invalidParameter">
		<p class="clear error">A parameter seems invalid.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="keyExisting">
		<p class="clear error">This key already exists.</p>
	</logic:messagesPresent>
	<logic:messagesPresent message="errors" property="dicoNotUpdated">
		<p class="clear error">Woups ! An internal error occured and the dico wasn't created.</p>
	</logic:messagesPresent>
	
	<bean:define id="dicoName" name="myAdminDicoForm" property="dicoName" />
	
	<!-- Search form -->
	<fieldset><legend>Search dico from a key</legend>
	<html:form action="/action/adminSearchDico" styleId="SearchDicoByKey" styleClass="srvConfigForm">
		<html:hidden styleId="dispatchConfigService" property="dispatch" />
		<div class="field">
      <label><span>Key :</span><input type="text" id="dicoName" name="dicoName" value="<%=dicoName%>"/></label>
		</div>
		
		<div class="call2action">
      <html:submit property="submit" value="Search contents" onclick="goDispatch('displayDico', 'dispatchConfigService')" />
    </div>
	</html:form>
	</fieldset>		
	
	
	<!-- Display Dico values -->
	<logic:equal name="myAdminDicoForm" property="display" value="displayDico">
		<logic:notEmpty name="myAdminDicoForm" property="values">
			<fieldset><legend>Display values</legend>
			<nested:form action="/action/adminSearchDico" styleClass="srvConfigForm">
				<html:hidden styleId="dispatchConfigUpdate" property="dispatch" />
					<nested:root name="myAdminDicoForm">
						<label>Dico key:</label>&nbsp;<nested:text property="dicoName" readonly="true"/>
						<div class="edit-dico">
              <nested:iterate property="values" scope="request" type="net.violet.vadmin.objects.data.DicoData">
                <bean:define id="iso" name="values" property="iso_code" />
                <bean:define id="id" name="values" property="id" />
                <nested:hidden property="iso_code" value="<%=iso.toString()%>"/>
                <nested:hidden property="id" value="<%=id.toString()%>"/>
                <label><nested:write property="iso_code" /></label>
                <nested:textarea property="value" styleClass="textarea-dico"/>
              </nested:iterate>
            </div>
					</nested:root>
					<div class="update">
						<nested:submit property="submit" value="Update" onclick="goDispatch('update', 'dispatchConfigUpdate')" />
					</div>
				</nested:form>
			</fieldset>
		</logic:notEmpty>	
		<logic:empty name="myAdminDicoForm" property="values">
			<fieldset><legend class="clear error">ごめんなさい。。(￣▽￣;)</legend>
				<p class="error">No match...♪</p>
			</fieldset>
		</logic:empty>
	</logic:equal>
	
	<logic:messagesPresent message="errors" property="dicoUpdated">
		<p style="color:green; font-weight:bold;">Entries were updated \(^o^)/</p>
	</logic:messagesPresent>
	
</div>
<!-- FIN DIV CONTENT -->

<script type="text/javascript">
	function goDispatch(where, dispatchField){
		document.getElementById(dispatchField).value = where;
	}
</script>

<%@ include file="/include_jsp/admin/footer.jsp" %>