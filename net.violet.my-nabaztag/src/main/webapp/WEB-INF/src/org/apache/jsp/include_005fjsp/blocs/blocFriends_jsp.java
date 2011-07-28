package org.apache.jsp.include_005fjsp.blocs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class blocFriends_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_empty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_rewrite_forward_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_logic_empty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_rewrite_forward_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_logic_empty_property_name.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_html_rewrite_forward_nobody.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
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

      out.write("\n\r\n\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\n\n");
	Lang dico_lang =	SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n<h1>");
      out.print(DicoTools.dico(dico_lang , "bloc/friends_title"));
      out.write("</h1>\r\n\r\n<div class=\"block-content blocListPeople\">\r\n<div class=\"inner\">\r\n\t");
/* Tu n'as pas d'amis */ 
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:empty
      org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_0 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
      _jspx_th_logic_empty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_empty_0.setParent(null);
      _jspx_th_logic_empty_0.setName("BlocForm");
      _jspx_th_logic_empty_0.setProperty("result");
      int _jspx_eval_logic_empty_0 = _jspx_th_logic_empty_0.doStartTag();
      if (_jspx_eval_logic_empty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t<p>");
          out.print(DicoTools.dico(dico_lang , "bloc/friends_no_friends_description"));
          out.write("&nbsp;<a onclick=\"TB_show('', 'myAnnuaire.do?height=515&width=800');\" href='javascript:;'>");
          out.print(DicoTools.dico(dico_lang , "bloc/friends_link_directory"));
          out.write("</a></p>\r\n\t");
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
      out.write("\r\n\t\r\n\t");
/* T'as pleins d'amis */ 
      out.write('\r');
      out.write('\n');
      out.write('	');
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("BlocForm");
      _jspx_th_logic_notEmpty_0.setProperty("result");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\r\n\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_logic_iterate_0.setName("BlocForm");
          _jspx_th_logic_iterate_0.setProperty("result");
          _jspx_th_logic_iterate_0.setId("userData");
          int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
          if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object userData = null;
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_0.doInitBody();
            }
            userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
            do {
              out.write("\n\t\t\t");
              //  logic:notEmpty
              org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_1 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
              _jspx_th_logic_notEmpty_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEmpty_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_notEmpty_1.setName("userData");
              _jspx_th_logic_notEmpty_1.setProperty("user_pseudo");
              int _jspx_eval_logic_notEmpty_1 = _jspx_th_logic_notEmpty_1.doStartTag();
              if (_jspx_eval_logic_notEmpty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_bean_define_0.setId("pseudo");
                  _jspx_th_bean_define_0.setName("userData");
                  _jspx_th_bean_define_0.setProperty("user_pseudo");
                  int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
                  if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
                  java.lang.Object pseudo = null;
                  pseudo = (java.lang.Object) _jspx_page_context.findAttribute("pseudo");
                  out.write("\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
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
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
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
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_bean_define_3.setId("user_main");
                  _jspx_th_bean_define_3.setName("userData");
                  _jspx_th_bean_define_3.setProperty("userWithAtLeastOneObject");
                  int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
                  if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
                  java.lang.Object user_main = null;
                  user_main = (java.lang.Object) _jspx_page_context.findAttribute("user_main");
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_bean_define_4.setId("user_good");
                  _jspx_th_bean_define_4.setName("userData");
                  _jspx_th_bean_define_4.setProperty("user_good");
                  int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
                  if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
                  java.lang.Object user_good = null;
                  user_good = (java.lang.Object) _jspx_page_context.findAttribute("user_good");
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_bean_define_5.setId("user_image");
                  _jspx_th_bean_define_5.setName("userData");
                  _jspx_th_bean_define_5.setProperty("user_image");
                  int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
                  if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
                  java.lang.Object user_image = null;
                  user_image = (java.lang.Object) _jspx_page_context.findAttribute("user_image");
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_bean_define_6.setId("city");
                  _jspx_th_bean_define_6.setName("userData");
                  _jspx_th_bean_define_6.setProperty("annu_city");
                  int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
                  if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
                  java.lang.Object city = null;
                  city = (java.lang.Object) _jspx_page_context.findAttribute("city");
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_bean_define_7.setId("nom_pays");
                  _jspx_th_bean_define_7.setName("paysdata");
                  _jspx_th_bean_define_7.setProperty("pays_nom");
                  int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
                  if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
                  java.lang.Object nom_pays = null;
                  nom_pays = (java.lang.Object) _jspx_page_context.findAttribute("nom_pays");
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_bean_define_8.setId("age");
                  _jspx_th_bean_define_8.setName("userData");
                  _jspx_th_bean_define_8.setProperty("age");
                  int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
                  if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                  java.lang.Object age = null;
                  age = (java.lang.Object) _jspx_page_context.findAttribute("age");
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_bean_define_9.setId("sexe");
                  _jspx_th_bean_define_9.setName("userData");
                  _jspx_th_bean_define_9.setProperty("annu_sexe");
                  int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
                  if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                  java.lang.Object sexe = null;
                  sexe = (java.lang.Object) _jspx_page_context.findAttribute("sexe");
                  out.write("\r\n\t\t\t\r\n\t\t\t");
 String sex_java = sexe.equals("H") ? "bloc/profile_man" : "bloc/profile_woman";
                  out.write("\r\n\t\t\t\r\n\t\t\t<ul class=\"entry-full\" style=\"display:none;\" >\t\t\r\n\t\t\t\t\r\n\t\t\t\t<li class=\"photo\">\r\n\t\t\t\t\t<a onclick=\"TB_show('");
                  out.print(DicoTools.dico(dico_lang , "bloc/friends_title_overlay"));
                  out.write("', '");
                  if (_jspx_meth_html_rewrite_0(_jspx_th_logic_notEmpty_1, _jspx_page_context))
                    return;
                  out.write("?height=515&width=800&user_id=");
                  out.print(user_id);
                  out.write("&myFriend=1')\" href=\"javascript:;\">\r\n\t\t\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_logic_equal_0.setName("userData");
                  _jspx_th_logic_equal_0.setProperty("user_good");
                  _jspx_th_logic_equal_0.setValue("1");
                  int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
                  if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t\t\t");
                      if (_jspx_meth_logic_equal_1(_jspx_th_logic_equal_0, _jspx_page_context))
                        return;
                      out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t");
                      //  logic:equal
                      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                      _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
                      _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
                      _jspx_th_logic_equal_2.setName("userData");
                      _jspx_th_logic_equal_2.setProperty("user_image");
                      _jspx_th_logic_equal_2.setValue("1");
                      int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
                      if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        do {
                          out.write("\r\n\t\t\t\t\t\t\t\t<img class=\"photo\" align=\"left\" src=\"../photo/");
                          out.print(user_id);
                          out.write("_S.jpg\" width=\"74\" height=\"74\" border=\"0\">\r\n\t\t\t\t\t\t\t");
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
                  out.write("\r\n\t\t\r\n\t\t\t\t\t\t");
                  if (_jspx_meth_logic_equal_3(_jspx_th_logic_notEmpty_1, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t</a>\r\n\t\t\t\t</li>\r\n\t\t\t\t\r\n\t\t\t\t<li class=\"pseudo\">");
                  out.print(pseudo);
                  out.write("</li>\r\n\t\t\t\t\r\n\t\t\t\t<li>\r\n\t\t\t\t\t");
                  out.print(DicoTools.dico(dico_lang , sex_java));
                  out.write("\r\n\t\t\t\t\t");
                  //  logic:notEqual
                  org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                  _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
                  _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_logic_notEqual_0.setName("userData");
                  _jspx_th_logic_notEqual_0.setProperty("age");
                  _jspx_th_logic_notEqual_0.setValue("0");
                  int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
                  if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t\t, ");
                      out.print(age);
                      out.write(' ');
                      out.print(DicoTools.dico(dico_lang , "bloc/profile_age"));
                      out.write("\r\n\t\t\t\t\t");
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
                  out.write("\r\n\t\t\t\t</li>\t\t\t\r\n\t\t\t\t\r\n\t\t\t\t<li class=\"location\"><strong>");
                  out.print(nom_pays);
                  out.write("</strong>, ");
                  out.print(city);
                  out.write("</li>\r\n\t\t\t\t\r\n\t\t\t\t");
                  //  logic:notEqual
                  org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                  _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
                  _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_logic_notEqual_1.setName("userData");
                  _jspx_th_logic_notEqual_1.setProperty("userWithAtLeastOneObject");
                  _jspx_th_logic_notEqual_1.setValue("0");
                  int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
                  if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t<li class=\"annuaire\" ><a href=\"javascript:;\" onclick=\"sendMsgTo('");
                      out.print(pseudo);
                      out.write("')\">");
                      out.print(DicoTools.dico(dico_lang , "myTerrier/friends_send_message"));
                      out.write("</a></li>\r\n\t\t\t\t");
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
                  out.write("\r\n\t\t\t\t");
                  if (_jspx_meth_logic_equal_4(_jspx_th_logic_notEmpty_1, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\r\n\t\t\t</ul>\r\n\t\t\t\r\n\t\t\t<ul class=\"entry\" >\r\n\t\t\t\t");
/* Link to send message, or not if the user doesn't have any rabbit */ 
                  out.write("\r\n\t\t\t\t");
                  //  logic:notEqual
                  org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                  _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
                  _jspx_th_logic_notEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_logic_notEqual_2.setName("userData");
                  _jspx_th_logic_notEqual_2.setProperty("userWithAtLeastOneObject");
                  _jspx_th_logic_notEqual_2.setValue("0");
                  int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
                  if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t<li class=\"pseudo\"><a class=\"tooltiped TTfriends\" title=\"");
                      out.print(DicoTools.dico(dico_lang , sex_java));
                      //  logic:notEqual
                      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_3 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                      _jspx_th_logic_notEqual_3.setPageContext(_jspx_page_context);
                      _jspx_th_logic_notEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_2);
                      _jspx_th_logic_notEqual_3.setName("userData");
                      _jspx_th_logic_notEqual_3.setProperty("age");
                      _jspx_th_logic_notEqual_3.setValue("0");
                      int _jspx_eval_logic_notEqual_3 = _jspx_th_logic_notEqual_3.doStartTag();
                      if (_jspx_eval_logic_notEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        do {
                          out.write("<br>");
                          out.print(age);
                          out.write(' ');
                          out.print(DicoTools.dico(dico_lang , "bloc/profile_age"));
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
                      out.write("<br>");
                      out.print(nom_pays);
                      out.write(',');
                      out.write(' ');
                      out.print(city);
                      out.write("\" href=\"javascript:;\" onclick=\"sendMsgTo('");
                      out.print(pseudo);
                      out.write("')\">");
                      out.print(pseudo);
                      out.write("</a></li>\r\n\t\t\t\t");
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
                  out.write("\r\n\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
                  _jspx_th_logic_equal_5.setName("userData");
                  _jspx_th_logic_equal_5.setProperty("userWithAtLeastOneObject");
                  _jspx_th_logic_equal_5.setValue("0");
                  int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
                  if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t<li class=\"pseudo\"><a class=\"tooltiped TTfriends\" title=\"");
                      out.print(DicoTools.dico(dico_lang , sex_java));
                      //  logic:notEqual
                      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_4 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
                      _jspx_th_logic_notEqual_4.setPageContext(_jspx_page_context);
                      _jspx_th_logic_notEqual_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_5);
                      _jspx_th_logic_notEqual_4.setName("userData");
                      _jspx_th_logic_notEqual_4.setProperty("age");
                      _jspx_th_logic_notEqual_4.setValue("0");
                      int _jspx_eval_logic_notEqual_4 = _jspx_th_logic_notEqual_4.doStartTag();
                      if (_jspx_eval_logic_notEqual_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                        do {
                          out.write("<br>");
                          out.print(age);
                          out.write(' ');
                          out.print(DicoTools.dico(dico_lang , "bloc/profile_age"));
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
                      out.write("<br>");
                      out.print(nom_pays);
                      out.write(',');
                      out.write(' ');
                      out.print(city);
                      out.write("\" href=\"javascript:;\">");
                      out.print(pseudo);
                      out.write("</a></li>\r\n\t\t\t\t");
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
                  out.write("\r\n\t\t\t</ul>\r\n\t\t\t\r\n\t\t\t");
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
              out.write("\r\n\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
              userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
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
          out.write("\r\n\t\t<hr class=\"spacer\" />\t\t\r\n\t");
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
      out.write("\r\n\t</div>\r\n</div>\t\r\n\r\n\r\n<script language=\"javascript\">\r\n$('a.TTfriends').Tooltip({\r\n\tdelay: 0,\r\n\ttrack: true,\r\n\tshowURL: false\r\n});\r\n </script>\r\n ");
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

  private boolean _jspx_meth_html_rewrite_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_rewrite_0 = (org.apache.struts.taglib.html.RewriteTag) _jspx_tagPool_html_rewrite_forward_nobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    _jspx_th_html_rewrite_0.setPageContext(_jspx_page_context);
    _jspx_th_html_rewrite_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
    _jspx_th_html_rewrite_0.setForward("goProfil");
    int _jspx_eval_html_rewrite_0 = _jspx_th_html_rewrite_0.doStartTag();
    if (_jspx_th_html_rewrite_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
    _jspx_th_logic_equal_1.setName("userData");
    _jspx_th_logic_equal_1.setProperty("user_image");
    _jspx_th_logic_equal_1.setValue("0");
    int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
    if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t\t\t<img class=\"photo\" align=\"left\" src=\"../photo/default_S.jpg\" width=\"74\" height=\"74\" border=\"0\">\r\n\t\t\t\t\t\t\t");
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

  private boolean _jspx_meth_logic_equal_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
    _jspx_th_logic_equal_3.setName("userData");
    _jspx_th_logic_equal_3.setProperty("user_good");
    _jspx_th_logic_equal_3.setValue("0");
    int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
    if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t\t\t<img class=\"photo\" align=\"left\" src=\"../photo/default_S.jpg\" width=\"74\" height=\"74\" border=\"0\">\r\n\t\t\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_3);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_3);
    return false;
  }

  private boolean _jspx_meth_logic_equal_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
    _jspx_th_logic_equal_4.setName("userData");
    _jspx_th_logic_equal_4.setProperty("userWithAtLeastOneObject");
    _jspx_th_logic_equal_4.setValue("0");
    int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
    if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t");
        out.write("\r\n\t\t\t\t");
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
}
