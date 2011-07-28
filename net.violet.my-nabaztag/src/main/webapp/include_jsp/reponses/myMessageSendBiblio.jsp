<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	String dico_lang =	Long.toString(SessionTools.getLangFromSession(session, request).getId());%>

&nbsp;
<script type="text/javascript">
	var msg = "";
	var col = gErrorColor;
	
	<logic:equal name="myMessagesSendBiblioForm" property="erreur" value="parental_error">
		msg = msg_txt['parental_error'];	
	</logic:equal>
	
	<logic:equal name="myMessagesSendBiblioForm" property="erreur" value="no_id_rabbit">
		msg = msg_txt['no_id_rabbit'];	
	</logic:equal>
	
	<logic:equal name="myMessagesSendBiblioForm" property="erreur" value="create_tts_error">
		msg = msg_txt['create_tts_error'];	
	</logic:equal>
	
	<logic:equal name="myMessagesSendBiblioForm" property="erreur" value="send_tts_error">
		msg = msg_txt['send_tts_error'];	
	</logic:equal>
	
	<logic:equal name="myMessagesSendBiblioForm" property="erreur" value="no_credit_error">
		msg = msg_txt['no_credit_error']; 	
	</logic:equal>
	
	<logic:equal name="myMessagesSendBiblioForm" property="erreur" value="bad_rabbit_error">
		msg = msg_txt['bad_rabbit_error'];	
	</logic:equal>
	
	<logic:equal name="myMessagesSendBiblioForm" property="erreur" value="black_list_error">
		msg = msg_txt['black_list_error']; 	
	</logic:equal>
	
	<logic:equal name="myMessagesSendBiblioForm" property="erreur" value="server_error">
		msg = msg_txt['server_error'];	
	</logic:equal>
	
	<logic:equal name="myMessagesSendBiblioForm" property="erreur" value="OK">
		msg = msg_txt['msg_OK'];
		col = gHColor;			
	</logic:equal>
	
	showFormWaitForResponse("sendMsg", false);
	
	if (msg=="") msg = '<bean:write name="myMessagesSendBiblioForm" property="erreur" />';
	
	var d = document.getElementById("Messages_biblio");
	var id = "";
	var _home = "";
	
	// on revient soit au quicknab soit dans la partie Message
	if (currentTab == "Envoyer") {
		id = "Messages_bibliotheque";
		_home = "";
	} else {
		id = "QuickNabSmenu";
		_home = "Home";
	
	}
	
	tabMessageChangeUrl( id , '../action/myMessages'+_home+'Biblio.do','../action/myMessagesSendBiblio.do');

	// on ferme le "envoyer plus tard"
	messagesDateToggle("force_close");

	$("div.mainTabBody").msgPopup(msg, col, 4000);
	
</script>
