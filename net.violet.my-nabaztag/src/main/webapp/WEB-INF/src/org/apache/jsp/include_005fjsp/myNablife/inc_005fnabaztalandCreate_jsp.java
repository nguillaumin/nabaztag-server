package org.apache.jsp.include_005fjsp.myNablife;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fnabaztalandCreate_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_text_styleId_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_textarea_styleId_rows_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_select_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_lessEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_option_value;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_select_styleId_style_property;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_text_style_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_styleId_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_rewrite_forward_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_text_styleId_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_textarea_styleId_rows_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_select_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_lessEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_option_value = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_select_styleId_style_property = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_text_style_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_styleId_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_rewrite_forward_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_equal_value_name.release();
    _jspx_tagPool_html_form_styleId_action.release();
    _jspx_tagPool_logic_notEqual_value_name.release();
    _jspx_tagPool_html_text_styleId_property_name.release();
    _jspx_tagPool_html_textarea_styleId_rows_property_name.release();
    _jspx_tagPool_html_select_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_logic_lessEqual_value_name.release();
    _jspx_tagPool_html_option_value.release();
    _jspx_tagPool_html_select_styleId_style_property.release();
    _jspx_tagPool_html_text_style_property_name_nobody.release();
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.release();
    _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.release();
    _jspx_tagPool_html_rewrite_forward_nobody.release();
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

      out.write("\r\n\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myNabaztalandCreateForm");
      _jspx_th_bean_define_0.setProperty("mode");
      _jspx_th_bean_define_0.setId("mode");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.Object mode = null;
      mode = (java.lang.Object) _jspx_page_context.findAttribute("mode");
      out.write("\r\n\r\n");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_0.setParent(null);
      _jspx_th_logic_equal_0.setName("mode");
      _jspx_th_logic_equal_0.setValue("0");
      int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
      if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
          _jspx_th_bean_define_1.setId("idNabcast");
          _jspx_th_bean_define_1.setName("myNabaztalandCreateForm");
          _jspx_th_bean_define_1.setProperty("idNabcast");
          int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
          if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
          java.lang.Object idNabcast = null;
          idNabcast = (java.lang.Object) _jspx_page_context.findAttribute("idNabcast");
          out.write("\r\n\r\n<div class=\"intro-cadre-contener intro-cadre-simple service-introduction\">\r\n\t\r\n\t<div class=\"intro-cadre-top\">\r\n\t\t<img alt=\"nabcast\" src=\"../include_img/services/icones_services/icon_nabcast.gif\" class=\"icone-service\"/>\r\n\t\t<h2>");
          out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_new_title"));
          out.write("</h2>\r\n\t</div>\r\n\t\r\n\r\n\t<div class=\"intro-cadre-content\">\r\n\t\t\r\n\t\t\t<div class=\"image\">\r\n\t\t\t\t<img src=\"../include_img/abo/nabcasts.gif\"/>\t\t\r\n\t\t\t</div>\r\n\r\n\t\t\t<div class=\"description\">\r\n\t\t\t\t");
          out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_new_text"));
          out.write("\r\n\t\t\t</div>\r\n\t</div>\r\n\t\r\n\t<hr class=\"clearer\" />\r\n\t\r\n</div>\r\n\t\r\n");
          //  html:form
          org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_action.get(org.apache.struts.taglib.html.FormTag.class);
          _jspx_th_html_form_0.setPageContext(_jspx_page_context);
          _jspx_th_html_form_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
          _jspx_th_html_form_0.setAction("/action/myNabaztalandCreate");
          _jspx_th_html_form_0.setStyleId("formNabcast");
          int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
          if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write('\r');
              out.write('\n');
              out.write('	');
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_bean_define_2.setId("soundList");
              _jspx_th_bean_define_2.setName("myNabaztalandCreateForm");
              _jspx_th_bean_define_2.setProperty("nabcast_soundList");
              int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
              if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
              java.lang.Object soundList = null;
              soundList = (java.lang.Object) _jspx_page_context.findAttribute("soundList");
              out.write('\r');
              out.write('\n');
              out.write('	');
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_bean_define_3.setId("animList");
              _jspx_th_bean_define_3.setName("myNabaztalandCreateForm");
              _jspx_th_bean_define_3.setProperty("nabcast_animList");
              int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
              if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
              java.lang.Object animList = null;
              animList = (java.lang.Object) _jspx_page_context.findAttribute("animList");
              out.write('\r');
              out.write('\n');
              out.write('	');
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_bean_define_4.setId("nabcast_categorie");
              _jspx_th_bean_define_4.setName("myNabaztalandCreateForm");
              _jspx_th_bean_define_4.setProperty("nabcast_categorie");
              int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
              if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
              java.lang.Object nabcast_categorie = null;
              nabcast_categorie = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_categorie");
              out.write("\r\n\r\n<div class=\"specialBlock\" style=\"overflow:hidden;\">\r\n\t<div class=\"createNabcast Column50\" >\r\n\t\t<h2 class='nabcastName'>\r\n\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_logic_equal_1.setName("idNabcast");
              _jspx_th_logic_equal_1.setValue("0");
              int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
              if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t");
                  out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_new"));
                  out.write("\r\n\t\t\t");
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
              //  logic:notEqual
              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
              _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_logic_notEqual_0.setName("idNabcast");
              _jspx_th_logic_notEqual_0.setValue("0");
              int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
              if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
                  _jspx_th_bean_define_5.setId("titre");
                  _jspx_th_bean_define_5.setName("myNabaztalandCreateForm");
                  _jspx_th_bean_define_5.setProperty("nabcast_title");
                  int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
                  if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
                  java.lang.Object titre = null;
                  titre = (java.lang.Object) _jspx_page_context.findAttribute("titre");
                  out.write("\r\n\t\t\t\t");
                  out.print(titre);
                  out.write("\r\n\t\t\t");
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
              out.write("\t\t\r\n\t\t</h2>\r\n\t\t\r\n\r\n\t\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_title"));
              out.write("</label>\r\n\t\t\t");
              //  html:text
              org.apache.struts.taglib.html.TextTag _jspx_th_html_text_0 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_styleId_property_name.get(org.apache.struts.taglib.html.TextTag.class);
              _jspx_th_html_text_0.setPageContext(_jspx_page_context);
              _jspx_th_html_text_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_html_text_0.setName("myNabaztalandCreateForm");
              _jspx_th_html_text_0.setProperty("nabcast_title");
              _jspx_th_html_text_0.setStyleId("nabcastName");
              int _jspx_eval_html_text_0 = _jspx_th_html_text_0.doStartTag();
              if (_jspx_eval_html_text_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                if (_jspx_eval_html_text_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_html_text_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_html_text_0.doInitBody();
                }
                do {
                  out.write("\r\n\t\t\t\t");
                  out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_type_title"));
                  out.write("\r\n\t\t\t");
                  int evalDoAfterBody = _jspx_th_html_text_0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_html_text_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_html_text_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_text_styleId_property_name.reuse(_jspx_th_html_text_0);
                return;
              }
              _jspx_tagPool_html_text_styleId_property_name.reuse(_jspx_th_html_text_0);
              out.write("\r\n\r\n\t\r\n\t\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_description"));
              out.write("</label>\r\n\t\t\t");
              //  html:textarea
              org.apache.struts.taglib.html.TextareaTag _jspx_th_html_textarea_0 = (org.apache.struts.taglib.html.TextareaTag) _jspx_tagPool_html_textarea_styleId_rows_property_name.get(org.apache.struts.taglib.html.TextareaTag.class);
              _jspx_th_html_textarea_0.setPageContext(_jspx_page_context);
              _jspx_th_html_textarea_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_html_textarea_0.setName("myNabaztalandCreateForm");
              _jspx_th_html_textarea_0.setProperty("nabcast_description");
              _jspx_th_html_textarea_0.setRows("5");
              _jspx_th_html_textarea_0.setStyleId("nabcastDesc");
              int _jspx_eval_html_textarea_0 = _jspx_th_html_textarea_0.doStartTag();
              if (_jspx_eval_html_textarea_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                if (_jspx_eval_html_textarea_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_html_textarea_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_html_textarea_0.doInitBody();
                }
                do {
                  out.write("\r\n\t\t\t\t");
                  out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_type_description"));
                  out.write("\r\n\t\t\t");
                  int evalDoAfterBody = _jspx_th_html_textarea_0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_html_textarea_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_html_textarea_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_textarea_styleId_rows_property_name.reuse(_jspx_th_html_textarea_0);
                return;
              }
              _jspx_tagPool_html_textarea_styleId_rows_property_name.reuse(_jspx_th_html_textarea_0);
              out.write("\r\n\t\t\r\n\t\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_language"));
              out.write("</label>\r\n\t\t\t\r\n\t\t\t");
              //  html:select
              org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_0 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_property_name.get(org.apache.struts.taglib.html.SelectTag.class);
              _jspx_th_html_select_0.setPageContext(_jspx_page_context);
              _jspx_th_html_select_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_html_select_0.setName("myNabaztalandCreateForm");
              _jspx_th_html_select_0.setProperty("nabcast_lang");
              int _jspx_eval_html_select_0 = _jspx_th_html_select_0.doStartTag();
              if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_html_select_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_html_select_0.doInitBody();
                }
                do {
                  out.write("\r\n\t\t\t\t");
                  //  logic:iterate
                  org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
                  _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
                  _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_0);
                  _jspx_th_logic_iterate_0.setName("myNabaztalandCreateForm");
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
                      out.write("\r\n\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                      _jspx_th_bean_define_6.setName("langData");
                      _jspx_th_bean_define_6.setProperty("lang_id");
                      _jspx_th_bean_define_6.setId("lang_id");
                      int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
                      if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                      java.lang.Object lang_id = null;
                      lang_id = (java.lang.Object) _jspx_page_context.findAttribute("lang_id");
                      out.write("\r\n\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                      _jspx_th_bean_define_7.setName("langData");
                      _jspx_th_bean_define_7.setProperty("lang_title");
                      _jspx_th_bean_define_7.setId("lang_title");
                      int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
                      if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
                      java.lang.Object lang_title = null;
                      lang_title = (java.lang.Object) _jspx_page_context.findAttribute("lang_title");
                      out.write("\r\n\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                      _jspx_th_bean_define_8.setName("langData");
                      _jspx_th_bean_define_8.setProperty("lang_type");
                      _jspx_th_bean_define_8.setId("lang_type");
                      int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
                      if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                      java.lang.Object lang_type = null;
                      lang_type = (java.lang.Object) _jspx_page_context.findAttribute("lang_type");
                      out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t");
                      //  logic:lessEqual
                      org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_0 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
                      _jspx_th_logic_lessEqual_0.setPageContext(_jspx_page_context);
                      _jspx_th_logic_lessEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                      _jspx_th_logic_lessEqual_0.setName("lang_type");
                      _jspx_th_logic_lessEqual_0.setValue("0");
                      int _jspx_eval_logic_lessEqual_0 = _jspx_th_logic_lessEqual_0.doStartTag();
                      if (_jspx_eval_logic_lessEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        do {
                          out.write("\r\n\t\t\t\t\t\t\t");
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
                          out.write("\r\n\t\t\t\t\t");
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
                      out.write("\r\n\t\t\t\t");
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
                _jspx_tagPool_html_select_property_name.reuse(_jspx_th_html_select_0);
                return;
              }
              _jspx_tagPool_html_select_property_name.reuse(_jspx_th_html_select_0);
              out.write("\r\n\t\t\r\n\r\n\t\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_category"));
              out.write("</label>\r\n\r\n\t\t\t<div style='width:65%;padding:5px; background-color:#ffffff' id='categContener'>\r\n\r\n\t\t\t ");
              //  html:select
              org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_1 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_styleId_style_property.get(org.apache.struts.taglib.html.SelectTag.class);
              _jspx_th_html_select_1.setPageContext(_jspx_page_context);
              _jspx_th_html_select_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_html_select_1.setProperty("nabcast_categorie");
              _jspx_th_html_select_1.setStyleId("nabcastCat");
              _jspx_th_html_select_1.setStyle("width:100%;");
              int _jspx_eval_html_select_1 = _jspx_th_html_select_1.doStartTag();
              if (_jspx_eval_html_select_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                if (_jspx_eval_html_select_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_html_select_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_html_select_1.doInitBody();
                }
                do {
                  out.write("\r\n\t\t\t\t\t<!-- <option class=\"sticky\" value=\"\">");
                  out.print(DicoTools.dico(dico_lang, "srv_create/srv_create/nabcast_choose_category"));
                  out.write("</option>-->\r\n\t\t\t\t\t");
                  //  logic:iterate
                  org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
                  _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
                  _jspx_th_logic_iterate_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_1);
                  _jspx_th_logic_iterate_1.setName("myNabaztalandCreateForm");
                  _jspx_th_logic_iterate_1.setProperty("nabcast_categorieList");
                  _jspx_th_logic_iterate_1.setId("nabcastCategData");
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
                      out.write("\r\n\t\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
                      _jspx_th_bean_define_9.setName("nabcastCategData");
                      _jspx_th_bean_define_9.setProperty("nabcastcateg_id");
                      _jspx_th_bean_define_9.setId("nabcastcateg_id");
                      int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
                      if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                      java.lang.Object nabcastcateg_id = null;
                      nabcastcateg_id = (java.lang.Object) _jspx_page_context.findAttribute("nabcastcateg_id");
                      out.write("\r\n\t\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
                      _jspx_th_bean_define_10.setName("nabcastCategData");
                      _jspx_th_bean_define_10.setProperty("nabcastcateg_val");
                      _jspx_th_bean_define_10.setId("nabcastcateg_val");
                      int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
                      if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
                      java.lang.Object nabcastcateg_val = null;
                      nabcastcateg_val = (java.lang.Object) _jspx_page_context.findAttribute("nabcastcateg_val");
                      out.write("\r\n\t\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
                      _jspx_th_bean_define_11.setName("nabcastCategData");
                      _jspx_th_bean_define_11.setProperty("nabcastcateg_lang");
                      _jspx_th_bean_define_11.setId("nabcastcateg_lang");
                      int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
                      if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
                      java.lang.Object nabcastcateg_lang = null;
                      nabcastcateg_lang = (java.lang.Object) _jspx_page_context.findAttribute("nabcastcateg_lang");
                      out.write("\r\n\t\t\t\t\t\t");
                      //  bean:define
                      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_12 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                      _jspx_th_bean_define_12.setPageContext(_jspx_page_context);
                      _jspx_th_bean_define_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
                      _jspx_th_bean_define_12.setName("nabcastCategData");
                      _jspx_th_bean_define_12.setProperty("nabcastcateg_desc");
                      _jspx_th_bean_define_12.setId("nabcastcateg_desc");
                      int _jspx_eval_bean_define_12 = _jspx_th_bean_define_12.doStartTag();
                      if (_jspx_th_bean_define_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
                        return;
                      }
                      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
                      java.lang.Object nabcastcateg_desc = null;
                      nabcastcateg_desc = (java.lang.Object) _jspx_page_context.findAttribute("nabcastcateg_desc");
                      out.write("\r\n\t\r\n\t\t\t\t\t\t<option class=\"");
                      out.print("lang_"+nabcastcateg_lang.toString());
                      out.write("\" value=\"");
                      out.print(nabcastcateg_id.toString());
                      out.write('"');
                      out.write(' ');
                      out.print( (nabcast_categorie.equals(nabcastcateg_id.toString())) ? "selected" : "" );
                      out.write('>');
                      out.print(nabcastcateg_val.toString());
                      out.write("</option>\r\n\t\t\t\t\t");
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
                    _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_1);
                    return;
                  }
                  _jspx_tagPool_logic_iterate_property_name_id.reuse(_jspx_th_logic_iterate_1);
                  out.write("\r\n\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_html_select_1.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_html_select_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_html_select_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_select_styleId_style_property.reuse(_jspx_th_html_select_1);
                return;
              }
              _jspx_tagPool_html_select_styleId_style_property.reuse(_jspx_th_html_select_1);
              out.write("\r\n\t\t\t</div>\t\r\n\t\t\t\r\n\t\t\t");
              out.write("\r\n\t\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_shortcut"));
              out.write("</label>\r\n\t\t\thttp://my.nabaztag.com/n/ ");
              if (_jspx_meth_html_text_1(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t<p class=\"little-hint\">");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_shortcut_hint"));
              out.write("</p>\r\n\t</div>\r\n\r\n<div class=\"Column50 createNabcast\">\t\r\n\t<h2>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_signature"));
              out.write("</h2>\r\n\r\n\r\n\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_signature_color"));
              out.write("</label>\t\r\n\t\t<ul class=\"colorChoice\" id=\"colorPicker\" style=\"float:left;\">\r\n\t\t\t<li style=\"background-color:#ff0000;\"><a class=\"color_unselected\" href=\"#\"><span>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_color_red"));
              out.write("</span></a></li>\r\n\t\t\t<li style=\"background-color:#ffff00;\"><a class=\"color_unselected\" href=\"#\"><span>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_color_yellow"));
              out.write("</span></a></li>\r\n\t\t\t<li style=\"background-color:#00ff00;\"><a class=\"color_unselected\" href=\"#\"><span>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_color_green"));
              out.write("</span></a></li>\r\n\t\t\t<li style=\"background-color:#00ffff;\"><a class=\"color_unselected\" href=\"#\"><span>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_color_cyan"));
              out.write("</span></a></li>\t\t\t\t\t\t\t\t\t\r\n\t\t\t<li style=\"background-color:#0000ff;\"><a class=\"color_unselected\" href=\"#\"><span>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_color_blue"));
              out.write("</span></a></li>\r\n\t\t\t<li style=\"background-color:#ff00ff;\"><a class=\"color_unselected\" href=\"#\"><span>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_color_mauve"));
              out.write("</span></a></li>\t\r\n\t\t</ul>\r\n\r\n\t\t<hr class=\"spacer\" />\r\n\r\n\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_signature_animation"));
              out.write("</label>\r\n\t\t");
              //  html:select
              org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_2 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_property_name.get(org.apache.struts.taglib.html.SelectTag.class);
              _jspx_th_html_select_2.setPageContext(_jspx_page_context);
              _jspx_th_html_select_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_html_select_2.setName("myNabaztalandCreateForm");
              _jspx_th_html_select_2.setProperty("nabcast_anim");
              int _jspx_eval_html_select_2 = _jspx_th_html_select_2.doStartTag();
              if (_jspx_eval_html_select_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                if (_jspx_eval_html_select_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_html_select_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_html_select_2.doInitBody();
                }
                do {
                  out.write("\r\n\t\t\t<option value=\"0\">");
                  out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_signature_choose_animation"));
                  out.write("</option>\t\r\n\t\t\t");
                  if (_jspx_meth_html_optionsCollection_0(_jspx_th_html_select_2, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t");
                  int evalDoAfterBody = _jspx_th_html_select_2.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_html_select_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_html_select_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_select_property_name.reuse(_jspx_th_html_select_2);
                return;
              }
              _jspx_tagPool_html_select_property_name.reuse(_jspx_th_html_select_2);
              out.write("\r\n\r\n\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\r\n\t\t<label>");
              out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_signature_sound"));
              out.write("</label>\r\n\t\t");
              if (_jspx_meth_html_select_3(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\r\n\r\n\t\t<hr class=\"spacer\" />\r\n\r\n\t\t");
              if (_jspx_meth_logic_equal_2(_jspx_th_html_form_0, _jspx_page_context))
                return;
              out.write("\r\n\t\t\r\n\t\t");
              //  logic:notEqual
              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
              _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_logic_notEqual_1.setName("idNabcast");
              _jspx_th_logic_notEqual_1.setValue("0");
              int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
              if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_13 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_13.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
                  _jspx_th_bean_define_13.setId("idNabcast");
                  _jspx_th_bean_define_13.setName("myNabaztalandCreateForm");
                  _jspx_th_bean_define_13.setProperty("idNabcast");
                  int _jspx_eval_bean_define_13 = _jspx_th_bean_define_13.doStartTag();
                  if (_jspx_th_bean_define_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
                  idNabcast = (java.lang.Object) _jspx_page_context.findAttribute("idNabcast");
                  out.write("\r\n\t\t\t");
                  //  html:hidden
                  org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_2 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
                  _jspx_th_html_hidden_2.setPageContext(_jspx_page_context);
                  _jspx_th_html_hidden_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
                  _jspx_th_html_hidden_2.setName("myNabaztalandCreateForm");
                  _jspx_th_html_hidden_2.setStyleId("idNabcast");
                  _jspx_th_html_hidden_2.setProperty("idNabcast");
                  _jspx_th_html_hidden_2.setValue(idNabcast.toString());
                  int _jspx_eval_html_hidden_2 = _jspx_th_html_hidden_2.doStartTag();
                  if (_jspx_th_html_hidden_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_2);
                    return;
                  }
                  _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_2);
                  out.write("\r\n\t\t\t");
                  if (_jspx_meth_html_hidden_3(_jspx_th_logic_notEqual_1, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t");
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
              out.write("\r\n\t\r\n\t\t<input type=\"hidden\" id=\"colorPicker_value\" />\r\n\t\r\n\t</div>\r\n\t\r\n\t<hr class=\"spacer\"/>\r\n\r\n\t<div style=\"text-align:center; \">\r\n\t\t");
              //  logic:notEqual
              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
              _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_logic_notEqual_2.setName("idNabcast");
              _jspx_th_logic_notEqual_2.setValue("0");
              int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
              if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\t\r\n\t\t\t<input type=\"button\" value=\"");
                  out.print(DicoTools.dico(dico_lang, "srv_create/nabcast_delete"));
                  out.write("\" class=\"genericDeleteBt\" onclick=\"DeleteMyNabcast();\" />\r\n\t\t");
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
              out.write("\r\n\t\t<input type=\"button\" value=\"");
              out.print(DicoTools.dico(dico_lang, "myTerrier/nabshare_validate"));
              out.write("\" class=\"genericBt\" onclick=\"validateNabcast();\" />\r\n\t</div>\r\n\r\n</div>\t\r\n");
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
          out.write("\r\n\r\n<div id=\"mynabcastUpload\"></div>\r\n\r\n\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_14 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_14.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
          _jspx_th_bean_define_14.setProperty("colorPicker_value");
          _jspx_th_bean_define_14.setName("myNabaztalandCreateForm");
          _jspx_th_bean_define_14.setId("colorPicker");
          int _jspx_eval_bean_define_14 = _jspx_th_bean_define_14.doStartTag();
          if (_jspx_th_bean_define_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
          java.lang.Object colorPicker = null;
          colorPicker = (java.lang.Object) _jspx_page_context.findAttribute("colorPicker");
          out.write("\r\n\r\n\t<script type=\"text/javascript\">\r\n\t\tcolorPicker_Init('colorPicker', '");
          out.print(colorPicker);
          out.write("');\r\n\t\tnabCastLang_init();\r\n\t\t\r\n\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_3 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_3.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
          _jspx_th_logic_notEqual_3.setName("idNabcast");
          _jspx_th_logic_notEqual_3.setValue("0");
          int _jspx_eval_logic_notEqual_3 = _jspx_th_logic_notEqual_3.doStartTag();
          if (_jspx_eval_logic_notEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t/* si on a selectionn� un nabcast pour l'edition*/\r\n\t\t\tblocMyNabcast_Select(");
              out.print(idNabcast);
              out.write(");\r\n\t\t\t\r\n\t\t\tdivChangeUrl(\"mynabcastUpload\", \"");
              if (_jspx_meth_html_rewrite_0(_jspx_th_logic_notEqual_3, _jspx_page_context))
                return;
              out.write("?idNabcast=");
              out.print(idNabcast);
              out.write("\");\r\n\t\t\t\r\n\r\n\t\t\t\r\n\t\t");
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
          out.write("\t\r\n\t</script>\r\n\r\n");
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
      out.write("\r\n\r\n\r\n");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_3.setParent(null);
      _jspx_th_logic_equal_3.setName("mode");
      _jspx_th_logic_equal_3.setValue("1");
      int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
      if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
/* Nabcast cr�� la r�ponse est g�r� dans validateNabcast.js */
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_15 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_15.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_3);
          _jspx_th_bean_define_15.setId("idNabcast");
          _jspx_th_bean_define_15.setName("myNabaztalandCreateForm");
          _jspx_th_bean_define_15.setProperty("idNabcast");
          int _jspx_eval_bean_define_15 = _jspx_th_bean_define_15.doStartTag();
          if (_jspx_th_bean_define_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_15);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_15);
          java.lang.Object idNabcast = null;
          idNabcast = (java.lang.Object) _jspx_page_context.findAttribute("idNabcast");
          out.write('\r');
          out.write('\n');
          out.write('	');
          out.print(idNabcast);
          out.write('\r');
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
      out.write("\r\n\r\n");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_4.setParent(null);
      _jspx_th_logic_equal_4.setName("mode");
      _jspx_th_logic_equal_4.setValue("3");
      int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
      if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
/* Nabcast modifi�  */
          out.write('\r');
          out.write('\n');
          out.write('	');
/* la r�ponse est g�r� dans validateNabcast.js  */
          out.write('\r');
          out.write('\n');
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

  private boolean _jspx_meth_html_text_1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_1 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_style_property_name_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_1.setPageContext(_jspx_page_context);
    _jspx_th_html_text_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_text_1.setName("myNabaztalandCreateForm");
    _jspx_th_html_text_1.setProperty("nabcast_shortcut");
    _jspx_th_html_text_1.setStyle("width:120px;");
    int _jspx_eval_html_text_1 = _jspx_th_html_text_1.doStartTag();
    if (_jspx_th_html_text_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_style_property_name_nobody.reuse(_jspx_th_html_text_1);
      return true;
    }
    _jspx_tagPool_html_text_style_property_name_nobody.reuse(_jspx_th_html_text_1);
    return false;
  }

  private boolean _jspx_meth_html_optionsCollection_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_select_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:optionsCollection
    org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_optionsCollection_0 = (org.apache.struts.taglib.html.OptionsCollectionTag) _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
    _jspx_th_html_optionsCollection_0.setPageContext(_jspx_page_context);
    _jspx_th_html_optionsCollection_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_2);
    _jspx_th_html_optionsCollection_0.setName("myNabaztalandCreateForm");
    _jspx_th_html_optionsCollection_0.setProperty("nabcast_animList");
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

  private boolean _jspx_meth_html_select_3(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:select
    org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_3 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_property_name.get(org.apache.struts.taglib.html.SelectTag.class);
    _jspx_th_html_select_3.setPageContext(_jspx_page_context);
    _jspx_th_html_select_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_select_3.setName("myNabaztalandCreateForm");
    _jspx_th_html_select_3.setProperty("nabcast_sound");
    int _jspx_eval_html_select_3 = _jspx_th_html_select_3.doStartTag();
    if (_jspx_eval_html_select_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_html_select_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_html_select_3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_html_select_3.doInitBody();
      }
      do {
        out.write("\r\n\t\t\t");
        if (_jspx_meth_html_optionsCollection_1(_jspx_th_html_select_3, _jspx_page_context))
          return true;
        out.write("\r\n\t\t");
        int evalDoAfterBody = _jspx_th_html_select_3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_html_select_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_html_select_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_select_property_name.reuse(_jspx_th_html_select_3);
      return true;
    }
    _jspx_tagPool_html_select_property_name.reuse(_jspx_th_html_select_3);
    return false;
  }

  private boolean _jspx_meth_html_optionsCollection_1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_select_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:optionsCollection
    org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_optionsCollection_1 = (org.apache.struts.taglib.html.OptionsCollectionTag) _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
    _jspx_th_html_optionsCollection_1.setPageContext(_jspx_page_context);
    _jspx_th_html_optionsCollection_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_3);
    _jspx_th_html_optionsCollection_1.setName("myNabaztalandCreateForm");
    _jspx_th_html_optionsCollection_1.setProperty("nabcast_soundList");
    _jspx_th_html_optionsCollection_1.setLabel("label");
    _jspx_th_html_optionsCollection_1.setValue("id");
    int _jspx_eval_html_optionsCollection_1 = _jspx_th_html_optionsCollection_1.doStartTag();
    if (_jspx_th_html_optionsCollection_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.reuse(_jspx_th_html_optionsCollection_1);
      return true;
    }
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.reuse(_jspx_th_html_optionsCollection_1);
    return false;
  }

  private boolean _jspx_meth_logic_equal_2(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_equal_2.setName("idNabcast");
    _jspx_th_logic_equal_2.setValue("0");
    int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
    if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t");
        if (_jspx_meth_html_hidden_0(_jspx_th_logic_equal_2, _jspx_page_context))
          return true;
        out.write("\r\n\t\t\t");
        if (_jspx_meth_html_hidden_1(_jspx_th_logic_equal_2, _jspx_page_context))
          return true;
        out.write("\r\n\t\t");
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

  private boolean _jspx_meth_html_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
    _jspx_th_html_hidden_0.setName("myNabaztalandCreateForm");
    _jspx_th_html_hidden_0.setProperty("mode");
    _jspx_th_html_hidden_0.setValue("1");
    _jspx_th_html_hidden_0.setStyleId("mode");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_0);
    return false;
  }

  private boolean _jspx_meth_html_hidden_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
    _jspx_th_html_hidden_1.setName("myNabaztalandCreateForm");
    _jspx_th_html_hidden_1.setStyleId("idNabcast");
    _jspx_th_html_hidden_1.setProperty("idNabcast");
    _jspx_th_html_hidden_1.setValue("0");
    int _jspx_eval_html_hidden_1 = _jspx_th_html_hidden_1.doStartTag();
    if (_jspx_th_html_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_1);
      return true;
    }
    _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_1);
    return false;
  }

  private boolean _jspx_meth_html_hidden_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_3 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_3.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
    _jspx_th_html_hidden_3.setName("myNabaztalandCreateForm");
    _jspx_th_html_hidden_3.setProperty("mode");
    _jspx_th_html_hidden_3.setValue("3");
    _jspx_th_html_hidden_3.setStyleId("mode");
    int _jspx_eval_html_hidden_3 = _jspx_th_html_hidden_3.doStartTag();
    if (_jspx_th_html_hidden_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_3);
      return true;
    }
    _jspx_tagPool_html_hidden_value_styleId_property_name_nobody.reuse(_jspx_th_html_hidden_3);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_0 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_0.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_3);
    _jspx_th_html_rewrite_0.setForward("goNabcastUpload");
    int _jspx_eval_html_rewrite_0 = _jspx_th_html_rewrite_0.doStartTag();
    if (_jspx_th_html_rewrite_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
    return false;
  }
}
