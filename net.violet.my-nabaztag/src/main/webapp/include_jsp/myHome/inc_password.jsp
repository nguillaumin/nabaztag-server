<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>


<bean:define name="myHomePasswordForm" property="mode" id="mode" />
<bean:define name="myHomePasswordForm" property="ok" id="ok" />


<%-- TRAITEMENT ERREUR/SUCCES --%>
<logic:notEqual name="mode" value="0">
	<ul class="general-msg">
		<logic:equal name="ok" value="-1">
			<li class="error" ><%=DicoTools.dico(dico_lang , "recovery/error")%></li>
		</logic:equal>
		
		<logic:equal name="ok" value="1">
			<li><%=DicoTools.dico(dico_lang , "recovery/confirmation")%></li>
		</logic:equal>
	</ul>
</logic:notEqual>

<div class="recover-form">
	<p><%=DicoTools.dico(dico_lang , "recovery/intro_text")%></p>
	
	<html:form action="/action/myHomePassword" styleId="formPass" >
		<html:hidden name="myHomePasswordForm" property="mode" value="1"/>

			<label><%=DicoTools.dico(dico_lang , "recovery/nabname")%></label>
			<html:text name="myHomePasswordForm" styleId="user_login" property="user_login"></html:text>


			<label><%=DicoTools.dico(dico_lang , "recovery/email")%></label>
			<html:text name="myHomePasswordForm" styleId="user_mail" property="user_mail"></html:text>

				
		<div class="div-spacer">
			<button type="submit" class="little"><span><%=DicoTools.dico(dico_lang , "recovery/button")%></span></button>
		</div>		
		
	</html:form>
	
	
</div>

<script language="javascript">
    var options = { 
        target:        '#recover-password',    
        beforeSubmit:  validateRetrievePassword,  // pre-submit callback 
        success:       function(){}  // post-submit callback 
    }; 
 
    // bind form using 'ajaxForm' 
    $('#recover-password form').ajaxForm(options); 	
    
	msgHandling.errorMsgShow();
	
	<logic:equal name="ok" value="1">
		$("div.home-login a.opened-arrow").trigger('click');
	</logic:equal>
	
</script>