package org.apache.jsp.include_005fjsp.myMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyMessagesMp3_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_select_styleId_style_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_file_value_styleId_style_property_name_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_html_select_styleId_style_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_file_value_styleId_style_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_html_select_styleId_style_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_notEqual_value_property_name.release();
    _jspx_tagPool_html_file_value_styleId_style_property_name_nobody.release();
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
      out.write('\r');
      out.write('\n');
 response.setCharacterEncoding("UTF-8"); 
      out.write('\r');
      out.write('\n');
 request.setCharacterEncoding("UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n<div id=\"mp3-sender\">\r\n\t<ul class=\"menu\">\r\n\t\t<li id=\"mp3Perso_li\"  class=\"selected\"><a href=\"javascript:;\" onclick=\"sendMp3Chooser('mp3Perso')\" >");
      out.print(DicoTools.dico(dico_lang, "myMessages/my_mp3"));
      out.write("</a></li>\r\n\t\t<li id=\"mp3Upload_li\" ><a href=\"javascript:;\" onclick=\"sendMp3Chooser('mp3Upload')\" >");
      out.print(DicoTools.dico(dico_lang, "myMessages/send_new_mp3"));
      out.write("</a></li>\r\n\t</ul>\r\n\t<div id=\"mp3Perso\" class=\"mp3-choice\">\r\n\t\t<label>");
      out.print(DicoTools.dico(dico_lang, "myMessages/mp3_your_mp3s"));
      out.write("</label><br/>\r\n\t\t");
      //  html:select
      org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_0 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_styleId_style_property_name.get(org.apache.struts.taglib.html.SelectTag.class);
      _jspx_th_html_select_0.setPageContext(_jspx_page_context);
      _jspx_th_html_select_0.setParent(null);
      _jspx_th_html_select_0.setName("myMessagesMp3Form");
      _jspx_th_html_select_0.setProperty("idMp3");
      _jspx_th_html_select_0.setStyleId("idMp3");
      _jspx_th_html_select_0.setStyle("width:200px;");
      int _jspx_eval_html_select_0 = _jspx_th_html_select_0.doStartTag();
      if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_html_select_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_html_select_0.doInitBody();
        }
        do {
          out.write("\r\n\t\t\t<option value=\"-1\" selected>");
          out.print(DicoTools.dico(dico_lang, "myMessages/mp3_choose"));
          out.write("</option>\r\n\t\t\t\r\n\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_0);
          _jspx_th_logic_iterate_0.setName("myMessagesMp3Form");
          _jspx_th_logic_iterate_0.setProperty("listeMp3");
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
              out.write("\r\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_0.setId("musicId");
              _jspx_th_bean_define_0.setName("musicData");
              _jspx_th_bean_define_0.setProperty("musicId");
              int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
              if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
              java.lang.Object musicId = null;
              musicId = (java.lang.Object) _jspx_page_context.findAttribute("musicId");
              out.write("\r\n\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_1.setId("musicName");
              _jspx_th_bean_define_1.setName("musicData");
              _jspx_th_bean_define_1.setProperty("musicName");
              int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
              if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
              java.lang.Object musicName = null;
              musicName = (java.lang.Object) _jspx_page_context.findAttribute("musicName");
              out.write("\r\n\t\t\t\t\r\n\t\t\t");
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_equal_0.setName("myMessagesMp3Form");
              _jspx_th_logic_equal_0.setProperty("idMp3");
              _jspx_th_logic_equal_0.setValue(musicId.toString());
              int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
              if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t<option value=\"");
                  out.print(musicId.toString());
                  out.write("\" selected>");
                  out.print(musicName);
                  out.write("</option>\r\n\t\t\t");
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
              out.write("\r\n\t\t\t");
              //  logic:notEqual
              org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
              _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_logic_notEqual_0.setName("myMessagesMp3Form");
              _jspx_th_logic_notEqual_0.setProperty("idMp3");
              _jspx_th_logic_notEqual_0.setValue(musicId.toString());
              int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
              if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("\r\n\t\t\t\t<option value=\"");
                  out.print(musicId.toString());
                  out.write('"');
                  out.write('>');
                  out.print(musicName);
                  out.write("</option>\r\n\t\t\t");
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
              out.write("\r\n\t\t\t\r\n\t\t\t");
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
          out.write("\r\n\t\t");
          int evalDoAfterBody = _jspx_th_html_select_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
        if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.popBody();
        }
      }
      if (_jspx_th_html_select_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_html_select_styleId_style_property_name.reuse(_jspx_th_html_select_0);
        return;
      }
      _jspx_tagPool_html_select_styleId_style_property_name.reuse(_jspx_th_html_select_0);
      out.write("\r\n\t</div>\t\r\n\t<div id=\"mp3Upload\" class=\"mp3-choice\">\r\n\t\t\t<iframe src =\"javascript:void(0);\" id=\"uploadIframe\" name=\"uploadIframe\" style=\"border: 0px; width:0px; height:0;\"></iframe>\r\n\t\r\n\t\t\t<label>");
      out.print(DicoTools.dico(dico_lang, "myMessages/mp3_send_file"));
      out.write("</label><br />\r\n\t\t\t<input name=\"musicName\" type=\"text\" id=\"musicName\" style=\"width:92%;\">\r\n\t\t\t<hr class=\"spacer\" />\r\n\t\t\t<label>");
      out.print(DicoTools.dico(dico_lang, "myMessages/mp3_file"));
      out.write("</label><br />\r\n\t\t\t");
      if (_jspx_meth_html_file_0(_jspx_page_context))
        return;
      out.write("\r\n\r\n\t\t\t<hr class=\"spacer\" />\r\n\r\n\t\t\t<label>");
      out.print(DicoTools.dico(dico_lang, "myMessages/mp3_record_from"));
      out.write("</label><br/><input  name=\"musicStart\"  id=\"musicStart\" value=\"0\" type=\"text\"  style=\"width:30px\" >");
      out.print(DicoTools.dico(dico_lang, "myMessages/mp3_seconds_beginning"));
      out.write("<hr  class=\"spacer\" />\r\n\t\t\t<label>");
      out.print(DicoTools.dico(dico_lang, "myMessages/mp3_duration"));
      out.write("</label><br/><input name=\"musicTime\"  id=\"musicTime\" value=\"45\" type=\"text\"  style=\"width:30px\" > ");
      out.print(DicoTools.dico(dico_lang, "myMessages/mp3_seconds"));
      out.write("\t\r\n\t\t\t<hr class=\"clearer\" style=\"margin-bottom:10px;\"/>\r\n\r\n\t</div>\r\n</div>");
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

  private boolean _jspx_meth_html_file_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:file
    org.apache.struts.taglib.html.FileTag _jspx_th_html_file_0 = (org.apache.struts.taglib.html.FileTag) _jspx_tagPool_html_file_value_styleId_style_property_name_nobody.get(org.apache.struts.taglib.html.FileTag.class);
    _jspx_th_html_file_0.setPageContext(_jspx_page_context);
    _jspx_th_html_file_0.setParent(null);
    _jspx_th_html_file_0.setName("myMessagesMp3Form");
    _jspx_th_html_file_0.setProperty("musicFile");
    _jspx_th_html_file_0.setStyleId("musicFile");
    _jspx_th_html_file_0.setStyle("width:92%;");
    _jspx_th_html_file_0.setValue("");
    int _jspx_eval_html_file_0 = _jspx_th_html_file_0.doStartTag();
    if (_jspx_th_html_file_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_file_value_styleId_style_property_name_nobody.reuse(_jspx_th_html_file_0);
      return true;
    }
    _jspx_tagPool_html_file_value_styleId_style_property_name_nobody.reuse(_jspx_th_html_file_0);
    return false;
  }
}
