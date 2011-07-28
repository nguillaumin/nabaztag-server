package org.apache.jsp.include_005fjsp.utils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fjavascripts_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_value_type_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_write_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_match_value_scope_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notMatch_value_scope_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_value_type_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_write_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_match_value_scope_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notMatch_value_scope_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_value_type_id_nobody.release();
    _jspx_tagPool_logic_equal_value_name.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_logic_greaterThan_value_property_name.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_bean_write_property_name_nobody.release();
    _jspx_tagPool_logic_match_value_scope_name.release();
    _jspx_tagPool_logic_notMatch_value_scope_name.release();
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n");

	String iAmABatard = "false";

      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_value_type_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setId("isBatard");
      _jspx_th_bean_define_0.setType("String");
      _jspx_th_bean_define_0.setValue(iAmABatard);
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_value_type_id_nobody.reuse(_jspx_th_bean_define_0);
      String isBatard = null;
      isBatard = (String) _jspx_page_context.findAttribute("isBatard");
      out.write('\n');
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_0.setParent(null);
      _jspx_th_logic_equal_0.setName("page_title");
      _jspx_th_logic_equal_0.setValue("myNewAccount");
      int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
      if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_0);
          _jspx_th_bean_define_1.setName("myNewAccountForm");
          _jspx_th_bean_define_1.setProperty("userData");
          _jspx_th_bean_define_1.setId("userData");
          int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
          if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\n');
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
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_1.setParent(null);
      _jspx_th_logic_equal_1.setName("page_title");
      _jspx_th_logic_equal_1.setValue("myMessages");
      int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
      if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_1);
          _jspx_th_bean_define_2.setName("myMessagesForm");
          _jspx_th_bean_define_2.setProperty("userData");
          _jspx_th_bean_define_2.setId("userData");
          int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
          if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\n');
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
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_2.setParent(null);
      _jspx_th_logic_equal_2.setName("page_title");
      _jspx_th_logic_equal_2.setValue("myTerrier");
      int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
      if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
          _jspx_th_bean_define_3.setName("myTerrierForm");
          _jspx_th_bean_define_3.setProperty("userData");
          _jspx_th_bean_define_3.setId("userData");
          int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
          if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\n');
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
      out.write('\n');
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_3.setParent(null);
      _jspx_th_logic_equal_3.setName("page_title");
      _jspx_th_logic_equal_3.setValue("myNablife");
      int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
      if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write('\n');
          out.write('	');
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_3);
          _jspx_th_bean_define_4.setName("myNablifeForm");
          _jspx_th_bean_define_4.setProperty("userData");
          _jspx_th_bean_define_4.setId("userData");
          int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
          if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
          java.lang.Object userData = null;
          userData = (java.lang.Object) _jspx_page_context.findAttribute("userData");
          out.write('\n');
          int evalDoAfterBody = _jspx_th_logic_equal_3.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_equal_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_3);
        return;
      }
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_3);
      out.write('\n');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_5.setParent(null);
      _jspx_th_bean_define_5.setName("userData");
      _jspx_th_bean_define_5.setProperty("user_24");
      _jspx_th_bean_define_5.setId("user_24");
      int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
      if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
        return;
      }
      _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
      java.lang.Object user_24 = null;
      user_24 = (java.lang.Object) _jspx_page_context.findAttribute("user_24");
      out.write('\n');
      out.write('\n');

	/* fichier d'inclusion de tous les javascripts */

      out.write("\n<script type=\"text/javascript\">\n\tcurrentTab=\"\";/* bidouille IE*/\n\n\tvar nabaztag = {};\n\tnabaztag.constantes = {};\n\tnabaztag.constantes.DOMAIN \t\t\t= \"");
      out.print(MyConstantes.DOMAIN);
      out.write("\";\n\tnabaztag.constantes.STREAM_SERVER \t= \"");
      out.print(MyConstantes.STREAM_SERVER);
      out.write("\";\n\n\tnabaztag.constantes.H24 \t\t\t= ");
      out.print(user_24);
      out.write(";\n\n\t");
      if (_jspx_meth_logic_greaterThan_0(_jspx_page_context))
        return;
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_4(_jspx_page_context))
        return;
      out.write("\n\n\n\tnabaztag.constantes.OBJECTID\t\t= ");
      if (_jspx_meth_bean_write_0(_jspx_page_context))
        return;
      out.write(";\n\n\tnabaztag.constantes.BAD_CONNECTED_OBJECT\t= ");
      if (_jspx_meth_logic_match_0(_jspx_page_context))
        return;
      if (_jspx_meth_logic_notMatch_0(_jspx_page_context))
        return;
      out.write(";\n\n</script>\n\n");

	// en local et sur DEV, on utilise les javascripts tel quel
	if (MyConstantes.DOMAIN.equals("localhost") || MyConstantes.DOMAIN.equals("192.168.1.11")) {

      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/jquery-1.2.1.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.compat-1.1.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.color.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.easing.1.1.1.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery-select.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/thickbox.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.form.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.tooltip.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.block.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jqModal.js\"></script>\n");
      out.write("\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/dom-creator.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/datePicker.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.jmp3.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.dimensions.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/jQuery/plugins/jquery.mousewheel.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/cdl-conf.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/platform-detection.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/vars.js\"></script>\n\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/swfobject-1.5.1.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/conftools/jstools.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag_validate.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag_blocks.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag_jqueryModules.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/common/mynabaztag-nabThem.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myHome/mynabaztag-register.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myTerrier/mynabaztag-terrier.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myNablife/mynabaztag-nablife.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myNablife/nablife-validate.js\"></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src=\"../include_js/myNablife/bloc.services.js\"></script>\n\t");
      out.write('\n');
      out.write('\n');

	// sinon on utilise les versions "minimized" ( necessite un ANT, target -> jsy_nabaztag )
	} else {

      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.jquery.js?v=1.2.1a'></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.conftools.js?v=1.2'></script>\n\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.common.js?v=1.1'></script>\n\n\t");
      if (_jspx_meth_logic_equal_5(_jspx_page_context))
        return;
      out.write('\n');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_6(_jspx_page_context))
        return;
      out.write('\n');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_7(_jspx_page_context))
        return;
      out.write('\n');
      out.write('\n');
      out.write('	');
      if (_jspx_meth_logic_equal_8(_jspx_page_context))
        return;
      out.write('\n');
      out.write('\n');
 } 
      out.write('\n');
      out.write('\n');
      out.write('	');
      out.write("\n\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/mynabaztag_text.jsp?v=1.4&l=");
      out.print(dico_lang.getIsoCode());
      out.write("'></script>\n\n\t");
      //  logic:match
      org.apache.struts.taglib.logic.MatchTag _jspx_th_logic_match_1 = (org.apache.struts.taglib.logic.MatchTag) _jspx_tagPool_logic_match_value_scope_name.get(org.apache.struts.taglib.logic.MatchTag.class);
      _jspx_th_logic_match_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_match_1.setParent(null);
      _jspx_th_logic_match_1.setScope("page");
      _jspx_th_logic_match_1.setName("isBatard");
      _jspx_th_logic_match_1.setValue("true");
      int _jspx_eval_logic_match_1 = _jspx_th_logic_match_1.doStartTag();
      if (_jspx_eval_logic_match_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t<script>\n\t\t\t");
          out.write("\n\t\t\tstartOnlineStatusCheck(");
          out.print(user_id);
          out.write(");\n\t\t</script>\n\t");
          int evalDoAfterBody = _jspx_th_logic_match_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_match_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_1);
        return;
      }
      _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_1);
      out.write('\n');
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

  private boolean _jspx_meth_logic_greaterThan_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:greaterThan
    org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
    _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_greaterThan_0.setParent(null);
    _jspx_th_logic_greaterThan_0.setName("userData");
    _jspx_th_logic_greaterThan_0.setProperty("user_id");
    _jspx_th_logic_greaterThan_0.setValue("0");
    int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
    if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\tnabaztag.constantes.ISLOG \t\t= true;\n\t");
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

  private boolean _jspx_meth_logic_equal_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_4 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_4.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_4.setParent(null);
    _jspx_th_logic_equal_4.setName("userData");
    _jspx_th_logic_equal_4.setProperty("user_id");
    _jspx_th_logic_equal_4.setValue("0");
    int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
    if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\tnabaztag.constantes.ISLOG \t\t= false;\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_4);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_4);
    return false;
  }

  private boolean _jspx_meth_bean_write_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_0 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_0.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_0.setParent(null);
    _jspx_th_bean_write_0.setName("userData");
    _jspx_th_bean_write_0.setProperty("userWithAtLeastOneObject");
    int _jspx_eval_bean_write_0 = _jspx_th_bean_write_0.doStartTag();
    if (_jspx_th_bean_write_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
    return false;
  }

  private boolean _jspx_meth_logic_match_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:match
    org.apache.struts.taglib.logic.MatchTag _jspx_th_logic_match_0 = (org.apache.struts.taglib.logic.MatchTag) _jspx_tagPool_logic_match_value_scope_name.get(org.apache.struts.taglib.logic.MatchTag.class);
    _jspx_th_logic_match_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_match_0.setParent(null);
    _jspx_th_logic_match_0.setScope("page");
    _jspx_th_logic_match_0.setName("isBatard");
    _jspx_th_logic_match_0.setValue("true");
    int _jspx_eval_logic_match_0 = _jspx_th_logic_match_0.doStartTag();
    if (_jspx_eval_logic_match_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("true");
        int evalDoAfterBody = _jspx_th_logic_match_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_match_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_0);
      return true;
    }
    _jspx_tagPool_logic_match_value_scope_name.reuse(_jspx_th_logic_match_0);
    return false;
  }

  private boolean _jspx_meth_logic_notMatch_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notMatch
    org.apache.struts.taglib.logic.NotMatchTag _jspx_th_logic_notMatch_0 = (org.apache.struts.taglib.logic.NotMatchTag) _jspx_tagPool_logic_notMatch_value_scope_name.get(org.apache.struts.taglib.logic.NotMatchTag.class);
    _jspx_th_logic_notMatch_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_notMatch_0.setParent(null);
    _jspx_th_logic_notMatch_0.setScope("page");
    _jspx_th_logic_notMatch_0.setName("isBatard");
    _jspx_th_logic_notMatch_0.setValue("true");
    int _jspx_eval_logic_notMatch_0 = _jspx_th_logic_notMatch_0.doStartTag();
    if (_jspx_eval_logic_notMatch_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("false");
        int evalDoAfterBody = _jspx_th_logic_notMatch_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notMatch_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notMatch_value_scope_name.reuse(_jspx_th_logic_notMatch_0);
      return true;
    }
    _jspx_tagPool_logic_notMatch_value_scope_name.reuse(_jspx_th_logic_notMatch_0);
    return false;
  }

  private boolean _jspx_meth_logic_equal_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_5.setParent(null);
    _jspx_th_logic_equal_5.setName("page_title");
    _jspx_th_logic_equal_5.setValue("myNewAccount");
    int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
    if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.home.js?v=1.1'></script>\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_5);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_5);
    return false;
  }

  private boolean _jspx_meth_logic_equal_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_6 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_6.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_6.setParent(null);
    _jspx_th_logic_equal_6.setName("page_title");
    _jspx_th_logic_equal_6.setValue("myNablife");
    int _jspx_eval_logic_equal_6 = _jspx_th_logic_equal_6.doStartTag();
    if (_jspx_eval_logic_equal_6 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.nablife.js?v=1.1'></script>\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_6.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_6);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_6);
    return false;
  }

  private boolean _jspx_meth_logic_equal_7(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_7 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_7.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_7.setParent(null);
    _jspx_th_logic_equal_7.setName("page_title");
    _jspx_th_logic_equal_7.setValue("myTerrier");
    int _jspx_eval_logic_equal_7 = _jspx_th_logic_equal_7.doStartTag();
    if (_jspx_eval_logic_equal_7 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\n\t\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.terrier.js?v=1.1'></script>\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_7.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_7);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_7);
    return false;
  }

  private boolean _jspx_meth_logic_equal_8(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_8 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_8.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_8.setParent(null);
    _jspx_th_logic_equal_8.setName("page_title");
    _jspx_th_logic_equal_8.setValue("myMessages");
    int _jspx_eval_logic_equal_8 = _jspx_th_logic_equal_8.doStartTag();
    if (_jspx_eval_logic_equal_8 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write('\n');
        out.write('	');
        out.write('	');
        out.write("\n\t\t<script charset=\"utf-8\" type=\"text/javascript\" src='../include_js/dist/nabaztag.home.js?v=1.1'></script>\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_8.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_8);
      return true;
    }
    _jspx_tagPool_logic_equal_value_name.reuse(_jspx_th_logic_equal_8);
    return false;
  }
}
