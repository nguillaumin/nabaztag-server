package org.apache.jsp.include_005fjsp.static_005fcontent.us;

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

      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>They're here at last!</h3>\r\n\t<a href=\"http://www.ztore.net\" target=\"_blank\"><img src=\"../include_img/ztamps.jpg\" alt=\"Treat your Nabaztag:tag to some Ztamp:s\" title=\"Treat your Nabaztag:tag to some Ztamp:s\" /></a>\r\n\t<p><strong>The RFID Stamps that enable your Nabaztag:tag to recognize your everyday Objects - and react when he senses are available!<br/><a href=\"http://www.ztore.net\">I want them! I want them!</a></strong></p>\r\n</div>\r\n");
      out.write("\r\n<div class=\"block-info\">\r\n    <h3>NEW : Make him speak with your voice</h3>\r\n<a href=\"myMessages.do\"><img src=\"../include_img/lapinmicro_grey-bg.gif\" alt=\"Send a voice message\" title=\"Send a voice message\" /></a>\r\n    <p>Up to now you dedicated songs or sent texts to your friends. You can now record your own voice and send it via Nabaztag.<br />\r\n  <a href=\"myMessages.do\">Send a voice message</a></p>\r\n</div>\r\n\r\n");
      out.write("\r\n<div class=\"block-info\">\r\n\t<h3>Nabaztag becomes your PA</h3>\r\n    <a href=\"http://www.nabaztag.com/partner/alinto/indexEN.html\" target=\"_blank\"><img src=\"../include_img/alinto.gif\" alt=\"Nabaztag becomes your PA\" title=\"Nabaztag becomes your PA\"/></a>\r\n    <p>Now nabaztag will remind  you all your important events.<br /><a href=\"http://www.nabaztag.com/partner/alinto/indexEN.html\" target=\"_blank\">Create your Nabazcal</a></p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Even prettier</h3>\r\n    <a href=\"http://www.ztore.net\" target=\"_blank\"><img src=\"../include_img/ears.gif\" /></a>\r\n  <p><strong>Personalize your Rabbit</strong>.Get him some ears that you like, dress him, undress him, combine different ears</p>\r\n<a class=\"simple-link\" href=\"http://www.ztore.net\" target=\"_blank\">All the ears</a></div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Guided Tour</h3>\r\n<a href=\"http://www.nabaztag.com/guidedtour/US/\" target=\"_blank\"><img src=\"../include_img/visuel_guidedtour.gif\" /></a>\r\n  <p>Your pretty white Nabaztag has just begun his new life with you. In the upcoming few pages, <a href=\"http://www.nabaztag.com/guidedtour/US/\" target=\"_blank\">we&rsquo;ll show you some of the possibilities of your new companion</a>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Nabcasts: Broadcast yourself</h3>\r\n  <p><strong>Nabcasts are shows for Rabbits</strong> created by Nabaztag owners on all types of subjects. Check out the service guide to find one and <strong>soon enough, you too will create your very own</strong>.</p>\r\n</div>\r\n\r\n");
      out.write("\r\n\r\n<div class=\"block-info\">\r\n\t<h3>Beyond Nablife</h3>\r\n<img src=\"../include_img/abo/nabazphere.gif\" />\r\n\t<p>Any  developer can <a href=\"http://api.nabaztag.com\" target=\"_blank\">create applications for Nabaztag</a>. Take a look at all the applications at <a href=\"http://www.nabzone.com\" target=\"_blank\">Nabzone</a>, for example.</p>\r\n\t</div>");
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
