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

<bean:define id="mp3Id" name="myListeMp3Form" property="mp3Id" />
<bean:define id="categId" name="myListeMp3Form" property="categId" />



<hr class="spacer" />
<div class="moreMp3Options" >
	<input name="shareMp3" value="share_mp3" type="checkbox" onclick="moreOptionsToggle(<%=mp3Id%>);" id="share_<%=mp3Id%>" <logic:greaterThan name="myListeMp3Form" property="categId" value="0">checked="checked"</logic:greaterThan> /><label style="margin-left:5px;" for="share_<%=mp3Id%>"><%=DicoTools.dico(dico_lang, "myTerrier/nabshare_publish")%></label>
	<div id="more_<%=mp3Id%>" style="<logic:equal name="myListeMp3Form" property="categId" value="0">display:none;</logic:equal>" >
		<hr class="spacer" />
		<label for="tags"><%=DicoTools.dico(dico_lang, "myTerrier/nabshare_tags")%></label>
		<textarea name="cloudTag" id="tags" cols="" rows="3" ><logic:iterate name="myListeMp3Form" property="listeTags" id="tagData"><bean:write name="tagData" property="word"/>, </logic:iterate></textarea>
		<hr class="spacer" />
		
		<label><%=DicoTools.dico(dico_lang, "myTerrier/nabshare_category")%></label>
		<select name="categId" id="categfor_<%=mp3Id%>" >
			<option value=""><%=DicoTools.dico(dico_lang, "myTerrier/nabshare_category_none")%></option>
			<logic:iterate name="myListeMp3Form" property="listeCateg" id="LabelData">
				<bean:define id="id" name="LabelData" property="id"/>
				<bean:define id="label" name="LabelData" property="label"/>
				<option value="<%=id%>" <logic:equal name="LabelData" property="id" value="<%=categId.toString()%>">selected</logic:equal>><%=label%></option>
			</logic:iterate>
		</select>
	</div>
	
	<hr class="spacer" />
	
	<input onclick="mp3_toggleOptions( 'mp3Options_<%=mp3Id%>');" type="button" value="<%=DicoTools.dico(dico_lang, "myTerrier/nabshare_cancel")%>" class="genericBt" />
	<input type="submit" value="<%=DicoTools.dico(dico_lang, "myTerrier/nabshare_validate")%>" class="genericBt"/>
	
	
	<hr class="clearer" />
</div>