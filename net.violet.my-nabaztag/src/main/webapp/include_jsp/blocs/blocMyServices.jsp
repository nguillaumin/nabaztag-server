<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@page import="net.violet.platform.datamodel.Lang"%><h1>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %> 


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>


	<logic:greaterThan name="BlocForm" property="user_main" value="0">
		<%=DicoTools.dico(dico_lang , "bloc/myservices_title")%>
	</logic:greaterThan>
	<logic:lessEqual name="BlocForm" property="user_main" value="0">
		<%=DicoTools.dico(dico_lang, "bloc/about_nabaztag_title")%>
	</logic:lessEqual>
</h1>

<div class="block-content">
<div class="inner">
<logic:notEmpty name="BlocForm" property="result"> 
	<ul class="list" >
 	<logic:iterate name="BlocForm" property="result" id="blocData" >
			<bean:define id="url" name="blocData" property="url" />
			<bean:define id="id" name="blocData" property="id" />
			<bean:define id="name" name="blocData" property="name" />
			<bean:define id="type" name="blocData" property="type" />
	
			<%/* Les nabcasts */%>
			<logic:equal name="blocData" property="type" value="1">
				<li style="background-image:url(../include_img/services/icones_services/icon_service.gif); background:none;" id="id_<%=id%>_<%=type%>" class="nabcast"><img src="../include_img/loading.gif" class="loader"/><a class="srvConfigLink" title="<%=DicoTools.dico_if(dico_lang, name.toString())%>" href='<%=url%>'><span class="srvName"><%=DicoTools.dico_if(dico_lang, name.toString())%></span></a></li>
			</logic:equal>
			
			<%/* Les services */%>
			<logic:equal name="blocData" property="type" value="2">
				<li style="background-image:url(../include_img/services/icones_services/icon_service.gif);  background:none;" id="id_<%=id%>_<%=type%>" class="service"><img src="../include_img/loading.gif" class="loader"/><a class="srvConfigLink" title="<%=DicoTools.dico_if(dico_lang, name.toString())%>" href='<%=url%>'><span class="srvName"><%=DicoTools.dico_if(dico_lang, name.toString())%></span></a></li>
			</logic:equal>		
			
			<%/* Les services en attente de validation */%>
			<logic:equal name="blocData" property="type" value="4">
				<li style="background-image:url(../include_img/services/icones_services/icon_service.gif);  background:none;" id="id_<%=id%>_<%=type%>" class="service servicactivated"><img src="../include_img/loading.gif" class="loader"/><a class="srvConfigLink" title="<%=DicoTools.dico_if(dico_lang, name.toString())%>" href='<%=url%>'><span class="srvName"><%=DicoTools.dico_if(dico_lang, name.toString())%></span></a></li>
			</logic:equal>
			
					
	</logic:iterate>
  </ul>
</logic:notEmpty>
<logic:empty name="BlocForm" property="result">
	<logic:greaterThan name="BlocForm" property="user_main" value="0">
		<%=DicoTools.dico(dico_lang , "bloc/myservices_no_service_error")%>
	</logic:greaterThan>
	<logic:lessEqual name="BlocForm" property="user_main" value="0">
		<%=DicoTools.dico(dico_lang, "bloc/about_nabaztag_description")%>
	</logic:lessEqual>
</logic:empty>
</div>
</div>

<script language="javascript">
	//TB_init("bloc-MyServices");
	bloc.services.init();
</script>