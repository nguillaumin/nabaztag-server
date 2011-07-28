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
<bean:define id="isConnected" property="isConnected" name="myTerrierCompteForm" />
<bean:define id="macAddress" property="macAddress" name="myTerrierCompteForm" />

	<html:form action="action/myTerrierCompte" styleId="form_account" acceptCharset="UTF-8" >
	
		<div class="flat-block"  > 
		   
			<div class="flat-block-top" >
				<h3 class="no-icone">
					<%=DicoTools.dico(dico_lang, "myTerrier/prefs_account_title")%>
				</h3>
			</div>
		
			<div class="flat-block-content" >
				<div class="flat-block-content-inner" >
					
					<%-- *********************************************************************************************** --%>
		
					<div class="rabbit-state"> 

							<logic:equal name="isConnected" value="0">
								<img src="../include_img/terrier/rabbit_off.gif" alt="offline" />
								<span class="rabbit-state">
									OFFLINE
								</span>
							</logic:equal>
							
							<logic:equal name="isConnected" value="1">
								<img src="../include_img/terrier/rabbit_on.gif" alt="online" />
								<span class="rabbit-state">
									ONLINE
								</span>
							</logic:equal>	
							
							<logic:equal name="isConnected" value="2">
								<img src="../include_img/terrier/rabbit_waiting.gif" alt="" />
							</logic:equal>
					</div>
					
					<%-- *********************************************************************************************** --%>
					
					<div class="account-infos">
						<%-- PSEUDO --%>
						<div class="form-line">
							<label class="center">
								<%=DicoTools.dico(dico_lang, "myTerrier/account_nabname")%>
							</label>
							<span style="font-size:14px; font-weight:bold;">
								<bean:write name="myTerrierCompteForm" property="pseudo"/>
							</span>
						</div>
		
						<%-- EMAIL --%>				
						<div class="form-line">				
							<label class="center">
								<%=DicoTools.dico(dico_lang, "myTerrier/account_email")%>
							</label>
							<span style="font-size:14px; font-weight:bold;">
								<bean:write name="myTerrierCompteForm" property="email"/>
							</span>
						</div>
						
						<%-- ADRESSE MAC --%>
						
						<logic:notEmpty name="macAddress" >
						
							<div class="form-line">				
								<label class="center">
									<%=DicoTools.dico(dico_lang, "myTerrier/account_serial")%>
								</label>
								<span style="font-size:14px; font-weight:bold;">
									<%=macAddress %>
								</span>
							</div>
						
						</logic:notEmpty>
						
						</div>
						
				</div>
		
			</div>
		</div>
	</html:form>	