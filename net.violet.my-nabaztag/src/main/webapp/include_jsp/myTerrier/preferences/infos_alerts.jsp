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
<logic:equal  name="myTerrierAlerteForm" property="mode" value="1">
	<ul class="general-msg">
		<li>
			<%=DicoTools.dico(dico_lang, "myTerrier/preferences_modif_ok")%>
		</li>
	</ul>
</logic:equal>

<html:form action="action/myTerrierAlerte" styleId="form_alerts" acceptCharset="UTF-8" >
	<html:hidden name="myTerrierAlerteForm" property="mode" value="1" />
	<html:hidden name="myTerrierAlerteForm" property="queFaire" value="alerte" />

	<div class="flat-block"  > 
	   
		<div class="flat-block-top" >
			<h3 class="no-icone">
				<%=DicoTools.dico(dico_lang, "myTerrier/preferences_title")%>
			</h3>
		</div>
	
		<div class="flat-block-content" >
			<div class="flat-block-content-inner" >
				<%-- *********************************************************************************************** --%>
					<div class="form-line">
						<label>
							<html:checkbox styleId="c1" name="myTerrierAlerteForm" property="msgSent" value="1"/>
							<%=DicoTools.dico(dico_lang, "myTerrier/preferences_send_mail_message")%>
						</label>
					</div>
	
					<div class="form-line">
						<label>
							<html:checkbox styleId="c2" name="myTerrierAlerteForm" property="msgPlayed" value="1"/>
							<%=DicoTools.dico(dico_lang, "myTerrier/preferences_send_mail_played")%>
						</label>
					</div>
					
					<div class="form-line">
						<label>
							<html:checkbox styleId="c3" name="myTerrierAlerteForm" property="msgReceived" value="1"/>
							<%=DicoTools.dico(dico_lang, "myTerrier/preferences_send_mail_received")%>
						</label>
					</div>
					
					<div class="form-line">
						<label>
							<html:checkbox styleId="c4" name="myTerrierAlerteForm" property="newsletter" value="1"/>
							<%=DicoTools.dico(dico_lang, "myTerrier/preferences_susbscribe_newsletter")%>
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
<%--
<div class="profilBlock specialBlock" style="min-height: 1%;">
	<div class="title">
		<h2><%=DicoTools.dico(dico_lang, "myTerrier/preferences_title")%></h2>
	</div>
	<div class="infosWide">
		<html:checkbox styleId="c1" name="myTerrierAlerteForm" property="msgSent" value="1"/><label for="c1" class="right"><%=DicoTools.dico(dico_lang, "myTerrier/preferences_send_mail_message")%></label>
		<hr class="spacer" />
		<html:checkbox styleId="c2" name="myTerrierAlerteForm" property="msgPlayed" value="1"/><label for="c2" class="right"><%=DicoTools.dico(dico_lang, "myTerrier/preferences_send_mail_played")%></label>
		<hr class="spacer" />
		<html:checkbox styleId="c3" name="myTerrierAlerteForm" property="msgReceived" value="1"/><label for="c3" class="right"><%=DicoTools.dico(dico_lang, "myTerrier/preferences_send_mail_received")%></label>
		<hr class="spacer" />
		<html:checkbox styleId="c4" name="myTerrierAlerteForm" property="newsletter" value="1"/><label for="c4" class="right"><%=DicoTools.dico(dico_lang, "myTerrier/preferences_susbscribe_newsletter")%></label>
	</div>
</div>

--%>