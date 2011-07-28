package org.apache.jsp.include_005fjsp.reponses;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class genericNoRabbitMessages_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\r\n\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\n\n");
	Lang dico_lang =	SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n\r\n\r\n\r\n<div class=\"intro-cadre-contener intro-cadre-simple service-introduction\">\r\n\t\r\n\t<div class=\"intro-cadre-top\">\r\n\t\t<h2>\r\n\t\t\t");
      out.print(DicoTools.dico(dico_lang, "generic/noRabbitMessages_title"));
      out.write("\r\n\t\t</h2>\r\n\t</div>\r\n\t\r\n\r\n\t<div class=\"intro-cadre-content\">\r\n\t\t\r\n\t\t\t<div class=\"image\">\r\n\t\t\t\t<img src=\"../include_img/layout/lapin-message-big.gif\"/>\t\t\r\n\t\t\t</div>\r\n\r\n\t\t\t<div class=\"description\">\r\n\t\t\t\t<div>\r\n\t\t\t\t\t");
      out.print(DicoTools.dico(dico_lang, "generic/noRabbitMessages"));
      out.write("\t\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t\r\n\t</div>\r\n\t\r\n</div>\r\n");
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
