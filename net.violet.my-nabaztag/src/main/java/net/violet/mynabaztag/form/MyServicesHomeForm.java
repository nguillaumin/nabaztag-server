package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.violet.platform.dataobjects.MotherTongueLangData;
import net.violet.platform.dataobjects.NabcastCategData;
import net.violet.platform.dataobjects.NablifeServicesData;
import net.violet.platform.dataobjects.SrvCategData;
import net.violet.platform.util.StringShop;

public final class MyServicesHomeForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private String languser = StringShop.EMPTY_STRING;
	private long langCategorie;
	private long user_main;
	private long user_id;
	private int mode;
	private int idCateg;
	private int nabcastCateg;
	private int idmusic;
	private String nameCateg = StringShop.EMPTY_STRING;
	private List<SrvCategData> listeCategorie = new ArrayList<SrvCategData>();
	private List<NablifeServicesData> listServices = new ArrayList<NablifeServicesData>();
	private List<NabcastCategData> listeNabcastCateg = new ArrayList<NabcastCategData>();

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

	private final List<MotherTongueLangData> langList = new ArrayList<MotherTongueLangData>();

	public List<MotherTongueLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(Collection<MotherTongueLangData> langList) {
		this.langList.addAll(langList);
	}

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
	 * @return the listeCategorie
	 */
	public List<SrvCategData> getListeCategorie() {
		return this.listeCategorie;
	}

	/**
	 * @param listeCategorie the listeCategorie to set
	 */
	public void setListeCategorie(List<SrvCategData> listeCategorie) {
		this.listeCategorie = listeCategorie;
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
	 * @return the languser
	 */
	public String getLanguser() {
		return this.languser;
	}

	/**
	 * @param languser the languser to set
	 */
	public void setLanguser(String languser) {
		this.languser = languser;
	}

	/**
	 * @return the user_id
	 */
	public long getUser_id() {
		return this.user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_main
	 */
	public long getUser_main() {
		return this.user_main;
	}

	/**
	 * @param user_main the user_main to set
	 */
	public void setUser_main(long user_main) {
		this.user_main = user_main;
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

	public long getLangCategorie() {
		return this.langCategorie;
	}

	public void setLangCategorie(long langCategorie) {
		this.langCategorie = langCategorie;
	}

	public List<NablifeServicesData> getListServices() {
		return this.listServices;
	}

	public void setListServices(List<NablifeServicesData> listServices) {
		this.listServices = listServices;
	}

	public List<NabcastCategData> getListeNabcastCateg() {
		return this.listeNabcastCateg;
	}

	public void setListeNabcastCateg(List<NabcastCategData> listeNabcastCateg) {
		this.listeNabcastCateg = listeNabcastCateg;
	}

	public int getNabcastCateg() {
		return this.nabcastCateg;
	}

	public void setNabcastCateg(int nabcastCateg) {
		this.nabcastCateg = nabcastCateg;
	}
}
