/**
* Vérifie que tout est correct AVANT ajout d'un nabcast
* @parame {formId}idForm: id de du form
*/
function nablifeValidateAddMp3(idForm){
	if (isEmpty("idMp3")){
		customAlertN( msg_txt['nabcast_noMp3'] );
		return false;
	}
	
	
	if ( $("#myNabcastLatercheck").get(0).checked ) {
		var v = $("#time").hourSelect_Validate();
		if (v!=true) {
			customAlertN( v );
			return false;		
		}
	
		v = $("#date_delay").dateSelect_Validate();
		if (v!=true) {
			customAlertN( v );
			return false;			
		}
		/* Test de la validite de la date */
		var date = $("#date_delay").val();
		var tab = date.split("/");
		var newDate = tab[2]+"-"+tab[1]+"-"+tab[0];
		if( !checkDate(newDate) ){
			customAlertN( date );
			return false;			
		} 
	}
	else{
		$("#date_delay").val("");
	}
	
	var realTime = $("#time").val();
	realTime = realTime.split(":");

	
	// on change discretement l'heure avec LA bonne heure en am/pm....
	$("input[@name=heure_delay]").val(realTime[0]);
		
	submitAjaxForm('nabaztalandAdd','mynabcastUpload');
}


/**
* check les champs lors de l'inscription a un nabcast
*/
function nablifeValidateNabcastConfig(miseAjour){

	var context = $("div.nabcastSubscribe");
	
	var f = function(){
			formSubmitBackground("nabcastConfig", function(e,r){
				var fValues = $("#time", context).hourSelect_GetTime();
				if (fValues['heures']!=0) $(".h", context).get(0).value = fValues['heures'];
				if (fValues['minutes']!=0) $(".m", context).get(0).value = fValues['minutes'];
				endSrvConfig(e,r,miseAjour);
		   });		
	};
	
	$("#heures", context).val( $(".h", context).val() );
	$("#minutes", context).val( $(".m", context).val() );	
	
	// heure vide ou pas correct
	if ( $("#time", context).val()=="" ) {
		customConfirmN( msg_txt['bad_time_subscribe_nabcast'], null, function(){

			f();
			
		}, true);

	} else {

		f();
		
	}

	return false;
}

/**
* check les champs lors de la config de la qualité de l'air
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateAirConfig(miseAjour){
	var hour1 = $("#time1").hourSelect_Validate(false);
	var hour2 = $("#time2").hourSelect_Validate(false);
	
	if ( hour1!=true) {
			customAlertN( hour1 );
			return false;
	}
	if ( hour2!=true) {
			customAlertN( hour2 );
			return false;
	}
	
	if ( isEmpty("listVille") ){
			customAlertN( msg_txt['choix_ville'] );
			return false;			
	}
	

	if ( isEmpty("time2") && isEmpty("time1") && !checkboxIsChecked("lm") ){	// on a choisi ni alert lumineuse ni horaire
			customAlertN(  msg_txt['lumin_ou_horaire']  );
			return false;				
	}

	serviceUpdate(miseAjour);
	
	return false;
}

/**
* check les champs lors de la config de l'humeur
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateHumeurConfig(miseAjour){
	if ( isEmpty("freqSrv") ){
		customAlertN( msg_txt['une_frequence'] );
		return false;			
	}
	
	if ($("div.spoken-lang-service input:checked").length==0){
		customAlertN( msg_txt['une_langue'] );
		return false;			
	}
	
	serviceUpdate(miseAjour);	
	return false;
}

/**
* check les champs lors de la config du Tai-Chi
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateTaichiConfig(miseAjour){
	if ( isEmpty("freqSrv") ) {
		customAlertN( msg_txt['une_frequence'] );
		return false;		
	}
	
	serviceUpdate(miseAjour);
	
	return false;	
}

/**
* check les champs lors de la config du bilan
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateBilanConfig(miseAjour){
	var hour = $("#time").hourSelect_Validate(true);
	
	if ( hour!=true) {
		customAlertN( hour );
		return false;
	}
	
	if ( isEmpty("freqSrv") ) {
		customAlertN(  msg_txt['un_jour'] );
		return false;		
	}			
	
	serviceUpdate(miseAjour);
	return false;
}

/**
* check les champs lors de la config du périph
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidatePeriphConfig(miseAjour){
	var hour1 = $("#time1").hourSelect_Validate(true);
	var hour2 = $("#time2").hourSelect_Validate(true);
			
	if ( $("#arrivee").val() == $("#depart").val() ) {
		customAlertN(  msg_txt['depart_arrive_choix'] );
		return false;
	}

	if ( isEmpty("time2") && isEmpty("time1") && !checkboxIsChecked("lumiere") ){	// on a choisi ni alert lumineuse ni horaire
			customAlertN(  msg_txt['lumin_ou_horaire']  );
			return false;				
	}

	if ( !isEmpty("time2") && hour2!=true ){
		customAlertN( hour2 );
			return false;			
	}
	if ( !isEmpty("time1") && hour1!=true ){
		customAlertN( hour1 );	
			return false;		
	}
		
	serviceUpdate(miseAjour);
	
	return false;
}

/**
* check les champs lors de la config du reveil
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateReveilConfig(miseAjour){
	var hour = $("#time").hourSelect_Validate(true);
	
	if ( hour!=true) {
		customAlertN( hour );
		return false;
	}

	if ($("#son").val() == 0) {
		customAlertN( msg_txt['choix_musique'] );
		return false;
	}
	
	serviceUpdate(miseAjour);
	
	return false;
}
/**
* check les champs lors de la config du reveil FULL
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateReveilFullConfig(miseAjour){
	var hour = new Array();//$("#time").hourSelect_Validate(true);
	var song = new Array();
	
	var error = true;
	for (i=1; i<=7; i++){
		hour[i] = $("#time"+i).val();	//$("#time"+i).hourSelect_Validate();
		song[i] = $.chooseSong.getId("son"+i);
	
		// si on en trouve un de bon, on estime qu'il n'y a pas d'erreur
		if ( hour[i]!="" && song[i]!=0) error = false;
		// on v�rifie qu'� un champ soit associ� une son (et r�ciproquement)
		if ( hour[i]=="" && song[i]!=0) { error = true; break; }
		if ( hour[i]!="" && song[i]==0) { error = true; break; }
			
	}

	if(error) {
		customAlertN( msg_txt['verify_hour_and_song'] ); //$("div.popup-song")
		return false;
	}
	
	
	serviceUpdate(miseAjour);
	
	return false;
}

/**
* check les champs lors de la config de la bourse
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateBourseFreeConfig(miseAjour){
	
	if ( isEmpty("list") ) {
		customAlertN(  msg_txt['choix_indicateur'] );
		return false;
	}

	if ( isEmpty("time2") && isEmpty("time1") && !checkboxIsChecked("lumiere") ){	// on a choisi ni alert lumineuse ni horaire
			customAlertN(  msg_txt['lumin_ou_horaire']  );
			return false;				
	}

	
	serviceUpdate(miseAjour);
	
	return false;
}

/**
* check les champs lors de la config de l'horloge FREE
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateHorlogeFreeConfig(miseAjour){

	
		if ( $("input:checked[@name=checkListClockType]").length < 1 ) { //$("input:checked[@name=checkListClockType]")
			customAlertN( msg_txt['choix_type_horloge'] );
			return false;
		}
	
		if ( $("input:checked[@name=checkListLang]").length < 1 ){ //$("input:checked[@name=checkListLang]")
			customAlertN( msg_txt['choix_langue'] );
			return false;			
		}
				
	/*serviceUpdate(miseAjour);*/
	
	return true;
}

function nablifeValidateHorlogeUpload(a,f,o){
	
	if ( $("#musicFile",f).val() == "") {
		customAlertN(msg_txt['mp3_invalidFile']);
		return false;
	}

	return true;
}


/**
* check les champs lors de la config de la météo FREE
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateMeteoFreeConfig(miseAjour){
	
	
	if (isEmpty("idVille")) {
		customAlertN( msg_txt['choix_ville'] );
		return false;
	}
	
	if ( isEmpty("time2") && isEmpty("time1") && !checkboxIsChecked("fl") ){	// on a choisi ni alert lumineuse ni horaire
			customAlertN(  msg_txt['lumin_ou_horaire']  );
			return false;				
	}
		
	serviceUpdate(miseAjour);
	
	return false;
		
}

/**
* check les champs lors de la config de la bourse Full
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateBourseFullConfig(miseAjour){
	if ( isEmpty("alertName") ){
		customAlertN(  msg_txt['choix_nom'] );
		return false;			
	}
	
	if ( (isEmpty("list") && isEmpty("valName")) || (!isEmpty("list") && !isEmpty("valName")) ) {
		customAlertN(  msg_txt['val_ou_indice'] );
		return false;			
	}
	
	if ( isEmpty("time2") && isEmpty("time1") && !checkboxIsChecked("lumiere") ){	// on a choisi ni alert lumineuse ni horaire
			customAlertN(  msg_txt['lumin_ou_horaire']  );
			return false;				
	}
	
	serviceUpdate(miseAjour);
	return false;		
}

/**
* check les champs lors de la config des RSS Full
* @param {boolean}miseAjour flag pour savoir si on ajoute ou si on modifie le service
*/
function nablifeValidateRssFullConfig(a, f, o){
	
	if ( !isNum($('#srvNbNews').val()) || ($('#srvNbNews').val() < 1) || ($('#srvNbNews').val() > 30) ) {
		customAlertN( msg_txt['error_nbNews'] );
		return false;
	}
	
	if ( isEmpty("name") ){
		customAlertN( msg_txt['choix_rss_name'] );
		return false;			
	}
	
	if ( isEmpty("url") || $('#url').val().indexOf(")") != -1 || $('#url').val().indexOf("(") != -1){
		customAlertN( msg_txt['unvalid_caracter_url'] );
		return false;
	}
	
	// Replace feed:// by http:// (for MAC)
	var feedString = "feed://"
	if($('#url').val().indexOf(feedString)!= -1){
		var temp = $('#url').val().replace(feedString,'http://');
		$('#url').val(temp);
	}
	
	//Check the URL adress (regex moche de composition perso � am�liorer...humm...)
	var regex= new RegExp("(http|https):\/\/?(?:[a-z0-9](?:[-a-z0-9]*[a-z0-9])?\.)+([a-z]{2,4})");
	if( !regex.test($('#url').val()) ){
		customAlertN( msg_txt['unvalid_caracter_url'] );
		return false;
	}
	
	if ($('#confHeure').attr("checked") ) {
		var hourSe = $("#time1").hourSelect_Validate(true);
		var hourWe = $("#time2").hourSelect_Validate(true);
			
		if ( isEmpty("time1") && isEmpty("time2") ) {
			customAlertN( msg_txt['choix_horaire'] );
			return false;
		}
	}
		
	return true;
}

function nablifeValidatePodcastFullConfig(a,f,o){
	
	if ( !isNum($('#podcastNbNews').val()) || ($('#podcastNbNews').val() < 1) || ($('#podcastNbNews').val() > 30) ) {
		customAlertN( msg_txt['error_nbNews'] );
		return false;
	}
	
	if ( isEmpty($('#podcastUrl').val()) || $('#podcastUrl').val().indexOf(")") != -1 || $('#podcastUrl').val().indexOf("(") != -1){
		customAlertN( msg_txt['unvalid_caracter_url'] )
		return false;
	}
	
	// Replace "feed://" by "http://" (for MAC)
	if($('#podcastUrl').val().match('^(?:(?:feed?):\/\/)') != null){
		var temp = $('#podcastUrl').val().replace(/^(?:(?:feed?):\/\/)/,'http://');
		$('#podcastUrl').val(temp);
	}
	
	//Check the URL adress (regex moche de composition perso � am�liorer...humm...)
	var regexp = new RegExp("(http|https):\/\/?(?:[a-z0-9](?:[-a-z0-9]*[a-z0-9])?\.)+([a-z]{2,4})");
	if( !regexp.test($('#podcastUrl').val()) ){
		customAlertN( msg_txt['unvalid_caracter_url'] );
		return false;
	}
	
	if ($('#confHeure').attr("checked") ) {
		var hourSe = $("#time1").hourSelect_Validate(true);
		var hourWe = $("#time2").hourSelect_Validate(true);
			
		if ( isEmpty("time1") && isEmpty("time2") ) {
			customAlertN( msg_txt['choix_horaire'] );
			return false;
		}
	}

	return true;
}


function nablifeValidateWebRadioFreeConfig(miseAjour){

	// on a pas choisi d'heure
	if ( $("input.hourPicker[@value!=]").length < 1 ) {
		customAlertN(msg_txt['radio_time_choose']); 
	} else {
		serviceUpdate(miseAjour);
	}
	

	return false;
}

function nablifeValidateFlickrConfig(a, f, o){
	if ( isEmpty( "login") ) {
		customAlertN( msg_txt['srv_bad_login'] );
		return false;
	}

	return true;
}

function nablifeValidateTwitterConfig(a, f, o){
	if ( isEmpty( "login") ) {
		customAlertN( msg_txt['srv_bad_login'] );
		return false;
	}

	return true;
}

function nablifeValidateGmailConfig(a, f, o){
	if ( isEmpty( "login") ) {
		customAlertN( msg_txt['srv_bad_login'] );
		return false;
	}
	
	if ( isEmpty( "password") ) {
		customAlertN( msg_txt['srv_bad_password'] );
		return false;
	}

	return true;
}

function nablifeValidateRssFreeConfig(miseAjour){
	
	if ( ($('#rssNbNews').val() < 1) || ($('#rssNbNews').val() > 30) ) {
		customAlertN( msg_txt['error_nbNews'] );
		return false;
	}

	if ( !isNum($('#rssNbNews').val()) ){
		customAlertN( msg_txt['error_nbNews'] );
		return false;
	}
	
	if ($('#confHeure').attr("checked") ) {
		var hourSe = $("#time1").hourSelect_Validate(true);
		var hourWe = $("#time2").hourSelect_Validate(true);
			
		if ( isEmpty("time1") && isEmpty("time2") ) {
			customAlertN( msg_txt['choix_horaire'] );
			return false;
		}
	}
	
	serviceUpdate(miseAjour);
	
	return false;

}

function nablifeValidatePodcastFreeConfig(miseAjour){

	if ( ($('#podcastNbNews').val() < 1) || ($('#podcastNbNews').val() > 30) ) {
		customAlertN( msg_txt['error_nbNews'] );
		return false;
	}

	if ( !isNum($('#podcastNbNews').val()) ){
		customAlertN( msg_txt['error_nbNews'] );
		return false;
	}

	if ($('#confHeure').attr("checked") ) {
		var hourSe = $("#time1").hourSelect_Validate(true);
		var hourWe = $("#time2").hourSelect_Validate(true);
			
		if ( isEmpty("time1") && isEmpty("time2") ) {
			customAlertN( msg_txt['choix_horaire'] );
			return false;
		}
	}
	
	serviceUpdate(miseAjour);
	
	return false;

}


function nablifeValidateDialogConfig(miseAjour){

	if ( isEmpty("friendName") ) {
		customAlertN( msg_txt['emptyName'] );
		return false;		
	}
	
	serviceUpdate(miseAjour);
	
	return false;
}

function nablifeValidateMailConfig(miseAjour, selectId) {

	if ( isEmpty( "mail_serveur") ) {
		customAlertN( msg_txt['mail_serveur'] );
		return false;
	}
	if ( isEmpty( "mail_compte") ) {
		customAlertN( msg_txt['mail_compte'] );
		return false;	
	}
	if ( isEmpty( "mail_password") ) {
		customAlertN( msg_txt['mail_password'] );
		return false;	
	}

	formSubmitBackground("srvMailConfig", function(html){
		
		var srv_src = $("input[name=srv_src]").val();
		
		if (srv_src=="") {
			$("#config").html(html);	
		} else {
			$("#config").html(html);	
		}
		
		
		// c degueu on execute les scripts....
		$("#config script").each( function(){
			eval( $(this).html() );
		});
		
		if (selectId==null){
			blocLoad("bloc-MyServices", null, function(){blocMyServices_Select("id_6_2")});
		}
		
				
	});
	
	
	return false;
}