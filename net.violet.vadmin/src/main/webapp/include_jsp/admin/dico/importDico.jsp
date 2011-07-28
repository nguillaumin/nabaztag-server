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
	<html:form action="/action/adminImportDico" styleId="SearchDicoByKey" styleClass="srvConfigForm" enctype="multipart/form-data">
		<html:hidden styleId="dispatchConfigService" property="dispatch" />
		
    <div class="field">
      <label><span>CSV file to import :&nbsp;</span><html:file property="importFile"/></label>
		</div>
		
    <div class="call2action">
      <html:submit property="submit" value="Import Dico" onclick="goDispatch('importDico', 'dispatchConfigService')" />
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