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

<bean:define property="isReg" name="mySrvGoogleForm" id="isReg"/>
<bean:define property="isCon" name="mySrvGoogleForm" id="isCon" />
<bean:define name="mySrvGoogleForm" property="friendList" id="list"/>
<bean:define property="serviceName" name="mySrvGoogleForm" id="serviceName" type="String"/>

<!-- ----------------------------La liste de amis Google--------------------------------------------- -->
<h1>dico_if(dico_lang, serviceName)</h1>
<label><%=DicoTools.dico(dico_lang , "srv_gtalk/friends_list")%></label><br>
<logic:empty name="list">
	<%=DicoTools.dico(dico_lang , "srv_gtalk/no_friend_added")%>
</logic:empty>

<logic:notEmpty name="list">
	<ul>
	<logic:iterate name="list" id="gfld">
		<li>
		<bean:define name="gfld" property="id" id="id"/>
		<bean:define name="gfld" property="name" id="name"/>
		<bean:define name="gfld" property="titleSoundon" id="titleon"/>
		<bean:define name="gfld" property="titleSoundoff" id="titleoff"/>
		<bean:define name="gfld" property="state" id="state"/>				
		
		<%=name%><br>
		<%=DicoTools.dico(dico_lang , "srv_gtalk/whos_online")%><%=titleon%><br>
		<%=DicoTools.dico(dico_lang , "srv_gtalk/whos_offline")%><%=titleoff%><br>
		<logic:equal name="state" value="1"> <%=DicoTools.dico(dico_lang , "srv_gtalk/online")%></logic:equal><br>
		<logic:equal name="state" value="0"> <%=DicoTools.dico(dico_lang , "srv_gtalk/offline")%></logic:equal><br>
		
		<a href='<html:rewrite forward="srvGoogle"/>?add=2&friendName=<%=name%>'><%=DicoTools.dico(dico_lang , "srv_gtalk/modify")%></a>
		<a href='<html:rewrite forward="srvGoogle"/>?delete=1&deleteFriend=<%=name%>'><%=DicoTools.dico(dico_lang , "srv_gtalk/delete")%></a>
		</li>
	</logic:iterate>
	</ul>
</logic:notEmpty>
<br>
<br>

<!--  -----------------------------Info du Compte Gmail----------------------------------------------- -->
<html:form action="/action/srvGoogle" styleClass="srvConfigForm">
<html:hidden name="mySrvGoogleForm" property="add" value="0"/>
<html:hidden name="mySrvGoogleForm" property="delete" value="0"/>
<bean:define name="mySrvGoogleForm" property="checkCon" id="checkCon" />

	<label><%=DicoTools.dico(dico_lang , "srv_gtalk/your_gtalk_account")%></label>
	<br>
	
	<label><%=DicoTools.dico(dico_lang , "srv_gtalk/adress_or_login")%></label>
	<html:text name="mySrvGoogleForm" property="login"></html:text>
	<br>
	<label><%=DicoTools.dico(dico_lang , "srv_gtalk/password")%></label>
	<html:password name="mySrvGoogleForm" property="pass"></html:password>
	<br>
	
	<logic:equal name="checkCon" value="0">
		<%=DicoTools.dico(dico_lang , "srv_gtalk/bad_parameters")%>
	</logic:equal>
	
	<logic:equal name="isReg" value="0">
		<html:hidden name="mySrvGoogleForm" property="createAccount" value="1"/>
		<html:submit value="<%=DicoTools.dico(dico_lang , "srv_gtalk/bad_parameters_button_ok")%>"/>
	</logic:equal>
	
	<logic:equal name="isReg" value="1">
		<logic:equal name="isCon" value="0">
			<%=DicoTools.dico(dico_lang , "srv_gtalk/bad_parameters")%>
		</logic:equal>
		<br>
		<html:hidden name="mySrvGoogleForm" property="createAccount" value="2"/>
		<html:submit value="<%=DicoTools.dico(dico_lang , "srv_gtalk/button_modify")%>"/>
	</logic:equal>
</html:form>

<html:form action="/action/srvGoogle">
	<logic:equal name="isReg" value="1">
		<html:hidden name="mySrvGoogleForm" property="createAccount" value="3"/>
		<html:submit value="<%=DicoTools.dico(dico_lang , "srv_gtalk/button_delete")%>"/>
	</logic:equal>
</html:form>
<br>
<br>



<!-- ------------------------------Alertes ami--------------------------------------------------------- -->

<html:form action="/action/srvGoogle">
<bean:define id="jList" name="mySrvGoogleForm" property="jabberList"/>
<bean:define id="sList" name="mySrvGoogleForm" property="soundList" />
<html:hidden name="mySrvGoogleForm" property="add" value="1"/>
<html:hidden name="mySrvGoogleForm" property="delete" value="0"/>


	<label><%=DicoTools.dico(dico_lang , "srv_gtalk/add_modify_alert")%></label>
	<br>
	
	<html:select name="mySrvGoogleForm" property="jabberFriend">
		<option value="nofriendselected"><%=DicoTools.dico(dico_lang , "srv_gtalk/choose_friend")%></option>
		<html:optionsCollection name="jList" label="label" value="id"/>
	</html:select>
	<br>
	
	<label><%=DicoTools.dico(dico_lang , "srv_gtalk/when_friend_connects")%></label>
	<html:select name="mySrvGoogleForm" property="soundon">
		<option value="nosoundselected"><%=DicoTools.dico(dico_lang , "srv_gtalk/choose_sound")%></option>
		<html:optionsCollection name="sList" label="music_name" value="music_url"/>
	</html:select>
	<br>
	<label><%=DicoTools.dico(dico_lang , "srv_gtalk/or_type_text")%></label>
	<html:textarea name="mySrvGoogleForm" property="textConnect">
	</html:textarea>
	<br>
	<br>
	
	<label><%=DicoTools.dico(dico_lang , "srv_gtalk/when_friend_disconnects")%></label>
	<html:select name="mySrvGoogleForm" property="soundoff">
		<option value="nosoundselected"><%=DicoTools.dico(dico_lang , "srv_gtalk/choose_sound")%></option>
		<html:optionsCollection name="sList" label="music_name" value="music_url"/>
	</html:select>
	<br>
	<label><%=DicoTools.dico(dico_lang , "srv_gtalk/or_type_text")%></label>
	<html:textarea name="mySrvGoogleForm" property="textDeconnect">
	</html:textarea>
	<br>
	<br>
	

	<html:submit value="<%=DicoTools.dico(dico_lang , "srv_gtalk/add_alert")%>"/>
</html:form>

