<%@page pageEncoding="UTF-8"%>
<%@ page import="net.violet.platform.util.StaticTools" %>
<%@ page import="net.violet.platform.util.SessionTools" %>
<%@ page import="net.violet.platform.datamodel.Lang" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ include file="/include_jsp/utils/inc_dico.jsp" %>


<%	Lang dico_lang = SessionTools.getLangFromSession(session, request);%>

<bean:define name="myNabaztalandHomeForm" property="languser" id="lang" type="String"/>
<bean:define id="mode" name="myNabaztalandHomeForm" property="mode"/>
<bean:define id="langCategorie" name="myNabaztalandHomeForm" property="langCategorie" />
<bean:define id="nabcastCateg" name="myNabaztalandHomeForm" property="nabcastCateg" />
<bean:define id="nameCateg" name="myNabaztalandHomeForm" property="nameCateg" />
<bean:define id="idCateg" name="myNabaztalandHomeForm" property="idCateg"/>
<bean:size 	id="nbLangue" name="myNabaztalandHomeForm" property="langList" />
<bean:define id="listeCategorie" name="myNabaztalandHomeForm" property="listeCategorie" />
<bean:define id="listeNabcastCateg" name="myNabaztalandHomeForm" property="listeNabcastCateg" />
<bean:define id="listeLang" name="myNabaztalandHomeForm" property="langList" />
<bean:define id="user_main" name="myNabaztalandHomeForm" property="user_main" />			

<bean:parameter id="type" name="type" value=""/> <%-- indique si on est en mode nabcast ou service --%>

<div class="main-layout">
	<div class="menu">
		<%@ include file="/include_jsp/myNablife/inc_nablifeMenu.jsp" %>
	</div>
	
	<div class="intro-cadre-contener">
		<div class="intro-cadre-top">
			<a href="myNablife.do"><h3 ><%=DicoTools.dico(dico_lang, "myNablife/nabcasts_title")%></h3></a>
		</div>
		<div class="intro-cadre-content">
			<h2 class="categTitle"><%=nameCateg%></h2>
		</div>
	</div>
	
	<hr class="clearer" />
	
<%----------------------------------------------------------------
	Listing des nabcasts
	--------------------------------------------------------------%>				

	<logic:iterate id="nabcastData" name="myNabaztalandHomeForm" property="listNabcast">
		<bean:define id="n_id" name="nabcastData" property="nabcast_id"/>
		<bean:define id="n_title" name="nabcastData" property="nabcast_titre"/>
		<bean:define id="n_categ" name="nabcastData" property="nabcast_categ"/>
		<bean:define id="n_desc" name="nabcastData" property="nabcast_description"/>
		<bean:define id="n_author" name="nabcastData" property="nabcast_author"/>
		<bean:define id="user_image_ok" name="nabcastData" property="nabcast_image"/>
		<bean:define id="isSubscribed" name="nabcastData" property="isSubscribed"/>	

		<div class="flat-block list-services"  > 
			
			<logic:equal name="isSubscribed" value="1">
				<input type="hidden" value="true" name="subscribed" />
		    </logic:equal>
			<logic:equal name="isSubscribed" value="0">
				<input type="hidden" value="false" name="subscribed" />							
			</logic:equal>

				
			<div class="flat-block-top" >
				<img class="srv-ico" src="../include_img/services/icones_services/icon_nabcast.gif" />
				<h3>
					<span title="<%=n_title%>">
							<%=n_title%>
					</span>
				</h3>
			</div>
			
			<div class="flat-block-content" >
				<div class="flat-block-content-inner" >
					
					<div class="description">
						<div class="image">
			      			<logic:equal name="isSubscribed" value="-1">
				      			<logic:notEqual name="user_image_ok" value="0">
									<img  border="0" src='<%="../photo/"+n_author+"_S.jpg"%>' height="100" />
								</logic:notEqual>
								<logic:equal name="user_image_ok" value="0">
									<img  border="0" src='<%="../photo/default_S.jpg"%>' height="100" />
								</logic:equal>
			 		        </logic:equal>
			        		
			        		<logic:notEqual name="isSubscribed" value="-1">
								<a href="javascript:;" onclick="srvConfigToggle('id_<%=n_id%>_1', '<html:rewrite forward="goNabcastSubscribe" />?idNabcast=<%=n_id%>');"  >
								
								<logic:notEqual name="user_image_ok" value="0">
									<img  border="0" src='<%="../photo/"+n_author+"_S.jpg"%>' height="100" />
								</logic:notEqual>
								<logic:equal name="user_image_ok" value="0">
									<img  border="0"  src='<%="../photo/default_S.jpg"%>' height="100"  />
								</logic:equal>
								
								</a>
			        		</logic:notEqual>
						</div>									
						<p>
							<%=n_desc%>
						</p>
						
						<%-- <div class="truncated"></div> --%>
														
					</div>
					
					<%-- affichage du bouton si on est logué--%>
					<logic:notEqual name="isSubscribed" value="-1">
						<a class="add-srv" href="<html:rewrite forward="goNabcastSubscribe" />?idNabcast=<%=n_id%>">+</a>
						<div class="text-status-holder" >
							<strong>
								<%-- pas inscrit --%><logic:equal name="isSubscribed" value="0"><%=DicoTools.dico(dico_lang, "srv_all/subscribe")%></logic:equal>
								<%-- inscrit --%><logic:greaterThan name="isSubscribed" value="0"><%=DicoTools.dico(dico_lang, "srv_all/configuration")%></logic:greaterThan>
							</strong>
						</div>
					</logic:notEqual>
														
				</div>
			</div>
		</div>
		
	</logic:iterate>
				

	<hr class="clearer" />
</div>