<%@page import="net.violet.platform.util.Api"%>
<%@page import="net.violet.platform.datamodel.Hardware.HARDWARE"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
out.clear();
if (HARDWARE.V2.checkIdentifier(request.getParameter("sn")) || HARDWARE.MIRROR.checkIdentifier(request.getParameter("sn"))) {
	out.println(new Api(Api.API_STREAM).action(request));
}else{
	out.println(Api.MESSAGE_ERROR_SN);
}
out.flush();
%>


