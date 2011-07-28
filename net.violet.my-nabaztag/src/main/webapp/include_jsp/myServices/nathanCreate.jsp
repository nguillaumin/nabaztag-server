<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools"%>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8");%>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="/include_jsp/utils/inc_dico.jsp"%>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>


<%-- a insérer dans un setUpSrv-container --%>

<%--affichage d'un message d'erreur --%>
<script type="text/javascript">
<!--
	<logic:equal name="mySrvNathanCreateForm" property="error" value="1">
			$.msgPopupOverlay("Impossible d'uploader cette partie.", 8000);		
	</logic:equal>
	<logic:equal name="mySrvNathanCreateForm" property="error" value="2">
			$.msgPopupOverlay("La version ne contient aucun son.", 8000);		
	</logic:equal>
	<logic:equal name="mySrvNathanCreateForm" property="error" value="3">
			$.msgPopupOverlay("La création a échoué.", 8000);		
	</logic:equal>
//-->
</script>


<div id="setUpSrv">
	<bean:define id="url" name="mySrvNathanCreateForm" property="url" />
	<%String dicoRoot = ""; %>
	<logic:notEmpty name="mySrvNathanCreateForm" property="mp3IdList">
		<% dicoRoot = "_next"; %>
	</logic:notEmpty>

	<div class="main-cadre-contener serviceContener serviceBookReaderConfig">	
		<div class="main-cadre-top" >
		
			<h2><%=DicoTools.dico(dico_lang , "srv_nathan/add_title")%></h2>
		</div>
		
		<div class="main-cadre-content" >
			<div  class="nathan-return"><a href="myNablife.do?service=<%=url %>"><%=DicoTools.dico(dico_lang , "srv_nathan/add_back")%></a></div>
			<hr class="clearer" />
			<div class="nathan">		
		
			<%=DicoTools.dico(dico_lang , "srv_nathan/add_content"+dicoRoot)%>
			<h3><%=DicoTools.dico(dico_lang , "srv_nathan/create_mic"+dicoRoot)%></h3>
			<%=DicoTools.dico(dico_lang , "srv_nathan/create_mic_long"+dicoRoot)%>
		
			<div class="ztamps-contener">
			
				<bean:define id="idApplet" name="mySrvNathanCreateForm" property="appletId" />
				<bean:define id="isbn" name="mySrvNathanCreateForm" property="isbn" />

				<bean:define id="mp3Id" name="mySrvNathanCreateForm" property="mp3Id" />
				<bean:define id="serverPath" name="mySrvNathanCreateForm" property="serverPath" />


				<%-- l'utilisateur a uploade au moins une partie --%>
				<logic:notEmpty name="mySrvNathanCreateForm" property="mp3IdList">
				
				<%-- form de suppression d'une partie --%>
				<html:form action="/action/srvNathanCreate.do" method="post" styleId="removeForm">
				
					<html:hidden property="dispatch" value="delete"/>	
					<html:hidden property="isbn" value="<%=isbn.toString()%>"/>
					<html:hidden property="appletId" value="<%=idApplet.toString()%>"/>
					
					<%-- la liste des ids existants--%>
					<ul>
						<%int count = 1; %>
						<logic:iterate name="mySrvNathanCreateForm" property="mp3IdList" id="fragment">
							<input type="hidden" name="mp3IdList" value="<%=fragment%>" />
							
							<li><%=DicoTools.dico(dico_lang , "srv_nathan/create_part")%> <%=count++%> 
								<a href="#js" onclick="$(this).parents('form').find('input[name=mp3Id]').val( '<%=fragment%>' );
														page.postAjax('removeForm', 'setUpSrv-container'); return false;">
									<%=DicoTools.dico(dico_lang , "srv_nathan/delete_part")%>
								</a>
							</li>
						</logic:iterate>
					</ul>

					<input name="mp3Id" type="hidden" id="mp3Id" value="<%=mp3Id%>">
				</html:form>
				
				<%=DicoTools.dico(dico_lang , "srv_nathan/finish_content")%>
				
				<%-- form de validation --%>
				<html:form action="/action/srvNathanCreate.do" styleId="finishForm">
				
					<html:hidden property="dispatch" value="finish"/>	
					<html:hidden property="isbn" value="<%=isbn.toString()%>"/>
					<html:hidden property="appletId" value="<%=idApplet.toString()%>"/>
					
					<!-- les ids associes a des fichiers deja uploades -->
					<div class="button-bloc">
					<logic:iterate name="mySrvNathanCreateForm" property="mp3IdList" id="fragment">
						<input type="hidden" name="mp3IdList" value="<%=fragment%>" />
					</logic:iterate>
					
					<html:submit value="<%=DicoTools.dico(dico_lang , "srv_nathan/create_finish")%>" onclick="page.postAjax('finishForm', 'setUpSrv-container'); return false;"/>
					
					</div>
				</html:form>	
				
				</logic:notEmpty>


				<%-- form d'ajout d'une partie --%>
				
				<html:form action="/action/srvNathanCreate.do" method="post" enctype="multipart/form-data" styleId="addForm">
				
					<html:hidden property="dispatch" value="add"/>	
					<html:hidden property="isbn" value="<%=isbn.toString()%>"/>
					<html:hidden property="appletId" value="<%=idApplet.toString()%>"/>
					
					<%-- la liste des ids existants--%>
						<logic:iterate name="mySrvNathanCreateForm" property="mp3IdList" id="fragment">
							<input type="hidden" name="mp3IdList" value="<%=fragment%>" />
						</logic:iterate>

					<input name="mp3Id" type="hidden" id="mp3Id" value="<%=mp3Id%>">
  
  					<%=DicoTools.dico(dico_lang , "srv_nathan/add_player"+dicoRoot)%>
  					<div>
					<div id="flashcontent" class="enreg_box">
					</div>
					
					<div class="enreg_img">
	<img title="Enregistrez directement ici" alt="Enregistrez directement ici" src="../include_img/lapinmicro.gif"/>
					</div>
					</div>
					<hr class="clearer"/>
					
					<h3><%=DicoTools.dico(dico_lang , "srv_nathan/create_soft"+dicoRoot)%></h3>
					<%=DicoTools.dico(dico_lang , "srv_nathan/create_soft_long"+dicoRoot)%>
					<%=DicoTools.dico(dico_lang , "srv_nathan/add_file"+dicoRoot)%>
					<html:file property="mp3File" />
					<br />
					<%=DicoTools.dico(dico_lang , "srv_nathan/add_comment"+dicoRoot)%>
					<div class="button-bloc">
					<html:submit value="<%=DicoTools.dico(dico_lang , "srv_nathan/add_part")%>" onclick="if($(this).parents('form').find('input[name=mp3File]').val() == ''){$(this).parents('form').find('input[name=mp3File]').remove();} page.postAjax('addForm', 'setUpSrv-container'); return false;" />
					</div>
				</html:form>
				
				<hr class="clearer"/>
				
			</div>

			<hr class="clearer" />
		</div>	
	
		</div>
	</div>
	
</div>


<div id="how-to-container" class="main-cadre-contener">

	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/how_does_it_work")%></h2></div>
	
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<div class="srv-main-config">
			<p>	<%=DicoTools.dico(dico_lang , "srv_nathan/how_to_create")%></p>
		</div>
		
		<hr class="spacer"/>
			
	</div>
</div>
	
	
<script type="text/javascript">
<!--
	var so = new SWFObject("../include_flash/audioControl.swf", "audiocontrol", "300", "150", "9", "#FFFFFF");
	so.addParam("wmode", "transparent");
	so.addVariable("myServer","<%=serverPath%>");
	so.addVariable("mySound","<%=mp3Id%>");
	so.addVariable("playMode","true");
	so.addVariable("message","<%=DicoTools.dico(dico_lang, "myMessagesVocal/warning")%>");
	if (!so.write("flashcontent")) { // bad player detection > force creation
		var container = document.getElementById("flashcontent");
		container.innerHTML = so.getSWFHTML();
	}
//-->
</script>

