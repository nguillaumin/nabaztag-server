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



<bean:define id="couleurSignature" name="myTerrierSignatureForm" property="userColorSign" type="String"/>
		
<html:form action="/action/myTerrierSignature" styleId="idTerrierSignature">
<html:hidden property="mode" value="3"/>
<html:hidden property="userColorSign" styleId="colorPicker_value"/>


<div class="flat-block"> 
	<div class="flat-block-top">
		<h3 class="no-icone">
			<%=DicoTools.dico(dico_lang, "myTerrier/signature_title")%>
		</h3>
	</div>

	<div class="flat-block-content">
		<div class="flat-block-content-inner">
		<%-- *********************************************** --%>

		<div class="twoCol-left" style="width:75%">
			<div>
				<%=DicoTools.dico(dico_lang, "myTerrier/signature_description")%>
			</div>
			
			<hr class="spacer" />		
		
			<%-- Choix de l'animation sonore --%>
			<div class="form-line">
				<label class="center">
					<%=DicoTools.dico(dico_lang, "myTerrier/signature_choose_sound")%>
				</label>
				<span>
					<html:select property="userMusic"
								onchange=""
								styleClass="custom">
						<option value="0"><%=DicoTools.dico(dico_lang, "myTerrier/signature_choose_sound_none")%></option>
						<!-- MusicData -->
						<html:optionsCollection name="myTerrierSignatureForm" property="musicList" label="music_name" value="music_id"/>
					</html:select>	
				</span>
			</div>

			<%-- Choix de l'animation coloree --%>
			<div class="form-line">
				<label class="center">
					<%=DicoTools.dico(dico_lang, "myTerrier/signature_choose_animation")%>
				</label>
				<span>
				

					<html:select property="userColor"
								onchange=""
								styleClass="custom">
						<option value="0"><%=DicoTools.dico(dico_lang, "myTerrier/signature_choose_animation_none")%></option>
						<!-- AnimData -->
						<html:optionsCollection name="myTerrierSignatureForm" property="animList" label="label" value="id"/>
					</html:select>
					
				</span>
			</div>

			<%-- Choix de la couleur --%>
			
		
			<div class="form-line">
				<label class="center">
					<%=DicoTools.dico(dico_lang, "myTerrier/signature_choose_color")%>
				</label>
				<span>

					<ul class="colorChoice" id="colorPicker">
						<li style="background-color:#ff0000;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "myTerrier/signature_color_red")%></span></a></li>
						<li style="background-color:#ffff00;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "myTerrier/signature_color_yellow")%></span></a></li>
						<li style="background-color:#00ff00;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "myTerrier/signature_color_green")%></span></a></li>
						<li style="background-color:#00ffff;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "myTerrier/signature_color_cyan")%></span></a></li>									
						<li style="background-color:#0000ff;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "myTerrier/signature_color_blue")%></span></a></li>
						<li style="background-color:#ff00ff;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "myTerrier/signature_color_mauve")%></span></a></li>	
					</ul>
			
				</span>
			</div>
			<hr class="clearer" />
		</div>

		<div class="twoCol-right" style="width:23%">
			<div id="signature">   
					<bean:define name="myTerrierSignatureForm" property="user_signature" id="user_signature"/>
					<embed id="mymovie" width="135" height="135" flashvars="cdll=<%=user_signature.toString() %>" quality="high" name="mymovie" wmode="transparent" src="../include_flash/CDLEditor.swf" type="application/x-shockwave-flash"/>				
			</div>
		</div>		
		
			<hr class="clearer" />

		<div class="buttons">			
			<input class="genericBt" type="button" name="modifProfil" value="<%=DicoTools.dico(dico_lang, "myTerrier/signature_modify")%>" onclick="validateProfilSignature();"/>		
		</div>
		
		<%-- *********************************************** --%>		
		</div>
	</div>
</div>


</html:form> 
<script type="text/javascript">
	setTimeout(function(){
		colorPicker_Init('colorPicker', '<%=couleurSignature%>');	
	}, 100);
</script>
