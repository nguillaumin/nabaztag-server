package org.apache.jsp.include_005fjsp.myServices;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class genericService_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_name_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_name_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_logic_notEmpty_name_name.release();
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

      out.write("\r\n\r\n\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n<!-- Title -->\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setProperty("title");
      _jspx_th_bean_define_0.setName("myServiceConfigForm");
      _jspx_th_bean_define_0.setId("title");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.Object title = null;
      title = (java.lang.Object) _jspx_page_context.findAttribute("title");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setProperty("image");
      _jspx_th_bean_define_1.setName("myServiceConfigForm");
      _jspx_th_bean_define_1.setId("image");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object image = null;
      image = (java.lang.Object) _jspx_page_context.findAttribute("image");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setProperty("introduction");
      _jspx_th_bean_define_2.setName("myServiceConfigForm");
      _jspx_th_bean_define_2.setId("introduction");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      java.lang.Object introduction = null;
      introduction = (java.lang.Object) _jspx_page_context.findAttribute("introduction");
      out.write("\r\n\r\n<div class=\"intro-cadre-contener intro-cadre-simple service-introduction\">\r\n\t<div class=\"intro-cadre-top\">\r\n\t\t<h2>");
      out.print(title);
      out.write("</h2>\r\n\t</div>\r\n\t\r\n\t<div class=\"intro-cadre-content\">\r\n\t\t\t<div class=\"image\"><img src=\"");
      out.print(image);
      out.write("\" /></div>\r\n\r\n\t\t\t<div class=\"description\">\r\n\t\t\t\t<div><h3>");
      out.print(introduction);
      out.write("</h3></div>\r\n\t\t\t\t\r\n\t\t\t\t");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setProperty("description");
      _jspx_th_logic_notEmpty_0.setName("myServiceConfigForm");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_bean_define_3.setProperty("description");
          _jspx_th_bean_define_3.setName("myServiceConfigForm");
          _jspx_th_bean_define_3.setId("description");
          int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
          if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
          java.lang.Object description = null;
          description = (java.lang.Object) _jspx_page_context.findAttribute("description");
          out.write("\r\n\t\t\t\t\t<a class=\"bCollapseLink simple-link closed-arrow\" href=\"#\" target=\"#more\">");
          out.print(DicoTools.dico(dico_lang , "generic/showMore"));
          out.write("</a>\r\n\t\t\t\t\t<div class=\"more-desc\" id=\"more\" style=\"display:none;\">");
          out.print(description);
          out.write("</div>\r\n\t\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_notEmpty_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_notEmpty_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_0);
        return;
      }
      _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_0);
      out.write("\t\t\t\t\r\n\t\t\t</div>\r\n\r\n\t</div>\t\t\r\n</div>\t\r\n\r\n<hr class=\"spacer\" />\r\n\t\r\n<div class=\"bottom-bar-outer\">\r\n\t<div class=\"bottom-bar-inner\">\r\n\t\t<a onclick=\"nablife.returnToSrvList()\" href=\"javascript:;\" class=\"srv-back\" ><span>&lt;");
      out.print(DicoTools.dico(dico_lang , "services/back_to_list"));
      out.write("</span></a>\r\n\t</div>\r\n</div>\r\n\r\n<hr class=\"spacer\" />\r\n\r\n<script language=\"javascript\">\r\n\ttools.init_collapseLink();\r\n</script>\r\n\r\n<!-- ****** -->\r\n\r\n\r\n<!-- howto -->\r\n\r\n");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_1 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_name_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_1.setParent(null);
      _jspx_th_logic_notEmpty_1.setName("howTo");
      _jspx_th_logic_notEmpty_1.setName("myServiceConfigForm");
      int _jspx_eval_logic_notEmpty_1 = _jspx_th_logic_notEmpty_1.doStartTag();
      if (_jspx_eval_logic_notEmpty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
          _jspx_th_bean_define_4.setProperty("howTo");
          _jspx_th_bean_define_4.setName("myServiceConfigForm");
          _jspx_th_bean_define_4.setId("howTo");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object howTo = null;
          howTo = (java.lang.Object) _jspx_page_context.findAttribute("howTo");
          out.write("\r\n<div class=\"main-cadre-contener\">\r\n\r\n\t<div class=\"main-cadre-top\"><h2>");
          out.print(DicoTools.dico(dico_lang , "services/how_does_it_work"));
          out.write("</h2></div>\r\n\t\r\n\t<div class=\"main-cadre-content\">\r\n\t\t<hr class=\"spacer\"/>\r\n\t\t<div class=\"srv-main-config\">\r\n\t\t\t<p>");
          out.print(howTo);
          out.write("</p>\t\t\r\n\t\t</div>\r\n\t\t<hr class=\"spacer\"/>\t\r\n\t</div>\r\n</div>\r\n");
          int evalDoAfterBody = _jspx_th_logic_notEmpty_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_notEmpty_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_notEmpty_name_name.reuse(_jspx_th_logic_notEmpty_1);
        return;
      }
      _jspx_tagPool_logic_notEmpty_name_name.reuse(_jspx_th_logic_notEmpty_1);
      out.write("\r\n\r\n\r\n<!-- ****** -->\r\n\r\n\r\n<!-- shortcut -->\r\n");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_2 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_2.setParent(null);
      _jspx_th_logic_notEmpty_2.setProperty("shortcut");
      _jspx_th_logic_notEmpty_2.setName("myServiceConfigForm");
      int _jspx_eval_logic_notEmpty_2 = _jspx_th_logic_notEmpty_2.doStartTag();
      if (_jspx_eval_logic_notEmpty_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_2);
          _jspx_th_bean_define_5.setProperty("shortcut");
          _jspx_th_bean_define_5.setName("myServiceConfigForm");
          _jspx_th_bean_define_5.setId("shortcut");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object shortcut = null;
          shortcut = (java.lang.Object) _jspx_page_context.findAttribute("shortcut");
          out.write("\r\n\r\n<div class=\"main-cadre-contener\">\r\n\r\n\t<div class=\"main-cadre-top\"><h2>");
          out.print(DicoTools.dico(dico_lang , "services/direct_link"));
          out.write("</h2></div>\r\n\t\r\n\t<div class=\"main-cadre-content\">\r\n\t\t<hr class=\"spacer\"/>\r\n\t\t<div class=\"srv-main-config\">\r\n\t\t\t<p align=\"center\"><input class=\"auto-select-field\" type=\"text\" value=\"http://my.nabaztag.com/");
          out.print(shortcut);
          out.write("\" /></p>\t\t\r\n\t\t</div>\r\n\t\t<hr class=\"spacer\"/>\r\n\t\t\t\r\n\t</div>\r\n</div>\r\n\r\n<script>\r\n\t$(\"input.auto-select-field\").click(function(){\r\n\t\t$(this).select();\r\n\t});\r\n\t$(\"input.auto-select-field\").keydown(function(){\r\n\t\treturn false;\r\n\t});\r\n</script>\r\n");
          int evalDoAfterBody = _jspx_th_logic_notEmpty_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_notEmpty_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_2);
        return;
      }
      _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_2);
      out.write("\r\n\r\n\r\n<!-- ******* -->\r\n\r\n<div class=\"bottom-bar-outer\">\r\n\t<div class=\"bottom-bar-inner\">\r\n\t\t<a onclick=\"nablife.returnToSrvList()\" href=\"javascript:;\" class=\"srv-back\" ><span>&lt;");
      out.print(DicoTools.dico(dico_lang , "services/back_to_list"));
      out.write("</span></a>\r\n\t</div>\r\n</div>\r\n");
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
