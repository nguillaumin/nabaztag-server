<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	String dico_lang =	Long.toString(SessionTools.getLangFromSession(session, request).getId());%>

<bean:define name="myNabcastForm" property="langUser" id="lang" type="String"/>

<ul>
<logic:iterate name="myNabcastForm" property="listNabcastSubscribe" id="NabcastData" >
	<li>
	<bean:define id="nabcast_titre" name="NabcastData" property="nabcast_titre" />
	<bean:define id="nabcast_categ" name="NabcastData" property="nabcast_categ" />
	<bean:define id="nabcast_id" name="NabcastData" property="nabcast_id" />
	<bean:define id="nabcast_hours" name="NabcastData" property="nabcast_hours" />
	<bean:define id="nabcast_minutes" name="NabcastData" property="nabcast_minutes" />
	<logic:equal name="NabcastData" property="nabcast_hours" value="-1">
		<%=nabcast_titre%> :<%=nabcast_categ%>
		<!--aucun-->
	</logic:equal>
	<logic:notEqual name="NabcastData" property="nabcast_hours" value="-1">
		<%=nabcast_titre%> :<%=nabcast_categ%> <%=nabcast_hours%>:
		<logic:greaterThan name="NabcastData" property="nabcast_minutes" value="9">
			<%=nabcast_minutes%>
		</logic:greaterThan>
		<logic:lessThan name="NabcastData" property="nabcast_minutes" value="10">
			0<%=nabcast_minutes%>
		</logic:lessThan>
	</logic:notEqual>
	
	<a href="<html:rewrite forward='goNabcast'/>?idNabcast=<%=nabcast_id%>&action=displayHisto">histo</a>
	<a href="<html:rewrite forward='goNabcast'/>?idNabcast=<%=nabcast_id%>&action=subscribe">modif heure</a>
	<a href="<html:rewrite forward='goNabcast'/>?idNabcast=<%=nabcast_id%>&action=unsubscribe">d&eacute;sabonner</a>
</logic:iterate>
</ul>


<ul>              
<logic:iterate name="myNabcastForm" property="listNabcastCreate" id="NabcastData" >
	<li>
	<bean:define id="nabcast_titre" name="NabcastData" property="nabcast_titre" />
	<bean:define id="nabcast_categ" name="NabcastData" property="nabcast_categ" />
	<bean:define id="nabcast_description" name="NabcastData" property="nabcast_description" />
	<bean:define id="nabcast_nbr_abonne" name="NabcastData" property="nabcast_nbr_abonne" />
	<bean:define id="nabcast_id" name="NabcastData" property="nabcast_id" />

	<a href="<html:rewrite forward='goNabcast'/>?idNabcast=<%=nabcast_id%>&action=editNabcast"><strong><%=nabcast_titre%></strong></a>
	desc : <%=nabcast_description%><br>
	categ : <%=nabcast_categ%><br>
	nb abonnes : <%=nabcast_nbr_abonne%> abonnes<br>

	<a href="<html:rewrite forward='goNabcast'/>?idNabcast=<%=nabcast_id%>&action=ajoutNabcast"></a>
	<a href="<html:rewrite forward='goNabcast'/>?idNabcast=<%=nabcast_id%>&action=editNabcast"></a>
	<a href="<html:rewrite forward='goNabcast'/>?idNabcast=<%=nabcast_id%>&action=editNabcastSignok&mode=1"></a>
	<a href="<html:rewrite forward='goNabcast'/>?idNabcast=<%=nabcast_id%>&action=delNabcast" onclick="if(window.confirm('confirmation')){return true;}else{return false;}" ></a>
</logic:iterate>		
</ul>
