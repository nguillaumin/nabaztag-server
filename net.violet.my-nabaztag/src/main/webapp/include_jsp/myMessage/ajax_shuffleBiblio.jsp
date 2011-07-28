<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.dataobjects.MusicData" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>

<%
final MusicData theMusicData = MusicData.findRandom(SessionTools.getLangFromSession(session, request));
pageContext.setAttribute("musicData", theMusicData, PageContext.PAGE_SCOPE);
%>
selectItem("<bean:write  name="musicData" property="label"/>", <bean:write  name="musicData" property="id"/>);
