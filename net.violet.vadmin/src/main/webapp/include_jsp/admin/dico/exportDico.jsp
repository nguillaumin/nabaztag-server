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

	<!-- export file -->
	<fieldset><legend>Test export file</legend>
	
	<bean:define id="url" name="myAdminDicoForm" property="url" type="String" />
	
	CSV File will be generated => <a href="<%=url%>" target="_blank"> link </a><br/>
	<html:form action="/action/adminExportDico" styleId="SearchDicoByKey" styleClass="srvConfigForm">
		<html:hidden styleId="dispatchConfigService" property="dispatch" />
		<div class="call2action">
      <html:submit property="submit" value="Export Dico" onclick="goDispatch('export', 'dispatchConfigService')" />
    </div>
	</html:form>
	</fieldset>		
	
	<div id="waiting"></div>

</div>
<!-- FIN DIV CONTENT -->

<script type="text/javascript">
	function goDispatch(where, dispatchField){
			
		document.getElementById(dispatchField).value = where;
		document.getElementById('waiting').innerHTML = "<p class='clear error'>Please wait during the processing.</p>";
	}

</script>

<%@ include file="/include_jsp/admin/footer.jsp" %>