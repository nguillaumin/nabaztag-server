<%--
	inclus dans inc_nabaztalandHome.jsp et inc_allServices.jsp
--%>
<table>
	<tr>
		<td>
			
			
			<div class="categ-chooser">
				<select>
					<option class="choosetxt service" value="" ><%=DicoTools.dico(dico_lang, "myNablife/services_categories_choose")%></option>		
					<optgroup label="<%=DicoTools.dico(dico_lang, "myNablife/services_categories")%>">
						<logic:iterate name="listeCategorie" id="srvCategData" offset="0">
							<bean:define id="name" name="srvCategData" property="label" />
							<bean:define id="id" name="srvCategData" property="id" />
							
							<option class="service" value="myServicesHome.do?type=service&idCateg=<%=id%>&mode=1&langCategorie=<%=langCategorie%>"><%=DicoTools.dico_if(dico_lang, name.toString())%></option>
			
						</logic:iterate>
					</optgroup>
					
					<optgroup label="<%=DicoTools.dico(dico_lang, "myNablife/nabcasts_categories")%>">
						<%-- <option class="choosetxt service" value="myNabaztalandHome.do" ><%=DicoTools.dico(dico_lang, "myNablife/nabcasts_categories_choose")%></option> --%>
						<logic:iterate name="listeNabcastCateg" id="nabcastCategData" offset="0">
							<bean:define id="name" name="nabcastCategData" property="nabcastcateg_val" />
							<bean:define id="id" name="nabcastCategData" property="nabcastcateg_id" />
							
							<option class="nabcast" value="myNabaztalandHome.do?type=nabcast&idCateg=<%=id%>&mode=1&langCategorie=<%=langCategorie%>"><%=name.toString()%></option>	
										
						</logic:iterate>					
					</optgroup>
					
				</select>
			</div>	
		</td>
		<td>
			<%-- on affiche les langues que si on en a plus de 1 --%>
			<logic:greaterThan name="nbLangue" value="1">
				<ul class="langSelect">
					<%-- TODO: logic:iterate, each row -> check if user and selected --%>
					<logic:iterate name="listeLang" id="langInList">
						<bean:define name="langInList" property="lang_id" id="theLangId"/>
						<bean:define name="langInList" property="lang_iso_code" id="lang_iso_code"/>
						<li class="<%=lang_iso_code.toString() %> <logic:equal name="langCategorie"  value="<%=theLangId.toString() %>">selected</logic:equal>" ><a href="<html:rewrite forward="goServicesHome"/>?langCategorie=<%=theLangId.toString() %>" ><span><%=lang_iso_code.toString() %></span></a></li>
					</logic:iterate>
				</ul>
			</logic:greaterThan>
		</td>
	</tr>
</table>


<ul class="menulinks">
	<%-- logu� avec un lapin --%>
	<logic:notEqual name="user_main" value="0">
		<li>
			<%=DicoTools.dico(dico_lang, "myNablife/menuLink1")%>
		</li>
	</logic:notEqual>
	<logic:notEqual name="user_id" value="0">
		<li>
			<%=DicoTools.dico(dico_lang, "myNablife/menuLink2")%>
		</li>
	</logic:notEqual>
	<li>
		<%=DicoTools.dico(dico_lang, "myNablife/menuLink3")%>
	</li>
</ul>