package org.apache.jsp.include_005fjsp.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.management.Maintenance;

public final class inc_005fheader_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_top_warning.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_value_type_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_match_value_scope_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_write_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_text_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_password_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_rewrite_forward_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_value_id_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_value_type_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_match_value_scope_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_write_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_text_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_password_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_rewrite_forward_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_value_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_value_type_id_nobody.release();
    _jspx_tagPool_logic_greaterThan_value_name.release();
    _jspx_tagPool_logic_equal_value_name.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_match_value_scope_name.release();
    _jspx_tagPool_bean_write_property_name_nobody.release();
    _jspx_tagPool_logic_greaterThan_value_property_name.release();
    _jspx_tagPool_html_form_styleId_action.release();
    _jspx_tagPool_html_hidden_value_property_name_nobody.release();
    _jspx_tagPool_html_text_property_name_nobody.release();
    _jspx_tagPool_html_password_property_name_nobody.release();
    _jspx_tagPool_html_rewrite_forward_nobody.release();
    _jspx_tagPool_bean_define_value_id_nobody.release();
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

      out.write("\n\r\n\r\n\r\n\n\r\n\r\n\r\n\r\n\r\n\t\r\n");
 String redirectUrl = request.getParameter("goTo"); 
      out.write('\r');
      out.write('\n');
 String thisIsBatard = "false";
      out.write('\r');
      out.write('\n');
 String id = Long.toString( SessionTools.getRabbitIdFromSession(session) ); 
      out.write("\t\t\r\n\t\n\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_type_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setId("isBatard");
      _jspx_th_bean_define_0.setType("String");
      _jspx_th_bean_define_0.setValue(thisIsBatard);
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_0);
      String isBatard = null;
      isBatard = (String) _jspx_page_context.findAttribute("isBatard");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_type_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setId("idLapin");
      _jspx_th_bean_define_1.setType("String");
      _jspx_th_bean_define_1.setValue(id);
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_1);
      String idLapin = null;
      idLapin = (String) _jspx_page_context.findAttribute("idLapin");
      out.write("\r\n\t\r\n\t");
      out.write('\r');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_greaterThan_0(_jspx_page_context))
        return;
      out.write("\r\n\r\n\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_0.setParent(null);
      _jspx_th_logic_equal_0.setName("page_title");
      _jspx_th_logic_equal_0.setValue("myNewAccount");
      int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
      if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
          _jspx_th_bean_define_2.setName("myNewAccountForm");
          _jspx_th_bean_define_2.setProperty("userData");
          _jspx_th_bean_define_2.setId("userData");
          int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
          if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_0);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_0);
      out.write("\t\r\n\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_1.setParent(null);
      _jspx_th_logic_equal_1.setName("page_title");
      _jspx_th_logic_equal_1.setValue("myMessages");
      int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
      if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\t\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_1);
          _jspx_th_bean_define_3.setName("myMessagesForm");
          _jspx_th_bean_define_3.setProperty("userData");
          _jspx_th_bean_define_3.setId("userData");
          int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
          if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_1);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_1);
      out.write("\t\t\t\r\n\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_2.setParent(null);
      _jspx_th_logic_equal_2.setName("page_title");
      _jspx_th_logic_equal_2.setValue("myTerrier");
      int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
      if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\t\t\t\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
          _jspx_th_bean_define_4.setName("myTerrierForm");
          _jspx_th_bean_define_4.setProperty("userData");
          _jspx_th_bean_define_4.setId("userData");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_2);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_2);
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_3.setParent(null);
      _jspx_th_logic_equal_3.setName("page_title");
      _jspx_th_logic_equal_3.setValue("myNablife");
      int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
      if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_3);
          _jspx_th_bean_define_5.setName("myNablifeForm");
          _jspx_th_bean_define_5.setProperty("userData");
          _jspx_th_bean_define_5.setId("userData");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_3.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_3);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_3);
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_4.setParent(null);
      _jspx_th_logic_equal_4.setName("page_title");
      _jspx_th_logic_equal_4.setValue("myTools");
      int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
      if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_4);
          _jspx_th_bean_define_6.setName("myToolsForm");
          _jspx_th_bean_define_6.setProperty("userData");
          _jspx_th_bean_define_6.setId("userData");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_4.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_4);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_4);
      out.write("\r\n\r\n\t<!-- ********************* LOGIN BOX ********************* -->\r\n\r\n\r\n\t<div id=\"loginBox\" \r\n\t\t");
      if (_jspx_meth_logic_equal_5(_jspx_page_context))
        return;
      out.write("\r\n\t\t");
      if (_jspx_meth_logic_equal_7(_jspx_page_context))
        return;
      out.write("\r\n\t>\r\n\t\t\r\n\t\t");
      out.write("\r\n\t\t");
      //  logic:match
      org.apache.struts.taglib.logic.MatchTag _jspx_th_logic_match_0 = (org.apache.struts.taglib.logic.MatchTag) _jspx_tagPool_logic_match_value_scope_name.get(org.apache.struts.taglib.logic.MatchTag.class);
      _jspx_th_logic_match_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_match_0.setParent(null);
      _jspx_th_logic_match_0.setScope("page");
      _jspx_th_logic_match_0.setName("isBatard");
      _jspx_th_logic_match_0.setValue("true");
      int _jspx_eval_logic_match_0 = _jspx_th_logic_match_0.doStartTag();
      if (_jspx_eval_logic_match_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t<div class=\"bat\"><a  href='myTerrier.do?onglet=Profil'><img border=\"0\" title=\"");
          out.print(DicoTools.dico(dico_lang, "header/waiting_connexion"));
          out.write("\" src=\"../include_img/rotating_arrow.gif\" /></a></div>\r\n\t\t");
          int evalDoAfterBody = _jspx_th_logic_match_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_match_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_0);
        return;
      }
      _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_0);
      out.write("\r\n\r\n\t\t<div class=\"main\">\r\n\r\n\t\t\t<input type=\"hidden\" value=\"");
      if (_jspx_meth_bean_write_0(_jspx_page_context))
        return;
      out.write("\" id=\"user_lang\" />\r\n\t\t\t\t\r\n\t\t\t\r\n\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_7.setParent(null);
      _jspx_th_bean_define_7.setName("userData");
      _jspx_th_bean_define_7.setProperty("user_24");
      _jspx_th_bean_define_7.setId("user_24");
      int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
      if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
      java.lang.Object user_24 = null;
      user_24 = (java.lang.Object) _jspx_page_context.findAttribute("user_24");
      out.write("\r\n\t\t\t\r\n\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_8.setParent(null);
      _jspx_th_bean_define_8.setName("userData");
      _jspx_th_bean_define_8.setProperty("user_lang");
      _jspx_th_bean_define_8.setId("user_lang");
      int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
      if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
      java.lang.Object user_lang = null;
      user_lang = (java.lang.Object) _jspx_page_context.findAttribute("user_lang");
      out.write("\r\n\t\t\t\r\n\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_9.setParent(null);
      _jspx_th_bean_define_9.setId("user_id");
      _jspx_th_bean_define_9.setName("userData");
      _jspx_th_bean_define_9.setProperty("user_id");
      int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
      if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
      java.lang.Object user_id = null;
      user_id = (java.lang.Object) _jspx_page_context.findAttribute("user_id");
      out.write("\r\n\t\t\t\r\n\t\t\t");
      //  logic:greaterThan
      org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_1 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
      _jspx_th_logic_greaterThan_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_greaterThan_1.setParent(null);
      _jspx_th_logic_greaterThan_1.setName("userData");
      _jspx_th_logic_greaterThan_1.setProperty("user_id");
      _jspx_th_logic_greaterThan_1.setValue("0");
      int _jspx_eval_logic_greaterThan_1 = _jspx_th_logic_greaterThan_1.doStartTag();
      if (_jspx_eval_logic_greaterThan_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write(' ');
 /* User connectï¿½ */
          out.write("\r\n\t\t\t\t<div class=\"profil\">\r\n\t\t\t\t\t<div class=\"photo\">\r\n\t\t\t\t\t\t");
          if (_jspx_meth_logic_equal_9(_jspx_th_logic_greaterThan_1, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_10 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_10.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_1);
          _jspx_th_logic_equal_10.setName("userData");
          _jspx_th_logic_equal_10.setProperty("user_image");
          _jspx_th_logic_equal_10.setValue("1");
          int _jspx_eval_logic_equal_10 = _jspx_th_logic_equal_10.doStartTag();
          if (_jspx_eval_logic_equal_10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t<img class=\"photo user_picture\" align=\"left\" src=\"../photo/");
              out.print(user_id);
              out.write("_S.jpg\" height=\"42\" border=\"0\">\r\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_equal_10.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_equal_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_10);
            return;
          }
          _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_10);
          out.write("\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t\t<div class=\"pseudo\">\n\t\t\t\t\t\t\t");
          out.print( VObjectData.getData(SessionTools.getRabbitFromSession(session)).getObject_login() );
          out.write("\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t<input type=\"hidden\" value=\"");
          out.print(user_id);
          out.write("\" id=\"user_id\" />\r\n\t\r\n\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_greaterThan_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_greaterThan_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_1);
        return;
      }
      _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_1);
      out.write("\r\n\t\r\n\t\t\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_11 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_11.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_11.setParent(null);
      _jspx_th_logic_equal_11.setName("userData");
      _jspx_th_logic_equal_11.setProperty("user_id");
      _jspx_th_logic_equal_11.setValue("0");
      int _jspx_eval_logic_equal_11 = _jspx_th_logic_equal_11.doStartTag();
      if (_jspx_eval_logic_equal_11 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t");
/* User NON connectï¿½*/
          out.write("\r\n\t\t\t\t");
          //  html:form
          org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_action.get(org.apache.struts.taglib.html.FormTag.class);
          _jspx_th_html_form_0.setPageContext(_jspx_page_context);
          _jspx_th_html_form_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_11);
          _jspx_th_html_form_0.setAction("/action/mySession");
          _jspx_th_html_form_0.setStyleId("login_form");
          int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
          if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t");
              if (_jspx_meth_logic_equal_12(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\t\r\n\t\t\t\t\t");
              if (_jspx_meth_logic_equal_13(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\t\t\t\r\n\t\t\t\t\t");
              if (_jspx_meth_logic_equal_14(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t");
              if (_jspx_meth_logic_equal_15(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\r\n\t\r\n\t\t\t\t\t");
              if (_jspx_meth_html_hidden_4(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t");
              if (_jspx_meth_html_hidden_5(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "header/login_nabname"));
              out.write(" </label>");
              if (_jspx_meth_html_text_0(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("<br />\r\n\t\t\t\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "header/login_password"));
              out.write(" </label>");
              if (_jspx_meth_html_password_0(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t<input type=\"hidden\" name=\"redirectUrl\" value=\"");
              out.print( (redirectUrl==null) ? "myNablife.do" : redirectUrl );
              out.write("\"/>\r\n\t\t\t\t\t<label>&nbsp;</label><input style=\"width:84px;\" class=\"genericBt\" type=\"submit\" value=\"");
              out.print(DicoTools.dico(dico_lang, "header/login_button"));
              out.write("\" class=\"genericBt\"/>\r\n\t\t\t\t\t<hr class=\"clearer\" />\r\n\t\t\t\t");
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
          out.write("\r\n\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_equal_11.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_11);
        return;
      }
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_11);
      out.write("\r\n\t\t\t\r\n\t\t</div>\r\n\t</div>\r\n\r\n\t");
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:greaterThan
      org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_2 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
      _jspx_th_logic_greaterThan_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_greaterThan_2.setParent(null);
      _jspx_th_logic_greaterThan_2.setName("userData");
      _jspx_th_logic_greaterThan_2.setProperty("user_id");
      _jspx_th_logic_greaterThan_2.setValue("0");
      int _jspx_eval_logic_greaterThan_2 = _jspx_th_logic_greaterThan_2.doStartTag();
      if (_jspx_eval_logic_greaterThan_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write(' ');
 /* User connectï¿½ */
          out.write("\r\n\t\t<div class=\"functions\">\r\n\t\t\t<ul>\r\n\t\t\t\t<li class=\"lien-profil\"><a href=\"myTerrier.do?onglet=Profil\"><span>");
          out.print(DicoTools.dico(dico_lang, "header/my_profile"));
          out.write("</span></a></li>\r\n\t\t\t\t<li class=\"rcv_message\"><a title=\"Messages\" href='myMessages.do?onglet=Recu' ><span style=\"display:block;\" id=\"nbMessagesReceivedHeader\"></span></a></li>\r\n\t\t\t\t<li class=\"preference\"><a href='myTerrier.do?onglet=MesPreferences' ><span>");
          out.print(DicoTools.dico(dico_lang, "header/my_preferences"));
          out.write("</span></a></li>\r\n\t\t\t\t<li class=\"unlog\"><a title=\"Dï¿½connecter\" href='");
          if (_jspx_meth_html_rewrite_0(_jspx_th_logic_greaterThan_2, _jspx_page_context))
            return;
          out.write("?action=disconnect&forward=home' ><span>");
          out.print(DicoTools.dico(dico_lang, "header/disconnect"));
          out.write("</span></a></li>\r\n\t\t\t</ul>\r\n\t\t</div>\r\n\t");
          int evalDoAfterBody = _jspx_th_logic_greaterThan_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_greaterThan_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_2);
        return;
      }
      _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_2);
      out.write("\r\n\t\r\n\t<!-- *********************  /LOGIN BOX ********************* -->\r\n");
      out.write("\n\n\n\n");
 final Maintenance theMessage = Maintenance.getInstance(); 
      out.write('\n');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_10.setParent(null);
      _jspx_th_bean_define_10.setId("display");
      _jspx_th_bean_define_10.setValue(theMessage.getDisplay());
      int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
      if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_10);
        return;
      }
      _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_10);
      java.lang.String display = null;
      display = (java.lang.String) _jspx_page_context.findAttribute("display");
      out.write('\n');
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_16 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_16.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_16.setParent(null);
      _jspx_th_logic_equal_16.setName("display");
      _jspx_th_logic_equal_16.setValue("ON");
      int _jspx_eval_logic_equal_16 = _jspx_th_logic_equal_16.doStartTag();
      if (_jspx_eval_logic_equal_16 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\n<div class=\"top-warning-msg\" style=\"margin:0; padding:0;  width:100%; position:absolute; top:0, left:0 text-align:center; z-index:1; opacity:0.80; filter:alpha(opacity=80)\">\n\t<span style=\"position:relative; padding-right:15px; color:black; border:1px solid black; border-top:none; font-weight:bold; line-height:20px; display:block;  background:white; margin:0 240px 0 90px; text-align:center;\">&bull;&nbsp;\n\t\t");
          out.print(theMessage.getMessage() );
          out.write("\n\t&nbsp;&bull;<a class=\"close\" href=\"javascript:hideMaintenanceMsg('");
          out.print(theMessage.getEndMaintenance(true) );
          out.write("')\" style=\"width:12px; height:13px; display:block; position:absolute; top:2px; right:2px; text-indent:-5000px; background-image:url(/vl/include_img/closeWin.gif);background-repeat:no-repeat;\">[X]</a></span>\n</div>\n");
          int evalDoAfterBody = _jspx_th_logic_equal_16.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_16);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_16);
      out.write('\n');
      out.write("\r\n\t\r\n\r\n\t<div id=\"mainLogo\" ><a href=\"../action/myNablife.do\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/nabaztag"));
      out.write("</span></a></div>\r\n\t<div id=\"mainBigMenu\">\r\n\t\t<ul>\r\n\t\t\t<li><a class=\"myNablife");
      if (_jspx_meth_logic_equal_17(_jspx_page_context))
        return;
      out.write("\" href=\"../action/myNablife.do\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/section_nablife"));
      out.write("</span></a></li>\r\n\t\t\t<li><a class=\"myMessages");
      if (_jspx_meth_logic_equal_18(_jspx_page_context))
        return;
      out.write("\" href=\"../action/myMessages.do\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/section_messages"));
      out.write("</span></a></li>\r\n\t\t\t<li><a class=\"myTerrier");
      if (_jspx_meth_logic_equal_19(_jspx_page_context))
        return;
      out.write("\" href=\"../action/myTerrier.do\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/section_terrier"));
      out.write("</span></a></li>\r\n\t\t\t");
      out.write("\r\n\t\t\t<li><a class=\"myHelp\" href=\"http://help.nabaztag.com/index.php?langue=");
      out.print( dico_lang.getHelpLangId() );
      out.write("\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/section_aide"));
      out.write("</span></a></li>\t\t\t\t\t\t\r\n\t\t</ul>\r\n\t</div>\r\n");
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

  private boolean _jspx_meth_logic_greaterThan_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:greaterThan
    org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
    _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_greaterThan_0.setParent(null);
    _jspx_th_logic_greaterThan_0.setName("idLapin");
    _jspx_th_logic_greaterThan_0.setValue("0");
    int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
    if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write(" \r\n\t\t<script language=\"javascript\">\r\n\t\t\t$(document).ready(function(){\r\n\t\t\t\tvar myLink = $(\"div.copyright a[@href*='m-12']\");\r\n\t\t\t\t$(myLink).attr(\"href\", \"myMessages.do?action=messages_TellFriend\");\r\n\t\t\t\t$(myLink).removeAttr(\"target\");\r\n\t\t\t});\r\n\t\t</script>\r\n\t");
        int evalDoAfterBody = _jspx_th_logic_greaterThan_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_greaterThan_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_0);
      return true;
    }
    _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_5.setParent(null);
    _jspx_th_logic_equal_5.setName("page_title");
    _jspx_th_logic_equal_5.setValue("myNewAccount");
    int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
    if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_logic_equal_6(_jspx_th_logic_equal_5, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_logic_equal_5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_5);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_5);
    return false;
  }

  private boolean _jspx_meth_logic_equal_6(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_5, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_6 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_6.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_5);
    _jspx_th_logic_equal_6.setName("userData");
    _jspx_th_logic_equal_6.setProperty("user_id");
    _jspx_th_logic_equal_6.setValue("0");
    int _jspx_eval_logic_equal_6 = _jspx_th_logic_equal_6.doStartTag();
    if (_jspx_eval_logic_equal_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("style=\"display:none;\"");
        int evalDoAfterBody = _jspx_th_logic_equal_6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_6);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_6);
    return false;
  }

  private boolean _jspx_meth_logic_equal_7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_7 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_7.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_7.setParent(null);
    _jspx_th_logic_equal_7.setName("page_title");
    _jspx_th_logic_equal_7.setValue("myNablife");
    int _jspx_eval_logic_equal_7 = _jspx_th_logic_equal_7.doStartTag();
    if (_jspx_eval_logic_equal_7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_logic_equal_8(_jspx_th_logic_equal_7, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_logic_equal_7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_7);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_7);
    return false;
  }

  private boolean _jspx_meth_logic_equal_8(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_7, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_8 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_8.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_7);
    _jspx_th_logic_equal_8.setName("userData");
    _jspx_th_logic_equal_8.setProperty("user_id");
    _jspx_th_logic_equal_8.setValue("0");
    int _jspx_eval_logic_equal_8 = _jspx_th_logic_equal_8.doStartTag();
    if (_jspx_eval_logic_equal_8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("style=\"display:none;\"");
        int evalDoAfterBody = _jspx_th_logic_equal_8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_8);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_8);
    return false;
  }

  private boolean _jspx_meth_bean_write_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_0 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_0.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_0.setParent(null);
    _jspx_th_bean_write_0.setName("userData");
    _jspx_th_bean_write_0.setProperty("user_lang");
    int _jspx_eval_bean_write_0 = _jspx_th_bean_write_0.doStartTag();
    if (_jspx_th_bean_write_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_9(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_9 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_9.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_1);
    _jspx_th_logic_equal_9.setName("userData");
    _jspx_th_logic_equal_9.setProperty("user_image");
    _jspx_th_logic_equal_9.setValue("0");
    int _jspx_eval_logic_equal_9 = _jspx_th_logic_equal_9.doStartTag();
    if (_jspx_eval_logic_equal_9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t\t<img class=\"photo user_picture\" align=\"left\" src=\"../photo/default_S.jpg\" height=\"42\" border=\"0\">\r\n\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_9.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_9);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_9);
    return false;
  }

  private boolean _jspx_meth_logic_equal_12(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_12 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_12.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_equal_12.setName("page_title");
    _jspx_th_logic_equal_12.setValue("myHome");
    int _jspx_eval_logic_equal_12 = _jspx_th_logic_equal_12.doStartTag();
    if (_jspx_eval_logic_equal_12 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t");
        if (_jspx_meth_html_hidden_0(_jspx_th_logic_equal_12, _jspx_page_context))
          return true;
        out.write("\r\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_12.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_12);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_12);
    return false;
  }

  private boolean _jspx_meth_html_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_12, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_12);
    _jspx_th_html_hidden_0.setName("mySessionForm");
    _jspx_th_html_hidden_0.setProperty("url");
    _jspx_th_html_hidden_0.setValue("goNablife");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_13(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_13 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_13.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_equal_13.setName("page_title");
    _jspx_th_logic_equal_13.setValue("myMessages");
    int _jspx_eval_logic_equal_13 = _jspx_th_logic_equal_13.doStartTag();
    if (_jspx_eval_logic_equal_13 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\t\r\n\t\t\t\t\t\t");
        if (_jspx_meth_html_hidden_1(_jspx_th_logic_equal_13, _jspx_page_context))
          return true;
        out.write("\r\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_13.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_13);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_13);
    return false;
  }

  private boolean _jspx_meth_html_hidden_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_13, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_13);
    _jspx_th_html_hidden_1.setName("mySessionForm");
    _jspx_th_html_hidden_1.setProperty("url");
    _jspx_th_html_hidden_1.setValue("goMessages");
    int _jspx_eval_html_hidden_1 = _jspx_th_html_hidden_1.doStartTag();
    if (_jspx_th_html_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_1);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_1);
    return false;
  }

  private boolean _jspx_meth_logic_equal_14(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_14 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_14.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_equal_14.setName("page_title");
    _jspx_th_logic_equal_14.setValue("myTerrier");
    int _jspx_eval_logic_equal_14 = _jspx_th_logic_equal_14.doStartTag();
    if (_jspx_eval_logic_equal_14 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t");
        if (_jspx_meth_html_hidden_2(_jspx_th_logic_equal_14, _jspx_page_context))
          return true;
        out.write("\t\t\r\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_14.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_14);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_14);
    return false;
  }

  private boolean _jspx_meth_html_hidden_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_14, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_2 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_2.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_14);
    _jspx_th_html_hidden_2.setName("mySessionForm");
    _jspx_th_html_hidden_2.setProperty("url");
    _jspx_th_html_hidden_2.setValue("goTerrier");
    int _jspx_eval_html_hidden_2 = _jspx_th_html_hidden_2.doStartTag();
    if (_jspx_th_html_hidden_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_2);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_2);
    return false;
  }

  private boolean _jspx_meth_logic_equal_15(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_15 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_15.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_equal_15.setName("page_title");
    _jspx_th_logic_equal_15.setValue("myNablife");
    int _jspx_eval_logic_equal_15 = _jspx_th_logic_equal_15.doStartTag();
    if (_jspx_eval_logic_equal_15 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t");
        if (_jspx_meth_html_hidden_3(_jspx_th_logic_equal_15, _jspx_page_context))
          return true;
        out.write("\t\r\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_15.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_15);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_15);
    return false;
  }

  private boolean _jspx_meth_html_hidden_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_15, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_3 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_3.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_15);
    _jspx_th_html_hidden_3.setName("mySessionForm");
    _jspx_th_html_hidden_3.setProperty("url");
    _jspx_th_html_hidden_3.setValue("goNablife");
    int _jspx_eval_html_hidden_3 = _jspx_th_html_hidden_3.doStartTag();
    if (_jspx_th_html_hidden_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_3);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_3);
    return false;
  }

  private boolean _jspx_meth_html_hidden_4(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_4 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_4.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_hidden_4.setName("mySessionForm");
    _jspx_th_html_hidden_4.setProperty("action");
    _jspx_th_html_hidden_4.setValue("connect");
    int _jspx_eval_html_hidden_4 = _jspx_th_html_hidden_4.doStartTag();
    if (_jspx_th_html_hidden_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_4);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_4);
    return false;
  }

  private boolean _jspx_meth_html_hidden_5(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_5 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_5.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_hidden_5.setName("mySessionForm");
    _jspx_th_html_hidden_5.setProperty("redirectUrlBadLogin");
    _jspx_th_html_hidden_5.setValue("myNablife.do?badLogin=1");
    int _jspx_eval_html_hidden_5 = _jspx_th_html_hidden_5.doStartTag();
    if (_jspx_th_html_hidden_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_5);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_5);
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

  private boolean _jspx_meth_html_rewrite_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_0 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_0.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_2);
    _jspx_th_html_rewrite_0.setForward("goSession");
    int _jspx_eval_html_rewrite_0 = _jspx_th_html_rewrite_0.doStartTag();
    if (_jspx_th_html_rewrite_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_17(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_17 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_17.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_17.setParent(null);
    _jspx_th_logic_equal_17.setName("page_title");
    _jspx_th_logic_equal_17.setValue("myNablife");
    int _jspx_eval_logic_equal_17 = _jspx_th_logic_equal_17.doStartTag();
    if (_jspx_eval_logic_equal_17 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('O');
        out.write('N');
        int evalDoAfterBody = _jspx_th_logic_equal_17.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_17);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_17);
    return false;
  }

  private boolean _jspx_meth_logic_equal_18(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_18 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_18.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_18.setParent(null);
    _jspx_th_logic_equal_18.setName("page_title");
    _jspx_th_logic_equal_18.setValue("myMessages");
    int _jspx_eval_logic_equal_18 = _jspx_th_logic_equal_18.doStartTag();
    if (_jspx_eval_logic_equal_18 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('O');
        out.write('N');
        int evalDoAfterBody = _jspx_th_logic_equal_18.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_18);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_18);
    return false;
  }

  private boolean _jspx_meth_logic_equal_19(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_19 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_19.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_19.setParent(null);
    _jspx_th_logic_equal_19.setName("page_title");
    _jspx_th_logic_equal_19.setValue("myTerrier");
    int _jspx_eval_logic_equal_19 = _jspx_th_logic_equal_19.doStartTag();
    if (_jspx_eval_logic_equal_19 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('O');
        out.write('N');
        int evalDoAfterBody = _jspx_th_logic_equal_19.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_19);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_19);
    return false;
  }
}
