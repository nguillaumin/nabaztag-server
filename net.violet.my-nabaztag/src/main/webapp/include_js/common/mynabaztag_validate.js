/*
*	TERRIER
*/

/**
*  Affiche un message de confirmation de suppression d'un nabshare
* @param musicID	ID du nabshare a détruire
*/
function confirmDeleteMp3(musicID) {
	customConfirmN(msg_txt['confirm_delete'], null, function(){
		var url = "../action/myManageMp3.do?queFaire=delete&idMp3="+musicID;
		$.get(url);
		$("#updateMusic_"+musicID).fadeOut();	
	});
}

/**
* Vérifie que tout est correct AVANT upload
*/
function terrierValidateUploadMp3(){
	
	// check si le nom est vide
	if ( fieldIsEmpty("musicName")) {
		customAlertN( msg_txt['mp3_invalidName'] );
		return false;		
	}
	
	// on veut partager le meupeu3
	if ( checkboxIsChecked('shareMp3') ) {
		var sel = document.getElementById('categId');
		if (sel.selectedIndex==0){	// on a pas assigner de catégorie !
			customAlertN( msg_txt['mp3_invalidCat'] );
			return false;
		}		
	}
	
	if (fieldIsEmpty("musicFile")) { // on a pas choisi de fichier
		customAlertN(msg_txt['mp3_invalidFile']	);
		return false;
	}
	
	return true;
}


/**
* Vérifie que tout est correct lors de l'édition d'un mp3
* @param {divId}musicID	id du mp3
*/
function validateEditMp3(musicID){

	if ( fieldIsEmpty('namer_'+musicID) ){	// le champs nom est vide
		customAlertN( msg_txt['mp3_invalidName'] );
		return false;
	}
	
	if ( checkboxIsChecked('share_'+musicID) ) {
		var sel = document.getElementById('categfor_'+musicID);
		if (sel.selectedIndex==0){
			customAlertN( msg_txt['mp3_invalidCat'] );
			return false;
		}
	}
	

	$('#updateMusic_'+musicID).ajaxSubmit(function(e){
														updateMp3List();
													  });
	LoaderShow('contentMaMusique');
	
	return false;
	
}

/*
*	MES MESSAGES
*/

/**
* V�rifie les champs avant envoi d'un message
* @param send{boolean} on envoi ou on �coute seulement le message ? send est true par d�faut
*/
function validateSendMessage(send){

	if (send==null) send=1; // par defaut;

	var emptyDate = false;
	var invalideHour = false;

	/* ******************************* */

	// si on veut un envoi diff�r�
	if (checkboxIsChecked("plustard")) {
		/* pour conserver la compatibilit� entre le code java, et les modules JS date / heure
		 * je fait une ptite bidouille pour envoyer les bonnes info dans le post
		 */
		
		var trueDate = $("#date_delay").val();
		var trueHour = $("#time").val();	
	
		var dd = trueDate.split("/");
		var hh = trueHour.split(":");
		
		$("#choixJourDiff").get(0).value = dd[0];
		$("#choixMoisDiff").get(0).value = dd[1];
		$("#choixAnneeDiff").get(0).value = dd[2];	
		
		$("#choixHeureDiff").get(0).value = hh[0];		
		$("#choixMinuteDiff").get(0).value = hh[1];	
	
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
		
	} // fin du if (envoi différé)



	// on set le mode d'envoi suivant si on veut �couter ou bien envoyer le message	
	$("#send").val(send);
	
	// vérifie si le champs pseudo est vide
	if ( send && fieldIsEmpty('destName') && selectIsEmpty('friendObjectId') ) {
			customAlertN( msg_txt['emptyName'] );
			return false;
	}

	// vérifie le contenu du champs texte a envoyer, qu'il ne soit pas vide
	if (currentSousTab == "texte") { // on essaye d'envoyer un TTS
		if ( fieldIsEmpty('messageTTS') ) { // mais on a pas écrit de texte, c'est balo
			customAlertN( msg_txt['emptyMsg'] );
			return false;
		}
	}
	
	// clind d'oeil, bibliotheque ou nabshare, on vérifie qu'un fichier est bien selected
	if (currentTab=="Envoyer" && ( currentSousTab == "clindoeil" || currentSousTab == "bibliotheque" || currentSousTab == "nabshare" ) ) {
		if ( fieldIsEmpty('itemValue') ) {
			customAlertN(msg_txt['emptyItem']);
			return false;			
		}
	}
	
	if (currentSousTab == "mp3" && currentTab=="Envoyer") {
		// premier cas on upload un fichier
		if ( $("#idMp3").val() == "-1" ) {
			if ( fieldIsEmpty('musicName') ) { // le champs nom est vide
				customAlertN( msg_txt['mp3_invalidName'] );
				return false;
			}
			if ( fieldIsEmpty('musicFile') ) { // le champs filename
				customAlertN( msg_txt['mp3_invalidFile'] );
				return false;
			}
			
			// on met la valeur a 120 max
			if ( $("#musicTime").attr("value")>120 ) $("#musicTime").attr("value", "120");
			
			messagesStartMp3upload();
			return;
		} else { // deuxieme cas on envoi juste un mp3 issu
			// vieux hack, on renomme le champs pour pas plant� java (qui s'attend a un type Filename tout le temps...)
			$("#musicFile").attr("name", "musicFileFake");
		}
	}

	if (send) {
		// on submit le form si tout est ok
		submitAjaxForm('sendMsg','boxContent');	
	} else {
		// pour �couter....
		formSubmitBackground('sendMsg', function(h,r){
			var file = $.trim(h);
			loadPersoPlayer(file, 200, true, "myPlayerMp3Message");
		});
	}

	// vieux hack, on RE renomme le champs
	$("#musicFile").attr("name", "musicFile");	
}


/*
*	NABAZTALAND
*/

/**
* Vérifie les champs création du nabcast
*/
function validateNabcast(){

	idNabcast = document.getElementById("idNabcast").value;

	if ( isEmpty("nabcastName")) {
		customAlertN( msg_txt['nabcast_emptyName'] );
		return false;
	}
	if ( isEmpty("nabcastDesc")) {
		customAlertN( msg_txt['nabcast_emptyDesc'] );
		return false;
	}
	if ( isEmpty("nabcastCat")) {
		customAlertN( msg_txt['nabcast_emptyCat'] );
		return false;
	}

	var n = document.getElementById("nabcastName").value;
	var i = document.getElementById("idNabcast").value;

		
	var fModif = function(html, response){	// fonction aprés la modification d'un nabcast
		var li = document.getElementById("myNabcastsList_"+i);
		
		// on modifie le nom dans la liste de droite
		$("a", li).html(n);
	}
	
	var fNew = function(html, response){	// fonction aprés la création d'un nabcast
		var id = $.trim(html);	// on recupere l'id généré

			
		// on rajoute le nabcast a la fin de la liste
		blocMyNabcast_addItem(id, n, true);
	
		// on passe en mode edition
		document.getElementById("mode").value="3";
		document.getElementById("idNabcast").value=id;
		
		divChangeUrl("mynabcastUpload", "myNabaztalandUpload.do?idNabcast="+id);		

	}

	if (idNabcast!=0) {
		formSubmitBackground("formNabcast", fModif);
	} else {
		formSubmitBackground("formNabcast", fNew);
	}
	 
}


/**
* Vérifie que tout est correct AVANT modification de notre photo
* @param {formId}idForm id de du form
*/
function terrierValidateNewPict(idForm){
	
	var img = $("input#browse", "#"+idForm).get(0).value;

	var ext = img.split("."); // alors �a, c'est pour r�cuperer l'extension d'un fichier, c'est tr�s tr�s moche....
	ext = ext[ext.length-1].toUpperCase();
	

	if (img=="") {
		customAlertN( msg_txt['img_nullFile'] );
		return;
	}
	if (ext!="JPG" && ext!="JPEG") {
		customAlertN( msg_txt['img_invalidFile'] );
		return;		
	}
	$("#profil-contener .twoCol-right").block();	
	$("#"+idForm).get(0).submit();
	$('#pictureLoader').toggle();
	
}


function terrierValidateNewPictEnd(){
	var newUrl = antiCacheUrl($("img.user_picture").attr("src"));
	
	$("#imageHolder img").remove();
	$("#imageHolder").append("<img src='"+newUrl+"'>");
	
	$(".profil .photo img").remove();
	$(".profil .photo").append('<img height="33" border="0" align="left" src="'+newUrl+'" class="photo user_picture"/>');
	$("#profil-contener .twoCol-right").unblock();
	
}



function validatePreferences(){
	
	if ( $("input[@name=newPassword]").val() != "" ) {
		if (!checkNabname( $("input[@name=pseudo]").val() ) ) {
			customAlertN( msg_txt['reg_wrong_format'] );	
			return false;
		}
		
		if ( $("input[@name=newPassword]").val().length < 5 ){	
			customAlertN(  msg_txt['reg_pass_too_short'] );	
			return false;
		}
		
		if ( $("input[@name=newPassword]").val() != $("input[@name=newPasswordVerif]").val() ){	
			customAlertN(  msg_txt['reg_bad_confirmpass'] );	
			return false;		
		}
	}	


	return true;
}


/// return false si le nabname est incorrect
function checkNabname(str){
	var state = true;
	
	//check si le char est a-z A-Z 0-9 ou - ou _ ou .
	// ok j'aurais pu faire une regex... (j'aurai du meme...)
	for (i=0; i<str.length; i++) {
		var c = str.charCodeAt(i);

		if ( (c<48) || (c>57 && c<65) || (c>90 && c<97) || (c>122 && c<500) ){
			state = false;
			break;			
		}
		
	}
	
	return state; 
}
function regMacUpdateValidate(){
	
	if ( $("input[@name=macAddress]").val() == "" ){
		customAlertN( msg_txt['reg_bad_macadress'] );
		return false;			
	}
	
	if ( macAdressValidate( $("#numSerie").val() )==false){
		customAlertN(  msg_txt['reg_bad_macadress'] );
		return false;	
	}
			
	customConfirmN( msg_txt['reg_confirm_mac_address'] + "<br /><span style='font-weight:bold;font-size:16px;'>" + $("#numSerie").val() +"</span>", "", function(){
		submitAjaxForm("idFormMAC", null, null, function(a){
			var ret = $(a);
			$(a).removeClass("block-content");
			$("#block-content-updatemac").html( $(a).html() );
		});	
	});
			
	return false;
	
}

function regMainValidate(){

	if ($("input[@name=dispatch]").val() != "insertUser") {
		submitAjaxForm("registerProfil", "contentCreerCompte");
		return false;	
	}
	
 
	if ( isEmpty("prenom") ){
		msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_empty_prenom'], name:"#prenom", type:"error"} );
		return false;			
	}
	
	if ( isEmpty("nom") ){
		msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_empty_nom'], name:"#nom", type:"error"} );
		return false;			
	}
	
	if ( isEmpty("pays") ){
		msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_empty_pays'], name:"#pays", type:"error"} );
		return false;			
	}
	
	if ($("#pseudoId").length>0) {
		if ( !checkNabname( $("#pseudoId").val() ) ) {
			msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_wrong_format'], name:"#pseudoId", type:"error"} );
			return false;	
		}

		if (isEmpty("pseudoId")){	
			msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_empty_nabname'], name:"#pseudoId", type:"error"} );	
			return false;
		}
	}
	
	if ($("#email").length>0) {	
		if (isEmpty("email")){	
			msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_empty_email'], name:"#email", type:"error"} );		
			return false;
		}
		
		if ( !checkemail( $("#email").val() ) ){
			msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_invalid_email'], name:"#email", type:"error"} );	
			return false;
		}
	
		if ($("#email").val() != $("#emailConf").val()){
			msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_bad_confirmemail'], name:"#emailConf", type:"error"} );
			return false;	
		}
	
		if ( $("#pass").val().length < 5 ){	
			msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_pass_too_short'], name:"#pass", type:"error"} );	
			return false;
		}
		
		if (isEmpty("pass")){	
			msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_empty_pass'], name:"#pass", type:"error"} );	
			return false;
		}
		if ($("#passConfirm").val() != $("#pass").val()){
			msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_bad_confirmpass'], name:"#passConfirm", type:"error"} );
			return false;				
		}
	}
	
	if ( $("input[@type=radio][@checked]").val() != 0 && $("#numSerie").length>0) {	// le cas ou on entre une mac adresse
		if ( macAdressValidate( $("#numSerie").val() )==false){
			msgHandling.errorMsgShowDisplay( {text:msg_txt['reg_bad_macadress'], name:"#numSerie", type:"error"} );
			return false;	
		}
	}

	return true;
}

function regRecoverValidate(){

	if ( $("#recoverAccount input[@name=emailAddress]").val() == "" ) {	
		customAlertN(  msg_txt['reg_empty_email'] );	
		return false;
	}

	if ( macAdressValidate( $("#recoverAccount input[@name=macAddress]").val() )==false){
		customAlertN(  msg_txt['reg_bad_macadress'] );
		return false;	
	}

	submitAjaxForm("recoverAccount", "recupForm", null, register.initRecupSerialForm);	
	return false;
}


/**
* v�rifie la validit� formulaire d'enregistrement du num�ro de s�rie du nab
* strip les �ventuels '-' et :, v�rifie que chaque char est [a-f] [0-1] et que
* la longueur de la chaine est 12 uniquement.
*/
//var gNumSerie = null;

function macAdressValidate(str){
	if (str=="" || str==null) return false;
	
	var ok = true;
	
	// table hexa
	var hexa = "0123465789ABCDEF"; 

	//var str = $("#numSerie").val();
	
	// on passe en upperCase
	str = $.trim(str.toUpperCase());
	
	// on vire les - si yen a...
	var s = str.replace(/-/g, "");
	
	// on vire les : aussi tant qu'on y est ;)
	var s = s.replace(/:/g, "");
	
	// on parse la chaine pour voir si tous les char sont hexa valid
	for (i=0; i<s.length; i++){
		var c = s.charAt(i);
		if (strpos(hexa, c)<0){
			// pas hexa
			ok = false;
		}
	}
	
	// si la chaine a PAS une longueur de 12
	if (s.length != 12 /*&& s.length!=16*/){
		ok = false;
	}
	
	
	
	return ok;
}

function nablifeValidateAbonnementCB() {

	var error = false;
	
	$("#contentMonAbonnement input").each(function(){
		v= $(this).val();
		
		if (v=="") {
			error = true;
			
		}
	});

	if (error) {
		customAlertN( msg_txt['champs_obligatoires'] );
		return false;
	}
	

	
	var nb1 = $("#numCarte1").val();
	var nb2 = $("#numCarte2").val();
	var nb3 = $("#numCarte3").val();
	var nb4 = $("#numCarte4").val();			
	
	//if ( !(nb1>=1000) || !(nb2>=1000) || !(nb3>=1000) || !(nb4>=1000) ) {
	if ( nb1=="" || nb2=="" || nb3=="" || nb4=="" ) {	
		customAlertN( msg_txt['cb_invalid'] );
		return false;
	}
	
	if ( isEmpty( "crypto" ) ) {
		customAlertN( msg_txt['cryto_invalid'] );
		return false;
	}
	
	if ( !$("#checkMe").attr("checked") ){
		customAlertN( msg_txt['accept_conditions'] );
		return false;
	}
	
	submitAjaxForm("AbonnementCB", "contentMonAbonnement");
		
	return false;
}

function registerValidateTellFriendConfig(){
	if ( isEmpty("mailZone") ) {
		customAlertN( msg_txt['bad_friend_address'] );
		return false;
	}
	
	submitAjaxForm("registerFriends", "contentCreerCompte");
	
	return false;
}

function validateAnnuaireSearch(){
	
	$('div.votre-recherche').remove();
	
	updateHiddenValue('newPage', '1');
	

	submitAjaxForm('rechercheAnnuaire', 'resultat-recherche');


	return false;
}

function validateProfilSignature(){
	formSubmitBackground('idTerrierSignature', function(){
		//goToTheTopOfThePage();
		$.msgPopupOverlay(msg_txt['profil_modif_ok']);
	}, "signature-contener");
	return false;		
}

function validateMyProfil(){

		formSubmitBackground('idForm', function(){
			//goToTheTopOfThePage();
			$.msgPopupOverlay(msg_txt['profil_modif_ok']);
		});
		
	return false;
}

function validateRetrievePassword(){
	if ( !checkemail( $("#user_mail").val()) ) {
		customAlertN( msg_txt['reg_invalid_email'] );
		return false;
	}
		
	if ( isEmpty( "user_login") || isEmpty( "user_mail") ) {
		customAlertN( msg_txt['field_password_recovery'] );
		return false;
	}
			
	$('#recover-password').block();
	
}

function validateFinalRabbitPing(){
	// c'est moche tout �a.
	showFormWaitForResponse("thisForm", true);
	
	$("#thisForm").ajaxSubmit("#contentCreerCompte",
		function(html, response){
	
			showFormWaitForResponse("thisForm", false);
			
			if (response=="error") {
				customAlertN( msg_txt['lost_connexion'] );
			}
			
	});
	return false;
}
