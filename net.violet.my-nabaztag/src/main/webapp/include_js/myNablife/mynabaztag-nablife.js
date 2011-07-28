/**
 * @author nicolas
 */

	nablife = function($) {
		var selectedCategory		= 0;
		//var selectedService			= null;
		var selectedNabcastCategory = 0;
		var selectedNabcast			= 0;
		var loadedServiceName		= "";				
		var loadingService			= false;
		var contener 				= null;
	    return {
			
			getCurrentServiceName : function(){
				return loadedServiceName;
			},
			
			init: function(noreset){
				if (!noreset) {
					selectedNabcastCategory = 0;
					selectedNabcast			= 0;
				}
				

				contener = $("div.contentMainTab:visible")[0];

				// si on trouve pas ça, il y a un probleme, on arrete le script
				if ( $("div.categ-chooser", contener).length == 0) {
					return false;
				}
				
				nablife.initCategChooser();
				nablife.initSrvChooser();
				nablife.initLngChooser();				
				
				// tooltip sur les titres
				$('h3 span').Tooltip({
					delay: 500,
					track: false,
					showURL: false,
					extraClass:"notFixed"
				});
				
							
			
			},
			
			initCategChooser : function(){
				var o = $("div.categ-chooser select", contener);
				var blocked = ($.browser.msie6)  ? $(o).parents("div.menu") : $("div.mainTab");

				// on desactive la roulette sur le select pour notre ami IE
				$(o)[0].onmousewheel=function(){return false;};

				$(o)
					.unbind()
					.bind("change",
						function(){
							if (url=="") return false;
							var type = $(":selected",this).is(".service") ? "service" : "nabcast";
							var url = $(this).val();
							
							// on va charger dans un div ou un autre, en fonction de si c'est un nabcast ou pas.
							// a priori on pourrait tout charger au meme endroit, mais comme c'etait comme ça depuis le début
							// je garde pour ne pas avoir a changer trop de truc derriere
							var content = (type == "service") ? $("#contentServicesByCateg") : $("#contentNabaztaland");	

							$(blocked).block();
							
							$(content).load(url, function(){
								
								$("#contentAllServices").hide();
								$("#contentServicesByCateg").hide();
								$("#contentNabaztaland").hide();

								$(this).show();
								
								nablife.init();

								// on recupe l'id de category
								var m = new RegExp("idCateg=([0-9]+)").exec(url);
								
								if (type == "service") {
									selectedCategory = (m!=null) ? m[1] : 0;
									selectedNabcastCategory = 0;
								} else {
									selectedNabcastCategory = (m!=null) ? m[1] : 0;
									selectedCategory		= 0;
								}
								
								$(blocked).unblock();
							}); 

						}
				);
				

			},
						
			initLngChooser : function(){
				$("ul.langSelect a").each(function(){
					
					var blocked = ($.browser.msie6)  ? $(this).parents("div.menu") : $("div.main-layout");
					
					$(this).bind("click", function() {
						$(blocked).block();
						$(contener).load( $(this).attr("href"), function(){
							$(blocked).unblock();
							nablife.init();
						});
						return false;
					});

				});
			},
						
			initSrvChooser : function(){
				var srvHoverOn = function(){
					if (loadingService) return;
					$(this).css("cursor", "pointer");
					
					// j'ai un bug incompréhensible sur IE6 (décalage a l'affichage) donc on désactive ça
					if (!$.browser.msie6) $("h3", this).addClass("selected");
					
					$("div.text-status-holder strong", this).show();
											
					$("a.add-srv", this).addClass("selected");
				}
				
				var srvHoverOff = function(){
					if (loadingService) return;
					$("h3", this).removeClass("selected");
					
					$("div.text-status-holder strong", this).hide();
					
					$("a.add-srv", this).removeClass("selected");
				}

				// on regarde si on a les liens, signifiant que l'on peut activer les clics
				if ( $("div.list-services a.add-srv").length > 0 ) {
				
					$("div.list-services").each(function(){
						var l = $("a.add-srv", this);
						
						// on prepare le "status" a cote du bouton + 
						$("div.text-status-holder strong", this).hide();
						
						$(this).hover(srvHoverOn, srvHoverOff);
						$(this).bind("click", nablife.loadSrvPage);
					});
				}
			},
			
			returnToSrvList : function(){

				loadedServiceName = "";
				
				bloc.services.clearSelected();
				
				$("#contentSrvConfig").hide();
				
				var elem = null;
				
				if (selectedCategory>0 && selectedNabcastCategory==0) {
					elem = $("#contentServicesByCateg");
				} else if (selectedCategory==0 && selectedNabcastCategory>0) {
					elem = $("#contentNabaztaland");
				} else {
					elem = $("#contentAllServices");
				}
				
				var v = $.trim($(elem).html());
				
				// si on revient sur une page ou il n'y a rien, on charge la home de nablife.
				if (v.length < 20) {
					$(elem).load("myNablife.do", function(){
						$(elem).show();
						goToTheTopOfThePage();
						nablife.init();						
					});
				} else {
					$(elem).show();
					
					goToTheTopOfThePage();
				}
				
			},
			
			// on peut passer une url en parametre
			loadSrvPage : function(url, callback){
				
				if (loadingService) return -1;

				var href = "";
				var contener = null;
				var srvName="";
				
				// on a passé une url en param
				// cela veut dire qu'on va dans la page de config depuis la liste dans un bloc
				if( varType(url) =="string" ){
					href = url;
					
					// on affiche le loader
					if ($("#contentSrvConfig").html().length<100) {
						$("div.mainTabBody").block();
					}
					
					var tmp = callback;
					var callback = function(){
						// cas different si c'est un nabcast ou un service
						var srvTitle = $("h2 span.nabcast-title").html();
						if (srvTitle==null) srvTitle = $("#contentSrvConfig h2").html();
						
						//// on recupere le nom du service pour l'highliter
						if(srvTitle!=null) {
							var title = $.trim( srvTitle );
							bloc.services.elementHighlight(title);
							bloc.services.elementLoadingOff();
						}
						
						if (tmp!=null) tmp();
					}
					
					//callback = tmp;
						
				} else {
					// sinon on a cliqué sur le lien de config dans la page de nablife
					// on recup l'url depuis le lien directement
					href = $("a.add-srv", this).attr("href");
					contener = this;
					
					// block la page des services pdt le chargement
					$(contener).block();
					
					srvName = $.trim( $("h3", this).text());
				}
 

				loadingService = true;
				
			
				// chargement du service
				$("#contentSrvConfig").load( antiCacheUrl(href), function(){
					
					// Récup du nom du service en cours.
					// normalement, universel, nabcast/service...
					loadedServiceName = $.trim( $(".service-introduction .nabcast-title, .service-introduction h2" ).eq(0).text() );
					
					loadingService = false;
					goToTheTopOfThePage();
					bloc.services.elementHighlight(srvName);
					
					$("div.contentMainTab:visible").hide();	// on cache la liste des services
					$(this).show();						// on montre le service chargé
					
					$("#contentSrvConfig").show();
					
					// on débloque la liste, et simule un mouse out pour deslectionner le block
					$(contener)
						.unblock()
						.trigger("mouseout");
					

					$(contener).unblock();
				
					// nécessaire pour l'historique des nabcasts
					TB_init("contentSrvConfig");
					
					if (callback!=null) callback();
						
				});
						
				return false;		
			}
			
		}
	}(jQuery);

