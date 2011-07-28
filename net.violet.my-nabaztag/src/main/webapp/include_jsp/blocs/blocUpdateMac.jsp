<%/* 0013d3849e90 */ %>
<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@page import="net.violet.platform.datamodel.Lang"%>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>


<% /* la mise a jour de la mac c'est bien passé, on redirect */ %>
<logic:equal name="BlocForm" property="errors" value="success">
	<script type="text/javascript">
		redirect("myTerrier.do?onglet=Profil");
	</script>
</logic:equal>

<logic:equal name="BlocForm" property="param1" value="true">
	<h1>
		<%=DicoTools.dico(dico_lang , "bloc/updateMac_title")%>
	</h1>
</logic:equal>
			
<div class="block-content"><div class="inner">
	<logic:notEmpty name="BlocForm" property="macAddress">	
	
			<logic:equal name="BlocForm" property="param1" value="true">
				<div id="block-content-updatemac">
			</logic:equal>
			
			<logic:equal name="BlocForm" property="errors" value="none">
				<p>
					<%=DicoTools.dico(dico_lang , "bloc/updateMac_text1-1")%>	
					<a href="javascript:;" onclick="$('#chgMacForm').show();"><%=DicoTools.dico(dico_lang , "bloc/updateMac_cliquez")%></a>
					<%=DicoTools.dico(dico_lang , "bloc/updateMac_text1-2")%>				
				</p>
		
				<hr class="spacer" />
				
				<div id="chgMacForm" style="display:none" >	
			</logic:equal>
			
				<p>
					<%=DicoTools.dico(dico_lang , "bloc/updateMac_text2")%>
				</p>
				
				<hr class="spacer" />
				
				<html:form action="/action/Bloc" styleId="idFormMAC" onsubmit="return regMacUpdateValidate();" acceptCharset="UTF-8" >
				
					<logic:notEqual name="BlocForm" property="errors" value="success">
						
						<logic:equal name="BlocForm" property="errors" value="recheckMac">
							<p class='generic-error'>
								<%=DicoTools.dico(dico_lang , "bloc/updateMac_error1")%>
							</p>
						</logic:equal>
						
						<logic:equal name="BlocForm" property="errors" value="gotARabbit">
							<p class='generic-error'>
								<%=DicoTools.dico(dico_lang , "bloc/updateMac_error1")%>
							</p>
						</logic:equal>
											
						<html:text name="BlocForm" property="macAddress" styleId="numSerie" styleClass="serialInput" maxlength="12" />
						
						<html:hidden property="action" value="updateMac"/>
						<html:hidden property="param1" value="false"/>
						<html:hidden name="BlocForm" property="errors" value="none"/>
						<p class="validButton">				
							<input type="submit" name="modifProfil" value="<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_modify_button")%>" />
						</p>
					</logic:notEqual>
					 
				</html:form>
				
			<logic:equal name="BlocForm" property="errors" value="none">
				</div>
			</logic:equal>
		
			<logic:equal name="BlocForm" property="param1" value="true">
				</div>
			</logic:equal>	
		
	
	</logic:notEmpty>
</div></div>

