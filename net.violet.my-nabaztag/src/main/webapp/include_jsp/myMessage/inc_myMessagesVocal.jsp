<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>

<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<bean:define name="myMessagesVocalForm" property="langUser" id="lang" type="String"/>
<bean:define name="myMessagesVocalForm" property="idMp3" id="idMp3" type="String"/>
<bean:define name="myMessagesVocalForm" property="serverPath" id="serverPath" type="String"/>

<div class="Column66" id="flashcontent"></div>

<script type="text/javascript">
	var so = new SWFObject("../include_flash/audioControl.swf", "audiocontrol", "300", "150", "9", "#FFFFFF");
	so.addParam("wmode", "transparent");
	so.addVariable("myServer","<%=serverPath%>");
	so.addVariable("mySound","<%=idMp3%>");
	so.addVariable("message","<%=DicoTools.dico(dico_lang, "myMessagesVocal/warning")%>");
	if (!so.write("flashcontent")) { // flash player version detection failed > force creation
		var container = document.getElementById("flashcontent");
		if (container) container.innerHTML = so.getSWFHTML();
	}
</script>

<div class="Column33">
	<%=DicoTools.dico(dico_lang, "myMessagesVocal/explain")%>
</div>
<hr class="spacer" />

<input name="idMp3" type="hidden" id="idMp3" value="<%=idMp3%>">

<label><%=DicoTools.dico(dico_lang, "myMessagesVocal/comment")%> : </label><input name="musicName" type="text" id="musicName" size="34"><br/>

<small><%=DicoTools.dico(dico_lang, "myMessagesVocal/rmq")%></small>
