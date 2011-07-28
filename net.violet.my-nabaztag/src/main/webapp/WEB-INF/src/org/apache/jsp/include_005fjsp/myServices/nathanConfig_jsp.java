package org.apache.jsp.include_005fjsp.myServices;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class nathanConfig_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_method_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_radio_value_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_submit_value_onclick_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_method_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_radio_value_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_submit_value_onclick_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_html_form_styleId_method_action.release();
    _jspx_tagPool_html_hidden_value_property_nobody.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_html_radio_value_property_name_nobody.release();
    _jspx_tagPool_html_submit_value_onclick_nobody.release();
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

      out.write('\n');
      out.write('\n');
      out.write('\n');
 response.setContentType("text/html;charset=UTF-8");
      out.write("\n\n\n\n\n\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write('\n');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("mySrvNathanConfigForm");
      _jspx_th_bean_define_0.setProperty("dicoRoot");
      _jspx_th_bean_define_0.setId("dicoRoot");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.Object dicoRoot = null;
      dicoRoot = (java.lang.Object) _jspx_page_context.findAttribute("dicoRoot");
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write("\n\n<div id=\"setUpSrv\">\n\t\n\t<div class=\"main-cadre-contener serviceContener serviceBookReaderConfig\">\t\n\t\t<div class=\"main-cadre-top\" >\n\t\t\t<h2>");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/config_title"));
      out.write("</h2>\n\t\t</div>\n\t\t\n\t\t<div class=\"main-cadre-content\" >\n\n\t\t\t<div class=\"intro\">");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/config_intro"));
      out.write("</div>\n\t\t\t\n\t\t\t<div class=\"ztamps-contener\">\n\t\t\t\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setId("isbn");
      _jspx_th_bean_define_1.setName("mySrvNathanConfigForm");
      _jspx_th_bean_define_1.setProperty("isbn");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object isbn = null;
      isbn = (java.lang.Object) _jspx_page_context.findAttribute("isbn");
      out.write("\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setId("versionId");
      _jspx_th_bean_define_2.setName("mySrvNathanConfigForm");
      _jspx_th_bean_define_2.setProperty("versionId");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      java.lang.Object versionId = null;
      versionId = (java.lang.Object) _jspx_page_context.findAttribute("versionId");
      out.write("\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setId("appletId");
      _jspx_th_bean_define_3.setName("mySrvNathanConfigForm");
      _jspx_th_bean_define_3.setProperty("appletId");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      java.lang.Object appletId = null;
      appletId = (java.lang.Object) _jspx_page_context.findAttribute("appletId");
      out.write("\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_4.setParent(null);
      _jspx_th_bean_define_4.setId("description");
      _jspx_th_bean_define_4.setName("mySrvNathanConfigForm");
      _jspx_th_bean_define_4.setProperty("description");
      int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
      if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
      java.lang.Object description = null;
      description = (java.lang.Object) _jspx_page_context.findAttribute("description");
      out.write("\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_5.setParent(null);
      _jspx_th_bean_define_5.setId("isShared");
      _jspx_th_bean_define_5.setName("mySrvNathanConfigForm");
      _jspx_th_bean_define_5.setProperty("isShared");
      int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
      if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
      java.lang.Object isShared = null;
      isShared = (java.lang.Object) _jspx_page_context.findAttribute("isShared");
      out.write("\n\t\t\t\n\t\t\t\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_method_action.get(org.apache.struts.taglib.html.FormTag.class);
      _jspx_th_html_form_0.setPageContext(_jspx_page_context);
      _jspx_th_html_form_0.setParent(null);
      _jspx_th_html_form_0.setAction("/action/srvNathanConfig.do");
      _jspx_th_html_form_0.setMethod("post");
      _jspx_th_html_form_0.setStyleId("configForm");
      int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
      if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t\t\t\n\t\t\t\t\t");
          if (_jspx_meth_html_hidden_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\t\n\t\t\t\t\t");
          //  html:hidden
          org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
          _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
          _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_hidden_1.setProperty("isbn");
          _jspx_th_html_hidden_1.setValue(isbn.toString());
          int _jspx_eval_html_hidden_1 = _jspx_th_html_hidden_1.doStartTag();
          if (_jspx_th_html_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_1);
            return;
          }
          _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_1);
          out.write("\n\t\t\t\t\t");
          //  html:hidden
          org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_2 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
          _jspx_th_html_hidden_2.setPageContext(_jspx_page_context);
          _jspx_th_html_hidden_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_hidden_2.setProperty("versionId");
          _jspx_th_html_hidden_2.setValue(versionId.toString());
          int _jspx_eval_html_hidden_2 = _jspx_th_html_hidden_2.doStartTag();
          if (_jspx_th_html_hidden_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_2);
            return;
          }
          _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_2);
          out.write("\n\t\t\t\t\t");
          //  html:hidden
          org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_3 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
          _jspx_th_html_hidden_3.setPageContext(_jspx_page_context);
          _jspx_th_html_hidden_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_hidden_3.setProperty("appletId");
          _jspx_th_html_hidden_3.setValue(appletId.toString());
          int _jspx_eval_html_hidden_3 = _jspx_th_html_hidden_3.doStartTag();
          if (_jspx_th_html_hidden_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_3);
            return;
          }
          _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_3);
          out.write("\t\t\t\t\t\n\t\t\t\t<div class=\"nathan\">\t\n\t\t\t\t<div class=\"enregistrement\">\n\t\t\t\t\t<strong>");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_describe"));
          out.write(" </strong><br />\n\t\t\t\t\t\n\t\t\t\t\t<b>");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_i_am"));
          out.write(" : </b><br /><br />\n\t\t\t\t\t<div class=\"nathan-descr-box\">\n\t\t\t\t\t<ul>\n\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_iterate_0.setId("tag");
          _jspx_th_logic_iterate_0.setName("mySrvNathanConfigForm");
          _jspx_th_logic_iterate_0.setProperty("sexTags");
          int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
          if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object tag = null;
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_0.doInitBody();
            }
            tag = (java.lang.Object) _jspx_page_context.findAttribute("tag");
            do {
              out.write("\n\t\t\t\t\t\t<li>");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_6.setId("tagId");
              _jspx_th_bean_define_6.setName("tag");
              _jspx_th_bean_define_6.setProperty("id");
              int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
              if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
              java.lang.Object tagId = null;
              tagId = (java.lang.Object) _jspx_page_context.findAttribute("tagId");
              out.write("\n\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_7.setId("tagLabel");
              _jspx_th_bean_define_7.setName("tag");
              _jspx_th_bean_define_7.setProperty("label");
              _jspx_th_bean_define_7.setType("java.lang.String");
              int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
              if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
                return;
              }
              _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
              java.lang.String tagLabel = null;
              tagLabel = (java.lang.String) _jspx_page_context.findAttribute("tagLabel");
              out.write("\n\t\t\t\t\t\t");
              //  html:radio
              org.apache.struts.taglib.html.RadioTag _jspx_th_html_radio_0 = (org.apache.struts.taglib.html.RadioTag) _jspx_tagPool_html_radio_value_property_name_nobody.get(org.apache.struts.taglib.html.RadioTag.class);
              _jspx_th_html_radio_0.setPageContext(_jspx_page_context);
              _jspx_th_html_radio_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_html_radio_0.setName("mySrvNathanConfigForm");
              _jspx_th_html_radio_0.setProperty("checkedSexTag");
              _jspx_th_html_radio_0.setValue(tagId.toString());
              int _jspx_eval_html_radio_0 = _jspx_th_html_radio_0.doStartTag();
              if (_jspx_th_html_radio_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_0);
                return;
              }
              _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_0);
              out.print(DicoTools.dico(dico_lang , tagLabel));
              out.write("\n\t\t\t\t\t\t</li>\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
              tag = (java.lang.Object) _jspx_page_context.findAttribute("tag");
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
          out.write("\n\t\t\t\t\t</ul>\n\t\t\t\t\t</div>\n\t\t\t\t\t<hr class=\"clearer\" />\n\t\t\t\t\t\n\t\t\t\t\t\n\t\t\t\t\t<b>");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_interpretation"));
          out.write(" : </b><br /><br />\n\t\t\t\t\t<div class=\"nathan-descr-box\">\n\t\t\t\t\t<ul>\n\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_iterate_1.setId("tag");
          _jspx_th_logic_iterate_1.setName("mySrvNathanConfigForm");
          _jspx_th_logic_iterate_1.setProperty("interpretationTags");
          int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
          if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object tag = null;
            if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_1.doInitBody();
            }
            tag = (java.lang.Object) _jspx_page_context.findAttribute("tag");
            do {
              out.write("\n\t\t\t\t\t\t<li>");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_8.setId("tagId");
              _jspx_th_bean_define_8.setName("tag");
              _jspx_th_bean_define_8.setProperty("id");
              int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
              if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
              java.lang.Object tagId = null;
              tagId = (java.lang.Object) _jspx_page_context.findAttribute("tagId");
              out.write("\n\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_9.setId("tagLabel");
              _jspx_th_bean_define_9.setName("tag");
              _jspx_th_bean_define_9.setProperty("label");
              _jspx_th_bean_define_9.setType("java.lang.String");
              int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
              if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                return;
              }
              _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
              java.lang.String tagLabel = null;
              tagLabel = (java.lang.String) _jspx_page_context.findAttribute("tagLabel");
              out.write("\n\t\t\t\t\t\t");
              //  html:radio
              org.apache.struts.taglib.html.RadioTag _jspx_th_html_radio_1 = (org.apache.struts.taglib.html.RadioTag) _jspx_tagPool_html_radio_value_property_name_nobody.get(org.apache.struts.taglib.html.RadioTag.class);
              _jspx_th_html_radio_1.setPageContext(_jspx_page_context);
              _jspx_th_html_radio_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_html_radio_1.setName("mySrvNathanConfigForm");
              _jspx_th_html_radio_1.setProperty("checkedInterpretationTag");
              _jspx_th_html_radio_1.setValue(tagId.toString());
              int _jspx_eval_html_radio_1 = _jspx_th_html_radio_1.doStartTag();
              if (_jspx_th_html_radio_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_1);
                return;
              }
              _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_1);
              out.print(DicoTools.dico(dico_lang , tagLabel));
              out.write("\n\t\t\t\t\t\t</li>\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
              tag = (java.lang.Object) _jspx_page_context.findAttribute("tag");
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_logic_iterate_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_1);
            return;
          }
          _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_1);
          out.write(" \n\t\t\t\t\t</ul>\n\t\t\t\t\t</div>\n\t\t\t\t\t<hr class=\"clearer\" />\n\t\t\t\t\t\n\t\t\t\t\t<b>");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_effects"));
          out.write(" : </b><br/>\n\t\t\t\t\t<div class=\"nathan-descr-box\">\n\t\t\t\t\t<ul>\n\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_2 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_2.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_iterate_2.setId("tag");
          _jspx_th_logic_iterate_2.setName("mySrvNathanConfigForm");
          _jspx_th_logic_iterate_2.setProperty("effectsTags");
          int _jspx_eval_logic_iterate_2 = _jspx_th_logic_iterate_2.doStartTag();
          if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object tag = null;
            if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_2.doInitBody();
            }
            tag = (java.lang.Object) _jspx_page_context.findAttribute("tag");
            do {
              out.write("\n\t\t\t\t\t\t<li>");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
              _jspx_th_bean_define_10.setId("tagId");
              _jspx_th_bean_define_10.setName("tag");
              _jspx_th_bean_define_10.setProperty("id");
              int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
              if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
              java.lang.Object tagId = null;
              tagId = (java.lang.Object) _jspx_page_context.findAttribute("tagId");
              out.write("\n\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
              _jspx_th_bean_define_11.setId("tagLabel");
              _jspx_th_bean_define_11.setName("tag");
              _jspx_th_bean_define_11.setProperty("label");
              _jspx_th_bean_define_11.setType("java.lang.String");
              int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
              if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
                return;
              }
              _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
              java.lang.String tagLabel = null;
              tagLabel = (java.lang.String) _jspx_page_context.findAttribute("tagLabel");
              out.write("\n\t\t\t\t\t\t");
              //  html:radio
              org.apache.struts.taglib.html.RadioTag _jspx_th_html_radio_2 = (org.apache.struts.taglib.html.RadioTag) _jspx_tagPool_html_radio_value_property_name_nobody.get(org.apache.struts.taglib.html.RadioTag.class);
              _jspx_th_html_radio_2.setPageContext(_jspx_page_context);
              _jspx_th_html_radio_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
              _jspx_th_html_radio_2.setName("mySrvNathanConfigForm");
              _jspx_th_html_radio_2.setProperty("checkedEffectsTag");
              _jspx_th_html_radio_2.setValue(tagId.toString());
              int _jspx_eval_html_radio_2 = _jspx_th_html_radio_2.doStartTag();
              if (_jspx_th_html_radio_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_2);
                return;
              }
              _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_2);
              out.print(DicoTools.dico(dico_lang , tagLabel));
              out.write("\n\t\t\t\t\t\t</li>\t\t\t\t\t\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_2.doAfterBody();
              tag = (java.lang.Object) _jspx_page_context.findAttribute("tag");
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_logic_iterate_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_2);
            return;
          }
          _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_2);
          out.write("\n\t\t\t\t\t</ul>\n\t\t\t\t\t</div>\n\t\t\t\t\t<hr class=\"clearer\" />\n\t\t\t\t\t\n\t\t\t\t\t<b>");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_i_speak"));
          out.write(" : </b><br /><br />\n\t\t\t\t\t<div class=\"nathan-descr-box\">\n\t\t\t\t\t<ul>\n\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_3 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_3.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_iterate_3.setId("tag");
          _jspx_th_logic_iterate_3.setName("mySrvNathanConfigForm");
          _jspx_th_logic_iterate_3.setProperty("accentTags");
          int _jspx_eval_logic_iterate_3 = _jspx_th_logic_iterate_3.doStartTag();
          if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object tag = null;
            if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_3.doInitBody();
            }
            tag = (java.lang.Object) _jspx_page_context.findAttribute("tag");
            do {
              out.write("\n\t\t\t\t\t\t<li>");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_12 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_12.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
              _jspx_th_bean_define_12.setId("tagId");
              _jspx_th_bean_define_12.setName("tag");
              _jspx_th_bean_define_12.setProperty("id");
              int _jspx_eval_bean_define_12 = _jspx_th_bean_define_12.doStartTag();
              if (_jspx_th_bean_define_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
              java.lang.Object tagId = null;
              tagId = (java.lang.Object) _jspx_page_context.findAttribute("tagId");
              out.write("\n\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_13 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_13.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
              _jspx_th_bean_define_13.setId("tagLabel");
              _jspx_th_bean_define_13.setName("tag");
              _jspx_th_bean_define_13.setProperty("label");
              _jspx_th_bean_define_13.setType("java.lang.String");
              int _jspx_eval_bean_define_13 = _jspx_th_bean_define_13.doStartTag();
              if (_jspx_th_bean_define_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
                return;
              }
              _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
              java.lang.String tagLabel = null;
              tagLabel = (java.lang.String) _jspx_page_context.findAttribute("tagLabel");
              out.write("\n\t\t\t\t\t\t");
              //  html:radio
              org.apache.struts.taglib.html.RadioTag _jspx_th_html_radio_3 = (org.apache.struts.taglib.html.RadioTag) _jspx_tagPool_html_radio_value_property_name_nobody.get(org.apache.struts.taglib.html.RadioTag.class);
              _jspx_th_html_radio_3.setPageContext(_jspx_page_context);
              _jspx_th_html_radio_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
              _jspx_th_html_radio_3.setName("mySrvNathanConfigForm");
              _jspx_th_html_radio_3.setProperty("checkedAccentTag");
              _jspx_th_html_radio_3.setValue(tagId.toString());
              int _jspx_eval_html_radio_3 = _jspx_th_html_radio_3.doStartTag();
              if (_jspx_th_html_radio_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_3);
                return;
              }
              _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_3);
              out.print(DicoTools.dico(dico_lang , tagLabel));
              out.write("\n\t\t\t\t\t\t</li>\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_3.doAfterBody();
              tag = (java.lang.Object) _jspx_page_context.findAttribute("tag");
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
          out.write("\n\t\t\t\t\t</ul>\n\t\t\t\t\t</div>\n\t\t\t\t\t<hr class=\"clearer\" /><br/>\n\t\t\t\t\t\n\t\t\t\t\t<strong>");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_description"));
          out.write(" </strong> \n\t\t\t\t\t<textarea name=\"description\" cols=\"50\">");
          out.print(description);
          out.write("</textarea><br/><br/>\n\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_des_share"));
          out.write("\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_partage"));
          out.write("<br/>\n\t\t\t\t\t<div class=\"service-description\">\n\t\t\t\t\t<p>");
          if (_jspx_meth_html_radio_4(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_dont_share"));
          out.write("</p>\n\t\t\t\t\t<p>");
          if (_jspx_meth_html_radio_5(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_share"));
          out.write("</p>\n\t\t\t\t\t<br />\n\t\t\t\t\t<div class=\"item-tools\">\n\t\t\t\t\t");
          //  html:submit
          org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_0 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_onclick_nobody.get(org.apache.struts.taglib.html.SubmitTag.class);
          _jspx_th_html_submit_0.setPageContext(_jspx_page_context);
          _jspx_th_html_submit_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_submit_0.setValue(DicoTools.dico(dico_lang , "srv_nathan/config_validate"));
          _jspx_th_html_submit_0.setOnclick("page.postAjax('configForm', 'setUpSrv-container'); return false;");
          int _jspx_eval_html_submit_0 = _jspx_th_html_submit_0.doStartTag();
          if (_jspx_th_html_submit_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_submit_value_onclick_nobody.reuse(_jspx_th_html_submit_0);
            return;
          }
          _jspx_tagPool_html_submit_value_onclick_nobody.reuse(_jspx_th_html_submit_0);
          out.write("\n\t\t\t\t\t</div></div></div></div>\n\t\t\t\t\t<div class=\"info-note\">\n\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/config_hint"));
          out.write("\n\t\t\t\t\t</div>\n\t\t\t\t\n\t\t\t\t");
          int evalDoAfterBody = _jspx_th_html_form_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_html_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_html_form_styleId_method_action.reuse(_jspx_th_html_form_0);
        return;
      }
      _jspx_tagPool_html_form_styleId_method_action.reuse(_jspx_th_html_form_0);
      out.write("\n\t\t\t\t\n\t\t\t</div>\n\n\t\t\t<hr class=\"clearer\" />\n\t\t\t\n\t\t\t");
      out.write("\n\t\t</div><!-- End of main content -->\n\t</div>\n\t\n</div>\n\n\n\n<div id=\"how-to-container\" class=\"main-cadre-contener\">\n\n\t<div class=\"main-cadre-top\"><h2>");
      out.print(DicoTools.dico(dico_lang , "services/how_does_it_work"));
      out.write("</h2></div>\n\t\n\t<div class=\"main-cadre-content\">\n\t\t<hr class=\"spacer\"/>\n\t\t<div class=\"srv-main-config\">\n\t\t\t<p>\t");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/how_to_config"));
      out.write("</p>\n\t\t</div>\n\t\t\n\t\t<hr class=\"spacer\"/>\n\t\t\t\n\t</div>\n</div>\n\n");
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

  private boolean _jspx_meth_html_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_hidden_0.setProperty("dispatch");
    _jspx_th_html_hidden_0.setValue("edit");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
    return false;
  }

  private boolean _jspx_meth_html_radio_4(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:radio
    org.apache.struts.taglib.html.RadioTag _jspx_th_html_radio_4 = (org.apache.struts.taglib.html.RadioTag) _jspx_tagPool_html_radio_value_property_name_nobody.get(org.apache.struts.taglib.html.RadioTag.class);
    _jspx_th_html_radio_4.setPageContext(_jspx_page_context);
    _jspx_th_html_radio_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_radio_4.setName("mySrvNathanConfigForm");
    _jspx_th_html_radio_4.setProperty("isShared");
    _jspx_th_html_radio_4.setValue("0");
    int _jspx_eval_html_radio_4 = _jspx_th_html_radio_4.doStartTag();
    if (_jspx_th_html_radio_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_4);
      return true;
    }
    _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_4);
    return false;
  }

  private boolean _jspx_meth_html_radio_5(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:radio
    org.apache.struts.taglib.html.RadioTag _jspx_th_html_radio_5 = (org.apache.struts.taglib.html.RadioTag) _jspx_tagPool_html_radio_value_property_name_nobody.get(org.apache.struts.taglib.html.RadioTag.class);
    _jspx_th_html_radio_5.setPageContext(_jspx_page_context);
    _jspx_th_html_radio_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_radio_5.setName("mySrvNathanConfigForm");
    _jspx_th_html_radio_5.setProperty("isShared");
    _jspx_th_html_radio_5.setValue("1");
    int _jspx_eval_html_radio_5 = _jspx_th_html_radio_5.doStartTag();
    if (_jspx_th_html_radio_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_5);
      return true;
    }
    _jspx_tagPool_html_radio_value_property_name_nobody.reuse(_jspx_th_html_radio_5);
    return false;
  }
}
