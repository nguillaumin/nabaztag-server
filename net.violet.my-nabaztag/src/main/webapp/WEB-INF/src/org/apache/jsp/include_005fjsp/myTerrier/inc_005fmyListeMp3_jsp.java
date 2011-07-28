package org.apache.jsp.include_005fjsp.myTerrier;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyListeMp3_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_greaterThan_value_property_name.release();
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

      out.write("\n\r\n\n\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\n');
      out.write('\n');
	Lang dico_lang =	SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n<div class=\"intro-cadre-contener\">\r\n\t<div class=\"intro-cadre-top\">\n\t\t<h2>");
      out.print(DicoTools.dico(dico_lang, "myTerrier/mp3_titre"));
      out.write("</h2>\r\n\t</div>\r\n\t<div class=\"intro-cadre-content\">\r\n\t\t<div class=\"intro\">\r\n\t\t\t");
      out.print(DicoTools.dico(dico_lang, "myTerrier/mp3_intro"));
      out.write("\r\n\t\t</div>\r\n\t</div>\r\n</div>\n\r\n<p align=\"center\">\r\n\t<button onclick=\"TB_show('', 'myManageMp3.do?height=170&width=250')\" class=\"genericBt\">");
      out.print(DicoTools.dico(dico_lang, "myTerrier/add_mp3"));
      out.write("</button>\r\n</p>\r\n\r\n<hr class=\"spacer\" />\r\n\r\n<div class=\"flat-block\">\r\n<div class=\"flat-block-top\">\r\n<h3 class=\"no-icone\">");
      out.print(DicoTools.dico(dico_lang, "myTerrier/mp3_perso"));
      out.write("</h3>\r\n</div>\r\n\r\n<div class=\"flat-block-content\">\r\n<div class=\"flat-block-content-inner\">\r\n<p>");
      out.print(DicoTools.dico(dico_lang, "myTerrier/mp3_perso_hint"));
      out.write("</p>\r\n\r\n<div style=\"float:left; margin-left:10px;\" id=\"myPlayerMp3\"></div>\r\n\r\n<hr class=\"clearer\" />\r\n\r\n<div class=\"maMusique\" style=\"height:auto; padding-bottom:10px;\">\r\n\t<div style=\"float:left;  width:49%;\">\r\n\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_0.setParent(null);
      _jspx_th_logic_iterate_0.setName("myListeMp3Form");
      _jspx_th_logic_iterate_0.setProperty("listeMp3User1");
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
          _jspx_th_bean_define_0.setId("music_name1");
          _jspx_th_bean_define_0.setName("musicData");
          _jspx_th_bean_define_0.setProperty("music_name");
          int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
          if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
          java.lang.Object music_name1 = null;
          music_name1 = (java.lang.Object) _jspx_page_context.findAttribute("music_name1");
          out.write("\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_1.setId("music_share1");
          _jspx_th_bean_define_1.setName("musicData");
          _jspx_th_bean_define_1.setProperty("music_share");
          int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
          if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
          java.lang.Object music_share1 = null;
          music_share1 = (java.lang.Object) _jspx_page_context.findAttribute("music_share1");
          out.write("\t\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_2.setId("music_id1");
          _jspx_th_bean_define_2.setName("musicData");
          _jspx_th_bean_define_2.setProperty("music_id");
          int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
          if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
          java.lang.Object music_id1 = null;
          music_id1 = (java.lang.Object) _jspx_page_context.findAttribute("music_id1");
          out.write("\t\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_3.setId("music_name_short1");
          _jspx_th_bean_define_3.setName("musicData");
          _jspx_th_bean_define_3.setProperty("music_name_short");
          int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
          if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
          java.lang.Object music_name_short1 = null;
          music_name_short1 = (java.lang.Object) _jspx_page_context.findAttribute("music_name_short1");
          out.write("\t\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_4.setId("music_url1");
          _jspx_th_bean_define_4.setName("musicData");
          _jspx_th_bean_define_4.setProperty("music_url");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object music_url1 = null;
          music_url1 = (java.lang.Object) _jspx_page_context.findAttribute("music_url1");
          out.write("\r\n\r\n\t\t\t<form action=\"../action/myManageMp3.do\" name=\"sendMsg\" method=\"post\" id=\"updateMusic_");
          out.print(music_id1);
          out.write("\" onsubmit=\"return validateEditMp3(");
          out.print(music_id1);
          out.write(");\">\r\n\t\t\t\t<input type=\"hidden\" name=\"queFaire\" value=\"update\">\r\n\t\t\t\t<input type=\"hidden\" name=\"idMp3\" value=\"");
          out.print(music_id1);
          out.write("\">\r\n\t\t\t\t<ul style=\"position:relative;\" class=\"specialBlock");
          if (_jspx_meth_logic_greaterThan_0(_jspx_th_logic_iterate_0, _jspx_page_context))
            return;
          out.write("\">\r\n\t\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_equal_0.setName("musicData");
          _jspx_th_logic_equal_0.setProperty("music_styleid");
          _jspx_th_logic_equal_0.setValue(String.valueOf(MusicStyle.CATEGORIE_MP3_PERSO));
          int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
          if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t<li class='edit'><a href='javascript:void(0)' onclick=\"mp3_toggleOptions( 'mp3Options_");
              out.print(music_id1);
              out.write('\'');
              out.write(',');
              out.write(' ');
              out.print(music_share1);
              out.write(" );\"><span>[E]</span></a></li>\r\n\t\t\t\t\t\t\t<li class='nom'><div onclick=\"mp3_toggleOptions( 'mp3Options_");
              out.print(music_id1);
              out.write('\'');
              out.write(',');
              out.write(' ');
              out.print(music_share1);
              out.write(" );\" id=\"mp3name_");
              out.print(music_id1);
              out.write("\" title=\"");
              out.print(music_name1);
              out.write(" : id = ");
              out.print(music_id1);
              out.write('"');
              out.write('>');
              out.print(music_name_short1);
              out.write("</div></li>\r\n\t\t\t\t\t");
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
          out.write("\r\n\t\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_notEqual_0.setName("musicData");
          _jspx_th_logic_notEqual_0.setProperty("music_styleid");
          _jspx_th_logic_notEqual_0.setValue(String.valueOf(MusicStyle.CATEGORIE_MP3_PERSO));
          int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
          if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t<li class='nom'><div title=\"");
              out.print(music_name1);
              out.write(" : id = ");
              out.print(music_id1);
              out.write('"');
              out.write('>');
              out.print(music_name_short1);
              out.write("</div></li>\r\n\t\t\t\t\t");
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
          out.write("\r\n\t\t\t\t\t<li class='play'><a class=\"LplayIcone\" href='javascript:void(0);' onclick=\"loadPersoPlayer('");
          out.print(music_url1);
          out.write("');\" title=\"");
          out.print(DicoTools.dico(dico_lang, "myTerrier/play_mp3"));
          out.write("\" ><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/play_mp3"));
          out.write("</span></a></li>\r\n\t\t\t\t\t<li class='delete'><a class=\"LdeleteIcone\" href='javascript:void(0);' onclick=\"confirmDeleteMp3(");
          out.print(music_id1);
          out.write(");\" title=\"");
          out.print(DicoTools.dico(dico_lang, "myTerrier/delete_mp3"));
          out.write("\" ><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/delete_mp3"));
          out.write("</span></a></li>\r\n\t\t\t\t\t<li class='edit_options' id='mp3Options_");
          out.print(music_id1);
          out.write("'>\r\n\t\t\t\t\t\t&nbsp;\r\n\t\t\t\t\t</li>\t\t\t\r\n\t\t\t\t</ul>\r\n\t\t\t</form>\r\n\r\n\t\t");
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
      out.write("  \r\n\t</div>\r\n\t\r\n\t<div style=\"float:left; width:49%\">\r\n\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_1.setParent(null);
      _jspx_th_logic_iterate_1.setName("myListeMp3Form");
      _jspx_th_logic_iterate_1.setProperty("listeMp3User2");
      _jspx_th_logic_iterate_1.setId("musicData");
      int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
      if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object musicData = null;
        if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_1.doInitBody();
        }
        musicData = (java.lang.Object) _jspx_page_context.findAttribute("musicData");
        do {
          out.write("\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_5.setId("music_name2");
          _jspx_th_bean_define_5.setName("musicData");
          _jspx_th_bean_define_5.setProperty("music_name");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object music_name2 = null;
          music_name2 = (java.lang.Object) _jspx_page_context.findAttribute("music_name2");
          out.write("\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_6.setId("music_share2");
          _jspx_th_bean_define_6.setName("musicData");
          _jspx_th_bean_define_6.setProperty("music_share");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object music_share2 = null;
          music_share2 = (java.lang.Object) _jspx_page_context.findAttribute("music_share2");
          out.write("\t\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_7.setId("music_id2");
          _jspx_th_bean_define_7.setName("musicData");
          _jspx_th_bean_define_7.setProperty("music_id");
          int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
          if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
          java.lang.Object music_id2 = null;
          music_id2 = (java.lang.Object) _jspx_page_context.findAttribute("music_id2");
          out.write("\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_8.setId("music_name_short2");
          _jspx_th_bean_define_8.setName("musicData");
          _jspx_th_bean_define_8.setProperty("music_name_short");
          int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
          if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
          java.lang.Object music_name_short2 = null;
          music_name_short2 = (java.lang.Object) _jspx_page_context.findAttribute("music_name_short2");
          out.write("\t\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_9.setId("music_url2");
          _jspx_th_bean_define_9.setName("musicData");
          _jspx_th_bean_define_9.setProperty("music_url");
          int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
          if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
          java.lang.Object music_url2 = null;
          music_url2 = (java.lang.Object) _jspx_page_context.findAttribute("music_url2");
          out.write("\r\n\r\n\t\t\t<form action=\"../action/myManageMp3.do\" name=\"sendMsg\" method=\"post\" id=\"updateMusic_");
          out.print(music_id2);
          out.write("\" onsubmit=\"return validateEditMp3(");
          out.print(music_id2);
          out.write(");\">\r\n\t\t\t<input type=\"hidden\" name=\"queFaire\" value=\"update\">\r\n\t\t\t<input type=\"hidden\" name=\"idMp3\" value=\"");
          out.print(music_id2);
          out.write("\">\t\t\t\r\n\t\t\t<ul  style=\"position:relative;\" class=\"specialBlock");
          if (_jspx_meth_logic_greaterThan_1(_jspx_th_logic_iterate_1, _jspx_page_context))
            return;
          out.write("\">\r\n\t\t\t\t");
          //  logic:equal
          org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
          _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_equal_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_logic_equal_1.setName("musicData");
          _jspx_th_logic_equal_1.setProperty("music_styleid");
          _jspx_th_logic_equal_1.setValue(String.valueOf(MusicStyle.CATEGORIE_MP3_PERSO));
          int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
          if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t\t\t<li class='edit'><a href='javascript:void(0)' onclick=\"mp3_toggleOptions( 'mp3Options_");
              out.print(music_id2);
              out.write('\'');
              out.write(',');
              out.write(' ');
              out.print(music_share2);
              out.write(" );\"><span>[E]</span></a></li>\r\n\t\t\t\t\t\t\t<li class='nom'><div onclick=\"mp3_toggleOptions( 'mp3Options_");
              out.print(music_id2);
              out.write('\'');
              out.write(',');
              out.write(' ');
              out.print(music_share2);
              out.write(" );\" id=\"mp3name_");
              out.print(music_id2);
              out.write("\" title=\"");
              out.print(music_name2);
              out.write(" : id = ");
              out.print(music_id2);
              out.write('"');
              out.write('>');
              out.print(music_name_short2);
              out.write("</div></li>\r\n\t\t\t\t");
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
          out.write("\r\n\t\t\t\t");
          //  logic:notEqual
          org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
          _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
          _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_logic_notEqual_1.setName("musicData");
          _jspx_th_logic_notEqual_1.setProperty("music_styleid");
          _jspx_th_logic_notEqual_1.setValue(String.valueOf(MusicStyle.CATEGORIE_MP3_PERSO));
          int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
          if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\r\n\t\t\t\t\t<li class='nom'><div title=\"");
              out.print(music_name2);
              out.write(" : id = ");
              out.print(music_id2);
              out.write('"');
              out.write('>');
              out.print(music_name_short2);
              out.write("</div></li>\r\n\t\t\t\t");
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
          out.write("\r\n\t\t\t\t<li class='play'><a class=\"LplayIcone\" href='javascript:void(0);' onclick=\"loadPersoPlayer('");
          out.print(music_url2);
          out.write("');\" title=\"");
          out.print(DicoTools.dico(dico_lang, "myTerrier/play_mp3"));
          out.write("\" ><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/play_mp3"));
          out.write("</span></a></li>\r\n\t\t\t\t<li class='delete'><a class=\"LdeleteIcone\" href='javascript:void(0);' onclick=\"confirmDeleteMp3(");
          out.print(music_id2);
          out.write(");\" title=\"");
          out.print(DicoTools.dico(dico_lang, "myTerrier/delete_mp3"));
          out.write("\" ><span>");
          out.print(DicoTools.dico(dico_lang, "myTerrier/delete_mp3"));
          out.write("</span></a></li>\r\n\t\t\t\t<li class='edit_options' id='mp3Options_");
          out.print(music_id2);
          out.write("'>&nbsp;</li>\t\t\t\r\n\t\t\t</ul>\r\n\t\t\t</form>\r\n\t\t");
          int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
          musicData = (java.lang.Object) _jspx_page_context.findAttribute("musicData");
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
      out.write("\r\n\t</div>\r\n\t</div>\r\n\t</div>\r\n\t</div>\r\n</div>\r\n\r\n<script>\r\n\tTB_init();\r\n</script>\r\n");
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

  private boolean _jspx_meth_logic_greaterThan_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:greaterThan
    org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
    _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_greaterThan_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_logic_greaterThan_0.setName("musicData");
    _jspx_th_logic_greaterThan_0.setProperty("music_share");
    _jspx_th_logic_greaterThan_0.setValue("0");
    int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
    if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("Selected");
        int evalDoAfterBody = _jspx_th_logic_greaterThan_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_greaterThan_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_0);
      return true;
    }
    _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_0);
    return false;
  }

  private boolean _jspx_meth_logic_greaterThan_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:greaterThan
    org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_1 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
    _jspx_th_logic_greaterThan_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_greaterThan_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
    _jspx_th_logic_greaterThan_1.setName("musicData");
    _jspx_th_logic_greaterThan_1.setProperty("music_share");
    _jspx_th_logic_greaterThan_1.setValue("0");
    int _jspx_eval_logic_greaterThan_1 = _jspx_th_logic_greaterThan_1.doStartTag();
    if (_jspx_eval_logic_greaterThan_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("Selected");
        int evalDoAfterBody = _jspx_th_logic_greaterThan_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_greaterThan_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_1);
      return true;
    }
    _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_1);
    return false;
  }
}
