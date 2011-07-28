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

<bean:define name="myAnnuaireForm" property="langUser" id="lang" type="String"/>

<div class='annuaire'>
<div id="annu-form">
  <form action="../action/myAnnuaireResult.do" name="rechercheAnnuaire" id="rechercheAnnuaire" onsubmit="return validateAnnuaireSearch();" style="position:relative;">
    <html:hidden styleId="nPage" name="myAnnuaireForm" property="page_index" value="" />
    <!--html:hidden styleId="newPage" name="myAnnuaireForm" property="page_new" value="0" /-->
    <html:hidden styleId="tri" name="myAnnuaireForm" property="type_tri" value="" />
    <html:hidden styleId="sens" name="myAnnuaireForm" property="typeTri" value="" />
    <input style="position:absolute; right:20px; top:31px; width:90px; z-index:100;" type="submit" name="Submit" value="<%=DicoTools.dico(dico_lang , "directory/button_search")%>" class="genericBt" />
    
    <div>
    	<input type="hidden" value="12" name="nbAffParPage" />
	    <label for="agemini"><%=DicoTools.dico(dico_lang , "directory/age_mini")%></label>
	    <html:text name="myAnnuaireForm" property="agemin" styleClass="" size="2" />
	    <label for="agemaxi"><%=DicoTools.dico(dico_lang , "directory/age_to")%></label>
	    <html:text name="myAnnuaireForm" property="agemax" styleClass="" size="2" />
	    <%=DicoTools.dico(dico_lang , "directory/sexe")%>
		<html:radio name="myAnnuaireForm" property="sexe" value="F" styleId="ff"/>
		<label for="ff"><%=DicoTools.dico(dico_lang , "directory/sexe_woman")%></label>
		<html:radio name="myAnnuaireForm" property="sexe" value="H" styleId="gg"/>
		<label for="gg"><%=DicoTools.dico(dico_lang , "directory/sexe_man")%></label>   
	</div>
	<div style="position:absolute; top:29px; left:4px;" >
        <label for="nom"><strong><%=DicoTools.dico(dico_lang , "directory/name")%></strong></label>
        <html:text name="myAnnuaireForm" property="pseudo" styleClass="" size="15" />	
      	<input type="hidden" name="ville" id="ville" />
      	<input type="hidden" name="pays" id="pays" />           
	</div>  
  </form>
  <%/* placé en dehors du form, pour une raison de performance avec ie.... */%>
    <ul class="inline" style="position:absolute; top:29px; left:199px;">
      <li>
      	<strong><%=DicoTools.dico(dico_lang , "directory/city")%></strong>
        <html:select name="myAnnuaireForm" property="ville" styleClass="anselect" onchange="updateHiddenFromSelect(this, 'ville')">
			<option value=""></option>
		      <bean:define id="annudata" name="myAnnuaireForm" property="listeVille" />
			<html:optionsCollection name="annudata" value="annu_city" label="annu_cityFormatted"  />
		</html:select>
      </li>
      <li>
      	<strong><%=DicoTools.dico(dico_lang , "directory/country")%></strong>
        <html:select name="myAnnuaireForm" property="pays" styleClass="anselect" style="width:150px;" onchange="updateHiddenFromSelect(this, 'pays')">
			<option value=""></option>
		      <bean:define id="paysdata" name="myAnnuaireForm" property="listePays" />
			<html:optionsCollection name="paysdata" value="paysCode" label="pays_nom" />
		</html:select>
      </li>

</ul>  
</div>
<div class="profil" id="profilBlock" style="display:none;">
  <div class="ficheProfil" id="profilContent" ></div>
 	<div class="backAnnuaire"><a href="javascript:;" onclick="returnToAnnuaire()"><%=DicoTools.dico(dico_lang , "directory/back_directory")%></a></div>
</div>
<div class="resultat-recherche" id="resultat-recherche">
  <!-- <div class="votre-recherche"><strong><%=DicoTools.dico(dico_lang , "directory/new_in_country")%></strong></div> -->
    <div class="list-recherche-contener" style="height:428px;" >
  <logic:iterate name="myAnnuaireForm" property="listeLastProfil" id="userData">
    <div class="fiche">
      <bean:define id="paysdata" name="userData" property="pays" />
      <bean:define id="user_id" name="userData" property="id" />
      <bean:define id="user_pseudo" name="userData" property="user_pseudo" />
      
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
    </div>
  <div class="paginate"></div>
</div>
