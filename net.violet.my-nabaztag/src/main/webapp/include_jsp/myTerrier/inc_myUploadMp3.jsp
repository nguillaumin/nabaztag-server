<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>


<html:form action="/action/myManageMp3" styleId="uploadMP3_form" enctype="multipart/form-data" target="uploadIframe">
	<html:hidden name="myManageMp3Form" property="queFaire" value="add"/>
	<html:hidden name="myManageMp3Form" property="forward" value="messages"/>

	<div id="uploadForm">
		<label><%=DicoTools.dico(dico_lang, "myTerrier/mp3_upload_name_file")%></label>
		<input id="musicName" name="musicName" type="text" style="width:92%; ">
		<%-- <input type="hidden" name="queFaire" value="update" /> --%>
		
		<hr class="spacer" style="width:92%; border:1px solid red;"/>
		
		<label ><%=DicoTools.dico(dico_lang, "myTerrier/mp3_upload_file")%></label>
		<html:file name="myManageMp3Form" property="musicFile" styleId="musicFile" style="width:92%;" />

		<hr class="spacer" />

		<label><input name="shareMp3" type="checkbox" value="share_mp3" id="shareMp3" onclick="terrierUploadOptionToggle();"/>
		<%=DicoTools.dico(dico_lang, "myTerrier/mp3_upload_share")%></label>

		<div style="display:none;" id="uploadMP3options">
			<hr class="spacer" />
			<label><%=DicoTools.dico(dico_lang, "myTerrier/mp3_upload_category")%></label>
			<select name="categId" id="categId">
				<option value=""><%=DicoTools.dico(dico_lang, "myTerrier/mp3_upload_category_none")%></option>
				<logic:iterate name="myManageMp3Form" property="listeCateg" id="labelData">
					<bean:define id="id" name="labelData" property="id"/>
					<bean:define id="label" name="labelData" property="label"/>
					<option value="<%=id%>"><%=label%></option>
				</logic:iterate>
			</select>
			
			<hr class="spacer" />			
			
			<%=DicoTools.dico(dico_lang, "myTerrier/mp3_upload_tags")%>
			<textarea rows="4" name="cloudTag"></textarea>

		</div>

		<hr class="clearer" />
		
		<input  style="margin-top:8px;" name="Ajouter" type="submit" id="Ajouter" value="<%=DicoTools.dico(dico_lang, "myTerrier/add_mp3")%>" class="genericBt" /> <%-- onclick="terrierValidateUploadMp3(); --%>
		
	</div>
</html:form>

<script language="javascript">
	terrierActivateUpload();
</script>
