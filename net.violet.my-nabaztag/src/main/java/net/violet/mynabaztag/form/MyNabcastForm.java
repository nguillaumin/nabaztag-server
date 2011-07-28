package net.violet.mynabaztag.form;

import java.util.List;

import net.violet.platform.dataobjects.UserData;

import org.apache.struts.upload.FormFile;

public final class MyNabcastForm extends AbstractForm {

	private static final long serialVersionUID = 1L;
	private String langUser;
	private int idLapin;
	private String err_message;
	private String action;
	private int idCateg;
	private String nameCateg;
	private int isLog;
	private int shiftPage;
	private int shiftPage2;
	private int idNabcast;
	private int idNabcastval;
	private String titreNabcast;
	private String descNabcast;
	private int idmusic;
	private int modeFull; // savoir si il est abonné
	private int redirect; // permet de renvoyer sur une autre page
	private String musicName;
	private int heure;
	private int minute;
	private int mode; // update ou insert ; nabcast_home ou mynabcast
	private int acceptCondition; // accepte les conditions
	private String nabcast_color_sign; // la color de la signature
	private String nabcast_music_sign; // la music de la signature
	private String nabcast_anim_sign; // la chorégraphie de la signature
	private String cdll;
	private int afficher_flash;
	private int nbrAbonne;
	private String titreSongNabcast;
	private String idMP3; // id de mp3
	private FormFile musicFile;
	private String musicCut;
	private String musicTime;

	private String afficheEnvoieDiff;
	private String date_diff;
	private String heure_diff;
	private String minute_diff;
	private String timezone;
	private int debug;

	/* partie home */
	private List listeTopNabcast; // top des nabcasts les meilleurs( par rapport au nombre d'abonné)
	private List listeCategorie; // liste des différents catégories de nabcast
	private List listeNews; // liste des news sur le nabcast
	private List listNewNabcast; // Liste des nouveaux nabcast entrant
	private List listAllNabcastbyCateg; // Liste des nabcast par catégorie
	private List listepagination; // pagination de page
	private List listepagination2; // pagination de page2
	private List listeAllSongNabcast; // list tous les sons d'un nabcast
	private List listNabcastSubscribe; // list des nabcast dont on est abonné
	private List listNabcastCreate;// list des nabcast créés
	private List listeMusique; // list Musique pour la signature
	private List listeAnimation; // list animation pour la signature
	private List listeAllSongNabcastAfter; // liste tous les sons d'un nabcast préprogrammé (plus tard)
	private List listmp3; // liste mp3 perso

	private List listHeure;
	private List listMinute;

	private UserData userData = UserData.getData(null);

	/**
	 * @return the userData
	 */
	public UserData getUserData() {
		return this.userData;
	}

	/**
	 * @param inValue the userData to set
	 */
	public void setUser(UserData inValue) {
		this.userData = inValue;
	}

	/**
	 * @return the acceptCondition
	 */
	public int getAcceptCondition() {
		return this.acceptCondition;
	}

	/**
	 * @param inValue the acceptCondition to set
	 */
	public void setAcceptCondition(int inValue) {
		this.acceptCondition = inValue;
	}

	/**
	 * @return the afficheEnvoieDiff
	 */
	public String getAfficheEnvoieDiff() {
		return this.afficheEnvoieDiff;
	}

	/**
	 * @param inValue the afficheEnvoieDiff to set
	 */
	public void setAfficheEnvoieDiff(String inValue) {
		this.afficheEnvoieDiff = inValue;
	}

	/**
	 * @return the afficher_flash
	 */
	public int getAfficher_flash() {
		return this.afficher_flash;
	}

	/**
	 * @param inValue the afficher_flash to set
	 */
	public void setAfficher_flash(int inValue) {
		this.afficher_flash = inValue;
	}

	/**
	 * @return the cdll
	 */
	public String getCdll() {
		return this.cdll;
	}

	/**
	 * @param inValue the cdll to set
	 */
	public void setCdll(String inValue) {
		this.cdll = inValue;
	}

	/**
	 * @return the date_diff
	 */
	public String getDate_diff() {
		return this.date_diff;
	}

	/**
	 * @param inValue the date_diff to set
	 */
	public void setDate_diff(String inValue) {
		this.date_diff = inValue;
	}

	/**
	 * @return the descNabcast
	 */
	public String getDescNabcast() {
		return this.descNabcast;
	}

	/**
	 * @param inValue the descNabcast to set
	 */
	public void setDescNabcast(String inValue) {
		this.descNabcast = inValue;
	}

	/**
	 * @return the err_message
	 */
	public String getErr_message() {
		return this.err_message;
	}

	/**
	 * @param inValue the err_message to set
	 */
	public void setErr_message(String inValue) {
		this.err_message = inValue;
	}

	/**
	 * @return the heure
	 */
	public int getHeure() {
		return this.heure;
	}

	/**
	 * @param inValue the heure to set
	 */
	public void setHeure(int inValue) {
		this.heure = inValue;
	}

	/**
	 * @return the heure_diff
	 */
	public String getHeure_diff() {
		return this.heure_diff;
	}

	/**
	 * @param inValue the heure_diff to set
	 */
	public void setHeure_diff(String inValue) {
		this.heure_diff = inValue;
	}

	/**
	 * @return the idCateg
	 */
	public int getIdCateg() {
		return this.idCateg;
	}

	/**
	 * @param idCateg the idCateg to set
	 */
	public void setIdCateg(int idCateg) {
		this.idCateg = idCateg;
	}

	/**
	 * @return the idLapin
	 */
	public int getIdLapin() {
		return this.idLapin;
	}

	/**
	 * @param idLapin the idLapin to set
	 */
	public void setIdLapin(int idLapin) {
		this.idLapin = idLapin;
	}

	/**
	 * @return the idMP3
	 */
	public String getIdMP3() {
		return this.idMP3;
	}

	/**
	 * @param idMP3 the idMP3 to set
	 */
	public void setIdMP3(String idMP3) {
		this.idMP3 = idMP3;
	}

	/**
	 * @return the idmusic
	 */
	public int getIdmusic() {
		return this.idmusic;
	}

	/**
	 * @param idmusic the idmusic to set
	 */
	public void setIdmusic(int idmusic) {
		this.idmusic = idmusic;
	}

	/**
	 * @return the idNabcast
	 */
	public int getIdNabcast() {
		return this.idNabcast;
	}

	/**
	 * @param idNabcast the idNabcast to set
	 */
	public void setIdNabcast(int idNabcast) {
		this.idNabcast = idNabcast;
	}

	/**
	 * @return the idNabcastval
	 */
	public int getIdNabcastval() {
		return this.idNabcastval;
	}

	/**
	 * @param idNabcastval the idNabcastval to set
	 */
	public void setIdNabcastval(int idNabcastval) {
		this.idNabcastval = idNabcastval;
	}

	/**
	 * @return the isLog
	 */
	public int getIsLog() {
		return this.isLog;
	}

	/**
	 * @param isLog the isLog to set
	 */
	public void setIsLog(int isLog) {
		this.isLog = isLog;
	}

	/**
	 * @return the langUser
	 */
	public String getLangUser() {
		return this.langUser;
	}

	/**
	 * @param langUser the langUser to set
	 */
	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	/**
	 * @return the listAllNabcastbyCateg
	 */
	public List getListAllNabcastbyCateg() {
		return this.listAllNabcastbyCateg;
	}

	/**
	 * @param listAllNabcastbyCateg the listAllNabcastbyCateg to set
	 */
	public void setListAllNabcastbyCateg(List listAllNabcastbyCateg) {
		this.listAllNabcastbyCateg = listAllNabcastbyCateg;
	}

	/**
	 * @return the listeAllSongNabcast
	 */
	public List getListeAllSongNabcast() {
		return this.listeAllSongNabcast;
	}

	/**
	 * @param listeAllSongNabcast the listeAllSongNabcast to set
	 */
	public void setListeAllSongNabcast(List listeAllSongNabcast) {
		this.listeAllSongNabcast = listeAllSongNabcast;
	}

	/**
	 * @return the listeAllSongNabcastAfter
	 */
	public List getListeAllSongNabcastAfter() {
		return this.listeAllSongNabcastAfter;
	}

	/**
	 * @param listeAllSongNabcastAfter the listeAllSongNabcastAfter to set
	 */
	public void setListeAllSongNabcastAfter(List listeAllSongNabcastAfter) {
		this.listeAllSongNabcastAfter = listeAllSongNabcastAfter;
	}

	/**
	 * @return the listeAnimation
	 */
	public List getListeAnimation() {
		return this.listeAnimation;
	}

	/**
	 * @param listeAnimation the listeAnimation to set
	 */
	public void setListeAnimation(List listeAnimation) {
		this.listeAnimation = listeAnimation;
	}

	/**
	 * @return the listeCategorie
	 */
	public List getListeCategorie() {
		return this.listeCategorie;
	}

	/**
	 * @param listeCategorie the listeCategorie to set
	 */
	public void setListeCategorie(List listeCategorie) {
		this.listeCategorie = listeCategorie;
	}

	/**
	 * @return the listeMusique
	 */
	public List getListeMusique() {
		return this.listeMusique;
	}

	/**
	 * @param listeMusique the listeMusique to set
	 */
	public void setListeMusique(List listeMusique) {
		this.listeMusique = listeMusique;
	}

	/**
	 * @return the listeNews
	 */
	public List getListeNews() {
		return this.listeNews;
	}

	/**
	 * @param listeNews the listeNews to set
	 */
	public void setListeNews(List listeNews) {
		this.listeNews = listeNews;
	}

	/**
	 * @return the listepagination
	 */
	public List getListepagination() {
		return this.listepagination;
	}

	/**
	 * @param listepagination the listepagination to set
	 */
	public void setListepagination(List listepagination) {
		this.listepagination = listepagination;
	}

	/**
	 * @return the listepagination2
	 */
	public List getListepagination2() {
		return this.listepagination2;
	}

	/**
	 * @param listepagination2 the listepagination2 to set
	 */
	public void setListepagination2(List listepagination2) {
		this.listepagination2 = listepagination2;
	}

	/**
	 * @return the listeTopNabcast
	 */
	public List getListeTopNabcast() {
		return this.listeTopNabcast;
	}

	/**
	 * @param listeTopNabcast the listeTopNabcast to set
	 */
	public void setListeTopNabcast(List listeTopNabcast) {
		this.listeTopNabcast = listeTopNabcast;
	}

	/**
	 * @return the listHeure
	 */
	public List getListHeure() {
		return this.listHeure;
	}

	/**
	 * @param listHeure the listHeure to set
	 */
	public void setListHeure(List listHeure) {
		this.listHeure = listHeure;
	}

	/**
	 * @return the listMinute
	 */
	public List getListMinute() {
		return this.listMinute;
	}

	/**
	 * @param listMinute the listMinute to set
	 */
	public void setListMinute(List listMinute) {
		this.listMinute = listMinute;
	}

	/**
	 * @return the listmp3
	 */
	public List getListmp3() {
		return this.listmp3;
	}

	/**
	 * @param listmp3 the listmp3 to set
	 */
	public void setListmp3(List listmp3) {
		this.listmp3 = listmp3;
	}

	/**
	 * @return the listNabcastCreate
	 */
	public List getListNabcastCreate() {
		return this.listNabcastCreate;
	}

	/**
	 * @param listNabcastCreate the listNabcastCreate to set
	 */
	public void setListNabcastCreate(List listNabcastCreate) {
		this.listNabcastCreate = listNabcastCreate;
	}

	/**
	 * @return the listNabcastSubscribe
	 */
	public List getListNabcastSubscribe() {
		return this.listNabcastSubscribe;
	}

	/**
	 * @param listNabcastSubscribe the listNabcastSubscribe to set
	 */
	public void setListNabcastSubscribe(List listNabcastSubscribe) {
		this.listNabcastSubscribe = listNabcastSubscribe;
	}

	/**
	 * @return the listNewNabcast
	 */
	public List getListNewNabcast() {
		return this.listNewNabcast;
	}

	/**
	 * @param listNewNabcast the listNewNabcast to set
	 */
	public void setListNewNabcast(List listNewNabcast) {
		this.listNewNabcast = listNewNabcast;
	}

	/**
	 * @return the minute
	 */
	public int getMinute() {
		return this.minute;
	}

	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * @return the minute_diff
	 */
	public String getMinute_diff() {
		return this.minute_diff;
	}

	/**
	 * @param minute_diff the minute_diff to set
	 */
	public void setMinute_diff(String minute_diff) {
		this.minute_diff = minute_diff;
	}

	/**
	 * @return the mode
	 */
	public int getMode() {
		return this.mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * @return the modeFull
	 */
	public int getModeFull() {
		return this.modeFull;
	}

	/**
	 * @param modeFull the modeFull to set
	 */
	public void setModeFull(int modeFull) {
		this.modeFull = modeFull;
	}

	/**
	 * @return the musicCut
	 */
	public String getMusicCut() {
		return this.musicCut;
	}

	/**
	 * @param musicCut the musicCut to set
	 */
	public void setMusicCut(String musicCut) {
		this.musicCut = musicCut;
	}

	/**
	 * @return the musicFile
	 */
	public FormFile getMusicFile() {
		return this.musicFile;
	}

	/**
	 * @param musicFile the musicFile to set
	 */
	public void setMusicFile(FormFile musicFile) {
		this.musicFile = musicFile;
	}

	/**
	 * @return the musicName
	 */
	public String getMusicName() {
		return this.musicName;
	}

	/**
	 * @param musicName the musicName to set
	 */
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	/**
	 * @return the musicTime
	 */
	public String getMusicTime() {
		return this.musicTime;
	}

	/**
	 * @param musicTime the musicTime to set
	 */
	public void setMusicTime(String musicTime) {
		this.musicTime = musicTime;
	}

	/**
	 * @return the nabcast_anim_sign
	 */
	public String getNabcast_anim_sign() {
		return this.nabcast_anim_sign;
	}

	/**
	 * @param nabcast_anim_sign the nabcast_anim_sign to set
	 */
	public void setNabcast_anim_sign(String nabcast_anim_sign) {
		this.nabcast_anim_sign = nabcast_anim_sign;
	}

	/**
	 * @return the nabcast_color_sign
	 */
	public String getNabcast_color_sign() {
		return this.nabcast_color_sign;
	}

	/**
	 * @param nabcast_color_sign the nabcast_color_sign to set
	 */
	public void setNabcast_color_sign(String nabcast_color_sign) {
		this.nabcast_color_sign = nabcast_color_sign;
	}

	/**
	 * @return the nabcast_music_sign
	 */
	public String getNabcast_music_sign() {
		return this.nabcast_music_sign;
	}

	/**
	 * @param nabcast_music_sign the nabcast_music_sign to set
	 */
	public void setNabcast_music_sign(String nabcast_music_sign) {
		this.nabcast_music_sign = nabcast_music_sign;
	}

	/**
	 * @return the nameCateg
	 */
	public String getNameCateg() {
		return this.nameCateg;
	}

	/**
	 * @param nameCateg the nameCateg to set
	 */
	public void setNameCateg(String nameCateg) {
		this.nameCateg = nameCateg;
	}

	/**
	 * @return the nbrAbonne
	 */
	public int getNbrAbonne() {
		return this.nbrAbonne;
	}

	/**
	 * @param nbrAbonne the nbrAbonne to set
	 */
	public void setNbrAbonne(int nbrAbonne) {
		this.nbrAbonne = nbrAbonne;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return this.action;
	}

	/**
	 * @param queFaire the queFaire to set
	 */
	public void setAction(String inValue) {
		this.action = inValue;
	}

	/**
	 * @return the redirect
	 */
	public int getRedirect() {
		return this.redirect;
	}

	/**
	 * @param redirect the redirect to set
	 */
	public void setRedirect(int redirect) {
		this.redirect = redirect;
	}

	/**
	 * @return the shiftPage
	 */
	public int getShiftPage() {
		return this.shiftPage;
	}

	/**
	 * @param shiftPage the shiftPage to set
	 */
	public void setShiftPage(int shiftPage) {
		this.shiftPage = shiftPage;
	}

	/**
	 * @return the shiftPage2
	 */
	public int getShiftPage2() {
		return this.shiftPage2;
	}

	/**
	 * @param shiftPage2 the shiftPage2 to set
	 */
	public void setShiftPage2(int shiftPage2) {
		this.shiftPage2 = shiftPage2;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return this.timezone;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the titreNabcast
	 */
	public String getTitreNabcast() {
		return this.titreNabcast;
	}

	/**
	 * @param titreNabcast the titreNabcast to set
	 */
	public void setTitreNabcast(String titreNabcast) {
		this.titreNabcast = titreNabcast;
	}

	/**
	 * @return the titreSongNabcast
	 */
	public String getTitreSongNabcast() {
		return this.titreSongNabcast;
	}

	/**
	 * @param titreSongNabcast the titreSongNabcast to set
	 */
	public void setTitreSongNabcast(String titreSongNabcast) {
		this.titreSongNabcast = titreSongNabcast;
	}

	/**
	 * @return the debug
	 */
	public int getDebug() {
		return this.debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(int debug) {
		this.debug = debug;
	}
}
