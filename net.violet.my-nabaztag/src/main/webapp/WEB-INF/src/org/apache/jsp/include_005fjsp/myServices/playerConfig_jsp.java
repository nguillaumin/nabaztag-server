package org.apache.jsp.include_005fjsp.myServices;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class playerConfig_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_select_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_submit_value_styleId_styleClass_style_property_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_select_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_submit_value_styleId_styleClass_style_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_html_form_action.release();
    _jspx_tagPool_html_hidden_value_property_nobody.release();
    _jspx_tagPool_html_select_property_name.release();
    _jspx_tagPool_html_submit_value_styleId_styleClass_style_property_nobody.release();
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
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n");
	Lang dico_lang =SessionTools.getLangFromSession(session, request);
      out.write('\n');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("mySrvPlayerForm");
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
      out.write("\r\n<div id=\"setUpSrv\">\r\n\t\r\n\t");
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_0.setParent(null);
      _jspx_th_logic_notEqual_0.setName("mySrvPlayerForm");
      _jspx_th_logic_notEqual_0.setProperty("isMarkup");
      _jspx_th_logic_notEqual_0.setValue("0");
      int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
      if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t<div class=\"main-cadre-contener serviceContener serviceBookReaderConfig\">\t\r\n\t\t\t\t<div class=\"main-cadre-top\" >\r\n\t\t\t\t\t<h2>\r\n\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "services/configure"));
          out.write("\r\n\t\t\t\t\t</h2>\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\t\t\t\t<div class=\"main-cadre-content\" >\r\n\t\t\t\t\t<!-- ******************************************** CONTENT ***************************************************** --> \r\n\n\t\t\t\t\t\r\n\t\t\t\t\t<div class=\"intro\">\r\n\t\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_"+dicoRoot.toString()+"/config_intro"));
          out.write("\t\t\t\t\t\t\t\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t\r\n\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
          _jspx_th_logic_iterate_0.setName("mySrvPlayerForm");
          _jspx_th_logic_iterate_0.setProperty("mySetting");
          _jspx_th_logic_iterate_0.setId("listSetting");
          int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
          if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object listSetting = null;
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_0.doInitBody();
            }
            listSetting = (java.lang.Object) _jspx_page_context.findAttribute("listSetting");
            do {
              out.write("\r\n\t\t\t\t\t\t<div class=\"ztamps-contener\">\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_1.setId("idApplet");
              _jspx_th_bean_define_1.setName("mySrvPlayerForm");
              _jspx_th_bean_define_1.setProperty("appletId");
              int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
              if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
              java.lang.Object idApplet = null;
              idApplet = (java.lang.Object) _jspx_page_context.findAttribute("idApplet");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_2.setId("isbn");
              _jspx_th_bean_define_2.setName("mySrvPlayerForm");
              _jspx_th_bean_define_2.setProperty("isbn");
              int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
              if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
              java.lang.Object isbn = null;
              isbn = (java.lang.Object) _jspx_page_context.findAttribute("isbn");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_3.setId("picture_value");
              _jspx_th_bean_define_3.setName("listSetting");
              _jspx_th_bean_define_3.setProperty("pictureObject");
              int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
              if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
              java.lang.Object picture_value = null;
              picture_value = (java.lang.Object) _jspx_page_context.findAttribute("picture_value");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_4.setId("ztamp_id");
              _jspx_th_bean_define_4.setName("listSetting");
              _jspx_th_bean_define_4.setProperty("ztampId");
              int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
              if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
              java.lang.Object ztamp_id = null;
              ztamp_id = (java.lang.Object) _jspx_page_context.findAttribute("ztamp_id");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_5.setId("ztamp_serial");
              _jspx_th_bean_define_5.setName("listSetting");
              _jspx_th_bean_define_5.setProperty("ztampSerial");
              int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
              if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
              java.lang.Object ztamp_serial = null;
              ztamp_serial = (java.lang.Object) _jspx_page_context.findAttribute("ztamp_serial");
              out.write("\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t<div class=\"ztamps-pic\">\r\n\t\t\t\t\t\t\t\t<img src=\"../include_img/ztamps/");
              out.print(picture_value);
              out.write(".jpg\" /><br />\r\n\t\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "services/books_legend_ztamp"));
              out.write("\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_0.setName("listSetting");
              _jspx_th_logic_equal_0.setProperty("voiceId");
              _jspx_th_logic_equal_0.setValue("-1");
              int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
              if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
                  _jspx_th_bean_define_6.setId("object_login");
                  _jspx_th_bean_define_6.setName("listSetting");
                  _jspx_th_bean_define_6.setProperty("nameObjectLastRead");
                  int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
                  if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                  java.lang.Object object_login = null;
                  object_login = (java.lang.Object) _jspx_page_context.findAttribute("object_login");
                  out.write("\n\t\t\t\t\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
                  _jspx_th_bean_define_7.setId("user_id");
                  _jspx_th_bean_define_7.setName("listSetting");
                  _jspx_th_bean_define_7.setProperty("idUserLastRead");
                  int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
                  if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
                  java.lang.Object user_id = null;
                  user_id = (java.lang.Object) _jspx_page_context.findAttribute("user_id");
                  out.write("\r\n\t\t\t\t\t\t\t\t<div style=\"margin-left:110px;\">\n\t\t\t\t\t\t\t\t\t\t");
                  out.print(DicoTools.dico_jsp(dico_lang , "services/books_lastReader", object_login, user_id ));
                  out.write("\r\n\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t");
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
              out.write("\r\n\t\t\r\n\t\t\t\t\t\t\t");
              //  logic:notEqual
              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
              _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_notEqual_1.setName("listSetting");
              _jspx_th_logic_notEqual_1.setProperty("voiceId");
              _jspx_th_logic_notEqual_1.setValue("-1");
              int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
              if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t");
                  //  html:form
                  org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_action.get(org.apache.struts.taglib.html.FormTag.class);
                  _jspx_th_html_form_0.setPageContext(_jspx_page_context);
                  _jspx_th_html_form_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
                  _jspx_th_html_form_0.setAction("/action/srvPlayerConfig");
                  int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
                  if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"bookId\" value=\"");
                      out.print(ztamp_id);
                      out.write("\" />\r\n\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"isbn\" value=\"");
                      out.print(isbn);
                      out.write("\" />\r\n\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"bookSerial\" value=\"");
                      out.print(ztamp_serial);
                      out.write("\" />\r\n\t\t\t\t\t\t\t\t\t<input type=\"hidden\" name=\"appletId\" value=\"");
                      out.print(idApplet);
                      out.write("\" />\n\t\t\t\t\t\t\t\t\t\t\t");
                      if (_jspx_meth_html_hidden_0(_jspx_th_html_form_0, _jspx_page_context))
                        return;
                      out.write("\r\n\t\t\t\t\t\t\t\t\t<div style=\"margin-left:110px;\">\r\n\t\t\t\t\t\t\t\t\t");
                      //  logic:notEqual
                      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                      _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
                      _jspx_th_logic_notEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
                      _jspx_th_logic_notEqual_2.setName("mySrvPlayerForm");
                      _jspx_th_logic_notEqual_2.setProperty("sizeVoice");
                      _jspx_th_logic_notEqual_2.setValue("1");
                      int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
                      if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        do {
                          out.write("\r\n\t\t\t\t\t\t\t\t\t\t<p>\r\n\t\t\t\t\t\t\t\t\t\t\t<span>");
                          out.print(DicoTools.dico(dico_lang , "services/books_choose_voice"));
                          out.write("</span>\r\n\t\t\t\t\t\t\t\t\t\t\t");
                          out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
                          //  html:select
                          org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_0 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_property_name.get(org.apache.struts.taglib.html.SelectTag.class);
                          _jspx_th_html_select_0.setPageContext(_jspx_page_context);
                          _jspx_th_html_select_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_2);
                          _jspx_th_html_select_0.setName("mySrvPlayerForm");
                          _jspx_th_html_select_0.setProperty("voiceId");
                          int _jspx_eval_html_select_0 = _jspx_th_html_select_0.doStartTag();
                          if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                            if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                              out = _jspx_page_context.pushBody();
                              _jspx_th_html_select_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                              _jspx_th_html_select_0.doInitBody();
                            }
                            do {
                              out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"0\" >");
                              out.print(DicoTools.dico(dico_lang , "services/books_voice1"));
                              out.write("</option>\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
                              //  logic:notEqual
                              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_3 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                              _jspx_th_logic_notEqual_3.setPageContext(_jspx_page_context);
                              _jspx_th_logic_notEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_0);
                              _jspx_th_logic_notEqual_3.setName("listSetting");
                              _jspx_th_logic_notEqual_3.setProperty("voiceId");
                              _jspx_th_logic_notEqual_3.setValue("0");
                              int _jspx_eval_logic_notEqual_3 = _jspx_th_logic_notEqual_3.doStartTag();
                              if (_jspx_eval_logic_notEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                              do {
                              out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"1\" selected=\"selected\">");
                              out.print(DicoTools.dico(dico_lang , "services/books_voice2"));
                              out.write("</option>\n\t\t\t\t\t\t\t\t\t\t\t\t");
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
                              out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
                              //  logic:equal
                              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                              _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
                              _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_0);
                              _jspx_th_logic_equal_1.setName("listSetting");
                              _jspx_th_logic_equal_1.setProperty("voiceId");
                              _jspx_th_logic_equal_1.setValue("0");
                              int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
                              if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                              do {
                              out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t<option value=\"1\">");
                              out.print(DicoTools.dico(dico_lang , "services/books_voice2"));
                              out.write("</option>\r\n\t\t\t\t\t\t\t\t\t\t\t\t");
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
                              out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t");
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
                          out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t<br />\r\n\t\t\t\t\t\t\t\t\t\t\t");
                          //  html:submit
                          org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_0 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_styleId_styleClass_style_property_nobody.get(org.apache.struts.taglib.html.SubmitTag.class);
                          _jspx_th_html_submit_0.setPageContext(_jspx_page_context);
                          _jspx_th_html_submit_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_2);
                          _jspx_th_html_submit_0.setProperty("button");
                          _jspx_th_html_submit_0.setStyle("margin-top:5px;");
                          _jspx_th_html_submit_0.setStyleId("update");
                          _jspx_th_html_submit_0.setValue(DicoTools.dico(dico_lang , "services/books_updateConfig"));
                          _jspx_th_html_submit_0.setStyleClass("genericBt");
                          int _jspx_eval_html_submit_0 = _jspx_th_html_submit_0.doStartTag();
                          if (_jspx_th_html_submit_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                            _jspx_tagPool_html_submit_value_styleId_styleClass_style_property_nobody.reuse(_jspx_th_html_submit_0);
                            return;
                          }
                          _jspx_tagPool_html_submit_value_styleId_styleClass_style_property_nobody.reuse(_jspx_th_html_submit_0);
                          out.write("<br />\n\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t");
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
                      out.write("\r\n\t\t\t\t\t\t\t\t\t\t");
                      //  logic:notEqual
                      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_4 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                      _jspx_th_logic_notEqual_4.setPageContext(_jspx_page_context);
                      _jspx_th_logic_notEqual_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
                      _jspx_th_logic_notEqual_4.setName("listSetting");
                      _jspx_th_logic_notEqual_4.setProperty("markUpIndex");
                      _jspx_th_logic_notEqual_4.setValue("0");
                      int _jspx_eval_logic_notEqual_4 = _jspx_th_logic_notEqual_4.doStartTag();
                      if (_jspx_eval_logic_notEqual_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        do {
                          out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t<p style=\"margin-top:11px;\">\r\n\t\t\t\t\t\t\t\t\t\t\t\t<span>");
                          out.print(DicoTools.dico(dico_lang , "services/books_reset_story"));
                          out.write("</span>\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
                          out.print(DicoTools.dico(dico_lang , "services/books_reset_story_txt"));
                          out.write("<br />\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
                          //  html:submit
                          org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_1 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_styleId_styleClass_style_property_nobody.get(org.apache.struts.taglib.html.SubmitTag.class);
                          _jspx_th_html_submit_1.setPageContext(_jspx_page_context);
                          _jspx_th_html_submit_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_4);
                          _jspx_th_html_submit_1.setProperty("button");
                          _jspx_th_html_submit_1.setStyle("margin-top:5px;");
                          _jspx_th_html_submit_1.setStyleId("resetApplet");
                          _jspx_th_html_submit_1.setValue(DicoTools.dico(dico_lang , "services/books_reset"));
                          _jspx_th_html_submit_1.setStyleClass("genericBt");
                          int _jspx_eval_html_submit_1 = _jspx_th_html_submit_1.doStartTag();
                          if (_jspx_th_html_submit_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                            _jspx_tagPool_html_submit_value_styleId_styleClass_style_property_nobody.reuse(_jspx_th_html_submit_1);
                            return;
                          }
                          _jspx_tagPool_html_submit_value_styleId_styleClass_style_property_nobody.reuse(_jspx_th_html_submit_1);
                          out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t\t\t\t\t");
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
                      out.write("\r\n\t\t\t\t\t\t\t\t\r\n\t\t\n\t\t\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\t");
                      int evalDoAfterBody = _jspx_th_html_form_0.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_html_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_html_form_action.reuse(_jspx_th_html_form_0);
                    return;
                  }
                  _jspx_tagPool_html_form_action.reuse(_jspx_th_html_form_0);
                  out.write("\r\n\t\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
              listSetting = (java.lang.Object) _jspx_page_context.findAttribute("listSetting");
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
          out.write("\r\n\t\t\r\n\t\t\t\t\t<div>\r\n\t\t\t\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\t\t<h3>");
          out.print(DicoTools.dico(dico_lang , "services/books_howTo"));
          out.write("</h3>\r\n\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "services/books_howToText"));
          out.write("\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<hr class=\"clearer\" />\r\n\t\t\t\t\t\r\n\t\t\t\t\t");
          out.write("\r\n\t\t\t\t</div><!-- End of main content -->\r\n\t\t\t</div>\r\n\r\n\t");
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
      out.write("\r\n\t\r\n\t\r\n\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_2.setParent(null);
      _jspx_th_logic_equal_2.setName("mySrvPlayerForm");
      _jspx_th_logic_equal_2.setProperty("isMarkup");
      _jspx_th_logic_equal_2.setValue("0");
      int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
      if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t<div class=\"main-cadre-contener serviceContener\">\t\r\n\t\t\t\t<div class=\"main-cadre-top\" >\r\n\t\t\t\t\t<h2>\r\n\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "services/configure"));
          out.write("\r\n\t\t\t\t\t</h2>\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\t\t\t\t<div class=\"main-cadre-content\" >\r\n\t\t\t\t\t<!-- ******************************************** CONTENT ***************************************************** -->\r\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_"+dicoRoot.toString()+"/config_nobooks"));
          out.write("\r\n\t\t\t\t\t<!-- ******************************************** /CONTENT ***************************************************** -->\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t");
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
      out.write(" \r\n</div>\t\r\n\t\r\n<script type=\"text/javascript\">\r\n\r\n\tfunction initBookReader(){\r\n\t\r\n\t\t// sur un click d'un bouton, on met le dispatch a la bonne valeur\r\n\t\t$(\"input[name=button]\").bind(\"click\", function(){\r\n\t\r\n\t\t\t$(this)\r\n\t\t\t\t.parents(\"form\")\r\n\t\t\t\t.find(\"input[name=dispatch]\")\r\n\t\t\t\t.val( $(this).attr('id') );\r\n\t\t});\r\n\t\t\r\n\t\t\r\n\t\t$(\"div.serviceContener form\").ajaxForm({\r\n\t\t\tbeforeSubmit: function(formData,f,o){\r\n\t\t\t\t$(\"#setUpSrv\").block();\r\n\t\t\t},\r\n\t\t\tsuccess: function (data) {\r\n\t\t\t\tvar r = $(data)[0];\r\n\t\t\t\tvar h = $(r).html();\r\n\t\t\t\t$(\"#setUpSrv\")\r\n\t\t\t\t\t.html(h)\r\n\t\t\t\t\t.unblock();\r\n\t\t\t\t\r\n\t\t\t\tinitBookReader();\r\n\t\t\t\tmsgHandling.simpleMsgShow( msg_txt['modif_srv_ok'] );\r\n\t\t\t}\t\r\n\t\t});\r\n\t}\r\n\t\r\n\tinitBookReader();\r\n\r\n\r\n</script>");
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
    _jspx_th_html_hidden_0.setValue("update");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
    return false;
  }
}
