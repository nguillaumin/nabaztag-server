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

<bean:define name="myAddFriendForm" property="profil" id="userData"/>
<bean:define name="userData" property="user_pseudo" id="friend_name" />

<logic:equal name="myAddFriendForm" property="mode" value="6">
<%=friend_name%> <%=DicoTools.dico(dico_lang, "friend/already_in_list")%>
</logic:equal>

<logic:equal name="myAddFriendForm" property="mode" value="7">
<%=friend_name%> <%=DicoTools.dico(dico_lang, "friend/added_to_list")%> 
</logic:equal>

<logic:equal name="myAddFriendForm" property="mode" value="8">
<%=DicoTools.dico(dico_lang, "friend/already_asked")%> <%=friend_name%> <%=DicoTools.dico(dico_lang, "friend/to_be_friend")%>
<%=DicoTools.dico(dico_lang, "friend/wait_answer")%>
</logic:equal>

<logic:equal name="myAddFriendForm" property="mode" value="9">
<%=DicoTools.dico(dico_lang, "friend/mail_sent")%> <%=friend_name%> <%=DicoTools.dico(dico_lang, "friend/ask_be_friend")%>
</logic:equal>

<logic:equal name="myAddFriendForm" property="mode" value="-1">
<%=DicoTools.dico(dico_lang, "friend/error")%>
</logic:equal>

