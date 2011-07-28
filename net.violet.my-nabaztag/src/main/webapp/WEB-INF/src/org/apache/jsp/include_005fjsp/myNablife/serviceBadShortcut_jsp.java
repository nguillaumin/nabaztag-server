package org.apache.jsp.include_005fjsp.myNablife;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class serviceBadShortcut_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\r\n\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write('\n');
      out.write("\r\n\r\n");
      out.print(DicoTools.dico(dico_lang , "partner/bad_shortcut" ));
      out.write("\r\n\r\n<div class=\"intro-cadre-contener\">\r\n\t\r\n\t<div class=\"intro-cadre-top\" style=\"position:relative;\">\r\n\t\t<h2>\r\n\t\t\t");
      out.print(DicoTools.dico(dico_lang , "partner/partner_discover" ));
      out.write("\r\n\t\t</h2>\r\n\t</div>\r\n\t\r\n</div>\r\n\t\r\n<div class=\"threeCol-left\">\r\n\t<div class=\"flat-block\">\r\n\t\t<div class=\"flat-block-top\">\r\n\t\t  <h3 class=\"no-icone\">");
      out.print(DicoTools.dico(dico_lang , "partner/partner_left_title"));
      out.write("</h3>\r\n\t\t</div>\r\n\t\r\n\t\t<div class=\"flat-block-content\">\r\n\t\t\t<div class=\"flat-block-content-inner\" >\r\n\t\t\t\t");
      out.print(DicoTools.dico(dico_lang , "partner/partner_left_text"));
      out.write("\r\n\t\t\t\t<hr class=\"clearer\" />\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n\r\n<div class=\"threeCol-middle\">\r\n\t<div class=\"flat-block\">\r\n\t\t<div class=\"flat-block-top\">\r\n\t\t  <h3 class=\"no-icone\">");
      out.print(DicoTools.dico(dico_lang , "partner/partner_middle_title"));
      out.write("</h3>\r\n\t\t</div>\r\n\t\r\n\t\t<div class=\"flat-block-content\">\r\n\t\t\t<div class=\"flat-block-content-inner\" >\r\n\t\t\t\t");
      out.print(DicoTools.dico(dico_lang , "partner/partner_middle_text"));
      out.write("\r\n\t\t\t\t<hr class=\"clearer\" />\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n\r\n<div class=\"threeCol-right\">\r\n\t<div class=\"flat-block\">\r\n\t\t<div class=\"flat-block-top\">\r\n\t\t  <h3 class=\"no-icone\">");
      out.print(DicoTools.dico(dico_lang , "partner/partner_right_title"));
      out.write("</h3>\r\n\t\t</div>\r\n\t\r\n\t\t<div class=\"flat-block-content\">\r\n\t\t\t<div class=\"flat-block-content-inner\" >\r\n\t\t\t\t");
      out.print(DicoTools.dico(dico_lang , "partner/partner_right_text"));
      out.write("\r\n\t\t\t\t<hr class=\"clearer\" />\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t</div>\r\n</div>\t");
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
