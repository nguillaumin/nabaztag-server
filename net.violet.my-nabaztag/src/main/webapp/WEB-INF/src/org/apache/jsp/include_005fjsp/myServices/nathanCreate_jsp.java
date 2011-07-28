package org.apache.jsp.include_005fjsp.myServices;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class nathanCreate_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_method_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_submit_value_onclick_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_method_enctype_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_file_property_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_method_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_submit_value_onclick_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_method_enctype_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_file_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_html_form_styleId_method_action.release();
    _jspx_tagPool_html_hidden_value_property_nobody.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_html_form_styleId_action.release();
    _jspx_tagPool_html_submit_value_onclick_nobody.release();
    _jspx_tagPool_html_form_styleId_method_enctype_action.release();
    _jspx_tagPool_html_file_property_nobody.release();
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
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write("\n<script type=\"text/javascript\">\n<!--\n\t");
      if (_jspx_meth_logic_equal_0(_jspx_page_context))
        return;
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_1(_jspx_page_context))
        return;
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_2(_jspx_page_context))
        return;
      out.write("\n//-->\n</script>\n\n\n<div id=\"setUpSrv\">\n\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setId("url");
      _jspx_th_bean_define_0.setName("mySrvNathanCreateForm");
      _jspx_th_bean_define_0.setProperty("url");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.Object url = null;
      url = (java.lang.Object) _jspx_page_context.findAttribute("url");
      out.write('\n');
      out.write('	');
String dicoRoot = ""; 
      out.write('\n');
      out.write('	');
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("mySrvNathanCreateForm");
      _jspx_th_logic_notEmpty_0.setProperty("mp3IdList");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          out.write('	');
 dicoRoot = "_next"; 
          out.write('\n');
          out.write('	');
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
      out.write("\n\n\t<div class=\"main-cadre-contener serviceContener serviceBookReaderConfig\">\t\n\t\t<div class=\"main-cadre-top\" >\n\t\t\n\t\t\t<h2>");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/add_title"));
      out.write("</h2>\n\t\t</div>\n\t\t\n\t\t<div class=\"main-cadre-content\" >\n\t\t\t<div  class=\"nathan-return\"><a href=\"myNablife.do?service=");
      out.print(url );
      out.write('"');
      out.write('>');
      out.print(DicoTools.dico(dico_lang , "srv_nathan/add_back"));
      out.write("</a></div>\n\t\t\t<hr class=\"clearer\" />\n\t\t\t<div class=\"nathan\">\t\t\n\t\t\n\t\t\t");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/add_content"+dicoRoot));
      out.write("\n\t\t\t<h3>");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/create_mic"+dicoRoot));
      out.write("</h3>\n\t\t\t");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/create_mic_long"+dicoRoot));
      out.write("\n\t\t\n\t\t\t<div class=\"ztamps-contener\">\n\t\t\t\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setId("idApplet");
      _jspx_th_bean_define_1.setName("mySrvNathanCreateForm");
      _jspx_th_bean_define_1.setProperty("appletId");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object idApplet = null;
      idApplet = (java.lang.Object) _jspx_page_context.findAttribute("idApplet");
      out.write("\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setId("isbn");
      _jspx_th_bean_define_2.setName("mySrvNathanCreateForm");
      _jspx_th_bean_define_2.setProperty("isbn");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      java.lang.Object isbn = null;
      isbn = (java.lang.Object) _jspx_page_context.findAttribute("isbn");
      out.write("\n\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setId("mp3Id");
      _jspx_th_bean_define_3.setName("mySrvNathanCreateForm");
      _jspx_th_bean_define_3.setProperty("mp3Id");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      java.lang.Object mp3Id = null;
      mp3Id = (java.lang.Object) _jspx_page_context.findAttribute("mp3Id");
      out.write("\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_4.setParent(null);
      _jspx_th_bean_define_4.setId("serverPath");
      _jspx_th_bean_define_4.setName("mySrvNathanCreateForm");
      _jspx_th_bean_define_4.setProperty("serverPath");
      int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
      if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
      java.lang.Object serverPath = null;
      serverPath = (java.lang.Object) _jspx_page_context.findAttribute("serverPath");
      out.write("\n\n\n\t\t\t\t");
      out.write("\n\t\t\t\t");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_1 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_1.setParent(null);
      _jspx_th_logic_notEmpty_1.setName("mySrvNathanCreateForm");
      _jspx_th_logic_notEmpty_1.setProperty("mp3IdList");
      int _jspx_eval_logic_notEmpty_1 = _jspx_th_logic_notEmpty_1.doStartTag();
      if (_jspx_eval_logic_notEmpty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t\t\t\n\t\t\t\t");
          out.write("\n\t\t\t\t");
          //  html:form
          org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_method_action.get(org.apache.struts.taglib.html.FormTag.class);
          _jspx_th_html_form_0.setPageContext(_jspx_page_context);
          _jspx_th_html_form_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
          _jspx_th_html_form_0.setAction("/action/srvNathanCreate.do");
          _jspx_th_html_form_0.setMethod("post");
          _jspx_th_html_form_0.setStyleId("removeForm");
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
              _jspx_th_html_hidden_2.setProperty("appletId");
              _jspx_th_html_hidden_2.setValue(idApplet.toString());
              int _jspx_eval_html_hidden_2 = _jspx_th_html_hidden_2.doStartTag();
              if (_jspx_th_html_hidden_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_2);
                return;
              }
              _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_2);
              out.write("\n\t\t\t\t\t\n\t\t\t\t\t");
              out.write("\n\t\t\t\t\t<ul>\n\t\t\t\t\t\t");
int count = 1; 
              out.write("\n\t\t\t\t\t\t");
              //  logic:iterate
              org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
              _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_logic_iterate_0.setName("mySrvNathanCreateForm");
              _jspx_th_logic_iterate_0.setProperty("mp3IdList");
              _jspx_th_logic_iterate_0.setId("fragment");
              int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
              if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object fragment = null;
                if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_logic_iterate_0.doInitBody();
                }
                fragment = (java.lang.Object) _jspx_page_context.findAttribute("fragment");
                do {
                  out.write("\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"mp3IdList\" value=\"");
                  out.print(fragment);
                  out.write("\" />\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<li>");
                  out.print(DicoTools.dico(dico_lang , "srv_nathan/create_part"));
                  out.write(' ');
                  out.print(count++);
                  out.write(" \n\t\t\t\t\t\t\t\t<a href=\"#js\" onclick=\"$(this).parents('form').find('input[name=mp3Id]').val( '");
                  out.print(fragment);
                  out.write("' );\n\t\t\t\t\t\t\t\t\t\t\t\t\t\tpage.postAjax('removeForm', 'setUpSrv-container'); return false;\">\n\t\t\t\t\t\t\t\t\t");
                  out.print(DicoTools.dico(dico_lang , "srv_nathan/delete_part"));
                  out.write("\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
                  fragment = (java.lang.Object) _jspx_page_context.findAttribute("fragment");
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
              out.write("\n\t\t\t\t\t</ul>\n\n\t\t\t\t\t<input name=\"mp3Id\" type=\"hidden\" id=\"mp3Id\" value=\"");
              out.print(mp3Id);
              out.write("\">\n\t\t\t\t");
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
          out.write("\n\t\t\t\t\n\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/finish_content"));
          out.write("\n\t\t\t\t\n\t\t\t\t");
          out.write("\n\t\t\t\t");
          //  html:form
          org.apache.struts.taglib.html.FormTag _jspx_th_html_form_1 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_action.get(org.apache.struts.taglib.html.FormTag.class);
          _jspx_th_html_form_1.setPageContext(_jspx_page_context);
          _jspx_th_html_form_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
          _jspx_th_html_form_1.setAction("/action/srvNathanCreate.do");
          _jspx_th_html_form_1.setStyleId("finishForm");
          int _jspx_eval_html_form_1 = _jspx_th_html_form_1.doStartTag();
          if (_jspx_eval_html_form_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t\t\t\n\t\t\t\t\t");
              if (_jspx_meth_html_hidden_3(_jspx_th_html_form_1, _jspx_page_context))
                return;
              out.write("\t\n\t\t\t\t\t");
              //  html:hidden
              org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_4 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
              _jspx_th_html_hidden_4.setPageContext(_jspx_page_context);
              _jspx_th_html_hidden_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
              _jspx_th_html_hidden_4.setProperty("isbn");
              _jspx_th_html_hidden_4.setValue(isbn.toString());
              int _jspx_eval_html_hidden_4 = _jspx_th_html_hidden_4.doStartTag();
              if (_jspx_th_html_hidden_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_4);
                return;
              }
              _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_4);
              out.write("\n\t\t\t\t\t");
              //  html:hidden
              org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_5 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
              _jspx_th_html_hidden_5.setPageContext(_jspx_page_context);
              _jspx_th_html_hidden_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
              _jspx_th_html_hidden_5.setProperty("appletId");
              _jspx_th_html_hidden_5.setValue(idApplet.toString());
              int _jspx_eval_html_hidden_5 = _jspx_th_html_hidden_5.doStartTag();
              if (_jspx_th_html_hidden_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_5);
                return;
              }
              _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_5);
              out.write("\n\t\t\t\t\t\n\t\t\t\t\t<!-- les ids associes a des fichiers deja uploades -->\n\t\t\t\t\t<div class=\"button-bloc\">\n\t\t\t\t\t");
              //  logic:iterate
              org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
              _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_iterate_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
              _jspx_th_logic_iterate_1.setName("mySrvNathanCreateForm");
              _jspx_th_logic_iterate_1.setProperty("mp3IdList");
              _jspx_th_logic_iterate_1.setId("fragment");
              int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
              if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object fragment = null;
                if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_logic_iterate_1.doInitBody();
                }
                fragment = (java.lang.Object) _jspx_page_context.findAttribute("fragment");
                do {
                  out.write("\n\t\t\t\t\t\t<input type=\"hidden\" name=\"mp3IdList\" value=\"");
                  out.print(fragment);
                  out.write("\" />\n\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
                  fragment = (java.lang.Object) _jspx_page_context.findAttribute("fragment");
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
              out.write("\n\t\t\t\t\t\n\t\t\t\t\t");
              //  html:submit
              org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_0 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_onclick_nobody.get(org.apache.struts.taglib.html.SubmitTag.class);
              _jspx_th_html_submit_0.setPageContext(_jspx_page_context);
              _jspx_th_html_submit_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
              _jspx_th_html_submit_0.setValue(DicoTools.dico(dico_lang , "srv_nathan/create_finish"));
              _jspx_th_html_submit_0.setOnclick("page.postAjax('finishForm', 'setUpSrv-container'); return false;");
              int _jspx_eval_html_submit_0 = _jspx_th_html_submit_0.doStartTag();
              if (_jspx_th_html_submit_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_html_submit_value_onclick_nobody.reuse(_jspx_th_html_submit_0);
                return;
              }
              _jspx_tagPool_html_submit_value_onclick_nobody.reuse(_jspx_th_html_submit_0);
              out.write("\n\t\t\t\t\t\n\t\t\t\t\t</div>\n\t\t\t\t");
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
          out.write("\t\n\t\t\t\t\n\t\t\t\t");
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
      out.write("\n\n\n\t\t\t\t");
      out.write("\n\t\t\t\t\n\t\t\t\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_form_2 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_method_enctype_action.get(org.apache.struts.taglib.html.FormTag.class);
      _jspx_th_html_form_2.setPageContext(_jspx_page_context);
      _jspx_th_html_form_2.setParent(null);
      _jspx_th_html_form_2.setAction("/action/srvNathanCreate.do");
      _jspx_th_html_form_2.setMethod("post");
      _jspx_th_html_form_2.setEnctype("multipart/form-data");
      _jspx_th_html_form_2.setStyleId("addForm");
      int _jspx_eval_html_form_2 = _jspx_th_html_form_2.doStartTag();
      if (_jspx_eval_html_form_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t\t\t\n\t\t\t\t\t");
          if (_jspx_meth_html_hidden_6(_jspx_th_html_form_2, _jspx_page_context))
            return;
          out.write("\t\n\t\t\t\t\t");
          //  html:hidden
          org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_7 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
          _jspx_th_html_hidden_7.setPageContext(_jspx_page_context);
          _jspx_th_html_hidden_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_2);
          _jspx_th_html_hidden_7.setProperty("isbn");
          _jspx_th_html_hidden_7.setValue(isbn.toString());
          int _jspx_eval_html_hidden_7 = _jspx_th_html_hidden_7.doStartTag();
          if (_jspx_th_html_hidden_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_7);
            return;
          }
          _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_7);
          out.write("\n\t\t\t\t\t");
          //  html:hidden
          org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_8 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
          _jspx_th_html_hidden_8.setPageContext(_jspx_page_context);
          _jspx_th_html_hidden_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_2);
          _jspx_th_html_hidden_8.setProperty("appletId");
          _jspx_th_html_hidden_8.setValue(idApplet.toString());
          int _jspx_eval_html_hidden_8 = _jspx_th_html_hidden_8.doStartTag();
          if (_jspx_th_html_hidden_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_8);
            return;
          }
          _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_8);
          out.write("\n\t\t\t\t\t\n\t\t\t\t\t");
          out.write("\n\t\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_2 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_2.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_2);
          _jspx_th_logic_iterate_2.setName("mySrvNathanCreateForm");
          _jspx_th_logic_iterate_2.setProperty("mp3IdList");
          _jspx_th_logic_iterate_2.setId("fragment");
          int _jspx_eval_logic_iterate_2 = _jspx_th_logic_iterate_2.doStartTag();
          if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object fragment = null;
            if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_2.doInitBody();
            }
            fragment = (java.lang.Object) _jspx_page_context.findAttribute("fragment");
            do {
              out.write("\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"mp3IdList\" value=\"");
              out.print(fragment);
              out.write("\" />\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_2.doAfterBody();
              fragment = (java.lang.Object) _jspx_page_context.findAttribute("fragment");
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
          out.write("\n\n\t\t\t\t\t<input name=\"mp3Id\" type=\"hidden\" id=\"mp3Id\" value=\"");
          out.print(mp3Id);
          out.write("\">\n  \n  \t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/add_player"+dicoRoot));
          out.write("\n  \t\t\t\t\t<div>\n\t\t\t\t\t<div id=\"flashcontent\" class=\"enreg_box\">\n\t\t\t\t\t</div>\n\t\t\t\t\t\n\t\t\t\t\t<div class=\"enreg_img\">\n\t<img title=\"Enregistrez directement ici\" alt=\"Enregistrez directement ici\" src=\"../include_img/lapinmicro.gif\"/>\n\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t\t<hr class=\"clearer\"/>\n\t\t\t\t\t\n\t\t\t\t\t<h3>");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/create_soft"+dicoRoot));
          out.write("</h3>\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/create_soft_long"+dicoRoot));
          out.write("\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/add_file"+dicoRoot));
          out.write("\n\t\t\t\t\t");
          if (_jspx_meth_html_file_0(_jspx_th_html_form_2, _jspx_page_context))
            return;
          out.write("\n\t\t\t\t\t<br />\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/add_comment"+dicoRoot));
          out.write("\n\t\t\t\t\t<div class=\"button-bloc\">\n\t\t\t\t\t");
          //  html:submit
          org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_1 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_onclick_nobody.get(org.apache.struts.taglib.html.SubmitTag.class);
          _jspx_th_html_submit_1.setPageContext(_jspx_page_context);
          _jspx_th_html_submit_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_2);
          _jspx_th_html_submit_1.setValue(DicoTools.dico(dico_lang , "srv_nathan/add_part"));
          _jspx_th_html_submit_1.setOnclick("if($(this).parents('form').find('input[name=mp3File]').val() == ''){$(this).parents('form').find('input[name=mp3File]').remove();} page.postAjax('addForm', 'setUpSrv-container'); return false;");
          int _jspx_eval_html_submit_1 = _jspx_th_html_submit_1.doStartTag();
          if (_jspx_th_html_submit_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_submit_value_onclick_nobody.reuse(_jspx_th_html_submit_1);
            return;
          }
          _jspx_tagPool_html_submit_value_onclick_nobody.reuse(_jspx_th_html_submit_1);
          out.write("\n\t\t\t\t\t</div>\n\t\t\t\t");
          int evalDoAfterBody = _jspx_th_html_form_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_html_form_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_html_form_styleId_method_enctype_action.reuse(_jspx_th_html_form_2);
        return;
      }
      _jspx_tagPool_html_form_styleId_method_enctype_action.reuse(_jspx_th_html_form_2);
      out.write("\n\t\t\t\t\n\t\t\t\t<hr class=\"clearer\"/>\n\t\t\t\t\n\t\t\t</div>\n\n\t\t\t<hr class=\"clearer\" />\n\t\t</div>\t\n\t\n\t\t</div>\n\t</div>\n\t\n</div>\n\n\n<div id=\"how-to-container\" class=\"main-cadre-contener\">\n\n\t<div class=\"main-cadre-top\"><h2>");
      out.print(DicoTools.dico(dico_lang , "services/how_does_it_work"));
      out.write("</h2></div>\n\t\n\t<div class=\"main-cadre-content\">\n\t\t<hr class=\"spacer\"/>\n\t\t<div class=\"srv-main-config\">\n\t\t\t<p>\t");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/how_to_create"));
      out.write("</p>\n\t\t</div>\n\t\t\n\t\t<hr class=\"spacer\"/>\n\t\t\t\n\t</div>\n</div>\n\t\n\t\n<script type=\"text/javascript\">\n<!--\n\tvar so = new SWFObject(\"../include_flash/audioControl.swf\", \"audiocontrol\", \"300\", \"150\", \"9\", \"#FFFFFF\");\n\tso.addParam(\"wmode\", \"transparent\");\n\tso.addVariable(\"myServer\",\"");
      out.print(serverPath);
      out.write("\");\n\tso.addVariable(\"mySound\",\"");
      out.print(mp3Id);
      out.write("\");\n\tso.addVariable(\"playMode\",\"true\");\n\tso.addVariable(\"message\",\"");
      out.print(DicoTools.dico(dico_lang, "myMessagesVocal/warning"));
      out.write("\");\n\tif (!so.write(\"flashcontent\")) { // bad player detection > force creation\n\t\tvar container = document.getElementById(\"flashcontent\");\n\t\tcontainer.innerHTML = so.getSWFHTML();\n\t}\n//-->\n</script>\n\n");
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

  private boolean _jspx_meth_logic_equal_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_0.setParent(null);
    _jspx_th_logic_equal_0.setName("mySrvNathanCreateForm");
    _jspx_th_logic_equal_0.setProperty("error");
    _jspx_th_logic_equal_0.setValue("1");
    int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
    if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t$.msgPopupOverlay(\"Impossible d'uploader cette partie.\", 8000);\t\t\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_0);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_1.setParent(null);
    _jspx_th_logic_equal_1.setName("mySrvNathanCreateForm");
    _jspx_th_logic_equal_1.setProperty("error");
    _jspx_th_logic_equal_1.setValue("2");
    int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
    if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t$.msgPopupOverlay(\"La version ne contient aucun son.\", 8000);\t\t\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_1);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_1);
    return false;
  }

  private boolean _jspx_meth_logic_equal_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_2.setParent(null);
    _jspx_th_logic_equal_2.setName("mySrvNathanCreateForm");
    _jspx_th_logic_equal_2.setProperty("error");
    _jspx_th_logic_equal_2.setValue("3");
    int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
    if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t\t$.msgPopupOverlay(\"La création a échoué.\", 8000);\t\t\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_2);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_2);
    return false;
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
    _jspx_th_html_hidden_0.setValue("delete");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
    return false;
  }

  private boolean _jspx_meth_html_hidden_3(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_3 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_3.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_1);
    _jspx_th_html_hidden_3.setProperty("dispatch");
    _jspx_th_html_hidden_3.setValue("finish");
    int _jspx_eval_html_hidden_3 = _jspx_th_html_hidden_3.doStartTag();
    if (_jspx_th_html_hidden_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_3);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_3);
    return false;
  }

  private boolean _jspx_meth_html_hidden_6(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_6 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_6.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_2);
    _jspx_th_html_hidden_6.setProperty("dispatch");
    _jspx_th_html_hidden_6.setValue("add");
    int _jspx_eval_html_hidden_6 = _jspx_th_html_hidden_6.doStartTag();
    if (_jspx_th_html_hidden_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_6);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_6);
    return false;
  }

  private boolean _jspx_meth_html_file_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:file
    org.apache.struts.taglib.html.FileTag _jspx_th_html_file_0 = (org.apache.struts.taglib.html.FileTag) _jspx_tagPool_html_file_property_nobody.get(org.apache.struts.taglib.html.FileTag.class);
    _jspx_th_html_file_0.setPageContext(_jspx_page_context);
    _jspx_th_html_file_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_2);
    _jspx_th_html_file_0.setProperty("mp3File");
    int _jspx_eval_html_file_0 = _jspx_th_html_file_0.doStartTag();
    if (_jspx_th_html_file_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_file_property_nobody.reuse(_jspx_th_html_file_0);
      return true;
    }
    _jspx_tagPool_html_file_property_nobody.reuse(_jspx_th_html_file_0);
    return false;
  }
}
