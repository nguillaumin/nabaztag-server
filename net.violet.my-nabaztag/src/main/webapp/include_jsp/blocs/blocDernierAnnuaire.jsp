<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.datamodel.User" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>
<%
    final User theUser = SessionTools.getUserFromSession(request);
    final String myUser_id;
    if (theUser == null) {
       myUser_id = "0";
    } else {
       myUser_id = theUser.getId().toString();
    }
%>
<% 	String user_main = Long.toString(SessionTools.getRabbitIdFromSession(session)); %>

<bean:define id="userMain" value="<%=user_main %>"/>
<bean:define id="user" value="<%=myUser_id %>"/>

<h1><%=DicoTools.dico(dico_lang , "bloc/last_rabbits_title")%></h1>

<div class="block-content">
	<div class="inner">
		<logic:greaterThan name="userMain" value="0">
			<div class="intro">
					<%=DicoTools.dico(dico_lang , "bloc/nabthem-intro")%>
			</div>
		</logic:greaterThan>
		<div id="nabthem"></div>	
	</div>		
</div>

<script>
	<bean:define id="user" value="<%=myUser_id %>"/>
	var datas = 
		[
		<logic:iterate name="BlocForm" property="result" id="userData">
			<bean:define id="paysdata" name="userData" property="pays" />
			<bean:define id="pseudo" name="userData" property="user_pseudo" />
			<bean:define id="user_id" name="userData" property="id" />
			<bean:define id="user_good" name="userData" property="user_good" />
			<bean:define id="city" name="userData" property="annu_city" />
			<bean:define id="nom_pays" name="paysdata" property="pays_nom" />
			<bean:define id="age" name="userData" property="age" />
			<bean:define id="sexe" name="userData" property="annu_sexe" />
			 
			<% String sex_java = sexe.equals("H") ? "bloc/profile_man" : "bloc/profile_woman";%>
			
			{
				"sex"		: "<%=DicoTools.dico(dico_lang , sex_java)%>",
				"nabname"	: "<%=pseudo%>",
				"imgUrl"	: "../photo/<%=user_id%>_B.jpg",
				"age"		: <%=age%>,
				"city"		: "<%=city%>",
				"country"	: "<%=nom_pays%>"
			},
		</logic:iterate>
		{} ] 	

	$("#nabthem").nabThemWidget({
			showMsgSend:nabaztag.constantes.ISLOG,
			pictAnimSpeed:600,
			"datas":datas,
			cycleSpeed:8000,
			texts	: {
							age				: '<%=DicoTools.dico(dico_lang , "bloc/last_rabbits_age_year")%>', 
							tellHimHello	: '<%=DicoTools.dico(dico_lang , "bloc/nabthem-tellhello")%>',			
							writeHim		: '<%=DicoTools.dico(dico_lang , "bloc/nabthem-writehim")%>',
							send			: '<%=DicoTools.dico(dico_lang , "bloc/nabthem-sendmessage")%>',
							emptyMsg		: '<%=DicoTools.dico(dico_lang , "bloc/nabthem-messageempty")%>'						
						}
	});
</script>

