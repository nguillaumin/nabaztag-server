package org.apache.jsp.include_005fjsp.myNablife.myMails;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyMailsList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_rewrite_forward_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_button_value_styleClass_property_onclick_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_rewrite_forward_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_button_value_styleClass_property_onclick_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_html_rewrite_forward_nobody.release();
    _jspx_tagPool_logic_equal_value_name.release();
    _jspx_tagPool_logic_notEqual_value_name.release();
    _jspx_tagPool_html_button_value_styleClass_property_onclick_nobody.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
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

    java.lang.Object _jspx_mailKeywordData_1 = null;

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
      out.write("\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write('\n');
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myMailsListForm");
      _jspx_th_bean_define_0.setProperty("isReg");
      _jspx_th_bean_define_0.setId("isReg");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.Object isReg = null;
      isReg = (java.lang.Object) _jspx_page_context.findAttribute("isReg");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("myMailsListForm");
      _jspx_th_bean_define_1.setProperty("justList");
      _jspx_th_bean_define_1.setId("justList");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object justList = null;
      justList = (java.lang.Object) _jspx_page_context.findAttribute("justList");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setProperty("serviceName");
      _jspx_th_bean_define_2.setName("myMailsListForm");
      _jspx_th_bean_define_2.setId("serviceName");
      _jspx_th_bean_define_2.setType("String");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      String serviceName = null;
      serviceName = (String) _jspx_page_context.findAttribute("serviceName");
      out.write("\r\n\r\n\r\n");
/* très très moche cette condition.... */
      out.write('\r');
      out.write('\n');
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_0.setParent(null);
      _jspx_th_logic_notEqual_0.setName("myMailsListForm");
      _jspx_th_logic_notEqual_0.setProperty("justList");
      _jspx_th_logic_notEqual_0.setValue("true");
      int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
      if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n<div class='backLink' ><a href='javascript:;' onclick='srvConfigToggle()'>");
          out.print(DicoTools.dico(dico_lang, "srv_mail/back"));
          out.write("</a></div>\r\n<div class=\"tabSousNavContener-tr\">\r\n\r\n\t\t\t<div class=\"tabSousNavContener-bl\">\r\n\t\t\t\t<!-- ******************************************** CONTENT ***************************************************** -->   \r\n\r\n\t\t<div class=\"srv-main-config\" >\r\n\t\t\t<div class=\"title-srv\">\r\n\t\t\t\t<h1>");
          out.print(DicoTools.dico_if(dico_lang, serviceName));
          out.write("</h1>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<div id=\"description-srv\">\r\n\t\t\t<hr class=\"spacer\" />\r\n\r\n\t\t\t<p class=\"short-desc\">\r\n\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_mail_full/description"));
          out.write("\r\n\t\t\t</p>\r\n\t\t\t\r\n\t\t\t<p class=\"long-desc\">\r\n\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_mail_full/long_description"));
          out.write("\r\n\t\t\t</p>\r\n\t\t</div>\r\n\t\t\r\n\t\t<hr class=\"spacer\" />\t\t\t\r\n\t\t\r\n\t<div class=\"Column33\" style=\"width:44%; margin-right:8px;\" >\r\n\t\t<div class=\"srv-list-entries\" >\r\n\t\t\t<!-- ******************************** Affichage de la liste des comptes mail surveill�s  **************************** -->\r\n\t\t\t<h2>");
          out.print(DicoTools.dico(dico_lang, "srv_mail/account_list"));
          out.write("</h2>\r\n");
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
      out.write("\r\n\t<ul id=\"list-contener\">\r\n\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_0.setParent(null);
      _jspx_th_logic_iterate_0.setName("myMailsListForm");
      _jspx_th_logic_iterate_0.setProperty("mailsAccounts");
      _jspx_th_logic_iterate_0.setId("mailUserData");
      int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
      if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object mailUserData = null;
        if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_0.doInitBody();
        }
        mailUserData = (java.lang.Object) _jspx_page_context.findAttribute("mailUserData");
        do {
          out.write("\r\n\t\t\t\r\n\t\t\t\t<!-- Les informations du compte -->\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_3.setId("identifiant");
          _jspx_th_bean_define_3.setName("mailUserData");
          _jspx_th_bean_define_3.setProperty("identifiant");
          int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
          if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
          java.lang.Object identifiant = null;
          identifiant = (java.lang.Object) _jspx_page_context.findAttribute("identifiant");
          out.write("\t\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_4.setId("accountLogin");
          _jspx_th_bean_define_4.setName("mailUserData");
          _jspx_th_bean_define_4.setProperty("mailUser_login");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object accountLogin = null;
          accountLogin = (java.lang.Object) _jspx_page_context.findAttribute("accountLogin");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_5.setId("accountPassive");
          _jspx_th_bean_define_5.setName("mailUserData");
          _jspx_th_bean_define_5.setProperty("mailUser_passive");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object accountPassive = null;
          accountPassive = (java.lang.Object) _jspx_page_context.findAttribute("accountPassive");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_6.setId("accountNbr");
          _jspx_th_bean_define_6.setName("mailUserData");
          _jspx_th_bean_define_6.setProperty("mailUser_nbr");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object accountNbr = null;
          accountNbr = (java.lang.Object) _jspx_page_context.findAttribute("accountNbr");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_7.setId("accountSrv_src");
          _jspx_th_bean_define_7.setName("mailUserData");
          _jspx_th_bean_define_7.setProperty("mailUser_srvSrc");
          int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
          if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
          java.lang.Object accountSrv_src = null;
          accountSrv_src = (java.lang.Object) _jspx_page_context.findAttribute("accountSrv_src");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_8.setId("accountHost");
          _jspx_th_bean_define_8.setName("mailUserData");
          _jspx_th_bean_define_8.setProperty("mailUser_host");
          int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
          if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
          java.lang.Object accountHost = null;
          accountHost = (java.lang.Object) _jspx_page_context.findAttribute("accountHost");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_9.setId("accountType");
          _jspx_th_bean_define_9.setName("mailUserData");
          _jspx_th_bean_define_9.setProperty("mailUser_type");
          int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
          if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
          java.lang.Object accountType = null;
          accountType = (java.lang.Object) _jspx_page_context.findAttribute("accountType");
          out.write("\r\n\t\r\n\t\t\t\t<li id=\"srvItem_");
          out.print(identifiant);
          out.write("\" >\r\n\t\t\t\t\t<ul class=\"ico-list\" >\r\n\t\t\t\t\t\t<li class=\"supprimer\">\r\n\t\t\t\t\t\t\t<!-- <a href=\"");
          if (_jspx_meth_html_rewrite_0(_jspx_th_logic_iterate_0, _jspx_page_context))
            return;
          out.write("?srv_src=");
          out.print(accountSrv_src);
          out.write("&delete=1\">supprimer facon Jerem</a> -->\r\n\t\t\t\t\t\t\t<a class=\"link-delete suppr\" onclick=\"srvDelete(");
          out.print(identifiant);
          out.write(',');
          out.write(' ');
          out.write('\'');
          out.print(accountSrv_src);
          out.write("')\" href='javascript:;'><span>");
          out.print(DicoTools.dico(dico_lang, "srv_mail/account_delete"));
          out.write("</span></a>\r\n\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
/* pour update setter queFaire a : update_account et pr�remplir le formulaire */
          out.write("\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t\t<ul class=\"txt-list\">\r\n\t\t\t\t\t\t<li class=\"nom\"><strong>");
          out.print(accountLogin );
          out.write("<br />");
          out.print(accountHost);
          out.write("</strong><!--login pour le compte mail--></li>\r\n\t\t\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_equal_0.setName("accountPassive");
          _jspx_th_logic_equal_0.setValue("1");
          int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
          if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t<li>");
              out.print(DicoTools.dico(dico_lang , "srv_mail/pending"));
              out.write(' ');
              out.write(':');
              out.write(' ');
              out.print(accountNbr );
              out.write(" <!--nbr de mails nouveaux depuis la derniere verifcation--></li>\r\n\t\t\t\t\t\t");
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
          out.write("\r\n\t\t\t\t\t\t<li class=\"keywords\">\r\n\t\t\t\t\t\t<!-- Les informations du compte -->\r\n\t\t\t\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_iterate_1.setId("mailKeywordData");
          _jspx_th_logic_iterate_1.setName("mailUserData");
          _jspx_th_logic_iterate_1.setProperty("keywords");
          int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
          if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object mailKeywordData = null;
            if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_1.doInitBody();
            }
            mailKeywordData = (java.lang.Object) _jspx_page_context.findAttribute("mailKeywordData");
            do {
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_10.setId("keywordSrv");
              _jspx_th_bean_define_10.setName("mailKeywordData");
              _jspx_th_bean_define_10.setProperty("mailKeyword_srvSrc");
              int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
              if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
              java.lang.Object keywordSrv = null;
              keywordSrv = (java.lang.Object) _jspx_page_context.findAttribute("keywordSrv");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_11.setId("keywordWord");
              _jspx_th_bean_define_11.setName("mailKeywordData");
              _jspx_th_bean_define_11.setProperty("mailKeyword_keyword");
              int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
              if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
              java.lang.Object keywordWord = null;
              keywordWord = (java.lang.Object) _jspx_page_context.findAttribute("keywordWord");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_12 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_12.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_12.setId("keywordPassive");
              _jspx_th_bean_define_12.setName("mailKeywordData");
              _jspx_th_bean_define_12.setProperty("mailKeyword_passive");
              int _jspx_eval_bean_define_12 = _jspx_th_bean_define_12.doStartTag();
              if (_jspx_th_bean_define_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
              java.lang.Object keywordPassive = null;
              keywordPassive = (java.lang.Object) _jspx_page_context.findAttribute("keywordPassive");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_13 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_13.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_bean_define_13.setId("keywordMusicName");
              _jspx_th_bean_define_13.setName("mailKeywordData");
              _jspx_th_bean_define_13.setProperty("mailKeyword_musicName");
              int _jspx_eval_bean_define_13 = _jspx_th_bean_define_13.doStartTag();
              if (_jspx_th_bean_define_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
              java.lang.Object keywordMusicName = null;
              keywordMusicName = (java.lang.Object) _jspx_page_context.findAttribute("keywordMusicName");
              out.write("\r\n\t\t\t\t\t\t\t<!-- <li> keywordSrv : ");
              out.print(keywordSrv);
              out.write("<br /> </li> -->\t\t\t\r\n\t\t\t\t\t\t\t<!-- keywordPassive : <li>");
              out.print(keywordPassive);
              out.write("<br /></li>-->\r\n\t\t\t\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t\t\t\t<li>\r\n\t\t\t\t\t\t\t\t\t\t");
 /* flash lumineux activé */ 
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_logic_equal_1.setName("keywordPassive");
              _jspx_th_logic_equal_1.setValue("1");
              int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
              if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:;\" class=\"lumi-on\" ><span>");
                  out.print(accountPassive);
                  out.write("</span></a>\r\n\t\t\t\t\t\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t");
 /* flash lumineux desactivé */ 
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
              _jspx_th_logic_equal_2.setName("keywordPassive");
              _jspx_th_logic_equal_2.setValue("0");
              int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
              if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:;\" class=\"lumi-off\" ><span>");
                  out.print(accountPassive);
                  out.write("</span></a>\r\n\t\t\t\t\t\t\t\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_equal_2.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_equal_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_2);
                return;
              }
              _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_2);
              out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t\t<strong>");
              out.print(DicoTools.dico(dico_lang, "srv_mail/keyword"));
              out.write("</strong> ");
              out.print(keywordWord);
              out.write("\r\n\t\t\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\t\t\t<li><!-- <strong>keywordMusicName :</strong> </li>-->");
              out.print(keywordMusicName);
              out.write("</li>\r\n\t\t\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
              mailKeywordData = (java.lang.Object) _jspx_page_context.findAttribute("mailKeywordData");
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
          out.write("\t\r\n\t\t\t\t\t\t</li>\t\t\t\t\t\r\n\t\t\t\t\t\t<li class=\"modifier\">\r\n\t\t\t\t\t\t\t<a class=\"link-modify\" href=\"");
          if (_jspx_meth_html_rewrite_1(_jspx_th_logic_iterate_0, _jspx_page_context))
            return;
          out.write("?srv_src=");
          out.print(accountSrv_src);
          out.write('"');
          out.write('>');
          out.print(DicoTools.dico(dico_lang, "srv_mail/button_modify"));
          out.write("</a>\r\n\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t</ul>\r\n\t\t\t\t</li>\r\n\t\t");
          int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
          mailUserData = (java.lang.Object) _jspx_page_context.findAttribute("mailUserData");
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
      out.write("\r\n\t\t<li style=\"border:none; text-align:center;\">\r\n\r\n\t\t\t\t");
/* Supression */
      out.write("\t\t\r\n\t\t\t\t");
      //  logic:notEqual
      org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
      _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEqual_1.setParent(null);
      _jspx_th_logic_notEqual_1.setName("isReg");
      _jspx_th_logic_notEqual_1.setValue("0");
      int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
      if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t");
          //  html:button
          org.apache.struts.taglib.html.ButtonTag _jspx_th_html_button_0 = (org.apache.struts.taglib.html.ButtonTag) _jspx_tagPool_html_button_value_styleClass_property_onclick_nobody.get(org.apache.struts.taglib.html.ButtonTag.class);
          _jspx_th_html_button_0.setPageContext(_jspx_page_context);
          _jspx_th_html_button_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
          _jspx_th_html_button_0.setValue(DicoTools.dico(dico_lang, "srv_mail/deactivate"));
          _jspx_th_html_button_0.setProperty("del");
          _jspx_th_html_button_0.setStyleClass("genericDeleteBt");
          _jspx_th_html_button_0.setOnclick("srvDelete()");
          int _jspx_eval_html_button_0 = _jspx_th_html_button_0.doStartTag();
          if (_jspx_th_html_button_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_button_value_styleClass_property_onclick_nobody.reuse(_jspx_th_html_button_0);
            return;
          }
          _jspx_tagPool_html_button_value_styleClass_property_onclick_nobody.reuse(_jspx_th_html_button_0);
          out.write("\r\n\t\t\t\t");
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
      out.write("\r\n\r\n\t\t\t\t");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("myMailsListForm");
      _jspx_th_logic_notEmpty_0.setProperty("mailsAccounts");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t\t<a class=\"link-modify\" href='myMailsCreate.do'><input class=\"genericBt\" type=\"button\" value=\"");
          out.print(DicoTools.dico(dico_lang, "srv_mail/add"));
          out.write("\"/></a>\r\n\t\t\t\t");
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
      out.write("\r\n\r\n\t\t</li>\r\n\r\n");
/* très très moche cette condition.... */
      out.write("\r\n</ul>\r\n\r\n");
      if (_jspx_meth_logic_notEqual_2(_jspx_page_context))
        return;
      out.write("\r\n\t\r\n<script type=\"text/javascript\">\r\n\t");
      if (_jspx_meth_logic_notEqual_3(_jspx_page_context))
        return;
      out.write("\r\n\t\r\n\t/* liens de modifs */\r\n\t$(\"a.link-modify\").each(function(){\r\n\t\tvar url = $(this).attr(\"href\");\r\n\t\t\r\n\t\tif ( $(this).attr(\"href\") != \"javascript:;\" ) {\r\n\t\t\t\r\n\t\t\t$(this).attr(\"href\", \"javascript:;\");\r\n\t\t\t$(this).click(function(){\r\n\t\t\t\tdivChangeUrlBackground(\"config\", url);\r\n\t\t\t});\r\n\t\t\t\r\n\t\t}\r\n\t\t\r\n\t\t\r\n\t});\r\n\tif ( $(\"#prev_srv-mail-n0\").length < 1) {\r\n\t\taddPreview(\"srv-mail-n0\", \t\t'");
      out.print(DicoTools.dico(dico_lang , "srv_mail/no_mail"));
      out.write("' );\r\n\t\taddPreview(\"srv-mail-n1\", \t\t'");
      out.print(DicoTools.dico(dico_lang , "srv_mail/one_mail"));
      out.write("' );\r\n\t\taddPreview(\"srv-mail-n2\", \t\t'");
      out.print(DicoTools.dico(dico_lang , "srv_mail/two_mail"));
      out.write("' );\t\t\r\n\t\taddPreview(\"srv-mail-n3\", \t\t'");
      out.print(DicoTools.dico(dico_lang , "srv_mail/threemore_mail"));
      out.write("' );\r\n\t}\t\r\n</script>");
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
    _jspx_th_html_rewrite_0.setForward("goMailsCreate");
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
    _jspx_th_html_rewrite_1.setForward("goMailsCreate");
    int _jspx_eval_html_rewrite_1 = _jspx_th_html_rewrite_1.doStartTag();
    if (_jspx_th_html_rewrite_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_1);
      return true;
    }
    _jspx_tagPool_html_rewrite_forward_nobody.reuse(_jspx_th_html_rewrite_1);
    return false;
  }

  private boolean _jspx_meth_logic_notEqual_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_2.setParent(null);
    _jspx_th_logic_notEqual_2.setName("myMailsListForm");
    _jspx_th_logic_notEqual_2.setProperty("justList");
    _jspx_th_logic_notEqual_2.setValue("true");
    int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
    if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t<!-- ************************************************************************************************ -->\r\n\t\t</div>\r\n\t</div>\r\n\t<div class=\"srv-config Column50\" id=\"config\" style=\"float:left; min-height:100px;\">\r\n\t&nbsp;\r\n\t</div>\r\n\t\t\t<hr class=\"spacer\" />\r\n\r\n\t\t\t<div id=\"keskidit\" class=\"keskidit\">\r\n\t\t\t\t<div class=\"inner\" ></div>\r\n\t\t\t</div>\t\r\n\t\t\t\t<!-- ******************************************** /CONTENT ***************************************************** -->   \t\t\t\t\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\t</div>\r\n\r\n</div>\r\n\t\r\n");
        int evalDoAfterBody = _jspx_th_logic_notEqual_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_2);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_2);
    return false;
  }

  private boolean _jspx_meth_logic_notEqual_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_3 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_3.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_3.setParent(null);
    _jspx_th_logic_notEqual_3.setName("myMailsListForm");
    _jspx_th_logic_notEqual_3.setProperty("justList");
    _jspx_th_logic_notEqual_3.setValue("true");
    int _jspx_eval_logic_notEqual_3 = _jspx_th_logic_notEqual_3.doStartTag();
    if (_jspx_eval_logic_notEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t// on charge le formulaire d'ajout de compte\r\n\t\tdivChangeUrl(\"config\", \"myMailsCreate.do\");\r\n\t");
        int evalDoAfterBody = _jspx_th_logic_notEqual_3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_3);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_3);
    return false;
  }
}
