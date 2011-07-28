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

<bean:define property="isReg" name="mySrvReveilFreeForm" id="isReg"/>


<div class="main-cadre-contener">
	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<!-- ******************************************** CONTENT ***************************************************** --> 

		<html:form action="/action/srvReveilFreeConfig" styleId="srvReveilConfig" styleClass="srvConfigForm">
			
			<bean:define name="mySrvReveilFreeForm" property="horraire" id="heures" />
			<bean:define name="mySrvReveilFreeForm" property="sonNom" id="sonNom" />
			<bean:define name="mySrvReveilFreeForm" property="music_id" id="musicId" />				

			<label><%=DicoTools.dico(dico_lang , "srv_reveil/choose_sound")%></label>
			<input name="music_id" id="son" type='hidden' class='songPicker'  title="<%=sonNom%>" value="<%=musicId%>"/>
		
			<hr class="spacer">
					
			<label><%=DicoTools.dico(dico_lang , "srv_reveil/choose_time")%></label>
			<input id="time" name="horraire" type="hidden" class="hourPicker" value="<%=heures%>" />
			
	
			<hr class="spacer">
			<div align="center">
			
				<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
			
				<div align="center">
					<%/* Supression */%>		
					<logic:notEqual name="isReg" value="0">
						<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_reveil/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_reveil/deactivate")%></html:button>
					</logic:notEqual>
					
					<%/* Creation */%>
					<logic:equal name="isReg" value="0">
						<html:submit property="activate" value="<%=DicoTools.dico(dico_lang , "srv_reveil/activate")%>" styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_reveil/activate")%></html:submit>	
					</logic:equal>
					
					<%/* Mise a jour */%>			
					<logic:notEqual name="isReg" value="0">
						<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_reveil/update")%>"  styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_reveil/update")%></html:submit>
					</logic:notEqual>
				</div>
					
			</div>		
		</html:form>
		
	</div>
</div>


<script type="text/javascript">
	$("input.hourPicker").hourSelect_InitN( false );

	$("input.songPicker").chooseSong();
	
	$("#contentSrvConfig form").submit(function(){
		//return false;
		return nablifeValidateReveilConfig(<logic:equal name="isReg" value="1">true</logic:equal>); 
	});
</script>