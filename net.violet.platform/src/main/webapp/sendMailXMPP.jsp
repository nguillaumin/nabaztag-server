<%@page import="net.violet.platform.util.Constantes"%>
<%@page import="net.violet.platform.util.Templates"%>
<%
if(null != request.getParameter("m") && null != request.getParameter("d") && null != request.getParameter("r") && 1 == Constantes.SEND_MAIL_ALERT_XMPP) {
	Templates.sendMailXMPP(request, request.getParameter("m"), request.getParameter("d"), request.getParameter("r"));
}
%>