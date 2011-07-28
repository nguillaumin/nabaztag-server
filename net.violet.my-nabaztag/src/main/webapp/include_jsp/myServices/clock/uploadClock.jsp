<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="java.util.List" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request); %>

<%-- Soit l'action me renvois un message d'erreur ou de succès --%>
<logic:messagesPresent>
<textarea> 
	<%-- du JS dans un textarea ? ceci est NORMAL :) le plugin form execute automatiquement le JS qui est inclus dans une balise textarea --%>
	var msg;
	var type_msg;
	<logic:messagesPresent message="errors" property="actionSuccess"> 
		msg="<html:errors property="actionSuccess"/>";
		type_msg = "success";
	</logic:messagesPresent>  
	
	<logic:messagesPresent message="errors" property="fileSizeError"> 
		msg="<html:errors property="fileSizeError"/>";
		type_msg = "error";		
	</logic:messagesPresent>  
	
	<logic:messagesPresent message="errors" property="fileTypeError"> 
		msg="<html:errors property="fileTypeError"/>";
		type_msg = "error";		
	</logic:messagesPresent>  
	
	<logic:messagesPresent message="errors" property="actionError"> 
		msg="<html:errors property="actionError"/>"
		type_msg = "error";		
	</logic:messagesPresent>  
</textarea>
 	
</logic:messagesPresent> 

<%-- Soit l'action me renvois le formulaire de base --%>
<logic:messagesNotPresent>

	<div class="main-cadre-contener main-cadre-ext">
		
			<div class="main-cadre-top">
				<h2 class="new-sticker" ><span>NEW - </span><%=DicoTools.dico(dico_lang , "srv_clock/nabaztime_title")%></h2>
			</div>
			
		
			<div class="main-cadre-content">
				<%-- ************************************************************************************************************* --%>
				<div class="intro">
					<%=DicoTools.dico(dico_lang, "srv_clock/uploadText")%>
				</div>
				<html:form action="/action/srvClockUpload" styleId="uploadMP3_form" enctype="multipart/form-data">
	
						<hr class="spacer" />
						<div class="srv-cfg-line" >
							<label class="line-label"><%=DicoTools.dico(dico_lang , "srv_clock/language")%></label>
							<div class="line-form" >
								<html:select property="lang_selected" >
									<logic:iterate name="mySrvClockUploadForm" property="langList" id="langData">
										<bean:define name="langData" property="lang_id" id="lang_id"/>
										<bean:define name="langData" property="lang_title" id="lang_title"/>
										<bean:define name="langData" property="lang_type" id="lang_type"/>
							
										<logic:lessEqual name="lang_type" value="0">
											<html:option value="<%=lang_id.toString()%>"><%=lang_title.toString()%></html:option>
										</logic:lessEqual>
									</logic:iterate>
								</html:select>
							</div>
						</div>
						
						<div class="srv-cfg-line" >
							<label class="line-label"><%=DicoTools.dico(dico_lang , "srv_bilan/time")%></label>
							<div class="line-form" >
								<html:select property="hour" styleId="spokenLanguage">
									<logic:iterate  name="mySrvClockUploadForm" property="listHour" id="listHour" type="String">
										<html:option value="<%=listHour.toString()%>"/>
									</logic:iterate>
								</html:select>
						
								<bean:define name="mySrvClockUploadForm" property="dico_hour" id="dico_hour" type="String"/>
								<%=DicoTools.dico(dico_lang , dico_hour.toString())%>
							
								<logic:equal name="mySrvClockUploadForm" property="hour_mode" value="12" >
									<html:select property="morning" style="width:60px">
										<html:option value="true">am</html:option>
										<html:option value="false">pm</html:option>
									</html:select>
								</logic:equal>
								
							</div>	
						</div>
	
	
						<div class="srv-cfg-line" >
							<label class="line-label" ><%=DicoTools.dico(dico_lang, "myTerrier/mp3_upload_file")%></label>
							<div class="line-form" ><html:file name="mySrvClockUploadForm" property="musicFile" styleId="musicFile" /></div>
						</div>
									
						<div align="center" style="clear:both;">	
							<input type="hidden" name="dispatch" value="uploadMP3" />
							<html:submit styleClass="genericBt" property="start" value="<%=DicoTools.dico(dico_lang , "srv_clock/upload_button")%>"/>
						</div>
						
						<hr class="spacer" />
						
						<%-- <div style="text-align:right;">
						<%=DicoTools.dico(dico_lang , "srv_clock/note1")%>
						</div>--%>					
				</html:form>
		
				<%-- ************************************************************************************************************* --%>	
			</div>
	</div>

	<script type="text/javascript">


		$("#uploadMP3_form").ajaxForm({
			beforeSubmit: function(a,f,o){
				var valid = nablifeValidateHorlogeUpload(a,f,o);
				if (valid) {
					$("#uploadMP3_form").parents(".main-cadre-contener").block();
				}
				
				return valid;
			},
			dataType:  "script",
			success: function(data) {
				$("#uploadMP3_form").parents(".main-cadre-contener").unblock();
				$("#uploadMP3_form").resetForm();
				if (type_msg == "error"){
					customAlertN(msg);
				} else {
					$.msgPopupOverlay(msg);
				}
			}
		});
	

	</script>
		
</logic:messagesNotPresent>
