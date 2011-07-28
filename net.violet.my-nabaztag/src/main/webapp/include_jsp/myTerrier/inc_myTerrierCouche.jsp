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

<bean:define name="myTerrierCoucheForm" property="user_main" id="user_main"/>
<bean:define name="myTerrierCoucheForm" property="startW" id="startW" />
<bean:define name="myTerrierCoucheForm" property="endW" id="endW" />
<bean:define name="myTerrierCoucheForm" property="startWe" id="startWe" />
<bean:define name="myTerrierCoucheForm" property="endWe" id="endWe" />
<logic:greaterThan name="user_main" value="0">

<html:form action="/action/myTerrierCouche" >

<div class="flat-block"  > 
   
	<div class="flat-block-top" >
		<h3 class="no-icone">
			<%=DicoTools.dico(dico_lang, "myTerrier/couche_title")%>
		</h3>
	</div>

	<div class="flat-block-content" >
		<div class="flat-block-content-inner" >
			<%-- *********************************************************************************************** --%>

				<div  class="twoCol-left" >					
					<fieldset>
						<legend>
							<%=DicoTools.dico(dico_lang, "myTerrier/couche_week")%> &bull; <%=DicoTools.dico(dico_lang, "myTerrier/couche_go_to_sleep")%>
						</legend>
						
						<div class="form-line">
							<label style="width: 140px;">
								<%=DicoTools.dico(dico_lang, "myTerrier/couche_week_from")%>
							</label>
							<input id="endW" name="endW" type="hidden" class="hourPicker" value="<%=endW%>" />						
						</div>
						
						<div class="form-line">
							<label style="width: 140px;">
								<%=DicoTools.dico(dico_lang, "myTerrier/couche_week_to")%>
							</label>
							<input id="startW" name="startW" type="hidden" class="hourPicker" value="<%=startW%>" />	
						</div>
						
					</fieldset>
				</div>
				<div  class="twoCol-right" >
					<fieldset>
						<legend>
							<%=DicoTools.dico(dico_lang, "myTerrier/couche_weekend")%> &bull; <%=DicoTools.dico(dico_lang, "myTerrier/couche_go_to_sleep")%>
						</legend>
						
						<div class="form-line">
							<label style="width: 140px;">
								<%=DicoTools.dico(dico_lang, "myTerrier/couche_week_from")%>
							</label>
							<input id="endWe" name="endWe" type="hidden" class="hourPicker" value="<%=endWe%>" />						
						</div>
						
						<div class="form-line">
							<label style="width: 140px;">
								<%=DicoTools.dico(dico_lang, "myTerrier/couche_week_to")%>
							</label>
							<input id="startWe" name="startWe" type="hidden" class="hourPicker" value="<%=startWe%>" />	
						</div>
						
					</fieldset>
				</div>
				<hr class="clearer" />
		
				<div class="buttons">
						<logic:equal name="myTerrierCoucheForm" property="isReg" value="1">
							<html:hidden name="myTerrierCoucheForm" property="delete" value="0"/>	
							<html:button value="<%=DicoTools.dico(dico_lang, "myTerrier/couche_button_deactivate")%>" property="del" styleClass="genericDeleteBt" />
						</logic:equal>
					
						<logic:equal name="myTerrierCoucheForm" property="isReg" value="0">
							<html:hidden name="myTerrierCoucheForm" property="add" value="1"/>
							<html:submit value="<%=DicoTools.dico(dico_lang, "myTerrier/couche_button_activate")%>" styleClass="genericBt" />
						</logic:equal>
						
						<logic:equal name="myTerrierCoucheForm" property="isReg" value="1">
							<html:hidden name="myTerrierCoucheForm" property="add" value="1"/>
							<html:submit value="<%=DicoTools.dico(dico_lang, "myTerrier/couche_button_modify")%>" styleClass="genericBt" />
						</logic:equal>
				</div>
				
				<div class="little-hint">
					<%=DicoTools.dico(dico_lang, "myTerrier/couche_note")%>
				</div>
				
				
							
			<%-- *********************************************************************************************** --%>
		</div>
	</div>
</div>
</html:form>
</logic:greaterThan>


 