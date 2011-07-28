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

<bean:define name="myServicesHomeForm" property="languser" id="lang" type="String"/>
<bean:define id="mode" name="myServicesHomeForm" property="mode"/>
<bean:define id="langCategorie" name="myServicesHomeForm" property="langCategorie" type="Long"/>
<bean:define id="idCateg" name="myServicesHomeForm" property="idCateg"/>
<bean:define id="nameCateg" name="myServicesHomeForm" property="nameCateg"/>
<bean:size id="nbLangue" name="myServicesHomeForm" property="langList" />
<bean:define id="listeCategorie" name="myServicesHomeForm" property="listeCategorie" />
<bean:define id="listeNabcastCateg" name="myServicesHomeForm" property="listeNabcastCateg" />
<bean:define id="listeLang" name="myServicesHomeForm" property="langList" />
<bean:define id="user_main" name="myServicesHomeForm" property="user_main" />
<bean:define id="user_id" name="myServicesHomeForm" property="user_id" />

<bean:parameter id="type" name="type" value=""/> <%-- indique si on est en mode nabcast ou service --%>

<div class="main-layout" >
			
			<div class="menu">
				<%@ include file="/include_jsp/myNablife/inc_nablifeMenu.jsp" %>
			</div>
			
			<%----------------------------------------------------------------
				Texte introductif, seulement sur la "home" de nablife 
				--------------------------------------------------------------%>
			<logic:equal name="idCateg" value="0">
				<div class="intro-cadre-contener">
					<div class="intro-cadre-top">
						<h2><%=DicoTools.dico(dico_lang, "myNablife/intro_titre")%></h2>
					</div>
					<div class="intro-cadre-content">
						<div class="intro" >
							<%=DicoTools.dico(dico_lang, "myNablife/intro_text")%>
						</div>
					</div>
					
				</div>
			</logic:equal>
			<%----------------------------------------------------------------
				Texte introductif, seulement sur les pages "categories"
				--------------------------------------------------------------%>			
			<logic:notEqual name="idCateg" value="0">
				<div class="intro-cadre-contener">
					<div class="intro-cadre-top">
						<a href="myNablife.do"><h3 ><%=DicoTools.dico(dico_lang, "myNablife/services_title")%></h3></a>
					</div>					
					<div class="intro-cadre-content">
						<h2 class="categTitle"><%=DicoTools.dico_if(dico_lang, nameCateg.toString())%></h2>
					</div>
				</div>
			</logic:notEqual>	
			
			<hr class="clearer" />
								
			<%----------------------------------------------------------------
				Listing des services
				--------------------------------------------------------------%>				

				<logic:iterate id="nablifeServicesData" name="myServicesHomeForm" property="listServices">
					<bean:define id="s_id" name="nablifeServicesData" property="id"/>
					<bean:define id="s_name" name="nablifeServicesData" property="name"/>
					<bean:define id="s_desc" name="nablifeServicesData" property="desc"/>
					<bean:define id="s_link" name="nablifeServicesData" property="link"/>
					<bean:define id="s_srvImg" name="nablifeServicesData" property="srvImg" type="String"/>
					<bean:define id="s_srvIcone" name="nablifeServicesData" property="srvIcone" type="String"/>
					<bean:define id="s_type_config" name="nablifeServicesData" property="srvlist_setup" />
					<bean:define id="isSubscribed" name="nablifeServicesData" property="isSubscribed"/>
					

					<div class="flat-block list-services" > 
						
						<logic:equal name="isSubscribed" value="1">
							<input type="hidden" value="true" name="subscribed" />
					    </logic:equal>
						<logic:equal name="isSubscribed" value="0">
							<input type="hidden" value="false" name="subscribed" />							
						</logic:equal>

							
						<div class="flat-block-top" >
							<img class="srv-ico" src="<%=s_srvIcone%>" alt="<%=DicoTools.dico_if(langCategorie,s_name.toString())%>" />
							<h3>
								<span title="<%=DicoTools.dico_if(langCategorie,s_name.toString())%>">
										<%=DicoTools.dico_if(langCategorie,s_name.toString())%>
								</span>
							</h3>
						</div>
						
						<div class="flat-block-content" >
							<div class="flat-block-content-inner" >
								
								<div class="description">
									<div class="image">
										<logic:equal name="isSubscribed" value="-1">
											<img border="0" src="<%=s_srvImg%>" alt="<%=DicoTools.dico_if(langCategorie,s_name.toString())%>" />
										</logic:equal>
										
										<logic:notEqual name="isSubscribed" value="-1">
											<a href="<%=s_link.toString()%>" ><img  border="0" src="<%=s_srvImg%>" alt="<%=DicoTools.dico_if(langCategorie,s_name.toString())%>" /></a>
										</logic:notEqual>	
									</div>									
									<p>
										<%=DicoTools.dico_if(langCategorie,s_desc.toString())%>
									</p>
									<logic:notEqual name="idCateg" value="0">
										<div class="truncated"></div>
									</logic:notEqual>		
															
								</div>
								
								<%-- affichage du bouton si on est logué--%>
								<logic:notEqual name="isSubscribed" value="-1">
									<a class="add-srv" href="<%=s_link.toString()%>">+</a>
									<div class="text-status-holder" >
										<strong>
											<logic:equal name="s_type_config" value="CONFIG">
												<%-- pas inscrit --%><logic:equal name="isSubscribed" value="0"><%=DicoTools.dico(dico_lang, "srv_all/subscribe")%></logic:equal>
												<%-- inscrit --%><logic:greaterThan name="isSubscribed" value="0"><%=DicoTools.dico(dico_lang, "srv_all/configuration")%></logic:greaterThan>
											</logic:equal>
											<logic:equal name="s_type_config" value="INFO">
												<%-- pas inscrit --%><%-- juste en savoir plus --%><logic:equal name="isSubscribed" value="3"><%=DicoTools.dico(dico_lang, "srv_all/learn_more")%></logic:equal>
												<%-- inscrit --%><logic:equal name="isSubscribed" value="1"><%=DicoTools.dico(dico_lang, "srv_all/configuration")%></logic:equal>
												
											</logic:equal>											
										</strong>
									</div>
								</logic:notEqual>
																	
							</div>
						</div>
					</div>
					
				</logic:iterate>

			
			<hr class="clearer"/>
			

	
</div>
