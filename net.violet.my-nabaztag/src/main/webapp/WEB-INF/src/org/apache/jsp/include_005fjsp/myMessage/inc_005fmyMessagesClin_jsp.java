package org.apache.jsp.include_005fjsp.myMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyMessagesClin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_lessEqual_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_lessEqual_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_logic_lessEqual_value_name.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
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
      out.write("\n\r\n\r\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myMessagesClinForm");
      _jspx_th_bean_define_0.setProperty("langUser");
      _jspx_th_bean_define_0.setId("lang");
      _jspx_th_bean_define_0.setType("String");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      String lang = null;
      lang = (String) _jspx_page_context.findAttribute("lang");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("myMessagesClinForm");
      _jspx_th_bean_define_1.setProperty("langClin");
      _jspx_th_bean_define_1.setId("langClin");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      java.lang.Object langClin = null;
      langClin = (java.lang.Object) _jspx_page_context.findAttribute("langClin");
      out.write("\r\n\r\n\r\n<div>\r\n\t<ul class=\"langSelect\">\n\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_0.setParent(null);
      _jspx_th_logic_iterate_0.setName("myMessagesClinForm");
      _jspx_th_logic_iterate_0.setProperty("langList");
      _jspx_th_logic_iterate_0.setId("langData");
      int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
      if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object langData = null;
        if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_0.doInitBody();
        }
        langData = (java.lang.Object) _jspx_page_context.findAttribute("langData");
        do {
          out.write('\n');
          out.write('	');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_2.setName("langData");
          _jspx_th_bean_define_2.setProperty("lang_id");
          _jspx_th_bean_define_2.setId("lang_id");
          int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
          if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
          java.lang.Object lang_id = null;
          lang_id = (java.lang.Object) _jspx_page_context.findAttribute("lang_id");
          out.write('\n');
          out.write('	');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_3.setName("langData");
          _jspx_th_bean_define_3.setProperty("lang_type");
          _jspx_th_bean_define_3.setId("lang_type");
          int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
          if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
          java.lang.Object lang_type = null;
          lang_type = (java.lang.Object) _jspx_page_context.findAttribute("lang_type");
          out.write('\n');
          out.write('	');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_bean_define_4.setName("langData");
          _jspx_th_bean_define_4.setProperty("lang_iso_code");
          _jspx_th_bean_define_4.setId("lang_iso_code");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object lang_iso_code = null;
          lang_iso_code = (java.lang.Object) _jspx_page_context.findAttribute("lang_iso_code");
          out.write("\n\t\t\t\n\t\t");
          //  logic:lessEqual
          org.apache.struts.taglib.logic.LessEqualTag _jspx_th_logic_lessEqual_0 = (org.apache.struts.taglib.logic.LessEqualTag) _jspx_tagPool_logic_lessEqual_value_name.get(org.apache.struts.taglib.logic.LessEqualTag.class);
          _jspx_th_logic_lessEqual_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_lessEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
          _jspx_th_logic_lessEqual_0.setName("lang_type");
          _jspx_th_logic_lessEqual_0.setValue("0");
          int _jspx_eval_logic_lessEqual_0 = _jspx_th_logic_lessEqual_0.doStartTag();
          if (_jspx_eval_logic_lessEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            do {
              out.write("\n\t\t\t<li class=\"");
              out.print(lang_iso_code.toString());
              out.write(' ');
              //  logic:equal
              org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
              _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
              _jspx_th_logic_equal_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_lessEqual_0);
              _jspx_th_logic_equal_0.setName("myMessagesClinForm");
              _jspx_th_logic_equal_0.setProperty("langClin");
              _jspx_th_logic_equal_0.setValue(lang_id.toString());
              int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
              if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
                do {
                  out.write("selected");
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
              out.write("\" ><a href=\"javascript:void(0);\" onclick=\"tabChangeUrl( 'Messages_clindoeil' , '../action/myMessagesClin.do?langClin=");
              out.print(lang_id.toString());
              out.write("'); $('#mp3persoDiv').hide();\"><span></span></a></li>\n\t\t");
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
          out.write('\n');
          out.write('	');
          int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
          langData = (java.lang.Object) _jspx_page_context.findAttribute("langData");
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
      out.write("\r\n\t</ul>\r\n</div>\r\n\r\n<div class=\"selectedItem\" >\r\n\t");
      out.print(DicoTools.dico(dico_lang, "myMessages/user_selection"));
      out.write(" <span id=\"ItemReplaceme1\">-</span>\r\n</div>\r\n\r\n\r\n<input name=\"idClin\" type=\"hidden\" id=\"itemValue\" value=\"\">  \r\n<div class=\"specialBlock\"> \r\n\t<ul class=\"item-listing\">\r\n\t\t<li><a onclick=\"ajaxJsExec('../include_jsp/myMessage/ajax_shuffleClin.jsp?langClin=");
      out.print(langClin);
      out.write("')\" href='javascript:;' class=\"shuffle\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/shuffle_selection"));
      out.write("</a></li>\r\n\t\t");
      //  logic:iterate
      org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_1 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
      _jspx_th_logic_iterate_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_iterate_1.setParent(null);
      _jspx_th_logic_iterate_1.setName("myMessagesClinForm");
      _jspx_th_logic_iterate_1.setProperty("listeCatClin");
      _jspx_th_logic_iterate_1.setId("musicStyleData");
      int _jspx_eval_logic_iterate_1 = _jspx_th_logic_iterate_1.doStartTag();
      if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        java.lang.Object musicStyleData = null;
        if (_jspx_eval_logic_iterate_1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
          out = _jspx_page_context.pushBody();
          _jspx_th_logic_iterate_1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
          _jspx_th_logic_iterate_1.doInitBody();
        }
        musicStyleData = (java.lang.Object) _jspx_page_context.findAttribute("musicStyleData");
        do {
          out.write("\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_5.setId("maCatClinId");
          _jspx_th_bean_define_5.setName("musicStyleData");
          _jspx_th_bean_define_5.setProperty("musicSytleId");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object maCatClinId = null;
          maCatClinId = (java.lang.Object) _jspx_page_context.findAttribute("maCatClinId");
          out.write("\r\n\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_1);
          _jspx_th_bean_define_6.setId("maCatClinName");
          _jspx_th_bean_define_6.setName("musicStyleData");
          _jspx_th_bean_define_6.setProperty("musicStyleName");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object maCatClinName = null;
          maCatClinName = (java.lang.Object) _jspx_page_context.findAttribute("maCatClinName");
          out.write("\t\t\r\n\t\t\t<!-- <li><a href=\"../action/myMessagesClinChoice.do?height=420&width=350&langClin=");
          out.print(langClin);
          out.write("&idClin=");
          out.print(maCatClinId);
          out.write("\" class=\"thickbox item\">");
          out.print(maCatClinName);
          out.write("</a></li> -->\r\n\t\t\t<li><a href=\"../action/myMessagesClinChoice.do?langClin=");
          out.print(langClin);
          out.write("&idClin=");
          out.print(maCatClinId);
          out.write("\" class=\"item\">");
          out.print(maCatClinName);
          out.write("</a></li>\r\n\t\t");
          int evalDoAfterBody = _jspx_th_logic_iterate_1.doAfterBody();
          musicStyleData = (java.lang.Object) _jspx_page_context.findAttribute("musicStyleData");
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
      out.write("\r\n\t</ul>\r\n<hr class=\"clearer\" />\r\n</div>\r\n\r\n<script type=\"text/javascript\">\r\n\t$(\"a.item\").openMsgCatChoice();\r\n</script>\t\r\n\r\n\r\n");
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
}
