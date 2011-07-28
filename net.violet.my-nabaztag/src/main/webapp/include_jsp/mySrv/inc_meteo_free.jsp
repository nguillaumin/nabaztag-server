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

<bean:define property="isReg" name="mySrvMeteoFreeForm" id="isReg"/>
<bean:define property="serviceName" name="mySrvMeteoFreeForm" id="serviceName" type="String"/>

 <div class="main-cadre-contener">
	 <div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2>
	 </div>
	 <div class="main-cadre-content">
			<hr class="spacer"/>
			<!-- ******************************************** CONTENT ***************************************************** -->        
			
			<div class="srv-main-config" >
				<hr class="spacer" />
				
				<html:form action="/action/srvMeteoFreeConfig" styleId="srvMeteoFreeConfig" styleClass="srvConfigForm">
				<html:hidden name="mySrvMeteoFreeForm" property="add" value="1"/>
					
					<bean:define name="mySrvMeteoFreeForm" property="horraire1" id="heures1" />
					<bean:define name="mySrvMeteoFreeForm" property="horraire2" id="heures2" />
					<bean:define id="flashActivated" value="<%=( !heures1.equals("") || !heures2.equals("") ) ? "true" : "false" %>" />
			
					<table class="table-serv-conf">
						
						<tr>
							<th>
								<%=DicoTools.dico(dico_lang , "srv_weather/choose_city")%>
							</th>
							<td>
								<html:select name="mySrvMeteoFreeForm" property="idVille" styleId="idVille" style="width:200px;">
									<option value=""><%=DicoTools.dico(dico_lang , "generic/choose_country")%></option>
									<!--  SourceData -->
									<html:optionsCollection name="mySrvMeteoFreeForm" property="villeList" label="label" value="source_path"/>
								</html:select>
							</td>
						</tr>
						
						<tr>
							<th>
								<%=DicoTools.dico(dico_lang , "srv_weather/typedeg")%>
							</th>
							<td>
								<html:radio name="mySrvMeteoFreeForm" property="typedeg" value="1"><%=DicoTools.dico(dico_lang , "srv_weather/degres")%></html:radio>
								<html:radio name="mySrvMeteoFreeForm" property="typedeg" value="2"><%=DicoTools.dico(dico_lang , "srv_weather/farenheit")%></html:radio>
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
											<span class="label" style="padding-left:12px;" ><%=DicoTools.dico(dico_lang , "service/second_flash")%></span><input id="time2" name="horraire2" type="hidden" class="hourPicker" value="<%=heures2%>" />
										</div>

										<hr class="clearer" />
										
										<div style="clear:both; margin:6px 0;">
																				
											<%=DicoTools.dico(dico_lang , "srv_weather/language")%>
	
											<html:select name="mySrvMeteoFreeForm" property="langSrv" style="width:150px;">
												<logic:iterate name="mySrvMeteoFreeForm" property="langList" id="langData">
													<bean:define name="langData" property="lang_id" id="lang_id"/>
													<bean:define name="langData" property="lang_title" id="lang_title"/>
													<bean:define name="langData" property="lang_type" id="lang_type"/>
													<html:option value="<%=lang_id.toString()%>"><%=lang_title.toString()%></html:option>
												</logic:iterate>
											</html:select>

										</div>
										
									</div>
									
								</div>
								
							</td>
						</tr>
						
						<tr class="lights">
							<th>
								<%=DicoTools.dico(dico_lang , "srv_weather/light_language")%>
							</th>
							<td>
								<html:checkbox name="mySrvMeteoFreeForm" property="lumiere" value="1" styleId="fl"/> <a href="#keskiditTop"><%=DicoTools.dico(dico_lang , "srv_all/light_help")%></a> <%-- &nbsp;<a class="thickbox link-help-txt" title="<%=DicoTools.dico(dico_lang , "srv_all/light_title")%>" href='javascript:;' onclick="TB_show('<%=DicoTools.dico(dico_lang , "srv_all/light_title")%>', '#TB_inline?height=338&width=480&inlineId=keskidit')" ><%=DicoTools.dico(dico_lang , "srv_all/light_help")%></a>--%>						
							</td>
						</tr>
						
						

						<tr class="reco">
							<th>
								<%=DicoTools.dico(dico_lang , "services/vocal")%>
							</th>
							<td>
								<span style="font-size:16px; font-weight:bold;"><%=DicoTools.dico(dico_lang , "srv_meteo_free/vocal")%></span> <%=DicoTools.dico(dico_lang , "srv_all/reco_link")%>							
							</td>
						</tr>
						
						
					</table>

					
					<hr class="spacer" />
					
					<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
			
			
					<div align="center">
						
						<%/* Creation */%>
						<logic:equal name="isReg" value="0">
							<html:submit property="activate" value="<%=DicoTools.dico(dico_lang , "srv_weather/add")%>" styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_weather/add")%></html:submit>	
						</logic:equal>
		
						<logic:notEqual name="isReg" value="0">
							<%/* Supression */%>
							<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_weather/deactivate")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_weather/deactivate")%></html:button>
							<%/* Mise a jour */%>
							<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_weather/modify")%>"  styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_weather/modify")%></html:submit>
						</logic:notEqual>
					</div>
					
				</html:form>
			</div>

			<hr class="spacer" />


				<!-- ******************************************** /CONTENT ***************************************************** -->        

	</div>
</div>


<script type="text/javascript">


		$("input.hourPicker").hourSelect_InitN( false );
			
		$("#contentSrvConfig form").submit(function(){
			return nablifeValidateMeteoFreeConfig(<logic:equal name="isReg" value="1">true</logic:equal>); 
		});
		
		$("#idVille").select_ListeVilleFormat();
		
		srvTableInit(); // active le roll over sur les TR
		$("div.b_checkactivate").checkactivate();


</script>