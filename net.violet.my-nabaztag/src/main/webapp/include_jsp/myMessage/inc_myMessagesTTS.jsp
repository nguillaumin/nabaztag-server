<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>

<bean:define name="myMessagesTTSForm" property="langUser" id="lang" type="String"/>

<div class="Column66">
	<textarea accept-charset="utf-8" rows="4" name="messageTTS" id="messageTTS" class="tts-textarea"></textarea>
</div>
<div class="Column33">
	<p><%=DicoTools.dico(dico_lang, "myMessages/TTS_teaser")%></p>
</div>
<hr class="spacer" />


<label><%=DicoTools.dico(dico_lang, "myMessages/TTS_choose_voice")%></label><br />

<html:select name="myMessagesTTSForm" property="idVoice">
	<logic:iterate name="myMessagesTTSForm" property="listeVoix" id="tts">
	<bean:define id="idVoix1" name="tts" property="idVoix"/>
	<bean:define id="libelles" name="tts" property="libelles"/>

	<logic:equal name="myMessagesTTSForm" property="idVoice" value="<%=idVoix1.toString()%>">
		<option value="<%=idVoix1.toString()%>" selected>
	</logic:equal>
	<logic:notEqual name="myMessagesTTSForm" property="idVoice" value="<%=idVoix1.toString()%>">
		<option value="<%=idVoix1.toString()%>">
	</logic:notEqual>
	<%=libelles%></option>
	</logic:iterate>
</html:select>