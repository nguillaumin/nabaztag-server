<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	String dico_lang =	Long.toString(SessionTools.getLangFromSession(session, request).getId());%>

<iframe src ='javascript:void(0);' id='myUploadIframe' name='myUploadIframe' style='border: none; width:0; height:0'></iframe>
<html:form	action="/action/myTerrierEditRabbitImage" enctype="multipart/form-data" styleId="formUpPhoto" target="myUploadIframe">
		<a class="closebt" href="javascript:;" onclick="$('#pictureLoader').toggle();" ><img border="0" src="../include_img/closeWin.gif" /></a>
		<hr class="spacer" />
		<input type="hidden" name="modify" value="1" />
		<input name="imageFile" type="file" size="30" id="browse" />
		<hr class="spacer" />
		<div align="center"><input type="button" class="genericBt" name="Submit" value="Valider" onclick="terrierValidateNewPict('formUpPhoto')">
		</div>
</html:form>


