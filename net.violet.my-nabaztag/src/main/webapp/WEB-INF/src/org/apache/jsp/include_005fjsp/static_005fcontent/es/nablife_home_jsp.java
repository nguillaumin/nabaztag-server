package org.apache.jsp.include_005fjsp.static_005fcontent.es;

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

      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>¡Al fin llegaron!</h3>\r\n\t<a href=\"http://www.ztore.net\" target=\"_blank\"><img src=\"../include_img/ztamps.jpg\" alt=\"Ofrece Ztamp:s a tu Nabaztag:tag\" title=\"Ofrece Ztamp:s a tu Nabaztag:tag\" /></a>\r\n\t<p><strong>¡Los Timbres RFID que permiten que tu Nabaztag:tag  reconozca tus Objetos comunes y que reaccione cuando sienta que están disponibles!<br/><a href=\"http://www.ztore.net\">¡Quiero tenerlos, quiero tenerlos!</a></strong></p>\r\n</div>\r\n");
      out.write("\r\n\r\n\r\n<div class=\"block-info\">\r\n    <h3>NUEVO : Hazle hablar con tu voz</h3>\r\n    <a href=\"myMessages.do\"><img src=\"../include_img/lapinmicro_grey-bg.gif\" alt=\"Enviar un mensaje oral\" title=\"Enviar un mensaje oral\" /></a>\r\n    <p>Env&iacute;as canciones o textos que has le&iacute;do a tus amigos y adem&aacute;s, ahora puedes tambi&eacute;n hablar en el micr&oacute;fono de tu ordenador y Nabaztag transmitir&aacute; tus palabras.<br /><a href=\"myMessages.do\">Enviar un mensaje oral</a></p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Cada dia mas guapo</h3>\r\n    <a href=\"http://www.ztore.net\" target=\"_blank\"><img src=\"../include_img/ears.gif\" /></a>\r\n  <p><strong>Personaliza tu Conejo, </strong>ofr&eacute;cele orejas que se parezcan a ti, vist&eacute;ele y desv&iacute;stele, combinando orejas diferentes.</p>\r\n\t<p><a class=\"simple-link\" href=\"http://www.ztore.net\" target=\"_blank\">Todas las orejas</a></p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Guided Tour</h3>\r\n<a href=\"http://www.nabaztag.com/guidedtour/ES/\" target=\"_blank\"><img src=\"../include_img/visuel_guidedtour.gif\" /></a>\r\n  <p>Tu simp&aacute;tico Nabaztag, todo blanco, comienza a ser parte de tu vida. En unas pocas p&aacute;ginas te proponemos  <a href=\"http://www.nabaztag.com/guidedtour/ES/\" target=\"_blank\">que te familiarices con las posibilidades que te ofrece tu nuevo compa&ntilde;ero</a>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Nabcasts: difunde sus contenidos</h3>\r\n  <p>Los <strong>Nabcasts son emisiones para los Conejos</strong>, que abordan m&uacute;ltiples temas y que han sido creadas para los que tienen un Nabaztag. Consulta la gu&iacute;a de servicios y <strong>dentro de poco t&uacute; crear&aacute;s el tuyo</strong>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Mas alla de Nablife</h3>\r\n<img src=\"../include_img/abo/nabazphere.gif\" />\r\n\t<p>Nabaztag permite a cualquier persona <a href=\"http://api.nabaztag.com\" target=\"_blank\">desarrollar sus propias aplicaciones</a>. Descubre por ejemplo todas aquellas que han sido desarrolladas en <a href=\"http://www.nabzone.com\" target=\"_blank\">Nabzone</a>.</p>\r\n</div>\r\n");
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
