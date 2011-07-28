package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.NabcastValData;
import net.violet.platform.dataobjects.UserData;

public final class MyNabaztalandHistoForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int idNabcast;
	private UserData userData = UserData.getData(null);
	private int user_id;
	private int user_main;
	private List<NabcastValData> listeNabcastval = new ArrayList<NabcastValData>();
	private NabcastData nabcastData = new NabcastData();

	/* USAGE: MULTI PAGE */
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
	private int nombre_profils; // nombre de message par page
	private int nombre_pages; // nombre de pages
	private int page_courante; // page a afficher
	private int nbAffParPage; // nombre de message par page

	/**
	 * @return the nbAffParPage
	 */
	public int getNbAffParPage() {
		return this.nbAffParPage;
	}

	/**
	 * @param nbAffParPage the nbAffParPage to set
	 */
	public void setNbAffParPage(int nbAffParPage) {
		this.nbAffParPage = nbAffParPage;
	}

	/**
	 * @return the nombre_pages
	 */
	public int getNombre_pages() {
		return this.nombre_pages;
	}

	/**
	 * @param nombre_pages the nombre_pages to set
	 */
	public void setNombre_pages(int nombre_pages) {
		this.nombre_pages = nombre_pages;
	}

	/**
	 * @return the nombre_profils
	 */
	public int getNombre_profils() {
		return this.nombre_profils;
	}

	/**
	 * @param nombre_profils the nombre_profils to set
	 */
	public void setNombre_profils(int nombre_profils) {
		this.nombre_profils = nombre_profils;
	}

	/**
	 * @return the page_AffIndex
	 */
	public int getPage_AffIndex() {
		return this.page_AffIndex;
	}

	/**
	 * @param page_AffIndex the page_AffIndex to set
	 */
	public void setPage_AffIndex(int page_AffIndex) {
		this.page_AffIndex = page_AffIndex;
	}

	/**
	 * @return the page_AffIndexM
	 */
	public int getPage_AffIndexM() {
		return this.page_AffIndexM;
	}

	/**
	 * @param page_AffIndexM the page_AffIndexM to set
	 */
	public void setPage_AffIndexM(int page_AffIndexM) {
		this.page_AffIndexM = page_AffIndexM;
	}

	/**
	 * @return the page_AffIndexMM
	 */
	public int getPage_AffIndexMM() {
		return this.page_AffIndexMM;
	}

	/**
	 * @param page_AffIndexMM the page_AffIndexMM to set
	 */
	public void setPage_AffIndexMM(int page_AffIndexMM) {
		this.page_AffIndexMM = page_AffIndexMM;
	}

	/**
	 * @return the page_AffIndexP
	 */
	public int getPage_AffIndexP() {
		return this.page_AffIndexP;
	}

	/**
	 * @param page_AffIndexP the page_AffIndexP to set
	 */
	public void setPage_AffIndexP(int page_AffIndexP) {
		this.page_AffIndexP = page_AffIndexP;
	}

	/**
	 * @return the page_AffIndexPP
	 */
	public int getPage_AffIndexPP() {
		return this.page_AffIndexPP;
	}

	/**
	 * @param page_AffIndexPP the page_AffIndexPP to set
	 */
	public void setPage_AffIndexPP(int page_AffIndexPP) {
		this.page_AffIndexPP = page_AffIndexPP;
	}

	/**
	 * @return the page_courante
	 */
	public int getPage_courante() {
		return this.page_courante;
	}

	/**
	 * @param page_courante the page_courante to set
	 */
	public void setPage_courante(int page_courante) {
		this.page_courante = page_courante;
	}

	/**
	 * @return the page_index
	 */
	public int getPage_index() {
		return this.page_index;
	}

	/**
	 * @param page_index the page_index to set
	 */
	public void setPage_index(int page_index) {
		this.page_index = page_index;
	}

	/**
	 * @return the page_indexD
	 */
	public int getPage_indexD() {
		return this.page_indexD;
	}

	/**
	 * @param page_indexD the page_indexD to set
	 */
	public void setPage_indexD(int page_indexD) {
		this.page_indexD = page_indexD;
	}

	/**
	 * @return the page_indexF
	 */
	public int getPage_indexF() {
		return this.page_indexF;
	}

	/**
	 * @param page_indexF the page_indexF to set
	 */
	public void setPage_indexF(int page_indexF) {
		this.page_indexF = page_indexF;
	}

	/**
	 * @return the page_indexM
	 */
	public int getPage_indexM() {
		return this.page_indexM;
	}

	/**
	 * @param page_indexM the page_indexM to set
	 */
	public void setPage_indexM(int page_indexM) {
		this.page_indexM = page_indexM;
	}

	/**
	 * @return the page_indexMM
	 */
	public int getPage_indexMM() {
		return this.page_indexMM;
	}

	/**
	 * @param page_indexMM the page_indexMM to set
	 */
	public void setPage_indexMM(int page_indexMM) {
		this.page_indexMM = page_indexMM;
	}

	/**
	 * @return the page_indexP
	 */
	public int getPage_indexP() {
		return this.page_indexP;
	}

	/**
	 * @param page_indexP the page_indexP to set
	 */
	public void setPage_indexP(int page_indexP) {
		this.page_indexP = page_indexP;
	}

	/**
	 * @return the page_indexPP
	 */
	public int getPage_indexPP() {
		return this.page_indexPP;
	}

	/**
	 * @param page_indexPP the page_indexPP to set
	 */
	public void setPage_indexPP(int page_indexPP) {
		this.page_indexPP = page_indexPP;
	}

	/**
	 * @return the page_new
	 */
	public int getPage_new() {
		return this.page_new;
	}

	/**
	 * @param page_new the page_new to set
	 */
	public void setPage_new(int page_new) {
		this.page_new = page_new;
	}

	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return this.user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_main
	 */
	public int getUser_main() {
		return this.user_main;
	}

	/**
	 * @param user_main the user_main to set
	 */
	public void setUser_main(int user_main) {
		this.user_main = user_main;
	}

	/**
	 * @return the userData
	 */
	public UserData getUserData() {
		return this.userData;
	}

	/**
	 * @param data the userData to set
	 */
	public void setUser(UserData userData) {
		this.userData = userData;
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
	 * @return the listeNabcastval
	 */
	public List<NabcastValData> getListeNabcastval() {
		return this.listeNabcastval;
	}

	/**
	 * @param listeNabcastval the listeNabcastval to set
	 */
	public void setListeNabcastval(List<NabcastValData> listeNabcastval) {
		this.listeNabcastval = listeNabcastval;
	}

	/**
	 * @return the nabcastData
	 */
	public NabcastData getNabcastData() {
		return this.nabcastData;
	}

	/**
	 * @param nabcastData the nabcastData to set
	 */
	public void setNabcastData(NabcastData nabcastData) {
		this.nabcastData = nabcastData;
	}
}
