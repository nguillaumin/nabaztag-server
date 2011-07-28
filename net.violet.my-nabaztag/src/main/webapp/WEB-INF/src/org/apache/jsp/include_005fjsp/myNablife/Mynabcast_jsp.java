package org.apache.jsp.include_005fjsp.myNablife;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class Mynabcast_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_lessThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_rewrite_forward_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_lessThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_rewrite_forward_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
    _jspx_tagPool_logic_greaterThan_value_property_name.release();
    _jspx_tagPool_logic_lessThan_value_property_name.release();
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

      out.write("\n\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\n");
	String dico_lang =	Long.toString(SessionTools.getLangFromSession(session, request).getId());
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myNabcastForm");
      _jspx_th_bean_define_0.setProperty("langUser");
      _jspx_th_bean_define_0.setId("lang");
      _jspx_th_bean_define_0.setType("String");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      String lang = null;
      lang = (String) _jspx_page_context.findAttribute("lang");
      out.write("\r\n\r\n<ul>\r\n");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_0.setParent(null);
      _jspx_th_logic_iterate_0.setName("myNabcastForm");
      _jspx_th_logic_iterate_0.setProperty("listNabcastSubscribe");
      _jspx_th_logic_iterate_0.setId("NabcastData");
      int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
      if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object NabcastData = null;
        if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_0.doInitBody();
        }
        NabcastData = (java.lang.Object) _jspx_page_context.findAttribute("NabcastData");
        do {
          out.write("\r\n\t<li>\r\n\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_1.setId("nabcast_titre");
          _jspx_th_bean_define_1.setName("NabcastData");
          _jspx_th_bean_define_1.setProperty("nabcast_titre");
          int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
          if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
          java.lang.Object nabcast_titre = null;
          nabcast_titre = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_titre");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_2.setId("nabcast_categ");
          _jspx_th_bean_define_2.setName("NabcastData");
          _jspx_th_bean_define_2.setProperty("nabcast_categ");
          int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
          if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
          java.lang.Object nabcast_categ = null;
          nabcast_categ = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_categ");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_3.setId("nabcast_id");
          _jspx_th_bean_define_3.setName("NabcastData");
          _jspx_th_bean_define_3.setProperty("nabcast_id");
          int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
          if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
          java.lang.Object nabcast_id = null;
          nabcast_id = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_id");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_4.setId("nabcast_hours");
          _jspx_th_bean_define_4.setName("NabcastData");
          _jspx_th_bean_define_4.setProperty("nabcast_hours");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object nabcast_hours = null;
          nabcast_hours = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_hours");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_5.setId("nabcast_minutes");
          _jspx_th_bean_define_5.setName("NabcastData");
          _jspx_th_bean_define_5.setProperty("nabcast_minutes");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object nabcast_minutes = null;
          nabcast_minutes = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_minutes");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_equal_0.setName("NabcastData");
          _jspx_th_logic_equal_0.setProperty("nabcast_hours");
          _jspx_th_logic_equal_0.setValue("-1");
          int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
          if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t");
              out.print(nabcast_titre);
              out.write(' ');
              out.write(':');
              out.print(nabcast_categ);
              out.write("\r\n\t\t<!--aucun-->\r\n\t");
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
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_notEqual_0.setName("NabcastData");
          _jspx_th_logic_notEqual_0.setProperty("nabcast_hours");
          _jspx_th_logic_notEqual_0.setValue("-1");
          int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
          if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t");
              out.print(nabcast_titre);
              out.write(' ');
              out.write(':');
              out.print(nabcast_categ);
              out.write(' ');
              out.print(nabcast_hours);
              out.write(":\r\n\t\t");
              //  logic:greaterThan
              org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
              _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_greaterThan_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
              _jspx_th_logic_greaterThan_0.setName("NabcastData");
              _jspx_th_logic_greaterThan_0.setProperty("nabcast_minutes");
              _jspx_th_logic_greaterThan_0.setValue("9");
              int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
              if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t");
                  out.print(nabcast_minutes);
                  out.write("\r\n\t\t");
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
              out.write("\r\n\t\t");
              //  logic:lessThan
              org.apache.struts.taglib.logic.LessThanTag _jspx_th_logic_lessThan_0 = (org.apache.struts.taglib.logic.LessThanTag) _jspx_tagPool_logic_lessThan_value_property_name.get(org.apache.struts.taglib.logic.LessThanTag.class);
              _jspx_th_logic_lessThan_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_lessThan_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
              _jspx_th_logic_lessThan_0.setName("NabcastData");
              _jspx_th_logic_lessThan_0.setProperty("nabcast_minutes");
              _jspx_th_logic_lessThan_0.setValue("10");
              int _jspx_eval_logic_lessThan_0 = _jspx_th_logic_lessThan_0.doStartTag();
              if (_jspx_eval_logic_lessThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t0");
                  out.print(nabcast_minutes);
                  out.write("\r\n\t\t");
                  int evalDoAfterBody = _jspx_th_logic_lessThan_0.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_lessThan_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_lessThan_value_property_name.reuse(_jspx_th_logic_lessThan_0);
                return;
              }
              _jspx_tagPool_logic_lessThan_value_property_name.reuse(_jspx_th_logic_lessThan_0);
              out.write('\r');
              out.write('\n');
              out.write('	');
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
          out.write("\r\n\t\r\n\t<a href=\"");
          if (_jspx_meth_html_rewrite_0(_jspx_th_logic_iterate_0, _jspx_page_context))
            return;
          out.write("?idNabcast=");
          out.print(nabcast_id);
          out.write("&action=displayHisto\">histo</a>\r\n\t<a href=\"");
          if (_jspx_meth_html_rewrite_1(_jspx_th_logic_iterate_0, _jspx_page_context))
            return;
          out.write("?idNabcast=");
          out.print(nabcast_id);
          out.write("&action=subscribe\">modif heure</a>\r\n\t<a href=\"");
          if (_jspx_meth_html_rewrite_2(_jspx_th_logic_iterate_0, _jspx_page_context))
            return;
          out.write("?idNabcast=");
          out.print(nabcast_id);
          out.write("&action=unsubscribe\">d&eacute;sabonner</a>\r\n");
          int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
          NabcastData = (java.lang.Object) _jspx_page_context.findAttribute("NabcastData");
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
      out.write("\r\n</ul>\r\n\r\n\r\n<ul>              \r\n");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_1.setParent(null);
      _jspx_th_logic_iterate_1.setName("myNabcastForm");
      _jspx_th_logic_iterate_1.setProperty("listNabcastCreate");
      _jspx_th_logic_iterate_1.setId("NabcastData");
      int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
      if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object NabcastData = null;
        if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_1.doInitBody();
        }
        NabcastData = (java.lang.Object) _jspx_page_context.findAttribute("NabcastData");
        do {
          out.write("\r\n\t<li>\r\n\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_6.setId("nabcast_titre");
          _jspx_th_bean_define_6.setName("NabcastData");
          _jspx_th_bean_define_6.setProperty("nabcast_titre");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object nabcast_titre = null;
          nabcast_titre = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_titre");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_7.setId("nabcast_categ");
          _jspx_th_bean_define_7.setName("NabcastData");
          _jspx_th_bean_define_7.setProperty("nabcast_categ");
          int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
          if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
          java.lang.Object nabcast_categ = null;
          nabcast_categ = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_categ");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_8.setId("nabcast_description");
          _jspx_th_bean_define_8.setName("NabcastData");
          _jspx_th_bean_define_8.setProperty("nabcast_description");
          int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
          if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
          java.lang.Object nabcast_description = null;
          nabcast_description = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_description");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_9.setId("nabcast_nbr_abonne");
          _jspx_th_bean_define_9.setName("NabcastData");
          _jspx_th_bean_define_9.setProperty("nabcast_nbr_abonne");
          int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
          if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
          java.lang.Object nabcast_nbr_abonne = null;
          nabcast_nbr_abonne = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_nbr_abonne");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_10.setId("nabcast_id");
          _jspx_th_bean_define_10.setName("NabcastData");
          _jspx_th_bean_define_10.setProperty("nabcast_id");
          int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
          if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
          java.lang.Object nabcast_id = null;
          nabcast_id = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_id");
          out.write("\r\n\r\n\t<a href=\"");
          if (_jspx_meth_html_rewrite_3(_jspx_th_logic_iterate_1, _jspx_page_context))
            return;
          out.write("?idNabcast=");
          out.print(nabcast_id);
          out.write("&action=editNabcast\"><strong>");
          out.print(nabcast_titre);
          out.write("</strong></a>\r\n\tdesc : ");
          out.print(nabcast_description);
          out.write("<br>\r\n\tcateg : ");
          out.print(nabcast_categ);
          out.write("<br>\r\n\tnb abonnes : ");
          out.print(nabcast_nbr_abonne);
          out.write(" abonnes<br>\r\n\r\n\t<a href=\"");
          if (_jspx_meth_html_rewrite_4(_jspx_th_logic_iterate_1, _jspx_page_context))
            return;
          out.write("?idNabcast=");
          out.print(nabcast_id);
          out.write("&action=ajoutNabcast\"></a>\r\n\t<a href=\"");
          if (_jspx_meth_html_rewrite_5(_jspx_th_logic_iterate_1, _jspx_page_context))
            return;
          out.write("?idNabcast=");
          out.print(nabcast_id);
          out.write("&action=editNabcast\"></a>\r\n\t<a href=\"");
          if (_jspx_meth_html_rewrite_6(_jspx_th_logic_iterate_1, _jspx_page_context))
            return;
          out.write("?idNabcast=");
          out.print(nabcast_id);
          out.write("&action=editNabcastSignok&mode=1\"></a>\r\n\t<a href=\"");
          if (_jspx_meth_html_rewrite_7(_jspx_th_logic_iterate_1, _jspx_page_context))
            return;
          out.write("?idNabcast=");
          out.print(nabcast_id);
          out.write("&action=delNabcast\" onclick=\"if(window.confirm('confirmation')){return true;}else{return false;}\" ></a>\r\n");
          int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
          NabcastData = (java.lang.Object) _jspx_page_context.findAttribute("NabcastData");
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
      out.write("\t\t\r\n</ul>\r\n");
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
    _jspx_th_html_rewrite_0.setForward("goNabcast");
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
    _jspx_th_html_rewrite_1.setForward("goNabcast");
    int _jspx_eval_html_rewrite_1 = _jspx_th_html_rewrite_1.doStartTag();
    if (_jspx_th_html_rewrite_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_1);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_1);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_2 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_2.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_html_rewrite_2.setForward("goNabcast");
    int _jspx_eval_html_rewrite_2 = _jspx_th_html_rewrite_2.doStartTag();
    if (_jspx_th_html_rewrite_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_2);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_2);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_3 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_3.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_html_rewrite_3.setForward("goNabcast");
    int _jspx_eval_html_rewrite_3 = _jspx_th_html_rewrite_3.doStartTag();
    if (_jspx_th_html_rewrite_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_3);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_3);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_4 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_4.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_html_rewrite_4.setForward("goNabcast");
    int _jspx_eval_html_rewrite_4 = _jspx_th_html_rewrite_4.doStartTag();
    if (_jspx_th_html_rewrite_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_4);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_4);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_5 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_5.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_html_rewrite_5.setForward("goNabcast");
    int _jspx_eval_html_rewrite_5 = _jspx_th_html_rewrite_5.doStartTag();
    if (_jspx_th_html_rewrite_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_5);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_5);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_6(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_6 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_6.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_html_rewrite_6.setForward("goNabcast");
    int _jspx_eval_html_rewrite_6 = _jspx_th_html_rewrite_6.doStartTag();
    if (_jspx_th_html_rewrite_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_6);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_6);
    return false;
  }

  private boolean _jspx_meth_html_rewrite_7(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_7 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_7.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_html_rewrite_7.setForward("goNabcast");
    int _jspx_eval_html_rewrite_7 = _jspx_th_html_rewrite_7.doStartTag();
    if (_jspx_th_html_rewrite_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_7);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_7);
    return false;
  }
}
