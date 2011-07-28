// GESTION DES COOKIEs ----------------------------------------------------------------
function setCookie(nom, valeur) {
	var argv=setCookie.arguments;
	var argc=setCookie.arguments.length;
	var expires=(argc > 2) ? argv[2] : null;
	var path=(argc > 3) ? argv[3] : null;
	var domain=(argc > 4) ? argv[4] : null;
	var secure=(argc > 5) ? argv[5] : false;
	document.cookie=nom+"="+escape(valeur)+
	((expires==null) ? "" : ("; expires="+expires.toGMTString()))+
	((path==null) ? "" : ("; path="+path))+
	((domain==null) ? "" : ("; domain="+domain))+
	((secure==true) ? "; secure" : "");
}



function getCookieVal(offset) {
	var endstr=document.cookie.indexOf (";", offset);
	if (endstr==-1) endstr=document.cookie.length;
	return unescape(document.cookie.substring(offset, endstr));
}

function getCookie(nom) {
	var arg=nom+"=";
	var alen=arg.length;
	var clen=document.cookie.length;
	var i=0;
	while (i<clen) {
		var j=i+alen;
		if (document.cookie.substring(i, j)==arg) return getCookieVal(j);
		i=document.cookie.indexOf(" ",i)+1;
		if (i==0) break;
	}
	return null;
}

function hideMaintenanceMsg(date){
	var expire = new Date(date);
	setCookie("maintenanceWarning", "hide", expire);
	
	$("div.top-warning-msg").animate({'opacity':'0.0'});
}

// -----------------------------------------------------------------------------------

// pas sur que �a marche sur tous les types....
// surtout sur Safari ;)
// test� pour Array
function varType(quoidoncquesse){
 
   if (! (quoidoncquesse instanceof Object)){
      return (typeof(quoidoncquesse))
   }
   else {
      switch (true)  {
         case (quoidoncquesse instanceof String) : return "String"; break;
         case (quoidoncquesse instanceof Array)  : return "Array";  break;
         case (quoidoncquesse instanceof RegExp) : return "RegExp"; break;
      }	
   }                   			 		
 
}

function isArray(v) {
	return varType(v) == "Array";
}

function goToTheTopOfThePage(){
	$(document).scrollTop(0);
}

function goToAnchor(anchor) {
		window.location.replace("#"+anchor);
}

function goToFirstErrorOfPage(){
	$("div.generic-error, ul.generic-error").filter(":first").before("<a name='error'>");
	window.location.replace("#error");
}

function redirect(url) {
	window.location.replace(antiCacheUrl(url));
}

var Url = function() {
	
	return {
		
		argReplace: function(argToFind, replaceBy, myUrl){
			if (myUrl==null) { // si on ne donne pas d'url on prend celle en cours
				var myUrl = document.location.href;
			}
				
			var vars = Url._getVars(myUrl);
			var host = myUrl.split("\?")[0];
			
			var change = new Object;
			change[argToFind] = replaceBy;
			
 			$.extend(vars, change);

 			return host + "?" + Url._serialize(vars);
 		},
		
		// retourne un tableau avec les args (Array)
		_getVars: function(myUrl){
			var varTable = new Object;
			
			var search = myUrl.split("\?")[1];
			
			if (search==null) return [];
			
			search = search.split("#")[0];
			
			var vars = search.split("&");
	
			for (var i=0; i<vars.length; i++) {
				var pair = vars[i].split("=");

				varTable[ pair[0] ] = pair[1];
				
			}
			
			return varTable;
		},
		
		_serialize: function(obj){
			var u = "";
			if (obj==null) {
					return false;
			}

			for ( keyVar in obj ) {
   				u +=  keyVar + "=" +  obj[keyVar] + "&";
			}
			
			u = u.substr( 0, u.length -1  );	// on vire le dernier &
			
			return u;
		}
		
	}
}();

function strReplace(s, r, w){
     return s.split(r).join(w);
}

function addslashes(str) {
	str = strReplace(str, "'", "\\'");
	str = strReplace(str, "\"", "\\\"");	
	return str;
}

function stripslashes(str) {
	str=str.replace(/\\'/g,'\'');
	str=str.replace(/\\"/g,'"');
	str=str.replace(/\\\\/g,'\\');
	str=str.replace(/\\0/g,'\0');
	return str;
}

function antiCacheUrl(url){
	var ts = new Date().getTime(); 

	if (url.indexOf("?")>0) {
		url+="&nocache="+ts;
	} else {
		url+="?nocache="+ts;		
	}	
	
	return url;
}

function strpos(str, ch) { 
	if (str == null) return;
	var s = str.indexOf(ch);
	return s;
}

// format YYYY-MM-JJ
function checkDate(dateStr) {
	if(dateStr < 10) return false;
	var dateTab = dateStr.split("-");
	
	//if no 3 elements or elements isNAN
	if((dateTab.length != 3) || isNaN(parseInt(dateTab[0])) || isNaN(parseInt(dateTab[1])) || isNaN(parseInt(dateTab[2]))) return false;
	
	//create date object. Warning, months form 0 to 11 (YYYY/MM/DD)
	var dateObj = new Date(eval(dateTab[0]),eval(dateTab[1])-1,eval(dateTab[2]));
	
	//2K bug fix
	var theyear = dateObj.getYear();
	if((Math.abs(theyear)+"").length < 4) theyear = theyear + 1900;
	
	//check if dateObj = date entered.
	return ((dateObj.getDate() == eval(dateTab[2])) && (dateObj.getMonth() == eval(dateTab[1])-1) && (theyear == eval(dateTab[0])));
}

/**
*/
function checkemail(str){
	var filter=/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i
	
	if ( filter.test(str) )
		testresults = true;
	else{
		testresults = false;
	}

	return (testresults);
	
}

/**
* retourne si un groupe de radio est coché ou pas
* @param name: le name du groupe de bouton (attention NE PAS UTILISER l'ID)
* @return true si aucun des radios n'est coché
*/
function radioIsNotChecked(name) {
	return  $("[@name="+name+"]:checked").length == 0;
}

/**
* retourne si une valeur est un chiffre ou pas
* @return true if argvalue contains only numeric characters
*/
function isNum(argvalue) {
	argvalue = argvalue.toString();
	
	if (argvalue.length == 0)
	return false;
	
	for (var n = 0; n < argvalue.length; n++)
	if (argvalue.substring(n, n+1) < "0" || argvalue.substring(n, n+1) > "9")
	return false;
	
	return true;
}

function isEmpty(id){
	var o = document.getElementById(id);
	
	if (o==null) return;
	
	var type = o.nodeName;
	
	if (type=="INPUT" || type=="TEXTAREA"){
		return fieldIsEmpty(id);
	}
	
	if (type=="SELECT"){

		return selectIsEmpty(id);		
	}
	
	alert("type inconnu: "+type);
}



/**
* retourne TRUE si le champ est vide (text, textarea)
* @param idField	ID du champs  a vérifier
* @return bool
*/
function fieldIsEmpty(idField){
	return $.trim(document.getElementById(idField).value)=="";
}

/**
* retourne TRUE si la case est cochée
* @param idCheckbox	ID de la case a cocher a vérifier
* @return bool
*/
function checkboxIsChecked(idCheckbox){
	 var c = document.getElementById(idCheckbox);
	 var ret = false;
	 
	 if (c!=null) ret = c.checked;
	 
	 return ret;
}

/**
* retourne TRUE si le select renvoi une valeur non null
* @param idSelect	ID du select a vérifier
* @param value		(option) valeur a checker pour considerer que le select est null
* @return bool
*/
function selectIsEmpty(idSelect, value){
	var sel = document.getElementById(idSelect);	
	//if (sel == null) DEBUG_output(idselect+" n'existe pas");
	if (sel.options[sel.selectedIndex].value=="" || sel.options[sel.selectedIndex].value==value){
		return true;
	}
	
	return false;
}

