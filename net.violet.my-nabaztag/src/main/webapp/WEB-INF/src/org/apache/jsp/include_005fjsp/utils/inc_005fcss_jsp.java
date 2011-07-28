package org.apache.jsp.include_005fjsp.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.datamodel.User;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fcss_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\t<link href=\"../include_css/localImg.");
      out.print(dico_lang.getId());
      out.write(".css?v=1\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\r\n");

		// en local et sur DEV, on utilise les css tel quel
		if (MyConstantes.DOMAIN.equals("localhost") || MyConstantes.DOMAIN.equals("192.168.1.11")) {
	
      out.write('\r');
      out.write('\n');
      out.write('	');
      out.write("\r\n\t<link href=\"../include_css/modules/thickbox.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/modules/modal.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/modules/widget.nabthem.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/modules/jquery.tooltip.css\" rel=\"stylesheet\" type=\"text/css\" />\t\r\n\r\n\r\n\t");
      out.write("\r\n\t<link href=\"../include_css/layout/images.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/layout/blocks.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/layout/basic.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/layout/blocks_layout.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/layout/main-layout.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\r\n\t");
      out.write("\r\n\t<link href=\"../include_css/part_layout/nabcasts.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/part_layout/home.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/part_layout/nablife.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/part_layout/messages.css\" rel=\"stylesheet\" type=\"text/css\" />\t\r\n\t<link href=\"../include_css/part_layout/terrier.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\r\n\t");
      out.write("\r\n\r\n\t<link title=\"layout_green\" href=\"../include_css/layout_green/layout.css?v=1.3\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_orange\" href=\"../include_css/layout_orange/layout.css?v=1.3\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_blue\" href=\"../include_css/layout_blue/layout.css?v=1.3\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_violet\" href=\"../include_css/layout_violet/layout.css?v=1.3\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_pink\" href=\"../include_css/layout_pink/layout.css?v=1.3\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\r\n\r\n");

	// sinon on utilise les versions "minimized" ( necessite un ANT, target -> cssy_nabaztag )
	} else {

      out.write("\r\n\r\n\t<link href=\"../include_css/dist/nabaztag.modules.css?v=1.1\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/dist/nabaztag.layout.css?v=1.1.1\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/dist/nabaztag.parts.css?v=1.1\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\r\n\t<link title=\"layout_green\" \thref=\"../include_css/dist/nabaztag.layout_green.css?v=1.1\" \trel=\"stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_orange\" href=\"../include_css/dist/nabaztag.layout_orange.css?v=1.1\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_blue\" \thref=\"../include_css/dist/nabaztag.layout_blue.css?v=1.1\" \trel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_violet\" href=\"../include_css/dist/nabaztag.layout_violet.css?v=1.1\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_pink\" \thref=\"../include_css/dist/nabaztag.layout_pink.css?v=1.1\" \trel=\"alternate stylesheet\" type=\"text/css\" />\t\r\n\r\n");
 } 
      out.write("\r\n<!--[if IE 6]>\r\n\t<link href=\"../include_css/ieHack.css?v=1.2\" rel=\"stylesheet\" type=\"text/css\" />\r\n<![endif]-->\r\n\r\n<!--[if IE 7]>\r\n\t<link href=\"../include_css/ie7Hack.css?v=1.2\" rel=\"stylesheet\" type=\"text/css\" />\r\n<![endif]-->\r\n");
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
