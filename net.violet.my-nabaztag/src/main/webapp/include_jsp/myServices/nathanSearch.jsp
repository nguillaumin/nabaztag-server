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
	<bean:define id="bookSerial" name="mySrvNathanHomeForm" property="bookSerial"/>
			
	<form action="../action/srvNathanHome.do" id="searchForm">
		<input type="hidden" name="isbn" value="<%=isbn%>"/>
		<input type="hidden" name="appletId" value="<%=idApplet%>"/>
		<input type="hidden" name="dispatch" value="search"/>	
		
		<div class="nathan" id="cadre">
		<br/><%=DicoTools.dico(dico_lang , "srv_nathan/search_param")%>  <br/>
		<div class="search-zone">
		<div class="nathan-col">
		<h4><%=DicoTools.dico(dico_lang , "srv_nathan/search_sex")%>  </h4>
		<logic:iterate id="tag" name="mySrvNathanHomeForm" property="sexTags">
			<bean:define id="tagId" name="tag" property="id" />
			<bean:define id="tagLabel" name="tag" property="label" type="java.lang.String"/>
			<p><html:multibox name="mySrvNathanHomeForm" property="checkedTags" >
				<bean:write name="tagId"/>
			</html:multibox>
			<%=DicoTools.dico(dico_lang , tagLabel)%></p>
		</logic:iterate>
		</div>
		<div class="nathan-col">
		<h4><%=DicoTools.dico(dico_lang , "srv_nathan/search_interpretation")%> : </h4>
		<logic:iterate id="tag" name="mySrvNathanHomeForm" property="interpretationTags">
			<bean:define id="tagId" name="tag" property="id" />
			<bean:define id="tagLabel" name="tag" property="label" type="java.lang.String" />
			<p><html:multibox name="mySrvNathanHomeForm" property="checkedTags" >
				<bean:write name="tagId"/>
			</html:multibox>
			<%=DicoTools.dico(dico_lang , tagLabel)%></p>
		</logic:iterate>
		</div>
		
		<div class="nathan-col">
		<h4><%=DicoTools.dico(dico_lang , "srv_nathan/search_effects")%> : </h4>
		<logic:iterate id="tag" name="mySrvNathanHomeForm" property="effectsTags">
			<bean:define id="tagId" name="tag" property="id" />
			<bean:define id="tagLabel" name="tag" property="label" type="java.lang.String"/>
			<p><html:multibox name="mySrvNathanHomeForm" property="checkedTags" >
				<bean:write name="tagId"/>
			</html:multibox>
			<%=DicoTools.dico(dico_lang , tagLabel)%></p>
		</logic:iterate>
		</div>
		<div class="nathan-col">
		<h4><%=DicoTools.dico(dico_lang , "srv_nathan/search_told")%> : </h4>
		<logic:iterate id="tag" name="mySrvNathanHomeForm" property="accentTags">
			<bean:define id="tagId" name="tag" property="id" />
			<bean:define id="tagLabel" name="tag" property="label" type="java.lang.String"/>
			<p><html:multibox name="mySrvNathanHomeForm" property="checkedTags" >
				<bean:write name="tagId"/>
			</html:multibox>
			<%=DicoTools.dico(dico_lang , tagLabel)%></p>
		</logic:iterate>
		</div>
		<hr class="clearer"/>
		</div>
		<div class="button-bloc">
		<input type="hidden" value="<%=bookSerial%>" name="bookSerial" />
		<input type="button" value="<%=DicoTools.dico(dico_lang , "srv_nathan/search_button")%>" onclick="page.postAjax('searchForm', 'main-config-bloc')"/>
	</div>
	</div>
	</form>			
			
	<hr class="clearer"/>
			
	<div id="myMp3Player" class="myPlayerMp3Message"></div>
			
	<div id="list-bloc">
	
		<form action="../action/srvNathanHome.do" id="selectionForm">
			<input type="hidden" name="idApplet" value="<%=idApplet%>" />
			<input type="hidden" name="isbn" value="<%=isbn%>" />
			<input type="hidden" name="dispatch" value="subscribe" />
		<div class="nathan" id="cadre">
			<logic:iterate name="mySrvNathanHomeForm" property="resultList" id="version">
				<bean:define id="objectLogin" name="version" property="author" type="String" />
				<bean:define id="nb" name="version" property="nb" />
				<bean:define id="description" name="version" property="description" />
				<bean:define id="versionId" name="version" property="id" />
				<bean:define id="img" name="version" property="img" />
				<div class="version-bloc">
				<div class="version-selector">
					<input type="checkbox" name="selectedVersions" value="<%=versionId %>" />
					<logic:equal value="-1" name="version" property="img">
						<img src="../photo/default_S.jpg"/>
					</logic:equal>
					<logic:notEqual value="-1" name="version" property="img">
						<logic:equal name="version" property="official" value="true">
							<img src="<%=img%>"/>
						</logic:equal>
						<logic:equal name="version" property="official" value="false">
							<img src="../photo/<%=img%>_S.jpg"/>
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
						<%=DicoTools.dico(dico_lang , name)%>|
					</logic:iterate>					
				</div>
					
					<div class="version-note"><%=nb%> <%=DicoTools.dico(dico_lang , "srv_nathan/list_subscribers")%> </div>
					<div class="version-des"><%=description%></div>
					</div>
					<div class="version-tools">
					<div class="item-tools">
					   <logic:notEqual name="version" property="preview" value="">
						 <bean:define id="preview" name="version" property="preview"/>
						 <a href="#js;" onclick="loadPersoPlayer('<%=preview%>', '300', '1', 'myMp3Player'); return false;"><%=DicoTools.dico(dico_lang , "srv_nathan/list_preview")%></a>
					   </logic:notEqual>
					<br />
						<%-- encouragement pour les versions non officielles --%>
				<logic:equal
				name="version" property="official" value="false">							
				
					<%String text = DicoTools.dico(dico_lang, "srv_nathan/nabthem_text" ); 
					  String resp = DicoTools.dico(dico_lang, "srv_nathan/nabthem_ok" );
					%> 
					<logic:equal	name="version" property="official" value="false">						
					<logic:notEqual name="mySrvNathanHomeForm" property="objectLogin" value="<%=objectLogin%>">						
					<a href="#js;" onclick="$.get('myMessagesSendClin.do?destName=<%=objectLogin%>&send=1&categId=94'); msgHandling.simpleMsgShow('<%=resp%>'); return false;">
					<%=DicoTools.dico(dico_lang, "srv_nathan/nabthem" )%></a>
					</logic:notEqual>
					</logic:equal>
				</logic:equal>
				</div>
				</div>
				<hr class="clearer1"/>
				
			</logic:iterate>
			<logic:notEmpty name="mySrvNathanHomeForm" property="resultList">
			<div class="button-bloc">
			<input  type="hidden" value="<%=bookSerial%>" name="bookSerial"/>
			<input type="submit" value="<%=DicoTools.dico(dico_lang , "srv_nathan/add_to_selection")%>"  onclick="activateTab('selectionTab'); page.postAjax('selectionForm', 'main-config-bloc'); return false;"/>
			</div>
			</logic:notEmpty>
			</div>
		</form>
			
	</div>
</div>