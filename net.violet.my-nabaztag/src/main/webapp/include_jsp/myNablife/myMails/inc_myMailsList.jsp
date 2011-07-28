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

<bean:define  name="myMailsListForm" property="isReg" id="isReg"/>
<bean:define  name="myMailsListForm" property="justList" id="justList"/>
<bean:define property="serviceName" name="myMailsListForm" id="serviceName" type="String"/>


<%/* très très moche cette condition.... */%>
<logic:notEqual name="myMailsListForm" property="justList" value="true">
<div class='backLink' ><a href='javascript:;' onclick='srvConfigToggle()'><%=DicoTools.dico(dico_lang, "srv_mail/back")%></a></div>
<div class="tabSousNavContener-tr">

			<div class="tabSousNavContener-bl">
				<!-- ******************************************** CONTENT ***************************************************** -->   

		<div class="srv-main-config" >
			<div class="title-srv">
				<h1><%=DicoTools.dico_if(dico_lang, serviceName)%></h1>
			</div>
		</div>
		<div id="description-srv">
			<hr class="spacer" />

			<p class="short-desc">
				<%=DicoTools.dico(dico_lang , "srv_mail_full/description")%>
			</p>
			
			<p class="long-desc">
				<%=DicoTools.dico(dico_lang , "srv_mail_full/long_description")%>
			</p>
		</div>
		
		<hr class="spacer" />			
		
	<div class="Column33" style="width:44%; margin-right:8px;" >
		<div class="srv-list-entries" >
			<!-- ******************************** Affichage de la liste des comptes mail surveill�s  **************************** -->
			<h2><%=DicoTools.dico(dico_lang, "srv_mail/account_list")%></h2>
</logic:notEqual>
	<ul id="list-contener">
		<logic:iterate name="myMailsListForm" property="mailsAccounts" id="mailUserData">
			
				<!-- Les informations du compte -->
				<bean:define id="identifiant" name="mailUserData" property="identifiant" />	
				<bean:define id="accountLogin" name="mailUserData" property="mailUser_login" />
				<bean:define id="accountPassive" name="mailUserData" property="mailUser_passive" />
				<bean:define id="accountNbr" name="mailUserData" property="mailUser_nbr" />
				<bean:define id="accountSrv_src" name="mailUserData" property="mailUser_srvSrc" />
				<bean:define id="accountHost" name="mailUserData" property="mailUser_host" />
				<bean:define id="accountType" name="mailUserData" property="mailUser_type"/>
	
				<li id="srvItem_<%=identifiant%>" >
					<ul class="ico-list" >
						<li class="supprimer">
							<!-- <a href="<html:rewrite forward="goMailsCreate"/>?srv_src=<%=accountSrv_src%>&delete=1">supprimer facon Jerem</a> -->
							<a class="link-delete suppr" onclick="srvDelete(<%=identifiant%>, '<%=accountSrv_src%>')" href='javascript:;'><span><%=DicoTools.dico(dico_lang, "srv_mail/account_delete")%></span></a>
						</li>
							
						<%/* pour update setter queFaire a : update_account et pr�remplir le formulaire */%>
					</ul>
					<ul class="txt-list">
						<li class="nom"><strong><%=accountLogin %><br /><%=accountHost%></strong><!--login pour le compte mail--></li>
						<logic:equal name="accountPassive" value="1">
							<li><%=DicoTools.dico(dico_lang , "srv_mail/pending")%> : <%=accountNbr %> <!--nbr de mails nouveaux depuis la derniere verifcation--></li>
						</logic:equal>
						<li class="keywords">
						<!-- Les informations du compte -->
						<logic:iterate id="mailKeywordData" name="mailUserData" property="keywords">
							<bean:define id="keywordSrv" name="mailKeywordData" property="mailKeyword_srvSrc" />
							<bean:define id="keywordWord" name="mailKeywordData" property="mailKeyword_keyword" />
							<bean:define id="keywordPassive" name="mailKeywordData" property="mailKeyword_passive" />
							<bean:define id="keywordMusicName" name="mailKeywordData" property="mailKeyword_musicName" />
							<!-- <li> keywordSrv : <%=keywordSrv%><br /> </li> -->			
							<!-- keywordPassive : <li><%=keywordPassive%><br /></li>-->
								<ul>
									<li>
										<% /* flash lumineux activé */ %>
										<logic:equal name="keywordPassive" value="1">
											<a href="javascript:;" class="lumi-on" ><span><%=accountPassive%></span></a>
										</logic:equal>
													
										<% /* flash lumineux desactivé */ %>
										<logic:equal name="keywordPassive" value="0">
											<a href="javascript:;" class="lumi-off" ><span><%=accountPassive%></span></a>
										</logic:equal>
																			
										<strong><%=DicoTools.dico(dico_lang, "srv_mail/keyword")%></strong> <%=keywordWord%>
									</li>
									<li><!-- <strong>keywordMusicName :</strong> </li>--><%=keywordMusicName%></li>
								</ul>
							
						</logic:iterate>	
						</li>					
						<li class="modifier">
							<a class="link-modify" href="<html:rewrite forward="goMailsCreate"/>?srv_src=<%=accountSrv_src%>"><%=DicoTools.dico(dico_lang, "srv_mail/button_modify")%></a>
						</li>
						
					</ul>
				</li>
		</logic:iterate>
		<li style="border:none; text-align:center;">

				<%/* Supression */%>		
				<logic:notEqual name="isReg" value="0">
					<html:button value="<%=DicoTools.dico(dico_lang, "srv_mail/deactivate")%>" property="del" styleClass="genericDeleteBt" onclick="srvDelete()" />
				</logic:notEqual>

				<logic:notEmpty name="myMailsListForm" property="mailsAccounts" >
					<a class="link-modify" href='myMailsCreate.do'><input class="genericBt" type="button" value="<%=DicoTools.dico(dico_lang, "srv_mail/add")%>"/></a>
				</logic:notEmpty>

		</li>

<%/* très très moche cette condition.... */%>
</ul>

<logic:notEqual name="myMailsListForm" property="justList" value="true">
		<!-- ************************************************************************************************ -->
		</div>
	</div>
	<div class="srv-config Column50" id="config" style="float:left; min-height:100px;">
	&nbsp;
	</div>
			<hr class="spacer" />

			<div id="keskidit" class="keskidit">
				<div class="inner" ></div>
			</div>	
				<!-- ******************************************** /CONTENT ***************************************************** -->   				
			<hr class="spacer" />
			</div>

</div>
	
</logic:notEqual>
	
<script type="text/javascript">
	<logic:notEqual name="myMailsListForm" property="justList" value="true">
		// on charge le formulaire d'ajout de compte
		divChangeUrl("config", "myMailsCreate.do");
	</logic:notEqual>
	
	/* liens de modifs */
	$("a.link-modify").each(function(){
		var url = $(this).attr("href");
		
		if ( $(this).attr("href") != "javascript:;" ) {
			
			$(this).attr("href", "javascript:;");
			$(this).click(function(){
				divChangeUrlBackground("config", url);
			});
			
		}
		
		
	});
	if ( $("#prev_srv-mail-n0").length < 1) {
		addPreview("srv-mail-n0", 		'<%=DicoTools.dico(dico_lang , "srv_mail/no_mail")%>' );
		addPreview("srv-mail-n1", 		'<%=DicoTools.dico(dico_lang , "srv_mail/one_mail")%>' );
		addPreview("srv-mail-n2", 		'<%=DicoTools.dico(dico_lang , "srv_mail/two_mail")%>' );		
		addPreview("srv-mail-n3", 		'<%=DicoTools.dico(dico_lang , "srv_mail/threemore_mail")%>' );
	}	
</script>