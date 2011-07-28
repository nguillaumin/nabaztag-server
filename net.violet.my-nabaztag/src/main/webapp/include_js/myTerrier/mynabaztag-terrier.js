/**
 * @author nicolas
 */

/**
* check les champs lors de la config du couchage
*/

function nablifeValidateCoucheConfig(){
	var hour1S =  ( $("#startW").val() 	== "" ) 	? false : true;
	var hour1E =  ( $("#endW").val() 	== "" ) 	? false : true;
	var hour2S =  ( $("#startWe").val() == "" ) 	? false : true;
	var hour2E =  ( $("#endWe").val() 	== "" ) 	? false : true;

	if ( (hour1S==true && hour1E==false) || (hour1S==false && hour1E==true) ){
		customAlertN( msg_txt['heure_semaine_incorrect'] );
		return false;		
	}

	if ( (hour2S==true && hour2E==false) || (hour2S==false && hour2E==true) ){
		customAlertN( msg_txt['heure_weekend_incorrect'] );
		return false;		
	}
	
	if ( (hour1S==false && hour1E==false && hour2S==false && hour2E==false) ){
		customAlertN( msg_txt['heure_semaine_incorrect'] + "<br />" + msg_txt['heure_weekend_incorrect'] );
		return false;
	}
		
	return true;
}

friends = function() {

    return {
		init_forms: function(){

			
			friends.init_addfriend_form();
			friends.init_modiffriend_form();
			friends.init_modifblacklist_form();
			
			tools.init_simpleAjaxLink(friends.init_forms);


			$("div.amis .checkboxFriends").unbind();
			$("div.amis .checkboxFriends").bind("click", function(){
				$(this).parents("div.amis").toggleClass("selected");
			})

			msgHandling.errorMsgShow();			
			
		},
		
		init_addfriend_form : function(){
			$('#add_new_friend').unbind();
			
			$('#add_new_friend').ajaxForm({
				target: 		"#friends-contener",
				beforeSubmit: 	beforeFormAction,
				success: 		function(){
					
					friends.init_forms();
				
				}			
			});	
						
		},
		
		init_modiffriend_form : function(){
			var shouldUpdateBlackList = false;
			
			$('#update_friends').unbind();
			
			$('#update_friends').ajaxForm({
				target: 		"#friends-contener",
				
				beforeSubmit: 	function(formData, b, c) {
					for (var i=0; i < formData.length; i++) { 
						if (formData[i].name == "dispatch" && formData[i].value=="addBlackList") {
							shouldUpdateBlackList = true;
						}
					}

					beforeFormAction(formData, b, c);
				},
				
				success: 		function(){
					if (shouldUpdateBlackList) {
						divChangeUrl("black-contener", "myTerrierBlackList.do?dispatch=load", friends.init_forms);
					}
					
					friends.init_forms();
				}
							
			});	
						
		},
				
		init_modifblacklist_form : function(){
			$('#update_black').unbind();
			
			$('#update_black').ajaxForm({
				target: 		"#black-contener",
				beforeSubmit: 	beforeFormAction,
				success: 		function(){
					
					friends.init_forms();
				
				}			
			});	
						
		}

		
	}
}();

 preferences = function() {
	var startLang;
	var hourMode;
    return {
		/*
		 * Initialisation de tous les formulaires
		 */
        init_forms: function(hourMode){
			preferences.startLang = $("#spokenLanguage").val();
			preferences.hourMode = nabaztag.constantes.H24; //hourMode; // OVERRIDE par une global qui vient direct du java donc fiable.
			
			preferences.init_display_form();
			preferences.init_api_form();
			preferences.init_account_form();
			preferences.init_alertes_form();
			preferences.init_privacy_form();
			preferences.init_mesures_form();
			preferences.init_couche_form();
					
		},
		
		/*
		 * Initialisation du formulaire DISPLAY
		 */
		init_display_form: function(){
			$("#spokenLanguage").change(spokenLangChange);
			$("input[@name=checkListLang]").click(usingLangSite);
				
			$('#form_display').ajaxForm({
				target: 		"#display-contener",
				beforeSubmit: 	beforeFormAction,
				success: 		function(){
									var newLang = $("#spokenLanguage").val();

									if (newLang!=preferences.startLang) {
										//preferences.startLang = newLang;
										window.location.replace("myTerrier.do?onglet=MesPreferences");
									} else {
										preferences.init_display_form();
										msgHandling.simpleMsgShow();
									}
								}			
			});	
						
		},
		
		/*
		 * Initialisation du formulaire API
		 */
		init_api_form: function(){
			$('#api-contener form').ajaxForm({
				target: 		"#api-contener",
				beforeSubmit: 	beforeFormAction,
				success: 		function(){
									preferences.init_api_form();
									msgHandling.simpleMsgShow();
								}			
			});	
						
		},
		
		/*
		 * Initialisation du formulaire ACCOUNT
		 */
		init_account_form: function(){
			$('#account-contener form').ajaxForm({
				target: 		"#account-contener",
				beforeSubmit: 	function(a, b ,c){
									if ( validatePreferences() ){
										beforeFormAction(a, b ,c );
									} else {
										return false;	
									}
								},				

				success: 		function(){
									preferences.init_account_form();
									msgHandling.simpleMsgShow();
								}			
			});

			$("a.chg-mdp").bind("click", function(){
				$("fieldset.mdpfieldset").toggle();
				return false;
			});
						
		},
		
		/*
		 * Initialisation du formulaire ALERTEs
		 */
		init_alertes_form: function(){
			$('#alertes-contener form').ajaxForm({
				target: 		"#alertes-contener",
				beforeSubmit: 	beforeFormAction,
				success: 		function(){
									preferences.init_alertes_form();
									msgHandling.simpleMsgShow();
								}			
			});	
						
		},
		
		/*
		 * Initialisation du formulaire PRIVACY
		 */
		init_privacy_form: function(){
			$('#prive-contener form').ajaxForm({
				target: 		"#prive-contener",
				beforeSubmit: 	beforeFormAction,
				success: 		function(){
									preferences.init_privacy_form();
									msgHandling.simpleMsgShow();
								}			
			});	
						
		},		

		/*
		 * Initialisation du formulaire MESURES
		 */
		init_mesures_form: function(){
			$('#mesure-contener form').ajaxForm({
				target: 		"#mesure-contener",
				beforeSubmit: 	beforeFormAction,
				success: 		function(){
									preferences.init_mesures_form();
									msgHandling.simpleMsgShow();
								}			
			});	
						
		},		

		/*
		 * Initialisation du formulaire COUCHE
		 */
		init_couche_form: function(){
			var deleteCouche = function() {
				$("#couche-contener input[@name=delete]").val("1");
				$("#couche-contener input[@name=add]").val("0");
				$("#couche-contener form").submit();
			};
			
			$("#couche-contener input.hourPicker").hourSelect_Init( preferences.hourMode, false ); 
			$("#couche-contener .genericDeleteBt").bind("click", deleteCouche);
		
			$('#couche-contener form').ajaxForm({
				target: 		"#couche-contener",
				beforeSubmit: 	function(a, b ,c){
									// cas du DELETE
									if ($("#couche-contener input[@name=delete]").val()==1) {
										beforeFormAction(a, b ,c );
										return true;
									} else { // CAS modif ou ajoute
										if ( nablifeValidateCoucheConfig() ){
											beforeFormAction(a, b ,c );
										} else {
											return false;	
										}
									}
									
									
									
								},
				
				success: 		function(){
									preferences.init_couche_form();
									msgHandling.simpleMsgShow();
								}			
			});	
						
		}		
     
	 
	 }
}();
