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

<bean:define name="mySrvNathanConfigForm" property="dicoRoot" id="dicoRoot" />


<%-- a inclure dans setUpSrv-container --%>

<div id="setUpSrv">
	
	<div class="main-cadre-contener serviceContener serviceBookReaderConfig">	
		<div class="main-cadre-top" >
			<h2><%=DicoTools.dico(dico_lang , "srv_nathan/config_title")%></h2>
		</div>
		
		<div class="main-cadre-content" >

			<div class="intro"><%=DicoTools.dico(dico_lang , "srv_nathan/config_intro")%></div>
			
			<div class="ztamps-contener">
			
				<bean:define id="isbn" name="mySrvNathanConfigForm" property="isbn" />
				<bean:define id="versionId" name="mySrvNathanConfigForm" property="versionId"/>
				<bean:define id="appletId" name="mySrvNathanConfigForm" property="appletId"/>
				<bean:define id="description" name="mySrvNathanConfigForm" property="description"/>
				<bean:define id="isShared" name="mySrvNathanConfigForm" property="isShared"/>
			
				<html:form action="/action/srvNathanConfig.do" method="post" styleId="configForm">
				
					<html:hidden property="dispatch" value="edit"/>	
					<html:hidden property="isbn" value="<%=isbn.toString()%>"/>
					<html:hidden property="versionId" value="<%=versionId.toString()%>" />
					<html:hidden property="appletId" value="<%=appletId.toString()%>" />					
				<div class="nathan">	
				<div class="enregistrement">
					<strong><%=DicoTools.dico(dico_lang , "srv_nathan/config_describe")%> </strong><br />
					
					<b><%=DicoTools.dico(dico_lang , "srv_nathan/config_i_am")%> : </b><br /><br />
					<div class="nathan-descr-box">
					<ul>
					<logic:iterate id="tag" name="mySrvNathanConfigForm" property="sexTags">
						<li><bean:define id="tagId" name="tag" property="id" />
						<bean:define id="tagLabel" name="tag" property="label" type="java.lang.String"/>
						<html:radio name="mySrvNathanConfigForm" property="checkedSexTag" value="<%=tagId.toString()%>"/><%=DicoTools.dico(dico_lang , tagLabel)%>
						</li>
					</logic:iterate>
					</ul>
					</div>
					<hr class="clearer" />
					
					
					<b><%=DicoTools.dico(dico_lang , "srv_nathan/config_interpretation")%> : </b><br /><br />
					<div class="nathan-descr-box">
					<ul>
					<logic:iterate id="tag" name="mySrvNathanConfigForm" property="interpretationTags">
						<li><bean:define id="tagId" name="tag" property="id" />
						<bean:define id="tagLabel" name="tag" property="label" type="java.lang.String" />
						<html:radio name="mySrvNathanConfigForm" property="checkedInterpretationTag" value="<%=tagId.toString()%>"/><%=DicoTools.dico(dico_lang , tagLabel)%>
						</li>
					</logic:iterate> 
					</ul>
					</div>
					<hr class="clearer" />
					
					<b><%=DicoTools.dico(dico_lang , "srv_nathan/config_effects")%> : </b><br/>
					<div class="nathan-descr-box">
					<ul>
					<logic:iterate id="tag" name="mySrvNathanConfigForm" property="effectsTags">
						<li><bean:define id="tagId" name="tag" property="id" />
						<bean:define id="tagLabel" name="tag" property="label" type="java.lang.String" />
						<html:radio name="mySrvNathanConfigForm" property="checkedEffectsTag" value="<%=tagId.toString()%>"/><%=DicoTools.dico(dico_lang , tagLabel)%>
						</li>					
					</logic:iterate>
					</ul>
					</div>
					<hr class="clearer" />
					
					<b><%=DicoTools.dico(dico_lang , "srv_nathan/config_i_speak")%> : </b><br /><br />
					<div class="nathan-descr-box">
					<ul>
					<logic:iterate id="tag" name="mySrvNathanConfigForm" property="accentTags">
						<li><bean:define id="tagId" name="tag" property="id" />
						<bean:define id="tagLabel" name="tag" property="label" type="java.lang.String" />
						<html:radio name="mySrvNathanConfigForm" property="checkedAccentTag" value="<%=tagId.toString()%>"/><%=DicoTools.dico(dico_lang , tagLabel)%>
						</li>
					</logic:iterate>
					</ul>
					</div>
					<hr class="clearer" /><br/>
					
					<strong><%=DicoTools.dico(dico_lang , "srv_nathan/config_description")%> </strong> 
					<textarea name="description" cols="50"><%=description%></textarea><br/><br/>

					<%=DicoTools.dico(dico_lang , "srv_nathan/config_des_share")%>
					<%=DicoTools.dico(dico_lang , "srv_nathan/config_partage")%><br/>
					<div class="service-description">
					<p><html:radio name="mySrvNathanConfigForm" property="isShared" value="0"/><%=DicoTools.dico(dico_lang , "srv_nathan/config_dont_share")%></p>
					<p><html:radio name="mySrvNathanConfigForm" property="isShared" value="1"/><%=DicoTools.dico(dico_lang , "srv_nathan/config_share")%></p>
					<br />
					<div class="item-tools">
					<html:submit value="<%=DicoTools.dico(dico_lang , "srv_nathan/config_validate")%>" onclick="page.postAjax('configForm', 'setUpSrv-container'); return false;"/>
					</div></div></div></div>
					<div class="info-note">
						<%=DicoTools.dico(dico_lang , "srv_nathan/config_hint")%>
					</div>
				
				</html:form>
				
			</div>

			<hr class="clearer" />
			
			<%-- ************************************************************************************************************* --%>
		</div><!-- End of main content -->
	</div>
	
</div>



<div id="how-to-container" class="main-cadre-contener">

	<div class="main-cadre-top"><h2><%=DicoTools.dico(dico_lang , "services/how_does_it_work")%></h2></div>
	
	<div class="main-cadre-content">
		<hr class="spacer"/>
		<div class="srv-main-config">
			<p>	<%=DicoTools.dico(dico_lang , "srv_nathan/how_to_config")%></p>
		</div>
		
		<hr class="spacer"/>
			
	</div>
</div>

