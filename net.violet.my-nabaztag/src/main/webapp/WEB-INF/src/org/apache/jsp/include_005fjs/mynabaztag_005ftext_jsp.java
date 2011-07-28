package org.apache.jsp.include_005fjs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.DicoImpl;
import net.violet.platform.util.ConvertTools;
import java.util.List;
import java.sql.SQLException;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class mynabaztag_005ftext_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');

	String lang = request.getParameter("l");
	
	if (null == lang) 
		lang = ObjectLangData.DEFAULT_OBJECT_LANGUAGE.getLang_iso_code();

	final Lang theLang = Factories.LANG.findByIsoCode(lang);

	List<String> result = DicoImpl.listKey("js");

	String js = "";
	
	js = "var msg_txt = new Array;\n";
	
	for(String key : result){
		js += "msg_txt['"+key.substring(3)+"']	= \""+ ConvertTools.strreturn(ConvertTools.strbackslash(DicoTools.dico(theLang, key))) +"\";\n";
	}
	
	result = DicoImpl.listKey("mainTitle");
	
	js +="var gTitle = new Array();\n";
	
	//title['Quicknab']="toto";
	
	for(String key : result){
		js += "gTitle['"+key.substring(10)+"'] = \""+ ConvertTools.strreturn(ConvertTools.strbackslash(DicoTools.dico(theLang, key))) +"\";\n";
	}
	
	out.println(js);

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
