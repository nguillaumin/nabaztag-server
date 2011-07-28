package org.apache.jsp.include_005fjsp.static_005fcontent.fr;

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

      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Ils sont enfin là !</h3>\r\n\t<a href=\"http://www.ztore.net\" target=\"_blank\"><img src=\"../include_img/ztamps.jpg\" alt=\"Offrez des Ztamp:s à votre Nabaztag:tag\" title=\"Offrez des Ztamp:s à votre Nabaztag:tag\" /></a>\r\n\t<p><strong>Les Timbres RFID qui permettent à votre Nabaztag:tag de reconnaître vos Objets ordinaires et de réagir quand il les sent sont disponibles !<br/><a href=\"http://www.ztore.net\">Je les veux, je les veux !</a></strong></p>\r\n</div>\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n    <h3>Quand le Lapin rencontre le Poussin</h3>\r\n<a href=\"http://nabaztag.goojet.com\" target=\"_blank\"><img src=\"../include_img/logo_goojet.gif\" alt=\"Goojet - Nabaztag : Le meilleur des deux mondes\" title=\"Goojet - Nabaztag : Le meilleur des deux mondes\" /></a>\r\n    <p>Retrouvez le widget Nabaztag dans votre espace Goojet et vous pourrez de n'importe où envoyer des messages à votre Nabaztag.<br /><a href=\"http://nabaztag.goojet.com\" target=\"_blank\">Découvrir le service</a></p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n    <h3>Pilotez Nabaztag depuis votre smartphone BlackBerry&reg;</h3>\r\n<a href=\"http://www.nabaztag.com/partner/blackberry/indexFR.html\" target=\"_blank\"><img src=\"../include_img/bberry.gif\" alt=\"En savoir plus sur B-Ztag\" title=\"En savoir plus sur B-Ztag\" /></a>\r\n    <p>Installez l'application B-Ztag sur votre smartphone BlackBerry&reg; et animez votre Lapin &agrave; distance.<br /><a href=\"http://www.nabaztag.com/partner/blackberry/indexFR.html\">En savoir plus sur B-Ztag</a></p>\r\n</div>\r\n\r\n");
      out.write("\r\n<div class=\"block-info\">\r\n\t<h3>Nabaztag se fait assistant personnel</h3>\r\n    <a href=\"http://www.nabaztag.com/partner/alinto/indexFR.html\" target=\"_blank\"><img src=\"../include_img/alinto.gif\" alt=\"Nabaztag devient assistant personnel\" title=\"Nabaztag devient assistant personnel\"/></a>\r\n    <p>Maintenant Nabaztag vous rappelle aussi les événements importants de votre quotidien.<br /><a href=\"http://www.nabaztag.com/partner/alinto/indexFR.html\" target=\"_blank\">Cr&eacute;ez votre Nabazcal</a></p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Visite guid&eacute;e</h3>\r\n\t<a href=\"http://www.nabaztag.com/guidedtour/FR/\" target=\"_blank\"><img src=\"../include_img/visuel_guidedtour.gif\" alt=\"Apprivoiser mon Lapin\" title=\"Apprivoiser mon Lapin\" /></a>\r\n    <p>Votre joli Nabaztag tout blanc vient d'entrer dans votre vie. Nous vous proposons en quelques pages de <a href=\"http://www.nabaztag.com/guidedtour/FR/\" target=\"_blank\">vous familiariser avec les possibilit&eacute;s de votre nouveau compagnon</a>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Au-del&agrave; de Nablife</h3>\r\n<img src=\"../include_img/abo/nabazphere.gif\" />\r\n\t<p>Nabaztag permet &agrave;  n'importe quel d&eacute;veloppeur de <a href=\"http://api.nabaztag.com\" target=\"_blank\">cr&eacute;er ses propres applications</a>. D&eacute;couvrez par exemple toutes celles qui ont &eacute;t&eacute; d&eacute;velopp&eacute;es sur <a href=\"http://www.nabzone.com\" target=\"_blank\">Nabzone</a>.</p>\r\n</div>");
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
