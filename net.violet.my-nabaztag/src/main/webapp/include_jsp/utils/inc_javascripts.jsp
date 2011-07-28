<%@ page import="net.violet.platform.datamodel.factories.Factories"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.util.MyConstantes" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<%
	String iAmABatard = "false";
%>
<bean:define id="isBatard" type="String" value="<%=iAmABatard%>"/>

<logic:equal name="page_title" value="myNewAccount">
	<bean:define name="myNewAccountForm" property="userData" id="userData" />
</logic:equal>
<logic:equal name="page_title" value="myMessages">
	<bean:define name="myMessagesForm" property="userData" id="userData" />
</logic:equal>
<logic:equal name="page_title" value="myTerrier">
	<bean:define name="myTerrierForm" property="userData" id="userData" />
</logic:equal>
<logic:equal name="page_title" value="myNablife">
	<bean:define name="myNablifeForm" property="userData" id="userData" />
</logic:equal>

<bean:define name='userData' property='user_24' id="user_24"/>

<%
	/* fichier d'inclusion de tous les javascripts */
%>
<script type="text/javascript">
	currentTab="";/* bidouille IE*/

	var nabaztag = {};
	nabaztag.constantes = {};
	nabaztag.constantes.DOMAIN 			= "<%=MyConstantes.DOMAIN%>";
	nabaztag.constantes.STREAM_SERVER 	= "<%=MyConstantes.STREAM_SERVER%>";

	nabaztag.constantes.H24 			= <%=user_24%>;

	<logic:greaterThan name="userData" property="user_id" value="0">
		nabaztag.constantes.ISLOG 		= true;
	</logic:greaterThan>
	<logic:equal name="userData" property="user_id" value="0">
		nabaztag.constantes.ISLOG 		= false;
	</logic:equal>


	nabaztag.constantes.OBJECTID		= <bean:write name='userData' property='userWithAtLeastOneObject'/>;

	nabaztag.constantes.BAD_CONNECTED_OBJECT	= <logic:match scope="page" name="isBatard" value="true">true</logic:match><logic:notMatch scope="page" name="isBatard" value="true">false</logic:notMatch>;

</script>

<%
	// en local et sur DEV, on utilise les javascripts tel quel
	if (MyConstantes.DOMAIN.equals("localhost") || MyConstantes.DOMAIN.equals("192.168.1.11")) {
%>
	<%-- JQUERY et plugins --%>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/jquery-1.2.1.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery.compat-1.1.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery.color.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery.easing.1.1.1.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery-select.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/thickbox.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery.form.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery.tooltip.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery.block.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jqModal.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/dom-creator.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/datePicker.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery.jmp3.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery.dimensions.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/jQuery/plugins/jquery.mousewheel.js"></script>
	<%-- --------------------------------------------------------------------------------------------------------------- --%>

	<%-- Variables, configs g�n�rales, et outils commun a toutes les pages --%>
	<script charset="utf-8" type="text/javascript" src="../include_js/conftools/cdl-conf.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/conftools/platform-detection.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/conftools/vars.js"></script>

	<script charset="utf-8" type="text/javascript" src="../include_js/conftools/swfobject-1.5.1.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/conftools/jstools.js"></script>
	<%-- --------------------------------------------------------------------------------------------------------------- --%>

	<%-- Fourre tout a ranger... --%>
	<script charset="utf-8" type="text/javascript" src="../include_js/common/mynabaztag.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/common/mynabaztag_validate.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/common/mynabaztag_blocks.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/common/mynabaztag_jqueryModules.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/common/mynabaztag-nabThem.js"></script>
	<%-- ---------------------------------------------------------------------------------------------------------------  --%>

	<%-- Sp�cifiques myNewAccount --%>
	<script charset="utf-8" type="text/javascript" src="../include_js/myHome/mynabaztag-register.js"></script>
	<%-- ---------------------------------------------------------------------------------------------------------------  --%>

	<%-- Sp�cifiques myTerrier --%>
	<script charset="utf-8" type="text/javascript" src="../include_js/myTerrier/mynabaztag-terrier.js"></script>
	<%-- ---------------------------------------------------------------------------------------------------------------  --%>

	<%-- Sp�cifiques myNablife --%>
	<script charset="utf-8" type="text/javascript" src="../include_js/myNablife/mynabaztag-nablife.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/myNablife/nablife-validate.js"></script>
	<script charset="utf-8" type="text/javascript" src="../include_js/myNablife/bloc.services.js"></script>
	<%-- ---------------------------------------------------------------------------------------------------------------  --%>

<%
	// sinon on utilise les versions "minimized" ( necessite un ANT, target -> jsy_nabaztag )
	} else {
%>
	<script charset="utf-8" type="text/javascript" src='../include_js/dist/nabaztag.jquery.js?v=1.2.1a'></script>
	<script charset="utf-8" type="text/javascript" src='../include_js/dist/nabaztag.conftools.js?v=1.2'></script>
	<script charset="utf-8" type="text/javascript" src='../include_js/dist/nabaztag.common.js?v=1.1'></script>

	<logic:equal name="page_title" value="myNewAccount">
		<script charset="utf-8" type="text/javascript" src='../include_js/dist/nabaztag.home.js?v=1.1'></script>
	</logic:equal>

	<logic:equal name="page_title" value="myNablife">
		<script charset="utf-8" type="text/javascript" src='../include_js/dist/nabaztag.nablife.js?v=1.1'></script>
	</logic:equal>

	<logic:equal name="page_title" value="myTerrier">
		<script charset="utf-8" type="text/javascript" src='../include_js/dist/nabaztag.terrier.js?v=1.1'></script>
	</logic:equal>

	<logic:equal name="page_title" value="myMessages">
		<%-- dans nabaztag.home.js, il y tout ce qui faut pour g�rer l'envoi d'un mail a un ami --%>
		<script charset="utf-8" type="text/javascript" src='../include_js/dist/nabaztag.home.js?v=1.1'></script>
	</logic:equal>

<% } %>

	<%-- inclus dans tous les cas --%>
	<script charset="utf-8" type="text/javascript" src='../include_js/mynabaztag_text.jsp?v=1.4&l=<%=dico_lang.getIsoCode()%>'></script>

	<logic:match scope="page" name="isBatard" value="true">
		<script>
			<%-- cas du lapin "batard", on va v�rifier r�gulierement si le lapin et le serveur se sont trouv� --%>
			startOnlineStatusCheck(<%=user_id%>);
		</script>
	</logic:match>
