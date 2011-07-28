package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.datamodel.Application;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;
import net.violet.platform.datamodel.User;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.MyConstantes;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.MyConstantes;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.management.Maintenance;

public final class myNablife_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(5);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
    _jspx_dependants.add("/include_jsp/utils/inc_css.jsp");
    _jspx_dependants.add("/include_jsp/utils/inc_javascripts.jsp");
    _jspx_dependants.add("/include_jsp/utils/inc_header.jsp");
    _jspx_dependants.add("/include_jsp/utils/inc_top_warning.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_value_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_value_type_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_write_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_match_value_scope_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notMatch_value_scope_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_text_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_password_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_rewrite_forward_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_select_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_lessEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_option_value;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_parameter;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_parameter;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_lessEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_write_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_empty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_value_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_value_type_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_write_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_match_value_scope_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notMatch_value_scope_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_text_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_password_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_rewrite_forward_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_select_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_lessEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_option_value = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_parameter = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_parameter = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_lessEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_write_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_empty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_bean_define_value_id_nobody.release();
    _jspx_tagPool_bean_define_value_type_id_nobody.release();
    _jspx_tagPool_logic_equal_value_name.release();
    _jspx_tagPool_logic_greaterThan_value_property_name.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_bean_write_property_name_nobody.release();
    _jspx_tagPool_logic_match_value_scope_name.release();
    _jspx_tagPool_logic_notMatch_value_scope_name.release();
    _jspx_tagPool_logic_greaterThan_value_name.release();
    _jspx_tagPool_html_form_styleId_action.release();
    _jspx_tagPool_html_hidden_value_property_name_nobody.release();
    _jspx_tagPool_html_text_property_name_nobody.release();
    _jspx_tagPool_html_password_property_name_nobody.release();
    _jspx_tagPool_html_rewrite_forward_nobody.release();
    _jspx_tagPool_html_select_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_logic_lessEqual_value_name.release();
    _jspx_tagPool_html_option_value.release();
    _jspx_tagPool_logic_notEqual_value_parameter.release();
    _jspx_tagPool_logic_equal_value_parameter.release();
    _jspx_tagPool_logic_notEqual_value_name.release();
    _jspx_tagPool_logic_lessEqual_value_property_name.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_logic_iterate_name_id.release();
    _jspx_tagPool_logic_notEmpty_name.release();
    _jspx_tagPool_bean_write_name_nobody.release();
    _jspx_tagPool_logic_empty_property_name.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
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

      out.write("\n\n\n\n\n");

	response.setContentType("text/html;charset=UTF-8");

      out.write(" \n\n\n\n\n\n");

	final Lang dico_lang =	SessionTools.getLangFromSession(session, request);
	String user_main = Long.toString(SessionTools.getRabbitIdFromSession(session));
	session.setAttribute("page_title","myNablife");

      out.write('\n');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myNablifeForm");
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
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("myNablifeForm");
      _jspx_th_bean_define_1.setProperty("userId");
      _jspx_th_bean_define_1.setId("user_id");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object user_id = null;
      user_id = (java.lang.Object) _jspx_page_context.findAttribute("user_id");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setName("myNablifeForm");
      _jspx_th_bean_define_2.setProperty("onglet");
      _jspx_th_bean_define_2.setId("onglet");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      java.lang.Object onglet = null;
      onglet = (java.lang.Object) _jspx_page_context.findAttribute("onglet");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setName("myNablifeForm");
      _jspx_th_bean_define_3.setProperty("serviceToConfigure");
      _jspx_th_bean_define_3.setId("serviceToConfigure");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      java.lang.Object serviceToConfigure = null;
      serviceToConfigure = (java.lang.Object) _jspx_page_context.findAttribute("serviceToConfigure");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_4.setParent(null);
      _jspx_th_bean_define_4.setName("myNablifeForm");
      _jspx_th_bean_define_4.setProperty("categoryId");
      _jspx_th_bean_define_4.setId("categoryId");
      int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
      if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
      java.lang.Object categoryId = null;
      categoryId = (java.lang.Object) _jspx_page_context.findAttribute("categoryId");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_5.setParent(null);
      _jspx_th_bean_define_5.setName("myNablifeForm");
      _jspx_th_bean_define_5.setProperty("categoryLang");
      _jspx_th_bean_define_5.setId("categoryLang");
      int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
      if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
      java.lang.Object categoryLang = null;
      categoryLang = (java.lang.Object) _jspx_page_context.findAttribute("categoryLang");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_6.setParent(null);
      _jspx_th_bean_define_6.setId("badLogin");
      _jspx_th_bean_define_6.setValue((request.getParameter("badLogin")==null) ? "0" : (String)request.getParameter("badLogin"));
      int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
      if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_6);
        return;
      }
      _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_6);
      java.lang.String badLogin = null;
      badLogin = (java.lang.String) _jspx_page_context.findAttribute("badLogin");
      out.write('	');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_7.setParent(null);
      _jspx_th_bean_define_7.setId("searched");
      _jspx_th_bean_define_7.setValue((request.getParameter("searched")==null) ? "" : request.getParameter("searched"));
      int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
      if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_7);
        return;
      }
      _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_7);
      java.lang.String searched = null;
      searched = (java.lang.String) _jspx_page_context.findAttribute("searched");
      out.write('\n');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_8.setParent(null);
      _jspx_th_bean_define_8.setId("search");
      _jspx_th_bean_define_8.setValue((session.getAttribute("search")==null) ? "" : (String)session.getAttribute("search"));
      int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
      if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_8);
        return;
      }
      _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_8);
      java.lang.String search = null;
      search = (java.lang.String) _jspx_page_context.findAttribute("search");
      out.write("\n\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"fr\">\n\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n\n<title>");
      out.print(DicoTools.dico(dico_lang , "myNablife/page_title"));
      out.write("</title>\n\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_9.setParent(null);
      _jspx_th_bean_define_9.setId("search");
      _jspx_th_bean_define_9.setValue((request.getParameter("search")==null) ? "": request.getParameter("search"));
      int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
      if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_9);
        return;
      }
      _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_9);
      search = (java.lang.String) _jspx_page_context.findAttribute("search");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_10.setParent(null);
      _jspx_th_bean_define_10.setId("idNabcast");
      _jspx_th_bean_define_10.setValue((request.getParameter("idNabcast")==null) ? "": request.getParameter("idNabcast"));
      int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
      if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_10);
        return;
      }
      _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_10);
      java.lang.String idNabcast = null;
      idNabcast = (java.lang.String) _jspx_page_context.findAttribute("idNabcast");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_11.setParent(null);
      _jspx_th_bean_define_11.setId("goToListResult");
      _jspx_th_bean_define_11.setValue((request.getAttribute("goToListResult")==null) ? "": request.getAttribute("goToListResult").toString());
      int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
      if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_11);
        return;
      }
      _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_11);
      java.lang.String goToListResult = null;
      goToListResult = (java.lang.String) _jspx_page_context.findAttribute("goToListResult");
      out.write('\n');
      out.write('\n');
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\t<link href=\"../include_css/localImg.");
      out.print(dico_lang.getId());
      out.write(".css?v=1\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\r\n");

		// en local et sur DEV, on utilise les css tel quel
		if (MyConstantes.DOMAIN.equals("localhost") || MyConstantes.DOMAIN.equals("192.168.1.11")) {
	
      out.write('\r');
      out.write('\n');
      out.write('	');
      out.write("\r\n\t<link href=\"../include_css/modules/thickbox.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/modules/modal.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/modules/widget.nabthem.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/modules/jquery.tooltip.css\" rel=\"stylesheet\" type=\"text/css\" />\t\r\n\r\n\r\n\t");
      out.write("\r\n\t<link href=\"../include_css/layout/images.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/layout/blocks.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/layout/basic.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/layout/blocks_layout.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/layout/main-layout.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\r\n\t");
      out.write("\r\n\t<link href=\"../include_css/part_layout/nabcasts.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/part_layout/home.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/part_layout/nablife.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/part_layout/messages.css\" rel=\"stylesheet\" type=\"text/css\" />\t\r\n\t<link href=\"../include_css/part_layout/terrier.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\r\n\t");
      out.write("\r\n\r\n\t<link title=\"layout_green\" href=\"../include_css/layout_green/layout.css?v=1.3\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_orange\" href=\"../include_css/layout_orange/layout.css?v=1.3\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_blue\" href=\"../include_css/layout_blue/layout.css?v=1.3\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_violet\" href=\"../include_css/layout_violet/layout.css?v=1.3\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_pink\" href=\"../include_css/layout_pink/layout.css?v=1.3\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\r\n\r\n");

	// sinon on utilise les versions "minimized" ( necessite un ANT, target -> cssy_nabaztag )
	} else {

      out.write("\r\n\r\n\t<link href=\"../include_css/dist/nabaztag.modules.css?v=1.1\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/dist/nabaztag.layout.css?v=1.1.1\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t<link href=\"../include_css/dist/nabaztag.parts.css?v=1.1\" rel=\"stylesheet\" type=\"text/css\" />\r\n\t\r\n\t<link title=\"layout_green\" \thref=\"../include_css/dist/nabaztag.layout_green.css?v=1.1\" \trel=\"stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_orange\" href=\"../include_css/dist/nabaztag.layout_orange.css?v=1.1\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_blue\" \thref=\"../include_css/dist/nabaztag.layout_blue.css?v=1.1\" \trel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_violet\" href=\"../include_css/dist/nabaztag.layout_violet.css?v=1.1\" rel=\"alternate stylesheet\" type=\"text/css\" />\r\n\t<link title=\"layout_pink\" \thref=\"../include_css/dist/nabaztag.layout_pink.css?v=1.1\" \trel=\"alternate stylesheet\" type=\"text/css\" />\t\r\n\r\n");
 } 
      out.write("\r\n<!--[if IE 6]>\r\n\t<link href=\"../include_css/ieHack.css?v=1.2\" rel=\"stylesheet\" type=\"text/css\" />\r\n<![endif]-->\r\n\r\n<!--[if IE 7]>\r\n\t<link href=\"../include_css/ie7Hack.css?v=1.2\" rel=\"stylesheet\" type=\"text/css\" />\r\n<![endif]-->\r\n");
      out.write('\n');
      out.write("\n\n\n\n\n\n\n\n\n\n");

	String iAmABatard = "false";

      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_12 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_type_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_12.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_12.setParent(null);
      _jspx_th_bean_define_12.setId("isBatard");
      _jspx_th_bean_define_12.setType("String");
      _jspx_th_bean_define_12.setValue(iAmABatard);
      int _jspx_eval_bean_define_12 = _jspx_th_bean_define_12.doStartTag();
      if (_jspx_th_bean_define_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_12);
        return;
      }
      _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_12);
      String isBatard = null;
      isBatard = (String) _jspx_page_context.findAttribute("isBatard");
      out.write('\n');
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_0.setParent(null);
      _jspx_th_logic_equal_0.setName("page_title");
      _jspx_th_logic_equal_0.setValue("myNewAccount");
      int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
      if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_13 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_13.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
          _jspx_th_bean_define_13.setName("myNewAccountForm");
          _jspx_th_bean_define_13.setProperty("userData");
          _jspx_th_bean_define_13.setId("userData");
          int _jspx_eval_bean_define_13 = _jspx_th_bean_define_13.doStartTag();
          if (_jspx_th_bean_define_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\n');
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
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_1.setParent(null);
      _jspx_th_logic_equal_1.setName("page_title");
      _jspx_th_logic_equal_1.setValue("myMessages");
      int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
      if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_14 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_14.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_1);
          _jspx_th_bean_define_14.setName("myMessagesForm");
          _jspx_th_bean_define_14.setProperty("userData");
          _jspx_th_bean_define_14.setId("userData");
          int _jspx_eval_bean_define_14 = _jspx_th_bean_define_14.doStartTag();
          if (_jspx_th_bean_define_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\n');
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
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_2.setParent(null);
      _jspx_th_logic_equal_2.setName("page_title");
      _jspx_th_logic_equal_2.setValue("myTerrier");
      int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
      if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_15 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_15.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
          _jspx_th_bean_define_15.setName("myTerrierForm");
          _jspx_th_bean_define_15.setProperty("userData");
          _jspx_th_bean_define_15.setId("userData");
          int _jspx_eval_bean_define_15 = _jspx_th_bean_define_15.doStartTag();
          if (_jspx_th_bean_define_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_15);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_15);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\n');
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
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_3.setParent(null);
      _jspx_th_logic_equal_3.setName("page_title");
      _jspx_th_logic_equal_3.setValue("myNablife");
      int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
      if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_16 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_16.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_16.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_3);
          _jspx_th_bean_define_16.setName("myNablifeForm");
          _jspx_th_bean_define_16.setProperty("userData");
          _jspx_th_bean_define_16.setId("userData");
          int _jspx_eval_bean_define_16 = _jspx_th_bean_define_16.doStartTag();
          if (_jspx_th_bean_define_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_16);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_16);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\n');
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
      out.write('\n');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_17 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_17.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_17.setParent(null);
      _jspx_th_bean_define_17.setName("userData");
      _jspx_th_bean_define_17.setProperty("user_24");
      _jspx_th_bean_define_17.setId("user_24");
      int _jspx_eval_bean_define_17 = _jspx_th_bean_define_17.doStartTag();
      if (_jspx_th_bean_define_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_17);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_17);
      java.lang.Object user_24 = null;
      user_24 = (java.lang.Object) _jspx_page_context.findAttribute("user_24");
      out.write('\n');
      out.write('\n');

	/* fichier d'inclusion de tous les javascripts */

      out.write("\n<script type=\"text/javascript\">\n\tcurrentTab=\"\";/* bidouille IE*/\n\n\tvar nabaztag = {};\n\tnabaztag.constantes = {};\n\tnabaztag.constantes.DOMAIN \t\t\t= \"");
      out.print(MyConstantes.DOMAIN);
      out.write("\";\n\tnabaztag.constantes.STREAM_SERVER \t= \"");
      out.print(MyConstantes.STREAM_SERVER);
      out.write("\";\n\n\tnabaztag.constantes.H24 \t\t\t= ");
      out.print(user_24);
      out.write(";\n\n\t");
      if (_jspx_meth_logic_greaterThan_0(_jspx_page_context))
        return;
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_4(_jspx_page_context))
        return;
      out.write("\n\n\n\tnabaztag.constantes.OBJECTID\t\t= ");
      if (_jspx_meth_bean_write_0(_jspx_page_context))
        return;
      out.write(";\n\n\tnabaztag.constantes.BAD_CONNECTED_OBJECT\t= ");
      if (_jspx_meth_logic_match_0(_jspx_page_context))
        return;
      if (_jspx_meth_logic_notMatch_0(_jspx_page_context))
        return;
      out.write(";\n\n</script>\n\n");

	// en local et sur DEV, on utilise les javascripts tel quel
	if (MyConstantes.DOMAIN.equals("localhost") || MyConstantes.DOMAIN.equals("192.168.1.11")) {

      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/jquery-1.2.1.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.compat-1.1.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.color.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.easing.1.1.1.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery-select.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/thickbox.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.form.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.tooltip.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.block.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jqModal.js\"></script>\n");
      out.write("\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/dom-creator.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/datePicker.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.jmp3.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.dimensions.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.mousewheel.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/cdl-conf.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/platform-detection.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/vars.js\"></script>\n\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/swfobject-1.5.1.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/jstools.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag_validate.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag_blocks.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag_jqueryModules.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag-nabThem.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myHome/mynabaztag-register.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myTerrier/mynabaztag-terrier.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myNablife/mynabaztag-nablife.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myNablife/nablife-validate.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myNablife/bloc.services.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');

	// sinon on utilise les versions "minimized" ( necessite un ANT, target -> jsy_nabaztag )
	} else {

      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.jquery.js?v=1.2.1a'></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.conftools.js?v=1.2'></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.common.js?v=1.1'></script>\n\n\t");
      if (_jspx_meth_logic_equal_5(_jspx_page_context))
        return;
      out.write('\n');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_6(_jspx_page_context))
        return;
      out.write('\n');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_7(_jspx_page_context))
        return;
      out.write('\n');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_8(_jspx_page_context))
        return;
      out.write('\n');
      out.write('\n');
 } 
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/mynabaztag_text.jsp?v=1.4&l=");
      out.print(dico_lang.getIsoCode());
      out.write("'></script>\n\n\t");
      //  logic:match
      org.apache.struts.taglib.logic.MatchTag _jspx_th_logic_match_1 = (org.apache.struts.taglib.logic.MatchTag) _jspx_tagPool_logic_match_value_scope_name.get(org.apache.struts.taglib.logic.MatchTag.class);
      _jspx_th_logic_match_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_match_1.setParent(null);
      _jspx_th_logic_match_1.setScope("page");
      _jspx_th_logic_match_1.setName("isBatard");
      _jspx_th_logic_match_1.setValue("true");
      int _jspx_eval_logic_match_1 = _jspx_th_logic_match_1.doStartTag();
      if (_jspx_eval_logic_match_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t<script>\n\t\t\t");
          out.write("\n\t\t\tstartOnlineStatusCheck(");
          out.print(user_id);
          out.write(");\n\t\t</script>\n\t");
          int evalDoAfterBody = _jspx_th_logic_match_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_match_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_1);
        return;
      }
      _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_1);
      out.write('\n');
      out.write("\n\n</head>\n\n<body onload='javascript:getNewMessages();'>\n\n<div id=\"container\">\n\t<div id=\"header\">\n\t\t");
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
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_18 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_type_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_18.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_18.setParent(null);
      _jspx_th_bean_define_18.setId("isBatard");
      _jspx_th_bean_define_18.setType("String");
      _jspx_th_bean_define_18.setValue(thisIsBatard);
      int _jspx_eval_bean_define_18 = _jspx_th_bean_define_18.doStartTag();
      if (_jspx_th_bean_define_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_18);
        return;
      }
      _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_18);
      isBatard = (String) _jspx_page_context.findAttribute("isBatard");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_19 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_type_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_19.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_19.setParent(null);
      _jspx_th_bean_define_19.setId("idLapin");
      _jspx_th_bean_define_19.setType("String");
      _jspx_th_bean_define_19.setValue(id);
      int _jspx_eval_bean_define_19 = _jspx_th_bean_define_19.doStartTag();
      if (_jspx_th_bean_define_19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_19);
        return;
      }
      _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_19);
      String idLapin = null;
      idLapin = (String) _jspx_page_context.findAttribute("idLapin");
      out.write("\r\n\t\r\n\t");
      out.write('\r');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_greaterThan_1(_jspx_page_context))
        return;
      out.write("\r\n\r\n\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_9 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_9.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_9.setParent(null);
      _jspx_th_logic_equal_9.setName("page_title");
      _jspx_th_logic_equal_9.setValue("myNewAccount");
      int _jspx_eval_logic_equal_9 = _jspx_th_logic_equal_9.doStartTag();
      if (_jspx_eval_logic_equal_9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_20 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_20.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_20.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_9);
          _jspx_th_bean_define_20.setName("myNewAccountForm");
          _jspx_th_bean_define_20.setProperty("userData");
          _jspx_th_bean_define_20.setId("userData");
          int _jspx_eval_bean_define_20 = _jspx_th_bean_define_20.doStartTag();
          if (_jspx_th_bean_define_20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_20);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_20);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_9.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_9);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_9);
      out.write("\t\r\n\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_10 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_10.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_10.setParent(null);
      _jspx_th_logic_equal_10.setName("page_title");
      _jspx_th_logic_equal_10.setValue("myMessages");
      int _jspx_eval_logic_equal_10 = _jspx_th_logic_equal_10.doStartTag();
      if (_jspx_eval_logic_equal_10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\t\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_21 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_21.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_21.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_10);
          _jspx_th_bean_define_21.setName("myMessagesForm");
          _jspx_th_bean_define_21.setProperty("userData");
          _jspx_th_bean_define_21.setId("userData");
          int _jspx_eval_bean_define_21 = _jspx_th_bean_define_21.doStartTag();
          if (_jspx_th_bean_define_21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_21);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_21);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_10.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_10);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_10);
      out.write("\t\t\t\r\n\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_11 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_11.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_11.setParent(null);
      _jspx_th_logic_equal_11.setName("page_title");
      _jspx_th_logic_equal_11.setValue("myTerrier");
      int _jspx_eval_logic_equal_11 = _jspx_th_logic_equal_11.doStartTag();
      if (_jspx_eval_logic_equal_11 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\t\t\t\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_22 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_22.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_22.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_11);
          _jspx_th_bean_define_22.setName("myTerrierForm");
          _jspx_th_bean_define_22.setProperty("userData");
          _jspx_th_bean_define_22.setId("userData");
          int _jspx_eval_bean_define_22 = _jspx_th_bean_define_22.doStartTag();
          if (_jspx_th_bean_define_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_22);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_22);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_11.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_11);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_11);
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_12 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_12.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_12.setParent(null);
      _jspx_th_logic_equal_12.setName("page_title");
      _jspx_th_logic_equal_12.setValue("myNablife");
      int _jspx_eval_logic_equal_12 = _jspx_th_logic_equal_12.doStartTag();
      if (_jspx_eval_logic_equal_12 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_23 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_23.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_23.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_12);
          _jspx_th_bean_define_23.setName("myNablifeForm");
          _jspx_th_bean_define_23.setProperty("userData");
          _jspx_th_bean_define_23.setId("userData");
          int _jspx_eval_bean_define_23 = _jspx_th_bean_define_23.doStartTag();
          if (_jspx_th_bean_define_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_23);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_23);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_12.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_12);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_12);
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_13 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_13.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_13.setParent(null);
      _jspx_th_logic_equal_13.setName("page_title");
      _jspx_th_logic_equal_13.setValue("myTools");
      int _jspx_eval_logic_equal_13 = _jspx_th_logic_equal_13.doStartTag();
      if (_jspx_eval_logic_equal_13 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_24 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_24.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_24.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_13);
          _jspx_th_bean_define_24.setName("myToolsForm");
          _jspx_th_bean_define_24.setProperty("userData");
          _jspx_th_bean_define_24.setId("userData");
          int _jspx_eval_bean_define_24 = _jspx_th_bean_define_24.doStartTag();
          if (_jspx_th_bean_define_24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_24);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_24);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_13.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_13);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_13);
      out.write("\r\n\r\n\t<!-- ********************* LOGIN BOX ********************* -->\r\n\r\n\r\n\t<div id=\"loginBox\" \r\n\t\t");
      if (_jspx_meth_logic_equal_14(_jspx_page_context))
        return;
      out.write("\r\n\t\t");
      if (_jspx_meth_logic_equal_16(_jspx_page_context))
        return;
      out.write("\r\n\t>\r\n\t\t\r\n\t\t");
      out.write("\r\n\t\t");
      //  logic:match
      org.apache.struts.taglib.logic.MatchTag _jspx_th_logic_match_2 = (org.apache.struts.taglib.logic.MatchTag) _jspx_tagPool_logic_match_value_scope_name.get(org.apache.struts.taglib.logic.MatchTag.class);
      _jspx_th_logic_match_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_match_2.setParent(null);
      _jspx_th_logic_match_2.setScope("page");
      _jspx_th_logic_match_2.setName("isBatard");
      _jspx_th_logic_match_2.setValue("true");
      int _jspx_eval_logic_match_2 = _jspx_th_logic_match_2.doStartTag();
      if (_jspx_eval_logic_match_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t<div class=\"bat\"><a  href='myTerrier.do?onglet=Profil'><img border=\"0\" title=\"");
          out.print(DicoTools.dico(dico_lang, "header/waiting_connexion"));
          out.write("\" src=\"../include_img/rotating_arrow.gif\" /></a></div>\r\n\t\t");
          int evalDoAfterBody = _jspx_th_logic_match_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_match_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_2);
        return;
      }
      _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_2);
      out.write("\r\n\r\n\t\t<div class=\"main\">\r\n\r\n\t\t\t<input type=\"hidden\" value=\"");
      if (_jspx_meth_bean_write_1(_jspx_page_context))
        return;
      out.write("\" id=\"user_lang\" />\r\n\t\t\t\t\r\n\t\t\t\r\n\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_25 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_25.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_25.setParent(null);
      _jspx_th_bean_define_25.setName("userData");
      _jspx_th_bean_define_25.setProperty("user_24");
      _jspx_th_bean_define_25.setId("user_24");
      int _jspx_eval_bean_define_25 = _jspx_th_bean_define_25.doStartTag();
      if (_jspx_th_bean_define_25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_25);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_25);
      user_24 = (java.lang.Object) _jspx_page_context.findAttribute("user_24");
      out.write("\r\n\t\t\t\r\n\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_26 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_26.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_26.setParent(null);
      _jspx_th_bean_define_26.setName("userData");
      _jspx_th_bean_define_26.setProperty("user_lang");
      _jspx_th_bean_define_26.setId("user_lang");
      int _jspx_eval_bean_define_26 = _jspx_th_bean_define_26.doStartTag();
      if (_jspx_th_bean_define_26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_26);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_26);
      java.lang.Object user_lang = null;
      user_lang = (java.lang.Object) _jspx_page_context.findAttribute("user_lang");
      out.write("\r\n\t\t\t\r\n\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_27 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_27.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_27.setParent(null);
      _jspx_th_bean_define_27.setId("user_id");
      _jspx_th_bean_define_27.setName("userData");
      _jspx_th_bean_define_27.setProperty("user_id");
      int _jspx_eval_bean_define_27 = _jspx_th_bean_define_27.doStartTag();
      if (_jspx_th_bean_define_27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_27);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_27);
      user_id = (java.lang.Object) _jspx_page_context.findAttribute("user_id");
      out.write("\r\n\t\t\t\r\n\t\t\t");
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
          out.write("\r\n\t\t\t\t<div class=\"profil\">\r\n\t\t\t\t\t<div class=\"photo\">\r\n\t\t\t\t\t\t");
          if (_jspx_meth_logic_equal_18(_jspx_th_logic_greaterThan_2, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_19 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_19.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_19.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_2);
          _jspx_th_logic_equal_19.setName("userData");
          _jspx_th_logic_equal_19.setProperty("user_image");
          _jspx_th_logic_equal_19.setValue("1");
          int _jspx_eval_logic_equal_19 = _jspx_th_logic_equal_19.doStartTag();
          if (_jspx_eval_logic_equal_19 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t<img class=\"photo user_picture\" align=\"left\" src=\"../photo/");
              out.print(user_id);
              out.write("_S.jpg\" height=\"42\" border=\"0\">\r\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_equal_19.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_equal_19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_19);
            return;
          }
          _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_19);
          out.write("\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t\t<div class=\"pseudo\">\n\t\t\t\t\t\t\t");
          out.print( VObjectData.getData(SessionTools.getRabbitFromSession(session)).getObject_login() );
          out.write("\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t<input type=\"hidden\" value=\"");
          out.print(user_id);
          out.write("\" id=\"user_id\" />\r\n\t\r\n\t\t\t");
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
      out.write("\r\n\t\r\n\t\t\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_20 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_20.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_20.setParent(null);
      _jspx_th_logic_equal_20.setName("userData");
      _jspx_th_logic_equal_20.setProperty("user_id");
      _jspx_th_logic_equal_20.setValue("0");
      int _jspx_eval_logic_equal_20 = _jspx_th_logic_equal_20.doStartTag();
      if (_jspx_eval_logic_equal_20 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t");
/* User NON connectï¿½*/
          out.write("\r\n\t\t\t\t");
          //  html:form
          org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_action.get(org.apache.struts.taglib.html.FormTag.class);
          _jspx_th_html_form_0.setPageContext(_jspx_page_context);
          _jspx_th_html_form_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_20);
          _jspx_th_html_form_0.setAction("/action/mySession");
          _jspx_th_html_form_0.setStyleId("login_form");
          int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
          if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t");
              if (_jspx_meth_logic_equal_21(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\t\r\n\t\t\t\t\t");
              if (_jspx_meth_logic_equal_22(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\t\t\t\r\n\t\t\t\t\t");
              if (_jspx_meth_logic_equal_23(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t");
              if (_jspx_meth_logic_equal_24(_jspx_th_html_form_0, _jspx_page_context))
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
          int evalDoAfterBody = _jspx_th_logic_equal_20.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_20);
        return;
      }
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_20);
      out.write("\r\n\t\t\t\r\n\t\t</div>\r\n\t</div>\r\n\r\n\t");
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:greaterThan
      org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_3 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
      _jspx_th_logic_greaterThan_3.setPageContext(_jspx_page_context);
      _jspx_th_logic_greaterThan_3.setParent(null);
      _jspx_th_logic_greaterThan_3.setName("userData");
      _jspx_th_logic_greaterThan_3.setProperty("user_id");
      _jspx_th_logic_greaterThan_3.setValue("0");
      int _jspx_eval_logic_greaterThan_3 = _jspx_th_logic_greaterThan_3.doStartTag();
      if (_jspx_eval_logic_greaterThan_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write(' ');
 /* User connectï¿½ */
          out.write("\r\n\t\t<div class=\"functions\">\r\n\t\t\t<ul>\r\n\t\t\t\t<li class=\"lien-profil\"><a href=\"myTerrier.do?onglet=Profil\"><span>");
          out.print(DicoTools.dico(dico_lang, "header/my_profile"));
          out.write("</span></a></li>\r\n\t\t\t\t<li class=\"rcv_message\"><a title=\"Messages\" href='myMessages.do?onglet=Recu' ><span style=\"display:block;\" id=\"nbMessagesReceivedHeader\"></span></a></li>\r\n\t\t\t\t<li class=\"preference\"><a href='myTerrier.do?onglet=MesPreferences' ><span>");
          out.print(DicoTools.dico(dico_lang, "header/my_preferences"));
          out.write("</span></a></li>\r\n\t\t\t\t<li class=\"unlog\"><a title=\"Dï¿½connecter\" href='");
          if (_jspx_meth_html_rewrite_0(_jspx_th_logic_greaterThan_3, _jspx_page_context))
            return;
          out.write("?action=disconnect&forward=home' ><span>");
          out.print(DicoTools.dico(dico_lang, "header/disconnect"));
          out.write("</span></a></li>\r\n\t\t\t</ul>\r\n\t\t</div>\r\n\t");
          int evalDoAfterBody = _jspx_th_logic_greaterThan_3.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_greaterThan_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_3);
        return;
      }
      _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_3);
      out.write("\r\n\t\r\n\t<!-- *********************  /LOGIN BOX ********************* -->\r\n");
      out.write("\n\n\n\n");
 final Maintenance theMessage = Maintenance.getInstance(); 
      out.write('\n');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_28 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_28.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_28.setParent(null);
      _jspx_th_bean_define_28.setId("display");
      _jspx_th_bean_define_28.setValue(theMessage.getDisplay());
      int _jspx_eval_bean_define_28 = _jspx_th_bean_define_28.doStartTag();
      if (_jspx_th_bean_define_28.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_28);
        return;
      }
      _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_28);
      java.lang.String display = null;
      display = (java.lang.String) _jspx_page_context.findAttribute("display");
      out.write('\n');
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_25 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_25.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_25.setParent(null);
      _jspx_th_logic_equal_25.setName("display");
      _jspx_th_logic_equal_25.setValue("ON");
      int _jspx_eval_logic_equal_25 = _jspx_th_logic_equal_25.doStartTag();
      if (_jspx_eval_logic_equal_25 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\n<div class=\"top-warning-msg\" style=\"margin:0; padding:0;  width:100%; position:absolute; top:0, left:0 text-align:center; z-index:1; opacity:0.80; filter:alpha(opacity=80)\">\n\t<span style=\"position:relative; padding-right:15px; color:black; border:1px solid black; border-top:none; font-weight:bold; line-height:20px; display:block;  background:white; margin:0 240px 0 90px; text-align:center;\">&bull;&nbsp;\n\t\t");
          out.print(theMessage.getMessage() );
          out.write("\n\t&nbsp;&bull;<a class=\"close\" href=\"javascript:hideMaintenanceMsg('");
          out.print(theMessage.getEndMaintenance(true) );
          out.write("')\" style=\"width:12px; height:13px; display:block; position:absolute; top:2px; right:2px; text-indent:-5000px; background-image:url(/vl/include_img/closeWin.gif);background-repeat:no-repeat;\">[X]</a></span>\n</div>\n");
          int evalDoAfterBody = _jspx_th_logic_equal_25.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_25.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_25);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_25);
      out.write('\n');
      out.write("\r\n\t\r\n\r\n\t<div id=\"mainLogo\" ><a href=\"../action/myNablife.do\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/nabaztag"));
      out.write("</span></a></div>\r\n\t<div id=\"mainBigMenu\">\r\n\t\t<ul>\r\n\t\t\t<li><a class=\"myNablife");
      if (_jspx_meth_logic_equal_26(_jspx_page_context))
        return;
      out.write("\" href=\"../action/myNablife.do\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/section_nablife"));
      out.write("</span></a></li>\r\n\t\t\t<li><a class=\"myMessages");
      if (_jspx_meth_logic_equal_27(_jspx_page_context))
        return;
      out.write("\" href=\"../action/myMessages.do\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/section_messages"));
      out.write("</span></a></li>\r\n\t\t\t<li><a class=\"myTerrier");
      if (_jspx_meth_logic_equal_28(_jspx_page_context))
        return;
      out.write("\" href=\"../action/myTerrier.do\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/section_terrier"));
      out.write("</span></a></li>\r\n\t\t\t");
      out.write("\r\n\t\t\t<li><a class=\"myHelp\" href=\"http://help.nabaztag.com/index.php?langue=");
      out.print( dico_lang.getHelpLangId() );
      out.write("\"><span>");
      out.print(DicoTools.dico(dico_lang, "header/section_aide"));
      out.write("</span></a></li>\t\t\t\t\t\t\r\n\t\t</ul>\r\n\t</div>\r\n");
      out.write("\n\t</div>\n\n\t<div id=\"wrapper\"> ");
      out.write("\n\t\t\t<!-- COLONNE CENTRALE -->  \n\t\t\t  <div id=\"centercontent\" > \n\t\t\t\t<div class=\"tabNavContener top-tab-disabled\">\n\t\t\t\t\t<a href=\"myNablife.do\"><h1 class=\"icoTitle_Nablife\">&nbsp;</h1></a>\n\t\t\t\t\t<ul class=\"tabNav\">\n\t\t\t\t\t\t<li style=\"display:none\" id=\"AllServices\"><a href=\"");
      if (_jspx_meth_html_rewrite_1(_jspx_page_context))
        return;
      out.write("\"><span>");
      out.print(DicoTools.dico(dico_lang , "myNablife/tab_all_services"));
      out.write("</span></a></li>\n\t\t\t\t\t\t<li style=\"display:none\" id=\"Nabaztaland\"><a href=\"");
      if (_jspx_meth_html_rewrite_2(_jspx_page_context))
        return;
      out.write("\"><span>");
      out.print(DicoTools.dico(dico_lang , "myNablife/tab_nabaztaland"));
      out.write("</span></a></li>\n\t\t\t\t\t\t<li style=\"display:none;\" class=\"right\" id=\"Publier\"><a href=\"");
      if (_jspx_meth_html_rewrite_3(_jspx_page_context))
        return;
      out.write("\"><span>");
      out.print(DicoTools.dico(dico_lang , "myNablife/tab_publish_service"));
      out.write("</span></a></li>\t\t\t\t\t\n\t\t\t\t\t</ul>\n\t\t\t    </div>\n\t\t\t        <div class=\"mainTab\">\n\t\t\t          <div class=\"mainTabBody\">\n\t\t\t\t\t  \t<div id=\"contentAllServices\" class=\"contentMainTab srvlist-contener\">\n\t\t\t\t\t\t\t");
      out.write("\n\t\t\t\t\t\t</div>\n\t\t\t\n\t\t\t\t\t\t\n\t\t\t\t\t\t<div id=\"contentServicesByCateg\" class=\"contentMainTab srvlist-contener\">\n\t\t\t\t\t\t\t");
      out.write("\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\n\t\t\t\t\t\t<div id=\"contentNabaztaland\" class=\"contentMainTab srvlist-contener\">\n\t\t\t\t\t\t\t");
      out.write("\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\n\t\t\t\t\t\t<div id=\"contentPublier\" class=\"contentMainTab\">\n\t\t\t\t\t\t\t");
      out.write("\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t<div id=\"contentSrvConfig\" class=\"contentMainTab\" >\n\t\t\t\t\t\t\t");
      out.write("\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t<hr class=\"clearer\" />\n\t\t\t        </div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t\t<hr class=\"clearer\" />\n\t\t\t<!--/ COLONNE CENTRALE -->\n\t</div>\n\n\t<div id=\"leftcontent\"> ");
      out.write("\n\t\t<!-- COLONNE GAUCHE -->\n\t\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_29 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_29.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_29.setParent(null);
      _jspx_th_logic_equal_29.setName("userData");
      _jspx_th_logic_equal_29.setProperty("user_id");
      _jspx_th_logic_equal_29.setValue("0");
      int _jspx_eval_logic_equal_29 = _jspx_th_logic_equal_29.doStartTag();
      if (_jspx_eval_logic_equal_29 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write(' ');
 /* User NON connecté */
          out.write("\n\t\t\n\t\t\t<div id=\"bloc-homeBloc\" class=\"left-block manual\">\n\t\t\t\t<a href=\"myNablife.do\"><h1>&nbsp;</h1></a>\n\t\t\t\t<div class=\"block-content\">\n\t\t\t\t\t\n\t\t\t\t\t<div class=\"part website-language\"> ");
          out.write("\n\t\t\t\t\t\t<label>");
          out.print(DicoTools.dico(dico_lang , "bloc/home-interfaceLanguage"));
          out.write("</label>\n\t\t\t  \t\t\t");
          //  html:select
          org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_0 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_property_name.get(org.apache.struts.taglib.html.SelectTag.class);
          _jspx_th_html_select_0.setPageContext(_jspx_page_context);
          _jspx_th_html_select_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_29);
          _jspx_th_html_select_0.setName("myNablifeForm");
          _jspx_th_html_select_0.setProperty("langUser");
          int _jspx_eval_html_select_0 = _jspx_th_html_select_0.doStartTag();
          if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_html_select_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_html_select_0.doInitBody();
            }
            do {
              out.write("\n\t\t\t\t\t\t\t");
              //  logic:iterate
              org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
              _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_0);
              _jspx_th_logic_iterate_0.setName("myNablifeForm");
              _jspx_th_logic_iterate_0.setProperty("langList");
              _jspx_th_logic_iterate_0.setId("langData");
              int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
              if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object langData = null;
                if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_logic_iterate_0.doInitBody();
                }
                langData = (java.lang.Object) _jspx_page_context.findAttribute("langData");
                do {
                  out.write("\n\t\t\t\t\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_29 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_29.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_29.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_bean_define_29.setName("langData");
                  _jspx_th_bean_define_29.setProperty("lang_id");
                  _jspx_th_bean_define_29.setId("lang_id");
                  int _jspx_eval_bean_define_29 = _jspx_th_bean_define_29.doStartTag();
                  if (_jspx_th_bean_define_29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_29);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_29);
                  java.lang.Object lang_id = null;
                  lang_id = (java.lang.Object) _jspx_page_context.findAttribute("lang_id");
                  out.write("\n\t\t\t\t\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_30 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_30.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_30.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_bean_define_30.setName("langData");
                  _jspx_th_bean_define_30.setProperty("lang_title");
                  _jspx_th_bean_define_30.setId("lang_title");
                  int _jspx_eval_bean_define_30 = _jspx_th_bean_define_30.doStartTag();
                  if (_jspx_th_bean_define_30.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_30);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_30);
                  java.lang.Object lang_title = null;
                  lang_title = (java.lang.Object) _jspx_page_context.findAttribute("lang_title");
                  out.write("\n\t\t\t\t\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_31 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_31.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_31.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_bean_define_31.setName("langData");
                  _jspx_th_bean_define_31.setProperty("lang_type");
                  _jspx_th_bean_define_31.setId("lang_type");
                  int _jspx_eval_bean_define_31 = _jspx_th_bean_define_31.doStartTag();
                  if (_jspx_th_bean_define_31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_31);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_31);
                  java.lang.Object lang_type = null;
                  lang_type = (java.lang.Object) _jspx_page_context.findAttribute("lang_type");
                  out.write("\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t");
                  //  logic:lessEqual
                  org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_0 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
                  _jspx_th_logic_lessEqual_0.setPageContext(_jspx_page_context);
                  _jspx_th_logic_lessEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_logic_lessEqual_0.setName("lang_type");
                  _jspx_th_logic_lessEqual_0.setValue("0");
                  int _jspx_eval_logic_lessEqual_0 = _jspx_th_logic_lessEqual_0.doStartTag();
                  if (_jspx_eval_logic_lessEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t\t\t\t\t\t");
                      //  html:option
                      org.apache.struts.taglib.html.OptionTag _jspx_th_html_option_0 = (org.apache.struts.taglib.html.OptionTag) _jspx_tagPool_html_option_value.get(org.apache.struts.taglib.html.OptionTag.class);
                      _jspx_th_html_option_0.setPageContext(_jspx_page_context);
                      _jspx_th_html_option_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_0);
                      _jspx_th_html_option_0.setValue(lang_id.toString());
                      int _jspx_eval_html_option_0 = _jspx_th_html_option_0.doStartTag();
                      if (_jspx_eval_html_option_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        if (_jspx_eval_html_option_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                          out = _jspx_page_context.pushBody();
                          _jspx_th_html_option_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                          _jspx_th_html_option_0.doInitBody();
                        }
                        do {
                          out.print(lang_title.toString());
                          int evalDoAfterBody = _jspx_th_html_option_0.doAfterBody();
                          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                            break;
                        } while (true);
                        if (_jspx_eval_html_option_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                          out = _jspx_page_context.popBody();
                        }
                      }
                      if (_jspx_th_html_option_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_html_option_value.reuse(_jspx_th_html_option_0);
                        return;
                      }
                      _jspx_tagPool_html_option_value.reuse(_jspx_th_html_option_0);
                      out.write("\n\t\t\t\t\t\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_lessEqual_0.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_lessEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_0);
                    return;
                  }
                  _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_0);
                  out.write("\n\t\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
                  langData = (java.lang.Object) _jspx_page_context.findAttribute("langData");
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_logic_iterate_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_0);
                return;
              }
              _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_0);
              out.write("\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_html_select_0.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_html_select_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_select_property_name.reuse(_jspx_th_html_select_0);
            return;
          }
          _jspx_tagPool_html_select_property_name.reuse(_jspx_th_html_select_0);
          out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t</div>\n\t\t\t\t\t\n\t\t\t\t\t<div class=\"part\"> ");
          out.write("\n\t\t\t\t\t\t<h2>");
          out.print(DicoTools.dico(dico_lang , "bloc/home-gotAccountTitle"));
          out.write("</h2>\n\t\t\t\t\t\t<div class=\"content\">\n\t\t\t\t\t\t\t\t<div class=\"login\">\n\t\t\t\t\t\t\t\t\t");
          //  html:form
          org.apache.struts.taglib.html.FormTag _jspx_th_html_form_1 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_action.get(org.apache.struts.taglib.html.FormTag.class);
          _jspx_th_html_form_1.setPageContext(_jspx_page_context);
          _jspx_th_html_form_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_29);
          _jspx_th_html_form_1.setAction("/action/mySession");
          _jspx_th_html_form_1.setStyleId("login_form");
          int _jspx_eval_html_form_1 = _jspx_th_html_form_1.doStartTag();
          if (_jspx_eval_html_form_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_html_hidden_6(_jspx_th_html_form_1, _jspx_page_context))
                return;
              out.write("\n\t\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_html_hidden_7(_jspx_th_html_form_1, _jspx_page_context))
                return;
              out.write("\n\t\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_html_hidden_8(_jspx_th_html_form_1, _jspx_page_context))
                return;
              out.write("\n\t\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_html_hidden_9(_jspx_th_html_form_1, _jspx_page_context))
                return;
              out.write("\n\t\t\t\t\t\t\t\t\t\t<label>\n\t\t\t\t\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "myHome/nabname"));
              out.write("\n\t\t\t\t\t\t\t\t\t\t</label>\n\t\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_html_text_1(_jspx_th_html_form_1, _jspx_page_context))
                return;
              out.write("\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t<label>\n\t\t\t\t\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "myHome/motdepasse"));
              out.write("\n\t\t\t\t\t\t\t\t\t\t</label>\n\t\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_html_password_1(_jspx_th_html_form_1, _jspx_page_context))
                return;
              out.write("\n\t\n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_html_hidden_10(_jspx_th_html_form_1, _jspx_page_context))
                return;
              out.write("\n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t<div class=\"div-spacer\">\n\t\t\t\t\t\t\t\t\t\t\t<button type=\"submit\" class=\"little\" /><span>OK</span></button>\n\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_html_form_1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_html_form_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_form_styleId_action.reuse(_jspx_th_html_form_1);
            return;
          }
          _jspx_tagPool_html_form_styleId_action.reuse(_jspx_th_html_form_1);
          out.write("\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t<hr class=\"spacer\" />\t\t\t\t\t\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<a class=\"closed-arrow simple-link divChangeLink\" href=\"myHomePassword.do#recover-password\">\n\t\t\t\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "myHome/forgotten_password"));
          out.write("\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<div id=\"recover-password\"></div>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<hr class=\"clearer\" />\n\t\t\t\t\t\t</div>\t\n\t\t\t\t\t</div>\n\t\n\t\t\t\t\t<div class=\"part nabaztag-desc\">\n\t\t\t\t\t\t<div class=\"content\">\n\t\t\t\t\t\t\t<div class=\"lapinTrans\"></div>\n\t\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "bloc/about_nabaztag_description"));
          out.write("\n\t\t\t\t\t\t</div>\t\n\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t</div>\t\t\t\n\t\t\t</div>\n\t\t");
          int evalDoAfterBody = _jspx_th_logic_equal_29.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_29.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_29);
        return;
      }
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_29);
      out.write(" \t\n\t\t\n\t\t<div class=\"contener\"></div>\n\t\t\t\t\n\t\t<!-- /COLONNE GAUCHE -->\n\t</div>\n\n\t<div id=\"rightcontent\"> ");
      out.write("\n\t\t<!-- COLONNE DROITE -->\n\t\t\t<div class=\"manual right-block\" style=\"display:block; border: medium none ; background: transparent none repeat scroll 0%; min-height: 1px; height: 1%;\">\n\t\t\t\t<h1>");
      out.print(DicoTools.dico(dico_lang, "myNablife/rightBlocInfos_title"));
      out.write("</h1>\n\t\t\n\t\t\t\t<div class=\"block-content\">\n\t\t\t\t\t<div class=\"inner\">\t\n\t\t\t\t\t\t");
 String staticContent = "/include_jsp/static_content/"+dico_lang.getIsoCode()+"/nablife.home.jsp"; 
      out.write("\n\t\t\t\t\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, staticContent, out, false);
      out.write("\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t\t\n\t\t\t<div class=\"contener\"></div>\n\t\t\t\t\t\t\n\t\t<!-- /COLONNE DROTIE -->\n\t</div>\n\t<div id=\"footer\">\n\t\t<div class=\"copyright\">");
      out.print(DicoTools.dico(dico_lang , "footer/copyright"));
      out.write(" | <a href=\"#\">");
      out.print(DicoTools.dico(dico_lang , "footer/contact_link"));
      out.write("</a></div>\n\t</div>\n</div>\n\n<script language=\"javascript\">\n\n\t\n\tvar page = {};\n\n\t/* Poste le formulaire en Ajax */\n\tpage.postAjax = function(frmId, refreshDiv) {\n\n\t\tvar frm = $(\"#\" + frmId);\n\t\tfrm.ajaxSubmit({\n\t\t\tbeforeSubmit: function(formData,f,o){\n\t\t\t\tfrm.block();\n\t\t\t},\n\t\t\tsuccess: function(responseData) {\n\t\t\t\tfrm.unblock();\n\t\t\t},\n\t\t\ttarget: \"#\"+refreshDiv\n\t\t});\n\t}\n\n\tpage.loadInDiv = function(refresh, urlToLoad, extract){\n\n\t\tvar wait = \"<img src='/include_img/loading.gif' class='loader'/>\";\n\n\t\tvar now = new Date(); // create a timestamp to prevent browser caching of ajax request\n\t\turlToLoad += ((urlToLoad.indexOf(\"?\") == -1) ? \"?\" : \"&\") \n\t\t\t+ (\"timestamp=\" + now.getHours() + now.getMinutes() + now.getSeconds());\n\n\t\tif (extract != null && extract != \"\") {\n\t\t\t$(refresh).html(wait).load(urlToLoad + \" \" + extract);\n\n\t\t} else {\n\t\t\t$(refresh).html(wait).load(urlToLoad);\n\t\t}\n    \treturn false;\n\t}\n\n\t$(document).ready(function(){\n\n\t\t// active le lien mot de passe\n\t\t$(\"a.closed-arrow\").click(function(){\n\t\t\t$(this).toggleOpenClose();\n");
      out.write("\n\t\t\tif ( $(this).is(\".closed-arrow\") ) {\n\t\t\t\t$(\"#recover-password\").html(\"\");\n\t\t\t\t$(this).parents(\"div.part\").find(\".login\").show();\n\t\t\t} else {\n\t\t\t\tvar u = $(this).attr(\"href\");\n\t\t\t\tvar t;\n\t\t\t\t$(this).parents(\"div.part\").find(\".login\").hide();\n\t\t\t\tu = u.split(\"#\");\n\t\t\t\tt = u[1];\t// target\n\t\t\t\tu = u[0];\t// url\n\n\t\t\t\t$(\"#\"+t).divChangeUrl(u);\n\t\t\t}\n\t\t\treturn false;\t\t\t\t\n\t\t});\n\t\t\n\t\t\n\t\t\n\t\t\n\t\t$(\"div.website-language select\").change(function(){\n\t\t\tvar l = $(this).val();\n\t\t\tvar url = \"myNablife.do?langUser=\" + l;\n\t\t\tredirect(url);\n\t\t});\n\t\t\t\t\n\t\t\n\t\t");
      out.write('\n');
      out.write('	');
      out.write('	');
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_parameter.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_0.setParent(null);
      _jspx_th_logic_notEqual_0.setParameter("onglet");
      _jspx_th_logic_notEqual_0.setValue("");
      int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
      if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_32 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_32.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_32.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
          _jspx_th_bean_define_32.setId("onglet");
          _jspx_th_bean_define_32.setValue( request.getParameter("onglet") );
          int _jspx_eval_bean_define_32 = _jspx_th_bean_define_32.doStartTag();
          if (_jspx_th_bean_define_32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_32);
            return;
          }
          _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_32);
          onglet = (java.lang.String) _jspx_page_context.findAttribute("onglet");
          out.write("\n\t\t\t$('#");
          out.print(onglet);
          out.write(" a').trigger(\"click\");\n\t\t");
          int evalDoAfterBody = _jspx_th_logic_notEqual_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_notEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_notEqual_value_parameter.reuse(_jspx_th_logic_notEqual_0);
        return;
      }
      _jspx_tagPool_logic_notEqual_value_parameter.reuse(_jspx_th_logic_notEqual_0);
      out.write("\n\n\t\t");
      out.write('\n');
      out.write('	');
      out.write('	');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_30 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_parameter.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_30.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_30.setParent(null);
      _jspx_th_logic_equal_30.setParameter("onglet");
      _jspx_th_logic_equal_30.setValue("");
      int _jspx_eval_logic_equal_30 = _jspx_th_logic_equal_30.doStartTag();
      if (_jspx_eval_logic_equal_30 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t\t\n\t\t\t");
          out.write("\n\t\t\t");
          if (_jspx_meth_logic_equal_31(_jspx_th_logic_equal_30, _jspx_page_context))
            return;
          out.write("\n\t\t\t\n\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_30);
          _jspx_th_logic_notEqual_1.setName("goToListResult");
          _jspx_th_logic_notEqual_1.setValue("yes");
          int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
          if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_32 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_32.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_32.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
              _jspx_th_logic_equal_32.setName("myNablifeForm");
              _jspx_th_logic_equal_32.setProperty("fromSearch");
              _jspx_th_logic_equal_32.setValue("true");
              int _jspx_eval_logic_equal_32 = _jspx_th_logic_equal_32.doStartTag();
              if (_jspx_eval_logic_equal_32 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t\t\n\t\t\t\t\t");
                  //  logic:lessEqual
                  org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_3 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_property_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
                  _jspx_th_logic_lessEqual_3.setPageContext(_jspx_page_context);
                  _jspx_th_logic_lessEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_32);
                  _jspx_th_logic_lessEqual_3.setName("myNablifeForm");
                  _jspx_th_logic_lessEqual_3.setProperty("objectId");
                  _jspx_th_logic_lessEqual_3.setValue("0");
                  int _jspx_eval_logic_lessEqual_3 = _jspx_th_logic_lessEqual_3.doStartTag();
                  if (_jspx_eval_logic_lessEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t\t\t$('input[@name=redirectUrl]').val(\"/action/myNablife.do?search=");
                      out.print(request.getParameter("search"));
                      out.write("\");\n\t\t\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_lessEqual_3.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_lessEqual_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_lessEqual_value_property_name.reuse(_jspx_th_logic_lessEqual_3);
                    return;
                  }
                  _jspx_tagPool_logic_lessEqual_value_property_name.reuse(_jspx_th_logic_lessEqual_3);
                  out.write("\n\t\t\t\t\t\n\t\t\t\t\t// on affiche les résultats et les bons messages\n\t\t\t\t\t// Service/Podcast/RSS\n\t\t\t\t\t");
                  //  logic:notEmpty
                  org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
                  _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
                  _jspx_th_logic_notEmpty_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_32);
                  _jspx_th_logic_notEmpty_0.setName("myNablifeForm");
                  _jspx_th_logic_notEmpty_0.setProperty("srvListData");
                  int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
                  if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_33 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_33.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_33.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
                      _jspx_th_bean_define_33.setId("srvListData");
                      _jspx_th_bean_define_33.setName("myNablifeForm");
                      _jspx_th_bean_define_33.setProperty("srvListData");
                      int _jspx_eval_bean_define_33 = _jspx_th_bean_define_33.doStartTag();
                      if (_jspx_th_bean_define_33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_33);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_33);
                      java.lang.Object srvListData = null;
                      srvListData = (java.lang.Object) _jspx_page_context.findAttribute("srvListData");
                      out.write("\n\t\t\t\t\t\t\t\n\t\t\t\t\t\tmainTab_GoTab('AllServices');\n\t\t\t\t\t\tvar reg = new RegExp(\"&amp;\", \"g\");\n\t\t\t\t\t\tvar url = '");
                      if (_jspx_meth_bean_write_6(_jspx_th_logic_notEmpty_0, _jspx_page_context))
                        return;
                      out.write("';\n\t\t\t\t\t\turl = url.replace(reg, \"&\");\n\t\t\t\t\t\tnablife.loadSrvPage(url+'&dispatch=load&srvId=");
                      if (_jspx_meth_bean_write_7(_jspx_th_logic_notEmpty_0, _jspx_page_context))
                        return;
                      out.write("');\n\t\t\t\t\t\t\n\t\t\t\t\t");
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
                  out.write("\n\n\t\t\t\t\t\n\t\t\t\t\t// Nabcast\n\t\t\t\t\t");
                  //  logic:notEmpty
                  org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_1 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
                  _jspx_th_logic_notEmpty_1.setPageContext(_jspx_page_context);
                  _jspx_th_logic_notEmpty_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_32);
                  _jspx_th_logic_notEmpty_1.setName("myNablifeForm");
                  _jspx_th_logic_notEmpty_1.setProperty("nabcastData");
                  int _jspx_eval_logic_notEmpty_1 = _jspx_th_logic_notEmpty_1.doStartTag();
                  if (_jspx_eval_logic_notEmpty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_34 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_34.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_34.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                      _jspx_th_bean_define_34.setId("listNabcastData");
                      _jspx_th_bean_define_34.setName("myNablifeForm");
                      _jspx_th_bean_define_34.setProperty("nabcastData");
                      int _jspx_eval_bean_define_34 = _jspx_th_bean_define_34.doStartTag();
                      if (_jspx_th_bean_define_34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_34);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_34);
                      java.lang.Object listNabcastData = null;
                      listNabcastData = (java.lang.Object) _jspx_page_context.findAttribute("listNabcastData");
                      out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t\tmainTab_GoTab('Nabaztaland');\t\t\t\n\t\t\t\t\t\t\n\t\t\t\t\t\t");
                      //  logic:iterate
                      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
                      _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
                      _jspx_th_logic_iterate_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                      _jspx_th_logic_iterate_1.setId("nabcastData");
                      _jspx_th_logic_iterate_1.setName("listNabcastData");
                      int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
                      if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        java.lang.Object nabcastData = null;
                        if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                          out = _jspx_page_context.pushBody();
                          _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                          _jspx_th_logic_iterate_1.doInitBody();
                        }
                        nabcastData = (java.lang.Object) _jspx_page_context.findAttribute("nabcastData");
                        do {
                          out.write("\n\t\t\t\t\t\t\t//alert('Recherche nabcast [");
                          if (_jspx_meth_bean_write_8(_jspx_th_logic_iterate_1, _jspx_page_context))
                            return;
                          out.write("] trouvé : ");
                          if (_jspx_meth_bean_write_9(_jspx_th_logic_iterate_1, _jspx_page_context))
                            return;
                          out.write("');\n\t\t\t\t\t\t\tnablife.loadSrvPage('../action/myNabaztalandSubscribe.do?idNabcast=");
                          if (_jspx_meth_bean_write_10(_jspx_th_logic_iterate_1, _jspx_page_context))
                            return;
                          out.write("&searched=");
                          if (_jspx_meth_bean_write_11(_jspx_th_logic_iterate_1, _jspx_page_context))
                            return;
                          out.write("');\n\t\t\t\t\t\t");
                          int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
                          nabcastData = (java.lang.Object) _jspx_page_context.findAttribute("nabcastData");
                          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                            break;
                        } while (true);
                        if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                          out = _jspx_page_context.popBody();
                        }
                      }
                      if (_jspx_th_logic_iterate_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_logic_iterate_name_id.reuse(_jspx_th_logic_iterate_1);
                        return;
                      }
                      _jspx_tagPool_logic_iterate_name_id.reuse(_jspx_th_logic_iterate_1);
                      out.write("\n\t\t\t\t\t\n\t\t\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_notEmpty_1.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_notEmpty_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_1);
                    return;
                  }
                  _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_1);
                  out.write("\n\t\t\t\t\t\n\t\t\t\t\t");
                  if (_jspx_meth_logic_notEmpty_2(_jspx_th_logic_equal_32, _jspx_page_context))
                    return;
                  out.write("\n\t\t\t\t\t\n\t\t\t\t\t");
                  out.write("\n\t\t\t\t\t\n\t\t\t\t\t// Aucun résultat\n\t\t\t\t\t");
                  if (_jspx_meth_logic_empty_0(_jspx_th_logic_equal_32, _jspx_page_context))
                    return;
                  out.write("\n\t\t\t\t\t\n\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_equal_32.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_equal_32.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_32);
                return;
              }
              _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_32);
              out.write("\n\t\t\t\t\n\t\t\t\t");
              out.write("\t\n\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_33 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_33.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_33.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
              _jspx_th_logic_equal_33.setName("myNablifeForm");
              _jspx_th_logic_equal_33.setProperty("fromSearch");
              _jspx_th_logic_equal_33.setValue("false");
              int _jspx_eval_logic_equal_33 = _jspx_th_logic_equal_33.doStartTag();
              if (_jspx_eval_logic_equal_33 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t\t\n\t\t\t\t\t\t");
/* on veut acceder a une categorie en particulier */
                  out.write("\n\t\t\t\t\t\t");
                  //  logic:notEqual
                  org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                  _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
                  _jspx_th_logic_notEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_33);
                  _jspx_th_logic_notEqual_2.setName("myNablifeForm");
                  _jspx_th_logic_notEqual_2.setProperty("categoryId");
                  _jspx_th_logic_notEqual_2.setValue("-1");
                  int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
                  if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t\t\t\tmainTab_GoTab('AllServices', \"");
                      if (_jspx_meth_html_rewrite_4(_jspx_th_logic_notEqual_2, _jspx_page_context))
                        return;
                      out.write("?idCateg=");
                      out.print(categoryId.toString());
                      out.write("&mode=1&langCategorie=");
                      out.print(categoryLang.toString());
                      out.write("\");\n\t\t\t\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_notEqual_2.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_notEqual_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_2);
                    return;
                  }
                  _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_2);
                  out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t\t");
/* ou pas */
                  out.write("\n\t\t\t\t\t\t");
                  if (_jspx_meth_logic_equal_34(_jspx_th_logic_equal_33, _jspx_page_context))
                    return;
                  out.write("\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t");
 /* on arrive et direct on a pass un service en parametre */ 
                  out.write("\n\t\t\t\t\t\t");
                  //  logic:notEmpty
                  org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_3 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
                  _jspx_th_logic_notEmpty_3.setPageContext(_jspx_page_context);
                  _jspx_th_logic_notEmpty_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_33);
                  _jspx_th_logic_notEmpty_3.setName("myNablifeForm");
                  _jspx_th_logic_notEmpty_3.setProperty("serviceToConfigure");
                  int _jspx_eval_logic_notEmpty_3 = _jspx_th_logic_notEmpty_3.doStartTag();
                  if (_jspx_eval_logic_notEmpty_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t\t\t\tvar tmp = \"");
                      out.print(serviceToConfigure);
                      out.write("\";\n\t\t\t\t\t\t\ttmp = tmp.split(\"|\");\n\t\t\t\t\t\t\tvar url  = tmp[1];\n\t\t\t\t\t\t\tvar id \t = tmp[0];\n\t\t\t\t\t\t\tif (url!=null) {\n\t\t\t\t\t\t\t\tsrvConfigToggle(id, url);\n\t\t\t\t\t\t\t} else {\n\t\t\t\t\t\t\t\tsrvConfigToggleN(id);\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_notEmpty_3.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_notEmpty_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_3);
                    return;
                  }
                  _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_3);
                  out.write("\n\t\t\t\t\t\n\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_equal_33.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_equal_33.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_33);
                return;
              }
              _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_33);
              out.write("\n\t\t\t\t\n\n\t\t\t\t\n\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_35 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_35.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_35.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
              _jspx_th_logic_equal_35.setName("badLogin");
              _jspx_th_logic_equal_35.setValue("1");
              int _jspx_eval_logic_equal_35 = _jspx_th_logic_equal_35.doStartTag();
              if (_jspx_eval_logic_equal_35 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t\tcustomAlertN(\"");
                  out.print(DicoTools.dico(dico_lang , "myHome/error_login"));
                  out.write("\");\n\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_equal_35.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_equal_35.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_35);
                return;
              }
              _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_35);
              out.write("\n\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_notEqual_1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_notEqual_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_1);
            return;
          }
          _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_1);
          out.write('\n');
          out.write('	');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_equal_30.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_30.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_parameter.reuse(_jspx_th_logic_equal_30);
        return;
      }
      _jspx_tagPool_logic_equal_value_parameter.reuse(_jspx_th_logic_equal_30);
      out.write("\n\t\t\n\t\t");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_4 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_4.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_4.setParent(null);
      _jspx_th_logic_notEmpty_4.setName("myNablifeForm");
      _jspx_th_logic_notEmpty_4.setProperty("popup");
      int _jspx_eval_logic_notEmpty_4 = _jspx_th_logic_notEmpty_4.doStartTag();
      if (_jspx_eval_logic_notEmpty_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t\tTB_show('','../action/srvDialogConfig.do?dispatch=load&applicationId=");
          out.print(Application.NativeApplication.EARS_COMMUNION.getApplication().getId());
          out.write("&height=1&width=400');\n\t\t");
          int evalDoAfterBody = _jspx_th_logic_notEmpty_4.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_notEmpty_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_4);
        return;
      }
      _jspx_tagPool_logic_notEmpty_property_name.reuse(_jspx_th_logic_notEmpty_4);
      out.write("\n\t\t\t\t\t\t\n\t});\n\n\n\n</script>\n\n</body>\n</html>\n");
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
    org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
    _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_greaterThan_0.setParent(null);
    _jspx_th_logic_greaterThan_0.setName("userData");
    _jspx_th_logic_greaterThan_0.setProperty("user_id");
    _jspx_th_logic_greaterThan_0.setValue("0");
    int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
    if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\tnabaztag.constantes.ISLOG \t\t= true;\n\t");
        int evalDoAfterBody = _jspx_th_logic_greaterThan_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_greaterThan_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_0);
      return true;
    }
    _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_4.setParent(null);
    _jspx_th_logic_equal_4.setName("userData");
    _jspx_th_logic_equal_4.setProperty("user_id");
    _jspx_th_logic_equal_4.setValue("0");
    int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
    if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\tnabaztag.constantes.ISLOG \t\t= false;\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_4);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_4);
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
    _jspx_th_bean_write_0.setProperty("userWithAtLeastOneObject");
    int _jspx_eval_bean_write_0 = _jspx_th_bean_write_0.doStartTag();
    if (_jspx_th_bean_write_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
    return false;
  }

  private boolean _jspx_meth_logic_match_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
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
        out.write("true");
        int evalDoAfterBody = _jspx_th_logic_match_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_match_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_0);
      return true;
    }
    _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_0);
    return false;
  }

  private boolean _jspx_meth_logic_notMatch_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notMatch
    org.apache.struts.taglib.logic.NotMatchTag _jspx_th_logic_notMatch_0 = (org.apache.struts.taglib.logic.NotMatchTag) _jspx_tagPool_logic_notMatch_value_scope_name.get(org.apache.struts.taglib.logic.NotMatchTag.class);
    _jspx_th_logic_notMatch_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_notMatch_0.setParent(null);
    _jspx_th_logic_notMatch_0.setScope("page");
    _jspx_th_logic_notMatch_0.setName("isBatard");
    _jspx_th_logic_notMatch_0.setValue("true");
    int _jspx_eval_logic_notMatch_0 = _jspx_th_logic_notMatch_0.doStartTag();
    if (_jspx_eval_logic_notMatch_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_logic_notMatch_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notMatch_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notMatch_value_scope_name.reuse(_jspx_th_logic_notMatch_0);
      return true;
    }
    _jspx_tagPool_logic_notMatch_value_scope_name.reuse(_jspx_th_logic_notMatch_0);
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
        out.write("\n\t\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.home.js?v=1.1'></script>\n\t");
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

  private boolean _jspx_meth_logic_equal_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_6 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_6.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_6.setParent(null);
    _jspx_th_logic_equal_6.setName("page_title");
    _jspx_th_logic_equal_6.setValue("myNablife");
    int _jspx_eval_logic_equal_6 = _jspx_th_logic_equal_6.doStartTag();
    if (_jspx_eval_logic_equal_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.nablife.js?v=1.1'></script>\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_6);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_6);
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
    _jspx_th_logic_equal_7.setValue("myTerrier");
    int _jspx_eval_logic_equal_7 = _jspx_th_logic_equal_7.doStartTag();
    if (_jspx_eval_logic_equal_7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.terrier.js?v=1.1'></script>\n\t");
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

  private boolean _jspx_meth_logic_equal_8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_8 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_8.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_8.setParent(null);
    _jspx_th_logic_equal_8.setName("page_title");
    _jspx_th_logic_equal_8.setValue("myMessages");
    int _jspx_eval_logic_equal_8 = _jspx_th_logic_equal_8.doStartTag();
    if (_jspx_eval_logic_equal_8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('\n');
        out.write('	');
        out.write('	');
        out.write("\n\t\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.home.js?v=1.1'></script>\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_8);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_8);
    return false;
  }

  private boolean _jspx_meth_logic_greaterThan_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:greaterThan
    org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_1 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
    _jspx_th_logic_greaterThan_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_greaterThan_1.setParent(null);
    _jspx_th_logic_greaterThan_1.setName("idLapin");
    _jspx_th_logic_greaterThan_1.setValue("0");
    int _jspx_eval_logic_greaterThan_1 = _jspx_th_logic_greaterThan_1.doStartTag();
    if (_jspx_eval_logic_greaterThan_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write(" \r\n\t\t<script language=\"javascript\">\r\n\t\t\t$(document).ready(function(){\r\n\t\t\t\tvar myLink = $(\"div.copyright a[@href*='m-12']\");\r\n\t\t\t\t$(myLink).attr(\"href\", \"myMessages.do?action=messages_TellFriend\");\r\n\t\t\t\t$(myLink).removeAttr(\"target\");\r\n\t\t\t});\r\n\t\t</script>\r\n\t");
        int evalDoAfterBody = _jspx_th_logic_greaterThan_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_greaterThan_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_1);
      return true;
    }
    _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_1);
    return false;
  }

  private boolean _jspx_meth_logic_equal_14(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_14 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_14.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_14.setParent(null);
    _jspx_th_logic_equal_14.setName("page_title");
    _jspx_th_logic_equal_14.setValue("myNewAccount");
    int _jspx_eval_logic_equal_14 = _jspx_th_logic_equal_14.doStartTag();
    if (_jspx_eval_logic_equal_14 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_logic_equal_15(_jspx_th_logic_equal_14, _jspx_page_context))
          return true;
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

  private boolean _jspx_meth_logic_equal_15(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_14, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_15 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_15.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_14);
    _jspx_th_logic_equal_15.setName("userData");
    _jspx_th_logic_equal_15.setProperty("user_id");
    _jspx_th_logic_equal_15.setValue("0");
    int _jspx_eval_logic_equal_15 = _jspx_th_logic_equal_15.doStartTag();
    if (_jspx_eval_logic_equal_15 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("style=\"display:none;\"");
        int evalDoAfterBody = _jspx_th_logic_equal_15.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_15);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_15);
    return false;
  }

  private boolean _jspx_meth_logic_equal_16(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_16 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_16.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_16.setParent(null);
    _jspx_th_logic_equal_16.setName("page_title");
    _jspx_th_logic_equal_16.setValue("myNablife");
    int _jspx_eval_logic_equal_16 = _jspx_th_logic_equal_16.doStartTag();
    if (_jspx_eval_logic_equal_16 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        if (_jspx_meth_logic_equal_17(_jspx_th_logic_equal_16, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_logic_equal_16.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_16);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_16);
    return false;
  }

  private boolean _jspx_meth_logic_equal_17(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_16, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_17 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_17.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_17.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_16);
    _jspx_th_logic_equal_17.setName("userData");
    _jspx_th_logic_equal_17.setProperty("user_id");
    _jspx_th_logic_equal_17.setValue("0");
    int _jspx_eval_logic_equal_17 = _jspx_th_logic_equal_17.doStartTag();
    if (_jspx_eval_logic_equal_17 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("style=\"display:none;\"");
        int evalDoAfterBody = _jspx_th_logic_equal_17.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_17);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_17);
    return false;
  }

  private boolean _jspx_meth_bean_write_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_1 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_1.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_1.setParent(null);
    _jspx_th_bean_write_1.setName("userData");
    _jspx_th_bean_write_1.setProperty("user_lang");
    int _jspx_eval_bean_write_1 = _jspx_th_bean_write_1.doStartTag();
    if (_jspx_th_bean_write_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_1);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_1);
    return false;
  }

  private boolean _jspx_meth_logic_equal_18(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_18 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_18.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_18.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_2);
    _jspx_th_logic_equal_18.setName("userData");
    _jspx_th_logic_equal_18.setProperty("user_image");
    _jspx_th_logic_equal_18.setValue("0");
    int _jspx_eval_logic_equal_18 = _jspx_th_logic_equal_18.doStartTag();
    if (_jspx_eval_logic_equal_18 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t\t<img class=\"photo user_picture\" align=\"left\" src=\"../photo/default_S.jpg\" height=\"42\" border=\"0\">\r\n\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_18.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_18);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_18);
    return false;
  }

  private boolean _jspx_meth_logic_equal_21(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_21 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_21.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_21.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_equal_21.setName("page_title");
    _jspx_th_logic_equal_21.setValue("myHome");
    int _jspx_eval_logic_equal_21 = _jspx_th_logic_equal_21.doStartTag();
    if (_jspx_eval_logic_equal_21 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t");
        if (_jspx_meth_html_hidden_0(_jspx_th_logic_equal_21, _jspx_page_context))
          return true;
        out.write("\r\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_21.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_21);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_21);
    return false;
  }

  private boolean _jspx_meth_html_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_21, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_21);
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

  private boolean _jspx_meth_logic_equal_22(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_22 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_22.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_22.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_equal_22.setName("page_title");
    _jspx_th_logic_equal_22.setValue("myMessages");
    int _jspx_eval_logic_equal_22 = _jspx_th_logic_equal_22.doStartTag();
    if (_jspx_eval_logic_equal_22 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\t\r\n\t\t\t\t\t\t");
        if (_jspx_meth_html_hidden_1(_jspx_th_logic_equal_22, _jspx_page_context))
          return true;
        out.write("\r\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_22.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_22);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_22);
    return false;
  }

  private boolean _jspx_meth_html_hidden_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_22, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_22);
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

  private boolean _jspx_meth_logic_equal_23(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_23 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_23.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_23.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_equal_23.setName("page_title");
    _jspx_th_logic_equal_23.setValue("myTerrier");
    int _jspx_eval_logic_equal_23 = _jspx_th_logic_equal_23.doStartTag();
    if (_jspx_eval_logic_equal_23 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t");
        if (_jspx_meth_html_hidden_2(_jspx_th_logic_equal_23, _jspx_page_context))
          return true;
        out.write("\t\t\r\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_23.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_23);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_23);
    return false;
  }

  private boolean _jspx_meth_html_hidden_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_23, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_2 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_2.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_23);
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

  private boolean _jspx_meth_logic_equal_24(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_24 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_24.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_24.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_equal_24.setName("page_title");
    _jspx_th_logic_equal_24.setValue("myNablife");
    int _jspx_eval_logic_equal_24 = _jspx_th_logic_equal_24.doStartTag();
    if (_jspx_eval_logic_equal_24 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t");
        if (_jspx_meth_html_hidden_3(_jspx_th_logic_equal_24, _jspx_page_context))
          return true;
        out.write("\t\r\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_24.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_24.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_24);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_24);
    return false;
  }

  private boolean _jspx_meth_html_hidden_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_24, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_3 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_3.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_24);
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

  private boolean _jspx_meth_html_rewrite_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_0 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_0.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_3);
    _jspx_th_html_rewrite_0.setForward("goSession");
    int _jspx_eval_html_rewrite_0 = _jspx_th_html_rewrite_0.doStartTag();
    if (_jspx_th_html_rewrite_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_26(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_26 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_26.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_26.setParent(null);
    _jspx_th_logic_equal_26.setName("page_title");
    _jspx_th_logic_equal_26.setValue("myNablife");
    int _jspx_eval_logic_equal_26 = _jspx_th_logic_equal_26.doStartTag();
    if (_jspx_eval_logic_equal_26 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('O');
        out.write('N');
        int evalDoAfterBody = _jspx_th_logic_equal_26.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_26.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_26);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_26);
    return false;
  }

  private boolean _jspx_meth_logic_equal_27(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_27 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_27.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_27.setParent(null);
    _jspx_th_logic_equal_27.setName("page_title");
    _jspx_th_logic_equal_27.setValue("myMessages");
    int _jspx_eval_logic_equal_27 = _jspx_th_logic_equal_27.doStartTag();
    if (_jspx_eval_logic_equal_27 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('O');
        out.write('N');
        int evalDoAfterBody = _jspx_th_logic_equal_27.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_27.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_27);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_27);
    return false;
  }

  private boolean _jspx_meth_logic_equal_28(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_28 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_28.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_28.setParent(null);
    _jspx_th_logic_equal_28.setName("page_title");
    _jspx_th_logic_equal_28.setValue("myTerrier");
    int _jspx_eval_logic_equal_28 = _jspx_th_logic_equal_28.doStartTag();
    if (_jspx_eval_logic_equal_28 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('O');
        out.write('N');
        int evalDoAfterBody = _jspx_th_logic_equal_28.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_28.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_28);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_28);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_1 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_1.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_1.setParent(null);
    _jspx_th_html_rewrite_1.setForward("goServicesHome");
    int _jspx_eval_html_rewrite_1 = _jspx_th_html_rewrite_1.doStartTag();
    if (_jspx_th_html_rewrite_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_1);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_1);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_2 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_2.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_2.setParent(null);
    _jspx_th_html_rewrite_2.setForward("goNabcastHome");
    int _jspx_eval_html_rewrite_2 = _jspx_th_html_rewrite_2.doStartTag();
    if (_jspx_th_html_rewrite_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_2);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_2);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_3 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_3.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_3.setParent(null);
    _jspx_th_html_rewrite_3.setForward("goNabcastCreate");
    int _jspx_eval_html_rewrite_3 = _jspx_th_html_rewrite_3.doStartTag();
    if (_jspx_th_html_rewrite_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_3);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_3);
    return false;
  }

  private boolean _jspx_meth_html_hidden_6(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_6 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_6.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
    _jspx_th_html_hidden_6.setName("mySessionForm");
    _jspx_th_html_hidden_6.setProperty("action");
    _jspx_th_html_hidden_6.setValue("connect");
    int _jspx_eval_html_hidden_6 = _jspx_th_html_hidden_6.doStartTag();
    if (_jspx_th_html_hidden_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_6);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_6);
    return false;
  }

  private boolean _jspx_meth_html_hidden_7(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_7 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_7.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
    _jspx_th_html_hidden_7.setName("mySessionForm");
    _jspx_th_html_hidden_7.setProperty("forward");
    _jspx_th_html_hidden_7.setValue("messages");
    int _jspx_eval_html_hidden_7 = _jspx_th_html_hidden_7.doStartTag();
    if (_jspx_th_html_hidden_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_7);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_7);
    return false;
  }

  private boolean _jspx_meth_html_hidden_8(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_8 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_8.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
    _jspx_th_html_hidden_8.setName("mySessionForm");
    _jspx_th_html_hidden_8.setProperty("url");
    _jspx_th_html_hidden_8.setValue("goNablife");
    int _jspx_eval_html_hidden_8 = _jspx_th_html_hidden_8.doStartTag();
    if (_jspx_th_html_hidden_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_8);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_8);
    return false;
  }

  private boolean _jspx_meth_html_hidden_9(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_9 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_9.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
    _jspx_th_html_hidden_9.setName("mySessionForm");
    _jspx_th_html_hidden_9.setProperty("redirectUrlBadLogin");
    _jspx_th_html_hidden_9.setValue("myNablife.do?badLogin=1");
    int _jspx_eval_html_hidden_9 = _jspx_th_html_hidden_9.doStartTag();
    if (_jspx_th_html_hidden_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_9);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_9);
    return false;
  }

  private boolean _jspx_meth_html_text_1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_1 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_property_name_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_1.setPageContext(_jspx_page_context);
    _jspx_th_html_text_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
    _jspx_th_html_text_1.setName("mySessionForm");
    _jspx_th_html_text_1.setProperty("pseudo");
    int _jspx_eval_html_text_1 = _jspx_th_html_text_1.doStartTag();
    if (_jspx_th_html_text_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_property_name_nobody.reuse(_jspx_th_html_text_1);
      return true;
    }
    _jspx_tagPool_html_text_property_name_nobody.reuse(_jspx_th_html_text_1);
    return false;
  }

  private boolean _jspx_meth_html_password_1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:password
    org.apache.struts.taglib.html.PasswordTag _jspx_th_html_password_1 = (org.apache.struts.taglib.html.PasswordTag) _jspx_tagPool_html_password_property_name_nobody.get(org.apache.struts.taglib.html.PasswordTag.class);
    _jspx_th_html_password_1.setPageContext(_jspx_page_context);
    _jspx_th_html_password_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
    _jspx_th_html_password_1.setName("mySessionForm");
    _jspx_th_html_password_1.setProperty("password");
    int _jspx_eval_html_password_1 = _jspx_th_html_password_1.doStartTag();
    if (_jspx_th_html_password_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_password_property_name_nobody.reuse(_jspx_th_html_password_1);
      return true;
    }
    _jspx_tagPool_html_password_property_name_nobody.reuse(_jspx_th_html_password_1);
    return false;
  }

  private boolean _jspx_meth_html_hidden_10(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_10 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_10.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
    _jspx_th_html_hidden_10.setName("mySessionForm");
    _jspx_th_html_hidden_10.setProperty("redirectUrl");
    _jspx_th_html_hidden_10.setValue("myNablife.do");
    int _jspx_eval_html_hidden_10 = _jspx_th_html_hidden_10.doStartTag();
    if (_jspx_th_html_hidden_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_10);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_10);
    return false;
  }

  private boolean _jspx_meth_logic_equal_31(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_30, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_31 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_31.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_31.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_30);
    _jspx_th_logic_equal_31.setName("goToListResult");
    _jspx_th_logic_equal_31.setValue("yes");
    int _jspx_eval_logic_equal_31 = _jspx_th_logic_equal_31.doStartTag();
    if (_jspx_eval_logic_equal_31 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\tmainTab_GoTab('AllServices');\n\t\t\t\t");
        if (_jspx_meth_logic_greaterThan_4(_jspx_th_logic_equal_31, _jspx_page_context))
          return true;
        out.write("\n\t\t\t\t");
        if (_jspx_meth_logic_lessEqual_2(_jspx_th_logic_equal_31, _jspx_page_context))
          return true;
        out.write("\n\t\t\t\t$(\"#contentSrvConfig\").show();\n\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_31.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_31.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_31);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_31);
    return false;
  }

  private boolean _jspx_meth_logic_greaterThan_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_31, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:greaterThan
    org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_4 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
    _jspx_th_logic_greaterThan_4.setPageContext(_jspx_page_context);
    _jspx_th_logic_greaterThan_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_31);
    _jspx_th_logic_greaterThan_4.setName("user_id");
    _jspx_th_logic_greaterThan_4.setValue("0");
    int _jspx_eval_logic_greaterThan_4 = _jspx_th_logic_greaterThan_4.doStartTag();
    if (_jspx_eval_logic_greaterThan_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\t\t");
        if (_jspx_meth_logic_lessEqual_1(_jspx_th_logic_greaterThan_4, _jspx_page_context))
          return true;
        out.write("\n\t\t\t\t\t");
        if (_jspx_meth_logic_greaterThan_5(_jspx_th_logic_greaterThan_4, _jspx_page_context))
          return true;
        out.write("\n\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_greaterThan_4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_greaterThan_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_4);
      return true;
    }
    _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_4);
    return false;
  }

  private boolean _jspx_meth_logic_lessEqual_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:lessEqual
    org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_1 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
    _jspx_th_logic_lessEqual_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_lessEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_4);
    _jspx_th_logic_lessEqual_1.setName("user_main");
    _jspx_th_logic_lessEqual_1.setValue("0");
    int _jspx_eval_logic_lessEqual_1 = _jspx_th_logic_lessEqual_1.doStartTag();
    if (_jspx_eval_logic_lessEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\t\t\t$(\"#bloc-DecouvrirNabaztag\").hide();\n\t\t\t\t\t\t//$(\"#contentSrvConfig\").load(\"../action/myShortcutState.do?badLogin=");
        if (_jspx_meth_bean_write_2(_jspx_th_logic_lessEqual_1, _jspx_page_context))
          return true;
        out.write("\");\n\t\t\t\t\t\tdivChangeUrl(\"contentSrvConfig\", '../action/myShortcutState.do?badLogin=");
        if (_jspx_meth_bean_write_3(_jspx_th_logic_lessEqual_1, _jspx_page_context))
          return true;
        out.write("');\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_lessEqual_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_lessEqual_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_1);
      return true;
    }
    _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_1);
    return false;
  }

  private boolean _jspx_meth_bean_write_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_lessEqual_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_2 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_2.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_1);
    _jspx_th_bean_write_2.setName("myNablifeForm");
    _jspx_th_bean_write_2.setProperty("badLogin");
    int _jspx_eval_bean_write_2 = _jspx_th_bean_write_2.doStartTag();
    if (_jspx_th_bean_write_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_2);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_2);
    return false;
  }

  private boolean _jspx_meth_bean_write_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_lessEqual_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_3 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_3.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_1);
    _jspx_th_bean_write_3.setName("myNablifeForm");
    _jspx_th_bean_write_3.setProperty("badLogin");
    int _jspx_eval_bean_write_3 = _jspx_th_bean_write_3.doStartTag();
    if (_jspx_th_bean_write_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_3);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_3);
    return false;
  }

  private boolean _jspx_meth_logic_greaterThan_5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:greaterThan
    org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_5 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
    _jspx_th_logic_greaterThan_5.setPageContext(_jspx_page_context);
    _jspx_th_logic_greaterThan_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_4);
    _jspx_th_logic_greaterThan_5.setName("user_main");
    _jspx_th_logic_greaterThan_5.setValue("0");
    int _jspx_eval_logic_greaterThan_5 = _jspx_th_logic_greaterThan_5.doStartTag();
    if (_jspx_eval_logic_greaterThan_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\t\t\t//$(\"#contentSrvConfig\").load(\"../action/myNablifeResultSearch.do\");\n\t\t\t\t\t\tdivChangeUrl(\"contentSrvConfig\", \"../action/myNablifeResultSearch.do\");\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_greaterThan_5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_greaterThan_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_5);
      return true;
    }
    _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_5);
    return false;
  }

  private boolean _jspx_meth_logic_lessEqual_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_31, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:lessEqual
    org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_2 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
    _jspx_th_logic_lessEqual_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_lessEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_31);
    _jspx_th_logic_lessEqual_2.setName("user_id");
    _jspx_th_logic_lessEqual_2.setValue("0");
    int _jspx_eval_logic_lessEqual_2 = _jspx_th_logic_lessEqual_2.doStartTag();
    if (_jspx_eval_logic_lessEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\t\t$(\"#bloc-DecouvrirNabaztag\").hide();\n\t\t\t\t\t//$(\"#contentSrvConfig\").load(\"../action/myShortcutState.do?badLogin=");
        if (_jspx_meth_bean_write_4(_jspx_th_logic_lessEqual_2, _jspx_page_context))
          return true;
        out.write("\");\n\t\t\t\t\tdivChangeUrl(\"contentSrvConfig\", \"../action/myShortcutState.do?badLogin=");
        if (_jspx_meth_bean_write_5(_jspx_th_logic_lessEqual_2, _jspx_page_context))
          return true;
        out.write("\");\n\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_lessEqual_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_lessEqual_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_2);
      return true;
    }
    _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_2);
    return false;
  }

  private boolean _jspx_meth_bean_write_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_lessEqual_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_4 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_4.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_2);
    _jspx_th_bean_write_4.setName("myNablifeForm");
    _jspx_th_bean_write_4.setProperty("badLogin");
    int _jspx_eval_bean_write_4 = _jspx_th_bean_write_4.doStartTag();
    if (_jspx_th_bean_write_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_4);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_4);
    return false;
  }

  private boolean _jspx_meth_bean_write_5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_lessEqual_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_5 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_5.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_2);
    _jspx_th_bean_write_5.setName("myNablifeForm");
    _jspx_th_bean_write_5.setProperty("badLogin");
    int _jspx_eval_bean_write_5 = _jspx_th_bean_write_5.doStartTag();
    if (_jspx_th_bean_write_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_5);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_5);
    return false;
  }

  private boolean _jspx_meth_bean_write_6(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_6 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_6.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
    _jspx_th_bean_write_6.setName("srvListData");
    _jspx_th_bean_write_6.setProperty("link");
    int _jspx_eval_bean_write_6 = _jspx_th_bean_write_6.doStartTag();
    if (_jspx_th_bean_write_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_6);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_6);
    return false;
  }

  private boolean _jspx_meth_bean_write_7(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_7 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_7.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
    _jspx_th_bean_write_7.setName("srvListData");
    _jspx_th_bean_write_7.setProperty("id");
    int _jspx_eval_bean_write_7 = _jspx_th_bean_write_7.doStartTag();
    if (_jspx_th_bean_write_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_7);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_7);
    return false;
  }

  private boolean _jspx_meth_bean_write_8(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_8 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_8.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_bean_write_8.setName("myNablifeForm");
    _jspx_th_bean_write_8.setProperty("searched");
    int _jspx_eval_bean_write_8 = _jspx_th_bean_write_8.doStartTag();
    if (_jspx_th_bean_write_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_8);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_8);
    return false;
  }

  private boolean _jspx_meth_bean_write_9(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_9 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_9.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_bean_write_9.setName("nabcastData");
    _jspx_th_bean_write_9.setProperty("nabcast_title");
    int _jspx_eval_bean_write_9 = _jspx_th_bean_write_9.doStartTag();
    if (_jspx_th_bean_write_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_9);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_9);
    return false;
  }

  private boolean _jspx_meth_bean_write_10(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_10 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_10.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_bean_write_10.setName("nabcastData");
    _jspx_th_bean_write_10.setProperty("nabcast_id");
    int _jspx_eval_bean_write_10 = _jspx_th_bean_write_10.doStartTag();
    if (_jspx_th_bean_write_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_10);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_10);
    return false;
  }

  private boolean _jspx_meth_bean_write_11(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_11 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_11.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_bean_write_11.setName("myNablifeForm");
    _jspx_th_bean_write_11.setProperty("searched");
    int _jspx_eval_bean_write_11 = _jspx_th_bean_write_11.doStartTag();
    if (_jspx_th_bean_write_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_11);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_11);
    return false;
  }

  private boolean _jspx_meth_logic_notEmpty_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_32, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEmpty
    org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_2 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
    _jspx_th_logic_notEmpty_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEmpty_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_32);
    _jspx_th_logic_notEmpty_2.setName("idNabCast");
    int _jspx_eval_logic_notEmpty_2 = _jspx_th_logic_notEmpty_2.doStartTag();
    if (_jspx_eval_logic_notEmpty_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\t\t\t//srvConfigToggle('id_");
        if (_jspx_meth_bean_write_12(_jspx_th_logic_notEmpty_2, _jspx_page_context))
          return true;
        out.write("_1', '../action/myNabaztalandSubscribe.do?idNabcast=");
        if (_jspx_meth_bean_write_13(_jspx_th_logic_notEmpty_2, _jspx_page_context))
          return true;
        out.write("');\n\t\t\t\t\t\tnablife.loadSrvPage('../action/myNabaztalandSubscribe.do?idNabcast=");
        if (_jspx_meth_bean_write_14(_jspx_th_logic_notEmpty_2, _jspx_page_context))
          return true;
        out.write("');\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_notEmpty_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEmpty_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEmpty_name.reuse(_jspx_th_logic_notEmpty_2);
      return true;
    }
    _jspx_tagPool_logic_notEmpty_name.reuse(_jspx_th_logic_notEmpty_2);
    return false;
  }

  private boolean _jspx_meth_bean_write_12(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_12 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_12.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_2);
    _jspx_th_bean_write_12.setName("idNabCast");
    int _jspx_eval_bean_write_12 = _jspx_th_bean_write_12.doStartTag();
    if (_jspx_th_bean_write_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_12);
      return true;
    }
    _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_12);
    return false;
  }

  private boolean _jspx_meth_bean_write_13(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_13 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_13.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_2);
    _jspx_th_bean_write_13.setName("idNabCast");
    int _jspx_eval_bean_write_13 = _jspx_th_bean_write_13.doStartTag();
    if (_jspx_th_bean_write_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_13);
      return true;
    }
    _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_13);
    return false;
  }

  private boolean _jspx_meth_bean_write_14(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_14 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_14.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_2);
    _jspx_th_bean_write_14.setName("idNabCast");
    int _jspx_eval_bean_write_14 = _jspx_th_bean_write_14.doStartTag();
    if (_jspx_th_bean_write_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_14);
      return true;
    }
    _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_14);
    return false;
  }

  private boolean _jspx_meth_logic_empty_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_32, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:empty
    org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_0 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
    _jspx_th_logic_empty_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_empty_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_32);
    _jspx_th_logic_empty_0.setName("myNablifeForm");
    _jspx_th_logic_empty_0.setProperty("srvListData");
    int _jspx_eval_logic_empty_0 = _jspx_th_logic_empty_0.doStartTag();
    if (_jspx_eval_logic_empty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\t\t\t");
        if (_jspx_meth_logic_empty_1(_jspx_th_logic_empty_0, _jspx_page_context))
          return true;
        out.write("\n\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_empty_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_empty_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_0);
      return true;
    }
    _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_0);
    return false;
  }

  private boolean _jspx_meth_logic_empty_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_empty_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:empty
    org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_1 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
    _jspx_th_logic_empty_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_empty_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_empty_0);
    _jspx_th_logic_empty_1.setName("myNablifeForm");
    _jspx_th_logic_empty_1.setProperty("nabcastData");
    int _jspx_eval_logic_empty_1 = _jspx_th_logic_empty_1.doStartTag();
    if (_jspx_eval_logic_empty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\t\t\t\t");
        if (_jspx_meth_logic_empty_2(_jspx_th_logic_empty_1, _jspx_page_context))
          return true;
        out.write("\n\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_empty_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_empty_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_1);
      return true;
    }
    _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_1);
    return false;
  }

  private boolean _jspx_meth_logic_empty_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_empty_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:empty
    org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_2 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
    _jspx_th_logic_empty_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_empty_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_empty_1);
    _jspx_th_logic_empty_2.setName("myNablifeForm");
    _jspx_th_logic_empty_2.setProperty("userData");
    int _jspx_eval_logic_empty_2 = _jspx_th_logic_empty_2.doStartTag();
    if (_jspx_eval_logic_empty_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\t\t\t\t\tgoService('../include_jsp/myNablife/serviceBadShortcut.jsp',-1);\n\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_empty_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_empty_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_2);
      return true;
    }
    _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_2);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_4 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_4.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_2);
    _jspx_th_html_rewrite_4.setForward("goServicesHome");
    int _jspx_eval_html_rewrite_4 = _jspx_th_html_rewrite_4.doStartTag();
    if (_jspx_th_html_rewrite_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_4);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_4);
    return false;
  }

  private boolean _jspx_meth_logic_equal_34(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_33, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_34 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_34.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_34.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_33);
    _jspx_th_logic_equal_34.setName("myNablifeForm");
    _jspx_th_logic_equal_34.setProperty("categoryId");
    _jspx_th_logic_equal_34.setValue("-1");
    int _jspx_eval_logic_equal_34 = _jspx_th_logic_equal_34.doStartTag();
    if (_jspx_eval_logic_equal_34 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t\t\t\t\tmainTab_GoTab('AllServices', \"");
        if (_jspx_meth_html_rewrite_5(_jspx_th_logic_equal_34, _jspx_page_context))
          return true;
        out.write("\", null, nablife.init );\n\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_34.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_34.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_34);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_34);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_34, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_5 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_5.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_34);
    _jspx_th_html_rewrite_5.setForward("goServicesHome");
    int _jspx_eval_html_rewrite_5 = _jspx_th_html_rewrite_5.doStartTag();
    if (_jspx_th_html_rewrite_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_5);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_5);
    return false;
  }
}
