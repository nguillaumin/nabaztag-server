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


<bean:define name="myMessagesBiblioChoiceForm" property="langUser" id="lang" type="String"/>

<div class="itemPlayer" id="myPlayerMp3"></div>

<div class="listItemContener">
	<table class="choixListItem">
				<logic:iterate name="myMessagesBiblioChoiceForm" property="listeBiblio" id="musicData">
					<bean:define id="monBiblioId" name="musicData" property="musicId"/>
					<bean:define id="monBiblioName" name="musicData" property="musicName"/>		
					<bean:define id="monBiblioUrl" name="musicData" property="musicUrl"/>						
	
					<tr>
						<td width="100%">
							<a class="name" id="ID_<%=monBiblioId%>" href="javascript:void(0);" title="id = <%=monBiblioId%>" onclick="selectItemN(this);">
								<%=monBiblioName%>
							</a>
						</td>
						<td>
							<a class="LplayIcone" href="javascript:;" title="&eacute;couter" onclick="loadPersoPlayer('<%=monBiblioUrl%>', '330');"><span><%=DicoTools.dico(dico_lang, "main/button_listen")%></span></a>
						</td>
					</tr>
				</logic:iterate>       
	</table>  
</div>






















