package org.apache.jsp.include_005fjsp.myNablife;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fallServices_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(2);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
    _jspx_dependants.add("/include_jsp/myNablife/inc_nablifeMenu.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_size_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_parameter_value_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_offset_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_rewrite_forward_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_size_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_parameter_value_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_offset_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_rewrite_forward_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_bean_size_property_name_id_nobody.release();
    _jspx_tagPool_bean_parameter_value_name_id_nobody.release();
    _jspx_tagPool_logic_iterate_offset_name_id.release();
    _jspx_tagPool_logic_greaterThan_value_name.release();
    _jspx_tagPool_logic_iterate_name_id.release();
    _jspx_tagPool_logic_equal_value_name.release();
    _jspx_tagPool_html_rewrite_forward_nobody.release();
    _jspx_tagPool_logic_notEqual_value_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
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
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myServicesHomeForm");
      _jspx_th_bean_define_0.setProperty("languser");
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
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setId("mode");
      _jspx_th_bean_define_1.setName("myServicesHomeForm");
      _jspx_th_bean_define_1.setProperty("mode");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object mode = null;
      mode = (java.lang.Object) _jspx_page_context.findAttribute("mode");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setId("langCategorie");
      _jspx_th_bean_define_2.setName("myServicesHomeForm");
      _jspx_th_bean_define_2.setProperty("langCategorie");
      _jspx_th_bean_define_2.setType("Long");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      Long langCategorie = null;
      langCategorie = (Long) _jspx_page_context.findAttribute("langCategorie");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setId("idCateg");
      _jspx_th_bean_define_3.setName("myServicesHomeForm");
      _jspx_th_bean_define_3.setProperty("idCateg");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      java.lang.Object idCateg = null;
      idCateg = (java.lang.Object) _jspx_page_context.findAttribute("idCateg");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_4.setParent(null);
      _jspx_th_bean_define_4.setId("nameCateg");
      _jspx_th_bean_define_4.setName("myServicesHomeForm");
      _jspx_th_bean_define_4.setProperty("nameCateg");
      int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
      if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
      java.lang.Object nameCateg = null;
      nameCateg = (java.lang.Object) _jspx_page_context.findAttribute("nameCateg");
      out.write('\r');
      out.write('\n');
      //  bean:size
      java.lang.Integer nbLangue = null;
      org.apache.struts.taglib.bean.SizeTag _jspx_th_bean_size_0 = (org.apache.struts.taglib.bean.SizeTag) _jspx_tagPool_bean_size_property_name_id_nobody.get(org.apache.struts.taglib.bean.SizeTag.class);
      _jspx_th_bean_size_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_size_0.setParent(null);
      _jspx_th_bean_size_0.setId("nbLangue");
      _jspx_th_bean_size_0.setName("myServicesHomeForm");
      _jspx_th_bean_size_0.setProperty("langList");
      int _jspx_eval_bean_size_0 = _jspx_th_bean_size_0.doStartTag();
      nbLangue = (java.lang.Integer) _jspx_page_context.findAttribute("nbLangue");
      if (_jspx_th_bean_size_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_size_property_name_id_nobody.reuse(_jspx_th_bean_size_0);
        return;
      }
      nbLangue = (java.lang.Integer) _jspx_page_context.findAttribute("nbLangue");
      _jspx_tagPool_bean_size_property_name_id_nobody.reuse(_jspx_th_bean_size_0);
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_5.setParent(null);
      _jspx_th_bean_define_5.setId("listeCategorie");
      _jspx_th_bean_define_5.setName("myServicesHomeForm");
      _jspx_th_bean_define_5.setProperty("listeCategorie");
      int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
      if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
      java.lang.Object listeCategorie = null;
      listeCategorie = (java.lang.Object) _jspx_page_context.findAttribute("listeCategorie");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_6.setParent(null);
      _jspx_th_bean_define_6.setId("listeNabcastCateg");
      _jspx_th_bean_define_6.setName("myServicesHomeForm");
      _jspx_th_bean_define_6.setProperty("listeNabcastCateg");
      int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
      if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
      java.lang.Object listeNabcastCateg = null;
      listeNabcastCateg = (java.lang.Object) _jspx_page_context.findAttribute("listeNabcastCateg");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_7.setParent(null);
      _jspx_th_bean_define_7.setId("listeLang");
      _jspx_th_bean_define_7.setName("myServicesHomeForm");
      _jspx_th_bean_define_7.setProperty("langList");
      int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
      if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
      java.lang.Object listeLang = null;
      listeLang = (java.lang.Object) _jspx_page_context.findAttribute("listeLang");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_8.setParent(null);
      _jspx_th_bean_define_8.setId("user_main");
      _jspx_th_bean_define_8.setName("myServicesHomeForm");
      _jspx_th_bean_define_8.setProperty("user_main");
      int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
      if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
      java.lang.Object user_main = null;
      user_main = (java.lang.Object) _jspx_page_context.findAttribute("user_main");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_9.setParent(null);
      _jspx_th_bean_define_9.setId("user_id");
      _jspx_th_bean_define_9.setName("myServicesHomeForm");
      _jspx_th_bean_define_9.setProperty("user_id");
      int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
      if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
      java.lang.Object user_id = null;
      user_id = (java.lang.Object) _jspx_page_context.findAttribute("user_id");
      out.write("\r\n\r\n");
      //  bean:parameter
      java.lang.String type = null;
      org.apache.struts.taglib.bean.ParameterTag _jspx_th_bean_parameter_0 = (org.apache.struts.taglib.bean.ParameterTag) _jspx_tagPool_bean_parameter_value_name_id_nobody.get(org.apache.struts.taglib.bean.ParameterTag.class);
      _jspx_th_bean_parameter_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_parameter_0.setParent(null);
      _jspx_th_bean_parameter_0.setId("type");
      _jspx_th_bean_parameter_0.setName("type");
      _jspx_th_bean_parameter_0.setValue("");
      int _jspx_eval_bean_parameter_0 = _jspx_th_bean_parameter_0.doStartTag();
      type = (java.lang.String) _jspx_page_context.findAttribute("type");
      if (_jspx_th_bean_parameter_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_parameter_value_name_id_nobody.reuse(_jspx_th_bean_parameter_0);
        return;
      }
      type = (java.lang.String) _jspx_page_context.findAttribute("type");
      _jspx_tagPool_bean_parameter_value_name_id_nobody.reuse(_jspx_th_bean_parameter_0);
      out.write(' ');
      out.write("\r\n\r\n<div class=\"main-layout\" >\r\n\t\t\t\r\n\t\t\t<div class=\"menu\">\r\n\t\t\t\t");
      out.write("\r\n<table>\r\n\t<tr>\r\n\t\t<td>\r\n\t\t\t\r\n\t\t\t\r\n\t\t\t<div class=\"categ-chooser\">\r\n\t\t\t\t<select>\r\n\t\t\t\t\t<option class=\"choosetxt service\" value=\"\" >");
      out.print(DicoTools.dico(dico_lang, "myNablife/services_categories_choose"));
      out.write("</option>\t\t\r\n\t\t\t\t\t<optgroup label=\"");
      out.print(DicoTools.dico(dico_lang, "myNablife/services_categories"));
      out.write("\">\r\n\t\t\t\t\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_offset_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_0.setParent(null);
      _jspx_th_logic_iterate_0.setName("listeCategorie");
      _jspx_th_logic_iterate_0.setId("srvCategData");
      _jspx_th_logic_iterate_0.setOffset("0");
      int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
      if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object srvCategData = null;
        if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_0.doInitBody();
        }
        srvCategData = (java.lang.Object) _jspx_page_context.findAttribute("srvCategData");
        do {
          out.write("\r\n\t\t\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_10.setId("name");
          _jspx_th_bean_define_10.setName("srvCategData");
          _jspx_th_bean_define_10.setProperty("label");
          int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
          if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
          java.lang.Object name = null;
          name = (java.lang.Object) _jspx_page_context.findAttribute("name");
          out.write("\r\n\t\t\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_11.setId("id");
          _jspx_th_bean_define_11.setName("srvCategData");
          _jspx_th_bean_define_11.setProperty("id");
          int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
          if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
          java.lang.Object id = null;
          id = (java.lang.Object) _jspx_page_context.findAttribute("id");
          out.write("\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t<option class=\"service\" value=\"myServicesHome.do?type=service&idCateg=");
          out.print(id);
          out.write("&mode=1&langCategorie=");
          out.print(langCategorie);
          out.write('"');
          out.write('>');
          out.print(DicoTools.dico_if(dico_lang, name.toString()));
          out.write("</option>\r\n\t\t\t\r\n\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
          srvCategData = (java.lang.Object) _jspx_page_context.findAttribute("srvCategData");
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.popBody();
        }
      }
      if (_jspx_th_logic_iterate_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_iterate_offset_name_id.reuse(_jspx_th_logic_iterate_0);
        return;
      }
      _jspx_tagPool_logic_iterate_offset_name_id.reuse(_jspx_th_logic_iterate_0);
      out.write("\r\n\t\t\t\t\t</optgroup>\r\n\t\t\t\t\t\r\n\t\t\t\t\t<optgroup label=\"");
      out.print(DicoTools.dico(dico_lang, "myNablife/nabcasts_categories"));
      out.write("\">\r\n\t\t\t\t\t\t");
      out.write("\r\n\t\t\t\t\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_offset_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_1.setParent(null);
      _jspx_th_logic_iterate_1.setName("listeNabcastCateg");
      _jspx_th_logic_iterate_1.setId("nabcastCategData");
      _jspx_th_logic_iterate_1.setOffset("0");
      int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
      if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object nabcastCategData = null;
        if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_1.doInitBody();
        }
        nabcastCategData = (java.lang.Object) _jspx_page_context.findAttribute("nabcastCategData");
        do {
          out.write("\r\n\t\t\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_12 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_12.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_12.setId("name");
          _jspx_th_bean_define_12.setName("nabcastCategData");
          _jspx_th_bean_define_12.setProperty("nabcastcateg_val");
          int _jspx_eval_bean_define_12 = _jspx_th_bean_define_12.doStartTag();
          if (_jspx_th_bean_define_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
          java.lang.Object name = null;
          name = (java.lang.Object) _jspx_page_context.findAttribute("name");
          out.write("\r\n\t\t\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_13 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_13.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_13.setId("id");
          _jspx_th_bean_define_13.setName("nabcastCategData");
          _jspx_th_bean_define_13.setProperty("nabcastcateg_id");
          int _jspx_eval_bean_define_13 = _jspx_th_bean_define_13.doStartTag();
          if (_jspx_th_bean_define_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
          java.lang.Object id = null;
          id = (java.lang.Object) _jspx_page_context.findAttribute("id");
          out.write("\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t<option class=\"nabcast\" value=\"myNabaztalandHome.do?type=nabcast&idCateg=");
          out.print(id);
          out.write("&mode=1&langCategorie=");
          out.print(langCategorie);
          out.write('"');
          out.write('>');
          out.print(name.toString());
          out.write("</option>\t\r\n\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
          nabcastCategData = (java.lang.Object) _jspx_page_context.findAttribute("nabcastCategData");
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.popBody();
        }
      }
      if (_jspx_th_logic_iterate_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_iterate_offset_name_id.reuse(_jspx_th_logic_iterate_1);
        return;
      }
      _jspx_tagPool_logic_iterate_offset_name_id.reuse(_jspx_th_logic_iterate_1);
      out.write("\t\t\t\t\t\r\n\t\t\t\t\t</optgroup>\r\n\t\t\t\t\t\r\n\t\t\t\t</select>\r\n\t\t\t</div>\t\r\n\t\t</td>\r\n\t\t<td>\r\n\t\t\t");
      out.write("\r\n\t\t\t");
      //  logic:greaterThan
      org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
      _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_greaterThan_0.setParent(null);
      _jspx_th_logic_greaterThan_0.setName("nbLangue");
      _jspx_th_logic_greaterThan_0.setValue("1");
      int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
      if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t<ul class=\"langSelect\">\r\n\t\t\t\t\t");
          out.write("\n\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_2 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_2.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_logic_iterate_2.setName("listeLang");
          _jspx_th_logic_iterate_2.setId("langInList");
          int _jspx_eval_logic_iterate_2 = _jspx_th_logic_iterate_2.doStartTag();
          if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object langInList = null;
            if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_2.doInitBody();
            }
            langInList = (java.lang.Object) _jspx_page_context.findAttribute("langInList");
            do {
              out.write("\n\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_14 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_14.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
              _jspx_th_bean_define_14.setName("langInList");
              _jspx_th_bean_define_14.setProperty("lang_id");
              _jspx_th_bean_define_14.setId("theLangId");
              int _jspx_eval_bean_define_14 = _jspx_th_bean_define_14.doStartTag();
              if (_jspx_th_bean_define_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
              java.lang.Object theLangId = null;
              theLangId = (java.lang.Object) _jspx_page_context.findAttribute("theLangId");
              out.write("\r\n\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_15 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_15.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
              _jspx_th_bean_define_15.setName("langInList");
              _jspx_th_bean_define_15.setProperty("lang_iso_code");
              _jspx_th_bean_define_15.setId("lang_iso_code");
              int _jspx_eval_bean_define_15 = _jspx_th_bean_define_15.doStartTag();
              if (_jspx_th_bean_define_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_15);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_15);
              java.lang.Object lang_iso_code = null;
              lang_iso_code = (java.lang.Object) _jspx_page_context.findAttribute("lang_iso_code");
              out.write("\n\t\t\t\t\t\t<li class=\"");
              out.print(lang_iso_code.toString() );
              out.write(' ');
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
              _jspx_th_logic_equal_0.setName("langCategorie");
              _jspx_th_logic_equal_0.setValue(theLangId.toString() );
              int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
              if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("selected");
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
              out.write("\" ><a href=\"");
              if (_jspx_meth_html_rewrite_0(_jspx_th_logic_iterate_2, _jspx_page_context))
                return;
              out.write("?langCategorie=");
              out.print(theLangId.toString() );
              out.write("\" ><span>");
              out.print(lang_iso_code.toString() );
              out.write("</span></a></li>\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_2.doAfterBody();
              langInList = (java.lang.Object) _jspx_page_context.findAttribute("langInList");
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_logic_iterate_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_iterate_name_id.reuse(_jspx_th_logic_iterate_2);
            return;
          }
          _jspx_tagPool_logic_iterate_name_id.reuse(_jspx_th_logic_iterate_2);
          out.write("\n\t\t\t\t</ul>\r\n\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_greaterThan_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_greaterThan_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_0);
        return;
      }
      _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_0);
      out.write("\r\n\t\t</td>\r\n\t</tr>\r\n</table>\r\n\r\n\r\n<ul class=\"menulinks\">\r\n\t");
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_0.setParent(null);
      _jspx_th_logic_notEqual_0.setName("user_main");
      _jspx_th_logic_notEqual_0.setValue("0");
      int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
      if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t<li>\r\n\t\t\t");
          out.print(DicoTools.dico(dico_lang, "myNablife/menuLink1"));
          out.write("\r\n\t\t</li>\r\n\t");
          int evalDoAfterBody = _jspx_th_logic_notEqual_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_notEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_0);
        return;
      }
      _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_0);
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_1.setParent(null);
      _jspx_th_logic_notEqual_1.setName("user_id");
      _jspx_th_logic_notEqual_1.setValue("0");
      int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
      if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t<li>\r\n\t\t\t");
          out.print(DicoTools.dico(dico_lang, "myNablife/menuLink2"));
          out.write("\r\n\t\t</li>\r\n\t");
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
      out.write("\r\n\t<li>\r\n\t\t");
      out.print(DicoTools.dico(dico_lang, "myNablife/menuLink3"));
      out.write("\r\n\t</li>\r\n</ul>");
      out.write("\r\n\t\t\t</div>\r\n\t\t\t\r\n\t\t\t");
      out.write("\r\n\t\t\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_1.setParent(null);
      _jspx_th_logic_equal_1.setName("idCateg");
      _jspx_th_logic_equal_1.setValue("0");
      int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
      if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t<div class=\"intro-cadre-contener\">\r\n\t\t\t\t\t<div class=\"intro-cadre-top\">\r\n\t\t\t\t\t\t<h2>");
          out.print(DicoTools.dico(dico_lang, "myNablife/intro_titre"));
          out.write("</h2>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div class=\"intro-cadre-content\">\r\n\t\t\t\t\t\t<div class=\"intro\" >\r\n\t\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang, "myNablife/intro_text"));
          out.write("\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t\r\n\t\t\t\t</div>\r\n\t\t\t");
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
      out.write("\r\n\t\t\t");
      out.write("\t\t\t\r\n\t\t\t");
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_2.setParent(null);
      _jspx_th_logic_notEqual_2.setName("idCateg");
      _jspx_th_logic_notEqual_2.setValue("0");
      int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
      if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t<div class=\"intro-cadre-contener\">\r\n\t\t\t\t\t<div class=\"intro-cadre-top\">\r\n\t\t\t\t\t\t<a href=\"myNablife.do\"><h3 >");
          out.print(DicoTools.dico(dico_lang, "myNablife/services_title"));
          out.write("</h3></a>\r\n\t\t\t\t\t</div>\t\t\t\t\t\r\n\t\t\t\t\t<div class=\"intro-cadre-content\">\r\n\t\t\t\t\t\t<h2 class=\"categTitle\">");
          out.print(DicoTools.dico_if(dico_lang, nameCateg.toString()));
          out.write("</h2>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_notEqual_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_notEqual_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_2);
        return;
      }
      _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_2);
      out.write("\t\r\n\t\t\t\r\n\t\t\t<hr class=\"clearer\" />\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t");
      out.write("\t\t\t\t\r\n\r\n\t\t\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_3 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_3.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_3.setParent(null);
      _jspx_th_logic_iterate_3.setId("nablifeServicesData");
      _jspx_th_logic_iterate_3.setName("myServicesHomeForm");
      _jspx_th_logic_iterate_3.setProperty("listServices");
      int _jspx_eval_logic_iterate_3 = _jspx_th_logic_iterate_3.doStartTag();
      if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object nablifeServicesData = null;
        if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_3.doInitBody();
        }
        nablifeServicesData = (java.lang.Object) _jspx_page_context.findAttribute("nablifeServicesData");
        do {
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_16 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_16.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_16.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_16.setId("s_id");
          _jspx_th_bean_define_16.setName("nablifeServicesData");
          _jspx_th_bean_define_16.setProperty("id");
          int _jspx_eval_bean_define_16 = _jspx_th_bean_define_16.doStartTag();
          if (_jspx_th_bean_define_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_16);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_16);
          java.lang.Object s_id = null;
          s_id = (java.lang.Object) _jspx_page_context.findAttribute("s_id");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_17 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_17.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_17.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_17.setId("s_name");
          _jspx_th_bean_define_17.setName("nablifeServicesData");
          _jspx_th_bean_define_17.setProperty("name");
          int _jspx_eval_bean_define_17 = _jspx_th_bean_define_17.doStartTag();
          if (_jspx_th_bean_define_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_17);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_17);
          java.lang.Object s_name = null;
          s_name = (java.lang.Object) _jspx_page_context.findAttribute("s_name");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_18 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_18.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_18.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_18.setId("s_desc");
          _jspx_th_bean_define_18.setName("nablifeServicesData");
          _jspx_th_bean_define_18.setProperty("desc");
          int _jspx_eval_bean_define_18 = _jspx_th_bean_define_18.doStartTag();
          if (_jspx_th_bean_define_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_18);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_18);
          java.lang.Object s_desc = null;
          s_desc = (java.lang.Object) _jspx_page_context.findAttribute("s_desc");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_19 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_19.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_19.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_19.setId("s_link");
          _jspx_th_bean_define_19.setName("nablifeServicesData");
          _jspx_th_bean_define_19.setProperty("link");
          int _jspx_eval_bean_define_19 = _jspx_th_bean_define_19.doStartTag();
          if (_jspx_th_bean_define_19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_19);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_19);
          java.lang.Object s_link = null;
          s_link = (java.lang.Object) _jspx_page_context.findAttribute("s_link");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_20 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_20.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_20.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_20.setId("s_srvImg");
          _jspx_th_bean_define_20.setName("nablifeServicesData");
          _jspx_th_bean_define_20.setProperty("srvImg");
          _jspx_th_bean_define_20.setType("String");
          int _jspx_eval_bean_define_20 = _jspx_th_bean_define_20.doStartTag();
          if (_jspx_th_bean_define_20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_20);
            return;
          }
          _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_20);
          String s_srvImg = null;
          s_srvImg = (String) _jspx_page_context.findAttribute("s_srvImg");
          out.write("\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_21 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_21.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_21.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_21.setId("s_srvIcone");
          _jspx_th_bean_define_21.setName("nablifeServicesData");
          _jspx_th_bean_define_21.setProperty("srvIcone");
          _jspx_th_bean_define_21.setType("String");
          int _jspx_eval_bean_define_21 = _jspx_th_bean_define_21.doStartTag();
          if (_jspx_th_bean_define_21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_21);
            return;
          }
          _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_21);
          String s_srvIcone = null;
          s_srvIcone = (String) _jspx_page_context.findAttribute("s_srvIcone");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_22 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_22.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_22.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_22.setId("s_type_config");
          _jspx_th_bean_define_22.setName("nablifeServicesData");
          _jspx_th_bean_define_22.setProperty("srvlist_setup");
          int _jspx_eval_bean_define_22 = _jspx_th_bean_define_22.doStartTag();
          if (_jspx_th_bean_define_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_22);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_22);
          java.lang.Object s_type_config = null;
          s_type_config = (java.lang.Object) _jspx_page_context.findAttribute("s_type_config");
          out.write("\r\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_23 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_23.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_23.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_23.setId("isSubscribed");
          _jspx_th_bean_define_23.setName("nablifeServicesData");
          _jspx_th_bean_define_23.setProperty("isSubscribed");
          int _jspx_eval_bean_define_23 = _jspx_th_bean_define_23.doStartTag();
          if (_jspx_th_bean_define_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_23);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_23);
          java.lang.Object isSubscribed = null;
          isSubscribed = (java.lang.Object) _jspx_page_context.findAttribute("isSubscribed");
          out.write("\r\n\t\t\t\t\t\r\n\r\n\t\t\t\t\t<div class=\"flat-block list-services\" > \r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
          if (_jspx_meth_logic_equal_2(_jspx_th_logic_iterate_3, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t\t\t");
          if (_jspx_meth_logic_equal_3(_jspx_th_logic_iterate_3, _jspx_page_context))
            return;
          out.write("\r\n\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"flat-block-top\" >\r\n\t\t\t\t\t\t\t<img class=\"srv-ico\" src=\"");
          out.print(s_srvIcone);
          out.write("\" alt=\"");
          out.print(DicoTools.dico_if(langCategorie,s_name.toString()));
          out.write("\" />\r\n\t\t\t\t\t\t\t<h3>\r\n\t\t\t\t\t\t\t\t<span title=\"");
          out.print(DicoTools.dico_if(langCategorie,s_name.toString()));
          out.write("\">\r\n\t\t\t\t\t\t\t\t\t\t");
          out.print(DicoTools.dico_if(langCategorie,s_name.toString()));
          out.write("\r\n\t\t\t\t\t\t\t\t</span>\r\n\t\t\t\t\t\t\t</h3>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"flat-block-content\" >\r\n\t\t\t\t\t\t\t<div class=\"flat-block-content-inner\" >\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t<div class=\"description\">\r\n\t\t\t\t\t\t\t\t\t<div class=\"image\">\r\n\t\t\t\t\t\t\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_logic_equal_4.setName("isSubscribed");
          _jspx_th_logic_equal_4.setValue("-1");
          int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
          if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t<img border=\"0\" src=\"");
              out.print(s_srvImg);
              out.write("\" alt=\"");
              out.print(DicoTools.dico_if(langCategorie,s_name.toString()));
              out.write("\" />\r\n\t\t\t\t\t\t\t\t\t\t");
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
          out.write("\r\n\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_3 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_3.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_logic_notEqual_3.setName("isSubscribed");
          _jspx_th_logic_notEqual_3.setValue("-1");
          int _jspx_eval_logic_notEqual_3 = _jspx_th_logic_notEqual_3.doStartTag();
          if (_jspx_eval_logic_notEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"");
              out.print(s_link.toString());
              out.write("\" ><img  border=\"0\" src=\"");
              out.print(s_srvImg);
              out.write("\" alt=\"");
              out.print(DicoTools.dico_if(langCategorie,s_name.toString()));
              out.write("\" /></a>\r\n\t\t\t\t\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_notEqual_3.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_notEqual_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_3);
            return;
          }
          _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_3);
          out.write("\t\r\n\t\t\t\t\t\t\t\t\t</div>\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t");
          out.print(DicoTools.dico_if(langCategorie,s_desc.toString()));
          out.write("\r\n\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t");
          if (_jspx_meth_logic_notEqual_4(_jspx_th_logic_iterate_3, _jspx_page_context))
            return;
          out.write("\t\t\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t");
          out.write("\r\n\t\t\t\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_5 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_5.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_logic_notEqual_5.setName("isSubscribed");
          _jspx_th_logic_notEqual_5.setValue("-1");
          int _jspx_eval_logic_notEqual_5 = _jspx_th_logic_notEqual_5.doStartTag();
          if (_jspx_eval_logic_notEqual_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t\t\t<a class=\"add-srv\" href=\"");
              out.print(s_link.toString());
              out.write("\">+</a>\r\n\t\t\t\t\t\t\t\t\t<div class=\"text-status-holder\" >\r\n\t\t\t\t\t\t\t\t\t\t<strong>\r\n\t\t\t\t\t\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_5);
              _jspx_th_logic_equal_5.setName("s_type_config");
              _jspx_th_logic_equal_5.setValue("CONFIG");
              int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
              if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_6 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_6.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_5);
                  _jspx_th_logic_equal_6.setName("isSubscribed");
                  _jspx_th_logic_equal_6.setValue("0");
                  int _jspx_eval_logic_equal_6 = _jspx_th_logic_equal_6.doStartTag();
                  if (_jspx_eval_logic_equal_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.print(DicoTools.dico(dico_lang, "srv_all/subscribe"));
                      int evalDoAfterBody = _jspx_th_logic_equal_6.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_equal_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_6);
                    return;
                  }
                  _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_6);
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                  //  logic:greaterThan
                  org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_1 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
                  _jspx_th_logic_greaterThan_1.setPageContext(_jspx_page_context);
                  _jspx_th_logic_greaterThan_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_5);
                  _jspx_th_logic_greaterThan_1.setName("isSubscribed");
                  _jspx_th_logic_greaterThan_1.setValue("0");
                  int _jspx_eval_logic_greaterThan_1 = _jspx_th_logic_greaterThan_1.doStartTag();
                  if (_jspx_eval_logic_greaterThan_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.print(DicoTools.dico(dico_lang, "srv_all/configuration"));
                      int evalDoAfterBody = _jspx_th_logic_greaterThan_1.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_greaterThan_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_1);
                    return;
                  }
                  _jspx_tagPool_logic_greaterThan_value_name.reuse(_jspx_th_logic_greaterThan_1);
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_equal_5.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_equal_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_5);
                return;
              }
              _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_5);
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_7 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_7.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_5);
              _jspx_th_logic_equal_7.setName("s_type_config");
              _jspx_th_logic_equal_7.setValue("INFO");
              int _jspx_eval_logic_equal_7 = _jspx_th_logic_equal_7.doStartTag();
              if (_jspx_eval_logic_equal_7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_8 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_8.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_7);
                  _jspx_th_logic_equal_8.setName("isSubscribed");
                  _jspx_th_logic_equal_8.setValue("3");
                  int _jspx_eval_logic_equal_8 = _jspx_th_logic_equal_8.doStartTag();
                  if (_jspx_eval_logic_equal_8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.print(DicoTools.dico(dico_lang, "srv_all/learn_more"));
                      int evalDoAfterBody = _jspx_th_logic_equal_8.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_equal_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_8);
                    return;
                  }
                  _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_8);
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_9 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_9.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_7);
                  _jspx_th_logic_equal_9.setName("isSubscribed");
                  _jspx_th_logic_equal_9.setValue("1");
                  int _jspx_eval_logic_equal_9 = _jspx_th_logic_equal_9.doStartTag();
                  if (_jspx_eval_logic_equal_9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.print(DicoTools.dico(dico_lang, "srv_all/configuration"));
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
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_equal_7.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_equal_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_7);
                return;
              }
              _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_7);
              out.write("\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t</strong>\r\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_notEqual_5.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_notEqual_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_5);
            return;
          }
          _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_5);
          out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t\r\n\t\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_iterate_3.doAfterBody();
          nablifeServicesData = (java.lang.Object) _jspx_page_context.findAttribute("nablifeServicesData");
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.popBody();
        }
      }
      if (_jspx_th_logic_iterate_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_3);
        return;
      }
      _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_3);
      out.write("\r\n\r\n\t\t\t\r\n\t\t\t<hr class=\"clearer\"/>\r\n\t\t\t\r\n\r\n\t\r\n</div>\r\n");
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

  private boolean _jspx_meth_html_rewrite_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_0 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_0.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
    _jspx_th_html_rewrite_0.setForward("goServicesHome");
    int _jspx_eval_html_rewrite_0 = _jspx_th_html_rewrite_0.doStartTag();
    if (_jspx_th_html_rewrite_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
    _jspx_th_logic_equal_2.setName("isSubscribed");
    _jspx_th_logic_equal_2.setValue("1");
    int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
    if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t\t<input type=\"hidden\" value=\"true\" name=\"subscribed\" />\r\n\t\t\t\t\t    ");
        int evalDoAfterBody = _jspx_th_logic_equal_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_2);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_2);
    return false;
  }

  private boolean _jspx_meth_logic_equal_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
    _jspx_th_logic_equal_3.setName("isSubscribed");
    _jspx_th_logic_equal_3.setValue("0");
    int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
    if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t\t<input type=\"hidden\" value=\"false\" name=\"subscribed\" />\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_3);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_3);
    return false;
  }

  private boolean _jspx_meth_logic_notEqual_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_4 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_4.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
    _jspx_th_logic_notEqual_4.setName("idCateg");
    _jspx_th_logic_notEqual_4.setValue("0");
    int _jspx_eval_logic_notEqual_4 = _jspx_th_logic_notEqual_4.doStartTag();
    if (_jspx_eval_logic_notEqual_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t\t\t\t\t<div class=\"truncated\"></div>\r\n\t\t\t\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_notEqual_4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_4);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_4);
    return false;
  }
}
