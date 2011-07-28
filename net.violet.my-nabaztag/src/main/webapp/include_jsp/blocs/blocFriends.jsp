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

<h1><%=DicoTools.dico(dico_lang , "bloc/friends_title")%></h1>

<div class="block-content blocListPeople">
<div class="inner">
	<%/* Tu n'as pas d'amis */ %>
	<logic:empty name="BlocForm" property="result">
		<p><%=DicoTools.dico(dico_lang , "bloc/friends_no_friends_description")%>&nbsp;<a onclick="TB_show('', 'myAnnuaire.do?height=515&width=800');" href='javascript:;'><%=DicoTools.dico(dico_lang , "bloc/friends_link_directory")%></a></p>
	</logic:empty>
	
	<%/* T'as pleins d'amis */ %>
	<logic:notEmpty name="BlocForm" property="result" >
			
		<logic:iterate name="BlocForm" property="result" id="userData">
			<logic:notEmpty name="userData" property="user_pseudo">

			<bean:define id="pseudo" name="userData" property="user_pseudo" />
			<bean:define id="paysdata" name="userData" property="pays" />
			<bean:define id="user_id" name="userData" property="id" />
			<bean:define id="user_main" name="userData" property="userWithAtLeastOneObject" />
			<bean:define id="user_good" name="userData" property="user_good" />
			<bean:define id="user_image" name="userData" property="user_image" />
			<bean:define id="city" name="userData" property="annu_city" />
			<bean:define id="nom_pays" name="paysdata" property="pays_nom" />
			<bean:define id="age" name="userData" property="age" />
			<bean:define id="sexe" name="userData" property="annu_sexe" />
			
			<% String sex_java = sexe.equals("H") ? "bloc/profile_man" : "bloc/profile_woman";%>
			
			<ul class="entry-full" style="display:none;" >		
				
				<li class="photo">
					<a onclick="TB_show('<%=DicoTools.dico(dico_lang , "bloc/friends_title_overlay")%>', '<html:rewrite forward="goProfil"/>?height=515&width=800&user_id=<%=user_id%>&myFriend=1')" href="javascript:;">
						<logic:equal name="userData" property="user_good" value="1">
							<logic:equal name="userData" property="user_image" value="0">
								<img class="photo" align="left" src="../photo/default_S.jpg" width="74" height="74" border="0">
							</logic:equal>
						
							<logic:equal name="userData" property="user_image" value="1">
								<img class="photo" align="left" src="../photo/<%=user_id%>_S.jpg" width="74" height="74" border="0">
							</logic:equal>
						</logic:equal>
		
						<logic:equal name="userData" property="user_good" value="0">
							<img class="photo" align="left" src="../photo/default_S.jpg" width="74" height="74" border="0">
						</logic:equal>
					</a>
				</li>
				
				<li class="pseudo"><%=pseudo%></li>
				
				<li>
					<%=DicoTools.dico(dico_lang , sex_java)%>
					<logic:notEqual name="userData" property="age" value="0">
						, <%=age%> <%=DicoTools.dico(dico_lang , "bloc/profile_age")%>
					</logic:notEqual>
				</li>			
				
				<li class="location"><strong><%=nom_pays%></strong>, <%=city%></li>
				
				<logic:notEqual name="userData" property="userWithAtLeastOneObject" value="0">
					<li class="annuaire" ><a href="javascript:;" onclick="sendMsgTo('<%=pseudo%>')"><%=DicoTools.dico(dico_lang , "myTerrier/friends_send_message")%></a></li>
				</logic:notEqual>
				<logic:equal name="userData" property="userWithAtLeastOneObject" value="0">
					<%-- cas ou la personne n'a pas de lapin --%>
				</logic:equal>
			
			</ul>
			
			<ul class="entry" >
				<%/* Link to send message, or not if the user doesn't have any rabbit */ %>
				<logic:notEqual name="userData" property="userWithAtLeastOneObject" value="0">
					<li class="pseudo"><a class="tooltiped TTfriends" title="<%=DicoTools.dico(dico_lang , sex_java)%><logic:notEqual name="userData" property="age" value="0"><br><%=age%> <%=DicoTools.dico(dico_lang , "bloc/profile_age")%></logic:notEqual><br><%=nom_pays%>, <%=city%>" href="javascript:;" onclick="sendMsgTo('<%=pseudo%>')"><%=pseudo%></a></li>
				</logic:notEqual>
				<logic:equal name="userData" property="userWithAtLeastOneObject" value="0">
					<li class="pseudo"><a class="tooltiped TTfriends" title="<%=DicoTools.dico(dico_lang , sex_java)%><logic:notEqual name="userData" property="age" value="0"><br><%=age%> <%=DicoTools.dico(dico_lang , "bloc/profile_age")%></logic:notEqual><br><%=nom_pays%>, <%=city%>" href="javascript:;"><%=pseudo%></a></li>
				</logic:equal>
			</ul>
			
			</logic:notEmpty>
		</logic:iterate>
		<hr class="spacer" />		
	</logic:notEmpty>
	</div>
</div>	


<script language="javascript">
$('a.TTfriends').Tooltip({
	delay: 0,
	track: true,
	showURL: false
});
 </script>
 