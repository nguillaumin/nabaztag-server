package org.apache.jsp.include_005fjsp.myServices.webRadio;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class webRadioFreePlayer_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_styleId_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_submit_value_title_styleClass_onclick_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_styleId_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_submit_value_title_styleClass_onclick_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_html_form_styleId_action.release();
    _jspx_tagPool_html_hidden_value_styleId_property_nobody.release();
    _jspx_tagPool_html_hidden_value_property_nobody.release();
    _jspx_tagPool_html_submit_value_title_styleClass_onclick_nobody.release();
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

      out.write("\r\n\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("mySrvWebRadioFreePlayerForm");
      _jspx_th_bean_define_0.setProperty("wradioId");
      _jspx_th_bean_define_0.setId("wradioId");
      _jspx_th_bean_define_0.setType("Integer");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      Integer wradioId = null;
      wradioId = (Integer) _jspx_page_context.findAttribute("wradioId");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("mySrvWebRadioFreePlayerForm");
      _jspx_th_bean_define_1.setProperty("dispatch");
      _jspx_th_bean_define_1.setId("dispatch");
      _jspx_th_bean_define_1.setType("String");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      String dispatch = null;
      dispatch = (String) _jspx_page_context.findAttribute("dispatch");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_2.setParent(null);
      _jspx_th_bean_define_2.setName("mySrvWebRadioFreePlayerForm");
      _jspx_th_bean_define_2.setProperty("rabbitName");
      _jspx_th_bean_define_2.setId("rabbitName");
      _jspx_th_bean_define_2.setType("String");
      int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
      if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
      String rabbitName = null;
      rabbitName = (String) _jspx_page_context.findAttribute("rabbitName");
      out.write(" \r\n ");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_3.setParent(null);
      _jspx_th_bean_define_3.setName("mySrvWebRadioFreePlayerForm");
      _jspx_th_bean_define_3.setProperty("isPlaying");
      _jspx_th_bean_define_3.setId("isPlaying");
      _jspx_th_bean_define_3.setType("String");
      int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
      if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
      String isPlaying = null;
      isPlaying = (String) _jspx_page_context.findAttribute("isPlaying");
      out.write(" \r\n \r\n<div class=\"srv-player\"> \r\n\t<div class=\"srv-player-info\">\r\n\t\t");
      out.print(DicoTools.dico(dico_lang , "services/listeningRadio"));
      out.write("<span class=\"srv-nabname\"> ");
      out.print(rabbitName );
      out.write(" </span>");
      out.print(DicoTools.dico(dico_lang , "services/now"));
      out.write("\r\n\t</div>\r\n\t<div class=\"srv-player-form\">\r\n\t\t");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_action.get(org.apache.struts.taglib.html.FormTag.class);
      _jspx_th_html_form_0.setPageContext(_jspx_page_context);
      _jspx_th_html_form_0.setParent(null);
      _jspx_th_html_form_0.setAction("/action/srvWebRadioFreePlayer");
      _jspx_th_html_form_0.setStyleId("srvWebRadioFreePlayer");
      int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
      if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t");
          if (_jspx_meth_html_hidden_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t");
          //  html:hidden
          org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
          _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
          _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_hidden_1.setProperty("wradioId");
          _jspx_th_html_hidden_1.setValue(wradioId.toString() );
          int _jspx_eval_html_hidden_1 = _jspx_th_html_hidden_1.doStartTag();
          if (_jspx_th_html_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_1);
            return;
          }
          _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_1);
          out.write("\r\n\t\t\t");
          out.write("\r\n\t\t\t<!-- html:hidden property =\"dispatch\" value=\"load\"/ -->\r\n\t\t\t");
          out.write("\t\t\r\n\t\t\t<!-- ");
          //  html:submit
          org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_0 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_title_styleClass_onclick_nobody.get(org.apache.struts.taglib.html.SubmitTag.class);
          _jspx_th_html_submit_0.setPageContext(_jspx_page_context);
          _jspx_th_html_submit_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_submit_0.setValue(DicoTools.dico(dico_lang , "services/listen_radio_button"));
          _jspx_th_html_submit_0.setOnclick("goDispatch('playNow', 'dispatchPlayer')");
          _jspx_th_html_submit_0.setStyleClass("webRadioPlayNow");
          _jspx_th_html_submit_0.setTitle("Click here to play");
          int _jspx_eval_html_submit_0 = _jspx_th_html_submit_0.doStartTag();
          if (_jspx_th_html_submit_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_submit_value_title_styleClass_onclick_nobody.reuse(_jspx_th_html_submit_0);
            return;
          }
          _jspx_tagPool_html_submit_value_title_styleClass_onclick_nobody.reuse(_jspx_th_html_submit_0);
          out.write("<br /> -->\r\n\t\t\t<a title=\"");
          out.print(DicoTools.dico(dico_lang , "services/listen_radio_button"));
          out.write("\" href=\"javascript:;\" class=\"webRadioPlayNow\" onclick=\"goDispatch('playNow', 'dispatchPlayer'); $('#srvWebRadioFreePlayer').submit()\">&nbsp;</a>\r\n\t\t\t");
          out.write("\t\t\t\t\r\n\t\t\t<!--");
          //  html:submit
          org.apache.struts.taglib.html.SubmitTag _jspx_th_html_submit_1 = (org.apache.struts.taglib.html.SubmitTag) _jspx_tagPool_html_submit_value_title_styleClass_onclick_nobody.get(org.apache.struts.taglib.html.SubmitTag.class);
          _jspx_th_html_submit_1.setPageContext(_jspx_page_context);
          _jspx_th_html_submit_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_submit_1.setValue(DicoTools.dico(dico_lang , "services/stop_radio_button"));
          _jspx_th_html_submit_1.setOnclick("goDispatch('stopPlay', 'dispatchPlayer')");
          _jspx_th_html_submit_1.setStyleClass("webRadioStopNow");
          _jspx_th_html_submit_1.setTitle("Click here to stop");
          int _jspx_eval_html_submit_1 = _jspx_th_html_submit_1.doStartTag();
          if (_jspx_th_html_submit_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_submit_value_title_styleClass_onclick_nobody.reuse(_jspx_th_html_submit_1);
            return;
          }
          _jspx_tagPool_html_submit_value_title_styleClass_onclick_nobody.reuse(_jspx_th_html_submit_1);
          out.write("-->\r\n\t\t\t<a title=\"");
          out.print(DicoTools.dico(dico_lang , "services/stop_radio_button"));
          out.write("\" href=\"javascript:;\" class=\"webRadioStopNow\" onclick=\"goDispatch('stopPlay', 'dispatchPlayer'); $('#srvWebRadioFreePlayer').submit()\">&nbsp;</a>\r\n\t\t\t<hr class=\"spacer\">\r\n\r\n\t\t\t\t\t\r\n\t\t");
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
      out.write("\r\n\t</div>\r\n</div>\r\n\r\n<script type=\"text/javascript\">\r\n\r\n\t$(\"#srvWebRadioFreePlayer\").submit(function(){\r\n\t\t\treturn actionRadio();\r\n\t});\r\n\r\n\r\n</script>");
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

  private boolean _jspx_meth_html_hidden_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_styleId_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_hidden_0.setStyleId("dispatchPlayer");
    _jspx_th_html_hidden_0.setProperty("dispatch");
    _jspx_th_html_hidden_0.setValue("load");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_styleId_property_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_styleId_property_nobody.reuse(_jspx_th_html_hidden_0);
    return false;
  }
}
