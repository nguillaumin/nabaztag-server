// va contenir (un jour) toutes les fonctions concernant les blocs droites/gauches 
var bloc = {};

var gHttpsUrl = "https://my.nabaztag.com/vl/action/";

var gHColor = "rgb(222, 235, 103)";	// couleur highlite générique
var gErrorColor = "rgb(230, 139, 147)";// couleur highlite erreur
var gSrvErrorFlag = false;		// mis a true, au cas ou la mise a jour du service c'est mal passé.
var gGlobal = new Object;


// dans ce tableau sont conserv?'?t de tout les div qui charge du contenu dynamiquement (true = en train de charger)
var loadingTable = new Array;
var loadingTimerTable = new Array;

// pour chaque groupe d'onglet (pour l'instant il n'y en a qu'un par page, mais sait on jamais !)
// on conserve quel onglet est selectionné
var currentTab = ""; 		//= new Array;
var currentSousTab = ""; 	//= new Array;

var loadingContent 			= "<span class='LoadingBlock'><img align='absmiddle' alt='' height='20' width='20' src='../include_img/loading.gif'></span>";
var loadingContentError 	= "<div id='LoadingBlockError'><span>Loading error</span></div>";
var processingContent 		= "<div class='ProcessingBlock'><div style='text-align:center;'><img alt='' height='100' width='93' src='../include_img/loadingAnimation.gif'></div></div>";
var backgroundSubmitContent = "<div class='backgroundSubmit'><div class='back'></div><div class='txt'><span>please wait</span></div></div>";

var blocOrder = new Array;

/* Home page */

var blockExtendable				= "|friends:open|";

// nabaztag.constantes.ISLOG est setté en amont dans les pages JSP
if(nabaztag.constantes.ISLOG){
	
	// blocOrder['onglet du site'] = colonnegauche_type_de_bloc1@param1^param2, colonnegauche_type_de_bloc2@param1^param2 : colonnedroite_type_de_bloc1@param1^param2
	
	// home
	blocOrder['CreerCompte']		= "generic@register"; 
	
	// nablife
	if ( !nabaztag.constantes.BAD_CONNECTED_OBJECT && nabaztag.constantes.OBJECTID == 0 ) {
		blocOrder['AllServices'] 		= "DernierAnnuaire : nablives";
	} else {
		blocOrder['AllServices'] 		= "MyServices, MyNabcast, DernierAnnuaire : nablives";
	}
	
	blocOrder['Nabaztaland'] 		= "MyServices, MyNabcast";
	blocOrder['Publier'] 			= "MyServices, MyNabcast";
	
	// message
	blocOrder['Envoyer']			= "friends:DernierAnnuaire";
	blocOrder['Envoyes'] = blocOrder['Recu'] = blocOrder['Archives'] = blocOrder['Envoyer'];
	
	// terrier
	blocOrder['Profil'] 			= (nabaztag.constantes.OBJECTID == 0) 	? "updateMac@true"
																			: "";
	blocOrder['MaMusique']			= ":friends";
	blocOrder['MesAmis']			= ":DernierAnnuaire";	

} else {
	
	//home
	blocOrder['SeConnecter'] 	= "DecouvrirNabaztag:DernierAnnuaire";
	blocOrder['CreerCompte']	= "generic@register"; 
	
	// nablife
	blocOrder['AllServices'] 	= "DernierAnnuaire : nablives"; //generic@homeBloc:
	blocOrder['Nabaztaland']	= blocOrder['AllServices'];

	// message
	blocOrder['Envoyer']		= "DernierAnnuaire:";

}
