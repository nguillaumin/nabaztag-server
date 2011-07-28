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

<bean:define name="myNabaztalandCreateForm" property="mode" id="mode" />

<logic:equal name="mode" value="0">
	<bean:define id="idNabcast" name="myNabaztalandCreateForm" property="idNabcast" />

<div class="intro-cadre-contener intro-cadre-simple service-introduction">
	
	<div class="intro-cadre-top">
		<img alt="nabcast" src="../include_img/services/icones_services/icon_nabcast.gif" class="icone-service"/>
		<h2><%=DicoTools.dico(dico_lang, "srv_create/nabcast_new_title")%></h2>
	</div>
	

	<div class="intro-cadre-content">
		
			<div class="image">
				<img src="../include_img/abo/nabcasts.gif"/>		
			</div>

			<div class="description">
				<%=DicoTools.dico(dico_lang, "srv_create/nabcast_new_text")%>
			</div>
	</div>
	
	<hr class="clearer" />
	
</div>
	
<html:form action="/action/myNabaztalandCreate" styleId="formNabcast">
	<bean:define id="soundList" name="myNabaztalandCreateForm" property="nabcast_soundList"/>
	<bean:define id="animList" name="myNabaztalandCreateForm" property="nabcast_animList"/>
	<bean:define id="nabcast_categorie" name="myNabaztalandCreateForm" property="nabcast_categorie"/>

<div class="specialBlock" style="overflow:hidden;">
	<div class="createNabcast Column50" >
		<h2 class='nabcastName'>
			<logic:equal name="idNabcast" value="0">
				<%=DicoTools.dico(dico_lang, "srv_create/nabcast_new")%>
			</logic:equal>
			<logic:notEqual name="idNabcast" value="0">
				<bean:define id="titre" name="myNabaztalandCreateForm" property="nabcast_title" />
				<%=titre%>
			</logic:notEqual>		
		</h2>
		

			<label><%=DicoTools.dico(dico_lang, "srv_create/nabcast_title")%></label>
			<html:text name="myNabaztalandCreateForm" property="nabcast_title" styleId="nabcastName">
				<%=DicoTools.dico(dico_lang, "srv_create/nabcast_type_title")%>
			</html:text>

	
			<label><%=DicoTools.dico(dico_lang, "srv_create/nabcast_description")%></label>
			<html:textarea name="myNabaztalandCreateForm" property="nabcast_description" rows="5" styleId="nabcastDesc">
				<%=DicoTools.dico(dico_lang, "srv_create/nabcast_type_description")%>
			</html:textarea>
		
			<label><%=DicoTools.dico(dico_lang, "srv_create/nabcast_language")%></label>
			
			<html:select name="myNabaztalandCreateForm" property="nabcast_lang" >
				<logic:iterate name="myNabaztalandCreateForm" property="langList" id="langData">
					<bean:define name="langData" property="lang_id" id="lang_id"/>
					<bean:define name="langData" property="lang_title" id="lang_title"/>
					<bean:define name="langData" property="lang_type" id="lang_type"/>
						
					<logic:lessEqual name="lang_type" value="0">
							<html:option value="<%=lang_id.toString()%>"><%=lang_title.toString()%></html:option>
					</logic:lessEqual>
				</logic:iterate>
			</html:select>
		

			<label><%=DicoTools.dico(dico_lang, "srv_create/nabcast_category")%></label>

			<div style='width:65%;padding:5px; background-color:#ffffff' id='categContener'>

			 <html:select property="nabcast_categorie" styleId="nabcastCat" style="width:100%;">
					<!-- <option class="sticky" value=""><%=DicoTools.dico(dico_lang, "srv_create/srv_create/nabcast_choose_category")%></option>-->
					<logic:iterate name="myNabaztalandCreateForm" property="nabcast_categorieList" id="nabcastCategData">
						<bean:define name="nabcastCategData" property="nabcastcateg_id" id="nabcastcateg_id"/>
						<bean:define name="nabcastCategData" property="nabcastcateg_val" id="nabcastcateg_val"/>
						<bean:define name="nabcastCategData" property="nabcastcateg_lang" id="nabcastcateg_lang"/>
						<bean:define name="nabcastCategData" property="nabcastcateg_desc" id="nabcastcateg_desc"/>
	
						<option class="<%="lang_"+nabcastcateg_lang.toString()%>" value="<%=nabcastcateg_id.toString()%>" <%= (nabcast_categorie.equals(nabcastcateg_id.toString())) ? "selected" : "" %>><%=nabcastcateg_val.toString()%></option>
					</logic:iterate>
				</html:select>
			</div>	
			
			<%-- ajout url courte --%>
			<label><%=DicoTools.dico(dico_lang, "srv_create/nabcast_shortcut")%></label>
			http://my.nabaztag.com/n/ <html:text name="myNabaztalandCreateForm" property="nabcast_shortcut" style="width:120px;" />
			<p class="little-hint"><%=DicoTools.dico(dico_lang, "srv_create/nabcast_shortcut_hint")%></p>
	</div>

<div class="Column50 createNabcast">	
	<h2><%=DicoTools.dico(dico_lang, "srv_create/nabcast_signature")%></h2>


		<label><%=DicoTools.dico(dico_lang, "srv_create/nabcast_signature_color")%></label>	
		<ul class="colorChoice" id="colorPicker" style="float:left;">
			<li style="background-color:#ff0000;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "srv_create/nabcast_color_red")%></span></a></li>
			<li style="background-color:#ffff00;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "srv_create/nabcast_color_yellow")%></span></a></li>
			<li style="background-color:#00ff00;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "srv_create/nabcast_color_green")%></span></a></li>
			<li style="background-color:#00ffff;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "srv_create/nabcast_color_cyan")%></span></a></li>									
			<li style="background-color:#0000ff;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "srv_create/nabcast_color_blue")%></span></a></li>
			<li style="background-color:#ff00ff;"><a class="color_unselected" href="#"><span><%=DicoTools.dico(dico_lang, "srv_create/nabcast_color_mauve")%></span></a></li>	
		</ul>

		<hr class="spacer" />

		<label><%=DicoTools.dico(dico_lang, "srv_create/nabcast_signature_animation")%></label>
		<html:select name="myNabaztalandCreateForm" property="nabcast_anim">
			<option value="0"><%=DicoTools.dico(dico_lang, "srv_create/nabcast_signature_choose_animation")%></option>	
			<html:optionsCollection name="myNabaztalandCreateForm" property="nabcast_animList" label="label" value="id"/>
		</html:select>

		<hr class="spacer" />
				
		<label><%=DicoTools.dico(dico_lang, "srv_create/nabcast_signature_sound")%></label>
		<html:select name="myNabaztalandCreateForm" property="nabcast_sound">
			<html:optionsCollection name="myNabaztalandCreateForm" property="nabcast_soundList" label="label" value="id"/>
		</html:select>

		<hr class="spacer" />

		<logic:equal name="idNabcast" value="0">
			<html:hidden name="myNabaztalandCreateForm" property="mode" value="1" styleId="mode" />
			<html:hidden name="myNabaztalandCreateForm" styleId="idNabcast" property="idNabcast" value="0" />
		</logic:equal>
		
		<logic:notEqual name="idNabcast" value="0">
			<bean:define id="idNabcast" name="myNabaztalandCreateForm" property="idNabcast" />
			<html:hidden name="myNabaztalandCreateForm" styleId="idNabcast" property="idNabcast" value="<%=idNabcast.toString()%>" />
			<html:hidden name="myNabaztalandCreateForm" property="mode" value="3" styleId="mode" />
		</logic:notEqual>
	
		<input type="hidden" id="colorPicker_value" />
	
	</div>
	
	<hr class="spacer"/>

	<div style="text-align:center; ">
		<logic:notEqual name="idNabcast" value="0">	
			<input type="button" value="<%=DicoTools.dico(dico_lang, "srv_create/nabcast_delete")%>" class="genericDeleteBt" onclick="DeleteMyNabcast();" />
		</logic:notEqual>
		<input type="button" value="<%=DicoTools.dico(dico_lang, "myTerrier/nabshare_validate")%>" class="genericBt" onclick="validateNabcast();" />
	</div>

</div>	
</html:form>

<div id="mynabcastUpload"></div>

	<bean:define property="colorPicker_value" name="myNabaztalandCreateForm" id="colorPicker"/>

	<script type="text/javascript">
		colorPicker_Init('colorPicker', '<%=colorPicker%>');
		nabCastLang_init();
		
		<logic:notEqual name="idNabcast" value="0">
			/* si on a selectionn� un nabcast pour l'edition*/
			blocMyNabcast_Select(<%=idNabcast%>);
			
			divChangeUrl("mynabcastUpload", "<html:rewrite forward='goNabcastUpload'/>?idNabcast=<%=idNabcast%>");
			

			
		</logic:notEqual>	
	</script>

</logic:equal>


<logic:equal name="mode" value="1">
	<%/* Nabcast cr�� la r�ponse est g�r� dans validateNabcast.js */%>
	<bean:define id="idNabcast" name="myNabaztalandCreateForm" property="idNabcast"/>
	<%=idNabcast%>
</logic:equal>

<logic:equal name="mode" value="3">
	<%/* Nabcast modifi�  */%>
	<%/* la r�ponse est g�r� dans validateNabcast.js  */%>
</logic:equal>