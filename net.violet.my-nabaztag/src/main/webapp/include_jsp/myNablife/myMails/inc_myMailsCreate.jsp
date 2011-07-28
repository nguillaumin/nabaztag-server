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
<%  boolean allowInput = false; %>

<bean:define name="myMailsCreateForm" property="srv_src" id="srv_src" />
<bean:define name="myMailsCreateForm" property="identifiant" id="identifiant" />

<bean:define name="myMailsCreateForm" property="error_upd" id="error_upd" />
<bean:define name="myMailsCreateForm" property="error_add" id="error_add" />
<bean:define name="myMailsCreateForm" property="add" id="add" />
<bean:define name="myMailsCreateForm" property="musicUrl" id="musicUrl" />
<bean:define name="myMailsCreateForm" property="displayConfig" id="displayConfig" />
<bean:define name="myMailsCreateForm" property="rabbitName" id="rabbitName" />

<logic:notEmpty name="myMailsCreateForm" property="srv_src">
	<%allowInput = true; %>
</logic:notEmpty>

<html:form action="/action/myMailsCreate.do" styleClass="srvConfigForm" styleId="srvMailConfig" method="post">
	<html:hidden name="myMailsCreateForm" property="srv_src" value="<%=srv_src.toString()%>"/>
	<html:hidden name="myMailsCreateForm" property="identifiant" value="<%=identifiant.toString()%>"/>
	
	<logic:empty name="myMailsCreateForm" property="srv_src" >
		<h2><%=DicoTools.dico(dico_lang, "srv_mail/add_account")%></h2>
	</logic:empty>
	<logic:notEmpty name="myMailsCreateForm" property="srv_src" >
		<h2><%=DicoTools.dico(dico_lang, "srv_mail/your_account")%></h2>
	</logic:notEmpty>

	<hr class="clearer" />
						
	<strong><%=DicoTools.dico(dico_lang, "srv_mail/incoming_server")%></strong>
	<html:text name="myMailsCreateForm" property="mail_serveur"  styleId="mail_serveur" disabled="<%=allowInput %>" />
	<logic:notEmpty name="myMailsCreateForm" property="srv_src">
		<html:hidden name="myMailsCreateForm" property="mail_serveur" />
	</logic:notEmpty>


	<hr class="spacer" />

	<strong><%=DicoTools.dico(dico_lang, "srv_mail/protocol")%></strong>
	<html:radio name="myMailsCreateForm" property="mail_protocol" value="pop" disabled="<%=allowInput %>"><%=DicoTools.dico(dico_lang, "srv_mail/protocol_pop")%></html:radio>
	<html:radio name="myMailsCreateForm" property="mail_protocol" value="imap" disabled="<%=allowInput %>"><%=DicoTools.dico(dico_lang, "srv_mail/protocol_imap")%></html:radio>
	<!--<html:radio name="myMailsCreateForm" property="mail_protocol" value="ssl" disabled="<%=allowInput %>"><%=DicoTools.dico(dico_lang, "srv_mail/protocol_ssl")%></html:radio>-->
	<logic:notEmpty name="myMailsCreateForm" property="srv_src">
		<html:hidden name="myMailsCreateForm" property="mail_protocol" />
	</logic:notEmpty>
	<hr class="spacer" />
	
	<!-- Secured or not -->
	<strong><%=DicoTools.dico(dico_lang, "srv_mail/protocol_ssl")%></strong>
	<html:checkbox name="myMailsCreateForm" property="secured" value="1" disabled="<%=allowInput %>" />
	<logic:notEmpty name="myMailsCreateForm" property="srv_src">
		<html:hidden name="myMailsCreateForm" property="secured" />
	</logic:notEmpty>
	<hr class="spacer" />
						
	<strong><%=DicoTools.dico(dico_lang, "srv_mail/account_name")%></strong>
	<html:text name="myMailsCreateForm" property="mail_compte" styleId="mail_compte" disabled="<%=allowInput %>" />
	<logic:notEmpty name="myMailsCreateForm" property="srv_src">
		<html:hidden name="myMailsCreateForm" property="mail_compte" />
	</logic:notEmpty>

	<hr class="spacer" />					
					
	<strong><%=DicoTools.dico(dico_lang, "srv_mail/password")%></strong>
	<html:password name="myMailsCreateForm" property="mail_password" styleId="mail_password"/>						
	<hr class="spacer" />	

<logic:equal name="displayConfig" value="1">
	<strong> <%=DicoTools.dico(dico_lang, "srv_mail/audio_flash")%></strong>
	<html:radio name="myMailsCreateForm"property="musicUrl" value="1" /><%=DicoTools.dico(dico_lang, "srv_mail/audio_flash_yes")%>
	<html:radio name="myMailsCreateForm" property="musicUrl" value="0" /><%=DicoTools.dico(dico_lang, "srv_mail/audio_flash_no")%>
	<hr class="spacer" />
	
	<strong><%=DicoTools.dico(dico_lang, "srv_mail/light_language")%> </strong>
	<html:radio name="myMailsCreateForm" property="passive" value="1"/><%=DicoTools.dico(dico_lang, "srv_mail/audio_flash_yes")%>
	<html:radio name="myMailsCreateForm" property="passive" value="0"/><%=DicoTools.dico(dico_lang, "srv_mail/audio_flash_no")%>
	
	<hr class="spacer" />
</logic:equal>
<logic:notEqual name="displayConfig" value="1">
	<html:hidden name="myMailsCreateForm"property="musicUrl" value="1" />
	<html:hidden name="myMailsCreateForm"property="passive" value="1" />
</logic:notEqual>



	
	<!-- Les filtres Keywords -->
	<div class="srv-list-entries">

		<h2><%=DicoTools.dico(dico_lang, "srv_mail/filters")%></h2>
		<strong><%=DicoTools.dico(dico_lang, "srv_mail/keywords_list")%></strong>
		<hr class="clearer">
		<html:hidden name="myMailsCreateForm" property="lumiereFilter"/>
		<html:hidden name="myMailsCreateForm" property="keyword" styleId="keyword"/>
		<logic:equal name="myMailsCreateForm" property="error_add" value="0" >
		<ul>
			<logic:greaterThan name="myMailsCreateForm" property="sizeRows" value="0">
				<logic:iterate id="row" name="myMailsCreateForm" property="rows" type="net.violet.mynabaztag.form.MyMailCreateForm">
					<bean:define id="selected_zik" name="row" property="sounds"/>
					<bean:define id="selected_light" name="row" property="light" type="java.lang.Integer"/>
					<li class="listentries">	
						<ul class="ico-list" >
							<li class="supprimer">
								<a class="link-delete suppr" onclick="mailSrvDeleteItem( this, <%=identifiant%> );" href='javascript:;'><span><%=DicoTools.dico(dico_lang, "srv_mail/delete")%></span></a>
							</li>
						</ul>
						<ul class="txt-list">						
							<label style="width:60px;"><%=DicoTools.dico(dico_lang, "srv_mail/keyword")%></label> <html:text name="row" property="keywords" disabled="true"/><br />
				
							<hr class="spacer" />
				
							<label style="width:60px;"><%=DicoTools.dico(dico_lang, "srv_mail/sound")%></label>
							<!-- MusicData -->
							<select name="listeMusiques" disabled="true">
								<logic:iterate id="zik" name="myMailsCreateForm" property="listeMusiques">
								<bean:define id="zik_id" name="zik" property="music_id"/>
								<option value="<bean:write name="zik" property="music_id"/>" <%= (selected_zik.equals(zik_id)) ? "selected" : "" %>><bean:write name="zik" property="music_name"/></option>
								</logic:iterate>
							</select>
							
							<hr class="spacer" />
							
							<strong><%=DicoTools.dico(dico_lang, "srv_mail/light_language")%></strong>&nbsp;<input type="checkbox" class="checker" disabled="true" value="1" <%= (selected_light==0) ? "" : "checked" %> />
							
							<hr class="spacer" />
						</ul>
					</li>			 
				</logic:iterate>
			</logic:greaterThan>
		</ul>
		</logic:equal>
	</div>
	
	<hr class="spacer">

	<h2><%=DicoTools.dico(dico_lang, "srv_mail/add_filter")%></h2>
	<label style="width:60px;"><%=DicoTools.dico(dico_lang, "srv_mail/keyword")%></label><input type="text" name="keywords" value="" id="add_key_input" maxlength="90" />

	<hr class="spacer" />

	<label style="width:60px;"><%=DicoTools.dico(dico_lang, "srv_mail/sound")%></label>
	<select name="sounds">
	<logic:iterate id="zik" name="myMailsCreateForm" property="listeMusiques">
	<bean:define id="zik_id" name="zik" property="music_id"/>
	<option value="<bean:write name="zik" property="music_id"/>"><bean:write name="zik" property="music_name"/></option>
	</logic:iterate>
	</select>	
	<hr class="spacer" />
	<strong><%=DicoTools.dico(dico_lang, "srv_mail/light_language")%></strong>&nbsp;<input value="1" type="checkbox" class="checker" />	
	&nbsp;<a class="thickbox link-help-txt" title="<%=DicoTools.dico(dico_lang , "srv_all/light_title")%>" href='javascript:;' onclick="TB_show('<%=DicoTools.dico(dico_lang , "srv_all/light_title")%>', '#TB_inline?height=170&width=564&inlineId=keskidit')" ><%=DicoTools.dico(dico_lang , "srv_all/light_help")%></a>
	<hr class="spacer" />
		
	<logic:notEqual name="srv_src" value="">
		<html:hidden name="myMailsCreateForm" property="add" styleId="add" value="1" />
		<html:hidden name="myMailsCreateForm" property="delete" styleId="delete" value="0" />
		<html:submit value="<%=DicoTools.dico(dico_lang, "srv_rss/modify")%>" styleClass="genericBt" />
	</logic:notEqual>
	<logic:equal name="srv_src" value="">
		<html:hidden name="myMailsCreateForm" property="add" styleId="add" value="1" />
		<html:hidden name="myMailsCreateForm" property="delete" styleId="delete" value="0" />
		<html:submit value="<%=DicoTools.dico(dico_lang, "srv_mail/button_activate")%>" styleClass="genericBt" />
	</logic:equal>	
</html:form>


<script type="text/javascript">

	$("div.srv-list-entries li.selected").removeClass("selected");	

	$("#srvItem_<%=identifiant%>").addClass("selected");
	
	//$("input.songPicker").chooseSong();
	
	<logic:empty name="myMailsCreateForm" property="srv_src" >
		$("#srvMailConfig").submit(function(){
			return nablifeValidateMailConfig(null, null); 
		});
	</logic:empty>
	
	<logic:notEmpty name="myMailsCreateForm" property="srv_src" >
		$("#srvMailConfig").submit(function(){
			return nablifeValidateMailConfig(null, <%=identifiant%>); 
		});
	</logic:notEmpty>	
	
	<%/* erreur de création du compte */%>
	<logic:equal name="myMailsCreateForm" property="error_add" value="1" >
		$("#srvMailConfig").msgPopup("<%=DicoTools.dico(dico_lang, "srv_mail/connexion_problem")%>", gErrorColor, 4000);
	</logic:equal>
	
	<%/* compte déjà surveillé par ce lapin */%>
	<logic:equal name="myMailsCreateForm" property="error_add" value="2" >
		$("#srvMailConfig").msgPopup("<%=DicoTools.dico(dico_lang, "srv_mail/account_already_monitored")%> <%=rabbitName%> ", gErrorColor, 4000);
	</logic:equal>
	
	<%/* pas erreur de creation de compte*/%>

	$("input.checker").selectSrvLum();
	
	
</script>
