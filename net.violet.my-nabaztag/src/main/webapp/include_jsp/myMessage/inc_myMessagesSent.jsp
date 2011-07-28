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

<bean:define name="myMessagesListForm" property="index" id="index" type="Integer"/>
<bean:define name="myMessagesListForm" property="nabcast" id="nabcast" type="Integer"/>

<form action="../action/myMessagesList.do" method="post" id="update_listSent">
	<input type="hidden" name="action" value="sent">

<table width="100%" cellspacing="0" cellpadding="0" class="messagesListeTable" id="messagesListeSent">
	<thead>
		<tr>		
			<th class="date"><%=DicoTools.dico(dico_lang, "myMessages/date")%></th>
			<th class="send-to"><%=DicoTools.dico(dico_lang, "myMessages/sent")%></th>
			<th class="title"><%=DicoTools.dico(dico_lang, "myMessages/message_title")%></th>
			<th class="played"><%=DicoTools.dico(dico_lang, "myMessages/played")%></th>
			<th class="select">&nbsp;</th>
			<th class="select">&nbsp;</th>	
		</tr>
	</thead>
	<tbody>
	<logic:notEmpty name="myMessagesListForm" property="listeMessages">	
		<logic:iterate name="myMessagesListForm" property="listeMessages" id="messageSentData">
				<bean:define name="messageSentData" property="id" id="message_id" />
			 	<bean:define name="messageSentData" property="recipientsUserId" id="recipientsUserId"/>
					<tr>
						<td><bean:write name="messageSentData" property="dateOfDelivery"/></td>
						<td><a onclick="TB_show('', 'myProfil.do?height=515&width=800&user_id=<%=recipientsUserId%>')" href="javascript:;"><bean:write name="messageSentData" property="recipientsObjectName"/></a></td>
						<td class="messageSentData"><bean:write name="messageSentData" property="eventsend_title"/></td>
						<td><bean:write name="messageSentData" property="nbPlayed"/>x</td>
						<td width="1%"><input class="genericBt" type="button" value="<%=DicoTools.dico(dico_lang, "myMessages/new")%>" onclick="sendMsgTo('<bean:write name="messageSentData" property="recipientsObjectName"/>');" /></td>
						<td><input type="checkbox" name="checkListMsg" value="<%=message_id%>" onclick="messages_selectMsg(this);"></td>
					</tr>
		</logic:iterate>
	</logic:notEmpty>
		<logic:empty name="myMessagesListForm" property="listeMessages">
			<tr><td colspan="10"><%=DicoTools.dico(dico_lang, "main/no_message")%></td></tr>
		</logic:empty>		
	
	</tbody>
</table>

<ul class="messages-list-func">
			<li><a href="javascript:;" onclick="messages_select_all('messagesListeSent')"><%=DicoTools.dico(dico_lang, "myMessages/select_all")%></a></li>
			<li><a href="myTerrier.do?onglet=blackList" ><%=DicoTools.dico(dico_lang, "myMessages/display_blacklist")%></a></li>
		</ul>
		
		<div class="messages-select-action">
		<select name="selectChoice" style="width:120px;">
			<!-- <option value=""><%=DicoTools.dico(dico_lang, "myMessages/action")%></option> -->
			<option value="delete_msg"><%=DicoTools.dico(dico_lang, "myMessages/delete")%></option>
		</select>
		<input type="button" value="<%=DicoTools.dico(dico_lang, "myMessages/button_ok")%>" class="genericBt" onclick="submitAjaxForm('update_listSent','contentEnvoyes');"/> 	
	</div>
</form>

<div class="paginate">
	<ul>
		<form action="../action/myMessagesList.do" name="pageSelector" method="post">
			<logic:greaterThan name="myMessagesListForm" property="nombre_pages" value="1">
				<bean:define id="page_indexD" name="myMessagesListForm" property="page_indexD" />
				<bean:define id="page_indexMM" name="myMessagesListForm" property="page_indexMM" />
				<bean:define id="page_indexM" name="myMessagesListForm" property="page_indexM" />
				<bean:define id="page_index" name="myMessagesListForm" property="page_index" />
				<bean:define id="page_indexP" name="myMessagesListForm" property="page_indexP" />
				<bean:define id="page_indexPP" name="myMessagesListForm" property="page_indexPP" />
				<bean:define id="page_indexF" name="myMessagesListForm" property="page_indexF" />

				<li>
					<a href="javascript:;" onclick="messagesChangePage('contentEnvoyes', <bean:write name="myMessagesListForm" property="page_indexD" />)"> &lt;&lt; </a>
				</li>
				
				<li>
					<a href="javascript:;" onclick="messagesChangePage('contentEnvoyes', <bean:write name="myMessagesListForm" property="page_indexM" />)"> &lt; </a>
				</li>
	
				<logic:notEqual name="myMessagesListForm" property="page_indexMM" value="0">
					<li>
						<a href="javascript:;" onclick="messagesChangePage('contentEnvoyes', <bean:write name="myMessagesListForm" property="page_indexMM" />)"> <bean:write name="myMessagesListForm" property="page_AffIndexMM" /> </a>
					</li>
				</logic:notEqual>
				
				<logic:notEqual name="myMessagesListForm" property="page_indexM" value="0">
					<li>
						<a href="javascript:;" onclick="messagesChangePage('contentEnvoyes', <bean:write name="myMessagesListForm" property="page_indexM" />)"> <bean:write name="myMessagesListForm" property="page_AffIndexM" /> </a>
					</li>
				</logic:notEqual>
	
				<li class="current">
					<a href="javascript:;"><bean:write name="myMessagesListForm" property="page_AffIndex" /></a>
				</li>
	
				<logic:notEqual name="myMessagesListForm" property="page_indexP" value="0">
					<li>
						<a href="javascript:;" onclick="messagesChangePage('contentEnvoyes', <bean:write name="myMessagesListForm" property="page_indexP" />)"> <bean:write name="myMessagesListForm" property="page_AffIndexP" /></a>
					</li>
				</logic:notEqual>
				
				<logic:notEqual name="myMessagesListForm" property="page_indexPP" value="0">
					<li>
						<a href="javascript:;" onclick="messagesChangePage('contentEnvoyes', <bean:write name="myMessagesListForm" property="page_indexPP" />)"> <bean:write name="myMessagesListForm" property="page_AffIndexPP" /></a>
					</li>
				</logic:notEqual>
	
				<li>
					<logic:equal name="myMessagesListForm" property="page_indexP" value="0">
						<a href="javascript:;" onclick="messagesChangePage('contentEnvoyes', <bean:write name="myMessagesListForm" property="page_indexF" />)"> &gt; </a>
					</logic:equal>
					<logic:notEqual name="myMessagesListForm" property="page_indexP" value="0">
						<a href="javascript:;" onclick="messagesChangePage('contentEnvoyes', <bean:write name="myMessagesListForm" property="page_indexP" />)"> &gt; </a>
					</logic:notEqual>						
				</li>
				
				<li>
					<a href="javascript:;" onclick="messagesChangePage('contentEnvoyes', <bean:write name="myMessagesListForm" property="page_indexF" />)"> &gt;&gt; </a>
				</li>
				
			</logic:greaterThan>
		</form>
	</ul>
</div>

<script type="text/javascript">
	messageListColorization("messagesListeSent");
</script>	

