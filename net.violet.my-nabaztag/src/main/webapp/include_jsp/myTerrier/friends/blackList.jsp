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

	
<logic:notEmpty name="myTerrierBlackListForm" property="listBlack">

<div class="flat-block">

<div class="flat-block-top">
<h3 class="no-icone"><%=DicoTools.dico(dico_lang, "myTerrier/my_blacklist")%></h3>
</div>

<div class="flat-block-content">
<div class="flat-block-content-inner">
	
	<form action="../action/myTerrierBlackList.do" method="post" id="update_black">
	
	<div class="actionFriends">
		<label><%=DicoTools.dico(dico_lang, "myTerrier/black_actions")%></label>
		<select name="selectChoice" id="actionFriends">
			<option value="deleteSelected"><%=DicoTools.dico(dico_lang, "myTerrier/black_delete_selection")%></option>
		</select>
		<input type="submit" value="<%=DicoTools.dico(dico_lang, "myTerrier/black_actions_ok_button")%>" class="genericBt" />
	</div>
	
	<hr class="spacer"/>
	
	<div class="listeAmis">
		<logic:iterate name="myTerrierBlackListForm" property="listBlack" id="userdata">
			<bean:define id="pseudo" name="userdata" property="user_pseudo" />
			<bean:define id="user_id" name="userdata" property="id" />
			<bean:define id="user_good" name="userdata" property="user_good" />
			<bean:define id="user_image" name="userdata" property="user_image" />
			<bean:define id="city" name="userdata" property="annu_city" />
			<bean:define id="nom_pays" name="userdata" property="pays_nom" />
			<bean:define id="age" name="userdata" property="age" />

			<div class="amis" >
			<input type="checkbox" name=checkBlackList value="<%=user_id%>" class="checkboxFriends" />			
				<div class="img">
					<logic:equal name="userdata" property="user_good" value="1">
						<logic:equal name="userdata" property="user_image" value="0">
							<img class="photo" align="left" src="../photo/default_S.jpg" 
								width="100" height="100" />
						</logic:equal>
						<logic:equal name="userdata" property="user_image" value="1">
							<img class="photo" align="left" src="../photo/<%=user_id%>_S.jpg"
								width="100" height="100" />
						</logic:equal>
					</logic:equal> 
					<logic:equal name="userdata" property="user_good" value="0">
						<img class="photo" align="left" src="../photo/default_S.jpg"
							width="100" height="100" />
					</logic:equal> 	
					<hr class="clearer" />		
				</div>
						
				<div class="infos">
						<span class="pseudo"><%=pseudo %></span><br /> 
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
						<br />
						<logic:notEmpty name="userdata" property="black_comment">
							<bean:define id="comment" name="userdata" property="black_comment" />
							<div title="<%=comment%>" style="height:40px; overflow:hidden;"><%=comment%></div>
						</logic:notEmpty>
						
				</div>
				<hr class="spacer" />
				<ul class="liens">
					<li>
						<a class="bSimpleAjaxLink" target="black-contener" href="<html:rewrite forward="goTerrierBlackList"/>?checkBlackList=<%=user_id%>"  ><%=DicoTools.dico(dico_lang, "myTerrier/black_delete")%></a>
					</li>
					<li>
						<a onclick="TB_show('', 'myProfil.do?height=515&width=800&user_id=<%=user_id%>');" href="javascript:;"><%=DicoTools.dico(dico_lang, "myTerrier/black_view_profile")%></a>
					</li>				
				</ul>				
			</div>
		</logic:iterate>		
	</div>
	<hr class="spacer"/>
			
	
	</form>
	
	</div>
	</div>
	</div>
	
	
	</logic:notEmpty>
	


