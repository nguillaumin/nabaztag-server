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

<bean:define property="isReg" name="mySrvTrafficForm" id="isReg"/>
<bean:define property="serviceName" name="mySrvTrafficForm" id="serviceName" type="String"/>

<div class="main-cadre-contener">
	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<!-- ******************************************** CONTENT ***************************************************** --> 

		<html:form action="/action/srvTrafficConfig" styleId="srvPeriphConfig" styleClass="srvConfigForm">
			<bean:define name="mySrvTrafficForm" property="horraire1" id="heures1" />
			<bean:define name="mySrvTrafficForm" property="horraire2" id="heures2" />
			<bean:define id="flashActivated" value="<%=( !heures1.equals("") || !heures2.equals("") ) ? "true" : "false" %>" />
			
				<table class="table-serv-conf">
					
					<tr>
						<th>
							<%=DicoTools.dico(dico_lang , "srv_traffic/start_point")%>
						</th>
						<td>
							<div class="contener">
								<html:select name="mySrvTrafficForm" property="depart" styleId="depart" style="width:150px;">
									<logic:iterate name = "mySrvTrafficForm" property = "departList" id="srvTrafic">
										<html:option value="<%= srvTrafic.toString()%>"><%=srvTrafic.toString()%></html:option>
									</logic:iterate>
								</html:select>
							</div>
							<div class="contener">
								<span class="label"><%=DicoTools.dico(dico_lang , "srv_traffic/to_point")%></span>
								<html:select name="mySrvTrafficForm" property="arrivee" styleId="arrivee" style="width:150px;">
									<logic:iterate name = "mySrvTrafficForm" property = "departList" id="srvTrafic">
										<html:option value="<%= srvTrafic.toString()%>"><%=srvTrafic.toString()%></html:option>
									</logic:iterate>
								</html:select>
							</div>														
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
										<span class="label" ><%=DicoTools.dico(dico_lang , "service/first_flash")%></span><input id="time1" name="horraire1" type="hidden" class="hourPicker1" value="<%=heures1%>" />	
									</div>
									
									<div class="contener">
										<span class="label" style="padding-left:12px;" ><%=DicoTools.dico(dico_lang , "service/second_flash")%></span><input id="time2" name="horraire2" type="hidden" class="hourPicker2" value="<%=heures2%>" />
									</div>
									
									<hr class="clearer" />
									
									<div style="clear:both; margin:6px 0;">
										<label><html:checkbox styleId="weekEnd" name="mySrvTrafficForm" property="weekEnd" value="1" /> <%=DicoTools.dico(dico_lang , "srv_traffic/deactivate_week_end")%> </label>
									</div>		
															
								</div>
								
							</div>
													
						</td>
					</tr>
					
					<tr class="lights">
						<th>
							<%=DicoTools.dico(dico_lang , "srv_traffic/light_language")%>
						</th>
						<td>
							<html:checkbox name="mySrvTrafficForm" styleId="lumiere" property="lumiere" value="1" /> <a href="#keskiditTop"><%=DicoTools.dico(dico_lang , "srv_all/light_help")%></a>					
						</td>
					</tr>
												
					<tr class="reco">
						<th>
							<%=DicoTools.dico(dico_lang , "services/vocal")%>
						</th>
						<td>
							<span style="font-size:16px; font-weight:bold;"><%=DicoTools.dico(dico_lang , "srv_traffic/vocal")%></span> <%=DicoTools.dico(dico_lang , "srv_all/reco_link")%>
						</td>
					</tr>
					
					
				</table>
			

		
		<hr class="spacer" />
		
		<html:hidden name="mySrvTrafficForm" property="add" value="1"/>	
		
		<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
			
			<div align="center">
				<%/* Supression */%>		
				<logic:notEqual name="isReg" value="0">
					<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_traffic/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_traffic/deactivate")%></html:button>
				</logic:notEqual>
				
				<%/* Creation */%>
				<logic:equal name="isReg" value="0">
					<html:submit property="activate" value="<%=DicoTools.dico(dico_lang , "srv_traffic/activate")%>" styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_traffic/activate")%></html:submit>	
				</logic:equal>
				
				<%/* Mise a jour */%>			
				<logic:notEqual name="isReg" value="0">
					<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_traffic/update")%>"  styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_traffic/update")%></html:submit>
				</logic:notEqual>
			</div>
			
	</html:form>

	</div>
</div>

<script type="text/javascript">
	$("input.hourPicker1").hourSelect_InitN(false );
	$("input.hourPicker2").hourSelect_InitN(false );
	
	$("#contentSrvConfig form").submit(function(){
		return nablifeValidatePeriphConfig(<logic:equal name="isReg" value="1">true</logic:equal>); 
	});
	
	srvTableInit(); // active le roll over sur les TR	
	
	$("div.b_checkactivate").checkactivate();
	
</script>