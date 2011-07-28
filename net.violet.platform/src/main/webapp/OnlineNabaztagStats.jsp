<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.admin.stats.StatsGenerator" %>

<%
out.clear();
out.println(StatsGenerator.getNabaztagOnline(request.getParameter("key")));
out.flush();
%>