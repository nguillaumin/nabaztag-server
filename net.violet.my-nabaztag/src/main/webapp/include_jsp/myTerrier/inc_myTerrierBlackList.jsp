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



		<h1><%=DicoTools.dico(dico_lang, "myTerrier/my_blacklist")%></h1>

	
	<div class="blackList">
			<a class="href-big-button"  onclick="divChangeUrl('contentMesAmis', '<html:rewrite forward="goTerrierFriends" />?dispatch=load')" href="javascript:;"><strong><%=DicoTools.dico(dico_lang, "myTerrier/manage_friendlist")%></strong></a>
	</div>
	<hr class="spacer"/>
	
	<logic:notEmpty name="myTerrierBlackListForm" property="listBlack">
	<form action="../action/myTerrierBlackList.do" method="post" id="update_black">
	<div class="listeAmis">
		<logic:iterate name="myTerrierBlackListForm" property="listBlack" id="userData">
			<bean:define id="paysdata" name="userData" property="pays" />
			<bean:define id="pseudo" name="userData" property="user_pseudo" />
			<bean:define id="user_id" name="userData" property="id" />
			<bean:define id="user_good" name="userData" property="user_good" />
			<bean:define id="user_image" name="userData" property="user_image" />
			<bean:define id="city" name="userData" property="annu_city" />
			<bean:define id="nom_pays" name="userData" property="pays_nom" />
			<bean:define id="age" name="userData" property="age" />
			<div class="amis" >
			<input type="checkbox" name=checkBlackList id="friendSelected" value="<%=user_id%>" class="checkboxFriends" />			
				<div class="img">
					<logic:equal name="userData" property="user_good" value="1">
						<logic:equal name="userData" property="user_picture" value="0">
							<img class="photo" align="left" src="../photo/default_S.jpg" 
								width="100" height="100" />
						</logic:equal>
						<logic:equal name="userData" property="user_image" value="1">
							<img class="photo" align="left" src="../photo/<%=user_id%>_S.jpg"
								width="100" height="100" />
						</logic:equal>
					</logic:equal> 
					<logic:equal name="userData" property="user_good" value="0">
						<img class="photo" align="left" src="../photo/default_S.jpg"
							width="100" height="100" />
					</logic:equal> 	
					<hr class="clearer" />		
				</div>
						
				<div class="infos">
					<p><span class="pseudo"><%=pseudo%></span> <%=age%> <%=DicoTools.dico(dico_lang, "myTerrier/black_years")%></p>
					<p><%=city%> <%=nom_pays%></p>
				</div>
				<hr class="spacer" />
				<ul class="liens">
					<li>
						<a onclick="divChangeUrl('contentMesAmis', '<html:rewrite forward="goTerrierBlackList"/>?checkBlackList=<%=user_id%>')" href="javascript:;"><%=DicoTools.dico(dico_lang, "myTerrier/black_delete")%></a>
					</li>
					<li>
						<a onclick="TB_show('', 'myProfil.do?height=515&width=800&user_id=<%=user_id%>');" href="javascript:;"><%=DicoTools.dico(dico_lang, "myTerrier/black_view_profile")%></a>
					</li>				
				</ul>				
			</div>
		</logic:iterate>		
	</div>
	
	<hr class="spacer"/>
			
	<div class="actionFriends">
		<label><%=DicoTools.dico(dico_lang, "myTerrier/black_actions")%></label>
		<select name="selectChoice" id="actionFriends">
			<option value="deleteSelected"><%=DicoTools.dico(dico_lang, "myTerrier/black_delete_selection")%></option>
		</select>
		<input type="button" value="<%=DicoTools.dico(dico_lang, "myTerrier/black_actions_ok_button")%>" class="genericBt" onclick="submitAjaxForm('update_black','contentMesAmis');"/>
	</div>
	</form>
	</logic:notEmpty>
	<logic:empty name="myTerrierBlackListForm" property="listBlack">
		<hr class="spacer"/>
		<div class="noFriends">
		<p><%=DicoTools.dico(dico_lang, "myTerrier/black_nobody")%></p>
		</div>
	</logic:empty>
	<hr class="spacer"/>



