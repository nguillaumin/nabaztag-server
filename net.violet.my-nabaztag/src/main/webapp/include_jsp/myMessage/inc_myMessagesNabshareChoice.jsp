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

<bean:define name="myMessagesNabshareChoiceForm" property="langUser" id="lang" type="String"/>

<div class="itemPlayer" id="myPlayerMp3"></div>

<div  class="listItemContener">

	<logic:notEmpty name="myMessagesNabshareChoiceForm" property="listeCateg">
		<table class="choixListItem">
					<logic:iterate name="myMessagesNabshareChoiceForm" property="listeCateg" id="categData">
						<bean:define id="music_url" name="categData" property="music_url"/>	
						<bean:define id="music_name_short" name="categData" property="music_name_short"/>	
						<bean:define id="music_id" name="categData" property="music_id"/>			
						<tr>
						<td width="100%">
	        			<a class="name" id="ID_<%=music_id%>" href="javascript:void(0);" title="id = <%=music_id%>" onclick="selectItemN(this);">
	        				<%=music_name_short%></a>
						</td>
							<td>
								<a class="LplayIcone" href="javascript:;" title="&eacute;couter" onclick="loadPersoPlayer('<%=music_url%>', '330')";><span><%=DicoTools.dico(dico_lang, "main/button_listen")%></span></a>
							</td>
						</tr>
					</logic:iterate>     
		</table>  
	</logic:notEmpty>
	
	<logic:empty name="myMessagesNabshareChoiceForm" property="listeCateg">
		<%=DicoTools.dico(dico_lang, "category/empty")%>!!!!		
	</logic:empty>
	
</div>