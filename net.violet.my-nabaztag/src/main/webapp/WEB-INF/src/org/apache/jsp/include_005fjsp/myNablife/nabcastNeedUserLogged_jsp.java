package org.apache.jsp.include_005fjsp.myNablife;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class nabcastNeedUserLogged_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_rewrite_forward_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_text_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_password_property_name_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_rewrite_forward_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_text_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_password_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_html_rewrite_forward_nobody.release();
    _jspx_tagPool_html_form_styleId_action.release();
    _jspx_tagPool_logic_notEmpty_name.release();
    _jspx_tagPool_html_text_property_name_nobody.release();
    _jspx_tagPool_html_password_property_name_nobody.release();
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

      out.write("\n\n\r\n\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\n\n\n\n\n\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
 Lang dico_lang =	SessionTools.getLangFromSession(session, request);
      out.write('\n');
 String redirectUrl = request.getParameter("goTo"); 
      out.write('\n');
 NabcastData nabcastData = (NabcastData) request.getAttribute("nabcastData"); 
      out.write("\n\n<script language=\"javascript\">\r\n\t $('input[@name=redirectUrl]').val(\"");
      out.print(redirectUrl);
      out.write("\");\n</script>\n\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setId("n_id");
      _jspx_th_bean_define_0.setName("nabcastData");
      _jspx_th_bean_define_0.setProperty("nabcast_id");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.Object n_id = null;
      n_id = (java.lang.Object) _jspx_page_context.findAttribute("n_id");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setId("n_title");
      _jspx_th_bean_define_1.setName("nabcastData");
      _jspx_th_bean_define_1.setProperty("nabcast_titre");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object n_title = null;
      n_title = (java.lang.Object) _jspx_page_context.findAttribute("n_title");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setId("n_categ");
      _jspx_th_bean_define_2.setName("nabcastData");
      _jspx_th_bean_define_2.setProperty("nabcast_categ");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      java.lang.Object n_categ = null;
      n_categ = (java.lang.Object) _jspx_page_context.findAttribute("n_categ");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setId("n_desc");
      _jspx_th_bean_define_3.setName("nabcastData");
      _jspx_th_bean_define_3.setProperty("nabcast_description");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      java.lang.Object n_desc = null;
      n_desc = (java.lang.Object) _jspx_page_context.findAttribute("n_desc");
      out.write("\n\n<div class=\"srv-cadre-contener\">\r\n\t\r\n\t<div class=\"main-cadre-top\">\r\n\t\t<h2 class=\"srv-title\" style=\"margin-left:11px;\" >");
      out.print(n_categ);
      out.write(" - <span class=\"nabcast-title\">");
      out.print(n_title);
      out.write("</span></h2>\r\n\t</div>\r\n\t\r\n\t<div class=\"srv-background\">\t\r\n\t\t<div class=\"srv-cadre-content\">\r\n\r\n\t\t\t");
      out.write("\r\n\r\n\t\t\t<div class=\"srv-description\">\r\n\t\t\t\t\t<div class=\"srv-desc-text\">\r\n\t\t\t\t\t\t<a class=\"thickbox link-more-options\" href=\"");
      if (_jspx_meth_html_rewrite_0(_jspx_page_context))
        return;
      out.write("?idNabcast=");
      out.print(n_id);
      out.write("&width=350&height=430\"  >");
      out.print(DicoTools.dico(dico_lang , "bloc/myservices_nabcast_histo"));
      out.write("</a>\r\n\t\t\t\t\t\t<p>");
      out.print(n_desc);
      out.write("</p>\r\n\t\t\t\t\t</div>\r\n\t\t\t</div>\r\n\r\n\t\t\t<hr class=\"clearer\" />\r\n\r\n\t\t\t<div class=\"srv-bottom-links\">\r\n\t\t\t\t<div class=\"srv-bottom-subscribefull\">&nbsp;</div>\t\t\t\t\t\r\n\t\t\t\t<a onclick=\"nablife.returnToSrvList()\" href=\"javascript:;\" class=\"srv-back\" ><span>");
      out.print(DicoTools.dico(dico_lang, "srv_nabcast_subscribe/back"));
      out.write("</span></a>\r\n\t\t\t</div>\r\n\r\n\t\t\t");
      out.write("\r\n\r\n\t\t\t<hr class=\"clearer\" />\r\n\r\n\t\t</div>\r\n\t</div>\r\n\t\r\n</div>\n<br/>\n\t<ul class=\"genericBigList\">\r\n\t\t<li>\r\n\t\t\t");
      out.print(DicoTools.dico(dico_lang , "main/please_connect"));
      out.write("\r\n\t\t\t<div>\r\n\t\t\t\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_action.get(org.apache.struts.taglib.html.FormTag.class);
      _jspx_th_html_form_0.setPageContext(_jspx_page_context);
      _jspx_th_html_form_0.setParent(null);
      _jspx_th_html_form_0.setAction("/action/mySession");
      _jspx_th_html_form_0.setStyleId("login_form");
      int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
      if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t<input type=\"hidden\" name=\"action\" value=\"connect\" />\r\n\t\t\t\t\t\r\n\t\t\t\t\t");
          //  logic:notEmpty
          org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
          _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEmpty_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_notEmpty_0.setName("search");
          int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
          if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t<input type=\"hidden\" name=\"redirectUrl\" value=\"");
              out.print(redirectUrl);
              out.write("\" />\r\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_notEmpty_0.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_notEmpty_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_notEmpty_name.reuse(_jspx_th_logic_notEmpty_0);
            return;
          }
          _jspx_tagPool_logic_notEmpty_name.reuse(_jspx_th_logic_notEmpty_0);
          out.write("\r\n\t\t\t\t\t\r\n\t\t\t\t\t<div class=\"inline-login\">\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"thirdCol\">\r\n\t\t\t\t\t\t\t<label>");
          out.print(DicoTools.dico(dico_lang , "myHome/nabname"));
          out.write("</label>\r\n\t\t\t\t\t\t\t");
          if (_jspx_meth_html_text_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"thirdCol\">\r\n\t\t\t\t\t\t\t<label>");
          out.print(DicoTools.dico(dico_lang , "myHome/motdepasse"));
          out.write("</label>\r\n\t\t\t\t\t\t\t");
          if (_jspx_meth_html_password_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"thirdCol\">\r\n\t\t\t\t\t\t\t<input type=\"submit\" value=\"OK\" class=\"genericBt\"/>\r\n\t\t\t\t\t\t</div>\r\n\t\t\r\n\t\t\t\t\t</div>\r\n\t\t\t\t");
          int evalDoAfterBody = _jspx_th_html_form_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_html_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_html_form_styleId_action.reuse(_jspx_th_html_form_0);
        return;
      }
      _jspx_tagPool_html_form_styleId_action.reuse(_jspx_th_html_form_0);
      out.write("\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\t\t\r\n\t\t\t</div>\r\n\t\t</li>\r\n\t\t<li>\r\n\t\t\t");
      out.print(DicoTools.dico(dico_lang , "main/discover_nabaztag"));
      out.write("\r\n\t\t</li>\t\t\r\n\t</ul>\t");
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

  private boolean _jspx_meth_html_rewrite_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_0 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_0.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_0.setParent(null);
    _jspx_th_html_rewrite_0.setForward("goNabcastHisto");
    int _jspx_eval_html_rewrite_0 = _jspx_th_html_rewrite_0.doStartTag();
    if (_jspx_th_html_rewrite_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
    return false;
  }

  private boolean _jspx_meth_html_text_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_0 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_property_name_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_0.setPageContext(_jspx_page_context);
    _jspx_th_html_text_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_text_0.setName("mySessionForm");
    _jspx_th_html_text_0.setProperty("pseudo");
    int _jspx_eval_html_text_0 = _jspx_th_html_text_0.doStartTag();
    if (_jspx_th_html_text_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_property_name_nobody.reuse(_jspx_th_html_text_0);
      return true;
    }
    _jspx_tagPool_html_text_property_name_nobody.reuse(_jspx_th_html_text_0);
    return false;
  }

  private boolean _jspx_meth_html_password_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:password
    org.apache.struts.taglib.html.PasswordTag _jspx_th_html_password_0 = (org.apache.struts.taglib.html.PasswordTag) _jspx_tagPool_html_password_property_name_nobody.get(org.apache.struts.taglib.html.PasswordTag.class);
    _jspx_th_html_password_0.setPageContext(_jspx_page_context);
    _jspx_th_html_password_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_password_0.setName("mySessionForm");
    _jspx_th_html_password_0.setProperty("password");
    int _jspx_eval_html_password_0 = _jspx_th_html_password_0.doStartTag();
    if (_jspx_th_html_password_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_password_property_name_nobody.reuse(_jspx_th_html_password_0);
      return true;
    }
    _jspx_tagPool_html_password_property_name_nobody.reuse(_jspx_th_html_password_0);
    return false;
  }
}
