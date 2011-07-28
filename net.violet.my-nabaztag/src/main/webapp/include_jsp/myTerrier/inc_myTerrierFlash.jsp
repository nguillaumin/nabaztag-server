<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	String dico_lang =	Long.toString(SessionTools.getLangFromSession(session, request).getId());%>

<bean:define name="myTerrierFlashForm" property="langUser" id="lang" type="String"/>
 
 <%-- Animation Flash --%>
<bean:define id="cdll" name="carteVisiteForm" property="cdll"/>
<logic:notEmpty name="cdll">
	<IFRAME SRC="../../vl/FR/flashsignature.jsp?cdll=<%=cdll%>" NAME="flashsignature" HEIGHT="390" WIDTH="300" FRAMEBORDER="0" align="middle"></IFRAME>
</logic:notEmpty>

<logic:empty name="cdll">
<bean:define id="music" name="carteVisiteForm" property="userMusic"/>
<IFRAME SRC="../../vl/FR/flashsignature.jsp?cdll=&music=<%=music%>" NAME="flashsignature" HEIGHT="390" WIDTH="300" FRAMEBORDER="0" align="middle"></IFRAME>
</logic:empty> 
