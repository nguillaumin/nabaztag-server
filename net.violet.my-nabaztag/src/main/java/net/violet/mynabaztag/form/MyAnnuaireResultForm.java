package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.UserData;
import net.violet.platform.util.StringShop;

public class MyAnnuaireResultForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	/* LA RECHERCHE */
	private String agemin = StringShop.EMPTY_STRING;
	private String agemax = StringShop.EMPTY_STRING;
	private String sexe = StringShop.EMPTY_STRING;
	private String fullfriend = StringShop.EMPTY_STRING; // afficher que les full friend rabbit
	private String pseudo = StringShop.EMPTY_STRING;
	private String ville = StringShop.EMPTY_STRING;
	private String pays = StringShop.EMPTY_STRING;
	private String type_tri = StringShop.EMPTY_STRING;
	/*
	 * 0 : tri par odre d'arrive 1 : tri par ordre alphabtique 2 : tri par age 3
	 * : tri par sexe 4 : tri par ville 5 : tri par pays 6 : tri par status
	 */
	private String typeTri = StringShop.EMPTY_STRING;
	/*
	 * ASC ou DESC
	 */

	/* LE USER LOGUE */
	private String langUser = StringShop.EMPTY_STRING;

	/* GESTION DES PAGES */
	private int page_indexD; // Page debut
	private int page_indexMM; // Page en cours -2
	private int page_indexM; // Page en cours -1
	private int page_index; // Page en cours et page de depart
	private int page_indexP; // Page en cours +1
	private int page_indexPP; // Page en cours +2
	private int page_indexF; // Page fin
	private int page_new; // Page de destination
	private int page_AffIndexMM; // Page en cours -2
	private int page_AffIndexM; // Page en cours -1
	private int page_AffIndex; // Page en cours et page de depart
	private int page_AffIndexP; // Page en cours +1
	private int page_AffIndexPP; // Page en cours +2

	private int nombre_profils; // nombre de profils trouves
	private int nombre_pages; // nombre de pages trouves
	private int page_courante; // page afficher
	private int nbAffParPage;

	private List<UserData> listeResultProfil = new ArrayList<UserData>();

	/**
	 * @return Returns the page_indexM.
	 */
	public int getPage_indexM() {
		return this.page_indexM;
	}

	/**
	 * @param page_indexM The page_indexM to set.
	 */
	public void setPage_indexM(int page_indexM) {
		this.page_indexM = page_indexM;
	}

	/**
	 * @return Returns the page_indexMM.
	 */
	public int getPage_indexMM() {
		return this.page_indexMM;
	}

	/**
	 * @param page_indexMM The page_indexMM to set.
	 */
	public void setPage_indexMM(int page_indexMM) {
		this.page_indexMM = page_indexMM;
	}

	/**
	 * @return Returns the page_indexP.
	 */
	public int getPage_indexP() {
		return this.page_indexP;
	}

	/**
	 * @param page_indexP The page_indexP to set.
	 */
	public void setPage_indexP(int page_indexP) {
		this.page_indexP = page_indexP;
	}

	/**
	 * @return Returns the page_indexPP.
	 */
	public int getPage_indexPP() {
		return this.page_indexPP;
	}

	/**
	 * @param page_indexPP The page_indexPP to set.
	 */
	public void setPage_indexPP(int page_indexPP) {
		this.page_indexPP = page_indexPP;
	}

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
	 * @return Returns the listeResultProfil.
	 */
	public List<UserData> getListeResultProfil() {
		return this.listeResultProfil;
	}

	/**
	 * @param listeResultProfil The listeResultProfil to set.
	 */
	public void setListeResultProfil(List<UserData> listeResultProfil) {
		this.listeResultProfil = listeResultProfil;
	}

	/**
	 * @return Returns the nombre_pages.
	 */
	public int getNombre_pages() {
		return this.nombre_pages;
	}

	/**
	 * @param nombre_pages The nombre_pages to set.
	 */
	public void setNombre_pages(int nombre_pages) {
		this.nombre_pages = nombre_pages;
	}

	/**
	 * @return Returns the nombre_profils.
	 */
	public int getNombre_profils() {
		return this.nombre_profils;
	}

	/**
	 * @param nombre_profils The nombre_profils to set.
	 */
	public void setNombre_profils(int nombre_profils) {
		this.nombre_profils = nombre_profils;
	}

	/**
	 * @return Returns the page_courante.
	 */
	public int getPage_courante() {
		return this.page_courante;
	}

	/**
	 * @param page_courante The page_courante to set.
	 */
	public void setPage_courante(int page_courante) {
		this.page_courante = page_courante;
	}

	/**
	 * @return Returns the page_index.
	 */
	public int getPage_index() {
		return this.page_index;
	}

	/**
	 * @param page_index The page_index to set.
	 */
	public void setPage_index(int page_index) {
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
	 * @return Returns the nbAffParPage.
	 */
	public int getNbAffParPage() {
		return this.nbAffParPage;
	}

	/**
	 * @param nbAffParPage The nbAffParPage to set.
	 */
	public void setNbAffParPage(int nbAffParPage) {
		this.nbAffParPage = nbAffParPage;
	}

	/**
	 * @return Returns the page_indexD.
	 */
	public int getPage_indexD() {
		return this.page_indexD;
	}

	/**
	 * @param page_indexD The page_indexD to set.
	 */
	public void setPage_indexD(int page_indexD) {
		this.page_indexD = page_indexD;
	}

	/**
	 * @return Returns the page_indexF.
	 */
	public int getPage_indexF() {
		return this.page_indexF;
	}

	/**
	 * @param page_indexF The page_indexF to set.
	 */
	public void setPage_indexF(int page_indexF) {
		this.page_indexF = page_indexF;
	}

	/**
	 * @return Returns the page_new.
	 */
	public int getPage_new() {
		return this.page_new;
	}

	/**
	 * @param page_new The page_new to set.
	 */
	public void setPage_new(int page_new) {
		this.page_new = page_new;
	}

	/**
	 * @return Returns the page_AffIndex.
	 */
	public int getPage_AffIndex() {
		return this.page_AffIndex;
	}

	/**
	 * @param page_AffIndex The page_AffIndex to set.
	 */
	public void setPage_AffIndex(int page_AffIndex) {
		this.page_AffIndex = page_AffIndex;
	}

	/**
	 * @return Returns the page_AffIndexM.
	 */
	public int getPage_AffIndexM() {
		return this.page_AffIndexM;
	}

	/**
	 * @param page_AffIndexM The page_AffIndexM to set.
	 */
	public void setPage_AffIndexM(int page_AffIndexM) {
		this.page_AffIndexM = page_AffIndexM;
	}

	/**
	 * @return Returns the page_AffIndexMM.
	 */
	public int getPage_AffIndexMM() {
		return this.page_AffIndexMM;
	}

	/**
	 * @param page_AffIndexMM The page_AffIndexMM to set.
	 */
	public void setPage_AffIndexMM(int page_AffIndexMM) {
		this.page_AffIndexMM = page_AffIndexMM;
	}

	/**
	 * @return Returns the page_AffIndexP.
	 */
	public int getPage_AffIndexP() {
		return this.page_AffIndexP;
	}

	/**
	 * @param page_AffIndexP The page_AffIndexP to set.
	 */
	public void setPage_AffIndexP(int page_AffIndexP) {
		this.page_AffIndexP = page_AffIndexP;
	}

	/**
	 * @return Returns the page_AffIndexPP.
	 */
	public int getPage_AffIndexPP() {
		return this.page_AffIndexPP;
	}

	/**
	 * @param page_AffIndexPP The page_AffIndexPP to set.
	 */
	public void setPage_AffIndexPP(int page_AffIndexPP) {
		this.page_AffIndexPP = page_AffIndexPP;
	}
}
