<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Application" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<bean:define property="isReg" name="mySrvBourseFullForm" id="isReg" />
<bean:define id="srvBourseDataList" name="mySrvBourseFullForm" property="supervisedList" />

<bean:define property="valueTo" name="mySrvBourseFullForm" id="valueTo" />
<bean:define property="maxValue" name="mySrvBourseFullForm" id="maxValue" />
<bean:define property="nbrValue" name="mySrvBourseFullForm" id="nbrValue" />
<bean:define property="serviceName" name="mySrvBourseFullForm" id="serviceName" type="String"/>


<div class="main-cadre-contener srv-config">
	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<!-- ******************************************** CONTENT ***************************************************** --> 

		<!--  *************************** La liste des valeurs surveill�s ************************************* -->
			<div>
				<div class="srv-list-entries" >
					<h2><%=DicoTools.dico(dico_lang , "srv_bourse/list_monitor")%></h2>
					
					<logic:empty name="srvBourseDataList">
						<%=DicoTools.dico(dico_lang , "srv_bourse/no_values")%>
					</logic:empty>
				
					<logic:notEmpty name="srvBourseDataList">
						<ul>
						<logic:iterate name="srvBourseDataList" id="srvBourseData">
							<bean:define id="id" name="srvBourseData" property="srv_id"/>
							<bean:define id="name" name="srvBourseData" property="srv_name"/>
							<bean:define id="lumiere" name="srvBourseData" property="srv_lumiere"/>
							<bean:define id="nbr" name="srvBourseData" property="nbr"/>
								
							<li id="srvItem_<%=id%>" >
								<ul class="ico-list" >
									<li class="supprimer">
										<a class="link-delete suppr" href='<html:rewrite forward="srvBourseFull"/>?dispatch=delete&applicationId=<%=Application.NativeApplication.BOURSE_FULL.getApplication().getId()%>&valueTo=<%=id%>'><span><%=DicoTools.dico(dico_lang , "srv_bourse/suppress")%></span></a>
									</li>					
			
									<li class="lumi">
										<% /* flash lumineux activ� */ %>
										<logic:equal name="lumiere" value="1">
											<a href="javascript:;" class="lumi-on" ><span><%=lumiere%></span></a>
										</logic:equal>
										
										<% /* flash lumineux desactiv� */ %>
										<logic:equal name="lumiere" value="0">
											<a href="javascript:;" class="lumi-off" ><span><%=lumiere%></span></a>
										</logic:equal>
									</li>
								</ul>
								<ul class="txt-list" style="height:1%;"> <%/* <-- vieux hack pour ie, bizarrement on peut pas le mettre dans la css... */%>
									<li class="nom" ><strong><%=name%></strong>&nbsp;</li>
									
									<logic:greaterEqual name="nbr" value="1">					
										<li class="horaire">
											<logic:greaterEqual name="nbr" value="1">
												<bean:define id="weekend" name="srvBourseData" property="weekend"/>
												<bean:define id="time1" name="srvBourseData" property="time1"/>
												<%=DicoTools.dico(dico_lang , "srv_bourse/first_audio_flash")%>&nbsp;<span class="userTime"><%=time1%></span><br>
											</logic:greaterEqual>
											<logic:equal name="nbr" value="2">
												<bean:define id="time2" name="srvBourseData" property="time2"/>
												<%=DicoTools.dico(dico_lang , "srv_bourse/second_audio_flash")%>&nbsp;<span class="userTime"><%=time2%></span><br>
											</logic:equal>
										</li>
									</logic:greaterEqual>
									<logic:greaterEqual name="weekend" value="1">
										<li>
											<%=DicoTools.dico(dico_lang , "srv_bourse/deactivate_week_end")%>
										</li>
									</logic:greaterEqual>
									
									<li class="modifier">
										<a class="link-modify" href='<html:rewrite forward="srvBourseFull"/>?dispatch=display&applicationId=<%=Application.NativeApplication.BOURSE_FULL.getApplication().getId()%>&valueTo=<%=id%>'><span><%=DicoTools.dico(dico_lang , "srv_bourse/modify")%></span></a>
									</li>
								</ul>
							</li>
						</logic:iterate>
			
										
						<li style="border:none; text-align:center;">
							<logic:greaterThan name="isReg" value="0">
								<%/* Supression */%>		
								<logic:notEqual name="isReg" value="0"> 
									<html:button property="delete" value="<%=DicoTools.dico(dico_lang , "srv_air/button_delete")%>" styleClass="genericDeleteBt" onclick="serviceDelete();"><%=DicoTools.dico(dico_lang , "srv_air/button_delete")%></html:button>
								</logic:notEqual>
							</logic:greaterThan>
							<logic:notEqual name="valueTo" value="0">
								<logic:lessThan name="nbrValue" value="<%=maxValue.toString()%>"> <% /* Tant que l'on ne depasse pas le quota */ %>					
									<a class="link-modify" href='<html:rewrite forward="srvBourseFull"/>?dispatch=display&applicationId=<%=Application.NativeApplication.BOURSE_FULL.getApplication().getId()%>'><input class="genericBt" type="button" value="<%=DicoTools.dico(dico_lang , "srv_bourse/button_add")%>"/></a>
								</logic:lessThan>
							</logic:notEqual>
						</li>
							
						
						</ul>
					</logic:notEmpty>
				</div>
			</div>
		
		<!--  **************************** Ajout d'une valeur ******************************** -->
		<div class="srv-config" >
		<html:form action="/action/srvBourseFullConfig" styleId="srvBourseConfig" styleClass="srvConfigForm">
			<input type="hidden" id="internalSrvId" value="<%=Application.NativeApplication.BOURSE_FULL.getApplication().getId()%>" />
			<logic:equal name="valueTo" value="0">
				<h2><%=DicoTools.dico(dico_lang , "srv_bourse/add_alert")%></h2>
			</logic:equal>
			<logic:notEqual name="valueTo" value="0">
				<h2><%=DicoTools.dico(dico_lang , "srv_bourse/modify_alert")%></h2>
			</logic:notEqual>
		
			<hr class="spacer" />
			
			<logic:equal name="mySrvBourseFullForm" property="duplicateName" value="1">
				<div class="warningMsg" ><%=DicoTools.dico(dico_lang , "srv_bourse/name_alert_exists")%></div>	
				<script type="text/javascript">
					gSrvErrorFlag = true;
					$("#valNameAlert").extHighlight();
				</script>
			</logic:equal>
				
			<div id="valNameAlert">
				<label><%=DicoTools.dico(dico_lang , "srv_bourse/name_alert")%></label>
				<html:text name="mySrvBourseFullForm" property="alertName" style="width:190px;" styleId="alertName" />
			</div>
			
			<hr class="spacer" />
			
			<logic:equal name="mySrvBourseFullForm" property="falseValue" value="1">
				<div class="warningMsg" ><%=DicoTools.dico(dico_lang , "srv_bourse/value_code_invalid")%></div>
				<script type="text/javascript">
					gSrvErrorFlag = true;		
					$("#valNameId").extHighlight();
				</script>
			</logic:equal>
				
			<div id="valNameId" style="background-color:#fff">
				<label><%=DicoTools.dico(dico_lang , "srv_bourse/value_name")%></label>
				<html:text name="mySrvBourseFullForm" property="valName" style="width:190px;"  styleId="valName" />
				<div align="center">
					<a class="link-help-txt" target='_blank' href='<%=DicoTools.dico(dico_lang , "srv_bourse/link")%>' ><%=DicoTools.dico(dico_lang , "srv_bourse/linkname")%></a>
				</div>
			</div>
			<hr class="spacer" />
			
			<label><%=DicoTools.dico(dico_lang , "srv_bourse/or_index")%></label>
			<html:select name="mySrvBourseFullForm" property="indic" styleId="list">
				<option value=""></option>
				<!--  FrequenceData -->
				<html:optionsCollection name="mySrvBourseFullForm" property="indicList" label="label" value="id"/>
			</html:select>
		
			<hr class="spacer" />
			
			<label><%=DicoTools.dico(dico_lang , "srv_bourse/first_audio_flash")%></label>
			<bean:define name="mySrvBourseFullForm" property="horraire1" id="heures1" />
			<input id="time1" name="horraire1" type="hidden" class="hourPicker" value="<%=heures1%>" />	
			<!-- <html:text name="mySrvBourseFullForm" property="horraire1" /> -->
			
			<hr class="spacer" />
				
			<label><%=DicoTools.dico(dico_lang , "srv_bourse/second_audio_flash")%></label>
			<bean:define name="mySrvBourseFullForm" property="horraire2" id="heures2" />
			<input id="time2" name="horraire2" type="hidden" class="hourPicker" value="<%=heures2%>" />	
				
			<!-- <html:text name="mySrvBourseFullForm" property="horraire2" /> -->
			
			<hr class="spacer" />
		
			<label for="weekend"><%=DicoTools.dico(dico_lang , "srv_bourse/deactivate_week_end")%></label><html:checkbox name="mySrvBourseFullForm" property="weekend" styleId="weekend" value="1" />
			
			<hr class="spacer" />
			
			<label for="lumiere"><%=DicoTools.dico(dico_lang , "srv_bourse/light_language")%></label><html:checkbox name="mySrvBourseFullForm" property="lumiere" styleId="lumiere" value="1" /> <a href="#keskiditTop"><%=DicoTools.dico(dico_lang , "srv_all/light_help")%></a> <%-- &nbsp;<a class="thickbox link-help-txt" id="popup" title="<%=DicoTools.dico(dico_lang , "srv_all/light_title")%>" href='javascript:;' onclick="TB_show('<%=DicoTools.dico(dico_lang , "srv_all/light_title")%>', '#TB_inline?height=186&width=480&inlineId=keskidit')" ><%=DicoTools.dico(dico_lang , "srv_all/light_help")%></a>--%>

			<hr class="spacer" />
			
			<html:hidden styleId="dispatchConfig" property ="dispatch" value="load" />
					
			<div align="center">
				<logic:lessThan name="nbrValue" value="<%=maxValue.toString()%>"> <!-- Tant que l'on ne depasse pas le quota -->
					<logic:equal name="valueTo" value="0">
						<html:submit property="activate" value="<%=DicoTools.dico(dico_lang , "srv_bourse/button_activate")%>" styleClass="genericBt" onclick="goDispatch('activate', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_bourse/button_activate")%></html:submit>	
					</logic:equal>
				</logic:lessThan>
			
				<logic:equal name="nbrValue" value="<%=maxValue.toString()%>">
					<%=DicoTools.dico(dico_lang , "srv_bourse/nb_max_alert")%>
				</logic:equal>
			
				<logic:notEqual name="valueTo" value="0">
					<html:hidden name="mySrvBourseFullForm" property="valueTo" styleId="valueTo" value="<%=valueTo.toString()%>"/>
					<html:submit property="update" value="<%=DicoTools.dico(dico_lang , "srv_bourse/button_update")%>"  styleClass="genericBt" onclick="goDispatch('update', 'dispatchConfig')"><%=DicoTools.dico(dico_lang , "srv_bourse/button_update")%></html:submit>	
				</logic:notEqual>
			</div>
		</html:form>
		
		<%-- <hr class="spacer" />
		
		<div id="keskidit" class="keskidit">
			<div class="inner"></div>
		</div>	
			--%>		
		
		</div>
			<!-- ******************************************** /CONTENT ***************************************************** -->        
			<hr class="spacer" />	
	</div>
</div>			

<hr class="clearer" />

<script type="text/javascript">
	/* liens de modifs */
	$("a.link-modify").each(function(){
		var url = $(this).attr("href");
		$(this).attr("href", "javascript:;");
		$(this).click(function(){
			divChangeUrlBackground("contentSrvConfig", url);
		});
	});
	
	$("a.link-delete").each(function(){
		var url = $(this).attr("href");
		$(this).attr("href", "javascript:;");
		$(this).click(function(){
			divChangeUrlBackground("contentSrvConfig", url);
		});
	});	
	
	$("input.hourPicker").hourSelect_InitN(false );
		
	$("#list").select_NotclickableValues();		
	
	$("#srvBourseConfig").submit(function(){
		return nablifeValidateBourseFullConfig(<logic:greaterThan name="isReg" value="0">true</logic:greaterThan>); 
	});
	
	$("#list").change(function(){
		$("#valName").val("");
	});
	$("#valName").change(function(){
		$("#list option").attr("selected", "false").eq(0).attr("selected", true);
	});
	
	<logic:notEqual name="valueTo" value="0">			
		$("#srvItem_<%=valueTo%>").addClass("selected");
	</logic:notEqual>

	
</script>

