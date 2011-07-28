/**
 * @author nicolas C.
 * affiche et gere le nabthem
 */

(function($) {

	$.fn.nabThemWidget = function(settings) {
		
		// les settings de base
		settings = jQuery.extend({
			url				: "Bloc.do?action=newcomers",
			showMsgSend		: true,
			cycleSpeed		: 5000,
			delayBetween	: 5000,			
			imgSize			: 150,
			infosBgColor	: "#EEE",				
			arrowDist		: 3,			
			pictAnim		: "linear",
			pictAnimSpeed	: 600,
			texts			: {
								age				: "ans",
								tellHimHello	: "Tell him hello",			
								writeHim		: "Write him",
								send			: "Send this message",
								emptyMsg		: "message is empty !"
								},
			datas			: null
		}, settings);
	  	
		// le plugin jquery a proprement parle
		return this.each(function(){
			
			// ------------------------------------------------------------------------------------------
			// globals
			var contener 	= this;
			var currentNab	= 0;
			var maxNab		= 0;
			var nabData		= [];
			//var autoMove	= true;
			var handleAnim	= null;
			
			// met a jour l'etat des fleches sur les cotes.
			// dans le cas ou il n'y a plus de nab avant, ou plus apres
			var updateArrows = function (){
				var p = $(".prev", contener);
				var n = $(".next", contener);
				
				if ( currentNab == 0) {
					$(p)
						.css("cursor", "default")
						.animate({opacity: 0.1}, "fast");
				} else if (currentNab == maxNab-1){
					$(n)
						.css("cursor", "default")
						.animate({opacity: 0.1}, "fast");
				} else {
					$(p)
						.css("cursor", "pointer")
						.animate({opacity: 0.6}, "fast");
					
					$(n)
						.css("cursor", "pointer")
						.animate({opacity: 0.6}, "fast");
				}
			}
			
			var cycleAnimation = function(mode){
				var sendMessageIsOpen = ( $("a.closed-arrow").length > 0) ;	
				if (mode == "start" &&  sendMessageIsOpen ) {
					handleAnim = setInterval( function(){changeNab('next');}, settings.cycleSpeed);
					
					//autoMove = true;
				} else {
					clearInterval(handleAnim);
					
					//autoMove = false;
				}
				
			}
			
			
				
							
			// ------------------------------------------------------------------------------------------
			// functions
			// action des boutons prev/next				
			var changeNab = function(dir){
				var direction;
				var previousNab = 0;
				if (dir == "next" ) {
					previousNab = currentNab;
					currentNab = (currentNab + 1) % maxNab;
					
				// nab precedent
				} else if (dir == "prev" ) {
					previousNab = currentNab;
					
					currentNab--;
					
					if (currentNab < 0 ) currentNab = (maxNab - 1);

				}
				
				$("ul.listOfNab li", contener).css("z-index", "1");
				$("ul.listOfNab li", contener).eq(previousNab).css("z-index", "2");
				
				$("ul.listOfNab li", contener)
					.eq(currentNab)
					.css({
						"z-index"	: 5,
						opacity		: 0.0
					});
					
				$("ul.listOfNab li", contener).animate({opacity: 1.0}, settings.pictAnimSpeed, settings.pictAnim);
				updateInfos();	
			}
			
			// met a jour les infos sur le nabaztag selectionne
			// (nom, age, ville etc)
			var updateInfos = function(){
				$(".nabInfo", contener).fadeOut("fast", function(){
					// vide le contene des infos
					$(this).html("");
					
					// creation du div qui contient le nabname
					$("<div />")
						.addClass("nabNameInfo")
						.text(nabData[currentNab].nabname)
						.appendTo(this);

					// creation du div qui contient le reste des infos pour ce nab
					var t = $("<div />").addClass("otherInfos");
					

					// gestions des infos du nabs
					var infos = [];
					var i =0;
					
					// liste des champs disponible (conditionne l'ordre d'affichage!)
					var inf = new Array ("sex", "age", "country", "city");

					// on boucle sur tous les champs
					jQuery.each(inf, function(t) {
						var tt = inf[t];
						if (nabData[currentNab][tt]!="") {
							// on remplis le tableau qui servira a l'affichage
							infos[i] = nabData[currentNab][tt];
							
							// pour l'age on met une chaine apres ("ans", "year old", etc)
							if (tt == "age") {
								infos[i]+= "&nbsp;" + settings.texts.age;
							}
							
							i++;
							
						}
					});

					// on cree une string simple avec le tableau,
					// pour avoir un truc du genre "sex, age, country, city"
					// (separe par des , quoi !)
					$(t).html(  infos.join(', ') );

					// on rajoute tout ça au contener des infos
					$(t).appendTo(this);
					
					// on l'affiche en fadeIn	
					//$(this).fadeIn("fast");					
					$(this).show();
				});
				

			}

			var showHideMsgArea = function(){
				
				
				var blockContener = $("div.block-content", contener);
				var divToShow = $("div.msg-contener", contener);
				var _height = $(blockContener).height();
				var _msgHeight = $(".msg-contener", blockContener).height() + 5;
				
				
				
				$(divToShow).toggle();

				$(this)
					.toggleClass("closed-arrow")
					.toggleClass("opened-arrow");

				return false;	
			}
			
			var disableButtons = function(){
				$("button", contener).addClass("disabled");
				$("button", contener).unbind();
				setTimeout(enableButtons, settings.delayBetween);				
			}



			var enableButtons = function(){
				$("button.sendHello", contener).bind("click", sendHelloHandler);
				$("button.sendMsg", contener).bind("click", sendMsgHandler);				
				$("button", contener).removeClass("disabled");
			}
			
			var msgSentCallback = function(a){
				$(contener).unblock();

				// si besoin, ferme la partie "message"				
				var divMsg = $(contener).find("div.msg-contener");
				$("div.msg-contener textarea", contener).val("");				
				$("a.opened-arrow", contener).trigger("click");

						
				// un peu tordu mais...
				// je récupere la meme réponse que l'envoi d'un clin d'oeil ou un TTS standard, 
				// a savoir un bout de code JS qui n'est absolument pas dans son contecte initiale, et qui donc
				// ne marche pas, et plante meme l'affichage. Je récupere donc juste ce que je veux a l'aide de regex
				// a savoir, juste la variable 'msg' et 'sendOK'
				var m = new RegExp("msg[ \t]*=[ \t]*msg_txt.*;").exec(a);
				var m2 = new RegExp("sendOK[ \t]*=[ \t]*.*;").exec(a);
				
				eval(m[0]);
				eval(m2[0]);
				
				msgHandling.simpleMsgShow(msg);
				changeNab("next");				
			}
			
			var sendHelloHandler = function(){
				if ( $(this).is(".disabled") ) return false;
				
				$(contener).block();
				
				var url = "myMessagesSendClin.do?destName="+(nabData[currentNab].nabname)+"&send=1";
				$.get(url, msgSentCallback);

				disableButtons();
			}
			
			var sendMsgHandler = function(){
				if ( $(this).is(".disabled") ) return false;
				
				var txt = $("div.msg-contener textarea", contener).val();
				
				// pas de message
				if (txt=="") {
					msgEmptyError("show");
					return false;
				}
				
				$(contener).block();

				var url = "myMessagesSendTTS.do";
				$.get(url, {
					"destName"	: nabData[currentNab].nabname,
					"send"		: 1,
					"messageTTS": txt
				}, msgSentCallback);
				
				disableButtons();
			}
			
			var msgEmptyError = function(state) {
				var tarea = $("div.msg-contener textarea", contener);
				if ( state == "hide") {
					$("div.msg-contener .error-msg span", contener).hide();
					$(tarea).removeClass("error");					
				} else {
					$("div.msg-contener .error-msg span", contener).show();
					$(tarea).addClass("error");
				}
			}
			
			// ------------------------------------------------------------------------------------------
			var initNabThem = function(data){
				nabData = data;
					
				var uu = $("<ul />")
					.css({
						position		: "relative",
						width			: settings.imgSize + "px",
						height			: settings.imgSize + "px",
						marginLeft		: 0
					})
					.addClass("listOfNab");
														
				// rajout des LI qui auront les images en background
				$.each(data, function(i,item){
					
					// si il n'y a pas d'image, on perd pas notre temps...
					if (item.imgUrl==null) return;
					
					
					var zIndex = (maxNab == 0) ? 5 : 1;
					var li = $("<li />");

					$(li)
						.addClass("nabEntry")
						.css({
							width			: settings.imgSize + "px",
							height			: settings.imgSize + "px",
							background		: "url(" + item.imgUrl + ") center center no-repeat",
							"background-color"	: "#EFEFEF",
							"position"		: "absolute",
							overflow		: "hidden",
							top				: "0",
							left			: "0",
							"z-index"		: zIndex
						})
						.appendTo(uu);					
						
					maxNab++;							

				});

				// on rajoute le tout dans le contener, wrappe dans un div qui fait office de cache
				$(contener)
					.addClass("nabThem")
					.append("<div />")
					.find("div")
					.addClass("cache")
					.css({
						overflow	: "hidden",
						width		: settings.imgSize + "px",
						height		: settings.imgSize + "px",
						margin		: "0 auto 0 auto"
					})
					.append(uu);
				
				
				
				// quand on a le cursor sur le widget, l'animation s'arrete....
				$(contener).hover(
					function(){
						cycleAnimation("stop");
					},
					
					function(){
						cycleAnimation("start");
					}					
				);
					
				// hover on sur les fleches
				var bt_hover_on	= function(){
					if ( $(this).css("cursor")=="pointer" ) $(this).css("opacity", 1);
				}
				
				// hover off sur les fleches
				var bt_hover_off= function(){
					if ( $(this).css("cursor")=="pointer" ) $(this).css("opacity", 0.6);
				}				
				
				// bouton NEXT
				var next = 	$("<a />")
								.addClass("next")
								.css({position:"absolute", display: "block", opacity: 0.6})								
								.text(">")
								.attr("href", "#")
								.click(function(){
									changeNab("next");
									return false;
								})
								.hover(bt_hover_on, bt_hover_off);

				// bouton NEXT								
				var prev = 	$("<a />")
								.addClass("prev")
								.css({position:"absolute", display: "block", opacity: 0.6})
								.text("<")
								.attr("href", "#")
								.click(function(){
									changeNab("prev");
									return false;
								})
								.hover(bt_hover_on, bt_hover_off);						

				// si le plugin mousewheel est disponible
				if (($.fn.mousewheel != null)) {
					$('ul', contener)
						.mousewheel(function(event, delta) {
							if ( !$("ul.listOfNab", contener).is(":animated")) {
								if (delta > 0)
									changeNab("prev");
								else if (delta < 0)
									changeNab("next");
							}
							
							return false; // prevent default
						});
				}

				
				// les boutons sont wrappe dans ce div			
				var controls =	$("<div />")
									.addClass("controls")
									.css({
										width 	: settings.imgSize + "px",
										margin	: "0 auto 0 auto",
										position: "absolute"
									})
									.append(prev)
									.append(next);
									
				// ajout des controls dans le dom
				$(contener)
					.find(".cache")
					.append(controls);

				// centrage des controles en hauteur					
				$(controls).css("top", ( (settings.imgSize/2) - ($(".prev", contener).height()/2) ) + "px");
				
				// positionnement des prev et next
				$(".prev", contener).css("left", "-" + ( $(".prev", contener).width() + settings.arrowDist ) + "px" );
				$(".next", contener).css("right", "-" + ( $(".next", contener).width() + settings.arrowDist ) + "px" );
				
				// positionnement du div qui contiendra les infos
				$("<div />")
					.addClass("nabInfo")
					.css({
						width:		(settings.imgSize-4) + "px",
						background:	settings.infosBgColor,
						margin:		"auto auto",
						padding:	"2px",
						display:	"none"
					})
					.appendTo(contener);
					
				// construction de la partie message				
				var msgContener = $("<div class='message'></div>"); 
				$(msgContener)
					.append("<button class='sendHello little'><span>"+(settings.texts.tellHimHello)+"</span></button>")
					.append("<div class='link-contener'><a class='closed-arrow simple-link' href='#'><strong>"+(settings.texts.writeHim)+"</strong></a></div>")
					.append([	["<div class='msg-contener' style='display:none;'>"],
								["<textarea></textarea><div class='error-msg'><span>"+(settings.texts.emptyMsg)+"</span></div>"],
								["<button class='sendMsg little'><span>"+(settings.texts.send)+"</span></button>"],
								["</div>"]
							].join(''));
			
				$(".msg-contener", msgContener).hide();
				
				$("a", msgContener).bind("click", showHideMsgArea);
				
				$("textarea", msgContener).bind("focus", function(){ msgEmptyError("hide") });
				
				if (settings.showMsgSend) {
					$(msgContener).appendTo(contener);
				}
				
				enableButtons();

				//updateArrows();
				updateInfos();
				
				cycleAnimation("start");
			}
			
			
			// on a rien dans les datas...
			if (datas[0].imgUrl==null ) return false;
			
			// on recupere les infos et on commence des qu'on les a
			// si on donne les datas directement, on fait pas d'appel ajax
			if (settings.datas==null)$.getJSON(settings.url, initNabThem);
			else initNabThem(datas);
			
		});
	};

})(jQuery);