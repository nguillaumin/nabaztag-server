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

<logic:notEmpty name="myToolsApiForm" property="numSerie">

	<bean:define name="myToolsApiForm" property="extConnect" id="extConnect"/>
	<bean:define name="myToolsApiForm" property="numSerie" id="numSerie"/>
	
	<%-- TRAITEMENT ERREUR/SUCCES --%>
	
	<logic:equal  name="myToolsApiForm" property="mode" value="1">
		<ul class="general-msg">
			<li>
				<%=DicoTools.dico(dico_lang, "myTerrier/preferences_modif_ok")%>
			</li>
		</ul>
	</logic:equal>
	
	
	<%-- FORMULAIRE --%>
	<form action="../action/myToolsApi.do" method="post" id="myToolsAPI" id="form_api" >
			
		<div class="flat-block"  > 
		   
			<div class="flat-block-top" >
				<h3 class="no-icone">
					<%=DicoTools.dico(dico_lang, "myTools/api_title")%>
				</h3>
			</div>
		
			<div class="flat-block-content" >
				<div class="flat-block-content-inner" >
					<%-- *********************************************************************************************** --%>
					
						<input type="hidden" name="mode"  value="1"/>
						
						<div>
							<p>
							<%=DicoTools.dico(dico_lang, "myTools/api_intro")%>
							</p>
						
							
							<logic:equal name="myToolsApiForm" property="extConnect" value="0">
									<p><%=DicoTools.dico(dico_lang, "myTools/api_not_activated")%></p>
							</logic:equal>
							
							<logic:notEqual name="myToolsApiForm" property="extConnect" value="0">
								<p>
									<%=DicoTools.dico(dico_lang, "myTools/api_activated")%>
								</p>
								<p>
									<%=DicoTools.dico(dico_lang, "myTools/your_token")%> <span class="token"><%=extConnect %></span><br />
									<%=DicoTools.dico(dico_lang, "myTools/your_serial")%> <span class="token"><%=numSerie %></span>
								</p>
							</logic:notEqual>
						</div>
						
						<div class="form-line" >	
							<logic:equal name="myToolsApiForm" property="extConnect" value="0">
								<input type="checkbox" name="checkAPI" value="1" /> 
							</logic:equal>
							
							<logic:notEqual name="myToolsApiForm" property="extConnect" value="0">
								<input type="checkbox" name="checkAPI" value="1" checked /> 
							</logic:notEqual>
							
								
							<%=DicoTools.dico(dico_lang, "myTools/text_checkbox")%>&nbsp;
						</div>
						
						<div class="buttons">
							<input class="genericBt" type="submit" name="submit" value="<%=DicoTools.dico(dico_lang, "myTerrier/preferences_modify")%>"/>
						</div>
				
						<%-- <input type="button" value="<%=DicoTools.dico(dico_lang, "myTools/validate_button")%>" class="genericBt" onclick="submitAjaxForm('myToolsAPI','contentMesAPI');"/> --%>
	
						<div class="form-line" >
						
							<%=DicoTools.dico(dico_lang, "myTools/outro")%>
						</div>
					
				
	
									
					<%-- *********************************************************************************************** --%>				
				</div>
			</div>
		</div>
		
	</form>
</logic:notEmpty>