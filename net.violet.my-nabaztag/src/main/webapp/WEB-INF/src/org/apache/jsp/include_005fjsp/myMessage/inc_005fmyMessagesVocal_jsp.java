package org.apache.jsp.include_005fjsp.myMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyMessagesVocal_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
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

      out.write("\n\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\n');
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myMessagesVocalForm");
      _jspx_th_bean_define_0.setProperty("langUser");
      _jspx_th_bean_define_0.setId("lang");
      _jspx_th_bean_define_0.setType("String");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      String lang = null;
      lang = (String) _jspx_page_context.findAttribute("lang");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("myMessagesVocalForm");
      _jspx_th_bean_define_1.setProperty("idMp3");
      _jspx_th_bean_define_1.setId("idMp3");
      _jspx_th_bean_define_1.setType("String");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      String idMp3 = null;
      idMp3 = (String) _jspx_page_context.findAttribute("idMp3");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setName("myMessagesVocalForm");
      _jspx_th_bean_define_2.setProperty("serverPath");
      _jspx_th_bean_define_2.setId("serverPath");
      _jspx_th_bean_define_2.setType("String");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      String serverPath = null;
      serverPath = (String) _jspx_page_context.findAttribute("serverPath");
      out.write("\r\n\n<div class=\"Column66\" id=\"flashcontent\"></div>\n\n<script type=\"text/javascript\">\n\tvar so = new SWFObject(\"../include_flash/audioControl.swf\", \"audiocontrol\", \"300\", \"150\", \"9\", \"#FFFFFF\");\n\tso.addParam(\"wmode\", \"transparent\");\n\tso.addVariable(\"myServer\",\"");
      out.print(serverPath);
      out.write("\");\n\tso.addVariable(\"mySound\",\"");
      out.print(idMp3);
      out.write("\");\n\tso.addVariable(\"message\",\"");
      out.print(DicoTools.dico(dico_lang, "myMessagesVocal/warning"));
      out.write("\");\n\tif (!so.write(\"flashcontent\")) { // flash player version detection failed > force creation\n\t\tvar container = document.getElementById(\"flashcontent\");\n\t\tif (container) container.innerHTML = so.getSWFHTML();\n\t}\n</script>\n\n<div class=\"Column33\">\n\t");
      out.print(DicoTools.dico(dico_lang, "myMessagesVocal/explain"));
      out.write("\n</div>\n<hr class=\"spacer\" />\n\n<input name=\"idMp3\" type=\"hidden\" id=\"idMp3\" value=\"");
      out.print(idMp3);
      out.write("\">\n\n<label>");
      out.print(DicoTools.dico(dico_lang, "myMessagesVocal/comment"));
      out.write(" : </label><input name=\"musicName\" type=\"text\" id=\"musicName\" size=\"34\"><br/>\n\n<small>");
      out.print(DicoTools.dico(dico_lang, "myMessagesVocal/rmq"));
      out.write("</small>\r\n");
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
