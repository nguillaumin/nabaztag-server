package org.apache.jsp.include_005fjsp.myServices.webRadio;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import java.util.Map;
import java.util.HashMap;
import net.violet.platform.datamodel.Lang;
import net.violet.mynabaztag.form.MySrvWebRadioFreeConfigForm;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class webRadioFreeConfig_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_styleClass_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_value_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_select_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_styleId_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_button_value_styleClass_property_onclick;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_submit_value_styleClass_property_onclick;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_empty_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_type_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_styleClass_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_value_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_select_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_styleId_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_button_value_styleClass_property_onclick = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_submit_value_styleClass_property_onclick = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_empty_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_bean_define_type_name_id_nobody.release();
    _jspx_tagPool_html_form_styleId_styleClass_action.release();
    _jspx_tagPool_html_hidden_value_property_nobody.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_bean_define_value_id_nobody.release();
    _jspx_tagPool_logic_equal_value_name.release();
    _jspx_tagPool_html_select_value_property_name.release();
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.release();
    _jspx_tagPool_logic_notEqual_value_name.release();
    _jspx_tagPool_html_hidden_value_styleId_property_nobody.release();
    _jspx_tagPool_logic_notEmpty_name.release();
    _jspx_tagPool_html_button_value_styleClass_property_onclick.release();
    _jspx_tagPool_html_submit_value_styleClass_property_onclick.release();
    _jspx_tagPool_logic_empty_name.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
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

      out.write("\r\n\r\n\r\n\n\r\n\r\n\r\n");

	response.setContentType("text/html;charset=UTF-8");

      out.write("\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("mySrvWebRadioFreeConfigForm");
      _jspx_th_bean_define_0.setProperty("applicationId");
      _jspx_th_bean_define_0.setId("applicationId");
      _jspx_th_bean_define_0.setType("Integer");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      Integer applicationId = null;
      applicationId = (Integer) _jspx_page_context.findAttribute("applicationId");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("mySrvWebRadioFreeConfigForm");
      _jspx_th_bean_define_1.setProperty("subscriptionId");
      _jspx_th_bean_define_1.setId("subscriptionId");
      _jspx_th_bean_define_1.setType("Integer");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      Integer subscriptionId = null;
      subscriptionId = (Integer) _jspx_page_context.findAttribute("subscriptionId");
      out.write('\r');
      out.write('\n');

	Lang dico_lang = SessionTools.getLangFromSession(session, request);
	Map<String, String> srv = new HashMap<String, String>();

	if (applicationId>0)
		srv = StaticTools.getServiceFromApplication(applicationId.toString(), dico_lang);
	if (subscriptionId>0)
		srv = StaticTools.getServiceFromSubscription(subscriptionId.toString(), dico_lang);

      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setName("mySrvWebRadioFreeConfigForm");
      _jspx_th_bean_define_2.setProperty("wradioId");
      _jspx_th_bean_define_2.setId("wradioId");
      _jspx_th_bean_define_2.setType("Integer");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      Integer wradioId = null;
      wradioId = (Integer) _jspx_page_context.findAttribute("wradioId");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setName("mySrvWebRadioFreeConfigForm");
      _jspx_th_bean_define_3.setProperty("dispatch");
      _jspx_th_bean_define_3.setId("dispatch");
      _jspx_th_bean_define_3.setType("String");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      String dispatch = null;
      dispatch = (String) _jspx_page_context.findAttribute("dispatch");
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_4.setParent(null);
      _jspx_th_bean_define_4.setName("mySrvWebRadioFreeConfigForm");
      _jspx_th_bean_define_4.setProperty("isRegistered");
      _jspx_th_bean_define_4.setId("isRegistered");
      _jspx_th_bean_define_4.setType("String");
      int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
      if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
      String isRegistered = null;
      isRegistered = (String) _jspx_page_context.findAttribute("isRegistered");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_5.setParent(null);
      _jspx_th_bean_define_5.setName("mySrvWebRadioFreeConfigForm");
      _jspx_th_bean_define_5.setId("mySrvWebRadioFreeConfigForm");
      _jspx_th_bean_define_5.setType("net.violet.mynabaztag.form.MySrvWebRadioFreeConfigForm");
      int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
      if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_name_id_nobody.reuse(_jspx_th_bean_define_5);
        return;
      }
      _jspx_tagPool_bean_define_type_name_id_nobody.reuse(_jspx_th_bean_define_5);
      net.violet.mynabaztag.form.MySrvWebRadioFreeConfigForm mySrvWebRadioFreeConfigForm = null;
      mySrvWebRadioFreeConfigForm = (net.violet.mynabaztag.form.MySrvWebRadioFreeConfigForm) _jspx_page_context.findAttribute("mySrvWebRadioFreeConfigForm");
      out.write("\n\r\n\r\n<div class=\"main-cadre-contener\">\r\n\t\r\n\t<div class=\"main-cadre-top\">\r\n\t\t<h2>");
      out.print(DicoTools.dico(dico_lang , "services/configure"));
      out.write("</h2>\r\n\t</div>\r\n\t\r\n\r\n\t<div class=\"main-cadre-content\">\r\n\t\t");
      out.write("\r\n \t\t\r\n \t\t\r\n\r\n\t\t\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_styleClass_action.get(org.apache.struts.taglib.html.FormTag.class);
      _jspx_th_html_form_0.setPageContext(_jspx_page_context);
      _jspx_th_html_form_0.setParent(null);
      _jspx_th_html_form_0.setAction("/action/srvWebRadioFreeConfig");
      _jspx_th_html_form_0.setStyleId("srvWebRadioFreeConfig");
      _jspx_th_html_form_0.setStyleClass("srvConfigForm");
      int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
      if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n \t\t\t\t<div class=\"srv-inner\" >\r\n\t\t\t\t\t");
          //  html:hidden
          org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
          _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
          _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_hidden_0.setProperty("wradioId");
          _jspx_th_html_hidden_0.setValue(wradioId.toString() );
          int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
          if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
            return;
          }
          _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
          out.write("\t\n\t\t\t\t\t");
          //  html:hidden
          org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
          _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
          _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_hidden_1.setProperty("applicationId");
          _jspx_th_html_hidden_1.setValue(applicationId.toString() );
          int _jspx_eval_html_hidden_1 = _jspx_th_html_hidden_1.doStartTag();
          if (_jspx_th_html_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_1);
            return;
          }
          _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_1);
          out.write("\t\r\n\t\t\t\t\t\r\n\t\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_iterate_0.setName("mySrvWebRadioFreeConfigForm");
          _jspx_th_logic_iterate_0.setProperty("listHours");
          _jspx_th_logic_iterate_0.setId("listHours");
          int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
          if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object listHours = null;
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_0.doInitBody();
            }
            listHours = (java.lang.Object) _jspx_page_context.findAttribute("listHours");
            do {
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_6.setId("agenda_id");
              _jspx_th_bean_define_6.setName("listHours");
              _jspx_th_bean_define_6.setProperty("agenda_id");
              int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
              if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
              java.lang.Object agenda_id = null;
              agenda_id = (java.lang.Object) _jspx_page_context.findAttribute("agenda_id");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_7.setId("agenda_key");
              _jspx_th_bean_define_7.setName("listHours");
              _jspx_th_bean_define_7.setProperty("agenda_key");
              _jspx_th_bean_define_7.setType("String");
              int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
              if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
                return;
              }
              _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
              String agenda_key = null;
              agenda_key = (String) _jspx_page_context.findAttribute("agenda_key");
              out.write("\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_8.setId("modulo");
              _jspx_th_bean_define_8.setValue( String.valueOf(Integer.parseInt(agenda_id.toString())%2) );
              int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
              if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_8);
                return;
              }
              _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_8);
              java.lang.String modulo = null;
              modulo = (java.lang.String) _jspx_page_context.findAttribute("modulo");
              out.write("\r\n\t\t\t\t\r\n\t\t\t\t\t\t\t<div class=\"srv-line-horaire \r\n\t\t\t\t\t\t\t\t");
              if (_jspx_meth_logic_equal_0(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              if (_jspx_meth_logic_equal_1(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t\t\t\" >\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t<div class=\"srv-line-delete\" >\r\n\t\t\t\t\t\t\t\t\t<a class=\"suppr\" href=\"javascript:;\" onclick=\"srvClearReveil(");
              out.print(agenda_id );
              out.write(");\"><span>x</span></a>\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"srv-line-day\" >\r\n\t\t\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "services/"+agenda_key));
              out.write("\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"srv-line-time\" >\n\t\t\t\t\t\t\t\t\t<input id=\"time");
              out.print(agenda_id );
              out.write("\" name=\"checkListHours\" type=\"hidden\" class=\"hourPicker\" value=\"");
              out.print(mySrvWebRadioFreeConfigForm.getCheckListHours()[Integer.parseInt(agenda_id.toString())-1]);
              out.write("\" />\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t<div class=\"srv-line-duration\" >\r\n\t\t\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "services/ListeningPeriod"));
              out.write("\r\n\t\t\t\t\t\t\t\t\t");
              //  html:select
              org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_0 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_value_property_name.get(org.apache.struts.taglib.html.SelectTag.class);
              _jspx_th_html_select_0.setPageContext(_jspx_page_context);
              _jspx_th_html_select_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_html_select_0.setName("mySrvWebRadioFreeConfigForm");
              _jspx_th_html_select_0.setProperty("listeningPeriodeSelected");
              _jspx_th_html_select_0.setValue(mySrvWebRadioFreeConfigForm.getListeningPeriodeSelected()[Integer.parseInt(agenda_id.toString())-1] );
              int _jspx_eval_html_select_0 = _jspx_th_html_select_0.doStartTag();
              if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_html_select_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_html_select_0.doInitBody();
                }
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t");
                  if (_jspx_meth_html_optionsCollection_0(_jspx_th_html_select_0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_html_select_0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_html_select_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_select_value_property_name.reuse(_jspx_th_html_select_0);
                return;
              }
              _jspx_tagPool_html_select_value_property_name.reuse(_jspx_th_html_select_0);
              out.write("\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
              listHours = (java.lang.Object) _jspx_page_context.findAttribute("listHours");
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
          out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_bean_define_9.setId("srv_vocal");
          _jspx_th_bean_define_9.setValue(srv.get("srv_vocal"));
          int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
          if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_9);
            return;
          }
          _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_9);
          java.lang.String srv_vocal = null;
          srv_vocal = (java.lang.String) _jspx_page_context.findAttribute("srv_vocal");
          out.write("\r\n\t\t\t\t\r\n\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_notEqual_0.setName("srv_vocal");
          _jspx_th_logic_notEqual_0.setValue("NOLOC");
          int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
          if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t<hr class=\"spacer\">\r\n\t\t\t\t\r\n\t\t\t\t<div>\r\n\t\t\t\t\t<label>");
              out.print(DicoTools.dico(dico_lang , "services/vocal"));
              out.write("</label>\r\n\t\t\t\t\t<span style=\"font-size:16px; font-weight:bold;\">");
              out.print(srv.get("srv_vocal"));
              out.write("</span> ");
              out.print(DicoTools.dico(dico_lang , "srv_all/reco_link"));
              out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t");
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
          out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t<hr class=\"spacer\">\r\n\t\t\t\t<div align=\"center\">\r\n\t\t\t\t");
          out.write("\r\n\t\t\t\t");
          if (_jspx_meth_html_hidden_2(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t\r\n\t\t\t\t");
          //  logic:notEmpty
          org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
          _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEmpty_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_notEmpty_0.setName("isRegistered");
          int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
          if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t");
              out.write("\t\t\t\r\n\t\t\t\t\t\t");
              //  html:button
              org.apache.struts.taglib.html.ButtonTag _jspx_th_html_button_0 = (org.apache.struts.taglib.html.ButtonTag) _jspx_tagPool_html_button_value_styleClass_property_onclick.get(org.apache.struts.taglib.html.ButtonTag.class);
              _jspx_th_html_button_0.setPageContext(_jspx_page_context);
              _jspx_th_html_button_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
              _jspx_th_html_button_0.setProperty("delete");
              _jspx_th_html_button_0.setValue(DicoTools.dico(dico_lang , "srv_clock/deactivate"));
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
                  out.print(DicoTools.dico(dico_lang , "srv_clock/deactivate"));
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
              out.write("\r\n\t\t\t\t\t\t");
              out.write("\t\r\n\t\t\t\t\t\t");
              //  html:submit
              org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_0 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_styleClass_property_onclick.get(org.apache.struts.taglib.html.SubmitTag.class);
              _jspx_th_html_submit_0.setPageContext(_jspx_page_context);
              _jspx_th_html_submit_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
              _jspx_th_html_submit_0.setProperty("update");
              _jspx_th_html_submit_0.setValue(DicoTools.dico(dico_lang , "srv_clock/update"));
              _jspx_th_html_submit_0.setStyleClass("genericBt");
              _jspx_th_html_submit_0.setOnclick("goDispatch('update', 'dispatchConfig')");
              int _jspx_eval_html_submit_0 = _jspx_th_html_submit_0.doStartTag();
              if (_jspx_eval_html_submit_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                if (_jspx_eval_html_submit_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_html_submit_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_html_submit_0.doInitBody();
                }
                do {
                  out.print(DicoTools.dico(dico_lang , "srv_clock/update"));
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
              out.write("\t\r\n\t\t\t\t");
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
          out.write("\r\n\t\t\t\t");
          //  logic:empty
          org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_0 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
          _jspx_th_logic_empty_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_empty_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_empty_0.setName("isRegistered");
          int _jspx_eval_logic_empty_0 = _jspx_th_logic_empty_0.doStartTag();
          if (_jspx_eval_logic_empty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t");
              out.write("\t\t\t\t\r\n\t\t\t\t\t");
              //  html:submit
              org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_1 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_styleClass_property_onclick.get(org.apache.struts.taglib.html.SubmitTag.class);
              _jspx_th_html_submit_1.setPageContext(_jspx_page_context);
              _jspx_th_html_submit_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_empty_0);
              _jspx_th_html_submit_1.setProperty("submit");
              _jspx_th_html_submit_1.setValue(DicoTools.dico(dico_lang , "srv_clock/activate"));
              _jspx_th_html_submit_1.setStyleClass("genericBt");
              _jspx_th_html_submit_1.setOnclick("goDispatch('activate', 'dispatchConfig')");
              int _jspx_eval_html_submit_1 = _jspx_th_html_submit_1.doStartTag();
              if (_jspx_eval_html_submit_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                if (_jspx_eval_html_submit_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_html_submit_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_html_submit_1.doInitBody();
                }
                do {
                  out.print(DicoTools.dico(dico_lang , "srv_clock/activate"));
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
              out.write("\r\n\t\t\t\t");
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
          out.write("\t\r\n\t\t\t\t</div>\t\t\r\n\t\t\t");
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
      out.write("\r\n\t\t");
      out.write("\t\r\n\t</div>\r\n</div>\r\n\r\n<script type=\"text/javascript\">\r\n\r\n\t$(\"input.hourPicker\").hourSelect_Init( $(\"#_user_24\").val(), false );\r\n\t\r\n\t$(\"#srvWebRadioFreeConfig\").submit(function(){\r\n\t\treturn nablifeValidateWebRadioFreeConfig(");
      if (_jspx_meth_logic_notEmpty_1(_jspx_page_context))
        return;
      out.write(");\t\t\r\n\t});\r\n\r\n\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_2.setParent(null);
      _jspx_th_logic_equal_2.setName("mySrvWebRadioFreeConfigForm");
      _jspx_th_logic_equal_2.setProperty("isV1");
      _jspx_th_logic_equal_2.setValue("true");
      int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
      if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\tsetTimeout(function(){\r\n\t\t\tblockUI(\"show\", \"");
          out.print(DicoTools.dico(dico_lang, "srv_podcast/notAV2"));
          out.write("\", \"div.mainTabBody\");\r\n\t\t}, 50);\r\n\t");
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
      out.write("\r\n\t\r\n</script>");
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

  private boolean _jspx_meth_logic_equal_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_logic_equal_0.setName("modulo");
    _jspx_th_logic_equal_0.setValue("0");
    int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
    if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("srv-line-odd");
        int evalDoAfterBody = _jspx_th_logic_equal_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_0);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_logic_equal_1.setName("modulo");
    _jspx_th_logic_equal_1.setValue("1");
    int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
    if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("srv-line-even");
        int evalDoAfterBody = _jspx_th_logic_equal_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_1);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_1);
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
    _jspx_th_html_optionsCollection_0.setName("mySrvWebRadioFreeConfigForm");
    _jspx_th_html_optionsCollection_0.setProperty("periodList");
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

  private boolean _jspx_meth_html_hidden_2(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_2 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_styleId_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_2.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_hidden_2.setStyleId("dispatchConfig");
    _jspx_th_html_hidden_2.setProperty("dispatch");
    _jspx_th_html_hidden_2.setValue("load");
    int _jspx_eval_html_hidden_2 = _jspx_th_html_hidden_2.doStartTag();
    if (_jspx_th_html_hidden_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_styleId_property_nobody.reuse(_jspx_th_html_hidden_2);
      return true;
    }
    _jspx_tagPool_html_hidden_value_styleId_property_nobody.reuse(_jspx_th_html_hidden_2);
    return false;
  }

  private boolean _jspx_meth_logic_notEmpty_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEmpty
    org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_1 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
    _jspx_th_logic_notEmpty_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEmpty_1.setParent(null);
    _jspx_th_logic_notEmpty_1.setName("dispatch");
    int _jspx_eval_logic_notEmpty_1 = _jspx_th_logic_notEmpty_1.doStartTag();
    if (_jspx_eval_logic_notEmpty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("true");
        int evalDoAfterBody = _jspx_th_logic_notEmpty_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEmpty_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEmpty_name.reuse(_jspx_th_logic_notEmpty_1);
      return true;
    }
    _jspx_tagPool_logic_notEmpty_name.reuse(_jspx_th_logic_notEmpty_1);
    return false;
  }
}
