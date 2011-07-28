package org.apache.jsp.include_005fjsp.myMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyProfil_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_write_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_empty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_write_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_empty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_greaterEqual_value_property_name.release();
    _jspx_tagPool_bean_write_property_name_nobody.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
    _jspx_tagPool_logic_empty_property_name.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
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
      out.write("\r\n\n\r\n\r\n\r\n\r\n\r\n\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\t<div class=\"Column66\">\r\n\t\r\n\t\t\t<!-- Fiche d'identite********************************************************************************** -->\r\n\r\n\t\t\t\r\n\t\t\t<div id=\"profil\" class=\"profilBlockLeft bigFiche\" style=\"height:365px;\">   \r\n\t\t\t\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_0.setParent(null);
      _jspx_th_logic_equal_0.setName("myProfilForm");
      _jspx_th_logic_equal_0.setProperty("isFriend");
      _jspx_th_logic_equal_0.setValue("1");
      int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
      if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t\t<h1 class=\"title-big ttMyFriend\" style=\"position:absolute; right:5px; top:2px;\" ><strong>");
          out.print(DicoTools.dico(dico_lang, "directory/my_friend"));
          out.write("</strong></h1>\r\n\t\t\t\t\t");
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
      out.write("\r\n\t\t\t\r\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setId("userData");
      _jspx_th_bean_define_0.setName("myProfilForm");
      _jspx_th_bean_define_0.setProperty("profil");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.Object userData = null;
      userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
      out.write("\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setId("paysdata");
      _jspx_th_bean_define_1.setName("userData");
      _jspx_th_bean_define_1.setProperty("pays");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object paysdata = null;
      paysdata = (java.lang.Object) _jspx_page_context.findAttribute("paysdata");
      out.write("\r\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setId("user_id");
      _jspx_th_bean_define_2.setName("userData");
      _jspx_th_bean_define_2.setProperty("id");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      java.lang.Object user_id = null;
      user_id = (java.lang.Object) _jspx_page_context.findAttribute("user_id");
      out.write("\r\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setId("user_has_object");
      _jspx_th_bean_define_3.setName("userData");
      _jspx_th_bean_define_3.setProperty("user_has_object");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      java.lang.Object user_has_object = null;
      user_has_object = (java.lang.Object) _jspx_page_context.findAttribute("user_has_object");
      out.write("\r\n\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_4.setParent(null);
      _jspx_th_bean_define_4.setId("user_pseudo");
      _jspx_th_bean_define_4.setName("userData");
      _jspx_th_bean_define_4.setProperty("user_pseudo");
      int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
      if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
      java.lang.Object user_pseudo = null;
      user_pseudo = (java.lang.Object) _jspx_page_context.findAttribute("user_pseudo");
      out.write("\r\n\t\t\t\t\r\n\t\t\t\t\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t<!-- ***************** -->\r\n\t\t\t\t<h1 class=\"pseudo\">");
      out.print(user_pseudo.toString());
      out.write("</h1>\r\n\t\t\t\t\r\n\t\t\t\t<!-- ***************** -->\r\n\t\t\t\t<div class=\"identite\" >\r\n\t\t\t\t\t\r\n\t\t\t\t\t");
/* GARCON */
      out.write(' ');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_1.setParent(null);
      _jspx_th_logic_equal_1.setName("userData");
      _jspx_th_logic_equal_1.setProperty("annu_sexe");
      _jspx_th_logic_equal_1.setValue("H");
      int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
      if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.print(DicoTools.dico(dico_lang, "directory/sexe_man"));
          out.write(',');
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
      out.write("\r\n\t\t\t\t\t");
/* FILLE */
      out.write(' ');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_2.setParent(null);
      _jspx_th_logic_equal_2.setName("userData");
      _jspx_th_logic_equal_2.setProperty("annu_sexe");
      _jspx_th_logic_equal_2.setValue("F");
      int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
      if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.print(DicoTools.dico(dico_lang, "directory/sexe_woman"));
          out.write(',');
          out.write(' ');
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
      out.write("\r\n\t\t\t\t\t\r\n\t\t\t\t\t");
/* AGE */
      out.write("\r\n\t\t\t\t\t");
      //  logic:greaterEqual
      org.apache.struts.taglib.logic.GreaterEqualTag _jspx_th_logic_greaterEqual_0 = (org.apache.struts.taglib.logic.GreaterEqualTag) _jspx_tagPool_logic_greaterEqual_value_property_name.get(org.apache.struts.taglib.logic.GreaterEqualTag.class);
      _jspx_th_logic_greaterEqual_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_greaterEqual_0.setParent(null);
      _jspx_th_logic_greaterEqual_0.setName("userData");
      _jspx_th_logic_greaterEqual_0.setProperty("age");
      _jspx_th_logic_greaterEqual_0.setValue("1");
      int _jspx_eval_logic_greaterEqual_0 = _jspx_th_logic_greaterEqual_0.doStartTag();
      if (_jspx_eval_logic_greaterEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t\t");
          if (_jspx_meth_bean_write_0(_jspx_th_logic_greaterEqual_0, _jspx_page_context))
            return;
          out.write(" &nbsp;");
          out.print(DicoTools.dico(dico_lang, "directory/years"));
          out.write(", \r\n\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_greaterEqual_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_greaterEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_greaterEqual_value_property_name.reuse(_jspx_th_logic_greaterEqual_0);
        return;
      }
      _jspx_tagPool_logic_greaterEqual_value_property_name.reuse(_jspx_th_logic_greaterEqual_0);
      out.write("\r\n\t\t\t\t\t\r\n\t\t\t\t\t");
      if (_jspx_meth_bean_write_1(_jspx_page_context))
        return;
      out.write(',');
      out.write(' ');
      if (_jspx_meth_bean_write_2(_jspx_page_context))
        return;
      out.write(".\r\n\t\t\t\t\t\r\n\t\t\t\t</div>\r\n\r\n\t\t\t\t<div class=\"description\" style=\"position:relative;\" >\r\n\t\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t<div class=\"photoContener\" style=\"background:url(");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_3.setParent(null);
      _jspx_th_logic_equal_3.setName("userData");
      _jspx_th_logic_equal_3.setProperty("user_good");
      _jspx_th_logic_equal_3.setValue("1");
      int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
      if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          if (_jspx_meth_logic_equal_4(_jspx_th_logic_equal_3, _jspx_page_context))
            return;
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_3);
          _jspx_th_logic_equal_5.setName("userData");
          _jspx_th_logic_equal_5.setProperty("user_image");
          _jspx_th_logic_equal_5.setValue("1");
          int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
          if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("../photo/");
              out.print(user_id);
              out.write("_B.jpg");
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
      if (_jspx_meth_logic_equal_6(_jspx_page_context))
        return;
      out.write(") no-repeat; background-position:center center;\">\r\n\t\t\t\t\t\t&nbsp;\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div class=\"comment\">\r\n\t\t\t\t\t\t");
      if (_jspx_meth_bean_write_3(_jspx_page_context))
        return;
      out.write("\n\t\t\t\t\t\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_7 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_7.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_7.setParent(null);
      _jspx_th_logic_equal_7.setName("myProfilForm");
      _jspx_th_logic_equal_7.setProperty("isFriend");
      _jspx_th_logic_equal_7.setValue("0");
      int _jspx_eval_logic_equal_7 = _jspx_th_logic_equal_7.doStartTag();
      if (_jspx_eval_logic_equal_7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_7);
          _jspx_th_bean_define_5.setName("myProfilForm");
          _jspx_th_bean_define_5.setProperty("userlog_id");
          _jspx_th_bean_define_5.setId("us");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object us = null;
          us = (java.lang.Object) _jspx_page_context.findAttribute("us");
          out.write("\n\t\t\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_7);
          _jspx_th_bean_define_6.setName("myProfilForm");
          _jspx_th_bean_define_6.setProperty("user_id");
          _jspx_th_bean_define_6.setId("friend_id");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object friend_id = null;
          friend_id = (java.lang.Object) _jspx_page_context.findAttribute("friend_id");
          out.write("\n\t\t\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_7);
          _jspx_th_logic_notEqual_0.setName("myProfilForm");
          _jspx_th_logic_notEqual_0.setProperty("userlog_id");
          _jspx_th_logic_notEqual_0.setValue(friend_id.toString());
          int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
          if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t\t\t\t\t\t \t<a id=\"buttonAddFriend\" class=\"href-big-button bbAddFriend\" href='javascript:;' onclick=\"addFriend('");
              out.print(friend_id);
              out.write("')\"><strong>");
              out.print(DicoTools.dico(dico_lang, "profile/add_to_friends"));
              out.write("</strong></a>\r\n\t\t\t\t\t\t\t");
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
          out.write("\r\n\t\t\t\t\t\t");
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
      out.write("\r\n\t\t\t\t\t</div>\r\n\t\t\t\t\t<div class=\"commandes\">\r\n\t\t\t\t\t<ul>\r\n\t\t\t\t\t\r\n\t\t\t\t\t\t");
/* Si l'utilisateur possède un lapin */
      out.write("\r\n\t\t\t\t\t\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_8 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_8.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_8.setParent(null);
      _jspx_th_logic_equal_8.setName("userData");
      _jspx_th_logic_equal_8.setProperty("user_has_object");
      _jspx_th_logic_equal_8.setValue("true");
      int _jspx_eval_logic_equal_8 = _jspx_th_logic_equal_8.doStartTag();
      if (_jspx_eval_logic_equal_8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t\t\t<li class=\"envoyer\">");
          out.print(DicoTools.dico(dico_lang, "directory/send"));
          out.write("</li>\r\n\n\t\t\t\t\t\t\t\t\t\t<li class=\"msgLink\">\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
          out.print(user_pseudo.toString());
          out.write("', 'text');\">");
          out.print(DicoTools.dico(dico_lang, "directory/send_text"));
          out.write("</a>\r\n\t\t\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\t\t\t<li class=\"msgLink\">\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
          out.print(user_pseudo.toString());
          out.write("', 'mp3');\">");
          out.print(DicoTools.dico(dico_lang, "directory/send_mp3"));
          out.write("</a>\r\n\t\t\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\t\t\t<li class=\"msgLink\">\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
          out.print(user_pseudo.toString());
          out.write("', 'bibliotheque');\">");
          out.print(DicoTools.dico(dico_lang, "directory/send_music"));
          out.write("</a>\r\n\t\t\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t<li class=\"msgLink\">\r\n\t\t\t\t\t\t\t\t<a href=\"javascript:void(0);\" onclick=\"sendMsgTo('");
          out.print(user_pseudo.toString());
          out.write("', 'clindoeil');\">");
          out.print(DicoTools.dico(dico_lang, "directory/send_clins"));
          out.write("</a>\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t");
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
      out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
      out.write("\r\n\t\t\t\t\t\t");
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_1.setParent(null);
      _jspx_th_logic_notEqual_1.setName("userData");
      _jspx_th_logic_notEqual_1.setProperty("user_has_object");
      _jspx_th_logic_notEqual_1.setValue("true");
      int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
      if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t\t\t<li class=\"msgLink\" >");
          out.print(DicoTools.dico_jsp(dico_lang , "directory/no_nabaztag", user_pseudo));
          out.write("</li>\r\n\t\t\t\t\t\t");
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
      out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t\t<hr class=\"clearer\" />\r\n\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t\t\r\n\r\n    \r\n\t\t</div>\r\n\t\t<!-- /Fiche d'identité********************************************************************************** -->\t\t\r\n\t</div>\r\n\r\n\t<div class=\"Column33\">\r\n\t\t\r\n\t\t<div class=\"profilBlockRight\">\r\n\t\t\t<div id=\"signature\">   \r\n\t\t\t\t<h1>");
      out.print(DicoTools.dico(dico_lang, "profile/signature"));
      out.write("</h1>\n\t\t\t\t\t");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_7.setParent(null);
      _jspx_th_bean_define_7.setName("userData");
      _jspx_th_bean_define_7.setProperty("user_signature");
      _jspx_th_bean_define_7.setId("user_signature");
      int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
      if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
      java.lang.Object user_signature = null;
      user_signature = (java.lang.Object) _jspx_page_context.findAttribute("user_signature");
      out.write("\r\n\t\t\t\t\t<embed id=\"mymovie\" width=\"135\" height=\"135\" flashvars=\"cdll=");
      out.print(user_signature.toString() );
      out.write("\" quality=\"high\" name=\"mymovie\" src=\"../include_flash/CDLEditor.swf\" type=\"application/x-shockwave-flash\"/>\t\t\t\t\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t\r\n\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\r\n\t\t<div class=\"profilBlockRight\" id=\"nabshare\">\r\n\t\t\t<h1>");
      out.print(DicoTools.dico(dico_lang, "profile/nabshare"));
      out.write("</h1>\r\n\t\t\t<div class=\"profil-scroller\">\r\n\r\n\t\t\t\t");
      //  logic:empty
      org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_0 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
      _jspx_th_logic_empty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_empty_0.setParent(null);
      _jspx_th_logic_empty_0.setName("myProfilForm");
      _jspx_th_logic_empty_0.setProperty("nabShares");
      int _jspx_eval_logic_empty_0 = _jspx_th_logic_empty_0.doStartTag();
      if (_jspx_eval_logic_empty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang, "profile/nabshare_none"));
          out.write("\r\n\t\t\t\t");
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
      out.write("\r\n\r\n\t\t\t\t");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("myProfilForm");
      _jspx_th_logic_notEmpty_0.setProperty("nabShares");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_logic_iterate_0.setName("myProfilForm");
          _jspx_th_logic_iterate_0.setProperty("nabShares");
          _jspx_th_logic_iterate_0.setId("musicData");
          int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
          if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object musicData = null;
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_0.doInitBody();
            }
            musicData = (java.lang.Object) _jspx_page_context.findAttribute("musicData");
            do {
              out.write("\r\n\t\t\t\t\t\t\t<li>\r\n\t\t\t\t\t\t\t\t");
              if (_jspx_meth_bean_write_4(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
              musicData = (java.lang.Object) _jspx_page_context.findAttribute("musicData");
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
          out.write("\r\n\t\t\t\t\t</ul>\t\t\t\r\n\t\t\t\t");
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
      out.write("\r\n\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t\r\n\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\r\n\t\t<div class=\"profilBlockRight\" style=\"display:none;\"> ");
      out.write("\r\n\t\t\t<div id=\"nabcast\">   \r\n\t\t\t\t<h1>");
      out.print(DicoTools.dico(dico_lang, "profile/nabcast"));
      out.write("</h1>\r\n\r\n\t\t\t\t<div class=\"profil-scroller\">\t\t\t\t\r\n\t\t\t\t\t");
      //  logic:empty
      org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_1 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
      _jspx_th_logic_empty_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_empty_1.setParent(null);
      _jspx_th_logic_empty_1.setName("myProfilForm");
      _jspx_th_logic_empty_1.setProperty("nabCast");
      int _jspx_eval_logic_empty_1 = _jspx_th_logic_empty_1.doStartTag();
      if (_jspx_eval_logic_empty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang, "profile/nabcast_none"));
          out.write("\r\n\t\t\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_empty_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_empty_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_1);
        return;
      }
      _jspx_tagPool_logic_empty_property_name.reuse(_jspx_th_logic_empty_1);
      out.write("\t\t\t\t\r\n\t\t\t\t\t\r\n\t\t\t\t\t");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_1 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_1.setParent(null);
      _jspx_th_logic_notEmpty_1.setName("myProfilForm");
      _jspx_th_logic_notEmpty_1.setProperty("nabCast");
      int _jspx_eval_logic_notEmpty_1 = _jspx_th_logic_notEmpty_1.doStartTag();
      if (_jspx_eval_logic_notEmpty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
          _jspx_th_logic_iterate_1.setName("myProfilForm");
          _jspx_th_logic_iterate_1.setProperty("nabCast");
          _jspx_th_logic_iterate_1.setId("nabcastData");
          int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
          if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object nabcastData = null;
            if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_1.doInitBody();
            }
            nabcastData = (java.lang.Object) _jspx_page_context.findAttribute("nabcastData");
            do {
              out.write("\r\n\t\t\t\t\t\t\t\t\t<li>\r\n\t\t\t\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_8.setName("nabcastData");
              _jspx_th_bean_define_8.setProperty("nabcast_id");
              _jspx_th_bean_define_8.setId("nabcast_id");
              int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
              if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
              java.lang.Object nabcast_id = null;
              nabcast_id = (java.lang.Object) _jspx_page_context.findAttribute("nabcast_id");
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_bean_write_5(_jspx_th_logic_iterate_1, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
              nabcastData = (java.lang.Object) _jspx_page_context.findAttribute("nabcastData");
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
          out.write("\r\n\t\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t");
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
      out.write("\t\t\t\t\t\t\t\t\r\n\t\t\t\t</div>\r\n\r\n\t\t\t</div> \t   \t\t\r\n\t\t</div>\t\t\r\n\t\t\r\n\t</div>\r\n\t\r\n\t<hr class=\"spacer\" />\t\r\n\r\n\t<div class=\"Column50\">\r\n\t\t<div class=\"profilBlockLeft\">\t\r\n\t\t<div id=\"message_recu\">   \r\n\t\t\t<h1>");
      out.print(DicoTools.dico(dico_lang, "profile/messages_received"));
      out.write("</h1>\r\n\t\t\t<ul>");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_2 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_2.setParent(null);
      _jspx_th_logic_iterate_2.setName("myProfilForm");
      _jspx_th_logic_iterate_2.setProperty("messagesReceived");
      _jspx_th_logic_iterate_2.setId("messageReceivedData");
      int _jspx_eval_logic_iterate_2 = _jspx_th_logic_iterate_2.doStartTag();
      if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object messageReceivedData = null;
        if (_jspx_eval_logic_iterate_2 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_2.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_2.doInitBody();
        }
        messageReceivedData = (java.lang.Object) _jspx_page_context.findAttribute("messageReceivedData");
        do {
          out.write("<li>\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
          _jspx_th_bean_define_9.setName("messageReceivedData");
          _jspx_th_bean_define_9.setProperty("id");
          _jspx_th_bean_define_9.setId("message_id");
          int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
          if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
          java.lang.Object message_id = null;
          message_id = (java.lang.Object) _jspx_page_context.findAttribute("message_id");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
          _jspx_th_bean_define_10.setName("messageReceivedData");
          _jspx_th_bean_define_10.setProperty("userSenderId");
          _jspx_th_bean_define_10.setId("event_sender");
          int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
          if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
          java.lang.Object event_sender = null;
          event_sender = (java.lang.Object) _jspx_page_context.findAttribute("event_sender");
          out.write("\r\n\t\t\t\t\t");
          if (_jspx_meth_bean_write_6(_jspx_th_logic_iterate_2, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t\t<a href=\"javascript:;\" onclick=\"sendMsgToWithText('");
          if (_jspx_meth_bean_write_7(_jspx_th_logic_iterate_2, _jspx_page_context))
            return;
          out.write("', 'text');\" >");
          if (_jspx_meth_bean_write_8(_jspx_th_logic_iterate_2, _jspx_page_context))
            return;
          out.write("</a>\r\n\t\t\t\t\t");
          if (_jspx_meth_bean_write_9(_jspx_th_logic_iterate_2, _jspx_page_context))
            return;
          out.write("x\r\n\t\t\t</li>");
          int evalDoAfterBody = _jspx_th_logic_iterate_2.doAfterBody();
          messageReceivedData = (java.lang.Object) _jspx_page_context.findAttribute("messageReceivedData");
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
      out.write("</ul>\t\t\r\n\t\t</div>   \r\n\t\t</div>\r\n\t</div>\r\n\t<div class=\"Column50\">\r\n\t\t<div class=\"profilBlockRight\">\t\r\n\t\t<div id=\"message_envoyer\"> \r\n\t\t\t<h1>");
      out.print(DicoTools.dico(dico_lang, "profile/messages_sent"));
      out.write("</h1>\r\n\t\t\t<ul>");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_3 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_3.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_3.setParent(null);
      _jspx_th_logic_iterate_3.setName("myProfilForm");
      _jspx_th_logic_iterate_3.setProperty("sendMessages");
      _jspx_th_logic_iterate_3.setId("MessageSentData");
      int _jspx_eval_logic_iterate_3 = _jspx_th_logic_iterate_3.doStartTag();
      if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object MessageSentData = null;
        if (_jspx_eval_logic_iterate_3 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_3.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_3.doInitBody();
        }
        MessageSentData = (java.lang.Object) _jspx_page_context.findAttribute("MessageSentData");
        do {
          out.write("<li>\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_11.setName("MessageSentData");
          _jspx_th_bean_define_11.setProperty("id");
          _jspx_th_bean_define_11.setId("message_id");
          int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
          if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
          java.lang.Object message_id = null;
          message_id = (java.lang.Object) _jspx_page_context.findAttribute("message_id");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_12 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_12.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
          _jspx_th_bean_define_12.setName("MessageSentData");
          _jspx_th_bean_define_12.setProperty("userSenderId");
          _jspx_th_bean_define_12.setId("event_sender");
          int _jspx_eval_bean_define_12 = _jspx_th_bean_define_12.doStartTag();
          if (_jspx_th_bean_define_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
          java.lang.Object event_sender = null;
          event_sender = (java.lang.Object) _jspx_page_context.findAttribute("event_sender");
          out.write("\r\n\t\t\r\n\t\t\t\t\t");
          if (_jspx_meth_bean_write_10(_jspx_th_logic_iterate_3, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t\t<a href=\"#\">");
          if (_jspx_meth_bean_write_11(_jspx_th_logic_iterate_3, _jspx_page_context))
            return;
          out.write("</a>\r\n\t\t\t\t\t");
          if (_jspx_meth_bean_write_12(_jspx_th_logic_iterate_3, _jspx_page_context))
            return;
          out.write("x\r\n\r\n\t\t\t</li>");
          int evalDoAfterBody = _jspx_th_logic_iterate_3.doAfterBody();
          MessageSentData = (java.lang.Object) _jspx_page_context.findAttribute("MessageSentData");
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
      out.write("</ul>\r\n\t\t</div>  \r\n\t\t</div>\r\n\t</div>\r\n\t\r\n<script type=\"text/javascript\">\r\n\t// actif que quand loggué\r\n\tif ($(\"#_user_main\").length != 0) {\r\n\t\t$(\"#buttonAddFriend\").show();\r\n\t}\r\n</script>\t\t\r\n");
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

  private boolean _jspx_meth_bean_write_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_0 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_0.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterEqual_0);
    _jspx_th_bean_write_0.setName("userData");
    _jspx_th_bean_write_0.setProperty("age");
    int _jspx_eval_bean_write_0 = _jspx_th_bean_write_0.doStartTag();
    if (_jspx_th_bean_write_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
    return false;
  }

  private boolean _jspx_meth_bean_write_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_1 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_1.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_1.setParent(null);
    _jspx_th_bean_write_1.setName("userData");
    _jspx_th_bean_write_1.setProperty("annu_city");
    int _jspx_eval_bean_write_1 = _jspx_th_bean_write_1.doStartTag();
    if (_jspx_th_bean_write_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_1);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_1);
    return false;
  }

  private boolean _jspx_meth_bean_write_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_2 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_2.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_2.setParent(null);
    _jspx_th_bean_write_2.setName("paysdata");
    _jspx_th_bean_write_2.setProperty("pays_nom");
    int _jspx_eval_bean_write_2 = _jspx_th_bean_write_2.doStartTag();
    if (_jspx_th_bean_write_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_2);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_2);
    return false;
  }

  private boolean _jspx_meth_logic_equal_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_3);
    _jspx_th_logic_equal_4.setName("userData");
    _jspx_th_logic_equal_4.setProperty("user_image");
    _jspx_th_logic_equal_4.setValue("0");
    int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
    if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("../photo/default_B.jpg");
        int evalDoAfterBody = _jspx_th_logic_equal_4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_4);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_4);
    return false;
  }

  private boolean _jspx_meth_logic_equal_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_6 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_6.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_6.setParent(null);
    _jspx_th_logic_equal_6.setName("userData");
    _jspx_th_logic_equal_6.setProperty("user_good");
    _jspx_th_logic_equal_6.setValue("0");
    int _jspx_eval_logic_equal_6 = _jspx_th_logic_equal_6.doStartTag();
    if (_jspx_eval_logic_equal_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("../photo/default_B.jpg");
        int evalDoAfterBody = _jspx_th_logic_equal_6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_6);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_6);
    return false;
  }

  private boolean _jspx_meth_bean_write_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_3 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_3.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_3.setParent(null);
    _jspx_th_bean_write_3.setName("userData");
    _jspx_th_bean_write_3.setProperty("user_comment");
    int _jspx_eval_bean_write_3 = _jspx_th_bean_write_3.doStartTag();
    if (_jspx_th_bean_write_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_3);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_3);
    return false;
  }

  private boolean _jspx_meth_bean_write_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_4 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_4.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_bean_write_4.setName("musicData");
    _jspx_th_bean_write_4.setProperty("label");
    int _jspx_eval_bean_write_4 = _jspx_th_bean_write_4.doStartTag();
    if (_jspx_th_bean_write_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_4);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_4);
    return false;
  }

  private boolean _jspx_meth_bean_write_5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_5 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_5.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_bean_write_5.setName("nabcastData");
    _jspx_th_bean_write_5.setProperty("nabcast_title");
    int _jspx_eval_bean_write_5 = _jspx_th_bean_write_5.doStartTag();
    if (_jspx_th_bean_write_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_5);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_5);
    return false;
  }

  private boolean _jspx_meth_bean_write_6(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_6 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_6.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
    _jspx_th_bean_write_6.setName("messageReceivedData");
    _jspx_th_bean_write_6.setProperty("dateOfDelivery");
    int _jspx_eval_bean_write_6 = _jspx_th_bean_write_6.doStartTag();
    if (_jspx_th_bean_write_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_6);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_6);
    return false;
  }

  private boolean _jspx_meth_bean_write_7(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_7 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_7.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
    _jspx_th_bean_write_7.setName("messageReceivedData");
    _jspx_th_bean_write_7.setProperty("sender_name");
    int _jspx_eval_bean_write_7 = _jspx_th_bean_write_7.doStartTag();
    if (_jspx_th_bean_write_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_7);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_7);
    return false;
  }

  private boolean _jspx_meth_bean_write_8(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_8 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_8.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
    _jspx_th_bean_write_8.setName("messageReceivedData");
    _jspx_th_bean_write_8.setProperty("title");
    int _jspx_eval_bean_write_8 = _jspx_th_bean_write_8.doStartTag();
    if (_jspx_th_bean_write_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_8);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_8);
    return false;
  }

  private boolean _jspx_meth_bean_write_9(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_9 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_9.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_2);
    _jspx_th_bean_write_9.setName("messageReceivedData");
    _jspx_th_bean_write_9.setProperty("nbPlayed");
    int _jspx_eval_bean_write_9 = _jspx_th_bean_write_9.doStartTag();
    if (_jspx_th_bean_write_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_9);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_9);
    return false;
  }

  private boolean _jspx_meth_bean_write_10(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_10 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_10.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
    _jspx_th_bean_write_10.setName("MessageSentData");
    _jspx_th_bean_write_10.setProperty("dateOfDelivery");
    int _jspx_eval_bean_write_10 = _jspx_th_bean_write_10.doStartTag();
    if (_jspx_th_bean_write_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_10);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_10);
    return false;
  }

  private boolean _jspx_meth_bean_write_11(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_11 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_11.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
    _jspx_th_bean_write_11.setName("MessageSentData");
    _jspx_th_bean_write_11.setProperty("title");
    int _jspx_eval_bean_write_11 = _jspx_th_bean_write_11.doStartTag();
    if (_jspx_th_bean_write_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_11);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_11);
    return false;
  }

  private boolean _jspx_meth_bean_write_12(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_12 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_12.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_3);
    _jspx_th_bean_write_12.setName("MessageSentData");
    _jspx_th_bean_write_12.setProperty("nbPlayed");
    int _jspx_eval_bean_write_12 = _jspx_th_bean_write_12.doStartTag();
    if (_jspx_th_bean_write_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_12);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_12);
    return false;
  }
}
