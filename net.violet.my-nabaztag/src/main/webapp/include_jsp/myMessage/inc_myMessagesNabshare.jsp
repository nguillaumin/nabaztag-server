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

<div class="nabshare">
  <div class="selectedItem"><%=DicoTools.dico(dico_lang, "myMessages/user_selection")%><span id="ItemReplaceme1">-</span></div>

  <h3><%=DicoTools.dico(dico_lang, "myMessages/nabshares_nav_by_cat")%></h3>
  <div id="ItemRubSelect" class="specialBlock">
  
  	<input name="idMp3" type="hidden" id="itemValue" value="">   
<ul class="item-listing">  
	<li><a onclick="ajaxJsExec('../action/myMessagesNabshareChoice.do?shuffle=1')" href='javascript:;' class="shuffle"><%=DicoTools.dico(dico_lang, "myMessages/shuffle_selection")%></a></li>
    <logic:iterate name="myMessagesNabshareForm" property="listeCateg" id="LabelData">
		<bean:define id="id" name="LabelData" property="id"/>
		<bean:define id="label" name="LabelData" property="label"/>
			<li><a id="nabshareRub_<%=id%>" href="../action/myMessagesNabshareChoice.do?idCateg=<%=id%>" class="item"><%=label%></a></li>
	</logic:iterate>
</ul>   
    	<hr class="clearer" />
  </div>
 
  <h3><%=DicoTools.dico(dico_lang, "myMessages/nabshares_nav_by_tag")%></h3>
   <div class="specialBlock" >
			<logic:iterate name="myMessagesNabshareForm" property="listeWordCloud" id="tagData">
				<bean:define id="word" name="tagData" property="word"/>
				<bean:define id="id_css" name="tagData" property="id_css"/>
					<a href="../action/myMessagesNabshareChoice.do?tag=<%=word%>" class="item cloud_<%=id_css%>"><%=word%></a>
			</logic:iterate>
 
  </div>


</div>
<script type="text/javascript">
	$("a.item").openMsgCatChoice();
</script>