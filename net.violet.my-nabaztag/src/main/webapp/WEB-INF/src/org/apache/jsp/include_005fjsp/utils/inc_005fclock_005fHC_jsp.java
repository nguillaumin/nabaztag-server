package org.apache.jsp.include_005fjsp.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.web.include_jsp.utils.ClockHC;

public final class inc_005fclock_005fHC_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n\r\n");

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

    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
