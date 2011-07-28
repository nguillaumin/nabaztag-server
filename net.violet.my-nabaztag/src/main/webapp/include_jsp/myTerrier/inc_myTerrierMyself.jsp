<%@page pageEncoding="UTF-8" %>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.User" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>

<%response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang =	SessionTools.getLangFromSession(session, request);%>
<%
    final User theUser = SessionTools.getUserFromSession(request);
    final String userId;
    if (theUser == null) {
       userId = "0";
    } else {
       userId = theUser.getId().toString();
    }
%>
<% 	String user_main = Long.toString(SessionTools.getRabbitIdFromSession(session)); %>

<bean:define id="userMain" value="<%=user_main %>"/>


<html:form action="/action/myTerrierMyself" styleId="idForm" acceptCharset="UTF-8" onsubmit="return validateMyProfil();" >
<html:hidden property="dispatch" value="change"/>
<%--<html:hidden property="dispatch" value="defaultBehaviour"/>--%>
<%--<html:hidden property="old_lang" value="<%=lang%>" styleId="old_lang" />--%>

<div class="flat-block"> 
	<div class="flat-block-top">
		<h3 class="no-icone">
		
			<%--<logic:notEqual name="isConnected" value="-1">
				<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_title")%> : 
			</logic:notEqual>--%>
			<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_title")%> :
			<bean:write name="myTerrierMyselfForm" property="rabbitName"/>
					
		</h3>
	</div>

	<div class="flat-block-content">
		<div class="flat-block-content-inner">
		<%-- *********************************************** --%>

		<%-- DESCRIPTION --%>
		
		<div class="twoCol-left" >
			<label><%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_annonce")%></label>	<br />
			<html:textarea rows="5" property="rabbitAnnounce">
				<bean:define name="myTerrierMyselfForm" property="rabbitAnnounce" id="rabbitAnnounce" type="String"/>
				<%=rabbitAnnounce%>
			</html:textarea>		
		</div>

		<div class="twoCol-right" >
				
				<div align="center">
					<%-- sur une seule ligne, car IE6 (et 7...) rajoute sinon un espace tout pourri n'importe ou .... --%>
					<table border="0" cellpadding="0" cellspacing="0" ><tr><td align="center" valign="middle" id="imageHolder" ><logic:equal name="myTerrierMyselfForm" property="rabbitPicture" value="1"><img class="user_picture" src="../photo/<%=userId%>_S.jpg" /></logic:equal><logic:notEqual name="myTerrierMyselfForm" property="rabbitPicture" value="1"><img class="user_picture" src="../photo/default_S.jpg" /></logic:notEqual></td></tr></table>	
				</div>


				<%-- image validé --%>					
				 <div align="center" class="uploader-link">
				 		<div id="pictureLoader"></div>
					  <a href="javascript:;" onclick="$('#pictureLoader').toggle();" ><%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_picture_modify")%></a>
				</div>	
					
		</div>
		
		<hr class="spacer" />
		
		<%-- PRENOM/NOM --%>
		 <div class="form-line">
				<label class="center">
					<%=DicoTools.dico(dico_lang , "register/infos_user_first_name")%>
				</label>
				<span>
					<html:text property="firstName" styleClass="formToolTip"/>	
				</span>
			</div>

			<div class="form-line">
				<label class="center">
					<%=DicoTools.dico(dico_lang , "register/infos_user_last_name")%>
				</label>
				<span>
					<html:text property="lastName" styleClass="formToolTip"/>	
				</span>
			</div>
			
		
		<%-- DATE DE NAISSANCE --%>			
		 <div class="form-line">
			<label class="center">
				<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_birth_date")%>
			</label>
			<span>
					<html:select property="jour">
						<html:optionsCollection name="myTerrierMyselfForm" property="listeJour" label="label" value="id" styleClass="date-jour"/>
					</html:select>
					<html:select property="mois">
						<html:optionsCollection name="myTerrierMyselfForm" property="listeMois" label="label" value="id" styleClass="date-mois"/>
					</html:select>
					<html:select property="annee">
						<html:optionsCollection name="myTerrierMyselfForm" property="listeAnnee" label="label" value="id" styleClass="date-annee"/>
					</html:select>
	
			</span>
		</div>					
							
		<%-- SEXE --%>			
		 <div class="form-line">
			<label class="center">
				<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_i_am")%>
			</label>
			<span>
				<html:radio property="annuSexe" value="F"><%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_i_am_girl")%></html:radio>
					&nbsp;&nbsp;
				<html:radio property="annuSexe" value="H"><%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_i_am_boy")%></html:radio>
			</span>
		</div>								
							
		<%-- CODE POSTAL --%>			
		 <div class="form-line">
			<label class="center">
				<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_zip_code")%>
			</label>
			<span>
				<html:text property="annuCp" size="30" />
			</span>
		</div>	

		<%-- VILLE --%>			
		 <div class="form-line">
			<label class="center">
				<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_city")%>
			</label>
			<span>
				<html:text property="annuCity" size="30"  styleClass="custom"/>
			</span>
		</div>	

		<%-- COUNTRY --%>			
		 <div class="form-line">
			<label class="center">
				<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_country")%>
			</label>
			<span>
				<html:select property="annuCountry"  styleClass="select-pays">
				<!-- PaysData -->
					<html:optionsCollection property="listePays" label="label" value="paysCode"/>
				</html:select>
			</span>
		</div>	
		
		<logic:greaterThan name="userMain" value="0">							
			<%-- EMAIL DU LAPIN --%>			
			 <div class="form-line email" >
				<label class="center">
					<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_email")%>
				</label>
				<span>
					<bean:write name="myTerrierMyselfForm" property="rabbitMail"/>
				</span>
			</div>										
		</logic:greaterThan>
		
		<%-- JVEUX PAS ETRE DANS L'ANNUAIRE --%>			
		 <div class="form-line">
			<label class="center">
				&nbsp;
			</label>
			<span>
				<html:checkbox property="annuConfirm" value="1" />
				<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_no_directory")%>
			</span>
		</div>	
									
		<hr class="clearer" />

		<div class="buttons">					
			<input type="submit" class="genericBt" name="modifProfil" value="<%=DicoTools.dico(dico_lang, "myTerrier/myrabbit_modify_button")%>" />
		</div>


		<%-- *********************************************** --%>		
		</div>
	</div>
</div>
</html:form>

<script type="text/javascript">
	setTimeout(function(){ // load the picture loader
		divChangeUrl("pictureLoader", "myTerrierEditRabbitImage.do");	
	}, 200);
</script>