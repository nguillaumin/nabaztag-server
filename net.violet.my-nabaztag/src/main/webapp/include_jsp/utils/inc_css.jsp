<%@ page import="net.violet.platform.datamodel.User" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.util.MyConstantes" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


	<link href="../include_css/localImg.<%=dico_lang.getId()%>.css?v=1" rel="stylesheet" type="text/css" />
	
<%
		// en local et sur DEV, on utilise les css tel quel
		if (MyConstantes.DOMAIN.equals("localhost") || MyConstantes.DOMAIN.equals("192.168.1.11")) {
	%>
	<%-- modules divers--%>
	<link href="../include_css/modules/thickbox.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/modules/modal.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/modules/widget.nabthem.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/modules/jquery.tooltip.css" rel="stylesheet" type="text/css" />	


	<%-- Layout g�n�rale --%>
	<link href="../include_css/layout/images.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/layout/blocks.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/layout/basic.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/layout/blocks_layout.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/layout/main-layout.css" rel="stylesheet" type="text/css" />

	<%-- Layout sp�cifique aux differentes parties du site --%>
	<link href="../include_css/part_layout/nabcasts.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/part_layout/home.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/part_layout/nablife.css" rel="stylesheet" type="text/css" />
	<link href="../include_css/part_layout/messages.css" rel="stylesheet" type="text/css" />	
	<link href="../include_css/part_layout/terrier.css" rel="stylesheet" type="text/css" />
	
	<%-- --%>

	<link title="layout_green" href="../include_css/layout_green/layout.css?v=1.3" rel="stylesheet" type="text/css" />
	<link title="layout_orange" href="../include_css/layout_orange/layout.css?v=1.3" rel="alternate stylesheet" type="text/css" />
	<link title="layout_blue" href="../include_css/layout_blue/layout.css?v=1.3" rel="alternate stylesheet" type="text/css" />
	<link title="layout_violet" href="../include_css/layout_violet/layout.css?v=1.3" rel="alternate stylesheet" type="text/css" />
	<link title="layout_pink" href="../include_css/layout_pink/layout.css?v=1.3" rel="alternate stylesheet" type="text/css" />


<%
	// sinon on utilise les versions "minimized" ( necessite un ANT, target -> cssy_nabaztag )
	} else {
%>

	<link href="../include_css/dist/nabaztag.modules.css?v=1.1" rel="stylesheet" type="text/css" />
	<link href="../include_css/dist/nabaztag.layout.css?v=1.1.1" rel="stylesheet" type="text/css" />
	<link href="../include_css/dist/nabaztag.parts.css?v=1.1" rel="stylesheet" type="text/css" />
	
	<link title="layout_green" 	href="../include_css/dist/nabaztag.layout_green.css?v=1.1" 	rel="stylesheet" type="text/css" />
	<link title="layout_orange" href="../include_css/dist/nabaztag.layout_orange.css?v=1.1" rel="alternate stylesheet" type="text/css" />
	<link title="layout_blue" 	href="../include_css/dist/nabaztag.layout_blue.css?v=1.1" 	rel="alternate stylesheet" type="text/css" />
	<link title="layout_violet" href="../include_css/dist/nabaztag.layout_violet.css?v=1.1" rel="alternate stylesheet" type="text/css" />
	<link title="layout_pink" 	href="../include_css/dist/nabaztag.layout_pink.css?v=1.1" 	rel="alternate stylesheet" type="text/css" />	

<% } %>
<!--[if IE 6]>
	<link href="../include_css/ieHack.css?v=1.2" rel="stylesheet" type="text/css" />
<![endif]-->

<!--[if IE 7]>
	<link href="../include_css/ie7Hack.css?v=1.2" rel="stylesheet" type="text/css" />
<![endif]-->
