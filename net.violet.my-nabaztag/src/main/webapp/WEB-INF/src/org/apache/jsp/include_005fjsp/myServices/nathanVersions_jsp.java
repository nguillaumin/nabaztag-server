package org.apache.jsp.include_005fjsp.myServices;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class nathanVersions_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
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

    java.lang.Object _jspx_tag_1 = null;

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
      _jspx_th_bean_define_0.setName("mySrvNathanHomeForm");
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
      out.write("\n<div id=\"config-bloc\">\n\t\t\t\n\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setId("idApplet");
      _jspx_th_bean_define_1.setName("mySrvNathanHomeForm");
      _jspx_th_bean_define_1.setProperty("appletId");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object idApplet = null;
      idApplet = (java.lang.Object) _jspx_page_context.findAttribute("idApplet");
      out.write('\n');
      out.write('	');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setId("isbn");
      _jspx_th_bean_define_2.setName("mySrvNathanHomeForm");
      _jspx_th_bean_define_2.setProperty("isbn");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      java.lang.Object isbn = null;
      isbn = (java.lang.Object) _jspx_page_context.findAttribute("isbn");
      out.write('\n');
      out.write('	');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setId("bookSerial");
      _jspx_th_bean_define_3.setName("mySrvNathanHomeForm");
      _jspx_th_bean_define_3.setProperty("bookSerial");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      java.lang.Object bookSerial = null;
      bookSerial = (java.lang.Object) _jspx_page_context.findAttribute("bookSerial");
      out.write("\n\t<div class=\"nathan-create\">\n\t");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/hint_create"));
      out.write("<a href=\"../action/srvNathanCreate.do?isbn=");
      out.print(isbn);
      out.write("&appletId=");
      out.print(idApplet);
      out.write("&dispatch=load\" onclick=\"page.loadInDiv('#setUpSrv-container',this.href); return false;\">");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/hint_link_create"));
      out.write("</a>\n\t<hr class=\"clearer\"/></div>\n\t\n\t<div id=\"list-bloc\">\n\t\n\t\t<form action=\"../action/srvNathanHome.do\" id=\"selectionForm\">\n\t\t\t<input type=\"hidden\" name=\"idApplet\" value=\"");
      out.print(idApplet);
      out.write("\" />\n\t\t\t<input type=\"hidden\" name=\"isbn\" value=\"");
      out.print(isbn);
      out.write("\" />\n\t\t\t<input type=\"hidden\" name=\"dispatch\" value=\"subscribe\" />\n\t<div class=\"nathan\" id=\"cadre\">\n\t\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_0.setParent(null);
      _jspx_th_logic_iterate_0.setName("mySrvNathanHomeForm");
      _jspx_th_logic_iterate_0.setProperty("resultList");
      _jspx_th_logic_iterate_0.setId("version");
      int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
      if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object version = null;
        if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_0.doInitBody();
        }
        version = (java.lang.Object) _jspx_page_context.findAttribute("version");
        do {
          out.write("\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_4.setId("objectLogin");
          _jspx_th_bean_define_4.setName("version");
          _jspx_th_bean_define_4.setProperty("author");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object objectLogin = null;
          objectLogin = (java.lang.Object) _jspx_page_context.findAttribute("objectLogin");
          out.write("\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_5.setId("nb");
          _jspx_th_bean_define_5.setName("version");
          _jspx_th_bean_define_5.setProperty("nb");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object nb = null;
          nb = (java.lang.Object) _jspx_page_context.findAttribute("nb");
          out.write("\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_6.setId("description");
          _jspx_th_bean_define_6.setName("version");
          _jspx_th_bean_define_6.setProperty("description");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object description = null;
          description = (java.lang.Object) _jspx_page_context.findAttribute("description");
          out.write("\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_7.setId("versionId");
          _jspx_th_bean_define_7.setName("version");
          _jspx_th_bean_define_7.setProperty("id");
          int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
          if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
          java.lang.Object versionId = null;
          versionId = (java.lang.Object) _jspx_page_context.findAttribute("versionId");
          out.write("\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_8.setId("img");
          _jspx_th_bean_define_8.setName("version");
          _jspx_th_bean_define_8.setProperty("img");
          int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
          if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
          java.lang.Object img = null;
          img = (java.lang.Object) _jspx_page_context.findAttribute("img");
          out.write("\n\t\t\t\t<div id=\"myMp3Player\" class=\"myPlayerMp3Message\"></div>\n\t\t\t\t<div class=\"version-bloc\">\n\t\t\t\t\t<div class=\"version-selector\">\n\t\t\t\t\t<input type=\"checkbox\" name=\"selectedVersions\" value=\"");
          out.print(versionId );
          out.write("\"  id=\"v");
          out.print(versionId );
          out.write("\" />\n\t\t\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_equal_0.setValue("-1");
          _jspx_th_logic_equal_0.setName("version");
          _jspx_th_logic_equal_0.setProperty("img");
          int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
          if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t\t\t\t\t\t<a onclick=\"javascript:selectInput('v");
              out.print(versionId );
              out.write("');\" ><img src=\"../photo/default_S.jpg\"/></a>\n\t\t\t\t\t\t");
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
          out.write("\n\t\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_notEqual_0.setValue("-1");
          _jspx_th_logic_notEqual_0.setName("version");
          _jspx_th_logic_notEqual_0.setProperty("img");
          int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
          if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
              _jspx_th_logic_equal_1.setName("version");
              _jspx_th_logic_equal_1.setProperty("official");
              _jspx_th_logic_equal_1.setValue("true");
              int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
              if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t\t\t\t\t<a onclick=\"javascript:selectInput('v");
                  out.print(versionId );
                  out.write("');\" ><img src=\"");
                  out.print(img);
                  out.write("\"/></a>\n\t\t\t\t\t\t\t");
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
              out.write("\n\t\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
              _jspx_th_logic_equal_2.setName("version");
              _jspx_th_logic_equal_2.setProperty("official");
              _jspx_th_logic_equal_2.setValue("false");
              int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
              if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t\t\t\t\t<a onclick=\"javascript:selectInput('v");
                  out.print(versionId );
                  out.write("');\" ><img src=\"../photo/");
                  out.print(img);
                  out.write("_S.jpg\"/></a>\n\t\t\t\t\t\t\t");
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
              out.write("\n\t\t\t\t\t\t");
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
          out.write("\t\n\t\t\t\t\t</div>\n\t\t\t\t<div class=\"version-info\">\n\t\t\t\t<div class=\"version-titre\">\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/list_tells"));
          out.write(' ');
          out.print(objectLogin);
          out.write(" </div>\n\t\t\t\t\t<div class=\"version-details\">\n\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_iterate_1.setName("version");
          _jspx_th_logic_iterate_1.setProperty("tags");
          _jspx_th_logic_iterate_1.setId("tag");
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
              out.write("\n\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_9.setId("name");
              _jspx_th_bean_define_9.setName("tag");
              _jspx_th_bean_define_9.setProperty("label");
              _jspx_th_bean_define_9.setType("java.lang.String");
              int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
              if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                return;
              }
              _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
              java.lang.String name = null;
              name = (java.lang.String) _jspx_page_context.findAttribute("name");
              out.write("\n\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , name));
              out.write(" |\n\t\t\t\t\t");
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
          out.write("\n\t\t\t\t\t</div>\n\t\t\t\t\t<div class=\"version-note\">");
          out.print(nb);
          out.write(' ');
          out.print(DicoTools.dico(dico_lang , "srv_nathan/list_subscribers"));
          out.write(" </div>\n\t\t\t       <div class=\"version-details\">\n\t\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_equal_3.setName("version");
          _jspx_th_logic_equal_3.setProperty("shared");
          _jspx_th_logic_equal_3.setValue("true");
          int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
          if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\t\t\t\t\n\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "srv_nathan/versions_shared"));
              out.write("\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_equal_3.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_equal_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_3);
            return;
          }
          _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_3);
          out.write("</div>\n\t\t\t\t\t\n\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_notEqual_1.setName("version");
          _jspx_th_logic_notEqual_1.setProperty("shared");
          _jspx_th_logic_notEqual_1.setValue("true");
          int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
          if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "srv_nathan/versions_notshared"));
              out.write("\n\t\t\t\t\t");
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
          out.write("\n\t\t\t\t\t<div class=\"version-des\">");
          out.print(description);
          out.write("</div>\n\t\t\t         </div>\n\t\t\t\t\t<div id=\"option-links\" class=\"version-tools\">\n\t\t\t\t\t<div class=\"item-tools\">\n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_10.setId("preview");
          _jspx_th_bean_define_10.setName("version");
          _jspx_th_bean_define_10.setProperty("preview");
          int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
          if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
          java.lang.Object preview = null;
          preview = (java.lang.Object) _jspx_page_context.findAttribute("preview");
          out.write("\n\t\t\t\t<a href=\"#js;\"\n\t\t\t\t\tonclick=\"loadPersoPlayer('");
          out.print(preview);
          out.write("', '300', '1', 'myMp3Player'); return false;\">");
          out.print(DicoTools.dico(dico_lang,
													"srv_nathan/list_preview"));
          out.write("</a>\n\t\t\t\t\t<a href=\"../action/srvNathanHome.do?isbn=");
          out.print(isbn);
          out.write("&versionId=");
          out.print(versionId);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=delete\" onclick=\"page.loadInDiv('#main-config-bloc', this.href);return false;\">");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/versions_delete"));
          out.write("</a><br/>\n\t\t\t\t\t\t<a href=\"../action/srvNathanConfig.do?isbn=");
          out.print(isbn);
          out.write("&versionId=");
          out.print(versionId);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=load\" onclick=\"page.loadInDiv('#setUpSrv-container', this.href);return false;\">");
          out.print(DicoTools.dico(dico_lang , "srv_nathan/versions_edit"));
          out.write("</a>\n\t\t\t\t\t\t\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t\t<hr class=\"clearer1\"/>\n\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
          version = (java.lang.Object) _jspx_page_context.findAttribute("version");
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
      out.write("\n\t\t<div class=\"button-bloc\">\n\t\t\t<input  type=\"hidden\" value=\"");
      out.print(bookSerial);
      out.write("\" name=\"bookSerial\"/>\t\t\n\t\t\t<input type=\"submit\" value=\"");
      out.print(DicoTools.dico(dico_lang , "srv_nathan/add_to_selection"));
      out.write("\" onclick=\"activateTab('selectionTab'); page.postAjax('selectionForm', 'main-config-bloc'); return false;\"/>\n\t\t\t</div>\n\t        </div>\n\t\t</form>\n\t</div>\n</div>");
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
}
