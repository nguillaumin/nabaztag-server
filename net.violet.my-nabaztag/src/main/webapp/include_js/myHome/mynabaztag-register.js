/**
 * @author nicolas
 */
 
/* ************************************************************* */

var DuppRemoveLine = function(){
	$(this).parents("tr.line-duplicate").remove();
	
	if ( $("tr.line-duplicate").length<10 ) {
		$("a.bt_AddFoyer").show();		
		return;
	}

};

var DuppAddLine = function(){
	var error = false;
	var foyerParent = $("tr.line-duplicate:last");

	// on test si un champs est vide (et on return si telle est le cas)
	$( "input, select", foyerParent ).not("[@type=hidden][@type=button]").each(function(){
		
	   if ( $(this).val() =="" ) {
			error=true;
	   }
	});

	
		
	// on clone le div que l'on veut
	var p = $("tr.line-duplicate:last").clone();
	
	// on reset les champs
	$("select, input[@type!=button]", p).val("");
	$("span", p).remove();
	$("a.bt_Remove", p).show();
	$("a.bt_Remove", p).bind("click", DuppRemoveLine);
	
	// on  rajoute l'action a ce bouton
	if ( $("tr.line-duplicate").length < 10 - 1 ){
		
	} else {
		$(this).hide();
	}			

	// on affiche la ligne
	$(p).appendTo("#line-duplicate-contener table");
};

register = function() {
	var historyStarted = false;
	var userObject	= "nabaztag";
	var cdlRegister = "";
	var flashSize	= "200"; 
	var savedPseudo = "";
	
	return {
		initRecupSerialForm: function(){
			msgHandling.errorMsgShow();
			
			// on hook le submit sur le A qui va bien
			$("#recupForm a.form-submit").click(function(){
				//$("#recoverAccount").submit();
				regRecoverValidate();
				return false;
			});
			
			// on active le back
			$("#backToMainForm").click(function(){
				$("#recupForm").html("");
				$("#bigMainForm form").unblock();
				$("#bigMainForm").show();
				$("#pseudoId").val(register.savedPseudo);
				$("#dispatch").val("insertUser");
				return false;
			});			
		},
		
		recupSerial: function(){
			
			
			register.savedPseudo = $("#pseudoId").val();
			
			$('#floatingMessage').remove();
			$("#pseudoId").val("");
			
			var opt = {
				target: "#recupForm",
				beforeSubmit: 	beforeFormAction,
				success: 		function(){
									// on cache le form principal
									$("#bigMainForm").hide();
									

									
									register.initRecupSerialForm();
													
								}
			};
			
			$("#dispatch").val("goGetAccountMac");
			
			$("#registerProfil").ajaxSubmit(opt);
				
			return false;			
			
		},
		
		goStart: function(object){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			divChangeUrl("contentCreerCompte", "registerMain.do?dispatch=startUp", function(){register.init(object)});			
		},
				
		goMainReg: function(object){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			divChangeUrl("contentCreerCompte", "registerMain.do?dispatch=insertUser", function(){register.init(object)});			
		},
		
		
		goVieCommune: function(object){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			divChangeUrl("contentCreerCompte", "myRegisterWizard.do?dispatch=load", function(){register.init(object)});	
		},
		
		goPreferences: function( skip, object ){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			if (skip == null) skip=false;
			
			if (skip==true) {
				// on passe toujours une liste avec au moins les infos.
				divChangeUrl("contentCreerCompte", "myRegisterWizard.do?dispatch=skip&listInterets=2", function(){register.init(object)});	
			} else {
				divChangeUrl("contentCreerCompte", "myRegisterInfo.do?dispatch=load", function(){register.init(object)});	
			}		
			

					
		},		
		
		goMusicUpload: function(object){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			divChangeUrl("contentCreerCompte", "myRegisterMp3.do", function(){register.init(object)});	
		},	
		
		goFriends: function(object){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			divChangeUrl("contentCreerCompte", "myRegisterFriends.do?dispatch=load", function(){register.init(object)});	
		},
		
		goMyself: function(object, skip){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			if (skip) {
				divChangeUrl("contentCreerCompte", "myRegisterProfil.do?dispatch=skip", function(){register.init(object)});
			} else{
				divChangeUrl("contentCreerCompte", "myRegisterProfil.do?dispatch=load", function(){register.init(object)});
			}	
		},
		
		goRecap: function(object){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			divChangeUrl("contentCreerCompte", "myRegisterRecap.do?dispatch=load", function(){register.init(object)});	
		},	
		
		goDeathTunel: function(){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();

			divChangeUrl("contentCreerCompte", "../include_jsp/myRegister/static_rabbitPlugHelp.jsp?macAddress=eeeeeeeeeee3&rabbName", function(){register.init()});					
		},
				
		goEndHelp: function(object){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			divChangeUrl("contentCreerCompte", "myRegisterHelp.do?dispatch=load", function(){register.init(object)});	
		},			
		
		goVraiFinFinal: function(){
			$(".contentMainTab").hide();
			$("#contentCreerCompte").show();
			divChangeUrl("contentCreerCompte", "../include_jsp/myRegister/static_wizard_last_real_fnal_end.jsp", function(){register.init()});					
		},
		
		init_rabbit: function(object){
			// init du flash
			var so = new SWFObject('../include_flash/CDLEditor.swf', "flashNab", '270', '270', '7', null, true);
			if ( $("#nabInscriptionFlash").length > 0) {
				so.addVariable("cdll", cdlRegister);
				so.addParam("wmode", "transparent");
				if (!so.write("nabInscriptionFlash")) {
					var container = document.getElementById("nabInscriptionFlash");
					if (container) container.innerHTML = so.getSWFHTML();
				}
			}
		}, 

		// param: object (nabaztag ou none pour l'instant)
		// param: forceInit, pour forcer le passage dans un des init. Ie a l'air de mal g�rer un truc, parfois il faut donc lui passer en dure
		init : function(object, forceInit){
			//console.debug("Init Register");
			goToTheTopOfThePage();
			
			// par d�faut on cache le selecteur de langue
			$("#spokenLanguage").hide();
			

			
			// on enregistre les actions des pseudos bouton submits
			$("a.form-submit").click(function(){
				$("#contentCreerCompte form").submit();
			});
			
			// on note si on va enregistrer un lapin ou pas
			//console.debug(register.userObject);
			register.userObject = (object!=null) ? object : register.userObject; 	
			if (register.userObject==null || register.userObject=="") register.userObject = "nabaztag";
			
			// on affiche que si on a un lapin a enregistrer (bloc a gauche)
			if (register.userObject == "nabaztag") {
				$("#bloc-generic-register").show();
			} else {
				$("#bloc-generic-register").remove();	// peut etre un peu violent a l'usage...
			}
		
			// on vire toutes les actions des boutons et des forms
			$("#contentCreerCompte input[@id^=bt_]").unbind();
			$("#contentCreerCompte form, #contentCreerCompte select, #contentCreerCompte input").unbind();

			$("#spokenLanguage").unbind();
			$("#spokenLanguage").change(function(){
				var l = $(this).val();
				
				var u = Url.argReplace("langUser", l);
				
				redirect(u);
			});
			
			register.init_rabbit();
			
			
			// on supprime l'action qui check si le lapin est online
			if (gCheckInterval!=null) {
				clearInterval(gCheckInterval);
				gCheckInterval = null;
			}

			// chooser de lapin
			$("ul.radio-chooser").rabbitChooser();
			
			// heures s'il y en a
			$("#contentCreerCompte input.hourPicker").hourSelect_Init( true, false );
			
			// active les tooltips
			$("textarea.formToolTip, input.formToolTip, select.formToolTip").formToolTip();

			// on met en valeur ou-kon-en-est dans les �tapes
			var where = $("#whereAreWe").val();
			if (forceInit!=null) where = forceInit;
			var whereFunc  = null;


			// on cache AIDE dans le "menu" de gauche.
			$("#bloc-generic-register ul li").eq(2).hide();
			
			// on cache la derniere entr�e "troubleshooting"
			$("#bloc-generic-register ul li").eq(8).hide();
						

			$("#bloc-generic-register ul li").removeClass("reg-weAreHere");
			$("#bloc-generic-register ul li").removeClass("reg-weAreNotHere");
			

			var img = $("<img class='wizard-img-indic' src='../include_img/layout/wizard_progress_bar_rabbit.png' />");

			$("img.wizard-img-indic").remove();
			$("#bloc-generic-register ul li:first").prepend( img );

			// si le block html est pas charg�, on attend un peu pour rajouter le lapin...
			// en esperant que le temps suffira....
			if (($.browser.msie && ie7) || ( !($.browser.msie)) && where!="vrai_fin_finale") {
				
				if ($("img.wizard-img-indic").length == 0) {
					setTimeout(function(){
						$("#bloc-generic-register ul li:first").prepend( img );
					},1000);
				}
			}	

			// on vire les �ventuels messages qui trainent.
			$("#floatingMessage").remove();
			
			switch(where) {
				case "start":

							
					$("#spokenLanguage").show(); // selecteur de langue
					$("#bloc-generic-register ul li").eq(0).addClass("reg-weAreHere");
					
					// Page d'introduction *********************************
					// bouton suivant, premiere page
					$("#bt_RegisterGoMain").bind("click", function(){
						var myParent = $(this).parents("div.contentMainTab");
						$(myParent).block();
						$(myParent).load( "registerMain.do", {dispatch: 'insertUser', object: register.userObject}, function(){
							//$("#leftcontent").prepend("<div class='left-block' style='border:none; margin-top:15px;'><h1>&nbsp;</h1><div class='block-content' id='nabInscriptionFlash'></div></div>");
							register.init();	
						});
					});
								
				break;
				
				case "main":
					
					// on cache le menu principal
					mainMenuSwitch("hide");
					$("#mainLogo a").click(function(){
						customConfirmN( msg_txt['abort_wizard'], "&nbsp;", function(){ redirect("myNablife.do") });
						return false;
					});
									
					$("#spokenLanguage").show(); // selecteur de langue
				
					// Page "connexion" animer Mon lapin *********************************
					$("#bloc-generic-register ul li").eq(1).addClass("reg-weAreHere");
					$("div.intro-cadre-content img").setOpacity(0.4);
					
					// on cache les villes pour l'instant
					$("#city-contener").hide();					
					
				// selecteur des pays
					var confLang = {
							"US" : {
								h24:0,
								zone:5
							},
							"DE" : {
								h24:1,
								zone:29
							},
							"ES" : {
								h24:1,
								zone:30
							},
							"FR" : {
								h24:1,
								zone:30
							},
							"IT" : {
								h24:1,
								zone:29
							},																				
							"UK" : {
								h24:1,
								zone:27
							},
							"BR" : {
								h24:1,
								zone:22
							}					
					};
									
					$("#registerProfil select[@name='countryId']").bind("change", function(){
						var selPays = $(this).val();
		
						$("#pays-contener").append("<img id='ville-load' align='absmiddle' style='border:none; margin:3px 0 0 5px;' alt='' height='20' width='20' src='../include_img/loading.gif'>");	
						
						$("#city-contener span.cities").load("registerMain.do", {dispatch: 'getListVille', countryId: selPays}, function(responseText){
							// on eleve le loader
							$("#ville-load").remove();
							
							var r = $.trim(responseText);
							if ( r.length > 0 ) {
								$("#city-contener").show();
								$("#city-contener select.formToolTip").formToolTip();
							} else {
								$("#city-contener").hide();
							}
						});

						// switch auto des langues et timeZone
						var c = confLang[selPays];
						
						// on a pas ce pays dans la liste....
						if (c!=null) {
							$("#timeZone").val(c.zone);
							// on update le 12/24 pour les heures de cet user. (ou� ok, on update une constante je sais c'est bizarre)
							nabaztag.constantes.H24 = c.h24;
						}
						
					
					});

					
	 				var opt = {
						target: "#contentCreerCompte",
						beforeSubmit: 	function(a, b ,c){
							
										if ( regMainValidate() ){
											beforeFormAction(a, b ,c );	
										} else {
											return false;
										}
	
									},	
						success: 		function(){
										register.init();
										
										if (register.userObject == "none") $("#rabbitBlock").remove();
										 
										msgHandling.errorMsgShow();
									}			
					};

					$("#registerProfil").ajaxForm(opt);

		
						
										
				break;
				
				case "help":
				case "death_tunnel" :
					if (gPlatform=="win" || gPlatform=="linux") {
						$("div.wifi-help-win").show();
					} else {
						$("div.wifi-help-mac").show();
					}
						

					// active les liens toggle
					$("a.more-help-link").bind("click", function(){
						var target = $(this).parents("div").eq(0).find("div.reg-help-more");
						$(target).toggle();
						return false;
					});	
					
					$("a.plateforme-switch").bind("click", function(){
						$("div.wifi-help-win").toggle();
						$("div.wifi-help-mac").toggle();
						return false;
					});

					$("#bloc-generic-register ul li").eq(2).show();
					$("#bloc-generic-register ul li").eq(2).addClass("reg-weAreHere");
				break;
				
				case "endHelp":
					// active le lien impression
					$("a.print_all").bind("click", function(){
						$("div.reg-help-more").show();
						popupFromPage("#contentCreerCompte", true);
						$("div.reg-help-more").hide();
						return false;
					});

					// le lien on verra plus tard
					$("a.connect_later").bind("click", function(){
						register.goVraiFinFinal();
						return false;
					});

					// active les liens toggle
					$("a.more-help-link").bind("click", function(){
						var target = $(this).parents("li").find("div.reg-help-more");
						$(target).toggle();
						return false;
					});
					
					$("#bloc-generic-register ul li").eq(8).show();
					$("#bloc-generic-register ul li").eq(8).addClass("reg-weAreHere");	
					var so;
					var a = new Array();
					a[0] = "long,3,none,nomusic.mid,1,7,0,led,4,255,153,0,0,led,3,255,153,0,0,led,2,255,153,0,0,led,1,255,153,0,0,led,0,255,153,0,2,led,4,0,0,0,2,led,3,0,0,0,2,led,2,0,0,0,2,led,1,0,0,0,2,led,0,0,0,0,4,led,4,255,153,0,4,led,3,255,153,0,4,led,2,255,153,0,4,led,1,255,153,0,4,led,0,255,153,0,6,led,4,0,0,0,6,led,3,0,0,0,6,led,2,0,0,0,6,led,1,0,0,0,6,led,0,0,0,0,8,led,4,255,153,0,8,led,3,255,153,0,8,led,2,255,153,0,8,led,1,255,153,0,8,led,0,255,153,0,10,led,4,0,0,0,10,led,3,0,0,0,10,led,2,0,0,0,10,led,1,0,0,0,10,led,0,0,0,0,12,led,0,0,0,0,12,led,1,0,0,0,12,led,2,0,0,0,12,led,3,0,0,0,12,led,4,0,0,"; 					 
					a[1] = "long,3,none,nomusic.mid,0,7,0,led,4,255,204,0,0,led,3,255,204,0,0,led,2,255,204,0,0,led,1,255,153,0,0,led,0,255,153,0,1,led,1,0,0,0,1,led,0,0,0,0,2,led,1,255,153,0,2,led,0,255,153,0,3,led,1,0,0,0,3,led,0,0,0,0,4,led,1,255,153,0,4,led,0,255,153,0,5,led,1,0,0,0,5,led,0,0,0,0,6,led,1,255,153,0,6,led,0,255,153,0,7,led,1,0,0,0,7,led,0,0,0,0,8,led,2,0,0,0,8,led,1,0,238,0,8,led,0,255,153,0,9,led,0,0,0,0,9,led,2,255,153,0,10,led,2,0,0,0,10,led,0,255,153,0,11,led,0,0,0,0,11,led,2,255,153,0,12,led,2,0,0,0,12,led,0,255,153,0,13,led,0,0,0,0,13,led,2,255,153,0,14,led,2,0,0,0,14,led,0,255,153,0,15,led,0,0,0,0,15,led,2,255,153,0,16";					
					a[2] = "long,3,none,nomusic.mid,0,7,0,led,4,255,204,0,0,led,3,255,204,0,0,led,2,255,204,0,0,led,1,255,153,0,0,led,0,255,153,0,1,led,1,0,0,0,1,led,0,0,0,0,2,led,1,255,153,0,2,led,0,255,153,0,3,led,1,0,0,0,3,led,0,0,0,0,4,led,1,255,153,0,4,led,0,255,153,0,5,led,1,0,0,0,5,led,0,0,0,0,6,led,1,255,153,0,6,led,0,255,153,0,7,led,1,0,0,0,7,led,0,0,0,0,8,led,2,0,0,0,8,led,1,0,238,0,8,led,0,255,153,0,9,led,0,0,0,0,9,led,2,255,153,0,10,led,2,0,0,0,10,led,0,255,153,0,11,led,0,0,0,0,11,led,2,255,153,0,12,led,2,0,0,0,12,led,0,255,153,0,13,led,0,0,0,0,13,led,2,255,153,0,14,led,2,0,0,0,14,led,0,255,153,0,15,led,0,0,0,0,15,led,2,255,153,0,16,led,2,0,0,0,16,led,0,255,153,0,17,led,0,0,0,0,17,led,2,255,153,0,18,led,2,0,0,0,18,led,0,255,153,0,19,led,3,0,0,0,19,led,0,0,0,0,19,led,2,0,238,0,20,led,3,255,153,0,20,led,0,255,153,0,21,led,3,0,0,0,21,led,0,0,0,0,22,led,3,255,153,0,22,led,0,255,153,0,23,led,3,0,0,0,23";
					a[3] = "long,3,none,nomusic.mid,0,7,0,led,4,255,204,0,0,led,3,255,204,0,0,led,2,255,204,0,0,led,1,255,153,0,0,led,0,255,153,0,1,led,1,0,0,0,1,led,0,0,0,0,2,led,1,255,153,0,2,led,0,255,153,0,3,led,1,0,0,0,3,led,0,0,0,0,4,led,1,255,153,0,4,led,0,255,153,0,5,led,1,0,0,0,5,led,0,0,0,0,6,led,1,255,153,0,6,led,0,255,153,0,7,led,1,0,0,0,7,led,0,0,0,0,8,led,2,0,0,0,8,led,1,0,238,0,8,led,0,255,153,0,9,led,0,0,0,0,9,led,2,255,153,0,10,led,2,0,0,0,10,led,0,255,153,0,11,led,0,0,0,0,11,led,2,255,153,0,12,led,2,0,0,0,12,led,0,255,153,0,13,led,0,0,0,0,13,led,2,255,153,0,14,led,2,0,0,0,14,led,0,255,153,0,15,led,0,0,0,0,15,led,2,255,153,0,16,led,2,0,0,0,16,led,0,255,153,0,17,led,0,0,0,0,17,led,2,255,153,0,18,led,2,0,0,0,18,led,0,255,153,0,19,led,3,0,0,0,19,led,0,0,0,0,19,led,2,0,238,0,20,led,3,255,153,0,20,led,0,255,153,0,21,led,3,0,0,0,21,led,0,0,0,0,22,led,3,255,153,0,22,led,0,255,153,0,23,led,3,0,0,0,23,led,0,0,0,0,24,led,3,255,153,0,24,led,0,255,153,0,25,led,3,0,0,0,25,led,0,0,0,0,26,led,4,0,0,0,26,led,3,0,238,0,26,led,0,255,153,0,27,led,0,0,0,0,27,led,4,255,153,0,28,led,4,0,0,0,28,led,0,255,153,0,29,led,0,0,0,0,29,led,4,255,153,0,30,led,4,0,0,0,30,led,0,255,153,0,31,led,0,0,0,0,31,led,4,255,153,0,32,led,4,0,0,0,32,led,0,255,153,0,33,led,0,0,0,0,33,led,4,255,153,0,34,led,4,0,0,0,34,led,0,255,153,0,35,led,0,0,0,0,35,led,4,255,153,0,36,led,4,0,0,0,36,led,0,255,153,0,37,led,0,0,0,0,37,led,4,255,153,0,38,led,4,0,0,0,38,led,0,255,153,0,39,led,0,0,0,0,39,led,4,255,153,0,40,led,4,0,0,0,40";
					a[4] = "long,3,none,nomusic.mid,1,7,0,led,0,255,255,255,1,led,0,255,230,233,2,led,0,255,204,219,3,led,0,255,179,211,4,led,0,255,153,210,5,led,0,255,128,217,6,led,0,255,102,231,7,led,0,255,76,252,8,led,0,231,51,255,9,led,0,196,25,255,10,led,0,153,0,255,11,led,0,200,28,255,12,led,0,238,56,255,13,led,0,255,85,244,14,led,0,255,113,224,15,led,0,255,142,213,16,led,0,255,170,210,17,led,0,255,199,216,18,led,0,255,227,231,19,led,0,255,255,255,20,led,0,0,0,0,20,led,1,0,0,0,20,led,2,0,0,0,20,led,3,0,0,0,20,led,4,0,0,0";

					for (i = 0; i<=4; i++) {
						so = new SWFObject('../include_flash/CDLEditor.swf', "flashNab", '160', '160', '7', null, true);
						so.addVariable("cdll", a[i]);
						so.addParam("wmode", "transparent");
						if (!so.write("helpNab" + (i+1))) {
							var container = document.getElementById("helpNab" + (i+1));
							if (container) container.innerHTML = so.getSWFHTML();
						}
					}										
					
					
					startOnlineStatusCheck($("#userId").val());
					
					$('#idFormMAC').ajaxForm({
						target: 		"#contentCreerCompte",
						beforeSubmit: 	beforeFormAction,
						success: 		function(){
											register.init();
											msgHandling.simpleMsgShow();
										}			
					});						

					var opt = {
						beforeSubmit: 	function(a, b ,c){
											var error = false;
											if ( checkemail( $("input[@name=email]").val() ) == false) {
												msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_invalid_email'], name:"email"} );
												return false;
											}
											
											beforeFormAction(a, b ,c );
											
										},	
	
						success: 		function(){
											$('#idFormContact').unblock();
											msgHandling.simpleMsgShow( $("div.result-contact-form").html() );
											$("textarea[@name=message]").val("");
										}			
					};

					$('#idFormContact').submit(function() {
						$("#myMacAddress").val( $("#numSerie").val() ); 	// on copie la mac du form du dessus.....
				        $(this).ajaxSubmit(opt); 
				        return false; 
		    		});

					
									
				break;
												
				case "vie_commune":
					$("#bloc-generic-register ul li").eq(3).addClass("reg-weAreHere");
					whereFunc = function(){register.goPreferences(true)};
					$("div.intro-cadre-content img").setOpacity(0.5);					


					// Page "D�buter la vie commune" *************************************************************************************************************
					$("a.bt_AddFoyer").bind("click", DuppAddLine);
					$("a.bt_Remove:first").hide();
					$("a.bt_Remove").click(DuppRemoveLine);
					
					opt = {
						target: 		"#contentCreerCompte",
						beforeSubmit: 	function(a, b ,c){
							
		
											//var error = false;
	
											var msg = "";		
											var errorDate= false;
											var errorSexe= false;
											var errorName= false;
											
											var errorVeille = false;
											
											// on check les heures de couch�/lev�, il faut qu'elles soient remplies
											$("#form_VieCommune [name~=leve], #form_VieCommune [name~=couche]").each(function(){
											    if ( $(this).val()=="") errorVeille = true;
											});
											
												
											
											$("tr.line-duplicate").each(function(){
												if ( $("input[@name=who]", this).val() != "" ) {
													errorDate = ( $("input[@name=date]", this).val() == "" );
												}
											});
											
											if (errorVeille) {
												msg += "&bull;" + msg_txt["register_bad_sleeping_hours"]+"<br />";
											}
									
											if (errorDate) {
												error = true;
												msg += "&bull;" + msg_txt["register_bad_foyer_date"]+"<br />";
											}
											
													
											if ( msg!="" ){
												customAlertN(msg);
												return false;	
											} else {
												beforeFormAction(a, b ,c );
											}
											
										},	
						success: 		function(){
											register.init();
										}			
					};

					$('#form_VieCommune').submit(function() { 

						$("tr.line-duplicate").each(function(){
							// Check de la validit� de la date
							var myDate = $("select[@name=annee]", this).val() + "-" + $("select[@name=mois]", this).val() + "-" + $("select[@name=jour]", this).val();  
							if ( !checkDate(myDate) ){
								// mauvaise date on fait rien
							} else {
								$("input[@name=date]", this).val(myDate);
							}
						});			
						
				        $(this).ajaxSubmit(opt); 
				        return false; 
		    		});
					

				break;

				case "preferences":
					$("#bloc-generic-register ul li").eq(4).addClass("reg-weAreHere");
					whereFunc = function(){ register.goMusicUpload(register.userObject) };
										
					$("div.intro-cadre-content img").setOpacity(0.6);					
					
					$("input[@name=authorisation]").click(function(){
						$("input[@name=notification]").attr("checked", false);
					});
					
					$("input[@name=notification]").click(function(){
						$("input[@name=authorisation]").attr("checked", false);
					});
					
					
					// Page "Prefs et manies" ***************************************************************************************************************
					$("#spoken-lang-profil .selected input").click( function(){ return false;});
					$("#spoken-lang-profil .selected input").attr("disabled", true);
					
					$('#form_Prefs').ajaxForm({
						target: 		"#contentCreerCompte",
						beforeSubmit: 	beforeFormAction,
						success: 		function(){
											register.init();
		
										}			
					});	
				break;

				case "music_upload":
					$("#bloc-generic-register ul li").eq(5).addClass("reg-weAreHere");
					whereFunc = function(){ register.goFriends(register.userObject) };
					
					$("div.intro-cadre-content img").setOpacity(0.7);					

					// Page "MUSIQUE" ************************************************************************************************************
		 			var opt = {
						target: 		"#contentCreerCompte",
						beforeSubmit: 	function(a, b ,c){
											var ext = $("#musicFile").val().substr($("#musicFile").val().length-4, 4).toUpperCase();
		
											// check si le nom est vide
											if ( fieldIsEmpty("musicName")) {
												customAlertN( msg_txt['mp3_invalidName'] );
												return false;		
											}
											
											if (fieldIsEmpty("musicFile")) { // on a pas choisi de fichier
												customAlertN(msg_txt['mp3_invalidFile']	);
												return false;
											}
											
											if (ext != ".MP3"){
												customAlertN(msg_txt['mp3_notAnMp3'] );
												return false;										
											}
											
											beforeFormAction(a, b ,c );
		
											return true;
											
										},	
						success: 		function(){
											msgHandling.simpleMsgShow();
											register.init(object, "music_upload");
										}			
					};
		 		
		
					$("#form-uploadMp3").ajaxForm(opt);	
					
					$("#bt_goFriends").bind("click", function(){
						var myParent = $(this).parents("div.contentMainTab");
						$(myParent).block(); 
						$(myParent).load( "myRegisterFriends.do", {"dispatch": 'load'}, function(){
							register.init();
						});
					});
					
					$("ul.reg-mp3-list li a.LdeleteIcone").click(function(){
							var url = $(this).attr("href");
							var obj = this;
							
							// message de confirmage
							customConfirmN( msg_txt["confirm_delete"], null, function(){
								$(obj).parents("li").destroyMe();
								$.get(url);	
							});
							
							return false;				
					});
			
								
				break;

				case "friends":
					$("#bloc-generic-register ul li").eq(6).addClass("reg-weAreHere");
					whereFunc = function(){ register.goMyself(register.userObject, true) };
					
					$("div.intro-cadre-content img").setOpacity(0.8);

					// page AddFriends ************************************************************************************************************************
					opt = {
						target: 		"#contentCreerCompte",
						beforeSubmit: 	function(a, b ,c){
											//var reg=new RegExp("[ ,;]+");
											//var tableauMail=chaine.split(reg);
											
											//return false;
											
											beforeFormAction(a, b ,c );
											return true;
											
										},	
						success: 		function(){
											register.init();
											msgHandling.simpleMsgShow();
										}
					};
					
					// ce module est aussi utilis� dans messages....
					if ( $("#contentTellFriends").length > 0 ) {
						opt.target = "#contentTellFriends";
						var urlAction = $("form[@name=myRegisterFriendsForm]").attr("action");
						
						// on modifie a la vol�e et a l'arrache le action du form...
						// pour ne PAS afficher les boutons du wizard (pr�c�dent / suivant) en cas d'appel en dehors du wizard						
						$("form[@name=myRegisterFriendsForm]").attr("action", Url.argReplace("fromMessages", "true", urlAction));
					}
					
					$('#form-addFriends').submit(function() {
						var reg=new RegExp("[ ,;]+");
						var tableauMail=$("#myListOfFriends").val().split(reg);
						var badMails = "";
						
						if (tableauMail.length > 0 && $("#myListOfFriends").val().length>0){
							
							for (var i=0; i<tableauMail.length; i++){
								var m = tableauMail[i];
								var c = checkemail(m);
								if (c == false && $.trim(m)!=""){ // pas un bon email !
									badMails+="<strong>"+m+"</strong>&nbsp;&nbsp;&nbsp;&nbsp;";
								}
							}
							
						} else {
							customAlertN( msg_txt['register_error_friends_empty'] );
							return false;
						}
						
						if (badMails!="") {
							customAlertN( msg_txt['register_badEmailList']+"<br />" + badMails);
							return false;
						} else {
							
							$("input.myFriendmail").remove();
		
							for (var i=0; i<tableauMail.length; i++){
								var m = tableauMail[i];
								var newHidden = $("<input class='myFriendmail' type='hidden' name='address' value='"+m+"' />");
								
								
								$("#myListOfFriends").after(newHidden);
							}						
						}
															 
				
						$(this).ajaxSubmit(opt); 
						
				        return false; 
		    		});
								
					$("a.popup-preview").click(function(){
						window.open("myRegisterFriends.do?dispatch=previsualisation", "", "resizable=no, location=no, width=740, height=600, menubar=no, status=no, scrollbars=yes, menubar=no");
						
					});

				
				break;

				case "myself":
					$("#bloc-generic-register ul li").eq(7).addClass("reg-weAreHere");
					whereFunc = function(){ register.goMyself(register.userObject, true) };
					$("div.intro-cadre-content img").setOpacity(0.9);
					
					// page Myself ************************************************************************************************************************
					$("#form-myself").ajaxForm({
						target: 		"#contentCreerCompte",
						beforeSubmit: 	beforeFormAction,
						success: 		function(){ register.init(); }
					});	
						
				break;
				
				case "recap":
					$("#bloc-generic-register ul li").eq(9).addClass("reg-weAreHere");
					$("div.intro-cadre-content img").setOpacity(1.0);
					
					// page recap ************************************************************************************************************************
					opt = {
						beforeSubmit: 	function(a, b ,c){
											if ( !checkemail ($("input[@name=address]").val()) && ($("#mailCheckBox").attr("checked")) ) {
												customAlertN( msg_txt['reg_invalid_email']);
												return false;
											}

											beforeFormAction(a, b ,c );
											
											
										},	
						success: 		function(){
											redirect("myNablife.do");
										}			
					};
		
					$('#form-recap').submit(function() { 
				        $(this).ajaxSubmit(opt); 
				        return false; 
		    		});	
													
				break;
				
				case "vrai_fin_finale":

					$("#bloc-generic-register img.wizard-img-indic").remove();
					$("#bloc-generic-register li").removeClass();
					$("#bloc-generic-register li").addClass("reg-weAreNotHere");
					
					// on affiche le menu principal
					mainMenuSwitch("show");
					$("#mainLogo a").unbind("click");
								
				break;
			}
			
			var stop=false;
			$("#bloc-generic-register ul li").each(function(){
				if ($(this).is(".reg-weAreHere")) {
					// si pas IE6
					if (($.browser.msie && ie7) || ( !($.browser.msie)) ) {
						$(this).prepend( img );
					} else {
						//console.debug("bad browser");
					}
					stop=true;
				}
				//console.debug("here");
				if (!stop) $(this).addClass("reg-weAreNotHere");
			});	
			
			
			// on ajoute le lien sur skip
			if (whereFunc!=null) $("div.reg-back a").click(whereFunc);
			

			
		}
	}
}();
