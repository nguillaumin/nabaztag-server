// JavaScript Document

/**
* vide les deux colonnes de blocs
*/
function resetAllblocs(){
	// la class manual est utilisé pour un block gérer manuellement (exemple, le block sur nablife ou on peut se loguer)
	$("#leftcontent div.left-block").not(".manual").remove();
	$("#rightcontent div.right-block").not(".manual").remove();		
}

//	

/** 
* charge le bloc
* @param idDiv l'id du div contener
* @param urlPage l'url du bloc a charger. Si urlPage == null, on assume que le bloc est déjà chargÃ©, on le recharg
* 			 	grace au champ cachÃ© qui est normalement dedans
* @param fAfter fonction a executÃ© aprÃ¨s le chargement
*/
function blocLoad(idDiv, urlPage, fAfter){

	var div = document.getElementById(idDiv);
	var blocName = idDiv.split("-");
	blocName = blocName[1];

	// on récupere l'url si elle n'est pas spécifiée
	if(urlPage==null){
		urlPage = $("#"+idDiv).find(".blockUrl").attr("value");
	}
	
	// chargement du loading
	$(div).block("<div><img style='border:none;height:20px; width:20px; margin-top:65px;  display:inline;' src='../include_img/loading.gif'></div>", {padding:0, margin:0, top:0, left:0, width:'185px', height:'157px'});

	urlPageC = antiCacheUrl(urlPage);
	
	var oldBack = $(div).css("background-image");

	$(div).css("background-image", "none");
	$(div).css("background", "none");	
	
	var toggleMode = function(){
		$(".entry-full", div).toggle();
		$(".entry", div).toggle();
		$(".toggleMode", div).toggleClass("toggleMode-close");	
		$(".toggleMode", div).toggleClass("toggleMode-open");			
	}
	

	$(div).load(urlPageC, function(html, response){
										/*
										 * Suppression du block en cas de contenu vide.
										 */
										var content = $(div).find("div.block-content");
									    var a = $(content).html();
									    if (a!=null) {
									    	a = $.trim(a);
											if (a.length<50) { // on estime qu'il peut y avoir quand meme quelques chars a la con...
												var contenerElem =  $(this).parents("div").filter("div.left-block, div.right-block");
												$(div).hide();
											}
									    }										
										/* fin de suppression*/
										
										$(div).css("min-height", "1px");
										$(div).css("height", "1%");
										$(div).css("border", "none");
										$(div).css("background-image", oldBack);
										$(div).css("background", oldBack);
										
										//showFormWaitForResponse(idDiv, false);
										$(div).unblock();
										
								   		// gestion des erreurs
				  						if (response == "error") {LoaderErrorShow($(div), urlPage);	return;}

										// si le texte est trop gros, on augmente le header du bloc
										var o = $("h1", this);
										if (o!=null && $(o).html()!=null) {
											if ($.trim($(o).html()).length>26)	$(o).addClass("bigger");
										}
										
										// dans un hidden on colle l'url courante (qu'on pourra récup plus tard)
										$(this).prepend("<input type='hidden' class='blockUrl' value='"+urlPage+"' />");
										
										var p = strpos(blockExtendable, "|"+blocName+":");
										
										// ceci est un block avec un mode FULL
										if ( p >= 0 ) {
											var tmp = blockExtendable.substr(p+1, blockExtendable.length );
											tmp = tmp.split("|");
											tmp = tmp[0].split(":");
											var state = tmp[1];
											
											$(o).before("<a href='javascript:;' class='toggleMode toggleMode-close'><span>[-]</span></a>");		
										}
										
										$("a.toggleMode", div).click( toggleMode );
										if (state == "close") {
											toggleMode();
										}
										
										// si on donne une function a executer a la fin, on le fait....
										if (fAfter!=null) fAfter();
										

									
										
										
								  });
							  
}

function returnBlocksList(tab){
	return blocOrder[tab];
}

/**
* @name updateBlocs
* @desc rÃ©cupere l'onglet en cours, et grace a la config
* @desc affiche les blocs qu'il faut.
*********************/
function updateBlocs(){

	resetAllblocs();

	var type = blocOrder[currentTab];

	if (type == null) return;
	
	var tmp = type.split(":")
	var left = tmp[0].split(",");

	var tt			= "";
	var blocType	= "";
	var params		= "";
	var paramStr	= "";
	var blocFinalId = "";

	if (tmp[0].length > 0) {
		for (i=0; i<left.length; i++){
			paramStr	= "";	
			tt = left[i].split("@");
			blocType = $.trim(tt[0]);

			if (tt[1]!=null) {
				params = tt[1].split('^');
				//paramStr = "&";
				p = params[0].split(';');

					
				for (e=0; e<params.length; e++) {
					paramStr += "&param"+(e+1)+"="+params[e];
				}	
				
				blocFinalId = blocType;
				if (blocType == "generic") blocFinalId += "-"+p[0];
		
			} else {
				params = "";
				paramStr = "";
				
				blocFinalId = blocType;				
			}
			
			if (blocType.charAt(0)=="-") continue;
		
			$("#leftcontent div.contener").append('<div class="left-block" id="bloc-'+blocFinalId+'" ></div>');
			blocLoad("bloc-"+blocFinalId, "Bloc.do?action="+blocType+paramStr);
		}
	}
	
	if (tmp[1]!=null) {
		var right = tmp[1].split(",");
		
		for (i=0; i<right.length; i++){
			paramStr	= "";
			tt = right[i].split("@");
			blocType = $.trim(tt[0]);

			if (tt[1]!=null) {

				params = tt[1].split('^');
				//paramStr = "&";
				p = params[0].split(';');

					
				for (e=0; e<params.length; e++) {
					paramStr += "&param"+(e+1)+"="+params[e];
				}	
				
				blocFinalId = blocType;
				if (blocType == "generic") blocFinalId += "-"+p[0];
							
			} else {
				params = "";
				paramStr = "";
				
				blocFinalId = blocType;				
			}
			
			if (blocType.charAt(0)=="-") continue;
	
			$("#rightcontent div.contener").append('<div class="right-block" id="bloc-'+blocFinalId+'" ></div>');
			blocLoad("bloc-"+blocFinalId, "Bloc.do?action="+blocType+paramStr);
		}	
	}
}


/* ********************************************************************************************** */
/* MyNabcastasts bloc
/* ********************************************************************************************** */
function blocMyNabcast_onclick(id){
	var idNabcast = id;
	mainTab_GoTab('Publier', baseUrl+'?idNabcast='+idNabcast, false);	
}

function blocMyNabcast_Init(){

	baseUrl ="myNabaztalandCreate.do";

	$("#bloc-MyNabcast a").click(function(){
											var li = $(this).parent();
											tmp = $(li).attr("id").split("_");
											idNabcast = tmp[1];										  
										  	blocMyNabcast_onclick(idNabcast);
										  });
}

function blocMyNabcast_addItem(id, intitule, selected){
	$("#bloc-MyNabcast").show(); // au cas où !
	$("#bloc-MyNabcast ul").append("<li id='myNabcastsList_"+id+"'><a href='javascript:;'>"+intitule+"</a></li>");
	$("#myNabcastsList_"+id).click(function(){blocMyNabcast_onclick(id)});
	if (selected!=null){
		blocMyNabcast_Select(id);
	}
}

function blocMyNabcast_unSelectAll(){
	var li = $("#bloc-MyNabcast");
	// on deselect tous
	$(li).find("a").removeClass("selected");
	
}

function blocMyNabcast_Select(id){
	var li = $("#bloc-MyNabcast #myNabcastsList_"+id);

	
	blocMyNabcast_unSelectAll();
	
	// on selectionne la bonne ligne
	$(li).find("a").addClass("selected");
}

/* ********************************************************************************************** */
/* MyMyServices bloc
/* ********************************************************************************************** */

function blocMyServices_unSelectAll(){
	var div = $("#bloc-MyServices");
	// on deselect tous
	$("li", div).removeClass("selected");
}

// il faut donner ex: srvAir, srvMeteo
function blocMyServices_SelectByUrl(str){
	var li = $("#bloc-MyServices ul.list li input[@type=hidden][@value*='"+str+".do']").parents("li");
	$(li).addClass("selected");
}

function blocMyServices_Select(id){
	if ( $("#bloc-MyServices").length<1 ) return;	// on ne fait rien si le div en question n'est pas présent...
	
	// si on indique rien, on essaye de trouver sur la page les champs cachés qui vont bien
	if (id==null) {
		var i = $("#internalSrvId").val();
		var t = $("#internalTypeId").val();
		id = "id_"+i+"_"+t;
		//console.debug(id);
	}

	var li = $("#bloc-MyServices #"+id);
	
	// on deselect tous
	$("#bloc-MyServices li").removeClass("selected");

	// si id commence pas par "id_", on suppose qu'on a donner un bout d'url
	// pour selectionner le truc. on utilise donc une autre fonction
	if (id.substr(0, 3)!="id_") blocMyServices_SelectByUrl(id);
	
											
	// on selectionne la bonne ligne
	$(li).addClass("selected");

}




