<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools"%>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8");%>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="/include_jsp/utils/inc_dico.jsp"%>


<%	Lang dico_lang =SessionTools.getLangFromSession(session, request);%>

<bean:define name="mySrvPlayerForm" property="dicoRoot" id="dicoRoot"></bean:define>
<div id="setUpSrv">
	
	<logic:notEqual name="mySrvPlayerForm" property="isMarkup" value="0">
			<div class="main-cadre-contener serviceContener serviceBookReaderConfig">	
				<div class="main-cadre-top" >
					<h2>
						<%=DicoTools.dico(dico_lang , "services/configure")%>
					</h2>
				</div>
				
				<div class="main-cadre-content" >
					<!-- ******************************************** CONTENT ***************************************************** --> 

					
					<div class="intro">
							<%=DicoTools.dico(dico_lang , "srv_"+dicoRoot.toString()+"/config_intro")%>							
					</div>
					
					<logic:iterate name="mySrvPlayerForm" property="mySetting" id="listSetting">
						<div class="ztamps-contener">
							<bean:define id="idApplet" name="mySrvPlayerForm" property="appletId" />
							<bean:define id="isbn" name="mySrvPlayerForm" property="isbn" />
							<bean:define id="picture_value" name="listSetting" property="pictureObject" />
							<bean:define id="ztamp_id" name="listSetting" property="ztampId" />
							<bean:define id="ztamp_serial" name="listSetting" property="ztampSerial" />
							
							<div class="ztamps-pic">
								<img src="../include_img/ztamps/<%=picture_value%>.jpg" /><br />
								<%=DicoTools.dico(dico_lang , "services/books_legend_ztamp")%>
							</div>
							
							<logic:equal name="listSetting" property="voiceId" value="-1">
								<bean:define id="object_login" name="listSetting" property="nameObjectLastRead" />
								<bean:define id="user_id" name="listSetting" property="idUserLastRead" />
								<div style="margin-left:110px;">
										<%=DicoTools.dico_jsp(dico_lang , "services/books_lastReader", object_login, user_id )%>
								</div>
							</logic:equal>
		
							<logic:notEqual name="listSetting" property="voiceId" value="-1">
								<html:form action="/action/srvPlayerConfig" >
									<input type="hidden" name="bookId" value="<%=ztamp_id%>" />
									<input type="hidden" name="isbn" value="<%=isbn%>" />
									<input type="hidden" name="bookSerial" value="<%=ztamp_serial%>" />
									<input type="hidden" name="appletId" value="<%=idApplet%>" />
											<html:hidden property ="dispatch" value="update" />
									<div style="margin-left:110px;">
									<logic:notEqual  name="mySrvPlayerForm" property="sizeVoice" value="1">
										<p>
											<span><%=DicoTools.dico(dico_lang , "services/books_choose_voice")%></span>
											<%-- ***********TODO pour l'instant on a que deux voix, cependant il faudrait automatiser cette liste selon le player qu'on utilise donc stocké en base ************ --%>
											<html:select name="mySrvPlayerForm" property="voiceId" >
												<option value="0" ><%=DicoTools.dico(dico_lang , "services/books_voice1")%></option>
												<logic:notEqual name="listSetting" property="voiceId" value="0">
													<option value="1" selected="selected"><%=DicoTools.dico(dico_lang , "services/books_voice2")%></option>
												</logic:notEqual>
												<logic:equal name="listSetting" property="voiceId" value="0">
													<option value="1"><%=DicoTools.dico(dico_lang , "services/books_voice2")%></option>
												</logic:equal>
											</html:select>
											<br />
											<html:submit property="button" style="margin-top:5px;" styleId="update" value="<%=DicoTools.dico(dico_lang , "services/books_updateConfig")%>" styleClass="genericBt" /><br />
										</p>
									</logic:notEqual>
										<logic:notEqual name="listSetting" property="markUpIndex" value="0">
											<p style="margin-top:11px;">
												<span><%=DicoTools.dico(dico_lang , "services/books_reset_story")%></span>
													<%=DicoTools.dico(dico_lang , "services/books_reset_story_txt")%><br />
													<html:submit property="button" style="margin-top:5px;" styleId="resetApplet" value="<%=DicoTools.dico(dico_lang , "services/books_reset")%>" styleClass="genericBt" />
											</p>
										</logic:notEqual>
								
		
									</div>
								</html:form>
						</logic:notEqual>
						</div>
					</logic:iterate>
		
					<div>
						<hr class="spacer" />
						<h3><%=DicoTools.dico(dico_lang , "services/books_howTo")%></h3>
						<%=DicoTools.dico(dico_lang , "services/books_howToText")%>
					</div>
					<hr class="clearer" />
					
					<%-- ************************************************************************************************************* --%>
				</div><!-- End of main content -->
			</div>

	</logic:notEqual>
	
	
	<logic:equal name="mySrvPlayerForm" property="isMarkup" value="0">
			<div class="main-cadre-contener serviceContener">	
				<div class="main-cadre-top" >
					<h2>
						<%=DicoTools.dico(dico_lang , "services/configure")%>
					</h2>
				</div>
				
				<div class="main-cadre-content" >
					<!-- ******************************************** CONTENT ***************************************************** -->
					<%=DicoTools.dico(dico_lang , "srv_"+dicoRoot.toString()+"/config_nobooks")%>
					<!-- ******************************************** /CONTENT ***************************************************** -->
				</div>
			</div>
	</logic:equal> 
</div>	
	
<script type="text/javascript">

	function initBookReader(){
	
		// sur un click d'un bouton, on met le dispatch a la bonne valeur
		$("input[name=button]").bind("click", function(){
	
			$(this)
				.parents("form")
				.find("input[name=dispatch]")
				.val( $(this).attr('id') );
		});
		
		
		$("div.serviceContener form").ajaxForm({
			beforeSubmit: function(formData,f,o){
				$("#setUpSrv").block();
			},
			success: function (data) {
				var r = $(data)[0];
				var h = $(r).html();
				$("#setUpSrv")
					.html(h)
					.unblock();
				
				initBookReader();
				msgHandling.simpleMsgShow( msg_txt['modif_srv_ok'] );
			}	
		});
	}
	
	initBookReader();


</script>