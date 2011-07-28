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


<bean:define name="myMessagesBiblioForm" property="langUser" id="lang" type="String"/>
<bean:define name="myMessagesBiblioForm" property="langBiblio" id="langClin"/>


<div class="selectedItem" >
	<%=DicoTools.dico(dico_lang, "myMessages/user_selection")%><span id="ItemReplaceme1">-</span>
</div>

	<input name="idBiblio" type="hidden" id="itemValue" value="">   
<div class="specialBlock"> 
	<ul class="item-listing">
		<li><a onclick="ajaxJsExec('../include_jsp/myMessage/ajax_shuffleBiblio.jsp')" href='javascript:;' class="shuffle"><%=DicoTools.dico(dico_lang, "myMessages/shuffle_selection")%></a></li>
		<logic:iterate name="myMessagesBiblioForm" property="listeCatBiblio" id="musicData">
			<bean:define id="maCatBiblioId" name="musicData" property="musicSytleId"/>
			<bean:define id="maCatBiblioName" name="musicData" property="musicStyleName"/>		
			<li><a href="../action/myMessagesBiblioChoice.do?langBiblio=<%=langClin%>&idBiblio=<%=maCatBiblioId%>" class="item"><%=maCatBiblioName%></a> </li>
		</logic:iterate>
	</ul>
	<hr class="clearer" />
</div>    

<script type="text/javascript">
	$("a.item").openMsgCatChoice();
</script>
