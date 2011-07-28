package org.apache.jsp.include_005fjsp.myNablife;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class inc_005fnablifeMenu_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\r\n<table>\r\n\t<tr>\r\n\t\t<td>\r\n\t\t\t\r\n\t\t\t\r\n\t\t\t<div class=\"categ-chooser\">\r\n\t\t\t\t<select>\r\n\t\t\t\t\t<option class=\"choosetxt service\" value=\"\" >");
      out.print(DicoTools.dico(dico_lang, "myNablife/services_categories_choose"));
      out.write("</option>\t\t\r\n\t\t\t\t\t<optgroup label=\"");
      out.print(DicoTools.dico(dico_lang, "myNablife/services_categories"));
      out.write("\">\r\n\t\t\t\t\t\t<logic:iterate name=\"listeCategorie\" id=\"srvCategData\" offset=\"0\">\r\n\t\t\t\t\t\t\t<bean:define id=\"name\" name=\"srvCategData\" property=\"label\" />\r\n\t\t\t\t\t\t\t<bean:define id=\"id\" name=\"srvCategData\" property=\"id\" />\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t<option class=\"service\" value=\"myServicesHome.do?type=service&idCateg=");
      out.print(id);
      out.write("&mode=1&langCategorie=");
      out.print(langCategorie);
      out.write('"');
      out.write('>');
      out.print(DicoTools.dico_if(dico_lang, name.toString()));
      out.write("</option>\r\n\t\t\t\r\n\t\t\t\t\t\t</logic:iterate>\r\n\t\t\t\t\t</optgroup>\r\n\t\t\t\t\t\r\n\t\t\t\t\t<optgroup label=\"");
      out.print(DicoTools.dico(dico_lang, "myNablife/nabcasts_categories"));
      out.write("\">\r\n\t\t\t\t\t\t");
      out.write("\r\n\t\t\t\t\t\t<logic:iterate name=\"listeNabcastCateg\" id=\"nabcastCategData\" offset=\"0\">\r\n\t\t\t\t\t\t\t<bean:define id=\"name\" name=\"nabcastCategData\" property=\"nabcastcateg_val\" />\r\n\t\t\t\t\t\t\t<bean:define id=\"id\" name=\"nabcastCategData\" property=\"nabcastcateg_id\" />\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t<option class=\"nabcast\" value=\"myNabaztalandHome.do?type=nabcast&idCateg=");
      out.print(id);
      out.write("&mode=1&langCategorie=");
      out.print(langCategorie);
      out.write('"');
      out.write('>');
      out.print(name.toString());
      out.write("</option>\t\r\n\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t</logic:iterate>\t\t\t\t\t\r\n\t\t\t\t\t</optgroup>\r\n\t\t\t\t\t\r\n\t\t\t\t</select>\r\n\t\t\t</div>\t\r\n\t\t</td>\r\n\t\t<td>\r\n\t\t\t");
      out.write("\r\n\t\t\t<logic:greaterThan name=\"nbLangue\" value=\"1\">\r\n\t\t\t\t<ul class=\"langSelect\">\r\n\t\t\t\t\t");
      out.write("\n\t\t\t\t\t<logic:iterate name=\"listeLang\" id=\"langInList\">\n\t\t\t\t\t\t<bean:define name=\"langInList\" property=\"lang_id\" id=\"theLangId\"/>\r\n\t\t\t\t\t\t<bean:define name=\"langInList\" property=\"lang_iso_code\" id=\"lang_iso_code\"/>\n\t\t\t\t\t\t<li class=\"");
      out.print(lang_iso_code.toString() );
      out.write(" <logic:equal name=\"langCategorie\"  value=\"");
      out.print(theLangId.toString() );
      out.write("\">selected</logic:equal>\" ><a href=\"<html:rewrite forward=\"goServicesHome\"/>?langCategorie=");
      out.print(theLangId.toString() );
      out.write("\" ><span>");
      out.print(lang_iso_code.toString() );
      out.write("</span></a></li>\n\t\t\t\t\t</logic:iterate>\n\t\t\t\t</ul>\r\n\t\t\t</logic:greaterThan>\r\n\t\t</td>\r\n\t</tr>\r\n</table>\r\n\r\n\r\n<ul class=\"menulinks\">\r\n\t");
      out.write("\r\n\t<logic:notEqual name=\"user_main\" value=\"0\">\r\n\t\t<li>\r\n\t\t\t");
      out.print(DicoTools.dico(dico_lang, "myNablife/menuLink1"));
      out.write("\r\n\t\t</li>\r\n\t</logic:notEqual>\r\n\t<logic:notEqual name=\"user_id\" value=\"0\">\r\n\t\t<li>\r\n\t\t\t");
      out.print(DicoTools.dico(dico_lang, "myNablife/menuLink2"));
      out.write("\r\n\t\t</li>\r\n\t</logic:notEqual>\r\n\t<li>\r\n\t\t");
      out.print(DicoTools.dico(dico_lang, "myNablife/menuLink3"));
      out.write("\r\n\t</li>\r\n</ul>");
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
