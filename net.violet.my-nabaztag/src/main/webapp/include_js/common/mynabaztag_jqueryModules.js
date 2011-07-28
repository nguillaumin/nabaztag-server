/* ********************************************************************************************** */
/* MAINTAB
/* ********************************************************************************************** */

var gDisabledTab = "";
if (!nabaztag.constantes.ISLOG) {
	gDisabledTab = "|Recu|Archives|Envoyes|Profil|MaMusique|MesAmis|MonAbonnement|MesPreferences|Publier|MesAPI|";
}

/**
* Transforme les UL avec la class "tabnav" en navigation principal (onglet)
* ajoute en automatique la class "last" au dernier onglet du UL
* si un href != # present, l'url sera chargï¿½ dans le div a montrer
* ajoute sur le onclick une action pour changer d'onglet.
*/
function mainTab_Init(){
	// dernier onglet
	var l = $("ul.tabNav li").length - 1;

	// on rajoute une ombre au dernier onglet
	$("ul.tabNav li").eq( l ).addClass("last");

	// et a l'avant dernier, si le dernier etait un onglet de droite
	if ( strpos( $("ul.tabNav li").eq( l ).attr('class'), "right")>=0 ) {
		$("ul.tabNav li").eq( l-1 ).addClass("last");
	}
		
	$("ul.tabNav li").each(function(){
		// choppe l'id et le href		
		var id = $(this).attr("id");
		var url = $("a", this).attr("href");
		
		// patch anti connerie IE6 (mais ptet aussi le 7...)
		// ie se croyant malin, quand un href="#" me renvoi urlcourante + #....
		if (url.substr( url.length-1, 1  ) == "#") {url="#"};
		
		// si pas disabled
		if ( strpos(gDisabledTab, "|"+id+"|") < 0 ){
			$("a", this).click(function(){
				var cb = null;
				
				if (id == "AllServices") {
					cb = nablife.init;
				}

				mainTab_GoTab(id, url, null, cb);
				blocMyNabcast_unSelectAll();
				blocMyServices_unSelectAll();
				if (ie6) window.event.returnValue=false;	// ie patch a la con.....
				return false;
			});
		} else {
			$("a", this).attr("href", "");
			$("a", this).css("cursor", "default");
			
			if($(this).is(".last")) $(this).addClass("disabled_last");
			else $(this).addClass("disabled");
		}
	});
	
}

/**
* change d'onglet principal (cache le div en cours, pour afficher le div voulu)
* sans aucun parametre, va charger le premier onglet
* @param tabId: ID_DU_DIV de l'onglet -> affiche le div "contentID_DU_DIV"
* @param url: si une url est prï¿½sente, on charge dans le div
* @param blockRefresh: refresh (true) ou pas (false) les blocks sur les cotï¿½s. (true par defaut)
* @param afterCallback: action a executer apr�s si besoin
*************************************************************************/
function mainTab_GoTab(tabId, url, blockRefresh, afterCallback){
	
	// si on a rien sp�cifi�, on va prendre le premier onglet de la page
	if (tabId==null) {
		tabId = $("ul.tabNav li:first").attr("id");
		url = $("ul.tabNav li:first a").attr("href");
	}

	if (tabId == "MonAbonnement" && window.location.hostname!="localhost" && window.location.hostname.substr(0,7)!="192.168") {
		if (window.location.protocol != "https:") {
			var newUrl = gHttpsUrl+"myTerrier.do?onglet=MonAbonnement";
		    window.location.replace( newUrl );		
			return;
		}
	}
	
	if (gTitle[tabId]!=null) {
		$("div.tabNavContener h1").eq(0).html(gTitle[tabId]);
	} else {
		$("div.tabNavContener h1").eq(0).html("["+tabId+"] pas de titre");
	}
	
	if (blockRefresh==null) blockRefresh = true;
	
	var obj = $("#"+tabId);

	var oldBlocksList = returnBlocksList(currentTab);
	var blocksList = returnBlocksList(tabId);
	
	// on note dans quelle tab nous sommes
	currentTab = tabId;

	// on deselect les onglets
	$("ul.tabNav li").removeClass("active");
	$("ul.tabNav li").removeClass("active_last");	

	// si l'onglet existe (pas un onglet virtuel)
	if ( obj.length>0 ){

		if ( strpos( $("#"+tabId).attr('class'), "last") >= 0) {
			// on highlite le tab
			$("#"+tabId).addClass("active_last");
		} else {
			$("#"+tabId).addClass("active");
		}
	
	}

	// on cache tout les contents
	$("div.contentMainTab").hide();

	if (url!=null && url!="#"){
		var divId = "#content"+tabId;
		
		if (!$.browser.msie) $(divId).height(300);
		
		$(divId)
			.html("")
			.addClass("loadingContent")
			.load(antiCacheUrl(url),
				function(){
					$(divId)
						.removeClass("loadingContent");
						
					if (!$.browser.msie) $(divId).height("auto");
						
					if (afterCallback!=null) afterCallback();
				}
			);

		
	}


	if (blocksList!=null && blockRefresh && (oldBlocksList != blocksList) ) updateBlocs();
	
	
	// on affiche le bon
	$("#content"+tabId).show();

}

/* ********************************************************************************************** */
/* COLORPICKER
/* ********************************************************************************************** */
/**
* @name rvb2hex
* @desc prend un rvb(r, v ,b) en entrï¿½ et ressort un #RRVVBB
* @param val: couleur rvb(r,v,b)
* @return la couleur sous la form #RRVVBB
*/
function rvb2hex(val){
  if (val.indexOf("rgb") >= 0)  {
	  var rgb_string = val.slice(val.indexOf('(') + 1,val.indexOf(')'));
	  var rgb_val = rgb_string.split(",");
	  val = "#";
	  var hexChars = "0123456789ABCDEF";
	  for (var i = 0; i < 3; i++)  {
		  var v = rgb_val[i].valueOf();
	  	val += hexChars.charAt(v/16) + hexChars.charAt(v%16);
	  }
  }
	
	return val;
}

/**
* @name colorPicker_GetColorFromLi
* @desc prend un objet jquery (un LI) et retour son background CSS au format #RRVVBB
* @param liObj: li (objet query) qui a le background
* @return col: la couleur sous la form RRVVBB
*/
function colorPicker_GetColorFromLi(liObj){
	var col = liObj.css("backgroundColor");
	// si on a pas un # au dï¿½but de la couleur, elle est au format RVB (firefox powah)
	if ( col.charAt(0) != "#" ) { col = rvb2hex(col); }
	col = col.substr(1, col.length);
	
	return col;
}

/**
* @name colorPicker_Select
* @desc selectionne la couleur, et met un champ cachï¿½ a jour
* @param obj: ID du A sur lequel l'user a cliquï¿½
*/
function colorPicker_Select(obj){
	var li = $(obj).parent();
	var ul = $(li).parents('ul');
	
	// savoir si la case est décochée ou pas.
	sel = $(obj).is(".color_selected");
	
	$(ul).find("a").removeClass("color_selected").addClass("color_unselected");


	if (!sel) {
		$(obj).addClass("color_selected");
		document.getElementById(ul.attr("id")+"_value").value = colorPicker_GetColorFromLi(li);
	} else {
		document.getElementById(ul.attr("id")+"_value").value = "";		
	}
	
}

function colorPicker_Init(idColorPicker, color_defaut){
	$("#"+idColorPicker+" li").each(function(){
		var col = colorPicker_GetColorFromLi($(this));
		var ul = $(this).parents("ul");
		
		
		// c'est la couleur choisite :)
		if (col == color_defaut) {
			$("a", this).removeClass();
			$("a", this).addClass("color_selected");
			document.getElementById(ul.attr("id")+"_value").value = col;
		}

		$("a", this).attr("href", "javascript:;");
		$("a", this).click(function(){
			colorPicker_Select(this);
		});											
											   
	});
	
}

$.chooseSong = function() {
	var _checkMouse = function(e) {
		var target = $.browser.msie ? window.event.srcElement : e.target;
		var cp = $(target).findClosestParent('div.popup-song');
		if (cp.get(0).className != 'song-picker-holder') {
			_closeAll();
		}
		
	};
		
	var _closeAll = function() {
		$("div.popup-song").hide();
		$(document).unbind('click', _checkMouse);
	}

	return {
		 
		reset: function(idDiv) {
			var obj = $(idDiv).parents("div.song-picker-holder");			
			$(obj).find(".songPicker").get(0).value = "";
			$(obj).find("span.text").html("-");
		},
		
		getId: function(idDiv) {
			return $("#"+idDiv).val();
		},
		
		open: function() {
			var myDiv =  $(this).siblings(".popup-song");
			_closeAll();
			$(myDiv).show();
			$(document).bind('click', _checkMouse);
		},
		close: function() {
			var myDiv =  $(this).siblings(".popup-song");
			$(myDiv).hide();
			$(document).unbind('click', _checkMouse);
		},		
		toggle: function() {
			var myDiv =  $(this).siblings(".popup-song");
			if ( $(myDiv).css("display")=="none" ) {
				$.chooseSong.open();
			} else {
				$.chooseSong.close();
			}
		},
		update: function() {
			var t = this.options[this.selectedIndex].text;
			var v = this.options[this.selectedIndex].value;
			var obj = $(this).parents("div.song-picker-holder");			
			$(obj).find(".songPicker").get(0).value = v;
			$(obj).find("span.text").html(t);
			
			$(obj).find("span.text").Highlight(1000, gHColor);
			
			_closeAll();
		}				
	};
	
}();


$.fn.chooseSong = function() {

	this.each(function() {
		if(this.nodeName.toLowerCase() != 'input') return;
		
		
			var calBut = $.A({href:'javascript:;', className:'song-picker'}, "");
			$(calBut).click($.chooseSong.open);
			
			$(this)
				.wrap('<div class="song-picker-holder" style="display:none;"></div>')
				.before(d = $.DIV({className:'popup-song', id:'loader-'+$(this).attr("id")}))
				.after(calBut);
			
			var songId 		= this.value;
			var songName 	= this.title;
			if (songName=="") songName="-";

			$(this).parents("div.song-picker-holder").append("<span class='text'>"+songName+"</span>");

			$(d).hide();
			

			$(d).load("srvSound.do", function(a){
				$(".myMp3Picker", this).change($.chooseSong.update);

			});

			
			$(this).parents("div.popup-song").mouseout($.chooseSong.close);
			$(this).parents("div.song-picker-holder").show();
			
	});
	
};

/* ********************************************************************************************** */
/* UploadForm
/* ********************************************************************************************** */

/**
* crï¿½e un form d'upload prï¿½vu pour l'ajax (avec un iframe pour)
* @param url l'url pour l'upload
* @param forName id a donner au form
* @param champs normalement une string de la form 'songpath|songtitle' les id a donnï¿½s au deux champs file et input
* @param hidden de la form champ1:value1|champ2:value2 pour rajouter des hiddens dans la form
*/
jQuery.fn.uploadMp3Form_Init = function(url, formName, champs, hidden) {

	var tmp;
	tmp = hidden.split("|");
	var champs = champs.split("|");

	var html="";
	
	html += "<iframe src ='javascript:void(0);' id='myUploadIframe' name='myUploadIframe' style='border: none; width:0; height:0'></iframe>";
	html += "<form target='myUploadIframe' id='"+formName+"' name='"+formName+"' enctype='multipart/form-data' action='"+url+"' method='post' >";	//
	
	
	for (a=0; a<tmp.length; a++){
		var t = tmp[a].split(":");
		html += "<input type='hidden' value='"+t[1]+"' name='"+t[0]+"' id='"+t[0]+"' />";
	}
	

	html += "<ul>";
	html += "<li>";
	html += "<label>"+msg_txt['chemin']+"</label>";
	html += "<input class='myFile normal' name='"+champs[0]+"' value='' id='"+champs[0]+"' type='file'>";
		
	html += "</li>";
	html += "<li>";
	html += "<label>"+msg_txt['titre']+"</label>";
	html += "	<input class='myName' name='"+champs[1]+"' value='' id='"+champs[1]+"' type='text'>";
	html += "</li>";
	html += "<li>";
	html += "<label>"+msg_txt['begin_mp3']+"</label>";
	html += "	<input style='width:50px;' id='song_start' value='0' name='song_start' class='normal'> "+msg_txt['from_beginning'];
	html += "</li>";
	html += "<li>";						
	html += "<label>"+msg_txt['length_mp3']+"</label>";
	html += "	<input style='width:50px;' id='song_end' value='45' name='song_end' class='normal'> "+msg_txt['seconds'];
	html += "</li>";
	html += "<li>";						
	html += "<label>&nbsp;</label>";
	html += "	<input value='"+msg_txt['ajouter']+"' class='normal genericBt' type='submit' onclick='$(\"#"+$(this).attr("id")+"\").uploadMp3Form_Validate();'>";
	html += "</li>"				
	html += "</ul>";
	html += "</form>";
	
	$(this).html(html);	
}


jQuery.fn.uploadMp3Form_Validate = function(){
	var file = $("input.myFile", this).val();
	var name = $("input.myName", this).val();	

	if (file==""){
		customAlertN(msg_txt['mp3_invalidFile']	);
		return false;		
	}
	
	if (name==""){
		customAlertN( msg_txt['mp3_invalidName'] );
		return false;		
	}
	
	// on met la valeur a 45 max
	if ( $("#song_end").val()>45 | $("#song_end").val()=="" ) {
		$("#song_end").val("45");
	}
			
	showFormWaitForResponse($(this).attr("id"), true);
	
	this.submit();
}


jQuery.fn.uploadMp3Form_End = function(id, listToRefresh) {
	var name = $("input.myName", this).val();
	// on vide les champs
	$("input.myFile, input.myName", this).val("");
	showFormWaitForResponse($(this).attr("id"), false);
	
	$("#"+listToRefresh).addOption(id, name);

}

jQuery.fn.dateSelect_Reset = function(){
	var myId = $(this).attr("id");	
	var c = $(this).get(0);
	c.value="";
	
	$("span.v","#"+myId).html( msg_txt['aucune'] );
}

jQuery.fn.dateSelect_SetToCurrent = function(){
	var myId = $(this).attr("id");

	/* Par defaut on affiche la date actuelle */
	var Objet_date = new Date();
	var d = ""+Objet_date.getDate();
	var m = ""+(Objet_date.getMonth()+1);	// le mois est retournï¿½ de 0 a 11 je trouve ï¿½a nul...

	if (d.length<2) d = "0" + d;
	if (m.length<2) m = "0" + m;

	curDate = d+"/"+m+"/"+Objet_date.getFullYear();
	
	$("#"+myId).val(curDate);

	$("span.v","#"+myId).html( "&nbsp; ("+curDate+")" );

	//$(this).parents("div.date-picker-holder").after("<span class='v'>&nbsp; ("+curDate+")</span>");	
}

jQuery.fn.dateSelect_Init = function(lang){
	
	
	$.datePicker.setDateFormat("dmy", '/');
	
	if (lang==1){

			$.datePicker.setLanguageStrings(
				['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],
				['Janvier', 'F&eacute;vrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Ao&ucirc;t', 'Septembre', 'Octobre', 'Novembre', 'D&eacute;cembre'],
				{p:'Pr&eacute;c&eacute;dent', n:'Suivant', c:'Fermer', b:'Choisissez une date'}
			);		

	}
	var today = new Date();
	var todayStr = paddMe(today.getDate()) + "/" + paddMe(today.getMonth()+1) + "/" + today.getFullYear();
	
	$(this).datePicker();
	var myId = $(this).attr("id");
	$(this).parents("div.date-picker-holder").append("<span class='v'>&nbsp; ("+todayStr+")</span>");	
	
	$(this).dateSelect_SetToCurrent();
	
	
	$.datePicker.setCallBackFunction( function() {
											   	var val = document.getElementById(myId).value;
												$("#"+myId).siblings("span.v").show();
												$("#"+myId).siblings("span.v").html("&nbsp; ("+val+")");
											   });
	
}
	
jQuery.fn.dateSelect_Validate = function(){
	if ( $(this).val()=="" ) return msg_txt['bad_date_selected']; 
	return true;
}


jQuery.fn.hourSelect_Reset = function(){
	var wrapper = $(this).parents("div").eq(0);
	$("input.h", wrapper).get(0).value="";
	$("input.m", wrapper).get(0).value="";
	$(this).get(0).value="";
}

jQuery.fn.hourSelect_SetToCurrent = function(){
	
	var meId = $(this).attr("id");
	var wrapper = $(this).parents("div").eq(0);	
	
	var Objet_date = new Date();	
	var ho = ""+Objet_date.getHours();
	var mi = ""+Objet_date.getMinutes();
	if (ho.length<2) ho = "0" + ho;
	if (mi.length<2) mi = "0" + mi;	
	

	$("input.h", wrapper).get(0).value = ho;
	$("input.m", wrapper).get(0).value = mi;
	

}


/*HELPER HOUR */
/* ***************************************************************** */
function convert24to12(heure, minute){
	heure = parseInt(heure, 10);
	minute = parseInt(minute, 10);	

	var h = new Array();
	
	h['period'] = "am";
	
	if (heure==0) heure = 12;
	if (heure>11) h['period'] = "pm";
	if (heure>12) heure -= 12;
	
	h['hours'] 		= (heure);
	h['minutes'] 	= (minute);

	return h;
}

function convert12to24(heure, minute, period){
	heure = parseInt(heure, 10);
	minute = parseInt(minute, 10);	

	if (heure == 0) heure ="x";
		
	var h = new Array();
	if ( heure==12 && period=="am" ) heure = 0;

	if (period=="pm" && heure<12) {
		heure += 12;
	}
	
	
	h['hours'] 		= (heure);
	h['minutes'] 	= (minute);
	
	return h;
}

function paddMe(value){
	
	value = ""+value;
	if (value.length<2 && value.length>0) value = "0" + value;
	
	return value;
}
		
// Nouvelle version du widget heure, qui ne translate pas les heures en 12/24, tout est g�r� cot� java
jQuery.fn.hourSelect_InitN = function(autoFill){
	var _maxHour = 23;
	var _minHour = 0
	var _maxMinute = 59;
	var _minMinute = 0

	// je m'appuie maintenant sur une variable global "constante" qui contient l'info
	var twentyFour = (nabaztag.constantes.H24==1) ? true : false;

	if (!twentyFour) {
		_maxHour = 12;
		_minHour = 1;
	}
		
	$(this).each(function(){
		// si on a d�ja cr�er le widget heure on se barre
		if ( $(this).parent("div.heureSelect-hold").length>0 ) return;
		if (autoFill == null) autoFill = true; // par defaut
		
		var me 			= $(this);
		var value		= $(me).val();
		var is24		= (value.length > 5) ? false:true;
		var Objet_date 	= new Date();
		var currHours	= ""+Objet_date.getHours();
		var currMinutes	= ""+Objet_date.getMinutes();
		var am = "selected='selected'";
		var pm = "";


		// quand on change une case, on padd avec un 0 devant si besoin
		var onchange = function(){
			var val = $(this).val();
			var wrapper = $(this).parents("div").eq(0);
			
			if (isNum(val) && val!=""){	
				$(this).val( paddMe(val) );
				//alert("ok");	
			} else {
				$(this).val("");
				$("input.h", wrapper).val("");
				$("input.m", wrapper).val("");
			}

		}
		
		var onkeyup = function(){
			
			// le div qui englobe
			var wrapper = $(this).parents("div").eq(0);
			
			// entrées du user
			var hh = $("input.h", wrapper).val();
			var mm = $("input.m", wrapper).val();	
			
			
			if ( $("input.h", wrapper).val().length == 2 && $(this).is("input:text") && !$(this).is(".done")) {
				// on rajoute un "done" dans la classe, pour que le focus passe sur les minutes automatiquement, mais juste la premiere fois
				$(this).addClass("done");		
				$("input.m", wrapper).focus();
			}
			
			updateHidden(hh, mm, wrapper);
		}


		function updateHidden(heure, minute, wrapper, is24){

			if (heure=="" || minute =="") {
				$("input", wrapper).not(".h").not(".m").val(""); 
				return;
			}

			var error = false;
		
			// tout est ok, l'heure est correcte
			if (heure>=_minHour && heure <=_maxHour && minute>=_minMinute && minute<=_maxMinute && isNum(heure) && isNum(minute) ) {
				
			} else {
				
				if (heure!="" && minute!="") {
					error = true;
				}				
				if (heure > _maxHour) {
					$( "input.h", wrapper).val(_maxHour);
					error = true;
				}
				
				if (minute > _maxMinute) {
					$( "input.m", wrapper).val(_maxMinute);
					error = true;	
				}

			}
			
			var period = ""; 
			if (!twentyFour) {
				period = " " + $("select", wrapper.eq(0) ).val().toUpperCase();
			}
			
			if (!error) {
				$(me).val(paddMe(heure)+":"+paddMe(minute) + period);
			} else {
				$(me).val("");
				
			}
			
			
		}


		// si on ne veut pas remplir les champs par d�faut, on met l'heure a vide
		if (!autoFill) {
			currHours="";
			currMinutes="";
		}


		var safariHack = $.browser.safari ? " style='padding-left:20px;' " : "";
		// on entoure le champs avec un div
		var w = me.wrap("<div class='heureSelect-hold' "+safariHack+"></div>");

		// si le champs est rempli, on choppe les heures
		if ( value!=null && value.length > 1){
			currHours 	= value.substr(0,2);
			currMinutes = value.substr(3,2);
			period		= value.split(" ")[1];
			
			if (period=="PM") {
				am = "";
				pm = "selected='selected'";
			}
	
		}
		
		var h="";
		h += "<span><input type='text' autocomplete='OFF' value='"+currHours+"'  maxlength='2' class='h' name='heure_delay' />&nbsp;:&nbsp;";
		h += "<input type='text' autocomplete='OFF' value='"+currMinutes+"' maxlength='2' class='m' name='minute_delay' />";

		if (!twentyFour) {
			h += "<select name='h12" + $(me).attr("id") + "' style='font-size:9px; margin:0 0 0 3px; padding:0; width:40px;'>";
			h += "<option value='am' "+am+">am</option>";			
			h += "<option value='pm' "+pm+">pm</option>";			
			h += "</select>";			
		}
		h += "</span>";

		
		// on met notre html apr�s le div
		$(me).after(h);	
	
		// parent du input hidden
		var par = $(w).parent();
		
		// sur un click, on selection tout
		$(par).find("input.h, input.m").click(function(){
			$(this).removeClass("done");
			this.select();
		});
		
				
		$("input.h, input.m", par).keyup(onkeyup);
		$("input.h, input.m", par).change( onchange);
		
		$("select", par).change( onkeyup );		
		
		updateHidden(currHours, currMinutes, $(w).parents('div.heureSelect-hold'), is24);	
				
		
	});

}

// TODO : twentyFour n'est plus utilis�....
jQuery.fn.hourSelect_Init = function(twentyFour, autoFill){
	var _maxHour = 23;
	var _minHour = 0
	var _maxMinute = 59;
	var _minMinute = 0

	// je m'appuie maintenant sur une variable global qui contient l'info
	twentyFour = (nabaztag.constantes.H24==1) ? true : false;

	if (!twentyFour) {
		_maxHour = 12;
		_minHour = 1;
	}
	
	if ( $(this).parents("form").attr("autocomplete") != "off" ) $(this).parents("form").attr("autocomplete", "off");

	$(this).each(function(){

		if ( $(this).parent("div.heureSelect-hold").length>0 ) return;
	
		if (autoFill == null) autoFill = true; // par defaut
		

		
		var me 			= $(this);
		var value		= $(me).val();
		var Objet_date 	= new Date();
		var currHours	= ""+Objet_date.getHours();
		var currMinutes	= ""+Objet_date.getMinutes();	
	
		function updateHidden(heure, minute, wrapper){

			if (heure=="" || minute =="") {
				$("input", wrapper).not(".h").not(".m").val(""); 
				return;
			}
			
			var a = $("select", wrapper.eq(0) ).val()=="pm";
			var error = false;
		
			if (heure>=_minHour && heure <=_maxHour && minute>=_minMinute && minute<=_maxMinute && isNum(heure) && isNum(minute) ) {

				if ( $("input[@type=radio][@checked]", wrapper.eq(0) ).length ==0 ){
					// par defaut on set AM
					$("input.am", wrapper.eq(0) ).attr("checked", true);
				}
			} else {
				if (heure!="" && minute!="") {
					error = true;
				}				
				if (heure > _maxHour) {
					$( "input.h", wrapper).val(_maxHour);
					error = true;
				}
				
				if (minute > _maxMinute) {
					$( "input.m", wrapper).val(_maxMinute);
					error = true;	
				}

			}
			

			if (!twentyFour) {
				
					period = "am";
					
					// si pm checked
					if ( a ) period = "pm";
					
					var newH = convert12to24(heure, minute, period);
					
					heure = newH['hours'];
					minute = newH['minutes'];	
				
			}
			
			if (!error) {
				$(me).val(paddMe(heure)+":"+paddMe(minute));
			} else {
				$(me).val("");
				
			}
			
			
		}
		
		// quand on change une case, on padd avec un 0 devant si besoin
		var onchange = function(){
			var val = $(this).val();
			var wrapper = $(this).parents("div").eq(0);
			
			if (isNum(val) && val!=""){	
				$(this).val( paddMe(val) );
			} else {
				$(this).val("");
				$("input.h", wrapper).val("");
				$("input.m", wrapper).val("");
			}

		}
		
		var onkeyup = function(){
			
			// le div qui englobe
			var wrapper = $(this).parents("div").eq(0);
			
			// entrées du user
			var hh = $("input.h", wrapper).val();
			var mm = $("input.m", wrapper).val();	
			
			// le mode, soit minute_delay, soit heure_delay
			var mode = $(this).attr("name");
			//var period;
			
			if ( $("input.h", wrapper).val().length == 2 && $(this).is("input:text") && !$(this).is(".done")) {
				// on rajoute un "done" dans la classe, pour que le focus passe sur les minutes automatiquement, mais juste la premiere fois
				$(this).addClass("done");		
				$("input.m", wrapper).focus();
			}
			
			updateHidden(hh, mm, wrapper);
		}
		
		// on entoure le champs avec un div
		var safariHack = $.browser.safari ? " style='padding-left:20px;' " : "";
		var w = me.wrap("<div class='heureSelect-hold' "+safariHack+"></div>");


		
		// si on ne veut pas remplir les champs par d�faut, on met l'heure a vide
		if (!autoFill) {
			currHours="";
			currMinutes="";
		}
		
		// si le champs est rempli, on choppe les heures
		if ( value!=null && value.length > 1){
			t = value.split(":");
			if (t[0]>=0) currHours = t[0];
			if (t[1]>=0) currMinutes = t[1];
		
			if (t[0][0]=="-") $(me).val(""); // on efface QUE si on a des -1
		}
		
		var am = "";
		var pm = "";
				
		
		// conversion 12/24 heures
		if (!twentyFour) {
			if (currHours!="" && currMinutes!="") {
				am = "selected='selected'";
				pm = "";
				
				var hh = convert24to12(currHours, currMinutes);
			
			
				currHours 	= hh['hours'];
				currMinutes = hh['minutes'];			
			
				if (  hh['period']=="pm" ) {
					am = "";
					pm = "selected='selected'";
				}
			}
		}
		
		// on padd avec un 0 s'il faut
		currHours = paddMe(currHours);
		currMinutes = paddMe(currMinutes);		
		
		var h="";
		h += "<span><input type='text' value='"+currHours+"'  maxlength='2' class='h' name='heure_delay' />&nbsp;:&nbsp;";
		h += "<input type='text' value='"+currMinutes+"' maxlength='2' class='m' name='minute_delay' />";

		if (!twentyFour) {
			h += "<select name='h12" + $(me).attr("id") + "' style='font-size:9px; margin:0 0 0 3px; padding:0; width:40px;'>";
			h += "<option value='am' "+am+">am</option>";			
			h += "<option value='pm' "+pm+">pm</option>";			
			h += "</select>";			
		}
		h += "</span>";

		
		// on met notre html apr�s le div
		$(me).after(h);	
	
		// parent du input hidden
		var par = $(w).parent();

		
		// sur un click, on selection tout
		$(par).find("input.h, input.m").click(function(){
			//alert("ok");
			$(this).removeClass("done");
			this.select();
		});
		
				
		$("input.h, input.m", par).keyup(onkeyup);
		$("input.h, input.m", par).change( onchange);
		//$("input.h, input.m", par).blur( onkeyup);
 
		
		
		$("select", par).click(onkeyup);		
		
		updateHidden(currHours, currMinutes, $(w).parents('div.heureSelect-hold'));	
	});
}

jQuery.fn.userTimeConvert = function(twentyfour){
	// on est en 24 heure, l'heure est bien formattée de base
	if (twentyfour==1) return;
	
	$(this).each(function(){
		var t = $(this).html();
		// ptit trick vite fait. si l'heure a déja un pm ou un am dedans on s'en va....
		if (t.charAt( (t.length)-1 )=="m") return;
	
		t = t.split(":");
		var h = t[0];
		var m = t[1];

		
		var newTime = convert24to12(h, m);
		
		$(this).html(paddMe(newTime['hours'])+":"+paddMe(newTime['minutes'])+"&nbsp;"+newTime['period']);
		
	});
	
}



jQuery.fn.hourSelect_GetTime = function(paddIt){
	if (paddIt==null) paddIt=true;
	
	var wrapper = $(this).parents("div").eq(0);
	var h = $("input.h", wrapper).val();
	var m = $("input.m", wrapper).val();
	
	if (paddIt) {
		if (h.length<2) h = "0" +h;
		if (m.length<2) m = "0" + m;	
	}
	
	return {heures:h, minutes:m};
}

jQuery.fn.hourSelect_Validate = function(timeMandatory){
	if (timeMandatory==null) timeMandatory=true;
	
	if (timeMandatory) {
		if ( $(this).val()=="") {
			return msg_txt['bad_hour_selected'];
		} else {
			return true;
		}
	} else {
		return true;
	}

}


jQuery.fn.select_ListeVilleFormat = function(){
	if (ie6) {
		$("option", this).eq(0).remove(); // on supprime la premiere ligne qui ne veut rien dire pour IE qui n'a pas le selecteur de pays
		return; // sorry.... you should have a REAL browser
	}
	
	
	var allTowns = new Object;
	var selected_pays="";
	var selected_ville="";	
	
	$(this).each(function(){	
		$("option", this).each(function(){
			
			var str = $(this).attr("value");//html();
			
			if (str==null) {
				str="";
			}
			
			tmp = str.split(".");
			
				
			var pays = tmp[1];
			var ville = tmp[2];


			var str = $(this).html();
			tmp = str.split(".");	
			var paysTxt = tmp[0];
			var villeTxt = tmp[1];			


			// ville/pays choisie
			if( $(this).attr("selected") == true || $(this).attr("selected") == "selected") { // suivant si on utilise jquery <1.1 ou pas....
				selected_pays  = pays; 
				selected_ville = ville;
			}
			
			pays = pays+"|"+paysTxt;
			ville = ville+"|"+villeTxt;		
			
			
			if (pays!="" && pays!=null) pays = $.trim(pays);
			if (ville!="" && ville!=null) ville = $.trim(ville);
			
			if (pays!="") {
				if (allTowns[pays]==null) {
					//console.debug(pays);
					allTowns[pays] = new Array;
				}
				if (ville!="") allTowns[pays].push(ville);
			}
			
		});
	});
	
	$("option", this).remove();
	
	$(this).attr("name","customCountries");
	$(this).attr("id","customCountries");
		
	$(this).after("<select style='margin-left:5px' id='customTowns' ></select>");

	$("#customTowns").after("<input  type='hidden' value='' id='idVille' name='idVille'>");
	
	for (var i in allTowns) {
		var t = i.split("|");
		
		p = allTowns[i];
		$(this).append("<option value='"+t[0]+"'>"+t[1]+"</option>");
		
		$("#customTowns").append("<option value='---' >---</option>");

		for (o = 0; o < p.length; o++) {
			var v = p[o].split("|");
			$("#customTowns").append("<option value='"+v[0]+"' class='town_"+t[0]+"'>"+v[1]+"</option>");
		}
	}
	
	var saved = $("#customTowns option");
	
	$("#customCountries").select_SelectFromValue(selected_pays);
	
	if (selected_pays==null) {
		$("#customCountries option").eq(0).attr("selected", "true");
		$("#customTowns").hide();
	}
	
	$("#customCountries").change(function(){
		
		p = $(this).val();
		$("#customTowns option").remove();
		$("#customTowns").append("<option value='---' >---</option>");		
		$(saved).each(function(){
			if ( ("town_"+p) == $(this).attr('class') )$("#customTowns").append("<option value='"+$(this).val()+"' class='"+$(this).attr('class')+"'>"+$(this).html()+"</option>");
		});
		
		
		$("#customTowns").select_SelectFromValue("---");
		$("#idVille").val("");
		$("#customTowns").show();			
	});
	
	$("#customTowns").change(function(){
		v = $(this).val();
		if (v!="") $("#idVille").val( "Nmeteo."+($("#customCountries").val())+"."+v+".weather" );
	});
	

	$("#customTowns").select_SelectFromValue(selected_ville);	
	
	$("#customTowns option").not(".town_"+selected_pays).remove();
	
	if ( $("#customTowns").val()!="" && $("#customCountries").val()!= null && $("#customCountries").val()!= "undefined")  {
		$("#idVille").val( "Nmeteo."+($("#customCountries").val()) + "." + ($("#customTowns").val())+".weather" )
	}

};

jQuery.fn.select_NotclickableValues = function(){

	var counter = 0;
	
	$("option", this).each(function(){
						  	var str = $(this).val();
							var h = "";
							
							if (str.substr(0, 4) == "----"){
								var s = $(this).html();
								if ( s.substr(0, 4) == "----") {
									s = s.substr(4, s.length - 8);
								}

								$(this).html(s);
								$(this).val("");
								$(this).css("font-weight", "bold");
								
								if ($.browser!="mozilla") $(this).css("background", "#ddd"); 	// bien le merci IE6....
								
								$(this).attr("disabled", "disabled");
							} else {
								$(this).html( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+$(this).html() );
							}
					});

}

// recodage rapide de Highlight du plugin Interface
jQuery.fn.Highlight = function(speed, color, callback, easing) {
	return this.each(function(){
		easing = typeof callback == 'string' ? callback : easing||null;
		callback = typeof callback == 'function' ? callback : null;
		
		var startColor = $(this).css("background-color");
		$(this).css("background-color", color);
		$(this).animate({backgroundColor:startColor}, {duration:speed, easing:easing, complete:callback});		
	});
}

jQuery.fn.extHighlight = function(delayBefore, delayAnim, color){
	

	if (delayBefore==null)	delayBefore = 3000;
	if (delayAnim==null)	delayAnim = 1000;
	if (color==null)		color = "rgb(222,235,103)";

	
	this.each(function() {
		$(this).css("background-color", color);
		$(this).animate({opacity:1.0}, delayBefore).animate({backgroundColor:"#FFFFFF"}, delayAnim,function(){$(this).css("background", "none");});
	});
}


$.paginate = function() {

	var nbTotal 	= null;
	var nbPerPage 	= 6;
	var nbPage 		= null;
	var dom			= null;
	var container	= null;
	var currentPage = null;



	return {
		 
		getDom: function(t) {
			dom = $("li.paginateIt-item", t);
			container = t;
			nbTotal = dom.length;

			nbPage = Math.ceil(nbTotal/nbPerPage);
			currentPage = 0;
			$(t).append("<ul class='paginate'></ul>");
			$(t).prepend("<ul class='paginate'></ul><hr class='clearer'>");
		},		
		showPage: function(page) {
			var startItem = (currentPage * nbPerPage);
			var endItem = (currentPage * nbPerPage ) + nbPerPage;
			
			for (i=startItem; i<endItem; i++){
				$(dom).eq(i).hide();
			}			
			
			startItem = (page * nbPerPage);
			endItem = (page * nbPerPage ) + nbPerPage;
			
			for (i=startItem; i<endItem; i++){
				$(dom).eq(i).show();
			}
			
			var nav = $(container).find("ul.paginate");
			$(nav).each(function(){
							$("li", this).eq(currentPage).removeClass("current");
							$("li", this).eq(page).addClass("current");
			});

			goToTheTopOfThePage();
			currentPage = page;
			
		},
		setNpp: function(t) {
			if (t!=null) nbPerPage = t;
		},
		showNav: function(t) {
			var navHtml = "";
			
			if (nbPage>1) {
				for (i=1; i<=nbPage; i++){
					navHtml+="<li><a href='javascript:;'>"+i+"</a></li>";
				}
			}

		
			var nav = $(container).find("ul.paginate");
			$(nav).append(navHtml);
			
			$("a", nav).click( function(){
				var p = $(this).html()  - 1 ; 
				$.paginate.showPage( p );
						
			} );
			
		}
					
	};
	
}();

$.fn.paginate = function(npp) {
	$(this).each(function(){
		if ( $(this).css("display")=="block" && ( $(this).parents(".contentMainTab" ).is(":visible") )){
			//$.paginate.setTotal( $("li.paginate-item", this).length );
			$.paginate.setNpp(npp);	
			$.paginate.getDom(this);
			$.paginate.showNav(this);
			$.paginate.showPage(0);
		}
		
	});
}


$.fn.radioChooser = function() {
	this.each(function(){
			$("input[@type='radio']").each(function(){
				if (this.checked) {
					$(this).parents("li").addClass("selected specialBlockSelected"); //			$(this).addClass("specialBlockSelected");
				}
				
				$(this).css("margin-right", "5px");
				$(this).css("margin-top", "2px");				
				$(this).click(function(){
					$(this).parents("ul").find("li").removeClass("selected");
					$(this).parents("ul").find("li").removeClass("specialBlockSelected");					
					$(this).parents("li").addClass("selected specialBlockSelected");
					
					$(this).attr("checked", "true");					
				});
				
			});
	});
}

$.fn.rabbitChooser = function() {

	
	
	var ch = function(){
			var id = $("input:checked[@type='radio']", this).attr("id");
			switch(id){
				case "i0":	// pas de lapin
					$("div.img").hide();
					$("div.form").hide();
					$("#numSerie").val("");				
				break;
				
				case "i1":	// un tag
					$("div.img").show();
					$("div.form").show();
					$("div.img img").attr("src", "../include_img/reg/serial_nab_vieux.gif");
				break;
				
				case "i2":	// un tag tag
					$("div.img").show();
					$("div.form").show();									
					$("div.img img").attr("src", "../include_img/reg/serial_nab.gif");				
				break;
			}	
	};
	
	ch();
	$(this).radioChooser();
	
	this.each(function(){
		$(this).click(function(){
			ch();
		});
	});	
}

$.fn.delayDisplay = function() {
	this.each(function(){
		var v = $(this).html();
		$(this).html( msg_txt["delay_"+v] );
	});
}

$.fn.select_SelectFromValue = function(valueToSelect){
	var counter = 0;
	
	$("option", this).each(function(){
		if ( $(this).val() == valueToSelect) {
			$(this).get(0).selected = true;
		}
	});
	
}


$.msgPopupOverlay = function(msg, delay, color) {
	
	if (color==null) color = "ffb";
	if (delay==null) delay = 4000;	//4s par defaut
	
	var html =	'<div id="floatingMessage" class="floatingMessage" style="display:none;" >';
	html	+=		'<div class="floatingMessage-inner" style="background-color:#'+color+'">';
	html	+=		'<div class="floatingMessage-close"><a href="javascript:;"><span>[x]</span></a></div>';	
	html	+=		msg;
	html	+=		'</div>';
	html	+=		'</div>';
	

		
	function showIt(delay){
		$("#container").prepend(html);
		
		if ($.browser.safari) $("#floatingMessage").show(); 
		else $("#floatingMessage").fadeIn();
		
		if (delay>0) {
			setTimeout("$('#floatingMessage').fadeOut(function(){$('#floatingMessage').remove();});", delay);
		}
		
		$("#floatingMessage .floatingMessage-close a").click(function(){
			$('#floatingMessage').fadeOut(function(){
				$("#floatingMessage").remove();
			});
		});
	}
	

	
	if ( $("#floatingMessage").length < 1 ) {
		showIt(delay);
	} else {
		$("#floatingMessage").fadeOut(function(){
			$("#floatingMessage").remove();
			showIt(delay);
		});	
	}	
};

// deprecated
$.fn.msgPopup = function (msg, color, delay, replace) {
	// deprecated, donc on utilise l'autre version de la fonction
	$.msgPopupOverlay(msg, delay);
	return;
}


$.fn.destroyMe = function (delay, backgroundChange) {
	if(backgroundChange==null) backgroundChange = true;
	if (delay==null) delay = 800;
	this.each(function(){
		if (backgroundChange) $(this).css("background-color", gHColor);
		
		$(this).Pulsate(200, 2, function(){$(this).remove(); });	
			
	});
	
}

// reprise de l'ancien Pulsate du plugin Interface
jQuery.fn.Pulsate = function(speed, times, callback) {
	
	return this.each(function(){
		var startColor = $(this).css("background-color");
				
		for (var i=0; i < times; i++) {	
			$(this).animate( { "backgroundColor": "#FFFFFF"}, speed )
			.animate( { "backgroundColor": startColor}, speed);
		}

		$(this).queue(callback);
	});
	
};

$.fn.formToolTip = function (){
	var myFocus = function(){
		var hasToolTip = $(this).siblings("span.tip").length>0;
		
		// on selectionne le champs et on montre la tooltip si besoin
		$(this).addClass("selected");
		$(this).siblings("span.tip").show();
		$(this).siblings("span.tip").css("position", "absolute");
		$(this).siblings("span.tip").css("bottom", "24px");
		$(this).siblings("span.tip").css("left", "40px");		
		$(this).siblings("span.tip").css("z-index", "900");
		
		if (ie6 && hasToolTip) { // merci IE6
			$(this).parents("div.main-cadre-content").find("select").hide();
			$(this).siblings("span.tip").css("left", "0px");	
		}
	}
	
	var myBlur = function(){
		$(this).removeClass("selected");
		$("span.tip").hide();
		
		if (ie6) { // merci IE6
			$(this).parents("div.main-cadre-content").find("select").show();	
		}
	}
	
	var oldS = 0;

	$(this).each(function(){
		var desc = $(this).attr('title');
		var ww = $(this).width();
		var hh = $(this).height();		
		
		
		// on vide la balise title		
		$(this).attr('title','');
		
	
		// on wrap dans un div
		$(this).wrap("<div class='formToolTip_contener' style='position:relative; float:left;'></div>");
		
		if ( desc != null && desc!="") {		
				
			$(this).parents("div.formToolTip_contener").prepend("<span class='tip' >"+desc+"</span>");			
				// centrage de la bulle en hauteur
				var tt = $(this).parents("div.formToolTip_contener").find("span.tip");
				
	
				if (!$.browser.safari) { //sur safari, ça a tendance a planter....
					var height = $(tt).height();
				}
			
		}
			
		$(this).focus( myFocus );
		$(this).blur( myBlur );		
	});
}

$.fn.selectSrvLum = function (){
	var counter=1;
	
	f = function(){
		var mid = $(this).attr("id");
		var id = mid.split("_");
		id = id [1];
		$("input.checker").not("#myCheck_"+id).attr("checked", "");
		
		$("input[@name=lumiereFilter]").val(id);
	};
	
	var selected = $("input[@name=lumiereFilter]").val();
	
	this.each(function(){
		//$(this).attr("checked", "checked");
		$(this).click(f);

		$(this).attr("id", "myCheck_"+counter);
		if (counter==selected) $(this).attr("checked", "checked");
		counter++;
	});

}

$.fn.openMsgCatChoice = function (){
	this.each(function(){
		var url = $(this).attr("href");
		var txt = $(this).html();
		
		var m = new RegExp("tag=(.*)").exec(url);
		
		// hack, pour le parametre tag, parfois compos� de plusieurs mots
		// s�par�s par un espace, on escape la string
		if (m != null ) {
			m[1] = escape(m[1]);
			url = url.split("?");
			url = url[0] + "?tag=" + m[1];	
		}
		
		$(this).attr("href", "javascript:;");
		$(this).click(function(){
			TB_show(txt, url+"&height=430&width=350");
		});
		
	});
}


$.srvToggleDesc = function() {
	return {
		 
		close: function(mode) {
			// due a un bug dans slideUp, je ne l'utilise pas (momentanément)
			/*if (mode=="quick")*/  $("#description-srv").hide();
			/*else  $("#description-srv").slideUp("fast");*/
			

			
			$("div.title-srv a").removeClass("srvToggleMode-close");
			$("div.title-srv a").addClass("srvToggleMode-open");

		},
		open: function() {
			$("#description-srv").slideDown("fast");
			$("div.title-srv a").removeClass("srvToggleMode-open");
			$("div.title-srv a").addClass("srvToggleMode-close");
	
		},
		toggle: function( what ) {
			if ($("#description-srv").css("display") == "block"){
				this.close();
				gBlockState[what]="closed";
				
			} else {
				this.open();			
				gBlockState[what]="opened";				

			}

			saveConfig();
			
		}		
					
	};
	
}();

$.fn.srvToggleDesc = function (){
	this.each(function(){
		var srv = $(this).attr("id");
		
		if ( gBlockState[ srv ]=="closed" ) {
			$.srvToggleDesc.close("quick");
		}

		$(this).attr("href", "javascript:;");
		$(this).click( function(){ $.srvToggleDesc.toggle( $(this).attr("id") );} );		

	});
	
}

$.fn.setOpacity = function (floatValue){
	var ieValue = floatValue * 100;
	
	$(this).each(function(){
		$(this).css("filter", "alpha(opacity="+ieValue+")");
		$(this).css("-moz-opacity", floatValue);
		$(this).css("opacity", floatValue);
	});
	
	return this;
 
}


$.fn.toggleOpenClose = function() {
	this.each(function(){
		if ( $(this).is(".closed-arrow") ) {
			$(this).removeClass("closed-arrow");
			$(this).addClass("opened-arrow");
		} else {
			$(this).removeClass("opened-arrow");
			$(this).addClass("closed-arrow");
		}
	});
}

$.fn.divChangeUrl = function(newUrl, functionAfter) {
	this.each(function(){
		var elem = $(this);
		elem.block();

		elem.css("min-height", "40px");
		
		// chargement de la page
		elem.load(newUrl, function(html, response){

			if (response == "error") {
				LoaderErrorShow( elem, newUrl);
				return;
			}
			
			if ( $.browser.msie && parseInt($.browser.version) < 7 ) elem.css("height", "1%");			
			elem.css("min-height", "1%");
	
			if (functionAfter!=null) functionAfter();						
							
		});

		
	});
	
	return this;
}

/*
 * checkactivate
 * permet d'avoir une checkbox qui active ou desactive un div
 */
$.fn.checkactivate = function() {
	this.each(function(){
		var checkbox = $("div.check input", this);
		var t = this;
		
		// on ferme le div avec le contenu si la case n'est pas coch�e
		if ( !$(checkbox).attr("checked") ) {
			$("div.content").hide();
		}
		
		// quand on coche la case
		$(checkbox).click(function(){

			$("div.content").toggle();

			// si c'est d�coch�
			//on "reset" �ventuellement le formulaire dans la zone masqu�e 
			if ( !$(checkbox).attr("checked") ) {
				
				// vide les champs text
				$("input", t).val("");
				
				// d�coche les cases
				$("input[@type=checkbox]", t).attr("checked", false);
			} 			
			
		});
		
	});
	
	return this;
}
	