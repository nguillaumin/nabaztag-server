<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<% response.setCharacterEncoding("UTF-8"); %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<div id="mp3-sender">
	<ul class="menu">
		<li id="mp3Perso_li"  class="selected"><a href="javascript:;" onclick="sendMp3Chooser('mp3Perso')" ><%=DicoTools.dico(dico_lang, "myMessages/my_mp3")%></a></li>
		<li id="mp3Upload_li" ><a href="javascript:;" onclick="sendMp3Chooser('mp3Upload')" ><%=DicoTools.dico(dico_lang, "myMessages/send_new_mp3")%></a></li>
	</ul>
	<div id="mp3Perso" class="mp3-choice">
		<label><%=DicoTools.dico(dico_lang, "myMessages/mp3_your_mp3s")%></label><br/>
		<html:select name="myMessagesMp3Form" property="idMp3" styleId="idMp3" style="width:200px;">
			<option value="-1" selected><%=DicoTools.dico(dico_lang, "myMessages/mp3_choose")%></option>
			
			<logic:iterate name="myMessagesMp3Form" property="listeMp3" id="musicData">
			<bean:define id="musicId" name="musicData" property="musicId"/>
			<bean:define id="musicName" name="musicData" property="musicName"/>
				
			<logic:equal name="myMessagesMp3Form" property="idMp3" value="<%=musicId.toString()%>">
				<option value="<%=musicId.toString()%>" selected><%=musicName%></option>
			</logic:equal>
			<logic:notEqual name="myMessagesMp3Form" property="idMp3" value="<%=musicId.toString()%>">
				<option value="<%=musicId.toString()%>"><%=musicName%></option>
			</logic:notEqual>
			
			</logic:iterate>
		</html:select>
	</div>	
	<div id="mp3Upload" class="mp3-choice">
			<iframe src ="javascript:void(0);" id="uploadIframe" name="uploadIframe" style="border: 0px; width:0px; height:0;"></iframe>
	
			<label><%=DicoTools.dico(dico_lang, "myMessages/mp3_send_file")%></label><br />
			<input name="musicName" type="text" id="musicName" style="width:92%;">
			<hr class="spacer" />
			<label><%=DicoTools.dico(dico_lang, "myMessages/mp3_file")%></label><br />
			<html:file name="myMessagesMp3Form" property="musicFile" styleId="musicFile" style="width:92%;" value="" />

			<hr class="spacer" />

			<label><%=DicoTools.dico(dico_lang, "myMessages/mp3_record_from")%></label><br/><input  name="musicStart"  id="musicStart" value="0" type="text"  style="width:30px" ><%=DicoTools.dico(dico_lang, "myMessages/mp3_seconds_beginning")%><hr  class="spacer" />
			<label><%=DicoTools.dico(dico_lang, "myMessages/mp3_duration")%></label><br/><input name="musicTime"  id="musicTime" value="45" type="text"  style="width:30px" > <%=DicoTools.dico(dico_lang, "myMessages/mp3_seconds")%>	
			<hr class="clearer" style="margin-bottom:10px;"/>

	</div>
</div>