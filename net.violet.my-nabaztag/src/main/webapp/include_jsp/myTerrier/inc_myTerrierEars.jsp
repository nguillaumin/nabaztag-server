<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>

<table cellpadding="0" cellspacing="0" class="content">
  <tr>
  	<td class="title"><h2>... et ses oreilles </h2></td>
    <td valign="top" >
    
    	<div id="hautOreilles">
    		<p>
				Découvrez et jouez avec les oreilles de Nabaztag,<br />
				Unies ou dépareillées, à vous de voir comment vous préférez personnaliser votre Nabaztag<br />
				Essayez-les en cliquant dessus, ou bien laissez faire le hasard ! 
    		</p>
    	</div>
    	
    	<div id="gaucheOreilles">
 			<a href="javascript:changeOreilleGauche('jaune','Jaune')"><img src="../include_img/terrier/oreilles/jaune.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('argent','Argent')"><img src="../include_img/terrier/oreilles/argent.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('or','Or')"><img src="../include_img/terrier/oreilles/or.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('croise','Etoiles Argent')"><img src="../include_img/terrier/oreilles/etoiles.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('rayeejaune','Spirale Jaune')"><img src="../include_img/terrier/oreilles/rjaune.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('rayeenoire','Spirale Noire')"><img src="../include_img/terrier/oreilles/rnoire.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('blanc','Blanc')"><img src="../include_img/terrier/oreilles/blanc.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('rouille','Corail')"><img src="../include_img/terrier/oreilles/orange.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('rayeebleue','Rayures Bleues')"><img src="../include_img/terrier/oreilles/rbleues.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('poids','Pois Rouge')"><img src="../include_img/terrier/oreilles/poidsRouge.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('cyan','Vert d\'eau')"><img src="../include_img/terrier/oreilles/cyan.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('magenta','Fuchsia')"><img src="../include_img/terrier/oreilles/magenta.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('rose','Rose')"><img src="../include_img/terrier/oreilles/rose.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleGauche('anis','Vert Anis')"><img src="../include_img/terrier/oreilles/anis.gif" height="88" width="27" /></a>
    	</div>
    	
    	<div id="milieuOreilles">
   			<span class="corpsLapin">
    			<img src="../include_img/terrier/oreilles/gauche_blanc.gif" height="117" width="28" name="og" id="og" />
    			<img src="../include_img/terrier/oreilles/crane.gif" height="38" width="37" />
    			<img src="../include_img/terrier/oreilles/droite_blanc.gif" height="117" width="28" name="od" id="od" /><br />
    			<img class="corps" src="../include_img/terrier/oreilles/corps.gif" height="156" width="145" />   		  
    		</span><br />
    		<span id="oreilleGaucheLib" class="textBlancG">Blanc</span>
    		<span id="oreilleDroiteLib" class="textBlancD">Blanc</span><br />
    		<span class="validButton"> <input type="button" onClick="putImage()" value="Essayer au hasard" /></span>
    	</div>
    	
    	<div id="droiteOreilles">
			<a href="javascript:changeOreilleDroite('jaune','Jaune')"><img src="../include_img/terrier/oreilles/jaune.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('argent','Argent')"><img src="../include_img/terrier/oreilles/argent.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('or','Or')"><img src="../include_img/terrier/oreilles/or.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('croise','Etoiles Argent')"><img src="../include_img/terrier/oreilles/etoiles.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('rayeejaune','Spirale Jaune')"><img src="../include_img/terrier/oreilles/rjaune.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('rayeenoire','Spirale Noire')"><img src="../include_img/terrier/oreilles/rnoire.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('blanc','Blanc')"><img src="../include_img/terrier/oreilles/blanc.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('rouille','Corail')"><img src="../include_img/terrier/oreilles/orange.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('rayeebleue','Rayures Bleues')"><img src="../include_img/terrier/oreilles/rbleues.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('poids','Pois Rouge')"><img src="../include_img/terrier/oreilles/poidsRouge.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('cyan','Vert d\'eau')"><img src="../include_img/terrier/oreilles/cyan.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('magenta','Fuchsia')"><img src="../include_img/terrier/oreilles/magenta.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('rose','Rose')"><img src="../include_img/terrier/oreilles/rose.gif" height="88" width="27" /></a>
			<a href="javascript:changeOreilleDroite('anis','Vert Anis')"><img src="../include_img/terrier/oreilles/anis.gif" height="88" width="27" /></a>
    	</div>
	</td>
  </tr>
</table>

