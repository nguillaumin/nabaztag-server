$.blockUI.defaults.elementMessageCSS.width = "50px";
$.blockUI.defaults.overlayCSS.opacity = 0.7;
$.blockUI.defaults.elementMessage = "<span class='LoadingBlock'><img align='absmiddle' style='border:none;' alt='' height='20' width='20' src='../include_img/loading.gif'></span>";

// chargement de la config
loadConfig();

$(document).ready(function() {
	
	// le petit loader en haut a droite de l'�cran pose
	// de gros soucis de perf sous IE6 et safari2....
	// il faudra faire un test safari2/safari3 car �a passe bien sur safari3
	// mais je n'arrive pas encore a detecter la version pr�cise de safari...
	if (!$.browser.msie6 && !$.browser.safari) {
		// creation du "loader"
		var loaderDiv = $("<div />")
						.attr("id", "ajaxLoader")
						.addClass("ajaxLoading")
						.css("opacity", 0.8)
						.text("loading");
	
		$(loaderDiv).prependTo("body #container");
						
		$(loaderDiv)
			.ajaxStart(function(){
				$(this).show();
				//if ( ie6 ) $(this).css( "top", ($(document).scrollTop() + 2) + "px"   );	// remplace un peu le manque de position:fixed de IE6
			})
			.ajaxStop(function(){
				$(this).hide();
			});	
	}
	
	
	
	// test du cookie de maintenance
	if ( getCookie("maintenanceWarning")=="hide" ) {
		$("div.top-warning-msg").animate({'opacity':'0.0'});
	}
	
	mainTab_Init();
});

function ProcessingShow(divId){
	var target = $("#"+divId);

	// un .hide nuisait gravement a SAFARI...., avec un coup de css bien moche, on cache le formulaire pendant l'upload
	$(target).css({position:"absolute", top:"-1000px", left:"-1000px" });
	
	$(target).after(processingContent);
}


function LoaderShow(divId){

	var t = document.getElementById(divId);

	// si on a donn?n ID de div et pas un obj jQuery
	if (t!==null) {
		divId = $(t);
	}

	var h = $(divId).height();

	$(divId).html(loadingContent);

	if (h>20) {
			if (ie6) $(divId).css("height", h+"px");			
			$(divId).css("min-height", h+"px");
			$("span.LoadingBlock", divId).css("margin-top", (h/2)-(20)+"px");
	}
	
}

function LoaderErrorShow(divId, urlerror){
	var t = document.getElementById(divId);
	if (t!=null) {
		$(t).html(loadingContentError); // si on donne l'id d'un div
		$("div", t).append("<a target='_blank' href='"+urlerror+"'>o&nbsp;</a>");		
	}
	else { // ou directement l'objet jquery
		divId.html(loadingContentError);
		$("div", divId).append("<a target='_blank' href='"+urlerror+"'>&nbsp;</a>");		
	}
}

// utilis� dans register/terrier
var beforeFormAction = function(a, form ,c){
	if ( $.browser.msie6 ) {
		$(form).css("visibility","hidden");	// suppression du form
	}
	
	$(form).block();
	
};

// peut servir pour d�bugguer les POST en ajax.
function showRequest(formData, jqForm, options) { 
    // formData is an array; here we use $.param to convert it to a string to display it 
    // but the form plugin does this for you automatically when it submits the data 
    var queryString = $.param(formData); 
 
    // jqForm is a jQuery object encapsulating the form element.  To access the 
    // DOM element for the form do this: 
    // var formElement = jqForm[0]; 
 
    // alert('About to submit: \n\n' + queryString); 
 
    // here we could return false to prevent the form from being submitted; 
    // returning anything other than false will allow the form submit to continue 
    return true; 
} 


function showProfilFromFlash(id) {
	TB_show('', '/vl/action/myProfil.do?height=515&width=800&user_id="+id+"&myFriend=1')
}

function submitAjaxForm(formId, outputId, toggle, postSubmitAction) {
		var myForm 		= $(document.getElementById(formId));//$(document.getElementById(idFormDIV)).find("form");
		var myOutput	= $(document.getElementById(outputId));//$(document.getElementById(idFormDIV)).find("div.form_submit_result");
 
 

 
		var options = {
			beforeSubmit:  function(){
				showFormWaitForResponse(formId, true)
			},  
			success: function(a, b, c) {
				showFormWaitForResponse(formId, false);
			}
		};
 		

 		
		if (outputId!=null){
			options.target = "#"+outputId;			
		}
		
 		if (postSubmitAction!=null) {
 			options.success = function(a, b, c){
 				postSubmitAction(a, b, c);
 				showFormWaitForResponse(formId, false);
 			}
 		}
 

		$(myForm).ajaxSubmit(options);
		
	
		if(toggle == true) {
			$(myForm).toggle();
		}
		
		
		if (outputId!=null){ $(myOutput).show(); }
}

function simpleAjaxRequest(url, fAction){
	$.ajax({ type: "GET", url: url, dataType: "html", success: function(e){
		if (fAction!=null) fAction();
	}});
}

function simpleAjaxSubmit(formId, fAction){
	var myForm = $(document.getElementById(formId));
	myForm.ajaxSubmit(function(e){
	   if (e.responseText == "ok") {
		   fAction();
	   }
	});	
}

/**
* @name showFormWaitForResponse
* @desc affiche par dessus un div un bloc de la meme taille, en transparent (css-> backgroundSubmit)
* @formId: l'id du div
* @state: true (affiche) false (cache)
*/
function showFormWaitForResponse(formId, state){
	var form = "#"+formId;
	var w = $(form).width();
	var h = $(form).height();

	if (state==1){ // on l'affiche
		$(form).css("position", "relative");
		$(form).prepend(backgroundSubmitContent);
		$(form).find("div.back", "div.backgroundSubmit").css("width", w+"px").css("height", h+"px");
		
		var ff = $(form).find("div.txt", "div.backgroundSubmit");
		
		var ww = ff.width()+32;
		var hh = ff.height()+32;		
		
		if (h>400) h=200;
		
		ff.css( "left", Math.floor((w-ww)/2)+"px" );
		ff.css( "top", Math.floor((h-hh)/2)+"px" );
		
	
	} else { // on le cache
		$(form).find("div.backgroundSubmit").remove();		
	}
	
}

/**
* @name formSubmitBackground
* @desc submit un formulaire en arriere plan
* @formId: l'id du div
* @actionAfter: function a executer apr?coup, si actionAfter == "jsExecute", la r?nse est ex?t?n javascript
* @where: injection dans le dom
*/
function formSubmitBackground(formId, actionAfter, where){

	var myForm 		= $(document.getElementById(formId));

	// rajoute un param aleatoire a l'url pour feinter le cache d'IE6
	urlAction = $(myForm).attr("action");
	$(myForm).attr( "action", antiCacheUrl(urlAction) );

	var f = function(html, response){
		showFormWaitForResponse(formId, false);
		if (actionAfter!=null && actionAfter!="jsExecute") {
			actionAfter(html, response);
		} else if (actionAfter=="jsExecute") {
			 eval(html.responseText);
		}
	};
		
	var options = {
		beforeSubmit:  showRequest,   
		success: function(html, response) {
			f(html, response);
		}
	};
	
	if (actionAfter!=null) {
		options.success = function(html, response){
			showFormWaitForResponse(formId, false);
			actionAfter(html, response);
					
		}
	}
	
	if ((where!=null) && ($('#' + where).length > 0)) {
		options.target = "#"+where;
	}

	showFormWaitForResponse(formId, true);

	myForm.ajaxSubmit(options);	
}

					

function updateHiddenFromSelect(obj, idHiddenField){
	value = obj.options[obj.selectedIndex].value;
	document.getElementById(idHiddenField).value = value;
}

function updateHiddenValue(idHiddenField, value){
	$("#"+idHiddenField).val(value);	
}

// active un onglet. suppose que tout les onglets sont contenus dans leur pere direct
function activateTab(idTab) {
	var tab = $("#" + idTab)[0];
	// parcours les tags de meme nom dans le parent et leur enleve la classe active
	$(tab.parentNode).find(tab.tagName).removeClass("active");
	// rajoute la classe active a notre onglet
	$(tab).addClass("active");
}

//coche et decoche input checkbox par son id=versionId
function selectInput(versionId){
	var check = document.createAttribute("checked");
 	check.nodeValue = "checked";
 	if(document.getElementById(versionId).getAttribute("checked") == "checked"){
 	document.getElementById(versionId).removeAttributeNode(check);
 	}
 	else{
	document.getElementById(versionId).setAttributeNode(check);
	}
}
		
// param idTab : id du "li" onglet DOIT etre du type xxx_yyyy ou xxx est l'id du div ID qui englobe le tout
// a revoir, le code en dur pour l'id "boxContent" est un peu con, et empeche plusieur tab dans la page
function tabChangeUrl(idTab, urlPage, loadIn) {

		var tab 		= document.getElementById(idTab);
		
		// recup de l'id du div englobant
		tmp = idTab.split('_');
		var idDivEnglobant = tmp[0];
		var divContener = document.getElementById( idDivEnglobant ); //$(tab).parent().parent();
		
		// on sort si l'onglet est en cours de chargement
		if (loadingTable[idDivEnglobant] == true) return;

		// on modifie l'url pour ajouter un chiffre qui varie tout le temps (pour ?ter le cache)
		urlPage = antiCacheUrl(urlPage);

		// on note l'onglet courant pour cette partie
		currentSousTab = tmp[1];		
		
		loadingUrlState(idDivEnglobant, "start");

		// on deselectionne toutes les tabs, on les "disable"
		$(divContener).find("li").removeClass();
		$(divContener).find("li").addClass("disabled");		
		
		// on active celui sur lequel on a cliqu?t on le degrise
		$("li#"+idTab).addClass("active");
		$("li#"+idTab).removeClass("disabled");

		// on recupere le div qui contiendra le html a charger
		objDiv = $(divContener).find("#boxContent");

		objDiv.load(urlPage,
					function(html, response){
						loadingUrlState(idDivEnglobant, "end");						
						$(divContener).find("li").removeClass("disabled");
						if (response == "error") {
							LoaderErrorShow(objDiv, urlPage);
							return;
						}
						
						if (ie6) objDiv.css("height", "1%");			
						objDiv.css("min-height", "1%");

						// on r?nit thickBox car le contenu dynamique peut contenir des liens thickbox
						TB_init("boxContent");
					});


		// chargement du loading
		LoaderShow(objDiv);

}

function tabMessageChangeUrl(idTab, urlPage, formAction){

	var tab 	= document.getElementById(idTab);
	var form 	= $(tab).parents('form');

	if (formAction != null ) {
		// on va changer le ACTION du form englobant
		form.attr("action", formAction);
	}

	// on affiche ou pas certaine fonction suivant le idTab
	if (idTab == "Messages_texte" || idTab == "Messages_vocal") {
		$('#mp3persoDiv').show();
	} else {
		if (document.getElementById('mp3perso')!=null) {
			document.getElementById('mp3perso').checked=false;
			$('#mp3persoDiv').hide();
		}

	}
		
	// dans le cas du mp3, il faut un peu tricher...
	// l'upload se fait dans une iframe cach?
	if (idTab == "Messages_mp3") {
		form.attr("target", "uploadIframe");
	}	

	$("div.myPlayerMp3Message").html("");
		
	tabChangeUrl(idTab, urlPage);
}

/**
* execute le code JS retourn?ans la JSP
* @arg url: url qui va retourner un code JS
* @arg fAction : function a executer apres coup
*************************************************************************/
function ajaxJsExec(url, fAction){
	$.ajax({ 
		   type: "GET",
		   url: url,
		   dataType: "script",
		   success: function(msg){
			   var r = msg;
			   if (r!=null) {
				   r = $.trim(r);
				   eval(r);
			}
			   if (fAction!=null) {
			   		fAction();
			   }
			   
			}
	});	
}

/* ************************************************************************************* */
function divChangeUrlBackground(idDiv, urlPage, functionAfter){
	var myDiv 		= $(document.getElementById(idDiv));

	if (loadingTable[idDiv] == true) return;
	
	blockUI("hide");
								
	loadingUrlState(idDiv, "start");
								
	showFormWaitForResponse(idDiv, true);
	

	
	urlPage = antiCacheUrl(urlPage);
	
	$(myDiv).load(urlPage, function(html, response){
		loadingUrlState(idDiv, "end");						
		showFormWaitForResponse(idDiv, false);
		
		if (response == "error") {
			LoaderErrorShow($(div), urlPage);
			return;
		}
		
		// on r? init thickBox car le contenu dynamique peut contenir des liens thickbox
		TB_init(idDiv);
		if (functionAfter!=null) functionAfter();	
							
	});
	
}


function divChangeUrl(idDiv, urlPage, functionAfter){
	
	var div = $("#" + idDiv)[0];
	if (div == null || loadingTable[idDiv]) return false;
	
		
	blockUI("hide");
		
	loadingUrlState(idDiv, "start");						
	
	urlPage = antiCacheUrl(urlPage);

	// chargement du loading
	LoaderShow(idDiv);

	// chargement de la page
	$(div).load(urlPage, function(html, response){
		loadingUrlState(idDiv, "end");						

		if (response == "error") {
			LoaderErrorShow($(div), urlPage);
			return;
		}
		
		if (ie6) $(div).css("height", "1%");			
		$(div).css("min-height", "1%");

		
		// on r? init thickBox car le contenu dynamique peut contenir des liens thickbox
		TB_init(idDiv);


		if (functionAfter!=null) functionAfter();						
						
	});

}


function loadingUrlState(idDiv, state){



	if (state=="start"){
		loadingTable[idDiv] = true;

	}else if (state=="end"){
		loadingTable[idDiv] = false;	
	}

	
}

var selectedCol = null;

function selectColor(imgId){
	tmp = imgId.split('_');
	idColForLapin = tmp[1];			// on r?pere l'id qu'on place dans le champs hidden
	
	
	var col = document.getElementById(imgId);
	
	if (selectedCol!=null)  {
			$(selectedCol).removeClass();
			$(selectedCol).addClass("color_unselected");				
	}
	
	if (selectedCol == col) { // on a recliqu?ur le meme ??
		document.getElementById('color').value="";
	} else {
		selectedCol = col;
	

		$(col).addClass("color_selected");	
	
		document.getElementById('color').value=idColForLapin;
	}
}


function messagesMoreOptionsToggle(){
	$('#MoreOptions01').toggle();
}
function messagesDateToggle(state){
	if ($("#plustard").get(0) == null) return;
	
	if (state=="force_close") {
		$("#plustard").get(0).checked = false;
		$("#moremore").hide();
	}else {
		$("#moremore").toggle();
	}
	
	if ( $("#moremore").css("display")=="none" ){
		$("#time").hourSelect_SetToCurrent();
		$("#date_delay").dateSelect_SetToCurrent();
	}
	
}

/* ************************************************************************************* */

function selectItemN(a){
	var id	= $(a).attr("id").split("_")[1];
	var t	= $.trim($(a).text());
	TB_remove();
	$("#itemValue").attr("value", id);
	$("#ItemReplaceme1, #ItemReplaceme2").html(t);
	$("div.selectedItem").Pulsate(100, 5);
	return false;
}

function selectItem(text, id){
	TB_remove();
	$("#itemValue").attr("value", id);	
	$("#ItemReplaceme1, #ItemReplaceme2").html(text);
	$("div.selectedItem").Pulsate(100, 5);
}


function selectNabshare(text, id){
	$("#ItemReplaceme1, #ItemReplaceme2").html(text);
	$("div.selectedItem").Pulsate(100, 5);	
}

/* ************************************************************************************* */
function selectRub(obj){

	$("a").removeClass('NabshareSelected'); // on deselect tout
	obj.addClass("NabshareSelected"); // on select celui qu'on veut
}


function nablifeCloseAll(){
		$("div.nablife_more").hide();
}
function nablifeOpenAll(){
		$("div.nablife_more").show();
}



function sendMsgTo(pseudo, type) {
	TB_remove();
	
	// on est pas du tout sur la bonne page pour envoyer un message...
	if ( $("#contentEnvoyer").length == 0 ) {
		redirect("../action/myMessages.do?nabname="+pseudo);
		return;		
	}
	
	$('#destName').val(pseudo);

	mainTab_GoTab("Envoyer");	
	
	switch (type) {
		case "text":
			tabMessageChangeUrl( 'Messages_texte' , '../action/myMessagesTTS.do','../action/myMessagesSendTTS.do');
			break;

		case "mp3":
			tabMessageChangeUrl( 'Messages_mp3' , '../action/myMessagesMp3.do','../action/myMessagesSendMp3.do');
			break;

		case "clindoeil":			
			tabMessageChangeUrl( 'Messages_clindoeil' , '../action/myMessagesClin.do','../action/myMessagesSendClin.do');
			break;
			
		case "bibliotheque":		
			tabMessageChangeUrl( 'Messages_bibliotheque' , '../action/myMessagesBiblio.do','../action/myMessagesSendBiblio.do');
			break;
			
		default: // on change pas d'onglet
			
			break;

	}
	
	// remonte en haut de la page
	goToTheTopOfThePage();
	
	return false;

}



/// TERRIER *****************************************************************************************
function updateMp3List(){
	// bon le TB remove n'a rien a foutre ici
	// mais mysterieusement il fait merder dans endMp3Upload
	divChangeUrl('contentMaMusique','../action/myListeMp3.do', function(){TB_remove()});
}

function terrierActivateUpload(){ 
	
	var opt = {
		beforeSubmit: 	function(a, b ,c){
							
							
							var r = terrierValidateUploadMp3();
							if (r) {
								ProcessingShow("uploadForm");
							} else {
								return false;
							}
							
						},	
		success: 		function(){
							TB_remove();
							updateMp3List();
						}			
	}
	
	$("#uploadMP3_form").ajaxForm(opt);
						
}

function terrierStartMp3upload() {

	
	// on submit le form si tout est ok
	document.getElementById("uploadMP3_form").submit();
	
	ProcessingShow("uploadForm");
}


var gCurrentMp3 = null;
function mp3_toggleOptions(DivMusicId, categId){
	var tmp = DivMusicId.split('_');
	var idMp3=tmp[1];

	// on attend, s ce div est deja en train de charger, on se casse.
	if (loadingTable[DivMusicId] == true) return;
		

	var myOptions = $('#'+DivMusicId);
	
	$("#"+DivMusicId).parents("ul").find("li.edit a").toggleClass("selected");
			
	if ( myOptions.css("display")=="block"){ // ferme le block
			myOptions.css("display", "none");
			
			$('#namer_'+idMp3).remove();
			$('#mp3name_'+idMp3).toggle();
			
			gCurrentMp3=null;
			
	} else { // ouvre le bloc
			if (gCurrentMp3!=null && gCurrentMp3!=DivMusicId) mp3_toggleOptions(gCurrentMp3);
			
			gCurrentMp3 = DivMusicId;
			
			myOptions.css("display", "block");	
			
			divChangeUrl(DivMusicId,'../action/myListeMp3.do?categId='+categId+'&mp3Id='+idMp3,
				function(){
					var value = $('#mp3name_'+idMp3).html();
					value = value.replace(/"/gi, "&#34;");

					$('#mp3name_'+idMp3).after("<input id='namer_"+idMp3+"' name='musicName' type='text' class='nameMp3Input' value=\""+$.trim(value)+"\">");
					$('#mp3name_'+idMp3).toggle();

			});	
	}
}


function loadPersoPlayer(fil, size, autoplay, divId) {
	if (size==null) size = 350;
	if (divId==null) divId = "myPlayerMp3";

	$("#"+divId).html("");
	
	var p = 'http://';
	
	var t = fil.split("/");
	t = t[0];
	var counter = 0;
	for (o=0; o<=t.length-1; o++){
		if (t.charAt(o)==".") {
			counter++;
		}
	}
	
	fil = strReplace(fil, "broadcast/", "");
	
	if (counter>=3) {	// ceci est (peut etre) une adresse ip
		p = p + fil;	
	} else {
		p = p + nabaztag.constantes.STREAM_SERVER + "/" + fil;
	}

	var s1 = new SWFObject("../include_flash/mediaplayer.swf","player","230","20","8");
	s1.addParam("allowfullscreen","false");
	s1.addVariable("width","230");
	s1.addVariable("height","20");
	s1.addVariable('backcolor', '0xEEEEEE');
	s1.addVariable('frontcolor', '0x666666');
	if (autoplay==true) s1.addVariable('autostart', 'true');
	s1.addVariable("file",p);
	s1.addVariable('enablejs', "true");
	s1.addVariable('showeq', "true");
	s1.addVariable('showdigits', "false");
	s1.addVariable('usefullscreen', "false");
	
	if (!s1.write(divId)) { // player detection failed
		var container = document.getElementById(divId);
		if (container) container.innerHTML = s1.getSWFHTML();
	}
	
}



/* **************************************************************************************************************************** */

function messagesChangePage(idDiv, page_index){
	var _do;
	
	if (idDiv == "contentArchives") _do = "archived";
	else if (idDiv == "contentRecu") _do = "received";
	else if (idDiv == "contentEnvoyes") _do = "sent";
	
	divChangeUrl(idDiv, '../action/myMessagesList.do?action='+_do+'&page_index='+page_index);
	
}


function messageListColorization(idTableListe){
	$("tr:even", "#"+idTableListe).addClass("even");
	$("tr:odd", "#"+idTableListe).addClass("odd");
}

function messages_selectMsg(obj){

	var checked = $(obj).attr("checked");
	var tr = $(obj).parents("tr");

	if (!checked) {
		$(tr).removeClass("selected");
	} else {
		$(tr).addClass("selected");
	}
}

function messages_select_all(idTableListe){
	$("#"+idTableListe+" input[@type=checkbox]").each( function(){
		$(this).attr("checked", true);
		messages_selectMsg(this);
	});
}

/* **************************************************************************************************************************** */
function alert_getPageSize(){
	var de = document.documentElement;
	var w = window.innerWidth || self.innerWidth || (de&&de.clientWidth) || document.body.clientWidth;
	var h = window.innerHeight || self.innerHeight || (de&&de.clientHeight) || document.body.clientHeight;
	
	arrayPageSize = new Array(w,h); 
	return arrayPageSize;
}

function alert_getPageScrollTop(){
	var yScrolltop;
	if (self.pageYOffset) {
		yScrolltop = self.pageYOffset;
	} else if (document.documentElement && document.documentElement.scrollTop){	 // Explorer 6 Strict
		yScrolltop = document.documentElement.scrollTop;
	} else if (document.body) {// all other Explorers
		yScrolltop = document.body.scrollTop;
	}
	arrayPageScroll = new Array('',yScrolltop); 
	return arrayPageScroll;
}

function alert_center(){
	var pagesize = alert_getPageSize();
	var arrayPageScroll = alert_getPageScrollTop();

	$("#alertBox")
	.css({left: ((pagesize[0] - $("#alertBox").width() )/2)+"px", top: (arrayPageScroll[1] + ((pagesize[1]- $("#alertBox").height() )/2))+"px" })
	.css({display:"block"});
}

function customConfirmN(msg, title, callback, yesno){
	var html = "";

	if (title==null) title="&nbsp;";
	html+= '<div id="confirm" class="jqmDialog jqmdWide">';
	html+= '<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC">'+title+'</div></div></div>';
	html+= '<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">';
	html+= '<div class="jqmdMSG">';
	
	html+= '<p>'+msg+'</p>';

	html+= '<div class="jqmdButtons">';
  	
  	if (yesno == true) {
  		msgYes = msg_txt['YES'];
  		msgNo = msg_txt['NO'];  		
  	} else {
  		msgYes = msg_txt['OK'];
  		msgNo = msg_txt['cancel'];  		
  	}
  	
  	html+= '<input type="submit" class="no" value="'+msgNo+'" />';
 	html+= '<input type="submit" class="yes" value="'+msgYes+'" />';
  		
 	html+= '</div>';
 	
	html+= '</div>';
	html+= '</div></div></div>';
	html+= '<input type="button" value="" class="jqmdX jqmClose" />';

	html+= '</div>';

	
	$('#confirm').jqmHide();
	$('#confirm').remove();
	
	$("body").prepend(html);
	$('#confirm').jqm({overlay: 75, modal: true, trigger: false});
	

  $('#confirm')
    .jqmShow()
	 .find('input')
      .click(function(){
        if( $(this).is('.yes') && (callback != null) ) (typeof callback == 'string') ? window.location.href = callback : callback();

        $('#confirm').jqmHide();
      });
      
	

}
function customAlertN(msg, title, callback){
	var html = "";
	if (title==null) title="&nbsp;";
	html+= '<div id="confirm" class="jqmDialog jqmdWide" >';
	html+= '<div class="jqmdTL"><div class="jqmdTR"><div class="jqmdTC" >'+title+'</div></div></div>';
	html+= '<div class="jqmdBL"><div class="jqmdBR"><div class="jqmdBC">';
	html+= '<div class="jqmdMSG">';
	
	html+= '<p>'+msg+'</p>';

	html+= '<div class="jqmdButtons">';
 	html+= '<input type="submit" class="yes" value="'+msg_txt['OK']+'" />';	
 	html+= '</div>';
 	
	html+= '</div>';
	html+= '</div></div></div>';
	//html+= '<input type="button" value="" class="jqmdX jqmClose" />';
	html+= '<input type="image" class="jqmdX jqmClose" src="../include_img/dialog/close.gif"/>';
	

	html+= '</div>';

	
	$('#confirm').jqmHide();
	$('#confirm').remove();
	
	$("body").prepend(html);
	$('#confirm').jqm({overlay: 75, modal: true, trigger: false});
	  

	  $('#confirm')
	    .jqmShow()
	    .find('input')
	      .click(function(){
			if( $(this).is('.yes') && (callback != null) ) (typeof callback == 'string') ? window.location.href = callback : callback();	        
			$('#confirm').jqmHide();
		  });
	
	
}



function alertWaitActionResponse(msg, urlTraitement, callback, pre_callback){
	// on cache tous les select....
	if (ie6) $("select").css("visibility","hidden");	// merci le bug d'ie, a voir comment r?er ?mieux que ?

	if (pre_callback != null) pre_callback();

	$("#modalContainer").remove();	// si le fond de page existe, on le vire

	// on cr? le fond de page
	$("body").append("<div id='modalContainer'></div>");
	$("#modalContainer").css("height", document.documentElement.scrollHeight + "px");
	
	$("body").append("<div id='alertBox'><p id='msgResponse'>"+msg+"</p></div>");
	$("#alertBox").css("backgroundImage", "url('../include_img/alert/lapin.gif')");

	// centrage de la boite
	var w = document.getElementById('alertBox').offsetWidth;
	$("#alertBox").css("left", ((document.documentElement.scrollWidth - w) / 2) + "px");

	$("#alertBox").append("<div id='btContener' style='width:100%; text-align:center;'></div>");
	$("#btContener").append("<a id='alertBtOK' href='javascript:void(0);'>"+msg_txt['OK']+"</a>");

	alert_center();
	
	$(window).resize(function(){alert_center()});
	$(window).scroll(function(){alert_center()});

// si ya une url de traitement, on suppose qu'il y a aussi un bouton cancel
	if (urlTraitement!=null) {
		alertAddCancel();

		$("#alertBtOK").click( function(){

			divChangeUrl("msgResponse", urlTraitement, function(){
				alertRemove();
			});
			
		});
		
	} else if ( urlTraitement==null && (callback != null) ) {

		alertAddCancel();

		$("#alertBtOK").click( function(){
			alertRemove();
			callback();
		});
		
		//callback();
	} else {

		$("#alertBtOK").click( function(){
			alertRemove();
		});		
	}
	
}

function alertRemove(){
	$(window).unbind();	
	$("#modalContainer").remove();
	$("#alertBox").remove();	
	if (ie6) $("select").css("visibility","visible");	// merci le bug d'ie, a voir comment r?er ?mieux que ?
}

// ajoute un bouton annuler a la boiboite
function alertAddCancel(){
	$("#btContener").prepend("<a href='javascript:void(0);' id='alertBtCancel' onclick='alertRemove();'>"+msg_txt['cancel']+"</a>");
}

// vire le bouton
function alertRemoveCancel(){
	$("#alertBtCancel").remove();
}


/* **************************************************************************************************************************** */

function messagesStartMp3upload() {
	// on submit le form si tout est ok
	document.getElementById("sendMsg").submit();
}

function messagesEndMp3upload(id, url) {		 
	tabMessageChangeUrl( id , url, '../action/myMessagesSendMp3.do');
}


function profilEndUpdateComment(){
	TB_remove();
	divChangeUrl('myInfo', '../action/myTerrierMyself.do');
}


/**
* @name srvStatusToggle
* @desc toggle l'affichage ou pas des description des nabcasts/services
* @arg modeOrId id du service, ou "all" si cela concerne tous les services
* @arg state 0 ou 1 pour ouvrir fermer, rien pour juste un toggle (uniquement si modeOrId != all dans ce cas)
*************************************************************************/
function srvStatusToggle(modeOrId, state) {
	if (modeOrId=="all") {
		if (state==0) $("div.desc").hide();
		else $("div.desc").show();
	} else {
		$("#statut_"+modeOrId).toggle();
	}
}


/**
* @name showProfilFromAnnuaire
* @desc affiche le profil de quelqu'un, dans l'annuaire
* @arg id identifiant de la personne dont on veut voir la fiche
*************************************************************************/
function showProfilFromAnnuaire(id){
	divChangeUrl("profilContent", "myProfil.do?user_id="+id);
	
	$("#resultat-recherche").hide();
	$("#annu-form").hide();
	$("#profilBlock").show();
}

function returnToAnnuaire(){
	$("#resultat-recherche").show();
	$("#annu-form").show();	
	$("#profilBlock").hide();
}

/**
* Supprime son nabcast a soi
*/
function DeleteMyNabcast() {
	var nabcastId = $("#idNabcast").val();
	
	customConfirmN( msg_txt['nabcats_confirm_delete'] + "<br /> <span style='font-weight:bold;'>" + $("#nabcastName").val() +"</span>", null, function(){

		$.ajax({ type: "GET", url: "myNabaztalandCreate.do?idNabcast="+nabcastId+"&mode=4", dataType: "html" });
	
		$("#myNabcastsList_"+nabcastId).destroyMe();
		divChangeUrl("contentPublier", "myNabaztalandCreate.do");
	});	
	
	
}

/**
* @name confirmNabcastMp3Delete
*************************************************************************/
function confirmNabcastMp3Delete(urlAction){
	customConfirmN( msg_txt['confirm_deleteNabcastMp3'], null, function(){ divChangeUrl('mynabcastUpload', urlAction) });
}
/**
* @name confirmNabcastMp3Delete
*************************************************************************/
function myNabcastTitleUpdate(str){
	var val =  $("h2.nabcastName").html();
	if (val!="") {
		val = val.split("-");
		val = $.trim(val[0]);
	}
	
	$("h2.nabcastName").html( val + str);
}



function myNabcastLaterToggle(){
	$("#myNabcastLater").toggle();
	
	if ( $("#myNabcastLater").css("display")=="none" ){
		$("#time").hourSelect_SetToCurrent();
		$("#date_delay").dateSelect_SetToCurrent();
	}

}


function nabcastSrvDelete(srvItemNb) {
	var srvName = $("span.nabcast-title").html();
	var srvUrl 	= "myNabaztalandSubscribe.do?mode=1&idNabcast="+srvItemNb;
	var msg = msg_txt['del_service']+"<br> <strong>"+srvName+"</strong>";	
	var default_func = function() {

		$.ajax({ type: "GET", url: srvUrl, dataType: "html"} );
				
		$("#bloc-MyServices #id_"+srvItemNb+"_1").destroyMe();
		srvConfigToggle();
	}
	
	customConfirmN(msg, "", default_func);
		
}

function srvRSSDelete() {
	var div = "contentSrvConfig";
	var rss_id = $("input[@name='rss_id']").val();
	var rss_name = $("input[@name='rss_name']").val();
	var urlDelete 	= $("#"+div+" form").attr("action") + "?queFaire=del_free&rssuser_id="+rss_id+"&rss_name="+rss_name ;	
	var srvId = $("#internalSrvId").val();
	var srvName = $("#"+div+" h1").html();
	

		
	var func = function() {
		$.ajax({ type: "GET", url: urlDelete, dataType: "html", success: function(e){}});
		
		
							
		$("#bloc-MyServices #id_"+srvId+"_2").destroyMe();
		srvConfigToggle();
	}
	
	var msg = msg_txt['del_service']+"<br> <strong>"+srvName+"</strong>";

	customConfirmN(msg, "", func);
}

function srvPodcastDelete() {
	var div = "contentSrvConfig";
	var pod_id = $("input[@name='podcast_id']").val();
	var pod_name = $("input[@name='podcast_name']").val();
	var urlDelete 	= $("#"+div+" form").attr("action") + "?queFaire=del_free&podcastuser_id="+pod_id;	
	var srvId = $("#internalSrvId").val();
	var srvName = $("#"+div+" h1").html();
	

		
	var func = function() {
		$.ajax({ type: "GET", url: urlDelete, dataType: "html", success: function(e){}});
		
		
							
		$("#bloc-MyServices #id_"+srvId+"_2").destroyMe();
		srvConfigToggle();
	}
	
	var msg = msg_txt['del_service']+"<br> <strong>"+srvName+"</strong>";

	customConfirmN(msg, "", func);
}

function srvWebRadioDelete(srvName) {

	var srvId = $("#internalSrvId").val();
	alert("srvId = "+srvId);
	

		
	var func = function() {
		$("#bloc-MyServices #id_"+srvId+"_2").destroyMe();
		srvConfigToggle();
	}
	
	var msg = msg_txt['del_service']+"<br> <strong>"+srvName+"</strong>";

	customConfirmN(msg, "", func);
}

function mailSrvDeleteItem(obj, selectId){
	var div = "contentSrvConfig";
	var srvName = $("#"+div+" h1").html();
	var compte = $("#mail_serveur").val();
	var inp = $(obj).parents("li.listentries").find("input[@name=keywords]");
	
	var func = function(){

		$('#add').val('0');
		$('#delete').val('1');
		$('#keyword').val( $(inp).val() );
		
		formSubmitBackground("srvMailConfig", function(html){
			mailSrvListItemRefresh(selectId); 
			$("#config").html(html.responseText);
		});
	}

	var msg = msg_txt['del_service_item']+" <strong>"+$(inp).val()+" ( "+srvName+" ) </strong>"
	customConfirmN(msg, "", func);	
	
	return false;
	
}

function mailSrvListItemRefresh(selectId){
	var sel = "srvItem_"+selectId;
	var url = "myMailsList.do?justList=true";

	divChangeUrlBackground("list-contener", url, function(){
		if (selectId!=false){
			$("#"+sel).addClass("selected");
		}
	});	
}

/**
 * srvItemNb est utilisé pour effacer une config a l'interieur d'un service
 * ex : effacer une alerte sur une valeur en particulier de Bourse Full
 * srvsrc ne sert (pour l'instant) que pour mail, qui n'est pas fait pareil que les autres...
 * @arg idSrv id du service
 * @arg type type du service (nabcast ou service)
 * @arg url url du service
 */
function srvDelete(srvItemNb, srvsrc) {
	
	if (srvsrc==null) srvsrc=""; // par defaut
	
	var srvId = $("#internalSrvId").val();

	var div = "contentSrvConfig";
	if ( $("#"+div).length == 0 ) div = "Sleep"; // si on trouve pas l'un, on prend l'autre (dans monTerrier)

	var srvName = $("#"+div+" h1").html();
	if (srvName==null) srvName = $("h2.srv-title").html();
	if (srvName==null)srvName = $("div.service-introduction h2").html();
	
	if (srvName=="") srvName = "<font color='red'>no name</font>";
	
	srvName = $.trim(srvName);
	
	var srvUrl 	= $("#"+div+" form").attr("action");	
	//var srvItemNb = $("#valueTo").val();
	var srvItemName = $("#srvItem_"+srvItemNb+" li.nom").html();
	var urlDelete = srvUrl+"?delete=1";

	if (srvId==6 || srvsrc != "" ) { // cas particulier du mail
		urlDelete = srvUrl+"?srv_src="+srvsrc+"&delete=1"; //srv_id=<%=accountSrv_src%>
	}

	var default_func = function() {
	
		$.ajax({ type: "GET", url: urlDelete, dataType: "html", success: function(e){
			
			if (div == "contentSrvConfig") {
				//blocLoad("bloc-MyServices");
				//srvConfigToggle();				
			} else { // quick en dirty, quand on est dans le service couché, qui n'est pas au meme endroit que les autres
				$("#contentCoucheConfig").html(e);
				
				$("#contentCoucheConfig script").each( function(){
					eval( $(this).html() );
				});				
			}
						
		}});
		
		
		bloc.services.elementDestroy(srvName);
		
		srvConfigToggle();
		
	}
		
	var itemDelete_func = function(){
		$.get(urlDelete);
		$("div.srv-list-entries #srvItem_"+srvItemNb).destroyMe();
	}
	

	var func = default_func;
	var url = null;
	
	var msg = msg_txt['del_service']+"<br> <strong>"+srvName+"</strong>";
	

	// dans ce cas précis, nous ne voulons pas effacer TOUT le services,
	// meme juste une alerte de ce service
	if (srvItemNb!=null) {
		urlDelete = urlDelete + "&valueTo="+srvItemNb;
		func = itemDelete_func;
		msg = msg_txt['del_service_item']+"<br> <strong>"+srvItemName+" <br /> ( "+srvName+" ) </strong>"
		//alertWaitActionResponse(msg, url, func);
		//return;
	}
			
	customConfirmN(msg, null, func);

}

function endSrvConfig(e, r, miseAjour) {

	var id = $("#internalSrvId").val();
	var type = $("#internalTypeId").val();	

	var div = "contentSrvConfig";
	if ( $("#"+div).length == 0 ) div = "Sleep"; // si on trouve pas l'un, on prend l'autre (dans monTerrier)

	html = e;
	
	
	// && $("div.tabSousNavContener-tr").length==0 ! pas bien pour la bourse
	if ( $("div.service-temp").length==0 /*&& $("div.tabSousNavContener-tr").length==0*/ ) { // q&d patch...
		if (html != null) {
			
			// on repositionne le flag a false.
			// dans les eval qui suivent, un evenement peut t�s bien modifier le flag
			// en cas d'erreur dans la config du service.
			gSrvErrorFlag = false; 
						
			if ($("div.tabSousNavContener-tr").length>0) { // q&d patch pour les services semi refait....
				$("div.tabSousNavContener-tr").remove();				
				$("div.srv-cadre-contener").siblings("script").remove();
				 
				
				$("div.srv-cadre-contener").after( html );
				
				
				$("div.srv-cadre-contener").siblings("script").each(function(){
					eval( $(this).html() );
				});
				
				
			} else {
				$("#"+div).html(html);
				
			
			}
			


		}	
	} else {
		if (html != null) {
				$("div.service-temp").remove();
				$("div.srv-cadre-contener").after(html);
					
				$("div.srv-cadre-contener script").each( function(){
					eval( $(this).html() );
				});	
		}
		
		
	}
	
	
	
	if (!miseAjour && $("#bloc-MyServices").length>0) {
		// selection du bon sous onglet dans mes services
		if (type==2) {
			gBlocMyServices = "service";
			if (gSrvErrorFlag == false) $.msgPopupOverlay(msg_txt['ajout_srv_ok']);//$("#contentSrvConfig, #TB_ajaxContent").msgPopup(msg_txt['ajout_srv_ok'], gHColor, 4000);
		} else {
			gBlocMyServices = "nabcast";
			if (gSrvErrorFlag == false) $.msgPopupOverlay(msg_txt['ajout_nabcast_ok']);//$("#contentSrvConfig, #TB_ajaxContent").msgPopup(msg_txt['ajout_nabcast_ok'], gHColor, 4000);			
		}
		blocLoad("bloc-MyServices", null, function(){blocMyServices_Select("id_"+id+"_"+type)});
	} else {
		if (type==2) {
			if (gSrvErrorFlag == false) $.msgPopupOverlay(msg_txt['modif_srv_ok']);//$("#contentSrvConfig, #TB_ajaxContent").msgPopup(msg_txt['modif_srv_ok'], gHColor, 4000);
		} else {
			if (gSrvErrorFlag == false) $.msgPopupOverlay(msg_txt['modif_nabcast_ok']);//$("#contentSrvConfig, #TB_ajaxContent").msgPopup(msg_txt['modif_nabcast_ok'], gHColor, 4000);			
		}		
	}
}

function serviceDelete(){
	goDispatch('delete', 'dispatchConfig');
	
	customConfirmN(msg_txt['del_service'], msg_txt['ask_confirmation'], function(){
			// on submit le formulaire via ajax et on inject le r�sultat dans le bon div
			$('form.srvConfigForm').ajaxSubmit(function(data) {
				$.msgPopupOverlay( msg_txt['delete_srv_ok'] );
				bloc.services.elementDestroy( nablife.getCurrentServiceName() );
				nablife.returnToSrvList();
			});
	});
	
}


function serviceUpdate(srvColumnMAJ){
	var divToUpdate = $("form.srvConfigForm").parents("div.main-cadre-contener");
	var divLoader;
	var msg;
	
	// le flag gSrvErrorFlag peut etre sett� a TRUE par le resultat du formulaire
	gSrvErrorFlag = false;

	// on regarde si le DIV dans lequel on charge le html existe (par defaut il n'existe pas dans le markup)
	// s'il n'est pas la on le rajoute
	if ( $(divToUpdate).parent("#srvLoader").length<1 ) {
		$(divToUpdate).wrap("<div id='srvLoader'></div>");
	}
	
	divLoader = $("#srvLoader");
	
	// on 'block' le div
	$(divLoader).block();

	// rajoute un param aleatoire a l'url pour feinter le cache d'IE6
	urlAction = $('form.srvConfigForm').attr("action");
	$('form.srvConfigForm').attr( "action", antiCacheUrl(urlAction) );


	var options = {
		target:divLoader,
	
		success: function(data) {
					
						 	
		 	$(divLoader).unblock();
			
			// suivant si on est dans une update ou un ajout de service, on ne met pas le meme text
			if (!srvColumnMAJ) {
				msg = msg_txt['ajout_srv_ok'];

				blocLoad("bloc-MyServices", null, function(){
					//blocMyServices_Select();
					bloc.services.elementHighlight( nablife.getCurrentServiceName() );
				});
			} else {
				msg = msg_txt['modif_srv_ok'];
			}
			
			// on affiche le text
			if (gSrvErrorFlag == false) {
				$.msgPopupOverlay( msg );
			}				
		}
	};
 
	
	// on submit le formulaire via ajax et on inject le r�sultat dans le bon div
	$('form.srvConfigForm').ajaxSubmit(options);
}

function srvConfigToggleN(urlPart){
	// on cache la liste des services	
	$("#content"+currentTab).hide();
	blocMyServices_SelectByUrl(urlPart);
}

// quand on est dans myNablife, amene direct sur l'onglet nabcast, sur le nabcastChoisi
function goNabcast(idNabcast){
	mainTab_GoTab('Nabaztaland'); 
	srvConfigToggle('id_'+idNabcast+'_1', '/vl/action/myNabaztalandSubscribe.do?idNabcast='+idNabcast);
}

// quand on est dans myNablife, amene direct sur l'onglet service, sur le service choisi
function goService(urlService, idService){
	mainTab_GoTab('AllServices'); 
	srvConfigToggle('id_'+idService+'_2', urlService);
}

// sans parametre, �a ferme le div de config du service 
function srvConfigToggle(id, url){
	
	// Dans ce cas, nous sommes sur une page "compte full" only, on fait un traitement different
	if ( $("div.blockUI").length > 0 ) {
		// on cache le blockui.
		blockUI("hide");
		nablife.returnToSrvList();
		return;	
	}
	
	if (id==null && url==null) {
		nablife.returnToSrvList();	
		return;		
	}
	
	
	blockUI("hide");


	if (currentTab=="Publier") {	//on est dans l'onglet "publier"
		mainTab_GoTab("AllServices");
	}
	
	
	var tmp; 
	var internalSrvId; 
	var internalTypeId;	
	
	
	if (id!=null) {
		// on recup l'id du service
		tmp = id.split("_"); 
		internalSrvId = tmp[1];
		internalTypeId = tmp[2];	

		// mega bidouille si on a un type 4, on le transforme en type 2
		if (internalTypeId == 4) internalTypeId = 2;
		

		// on cache la liste des services	
		$('div.contentMainTab').hide();
	
		// on affiche la config pour ce service
		$("#contentSrvConfig").show();

		divChangeUrl('contentSrvConfig', url, function(){ 
			
			goToTheTopOfThePage();
				
			//was "form"
			$("div.mainTabBody #contentSrvConfig").prepend("<input type='hidden' value='"+internalSrvId+"' id='internalSrvId' />");
			$("div.mainTabBody #contentSrvConfig").prepend("<input type='hidden' value='"+internalTypeId+"' id='internalTypeId' />");			

			blocMyServices_Select(id);
		});
		
	} else {
		blocMyServices_unSelectAll();
		
		$("#internalSrvId").remove();
		$("#internalTypeId").remove();		
		
		
		$("#content"+currentTab).show();
		$("#contentSrvConfig").hide();
	}
}

function srvClearReveil(nb){
	$("#time"+nb).hourSelect_Reset();
	if ( $("div.popup-song").length>1 ) $.chooseSong.reset("#son"+nb);	
}


// obj = obj du bouton a disabler
function disableBt(obj){
	$(obj).fadeTo("fast", 0.3);
	$(obj).attr("href","javascript:;");
	$(obj).unbind("click");
}


function moreOptionsToggle(id){
	$("#more_"+id).toggle();
}


function configureMailAccount(idItem, srv_src, mail_serveur, mail_protocol, mail_compte){
	loadKeywords = function(){
		divChangeUrl("mailAlertList","myMailsAccounts.do?queFaire=add_keywords&srv_src="+srv_src);
		$("#"+idItem).addClass("selected");
	}
	
	divChangeUrlBackground('contentSrvConfig', 'myMailsAccounts.do?queFaire=upd_m&srv_src='+srv_src+'&mail_serveur='+mail_serveur+'&mail_protocol='+mail_protocol+'&mail_compte='+mail_compte, loadKeywords);
}

function configureMailAccountKeyword(obj, musicUrl, musicName, passive, keyword_Srvsrc ){
	
	// heula ste bidouille...
	// je recupere le keyword non pas en passage d'argument (car si le keyword contient des " ou des ' ça risque de planter...
	// mais en le récuperant direct dans le dom...
	// c'est hideux, mais sinon ça ne marche pas, ou alors il faut changer dans le java et me renvoyer une chaine correcte....
	var ulRoot = $(obj).parents("ul");
	
	var keyword = $("li.myKeyword", ulRoot).find("span").html();
	var music = $("li.myMusic", ulRoot).find("span").html();
	var passive = $("#passive_mode", ulRoot).val();
	
	var idConfig = $("li.formModify", ulRoot);
	
	
	// on ferme les autres
	$("li.myKeyword, li.myMusic, li.keyModifier").show();		
	$("li.formModify").hide();
		
	$("li.myKeyword, li.myMusic, li.keyModifier", ulRoot).hide();	
	
	// on rempli le champ keyword
	$("input.keyword", ulRoot).val(keyword);

	// on select la bonne musique
	$("#musicUrl").select_SelectFromValue(music);

	
	// on check le bon bouton radio pour le passive
	$("input.passive_"+passive, ulRoot).get(0).checked=true;
	
		
	$(ulRoot).append("<input type='hidden' name='oldKeyword' value='"+keyword+"' />");
	$(ulRoot).append("<input type='hidden' name='oldpassive' value='"+passive+"' />");	
	
	$(idConfig).show();
	
	
	// on selectionne ce li
	$("#mailAlertList").find("li.selected").removeClass("selected");
	$(ulRoot).parents("li").eq(0).addClass("selected");
}

function startSrvDialogAction(url, fAction){
	var id = null; 
	
	// suivant si on est dans la popup ou pas...
	if ( $("#TB_ajaxContent").length > 0 ) id = "TB_ajaxContent";
	else id = "contentSrvConfig";
	
	divChangeUrlBackground(id, url, function(){if (fAction!=null) fAction();} );
}

function endSrvDialog(	state){
	// on accepte de se communier avec la personne (et c'est beau)
	switch (state) {
		case 'accept':
		case 'refused':
		case 'jette':
			TB_remove();
			srvConfigToggle();
		break;
	};
	
}

function addFriend(friendId){
	var url = "myAddFriend.do?friend_id="+friendId;

	$("#buttonAddFriend").hide();
	showFormWaitForResponse("TB_ajaxContent", true);
	
	simpleAjaxRequest(url, function(){

		showFormWaitForResponse("TB_ajaxContent", false);
			$("#TB_ajaxContent").msgPopup(msg_txt['ami_ajoute'], gHColor, 4000);
			blocLoad("bloc-friends"); // refresh du bloc
	});
}


function findPeopleRandomly(){
	$("#blocDest").block();


	ajaxJsExec("../action/myMessages.do?shuffle=1", function(){
		$("#blocDest").unblock();
	});
}

function terrierUploadOptionToggle() {
	var opened = $('#uploadMP3options').css("display")!="none";
	
	if (opened){
		$('#uploadMP3options').hide();
		TB_HEIGHT = 170;

	}else {
		$('#uploadMP3options').show();
		TB_HEIGHT = 325;		
	}
	
	$("#TB_ajaxContent").height( (TB_HEIGHT-16) + "px" );	
	TB_position();
	
}

function sendMp3Chooser(state){
	
	// on reset le form
	$("#musicName").val("");
	$("#musicFile").val("");
	$("#idMp3").get(0).selectedIndex = 0
	
	
	// on deselect tout
	$("ul.menu li").removeClass("selected");
	
	// on reselect le bon "onglet"
	$("#"+state+"_li").addClass("selected");
	
	// affiche la bonne partie
	$("div.mp3-choice").hide();
	$("#"+state).show();
}

function loadConfig(){
	var layouts = ["layout_green", "layout_pink", "layout_violet", "layout_blue", "layout_orange"];

	var layout = layouts[0];
	/* on n'appelle plus myUserPrefs.do, on regarde directement dans les cookies */
	
	var prefs = getCookie('userprefs_layout');
	if( prefs != null && prefs != "" ){
		loadCss( prefs );
	}
	else{
		loadCss( layout );
		//saveConfig( "userprefs_layout", layout );
	}
	
	$(document).ready(function() {
		$("body div#container").css("display", "block");
	});	
	
	/*
	var u = antiCacheUrl("myUserPrefs.do");
	
	 $.getJSON(u, { mode: "load" },
	 	function(json){
	   		gGlobal = json;

	   		if (gGlobal!=0) {
		   		if (gGlobal.prefs['userprefs_layout']!=null && gGlobal.prefs['userprefs_layout']!="") {
		   			loadCss( gGlobal.prefs['userprefs_layout'] );
					//alert(" 1 pref: " + gGlobal.prefs['userprefs_layout'] );
		   		} else {
		   			loadCss( layout );
		   			saveConfig("userprefs_layout","layout_green");
					//alert("2 pref: layout_green" );
		   		}
	   		} else {
   				loadCss( layout );
				//alert("2 pref: layout_green" );
	   		}
	   					
			$(document).ready(function() {
			   $("body div#container").css("display", "block");
			});	
			     
	   }
	 );*/
 
 	
 
}

function saveConfig(field, value){
	 var u = antiCacheUrl("myUserPrefs.do");
	 $.get(u,
	   { mode: "update", champ: field, "setValue": value },
	   function(data){
	     
	   }
	 );
	

}


function addPreview(keyPreview, textPreview){
	var so = new SWFObject("../include_flash/CDLEditor.swf", "mymovie", "135", "135", "8");
	var newCdl = gCdl[ keyPreview ];
	so.addVariable("cdll", newCdl);
	
	$("#keskidit div.inner").append("<div class='nabSrvPreview' id='prev_"+keyPreview+"'></div>");

	if (!so.write("prev_"+keyPreview)) {
		var container = document.getElementById("prev_"+keyPreview);
		if (container) container.innerHTML = so.getSWFHTML();
	}
	$("#prev_"+keyPreview).append("<strong>"+textPreview+"</strong>");
}

function loadCss( template ){
	$("ul.skin-selector li a").removeClass("selected");
	$("li."+template+" a").addClass("selected");
	
	$("#skin-template").val(template);

	$(document).ready(function() {
		// on change de css
		$("link[@title][@rel*=style]").each(function(){
			$(this).attr("disabled", true);
			if ( $(this).attr("title") == template ) {
				$(this).attr("disabled", false);
			}
		});
	});	
	
}
 
// change et sauve la nouvelle CSS
function changeCssSheme(layout){
	loadCss(layout);	
	saveConfig('userprefs_layout', layout);	
	
	if ($.browser.msie6) {
		$("body").hide().show();	// vive la bidouille....
	}
}



function spokenLangChange(){
	var myVal = $(this).val();
	var test = ($("#spoken-lang-profil input[@value="+myVal+"]").attr("checked") != null) ? true : false;
	
	if ( test == false) {
		$("#spoken-lang-profil input[@value="+myVal+"]").parents('label').extHighlight(1000);
		$("#spoken-lang-profil input[@value="+myVal+"]").attr("checked", "true");
		
	}

}

function usingLangSite(){
	var myVal = $(this).val();	
	var val = $("#spokenLanguage").val();
	if (myVal == val) $(this).attr("checked", "true");
}

var gNabcastCat = new Object;
function nabCastLang_init(){

	var nabcastLang = $("select[@name=nabcast_lang]");	
	var nabcastCateg = $("#nabcastCat");

	// on sauve en global.
	// a cause de IE, le select doit etre reconstruit completement en fonction du changement de langue
	// au lieu de juste mettre les options en display:none; ...
	//gNabcastCat = $(nabcastCateg).html();
	$("option", nabcastCateg).each(function(){
		var t = $(this).text();
		var v = $(this).attr('value');
		var l = $(this).attr('class');
		var s = $(this).attr('selected') ? true : false;
		if (v!=null) {
			gNabcastCat[v]=new Object;
			gNabcastCat[v]['text'] = t;
			gNabcastCat[v]['lang'] = l;
			gNabcastCat[v]['selected'] = s;
		}
	});

	nabCastLang_switch();
	
	$(nabcastLang).change( function(){
		nabCastLang_switch();
		$("#categContener").Highlight(2000, gHColor);		
	});


}

function nabCastLang_switch(){
	var nabcastLang = $("select[@name=nabcast_lang]").val();
	var nabcastCateg = $("#nabcastCat");

	
	$("#nabcastCat option").remove();
	var isSelected = false;
	for (var i in gNabcastCat){
		var opt = gNabcastCat[i];

		if (opt['lang']=="lang_"+nabcastLang) {
			$("#nabcastCat").addOption(i, opt['text'], opt['selected']);
			isSelected = true;
		}
	}
	if (!isSelected) $("option", nabcastCateg).eq(0).attr("selected", true);
		
}

function goDispatch(where, dispatchField){
		if (dispatchField == null) dispatchField = "dispatch";
		$("#"+dispatchField).val(where);
		
		
		switch (where) {
			case "goGetAccountNabName":
				$("#numSerie").val("");
				$("#registerProfil").submit();
			break;
			case "goGetAccountMac":
				$("#pseudoId").val("");
				$("#registerProfil").submit();
			break;
		}
		
}



/* CHECK STATUS */
/* quand le lapin est pas encore reconnu, mais va pas tarder a l'etre.... */
var gCheckInterval;
function onlineStatusCheck(id){
	var url = "MyUserHasARabbit.do?dispatcher=isConfirmed&userId=";
	url += id;
	url = antiCacheUrl(url);
	 $.get(url, function(data){
	   if (data!="") data = $.trim(data);
		
		if (data=="true"){
			clearInterval(gCheckInterval);
			
			var func;
			
			if ($("#whereAreWe").val()=="endHelp") { // cas ou qu'on est dans le Wizard
				func = register.goRecap;
			} else { // cas ou qu'on est dans le profil en mode batard
				func = function(){redirect('myNablife.do');};
			}	
			
			customAlertN( msg_txt['rabbit_detected'] , "", func );
		}
		
	 });	
	
}
function startOnlineStatusCheck(id){
	gCheckInterval = window.setInterval("onlineStatusCheck("+id+")", 10000);
}


// action appell� par l'intermediaire de l'url
// myTerrier.do?action=whatToDo
// TODO : l'installer sur toutes les JSP principales.
function performAction(action){
	switch(action) {
		
		// on veut acceder aux informations CB de la partie MonAbonnement
		case "updateCB":
			mainTab_GoTab('MonAbonnement', 'myTerrierAbo.do?mode=2');
		break
		
		case "messages_TellFriend":
			mainTab_GoTab("TellFriends");
			
			// on appelle register.init en callback, car ce fragment provient du wizard d'enregistrement...
			divChangeUrl("contentTellFriends", "myRegisterFriends.do?dispatch=load&fromMessages=true", register.init );
			//$('#Envoyer a').trigger("click");
		break;
		
		// on arrive en g�n�ral ici quand on se logue depuis www...
		case "connect":
			mainTab_GoTab('Envoyer'); // par defaut
			tabMessageChangeUrl( 'Messages_texte' , '../action/myMessagesTTS.do','../action/myMessagesSendTTS.do');
		break;
		
	}
}

function actionRadio(){
	$("div.srv-player").block();
	$("#srvWebRadioFreePlayer").ajaxSubmit(function(data){
		var ok;
		if (data==null) {
			ok = false;
		} else {
			ok = $.trim(data);
		}
		
		$("div.srv-player").unblock();
		
		var disp = $("#dispatchPlayer").val();
		var msg="";
		
		if (disp == "playNow") msg = msg_txt['play_radio'];	//"Votre lapin va bient�t vous jouer votre radio !";
		else msg = msg_txt['stop_radio'];	//"Votre lapin va s'arr�ter de jouer la radio dans quelques instants";
		
		if (ok) {
			$.msgPopupOverlay (msg);
		} else {
			$.msgPopupOverlay (msg_txt['error_radio'], -1); //"Une erreur c'est produite"
		}
	});
	return false;
}

function blockUI(state, text, element){
		if (state==null) {
			state = "show";
		}
		
		if (state== "show") {
			if (text==null || element==null) return;
			$(element).block('<div id="msgOverlayBecomeFull">'+text+'</div>',  { border: '1px solid #666', padding: '15px', width: '50%', left:'0' } );
			$("#msgOverlayBecomeFull").parent("div").css("left", "25%");
			$("#msgOverlayBecomeFull").parent("div").css("top", "-100px");
			$("#msgOverlayBecomeFull p").css("font-face", "Trebuchet MS");
			$("#msgOverlayBecomeFull p").css("font-size", "16px");			
		} else {
			$("div.mainTabBody").unblock();
		}
}

function getNewMessages(){

	$.get(antiCacheUrl("myNbReceivedMessages.do"), 
	function(data){ 
		
		if (data!=null) {
			data = $.trim(data);
			if (data.charAt(0)=="[") {
				data = data.substr(1, data.length -2);
			}
		}
		
		var msgNb = data;
		var msgNbStr = "["+data+"]";
		//$("#nbMessagesReceivedHeader").html("["+data+"]");
		
		 if (msgNb>0) {
		 	$("#nbMessagesReceivedHeader").html(msgNbStr);
		 	$("#nbMessagesReceived").html(msgNbStr);
		 	
		 	$("#nbMessagesReceivedHeader").parents("li").show();
		 	$("#nbMessagesReceived").show();
		 } else {
		 	$("#nbMessagesReceivedHeader").parents("li").hide();
		 	$("#nbMessagesReceived").hide();
		 	
		 }
	});

}

/*
 * Active / desactive le menu du haut
 */
function mainMenuSwitch(toState) {
    if (toState == "hide") {
        if (!$.browser.msie)
            $("#mainBigMenu ul, #loginBox:visible").fadeTo(1000, 0.3);    


        $("#mainBigMenu a, #loginBox:visible a").each(function(){
            $(this).click(function(){return false;});
            $(this).css("cursor", "default");
        });
        
    } else {
    	
        if (!$.browser.msie6)
            $("#mainBigMenu ul, #loginBox:visible").fadeTo(1000, 1.0);    

        $("#mainBigMenu a, #loginBox:visible a").each(function(){
            $(this).unbind("click");
            $(this).css("cursor", "");
            
        });   
             
    }
}

function popupFromPage(what, print) {
	//$("div.reg-help-more").show(); popupFromPage("#contentCreerCompte", true);
	var content = $(what).html();

	var t = '<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">';
	t+='<html xmlns="http://www.w3.org/1999/xhtml">';
	t+='<head>';
	t+='<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />';
	t+='<title></title>';
	t+='<link href="../include_css/mynabaztag.css?v=1.3" rel="stylesheet" type="text/css" />';
	t+='</head>';
	t+='<body style="display:block; width:750px; background-color:white;">';
	
	t+=content;


			
	t+='</body>';
	t+='</html>';
	
	var f = window.open();
	f.document.write(t);
	
	if (print){
		f.window.print();
	}		
}

function srvTableInit() {
	$("table.table-serv-conf tr").hover(
		function(){
			$("th", this).addClass("important-notice");
		},
		function(){
			$("th", this).removeClass("important-notice");		
		}
	);
}


tools = function() {

    return {
		
		init_simpleAjaxLink: function(callback){
			$("a.bSimpleAjaxLink").unbind("click");
			
			$("a.bSimpleAjaxLink").bind("click", function(){
				var t = $(this).attr("target");
				var u = $(this).attr("href");
				
				divChangeUrl(t, u, callback);
				
				return false;
			});
		},
		
		init_collapseLink: function(){
			$("a.bCollapseLink").unbind("click");
			
			$("a.bCollapseLink").bind("click", function(){
				var t = $(this).attr("target");
				var u = $(this).attr("href");
				
				// si on a mis la classe pour avoir la fleche
				$(this).toggleOpenClose();
				
				$(t).slideToggle("fast");
				
				return false;
			});
		}
				
    }
}();
	
msgHandling = function(){
	return {
			
		errorMsgShowDisplay: function(msg, form){
			var colors = {error:"FCBBBA", message:"FFFBB"};
			var currColor = colors.message;
			var finalTime = 4000; 

			if (msg==null || msg=="") return;
			
			// on supprime les error-indicator
			$("input.error-indicator, div.error-indicator, textarea.error-indicator, select.error-indicator").removeClass("error-indicator");

			
			if (form==null) form="";
			else form+=" ";
			
			var finalMsg = "";
			var firstOnePos= null;		
			
							
			// si c'est pas un tableau, on le transform en tableau quand meme (avec une seul row)
			if ( !isArray(msg) ) { // pas un tableau
				var t = msg;
				msg = new Array;
				msg[0] = t;	
			}
			
			finalMsg = "<ul>";
			for (var a=0; a<msg.length; a++) {
				var current =  msg[a];
				var target;
				
				if (current.type=="error") {
					currColor = colors.error;
					finalTime = 0;
				}
				 
				if (current.name != null && current.name !="") {
					target = $(form + "[@name="+(current.name)+"]");
					if (current.name.charAt(0) == "#") target = $(form + current.name); // si ya un # devant on prend l'object avec cette ID
					
					$(target).addClass("error-indicator");
					
					if (firstOnePos == null) {
						firstOnePos = $(target).offset().top - 150; 
					}
				}
				
				finalMsg += "<li>"+ current.text +"</li>";

					
			}			
			
			finalMsg += "</ul>";
			
			
			
			setTimeout(function(){
				if (firstOnePos != null) $(window).scrollTop( firstOnePos );
				$.msgPopupOverlay( finalMsg, finalTime, currColor );
			}, 200);

		},
		
		errorMsgShow: function(){
			var msg = new Array;
			var cc = 0;			
			$("ul.general-msg li").each(function(){ //[@class*='error']
				msg[cc] = {text:"", name:"", type:"message"};
				
				if ( $(this).is(".error")) { // c'est une erreur
					msg[cc].type = "error";
				}
				
				msg[cc].text =  $.trim($(this).html());
				
				// l'erreur s'applique a ce champs (->NAME)
				var rel;
				if ($(this).attr("id")!=null) {
					rel = ($(this).attr("id")).split("-");
					rel= rel[1];
					
					msg[cc].name = rel;
				}
				
				cc++;
				
			});
			
			msgHandling.errorMsgShowDisplay( msg );

			$("ul.general-msg").remove();
			
		},
		
		simpleMsgShow: function(msg){
			var directMsg = false;
			
			if (msg!="" && msg!=undefined) {
				directMsg = true;	
			} else {
				msg="";
			}
			
			var delay=4000;
			
			if (directMsg) {
				$.msgPopupOverlay( msg, delay );
				return;
			}
			
			$("ul.general-msg li[@class!='error']").each(function(){
				msg+=$(this).html();
				msg+="<br />";
				if ( $(this).is(".persistent")) {
					delay=0;
				}
			});	
			
			if (msg!="") $.msgPopupOverlay( msg, delay );
			
			$("ul.general-msg li[@class='error']").each(function(){
				
				msg = $(this).html();
				
				
				var center = "";

				customAlertN(msg);

			});				
			
			$("ul.general-msg").remove();
		}
	}
}();	