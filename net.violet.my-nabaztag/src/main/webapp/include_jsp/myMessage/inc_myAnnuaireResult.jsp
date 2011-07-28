<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<% response.setContentType("text/html;charset=UTF-8"); %>
<%@page import="net.violet.platform.datamodel.Lang"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<bean:define name="myAnnuaireResultForm" property="langUser" id="lang" type="String" />
	<!-- <strong><%=DicoTools.dico(dico_lang , "directory/results_search_contains")%>&nbsp;<bean:write name="myAnnuaireResultForm" property="nombre_profils" />&nbsp;<%=DicoTools.dico(dico_lang , "directory/results_nabz_in")%>&nbsp;<bean:write name="myAnnuaireResultForm" property="nombre_pages" />&nbsp;<%=DicoTools.dico(dico_lang , "directory/results_pages")%>	</strong> -->
	<table border="0" cellpadding="0" cellspacing="4">
		<tr>
			<td>
				<strong><%=DicoTools.dico(dico_lang , "directory/results_sort_by")%></strong>
			</td>
			<td width="35" align="right" nowrap="nowrap">
				<strong><%=DicoTools.dico(dico_lang , "directory/results_name")%></strong>
			</td>
			<td nowrap="nowrap">
				<table width="1%" border="0" cellpadding="0" cellspacing="0" class="upanddown">
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '1');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_up.gif" alt="" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '1');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_down.gif" alt="" border="0" /></a>
						</td>
					</tr>
				</table>
			</td>
			<td width="35" align="right" nowrap="nowrap">
				<strong><%=DicoTools.dico(dico_lang , "directory/results_age")%></strong>
			</td>
			<td nowrap="nowrap">
				<table width="1%" border="0" cellpadding="0" cellspacing="0" class="upanddown">
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '2');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_up.gif" alt="" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '2');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_down.gif" alt="" border="0" /></a>
						</td>
					</tr>
				</table>
			</td>
			<td width="35" align="right" nowrap="nowrap">
				<strong><%=DicoTools.dico(dico_lang , "directory/results_sexe")%></strong>
			</td>
			<td nowrap="nowrap">
				<table width="1%" border="0" cellpadding="0" cellspacing="0" class="upanddown">
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '3');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_up.gif" alt="" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '3');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_down.gif" alt="" border="0" /></a>
						</td>
					</tr>
				</table>
			</td>
			<td width="35" align="right" nowrap="nowrap">
				<strong><%=DicoTools.dico(dico_lang , "directory/results_city")%></strong>
			</td>
			<td nowrap="nowrap">
				<table width="1%" border="0" cellpadding="0" cellspacing="0" class="upanddown">
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '4');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_up.gif" alt="" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '4');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_down.gif" alt="" border="0" /></a>
						</td>
					</tr>
				</table>
			</td>
			<td width="35" align="right" nowrap="nowrap">
				<strong><%=DicoTools.dico(dico_lang , "directory/results_country")%></strong>
			</td>
			<td nowrap="nowrap">
				<table width="1%" border="0" cellpadding="0" cellspacing="0" class="upanddown">
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '5');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_up.gif" alt="" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '5');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_down.gif" alt="" border="0" /></a>
						</td>
					</tr>
				</table>
			</td>
			<td width="35" align="right" nowrap="nowrap">
				<strong><%=DicoTools.dico(dico_lang , "directory/results_status")%></strong>
			</td>
			<td nowrap="nowrap">
				<table width="1%" border="0" cellpadding="0" cellspacing="0" class="upanddown">
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '6');updateHiddenValue('sens', 'ASC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_up.gif" alt="" border="0" /></a>
						</td>
					</tr>
					<tr>
						<td>
							<a href="javascript:void(0)" onclick="updateHiddenValue('newPage', '0');updateHiddenValue('tri', '6');updateHiddenValue('sens', 'DESC'); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"><img src="../include_img/annuaire/annu_down.gif" alt="" border="0" /></a>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
    <div class="list-recherche-contener" >
<logic:notEmpty name="myAnnuaireResultForm" property="listeResultProfil" >    
	  <logic:iterate name="myAnnuaireResultForm" property="listeResultProfil" id="userData">
	    <div class="fiche">
	      <bean:define id="user_id" name="userData" property="id" />
	      <bean:define id="user_pseudo" name="userData" property="user_pseudo" />
	      <bean:define id="paysdata" name="userData" property="pays" />
	      <h1><a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo%>');"><%=user_pseudo%></a></h1>
	      
	      <div class="identite">
	        <logic:equal name="userData" property="annu_sexe" value="H"><%=DicoTools.dico(dico_lang , "directory/sexe_man")%></logic:equal>
	        <logic:equal name="userData" property="annu_sexe" value="F"><%=DicoTools.dico(dico_lang , "directory/sexe_woman")%></logic:equal>
	        <logic:greaterEqual name="userData" property="age" value="1"><bean:write name="userData" property="age"/>&nbsp;<%=DicoTools.dico(dico_lang , "directory/years")%></logic:greaterEqual>
	        <bean:write name="userData" property="annu_city"/>, <bean:write name="paysdata" property="pays_nom"/>.
	      </div>
	        
	      <div class="description">
	      	<a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo%>');">
		        <logic:equal name="userData" property="user_good" value="1">
		          <logic:equal name="userData" property="user_image" value="0">
		            <img class="photo" align="left" src="../photo/default_S.jpg" width="48" height="48" border="0">
		          </logic:equal>
		          <logic:equal name="userData" property="user_image" value="1">
		            <img class="photo" align="left" src="../photo/<%=user_id%>_S.jpg" width="48" height="48" border="0">
		          </logic:equal>
		        </logic:equal>
		        <logic:equal name="userData" property="user_good" value="0">
		          <img class="photo" align="left" src="../photo/default_S.jpg" width="48" height="48" border="0">
		        </logic:equal>
	        </a>
	        
	        <bean:write name="userData" property="user_comment"/>
	        
	      </div>
	      
	      <div class="commandes">
	        <ul>
	          <li class="envoyer">
	          	<%=DicoTools.dico(dico_lang , "directory/send")%>
	          </li>
				       <li class="msgLink"><a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo%>', 'text');"><%=DicoTools.dico(dico_lang , "directory/send_text")%></a></li>
				       <li class="msgLink"><a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo%>', 'mp3');"><%=DicoTools.dico(dico_lang , "directory/send_mp3")%></a></li>
				       <li class="msgLink"><a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo%>', 'bibliotheque');"><%=DicoTools.dico(dico_lang , "directory/send_music")%></a></li>
	          	<li class="msgLink">
	          		<a href="javascript:void(0);" onclick="sendMsgTo('<%=user_pseudo%>', 'clindoeil');"><%=DicoTools.dico(dico_lang , "directory/send_clins")%></a>
				</li>
	        </ul>
	        
	        <ul class="divers-links">
	          <li><a href="javascript:;" onclick="showProfilFromAnnuaire(<%=user_id%>);"><%=DicoTools.dico(dico_lang , "directory/view_profile")%></a></li>
	        </ul>
	        
	      </div>
	      <hr class="clearer" />
	    </div>
	  </logic:iterate>
</logic:notEmpty>
<logic:empty name="myAnnuaireResultForm" property="listeResultProfil" > 
<hr class="spacer" />
<div class="specialBlock" align="center"><h2><%=DicoTools.dico(dico_lang , "directory/no_result")%></h2></div>
</logic:empty>
    </div>	
    <logic:greaterThan name="myAnnuaireResultForm" property="nombre_pages" value="1">
		<div class="paginate">
			<form action="../action/myAnnuaireResult.do" name="pageSelector" method="post">
				<ul>
					<bean:define id="page_indexD" name="myAnnuaireResultForm" property="page_indexD" />
					<bean:define id="page_indexMM" name="myAnnuaireResultForm" property="page_indexMM" />
					<bean:define id="page_indexM" name="myAnnuaireResultForm" property="page_indexM" />
					<bean:define id="page_index" name="myAnnuaireResultForm" property="page_index" />
					<bean:define id="page_indexP" name="myAnnuaireResultForm" property="page_indexP" />
					<bean:define id="page_indexPP" name="myAnnuaireResultForm" property="page_indexPP" />
					<bean:define id="page_indexF" name="myAnnuaireResultForm" property="page_indexF" />

					<li>
						<a href="javascript:void(0);" onClick="javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', <%=page_indexD%>); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"> &lt;&lt; </a>
					</li>
					<li>
						<a href="javascript:void(0);" onClick="javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', <%=page_indexM%>); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"> &lt; </a>
					</li>

					<logic:notEqual name="myAnnuaireResultForm" property="page_indexMM" value="0">
						<li>
							<a href="javascript:void(0);" onClick="javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', <%=page_indexMM%>); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"> <bean:write name="myAnnuaireResultForm" property="page_AffIndexMM" /> </a>
						</li>
					</logic:notEqual>
					<logic:notEqual name="myAnnuaireResultForm" property="page_indexM" value="0">
						<li>
							<a href="javascript:void(0);" onClick="javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', <%=page_indexM%>); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"> <bean:write name="myAnnuaireResultForm" property="page_AffIndexM" /> </a>
						</li>
					</logic:notEqual>

					<li>
						<a href="javascript:void(0);"> <bean:write name="myAnnuaireResultForm" property="page_AffIndex" /> </a>
					</li>

					<logic:notEqual name="myAnnuaireResultForm" property="page_indexP" value="0">
						<li>
							<a href="javascript:void(0);" onClick="javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', <%=page_indexP%>); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"> <bean:write name="myAnnuaireResultForm" property="page_AffIndexP" /> </a>
						</li>
					</logic:notEqual>
					<logic:notEqual name="myAnnuaireResultForm" property="page_indexPP" value="0">
						<li>
							<a href="javascript:void(0);" onClick="javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', <%=page_indexPP%>); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"> <bean:write name="myAnnuaireResultForm" property="page_AffIndexPP" /> </a>
						</li>
					</logic:notEqual>

					<li>
						<logic:equal name="myAnnuaireResultForm" property="page_indexP" value="0">
							<a href="javascript:void(0);" onClick="javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', <%=page_indexF%>); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"> &gt; </a>
						</logic:equal>
						<logic:notEqual name="myAnnuaireResultForm" property="page_indexP" value="0">
							<a href="javascript:void(0);" onClick="javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', <%=page_indexP%>); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"> &gt; </a>
						</logic:notEqual>						
					</li>
					<li>
						<a href="javascript:void(0);" onClick="javascript:updateHiddenValue('newPage', '0');updateHiddenValue('nPage', <%=page_indexF%>); submitAjaxForm('rechercheAnnuaire', 'resultat-recherche')"> &gt;&gt; </a>
					</li>
				</ul>
			</form>
		</div>
	</logic:greaterThan>
	