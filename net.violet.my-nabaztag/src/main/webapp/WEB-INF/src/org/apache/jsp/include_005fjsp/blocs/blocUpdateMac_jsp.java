package org.apache.jsp.include_005fjsp.blocs;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class blocUpdateMac_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_onsubmit_action_acceptCharset;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_text_styleId_styleClass_property_name_maxlength_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_name_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_onsubmit_action_acceptCharset = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_text_styleId_styleClass_property_name_maxlength_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_html_form_styleId_onsubmit_action_acceptCharset.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
    _jspx_tagPool_html_text_styleId_styleClass_property_name_maxlength_nobody.release();
    _jspx_tagPool_html_hidden_value_property_nobody.release();
    _jspx_tagPool_html_hidden_value_property_name_nobody.release();
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

/* 0013d3849e90 */ 
      out.write("\r\n\r\n\r\n\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n");
	Lang dico_lang =	SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n\r\n");
 /* la mise a jour de la mac c'est bien passé, on redirect */ 
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_logic_equal_0(_jspx_page_context))
        return;
      out.write("\r\n\r\n");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_1.setParent(null);
      _jspx_th_logic_equal_1.setName("BlocForm");
      _jspx_th_logic_equal_1.setProperty("param1");
      _jspx_th_logic_equal_1.setValue("true");
      int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
      if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t<h1>\r\n\t\t");
          out.print(DicoTools.dico(dico_lang , "bloc/updateMac_title"));
          out.write("\r\n\t</h1>\r\n");
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
      out.write("\r\n\t\t\t\r\n<div class=\"block-content\"><div class=\"inner\">\r\n\t");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("BlocForm");
      _jspx_th_logic_notEmpty_0.setProperty("macAddress");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\t\r\n\t\r\n\t\t\t");
          if (_jspx_meth_logic_equal_2(_jspx_th_logic_notEmpty_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\r\n\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_logic_equal_3.setName("BlocForm");
          _jspx_th_logic_equal_3.setProperty("errors");
          _jspx_th_logic_equal_3.setValue("none");
          int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
          if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t<p>\r\n\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "bloc/updateMac_text1-1"));
              out.write("\t\r\n\t\t\t\t\t<a href=\"javascript:;\" onclick=\"$('#chgMacForm').show();\">");
              out.print(DicoTools.dico(dico_lang , "bloc/updateMac_cliquez"));
              out.write("</a>\r\n\t\t\t\t\t");
              out.print(DicoTools.dico(dico_lang , "bloc/updateMac_text1-2"));
              out.write("\t\t\t\t\r\n\t\t\t\t</p>\r\n\t\t\r\n\t\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\r\n\t\t\t\t<div id=\"chgMacForm\" style=\"display:none\" >\t\r\n\t\t\t");
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
          out.write("\r\n\t\t\t\r\n\t\t\t\t<p>\r\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang , "bloc/updateMac_text2"));
          out.write("\r\n\t\t\t\t</p>\r\n\t\t\t\t\r\n\t\t\t\t<hr class=\"spacer\" />\r\n\t\t\t\t\r\n\t\t\t\t");
          //  html:form
          org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_onsubmit_action_acceptCharset.get(org.apache.struts.taglib.html.FormTag.class);
          _jspx_th_html_form_0.setPageContext(_jspx_page_context);
          _jspx_th_html_form_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_html_form_0.setAction("/action/Bloc");
          _jspx_th_html_form_0.setStyleId("idFormMAC");
          _jspx_th_html_form_0.setOnsubmit("return regMacUpdateValidate();");
          _jspx_th_html_form_0.setAcceptCharset("UTF-8");
          int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
          if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\r\n\t\t\t\t\t");
              //  logic:notEqual
              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
              _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
              _jspx_th_logic_notEqual_0.setName("BlocForm");
              _jspx_th_logic_notEqual_0.setProperty("errors");
              _jspx_th_logic_notEqual_0.setValue("success");
              int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
              if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
                  _jspx_th_logic_equal_4.setName("BlocForm");
                  _jspx_th_logic_equal_4.setProperty("errors");
                  _jspx_th_logic_equal_4.setValue("recheckMac");
                  int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
                  if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t\t\t<p class='generic-error'>\r\n\t\t\t\t\t\t\t\t");
                      out.print(DicoTools.dico(dico_lang , "bloc/updateMac_error1"));
                      out.write("\r\n\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t");
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
                  out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
                  //  logic:equal
                  org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
                  _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
                  _jspx_th_logic_equal_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
                  _jspx_th_logic_equal_5.setName("BlocForm");
                  _jspx_th_logic_equal_5.setProperty("errors");
                  _jspx_th_logic_equal_5.setValue("gotARabbit");
                  int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
                  if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                    do {
                      out.write("\r\n\t\t\t\t\t\t\t<p class='generic-error'>\r\n\t\t\t\t\t\t\t\t");
                      out.print(DicoTools.dico(dico_lang , "bloc/updateMac_error1"));
                      out.write("\r\n\t\t\t\t\t\t\t</p>\r\n\t\t\t\t\t\t");
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
                  out.write("\r\n\t\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
                  if (_jspx_meth_html_text_0(_jspx_th_logic_notEqual_0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t\t\r\n\t\t\t\t\t\t");
                  if (_jspx_meth_html_hidden_0(_jspx_th_logic_notEqual_0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t\t");
                  if (_jspx_meth_html_hidden_1(_jspx_th_logic_notEqual_0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t\t");
                  if (_jspx_meth_html_hidden_2(_jspx_th_logic_notEqual_0, _jspx_page_context))
                    return;
                  out.write("\r\n\t\t\t\t\t\t<p class=\"validButton\">\t\t\t\t\r\n\t\t\t\t\t\t\t<input type=\"submit\" name=\"modifProfil\" value=\"");
                  out.print(DicoTools.dico(dico_lang, "myTerrier/myrabbit_modify_button"));
                  out.write("\" />\r\n\t\t\t\t\t\t</p>\r\n\t\t\t\t\t");
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
              out.write("\r\n\t\t\t\t\t \r\n\t\t\t\t");
              int evalDoAfterBody = _jspx_th_html_form_0.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
          }
          if (_jspx_th_html_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_form_styleId_onsubmit_action_acceptCharset.reuse(_jspx_th_html_form_0);
            return;
          }
          _jspx_tagPool_html_form_styleId_onsubmit_action_acceptCharset.reuse(_jspx_th_html_form_0);
          out.write("\r\n\t\t\t\t\r\n\t\t\t");
          if (_jspx_meth_logic_equal_6(_jspx_th_logic_notEmpty_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\r\n\t\t\t");
          if (_jspx_meth_logic_equal_7(_jspx_th_logic_notEmpty_0, _jspx_page_context))
            return;
          out.write("\t\r\n\t\t\r\n\t\r\n\t");
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
      out.write("\r\n</div></div>\r\n\r\n");
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
    _jspx_th_logic_equal_0.setName("BlocForm");
    _jspx_th_logic_equal_0.setProperty("errors");
    _jspx_th_logic_equal_0.setValue("success");
    int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
    if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t<script type=\"text/javascript\">\r\n\t\tredirect(\"myTerrier.do?onglet=Profil\");\r\n\t</script>\r\n");
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

  private boolean _jspx_meth_logic_equal_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
    _jspx_th_logic_equal_2.setName("BlocForm");
    _jspx_th_logic_equal_2.setProperty("param1");
    _jspx_th_logic_equal_2.setValue("true");
    int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
    if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t<div id=\"block-content-updatemac\">\r\n\t\t\t");
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

  private boolean _jspx_meth_html_text_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:text
    org.apache.struts.taglib.html.TextTag _jspx_th_html_text_0 = (org.apache.struts.taglib.html.TextTag) _jspx_tagPool_html_text_styleId_styleClass_property_name_maxlength_nobody.get(org.apache.struts.taglib.html.TextTag.class);
    _jspx_th_html_text_0.setPageContext(_jspx_page_context);
    _jspx_th_html_text_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
    _jspx_th_html_text_0.setName("BlocForm");
    _jspx_th_html_text_0.setProperty("macAddress");
    _jspx_th_html_text_0.setStyleId("numSerie");
    _jspx_th_html_text_0.setStyleClass("serialInput");
    _jspx_th_html_text_0.setMaxlength("12");
    int _jspx_eval_html_text_0 = _jspx_th_html_text_0.doStartTag();
    if (_jspx_th_html_text_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_text_styleId_styleClass_property_name_maxlength_nobody.reuse(_jspx_th_html_text_0);
      return true;
    }
    _jspx_tagPool_html_text_styleId_styleClass_property_name_maxlength_nobody.reuse(_jspx_th_html_text_0);
    return false;
  }

  private boolean _jspx_meth_html_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
    _jspx_th_html_hidden_0.setProperty("action");
    _jspx_th_html_hidden_0.setValue("updateMac");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
    return false;
  }

  private boolean _jspx_meth_html_hidden_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
    _jspx_th_html_hidden_1.setProperty("param1");
    _jspx_th_html_hidden_1.setValue("false");
    int _jspx_eval_html_hidden_1 = _jspx_th_html_hidden_1.doStartTag();
    if (_jspx_th_html_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_1);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_1);
    return false;
  }

  private boolean _jspx_meth_html_hidden_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_2 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_name_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_2.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
    _jspx_th_html_hidden_2.setName("BlocForm");
    _jspx_th_html_hidden_2.setProperty("errors");
    _jspx_th_html_hidden_2.setValue("none");
    int _jspx_eval_html_hidden_2 = _jspx_th_html_hidden_2.doStartTag();
    if (_jspx_th_html_hidden_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_2);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_name_nobody.reuse(_jspx_th_html_hidden_2);
    return false;
  }

  private boolean _jspx_meth_logic_equal_6(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_6 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_6.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
    _jspx_th_logic_equal_6.setName("BlocForm");
    _jspx_th_logic_equal_6.setProperty("errors");
    _jspx_th_logic_equal_6.setValue("none");
    int _jspx_eval_logic_equal_6 = _jspx_th_logic_equal_6.doStartTag();
    if (_jspx_eval_logic_equal_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t</div>\r\n\t\t\t");
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

  private boolean _jspx_meth_logic_equal_7(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEmpty_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_7 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_7.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
    _jspx_th_logic_equal_7.setName("BlocForm");
    _jspx_th_logic_equal_7.setProperty("param1");
    _jspx_th_logic_equal_7.setValue("true");
    int _jspx_eval_logic_equal_7 = _jspx_th_logic_equal_7.doStartTag();
    if (_jspx_eval_logic_equal_7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t</div>\r\n\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_equal_7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_7);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_7);
    return false;
  }
}
