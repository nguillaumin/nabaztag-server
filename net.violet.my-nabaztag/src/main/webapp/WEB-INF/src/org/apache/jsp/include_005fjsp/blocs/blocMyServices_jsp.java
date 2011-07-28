package org.apache.jsp.include_005fjsp.blocs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class blocMyServices_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_lessEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_empty_property_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_logic_greaterThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_lessEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_empty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_logic_greaterThan_value_property_name.release();
    _jspx_tagPool_logic_lessEqual_value_property_name.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_empty_property_name.release();
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

      out.write("\r\n\r\n<h1>\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write(" \r\n\r\n\r\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n\r\n\t");
      //  logic:greaterThan
      org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
      _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_greaterThan_0.setParent(null);
      _jspx_th_logic_greaterThan_0.setName("BlocForm");
      _jspx_th_logic_greaterThan_0.setProperty("user_main");
      _jspx_th_logic_greaterThan_0.setValue("0");
      int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
      if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t");
          out.print(DicoTools.dico(dico_lang , "bloc/myservices_title"));
          out.write('\r');
          out.write('\n');
          out.write('	');
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
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:lessEqual
      org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_0 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_property_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
      _jspx_th_logic_lessEqual_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_lessEqual_0.setParent(null);
      _jspx_th_logic_lessEqual_0.setName("BlocForm");
      _jspx_th_logic_lessEqual_0.setProperty("user_main");
      _jspx_th_logic_lessEqual_0.setValue("0");
      int _jspx_eval_logic_lessEqual_0 = _jspx_th_logic_lessEqual_0.doStartTag();
      if (_jspx_eval_logic_lessEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t");
          out.print(DicoTools.dico(dico_lang, "bloc/about_nabaztag_title"));
          out.write('\r');
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_lessEqual_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_lessEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_lessEqual_value_property_name.reuse(_jspx_th_logic_lessEqual_0);
        return;
      }
      _jspx_tagPool_logic_lessEqual_value_property_name.reuse(_jspx_th_logic_lessEqual_0);
      out.write("\r\n</h1>\r\n\r\n<div class=\"block-content\">\r\n<div class=\"inner\">\r\n");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("BlocForm");
      _jspx_th_logic_notEmpty_0.setProperty("result");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write(" \r\n\t<ul class=\"list\" >\r\n \t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_logic_iterate_0.setName("BlocForm");
          _jspx_th_logic_iterate_0.setProperty("result");
          _jspx_th_logic_iterate_0.setId("blocData");
          int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
          if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object blocData = null;
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_0.doInitBody();
            }
            blocData = (java.lang.Object) _jspx_page_context.findAttribute("blocData");
            do {
              out.write("\r\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_0.setId("url");
              _jspx_th_bean_define_0.setName("blocData");
              _jspx_th_bean_define_0.setProperty("url");
              int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
              if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
              java.lang.Object url = null;
              url = (java.lang.Object) _jspx_page_context.findAttribute("url");
              out.write("\r\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_1.setId("id");
              _jspx_th_bean_define_1.setName("blocData");
              _jspx_th_bean_define_1.setProperty("id");
              int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
              if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
              java.lang.Object id = null;
              id = (java.lang.Object) _jspx_page_context.findAttribute("id");
              out.write("\r\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_2.setId("name");
              _jspx_th_bean_define_2.setName("blocData");
              _jspx_th_bean_define_2.setProperty("name");
              int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
              if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
              java.lang.Object name = null;
              name = (java.lang.Object) _jspx_page_context.findAttribute("name");
              out.write("\r\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_3.setId("type");
              _jspx_th_bean_define_3.setName("blocData");
              _jspx_th_bean_define_3.setProperty("type");
              int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
              if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
              java.lang.Object type = null;
              type = (java.lang.Object) _jspx_page_context.findAttribute("type");
              out.write("\r\n\t\r\n\t\t\t");
/* Les nabcasts */
              out.write("\r\n\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_0.setName("blocData");
              _jspx_th_logic_equal_0.setProperty("type");
              _jspx_th_logic_equal_0.setValue("1");
              int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
              if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t<li style=\"background-image:url(../include_img/services/icones_services/icon_service.gif); background:none;\" id=\"id_");
                  out.print(id);
                  out.write('_');
                  out.print(type);
                  out.write("\" class=\"nabcast\"><img src=\"../include_img/loading.gif\" class=\"loader\"/><a class=\"srvConfigLink\" title=\"");
                  out.print(DicoTools.dico_if(dico_lang, name.toString()));
                  out.write("\" href='");
                  out.print(url);
                  out.write("'><span class=\"srvName\">");
                  out.print(DicoTools.dico_if(dico_lang, name.toString()));
                  out.write("</span></a></li>\r\n\t\t\t");
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
              out.write("\r\n\t\t\t\r\n\t\t\t");
/* Les services */
              out.write("\r\n\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_1.setName("blocData");
              _jspx_th_logic_equal_1.setProperty("type");
              _jspx_th_logic_equal_1.setValue("2");
              int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
              if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t<li style=\"background-image:url(../include_img/services/icones_services/icon_service.gif);  background:none;\" id=\"id_");
                  out.print(id);
                  out.write('_');
                  out.print(type);
                  out.write("\" class=\"service\"><img src=\"../include_img/loading.gif\" class=\"loader\"/><a class=\"srvConfigLink\" title=\"");
                  out.print(DicoTools.dico_if(dico_lang, name.toString()));
                  out.write("\" href='");
                  out.print(url);
                  out.write("'><span class=\"srvName\">");
                  out.print(DicoTools.dico_if(dico_lang, name.toString()));
                  out.write("</span></a></li>\r\n\t\t\t");
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
              out.write("\t\t\r\n\t\t\t\r\n\t\t\t");
/* Les services en attente de validation */
              out.write("\r\n\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_2.setName("blocData");
              _jspx_th_logic_equal_2.setProperty("type");
              _jspx_th_logic_equal_2.setValue("4");
              int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
              if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t<li style=\"background-image:url(../include_img/services/icones_services/icon_service.gif);  background:none;\" id=\"id_");
                  out.print(id);
                  out.write('_');
                  out.print(type);
                  out.write("\" class=\"service servicactivated\"><img src=\"../include_img/loading.gif\" class=\"loader\"/><a class=\"srvConfigLink\" title=\"");
                  out.print(DicoTools.dico_if(dico_lang, name.toString()));
                  out.write("\" href='");
                  out.print(url);
                  out.write("'><span class=\"srvName\">");
                  out.print(DicoTools.dico_if(dico_lang, name.toString()));
                  out.write("</span></a></li>\r\n\t\t\t");
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
              out.write("\r\n\t\t\t\r\n\t\t\t\t\t\r\n\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
              blocData = (java.lang.Object) _jspx_page_context.findAttribute("blocData");
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
          out.write("\r\n  </ul>\r\n");
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
      out.write('\r');
      out.write('\n');
      //  logic:empty
      org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_0 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
      _jspx_th_logic_empty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_empty_0.setParent(null);
      _jspx_th_logic_empty_0.setName("BlocForm");
      _jspx_th_logic_empty_0.setProperty("result");
      int _jspx_eval_logic_empty_0 = _jspx_th_logic_empty_0.doStartTag();
      if (_jspx_eval_logic_empty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  logic:greaterThan
          org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_1 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
          _jspx_th_logic_greaterThan_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_greaterThan_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_empty_0);
          _jspx_th_logic_greaterThan_1.setName("BlocForm");
          _jspx_th_logic_greaterThan_1.setProperty("user_main");
          _jspx_th_logic_greaterThan_1.setValue("0");
          int _jspx_eval_logic_greaterThan_1 = _jspx_th_logic_greaterThan_1.doStartTag();
          if (_jspx_eval_logic_greaterThan_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t");
              out.print(DicoTools.dico(dico_lang , "bloc/myservices_no_service_error"));
              out.write('\r');
              out.write('\n');
              out.write('	');
              int evalDoAfterBody = _jspx_th_logic_greaterThan_1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_greaterThan_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_1);
            return;
          }
          _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_1);
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  logic:lessEqual
          org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_1 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_property_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
          _jspx_th_logic_lessEqual_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_lessEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_empty_0);
          _jspx_th_logic_lessEqual_1.setName("BlocForm");
          _jspx_th_logic_lessEqual_1.setProperty("user_main");
          _jspx_th_logic_lessEqual_1.setValue("0");
          int _jspx_eval_logic_lessEqual_1 = _jspx_th_logic_lessEqual_1.doStartTag();
          if (_jspx_eval_logic_lessEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t");
              out.print(DicoTools.dico(dico_lang, "bloc/about_nabaztag_description"));
              out.write('\r');
              out.write('\n');
              out.write('	');
              int evalDoAfterBody = _jspx_th_logic_lessEqual_1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_lessEqual_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_lessEqual_value_property_name.reuse(_jspx_th_logic_lessEqual_1);
            return;
          }
          _jspx_tagPool_logic_lessEqual_value_property_name.reuse(_jspx_th_logic_lessEqual_1);
          out.write('\r');
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_empty_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_empty_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_0);
        return;
      }
      _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_0);
      out.write("\r\n</div>\r\n</div>\r\n\r\n<script language=\"javascript\">\r\n\t//TB_init(\"bloc-MyServices\");\r\n\tbloc.services.init();\r\n</script>");
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
