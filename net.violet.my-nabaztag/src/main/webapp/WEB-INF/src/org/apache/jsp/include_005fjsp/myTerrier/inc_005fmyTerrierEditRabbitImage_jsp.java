package org.apache.jsp.include_005fjsp.myTerrier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyTerrierEditRabbitImage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_target_styleId_enctype_action;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_html_form_target_styleId_enctype_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_html_form_target_styleId_enctype_action.release();
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
      out.write("\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\n\n");
	String dico_lang =	Long.toString(SessionTools.getLangFromSession(session, request).getId());
      out.write("\r\n\r\n<iframe src ='javascript:void(0);' id='myUploadIframe' name='myUploadIframe' style='border: none; width:0; height:0'></iframe>\r\n");
      if (_jspx_meth_html_form_0(_jspx_page_context))
        return;
      out.write("\r\n\r\n\r\n");
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

  private boolean _jspx_meth_html_form_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:form
    org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_target_styleId_enctype_action.get(org.apache.struts.taglib.html.FormTag.class);
    _jspx_th_html_form_0.setPageContext(_jspx_page_context);
    _jspx_th_html_form_0.setParent(null);
    _jspx_th_html_form_0.setAction("/action/myTerrierEditRabbitImage");
    _jspx_th_html_form_0.setEnctype("multipart/form-data");
    _jspx_th_html_form_0.setStyleId("formUpPhoto");
    _jspx_th_html_form_0.setTarget("myUploadIframe");
    int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
    if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t<a class=\"closebt\" href=\"javascript:;\" onclick=\"$('#pictureLoader').toggle();\" ><img border=\"0\" src=\"../include_img/closeWin.gif\" /></a>\r\n\t\t<hr class=\"spacer\" />\r\n\t\t<input type=\"hidden\" name=\"modify\" value=\"1\" />\r\n\t\t<input name=\"imageFile\" type=\"file\" size=\"30\" id=\"browse\" />\r\n\t\t<hr class=\"spacer\" />\r\n\t\t<div align=\"center\"><input type=\"button\" class=\"genericBt\" name=\"Submit\" value=\"Valider\" onclick=\"terrierValidateNewPict('formUpPhoto')\">\r\n\t\t</div>\r\n");
        int evalDoAfterBody = _jspx_th_html_form_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_html_form_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_form_target_styleId_enctype_action.reuse(_jspx_th_html_form_0);
      return true;
    }
    _jspx_tagPool_html_form_target_styleId_enctype_action.reuse(_jspx_th_html_form_0);
    return false;
  }
}
