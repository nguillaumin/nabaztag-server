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

<logic:equal name="myManageMp3Form" property="message" value="OK">
	 <%=DicoTools.dico(dico_lang, "myTerrier/mp3_deleted")%>
</logic:equal>

<logic:equal name="myManageMp3Form" property="message" value="upload_error">
	<script language="javascript">
		parent.customAlertN( parent.msg_txt['upload_error'] );
	</script>
</logic:equal>

<logic:equal name="myManageMp3Form" property="message" value="delete_error">
	delete_error
</logic:equal>

<logic:equal name="myManageMp3Form" property="message" value="update_error">
	update_error
</logic:equal>

