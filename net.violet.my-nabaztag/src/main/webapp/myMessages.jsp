<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="net.violet.platform.datamodel.Application" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<%
	response.setContentType("text/html;charset=UTF-8");
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
	Lang dico_lang =	SessionTools.getLangFromSession(session, request);
	session.setAttribute("page_title","myMessages");
	
	String action = request.getParameter("action"); // récup de l'action dans l'url
%>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>
<bean:define name="myMessagesForm" property="onglet" id="onglet"/>
<bean:define name="myMessagesForm" property="userId" id="user_id" />
<bean:define id="nabnameToSendMsgTo" name="myMessagesForm" property="pseudo" />
<bean:define name="myMessagesForm" property="langUser" id="lang" type="String"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title><%=DicoTools.dico(dico_lang , "myMessage/page_title")%></title>

<%@ include file="/include_jsp/utils/inc_css.jsp" %>
<%@ include file="/include_jsp/utils/inc_javascripts.jsp" %>

</head>
<body onload='javascript:getNewMessages();'>

	<div id="container">
		<div id="header">
			<%@ include file="/include_jsp/utils/inc_header.jsp" %>
		</div>
	
		<div id="wrapper"> <%-- ******************************************************************************************************** --%>
			<!-- COLONNE CENTRALE -->
			<div id="centercontent" > 

				<div class="tabNavContener">
					<h1 class="icoTitle_Messages">&nbsp;</h1>	
					<ul class="tabNav">
						<li id="Envoyer"><a href="#"><%=DicoTools.dico(dico_lang , "myMessage/tab_send")%></a></li>
						<li id="Recu"><a href="../action/myMessagesList.do?action=received"><span><%=DicoTools.dico(dico_lang , "myMessage/tab_received")%> <span id="nbMessagesReceived"></span></span></a></li>
						<li id="Archives"><a href="../action/myMessagesList.do?action=archived"><span><%=DicoTools.dico(dico_lang , "myMessage/tab_archived")%></span></a></li>
						<li id="Envoyes"><a href="../action/myMessagesList.do?action=sent"><%=DicoTools.dico(dico_lang , "myMessage/tab_sent")%></a></li>			
					</ul>
			    </div>
				
				<div class="mainTab">
					<div class="mainTabBody">
						<%--  Envoyer ******************************************************************************************************************* --%>
						
			
						<div id="contentEnvoyer" class="contentMainTab messages-contener">
	
							<form action="toto.html" name="sendMsg" id="sendMsg" method="post" enctype="multipart/form-data">
								<input type="hidden" value="" id="send" name="send" />
								
								<div class="specialBlock" id="blocDest">
									<input type="hidden" name="color" value="-1" id="color" />
									<label>
										<%=DicoTools.dico(dico_lang , "main/sendto")%>
										<input name="destName" type="text" id="destName" value="<%=nabnameToSendMsgTo%>"/>
							     	 </label>
							
									<span class="ou"><%=DicoTools.dico(dico_lang , "main/or")%></span>
							   
									<html:select name="myMessagesForm" property="friendObjectName" styleId="friendObjectId" style="width:130px;" onchange="document.getElementById('destName').value=''">
										<option value="" selected="selected"><%=DicoTools.dico(dico_lang , "main/choose_friend")%></option>
										<logic:iterate name="myMessagesForm" property="listeAmis" id="ami">
											<logic:notEmpty name="ami" property="user_pseudo">
											<bean:define id="object_login" name="ami" property="user_pseudo"/>
											<option value="<%=object_login%>"><%=object_login%></option>
											</logic:notEmpty>
										</logic:iterate>
									</html:select> 
							    
							    	<span class="ou"><%=DicoTools.dico(dico_lang , "main/or")%></span> <a onclick='findPeopleRandomly();' href='javascript:;' class="shuffle"><%=DicoTools.dico(dico_lang , "main/anybody")%></a><br />
									<a href="myAnnuaire.do?height=515&width=800" class="href-big-button bbAnnuaire thickbox" title="<%=DicoTools.dico(dico_lang, "main/directory_search_alt")%>" ><strong><%=DicoTools.dico(dico_lang , "main/directory_search")%></strong></a> </div>
									
									<div class="bt-Envoyer-contener" style="position:relative;">
										<input type="button" value="<%=DicoTools.dico(dico_lang , "main/button_listen")%>" class="genericBt" onClick="validateSendMessage(0)"/>
				
										<logic:greaterThan name="myMessagesForm" property="userId" value="0">
											<input type="button" value="<%=DicoTools.dico(dico_lang , "main/button_send")%>" class="genericBt" onClick="validateSendMessage(1)"/>
										</logic:greaterThan>
	
										<logic:equal name="myMessagesForm" property="userId" value="0">
											<a href="../action/mySession.do?height=170&width=250" class="thickbox">
												<input type="button" value="<%=DicoTools.dico(dico_lang , "main/button_send")%>" class="genericBt" />
											</a>
										</logic:equal>
									</div>
							  
							  		<div id="Messages">
							  
										<div class="tabSousNavContener-tr">
											<ul class="tabSousNav">
												<li id="Messages_texte"><a href="javascript:void(0);" onClick="tabMessageChangeUrl( 'Messages_texte' , '../action/myMessagesTTS.do','../action/myMessagesSendTTS.do');"><span><%=DicoTools.dico(dico_lang , "message_type/text")%></span></a></li>
												<li id="Messages_vocal"><a href="javascript:void(0);" onClick="tabMessageChangeUrl( 'Messages_vocal' , '../action/myMessagesVocal.do','../action/myMessagesSendVocal.do');"><span><%=DicoTools.dico(dico_lang , "message_type/voice")%></span></a></li>
												<li id="Messages_mp3"><a href="javascript:void(0);" onClick="tabMessageChangeUrl( 'Messages_mp3' , '../action/myMessagesMp3.do','../action/myMessagesSendMp3.do');"><span><%=DicoTools.dico(dico_lang , "message_type/mp3")%></span></a></li>
												<li id="Messages_clindoeil"><a href="javascript:void(0);" onClick="tabMessageChangeUrl( 'Messages_clindoeil' , '../action/myMessagesClin.do','../action/myMessagesSendClin.do');"><span><%=DicoTools.dico(dico_lang , "message_type/clins")%></span></a></li>
												<li id="Messages_bibliotheque"><a href="javascript:void(0);" onClick="tabMessageChangeUrl( 'Messages_bibliotheque' , '../action/myMessagesBiblio.do','../action/myMessagesSendBiblio.do');"><span><%=DicoTools.dico(dico_lang , "message_type/biblio")%></span></a></li>
												<li id="Messages_nabshare"><a href="javascript:void(0);" onClick="tabMessageChangeUrl( 'Messages_nabshare' , '../action/myMessagesNabshare.do','../action/myMessagesSendMp3.do');"><span><%=DicoTools.dico(dico_lang , "message_type/nabshare")%></span></a></li>
											</ul>  	
								
							        		<div class="tabSousNavContener-bl">
												<div id="myPlayerMp3Message" class="myPlayerMp3Message"></div>
							          			<div id="boxContent">
										            <p>&nbsp;</p>
										            <p>&nbsp;</p>
										            <p>&nbsp;</p>
										            <p>&nbsp;</p>
										            <p>&nbsp;</p>
								          		</div>
											
												<hr class="spacer" />
		
								          		<div id="MoreOptions" class="specialBlock">
								          			<a class="bigLink" href="javascript:void(0);" onclick="messagesMoreOptionsToggle();">
														<%=DicoTools.dico(dico_lang , "main/more_options")%>
													</a>
								            		
													<div id="MoreOptions01" class="MoreOptionsContent"  >
								              			<div id="mp3persoDiv" class="separator">
								                			<input name="saveMp3Perso" type="checkbox" value="save_mp3" id="mp3perso" />
								                			<label for="mp3perso"><%=DicoTools.dico(dico_lang , "main/save_MP3_perso")%></label>
								              			</div>
								              			
														<div class="separator">
								                			<input name="sendLater" type="checkbox" value="send_later" id="plustard" onClick="messagesDateToggle();"/>
								                			<label for="plustard" >
								                				<%=DicoTools.dico(dico_lang , "main/send_later")%>
															</label>
								              			</div>
													
														<div class="moremore_options separator" id="moremore" style="display:none; margin-left:25px;">
									                		<input id="choixJourDiff" name="choixJourDiff" type="hidden" />
									                		<input id="choixMoisDiff" name="choixMoisDiff" type="hidden" />
									                		<input id="choixAnneeDiff" name="choixAnneeDiff" type="hidden" />
									                		<input id="choixHeureDiff" name="choixHeureDiff" type="hidden" />
									                		<input id="choixMinuteDiff" name="choixMinuteDiff" type="hidden" />
									                		
															<div style="float:left;">
									                  			<input id="date_delay" type="hidden" class="datePicker" />
									                		</div>
															
									                		<div  style="float:left; margin-left:10px;">
									                  			<input id="time" type="hidden" class="hourPicker" />
									                		</div>
														
									                		<hr class="spacer" />
														
									              		</div>
											  
										              	<div style="margin-top:8px;padding-bottom:8px;">
															<fieldset>
											                	<legend>
											                		<%=DicoTools.dico(dico_lang , "main/colors_picker")%>
																</legend>
											                
																<ul class="colorSet">
																	<li><a href="javascript:selectColor('mycol_0')"><img src="../include_img/messages/couleur_00.gif" alt="" border="0" class="color_unselected" id="mycol_0"/></a></li>
																	<li><a href="javascript:selectColor('mycol_4')"><img src="../include_img/messages/couleur_01.gif" alt="" border="0" class="color_unselected" id="mycol_4"/></a></li>
																	<li><a href="javascript:selectColor('mycol_1')"><img src="../include_img/messages/couleur_02.gif" alt="" border="0" class="color_unselected" id="mycol_1"/></a></li>
																	<li><a href="javascript:selectColor('mycol_5')"><img src="../include_img/messages/couleur_03.gif" alt="" border="0" class="color_unselected" id="mycol_5"/></a></li>
																	<li><a href="javascript:selectColor('mycol_3')"><img src="../include_img/messages/couleur_04.gif" alt="" border="0" class="color_unselected" id="mycol_3"/></a></li>
																	<li><a href="javascript:selectColor('mycol_6')"><img src="../include_img/messages/couleur_05.gif" alt="" border="0" class="color_unselected" id="mycol_6"/></a></li>
																	<li><a href="javascript:selectColor('mycol_2')"><img src="../include_img/messages/couleur_06.gif" alt="" border="0" class="color_unselected" id="mycol_2"/></a></li>
																</ul>
															
																<hr class="clearer"/>
															</fieldset>
														</div>
													</div>
													
								            		<div class="bt-Envoyer-contener" >
								              			<input type="button" value="<%=DicoTools.dico(dico_lang , "main/button_listen")%>" class="genericBt" onClick="validateSendMessage(0)"/>
							              				<logic:greaterThan name="myMessagesForm" property="userId" value="0">
							                				<input type="button" value="<%=DicoTools.dico(dico_lang , "main/button_send")%>" class="genericBt" onClick="validateSendMessage(1)"/>
							              				</logic:greaterThan>
							              				
														<logic:equal name="myMessagesForm" property="userId" value="0">
							                				<a href="../action/mySession.do?height=170&width=250" class="thickbox">
							                					<input type="button" value="<%=DicoTools.dico(dico_lang , "main/button_send")%>" class="genericBt" />
							                				</a>
							              				</logic:equal>
								            		</div>
													
								          		</div>
											</div>	
						
										</div>
									</div>
								</form>


					</div>			
			
			
						<%-- /Envoyer ******************************************************************************************************************* --%>
					
						<%-- Messages Recus --%>
						<div id="contentRecu" class="contentMainTab messages-contener">
							.
						</div>
						<%-- /Messages Recus --%>
						
						<%-- Messages archives --%>
						<div id="contentArchives" class="contentMainTab messages-contener">
							.
						</div>
						<%-- /Messages archives --%>
						
						<%-- Messages envoyes --%>
						<div id="contentEnvoyes" class="contentMainTab messages-contener">
							.
						</div>
						<%-- /Messages envoyes --%>
	
	
						<%-- prévenir mes amis --%>
						<div id="contentTellFriends" class="contentMainTab messages-contener">
							.
						</div>
						<%-- /prévenir mes amis --%>
					
											
						<hr class="clearer" />
				
					</div>
				</div>
				
			</div>
			<!-- /COLONNE CENTRALE -->
		</div>

		
		<div id="leftcontent"> <%-- ******************************************************************************************************** --%>
			<!-- COLONNE GAUCHE -->
			<div class="contener"></div>
			<!-- /COLONNE GAUCHE -->
		</div>
		
		<div id="rightcontent"> <%-- ******************************************************************************************************** --%>
			<!-- COLONNE DROITE -->
			<div class="contener"></div>
			<!-- /COLONNE DROITE -->
		</div>	
		
		<div id="footer">
			<div class="copyright"><%=DicoTools.dico(dico_lang , "footer/copyright")%> | <a href="#"><%=DicoTools.dico(dico_lang , "footer/contact_link")%></a></div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function(){
			//DEBUG_activate();
					
			<logic:present parameter="action" >
				performAction("<%=action%>");
			</logic:present>
			
			<logic:notPresent parameter="action" >				
				<logic:empty name="myMessagesForm" property="onglet">
					mainTab_GoTab('Envoyer'); // par defaut
				</logic:empty>
				<logic:notEmpty name="myMessagesForm" property="onglet">
					$('#<%=onglet%> a').trigger("click");
				</logic:notEmpty>
				
				tabMessageChangeUrl( 'Messages_texte' , '../action/myMessagesTTS.do','../action/myMessagesSendTTS.do');
			</logic:notPresent>
	
			$('input.datePicker').dateSelect_Init( $("#_user_lang").val() );
			$('input.hourPicker').hourSelect_Init( $("#_user_24").val() );
								
			<logic:notEmpty name="myMessagesForm" property="popup">
				TB_show('','../action/srvDialogConfig.do?dispatch=load&applicationId=<%=Application.NativeApplication.EARS_COMMUNION.getApplication().getId()%>&height=1&width=400');
			</logic:notEmpty>
	
	
			
		});
	
	</script>

</body>
</html>