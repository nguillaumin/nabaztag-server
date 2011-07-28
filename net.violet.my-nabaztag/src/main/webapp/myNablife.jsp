<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page import="net.violet.platform.datamodel.Application" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<%
	response.setContentType("text/html;charset=UTF-8");
%> 

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
	final Lang dico_lang =	SessionTools.getLangFromSession(session, request);
	String user_main = Long.toString(SessionTools.getRabbitIdFromSession(session));
	session.setAttribute("page_title","myNablife");
%>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>
<bean:define name="myNablifeForm" property="langUser" id="lang" type="String"/>
<bean:define name="myNablifeForm" property="userId" id="user_id"/>
<bean:define name="myNablifeForm" property="onglet" id="onglet"/>
<bean:define name="myNablifeForm" property="serviceToConfigure" id="serviceToConfigure"/>
<bean:define name="myNablifeForm" property="categoryId" id="categoryId"/>
<bean:define name="myNablifeForm" property="categoryLang" id="categoryLang"/>
<bean:define id="badLogin" value="<%=(request.getParameter("badLogin")==null) ? "0" : (String)request.getParameter("badLogin")%>" />	
<bean:define id="searched" value="<%=(request.getParameter("searched")==null) ? "" : request.getParameter("searched")%>" />

<bean:define id="search" value="<%=(session.getAttribute("search")==null) ? "" : (String)session.getAttribute("search")%>"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title><%=DicoTools.dico(dico_lang , "myNablife/page_title")%></title>

<bean:define id="search" value="<%=(request.getParameter("search")==null) ? "": request.getParameter("search")%>"/>
<bean:define id="idNabcast" value="<%=(request.getParameter("idNabcast")==null) ? "": request.getParameter("idNabcast")%>"/>
<bean:define id="goToListResult" value="<%=(request.getAttribute("goToListResult")==null) ? "": request.getAttribute("goToListResult").toString()%>"/>

<%@ include file="/include_jsp/utils/inc_css.jsp" %>
<%@ include file="/include_jsp/utils/inc_javascripts.jsp" %>

</head>

<body onload='javascript:getNewMessages();'>

<div id="container">
	<div id="header">
		<%@ include file="/include_jsp/utils/inc_header.jsp" %>
	</div>

	<div id="wrapper"> <%-- ******************************************************************************************************** --%>
			<!-- COLONNE CENTRALE -->  
			  <div id="centercontent" > 
				<div class="tabNavContener top-tab-disabled">
					<a href="myNablife.do"><h1 class="icoTitle_Nablife">&nbsp;</h1></a>
					<ul class="tabNav">
						<li style="display:none" id="AllServices"><a href="<html:rewrite forward='goServicesHome'/>"><span><%=DicoTools.dico(dico_lang , "myNablife/tab_all_services")%></span></a></li>
						<li style="display:none" id="Nabaztaland"><a href="<html:rewrite forward='goNabcastHome'/>"><span><%=DicoTools.dico(dico_lang , "myNablife/tab_nabaztaland")%></span></a></li>
						<li style="display:none;" class="right" id="Publier"><a href="<html:rewrite forward='goNabcastCreate'/>"><span><%=DicoTools.dico(dico_lang , "myNablife/tab_publish_service")%></span></a></li>					
					</ul>
			    </div>
			        <div class="mainTab">
			          <div class="mainTabBody">
					  	<div id="contentAllServices" class="contentMainTab srvlist-contener">
							<%-- tous les services--%>
						</div>
			
						
						<div id="contentServicesByCateg" class="contentMainTab srvlist-contener">
							<%-- services par categorie --%>
						</div>
						
						<div id="contentNabaztaland" class="contentMainTab srvlist-contener">
							<%-- services nabaztaland --%>
						</div>
						
						<div id="contentPublier" class="contentMainTab">
							<%-- publier --%>
						</div>
									
						<div id="contentSrvConfig" class="contentMainTab" >
							<%-- publier --%>
						</div>
																
						<hr class="clearer" />
			        </div>
				</div>
			</div>
			<hr class="clearer" />
			<!--/ COLONNE CENTRALE -->
	</div>

	<div id="leftcontent"> <%-- ******************************************************************************************************** --%>
		<!-- COLONNE GAUCHE -->
		<logic:equal name="userData" property="user_id" value="0"> <% /* User NON connecté */%>
		
			<div id="bloc-homeBloc" class="left-block manual">
				<a href="myNablife.do"><h1>&nbsp;</h1></a>
				<div class="block-content">
					
					<div class="part website-language"> <%-- **************************************************** --%>
						<label><%=DicoTools.dico(dico_lang , "bloc/home-interfaceLanguage")%></label>
			  			<html:select name="myNablifeForm" property="langUser">
							<logic:iterate name="myNablifeForm" property="langList" id="langData">
								<bean:define name="langData" property="lang_id" id="lang_id"/>
								<bean:define name="langData" property="lang_title" id="lang_title"/>
								<bean:define name="langData" property="lang_type" id="lang_type"/>
									
								<logic:lessEqual name="lang_type" value="0">
									<html:option value="<%=lang_id.toString()%>"><%=lang_title.toString()%></html:option>
								</logic:lessEqual>
							</logic:iterate>
						</html:select>
																
					</div>
					
					<div class="part"> <%-- **************************************************** --%>
						<h2><%=DicoTools.dico(dico_lang , "bloc/home-gotAccountTitle")%></h2>
						<div class="content">
								<div class="login">
									<html:form action="/action/mySession" styleId="login_form">
										<html:hidden name="mySessionForm" property="action" value="connect"/>
										<html:hidden name="mySessionForm" property="forward" value="messages"/>
										<html:hidden name="mySessionForm" property="url" value="goNablife"/>
										<html:hidden name="mySessionForm" property="redirectUrlBadLogin" value="myNablife.do?badLogin=1"/>
										<label>
											<%=DicoTools.dico(dico_lang , "myHome/nabname")%>
										</label>
										<html:text name="mySessionForm" property="pseudo"/>
									
										<label>
											<%=DicoTools.dico(dico_lang , "myHome/motdepasse")%>
										</label>
										<html:password name="mySessionForm" property="password"/>
	
										
										<html:hidden name="mySessionForm" property="redirectUrl" value="myNablife.do"/>
										
										<div class="div-spacer">
											<button type="submit" class="little" /><span>OK</span></button>
										</div>
										
									</html:form>
									
									<hr class="spacer" />					
								</div>
								<a class="closed-arrow simple-link divChangeLink" href="myHomePassword.do#recover-password">
									<%=DicoTools.dico(dico_lang , "myHome/forgotten_password")%>
								</a>
								
								<div id="recover-password"></div>
								
								<hr class="clearer" />
						</div>	
					</div>
	
					<div class="part nabaztag-desc">
						<div class="content">
							<div class="lapinTrans"></div>
							<%=DicoTools.dico(dico_lang , "bloc/about_nabaztag_description")%>
						</div>	
					</div>
									
				</div>			
			</div>
		</logic:equal> 	
		
		<div class="contener"></div>
				
		<!-- /COLONNE GAUCHE -->
	</div>

	<div id="rightcontent"> <%-- ******************************************************************************************************** --%>
		<!-- COLONNE DROITE -->
			<div class="manual right-block" style="display:block; border: medium none ; background: transparent none repeat scroll 0%; min-height: 1px; height: 1%;">
				<h1><%=DicoTools.dico(dico_lang, "myNablife/rightBlocInfos_title")%></h1>
		
				<div class="block-content">
					<div class="inner">	
						<% String staticContent = "/include_jsp/static_content/"+dico_lang.getIsoCode()+"/nablife.home.jsp"; %>
						<jsp:include page="<%=staticContent%>" />
					</div>
				</div>
			</div>
			
			<div class="contener"></div>
						
		<!-- /COLONNE DROTIE -->
	</div>
	<div id="footer">
		<div class="copyright"><%=DicoTools.dico(dico_lang , "footer/copyright")%> | <a href="#"><%=DicoTools.dico(dico_lang , "footer/contact_link")%></a></div>
	</div>
</div>

<script language="javascript">

	
	var page = {};

	/* Poste le formulaire en Ajax */
	page.postAjax = function(frmId, refreshDiv) {

		var frm = $("#" + frmId);
		frm.ajaxSubmit({
			beforeSubmit: function(formData,f,o){
				frm.block();
			},
			success: function(responseData) {
				frm.unblock();
			},
			target: "#"+refreshDiv
		});
	}

	page.loadInDiv = function(refresh, urlToLoad, extract){

		var wait = "<img src='/include_img/loading.gif' class='loader'/>";

		var now = new Date(); // create a timestamp to prevent browser caching of ajax request
		urlToLoad += ((urlToLoad.indexOf("?") == -1) ? "?" : "&") 
			+ ("timestamp=" + now.getHours() + now.getMinutes() + now.getSeconds());

		if (extract != null && extract != "") {
			$(refresh).html(wait).load(urlToLoad + " " + extract);

		} else {
			$(refresh).html(wait).load(urlToLoad);
		}
    	return false;
	}

	$(document).ready(function(){

		// active le lien mot de passe
		$("a.closed-arrow").click(function(){
			$(this).toggleOpenClose();

			if ( $(this).is(".closed-arrow") ) {
				$("#recover-password").html("");
				$(this).parents("div.part").find(".login").show();
			} else {
				var u = $(this).attr("href");
				var t;
				$(this).parents("div.part").find(".login").hide();
				u = u.split("#");
				t = u[1];	// target
				u = u[0];	// url

				$("#"+t).divChangeUrl(u);
			}
			return false;				
		});
		
		
		
		
		$("div.website-language select").change(function(){
			var l = $(this).val();
			var url = "myNablife.do?langUser=" + l;
			redirect(url);
		});
				
		
		<%-- on va sur le bon onglet via l'url--------------------------------------------------------------------------%>
		<logic:notEqual parameter="onglet" value="">
			<bean:define id='onglet' value='<%= request.getParameter("onglet") %>' />
			$('#<%=onglet%> a').trigger("click");
		</logic:notEqual>

		<%-- on va PAS sur le bon onglet puisque on a pa "onglet" dans l'url---------------------------------------------%>
		<logic:equal parameter="onglet" value="">
			
			<%-- on affiche le résultat d'une recherche --%>
			<logic:equal name="goToListResult" value="yes">
				mainTab_GoTab('AllServices');
				<logic:greaterThan name="user_id" value="0">
					<logic:lessEqual name="user_main" value="0">
						$("#bloc-DecouvrirNabaztag").hide();
						//$("#contentSrvConfig").load("../action/myShortcutState.do?badLogin=<bean:write name="myNablifeForm" property="badLogin"/>");
						divChangeUrl("contentSrvConfig", '../action/myShortcutState.do?badLogin=<bean:write name="myNablifeForm" property="badLogin"/>');
					</logic:lessEqual>
					<logic:greaterThan name="user_main" value="0">
						//$("#contentSrvConfig").load("../action/myNablifeResultSearch.do");
						divChangeUrl("contentSrvConfig", "../action/myNablifeResultSearch.do");
					</logic:greaterThan>
				</logic:greaterThan>
				<logic:lessEqual name="user_id" value="0">
					$("#bloc-DecouvrirNabaztag").hide();
					//$("#contentSrvConfig").load("../action/myShortcutState.do?badLogin=<bean:write name="myNablifeForm" property="badLogin"/>");
					divChangeUrl("contentSrvConfig", "../action/myShortcutState.do?badLogin=<bean:write name="myNablifeForm" property="badLogin"/>");
				</logic:lessEqual>
				$("#contentSrvConfig").show();
			</logic:equal>
			
			<logic:notEqual name="goToListResult" value="yes">
				<logic:equal name="myNablifeForm" property="fromSearch" value="true">
					
					<logic:lessEqual name="myNablifeForm" property="objectId" value="0" >
						$('input[@name=redirectUrl]').val("/action/myNablife.do?search=<%=request.getParameter("search")%>");
					</logic:lessEqual>
					
					// on affiche les résultats et les bons messages
					// Service/Podcast/RSS
					<logic:notEmpty name="myNablifeForm" property="srvListData">
						<bean:define id="srvListData" name="myNablifeForm" property="srvListData"/>
							
						mainTab_GoTab('AllServices');
						var reg = new RegExp("&amp;", "g");
						var url = '<bean:write name="srvListData" property="link"/>';
						url = url.replace(reg, "&");
						nablife.loadSrvPage(url+'&dispatch=load&srvId=<bean:write name="srvListData" property="id"/>');
						
					</logic:notEmpty>

					
					// Nabcast
					<logic:notEmpty name="myNablifeForm" property="nabcastData">
						<bean:define id="listNabcastData" name="myNablifeForm" property="nabcastData"/>
						
						mainTab_GoTab('Nabaztaland');			
						
						<logic:iterate id="nabcastData" name="listNabcastData">
							//alert('Recherche nabcast [<bean:write name="myNablifeForm" property="searched" />] trouvé : <bean:write name="nabcastData" property="nabcast_title"/>');
							nablife.loadSrvPage('../action/myNabaztalandSubscribe.do?idNabcast=<bean:write name="nabcastData" property="nabcast_id"/>&searched=<bean:write name="myNablifeForm" property="searched" />');
						</logic:iterate>
					
					</logic:notEmpty>
					
					<logic:notEmpty name="idNabCast">
						//srvConfigToggle('id_<bean:write name="idNabCast" />_1', '../action/myNabaztalandSubscribe.do?idNabcast=<bean:write name="idNabCast" />');
						nablife.loadSrvPage('../action/myNabaztalandSubscribe.do?idNabcast=<bean:write name="idNabCast" />');
					</logic:notEmpty>
					
					<%--
					// Utilisateur
					// CECI MARCHE MAL, ON PASSE TOUJOURS PAR LA, DONC J'AI DÉSACTIVÉ (nicolas)
					<logic:notEmpty name="myNablifeForm" property="userData">
						<bean:define id="userData" name="myNablifeForm" property="userData"/>
						// Pour l'instant on redirige vers la page pas de résultat
						goService('../include_jsp/myNablife/serviceBadShortcut.jsp',-1);
					</logic:notEmpty>
					--%>
					
					// Aucun résultat
					<logic:empty name="myNablifeForm" property="srvListData">
						<logic:empty name="myNablifeForm" property="nabcastData">
							<logic:empty name="myNablifeForm" property="userData">
								goService('../include_jsp/myNablife/serviceBadShortcut.jsp',-1);
							</logic:empty>
						</logic:empty>
					</logic:empty>
					
				</logic:equal>
				
				<%-- on a demandé ni un nabcast ni un service --%>	
				<logic:equal name="myNablifeForm" property="fromSearch" value="false">
					
						<%/* on veut acceder a une categorie en particulier */%>
						<logic:notEqual name="myNablifeForm" property="categoryId" value="-1">
							mainTab_GoTab('AllServices', "<html:rewrite forward='goServicesHome'/>?idCateg=<%=categoryId.toString()%>&mode=1&langCategorie=<%=categoryLang.toString()%>");
						</logic:notEqual>
						
						<%/* ou pas */%>
						<logic:equal name="myNablifeForm" property="categoryId" value="-1">
							mainTab_GoTab('AllServices', "<html:rewrite forward='goServicesHome'/>", null, nablife.init );
						</logic:equal>
							
						<% /* on arrive et direct on a pass un service en parametre */ %>
						<logic:notEmpty name="myNablifeForm" property="serviceToConfigure">
							var tmp = "<%=serviceToConfigure%>";
							tmp = tmp.split("|");
							var url  = tmp[1];
							var id 	 = tmp[0];
							if (url!=null) {
								srvConfigToggle(id, url);
							} else {
								srvConfigToggleN(id);
							}
						</logic:notEmpty>
					
				</logic:equal>
				

				
				<logic:equal name="badLogin" value="1">
					customAlertN("<%=DicoTools.dico(dico_lang , "myHome/error_login")%>");
				</logic:equal>
			</logic:notEqual>
		</logic:equal>
		
		<logic:notEmpty name="myNablifeForm" property="popup">
			TB_show('','../action/srvDialogConfig.do?dispatch=load&applicationId=<%=Application.NativeApplication.EARS_COMMUNION.getApplication().getId()%>&height=1&width=400');
		</logic:notEmpty>
						
	});



</script>

</body>
</html>
