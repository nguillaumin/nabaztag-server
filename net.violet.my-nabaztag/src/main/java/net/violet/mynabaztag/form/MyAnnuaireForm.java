package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.AnnuData;
import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyAnnuaireForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private String agemin = StringShop.EMPTY_STRING;
	private String agemax = StringShop.EMPTY_STRING;
	private String sexe = StringShop.EMPTY_STRING;
	private String fullfriend = StringShop.EMPTY_STRING; // afficher que les full friend rabbit
	private String pseudo = StringShop.EMPTY_STRING;
	private String ville = StringShop.EMPTY_STRING;
	private String pays = StringShop.EMPTY_STRING;

	private String langUser = StringShop.EMPTY_STRING;

	private List<AnnuData> listeVille = new ArrayList<AnnuData>();
	private List<CountryData> listePays = new ArrayList<CountryData>();
	private List<UserData> listeLastProfil = new ArrayList<UserData>();

	// private int page_new = 1; // Page de destination Inutile?
	private String type_tri = StringShop.EMPTY_STRING;
	/*
	 * 0 : tri par odre d'arrive 1 : tri par ordre alphabtique 2 : tri par age 3
	 * : tri par sexe 4 : tri par ville 5 : tri par pays 6 : tri par status
	 */
	private String typeTri = StringShop.EMPTY_STRING;
	/*
	 * ASC ou DESC
	 */
	private String page_index = StringShop.EMPTY_STRING; // Page en cours et page de depart
	private String nbAffParPage = StringShop.EMPTY_STRING;

	private String session = StringShop.EMPTY_STRING;

	/**
	 * @return Returns the agemax.
	 */
	public String getAgemax() {
		return this.agemax;
	}

	/**
	 * @param agemax The agemax to set.
	 */
	public void setAgemax(String agemax) {
		this.agemax = agemax;
	}

	/**
	 * @return Returns the agemin.
	 */
	public String getAgemin() {
		return this.agemin;
	}

	/**
	 * @param agemin The agemin to set.
	 */
	public void setAgemin(String agemin) {
		this.agemin = agemin;
	}

	/**
	 * @return Returns the fullfriend.
	 */
	public String getFullfriend() {
		return this.fullfriend;
	}

	/**
	 * @param fullfriend The fullfriend to set.
	 */
	public void setFullfriend(String fullfriend) {
		this.fullfriend = fullfriend;
	}

	/**
	 * @return Returns the langUser.
	 */
	public String getLangUser() {
		return this.langUser;
	}

	/**
	 * @param langUser The langUser to set.
	 */
	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	/**
	 * @return Returns the listeLastProfil.
	 */
	public List<UserData> getListeLastProfil() {
		return this.listeLastProfil;
	}

	/**
	 * @param listeLastProfil The listeLastProfil to set.
	 */
	public void setListeLastProfil(List<UserData> listeLastProfil) {
		this.listeLastProfil = listeLastProfil;
	}

	/**
	 * @return Returns the listePays.
	 */
	public List<CountryData> getListePays() {
		return this.listePays;
	}

	/**
	 * @param listePays The listePays to set.
	 */
	public void setListePays(List<CountryData> listePays) {
		this.listePays = listePays;
	}

	/**
	 * @return Returns the listeVille.
	 */
	public List<AnnuData> getListeVille() {
		return this.listeVille;
	}

	/**
	 * @param listeVille The listeVille to set.
	 */
	public void setListeVille(List<AnnuData> listeVille) {
		this.listeVille = listeVille;
	}

	/**
	 * @return Returns the nbAffParPage.
	 */
	public String getNbAffParPage() {
		return this.nbAffParPage;
	}

	/**
	 * @param nbAffParPage The nbAffParPage to set.
	 */
	public void setNbAffParPage(String nbAffParPage) {
		this.nbAffParPage = nbAffParPage;
	}

	/**
	 * @return Returns the page_index.
	 */
	public String getPage_index() {
		return this.page_index;
	}

	/**
	 * @param page_index The page_index to set.
	 */
	public void setPage_index(String page_index) {
		this.page_index = page_index;
	}

	/**
	 * @return Returns the pays.
	 */
	public String getPays() {
		return this.pays;
	}

	/**
	 * @param pays The pays to set.
	 */
	public void setPays(String pays) {
		this.pays = pays;
	}

	/**
	 * @return Returns the pseudo.
	 */
	public String getPseudo() {
		return this.pseudo;
	}

	/**
	 * @param pseudo The pseudo to set.
	 */
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * @return Returns the session.
	 */
	public String getSession() {
		return this.session;
	}

	/**
	 * @param session The session to set.
	 */
	public void setSession(String session) {
		this.session = session;
	}

	/**
	 * @return Returns the sexe.
	 */
	public String getSexe() {
		return this.sexe;
	}

	/**
	 * @param sexe The sexe to set.
	 */
	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	/**
	 * @return Returns the type_tri.
	 */
	public String getType_tri() {
		return this.type_tri;
	}

	/**
	 * @param type_tri The type_tri to set.
	 */
	public void setType_tri(String type_tri) {
		this.type_tri = type_tri;
	}

	/**
	 * @return Returns the typeTri.
	 */
	public String getTypeTri() {
		return this.typeTri;
	}

	/**
	 * @param typeTri The typeTri to set.
	 */
	public void setTypeTri(String typeTri) {
		this.typeTri = typeTri;
	}

	/**
	 * @return Returns the ville.
	 */
	public String getVille() {
		return this.ville;
	}

	/**
	 * @param ville The ville to set.
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}
}
