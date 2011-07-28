package org.apache.jsp.include_005fjsp.static_005fcontent.br;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class nablife_home_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n\r\n<div class=\"block-info\">\r\n    <h3>Guia de Navegaçao</h3>\r\n    <a href=\"http://www.nabaztag.com/guidedtour/BR/\" target=\"_blank\"><img src=\"../include_img/visuel_guidedtour.gif\" /></a>\r\n    <p>Seu Nabaztag acaba de começar uma nova vida com voce. A seguir, em poucas páginas <a href=\"http://www.nabaztag.com/guidedtour/BR/\" target=\"_blank\">nós te mostraremos algumas \"habilidades\" de sua nova companhia</a>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n    <h3>Nabcasts: Transmita voce mesmo</h3>\r\n    <p><strong>Nabcasts sao tocados por Coelhos</strong>, criados por propietários de Nabaztags em diversas categorias. Cheque a guia de serviços para encontrar um e, <strong> em breve, voce criará o seu próprio Nabcast</strong>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n    <h3>O outro lado da Nabvida</h3>\r\n    <img src=\"../include_img/abo/nabazphere.gif\" />\r\n    <p>Qualquer desenvolvedor pode <a href=\"http://api.nabaztag.com\" target=\"_blank\">criar aplicaçoes para Nabaztag</a>. De uma olhada em todas as aplicaçoes da <a href=\"http://www.nabzone.com\" target=\"_blank\">Nabzone</a>, por exemplo.</p>\r\n</div>");
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
