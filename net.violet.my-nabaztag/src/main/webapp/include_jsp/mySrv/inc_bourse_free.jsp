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

<bean:define property="isReg" name="mySrvBourseFreeForm" id="isReg"/>
<bean:define property="serviceName" name="mySrvBourseFreeForm" id="serviceName" type="String"/>

<div class="main-cadre-contener">
	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<!-- ******************************************** CONTENT ***************************************************** --> 
		<div class="srv-main-config" >
			<hr class="spacer" />
			<!-- 
			<div id="description-srv">
				
				<p class="short-desc"><%=DicoTools.dico(dico_lang , "srv_bourse_free/description")%></p>
				<p class="long-desc"><%=DicoTools.dico(dico_lang , "srv_bourse_free/long_description")%></p>
			</div>
			
			<hr class="spacer" />
			-->					
			<html:form action="/action/srvBourseFreeConfig" styleId="srvBourseFreeConfig" styleClass="srvConfigForm">
				
				<bean:define name="mySrvBourseFreeForm" property="horraire1" id="heures1" />
				<bean:define name="mySrvBourseFreeForm" property="horraire2" id="heures2" />
				<bean:define id="flashActivated" value="<%=( !heures1.equals("") || !heures2.equals("") ) ? "true" : "false" %>" />
							
				<table class="table-serv-conf">
					
					<tr>
						<th>
							<%=DicoTools.dico(dico_lang , "srv_bourse/choose_index")%>
						</th>
						<td>
							<html:select name="mySrvBourseFreeForm" property="indic" styleId="list">
								<option value=""></option>
								<!--  FrequenceData -->	
								<html:optionsCollection name="mySrvBourseFreeForm" property="indicList" label="label" value="id"/>
							</html:select>
						</td>
					</tr>
					
					<tr class="flash">
						<th>
							<%=DicoTools.dico(dico_lang , "service/flash_audio")%>
						</th>
						<td>
							<div class="b_checkactivate checkactivate">
								
								<div class="check">
									<label class="lab">
										<input type="checkbox" <logic:equal value="true" name="flashActivated">checked="checked"</logic:equal> />
										<%=DicoTools.dico(dico_lang , "service/activate")%>
									</label>
								</div>
								
								<div class="content">		
													
									<div class="contener">
										<span class="label" ><%=DicoTools.dico(dico_lang , "service/first_flash")%></span><input id="time1" name="horraire1" type="hidden" class="hourPicker" value="<%=heures1%>" />
									</div>
									
									<div class="contener">
										<span class="label" ><%=DicoTools.dico(dico_lang , "service/second_flash")%></span><input id="time2" name="horraire2" type="hidden" class="hourPicker" value="<%=heures2%>" />
									</div>
									
									<hr class="clearer" />
									
									<div style="clear:both; margin:6px 0;">
										<label><html:checkbox name="mySrvBourseFreeForm" property="weekend" value="1" styleId="weekend" /><%=DicoTools.dico(dico_lang , "srv_bourse/deactivate_week_end")%></label>
									</div>
									
								</div>
								
							</div>
																				
						</td>
					</tr>

					<tr class="lights">
						<th>
							<%=DicoTools.dico(dico_lang , "srv_bourse/light_language")%>
						</th>
						<td>
							<html:checkbox name="mySrvBourseFreeForm" property="lumiere" value="1" styleId="lumiere" />	<a href="#keskiditTop"><%=DicoTools.dico(dico_lang , "srv_all/light_help")%></a>												
						</td>
					</tr>
										
										
					<tr class="reco">
						<th>
							<%=DicoTools.dico(dico_lang , "services/vocal")%>
						</th>
						<td>
							<span style="font-size:16px; font-weight:bold;"><%=DicoTools.dico(dico_lang , "srv_bourse/vocal")%></span> <%=DicoTools.dico(dico_lang , "srv_all/reco_link")%>											
						</td>
					</tr>
															
				</table>
			
				<hr class="spacer" />
						
				<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
			
			
				<div align="center">
					<%/* Supression */%>		
					<logic:notEqual name="isReg" value="0">
						<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_bourse/button_delete")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_bourse/button_delete")%></html:button>
					</logic:notEqual>
					
					<%/* Creation */%>
					<logic:equal name="isReg" value="0">
						<html:submit property="activate" value="<%=DicoTools.dico(dico_lang , "srv_bourse/button_activate")%>" styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_bourse/button_activate")%></html:submit>	
					</logic:equal>
					
					<%/* Mise a jour */%>			
					<logic:notEqual name="isReg" value="0">
						<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_bourse/button_update")%>"  styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_bourse/button_update")%></html:submit>
					</logic:notEqual>
				</div>
				
			</html:form>
		</div>
		
		<hr class="spacer" />			
		<!-- ******************************************** /CONTENT ***************************************************** -->        
	</div>
</div>

<script type="text/javascript">
	
	$("input.hourPicker").hourSelect_InitN(false );
		
	$("#list").select_NotclickableValues();
		
	$("#contentSrvConfig form").submit(function(){
		return nablifeValidateBourseFreeConfig(<logic:equal name="isReg" value="1">true</logic:equal>); 
	});
	
	srvTableInit(); // active le roll over sur les TR
	$("div.b_checkactivate").checkactivate();

</script>

