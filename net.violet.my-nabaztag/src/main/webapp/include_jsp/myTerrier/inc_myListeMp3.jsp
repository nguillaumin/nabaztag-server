<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<%@ page import="net.violet.platform.datamodel.MusicStyle" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>

<%	Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>

<div class="intro-cadre-contener">
	<div class="intro-cadre-top">
		<h2><%=DicoTools.dico(dico_lang, "myTerrier/mp3_titre")%></h2>
	</div>
	<div class="intro-cadre-content">
		<div class="intro">
			<%=DicoTools.dico(dico_lang, "myTerrier/mp3_intro")%>
		</div>
	</div>
</div>

<p align="center">
	<button onclick="TB_show('', 'myManageMp3.do?height=170&width=250')" class="genericBt"><%=DicoTools.dico(dico_lang, "myTerrier/add_mp3")%></button>
</p>

<hr class="spacer" />

<div class="flat-block">
<div class="flat-block-top">
<h3 class="no-icone"><%=DicoTools.dico(dico_lang, "myTerrier/mp3_perso")%></h3>
</div>

<div class="flat-block-content">
<div class="flat-block-content-inner">
<p><%=DicoTools.dico(dico_lang, "myTerrier/mp3_perso_hint")%></p>

<div style="float:left; margin-left:10px;" id="myPlayerMp3"></div>

<hr class="clearer" />

<div class="maMusique" style="height:auto; padding-bottom:10px;">
	<div style="float:left;  width:49%;">
		<logic:iterate name="myListeMp3Form" property="listeMp3User1" id="musicData">
			<bean:define id="music_name1" name="musicData" property="music_name"/>
			<bean:define id="music_share1" name="musicData" property="music_share"/>	
			<bean:define id="music_id1" name="musicData" property="music_id"/>	
			<bean:define id="music_name_short1" name="musicData" property="music_name_short"/>	
			<bean:define id="music_url1" name="musicData" property="music_url" />

			<form action="../action/myManageMp3.do" name="sendMsg" method="post" id="updateMusic_<%=music_id1%>" onsubmit="return validateEditMp3(<%=music_id1%>);">
				<input type="hidden" name="queFaire" value="update">
				<input type="hidden" name="idMp3" value="<%=music_id1%>">
				<ul style="position:relative;" class="specialBlock<logic:greaterThan name="musicData" property="music_share" value="0">Selected</logic:greaterThan>">
					<logic:equal name="musicData" property="music_styleid" value="<%=String.valueOf(MusicStyle.CATEGORIE_MP3_PERSO)%>">
							<li class='edit'><a href='javascript:void(0)' onclick="mp3_toggleOptions( 'mp3Options_<%=music_id1%>', <%=music_share1%> );"><span>[E]</span></a></li>
							<li class='nom'><div onclick="mp3_toggleOptions( 'mp3Options_<%=music_id1%>', <%=music_share1%> );" id="mp3name_<%=music_id1%>" title="<%=music_name1%> : id = <%=music_id1%>"><%=music_name_short1%></div></li>
					</logic:equal>
					<logic:notEqual name="musicData" property="music_styleid" value="<%=String.valueOf(MusicStyle.CATEGORIE_MP3_PERSO)%>">
						<li class='nom'><div title="<%=music_name1%> : id = <%=music_id1%>"><%=music_name_short1%></div></li>
					</logic:notEqual>
					<li class='play'><a class="LplayIcone" href='javascript:void(0);' onclick="loadPersoPlayer('<%=music_url1%>');" title="<%=DicoTools.dico(dico_lang, "myTerrier/play_mp3")%>" ><span><%=DicoTools.dico(dico_lang, "myTerrier/play_mp3")%></span></a></li>
					<li class='delete'><a class="LdeleteIcone" href='javascript:void(0);' onclick="confirmDeleteMp3(<%=music_id1%>);" title="<%=DicoTools.dico(dico_lang, "myTerrier/delete_mp3")%>" ><span><%=DicoTools.dico(dico_lang, "myTerrier/delete_mp3")%></span></a></li>
					<li class='edit_options' id='mp3Options_<%=music_id1%>'>
						&nbsp;
					</li>			
				</ul>
			</form>

		</logic:iterate>  
	</div>
	
	<div style="float:left; width:49%">
		<logic:iterate name="myListeMp3Form" property="listeMp3User2" id="musicData">
			<bean:define id="music_name2" name="musicData" property="music_name"/>
			<bean:define id="music_share2" name="musicData" property="music_share"/>	
			<bean:define id="music_id2" name="musicData" property="music_id"/>
			<bean:define id="music_name_short2" name="musicData" property="music_name_short"/>	
			<bean:define id="music_url2" name="musicData" property="music_url" />

			<form action="../action/myManageMp3.do" name="sendMsg" method="post" id="updateMusic_<%=music_id2%>" onsubmit="return validateEditMp3(<%=music_id2%>);">
			<input type="hidden" name="queFaire" value="update">
			<input type="hidden" name="idMp3" value="<%=music_id2%>">			
			<ul  style="position:relative;" class="specialBlock<logic:greaterThan name="musicData" property="music_share" value="0">Selected</logic:greaterThan>">
				<logic:equal name="musicData" property="music_styleid" value="<%=String.valueOf(MusicStyle.CATEGORIE_MP3_PERSO)%>">
							<li class='edit'><a href='javascript:void(0)' onclick="mp3_toggleOptions( 'mp3Options_<%=music_id2%>', <%=music_share2%> );"><span>[E]</span></a></li>
							<li class='nom'><div onclick="mp3_toggleOptions( 'mp3Options_<%=music_id2%>', <%=music_share2%> );" id="mp3name_<%=music_id2%>" title="<%=music_name2%> : id = <%=music_id2%>"><%=music_name_short2%></div></li>
				</logic:equal>
				<logic:notEqual name="musicData" property="music_styleid" value="<%=String.valueOf(MusicStyle.CATEGORIE_MP3_PERSO)%>">
					<li class='nom'><div title="<%=music_name2%> : id = <%=music_id2%>"><%=music_name_short2%></div></li>
				</logic:notEqual>
				<li class='play'><a class="LplayIcone" href='javascript:void(0);' onclick="loadPersoPlayer('<%=music_url2%>');" title="<%=DicoTools.dico(dico_lang, "myTerrier/play_mp3")%>" ><span><%=DicoTools.dico(dico_lang, "myTerrier/play_mp3")%></span></a></li>
				<li class='delete'><a class="LdeleteIcone" href='javascript:void(0);' onclick="confirmDeleteMp3(<%=music_id2%>);" title="<%=DicoTools.dico(dico_lang, "myTerrier/delete_mp3")%>" ><span><%=DicoTools.dico(dico_lang, "myTerrier/delete_mp3")%></span></a></li>
				<li class='edit_options' id='mp3Options_<%=music_id2%>'>&nbsp;</li>			
			</ul>
			</form>
		</logic:iterate>
	</div>
	</div>
	</div>
	</div>
</div>

<script>
	TB_init();
</script>
