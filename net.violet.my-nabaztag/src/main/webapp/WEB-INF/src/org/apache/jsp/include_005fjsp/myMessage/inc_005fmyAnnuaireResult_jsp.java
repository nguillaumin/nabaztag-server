package org.apache.jsp.include_005fjsp.myMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyAnnuaireResult_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_write_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_empty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_write_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_empty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_bean_write_property_name_nobody.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_greaterEqual_value_property_name.release();
    _jspx_tagPool_logic_empty_property_name.release();
    _jspx_tagPool_logic_greaterThan_value_property_name.release();
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

      out.write("\n\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myAnnuaireResultForm");
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
      out.write("\r\n\t<!-- <strong>");
      out.print(DicoTools.dico(dico_lang , "directory/results_search_contains"));
      out.write("&nbsp;");
      if (_jspx_meth_bean_write_0(_jspx_page_context))
        return;
      out.write("&nbsp;");
      out.print(DicoTools.dico(dico_lang , "directory/results_nabz_in"));
      out.write("&nbsp;");
      if (_jspx_meth_bean_write_1(_jspx_page_context))
        return;
      out.write("&nbsp;");
      out.print(DicoTools.dico(dico_lang , "directory/results_pages"));
      out.write("\t</strong> -->\r\n\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"4\">\r\n\t\t<tr>\r\n\t\t\t<td>\r\n\t\t\t\t<strong>");
      out.print(DicoTools.dico(dico_lang , "directory/results_sort_by"));
      out.write("</strong>\r\n\t\t\t</td>\r\n\t\t\t<td width=\"35\" align=\"right\" nowrap=\"nowrap\">\r\n\t\t\t\t<strong>");
      out.print(DicoTools.dico(dico_lang , "directory/results_name"));
      out.write("</strong>\r\n\t\t\t</td>\r\n\t\t\t<td nowrap=\"nowrap\">\r\n\t\t\t\t<table width=\"1%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"upanddown\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '1');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_up.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '1');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_down.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n\t\t\t</td>\r\n\t\t\t<td width=\"35\" align=\"right\" nowrap=\"nowrap\">\r\n\t\t\t\t<strong>");
      out.print(DicoTools.dico(dico_lang , "directory/results_age"));
      out.write("</strong>\r\n\t\t\t</td>\r\n\t\t\t<td nowrap=\"nowrap\">\r\n\t\t\t\t<table width=\"1%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"upanddown\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '2');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_up.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '2');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_down.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n\t\t\t</td>\r\n\t\t\t<td width=\"35\" align=\"right\" nowrap=\"nowrap\">\r\n\t\t\t\t<strong>");
      out.print(DicoTools.dico(dico_lang , "directory/results_sexe"));
      out.write("</strong>\r\n\t\t\t</td>\r\n\t\t\t<td nowrap=\"nowrap\">\r\n\t\t\t\t<table width=\"1%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"upanddown\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '3');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_up.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '3');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_down.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n\t\t\t</td>\r\n\t\t\t<td width=\"35\" align=\"right\" nowrap=\"nowrap\">\r\n\t\t\t\t<strong>");
      out.print(DicoTools.dico(dico_lang , "directory/results_city"));
      out.write("</strong>\r\n\t\t\t</td>\r\n\t\t\t<td nowrap=\"nowrap\">\r\n\t\t\t\t<table width=\"1%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"upanddown\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '4');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_up.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '4');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_down.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n\t\t\t</td>\r\n\t\t\t<td width=\"35\" align=\"right\" nowrap=\"nowrap\">\r\n\t\t\t\t<strong>");
      out.print(DicoTools.dico(dico_lang , "directory/results_country"));
      out.write("</strong>\r\n\t\t\t</td>\r\n\t\t\t<td nowrap=\"nowrap\">\r\n\t\t\t\t<table width=\"1%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"upanddown\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '5');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_up.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '5');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_down.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n\t\t\t</td>\r\n\t\t\t<td width=\"35\" align=\"right\" nowrap=\"nowrap\">\r\n\t\t\t\t<strong>");
      out.print(DicoTools.dico(dico_lang , "directory/results_status"));
      out.write("</strong>\r\n\t\t\t</td>\r\n\t\t\t<td nowrap=\"nowrap\">\r\n\t\t\t\t<table width=\"1%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"upanddown\">\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '6');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_up.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"updateHiddenValue('newPage', '0');updateHiddenValue('tri', '6');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"><img src=\"../include_img/annuaire/annu_down.gif\" alt=\"\" border=\"0\" /></a>\r\n\t\t\t\t\t\t</td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t</table>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</table>\r\n    <div class=\"list-recherche-contener\" >\r\n");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("myAnnuaireResultForm");
      _jspx_th_logic_notEmpty_0.setProperty("listeResultProfil");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("    \r\n\t  ");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_logic_iterate_0.setName("myAnnuaireResultForm");
          _jspx_th_logic_iterate_0.setProperty("listeResultProfil");
          _jspx_th_logic_iterate_0.setId("userData");
          int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
          if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object userData = null;
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_0.doInitBody();
            }
            userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
            do {
              out.write("\r\n\t    <div class=\"fiche\">\r\n\t      ");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_1.setId("user_id");
              _jspx_th_bean_define_1.setName("userData");
              _jspx_th_bean_define_1.setProperty("id");
              int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
              if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
              java.lang.Object user_id = null;
              user_id = (java.lang.Object) _jspx_page_context.findAttribute("user_id");
              out.write("\r\n\t      ");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_2.setId("user_pseudo");
              _jspx_th_bean_define_2.setName("userData");
              _jspx_th_bean_define_2.setProperty("user_pseudo");
              int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
              if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
              java.lang.Object user_pseudo = null;
              user_pseudo = (java.lang.Object) _jspx_page_context.findAttribute("user_pseudo");
              out.write("\r\n\t      ");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_3.setId("paysdata");
              _jspx_th_bean_define_3.setName("userData");
              _jspx_th_bean_define_3.setProperty("pays");
              int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
              if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
              java.lang.Object paysdata = null;
              paysdata = (java.lang.Object) _jspx_page_context.findAttribute("paysdata");
              out.write("\r\n\t      <h1><a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
              out.print(user_pseudo);
              out.write("');\">");
              out.print(user_pseudo);
              out.write("</a></h1>\r\n\t      \r\n\t      <div class=\"identite\">\r\n\t        ");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_0.setName("userData");
              _jspx_th_logic_equal_0.setProperty("annu_sexe");
              _jspx_th_logic_equal_0.setValue("H");
              int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
              if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.print(DicoTools.dico(dico_lang , "directory/sexe_man"));
                  int evalDoAfterBody = _jspx_th_logic_equal_0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_equal_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_0);
                return;
              }
              _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_0);
              out.write("\r\n\t        ");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_1.setName("userData");
              _jspx_th_logic_equal_1.setProperty("annu_sexe");
              _jspx_th_logic_equal_1.setValue("F");
              int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
              if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.print(DicoTools.dico(dico_lang , "directory/sexe_woman"));
                  int evalDoAfterBody = _jspx_th_logic_equal_1.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_equal_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_1);
                return;
              }
              _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_1);
              out.write("\r\n\t        ");
              //  logic:greaterEqual
              org.apache.struts.taglib.logic.GreaterEqualTag _jspx_th_logic_greaterEqual_0 = (org.apache.struts.taglib.logic.GreaterEqualTag) _jspx_tagPool_logic_greaterEqual_value_property_name.get(org.apache.struts.taglib.logic.GreaterEqualTag.class);
              _jspx_th_logic_greaterEqual_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_greaterEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_greaterEqual_0.setName("userData");
              _jspx_th_logic_greaterEqual_0.setProperty("age");
              _jspx_th_logic_greaterEqual_0.setValue("1");
              int _jspx_eval_logic_greaterEqual_0 = _jspx_th_logic_greaterEqual_0.doStartTag();
              if (_jspx_eval_logic_greaterEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  if (_jspx_meth_bean_write_2(_jspx_th_logic_greaterEqual_0, _jspx_page_context))
                    return;
                  out.write("&nbsp;");
                  out.print(DicoTools.dico(dico_lang , "directory/years"));
                  int evalDoAfterBody = _jspx_th_logic_greaterEqual_0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_greaterEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_greaterEqual_value_property_name.reuse(_jspx_th_logic_greaterEqual_0);
                return;
              }
              _jspx_tagPool_logic_greaterEqual_value_property_name.reuse(_jspx_th_logic_greaterEqual_0);
              out.write("\r\n\t        ");
              if (_jspx_meth_bean_write_3(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write(',');
              out.write(' ');
              if (_jspx_meth_bean_write_4(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write(".\r\n\t      </div>\r\n\t        \r\n\t      <div class=\"description\">\r\n\t      \t<a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
              out.print(user_pseudo);
              out.write("');\">\r\n\t\t        ");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_2.setName("userData");
              _jspx_th_logic_equal_2.setProperty("user_good");
              _jspx_th_logic_equal_2.setValue("1");
              int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
              if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t          ");
                  if (_jspx_meth_logic_equal_3(_jspx_th_logic_equal_2, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t          ");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
                  _jspx_th_logic_equal_4.setName("userData");
                  _jspx_th_logic_equal_4.setProperty("user_image");
                  _jspx_th_logic_equal_4.setValue("1");
                  int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
                  if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t            <img class=\"photo\" align=\"left\" src=\"../photo/");
                      out.print(user_id);
                      out.write("_S.jpg\" width=\"48\" height=\"48\" border=\"0\">\r\n\t\t          ");
                      int evalDoAfterBody = _jspx_th_logic_equal_4.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_equal_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_4);
                    return;
                  }
                  _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_4);
                  out.write("\r\n\t\t        ");
                  int evalDoAfterBody = _jspx_th_logic_equal_2.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_equal_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_2);
                return;
              }
              _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_2);
              out.write("\r\n\t\t        ");
              if (_jspx_meth_logic_equal_5(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("\r\n\t        </a>\r\n\t        \r\n\t        ");
              if (_jspx_meth_bean_write_5(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("\r\n\t        \r\n\t      </div>\r\n\t      \r\n\t      <div class=\"commandes\">\r\n\t        <ul>\r\n\t          <li class=\"envoyer\">\r\n\t          \t");
              out.print(DicoTools.dico(dico_lang , "directory/send"));
              out.write("\r\n\t          </li>\r\n\t\t\t\t       <li class=\"msgLink\"><a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
              out.print(user_pseudo);
              out.write("', 'text');\">");
              out.print(DicoTools.dico(dico_lang , "directory/send_text"));
              out.write("</a></li>\r\n\t\t\t\t       <li class=\"msgLink\"><a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
              out.print(user_pseudo);
              out.write("', 'mp3');\">");
              out.print(DicoTools.dico(dico_lang , "directory/send_mp3"));
              out.write("</a></li>\r\n\t\t\t\t       <li class=\"msgLink\"><a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
              out.print(user_pseudo);
              out.write("', 'bibliotheque');\">");
              out.print(DicoTools.dico(dico_lang , "directory/send_music"));
              out.write("</a></li>\r\n\t          \t<li class=\"msgLink\">\r\n\t          \t\t<a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
              out.print(user_pseudo);
              out.write("', 'clindoeil');\">");
              out.print(DicoTools.dico(dico_lang , "directory/send_clins"));
              out.write("</a>\r\n\t\t\t\t</li>\r\n\t        </ul>\r\n\t        \r\n\t        <ul class=\"divers-links\">\r\n\t          <li><a href=\"javascript:;\" onclick=\"showProfilFromAnnuaire(");
              out.print(user_id);
              out.write(");\">");
              out.print(DicoTools.dico(dico_lang , "directory/view_profile"));
              out.write("</a></li>\r\n\t        </ul>\r\n\t        \r\n\t      </div>\r\n\t      <hr class=\"clearer\" />\r\n\t    </div>\r\n\t  ");
              int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
              userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
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
          out.write('\r');
          out.write('\n');
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
      out.write('\r');
      out.write('\n');
      //  logic:empty
      org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_0 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
      _jspx_th_logic_empty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_empty_0.setParent(null);
      _jspx_th_logic_empty_0.setName("myAnnuaireResultForm");
      _jspx_th_logic_empty_0.setProperty("listeResultProfil");
      int _jspx_eval_logic_empty_0 = _jspx_th_logic_empty_0.doStartTag();
      if (_jspx_eval_logic_empty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write(" \r\n<hr class=\"spacer\" />\r\n<div class=\"specialBlock\" align=\"center\"><h2>");
          out.print(DicoTools.dico(dico_lang , "directory/no_result"));
          out.write("</h2></div>\r\n");
          int evalDoAfterBody = _jspx_th_logic_empty_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_empty_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_0);
        return;
      }
      _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_0);
      out.write("\r\n    </div>\t\n    ");
      //  logic:greaterThan
      org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
      _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_greaterThan_0.setParent(null);
      _jspx_th_logic_greaterThan_0.setName("myAnnuaireResultForm");
      _jspx_th_logic_greaterThan_0.setProperty("nombre_pages");
      _jspx_th_logic_greaterThan_0.setValue("1");
      int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
      if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t<div class=\"paginate\">\n\t\t\t<form action=\"../action/myAnnuaireResult.do\" name=\"pageSelector\" method=\"post\">\r\n\t\t\t\t<ul>\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_4.setId("page_indexD");
          _jspx_th_bean_define_4.setName("myAnnuaireResultForm");
          _jspx_th_bean_define_4.setProperty("page_indexD");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object page_indexD = null;
          page_indexD = (java.lang.Object) _jspx_page_context.findAttribute("page_indexD");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_5.setId("page_indexMM");
          _jspx_th_bean_define_5.setName("myAnnuaireResultForm");
          _jspx_th_bean_define_5.setProperty("page_indexMM");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object page_indexMM = null;
          page_indexMM = (java.lang.Object) _jspx_page_context.findAttribute("page_indexMM");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_6.setId("page_indexM");
          _jspx_th_bean_define_6.setName("myAnnuaireResultForm");
          _jspx_th_bean_define_6.setProperty("page_indexM");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object page_indexM = null;
          page_indexM = (java.lang.Object) _jspx_page_context.findAttribute("page_indexM");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_7.setId("page_index");
          _jspx_th_bean_define_7.setName("myAnnuaireResultForm");
          _jspx_th_bean_define_7.setProperty("page_index");
          int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
          if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
          java.lang.Object page_index = null;
          page_index = (java.lang.Object) _jspx_page_context.findAttribute("page_index");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_8.setId("page_indexP");
          _jspx_th_bean_define_8.setName("myAnnuaireResultForm");
          _jspx_th_bean_define_8.setProperty("page_indexP");
          int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
          if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
          java.lang.Object page_indexP = null;
          page_indexP = (java.lang.Object) _jspx_page_context.findAttribute("page_indexP");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_9.setId("page_indexPP");
          _jspx_th_bean_define_9.setName("myAnnuaireResultForm");
          _jspx_th_bean_define_9.setProperty("page_indexPP");
          int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
          if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
          java.lang.Object page_indexPP = null;
          page_indexPP = (java.lang.Object) _jspx_page_context.findAttribute("page_indexPP");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_10.setId("page_indexF");
          _jspx_th_bean_define_10.setName("myAnnuaireResultForm");
          _jspx_th_bean_define_10.setProperty("page_indexF");
          int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
          if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
          java.lang.Object page_indexF = null;
          page_indexF = (java.lang.Object) _jspx_page_context.findAttribute("page_indexF");
          out.write("\r\n\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"javascript:void(0);\" onClick=\"javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', ");
          out.print(page_indexD);
          out.write("); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"> &lt;&lt; </a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"javascript:void(0);\" onClick=\"javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', ");
          out.print(page_indexM);
          out.write("); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"> &lt; </a>\r\n\t\t\t\t\t</li>\r\n\r\n\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_logic_notEqual_0.setName("myAnnuaireResultForm");
          _jspx_th_logic_notEqual_0.setProperty("page_indexMM");
          _jspx_th_logic_notEqual_0.setValue("0");
          int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
          if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t<li>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onClick=\"javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', ");
              out.print(page_indexMM);
              out.write("); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"> ");
              if (_jspx_meth_bean_write_6(_jspx_th_logic_notEqual_0, _jspx_page_context))
                return;
              out.write(" </a>\r\n\t\t\t\t\t\t</li>\r\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_notEqual_0.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_notEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_0);
            return;
          }
          _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_0);
          out.write("\r\n\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_logic_notEqual_1.setName("myAnnuaireResultForm");
          _jspx_th_logic_notEqual_1.setProperty("page_indexM");
          _jspx_th_logic_notEqual_1.setValue("0");
          int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
          if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t<li>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onClick=\"javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', ");
              out.print(page_indexM);
              out.write("); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"> ");
              if (_jspx_meth_bean_write_7(_jspx_th_logic_notEqual_1, _jspx_page_context))
                return;
              out.write(" </a>\r\n\t\t\t\t\t\t</li>\r\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_notEqual_1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_notEqual_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_1);
            return;
          }
          _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_1);
          out.write("\r\n\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"javascript:void(0);\"> ");
          if (_jspx_meth_bean_write_8(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write(" </a>\r\n\t\t\t\t\t</li>\r\n\r\n\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_logic_notEqual_2.setName("myAnnuaireResultForm");
          _jspx_th_logic_notEqual_2.setProperty("page_indexP");
          _jspx_th_logic_notEqual_2.setValue("0");
          int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
          if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t<li>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onClick=\"javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', ");
              out.print(page_indexP);
              out.write("); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"> ");
              if (_jspx_meth_bean_write_9(_jspx_th_logic_notEqual_2, _jspx_page_context))
                return;
              out.write(" </a>\r\n\t\t\t\t\t\t</li>\r\n\t\t\t\t\t");
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
          out.write("\r\n\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_3 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_3.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_logic_notEqual_3.setName("myAnnuaireResultForm");
          _jspx_th_logic_notEqual_3.setProperty("page_indexPP");
          _jspx_th_logic_notEqual_3.setValue("0");
          int _jspx_eval_logic_notEqual_3 = _jspx_th_logic_notEqual_3.doStartTag();
          if (_jspx_eval_logic_notEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t<li>\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onClick=\"javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', ");
              out.print(page_indexPP);
              out.write("); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"> ");
              if (_jspx_meth_bean_write_10(_jspx_th_logic_notEqual_3, _jspx_page_context))
                return;
              out.write(" </a>\r\n\t\t\t\t\t\t</li>\r\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_notEqual_3.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_notEqual_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_3);
            return;
          }
          _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_3);
          out.write("\r\n\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_6 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_6.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_logic_equal_6.setName("myAnnuaireResultForm");
          _jspx_th_logic_equal_6.setProperty("page_indexP");
          _jspx_th_logic_equal_6.setValue("0");
          int _jspx_eval_logic_equal_6 = _jspx_th_logic_equal_6.doStartTag();
          if (_jspx_eval_logic_equal_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onClick=\"javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', ");
              out.print(page_indexF);
              out.write("); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"> &gt; </a>\r\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_equal_6.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_equal_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_6);
            return;
          }
          _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_6);
          out.write("\r\n\t\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_4 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_4.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_logic_notEqual_4.setName("myAnnuaireResultForm");
          _jspx_th_logic_notEqual_4.setProperty("page_indexP");
          _jspx_th_logic_notEqual_4.setValue("0");
          int _jspx_eval_logic_notEqual_4 = _jspx_th_logic_notEqual_4.doStartTag();
          if (_jspx_eval_logic_notEqual_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onClick=\"javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', ");
              out.print(page_indexP);
              out.write("); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"> &gt; </a>\r\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_notEqual_4.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_notEqual_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_4);
            return;
          }
          _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_4);
          out.write("\t\t\t\t\t\t\r\n\t\t\t\t\t</li>\r\n\t\t\t\t\t<li>\r\n\t\t\t\t\t\t<a href=\"javascript:void(0);\" onClick=\"javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', ");
          out.print(page_indexF);
          out.write("); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')\"> &gt;&gt; </a>\r\n\t\t\t\t\t</li>\r\n\t\t\t\t</ul>\n\t\t\t</form>\r\n\t\t</div>\n\t");
          int evalDoAfterBody = _jspx_th_logic_greaterThan_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_greaterThan_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_0);
        return;
      }
      _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_0);
      out.write('\r');
      out.write('\n');
      out.write('	');
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

  private boolean _jspx_meth_bean_write_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_0 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_0.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_0.setParent(null);
    _jspx_th_bean_write_0.setName("myAnnuaireResultForm");
    _jspx_th_bean_write_0.setProperty("nombre_profils");
    int _jspx_eval_bean_write_0 = _jspx_th_bean_write_0.doStartTag();
    if (_jspx_th_bean_write_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
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
    _jspx_th_bean_write_1.setName("myAnnuaireResultForm");
    _jspx_th_bean_write_1.setProperty("nombre_pages");
    int _jspx_eval_bean_write_1 = _jspx_th_bean_write_1.doStartTag();
    if (_jspx_th_bean_write_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_1);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_1);
    return false;
  }

  private boolean _jspx_meth_bean_write_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_2 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_2.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterEqual_0);
    _jspx_th_bean_write_2.setName("userData");
    _jspx_th_bean_write_2.setProperty("age");
    int _jspx_eval_bean_write_2 = _jspx_th_bean_write_2.doStartTag();
    if (_jspx_th_bean_write_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_2);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_2);
    return false;
  }

  private boolean _jspx_meth_bean_write_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_3 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_3.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_bean_write_3.setName("userData");
    _jspx_th_bean_write_3.setProperty("annu_city");
    int _jspx_eval_bean_write_3 = _jspx_th_bean_write_3.doStartTag();
    if (_jspx_th_bean_write_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_3);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_3);
    return false;
  }

  private boolean _jspx_meth_bean_write_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_4 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_4.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_bean_write_4.setName("paysdata");
    _jspx_th_bean_write_4.setProperty("pays_nom");
    int _jspx_eval_bean_write_4 = _jspx_th_bean_write_4.doStartTag();
    if (_jspx_th_bean_write_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_4);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_4);
    return false;
  }

  private boolean _jspx_meth_logic_equal_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
    _jspx_th_logic_equal_3.setName("userData");
    _jspx_th_logic_equal_3.setProperty("user_image");
    _jspx_th_logic_equal_3.setValue("0");
    int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
    if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t            <img class=\"photo\" align=\"left\" src=\"../photo/default_S.jpg\" width=\"48\" height=\"48\" border=\"0\">\r\n\t\t          ");
        int evalDoAfterBody = _jspx_th_logic_equal_3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_3);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_3);
    return false;
  }

  private boolean _jspx_meth_logic_equal_5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_logic_equal_5.setName("userData");
    _jspx_th_logic_equal_5.setProperty("user_good");
    _jspx_th_logic_equal_5.setValue("0");
    int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
    if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t          <img class=\"photo\" align=\"left\" src=\"../photo/default_S.jpg\" width=\"48\" height=\"48\" border=\"0\">\r\n\t\t        ");
        int evalDoAfterBody = _jspx_th_logic_equal_5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_5);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_5);
    return false;
  }

  private boolean _jspx_meth_bean_write_5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_5 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_5.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_bean_write_5.setName("userData");
    _jspx_th_bean_write_5.setProperty("user_comment");
    int _jspx_eval_bean_write_5 = _jspx_th_bean_write_5.doStartTag();
    if (_jspx_th_bean_write_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_5);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_5);
    return false;
  }

  private boolean _jspx_meth_bean_write_6(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_6 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_6.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
    _jspx_th_bean_write_6.setName("myAnnuaireResultForm");
    _jspx_th_bean_write_6.setProperty("page_AffIndexMM");
    int _jspx_eval_bean_write_6 = _jspx_th_bean_write_6.doStartTag();
    if (_jspx_th_bean_write_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_6);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_6);
    return false;
  }

  private boolean _jspx_meth_bean_write_7(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_7 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_7.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
    _jspx_th_bean_write_7.setName("myAnnuaireResultForm");
    _jspx_th_bean_write_7.setProperty("page_AffIndexM");
    int _jspx_eval_bean_write_7 = _jspx_th_bean_write_7.doStartTag();
    if (_jspx_th_bean_write_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_7);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_7);
    return false;
  }

  private boolean _jspx_meth_bean_write_8(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_8 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_8.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_bean_write_8.setName("myAnnuaireResultForm");
    _jspx_th_bean_write_8.setProperty("page_AffIndex");
    int _jspx_eval_bean_write_8 = _jspx_th_bean_write_8.doStartTag();
    if (_jspx_th_bean_write_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_8);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_8);
    return false;
  }

  private boolean _jspx_meth_bean_write_9(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_9 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_9.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_2);
    _jspx_th_bean_write_9.setName("myAnnuaireResultForm");
    _jspx_th_bean_write_9.setProperty("page_AffIndexP");
    int _jspx_eval_bean_write_9 = _jspx_th_bean_write_9.doStartTag();
    if (_jspx_th_bean_write_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_9);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_9);
    return false;
  }

  private boolean _jspx_meth_bean_write_10(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_10 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_10.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_3);
    _jspx_th_bean_write_10.setName("myAnnuaireResultForm");
    _jspx_th_bean_write_10.setProperty("page_AffIndexPP");
    int _jspx_eval_bean_write_10 = _jspx_th_bean_write_10.doStartTag();
    if (_jspx_th_bean_write_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_10);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_10);
    return false;
  }
}
