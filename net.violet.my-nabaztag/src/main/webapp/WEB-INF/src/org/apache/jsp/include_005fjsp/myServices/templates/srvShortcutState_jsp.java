package org.apache.jsp.include_005fjsp.myServices.templates;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.lang.String;
import java.util.Map;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class srvShortcutState_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_value_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_empty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_lessEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_write_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_text_styleId_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_password_styleId_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_value_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_empty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_lessEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_write_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_text_styleId_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_password_styleId_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_value_id_nobody.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_logic_empty_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_html_form_styleId_action.release();
    _jspx_tagPool_logic_lessEqual_value_name.release();
    _jspx_tagPool_logic_notEmpty_name.release();
    _jspx_tagPool_logic_notEqual_value_name.release();
    _jspx_tagPool_bean_write_name_nobody.release();
    _jspx_tagPool_logic_greaterThan_value_name.release();
    _jspx_tagPool_html_text_styleId_property_name_nobody.release();
    _jspx_tagPool_html_password_styleId_property_name_nobody.release();
    _jspx_tagPool_logic_equal_value_name.release();
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

      out.write("\r\n\r\n\r\n\r\n \r\n\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\t\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n");
 Lang dico_lang =	SessionTools.getLangFromSession(session, request);
      out.write('\r');
      out.write('\n');
 String user_main = Long.toString(SessionTools.getRabbitIdFromSession(session));
      out.write('\r');
      out.write('\n');

    final User theUser = SessionTools.getUserFromSession(request);
    final String user_id;
    if (theUser == null) {
       user_id = "0";
    } else {
       user_id = theUser.getId().toString();
    }

      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setId("search");
      _jspx_th_bean_define_0.setValue( (request.getSession().getAttribute("search")==null) ? "" : (String)request.getSession().getAttribute("search") );
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_0);
      java.lang.String search = null;
      search = (java.lang.String) _jspx_page_context.findAttribute("search");
      out.write("\t \r\n");
 String srvNameGlobal = ""; 
      out.write('\n');
 String srvShortCut = ""; 
      out.write('\n');
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("myNablifeForm");
      _jspx_th_bean_define_1.setProperty("badLogin");
      _jspx_th_bean_define_1.setId("badLogin");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object badLogin = null;
      badLogin = (java.lang.Object) _jspx_page_context.findAttribute("badLogin");
      out.write("\r\n\r\n\r\n");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("myNablifeForm");
      _jspx_th_logic_notEmpty_0.setProperty("srvListData");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_bean_define_2.setName("myNablifeForm");
          _jspx_th_bean_define_2.setProperty("srvListData");
          _jspx_th_bean_define_2.setId("nablifeServicesData");
          int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
          if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
          java.lang.Object nablifeServicesData = null;
          nablifeServicesData = (java.lang.Object) _jspx_page_context.findAttribute("nablifeServicesData");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_bean_define_3.setName("nablifeServicesData");
          _jspx_th_bean_define_3.setProperty("desc");
          _jspx_th_bean_define_3.setId("srvDesc");
          int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
          if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
          java.lang.Object srvDesc = null;
          srvDesc = (java.lang.Object) _jspx_page_context.findAttribute("srvDesc");
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_bean_define_4.setName("nablifeServicesData");
          _jspx_th_bean_define_4.setProperty("name");
          _jspx_th_bean_define_4.setId("srvName");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object srvName = null;
          srvName = (java.lang.Object) _jspx_page_context.findAttribute("srvName");
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_bean_define_5.setName("nablifeServicesData");
          _jspx_th_bean_define_5.setProperty("link");
          _jspx_th_bean_define_5.setId("srvLink");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object srvLink = null;
          srvLink = (java.lang.Object) _jspx_page_context.findAttribute("srvLink");
          out.write('\r');
          out.write('\n');
          out.write('	');
 srvNameGlobal = DicoTools.dico_if(dico_lang, srvName.toString()); 
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_bean_define_6.setName("nablifeServicesData");
          _jspx_th_bean_define_6.setProperty("srvImg");
          _jspx_th_bean_define_6.setId("srvImg");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object srvImg = null;
          srvImg = (java.lang.Object) _jspx_page_context.findAttribute("srvImg");
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_bean_define_7.setName("nablifeServicesData");
          _jspx_th_bean_define_7.setProperty("srvShortCut");
          _jspx_th_bean_define_7.setId("srvShortCutValue");
          int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
          if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
          java.lang.Object srvShortCutValue = null;
          srvShortCutValue = (java.lang.Object) _jspx_page_context.findAttribute("srvShortCutValue");
          out.write('\n');
          out.write('	');
 srvShortCut = "/action/myNablife.do?service="+srvShortCutValue.toString();
          out.write('\n');
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
      out.write("\r\n\r\n");
      //  logic:empty
      org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_0 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
      _jspx_th_logic_empty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_empty_0.setParent(null);
      _jspx_th_logic_empty_0.setName("myNablifeForm");
      _jspx_th_logic_empty_0.setProperty("srvListData");
      int _jspx_eval_logic_empty_0 = _jspx_th_logic_empty_0.doStartTag();
      if (_jspx_eval_logic_empty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\r');
          out.write('\n');
          out.write('	');
          //  logic:notEmpty
          org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_1 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
          _jspx_th_logic_notEmpty_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEmpty_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_empty_0);
          _jspx_th_logic_notEmpty_1.setName("myNablifeForm");
          _jspx_th_logic_notEmpty_1.setProperty("nabcastData");
          int _jspx_eval_logic_notEmpty_1 = _jspx_th_logic_notEmpty_1.doStartTag();
          if (_jspx_eval_logic_notEmpty_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t");
              //  logic:iterate
              org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
              _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_1);
              _jspx_th_logic_iterate_0.setId("nabcastData");
              _jspx_th_logic_iterate_0.setName("myNablifeForm");
              _jspx_th_logic_iterate_0.setProperty("nabcastData");
              int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
              if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                java.lang.Object nabcastData = null;
                if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
                  out = _jspx_page_context.pushBody();
                  _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
                  _jspx_th_logic_iterate_0.doInitBody();
                }
                nabcastData = (java.lang.Object) _jspx_page_context.findAttribute("nabcastData");
                do {
                  out.write("\r\n\t\t\t");
                  out.write("\r\n\t\t\t");
 srvNameGlobal = ((NabcastData)nabcastData).getNabcast_titre(); 
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_bean_define_8.setName("nabcastData");
                  _jspx_th_bean_define_8.setProperty("nabcast_description");
                  _jspx_th_bean_define_8.setId("srvDesc");
                  int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
                  if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
                  java.lang.Object srvDesc = null;
                  srvDesc = (java.lang.Object) _jspx_page_context.findAttribute("srvDesc");
                  out.write("\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_bean_define_9.setName("nabcastData");
                  _jspx_th_bean_define_9.setProperty("nabcast_shortcut");
                  _jspx_th_bean_define_9.setId("nabcastValue");
                  int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
                  if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                    return;
                  }
                  _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
                  java.lang.Object nabcastValue = null;
                  nabcastValue = (java.lang.Object) _jspx_page_context.findAttribute("nabcastValue");
                  out.write("\r\n\t\t\t");
                  //  bean:define
                  org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
                  _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
                  _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
                  _jspx_th_bean_define_10.setId("srvImg");
                  _jspx_th_bean_define_10.setValue("");
                  int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
                  if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                    _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_10);
                    return;
                  }
                  _jspx_tagPool_bean_define_value_id_nobody.reuse(_jspx_th_bean_define_10);
                  java.lang.String srvImg = null;
                  srvImg = (java.lang.String) _jspx_page_context.findAttribute("srvImg");
                  out.write("\t\t\t\n\t\t\t");
 srvShortCut = "/action/myNablife.do?nabcast="+nabcastValue.toString();
                  out.write("\r\n\t\t");
                  int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
                  nabcastData = (java.lang.Object) _jspx_page_context.findAttribute("nabcastData");
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
              out.write('\r');
              out.write('\n');
              out.write('	');
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
      out.write("\r\n\t\r\n\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_action.get(org.apache.struts.taglib.html.FormTag.class);
      _jspx_th_html_form_0.setPageContext(_jspx_page_context);
      _jspx_th_html_form_0.setParent(null);
      _jspx_th_html_form_0.setAction("/action/mySession");
      _jspx_th_html_form_0.setStyleId("shortcut_login_form");
      int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
      if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\r\n\t");
          //  logic:lessEqual
          org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_0 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
          _jspx_th_logic_lessEqual_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_lessEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_lessEqual_0.setName("user_main");
          _jspx_th_logic_lessEqual_0.setValue("0");
          int _jspx_eval_logic_lessEqual_0 = _jspx_th_logic_lessEqual_0.doStartTag();
          if (_jspx_eval_logic_lessEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\t\t\t\t\t\r\n\t\t");
              //  logic:notEmpty
              org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_2 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
              _jspx_th_logic_notEmpty_2.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEmpty_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_0);
              _jspx_th_logic_notEmpty_2.setName("search");
              int _jspx_eval_logic_notEmpty_2 = _jspx_th_logic_notEmpty_2.doStartTag();
              if (_jspx_eval_logic_notEmpty_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t<input type=\"hidden\" name=\"redirectUrl\" value=\"/action/myNablife.do?search=");
                  out.print(search);
                  out.write("\" />\r\n\t\t\t<script language=\"javascript\">\r\n\t\t\t\t$(\"input[@name=redirectUrl]\").attr(\"value\",\"/action/myNablife.do?search=");
                  out.print(search);
                  out.write("\");\r\n\t\t\t</script>\r\n\t\t");
                  int evalDoAfterBody = _jspx_th_logic_notEmpty_2.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_notEmpty_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_notEmpty_name.reuse(_jspx_th_logic_notEmpty_2);
                return;
              }
              _jspx_tagPool_logic_notEmpty_name.reuse(_jspx_th_logic_notEmpty_2);
              out.write('\r');
              out.write('\n');
              out.write('	');
              int evalDoAfterBody = _jspx_th_logic_lessEqual_0.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_lessEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_0);
            return;
          }
          _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_0);
          out.write("\r\n\r\n\t<div class=\"thirdCol\" style=\"border:1px solid white\">\r\n\t\t\r\n\t\t");
          out.write("\r\n\t\t<div id=\"rabbitFlash\" style=\"position:absolute; right:-30px; z-index:250; \">\r\n\t\t\t<img src=\"../include_img/template/visuel_lapin.gif\"/>\r\n\t\t</div>\t\t\r\n\t\t\r\n\t</div>\r\n\r\n\t");
          out.write("\r\n\r\n\t<div class=\"twoThirdCol\" style=\"border:1px solid white float:left;\">\r\n\t\r\n\t\t<div class=\"partner-content\">\r\n\t\t\t");
          if (_jspx_meth_logic_notEqual_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t<h1>");
          out.print(DicoTools.dico(dico_lang , "srv_Shortcut/want_to_listen1"));
          out.write(' ');
          out.print(srvNameGlobal);
          out.write(' ');
          out.print(DicoTools.dico(dico_lang , "srv_Shortcut/want_to_listen2"));
          out.write("</h1>\r\n\t\t\t<hr class=\"clearer\" />\r\n\t\t</div>\r\n\t\t\r\n\t\t\r\n\t\t<!-- patch pour les users sans lapin -->\r\n\t\t");
          //  logic:greaterThan
          org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
          _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_greaterThan_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_greaterThan_0.setName("user_id");
          _jspx_th_logic_greaterThan_0.setValue("0");
          int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
          if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t");
              //  logic:lessEqual
              org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_1 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
              _jspx_th_logic_lessEqual_1.setPageContext(_jspx_page_context);
              _jspx_th_logic_lessEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
              _jspx_th_logic_lessEqual_1.setName("user_main");
              _jspx_th_logic_lessEqual_1.setValue("0");
              int _jspx_eval_logic_lessEqual_1 = _jspx_th_logic_lessEqual_1.doStartTag();
              if (_jspx_eval_logic_lessEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\r\n\t\t\t<div class=\"main-cadre-contener\" id=\"shortcut-norabbit\">\r\n\t\t\t\t<div class=\"main-cadre-top\">\r\n\t\t\t\t\t<h2>\r\n\t\t\t\t\t\t");
                  out.print(DicoTools.dico(dico_lang , "srv_Shortcut/norabbit_teaser"));
                  out.write("\r\n\t\t\t\t\t</h2>\r\n\t\t\t\t</div>\r\n\t\t\t\r\n\t\t\t\t<div class=\"main-cadre-content\">\r\n\t\t\t\t\t");
                  out.write("\r\n\t\t\t\t\t<p>");
                  out.print(DicoTools.insertBR(dico_lang , "srv_Shortcut/norabbit_text", srvNameGlobal));
                  out.write("</p>\r\n\t\t\t\t\t<p><a href=\"http://www.nabaztag.com\">");
                  out.print(DicoTools.dico(dico_lang , "srv_Shortcut/norabbit_discover"));
                  out.write("</a></p>\r\n\t\t\t\t\t");
                  out.write("\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t\t");
                  int evalDoAfterBody = _jspx_th_logic_lessEqual_1.doAfterBody();
                  if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                    break;
                } while (true);
              }
              if (_jspx_th_logic_lessEqual_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_1);
                return;
              }
              _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_1);
              out.write("\r\n\t\t");
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
          out.write("\r\n\t\t\r\n\t\t\r\n\t\t");
          //  logic:lessEqual
          org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_2 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
          _jspx_th_logic_lessEqual_2.setPageContext(_jspx_page_context);
          _jspx_th_logic_lessEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_lessEqual_2.setName("user_id");
          _jspx_th_logic_lessEqual_2.setValue("0");
          int _jspx_eval_logic_lessEqual_2 = _jspx_th_logic_lessEqual_2.doStartTag();
          if (_jspx_eval_logic_lessEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\r\n\r\n\r\n\r\n\r\n\t\t\t<div class=\"main-cadre-contener\" id=\"shortcut-what-is-nabaztag\">\r\n\t\t\t\t<div class=\"main-cadre-top\">\r\n\t\t\t\t\t<h2>\r\n\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "srv_Shortcut/nabaztag_teaser"));
              out.write("\r\n\t\t\t\t\t</h2>\r\n\t\t\t\t</div>\r\n\t\t\t\r\n\t\t\t\t<div class=\"main-cadre-content\">\r\n\t\t\t\t\t");
              out.write("\r\n\t\t\t\t\t\r\n\t\t\t\t\t<p>");
              out.print(DicoTools.insertBR(dico_lang , "srv_Shortcut/nabaztag_teaser_text", srvNameGlobal));
              out.write("</p>\r\n\t\t\t\t\t<p><a href=\"http://www.nabaztag.com\">");
              out.print(DicoTools.dico(dico_lang , "srv_Shortcut/discover_nabaztag"));
              out.write("</a></p>\t\r\n\t\t\t\t\t");
              out.write("\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t\t\r\n\t\t\t\r\n\t\t\t<div class=\"flat-block\"  > \r\n\t\t\t   \r\n\t\t\t\t<div class=\"flat-block-top\" >\r\n\t\t\t\t\t<h3 class=\"no-icone\">\r\n\t\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "srv_Shortcut/have_rabbit_connect"));
              out.write("\r\n\t\t\t\t\t</h3>\r\n\t\t\t\t</div>\r\n\t\t\t\r\n\t\t\t\t<div class=\"flat-block-content\" >\r\n\t\t\t\t\t<div class=\"flat-block-content-inner\" >\r\n\t\t\t\t\t\t");
              out.write("\r\n\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_2);
              _jspx_th_bean_define_11.setId("pseudo");
              _jspx_th_bean_define_11.setName("mySessionForm");
              _jspx_th_bean_define_11.setProperty("pseudo");
              int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
              if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
              java.lang.Object pseudo = null;
              pseudo = (java.lang.Object) _jspx_page_context.findAttribute("pseudo");
              out.write("\r\n\t\t\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_12 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_12.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_2);
              _jspx_th_bean_define_12.setId("password");
              _jspx_th_bean_define_12.setName("mySessionForm");
              _jspx_th_bean_define_12.setProperty("password");
              int _jspx_eval_bean_define_12 = _jspx_th_bean_define_12.doStartTag();
              if (_jspx_th_bean_define_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_12);
              java.lang.Object password = null;
              password = (java.lang.Object) _jspx_page_context.findAttribute("password");
              out.write("\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"action\" value=\"connect\" />\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"redirectUrl\" value=\"");
              out.print(srvShortCut);
              out.write("\" />\r\n\t\t\r\n\t\t\r\n\t\t\t\t\t\t\t<div class=\"twoCol-left\" style=\"width:66%\">\r\n\t\t\t\t\t\t\t\t<label style=\"width:100%; text-align:right;\">\r\n\t\t\t\t\t\t\t\t\t");
              out.print( DicoTools.dico(dico_lang , "srv_Shortcut/nabname"));
              out.write("\r\n\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_html_text_0(_jspx_th_logic_lessEqual_2, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t\t\t\t</label>\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t<label style=\"width:100%; text-align:right;\">\r\n\t\t\t\t\t\t\t\t\t");
              out.print( DicoTools.dico(dico_lang , "srv_Shortcut/password"));
              out.write("\r\n\t\t\t\t\t\t\t\t\t");
              if (_jspx_meth_html_password_0(_jspx_th_logic_lessEqual_2, _jspx_page_context))
                return;
              out.write("\r\n\t\t\t\t\t\t\t\t</label>\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t<div class=\"twoCol-right\" style=\"width:32%\">\r\n\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t<div class=\"generic-button\" style=\"float: left; width: 60px; text-align: center; margin-top: 13px; margin-left: 29px;\" >\r\n\t\t\t\t\t\t\t        <div>\r\n\t\t\t\t\t\t\t            <div>\r\n\t\t\t\t\t\t\t            \t<a class=\"form-submit\" href='#' onclick=\"$(this).parents('form').submit();\">OK</a>\r\n\t\t\t\t\t\t\t            </div>\r\n\t\t\t\t\t\t\t        </div>\r\n\t\t\t\t\t\t\t    </div>\r\n\t\t\r\n\t\t\t\t\t\t\t</div>\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
              out.write("\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\t\t\t\r\n\t\t\t\t\r\n");
              out.write("\t\t\t\r\n\t\t");
              int evalDoAfterBody = _jspx_th_logic_lessEqual_2.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_lessEqual_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_2);
            return;
          }
          _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_2);
          out.write("\r\n\t\t\t\t\r\n\t</div>\r\n\r\n<hr class=\"clearer\" />\r\n<div style=\"width:550px; margin-left:auto; margin-right:auto; display:none;\">\t\r\n\t<div style=\"float:left;\">\r\n\t");
          out.write("\r\n\t\r\n\t</div>\r\n\t\r\n\t<div style=\"margin-left:298px;\">\r\n\t\t<div class=\"partner-container\">\r\n\t\t\t<div class=\"partner-top\"><h2>&nbsp;</h2></div>\r\n\t\t\t<div class=\"partner-content\">\r\n\t\t\t");
          if (_jspx_meth_logic_notEqual_1(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t");
          out.print(DicoTools.dico(dico_lang , "srv_Shortcut/want_to_listen1"));
          out.write(' ');
          out.print(srvNameGlobal);
          out.write(' ');
          out.print(DicoTools.dico(dico_lang , "srv_Shortcut/want_to_listen2"));
          out.write("\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t");
          //  logic:lessEqual
          org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_3 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
          _jspx_th_logic_lessEqual_3.setPageContext(_jspx_page_context);
          _jspx_th_logic_lessEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_logic_lessEqual_3.setName("user_id");
          _jspx_th_logic_lessEqual_3.setValue("0");
          int _jspx_eval_logic_lessEqual_3 = _jspx_th_logic_lessEqual_3.doStartTag();
          if (_jspx_eval_logic_lessEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t<div class=\"infotag-container\">\r\n\t\t\t\t<div class=\"infotag-top\"><h2>&nbsp;</h2></div>\r\n\t\t\t\t<div class=\"infotag-content\">\r\n\t\t\t\t<h2>");
              out.print(DicoTools.dico(dico_lang , "srv_Shortcut/nabaztag_teaser"));
              out.write("</h2>\r\n\t\t\t\t<p>");
              out.print(DicoTools.dico(dico_lang , "srv_Shortcut/nabaztag_teaser_text"));
              out.write("</p>\r\n\t\t\t\t<p><a href=\"http://www.nabaztag.com\">");
              out.print(DicoTools.dico(dico_lang , "srv_Shortcut/discover_nabaztag"));
              out.write("</a></p>\t\t\t\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\r\n\t\t\t<div class=\"login-container\">\r\n\t\t\t\t<div class=\"login-top\"><h2>&nbsp;</h2></div>\r\n\t\t\t\t<div class=\"login-content\">\r\n\t\t\t\t\t<h2>");
              out.print(DicoTools.dico(dico_lang , "srv_Shortcut/have_rabbit_connect"));
              out.write("</h2>\t\r\n\t\t\t\t\t<input type=\"hidden\" name=\"action\" value=\"connect\" />\r\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n\t\t\t\t\t\r\n\t\t\t\t\t\r\n\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_13 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_13.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_3);
              _jspx_th_bean_define_13.setId("pseudo");
              _jspx_th_bean_define_13.setName("mySessionForm");
              _jspx_th_bean_define_13.setProperty("pseudo");
              int _jspx_eval_bean_define_13 = _jspx_th_bean_define_13.doStartTag();
              if (_jspx_th_bean_define_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_13);
              java.lang.Object pseudo = null;
              pseudo = (java.lang.Object) _jspx_page_context.findAttribute("pseudo");
              out.write("\r\n\t\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_14 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_14.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_3);
              _jspx_th_bean_define_14.setId("password");
              _jspx_th_bean_define_14.setName("mySessionForm");
              _jspx_th_bean_define_14.setProperty("password");
              int _jspx_eval_bean_define_14 = _jspx_th_bean_define_14.doStartTag();
              if (_jspx_th_bean_define_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_14);
              java.lang.Object password = null;
              password = (java.lang.Object) _jspx_page_context.findAttribute("password");
              out.write("\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td>");
              if (_jspx_meth_html_text_1(_jspx_th_logic_lessEqual_3, _jspx_page_context))
                return;
              out.write("</td>\r\n\t\t\t\t\t\t<td>&nbsp;</td>\r\n\t          \t\t\t<td>&nbsp;</td>\r\n\t         \t\t\t</tr>\r\n\t         \t\t\t<tr>\r\n\t         \t\t\t\t<td><font style=\"font-size: 10px;\">");
              out.print( DicoTools.dico(dico_lang , "srv_Shortcut/nabname"));
              out.write("</font></td>\r\n\t\t\t\t\t\t<td>&nbsp;</td>\r\n\t          \t\t\t<td>&nbsp;</td>\r\n\t          \t\t</tr>\r\n\t          \t\t<tr>\r\n\t         \t\t\t\t<td>");
              if (_jspx_meth_html_password_1(_jspx_th_logic_lessEqual_3, _jspx_page_context))
                return;
              out.write("</td>\r\n\t         \t\t\t\t<td>&nbsp;</td>\r\n\t          \t\t\t<td>&nbsp;</td>\r\n\t          \t\t</tr>\r\n\t\t\t\t\t<tr>\r\n\t\t\t\t\t\t<td><font style=\"font-size: 10px;\">");
              out.print( DicoTools.dico(dico_lang , "srv_Shortcut/password"));
              out.write("</font></td>\r\n\t\t\t\t\t\t<td>&nbsp;</td>\r\n\t          \t\t\t<td><input type=\"submit\" value=\"OK\" class=\"genericShortcutBt\"/></td>\r\n\t\t\t\t\t</tr>\r\n\t\t\t\t\t</table>\r\n\t\t\t\t</div>\r\n\t\t\t</div>\r\n\t\t");
              int evalDoAfterBody = _jspx_th_logic_lessEqual_3.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_logic_lessEqual_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_3);
            return;
          }
          _jspx_tagPool_logic_lessEqual_value_name.reuse(_jspx_th_logic_lessEqual_3);
          out.write("\r\n\t</div>\r\n</div>\t\r\n\t");
          int evalDoAfterBody = _jspx_th_html_form_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_html_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_html_form_styleId_action.reuse(_jspx_th_html_form_0);
        return;
      }
      _jspx_tagPool_html_form_styleId_action.reuse(_jspx_th_html_form_0);
      out.write("\r\n\t\r\n\r\n<script type=\"text/javascript\">\r\n<!--\r\n\t$(\"#contentAllServices\").hide();\t// ne devrait pas etre là, mais j'ignore ce qui a été fait en amon, le div n'est pas caché quand on arrive là....\r\n\tvar so = new SWFObject(\"../include_flash/nabaz_attente.swf\", \"mymovie\", \"290\", \"290\", \"7\");\r\n\tso.addParam(\"wmode\", \"transparent\");\r\n\tif (!so.write(\"rabbitFlash\")) {\n\t\tvar container = document.getElementById(\"rabbitFlash\");\n\t\tif (container) container.innerHTML = so.getSWFHTML();\n\t}\r\n//-->\r\n</script>\r\n\r\n");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_0.setParent(null);
      _jspx_th_logic_equal_0.setName("badLogin");
      _jspx_th_logic_equal_0.setValue("1");
      int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
      if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\r\n\t\r\n\t<script language=\"javascript\">\r\n\t\tcustomAlertN(\"");
          out.print(DicoTools.dico(dico_lang , "myHome/error_login"));
          out.write("\");\r\n\t</script>\r\n\r\n");
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

  private boolean _jspx_meth_logic_notEqual_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_notEqual_0.setName("srvImg");
    _jspx_th_logic_notEqual_0.setValue("");
    int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
    if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t<img src=\"");
        if (_jspx_meth_bean_write_0(_jspx_th_logic_notEqual_0, _jspx_page_context))
          return true;
        out.write("\" style=\"float:left; margin-right:5px;\" />\r\n\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_notEqual_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_0);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_0);
    return false;
  }

  private boolean _jspx_meth_bean_write_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_0 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_0.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
    _jspx_th_bean_write_0.setName("srvImg");
    int _jspx_eval_bean_write_0 = _jspx_th_bean_write_0.doStartTag();
    if (_jspx_th_bean_write_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_0);
      return true;
    }
    _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_0);
    return false;
  }

  private boolean _jspx_meth_html_text_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_lessEqual_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_0 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_styleId_property_name_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_0.setPageContext(_jspx_page_context);
    _jspx_th_html_text_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_2);
    _jspx_th_html_text_0.setName("mySessionForm");
    _jspx_th_html_text_0.setProperty("pseudo");
    _jspx_th_html_text_0.setStyleId("pseudo");
    int _jspx_eval_html_text_0 = _jspx_th_html_text_0.doStartTag();
    if (_jspx_th_html_text_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_styleId_property_name_nobody.reuse(_jspx_th_html_text_0);
      return true;
    }
    _jspx_tagPool_html_text_styleId_property_name_nobody.reuse(_jspx_th_html_text_0);
    return false;
  }

  private boolean _jspx_meth_html_password_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_lessEqual_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:password
    org.apache.struts.taglib.html.PasswordTag _jspx_th_html_password_0 = (org.apache.struts.taglib.html.PasswordTag) _jspx_tagPool_html_password_styleId_property_name_nobody.get(org.apache.struts.taglib.html.PasswordTag.class);
    _jspx_th_html_password_0.setPageContext(_jspx_page_context);
    _jspx_th_html_password_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_2);
    _jspx_th_html_password_0.setName("mySessionForm");
    _jspx_th_html_password_0.setProperty("password");
    _jspx_th_html_password_0.setStyleId("password");
    int _jspx_eval_html_password_0 = _jspx_th_html_password_0.doStartTag();
    if (_jspx_th_html_password_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_password_styleId_property_name_nobody.reuse(_jspx_th_html_password_0);
      return true;
    }
    _jspx_tagPool_html_password_styleId_property_name_nobody.reuse(_jspx_th_html_password_0);
    return false;
  }

  private boolean _jspx_meth_logic_notEqual_1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_logic_notEqual_1.setName("srvImg");
    _jspx_th_logic_notEqual_1.setValue("");
    int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
    if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("<img src=\"");
        if (_jspx_meth_bean_write_1(_jspx_th_logic_notEqual_1, _jspx_page_context))
          return true;
        out.write("\" style=\"float:right; \" />");
        int evalDoAfterBody = _jspx_th_logic_notEqual_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_1);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_name.reuse(_jspx_th_logic_notEqual_1);
    return false;
  }

  private boolean _jspx_meth_bean_write_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_1 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_1.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
    _jspx_th_bean_write_1.setName("srvImg");
    int _jspx_eval_bean_write_1 = _jspx_th_bean_write_1.doStartTag();
    if (_jspx_th_bean_write_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_1);
      return true;
    }
    _jspx_tagPool_bean_write_name_nobody.reuse(_jspx_th_bean_write_1);
    return false;
  }

  private boolean _jspx_meth_html_text_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_lessEqual_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_1 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_styleId_property_name_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_1.setPageContext(_jspx_page_context);
    _jspx_th_html_text_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_3);
    _jspx_th_html_text_1.setName("mySessionForm");
    _jspx_th_html_text_1.setProperty("pseudo");
    _jspx_th_html_text_1.setStyleId("pseudo");
    int _jspx_eval_html_text_1 = _jspx_th_html_text_1.doStartTag();
    if (_jspx_th_html_text_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_styleId_property_name_nobody.reuse(_jspx_th_html_text_1);
      return true;
    }
    _jspx_tagPool_html_text_styleId_property_name_nobody.reuse(_jspx_th_html_text_1);
    return false;
  }

  private boolean _jspx_meth_html_password_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_lessEqual_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:password
    org.apache.struts.taglib.html.PasswordTag _jspx_th_html_password_1 = (org.apache.struts.taglib.html.PasswordTag) _jspx_tagPool_html_password_styleId_property_name_nobody.get(org.apache.struts.taglib.html.PasswordTag.class);
    _jspx_th_html_password_1.setPageContext(_jspx_page_context);
    _jspx_th_html_password_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_3);
    _jspx_th_html_password_1.setName("mySessionForm");
    _jspx_th_html_password_1.setProperty("password");
    _jspx_th_html_password_1.setStyleId("password");
    int _jspx_eval_html_password_1 = _jspx_th_html_password_1.doStartTag();
    if (_jspx_th_html_password_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_password_styleId_property_name_nobody.reuse(_jspx_th_html_password_1);
      return true;
    }
    _jspx_tagPool_html_password_styleId_property_name_nobody.reuse(_jspx_th_html_password_1);
    return false;
  }
}
