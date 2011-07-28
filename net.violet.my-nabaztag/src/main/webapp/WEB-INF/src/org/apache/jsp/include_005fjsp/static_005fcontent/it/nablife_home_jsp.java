package org.apache.jsp.include_005fjsp.static_005fcontent.it;

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

      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Finalmente sono arrivati!</h3>\r\n\t<a href=\"http://www.ztore.net\" target=\"_blank\"><img src=\"../include_img/ztamps.jpg\" alt=\"Regala degli Ztamp:s al tuo Nabaztag:tag\" title=\"Regala degli Ztamp:s al tuo Nabaztag:tag\" /></a>\r\n\t<p><strong>I Francobolli RFID che permettono al tuo Nabaztag:tag di riconoscere gli oggetti quotidiani e di reagire quando li annusa, sono ora disponibli!<br/><a href=\"http://www.ztore.net\">Li voglio, li voglio!</a></strong></p>\r\n</div>\r\n");
      out.write("\r\n<div class=\"block-info\">\r\n\t<h3>Nabaztag diventa personal assistant</h3>\r\n    <a href=\"http://www.nabaztag.com/partner/alinto/indexIT.html\" target=\"_blank\"><img src=\"../include_img/alinto.gif\" alt=\"Nabaztag diventa personal assistant\" title=\"Nabaztag diventa personal assistant\"/></a>\r\n    <p>Da adesso Nabaztag ti ricorder&agrave; anche gli appuntamenti importanti di tutti i giorni.<br /><a href=\"http://www.nabaztag.com/partner/alinto/indexIT.html\" target=\"_blank\">Crea il tuo Nabazcal</a></p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Sempre pi&ugrave; bello</h3>\r\n    <a href=\"http://www.ztore.net\" target=\"_blank\"><img src=\"../include_img/ears.gif\" /></a>\r\n  <p><strong>Personalizza il tuo Coniglio</strong>, regalagli delle orecchie che ti assomigliano, vestilo e svestilo, abbinagli orecchie diverse.</p>\r\n    <a class=\"simple-link\" href=\"http://www.ztore.net\" target=\"_blank\">Tutte le orecchie</a></div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Visita guidata</h3>\r\n\t<a href=\"http://www.nabaztag.com/guidedtour/IT/\" target=\"_blank\"><img src=\"../include_img/visuel_guidedtour.gif\"/></a>\r\n  <p>Il tuo bellissimo Nabaztag bianco &eacute; appena entrato nella tua vita. Ti proponiamo poche brevi pagine per <a href=\"http://www.nabaztag.com/guidedtour/IT/\" target=\"_blank\">familiarizzarti con le possibilit&agrave; del tuo nuovo comagno</a>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Nabcasts: diffondi i tuoi contenuti</h3>\r\n    <p>I <strong>Nabcasts sono delle emissioni per Conigli </strong> create dai padroni di Nabaztag su qualsiasi tipo di argomento. Consulta l'annuario dei servizi e  <strong>ben presto potrai anche tu crearne dei nuovi</strong>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n<div class=\"block-info\">\r\n\t<h3>Al di l&agrave; della Nablife</h3>\r\n<img src=\"../include_img/abo/nabazphere.gif\" />\r\n\t<p>Nabaztag permette a qualsiasi sviluppatore di <a href=\"http://api.nabaztag.com\" target=\"_blank\">creare le sue applicazioni</a>. Scopri, ad esempio, su <a href=\"http://www.nabzone.com\" target=\"_blank\">Nabzone</a> tutte quelle che sono gi&agrave; state create.</p>\r\n\t</div>\r\n");
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
