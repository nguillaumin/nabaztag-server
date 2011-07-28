package org.apache.jsp.include_005fjsp.static_005fcontent.de;

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

      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Jetzt sind die da!</h3>\r\n\t<a href=\"http://www.ztore.net\" target=\"_blank\"><img src=\"../include_img/ztamps.jpg\" alt=\"Verwöhnen Sie Ihren Nabaztag :tag  mit Ztamp:s\" title=\"Verwöhnen Sie Ihren Nabaztag :tag  mit Ztamp:s\" /></a>\r\n\t<p><strong>Die RFID-Marken, mit denen Ihr Nabaztag:tag Ihre Alltagsgegenstände erkennt  und auf sie reagiert, sind erhältlich!<br/><a href=\"http://www.ztore.net\">Ja, die will ich haben !</a></strong></p>\r\n</div>\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n    <h3>Nabaztag wird Ihr pers&ouml;nlicher Assistent</h3>\r\n    <a href=\"http://www.nabaztag.com/partner/alinto/indexDE.html\" target=\"_blank\"><img src=\"../include_img/alinto.gif\" alt=\"Nabaztag wird Ihr pers&ouml;nlicher Assistent\" title=\"Nabaztag wird Ihr pers&ouml;nlicher Assistent\"/></a>\r\n    <p>Ab jetzt verwaltet Nabaztag Ihre wichtigen Termine und erinnert Sie an Ihre Verabredungen.<br />\r\n    <a href=\"http://www.nabaztag.com/partner/alinto/indexDE.html\" target=\"_blank\">Richten Sie Ihren Nabazcal hier ein</a></p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Always more beautiful</h3>\r\n    <a href=\"http://www.ztore.net\" target=\"_blank\"><img src=\"../include_img/ears.gif\" /></a>\r\n  <p><strong>Personalisieren Sie Ihren Hasen.</strong> Statten Sie ihn mit einem Satz neuer Ohren aus, ziehen Sie ihn an und aus, kombinieren Sie verschiedene Ohren.</p>\r\n\t<a class=\"simple-link\" href=\"http://www.ztore.net\" target=\"_blank\">Alle Ohren</a></div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Guided Tour</h3>\r\n\t<a href=\"http://www.nabaztag.com/guidedtour/DE/\" target=\"_blank\"><img src=\"../include_img/visuel_guidedtour.gif\" alt=\"Apprivoiser mon Lapin\" title=\"Apprivoiser mon Lapin\" /></a>\r\n  <p>Ihr bildh&uuml;bscher Nabaztag in Schneewei&szlig; ist jetzt Ihr st&auml;ndiger Begleiter. Machen Sie sich <a href=\"http://www.nabaztag.com/guidedtour/DE/\" target=\"_blank\">hier mit den M&ouml;glichkeiten Ihres neuen Gef&auml;hrten vertraut</a>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Nabcasts : Erstellen Sie Ihre eigenen Inhalte</h3>\r\n  <p><strong>Nabcasts sind</strong> von Nabaztag-Besitzern erstellte <strong>Sendungen f&uuml;r den Hasen</strong> zu den verschiedensten und vielf&auml;ltigsten Themengebieten. Besuchen Sie das Dienste-Verzeichnis und <strong>erstellen Sie schon bald Ihren eigenen Nabcast</strong>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>More than Nablife</h3>\r\n<img src=\"../include_img/abo/nabazphere.gif\" />\r\n\t<p>Jeder kann <a href=\"http://api.nabaztag.com\" target=\"_blank\">seine eigenen Anwendungen f&uuml;r den Nabaztag programmieren</a>. In der <a href=\"http://www.nabzone.com\" target=\"_blank\">Nabzone</a> finden Sie eine breit gef&auml;cherte Auswahl selbst kreierter Anwendungen.</p>\r\n\t</div>\r\n");
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
