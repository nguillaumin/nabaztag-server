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



<bean:define name="myMessagesClinChoiceForm" property="langUser" id="lang" type="String"/>

<div class="itemPlayer" id="myPlayerMp3"></div>


<div  class="listItemContener">
	<table class="choixListItem">
				<logic:iterate name="myMessagesClinChoiceForm" property="listeClin" id="musicData">
					<bean:define id="monClinId" name="musicData" property="musicId"/>
					<bean:define id="monClinName" name="musicData" property="musicName"/>			
					<bean:define id="monClinUrl" name="musicData" property="musicUrl"/>						
					<tr>
						<td width="100%">
		        			<a class="name" id="ID_<%=monClinId%>" href="javascript:void(0);" title="id = <%=monClinId%>" onclick="selectItemN(this);">
		        				<%=monClinName%>
		        			</a>
						</td>
						<td>
							<a class="LplayIcone" href="javascript:;" title="<%=DicoTools.dico(dico_lang, "main/button_listen")%>" onclick="loadPersoPlayer('<%=monClinUrl%>', '330')";><span><%=DicoTools.dico(dico_lang, "main/button_listen")%></span></a>
						</td>
					</tr>
				</logic:iterate>       
	</table>  
</div>