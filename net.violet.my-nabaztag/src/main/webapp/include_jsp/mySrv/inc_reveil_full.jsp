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


<bean:define property="isReg" name="mySrvReveilFullForm" id="isReg"/>
<bean:define property="serviceName" name="mySrvReveilFullForm" id="serviceName" type="String"/>

<div class="main-cadre-contener">
	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/configure")%></h2></div>
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<!-- ******************************************** CONTENT ***************************************************** --> 
		<html:form action="/action/srvReveilFullConfig" styleId="srvReveilConfig" styleClass="srvConfigForm">
	
			<bean:define name="mySrvReveilFullForm" property="sonNom1" id="sonNom1" />
			<bean:define name="mySrvReveilFullForm" property="music_id1" id="music1" />
			<bean:define name="mySrvReveilFullForm" property="sonNom2" id="sonNom2" />
			<bean:define name="mySrvReveilFullForm" property="music_id2" id="music2" />
			<bean:define name="mySrvReveilFullForm" property="sonNom3" id="sonNom3" />
			<bean:define name="mySrvReveilFullForm" property="music_id3" id="music3" />
			<bean:define name="mySrvReveilFullForm" property="sonNom4" id="sonNom4" />
			<bean:define name="mySrvReveilFullForm" property="music_id4" id="music4" />
			<bean:define name="mySrvReveilFullForm" property="sonNom5" id="sonNom5" />
			<bean:define name="mySrvReveilFullForm" property="music_id5" id="music5" />
			<bean:define name="mySrvReveilFullForm" property="sonNom6" id="sonNom6" />
			<bean:define name="mySrvReveilFullForm" property="music_id6" id="music6" />
			<bean:define name="mySrvReveilFullForm" property="sonNom7" id="sonNom7" />
			<bean:define name="mySrvReveilFullForm" property="music_id7" id="music7" />
					
			<bean:define name="mySrvReveilFullForm" property="horraire1" id="heures1" />
			<bean:define name="mySrvReveilFullForm" property="horraire2" id="heures2" />
			<bean:define name="mySrvReveilFullForm" property="horraire3" id="heures3" />
			<bean:define name="mySrvReveilFullForm" property="horraire4" id="heures4" />
			<bean:define name="mySrvReveilFullForm" property="horraire5" id="heures5" />
			<bean:define name="mySrvReveilFullForm" property="horraire6" id="heures6" />								
			<bean:define name="mySrvReveilFullForm" property="horraire7" id="heures7" />	
	
			<a class="suppr" href="javascript:;" onclick="srvClearReveil(1);"><span>x</span></a>
			<label style="width:70px;"><%=DicoTools.dico(dico_lang , "srv_reveil/monday")%></label>
			<input id="time1" name="horraire1" type="hidden" class="hourPicker" value="<%=heures1%>" />
			<input type='hidden' class='songPicker' name="music_id1" id="son1" title="<%=sonNom1%>" value="<%=music1%>" />
		
			<hr class="spacer">
				
			<a class="suppr" href="javascript:;" onclick="srvClearReveil(2);"><span>x</span></a>
			<label style="width:70px;"><%=DicoTools.dico(dico_lang , "srv_reveil/tuesday")%></label>
			<input id="time2" name="horraire2" type="hidden" class="hourPicker" value="<%=heures2%>" />
			<input type='hidden' class='songPicker' name="music_id2"  id="son2" title="<%=sonNom2%>" value="<%=music2%>" />
		
			<hr class="spacer">
					
			<a class="suppr" href="javascript:;" onclick="srvClearReveil(3);"><span>x</span></a>
			<label style="width:70px;"><%=DicoTools.dico(dico_lang , "srv_reveil/wednesday")%></label>
			<input id="time3" name="horraire3" type="hidden" class="hourPicker" value="<%=heures3%>" />
			<input type='hidden' class='songPicker' name="music_id3"  id="son3"  title="<%=sonNom3%>" value="<%=music3%>"/>
		
			<hr class="spacer">
				
			<a class="suppr" href="javascript:;" onclick="srvClearReveil(4);"><span>x</span></a>
			<label style="width:70px;"><%=DicoTools.dico(dico_lang , "srv_reveil/thursday")%></label>
			<input id="time4" name="horraire4" type="hidden" class="hourPicker" value="<%=heures4%>" />
			<input type='hidden' class='songPicker' name="music_id4"  id="son4"  title="<%=sonNom4%>" value="<%=music4%>"/>
		
			<hr class="spacer">
				
			<a class="suppr" href="javascript:;" onclick="srvClearReveil(5);"><span>x</span></a>
			<label style="width:70px;"><%=DicoTools.dico(dico_lang , "srv_reveil/friday")%></label>
			<input id="time5" name="horraire5" type="hidden" class="hourPicker" value="<%=heures5%>" />
			<input type='hidden' class='songPicker' name="music_id5"  id="son5"  title="<%=sonNom5%>" value="<%=music5%>"/>
		
			<hr class="spacer">
					
			<a class="suppr" href="javascript:;" onclick="srvClearReveil(6);"><span>x</span></a>
			<label style="width:70px;"><%=DicoTools.dico(dico_lang , "srv_reveil/saturday")%></label>
			<input id="time6" name="horraire6" type="hidden" class="hourPicker" value="<%=heures6%>" />
			<input type='hidden' class='songPicker' name="music_id6"  id="son6"  title="<%=sonNom6%>" value="<%=music6%>"/>
		
			<hr class="spacer">
					
			<a class="suppr" href="javascript:;" onclick="srvClearReveil(7);"><span>x</span></a>
			<label style="width:70px;"><%=DicoTools.dico(dico_lang , "srv_reveil/sunday")%></label>
			<input id="time7" name="horraire7" type="hidden" class="hourPicker" value="<%=heures7%>" />
			<input type='hidden' class='songPicker' name="music_id7"  id="son7"  title="<%=sonNom7%>" value="<%=music7%>"/>
		
			<hr class="spacer">
		
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
			
		</html:form>

	</div>
</div>

<script type="text/javascript">
	$("input.hourPicker").hourSelect_InitN(false );
	
	
	$("input.songPicker").chooseSong();
	
	$("#contentSrvConfig form").submit(function(){
		return nablifeValidateReveilFullConfig(<logic:equal name="isReg" value="1">true</logic:equal>); 
	});
	
	
</script>

