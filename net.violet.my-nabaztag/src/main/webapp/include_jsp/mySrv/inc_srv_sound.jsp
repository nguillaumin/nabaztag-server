<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<bean:define property="sonList" name="mySrvSoundForm" id="musicData"/>
<bean:define property="name" name="mySrvSoundForm" id="name"/>
<bean:define property="srvSound_id" name="mySrvSoundForm" id="srvSound_id"/>

<!-- <html:form action="/action/srvSound"> -->
<!-- <%=name%><%=srvSound_id%> -->
<fieldset>
	<legend><%=DicoTools.dico(dico_lang , "srv_sound/choose_sound")%></legend>
	<html:select property="srvSound_id" name="mySrvSoundForm" styleClass="myMp3Picker">
		<option value="">&nbsp;</option>
		<html:optionsCollection name="musicData" label="music_name" value="music_id"/>
	</html:select>
</fieldset>	
<!--	
	<html:submit value="send"/>
</html:form>
-->