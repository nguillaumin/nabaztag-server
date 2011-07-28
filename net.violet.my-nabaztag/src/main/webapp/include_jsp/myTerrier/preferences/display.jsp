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
<logic:equal  name="myTerrierAffichageForm" property="mode" value="1">
	<ul class="general-msg">
		<li>
			<%=DicoTools.dico(dico_lang, "myTerrier/preferences_modif_ok")%>
		</li>
	</ul>
</logic:equal>

<%-- AFFICHAGE --%>
<html:form action="action/myTerrierAffichage" styleId="form_display" acceptCharset="UTF-8" >
	<bean:define name="myTerrierAffichageForm" property="layout" id="layout" type="String"/>


	<html:hidden name="myTerrierAffichageForm" property="mode" value="1" />

	<div class="flat-block"  > 
	   
		<div class="flat-block-top" >
			<h3 class="no-icone">
				<logic:greaterThan name="idLapin" value="0">
					<%=DicoTools.dico(dico_lang, "myTerrier/prefs_display_title")%>
				</logic:greaterThan>
			</h3>
		</div>
	
		<div class="flat-block-content" >
			<div class="flat-block-content-inner" >
				<%-- *********************************************************************************************** --%>

					<%-- Molette --%>
					<fieldset>
						<legend>
							<%=DicoTools.dico(dico_lang, "myTerrier/preference_skin_title")%>
						</legend>
						
						<div class="flat-block-intro">
							<%=DicoTools.dico(dico_lang, "myTerrier/site_color_text")%>
						</div>
						
	
						
						<hr class="spacer" />
						
						<html:hidden name="myTerrierAffichageForm" property="layout"  styleId='skin-template' />
						
						<ul class='skin-selector'>
							<li class='layout_green'><a  class='<logic:equal name="myTerrierAffichageForm" property="layout" value="layout_green">selected</logic:equal>' href="javascript:;" onclick="changeCssSheme('layout_green'); "><span>green</span></a></li>
							<li class='layout_pink'><a   class='<logic:equal name="myTerrierAffichageForm" property="layout" value="layout_pink">selected</logic:equal>' href="javascript:;" onclick="changeCssSheme('layout_pink'); "><span>pink</span></a></li>
							<li class='layout_violet'><a class='<logic:equal name="myTerrierAffichageForm" property="layout" value="layout_violet">selected</logic:equal>' href="javascript:;" onclick="changeCssSheme('layout_violet'); "><span>violet</span></a></li>
							<li class='layout_blue'><a   class='<logic:equal name="myTerrierAffichageForm" property="layout" value="layout_blue">selected</logic:equal>' href="javascript:;" onclick="changeCssSheme('layout_blue'); "><span>blue</span></a></li>
							<li class='layout_orange'><a class='<logic:equal name="myTerrierAffichageForm" property="layout" value="layout_orange">selected</logic:equal>' href="javascript:;" onclick="changeCssSheme('layout_orange'); "><span>orange</span></a></li>
						</ul>
						
						<hr class="spacer" />

					</fieldset>


					<%-- Langue affichage --%>
					<fieldset>
						<legend>
							<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_site_language")%>
						</legend>
						
						<div class="flat-block-intro">
							<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_site_language_intro")%>
						</div>
												
						<html:select property="langUser" styleId="spokenLanguage">
							<logic:iterate name="myTerrierAffichageForm" property="langList" id="langData">
								<bean:define name="langData" property="lang_id" id="lang_id"/>
								<bean:define name="langData" property="lang_title" id="lang_title"/>
								<bean:define name="langData" property="lang_type" id="lang_type"/>
								
								<logic:lessEqual name="lang_type" value="0">
										<html:option value="<%=lang_id.toString()%>">
											<%=lang_title.toString()%>
										</html:option>
								</logic:lessEqual>
								
							</logic:iterate>
						</html:select>						

					</fieldset>

					<%-- Langue que je parle --%>
					<fieldset>
						<legend>
							<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_i_speak")%>
						</legend>
						
						<div class="flat-block-intro">
							<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_i_speak_intro")%>
						</div>
												
						<ul id='spoken-lang-profil'>
							<logic:iterate name="myTerrierAffichageForm" property="langList" id="langData">
								<li><label>
								<bean:define name="langData" property="lang_id" id="lang_id"/>
								<bean:define name="langData" property="lang_title" id="lang_title"/>
								<bean:define name="langData" property="lang_type" id="lang_type"/>
									<html:multibox name="myTerrierAffichageForm" property="checkListLang" >
										<bean:write name="lang_id"/>
									</html:multibox>
									<bean:write name="lang_title"/>
								</label></li>
							</logic:iterate>
						</ul>					

					</fieldset>

					<div class="buttons">
						<input class="genericBt" type="submit" name="submit" value="<%=DicoTools.dico(dico_lang, "myTerrier/preferences_modify")%>"/>
					</div>

				<%-- *********************************************************************************************** --%>
			</div>
		</div>
	</div>
</html:form>