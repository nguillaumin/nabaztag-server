package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.util.StringShop;


public class MyMessagesListForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private String langUser = StringShop.EMPTY_STRING;
	private String action = StringShop.EMPTY_STRING;
	private String navig = StringShop.EMPTY_STRING;
	private String selectChoice = StringShop.EMPTY_STRING;
	private String[] checkListMsg;
	private int nabcast = 1;
	private long userId;
	private int index;
	private List listeMessages = new ArrayList();
	private String dateToday = StringShop.EMPTY_STRING;

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
	private int nombre_pages = 1; // nombre de pages
	private int page_courante; // page a afficher
	private int nbAffParPage; // nombre de message par page

	private String errorMsg = StringShop.EMPTY_STRING;

	/**
	 * @return the errorMsg
	 */
	public final String getErrorMsg() {
		return this.errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public final void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the nbAffParPage
	 */
	public final int getNbAffParPage() {
		return this.nbAffParPage;
	}

	/**
	 * @param nbAffParPage the nbAffParPage to set
	 */
	public final void setNbAffParPage(int nbAffParPage) {
		this.nbAffParPage = nbAffParPage;
	}

	/**
	 * @return the nombre_pages
	 */
	public final int getNombre_pages() {
		return this.nombre_pages;
	}

	/**
	 * @param nombre_pages the nombre_pages to set
	 */
	public final void setNombre_pages(int nombre_pages) {
		this.nombre_pages = nombre_pages;
	}

	/**
	 * @return the nombre_profils
	 */
	public final int getNombre_profils() {
		return this.nombre_profils;
	}

	/**
	 * @param nombre_profils the nombre_profils to set
	 */
	public final void setNombre_profils(int nombre_profils) {
		this.nombre_profils = nombre_profils;
	}

	/**
	 * @return the page_AffIndex
	 */
	public final int getPage_AffIndex() {
		return this.page_AffIndex;
	}

	/**
	 * @param page_AffIndex the page_AffIndex to set
	 */
	public final void setPage_AffIndex(int page_AffIndex) {
		this.page_AffIndex = page_AffIndex;
	}

	/**
	 * @return the page_AffIndexM
	 */
	public final int getPage_AffIndexM() {
		return this.page_AffIndexM;
	}

	/**
	 * @param page_AffIndexM the page_AffIndexM to set
	 */
	public final void setPage_AffIndexM(int page_AffIndexM) {
		this.page_AffIndexM = page_AffIndexM;
	}

	/**
	 * @return the page_AffIndexMM
	 */
	public final int getPage_AffIndexMM() {
		return this.page_AffIndexMM;
	}

	/**
	 * @param page_AffIndexMM the page_AffIndexMM to set
	 */
	public final void setPage_AffIndexMM(int page_AffIndexMM) {
		this.page_AffIndexMM = page_AffIndexMM;
	}

	/**
	 * @return the page_AffIndexP
	 */
	public final int getPage_AffIndexP() {
		return this.page_AffIndexP;
	}

	/**
	 * @param page_AffIndexP the page_AffIndexP to set
	 */
	public final void setPage_AffIndexP(int page_AffIndexP) {
		this.page_AffIndexP = page_AffIndexP;
	}

	/**
	 * @return the page_AffIndexPP
	 */
	public final int getPage_AffIndexPP() {
		return this.page_AffIndexPP;
	}

	/**
	 * @param page_AffIndexPP the page_AffIndexPP to set
	 */
	public final void setPage_AffIndexPP(int page_AffIndexPP) {
		this.page_AffIndexPP = page_AffIndexPP;
	}

	/**
	 * @return the page_courante
	 */
	public final int getPage_courante() {
		return this.page_courante;
	}

	/**
	 * @param page_courante the page_courante to set
	 */
	public final void setPage_courante(int page_courante) {
		this.page_courante = page_courante;
	}

	/**
	 * @return the page_index
	 */
	public final int getPage_index() {
		return this.page_index;
	}

	/**
	 * @param page_index the page_index to set
	 */
	public final void setPage_index(int page_index) {
		this.page_index = page_index;
	}

	/**
	 * @return the page_indexD
	 */
	public final int getPage_indexD() {
		return this.page_indexD;
	}

	/**
	 * @param page_indexD the page_indexD to set
	 */
	public final void setPage_indexD(int page_indexD) {
		this.page_indexD = page_indexD;
	}

	/**
	 * @return the page_indexF
	 */
	public final int getPage_indexF() {
		return this.page_indexF;
	}

	/**
	 * @param page_indexF the page_indexF to set
	 */
	public final void setPage_indexF(int page_indexF) {
		this.page_indexF = page_indexF;
	}

	/**
	 * @return the page_indexM
	 */
	public final int getPage_indexM() {
		return this.page_indexM;
	}

	/**
	 * @param page_indexM the page_indexM to set
	 */
	public final void setPage_indexM(int page_indexM) {
		this.page_indexM = page_indexM;
	}

	/**
	 * @return the page_indexMM
	 */
	public final int getPage_indexMM() {
		return this.page_indexMM;
	}

	/**
	 * @param page_indexMM the page_indexMM to set
	 */
	public final void setPage_indexMM(int page_indexMM) {
		this.page_indexMM = page_indexMM;
	}

	/**
	 * @return the page_indexP
	 */
	public final int getPage_indexP() {
		return this.page_indexP;
	}

	/**
	 * @param page_indexP the page_indexP to set
	 */
	public final void setPage_indexP(int page_indexP) {
		this.page_indexP = page_indexP;
	}

	/**
	 * @return the page_indexPP
	 */
	public final int getPage_indexPP() {
		return this.page_indexPP;
	}

	/**
	 * @param page_indexPP the page_indexPP to set
	 */
	public final void setPage_indexPP(int page_indexPP) {
		this.page_indexPP = page_indexPP;
	}

	/**
	 * @return the page_new
	 */
	public final int getPage_new() {
		return this.page_new;
	}

	/**
	 * @param page_new the page_new to set
	 */
	public final void setPage_new(int page_new) {
		this.page_new = page_new;
	}

	public String[] getCheckListMsg() {
		return this.checkListMsg;
	}

	public void setCheckListMsg(String[] checkListMsg) {
		this.checkListMsg = checkListMsg;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLangUser() {
		return this.langUser;
	}

	public void setLangUser(String langUser) {
		this.langUser = langUser;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String inValue) {
		this.action = inValue;
	}

	public List getListeMessages() {
		return this.listeMessages;
	}

	public void setListeMessages(List inValue) {
		this.listeMessages = inValue;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getNavig() {
		return this.navig;
	}

	public void setNavig(String navig) {
		this.navig = navig;
	}

	public int getNabcast() {
		return this.nabcast;
	}

	public void setNabcast(int nabcast) {
		this.nabcast = nabcast;
	}

	public String getSelectChoice() {
		return this.selectChoice;
	}

	public void setSelectChoice(String selectChoice) {
		this.selectChoice = selectChoice;
	}

	/**
	 * @return the dateToday
	 */
	public final String getDateToday() {
		return this.dateToday;
	}

	/**
	 * @param dateToday the dateToday to set
	 */
	public final void setDateToday(String dateToday) {
		this.dateToday = dateToday;
	}
}
