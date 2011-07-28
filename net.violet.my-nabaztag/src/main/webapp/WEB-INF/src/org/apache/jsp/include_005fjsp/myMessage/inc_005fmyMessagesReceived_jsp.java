package org.apache.jsp.include_005fjsp.myMessage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import net.violet.platform.util.StaticTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.MyConstantes;

public final class inc_005fmyMessagesReceived_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(1);
    _jspx_dependants.add("/include_jsp/utils/inc_dico.jsp");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_type_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEmpty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_iterate_property_name_id;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_define_property_name_id_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_bean_write_property_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_empty_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_equal_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_greaterThan_value_property_name;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_logic_notEqual_value_property_name;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEmpty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_iterate_property_name_id = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_define_property_name_id_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_bean_write_property_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_empty_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_equal_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_greaterThan_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_logic_notEqual_value_property_name = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_bean_define_type_property_name_id_nobody.release();
    _jspx_tagPool_logic_notEmpty_property_name.release();
    _jspx_tagPool_logic_iterate_property_name_id.release();
    _jspx_tagPool_bean_define_property_name_id_nobody.release();
    _jspx_tagPool_bean_write_property_name_nobody.release();
    _jspx_tagPool_logic_empty_property_name.release();
    _jspx_tagPool_logic_equal_value_property_name.release();
    _jspx_tagPool_logic_greaterThan_value_property_name.release();
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

      out.write("\n\r\n\r\n");
 response.setContentType("text/html;charset=UTF-8"); 
      out.write("\r\n\n\r\n\r\n\r\n\r\n");
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n\r\n\n");
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
      out.write("\r\n\r\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_0 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_0.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_0.setParent(null);
      _jspx_th_bean_define_0.setName("myMessagesListForm");
      _jspx_th_bean_define_0.setProperty("index");
      _jspx_th_bean_define_0.setId("index");
      _jspx_th_bean_define_0.setType("Integer");
      int _jspx_eval_bean_define_0 = _jspx_th_bean_define_0.doStartTag();
      if (_jspx_th_bean_define_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_0);
      Integer index = null;
      index = (Integer) _jspx_page_context.findAttribute("index");
      out.write('\r');
      out.write('\n');
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_1 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_type_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      _jspx_th_bean_define_1.setPageContext(_jspx_page_context);
      _jspx_th_bean_define_1.setParent(null);
      _jspx_th_bean_define_1.setName("myMessagesListForm");
      _jspx_th_bean_define_1.setProperty("nabcast");
      _jspx_th_bean_define_1.setId("nabcast");
      _jspx_th_bean_define_1.setType("Integer");
      int _jspx_eval_bean_define_1 = _jspx_th_bean_define_1.doStartTag();
      if (_jspx_th_bean_define_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
        return;
      }
      _jspx_tagPool_bean_define_type_property_name_id_nobody.reuse(_jspx_th_bean_define_1);
      Integer nabcast = null;
      nabcast = (Integer) _jspx_page_context.findAttribute("nabcast");
      out.write("\r\n\r\n\n<div id=\"myPlayerMp3_r\" class=\"messagesPlayer\" ></div>\n\r\n<form action=\"../action/myMessagesList.do\" method=\"post\" id=\"update_list\">\r\n\t<input type=\"hidden\" name=\"action\" value=\"received\">\n\r\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" class=\"messagesListeTable\" id=\"messagesListeReceived\">\n\t\t<thead>\n\t\t\t<tr>\t\t\n\t\t\t\t<th class=\"date\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/date"));
      out.write("</th>\n\t\t\t\t<th class=\"sender\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/sentby"));
      out.write("</th>\n\t\t\t\t<th class=\"title\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/message_title"));
      out.write("</th>\n\t\t\t\t<th class=\"played\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/played"));
      out.write("</th>\n\t\t\t\t<th class=\"select\">&nbsp;</th>\n\t\t\t\t<th class=\"select\">&nbsp;</th>\t\t\t\n\t\t\t</tr>\n\t\t</thead>\n\t\t<tbody>\n\t\t");
      //  logic:notEmpty
      org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_notEmpty_0 = (org.apache.struts.taglib.logic.NotEmptyTag) _jspx_tagPool_logic_notEmpty_property_name.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
      _jspx_th_logic_notEmpty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_notEmpty_0.setParent(null);
      _jspx_th_logic_notEmpty_0.setName("myMessagesListForm");
      _jspx_th_logic_notEmpty_0.setProperty("listeMessages");
      int _jspx_eval_logic_notEmpty_0 = _jspx_th_logic_notEmpty_0.doStartTag();
      if (_jspx_eval_logic_notEmpty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t\t");
          //  logic:iterate
          org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_iterate_0 = (org.apache.struts.taglib.logic.IterateTag) _jspx_tagPool_logic_iterate_property_name_id.get(org.apache.struts.taglib.logic.IterateTag.class);
          _jspx_th_logic_iterate_0.setPageContext(_jspx_page_context);
          _jspx_th_logic_iterate_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEmpty_0);
          _jspx_th_logic_iterate_0.setName("myMessagesListForm");
          _jspx_th_logic_iterate_0.setProperty("listeMessages");
          _jspx_th_logic_iterate_0.setId("messageReceivedData");
          int _jspx_eval_logic_iterate_0 = _jspx_th_logic_iterate_0.doStartTag();
          if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
            java.lang.Object messageReceivedData = null;
            if (_jspx_eval_logic_iterate_0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
              out = _jspx_page_context.pushBody();
              _jspx_th_logic_iterate_0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
              _jspx_th_logic_iterate_0.doInitBody();
            }
            messageReceivedData = (java.lang.Object) _jspx_page_context.findAttribute("messageReceivedData");
            do {
              out.write("\n\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_2 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_2.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_2.setName("messageReceivedData");
              _jspx_th_bean_define_2.setProperty("id");
              _jspx_th_bean_define_2.setId("message_id");
              int _jspx_eval_bean_define_2 = _jspx_th_bean_define_2.doStartTag();
              if (_jspx_th_bean_define_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_2);
              java.lang.Object message_id = null;
              message_id = (java.lang.Object) _jspx_page_context.findAttribute("message_id");
              out.write("\n\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_3 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_3.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_3.setName("messageReceivedData");
              _jspx_th_bean_define_3.setProperty("userSenderId");
              _jspx_th_bean_define_3.setId("userSenderId");
              int _jspx_eval_bean_define_3 = _jspx_th_bean_define_3.doStartTag();
              if (_jspx_th_bean_define_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_3);
              java.lang.Object userSenderId = null;
              userSenderId = (java.lang.Object) _jspx_page_context.findAttribute("userSenderId");
              out.write("\n\t\t\t\t");
              //  bean:define
              org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_4 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
              _jspx_th_bean_define_4.setPageContext(_jspx_page_context);
              _jspx_th_bean_define_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
              _jspx_th_bean_define_4.setName("messageReceivedData");
              _jspx_th_bean_define_4.setProperty("url");
              _jspx_th_bean_define_4.setId("event_url");
              int _jspx_eval_bean_define_4 = _jspx_th_bean_define_4.doStartTag();
              if (_jspx_th_bean_define_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
                return;
              }
              _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_4);
              java.lang.Object event_url = null;
              event_url = (java.lang.Object) _jspx_page_context.findAttribute("event_url");
              out.write("\n\t\t\t\t\t<tr>\t\t\n\t\t\t\t\t\t<td>");
              if (_jspx_meth_bean_write_0(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("</td>\n\t\t\t\t\t\t<td><a onclick=\"TB_show('', 'myProfil.do?height=515&width=800&user_id=");
              out.print(userSenderId);
              out.write("')\" href=\"javascript:;\">");
              if (_jspx_meth_bean_write_1(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("</a></td>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t<a onclick=\"loadPersoPlayer('");
              out.print(event_url);
              out.write("', '100%', true, 'myPlayerMp3_r')\" title=\"Ecouter\" href=\"javascript:;\">\n\t\t\t\t\t\t");
              if (_jspx_meth_bean_write_2(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("\n\t\t\t\t\t\t</a>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td>");
              if (_jspx_meth_bean_write_3(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("x</td>\n\t\t\t\t\t\t<td width=\"1%\"><input class=\"genericBt\" type=\"button\" value=\"");
              out.print(DicoTools.dico(dico_lang, "myMessages/reply"));
              out.write("\" onclick=\"sendMsgTo('");
              if (_jspx_meth_bean_write_4(_jspx_th_logic_iterate_0, _jspx_page_context))
                return;
              out.write("');\" /></td>\n\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"checkListMsg\" value=\"");
              out.print(message_id);
              out.write("\" onclick=\"messages_selectMsg(this);\"></td>\n\t\t\t\t\t</tr>\n\t\t\t");
              int evalDoAfterBody = _jspx_th_logic_iterate_0.doAfterBody();
              messageReceivedData = (java.lang.Object) _jspx_page_context.findAttribute("messageReceivedData");
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
          out.write('\n');
          out.write('	');
          out.write('	');
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
      out.write('\n');
      out.write('	');
      out.write('	');
      //  logic:empty
      org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_empty_0 = (org.apache.struts.taglib.logic.EmptyTag) _jspx_tagPool_logic_empty_property_name.get(org.apache.struts.taglib.logic.EmptyTag.class);
      _jspx_th_logic_empty_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_empty_0.setParent(null);
      _jspx_th_logic_empty_0.setName("myMessagesListForm");
      _jspx_th_logic_empty_0.setProperty("listeMessages");
      int _jspx_eval_logic_empty_0 = _jspx_th_logic_empty_0.doStartTag();
      if (_jspx_eval_logic_empty_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n\t\t\t<tr><td colspan=\"10\">");
          out.print(DicoTools.dico(dico_lang, "main/no_message"));
          out.write("</td></tr>\n\t\t");
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
      out.write("\t\t\n\t\t</tbody>\n\t</table>\r\n\r\n\t<ul class=\"messages-list-func\">\r\n\t\t<li><a href=\"javascript:;\" onclick=\"messages_select_all('messagesListeReceived')\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/select_all"));
      out.write("</a></li>\r\n\t\t<li>\r\n\t\t\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_0 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_0.setParent(null);
      _jspx_th_logic_equal_0.setName("myMessagesListForm");
      _jspx_th_logic_equal_0.setProperty("nabcast");
      _jspx_th_logic_equal_0.setValue("0");
      int _jspx_eval_logic_equal_0 = _jspx_th_logic_equal_0.doStartTag();
      if (_jspx_eval_logic_equal_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t<a href=\"javascript:void(0);\" onclick=\"divChangeUrl('contentRecu' , '../action/myMessagesList.do?action=received&index=");
          out.print(index);
          out.write("&nabcast=1');\">");
          out.print(DicoTools.dico(dico_lang, "myMessages/display_nabcast"));
          out.write("</a>\r\n\t\t\t");
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
      out.write("\r\n\t\t\t\r\n\t\t\t");
      //  logic:equal
      org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_1 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
      _jspx_th_logic_equal_1.setPageContext(_jspx_page_context);
      _jspx_th_logic_equal_1.setParent(null);
      _jspx_th_logic_equal_1.setName("myMessagesListForm");
      _jspx_th_logic_equal_1.setProperty("nabcast");
      _jspx_th_logic_equal_1.setValue("1");
      int _jspx_eval_logic_equal_1 = _jspx_th_logic_equal_1.doStartTag();
      if (_jspx_eval_logic_equal_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t<a href=\"javascript:void(0);\" onclick=\"divChangeUrl('contentRecu' , '../action/myMessagesList.do?action=received&index=");
          out.print(index);
          out.write("&nabcast=0');\">");
          out.print(DicoTools.dico(dico_lang, "myMessages/no_display_nabcasts"));
          out.write("</a>\r\n\t\t\t");
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
      out.write("\r\n\t\t</li>\r\n\t\t<li><a href=\"myTerrier.do?onglet=blackList\" >");
      out.print(DicoTools.dico(dico_lang, "myMessages/display_blacklist"));
      out.write("</a></li>\r\n\t</ul>\r\n\t\r\n\t<div class=\"messages-select-action\">\r\n\t\t<select name=\"selectChoice\" style=\"width:120px;\">\r\n\t\t\t<!-- <option value=\"\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/action"));
      out.write("</option> -->\r\n\t\t\t<option value=\"archive_msg\" selected=\"selected\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/archive"));
      out.write("</option>\r\n\t\t\t<option value=\"delete_msg\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/delete"));
      out.write("</option>\r\n\t\t\t<option value=\"replay_msg\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/replay"));
      out.write("</option>\r\n\t\t\t<option value=\"blacklist\">");
      out.print(DicoTools.dico(dico_lang, "myMessages/blacklist"));
      out.write("</option>\r\n\t\t</select>\r\n\t\t<input type=\"button\" value=\"");
      out.print(DicoTools.dico(dico_lang, "myMessages/button_ok"));
      out.write("\" class=\"genericBt\" onclick=\"submitAjaxForm('update_list','contentRecu');\"/> \t\r\n\t</div>\r\n\r\n</form>\r\n\r\n\r\n\r\n<div class=\"paginate\">\r\n\t<ul>\r\n\t\t<form action=\"../action/myMessagesList.do\" name=\"pageSelector\" method=\"post\" >\r\n\t\t\t");
      //  logic:greaterThan
      org.apache.struts.taglib.logic.GreaterThanTag _jspx_th_logic_greaterThan_0 = (org.apache.struts.taglib.logic.GreaterThanTag) _jspx_tagPool_logic_greaterThan_value_property_name.get(org.apache.struts.taglib.logic.GreaterThanTag.class);
      _jspx_th_logic_greaterThan_0.setPageContext(_jspx_page_context);
      _jspx_th_logic_greaterThan_0.setParent(null);
      _jspx_th_logic_greaterThan_0.setName("myMessagesListForm");
      _jspx_th_logic_greaterThan_0.setProperty("nombre_pages");
      _jspx_th_logic_greaterThan_0.setValue("1");
      int _jspx_eval_logic_greaterThan_0 = _jspx_th_logic_greaterThan_0.doStartTag();
      if (_jspx_eval_logic_greaterThan_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_5 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_5.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_5.setId("page_indexD");
          _jspx_th_bean_define_5.setName("myMessagesListForm");
          _jspx_th_bean_define_5.setProperty("page_indexD");
          int _jspx_eval_bean_define_5 = _jspx_th_bean_define_5.doStartTag();
          if (_jspx_th_bean_define_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_5);
          java.lang.Object page_indexD = null;
          page_indexD = (java.lang.Object) _jspx_page_context.findAttribute("page_indexD");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_6 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_6.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_6.setId("page_indexMM");
          _jspx_th_bean_define_6.setName("myMessagesListForm");
          _jspx_th_bean_define_6.setProperty("page_indexMM");
          int _jspx_eval_bean_define_6 = _jspx_th_bean_define_6.doStartTag();
          if (_jspx_th_bean_define_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_6);
          java.lang.Object page_indexMM = null;
          page_indexMM = (java.lang.Object) _jspx_page_context.findAttribute("page_indexMM");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_7 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_7.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_7.setId("page_indexM");
          _jspx_th_bean_define_7.setName("myMessagesListForm");
          _jspx_th_bean_define_7.setProperty("page_indexM");
          int _jspx_eval_bean_define_7 = _jspx_th_bean_define_7.doStartTag();
          if (_jspx_th_bean_define_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_7);
          java.lang.Object page_indexM = null;
          page_indexM = (java.lang.Object) _jspx_page_context.findAttribute("page_indexM");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_8 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_8.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_8.setId("page_index");
          _jspx_th_bean_define_8.setName("myMessagesListForm");
          _jspx_th_bean_define_8.setProperty("page_index");
          int _jspx_eval_bean_define_8 = _jspx_th_bean_define_8.doStartTag();
          if (_jspx_th_bean_define_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_8);
          java.lang.Object page_index = null;
          page_index = (java.lang.Object) _jspx_page_context.findAttribute("page_index");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_9 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_9.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_9.setId("page_indexP");
          _jspx_th_bean_define_9.setName("myMessagesListForm");
          _jspx_th_bean_define_9.setProperty("page_indexP");
          int _jspx_eval_bean_define_9 = _jspx_th_bean_define_9.doStartTag();
          if (_jspx_th_bean_define_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_9);
          java.lang.Object page_indexP = null;
          page_indexP = (java.lang.Object) _jspx_page_context.findAttribute("page_indexP");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_10 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_10.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_10.setId("page_indexPP");
          _jspx_th_bean_define_10.setName("myMessagesListForm");
          _jspx_th_bean_define_10.setProperty("page_indexPP");
          int _jspx_eval_bean_define_10 = _jspx_th_bean_define_10.doStartTag();
          if (_jspx_th_bean_define_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_10);
          java.lang.Object page_indexPP = null;
          page_indexPP = (java.lang.Object) _jspx_page_context.findAttribute("page_indexPP");
          out.write("\r\n\t\t\t\t");
          //  bean:define
          org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_define_11 = (org.apache.struts.taglib.bean.DefineTag) _jspx_tagPool_bean_define_property_name_id_nobody.get(org.apache.struts.taglib.bean.DefineTag.class);
          _jspx_th_bean_define_11.setPageContext(_jspx_page_context);
          _jspx_th_bean_define_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
          _jspx_th_bean_define_11.setId("page_indexF");
          _jspx_th_bean_define_11.setName("myMessagesListForm");
          _jspx_th_bean_define_11.setProperty("page_indexF");
          int _jspx_eval_bean_define_11 = _jspx_th_bean_define_11.doStartTag();
          if (_jspx_th_bean_define_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
            _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
            return;
          }
          _jspx_tagPool_bean_define_property_name_id_nobody.reuse(_jspx_th_bean_define_11);
          java.lang.Object page_indexF = null;
          page_indexF = (java.lang.Object) _jspx_page_context.findAttribute("page_indexF");
          out.write("\r\n\r\n\t\t\t<li>\r\n\t\t\t\t<a href=\"javascript:;\" onclick=\"messagesChangePage('contentRecu', ");
          if (_jspx_meth_bean_write_5(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write(")\"> &lt;&lt; </a>\r\n\t\t\t</li>\r\n\t\t\t\r\n\t\t\t<li>\r\n\t\t\t\t<a href=\"javascript:;\" onclick=\"messagesChangePage('contentRecu', ");
          if (_jspx_meth_bean_write_6(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write(")\"> &lt; </a>\r\n\t\t\t</li>\r\n\r\n\t\t\t");
          if (_jspx_meth_logic_notEqual_0(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\r\n\t\t\t");
          if (_jspx_meth_logic_notEqual_1(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write("\r\n\r\n\t\t\t<li class=\"current\">\r\n\t\t\t\t<a href=\"javascript:;\">");
          if (_jspx_meth_bean_write_11(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write("</a>\r\n\t\t\t</li>\r\n\r\n\t\t\t");
          if (_jspx_meth_logic_notEqual_2(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\r\n\t\t\t");
          if (_jspx_meth_logic_notEqual_3(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write("\r\n\r\n\t\t\t<li>\r\n\t\t\t\t");
          if (_jspx_meth_logic_equal_2(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write("\r\n\t\t\t\t");
          if (_jspx_meth_logic_notEqual_4(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write("\t\t\t\t\t\t\r\n\t\t\t</li>\r\n\t\t\t\r\n\t\t\t<li>\r\n\t\t\t\t<a href=\"javascript:;\" onclick=\"messagesChangePage('contentRecu', ");
          if (_jspx_meth_bean_write_18(_jspx_th_logic_greaterThan_0, _jspx_page_context))
            return;
          out.write(")\"> &gt;&gt; </a>\r\n\t\t\t</li>\r\n\t\t\t\r\n\t\t\t");
          int evalDoAfterBody = _jspx_th_logic_greaterThan_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_greaterThan_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_0);
        return;
      }
      _jspx_tagPool_logic_greaterThan_value_property_name.reuse(_jspx_th_logic_greaterThan_0);
      out.write("\r\n\t\t</form>\r\n\t</ul>\r\n\t\r\n</div>\r\n\r\n\r\n\r\n<script type=\"text/javascript\">\r\n\t\r\n\tgetNewMessages();\r\n\r\n\tmessageListColorization(\"messagesListeReceived\");\r\n\r\n\t// affiche le lecteur dés le départ\r\n\t//loadPersoPlayer('', '100%', true, 'myPlayerMp3_r' );\r\n\r\n\r\n\tvar msg = \"\";\r\n\tvar col = gErrorColor;\r\n\r\n\t");
      if (_jspx_meth_logic_equal_3(_jspx_page_context))
        return;
      out.write("\r\n\t\t\r\n\t");
      if (_jspx_meth_logic_equal_4(_jspx_page_context))
        return;
      out.write("\r\n\t\r\n\t");
      if (_jspx_meth_logic_equal_5(_jspx_page_context))
        return;
      out.write("\r\n\r\n</script>\t\r\n");
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

  private boolean _jspx_meth_bean_write_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_0 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_0.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_bean_write_0.setName("messageReceivedData");
    _jspx_th_bean_write_0.setProperty("dateOfDelivery");
    int _jspx_eval_bean_write_0 = _jspx_th_bean_write_0.doStartTag();
    if (_jspx_th_bean_write_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_0);
    return false;
  }

  private boolean _jspx_meth_bean_write_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_1 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_1.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_bean_write_1.setName("messageReceivedData");
    _jspx_th_bean_write_1.setProperty("sender_name");
    int _jspx_eval_bean_write_1 = _jspx_th_bean_write_1.doStartTag();
    if (_jspx_th_bean_write_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_1);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_1);
    return false;
  }

  private boolean _jspx_meth_bean_write_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_2 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_2.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_bean_write_2.setName("messageReceivedData");
    _jspx_th_bean_write_2.setProperty("title");
    int _jspx_eval_bean_write_2 = _jspx_th_bean_write_2.doStartTag();
    if (_jspx_th_bean_write_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_2);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_2);
    return false;
  }

  private boolean _jspx_meth_bean_write_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_3 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_3.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_bean_write_3.setName("messageReceivedData");
    _jspx_th_bean_write_3.setProperty("nbPlayed");
    int _jspx_eval_bean_write_3 = _jspx_th_bean_write_3.doStartTag();
    if (_jspx_th_bean_write_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_3);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_3);
    return false;
  }

  private boolean _jspx_meth_bean_write_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_iterate_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_4 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_4.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_iterate_0);
    _jspx_th_bean_write_4.setName("messageReceivedData");
    _jspx_th_bean_write_4.setProperty("sender_name");
    int _jspx_eval_bean_write_4 = _jspx_th_bean_write_4.doStartTag();
    if (_jspx_th_bean_write_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_4);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_4);
    return false;
  }

  private boolean _jspx_meth_bean_write_5(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_5 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_5.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_5.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_bean_write_5.setName("myMessagesListForm");
    _jspx_th_bean_write_5.setProperty("page_indexD");
    int _jspx_eval_bean_write_5 = _jspx_th_bean_write_5.doStartTag();
    if (_jspx_th_bean_write_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_5);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_5);
    return false;
  }

  private boolean _jspx_meth_bean_write_6(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_6 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_6.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_6.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_bean_write_6.setName("myMessagesListForm");
    _jspx_th_bean_write_6.setProperty("page_indexM");
    int _jspx_eval_bean_write_6 = _jspx_th_bean_write_6.doStartTag();
    if (_jspx_th_bean_write_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_6);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_6);
    return false;
  }

  private boolean _jspx_meth_logic_notEqual_0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_0 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_0.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_logic_notEqual_0.setName("myMessagesListForm");
    _jspx_th_logic_notEqual_0.setProperty("page_indexMM");
    _jspx_th_logic_notEqual_0.setValue("0");
    int _jspx_eval_logic_notEqual_0 = _jspx_th_logic_notEqual_0.doStartTag();
    if (_jspx_eval_logic_notEqual_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t<li>\r\n\t\t\t\t\t<a href=\"javascript:;\" onclick=\"messagesChangePage('contentRecu', ");
        if (_jspx_meth_bean_write_7(_jspx_th_logic_notEqual_0, _jspx_page_context))
          return true;
        out.write(")\"> ");
        if (_jspx_meth_bean_write_8(_jspx_th_logic_notEqual_0, _jspx_page_context))
          return true;
        out.write(" </a>\r\n\t\t\t\t</li>\r\n\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_notEqual_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_0);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_0);
    return false;
  }

  private boolean _jspx_meth_bean_write_7(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_7 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_7.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_7.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
    _jspx_th_bean_write_7.setName("myMessagesListForm");
    _jspx_th_bean_write_7.setProperty("page_indexMM");
    int _jspx_eval_bean_write_7 = _jspx_th_bean_write_7.doStartTag();
    if (_jspx_th_bean_write_7.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_7);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_7);
    return false;
  }

  private boolean _jspx_meth_bean_write_8(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_8 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_8.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_8.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_0);
    _jspx_th_bean_write_8.setName("myMessagesListForm");
    _jspx_th_bean_write_8.setProperty("page_AffIndexMM");
    int _jspx_eval_bean_write_8 = _jspx_th_bean_write_8.doStartTag();
    if (_jspx_th_bean_write_8.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_8);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_8);
    return false;
  }

  private boolean _jspx_meth_logic_notEqual_1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_1 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_1.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_logic_notEqual_1.setName("myMessagesListForm");
    _jspx_th_logic_notEqual_1.setProperty("page_indexM");
    _jspx_th_logic_notEqual_1.setValue("0");
    int _jspx_eval_logic_notEqual_1 = _jspx_th_logic_notEqual_1.doStartTag();
    if (_jspx_eval_logic_notEqual_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t<li>\r\n\t\t\t\t\t<a href=\"javascript:;\" onclick=\"messagesChangePage('contentRecu', ");
        if (_jspx_meth_bean_write_9(_jspx_th_logic_notEqual_1, _jspx_page_context))
          return true;
        out.write(")\"> ");
        if (_jspx_meth_bean_write_10(_jspx_th_logic_notEqual_1, _jspx_page_context))
          return true;
        out.write(" </a>\r\n\t\t\t\t</li>\r\n\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_notEqual_1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_1);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_1);
    return false;
  }

  private boolean _jspx_meth_bean_write_9(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_9 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_9.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_9.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
    _jspx_th_bean_write_9.setName("myMessagesListForm");
    _jspx_th_bean_write_9.setProperty("page_indexM");
    int _jspx_eval_bean_write_9 = _jspx_th_bean_write_9.doStartTag();
    if (_jspx_th_bean_write_9.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_9);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_9);
    return false;
  }

  private boolean _jspx_meth_bean_write_10(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_1, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_10 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_10.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_10.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_1);
    _jspx_th_bean_write_10.setName("myMessagesListForm");
    _jspx_th_bean_write_10.setProperty("page_AffIndexM");
    int _jspx_eval_bean_write_10 = _jspx_th_bean_write_10.doStartTag();
    if (_jspx_th_bean_write_10.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_10);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_10);
    return false;
  }

  private boolean _jspx_meth_bean_write_11(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_11 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_11.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_11.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_bean_write_11.setName("myMessagesListForm");
    _jspx_th_bean_write_11.setProperty("page_AffIndex");
    int _jspx_eval_bean_write_11 = _jspx_th_bean_write_11.doStartTag();
    if (_jspx_th_bean_write_11.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_11);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_11);
    return false;
  }

  private boolean _jspx_meth_logic_notEqual_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_2 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_logic_notEqual_2.setName("myMessagesListForm");
    _jspx_th_logic_notEqual_2.setProperty("page_indexP");
    _jspx_th_logic_notEqual_2.setValue("0");
    int _jspx_eval_logic_notEqual_2 = _jspx_th_logic_notEqual_2.doStartTag();
    if (_jspx_eval_logic_notEqual_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t<li>\r\n\t\t\t\t\t<a href=\"javascript:;\" onclick=\"messagesChangePage('contentRecu', ");
        if (_jspx_meth_bean_write_12(_jspx_th_logic_notEqual_2, _jspx_page_context))
          return true;
        out.write(")\"> ");
        if (_jspx_meth_bean_write_13(_jspx_th_logic_notEqual_2, _jspx_page_context))
          return true;
        out.write("</a>\r\n\t\t\t\t</li>\r\n\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_notEqual_2.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_2);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_2);
    return false;
  }

  private boolean _jspx_meth_bean_write_12(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_12 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_12.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_12.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_2);
    _jspx_th_bean_write_12.setName("myMessagesListForm");
    _jspx_th_bean_write_12.setProperty("page_indexP");
    int _jspx_eval_bean_write_12 = _jspx_th_bean_write_12.doStartTag();
    if (_jspx_th_bean_write_12.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_12);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_12);
    return false;
  }

  private boolean _jspx_meth_bean_write_13(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_13 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_13.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_13.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_2);
    _jspx_th_bean_write_13.setName("myMessagesListForm");
    _jspx_th_bean_write_13.setProperty("page_AffIndexP");
    int _jspx_eval_bean_write_13 = _jspx_th_bean_write_13.doStartTag();
    if (_jspx_th_bean_write_13.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_13);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_13);
    return false;
  }

  private boolean _jspx_meth_logic_notEqual_3(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_3 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_3.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_3.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_logic_notEqual_3.setName("myMessagesListForm");
    _jspx_th_logic_notEqual_3.setProperty("page_indexPP");
    _jspx_th_logic_notEqual_3.setValue("0");
    int _jspx_eval_logic_notEqual_3 = _jspx_th_logic_notEqual_3.doStartTag();
    if (_jspx_eval_logic_notEqual_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t<li>\r\n\t\t\t\t\t<a href=\"javascript:;\" onclick=\"messagesChangePage('contentRecu', ");
        if (_jspx_meth_bean_write_14(_jspx_th_logic_notEqual_3, _jspx_page_context))
          return true;
        out.write(")\"> ");
        if (_jspx_meth_bean_write_15(_jspx_th_logic_notEqual_3, _jspx_page_context))
          return true;
        out.write("</a>\r\n\t\t\t\t</li>\r\n\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_notEqual_3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_3);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_3);
    return false;
  }

  private boolean _jspx_meth_bean_write_14(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_14 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_14.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_14.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_3);
    _jspx_th_bean_write_14.setName("myMessagesListForm");
    _jspx_th_bean_write_14.setProperty("page_indexPP");
    int _jspx_eval_bean_write_14 = _jspx_th_bean_write_14.doStartTag();
    if (_jspx_th_bean_write_14.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_14);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_14);
    return false;
  }

  private boolean _jspx_meth_bean_write_15(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_3, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_15 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_15.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_15.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_3);
    _jspx_th_bean_write_15.setName("myMessagesListForm");
    _jspx_th_bean_write_15.setProperty("page_AffIndexPP");
    int _jspx_eval_bean_write_15 = _jspx_th_bean_write_15.doStartTag();
    if (_jspx_th_bean_write_15.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_15);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_15);
    return false;
  }

  private boolean _jspx_meth_logic_equal_2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_2 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_2.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_logic_equal_2.setName("myMessagesListForm");
    _jspx_th_logic_equal_2.setProperty("page_indexP");
    _jspx_th_logic_equal_2.setValue("0");
    int _jspx_eval_logic_equal_2 = _jspx_th_logic_equal_2.doStartTag();
    if (_jspx_eval_logic_equal_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t<a href=\"javascript:;\" onclick=\"messagesChangePage('contentRecu', ");
        if (_jspx_meth_bean_write_16(_jspx_th_logic_equal_2, _jspx_page_context))
          return true;
        out.write(")\"> &gt; </a>\r\n\t\t\t\t");
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

  private boolean _jspx_meth_bean_write_16(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_equal_2, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_16 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_16.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_16.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_equal_2);
    _jspx_th_bean_write_16.setName("myMessagesListForm");
    _jspx_th_bean_write_16.setProperty("page_indexF");
    int _jspx_eval_bean_write_16 = _jspx_th_bean_write_16.doStartTag();
    if (_jspx_th_bean_write_16.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_16);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_16);
    return false;
  }

  private boolean _jspx_meth_logic_notEqual_4(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:notEqual
    org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_notEqual_4 = (org.apache.struts.taglib.logic.NotEqualTag) _jspx_tagPool_logic_notEqual_value_property_name.get(org.apache.struts.taglib.logic.NotEqualTag.class);
    _jspx_th_logic_notEqual_4.setPageContext(_jspx_page_context);
    _jspx_th_logic_notEqual_4.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_logic_notEqual_4.setName("myMessagesListForm");
    _jspx_th_logic_notEqual_4.setProperty("page_indexP");
    _jspx_th_logic_notEqual_4.setValue("0");
    int _jspx_eval_logic_notEqual_4 = _jspx_th_logic_notEqual_4.doStartTag();
    if (_jspx_eval_logic_notEqual_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\t\t\t\t<a href=\"javascript:;\" onclick=\"messagesChangePage('contentRecu', ");
        if (_jspx_meth_bean_write_17(_jspx_th_logic_notEqual_4, _jspx_page_context))
          return true;
        out.write(")\"> &gt; </a>\r\n\t\t\t\t");
        int evalDoAfterBody = _jspx_th_logic_notEqual_4.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_notEqual_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_4);
      return true;
    }
    _jspx_tagPool_logic_notEqual_value_property_name.reuse(_jspx_th_logic_notEqual_4);
    return false;
  }

  private boolean _jspx_meth_bean_write_17(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_notEqual_4, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_17 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_17.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_17.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_notEqual_4);
    _jspx_th_bean_write_17.setName("myMessagesListForm");
    _jspx_th_bean_write_17.setProperty("page_indexP");
    int _jspx_eval_bean_write_17 = _jspx_th_bean_write_17.doStartTag();
    if (_jspx_th_bean_write_17.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_17);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_17);
    return false;
  }

  private boolean _jspx_meth_bean_write_18(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_greaterThan_0, PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  bean:write
    org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_write_18 = (org.apache.struts.taglib.bean.WriteTag) _jspx_tagPool_bean_write_property_name_nobody.get(org.apache.struts.taglib.bean.WriteTag.class);
    _jspx_th_bean_write_18.setPageContext(_jspx_page_context);
    _jspx_th_bean_write_18.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_greaterThan_0);
    _jspx_th_bean_write_18.setName("myMessagesListForm");
    _jspx_th_bean_write_18.setProperty("page_indexF");
    int _jspx_eval_bean_write_18 = _jspx_th_bean_write_18.doStartTag();
    if (_jspx_th_bean_write_18.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_18);
      return true;
    }
    _jspx_tagPool_bean_write_property_name_nobody.reuse(_jspx_th_bean_write_18);
    return false;
  }

  private boolean _jspx_meth_logic_equal_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_3 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_3.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_3.setParent(null);
    _jspx_th_logic_equal_3.setName("myMessagesListForm");
    _jspx_th_logic_equal_3.setProperty("errorMsg");
    _jspx_th_logic_equal_3.setValue("now_in_blackList");
    int _jspx_eval_logic_equal_3 = _jspx_th_logic_equal_3.doStartTag();
    if (_jspx_eval_logic_equal_3 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\tmsg = msg_txt['now_in_blackList'];\t\r\n\t\t$(\"div.mainTabBody\").msgPopup(msg, col, 4000);\r\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_3.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_3);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_3);
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
    _jspx_th_logic_equal_4.setName("myMessagesListForm");
    _jspx_th_logic_equal_4.setProperty("errorMsg");
    _jspx_th_logic_equal_4.setValue("already_in_blackList");
    int _jspx_eval_logic_equal_4 = _jspx_th_logic_equal_4.doStartTag();
    if (_jspx_eval_logic_equal_4 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\tmsg = msg_txt['already_in_blackList'];\t\r\n\t\t$(\"div.mainTabBody\").msgPopup(msg, col, 4000);\r\n\t");
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

  private boolean _jspx_meth_logic_equal_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_equal_5 = (org.apache.struts.taglib.logic.EqualTag) _jspx_tagPool_logic_equal_value_property_name.get(org.apache.struts.taglib.logic.EqualTag.class);
    _jspx_th_logic_equal_5.setPageContext(_jspx_page_context);
    _jspx_th_logic_equal_5.setParent(null);
    _jspx_th_logic_equal_5.setName("myMessagesListForm");
    _jspx_th_logic_equal_5.setProperty("errorMsg");
    _jspx_th_logic_equal_5.setValue("cannot_autoblackList");
    int _jspx_eval_logic_equal_5 = _jspx_th_logic_equal_5.doStartTag();
    if (_jspx_eval_logic_equal_5 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n\t\tmsg = msg_txt['cannot_autoblackList'];\t\r\n\t\t$(\"div.mainTabBody\").msgPopup(msg, col, 4000);\r\n\t");
        int evalDoAfterBody = _jspx_th_logic_equal_5.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_logic_equal_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_5);
      return true;
    }
    _jspx_tagPool_logic_equal_value_property_name.reuse(_jspx_th_logic_equal_5);
    return false;
  }
}
