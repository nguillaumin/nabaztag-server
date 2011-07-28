<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

	<bean:define name="myNabaztalandHistoForm" property="idNabcast" id="id" />
	<bean:define name="myNabaztalandHistoForm" property="nabcastData" id="nd" />
	<bean:define id="cat_name" name="nd" property="nabcast_categ" />
	<bean:define id="nab_title" name="nd" property="nabcast_titre" />
	<bean:define id="nab_desc" name="nd" property="nabcast_description" />
	<bean:define id="nab_lang" name="nd" property="nabcast_lang" />
	<bean:define name="myNabaztalandHistoForm" property="listeNabcastval" id="liste"/>
	
	
	<div class="itemPlayer" id="myPlayerMp3" style="left:5px; top:3px;"></div>

	<div class="nabcastHisto">
		
		<h1><%=nab_title%></h1>
		
		<div class="categorie"><strong><%=DicoTools.dico(dico_lang, "srv_nabcast_histo/category")%></strong><%=cat_name%></div>
		
		<div class="langue">
			<logic:equal name="nd" property="nabcast_lang" value="1"><%=DicoTools.dico(dico_lang, "srv_nabcast_histo/language_FR")%></logic:equal>
			<logic:notEqual name="nd" property="nabcast_lang" value="1"><%=DicoTools.dico(dico_lang, "srv_nabcast_histo/language_EN")%></logic:notEqual>		
		</div>

		<hr class="spacer" />

		<div class="description specialBlock">
			<%=nab_desc%>
		</div>
		
		<div class="subscribe" ><a href="javascript:;" onclick="TB_remove(); srvConfigToggle('id_<%=id%>_1', '<html:rewrite forward="goNabcastSubscribe"/>?idNabcast=<%=id%>');"><%=DicoTools.dico(dico_lang, "srv_nabcast_histo/subscribe")%></a></div>
		
		<hr class="spacer" />

		<h2><%=DicoTools.dico(dico_lang, "srv_nabcast_histo/list_content")%></h2>
		<div class="lesSons">
			<%--
				Devant la stupidité d'IE je m'incline, et j'utilise une table,
				qui se justifie presque de toute maniere dans ce cas...
			--%>
			<table cellspacing="0" cellpadding="0" border="0" >
				<logic:iterate id="nabcastValData" name="liste">
					<bean:define id="titre" name="nabcastValData" property="nabcastval_titre" />
					<bean:define id="tr" name="nabcastValData" property="nabcastval_timerelative"/>
					<bean:define id="mp3url" name="nabcastValData" property="nabcastval_url" />
						
					<tr>
						<td width="100%" class="tdnom">[<%=tr%>]&nbsp;<%=titre%></td>
						<td valign="top" class="tdplay">
							<div class="play">
								<a  href="javascript:;" class="LplayIcone"  title="&eacute;couter" onclick="loadPersoPlayer('<%=mp3url%>', '330')"><span><%=DicoTools.dico(dico_lang, "srv_nabcast_histo/listen_content")%></span></a>
							</div>						
						</td>
					</tr>
					
				</logic:iterate>
			</table>

		</div>
		
	</div>
	
