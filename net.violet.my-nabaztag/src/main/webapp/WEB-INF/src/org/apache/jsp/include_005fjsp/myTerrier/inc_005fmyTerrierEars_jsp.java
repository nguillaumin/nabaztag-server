package org.apache.jsp.include_005fjsp.myTerrier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyTerrierEars_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\n\n");
	Lang dico_lang =	SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n<table cellpadding=\"0\" cellspacing=\"0\" class=\"content\">\r\n  <tr>\r\n  \t<td class=\"title\"><h2>... et ses oreilles </h2></td>\r\n    <td valign=\"top\" >\r\n    \r\n    \t<div id=\"hautOreilles\">\r\n    \t\t<p>\r\n\t\t\t\tDécouvrez et jouez avec les oreilles de Nabaztag,<br />\r\n\t\t\t\tUnies ou dépareillées, à vous de voir comment vous préférez personnaliser votre Nabaztag<br />\r\n\t\t\t\tEssayez-les en cliquant dessus, ou bien laissez faire le hasard ! \r\n    \t\t</p>\r\n    \t</div>\r\n    \t\r\n    \t<div id=\"gaucheOreilles\">\r\n \t\t\t<a href=\"javascript:changeOreilleGauche('jaune','Jaune')\"><img src=\"../include_img/terrier/oreilles/jaune.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('argent','Argent')\"><img src=\"../include_img/terrier/oreilles/argent.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('or','Or')\"><img src=\"../include_img/terrier/oreilles/or.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('croise','Etoiles Argent')\"><img src=\"../include_img/terrier/oreilles/etoiles.gif\" height=\"88\" width=\"27\" /></a>\r\n");
      out.write("\t\t\t<a href=\"javascript:changeOreilleGauche('rayeejaune','Spirale Jaune')\"><img src=\"../include_img/terrier/oreilles/rjaune.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('rayeenoire','Spirale Noire')\"><img src=\"../include_img/terrier/oreilles/rnoire.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('blanc','Blanc')\"><img src=\"../include_img/terrier/oreilles/blanc.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('rouille','Corail')\"><img src=\"../include_img/terrier/oreilles/orange.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('rayeebleue','Rayures Bleues')\"><img src=\"../include_img/terrier/oreilles/rbleues.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('poids','Pois Rouge')\"><img src=\"../include_img/terrier/oreilles/poidsRouge.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('cyan','Vert d\\'eau')\"><img src=\"../include_img/terrier/oreilles/cyan.gif\" height=\"88\" width=\"27\" /></a>\r\n");
      out.write("\t\t\t<a href=\"javascript:changeOreilleGauche('magenta','Fuchsia')\"><img src=\"../include_img/terrier/oreilles/magenta.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('rose','Rose')\"><img src=\"../include_img/terrier/oreilles/rose.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleGauche('anis','Vert Anis')\"><img src=\"../include_img/terrier/oreilles/anis.gif\" height=\"88\" width=\"27\" /></a>\r\n    \t</div>\r\n    \t\r\n    \t<div id=\"milieuOreilles\">\r\n   \t\t\t<span class=\"corpsLapin\">\r\n    \t\t\t<img src=\"../include_img/terrier/oreilles/gauche_blanc.gif\" height=\"117\" width=\"28\" name=\"og\" id=\"og\" />\r\n    \t\t\t<img src=\"../include_img/terrier/oreilles/crane.gif\" height=\"38\" width=\"37\" />\r\n    \t\t\t<img src=\"../include_img/terrier/oreilles/droite_blanc.gif\" height=\"117\" width=\"28\" name=\"od\" id=\"od\" /><br />\r\n    \t\t\t<img class=\"corps\" src=\"../include_img/terrier/oreilles/corps.gif\" height=\"156\" width=\"145\" />   \t\t  \r\n    \t\t</span><br />\r\n    \t\t<span id=\"oreilleGaucheLib\" class=\"textBlancG\">Blanc</span>\r\n");
      out.write("    \t\t<span id=\"oreilleDroiteLib\" class=\"textBlancD\">Blanc</span><br />\r\n    \t\t<span class=\"validButton\"> <input type=\"button\" onClick=\"putImage()\" value=\"Essayer au hasard\" /></span>\r\n    \t</div>\r\n    \t\r\n    \t<div id=\"droiteOreilles\">\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('jaune','Jaune')\"><img src=\"../include_img/terrier/oreilles/jaune.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('argent','Argent')\"><img src=\"../include_img/terrier/oreilles/argent.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('or','Or')\"><img src=\"../include_img/terrier/oreilles/or.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('croise','Etoiles Argent')\"><img src=\"../include_img/terrier/oreilles/etoiles.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('rayeejaune','Spirale Jaune')\"><img src=\"../include_img/terrier/oreilles/rjaune.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('rayeenoire','Spirale Noire')\"><img src=\"../include_img/terrier/oreilles/rnoire.gif\" height=\"88\" width=\"27\" /></a>\r\n");
      out.write("\t\t\t<a href=\"javascript:changeOreilleDroite('blanc','Blanc')\"><img src=\"../include_img/terrier/oreilles/blanc.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('rouille','Corail')\"><img src=\"../include_img/terrier/oreilles/orange.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('rayeebleue','Rayures Bleues')\"><img src=\"../include_img/terrier/oreilles/rbleues.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('poids','Pois Rouge')\"><img src=\"../include_img/terrier/oreilles/poidsRouge.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('cyan','Vert d\\'eau')\"><img src=\"../include_img/terrier/oreilles/cyan.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('magenta','Fuchsia')\"><img src=\"../include_img/terrier/oreilles/magenta.gif\" height=\"88\" width=\"27\" /></a>\r\n\t\t\t<a href=\"javascript:changeOreilleDroite('rose','Rose')\"><img src=\"../include_img/terrier/oreilles/rose.gif\" height=\"88\" width=\"27\" /></a>\r\n");
      out.write("\t\t\t<a href=\"javascript:changeOreilleDroite('anis','Vert Anis')\"><img src=\"../include_img/terrier/oreilles/anis.gif\" height=\"88\" width=\"27\" /></a>\r\n    \t</div>\r\n\t</td>\r\n  </tr>\r\n</table>\r\n\r\n");
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
