package org.apache.jsp.include_005fjsp.myServices.templates;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class srvMainLayout_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_tiles_insert_attribute_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_tiles_insert_attribute_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_tiles_insert_attribute_nobody.release();
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

      out.write("\n\r\n\r\n\n\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\r\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n");
      if (_jspx_meth_tiles_insert_0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_tiles_insert_1(_jspx_page_context))
        return;
      out.write('\n');
      if (_jspx_meth_tiles_insert_2(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_tiles_insert_3(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_tiles_insert_4(_jspx_page_context))
        return;
      out.write('\n');
      if (_jspx_meth_tiles_insert_5(_jspx_page_context))
        return;
      out.write("\r\n\r\n<div class=\"bottom-bar-outer\">\r\n\t<div class=\"bottom-bar-inner\">\r\n\t\t<a onclick=\"nablife.returnToSrvList()\" href=\"javascript:;\" class=\"srv-back\" ><span>&lt;");
      out.print(DicoTools.dico(dico_lang , "services/back_to_list"));
      out.write("</span></a>\r\n\t</div>\r\n</div>\n");
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

  private boolean _jspx_meth_tiles_insert_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insert
    org.apache.struts.tiles.taglib.InsertTag _jspx_th_tiles_insert_0 = (org.apache.struts.tiles.taglib.InsertTag) _jspx_tagPool_tiles_insert_attribute_nobody.get(org.apache.struts.tiles.taglib.InsertTag.class);
    _jspx_th_tiles_insert_0.setPageContext(_jspx_page_context);
    _jspx_th_tiles_insert_0.setParent(null);
    _jspx_th_tiles_insert_0.setAttribute("description");
    int _jspx_eval_tiles_insert_0 = _jspx_th_tiles_insert_0.doStartTag();
    if (_jspx_th_tiles_insert_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_0);
      return true;
    }
    _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_0);
    return false;
  }

  private boolean _jspx_meth_tiles_insert_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insert
    org.apache.struts.tiles.taglib.InsertTag _jspx_th_tiles_insert_1 = (org.apache.struts.tiles.taglib.InsertTag) _jspx_tagPool_tiles_insert_attribute_nobody.get(org.apache.struts.tiles.taglib.InsertTag.class);
    _jspx_th_tiles_insert_1.setPageContext(_jspx_page_context);
    _jspx_th_tiles_insert_1.setParent(null);
    _jspx_th_tiles_insert_1.setAttribute("setUpSrv");
    int _jspx_eval_tiles_insert_1 = _jspx_th_tiles_insert_1.doStartTag();
    if (_jspx_th_tiles_insert_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_1);
      return true;
    }
    _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_1);
    return false;
  }

  private boolean _jspx_meth_tiles_insert_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insert
    org.apache.struts.tiles.taglib.InsertTag _jspx_th_tiles_insert_2 = (org.apache.struts.tiles.taglib.InsertTag) _jspx_tagPool_tiles_insert_attribute_nobody.get(org.apache.struts.tiles.taglib.InsertTag.class);
    _jspx_th_tiles_insert_2.setPageContext(_jspx_page_context);
    _jspx_th_tiles_insert_2.setParent(null);
    _jspx_th_tiles_insert_2.setAttribute("extraSrv");
    int _jspx_eval_tiles_insert_2 = _jspx_th_tiles_insert_2.doStartTag();
    if (_jspx_th_tiles_insert_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_2);
      return true;
    }
    _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_2);
    return false;
  }

  private boolean _jspx_meth_tiles_insert_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insert
    org.apache.struts.tiles.taglib.InsertTag _jspx_th_tiles_insert_3 = (org.apache.struts.tiles.taglib.InsertTag) _jspx_tagPool_tiles_insert_attribute_nobody.get(org.apache.struts.tiles.taglib.InsertTag.class);
    _jspx_th_tiles_insert_3.setPageContext(_jspx_page_context);
    _jspx_th_tiles_insert_3.setParent(null);
    _jspx_th_tiles_insert_3.setAttribute("rabbitTalk");
    int _jspx_eval_tiles_insert_3 = _jspx_th_tiles_insert_3.doStartTag();
    if (_jspx_th_tiles_insert_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_3);
      return true;
    }
    _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_3);
    return false;
  }

  private boolean _jspx_meth_tiles_insert_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insert
    org.apache.struts.tiles.taglib.InsertTag _jspx_th_tiles_insert_4 = (org.apache.struts.tiles.taglib.InsertTag) _jspx_tagPool_tiles_insert_attribute_nobody.get(org.apache.struts.tiles.taglib.InsertTag.class);
    _jspx_th_tiles_insert_4.setPageContext(_jspx_page_context);
    _jspx_th_tiles_insert_4.setParent(null);
    _jspx_th_tiles_insert_4.setAttribute("manual");
    int _jspx_eval_tiles_insert_4 = _jspx_th_tiles_insert_4.doStartTag();
    if (_jspx_th_tiles_insert_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_4);
      return true;
    }
    _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_4);
    return false;
  }

  private boolean _jspx_meth_tiles_insert_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insert
    org.apache.struts.tiles.taglib.InsertTag _jspx_th_tiles_insert_5 = (org.apache.struts.tiles.taglib.InsertTag) _jspx_tagPool_tiles_insert_attribute_nobody.get(org.apache.struts.tiles.taglib.InsertTag.class);
    _jspx_th_tiles_insert_5.setPageContext(_jspx_page_context);
    _jspx_th_tiles_insert_5.setParent(null);
    _jspx_th_tiles_insert_5.setAttribute("links");
    int _jspx_eval_tiles_insert_5 = _jspx_th_tiles_insert_5.doStartTag();
    if (_jspx_th_tiles_insert_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_5);
      return true;
    }
    _jspx_tagPool_tiles_insert_attribute_nobody.reuse(_jspx_th_tiles_insert_5);
    return false;
  }
}
