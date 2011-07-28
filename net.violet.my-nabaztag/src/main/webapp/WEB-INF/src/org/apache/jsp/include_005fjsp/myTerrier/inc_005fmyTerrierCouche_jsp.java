package org.apache.jsp.include_005fjsp.myTerrier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyTerrierCouche_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_button_value_styleClass_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_submit_value_styleClass_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_button_value_styleClass_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_submit_value_styleClass_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_greaterThan_value_name.release();
    _jspx_tagPool_html_form_action.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_html_hidden_value_property_name_nobody.release();
    _jspx_tagPool_html_button_value_styleClass_property_nobody.release();
    _jspx_tagPool_html_submit_value_styleClass_nobody.release();
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
      out.write("\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n");
	Lang dico_lang =	SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myTerrierCoucheForm");
      _jspx_th_bean_define_0.setProperty("user_main");
      _jspx_th_bean_define_0.setId("user_main");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.Object user_main = null;
      user_main = (java.lang.Object) _jspx_page_context.findAttribute("user_main");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("myTerrierCoucheForm");
      _jspx_th_bean_define_1.setProperty("startW");
      _jspx_th_bean_define_1.setId("startW");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object startW = null;
      startW = (java.lang.Object) _jspx_page_context.findAttribute("startW");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setName("myTerrierCoucheForm");
      _jspx_th_bean_define_2.setProperty("endW");
      _jspx_th_bean_define_2.setId("endW");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      java.lang.Object endW = null;
      endW = (java.lang.Object) _jspx_page_context.findAttribute("endW");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setName("myTerrierCoucheForm");
      _jspx_th_bean_define_3.setProperty("startWe");
      _jspx_th_bean_define_3.setId("startWe");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      java.lang.Object startWe = null;
      startWe = (java.lang.Object) _jspx_page_context.findAttribute("startWe");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_4.setParent(null);
      _jspx_th_bean_define_4.setName("myTerrierCoucheForm");
      _jspx_th_bean_define_4.setProperty("endWe");
      _jspx_th_bean_define_4.setId("endWe");
      int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
      if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
      java.lang.Object endWe = null;
      endWe = (java.lang.Object) _jspx_page_context.findAttribute("endWe");
      out.write('\r');
      out.write('\n');
      //  logic:greaterThan
      org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
      _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_greaterThan_0.setParent(null);
      _jspx_th_logic_greaterThan_0.setName("user_main");
      _jspx_th_logic_greaterThan_0.setValue("0");
      int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
      if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\r\n");
          //  html:form
          org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_action.get(org.apache.struts.taglib.html.FormTag.class);
          _jspx_th_html_form_0.setPageContext(_jspx_page_context);
          _jspx_th_html_form_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_html_form_0.setAction("/action/myTerrierCouche");
          int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
          if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\r\n<div class=\"flat-block\"  > \r\n   \r\n\t<div class=\"flat-block-top\" >\r\n\t\t<h3 class=\"no-icone\">\r\n\t\t\t");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_title"));
              out.write("\r\n\t\t</h3>\r\n\t</div>\r\n\r\n\t<div class=\"flat-block-content\" >\r\n\t\t<div class=\"flat-block-content-inner\" >\r\n\t\t\t");
              out.write("\r\n\r\n\t\t\t\t<div  class=\"twoCol-left\" >\t\t\t\t\t\r\n\t\t\t\t\t<fieldset>\r\n\t\t\t\t\t\t<legend>\r\n\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_week"));
              out.write(" &bull; ");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_go_to_sleep"));
              out.write("\r\n\t\t\t\t\t\t</legend>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"form-line\">\r\n\t\t\t\t\t\t\t<label style=\"width: 140px;\">\r\n\t\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_week_from"));
              out.write("\r\n\t\t\t\t\t\t\t</label>\r\n\t\t\t\t\t\t\t<input id=\"endW\" name=\"endW\" type=\"hidden\" class=\"hourPicker\" value=\"");
              out.print(endW);
              out.write("\" />\t\t\t\t\t\t\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"form-line\">\r\n\t\t\t\t\t\t\t<label style=\"width: 140px;\">\r\n\t\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_week_to"));
              out.write("\r\n\t\t\t\t\t\t\t</label>\r\n\t\t\t\t\t\t\t<input id=\"startW\" name=\"startW\" type=\"hidden\" class=\"hourPicker\" value=\"");
              out.print(startW);
              out.write("\" />\t\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t</fieldset>\r\n\t\t\t\t</div>\r\n\t\t\t\t<div  class=\"twoCol-right\" >\r\n\t\t\t\t\t<fieldset>\r\n\t\t\t\t\t\t<legend>\r\n\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_weekend"));
              out.write(" &bull; ");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_go_to_sleep"));
              out.write("\r\n\t\t\t\t\t\t</legend>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"form-line\">\r\n\t\t\t\t\t\t\t<label style=\"width: 140px;\">\r\n\t\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_week_from"));
              out.write("\r\n\t\t\t\t\t\t\t</label>\r\n\t\t\t\t\t\t\t<input id=\"endWe\" name=\"endWe\" type=\"hidden\" class=\"hourPicker\" value=\"");
              out.print(endWe);
              out.write("\" />\t\t\t\t\t\t\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t<div class=\"form-line\">\r\n\t\t\t\t\t\t\t<label style=\"width: 140px;\">\r\n\t\t\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_week_to"));
              out.write("\r\n\t\t\t\t\t\t\t</label>\r\n\t\t\t\t\t\t\t<input id=\"startWe\" name=\"startWe\" type=\"hidden\" class=\"hourPicker\" value=\"");
              out.print(startWe);
              out.write("\" />\t\r\n\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t</fieldset>\r\n\t\t\t\t</div>\r\n\t\t\t\t<hr class=\"clearer\" />\r\n\t\t\r\n\t\t\t\t<div class=\"buttons\">\r\n\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_logic_equal_0.setName("myTerrierCoucheForm");
              _jspx_th_logic_equal_0.setProperty("isReg");
              _jspx_th_logic_equal_0.setValue("1");
              int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
              if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t");
                  if (_jspx_meth_html_hidden_0(_jspx_th_logic_equal_0, _jspx_page_context))
                    return;
                  out.write("\t\r\n\t\t\t\t\t\t\t");
                  //  html:button
                  org.apache.struts.taglib.html.ButtonTag _jspx_th_html_button_0 = (org.apache.struts.taglib.html.ButtonTag) _jspx_tagPool_html_button_value_styleClass_property_nobody.get(org.apache.struts.taglib.html.ButtonTag.class);
                  _jspx_th_html_button_0.setPageContext(_jspx_page_context);
                  _jspx_th_html_button_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
                  _jspx_th_html_button_0.setValue(DicoTools.dico(dico_lang, "myTerrier/couche_button_deactivate"));
                  _jspx_th_html_button_0.setProperty("del");
                  _jspx_th_html_button_0.setStyleClass("genericDeleteBt");
                  int _jspx_eval_html_button_0 = _jspx_th_html_button_0.doStartTag();
                  if (_jspx_th_html_button_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_html_button_value_styleClass_property_nobody.reuse(_jspx_th_html_button_0);
                    return;
                  }
                  _jspx_tagPool_html_button_value_styleClass_property_nobody.reuse(_jspx_th_html_button_0);
                  out.write("\r\n\t\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t\t\r\n\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_logic_equal_1.setName("myTerrierCoucheForm");
              _jspx_th_logic_equal_1.setProperty("isReg");
              _jspx_th_logic_equal_1.setValue("0");
              int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
              if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t");
                  if (_jspx_meth_html_hidden_1(_jspx_th_logic_equal_1, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t\t\t");
                  //  html:submit
                  org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_0 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_styleClass_nobody.get(org.apache.struts.taglib.html.SubmitTag.class);
                  _jspx_th_html_submit_0.setPageContext(_jspx_page_context);
                  _jspx_th_html_submit_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_1);
                  _jspx_th_html_submit_0.setValue(DicoTools.dico(dico_lang, "myTerrier/couche_button_activate"));
                  _jspx_th_html_submit_0.setStyleClass("genericBt");
                  int _jspx_eval_html_submit_0 = _jspx_th_html_submit_0.doStartTag();
                  if (_jspx_th_html_submit_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_html_submit_value_styleClass_nobody.reuse(_jspx_th_html_submit_0);
                    return;
                  }
                  _jspx_tagPool_html_submit_value_styleClass_nobody.reuse(_jspx_th_html_submit_0);
                  out.write("\r\n\t\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_logic_equal_2.setName("myTerrierCoucheForm");
              _jspx_th_logic_equal_2.setProperty("isReg");
              _jspx_th_logic_equal_2.setValue("1");
              int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
              if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t");
                  if (_jspx_meth_html_hidden_2(_jspx_th_logic_equal_2, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t\t\t");
                  //  html:submit
                  org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_1 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_styleClass_nobody.get(org.apache.struts.taglib.html.SubmitTag.class);
                  _jspx_th_html_submit_1.setPageContext(_jspx_page_context);
                  _jspx_th_html_submit_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
                  _jspx_th_html_submit_1.setValue(DicoTools.dico(dico_lang, "myTerrier/couche_button_modify"));
                  _jspx_th_html_submit_1.setStyleClass("genericBt");
                  int _jspx_eval_html_submit_1 = _jspx_th_html_submit_1.doStartTag();
                  if (_jspx_th_html_submit_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_html_submit_value_styleClass_nobody.reuse(_jspx_th_html_submit_1);
                    return;
                  }
                  _jspx_tagPool_html_submit_value_styleClass_nobody.reuse(_jspx_th_html_submit_1);
                  out.write("\r\n\t\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\t\t\t\t<div class=\"little-hint\">\r\n\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang, "myTerrier/couche_note"));
              out.write("\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\t\t\t\t\r\n\t\t\t\t\t\t\t\r\n\t\t\t");
              out.write("\r\n\t\t</div>\r\n\t</div>\r\n</div>\r\n");
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
          out.write('\r');
          out.write('\n');
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
      out.write("\r\n\r\n\r\n ");
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

  private boolean _jspx_meth_html_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
    _jspx_th_html_hidden_0.setName("myTerrierCoucheForm");
    _jspx_th_html_hidden_0.setProperty("delete");
    _jspx_th_html_hidden_0.setValue("0");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_0);
    return false;
  }

  private boolean _jspx_meth_html_hidden_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_1);
    _jspx_th_html_hidden_1.setName("myTerrierCoucheForm");
    _jspx_th_html_hidden_1.setProperty("add");
    _jspx_th_html_hidden_1.setValue("1");
    int _jspx_eval_html_hidden_1 = _jspx_th_html_hidden_1.doStartTag();
    if (_jspx_th_html_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_1);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_1);
    return false;
  }

  private boolean _jspx_meth_html_hidden_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_2 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_2.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
    _jspx_th_html_hidden_2.setName("myTerrierCoucheForm");
    _jspx_th_html_hidden_2.setProperty("add");
    _jspx_th_html_hidden_2.setValue("1");
    int _jspx_eval_html_hidden_2 = _jspx_th_html_hidden_2.doStartTag();
    if (_jspx_th_html_hidden_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_2);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_2);
    return false;
  }
}
