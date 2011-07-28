// on rajoute dans la variable $.browser, certaine variables bien pratiques... 
$.browser.msie6 = ($.browser.msie && parseInt($.browser.version)<7);
$.browser.msie7 = ($.browser.msie && parseInt($.browser.version)==7);

var np=navigator.platform;
var platform = null;
if (/Win/.test(np)) gPlatform = "win";
if (/Mac/.test(np)) gPlatform = "mac";
if (/X11|Linux/.test(np)) gPlatform = "linux";
var Nom = navigator.appName;
var Version = parseFloat(navigator.appVersion);

var ieMac = (Nom == 'Microsoft Internet Explorer' && Plateforme() == "Macintosh" ) ? 1:0;
var ieVer = navigator.userAgent.match(/MSIE (\d+(?:\.\d+)+(?:b\d*)?)/) ;

if (ieVer!=null) {

	ieVer = (parseFloat(ieVer[1]));
	
	var ie6=false; // (Nom == 'Microsoft Internet Explorer' && Plateforme() == "Windows" ) ? 1:0;
	var ie7=true;
	
	if (ieVer==6) {
		ie6=true;
		ie7=false;	
	}
}

function Plateforme(){
  if (navigator.appVersion.indexOf("Win") > -1) {return "Windows";}
  if (navigator.appVersion.indexOf("Macintosh") > -1) {return "Macintosh";}
  if (navigator.appVersion.indexOf("X11") > -1) {return "Unix";}
  if (navigator.appVersion.indexOf("Unix") > -1) {return "Unix";}
  if (navigator.appVersion.indexOf("Linux") > -1) {return "Unix";}
  return "Unknown";
}