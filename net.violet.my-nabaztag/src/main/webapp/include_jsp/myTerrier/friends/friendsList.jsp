<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>

<%	Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>

<%-- Si l'action me renvoit un message d'erreur ou de succès --%>
<ul class="general-msg">
	<logic:messagesPresent message="errors" property="friendInexistent">
		<li class="error"><html:errors property="friendInexistent"/></li>
	</logic:messagesPresent>  

	<logic:messagesPresent message="errors" property="failed">
		<li><html:errors property="failed"/></li>
	</logic:messagesPresent>  

	<logic:messagesPresent message="errors" property="alreadyFriend">
		<li><html:errors property="alreadyFriend"/></li>
	</logic:messagesPresent>  

	<logic:messagesPresent message="errors" property="succes">
		<li><html:errors property="succes"/></li>
	</logic:messagesPresent>  

	<logic:messagesPresent message="errors" property="mailSent">
		<li><html:errors property="mailSent"/></li>
	</logic:messagesPresent>  

	<logic:messagesPresent message="errors" property="mustWait">
		<li><html:errors property="mustWait"/></li>
	</logic:messagesPresent>  
	<logic:messagesPresent message="errors" property="blackedUser">
		<li><html:errors property="blackedUser"/></li>
	</logic:messagesPresent>  
</ul>


	

	
	
<div class="flat-block">
	<div class="flat-block-top">
		<h3 class="no-icone"><%=DicoTools.dico(dico_lang, "myTerrier/friends_add")%></h3>
	</div>

	<div class="flat-block-content">
		<div class="flat-block-content-inner">
		
			<!-- zones pour entrer un ami directement -->
			<html:form action="/action/myTerrierFriendsList" styleId="add_new_friend" >
				<html:hidden  property ="dispatch" value="addFriend"/>
				
				
				<div class="form-line">
					<label class="center">
						<%=DicoTools.dico(dico_lang, "myTerrier/friends_add_nabname")%>
					</label>
					<span>
						<html:text property="name" value="" /><br/>
					</span>
				</div>
												
				<div class="buttons">
					<html:submit property="submit" styleClass="genericBt" value="<%=DicoTools.dico(dico_lang, "js/ajouter")%>"  />
				</div>
			</html:form>
		</div>
	</div>
</div>
	
<hr class="clearer" />
	
	
<logic:notEmpty name="myTerrierFriendsForm" property="reqList">	
<div class="flat-block">

<div class="flat-block-top">
<h3 class="no-icone"><%=DicoTools.dico(dico_lang, "myTerrier/friends_invitation_titre")%></h3>
</div>

<div class="flat-block-content">
<div class="flat-block-content-inner">

	<!-- La liste des gens qui m'ont demand� -->
		<%=DicoTools.dico(dico_lang, "myTerrier/friends_who_asked")%>
		<ul class="listPseudo" >
			<logic:iterate name="myTerrierFriendsForm" property="reqList" id="friendData">
				<bean:define name="friendData" property="user_id" id="friend_id"/>
				<bean:define name="friendData" property="user_pseudo" id="friend_login"/>
				<li>
					<strong><%=friend_login%></strong><%=DicoTools.dico(dico_lang, "myTerrier/friends_asking")%>
	
					<a class="bSimpleAjaxLink" target="friends-contener" href="myTerrierFriendsList.do?dispatch=acceptFriend&friend_id=<%=friend_id%>" ><%=DicoTools.dico(dico_lang, "myTerrier/friends_accept")%></a> | 
					<a class="bSimpleAjaxLink" target="friends-contener" href="myTerrierFriendsList.do?dispatch=acceptFriendAdd&friend_id=<%=friend_id%>" ><%=DicoTools.dico(dico_lang, "myTerrier/friends_accept_add")%></a> | 
					<a class="bSimpleAjaxLink" target="friends-contener" href="myTerrierFriendsList.do?dispatch=declineFriend&friend_id=<%=friend_id%>" ><%=DicoTools.dico(dico_lang, "myTerrier/friends_refuse")%></a>
				</li>
			</logic:iterate>
		</ul>
		
	<!-- La liste des gens a qui j'ai demand� -->
	<logic:notEmpty name="myTerrierFriendsForm" property="ansList">
		<%=DicoTools.dico(dico_lang, "myTerrier/friends_I_asked")%>
		<ul class="listPseudo" >
			<logic:iterate name="myTerrierFriendsForm" property="ansList" id="friendData">
				<bean:define name="friendData" property="user_id" id="friend_id"/>
				<bean:define name="friendData" property="user_pseudo" id="friend_login"/>
				<li>
					<strong><%=friend_login%></strong>
					
					<a class="bSimpleAjaxLink" target="contentMesAmis" href='<html:rewrite forward="goTerrierFriends"/>?dispatch=cancelFriend&friend_id=<%=friend_id%>' ><%=DicoTools.dico(dico_lang, "myTerrier/friends_cancel")%></a>
				</li>
			</logic:iterate>
		</ul>
	</logic:notEmpty>
	
</div>
</div>
	
</div>
</logic:notEmpty>

<logic:empty name="myTerrierFriendsForm" property="reqList">
<logic:notEmpty name="myTerrierFriendsForm" property="ansList">	

<div class="flat-block">

<div class="flat-block-top">
<h3 class="no-icone">Mes invitations</h3>
</div>

<div class="flat-block-content">
<div class="flat-block-content-inner">

<%=DicoTools.dico(dico_lang, "myTerrier/friends_I_asked")%>
<ul class="listPseudo" >
			<logic:iterate name="myTerrierFriendsForm" property="ansList" id="friendData">
				<bean:define name="friendData" property="user_id" id="friend_id"/>
				<bean:define name="friendData" property="user_pseudo" id="friend_login"/>
				<li>
					<strong><%=friend_login%></strong>
					
					<a class="bSimpleAjaxLink" target="contentMesAmis" href='<html:rewrite forward="goTerrierFriends"/>?dispatch=cancelFriend&friend_id=<%=friend_id%>' ><%=DicoTools.dico(dico_lang, "myTerrier/friends_cancel")%></a>
				</li>
			</logic:iterate>
		</ul>
		
		</div>
</div>
	
</div>

</logic:notEmpty>
</logic:empty>
	

<logic:notEmpty name="myTerrierFriendsForm" property="listFriends">
<div class="flat-block">

	<div class="flat-block-top">
		<h3 class="no-icone"><%=DicoTools.dico(dico_lang, "myTerrier/my_friends")%></h3>
	</div>

	<div class="flat-block-content">
		<div class="flat-block-content-inner">
	
	
		<form action="../action/myTerrierFriendsList.do" method="post" id="update_friends">
		
			<div class="actionFriends">
				<label><%=DicoTools.dico(dico_lang, "myTerrier/friends_actions")%></label>
				<select name="dispatch" id="actionFriends">
					<option value="deleteFriends"><%=DicoTools.dico(dico_lang, "myTerrier/friends_withdraw_friend")%></option>
					<option value="addBlackList"><%=DicoTools.dico(dico_lang, "myTerrier/friends_put_blacklist")%></option>
				</select>
				<input type="submit" value="<%=DicoTools.dico(dico_lang, "myTerrier/friends_ok_button")%>" class="genericBt" />
			</div>
	
			<hr class="spacer"/>
		
			<div class="listeAmis">
				<logic:iterate name="myTerrierFriendsForm" property="listFriends" id="userdata">
					<bean:define id="pseudo" name="userdata" property="user_pseudo" />
					<bean:define id="user_id" name="userdata" property="id" />
					<bean:define id="user_main" name="userdata" property="userWithAtLeastOneObject" />
					<bean:define id="user_good" name="userdata" property="user_good" />
					<bean:define id="user_image" name="userdata" property="user_image" />
					<bean:define id="city" name="userdata" property="annu_city" />
					<bean:define id="nom_pays" name="userdata" property="pays_nom" />
					<bean:define id="age" name="userdata" property="age" />
					
					
					
					<div class="amis" >	
						<input type="checkbox" name="checkListFriends" value="<%=user_id%>" class="checkboxFriends" />
						<div class="img">
							<logic:equal name="userdata" property="user_good" value="1">
								<logic:equal name="user_image" value="0">
									<a onclick="TB_show('', 'myProfil.do?height=515&width=800&user_id=<%=user_id%>');" href="javascript:;"><img class="photo" align="left" src="../photo/default_S.jpg" width="100" height="100" /></a>
								</logic:equal>
								<logic:equal name="userdata" property="user_image" value="1">
									<a onclick="TB_show('', 'myProfil.do?height=515&width=800&user_id=<%=user_id%>');" href="javascript:;"><img class="photo" align="left" src="../photo/<%=user_id%>_S.jpg" width="100" height="100" /></a>
								</logic:equal>
							</logic:equal> 
							<logic:equal name="userdata" property="user_good" value="0">
								<a onclick="TB_show('', 'myProfil.do?height=515&width=800&user_id=<%=user_id%>');" href="javascript:;"><img class="photo" align="left" src="../photo/default_S.jpg" width="100" height="100" /></a>
							</logic:equal> 	
							<hr class="clearer" />		
						</div>
								
						<div class="infos">
							<span class="pseudo"><%=pseudo %></span> <br/>
							<logic:notEqual name="city" value=""><%=city%></logic:notEqual> <logic:notEqual name="nom_pays" value="">(<%=nom_pays%>)</logic:notEqual> <br/>
							<logic:greaterThan name="age" value="0">
								<%= age%> <%=DicoTools.dico(dico_lang, "myTerrier/friends_years")%>
							</logic:greaterThan>
							
							<logic:equal name="userdata" property="annu_sexe" value="H">
								<%=DicoTools.dico(dico_lang, "bloc/profile_man")%>
							</logic:equal>
							<logic:equal name="userdata" property="annu_sexe" value="F">
								<%=DicoTools.dico(dico_lang, "bloc/profile_woman")%>
							</logic:equal>
							<br/>
							
							
						</div>
					
						<ul class="liens">

							<logic:notEqual name="userdata" property="userWithAtLeastOneObject" value="0">
								<li><a href="<html:rewrite forward="goMessages"/>?nabname=<%=pseudo%>"><%=DicoTools.dico(dico_lang, "myTerrier/friends_send_message")%></a></li>
							</logic:notEqual>
							<logic:equal name="userdata" property="userWithAtLeastOneObject" value="0">
								<%-- pas de nab, c'est la honte --%>
							</logic:equal>

							<li><a onclick="TB_show('', 'myProfil.do?height=515&width=800&user_id=<%=user_id%>');" href="javascript:;"><%=DicoTools.dico(dico_lang, "myTerrier/friends_view_profile")%></a></li>				
						</ul>	
								
					</div>
				</logic:iterate>
						
			</div>
		<hr class="spacer" />
		
		
	</form>
	
	</div>
	</div>
	</div>	
	</logic:notEmpty>
	
	
	
