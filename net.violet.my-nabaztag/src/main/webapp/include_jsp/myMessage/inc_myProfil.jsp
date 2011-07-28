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

	<div class="Column66">
	
			<!-- Fiche d'identite********************************************************************************** -->

			
			<div id="profil" class="profilBlockLeft bigFiche" style="height:365px;">   
				<logic:equal name="myProfilForm" property="isFriend" value="1">
						<h1 class="title-big ttMyFriend" style="position:absolute; right:5px; top:2px;" ><strong><%=DicoTools.dico(dico_lang, "directory/my_friend")%></strong></h1>
					</logic:equal>
			
				<bean:define id="userData" name="myProfilForm" property="profil" />
				<bean:define id="paysdata" name="userData" property="pays" />
				<bean:define id="user_id" name="userData" property="id" />
				<bean:define id="user_has_object" name="userData" property="user_has_object" />
				<bean:define id="user_pseudo" name="userData" property="user_pseudo" />
				
				
							
				<!-- ***************** -->
				<h1 class="pseudo"><%=user_pseudo.toString()%></h1>
				
				<!-- ***************** -->
				<div class="identite" >
					
					<%/* GARCON */%> <logic:equal name="userData" property="annu_sexe" value="H"><%=DicoTools.dico(dico_lang, "directory/sexe_man")%>,</logic:equal>
					<%/* FILLE */%> <logic:equal name="userData" property="annu_sexe" value="F"><%=DicoTools.dico(dico_lang, "directory/sexe_woman")%>, </logic:equal>
					
					<%/* AGE */%>
					<logic:greaterEqual name="userData" property="age" value="1">
						<bean:write name="userData" property="age" /> &nbsp;<%=DicoTools.dico(dico_lang, "directory/years")%>, 
					</logic:greaterEqual>
					
					<bean:write name="userData" property="annu_city" />, <bean:write name="paysdata" property="pays_nom" />.
					
				</div>

				<div class="description" style="position:relative;" >
	
						
					<div class="photoContener" style="background:url(<logic:equal name="userData" property="user_good" value="1"><logic:equal name="userData" property="user_image" value="0">../photo/default_B.jpg</logic:equal><logic:equal name="userData" property="user_image" value="1">../photo/<%=user_id%>_B.jpg</logic:equal></logic:equal><logic:equal name="userData" property="user_good" value="0">../photo/default_B.jpg</logic:equal>) no-repeat; background-position:center center;">
						&nbsp;
					</div>
					<div class="comment">
						<bean:write name="userData" property="user_comment" />
						<logic:equal name="myProfilForm" property="isFriend" value="0">
							<bean:define name="myProfilForm" property="userlog_id" id="us" />
							<bean:define name="myProfilForm" property="user_id" id="friend_id"/>
							<logic:notEqual name="myProfilForm" property="userlog_id" value="<%=friend_id.toString()%>">
							 	<a id="buttonAddFriend" class="href-big-button bbAddFriend" href='javascript:;' onclick="addFriend('<%=friend_id%>')"><strong><%=DicoTools.dico(dico_lang, "profile/add_to_friends")%></strong></a>
							</logic:notEqual>
						</logic:equal>
					</div>
					<div class="commandes">
					<ul>
					
						<%/* Si l'utilisateur possède un lapin */%>
						<logic:equal name="userData" property="user_has_object" value="true">
							<li class="envoyer"><%=DicoTools.dico(dico_lang, "directory/send")%></li>

										<li class="msgLink">
											<a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo.toString()%>', 'text');"><%=DicoTools.dico(dico_lang, "directory/send_text")%></a>
										</li>
										<li class="msgLink">
											<a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo.toString()%>', 'mp3');"><%=DicoTools.dico(dico_lang, "directory/send_mp3")%></a>
										</li>
										<li class="msgLink">
											<a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo.toString()%>', 'bibliotheque');"><%=DicoTools.dico(dico_lang, "directory/send_music")%></a>
										</li>
							<li class="msgLink">
								<a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo.toString()%>', 'clindoeil');"><%=DicoTools.dico(dico_lang, "directory/send_clins")%></a>
							</li>
						</logic:equal>
						
						<%-- Si l'utilisateur ne possède pas de lapin --%>
						<logic:notEqual name="userData" property="user_has_object" value="true">
							<li class="msgLink" ><%=DicoTools.dico_jsp(dico_lang , "directory/no_nabaztag", user_pseudo)%></li>
						</logic:notEqual>
						
					</ul>
					<hr class="clearer" />
				</div>
				</div>
				

    
		</div>
		<!-- /Fiche d'identité********************************************************************************** -->		
	</div>

	<div class="Column33">
		
		<div class="profilBlockRight">
			<div id="signature">   
				<h1><%=DicoTools.dico(dico_lang, "profile/signature")%></h1>
					<bean:define name="userData" property="user_signature" id="user_signature"/>
					<embed id="mymovie" width="135" height="135" flashvars="cdll=<%=user_signature.toString() %>" quality="high" name="mymovie" src="../include_flash/CDLEditor.swf" type="application/x-shockwave-flash"/>				
			</div>
		</div>
		
		<hr class="spacer" />
				
		<div class="profilBlockRight" id="nabshare">
			<h1><%=DicoTools.dico(dico_lang, "profile/nabshare")%></h1>
			<div class="profil-scroller">

				<logic:empty name="myProfilForm" property="nabShares">
					<%=DicoTools.dico(dico_lang, "profile/nabshare_none")%>
				</logic:empty>

				<logic:notEmpty name="myProfilForm" property="nabShares">
					<ul>
						<logic:iterate name="myProfilForm" property="nabShares" id="musicData">
							<li>
								<bean:write name="musicData" property="label"/>
							</li>
						</logic:iterate>
					</ul>			
				</logic:notEmpty>

			</div>
		</div>
		
		<hr class="spacer" />
				
		<div class="profilBlockRight" style="display:none;"> <%-- on ne l'affiche pas pour l'instant, ça bouzille l'interface --%>
			<div id="nabcast">   
				<h1><%=DicoTools.dico(dico_lang, "profile/nabcast")%></h1>

				<div class="profil-scroller">				
					<logic:empty name="myProfilForm" property="nabCast">
						<%=DicoTools.dico(dico_lang, "profile/nabcast_none")%>
					</logic:empty>				
					
					<logic:notEmpty name="myProfilForm" property="nabCast">
							<ul>
								<logic:iterate name="myProfilForm" property="nabCast" id="nabcastData">
									<li>
										<bean:define name="nabcastData" property="nabcast_id" id="nabcast_id"/>
										<bean:write name="nabcastData" property="nabcast_title"/>
									</li>
								</logic:iterate>
							</ul>
					</logic:notEmpty>								
				</div>

			</div> 	   		
		</div>		
		
	</div>
	
	<hr class="spacer" />	

	<div class="Column50">
		<div class="profilBlockLeft">	
		<div id="message_recu">   
			<h1><%=DicoTools.dico(dico_lang, "profile/messages_received")%></h1>
			<ul><logic:iterate name="myProfilForm" property="messagesReceived" id="messageReceivedData"><li>
				<bean:define name="messageReceivedData" property="id" id="message_id"/>
				<bean:define name="messageReceivedData" property="userSenderId" id="event_sender"/>
					<bean:write name="messageReceivedData" property="dateOfDelivery"/>
					<a href="javascript:;" onclick="sendMsgToWithText('<bean:write name="messageReceivedData" property="sender_name"/>', 'text');" ><bean:write name="messageReceivedData" property="title"/></a>
					<bean:write name="messageReceivedData" property="nbPlayed"/>x
			</li></logic:iterate></ul>		
		</div>   
		</div>
	</div>
	<div class="Column50">
		<div class="profilBlockRight">	
		<div id="message_envoyer"> 
			<h1><%=DicoTools.dico(dico_lang, "profile/messages_sent")%></h1>
			<ul><logic:iterate name="myProfilForm" property="sendMessages" id="MessageSentData"><li>
				<bean:define name="MessageSentData" property="id" id="message_id"/>
				<bean:define name="MessageSentData" property="userSenderId" id="event_sender"/>
		
					<bean:write name="MessageSentData" property="dateOfDelivery"/>
					<a href="#"><bean:write name="MessageSentData" property="title"/></a>
					<bean:write name="MessageSentData" property="nbPlayed"/>x

			</li></logic:iterate></ul>
		</div>  
		</div>
	</div>
	
<script type="text/javascript">
	// actif que quand loggué
	if ($("#_user_main").length != 0) {
		$("#buttonAddFriend").show();
	}
</script>		
