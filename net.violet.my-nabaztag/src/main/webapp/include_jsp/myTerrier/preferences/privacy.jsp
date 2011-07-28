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

<%-- TRAITEMENT ERREUR/SUCCES --%>
<logic:equal  name="myTerrierPriveForm" property="mode" value="1">
	<ul class="general-msg">
		<li>
			<%=DicoTools.dico(dico_lang, "myTerrier/preferences_modif_ok")%>
		</li>
	</ul>
</logic:equal>


<html:form action="action/myTerrierPrive" styleId="form_privacy" acceptCharset="UTF-8" >
	
	
	<html:hidden name="myTerrierPriveForm" property="mode" value="1" />	
	
	<html:hidden name="myTerrierPriveForm" property="queFaire" value="prive" />
			
	<div class="flat-block"  > 
	   
		<div class="flat-block-top" >
			<h3 class="no-icone">
				<%=DicoTools.dico(dico_lang, "myTerrier/preferences_privacy_title")%>
			</h3>
		</div>
	
		<div class="flat-block-content" >
			<div class="flat-block-content-inner" >
				<%-- *********************************************************************************************** --%>
	
	
				<div class="form-line">
					<label>
											
						<html:checkbox name="myTerrierPriveForm" property="authorisation" value="1"/>
						<%=DicoTools.dico(dico_lang, "myTerrier/preferences_privacy_authorize_add")%>
					</label>
				</div>
	
				<div class="form-line">
					<label>					
						<html:checkbox name="myTerrierPriveForm" property="notification" value="2"/>
						<%=DicoTools.dico(dico_lang, "myTerrier/preferences_privacy_notify_add")%>
					</label>
				</div>
				
				<div class="form-line">
					<label>					
						<html:checkbox styleId="f1" name="myTerrierPriveForm" property="filtre" value="1"/>
						<%=DicoTools.dico(dico_lang, "myTerrier/preferences_only_friends")%>
					</label>
				</div>
	
				<div class="buttons">
					<input class="genericBt" type="submit" name="submit" value="<%=DicoTools.dico(dico_lang, "myTerrier/preferences_modify")%>"/>
				</div>
				
				<%-- *********************************************************************************************** --%>
			</div>
		</div>
		
	</div>
</html:form>