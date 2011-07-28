package org.apache.jsp.include_005fjsp.myServices;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class nathanHome_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_name_id;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
    _jspx_tagPool_logic_greaterThan_value_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_logic_iterate_name_id.release();
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

      out.write("\n\n\n\n");

	response.setContentType("text/html;charset=UTF-8");

      out.write("\n\n\n\n\n");
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
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("mySrvNathanHomeForm");
      _jspx_th_bean_define_1.setProperty("objectLogin");
      _jspx_th_bean_define_1.setId("thisObjectLogin");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object thisObjectLogin = null;
      thisObjectLogin = (java.lang.Object) _jspx_page_context.findAttribute("thisObjectLogin");
      out.write("\n\n<div id=\"setUpSrv-container\">");
      out.write("\n<div id=\"setUpSrv\">");
      out.write('\n');
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_0.setParent(null);
      _jspx_th_logic_notEqual_0.setName("mySrvNathanHomeForm");
      _jspx_th_logic_notEqual_0.setProperty("isMarkup");
      _jspx_th_logic_notEqual_0.setValue("0");
      int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
      if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\n\t<div\n\t\tclass=\"main-cadre-contener serviceContener serviceBookReaderConfig\">\n\t<div class=\"main-cadre-top\">\n\t<h2>");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/home_title"));
          out.write("</h2>\n\t</div>\n\n\t<div class=\"main-cadre-content\">\n\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
          _jspx_th_bean_define_2.setId("idApplet");
          _jspx_th_bean_define_2.setName("mySrvNathanHomeForm");
          _jspx_th_bean_define_2.setProperty("appletId");
          int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
          if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
          java.lang.Object idApplet = null;
          idApplet = (java.lang.Object) _jspx_page_context.findAttribute("idApplet");
          out.write(' ');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
          _jspx_th_bean_define_3.setId("isbn");
          _jspx_th_bean_define_3.setName("mySrvNathanHomeForm");
          _jspx_th_bean_define_3.setProperty("isbn");
          int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
          if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
          java.lang.Object isbn = null;
          isbn = (java.lang.Object) _jspx_page_context.findAttribute("isbn");
          out.write("\n\n\t<div class=\"intro\">");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/config_book1"));
          out.write("\n\t<a href=\"../action/srvNathanCreate.do?isbn=");
          out.print(isbn);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=load\"\n\t\tonclick=\"page.loadInDiv('#setUpSrv-container',this.href); return false;\">");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/link_create"));
          out.write("</a>\n\t");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/config_book2"));
          out.write("</div>\n\t<hr class=\"clearer\"/>\n\t\n\t");
          out.write('\n');
          out.write('	');
          //  logic:greaterThan
          org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
          _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_greaterThan_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
          _jspx_th_logic_greaterThan_0.setName("mySrvNathanHomeForm");
          _jspx_th_logic_greaterThan_0.setProperty("isMarkup");
          _jspx_th_logic_greaterThan_0.setValue("1");
          int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
          if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t<div class=\"ztamp-img\">\n\t\t\t");
              //  logic:iterate
              org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
              _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
              _jspx_th_logic_iterate_0.setName("mySrvNathanHomeForm");
              _jspx_th_logic_iterate_0.setProperty("mySetting");
              _jspx_th_logic_iterate_0.setId("player");
              int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
              if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object player = null;
                if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_logic_iterate_0.doInitBody();
                }
                player = (java.lang.Object) _jspx_page_context.findAttribute("player");
                do {
                  out.write("\n\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_bean_define_4.setId("bookSerial");
                  _jspx_th_bean_define_4.setName("mySrvNathanHomeForm");
                  _jspx_th_bean_define_4.setProperty("serial");
                  int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
                  if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
                  java.lang.Object bookSerial = null;
                  bookSerial = (java.lang.Object) _jspx_page_context.findAttribute("bookSerial");
                  out.write("\n\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_bean_define_5.setId("serial");
                  _jspx_th_bean_define_5.setName("player");
                  _jspx_th_bean_define_5.setProperty("ztampSerial");
                  _jspx_th_bean_define_5.setType("String");
                  int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
                  if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
                    return;
                  }
                  _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
                  String serial = null;
                  serial = (String) _jspx_page_context.findAttribute("serial");
                  out.write("\n\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_bean_define_6.setId("picture");
                  _jspx_th_bean_define_6.setName("player");
                  _jspx_th_bean_define_6.setProperty("pictureObject");
                  int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
                  if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                  java.lang.Object picture = null;
                  picture = (java.lang.Object) _jspx_page_context.findAttribute("picture");
                  out.write("\n\t\t\t\t\n\t\t\t\t");
                  //  logic:notEqual
                  org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                  _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
                  _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_logic_notEqual_1.setName("mySrvNathanHomeForm");
                  _jspx_th_logic_notEqual_1.setProperty("serial");
                  _jspx_th_logic_notEqual_1.setValue(serial);
                  int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
                  if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\t\t\t\t\t\t\n\t\t\t\t<div id=\"zt-");
                      out.print(serial );
                      out.write("\" > \n\t\t\t\t\t<a href=\"../action/srvNathanHome.do?dispatch=load&serial=");
                      out.print(serial);
                      out.write("&isbn=");
                      out.print(isbn);
                      out.write("&appletId=");
                      out.print(idApplet);
                      out.write("\" onclick=\" activateTab('zt-");
                      out.print(serial );
                      out.write("'); page.loadInDiv('#ztamps-contener',this.href, '#ztamps-content'); return false;\"  >\n\t\t\t \t\t<img src=\"../include_img/ztamps/");
                      out.print(picture );
                      out.write(".gif\" alt=\"");
                      out.print(serial );
                      out.write("\" /></a>\n\t \t\t\t</div>\n\t \t\t\t");
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
                  out.write("\n\t \t\t\t\n\t \t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_logic_equal_0.setName("mySrvNathanHomeForm");
                  _jspx_th_logic_equal_0.setProperty("serial");
                  _jspx_th_logic_equal_0.setValue(serial);
                  int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
                  if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\t\t\t\t\t\t\n\t\t\t\t<div id=\"zt-");
                      out.print(serial );
                      out.write("\" class=\"active\"> \n\t\t\t\t\t<a href=\"../action/srvNathanHome.do?dispatch=load&serial=");
                      out.print(serial);
                      out.write("&isbn=");
                      out.print(isbn);
                      out.write("&appletId=");
                      out.print(idApplet);
                      out.write("\" onclick=\" activateTab('zt-");
                      out.print(serial );
                      out.write("'); page.loadInDiv('#ztamps-contener',this.href, '#ztamps-content'); return false;\"  >\n\t\t\t \t\t<img src=\"../include_img/ztamps/");
                      out.print(picture );
                      out.write(".gif\" alt=\"");
                      out.print(serial );
                      out.write("\" /></a>\n\t \t\t\t</div>\n\t \t\t\t");
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
                  out.write("\n\t \t\t\t\n\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
                  player = (java.lang.Object) _jspx_page_context.findAttribute("player");
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
              out.write("\n\t\t</div>\n\t\t<hr class=\"clearer\" />\n\t    <br />\n\t");
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
          out.write("\n\t\n\t<div class=\"ztamps-contener\" id=\"ztamps-contener\">\n\t\t<div class=\"tabSousNavContener-tr\" id=\"ztamps-content\">\n\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
          _jspx_th_bean_define_7.setId("bookSerial");
          _jspx_th_bean_define_7.setName("mySrvNathanHomeForm");
          _jspx_th_bean_define_7.setProperty("serial");
          int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
          if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
          java.lang.Object bookSerial = null;
          bookSerial = (java.lang.Object) _jspx_page_context.findAttribute("bookSerial");
          out.write("\n\t\t\n\t\t<ul id=\"nathanMenu\" class=\"tabSousNav\">\n\t\t\t<li id=\"nathanTab\" class=\"active\"><a\n\t\t\t\thref=\"../action/srvNathanHome.do?isbn=");
          out.print(isbn);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=official&serial=");
          out.print(bookSerial);
          out.write("\"\n\t\t\t\tonclick=\"activateTab('nathanTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;\"><span>");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/menu_nathan"));
          out.write("</span></a></li>\n\t\t\t<li id=\"versionsTab\"><a\n\t\t\t\thref=\"../action/srvNathanHome.do?isbn=");
          out.print(isbn);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=myversion&bookSerial=");
          out.print(bookSerial);
          out.write("\"\n\t\t\t\tonclick=\"activateTab('versionsTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;\"><span>");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/menu_versions"));
          out.write("</span></a></li>\n\t\t\t<li id=\"popularTab\"><a\n\t\t\t\thref=\"../action/srvNathanHome.do?isbn=");
          out.print(isbn);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=popular&serial=");
          out.print(bookSerial);
          out.write("\"\n\t\t\t\tonclick=\"activateTab('popularTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;\"><span>");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/menu_popular"));
          out.write("</span></a></li>\n\t\t\t<li id=\"newsTab\"><a\n\t\t\t\thref=\"../action/srvNathanHome.do?isbn=");
          out.print(isbn);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=recent&serial=");
          out.print(bookSerial);
          out.write("\"\n\t\t\t\tonclick=\"activateTab('newsTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;\"><span>");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/menu_news"));
          out.write("</span></a></li>\n\t\t\t<li id=\"searchTab\"><a\n\t\t\t\thref=\"../action/srvNathanHome.do?isbn=");
          out.print(isbn);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=search&bookSerial=");
          out.print(bookSerial);
          out.write("\"\n\t\t\t\tonclick=\"activateTab('searchTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;\"><span>");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/menu_search"));
          out.write("</span></a></li>\n\t\t\t<li id=\"selectionTab\"><a\n\t\t\t\thref=\"../action/srvNathanHome.do?isbn=");
          out.print(isbn);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=selection&bookSerial=");
          out.print(bookSerial);
          out.write("\"\n\t\t\t\tonclick=\"activateTab('selectionTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;\"><span>");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/menu_selection"));
          out.write("</span></a></li>\n\t\t</ul>\n\t\t<div class=\"tabSousNavContener-bl\">\n\t\t<div id=\"main-config-bloc\">\n\t\t\n\t\t<div id=\"config-bloc\">\n\t\t<div class = \"nathan-create\">\n\t\t");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/hint_create"));
          out.write("<a\n\t\t\thref=\"../action/srvNathanCreate.do?isbn=");
          out.print(isbn);
          out.write("&appletId=");
          out.print(idApplet);
          out.write("&dispatch=load\"\n\t\t\tonclick=\"page.loadInDiv('#setUpSrv-container',this.href); return false;\">");
          out.print(DicoTools.dico(dico_lang,
												"srv_nathan/hint_link_create"));
          out.write("</a>\n\t\t</div>\n\t\t<hr class = \"clearer\" />\n\n\t\t<div id=\"myMp3Player\" class=\"myPlayerMp3Message\"></div>\n\n\t\t<div id=\"list-bloc\">\n\t\t\n\t\t<form action=\"../action/srvNathanHome.do\" id=\"selectionForm\"><input\n\t\t\ttype=\"hidden\" name=\"idApplet\" value=\"");
          out.print(idApplet);
          out.write("\" /> <input\n\t\t\ttype=\"hidden\" name=\"isbn\" value=\"");
          out.print(isbn);
          out.write("\" /> <input \n\t\t\ttype=\"hidden\" name=\"dispatch\" value=\"subscribe\" />\n\n\t\t<div class=\"nathan\">\n\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
          _jspx_th_logic_iterate_1.setName("mySrvNathanHomeForm");
          _jspx_th_logic_iterate_1.setProperty("resultList");
          _jspx_th_logic_iterate_1.setId("version");
          int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
          if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object version = null;
            if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_1.doInitBody();
            }
            version = (java.lang.Object) _jspx_page_context.findAttribute("version");
            do {
              out.write("\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_8.setId("objectLogin");
              _jspx_th_bean_define_8.setName("version");
              _jspx_th_bean_define_8.setProperty("author");
              _jspx_th_bean_define_8.setType("String");
              int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
              if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                return;
              }
              _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
              String objectLogin = null;
              objectLogin = (String) _jspx_page_context.findAttribute("objectLogin");
              out.write("\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_9.setId("nb");
              _jspx_th_bean_define_9.setName("version");
              _jspx_th_bean_define_9.setProperty("nb");
              int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
              if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
              java.lang.Object nb = null;
              nb = (java.lang.Object) _jspx_page_context.findAttribute("nb");
              out.write("\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_10.setId("description");
              _jspx_th_bean_define_10.setName("version");
              _jspx_th_bean_define_10.setProperty("description");
              int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
              if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
              java.lang.Object description = null;
              description = (java.lang.Object) _jspx_page_context.findAttribute("description");
              out.write("\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_11.setId("versionId");
              _jspx_th_bean_define_11.setName("version");
              _jspx_th_bean_define_11.setProperty("id");
              int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
              if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
              java.lang.Object versionId = null;
              versionId = (java.lang.Object) _jspx_page_context.findAttribute("versionId");
              out.write("\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_12 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_12.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_12.setId("img");
              _jspx_th_bean_define_12.setName("version");
              _jspx_th_bean_define_12.setProperty("img");
              int _jspx_eval_bean_define_12 = _jspx_th_bean_define_12.doStartTag();
              if (_jspx_th_bean_define_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
              java.lang.Object img = null;
              img = (java.lang.Object) _jspx_page_context.findAttribute("img");
              out.write("\n\n\t\t\t<div class=\"version-bloc\">\n\t\t\t\t<div class=\"version-selector\">\n\t\t\t\t<input type=\"checkbox\"\n\t\t\t\tname=\"selectedVersions\" value=\"");
              out.print(versionId );
              out.write("\" id=\"v");
              out.print(versionId );
              out.write("\" />\n\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_logic_equal_1.setValue("-1");
              _jspx_th_logic_equal_1.setName("version");
              _jspx_th_logic_equal_1.setProperty("img");
              int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
              if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t\t\t<a onclick=\"javascript:selectInput('v");
                  out.print(versionId );
                  out.write("');\" ><img src=\"../photo/default_S.jpg\"/></a>\n\t\t\t\t\t");
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
              out.write("\n\t\t\t\t\t");
              //  logic:notEqual
              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
              _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_logic_notEqual_2.setValue("-1");
              _jspx_th_logic_notEqual_2.setName("version");
              _jspx_th_logic_notEqual_2.setProperty("img");
              int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
              if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_2);
                  _jspx_th_logic_equal_2.setName("version");
                  _jspx_th_logic_equal_2.setProperty("official");
                  _jspx_th_logic_equal_2.setValue("true");
                  int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
                  if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t\t\t\t<a onclick=\"javascript:selectInput('v");
                      out.print(versionId );
                      out.write("');\" ><img src=\"");
                      out.print(img);
                      out.write("\"/></a>\n\t\t\t\t\t\t");
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
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_2);
                  _jspx_th_logic_equal_3.setName("version");
                  _jspx_th_logic_equal_3.setProperty("official");
                  _jspx_th_logic_equal_3.setValue("false");
                  int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
                  if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t\t\t\t<a onclick=\"javascript:selectInput('v");
                      out.print(versionId );
                      out.write("');\" ><img src=\"../photo/");
                      out.print(img);
                      out.write("_S.jpg\"/></a>\n\t\t\t\t\t\t");
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
                  out.write("\n\t\t\t\t\t");
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
              out.write("\t\n\t\t\t\t</div>\n\t\t\t\t\n\t\t\t\t<div class=\"version-info\">\n\t\t\t\t<div class=\"version-titre\">\n\t\t\t\t");
              out.print(DicoTools.dico(dico_lang,"srv_nathan/list_tells"));
              out.write("\n\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_logic_equal_4.setName("version");
              _jspx_th_logic_equal_4.setProperty("official");
              _jspx_th_logic_equal_4.setValue("true");
              int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
              if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t\t\t");
                  out.print(DicoTools.dico(dico_lang,"srv_nathan/nathan"));
                  out.write("\n\t\t\t\t\t");
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
              out.write("\n\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_logic_equal_5.setName("version");
              _jspx_th_logic_equal_5.setProperty("official");
              _jspx_th_logic_equal_5.setValue("false");
              int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
              if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t\t\t");
                  out.print(objectLogin);
                  out.write(" \n\t\t\t\t\t");
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
              out.write("\n\t\t\t\t\n\t\t\t\t\t\n\t\t\t\t</div>\n                <div class=\"version-details\">\n\t\t\t\t");
              //  logic:iterate
              org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_2 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
              _jspx_th_logic_iterate_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_iterate_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_logic_iterate_2.setName("version");
              _jspx_th_logic_iterate_2.setProperty("tags");
              _jspx_th_logic_iterate_2.setId("tag");
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
                  out.write("\n\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_13 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_13.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
                  _jspx_th_bean_define_13.setId("name");
                  _jspx_th_bean_define_13.setName("tag");
                  _jspx_th_bean_define_13.setProperty("label");
                  _jspx_th_bean_define_13.setType("java.lang.String");
                  int _jspx_eval_bean_define_13 = _jspx_th_bean_define_13.doStartTag();
                  if (_jspx_th_bean_define_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
                    return;
                  }
                  _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
                  java.lang.String name = null;
                  name = (java.lang.String) _jspx_page_context.findAttribute("name");
                  out.write("\n\t\t\t\t");
                  out.print(DicoTools.dico(dico_lang, name));
                  out.write(" |\n\t\t\t\t\t\t");
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
              out.write("\n\t\t\t\t\t\t</div>\n\t\t\t\t");
              out.write(" \n\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_6 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_6.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_logic_equal_6.setName("version");
              _jspx_th_logic_equal_6.setProperty("official");
              _jspx_th_logic_equal_6.setValue("false");
              int _jspx_eval_logic_equal_6 = _jspx_th_logic_equal_6.doStartTag();
              if (_jspx_eval_logic_equal_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t<div class=\"version-note\">");
                  out.print(nb);
                  out.write(' ');
                  out.print(DicoTools.dico(dico_lang,	"srv_nathan/list_subscribers"));
                  out.write("\n\t\t\t\t</div>\n\t\t\t\t");
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
              out.write("\n\t\t\t\t<div class=\"version-des\">");
              out.print(description);
              out.write("</div>\n\t\t\t\t</div>\n\t\t\t\t");
              out.write("\n\t\t\t\t<div class=\"version-tools\">\n\t\t\t\t<div class=\"item-tools\">\n\t\t\t\t");
              //  logic:notEqual
              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_3 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
              _jspx_th_logic_notEqual_3.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_logic_notEqual_3.setName("version");
              _jspx_th_logic_notEqual_3.setProperty("preview");
              _jspx_th_logic_notEqual_3.setValue("");
              int _jspx_eval_logic_notEqual_3 = _jspx_th_logic_notEqual_3.doStartTag();
              if (_jspx_eval_logic_notEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\n\t\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_14 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_14.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_3);
                  _jspx_th_bean_define_14.setId("preview");
                  _jspx_th_bean_define_14.setName("version");
                  _jspx_th_bean_define_14.setProperty("preview");
                  int _jspx_eval_bean_define_14 = _jspx_th_bean_define_14.doStartTag();
                  if (_jspx_th_bean_define_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
                  java.lang.Object preview = null;
                  preview = (java.lang.Object) _jspx_page_context.findAttribute("preview");
                  out.write("\n\t\t\t\t<a href=\"#js;\"\n\t\t\t\t\tonclick=\"loadPersoPlayer('");
                  out.print(preview);
                  out.write("', '300', '1', 'myMp3Player'); return false;\">");
                  out.print(DicoTools.dico(dico_lang,
													"srv_nathan/list_preview"));
                  out.write("</a><br />\n\t\t\t\t\t\t\n\t\t\t\t");
                  out.write("\n\t\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_7 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_7.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_3);
                  _jspx_th_logic_equal_7.setName("version");
                  _jspx_th_logic_equal_7.setProperty("official");
                  _jspx_th_logic_equal_7.setValue("false");
                  int _jspx_eval_logic_equal_7 = _jspx_th_logic_equal_7.doStartTag();
                  if (_jspx_eval_logic_equal_7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\t\t\t\t\t\t\n\t\t\t\t\t");
                      //  logic:notEqual
                      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_4 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                      _jspx_th_logic_notEqual_4.setPageContext(_jspx_page_context);
                      _jspx_th_logic_notEqual_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_7);
                      _jspx_th_logic_notEqual_4.setName("mySrvNathanHomeForm");
                      _jspx_th_logic_notEqual_4.setProperty("objectLogin");
                      _jspx_th_logic_notEqual_4.setValue(objectLogin);
                      int _jspx_eval_logic_notEqual_4 = _jspx_th_logic_notEqual_4.doStartTag();
                      if (_jspx_eval_logic_notEqual_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        do {
                          out.write("\t\t\t\t\t\t\n\t\t\t\t\n\t\t\t\t\t");
String text = DicoTools.dico(dico_lang, "srv_nathan/nabthem_text" ); 
					  String resp = DicoTools.dico(dico_lang, "srv_nathan/nabthem_ok" );
					
                          out.write("\n\t\t\t\t\t<a href=\"#js;\" onclick=\"$.get('myMessagesSendClin.do?destName=");
                          out.print(objectLogin);
                          out.write("&send=1&categId=94'); msgHandling.simpleMsgShow('");
                          out.print(resp);
                          out.write("'); return false;\">");
                          out.print(DicoTools.dico(dico_lang, "srv_nathan/nabthem" ));
                          out.write("</a>\n\t\t\t\t\n\t\t\t\t");
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
                      out.write("\n\t\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_equal_7.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_equal_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_7);
                    return;
                  }
                  _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_7);
                  out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t");
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
              out.write("</div></div>\n\t\t\t</div>\n\t\t\t\n\t\t\t<hr class=\"clearer1\"/>\n\t\t\t \n\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
              version = (java.lang.Object) _jspx_page_context.findAttribute("version");
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
          out.write(" \n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_15 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_15.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
          _jspx_th_bean_define_15.setId("bookSerial");
          _jspx_th_bean_define_15.setName("mySrvNathanHomeForm");
          _jspx_th_bean_define_15.setProperty("serial");
          int _jspx_eval_bean_define_15 = _jspx_th_bean_define_15.doStartTag();
          if (_jspx_th_bean_define_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_15);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_15);
          bookSerial = (java.lang.Object) _jspx_page_context.findAttribute("bookSerial");
          out.write("\n\t\t\t<input type=\"hidden\" name=\"bookSerial\" value=\"");
          out.print(bookSerial);
          out.write("\" />\n\t\t\t");
          //  logic:notEmpty
          org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
          _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEmpty_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
          _jspx_th_logic_notEmpty_0.setName("mySrvNathanHomeForm");
          _jspx_th_logic_notEmpty_0.setProperty("resultList");
          int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
          if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t\t\t<div class=\"button-bloc\">\n\t\t\t\t\t<input type=\"submit\"\n\t\t\t\t\tvalue=\"");
              out.print(DicoTools.dico(dico_lang , "srv_nathan/add_to_selection"));
              out.write("\"\n\t\t\t\t\tonclick=\"activateTab('selectionTab'); page.postAjax('selectionForm', 'main-config-bloc'); return false;\" />\t\t\t\n\t\t\t\t</div>\n\t\t\t");
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
          out.write("\n\t\t</div>\n\t\t</form>\n\t\t</div>\n\t\t</div>\n\t\t</div>\n\t\t</div>\n        </div>\n\t\t</div>\n\t<hr class=\"clearer\" />\n\n\t</div>\n\t</div>\n");
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
      out.write(' ');
      out.write(' ');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_8 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_8.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_8.setParent(null);
      _jspx_th_logic_equal_8.setName("mySrvNathanHomeForm");
      _jspx_th_logic_equal_8.setProperty("isMarkup");
      _jspx_th_logic_equal_8.setValue("0");
      int _jspx_eval_logic_equal_8 = _jspx_th_logic_equal_8.doStartTag();
      if (_jspx_eval_logic_equal_8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t<div class=\"main-cadre-contener serviceContener\">\n\t<div class=\"main-cadre-top\">\n\t<h2>");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/title_nobook"));
          out.write("</h2>\n\t</div>\n\n\t<div class=\"main-cadre-content\">");
          out.print(DicoTools.dico(dico_lang, "srv_" + dicoRoot.toString()
								+ "/config_nobooks"));
          out.write("</div>\n\t</div>\n");
          int evalDoAfterBody = _jspx_th_logic_equal_8.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_8);
        return;
      }
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_8);
      out.write("</div>\n\n\n");
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_9 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_9.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_9.setParent(null);
      _jspx_th_logic_equal_9.setName("mySrvNathanHomeForm");
      _jspx_th_logic_equal_9.setProperty("isMarkup");
      _jspx_th_logic_equal_9.setValue("0");
      int _jspx_eval_logic_equal_9 = _jspx_th_logic_equal_9.doStartTag();
      if (_jspx_eval_logic_equal_9 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          //  logic:notEmpty
          org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_1 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
          _jspx_th_logic_notEmpty_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEmpty_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_9);
          _jspx_th_logic_notEmpty_1.setName("mySrvNathanHomeForm");
          _jspx_th_logic_notEmpty_1.setProperty("otherBooksList");
          int _jspx_eval_logic_notEmpty_1 = _jspx_th_logic_notEmpty_1.doStartTag();
          if (_jspx_eval_logic_notEmpty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\n\t\t<div class=\"main-cadre-contener serviceContener\">\n\t\t<div class=\"main-cadre-top\">\n\t\t<h2>");
              out.print(DicoTools.dico(dico_lang, "srv_nathan/other_books_title"));
              out.write("</h2>\n\t\t</div>\n\n\n\t\t<div class=\"main-cadre-content\">\n\t\t<hr class=\"spacer\" />\n\t\t<div class=\"srv-main-config\">\n\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_16 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_16.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_16.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
              _jspx_th_bean_define_16.setId("booksList");
              _jspx_th_bean_define_16.setName("mySrvNathanHomeForm");
              _jspx_th_bean_define_16.setProperty("otherBooksList");
              int _jspx_eval_bean_define_16 = _jspx_th_bean_define_16.doStartTag();
              if (_jspx_th_bean_define_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_16);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_16);
              java.lang.Object booksList = null;
              booksList = (java.lang.Object) _jspx_page_context.findAttribute("booksList");
              out.write(" \n\t\t\t<div class=\"nathan\">\n\t\t");
              //  logic:iterate
              org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_3 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
              _jspx_th_logic_iterate_3.setPageContext(_jspx_page_context);
              _jspx_th_logic_iterate_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
              _jspx_th_logic_iterate_3.setId("book");
              _jspx_th_logic_iterate_3.setName("booksList");
              int _jspx_eval_logic_iterate_3 = _jspx_th_logic_iterate_3.doStartTag();
              if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object book = null;
                if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_logic_iterate_3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_logic_iterate_3.doInitBody();
                }
                book = (java.lang.Object) _jspx_page_context.findAttribute("book");
                do {
                  out.write("\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_17 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_17.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_17.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_bean_define_17.setId("bookImg");
                  _jspx_th_bean_define_17.setName("book");
                  _jspx_th_bean_define_17.setProperty("img");
                  int _jspx_eval_bean_define_17 = _jspx_th_bean_define_17.doStartTag();
                  if (_jspx_th_bean_define_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_17);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_17);
                  java.lang.Object bookImg = null;
                  bookImg = (java.lang.Object) _jspx_page_context.findAttribute("bookImg");
                  out.write("\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_18 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_18.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_18.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_bean_define_18.setId("bookImgGrise");
                  _jspx_th_bean_define_18.setName("book");
                  _jspx_th_bean_define_18.setProperty("imgGrise");
                  int _jspx_eval_bean_define_18 = _jspx_th_bean_define_18.doStartTag();
                  if (_jspx_th_bean_define_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_18);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_18);
                  java.lang.Object bookImgGrise = null;
                  bookImgGrise = (java.lang.Object) _jspx_page_context.findAttribute("bookImgGrise");
                  out.write("\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_19 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_19.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_19.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_bean_define_19.setId("bookTitle");
                  _jspx_th_bean_define_19.setName("book");
                  _jspx_th_bean_define_19.setProperty("title");
                  int _jspx_eval_bean_define_19 = _jspx_th_bean_define_19.doStartTag();
                  if (_jspx_th_bean_define_19.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_19);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_19);
                  java.lang.Object bookTitle = null;
                  bookTitle = (java.lang.Object) _jspx_page_context.findAttribute("bookTitle");
                  out.write("\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_20 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_20.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_20.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_bean_define_20.setId("bookAuthor");
                  _jspx_th_bean_define_20.setName("book");
                  _jspx_th_bean_define_20.setProperty("author");
                  int _jspx_eval_bean_define_20 = _jspx_th_bean_define_20.doStartTag();
                  if (_jspx_th_bean_define_20.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_20);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_20);
                  java.lang.Object bookAuthor = null;
                  bookAuthor = (java.lang.Object) _jspx_page_context.findAttribute("bookAuthor");
                  out.write("\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_21 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_21.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_21.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_bean_define_21.setId("url");
                  _jspx_th_bean_define_21.setName("book");
                  _jspx_th_bean_define_21.setProperty("url");
                  int _jspx_eval_bean_define_21 = _jspx_th_bean_define_21.doStartTag();
                  if (_jspx_th_bean_define_21.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_21);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_21);
                  java.lang.Object url = null;
                  url = (java.lang.Object) _jspx_page_context.findAttribute("url");
                  out.write("\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_22 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_22.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_22.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_bean_define_22.setId("nb");
                  _jspx_th_bean_define_22.setName("book");
                  _jspx_th_bean_define_22.setProperty("nbVersions");
                  int _jspx_eval_bean_define_22 = _jspx_th_bean_define_22.doStartTag();
                  if (_jspx_th_bean_define_22.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_22);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_22);
                  java.lang.Object nb = null;
                  nb = (java.lang.Object) _jspx_page_context.findAttribute("nb");
                  out.write("\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_23 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_23.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_23.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_bean_define_23.setId("hasBook");
                  _jspx_th_bean_define_23.setName("book");
                  _jspx_th_bean_define_23.setProperty("hasBook");
                  int _jspx_eval_bean_define_23 = _jspx_th_bean_define_23.doStartTag();
                  if (_jspx_th_bean_define_23.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_23);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_23);
                  java.lang.Object hasBook = null;
                  hasBook = (java.lang.Object) _jspx_page_context.findAttribute("hasBook");
                  out.write("\n\t\t\t\n\t\t\t<div class=\"bouquin\">\n\t\t\t<div class=\"image\">\n\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_10 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_10.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_logic_equal_10.setName("book");
                  _jspx_th_logic_equal_10.setProperty("hasBook");
                  _jspx_th_logic_equal_10.setValue("1");
                  int _jspx_eval_logic_equal_10 = _jspx_th_logic_equal_10.doStartTag();
                  if (_jspx_eval_logic_equal_10 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t<a href=\"myNablife.do?service=");
                      out.print(url);
                      out.write("\"><img src=\"");
                      out.print(bookImg);
                      out.write("\" alt=\" \"/></a>\n\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_equal_10.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_equal_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_10);
                    return;
                  }
                  _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_10);
                  out.write("\n\t\t\t");
                  //  logic:notEqual
                  org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_5 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                  _jspx_th_logic_notEqual_5.setPageContext(_jspx_page_context);
                  _jspx_th_logic_notEqual_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_logic_notEqual_5.setName("book");
                  _jspx_th_logic_notEqual_5.setProperty("hasBook");
                  _jspx_th_logic_notEqual_5.setValue("1");
                  int _jspx_eval_logic_notEqual_5 = _jspx_th_logic_notEqual_5.doStartTag();
                  if (_jspx_eval_logic_notEqual_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t<a href=\"myNablife.do?service=");
                      out.print(url);
                      out.write("\"><img src=\"");
                      out.print(bookImgGrise);
                      out.write("\" alt=\" \"/></a>\n\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_notEqual_5.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_notEqual_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_5);
                    return;
                  }
                  _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_5);
                  out.write("\n\t\t\t</div>\n\t\t\t<div class=\"collec-titre\" >");
                  out.print(bookTitle);
                  out.write("</div>\n\t\t\t<div class=\"collec-author\">");
                  out.print(bookAuthor);
                  out.write("</div>\n\n\t\t\t<div class=\"collec-versions\" ><a href=\"myNablife.do?service=");
                  out.print(url);
                  out.write("\"><strong>");
                  out.print(nb);
                  out.write("</strong> ");
                  out.print(DicoTools.dico(dico_lang,
											"srv_nathan/other_books_versions"));
                  out.write("</a></div>\n\n\t\t\t<div class=\"collec-versions\" >\n\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_11 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_11.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_logic_equal_11.setName("book");
                  _jspx_th_logic_equal_11.setProperty("hasBook");
                  _jspx_th_logic_equal_11.setValue("1");
                  int _jspx_eval_logic_equal_11 = _jspx_th_logic_equal_11.doStartTag();
                  if (_jspx_eval_logic_equal_11 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t<a href=\"myNablife.do?service=");
                      out.print(url);
                      out.write('"');
                      out.write('>');
                      out.print(DicoTools.dico(dico_lang,
														"srv_nathan/other_books_haveIt"));
                      out.write("</a>\n\t\t\t\t\n\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_equal_11.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_equal_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_11);
                    return;
                  }
                  _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_11);
                  out.write(' ');
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_12 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_12.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
                  _jspx_th_logic_equal_12.setName("book");
                  _jspx_th_logic_equal_12.setProperty("hasBook");
                  _jspx_th_logic_equal_12.setValue("0");
                  int _jspx_eval_logic_equal_12 = _jspx_th_logic_equal_12.doStartTag();
                  if (_jspx_eval_logic_equal_12 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\n\t\t\t\t<a href=\"http://www.ztore.net\" target=\"_blank\">");
                      out.print(DicoTools.dico(dico_lang,
														"srv_nathan/other_books_dontHaveIt"));
                      out.write("</a>\n\t\t\t\t\n\t\t\t");
                      int evalDoAfterBody = _jspx_th_logic_equal_12.doAfterBody();
                      if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                        break;
                    } while (true);
                  }
                  if (_jspx_th_logic_equal_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_12);
                    return;
                  }
                  _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_12);
                  out.write("\n\t\t\t</div>\n\t\t\t</div>\n\t\t");
                  int evalDoAfterBody = _jspx_th_logic_iterate_3.doAfterBody();
                  book = (java.lang.Object) _jspx_page_context.findAttribute("book");
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
                if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.popBody();
                }
              }
              if (_jspx_th_logic_iterate_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_iterate_name_id.reuse(_jspx_th_logic_iterate_3);
                return;
              }
              _jspx_tagPool_logic_iterate_name_id.reuse(_jspx_th_logic_iterate_3);
              out.write("\n\t\t</div>\n\t\t<hr class=\"clearer\" />\n\t\t</div>\n\t\t<hr class=\"spacer\" />\n\t\t</div>\n\n\t\t</div>\n\n\t");
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
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_equal_9.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_9);
        return;
      }
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_9);
      out.write(' ');
      out.write("\n\n<div id=\"how-to-container\" class=\"main-cadre-contener\">\n\n<div class=\"main-cadre-top\">\n<h2>");
      out.print(DicoTools.dico(dico_lang, "services/how_does_it_work"));
      out.write("</h2>\n</div>\n\n<div class=\"main-cadre-content\">\n<hr class=\"spacer\" />\n<div class=\"srv-main-config\">");
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_6 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_6.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_6.setParent(null);
      _jspx_th_logic_notEqual_6.setName("mySrvNathanHomeForm");
      _jspx_th_logic_notEqual_6.setProperty("isMarkup");
      _jspx_th_logic_notEqual_6.setValue("0");
      int _jspx_eval_logic_notEqual_6 = _jspx_th_logic_notEqual_6.doStartTag();
      if (_jspx_eval_logic_notEqual_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t<p>");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/how_to"));
          out.write("</p>\n");
          int evalDoAfterBody = _jspx_th_logic_notEqual_6.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_notEqual_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_6);
        return;
      }
      _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_6);
      out.write(' ');
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_13 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_13.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_13.setParent(null);
      _jspx_th_logic_equal_13.setName("mySrvNathanHomeForm");
      _jspx_th_logic_equal_13.setProperty("isMarkup");
      _jspx_th_logic_equal_13.setValue("0");
      int _jspx_eval_logic_equal_13 = _jspx_th_logic_equal_13.doStartTag();
      if (_jspx_eval_logic_equal_13 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\n\n<div class=\"nathan-how_to_img\">\n\t<img title=\"Nabaztag vous lit 'Mes p'tites histoires'\" alt=\"Nabaztag vous lit 'Mes p'tites histoires'\" src=\"../include_img/lapin-bibli.gif\"/>\n</div>\n<div class=\"nathan-how_to\">\n\t");
          out.print(DicoTools.dico(dico_lang, "srv_nathan/how_to_nobook"));
          out.write("\n\t</div>\n\t<hr class=\"clearer\"/>\n");
          int evalDoAfterBody = _jspx_th_logic_equal_13.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_13);
        return;
      }
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_13);
      out.write("</div>\n\n<hr class=\"spacer\" />\n\n</div>\n</div>\n</div>");
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
