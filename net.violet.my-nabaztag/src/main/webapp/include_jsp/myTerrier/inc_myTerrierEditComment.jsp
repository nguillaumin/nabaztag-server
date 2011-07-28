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

<div style="width:99%;">
<%=DicoTools.dico(dico_lang, "myTerrier/comment_intro")%>
<html:form action="/action/myTerrierEditComment" styleId="editComment">
	<html:hidden property="modify" value="1" />
	<html:textarea rows="6" property="rabbitAnnounce" style="width:100%" ></html:textarea>
	<div class="validButton">
		<logic:empty name="myTerrierEditCommentForm" property="rabbitAnnounce">
			<input type="button" name="txtsave" value="<%=DicoTools.dico(dico_lang, "myTerrier/comment_add")%>" onclick="simpleAjaxSubmit('editComment')">
		</logic:empty>
		<logic:notEmpty name="myTerrierEditCommentForm" property="rabbitAnnounce">
			<input type="reset" name="txtsave" value="<%=DicoTools.dico(dico_lang, "myTerrier/comment_modify")%>" onclick="simpleAjaxSubmit('editComment', function(){profilEndUpdateComment()})">
		</logic:notEmpty>
	</div>
</html:form>
</div>