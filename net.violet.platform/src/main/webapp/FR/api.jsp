<%@page import="net.violet.platform.util.Api"%>
<%@page import="net.violet.platform.dataobjects.ObjectType"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
out.clear();
if (ObjectType.NABAZTAG.isValidSerial(request.getParameter("sn")) || ObjectType.MIRROR.isValidSerial(request.getParameter("sn"))) {
	out.println(new Api(Api.API_GENERAL).action(request));
}else{
	out.println(Api.MESSAGE_ERROR_SN);
}
out.flush();
%>