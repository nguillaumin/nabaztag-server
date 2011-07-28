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

<bean:define id="mode" name="myNabaztalandUploadForm" property="mode" />

<logic:notEqual name="mode" value="4">
<!-- *** Partie liste des nabcastvals *** -->
<hr class="spacer" />
<bean:define id="nd" name="myNabaztalandUploadForm" property="nabcastData"/>
<bean:define id="nabcastName" name="nd" property="nabcast_titre" />
<bean:define id="idNabcast" name="myNabaztalandUploadForm" property="idNabcast"/>

<bean:define id="nbrAbonn" name="myNabaztalandUploadForm" property="nbrAbonn"/>

<div class="Column33">
	<div class="profilBlockLeft diffusionNabcasts" >
		<h2><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/already_broadcasted")%></h2>
		<%/* on a une liste vide*/%>
		<logic:empty name="myNabaztalandUploadForm" property="nabcastValSent">
			<%=DicoTools.dico(dico_lang, "srv_nabcast_upload/none")%>
		</logic:empty>
		
		<%/* on a une liste non vide*/%>
		<logic:notEmpty name="myNabaztalandUploadForm" property="nabcastValSent">
			<ul>
				<logic:iterate name="myNabaztalandUploadForm" property="nabcastValSent" id="old">
					<bean:define id="titre" name="old" property="nabcastval_titre"/>
					<bean:define id="id_val" name="old" property="nabcastval_id"/>
					<bean:define id="tr" name="old" property="nabcastval_timerelative"/>
					<li>
						<div class='date'><a class="suppr" onclick="confirmNabcastMp3Delete('<html:rewrite forward="goNabcastUpload"/>?mode=3&idNabcast=<%=idNabcast%>&idNabcastVal=<%=id_val%>');" href='javascript:;'><span><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/delete")%></span></a><%=tr%></div>
						<div class='titre'><%=titre%></div>						
					</li>
				</logic:iterate>
			</ul>
		</logic:notEmpty>		

		<h2><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/not_yet_broadcasted")%></h2>
		<%/* on a une liste vide*/%>
		<logic:empty name="myNabaztalandUploadForm" property="nabcastValToSend">
			<%=DicoTools.dico(dico_lang, "srv_nabcast_upload/none")%>	
		</logic:empty>

		<%/* on a une liste non vide*/%>
		<logic:notEmpty name="myNabaztalandUploadForm" property="nabcastValToSend">
		<ul>
			<logic:iterate name="myNabaztalandUploadForm" property="nabcastValToSend" id="old">
			<bean:define id="titre" name="old" property="nabcastval_titre"/>
			<bean:define id="id_val" name="old" property="nabcastval_id"/>
			<bean:define id="tr" name="old" property="nabcastval_timerelative"/>
			<li> 
				<div class='date'><a class="suppr" onclick="confirmNabcastMp3Delete('<html:rewrite forward="goNabcastUpload"/>?mode=3&idNabcast=<%=idNabcast%>&idNabcastVal=<%=id_val%>')" href='javascript:;'><span><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/delete")%></span></a><%=tr%></div>
				<div class='titre'><%=titre%></div>
				
			</li>
			</logic:iterate>
		</ul>
		</logic:notEmpty>

	</div>
	
</div>	
<!-- **** Partie Upload **** -->
<div class="Column66 nabcastMp3Upload">
	<div class="profilBlockRight createNabcast">
			<h2><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/broadcast_mp3")%></h2>

			<fieldset>
				
				<legend><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/add_mp3")%></legend>	
				
				<div id="formUpload"></div>

			</fieldset>		
		
		
		<!-- **** Partie Ajout diffusion **** -->
		
			<html:form action="/action/myNabaztalandUpload" styleId="nabaztalandAdd">
			<html:hidden property="idNabcast" value="<%=idNabcast.toString()%>" />
			<html:hidden property="mode" value="2" />
			<fieldset>	
				<legend><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/select_mp3")%></legend>	
			    <ul>
				<li>
					<label><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/list_mp3")%></label>
					<html:select name="myNabaztalandUploadForm" property="idMp3" styleId="idMp3">
						<option value=""><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/choose_mp3")%></option>
						<html:optionsCollection name="myNabaztalandUploadForm" property="mp3List" label="music_name_short" value="id"/>
					</html:select>
				</li>
				<li>
					<label><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/diffusion_name")%></label>
					<input type="text" value="" name="song_title" class="myName"/>
				</li>
				<li>
					<label><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/broadcast_date")%></label>
					<div style="float:left;">
						<label style=" white-space:nowrap"><input class="normal" type="checkbox" name="checkbox" value="checkbox" id="myNabcastLatercheck" onclick="myNabcastLaterToggle();" />&nbsp;<%=DicoTools.dico(dico_lang, "srv_nabcast_upload/send_later")%></label>

						<div id="myNabcastLater" style="display:none; clear:both;">
							<hr class="spacer" />
							<input id="date_delay" name="date_delay" type="text" class="datePicker" />	
							<hr class="clearer" />	
							<p class="little-hint"><%=DicoTools.dico(dico_lang, "srv_nabcast_upload/date")%></p>

							<hr class="spacer" />						
							<input id="time" type="hidden" class="hourPicker" />
							<hr class="spacer" />							
						</div>
					</div>
				</li>
			</ul>
			<p class="validButton">
			  <input name="send" type="button" id="send" value="<%=DicoTools.dico(dico_lang, "srv_nabcast_upload/button_send")%>" class="normal genericBt" onclick="nablifeValidateAddMp3();" />
			</p>

			</fieldset>					

			
			</html:form>

	</div>	
</div>

<script language="javascript">
	$("#formUpload").uploadMp3Form_Init('<html:rewrite forward="goNabcastUpload" />', 'myNabaztalandUploadForm', 'song_path|song_title', 'idNabcast:<%=idNabcast.toString()%>|mode:1'); 
	$('input.datePicker').dateSelect_Init( $("#_user_lang").val() );
	$('input.hourPicker').hourSelect_Init( $("#_user_24").val() );
	myNabcastTitleUpdate( " - <small><%=nbrAbonn%> <%=DicoTools.dico(dico_lang, "srv_nabcast_upload/subscribers")%></small>" );
</script>

</logic:notEqual>

<logic:equal name="mode" value="4">
	<script language="javascript">
		<bean:define property="idMp3" name="myNabaztalandUploadForm" id="idMp3"/>
		parent.$("#formUpload").uploadMp3Form_End(<%=idMp3%>, 'idMp3');
	</script>
</logic:equal>

