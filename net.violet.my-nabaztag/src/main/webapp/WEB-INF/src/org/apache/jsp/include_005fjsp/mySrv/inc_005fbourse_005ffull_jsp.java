package org.apache.jsp.include_005fjsp.mySrv;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fbourse_005ffull_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_empty_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_rewrite_forward_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_button_value_styleClass_property_onclick;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_lessThan_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_styleClass_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_text_styleId_style_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_select_styleId_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_text_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_checkbox_value_styleId_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_styleId_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_submit_value_styleClass_property_onclick;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_styleId_property_name_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_empty_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_rewrite_forward_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_button_value_styleClass_property_onclick = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_lessThan_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_styleClass_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_text_styleId_style_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_select_styleId_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_text_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_checkbox_value_styleId_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_styleId_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_submit_value_styleClass_property_onclick = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_styleId_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_logic_empty_name.release();
    _jspx_tagPool_logic_notEmpty_name.release();
    _jspx_tagPool_logic_iterate_name_id.release();
    _jspx_tagPool_html_rewrite_forward_nobody.release();
    _jspx_tagPool_logic_equal_value_name.release();
    _jspx_tagPool_logic_greaterEqual_value_name.release();
    _jspx_tagPool_logic_greaterThan_value_name.release();
    _jspx_tagPool_logic_notEqual_value_name.release();
    _jspx_tagPool_html_button_value_styleClass_property_onclick.release();
    _jspx_tagPool_logic_lessThan_value_name.release();
    _jspx_tagPool_html_form_styleId_styleClass_action.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_html_text_styleId_style_property_name_nobody.release();
    _jspx_tagPool_html_select_styleId_property_name.release();
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.release();
    _jspx_tagPool_html_text_property_name_nobody.release();
    _jspx_tagPool_html_checkbox_value_styleId_property_name_nobody.release();
    _jspx_tagPool_html_hidden_value_styleId_property_nobody.release();
    _jspx_tagPool_html_submit_value_styleClass_property_onclick.release();
    _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.release();
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

      out.write("\n\r\n\n\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write('\r');
      out.write('\n');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setProperty("isReg");
      _jspx_th_bean_define_0.setName("mySrvBourseFullForm");
      _jspx_th_bean_define_0.setId("isReg");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.Object isReg = null;
      isReg = (java.lang.Object) _jspx_page_context.findAttribute("isReg");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setId("srvBourseDataList");
      _jspx_th_bean_define_1.setName("mySrvBourseFullForm");
      _jspx_th_bean_define_1.setProperty("supervisedList");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object srvBourseDataList = null;
      srvBourseDataList = (java.lang.Object) _jspx_page_context.findAttribute("srvBourseDataList");
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setProperty("valueTo");
      _jspx_th_bean_define_2.setName("mySrvBourseFullForm");
      _jspx_th_bean_define_2.setId("valueTo");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      java.lang.Object valueTo = null;
      valueTo = (java.lang.Object) _jspx_page_context.findAttribute("valueTo");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setProperty("maxValue");
      _jspx_th_bean_define_3.setName("mySrvBourseFullForm");
      _jspx_th_bean_define_3.setId("maxValue");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      java.lang.Object maxValue = null;
      maxValue = (java.lang.Object) _jspx_page_context.findAttribute("maxValue");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_4.setParent(null);
      _jspx_th_bean_define_4.setProperty("nbrValue");
      _jspx_th_bean_define_4.setName("mySrvBourseFullForm");
      _jspx_th_bean_define_4.setId("nbrValue");
      int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
      if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
      java.lang.Object nbrValue = null;
      nbrValue = (java.lang.Object) _jspx_page_context.findAttribute("nbrValue");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_5.setParent(null);
      _jspx_th_bean_define_5.setProperty("serviceName");
      _jspx_th_bean_define_5.setName("mySrvBourseFullForm");
      _jspx_th_bean_define_5.setId("serviceName");
      _jspx_th_bean_define_5.setType("String");
      int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
      if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
      String serviceName = null;
      serviceName = (String) _jspx_page_context.findAttribute("serviceName");
      out.write("\r\n\r\n\r\n<div class=\"main-cadre-contener srv-config\">\r\n\t<div class=\"main-cadre-top\"><h2>");
      out.print(DicoTools.dico(dico_lang , "services/configure"));
      out.write("</h2></div>\r\n\t<div class=\"main-cadre-content\">\r\n\t\t<hr class=\"spacer\"/>\r\n\t\t<!-- ******************************************** CONTENT ***************************************************** --> \r\n\r\n\t\t<!--  *************************** La liste des valeurs surveill�s ************************************* -->\r\n\t\t\t<div>\r\n\t\t\t\t<div class=\"srv-list-entries\" >\r\n\t\t\t\t\t<h2>");
      out.print(DicoTools.dico(dico_lang , "srv_bourse/list_monitor"));
      out.write("</h2>\r\n\t\t\t\t\t\r\n\t\t\t\t\t");
      //  logic:empty
      org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_0 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
      _jspx_th_logic_empty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_empty_0.setParent(null);
      _jspx_th_logic_empty_0.setName("srvBourseDataList");
      int _jspx_eval_logic_empty_0 = _jspx_th_logic_empty_0.doStartTag();
      if (_jspx_eval_logic_empty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_bourse/no_values"));
          out.write("\r\n\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_empty_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_empty_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_empty_name.reuse(_jspx_th_logic_empty_0);
        return;
      }
      _jspx_tagPool_logic_empty_name.reuse(_jspx_th_logic_empty_0);
      out.write("\r\n\t\t\t\t\r\n\t\t\t\t\t");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("srvBourseDataList");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_logic_iterate_0.setName("srvBourseDataList");
          _jspx_th_logic_iterate_0.setId("srvBourseData");
          int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
          if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object srvBourseData = null;
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_0.doInitBody();
            }
            srvBourseData = (java.lang.Object) _jspx_page_context.findAttribute("srvBourseData");
            do {
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_6.setId("id");
              _jspx_th_bean_define_6.setName("srvBourseData");
              _jspx_th_bean_define_6.setProperty("srv_id");
              int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
              if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
              java.lang.Object id = null;
              id = (java.lang.Object) _jspx_page_context.findAttribute("id");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_7.setId("name");
              _jspx_th_bean_define_7.setName("srvBourseData");
              _jspx_th_bean_define_7.setProperty("srv_name");
              int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
              if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
              java.lang.Object name = null;
              name = (java.lang.Object) _jspx_page_context.findAttribute("name");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_8.setId("lumiere");
              _jspx_th_bean_define_8.setName("srvBourseData");
              _jspx_th_bean_define_8.setProperty("srv_lumiere");
              int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
              if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
              java.lang.Object lumiere = null;
              lumiere = (java.lang.Object) _jspx_page_context.findAttribute("lumiere");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_9.setId("nbr");
              _jspx_th_bean_define_9.setName("srvBourseData");
              _jspx_th_bean_define_9.setProperty("nbr");
              int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
              if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
              java.lang.Object nbr = null;
              nbr = (java.lang.Object) _jspx_page_context.findAttribute("nbr");
              out.write("\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t<li id=\"srvItem_");
              out.print(id);
              out.write("\" >\r\n\t\t\t\t\t\t\t\t<ul class=\"ico-list\" >\r\n\t\t\t\t\t\t\t\t\t<li class=\"supprimer\">\r\n\t\t\t\t\t\t\t\t\t\t<a class=\"link-delete suppr\" href='");
              if (_jspx_meth_html_rewrite_0(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("?dispatch=delete&applicationId=");
              out.print(Application.NativeApplication.BOURSE_FULL.getApplication().getId());
              out.write("&valueTo=");
              out.print(id);
              out.write("'><span>");
              out.print(DicoTools.dico(dico_lang , "srv_bourse/suppress"));
              out.write("</span></a>\r\n\t\t\t\t\t\t\t\t\t</li>\t\t\t\t\t\r\n\t\t\t\r\n\t\t\t\t\t\t\t\t\t<li class=\"lumi\">\r\n\t\t\t\t\t\t\t\t\t\t");
 /* flash lumineux activ� */ 
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_0.setName("lumiere");
              _jspx_th_logic_equal_0.setValue("1");
              int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
              if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:;\" class=\"lumi-on\" ><span>");
                  out.print(lumiere);
                  out.write("</span></a>\r\n\t\t\t\t\t\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t");
 /* flash lumineux desactiv� */ 
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_1.setName("lumiere");
              _jspx_th_logic_equal_1.setValue("0");
              int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
              if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:;\" class=\"lumi-off\" ><span>");
                  out.print(lumiere);
                  out.write("</span></a>\r\n\t\t\t\t\t\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t\t\t\t<ul class=\"txt-list\" style=\"height:1%;\"> ");
/* <-- vieux hack pour ie, bizarrement on peut pas le mettre dans la css... */
              out.write("\r\n\t\t\t\t\t\t\t\t\t<li class=\"nom\" ><strong>");
              out.print(name);
              out.write("</strong>&nbsp;</li>\r\n\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t");
              //  logic:greaterEqual
              org.apache.struts.taglib.logic.GreaterEqualTag _jspx_th_logic_greaterEqual_0 = (org.apache.struts.taglib.logic.GreaterEqualTag) _jspx_tagPool_logic_greaterEqual_value_name.get(org.apache.struts.taglib.logic.GreaterEqualTag.class);
              _jspx_th_logic_greaterEqual_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_greaterEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_greaterEqual_0.setName("nbr");
              _jspx_th_logic_greaterEqual_0.setValue("1");
              int _jspx_eval_logic_greaterEqual_0 = _jspx_th_logic_greaterEqual_0.doStartTag();
              if (_jspx_eval_logic_greaterEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t<li class=\"horaire\">\r\n\t\t\t\t\t\t\t\t\t\t\t");
                  //  logic:greaterEqual
                  org.apache.struts.taglib.logic.GreaterEqualTag _jspx_th_logic_greaterEqual_1 = (org.apache.struts.taglib.logic.GreaterEqualTag) _jspx_tagPool_logic_greaterEqual_value_name.get(org.apache.struts.taglib.logic.GreaterEqualTag.class);
                  _jspx_th_logic_greaterEqual_1.setPageContext(_jspx_page_context);
                  _jspx_th_logic_greaterEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterEqual_0);
                  _jspx_th_logic_greaterEqual_1.setName("nbr");
                  _jspx_th_logic_greaterEqual_1.setValue("1");
                  int _jspx_eval_logic_greaterEqual_1 = _jspx_th_logic_greaterEqual_1.doStartTag();
                  if (_jspx_eval_logic_greaterEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterEqual_1);
                      _jspx_th_bean_define_10.setId("weekend");
                      _jspx_th_bean_define_10.setName("srvBourseData");
                      _jspx_th_bean_define_10.setProperty("weekend");
                      int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
                      if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
                      java.lang.Object weekend = null;
                      weekend = (java.lang.Object) _jspx_page_context.findAttribute("weekend");
                      out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterEqual_1);
                      _jspx_th_bean_define_11.setId("time1");
                      _jspx_th_bean_define_11.setName("srvBourseData");
                      _jspx_th_bean_define_11.setProperty("time1");
                      int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
                      if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
                      java.lang.Object time1 = null;
                      time1 = (java.lang.Object) _jspx_page_context.findAttribute("time1");
                      out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                      out.print(DicoTools.dico(dico_lang , "srv_bourse/first_audio_flash"));
                      out.write("&nbsp;<span class=\"userTime\">");
                      out.print(time1);
                      out.write("</span><br>\r\n\t\t\t\t\t\t\t\t\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_greaterEqual_1.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_greaterEqual_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_greaterEqual_value_name.reuse(_jspx_th_logic_greaterEqual_1);
                    return;
                  }
                  _jspx_tagPool_logic_greaterEqual_value_name.reuse(_jspx_th_logic_greaterEqual_1);
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterEqual_0);
                  _jspx_th_logic_equal_2.setName("nbr");
                  _jspx_th_logic_equal_2.setValue("2");
                  int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
                  if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_12 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_12.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
                      _jspx_th_bean_define_12.setId("time2");
                      _jspx_th_bean_define_12.setName("srvBourseData");
                      _jspx_th_bean_define_12.setProperty("time2");
                      int _jspx_eval_bean_define_12 = _jspx_th_bean_define_12.doStartTag();
                      if (_jspx_th_bean_define_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
                      java.lang.Object time2 = null;
                      time2 = (java.lang.Object) _jspx_page_context.findAttribute("time2");
                      out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                      out.print(DicoTools.dico(dico_lang , "srv_bourse/second_audio_flash"));
                      out.write("&nbsp;<span class=\"userTime\">");
                      out.print(time2);
                      out.write("</span><br>\r\n\t\t\t\t\t\t\t\t\t\t\t");
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
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_greaterEqual_0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_greaterEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_greaterEqual_value_name.reuse(_jspx_th_logic_greaterEqual_0);
                return;
              }
              _jspx_tagPool_logic_greaterEqual_value_name.reuse(_jspx_th_logic_greaterEqual_0);
              out.write("\r\n\t\t\t\t\t\t\t\t\t");
              //  logic:greaterEqual
              org.apache.struts.taglib.logic.GreaterEqualTag _jspx_th_logic_greaterEqual_2 = (org.apache.struts.taglib.logic.GreaterEqualTag) _jspx_tagPool_logic_greaterEqual_value_name.get(org.apache.struts.taglib.logic.GreaterEqualTag.class);
              _jspx_th_logic_greaterEqual_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_greaterEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_greaterEqual_2.setName("weekend");
              _jspx_th_logic_greaterEqual_2.setValue("1");
              int _jspx_eval_logic_greaterEqual_2 = _jspx_th_logic_greaterEqual_2.doStartTag();
              if (_jspx_eval_logic_greaterEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t<li>\r\n\t\t\t\t\t\t\t\t\t\t\t");
                  out.print(DicoTools.dico(dico_lang , "srv_bourse/deactivate_week_end"));
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_greaterEqual_2.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_greaterEqual_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_greaterEqual_value_name.reuse(_jspx_th_logic_greaterEqual_2);
                return;
              }
              _jspx_tagPool_logic_greaterEqual_value_name.reuse(_jspx_th_logic_greaterEqual_2);
              out.write("\r\n\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t<li class=\"modifier\">\r\n\t\t\t\t\t\t\t\t\t\t<a class=\"link-modify\" href='");
              if (_jspx_meth_html_rewrite_1(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("?dispatch=display&applicationId=");
              out.print(Application.NativeApplication.BOURSE_FULL.getApplication().getId());
              out.write("&valueTo=");
              out.print(id);
              out.write("'><span>");
              out.print(DicoTools.dico(dico_lang , "srv_bourse/modify"));
              out.write("</span></a>\r\n\t\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
              srvBourseData = (java.lang.Object) _jspx_page_context.findAttribute("srvBourseData");
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_logic_iterate_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_iterate_name_id.reuse(_jspx_th_logic_iterate_0);
            return;
          }
          _jspx_tagPool_logic_iterate_name_id.reuse(_jspx_th_logic_iterate_0);
          out.write("\r\n\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t<li style=\"border:none; text-align:center;\">\r\n\t\t\t\t\t\t\t");
          //  logic:greaterThan
          org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
          _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_greaterThan_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_logic_greaterThan_0.setName("isReg");
          _jspx_th_logic_greaterThan_0.setValue("0");
          int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
          if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t\t");
/* Supression */
              out.write("\t\t\r\n\t\t\t\t\t\t\t\t");
              //  logic:notEqual
              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
              _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
              _jspx_th_logic_notEqual_0.setName("isReg");
              _jspx_th_logic_notEqual_0.setValue("0");
              int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
              if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write(" \r\n\t\t\t\t\t\t\t\t\t");
                  //  html:button
                  org.apache.struts.taglib.html.ButtonTag _jspx_th_html_button_0 = (org.apache.struts.taglib.html.ButtonTag) _jspx_tagPool_html_button_value_styleClass_property_onclick.get(org.apache.struts.taglib.html.ButtonTag.class);
                  _jspx_th_html_button_0.setPageContext(_jspx_page_context);
                  _jspx_th_html_button_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
                  _jspx_th_html_button_0.setProperty("delete");
                  _jspx_th_html_button_0.setValue(DicoTools.dico(dico_lang , "srv_air/button_delete"));
                  _jspx_th_html_button_0.setStyleClass("genericDeleteBt");
                  _jspx_th_html_button_0.setOnclick("serviceDelete();");
                  int _jspx_eval_html_button_0 = _jspx_th_html_button_0.doStartTag();
                  if (_jspx_eval_html_button_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    if (_jspx_eval_html_button_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                      out = _jspx_page_context.pushBody();
                      _jspx_th_html_button_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                      _jspx_th_html_button_0.doInitBody();
                    }
                    do {
                      out.print(DicoTools.dico(dico_lang , "srv_air/button_delete"));
                      int evalDoAfterBody = _jspx_th_html_button_0.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                    if (_jspx_eval_html_button_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                      out = _jspx_page_context.popBody();
                    }
                  }
                  if (_jspx_th_html_button_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_html_button_value_styleClass_property_onclick.reuse(_jspx_th_html_button_0);
                    return;
                  }
                  _jspx_tagPool_html_button_value_styleClass_property_onclick.reuse(_jspx_th_html_button_0);
                  out.write("\r\n\t\t\t\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t\t\t\t");
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
          out.write("\r\n\t\t\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_logic_notEqual_1.setName("valueTo");
          _jspx_th_logic_notEqual_1.setValue("0");
          int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
          if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t\t");
              //  logic:lessThan
              org.apache.struts.taglib.logic.LessThanTag _jspx_th_logic_lessThan_0 = (org.apache.struts.taglib.logic.LessThanTag) _jspx_tagPool_logic_lessThan_value_name.get(org.apache.struts.taglib.logic.LessThanTag.class);
              _jspx_th_logic_lessThan_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_lessThan_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
              _jspx_th_logic_lessThan_0.setName("nbrValue");
              _jspx_th_logic_lessThan_0.setValue(maxValue.toString());
              int _jspx_eval_logic_lessThan_0 = _jspx_th_logic_lessThan_0.doStartTag();
              if (_jspx_eval_logic_lessThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write(' ');
 /* Tant que l'on ne depasse pas le quota */ 
                  out.write("\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t<a class=\"link-modify\" href='");
                  if (_jspx_meth_html_rewrite_2(_jspx_th_logic_lessThan_0, _jspx_page_context))
                    return;
                  out.write("?dispatch=display&applicationId=");
                  out.print(Application.NativeApplication.BOURSE_FULL.getApplication().getId());
                  out.write("'><input class=\"genericBt\" type=\"button\" value=\"");
                  out.print(DicoTools.dico(dico_lang , "srv_bourse/button_add"));
                  out.write("\"/></a>\r\n\t\t\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_lessThan_0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_lessThan_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_lessThan_value_name.reuse(_jspx_th_logic_lessThan_0);
                return;
              }
              _jspx_tagPool_logic_lessThan_value_name.reuse(_jspx_th_logic_lessThan_0);
              out.write("\r\n\t\t\t\t\t\t\t");
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
          out.write("\r\n\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t");
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
      out.write("\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t\r\n\t\t<!--  **************************** Ajout d'une valeur ******************************** -->\r\n\t\t<div class=\"srv-config\" >\r\n\t\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_styleClass_action.get(org.apache.struts.taglib.html.FormTag.class);
      _jspx_th_html_form_0.setPageContext(_jspx_page_context);
      _jspx_th_html_form_0.setParent(null);
      _jspx_th_html_form_0.setAction("/action/srvBourseFullConfig");
      _jspx_th_html_form_0.setStyleId("srvBourseConfig");
      _jspx_th_html_form_0.setStyleClass("srvConfigForm");
      int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
      if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t<input type=\"hidden\" id=\"internalSrvId\" value=\"");
          out.print(Application.NativeApplication.BOURSE_FULL.getApplication().getId());
          out.write("\" />\r\n\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_equal_3.setName("valueTo");
          _jspx_th_logic_equal_3.setValue("0");
          int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
          if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t<h2>");
              out.print(DicoTools.dico(dico_lang , "srv_bourse/add_alert"));
              out.write("</h2>\r\n\t\t\t");
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
          out.write("\r\n\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_notEqual_2.setName("valueTo");
          _jspx_th_logic_notEqual_2.setValue("0");
          int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
          if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t<h2>");
              out.print(DicoTools.dico(dico_lang , "srv_bourse/modify_alert"));
              out.write("</h2>\r\n\t\t\t");
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
          out.write("\r\n\t\t\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\r\n\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_equal_4.setName("mySrvBourseFullForm");
          _jspx_th_logic_equal_4.setProperty("duplicateName");
          _jspx_th_logic_equal_4.setValue("1");
          int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
          if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t<div class=\"warningMsg\" >");
              out.print(DicoTools.dico(dico_lang , "srv_bourse/name_alert_exists"));
              out.write("</div>\t\r\n\t\t\t\t<script type=\"text/javascript\">\r\n\t\t\t\t\tgSrvErrorFlag = true;\r\n\t\t\t\t\t$(\"#valNameAlert\").extHighlight();\r\n\t\t\t\t</script>\r\n\t\t\t");
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
          out.write("\r\n\t\t\t\t\r\n\t\t\t<div id=\"valNameAlert\">\r\n\t\t\t\t<label>");
          out.print(DicoTools.dico(dico_lang , "srv_bourse/name_alert"));
          out.write("</label>\r\n\t\t\t\t");
          if (_jspx_meth_html_text_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t</div>\r\n\t\t\t\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\r\n\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_equal_5.setName("mySrvBourseFullForm");
          _jspx_th_logic_equal_5.setProperty("falseValue");
          _jspx_th_logic_equal_5.setValue("1");
          int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
          if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t<div class=\"warningMsg\" >");
              out.print(DicoTools.dico(dico_lang , "srv_bourse/value_code_invalid"));
              out.write("</div>\r\n\t\t\t\t<script type=\"text/javascript\">\r\n\t\t\t\t\tgSrvErrorFlag = true;\t\t\r\n\t\t\t\t\t$(\"#valNameId\").extHighlight();\r\n\t\t\t\t</script>\r\n\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_equal_5.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_equal_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_5);
            return;
          }
          _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_5);
          out.write("\r\n\t\t\t\t\r\n\t\t\t<div id=\"valNameId\" style=\"background-color:#fff\">\r\n\t\t\t\t<label>");
          out.print(DicoTools.dico(dico_lang , "srv_bourse/value_name"));
          out.write("</label>\r\n\t\t\t\t");
          if (_jspx_meth_html_text_1(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t<div align=\"center\">\r\n\t\t\t\t\t<a class=\"link-help-txt\" target='_blank' href='");
          out.print(DicoTools.dico(dico_lang , "srv_bourse/link"));
          out.write('\'');
          out.write(' ');
          out.write('>');
          out.print(DicoTools.dico(dico_lang , "srv_bourse/linkname"));
          out.write("</a>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\r\n\t\t\t<label>");
          out.print(DicoTools.dico(dico_lang , "srv_bourse/or_index"));
          out.write("</label>\r\n\t\t\t");
          if (_jspx_meth_html_select_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\r\n\t\t\t<label>");
          out.print(DicoTools.dico(dico_lang , "srv_bourse/first_audio_flash"));
          out.write("</label>\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_13 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_13.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_bean_define_13.setName("mySrvBourseFullForm");
          _jspx_th_bean_define_13.setProperty("horraire1");
          _jspx_th_bean_define_13.setId("heures1");
          int _jspx_eval_bean_define_13 = _jspx_th_bean_define_13.doStartTag();
          if (_jspx_th_bean_define_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
          java.lang.Object heures1 = null;
          heures1 = (java.lang.Object) _jspx_page_context.findAttribute("heures1");
          out.write("\r\n\t\t\t<input id=\"time1\" name=\"horraire1\" type=\"hidden\" class=\"hourPicker\" value=\"");
          out.print(heures1);
          out.write("\" />\t\r\n\t\t\t<!-- ");
          if (_jspx_meth_html_text_2(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write(" -->\r\n\t\t\t\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\r\n\t\t\t<label>");
          out.print(DicoTools.dico(dico_lang , "srv_bourse/second_audio_flash"));
          out.write("</label>\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_14 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_14.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_bean_define_14.setName("mySrvBourseFullForm");
          _jspx_th_bean_define_14.setProperty("horraire2");
          _jspx_th_bean_define_14.setId("heures2");
          int _jspx_eval_bean_define_14 = _jspx_th_bean_define_14.doStartTag();
          if (_jspx_th_bean_define_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
          java.lang.Object heures2 = null;
          heures2 = (java.lang.Object) _jspx_page_context.findAttribute("heures2");
          out.write("\r\n\t\t\t<input id=\"time2\" name=\"horraire2\" type=\"hidden\" class=\"hourPicker\" value=\"");
          out.print(heures2);
          out.write("\" />\t\r\n\t\t\t\t\r\n\t\t\t<!-- ");
          if (_jspx_meth_html_text_3(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write(" -->\r\n\t\t\t\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\r\n\t\t\t<label for=\"weekend\">");
          out.print(DicoTools.dico(dico_lang , "srv_bourse/deactivate_week_end"));
          out.write("</label>");
          if (_jspx_meth_html_checkbox_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\r\n\t\t\t<label for=\"lumiere\">");
          out.print(DicoTools.dico(dico_lang , "srv_bourse/light_language"));
          out.write("</label>");
          if (_jspx_meth_html_checkbox_1(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write(" <a href=\"#keskiditTop\">");
          out.print(DicoTools.dico(dico_lang , "srv_all/light_help"));
          out.write("</a> ");
          out.write("\r\n\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\r\n\t\t\t");
          if (_jspx_meth_html_hidden_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t\t\r\n\t\t\t<div align=\"center\">\r\n\t\t\t\t");
          //  logic:lessThan
          org.apache.struts.taglib.logic.LessThanTag _jspx_th_logic_lessThan_1 = (org.apache.struts.taglib.logic.LessThanTag) _jspx_tagPool_logic_lessThan_value_name.get(org.apache.struts.taglib.logic.LessThanTag.class);
          _jspx_th_logic_lessThan_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_lessThan_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_lessThan_1.setName("nbrValue");
          _jspx_th_logic_lessThan_1.setValue(maxValue.toString());
          int _jspx_eval_logic_lessThan_1 = _jspx_th_logic_lessThan_1.doStartTag();
          if (_jspx_eval_logic_lessThan_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write(" <!-- Tant que l'on ne depasse pas le quota -->\r\n\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_6 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_6.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessThan_1);
              _jspx_th_logic_equal_6.setName("valueTo");
              _jspx_th_logic_equal_6.setValue("0");
              int _jspx_eval_logic_equal_6 = _jspx_th_logic_equal_6.doStartTag();
              if (_jspx_eval_logic_equal_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t");
                  //  html:submit
                  org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_0 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_styleClass_property_onclick.get(org.apache.struts.taglib.html.SubmitTag.class);
                  _jspx_th_html_submit_0.setPageContext(_jspx_page_context);
                  _jspx_th_html_submit_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_6);
                  _jspx_th_html_submit_0.setProperty("activate");
                  _jspx_th_html_submit_0.setValue(DicoTools.dico(dico_lang , "srv_bourse/button_activate"));
                  _jspx_th_html_submit_0.setStyleClass("genericBt");
                  _jspx_th_html_submit_0.setOnclick("goDispatch('activate', 'dispatchConfig')");
                  int _jspx_eval_html_submit_0 = _jspx_th_html_submit_0.doStartTag();
                  if (_jspx_eval_html_submit_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    if (_jspx_eval_html_submit_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                      out = _jspx_page_context.pushBody();
                      _jspx_th_html_submit_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                      _jspx_th_html_submit_0.doInitBody();
                    }
                    do {
                      out.print(DicoTools.dico(dico_lang , "srv_bourse/button_activate"));
                      int evalDoAfterBody = _jspx_th_html_submit_0.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                    if (_jspx_eval_html_submit_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                      out = _jspx_page_context.popBody();
                    }
                  }
                  if (_jspx_th_html_submit_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_html_submit_value_styleClass_property_onclick.reuse(_jspx_th_html_submit_0);
                    return;
                  }
                  _jspx_tagPool_html_submit_value_styleClass_property_onclick.reuse(_jspx_th_html_submit_0);
                  out.write("\t\r\n\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_lessThan_1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_lessThan_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_lessThan_value_name.reuse(_jspx_th_logic_lessThan_1);
            return;
          }
          _jspx_tagPool_logic_lessThan_value_name.reuse(_jspx_th_logic_lessThan_1);
          out.write("\r\n\t\t\t\r\n\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_7 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_7.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_equal_7.setName("nbrValue");
          _jspx_th_logic_equal_7.setValue(maxValue.toString());
          int _jspx_eval_logic_equal_7 = _jspx_th_logic_equal_7.doStartTag();
          if (_jspx_eval_logic_equal_7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "srv_bourse/nb_max_alert"));
              out.write("\r\n\t\t\t\t");
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
          out.write("\r\n\t\t\t\r\n\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_3 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_3.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_notEqual_3.setName("valueTo");
          _jspx_th_logic_notEqual_3.setValue("0");
          int _jspx_eval_logic_notEqual_3 = _jspx_th_logic_notEqual_3.doStartTag();
          if (_jspx_eval_logic_notEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t");
              //  html:hidden
              org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
              _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
              _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_3);
              _jspx_th_html_hidden_1.setName("mySrvBourseFullForm");
              _jspx_th_html_hidden_1.setProperty("valueTo");
              _jspx_th_html_hidden_1.setStyleId("valueTo");
              _jspx_th_html_hidden_1.setValue(valueTo.toString());
              int _jspx_eval_html_hidden_1 = _jspx_th_html_hidden_1.doStartTag();
              if (_jspx_th_html_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_1);
                return;
              }
              _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_1);
              out.write("\r\n\t\t\t\t\t");
              //  html:submit
              org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_1 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_styleClass_property_onclick.get(org.apache.struts.taglib.html.SubmitTag.class);
              _jspx_th_html_submit_1.setPageContext(_jspx_page_context);
              _jspx_th_html_submit_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_3);
              _jspx_th_html_submit_1.setProperty("update");
              _jspx_th_html_submit_1.setValue(DicoTools.dico(dico_lang , "srv_bourse/button_update"));
              _jspx_th_html_submit_1.setStyleClass("genericBt");
              _jspx_th_html_submit_1.setOnclick("goDispatch('update', 'dispatchConfig')");
              int _jspx_eval_html_submit_1 = _jspx_th_html_submit_1.doStartTag();
              if (_jspx_eval_html_submit_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                if (_jspx_eval_html_submit_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_html_submit_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_html_submit_1.doInitBody();
                }
                do {
                  out.print(DicoTools.dico(dico_lang , "srv_bourse/button_update"));
                  int evalDoAfterBody = _jspx_th_html_submit_1.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_html_submit_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_html_submit_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_submit_value_styleClass_property_onclick.reuse(_jspx_th_html_submit_1);
                return;
              }
              _jspx_tagPool_html_submit_value_styleClass_property_onclick.reuse(_jspx_th_html_submit_1);
              out.write("\t\r\n\t\t\t\t");
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
          out.write("\r\n\t\t\t</div>\r\n\t\t");
          int evalDoAfterBody = _jspx_th_html_form_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_html_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_html_form_styleId_styleClass_action.reuse(_jspx_th_html_form_0);
        return;
      }
      _jspx_tagPool_html_form_styleId_styleClass_action.reuse(_jspx_th_html_form_0);
      out.write("\r\n\t\t\r\n\t\t");
      out.write("\t\t\r\n\t\t\r\n\t\t</div>\r\n\t\t\t<!-- ******************************************** /CONTENT ***************************************************** -->        \r\n\t\t\t<hr class=\"spacer\" />\t\r\n\t</div>\r\n</div>\t\t\t\r\n\r\n<hr class=\"clearer\" />\r\n\r\n<script type=\"text/javascript\">\r\n\t/* liens de modifs */\r\n\t$(\"a.link-modify\").each(function(){\r\n\t\tvar url = $(this).attr(\"href\");\r\n\t\t$(this).attr(\"href\", \"javascript:;\");\r\n\t\t$(this).click(function(){\r\n\t\t\tdivChangeUrlBackground(\"contentSrvConfig\", url);\r\n\t\t});\r\n\t});\r\n\t\r\n\t$(\"a.link-delete\").each(function(){\r\n\t\tvar url = $(this).attr(\"href\");\r\n\t\t$(this).attr(\"href\", \"javascript:;\");\r\n\t\t$(this).click(function(){\r\n\t\t\tdivChangeUrlBackground(\"contentSrvConfig\", url);\r\n\t\t});\r\n\t});\t\r\n\t\r\n\t$(\"input.hourPicker\").hourSelect_InitN(false );\r\n\t\t\r\n\t$(\"#list\").select_NotclickableValues();\t\t\r\n\t\r\n\t$(\"#srvBourseConfig\").submit(function(){\r\n\t\treturn nablifeValidateBourseFullConfig(");
      if (_jspx_meth_logic_greaterThan_1(_jspx_page_context))
        return;
      out.write("); \r\n\t});\r\n\t\r\n\t$(\"#list\").change(function(){\r\n\t\t$(\"#valName\").val(\"\");\r\n\t});\r\n\t$(\"#valName\").change(function(){\r\n\t\t$(\"#list option\").attr(\"selected\", \"false\").eq(0).attr(\"selected\", true);\r\n\t});\r\n\t\r\n\t");
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_4 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_4.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_4.setParent(null);
      _jspx_th_logic_notEqual_4.setName("valueTo");
      _jspx_th_logic_notEqual_4.setValue("0");
      int _jspx_eval_logic_notEqual_4 = _jspx_th_logic_notEqual_4.doStartTag();
      if (_jspx_eval_logic_notEqual_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\t\t\t\r\n\t\t$(\"#srvItem_");
          out.print(valueTo);
          out.write("\").addClass(\"selected\");\r\n\t");
          int evalDoAfterBody = _jspx_th_logic_notEqual_4.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_notEqual_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_4);
        return;
      }
      _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_4);
      out.write("\r\n\r\n\t\r\n</script>\r\n\r\n");
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

  private boolean _jspx_meth_html_rewrite_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_0 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_0.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_html_rewrite_0.setForward("srvBourseFull");
    int _jspx_eval_html_rewrite_0 = _jspx_th_html_rewrite_0.doStartTag();
    if (_jspx_th_html_rewrite_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_1 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_1.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_html_rewrite_1.setForward("srvBourseFull");
    int _jspx_eval_html_rewrite_1 = _jspx_th_html_rewrite_1.doStartTag();
    if (_jspx_th_html_rewrite_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_1);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_1);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_lessThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_2 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_2.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessThan_0);
    _jspx_th_html_rewrite_2.setForward("srvBourseFull");
    int _jspx_eval_html_rewrite_2 = _jspx_th_html_rewrite_2.doStartTag();
    if (_jspx_th_html_rewrite_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_2);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_2);
    return false;
  }

  private boolean _jspx_meth_html_text_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_0 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_styleId_style_property_name_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_0.setPageContext(_jspx_page_context);
    _jspx_th_html_text_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_text_0.setName("mySrvBourseFullForm");
    _jspx_th_html_text_0.setProperty("alertName");
    _jspx_th_html_text_0.setStyle("width:190px;");
    _jspx_th_html_text_0.setStyleId("alertName");
    int _jspx_eval_html_text_0 = _jspx_th_html_text_0.doStartTag();
    if (_jspx_th_html_text_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_styleId_style_property_name_nobody.reuse(_jspx_th_html_text_0);
      return true;
    }
    _jspx_tagPool_html_text_styleId_style_property_name_nobody.reuse(_jspx_th_html_text_0);
    return false;
  }

  private boolean _jspx_meth_html_text_1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_1 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_styleId_style_property_name_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_1.setPageContext(_jspx_page_context);
    _jspx_th_html_text_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_text_1.setName("mySrvBourseFullForm");
    _jspx_th_html_text_1.setProperty("valName");
    _jspx_th_html_text_1.setStyle("width:190px;");
    _jspx_th_html_text_1.setStyleId("valName");
    int _jspx_eval_html_text_1 = _jspx_th_html_text_1.doStartTag();
    if (_jspx_th_html_text_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_styleId_style_property_name_nobody.reuse(_jspx_th_html_text_1);
      return true;
    }
    _jspx_tagPool_html_text_styleId_style_property_name_nobody.reuse(_jspx_th_html_text_1);
    return false;
  }

  private boolean _jspx_meth_html_select_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:select
    org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_0 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_styleId_property_name.get(org.apache.struts.taglib.html.SelectTag.class);
    _jspx_th_html_select_0.setPageContext(_jspx_page_context);
    _jspx_th_html_select_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_select_0.setName("mySrvBourseFullForm");
    _jspx_th_html_select_0.setProperty("indic");
    _jspx_th_html_select_0.setStyleId("list");
    int _jspx_eval_html_select_0 = _jspx_th_html_select_0.doStartTag();
    if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_html_select_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_html_select_0.doInitBody();
      }
      do {
        out.write("\r\n\t\t\t\t<option value=\"\"></option>\n\t\t\t\t<!--  FrequenceData -->\r\n\t\t\t\t");
        if (_jspx_meth_html_optionsCollection_0(_jspx_th_html_select_0, _jspx_page_context))
          return true;
        out.write("\r\n\t\t\t");
        int evalDoAfterBody = _jspx_th_html_select_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_html_select_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_select_styleId_property_name.reuse(_jspx_th_html_select_0);
      return true;
    }
    _jspx_tagPool_html_select_styleId_property_name.reuse(_jspx_th_html_select_0);
    return false;
  }

  private boolean _jspx_meth_html_optionsCollection_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_select_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:optionsCollection
    org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_optionsCollection_0 = (org.apache.struts.taglib.html.OptionsCollectionTag) _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
    _jspx_th_html_optionsCollection_0.setPageContext(_jspx_page_context);
    _jspx_th_html_optionsCollection_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_0);
    _jspx_th_html_optionsCollection_0.setName("mySrvBourseFullForm");
    _jspx_th_html_optionsCollection_0.setProperty("indicList");
    _jspx_th_html_optionsCollection_0.setLabel("label");
    _jspx_th_html_optionsCollection_0.setValue("id");
    int _jspx_eval_html_optionsCollection_0 = _jspx_th_html_optionsCollection_0.doStartTag();
    if (_jspx_th_html_optionsCollection_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.reuse(_jspx_th_html_optionsCollection_0);
      return true;
    }
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.reuse(_jspx_th_html_optionsCollection_0);
    return false;
  }

  private boolean _jspx_meth_html_text_2(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_2 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_property_name_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_2.setPageContext(_jspx_page_context);
    _jspx_th_html_text_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_text_2.setName("mySrvBourseFullForm");
    _jspx_th_html_text_2.setProperty("horraire1");
    int _jspx_eval_html_text_2 = _jspx_th_html_text_2.doStartTag();
    if (_jspx_th_html_text_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_property_name_nobody.reuse(_jspx_th_html_text_2);
      return true;
    }
    _jspx_tagPool_html_text_property_name_nobody.reuse(_jspx_th_html_text_2);
    return false;
  }

  private boolean _jspx_meth_html_text_3(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_3 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_property_name_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_3.setPageContext(_jspx_page_context);
    _jspx_th_html_text_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_text_3.setName("mySrvBourseFullForm");
    _jspx_th_html_text_3.setProperty("horraire2");
    int _jspx_eval_html_text_3 = _jspx_th_html_text_3.doStartTag();
    if (_jspx_th_html_text_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_property_name_nobody.reuse(_jspx_th_html_text_3);
      return true;
    }
    _jspx_tagPool_html_text_property_name_nobody.reuse(_jspx_th_html_text_3);
    return false;
  }

  private boolean _jspx_meth_html_checkbox_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:checkbox
    org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_checkbox_0 = (org.apache.struts.taglib.html.CheckboxTag) _jspx_tagPool_html_checkbox_value_styleId_property_name_nobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
    _jspx_th_html_checkbox_0.setPageContext(_jspx_page_context);
    _jspx_th_html_checkbox_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_checkbox_0.setName("mySrvBourseFullForm");
    _jspx_th_html_checkbox_0.setProperty("weekend");
    _jspx_th_html_checkbox_0.setStyleId("weekend");
    _jspx_th_html_checkbox_0.setValue("1");
    int _jspx_eval_html_checkbox_0 = _jspx_th_html_checkbox_0.doStartTag();
    if (_jspx_th_html_checkbox_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_checkbox_value_styleId_property_name_nobody.reuse(_jspx_th_html_checkbox_0);
      return true;
    }
    _jspx_tagPool_html_checkbox_value_styleId_property_name_nobody.reuse(_jspx_th_html_checkbox_0);
    return false;
  }

  private boolean _jspx_meth_html_checkbox_1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:checkbox
    org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_checkbox_1 = (org.apache.struts.taglib.html.CheckboxTag) _jspx_tagPool_html_checkbox_value_styleId_property_name_nobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
    _jspx_th_html_checkbox_1.setPageContext(_jspx_page_context);
    _jspx_th_html_checkbox_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_checkbox_1.setName("mySrvBourseFullForm");
    _jspx_th_html_checkbox_1.setProperty("lumiere");
    _jspx_th_html_checkbox_1.setStyleId("lumiere");
    _jspx_th_html_checkbox_1.setValue("1");
    int _jspx_eval_html_checkbox_1 = _jspx_th_html_checkbox_1.doStartTag();
    if (_jspx_th_html_checkbox_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_checkbox_value_styleId_property_name_nobody.reuse(_jspx_th_html_checkbox_1);
      return true;
    }
    _jspx_tagPool_html_checkbox_value_styleId_property_name_nobody.reuse(_jspx_th_html_checkbox_1);
    return false;
  }

  private boolean _jspx_meth_html_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_styleId_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_hidden_0.setStyleId("dispatchConfig");
    _jspx_th_html_hidden_0.setProperty("dispatch");
    _jspx_th_html_hidden_0.setValue("load");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_styleId_property_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_styleId_property_nobody.reuse(_jspx_th_html_hidden_0);
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
    _jspx_th_logic_greaterThan_1.setName("isReg");
    _jspx_th_logic_greaterThan_1.setValue("0");
    int _jspx_eval_logic_greaterThan_1 = _jspx_th_logic_greaterThan_1.doStartTag();
    if (_jspx_eval_logic_greaterThan_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("true");
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
}
