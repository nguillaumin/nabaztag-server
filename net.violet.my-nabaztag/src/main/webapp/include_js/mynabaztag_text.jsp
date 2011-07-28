
<%@page import="net.violet.platform.dataobjects.ObjectLangData"%>
<%@page import="net.violet.platform.datamodel.factories.Factories"%>
<%@page import="net.violet.platform.datamodel.Lang"%>
<%@page import="net.violet.platform.datamodel.DicoImpl"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@page import="net.violet.platform.util.ConvertTools"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>
<%
	String lang = request.getParameter("l");
	
	if (null == lang) 
		lang = ObjectLangData.DEFAULT_OBJECT_LANGUAGE.getLang_iso_code();

	final Lang theLang = Factories.LANG.findByIsoCode(lang);

	List<String> result = DicoImpl.listKey("js");

	String js = "";
	
	js = "var msg_txt = new Array;\n";
	
	for(String key : result){
		js += "msg_txt['"+key.substring(3)+"']	= \""+ ConvertTools.strreturn(ConvertTools.strbackslash(DicoTools.dico(theLang, key))) +"\";\n";
	}
	
	result = DicoImpl.listKey("mainTitle");
	
	js +="var gTitle = new Array();\n";
	
	//title['Quicknab']="toto";
	
	for(String key : result){
		js += "gTitle['"+key.substring(10)+"'] = \""+ ConvertTools.strreturn(ConvertTools.strbackslash(DicoTools.dico(theLang, key))) +"\";\n";
	}
	
	out.println(js);
%>