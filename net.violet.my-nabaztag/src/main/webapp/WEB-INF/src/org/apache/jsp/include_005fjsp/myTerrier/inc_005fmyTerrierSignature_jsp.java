package org.apache.jsp.include_005fjsp.myTerrier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyTerrierSignature_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_form_styleId_action;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_value_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_hidden_styleId_property_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_select_styleClass_property_onchange;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_form_styleId_action = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_value_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_hidden_styleId_property_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_select_styleClass_property_onchange = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_html_form_styleId_action.release();
    _jspx_tagPool_html_hidden_value_property_nobody.release();
    _jspx_tagPool_html_hidden_styleId_property_nobody.release();
    _jspx_tagPool_html_select_styleClass_property_onchange.release();
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
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

      out.write("\n\n\n\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\n\n\n\n\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\n');
      out.write('\n');
      out.write('\n');
	Lang dico_lang =	SessionTools.getLangFromSession(session, request);
      out.write("\n\n\n\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setId("couleurSignature");
      _jspx_th_bean_define_0.setName("myTerrierSignatureForm");
      _jspx_th_bean_define_0.setProperty("userColorSign");
      _jspx_th_bean_define_0.setType("String");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      String couleurSignature = null;
      couleurSignature = (String) _jspx_page_context.findAttribute("couleurSignature");
      out.write("\n\t\t\n");
      //  html:form
      org.apache.struts.taglib.html.FormTag _jspx_th_html_form_0 = (org.apache.struts.taglib.html.FormTag) _jspx_tagPool_html_form_styleId_action.get(org.apache.struts.taglib.html.FormTag.class);
      _jspx_th_html_form_0.setPageContext(_jspx_page_context);
      _jspx_th_html_form_0.setParent(null);
      _jspx_th_html_form_0.setAction("/action/myTerrierSignature");
      _jspx_th_html_form_0.setStyleId("idTerrierSignature");
      int _jspx_eval_html_form_0 = _jspx_th_html_form_0.doStartTag();
      if (_jspx_eval_html_form_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          if (_jspx_meth_html_hidden_0(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write('\n');
          if (_jspx_meth_html_hidden_1(_jspx_th_html_form_0, _jspx_page_context))
            return;
          out.write("\n\n\n<div class=\"flat-block\"> \n\t<div class=\"flat-block-top\">\n\t\t<h3 class=\"no-icone\">\n\t\t\t");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_title"));
          out.write("\n\t\t</h3>\n\t</div>\n\n\t<div class=\"flat-block-content\">\n\t\t<div class=\"flat-block-content-inner\">\n\t\t");
          out.write("\n\n\t\t<div class=\"twoCol-left\" style=\"width:75%\">\n\t\t\t<div>\n\t\t\t\t");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_description"));
          out.write("\n\t\t\t</div>\n\t\t\t\n\t\t\t<hr class=\"spacer\" />\t\t\n\t\t\n\t\t\t");
          out.write("\n\t\t\t<div class=\"form-line\">\n\t\t\t\t<label class=\"center\">\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_choose_sound"));
          out.write("\n\t\t\t\t</label>\n\t\t\t\t<span>\n\t\t\t\t\t");
          //  html:select
          org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_0 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_styleClass_property_onchange.get(org.apache.struts.taglib.html.SelectTag.class);
          _jspx_th_html_select_0.setPageContext(_jspx_page_context);
          _jspx_th_html_select_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_select_0.setProperty("userMusic");
          _jspx_th_html_select_0.setOnchange("");
          _jspx_th_html_select_0.setStyleClass("custom");
          int _jspx_eval_html_select_0 = _jspx_th_html_select_0.doStartTag();
          if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_html_select_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_html_select_0.doInitBody();
            }
            do {
              out.write("\n\t\t\t\t\t\t<option value=\"0\">");
              out.print(DicoTools.dico(dico_lang, "myTerrier/signature_choose_sound_none"));
              out.write("</option>\n\t\t\t\t\t\t<!-- MusicData -->\n\t\t\t\t\t\t");
              if (_jspx_meth_html_optionsCollection_0(_jspx_th_html_select_0, _jspx_page_context))
                return;
              out.write("\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_html_select_0.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_html_select_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_html_select_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_select_styleClass_property_onchange.reuse(_jspx_th_html_select_0);
            return;
          }
          _jspx_tagPool_html_select_styleClass_property_onchange.reuse(_jspx_th_html_select_0);
          out.write("\t\n\t\t\t\t</span>\n\t\t\t</div>\n\n\t\t\t");
          out.write("\n\t\t\t<div class=\"form-line\">\n\t\t\t\t<label class=\"center\">\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_choose_animation"));
          out.write("\n\t\t\t\t</label>\n\t\t\t\t<span>\n\t\t\t\t\n\n\t\t\t\t\t");
          //  html:select
          org.apache.struts.taglib.html.SelectTag _jspx_th_html_select_1 = (org.apache.struts.taglib.html.SelectTag) _jspx_tagPool_html_select_styleClass_property_onchange.get(org.apache.struts.taglib.html.SelectTag.class);
          _jspx_th_html_select_1.setPageContext(_jspx_page_context);
          _jspx_th_html_select_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_html_select_1.setProperty("userColor");
          _jspx_th_html_select_1.setOnchange("");
          _jspx_th_html_select_1.setStyleClass("custom");
          int _jspx_eval_html_select_1 = _jspx_th_html_select_1.doStartTag();
          if (_jspx_eval_html_select_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            if (_jspx_eval_html_select_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_html_select_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_html_select_1.doInitBody();
            }
            do {
              out.write("\n\t\t\t\t\t\t<option value=\"0\">");
              out.print(DicoTools.dico(dico_lang, "myTerrier/signature_choose_animation_none"));
              out.write("</option>\n\t\t\t\t\t\t<!-- AnimData -->\n\t\t\t\t\t\t");
              if (_jspx_meth_html_optionsCollection_1(_jspx_th_html_select_1, _jspx_page_context))
                return;
              out.write("\n\t\t\t\t\t");
              int evalDoAfterBody = _jspx_th_html_select_1.doAfterBody();
              if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
                break;
            } while (true);
            if (_jspx_eval_html_select_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.popBody();
            }
          }
          if (_jspx_th_html_select_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_html_select_styleClass_property_onchange.reuse(_jspx_th_html_select_1);
            return;
          }
          _jspx_tagPool_html_select_styleClass_property_onchange.reuse(_jspx_th_html_select_1);
          out.write("\n\t\t\t\t\t\n\t\t\t\t</span>\n\t\t\t</div>\n\n\t\t\t");
          out.write("\n\t\t\t\n\t\t\n\t\t\t<div class=\"form-line\">\n\t\t\t\t<label class=\"center\">\n\t\t\t\t\t");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_choose_color"));
          out.write("\n\t\t\t\t</label>\n\t\t\t\t<span>\n\n\t\t\t\t\t<ul class=\"colorChoice\" id=\"colorPicker\">\n\t\t\t\t\t\t<li style=\"background-color:#ff0000;\"><a class=\"color_unselected\" href=\"#\"><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_color_red"));
          out.write("</span></a></li>\n\t\t\t\t\t\t<li style=\"background-color:#ffff00;\"><a class=\"color_unselected\" href=\"#\"><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_color_yellow"));
          out.write("</span></a></li>\n\t\t\t\t\t\t<li style=\"background-color:#00ff00;\"><a class=\"color_unselected\" href=\"#\"><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_color_green"));
          out.write("</span></a></li>\n\t\t\t\t\t\t<li style=\"background-color:#00ffff;\"><a class=\"color_unselected\" href=\"#\"><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_color_cyan"));
          out.write("</span></a></li>\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t<li style=\"background-color:#0000ff;\"><a class=\"color_unselected\" href=\"#\"><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_color_blue"));
          out.write("</span></a></li>\n\t\t\t\t\t\t<li style=\"background-color:#ff00ff;\"><a class=\"color_unselected\" href=\"#\"><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_color_mauve"));
          out.write("</span></a></li>\t\n\t\t\t\t\t</ul>\n\t\t\t\n\t\t\t\t</span>\n\t\t\t</div>\n\t\t\t<hr class=\"clearer\" />\n\t\t</div>\n\n\t\t<div class=\"twoCol-right\" style=\"width:23%\">\n\t\t\t<div id=\"signature\">   \n\t\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
          _jspx_th_bean_define_1.setName("myTerrierSignatureForm");
          _jspx_th_bean_define_1.setProperty("user_signature");
          _jspx_th_bean_define_1.setId("user_signature");
          int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
          if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
          java.lang.Object user_signature = null;
          user_signature = (java.lang.Object) _jspx_page_context.findAttribute("user_signature");
          out.write("\n\t\t\t\t\t<embed id=\"mymovie\" width=\"135\" height=\"135\" flashvars=\"cdll=");
          out.print(user_signature.toString() );
          out.write("\" quality=\"high\" name=\"mymovie\" wmode=\"transparent\" src=\"../include_flash/CDLEditor.swf\" type=\"application/x-shockwave-flash\"/>\t\t\t\t\n\t\t\t</div>\n\t\t</div>\t\t\n\t\t\n\t\t\t<hr class=\"clearer\" />\n\n\t\t<div class=\"buttons\">\t\t\t\n\t\t\t<input class=\"genericBt\" type=\"button\" name=\"modifProfil\" value=\"");
          out.print(DicoTools.dico(dico_lang, "myTerrier/signature_modify"));
          out.write("\" onclick=\"validateProfilSignature();\"/>\t\t\n\t\t</div>\n\t\t\n\t\t");
          out.write("\t\t\n\t\t</div>\n\t</div>\n</div>\n\n\n");
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
      out.write(" \n<script type=\"text/javascript\">\r\n\tsetTimeout(function(){\r\n\t\tcolorPicker_Init('colorPicker', '");
      out.print(couleurSignature);
      out.write("');\t\r\n\t}, 100);\n</script>\n");
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
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_0 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_value_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_0.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_hidden_0.setProperty("mode");
    _jspx_th_html_hidden_0.setValue("3");
    int _jspx_eval_html_hidden_0 = _jspx_th_html_hidden_0.doStartTag();
    if (_jspx_th_html_hidden_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
      return true;
    }
    _jspx_tagPool_html_hidden_value_property_nobody.reuse(_jspx_th_html_hidden_0);
    return false;
  }

  private boolean _jspx_meth_html_hidden_1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_form_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:hidden
    org.apache.struts.taglib.html.HiddenTag _jspx_th_html_hidden_1 = (org.apache.struts.taglib.html.HiddenTag) _jspx_tagPool_html_hidden_styleId_property_nobody.get(org.apache.struts.taglib.html.HiddenTag.class);
    _jspx_th_html_hidden_1.setPageContext(_jspx_page_context);
    _jspx_th_html_hidden_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_form_0);
    _jspx_th_html_hidden_1.setProperty("userColorSign");
    _jspx_th_html_hidden_1.setStyleId("colorPicker_value");
    int _jspx_eval_html_hidden_1 = _jspx_th_html_hidden_1.doStartTag();
    if (_jspx_th_html_hidden_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_hidden_styleId_property_nobody.reuse(_jspx_th_html_hidden_1);
      return true;
    }
    _jspx_tagPool_html_hidden_styleId_property_nobody.reuse(_jspx_th_html_hidden_1);
    return false;
  }

  private boolean _jspx_meth_html_optionsCollection_0(javax.servlet.jsp.tagext.JspTag _jspx_th_html_select_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:optionsCollection
    org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_optionsCollection_0 = (org.apache.struts.taglib.html.OptionsCollectionTag) _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
    _jspx_th_html_optionsCollection_0.setPageContext(_jspx_page_context);
    _jspx_th_html_optionsCollection_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_0);
    _jspx_th_html_optionsCollection_0.setName("myTerrierSignatureForm");
    _jspx_th_html_optionsCollection_0.setProperty("musicList");
    _jspx_th_html_optionsCollection_0.setLabel("music_name");
    _jspx_th_html_optionsCollection_0.setValue("music_id");
    int _jspx_eval_html_optionsCollection_0 = _jspx_th_html_optionsCollection_0.doStartTag();
    if (_jspx_th_html_optionsCollection_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.reuse(_jspx_th_html_optionsCollection_0);
      return true;
    }
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.reuse(_jspx_th_html_optionsCollection_0);
    return false;
  }

  private boolean _jspx_meth_html_optionsCollection_1(javax.servlet.jsp.tagext.JspTag _jspx_th_html_select_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  html:optionsCollection
    org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_optionsCollection_1 = (org.apache.struts.taglib.html.OptionsCollectionTag) _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
    _jspx_th_html_optionsCollection_1.setPageContext(_jspx_page_context);
    _jspx_th_html_optionsCollection_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_html_select_1);
    _jspx_th_html_optionsCollection_1.setName("myTerrierSignatureForm");
    _jspx_th_html_optionsCollection_1.setProperty("animList");
    _jspx_th_html_optionsCollection_1.setLabel("label");
    _jspx_th_html_optionsCollection_1.setValue("id");
    int _jspx_eval_html_optionsCollection_1 = _jspx_th_html_optionsCollection_1.doStartTag();
    if (_jspx_th_html_optionsCollection_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.reuse(_jspx_th_html_optionsCollection_1);
      return true;
    }
    _jspx_tagPool_html_optionsCollection_value_property_name_label_nobody.reuse(_jspx_th_html_optionsCollection_1);
    return false;
  }
}
