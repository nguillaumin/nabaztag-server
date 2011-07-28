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

<bean:define id="searched" value="<%=(request.getParameter("searched")==null) ? "" : request.getParameter("searched")%>" />

<bean:define name="myNabaztalandSubscribeForm" property="mode" id="mode" />
<bean:define name="myNabaztalandSubscribeForm" property="idNabcast" id="idNabcast"/>
<bean:define name="myNabaztalandSubscribeForm" property="nabcastData" id="NabcastData" />
<bean:define name="myNabaztalandSubscribeForm"  property="isReg"  id="isReg"/>


<bean:define id="n_id" name="NabcastData" property="nabcast_id"/>
<bean:define id="n_title" name="NabcastData" property="nabcast_titre"/>
<bean:define id="n_categ" name="NabcastData" property="nabcast_categ"/>
<bean:define id="n_desc" name="NabcastData" property="nabcast_description"/>
<bean:define id="n_author" name="NabcastData" property="nabcast_author_pseudo" />
<bean:define id="n_author_id" name="NabcastData" property="nabcast_author" />
<bean:define id="user_image_ok" name="NabcastData" property="nabcast_image" />
			
<div class="srv-cadre-contener service-introduction">
	
	<div class="main-cadre-top">
		<h2 class="srv-title" style="margin-left:11px;" ><%=n_categ%> - <span class="nabcast-title"><%=n_title%></span>
		( <%=DicoTools.dico(dico_lang, "srv_nabcast/author")%> <a href="javascript:;" onclick="TB_show('', '/vl/action/myProfil.do?height=515&width=800&user_id=<%=n_author_id%>&myFriend=1')"><span style="font-size:14px;"><%=n_author%></span></a> )</h2>
	</div>
	
	<div class="srv-background">	
		<div class="srv-cadre-content">

			<%-- ************************************************************************************************************* --%>

			<div class="srv-description">
			

					<div class="srv-image">
						<logic:notEqual name="user_image_ok" value="0">
								<img src='<%="../photo/"+n_author_id+"_S.jpg"%>' />
						</logic:notEqual>
						<logic:equal name="user_image_ok" value="0">
								<img src='<%="../photo/default_S.jpg"%>' />
						</logic:equal>
					</div>
			
					<div class="srv-desc-text" style="margin-left:0">
						<a class="thickbox link-more-options" href="<html:rewrite forward="goNabcastHisto"/>?idNabcast=<%=n_id%>&width=350&height=430"  ><%=DicoTools.dico(dico_lang , "bloc/myservices_nabcast_histo")%></a>
						<p><%=n_desc%></p>
					</div>
					<div class="nabcastSubscribe">
						<html:form action="/action/myNabaztalandSubscribe" styleId="nabcastConfig">
							<bean:define name="myNabaztalandSubscribeForm" property="heures" id="heures" />
							<bean:define name="myNabaztalandSubscribeForm" property="minutes" id="minutes" />	
							<bean:define name="myNabaztalandSubscribeForm" property="user_main" id="user_main" />		
							
							<hr class="spacer" />
							
							<div>
								<%=DicoTools.dico(dico_lang, "srv_nabcast_subscribe/choose_time")%>
							</div>
							
							<hr class="spacer" />
							
							<div align="center">
								<input id="time" type="hidden" class="hourPicker" value="<%=heures%>:<%=minutes%>" />			
								
								<%/* pour conserver la compatibilit� avec le code java derriere */%>
								<input id="heures" type="hidden" name="heures" value="" />
								<input id="minutes" type="hidden" name="minutes" value="" />
								<html:hidden name="myNabaztalandSubscribeForm" property="mode" value="2"/>
								<html:hidden name="myNabaztalandSubscribeForm" property="idNabcast" value="<%=idNabcast.toString()%>" />
								
								<hr class="spacer" />
								
								<logic:equal name="myNabaztalandSubscribeForm" property="isReg" value="1" >
									<input type="button" value="<%=DicoTools.dico(dico_lang, "srv_nabcast_subscribe/deactivate")%>" class="genericDeleteBt" onclick="nabcastSrvDelete(<%=idNabcast.toString()%>)" />
								</logic:equal>
								
								<html:submit value="<%=DicoTools.dico(dico_lang, "srv_nabcast_subscribe/activate")%>" style="margin-left:10px;" styleClass="genericBt" />							
							
							</div>
						</html:form>					
					</div>
			</div>

			<hr class="clearer" />

			<div class="srv-bottom-links">
				<div class="srv-bottom-subscribefull">&nbsp;</div>	
				<logic:equal name="searched" value="">				
				<a onclick="nablife.returnToSrvList()" href="javascript:;" class="srv-back" ><span><%=DicoTools.dico(dico_lang, "srv_nabcast_subscribe/back")%></span></a>
				</logic:equal>
				<logic:notEqual name="searched" value="">
				<a href="../action/myNablife.do?search=<bean:write name="searched" />" class="srv-back" ><span><%=DicoTools.dico(dico_lang, "srv_nabcast_subscribe/back")%></span></a>
				</logic:notEqual>
			</div>

			<%-- ************************************************************************************************************* --%>

			<hr class="clearer" />

		</div>
	</div>
	
</div>

<logic:notEmpty name="NabcastData" property="nabcast_shortcut">
	<bean:define id="shortcut" name="NabcastData" property="nabcast_shortcut"/>
<%-- <div class="main-cadre-contener">

	<div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang , "services/direct_link")%></h2>
	</div>
	
		<div class="main-cadre-content">
			<hr class="spacer"/>
			<div class="srv-main-config">
				<p>[Introduction super pour expliquer a quoi sert ce lien magique]</p>
				<hr class="spacer"/>
				
				<p><a href="http://del.icio.us/post?url=http://my.nabaztag.com/<%=n_shortcut%>&title=<%=n_title%>@Nabaztag">[Ajouter à mon del.icio.us]</a></p>
				<p><a href="http://digg.com/submit?phase=2&url=http://my.nabaztag.com/<%=n_shortcut%>">[Ajouter à mon Digg It]</a></p>
				
				<hr class="spacer"/>
				
				<p>[URL de cette page :]</p>
				<p align="center"><input class="auto-select-field" type="text" value="http://my.nabaztag.com/<%=n_shortcut%>" /></p>		
			</div>
			<hr class="spacer"/>
			
		</div>
</div>--%>

<div class="main-cadre-contener">

	<div class="main-cadre-top">
		<h2><%=DicoTools.dico(dico_lang , "services/direct_link")%></h2>
	</div>
	
		<div class="main-cadre-content">
			<hr class="spacer"/>
			<div class="srv-main-config">
				<p><%=DicoTools.dico(dico_lang , "srv_all/shortcut_URL")%></p>
				<p align="center"><input class="auto-select-field" type="text" value="http://my.nabaztag.com/n/<%=shortcut%>" /></p>		
			</div>
			<hr class="spacer"/>
			
		</div>
</div>

</logic:notEmpty>

	<script type="text/javascript">
		$('input.hourPicker').hourSelect_Init( $("#_user_24").val(), false );
		$("#nabcastConfig").submit(function(){
			return nablifeValidateNabcastConfig(<logic:equal name="isReg" value="1">true</logic:equal>); 
		});
		
		$("input.auto-select-field").click(function(){
			$(this).select();
		});
		$("input.auto-select-field").keydown(function(){
			return false;
		});

	</script>
