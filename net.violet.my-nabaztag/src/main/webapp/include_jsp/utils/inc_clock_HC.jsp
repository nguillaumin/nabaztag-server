<%@page import="net.violet.platform.web.include_jsp.utils.ClockHC"%>

<%
	String path = request.getParameter("path");
	String content = request.getParameter("content");
	int ttl = Integer.parseInt(request.getParameter("ttl"));
	int from = Integer.parseInt(request.getParameter("from"));
	int lenmax = Integer.parseInt(request.getParameter("lenmax"));
	boolean adp = Boolean.parseBoolean(request.getParameter("adp"));
	boolean chor = Boolean.parseBoolean(request.getParameter("chor"));

	if (path != null && !path.equals("") && content != null && !content.equals("")) {
		String display = ClockHC.clockHC(from, lenmax, adp, chor, path, content);
		out.println(display);
	}
%>