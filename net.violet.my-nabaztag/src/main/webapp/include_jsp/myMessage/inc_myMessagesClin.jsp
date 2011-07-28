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


<bean:define name="myMessagesClinForm" property="langUser" id="lang" type="String"/>
<bean:define name="myMessagesClinForm" property="langClin" id="langClin"/>


<div>
	<ul class="langSelect">
	<logic:iterate name="myMessagesClinForm" property="langList" id="langData">
		<bean:define name="langData" property="lang_id" id="lang_id" />
		<bean:define name="langData" property="lang_type" id="lang_type"/>
		<bean:define name="langData" property="lang_iso_code" id="lang_iso_code"/>
			
		<logic:lessEqual name="lang_type" value="0">
			<li class="<%=lang_iso_code.toString()%> <logic:equal name="myMessagesClinForm" property="langClin" value="<%=lang_id.toString()%>">selected</logic:equal>" ><a href="javascript:void(0);" onclick="tabChangeUrl( 'Messages_clindoeil' , '../action/myMessagesClin.do?langClin=<%=lang_id.toString()%>'); $('#mp3persoDiv').hide();"><span></span></a></li>
		</logic:lessEqual>
	</logic:iterate>
	</ul>
</div>

<div class="selectedItem" >
	<%=DicoTools.dico(dico_lang, "myMessages/user_selection")%> <span id="ItemReplaceme1">-</span>
</div>


<input name="idClin" type="hidden" id="itemValue" value="">  
<div class="specialBlock"> 
	<ul class="item-listing">
		<li><a onclick="ajaxJsExec('../include_jsp/myMessage/ajax_shuffleClin.jsp?langClin=<%=langClin%>')" href='javascript:;' class="shuffle"><%=DicoTools.dico(dico_lang, "myMessages/shuffle_selection")%></a></li>
		<logic:iterate name="myMessagesClinForm" property="listeCatClin" id="musicStyleData">
			<bean:define id="maCatClinId" name="musicStyleData" property="musicSytleId"/>
			<bean:define id="maCatClinName" name="musicStyleData" property="musicStyleName"/>		
			<!-- <li><a href="../action/myMessagesClinChoice.do?height=420&width=350&langClin=<%=langClin%>&idClin=<%=maCatClinId%>" class="thickbox item"><%=maCatClinName%></a></li> -->
			<li><a href="../action/myMessagesClinChoice.do?langClin=<%=langClin%>&idClin=<%=maCatClinId%>" class="item"><%=maCatClinName%></a></li>
		</logic:iterate>
	</ul>
<hr class="clearer" />
</div>

<script type="text/javascript">
	$("a.item").openMsgCatChoice();
</script>	


