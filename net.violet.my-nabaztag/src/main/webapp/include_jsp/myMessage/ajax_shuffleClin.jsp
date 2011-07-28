<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<%@ page import="net.violet.platform.dataobjects.MusicData" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>
<%@page import="net.violet.platform.datamodel.factories.Factories"%>

<%
// On récupère la langue (paramètre langClin).
// TODO: dégager ça dans SessionTools.
Lang theLang = null;
final String theLangId = request.getParameter("langClin");
if (theLangId != null) {
	try {
		final long theLangVal = Long.parseLong(theLangId);
		theLang = Factories.LANG.find(theLangVal);
	} catch (NumberFormatException anException) {
		// paramètre foireux.
	}
}

if (theLang == null) {
	theLang = SessionTools.getLangFromSession(session, request);
}
final MusicData theMusicData = MusicData.findRandomClin(theLang);
pageContext.setAttribute("musicData", theMusicData, PageContext.PAGE_SCOPE);
%>
selectItem("<bean:write  name="musicData" property="label"/>", <bean:write  name="musicData" property="id"/>);
