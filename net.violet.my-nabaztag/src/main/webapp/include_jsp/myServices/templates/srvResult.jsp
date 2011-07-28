<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %> 
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<%@page import="java.util.TimeZone"%>
<bean:define name="myNablifeForm" property="langUser" id="lang" type="String"/>
<bean:define name="myNablifeForm" property="userId" id="user_id" />
<bean:define name="myNablifeForm" property="userTimeZone" id="timeZone"/>
<bean:define name="myNablifeForm" property="searched" id="searched"/>

<!-- Service/Podcast/RSS --> 
<logic:notEmpty name="myNablifeForm" property="srvListData">
	<bean:define id="srvListData" name="myNablifeForm" property="nablifeServicesData"/>
	<bean:define name="nablifeServicesData" property="desc" id="srvListDesc"/>
	<bean:define name="nablifeServicesData" property="name" id="srvListName"/>
	<bean:define name="nablifeServicesData" property="srvType" id="srvListType"/>
	<script language="javascipt">
		var url = '<bean:write name="nablifeServicesData" property="link"/>';
		url = url.replace("&amp;", "&");
	</script>
	<h2><%=DicoTools.dico(dico_lang , "myNablife/tab_all_services")%></h2>
	<div class="flat-block">
		<div class="flat-block-top">
			<img class="srv-ico" src="../include_img/services/icones_services/icon_<%=srvListType.toString()%>.gif" alt="<%=DicoTools.dico_if(dico_lang,srvListName.toString())%>"/>
			<a href="#" onclick="javascript:srvConfigToggle('id_<bean:write name="nablifeServicesData" property="id"/>_2', url+'&dispatch=load&srvId=<bean:write name="nablifeServicesData" property="id"/>&searched=<bean:write name="searched" />');"><h3 style="margin-left: 34px;"><%=DicoTools.dico_if(dico_lang, srvListName.toString())%></h3></a>
		</div>
		<div class="flat-block-content">
			<div class="flat-block-content-inner">
				<logic:notEmpty name="nablifeServicesData" property="srvImg">
					<a href="#" onclick="javascript:srvConfigToggle('id_<bean:write name="nablifeServicesData" property="id"/>_2', url+'&dispatch=load&srvId=<bean:write name="nablifeServicesData" property="id"/>&searched=<bean:write name="searched" />');">
					<img border="0" src="<bean:write name="nablifeServicesData" property="srvImg"/>" align="left" style="margin-right: 10px; clear: both;"/>
					</a>
				</logic:notEmpty>
				<logic:notEmpty name="nablifeServicesData" property="desc">
					<%=DicoTools.dico_if(dico_lang , srvListDesc.toString())%>
				</logic:notEmpty>	
				<hr class="clearer"/>
			</div>
		</div>
	</div>
	<%-- ************************************************************************************************************* --%>
	
	<hr class="spacer" />
</logic:notEmpty>

<!-- Nabcast -->
<logic:notEmpty name="myNablifeForm" property="nabcastData">
	<bean:define id="nabcastData" name="myNablifeForm" property="nabcastData"/>
	
	<h2><%=DicoTools.dico(dico_lang , "myNablife/tab_nabaztaland")%></h2>
			
	<logic:iterate id="nabcastData" name="myNablifeForm" property="nabcastData">
		
		<div class="flat-block">
			<div class="flat-block-top">
				<img class="srv-ico" src="../include_img/services/icones_services/icon_nabcast.gif"/>
				<a href="../action/myNablife.do?idNabcast=<bean:write name="nabcastData" property="nabcast_id"/>&searched=<bean:write name="searched" />">
				<h3 style="margin-left: 34px;"><bean:write name="nabcastData" property="nabcast_titre"/></h3>
				</a>
			</div>
			<div class="flat-block-content">
				<div class="flat-block-content-inner">
					<b><%=DicoTools.dico(dico_lang, "srv_nabcast_histo/category")%></b> <bean:write name="nabcastData" property="nabcast_categ_name"/><br/>
					<b><%=DicoTools.dico(dico_lang, "srv_nabcast_result/nabcaster")%></b> <bean:write name="nabcastData" property="nabcast_author_pseudo"/><br/>		
					<b><%=DicoTools.dico(dico_lang, "srv_nabcast_home/description")%> :</b><br/>
					<bean:write name="nabcastData" property="nabcast_description"/>	
				</div>
			</div>
		</div>
		
		<hr class="clearer" />
	</logic:iterate>
			
	<hr class="spacer" />
</logic:notEmpty>

<!-- Utilisateur -->
<logic:notEmpty name="myNablifeForm" property="userData">
	<bean:define id="userData" name="myNablifeForm" property="userData"/>
	<!-- Pour le moment on ne fait rien -->
	
	<hr class="spacer" />
</logic:notEmpty>
			



