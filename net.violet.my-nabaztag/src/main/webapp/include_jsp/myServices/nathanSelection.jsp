<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools"%>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8");%>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="/include_jsp/utils/inc_dico.jsp"%>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<bean:define name="mySrvNathanHomeForm" property="dicoRoot" id="dicoRoot"></bean:define>

<%-- a insérer dans un main-config-bloc --%>
<div id="config-bloc">
			
	<bean:define id="idApplet" name="mySrvNathanHomeForm" property="appletId" />
	<bean:define id="isbn" name="mySrvNathanHomeForm" property="isbn" />
	<bean:define id="bookSerial" name="mySrvNathanHomeForm" property="bookSerial" />
	
	<div class = "nathan-create">		
	<%=DicoTools.dico(dico_lang , "srv_nathan/hint_create")%><a href="../action/srvNathanCreate.do?isbn=<%=isbn%>&appletId=<%=idApplet%>&dispatch=load" onclick="page.loadInDiv('#setUpSrv-container',this.href); return false;"><%=DicoTools.dico(dico_lang , "srv_nathan/hint_link_create")%></a>
	<hr class="clearer"/></div>
	
	<div id="myMp3Player" class="myPlayerMp3Message"></div>
	
	<div id="list-bloc">
	
		<form action="../action/srvNathanHome.do" id="selectionForm">
			<input type="hidden" name="idApplet" value="<%=idApplet%>" />
			<input type="hidden" name="isbn" value="<%=isbn%>" />
			<input type="hidden" name="dispatch" value="remove" />
			
			<div class="nathan" id="cadre">
			<logic:iterate name="mySrvNathanHomeForm" property="resultList" id="version">
				<bean:define id="objectLogin" name="version" property="author" type="String"/>
				<bean:define id="nb" name="version" property="nb" />
				<bean:define id="description" name="version" property="description" />
				<bean:define id="versionId" name="version" property="id" />
				<bean:define id="img" name="version" property="img" />
				
				<div class="version-bloc">
				<div class="version-selector">
					<input type="checkbox" name="selectedVersions" value="<%=versionId %>"  id="v<%=versionId %>" />
					<logic:equal value="-1" name="version" property="img">
						<a onclick="javascript:selectInput('v<%=versionId %>');" ><img src="../photo/default_S.jpg"/></a>
					</logic:equal>
					<logic:notEqual value="-1" name="version" property="img">
						<logic:equal name="version" property="official" value="true">
							<a onclick="javascript:selectInput('v<%=versionId %>');" ><img src="<%=img%>"/></a>
						</logic:equal>
						<logic:equal name="version" property="official" value="false">
							<a onclick="javascript:selectInput('v<%=versionId %>');" ><img src="../photo/<%=img%>_S.jpg"/></a>
						</logic:equal>
					</logic:notEqual>	
					</div>
					
					<div class="version-info">
				
					<div class="version-titre">
					<%=DicoTools.dico(dico_lang,"srv_nathan/list_tells")%> 
					<logic:equal name="version" property="official" value="true">
						<%=DicoTools.dico(dico_lang,"srv_nathan/nathan")%>
					</logic:equal>
					<logic:equal name="version" property="official" value="false">
						<%=objectLogin%> 
					</logic:equal>
					</div>
					<div class="version-details">
					<logic:iterate name="version" property="tags" id="tag">
						<bean:define id="name" name="tag" property="label" type="java.lang.String"/>
						<%=DicoTools.dico(dico_lang , name)%> |
					</logic:iterate>
					</div>
					
					<%-- infos propres aux versions non-officielles --%>
					<logic:equal name="version" property="official" value="false">
						<div class="version-note">
						<%=nb%> <%=DicoTools.dico(dico_lang , "srv_nathan/list_subscribers")%> </div>
					</logic:equal>
					
					<div class="version-des"><%=description%></div>
					</div>
					<div class="version-tools">
					<logic:notEqual name="version" property="preview" value="">
						<bean:define id="preview" name="version" property="preview"/>
						<div class="item-tools"><a href="#js;" onclick="loadPersoPlayer('<%=preview%>', '300', '1', 'myMp3Player'); return false;"><%=DicoTools.dico(dico_lang , "srv_nathan/list_preview")%></a></div>
					</logic:notEqual>
					
					
						<%-- encouragement pour les versions non officielles --%>
				<logic:equal name="version" property="official" value="false">							
				<logic:notEqual name="mySrvNathanHomeForm" property="objectLogin" value="<%=objectLogin%>">	
					<div class="item-tools">
					<% String resp = DicoTools.dico(dico_lang, "srv_nathan/nabthem_ok" );%>
					<a href="#js;" onclick="$.get('myMessagesSendClin.do?destName=<%=objectLogin%>&send=1&categId=94'); msgHandling.simpleMsgShow('<%=resp%>'); return false;"><%=DicoTools.dico(dico_lang, "srv_nathan/nabthem" )%></a>
					</div>
				</logic:notEqual>
				
				</logic:equal>
					</div>
				</div>
				<hr class="clearer1"/>
			</logic:iterate>
			<div class="button-bloc">
			<input type="hidden" value="<%=bookSerial%>" name="bookSerial"/>
			<input type="submit" value="<%=DicoTools.dico(dico_lang , "srv_nathan/remove_from_selection")%>" onclick="page.postAjax('selectionForm', 'main-config-bloc'); return false;"/>
			</div>
		</div>
		</form>
	</div>

</div>	