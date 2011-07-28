<%@page pageEncoding="UTF-8" %>
<% response.setContentType("text/html;charset=UTF-8"); %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<div id="navigation">

	<div id="internationalization">
		<ul>
			<li><a href="adminChangeLocale.do?dispatch=english&redirect=goHome"><img src="../include_img/flags/en.gif" border="0"/></a></li>
			<li><a href="adminChangeLocale.do?dispatch=french&redirect=goHome"><img src="../include_img/flags/fr.gif" border="0"/></a></li>
			<li><a href="adminChangeLocale.do?dispatch=japanese&redirect=goHome"><img src="../include_img/flags/jp.gif" border="0"/></a></li>
		</ul>
	</div>

	<div class="nav-prim">
		<a href="admin.do?dispatch=disconnect">Log off</a>
		<br /><br />
		<hr />
		<h2>Objects</h2>
		<ul>
			<li><a href="adminSearchObject.do?dispatch=load">Search</a></li>
		</ul>
	</div>
	<div class="nav-prim">
		<h2>Services</h2>
		<ul>
			<li><a href="adminSearchApplication.do?dispatch=load">Search</a></li>
			<li><a href="adminAddApplication.do?dispatch=load">Add</a></li>
			<li><a href="adminRankApplication.do?dispatch=load">Rank</a></li>
		</ul>
	</div>
	<div class="nav-prim">
		<h2>News</h2>
		<ul>
			<li><a href="adminSearchNews.do?dispatch=load">Search</a></li>
			<li><a href="adminAddNews.do?dispatch=load">Add</a></li>
		</ul>
	</div>
	<div class="nav-prim">
		<h2>Press</h2>
		<ul>
			<li><a href="adminSearchPress.do?dispatch=load">Search</a></li>
			<li><a href="adminAddPress.do?dispatch=load">Add</a></li>
		</ul>
	</div>
	<div class="nav-prim">
		<h2>Store</h2>
		<ul>
			<li><a href="adminSearchStore.do?dispatch=load">Search</a></li>
			<li><a href="adminAddStore.do?dispatch=load">Add</a></li>
		</ul>
	</div>
	<div class="nav-prim">
		<h2>Weather</h2>
		<ul>
			<li><a href="adminAddWeather.do?dispatch=load">Add</a></li>
		</ul>
	</div>
	<div class="nav-prim">
		<h2>Dico</h2>
		<ul>
			<li><a href="adminSearchDico.do?dispatch=load">Search</a></li>
			<li><a href="adminAddDico.do?dispatch=load">Add</a></li>
			<li><a href="adminImportDico.do?dispatch=load">Import</a></li>
			<li><a href="adminExportDico.do?dispatch=load">Export</a></li>
		</ul>
	</div>
</div>