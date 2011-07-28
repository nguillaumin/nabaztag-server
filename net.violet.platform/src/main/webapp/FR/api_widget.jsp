<%@page import="net.violet.platform.util.Api"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
out.clear();
out.println(new Api(Api.API_WIDGET).action(request));
out.flush();
%>