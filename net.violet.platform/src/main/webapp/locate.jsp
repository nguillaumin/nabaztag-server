<%@ page import="net.violet.platform.web.LocateEntryPoint" %>
<%
	out.clearBuffer();
	final String trame = LocateEntryPoint.findConfigBySerial(request.getParameter("sn"), request.getParameter("h"), request.getParameter("v"), request.getRemoteAddr());
    out.print(trame);        
    	
%>
