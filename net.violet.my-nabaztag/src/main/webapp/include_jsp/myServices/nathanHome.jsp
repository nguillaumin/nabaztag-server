<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools"%>
<%@ page import="net.violet.platform.util.SessionTools"%>
<%@page import="net.violet.platform.datamodel.Lang"%>
<%
	response.setContentType("text/html;charset=UTF-8");
%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ include file="/include_jsp/utils/inc_dico.jsp"%>


<%
	Lang dico_lang = SessionTools.getLangFromSession(session, request);
%>

<bean:define name="mySrvNathanHomeForm" property="dicoRoot" id="dicoRoot" />
<bean:define name="mySrvNathanHomeForm" property="objectLogin" id="thisObjectLogin" />

<div id="setUpSrv-container"><%-- The main bloc : it contains all the service configuration page --%>
<div id="setUpSrv"><%-- The user has at least one book, he/she can see the config page --%>
<logic:notEqual name="mySrvNathanHomeForm" property="isMarkup" value="0">

	<div
		class="main-cadre-contener serviceContener serviceBookReaderConfig">
	<div class="main-cadre-top">
	<h2><%=DicoTools.dico(dico_lang, "srv_nathan/home_title")%></h2>
	</div>

	<div class="main-cadre-content">
	<bean:define id="idApplet" name="mySrvNathanHomeForm" property="appletId" /> 
	<bean:define id="isbn" name="mySrvNathanHomeForm" property="isbn" />

	<div class="intro"><%=DicoTools.dico(dico_lang, "srv_nathan/config_book1")%>
	<a href="../action/srvNathanCreate.do?isbn=<%=isbn%>&appletId=<%=idApplet%>&dispatch=load"
		onclick="page.loadInDiv('#setUpSrv-container',this.href); return false;"><%=DicoTools.dico(dico_lang, "srv_nathan/link_create")%></a>
	<%=DicoTools.dico(dico_lang, "srv_nathan/config_book2")%></div>
	<hr class="clearer"/>
	
	<%-- iteration sur tous les livres de cette histoire --%>
	<logic:greaterThan name="mySrvNathanHomeForm" property="isMarkup" value="1">
		<div class="ztamp-img">
			<logic:iterate name="mySrvNathanHomeForm" property="mySetting" id="player">
				<bean:define id="bookSerial" name="mySrvNathanHomeForm" property="serial" />
				<bean:define id="serial" name="player" property="ztampSerial" type="String"/>
				<bean:define id="picture" name="player" property="pictureObject" />
				
				<logic:notEqual name="mySrvNathanHomeForm" property="serial" value="<%=serial%>" >						
				<div id="zt-<%=serial %>" > 
					<a href="../action/srvNathanHome.do?dispatch=load&serial=<%=serial%>&isbn=<%=isbn%>&appletId=<%=idApplet%>" onclick=" activateTab('zt-<%=serial %>'); page.loadInDiv('#ztamps-contener',this.href, '#ztamps-content'); return false;"  >
			 		<img src="../include_img/ztamps/<%=picture %>.gif" alt="<%=serial %>" /></a>
	 			</div>
	 			</logic:notEqual>
	 			
	 			<logic:equal name="mySrvNathanHomeForm" property="serial" value="<%=serial%>" >						
				<div id="zt-<%=serial %>" class="active"> 
					<a href="../action/srvNathanHome.do?dispatch=load&serial=<%=serial%>&isbn=<%=isbn%>&appletId=<%=idApplet%>" onclick=" activateTab('zt-<%=serial %>'); page.loadInDiv('#ztamps-contener',this.href, '#ztamps-content'); return false;"  >
			 		<img src="../include_img/ztamps/<%=picture %>.gif" alt="<%=serial %>" /></a>
	 			</div>
	 			</logic:equal>
	 			
			</logic:iterate>
		</div>
		<hr class="clearer" />
	    <br />
	</logic:greaterThan>
	
	<div class="ztamps-contener" id="ztamps-contener">
		<div class="tabSousNavContener-tr" id="ztamps-content">
		<bean:define id="bookSerial" name="mySrvNathanHomeForm" property="serial" />
		
		<ul id="nathanMenu" class="tabSousNav">
			<li id="nathanTab" class="active"><a
				href="../action/srvNathanHome.do?isbn=<%=isbn%>&appletId=<%=idApplet%>&dispatch=official&serial=<%=bookSerial%>"
				onclick="activateTab('nathanTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;"><span><%=DicoTools.dico(dico_lang, "srv_nathan/menu_nathan")%></span></a></li>
			<li id="versionsTab"><a
				href="../action/srvNathanHome.do?isbn=<%=isbn%>&appletId=<%=idApplet%>&dispatch=myversion&bookSerial=<%=bookSerial%>"
				onclick="activateTab('versionsTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;"><span><%=DicoTools.dico(dico_lang, "srv_nathan/menu_versions")%></span></a></li>
			<li id="popularTab"><a
				href="../action/srvNathanHome.do?isbn=<%=isbn%>&appletId=<%=idApplet%>&dispatch=popular&serial=<%=bookSerial%>"
				onclick="activateTab('popularTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;"><span><%=DicoTools.dico(dico_lang, "srv_nathan/menu_popular")%></span></a></li>
			<li id="newsTab"><a
				href="../action/srvNathanHome.do?isbn=<%=isbn%>&appletId=<%=idApplet%>&dispatch=recent&serial=<%=bookSerial%>"
				onclick="activateTab('newsTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;"><span><%=DicoTools.dico(dico_lang, "srv_nathan/menu_news")%></span></a></li>
			<li id="searchTab"><a
				href="../action/srvNathanHome.do?isbn=<%=isbn%>&appletId=<%=idApplet%>&dispatch=search&bookSerial=<%=bookSerial%>"
				onclick="activateTab('searchTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;"><span><%=DicoTools.dico(dico_lang, "srv_nathan/menu_search")%></span></a></li>
			<li id="selectionTab"><a
				href="../action/srvNathanHome.do?isbn=<%=isbn%>&appletId=<%=idApplet%>&dispatch=selection&bookSerial=<%=bookSerial%>"
				onclick="activateTab('selectionTab'); page.loadInDiv('#main-config-bloc',this.href, '#config-bloc'); return false;"><span><%=DicoTools.dico(dico_lang, "srv_nathan/menu_selection")%></span></a></li>
		</ul>
		<div class="tabSousNavContener-bl">
		<div id="main-config-bloc">
		
		<div id="config-bloc">
		<div class = "nathan-create">
		<%=DicoTools.dico(dico_lang, "srv_nathan/hint_create")%><a
			href="../action/srvNathanCreate.do?isbn=<%=isbn%>&appletId=<%=idApplet%>&dispatch=load"
			onclick="page.loadInDiv('#setUpSrv-container',this.href); return false;"><%=DicoTools.dico(dico_lang,
												"srv_nathan/hint_link_create")%></a>
		</div>
		<hr class = "clearer" />

		<div id="myMp3Player" class="myPlayerMp3Message"></div>

		<div id="list-bloc">
		
		<form action="../action/srvNathanHome.do" id="selectionForm"><input
			type="hidden" name="idApplet" value="<%=idApplet%>" /> <input
			type="hidden" name="isbn" value="<%=isbn%>" /> <input 
			type="hidden" name="dispatch" value="subscribe" />

		<div class="nathan">
		<logic:iterate name="mySrvNathanHomeForm" property="resultList" id="version">
			<bean:define id="objectLogin" name="version" property="author" type="String" />
			<bean:define id="nb" name="version" property="nb" />
			<bean:define id="description" name="version" property="description" />
			<bean:define id="versionId" name="version" property="id" />
			<bean:define id="img" name="version" property="img" />

			<div class="version-bloc">
				<div class="version-selector">
				<input type="checkbox"
				name="selectedVersions" value="<%=versionId %>" id="v<%=versionId %>" />
					<logic:equal value="-1" name="version" property="img">
						<a onclick="javascript:selectInput('v<%=versionId %>');" ><img src="../photo/default_S.jpg"/></a>
					</logic:equal>
					<logic:notEqual value="-1" name="version" property="img">
						<logic:equal name="version" property="official" value="true">
							<a onclick="javascript:selectInput('v<%=versionId %>');" ><img src="<%=img%>"/></a>
						</logic:equal>
						<logic:equal name="version" property="official" value="false">
							<a onclick="javascript:selectInput('v<%=versionId %>');" ><img src="../photo/<%=img%>_S.jpg"/></a>
						</logic:equal>
					</logic:notEqual>	
				</div>
				
				<div class="version-info">
				<div class="version-titre">
				<%=DicoTools.dico(dico_lang,"srv_nathan/list_tells")%>
					<logic:equal name="version" property="official" value="true">
						<%=DicoTools.dico(dico_lang,"srv_nathan/nathan")%>
					</logic:equal>
					<logic:equal name="version" property="official" value="false">
						<%=objectLogin%> 
					</logic:equal>
				
					
				</div>
                <div class="version-details">
				<logic:iterate name="version" property="tags" id="tag">
				<bean:define id="name" name="tag" property="label"
					type="java.lang.String" />
				<%=DicoTools.dico(dico_lang, name)%> |
						</logic:iterate>
						</div>
				<%-- infos propres aux versions non-officielles --%> 
				<logic:equal
				name="version" property="official" value="false">
				<div class="version-note"><%=nb%> <%=DicoTools.dico(dico_lang,	"srv_nathan/list_subscribers")%>
				</div>
				</logic:equal>
				<div class="version-des"><%=description%></div>
				</div>
				<%-- fin version-bloc --%>
				<div class="version-tools">
				<div class="item-tools">
				<logic:notEqual name="version" property="preview" value="">
				<bean:define id="preview" name="version" property="preview" />
				<a href="#js;"
					onclick="loadPersoPlayer('<%=preview%>', '300', '1', 'myMp3Player'); return false;"><%=DicoTools.dico(dico_lang,
													"srv_nathan/list_preview")%></a><br />
						
				<%-- encouragement pour les versions non officielles --%>
					<logic:equal
				name="version" property="official" value="false">						
					<logic:notEqual name="mySrvNathanHomeForm" property="objectLogin" value="<%=objectLogin%>">						
				
					<%String text = DicoTools.dico(dico_lang, "srv_nathan/nabthem_text" ); 
					  String resp = DicoTools.dico(dico_lang, "srv_nathan/nabthem_ok" );
					%>
					<a href="#js;" onclick="$.get('myMessagesSendClin.do?destName=<%=objectLogin%>&send=1&categId=94'); msgHandling.simpleMsgShow('<%=resp%>'); return false;"><%=DicoTools.dico(dico_lang, "srv_nathan/nabthem" )%></a>
				
				</logic:notEqual>
				</logic:equal>
												
				</logic:notEqual></div></div>
			</div>
			
			<hr class="clearer1"/>
			 
		</logic:iterate> 
			<bean:define id="bookSerial" name="mySrvNathanHomeForm" property="serial" />
			<input type="hidden" name="bookSerial" value="<%=bookSerial%>" />
			<logic:notEmpty name="mySrvNathanHomeForm" property="resultList">
				<div class="button-bloc">
					<input type="submit"
					value="<%=DicoTools.dico(dico_lang , "srv_nathan/add_to_selection")%>"
					onclick="activateTab('selectionTab'); page.postAjax('selectionForm', 'main-config-bloc'); return false;" />			
				</div>
			</logic:notEmpty>
		</div>
		</form>
		</div>
		</div>
		</div>
		</div>
        </div>
		</div>
	<hr class="clearer" />

	</div>
	</div>
</logic:notEqual> <%-- The user does not have the book --%> <logic:equal
	name="mySrvNathanHomeForm" property="isMarkup" value="0">
	<div class="main-cadre-contener serviceContener">
	<div class="main-cadre-top">
	<h2><%=DicoTools.dico(dico_lang, "srv_nathan/title_nobook")%></h2>
	</div>

	<div class="main-cadre-content"><%=DicoTools.dico(dico_lang, "srv_" + dicoRoot.toString()
								+ "/config_nobooks")%></div>
	</div>
</logic:equal></div>


<%-- If the user does not have the book we display a bloc containing all the other nathan books --%>
<logic:equal name="mySrvNathanHomeForm" property="isMarkup" value="0">
	<logic:notEmpty name="mySrvNathanHomeForm" property="otherBooksList">

		<div class="main-cadre-contener serviceContener">
		<div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang, "srv_nathan/other_books_title")%></h2>
		</div>


		<div class="main-cadre-content">
		<hr class="spacer" />
		<div class="srv-main-config">
		<bean:define id="booksList"
			name="mySrvNathanHomeForm" property="otherBooksList" /> 
			<div class="nathan">
		<logic:iterate
			id="book" name="booksList">
			<bean:define id="bookImg" name="book" property="img" />
			<bean:define id="bookImgGrise" name="book" property="imgGrise" />
			<bean:define id="bookTitle" name="book" property="title" />
			<bean:define id="bookAuthor" name="book" property="author" />
			<bean:define id="url" name="book" property="url" />
			<bean:define id="nb" name="book" property="nbVersions" />
			<bean:define id="hasBook" name="book" property="hasBook" />
			
			<div class="bouquin">
			<div class="image">
			<logic:equal name="book" property="hasBook" value="1">
			<a href="myNablife.do?service=<%=url%>"><img src="<%=bookImg%>" alt=" "/></a>
			</logic:equal>
			<logic:notEqual name="book" property="hasBook" value="1">
			<a href="myNablife.do?service=<%=url%>"><img src="<%=bookImgGrise%>" alt=" "/></a>
			</logic:notEqual>
			</div>
			<div class="collec-titre" ><%=bookTitle%></div>
			<div class="collec-author"><%=bookAuthor%></div>

			<div class="collec-versions" ><a href="myNablife.do?service=<%=url%>"><strong><%=nb%></strong> <%=DicoTools.dico(dico_lang,
											"srv_nathan/other_books_versions")%></a></div>

			<div class="collec-versions" >
			<logic:equal name="book" property="hasBook" value="1">
				<a href="myNablife.do?service=<%=url%>"><%=DicoTools.dico(dico_lang,
														"srv_nathan/other_books_haveIt")%></a>
				
			</logic:equal> <logic:equal name="book" property="hasBook" value="0">
				<a href="http://www.ztore.net" target="_blank"><%=DicoTools.dico(dico_lang,
														"srv_nathan/other_books_dontHaveIt")%></a>
				
			</logic:equal>
			</div>
			</div>
		</logic:iterate>
		</div>
		<hr class="clearer" />
		</div>
		<hr class="spacer" />
		</div>

		</div>

	</logic:notEmpty>
</logic:equal> <%-- Le bloc how-to --%>

<div id="how-to-container" class="main-cadre-contener">

<div class="main-cadre-top">
<h2><%=DicoTools.dico(dico_lang, "services/how_does_it_work")%></h2>
</div>

<div class="main-cadre-content">
<hr class="spacer" />
<div class="srv-main-config"><logic:notEqual
	name="mySrvNathanHomeForm" property="isMarkup" value="0">
	<p><%=DicoTools.dico(dico_lang, "srv_nathan/how_to")%></p>
</logic:notEqual> 
<logic:equal name="mySrvNathanHomeForm" property="isMarkup" value="0">


<div class="nathan-how_to_img">
	<img title="Nabaztag vous lit 'Mes p'tites histoires'" alt="Nabaztag vous lit 'Mes p'tites histoires'" src="../include_img/lapin-bibli.gif"/>
</div>
<div class="nathan-how_to">
	<%=DicoTools.dico(dico_lang, "srv_nathan/how_to_nobook")%>
	</div>
	<hr class="clearer"/>
</logic:equal></div>

<hr class="spacer" />

</div>
</div>
</div>