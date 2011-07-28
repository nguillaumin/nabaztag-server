package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.CountryData;
import net.violet.platform.util.StringShop;

public class MyTerrierMyselfForm extends AbstractForm {

	private static final long serialVersionUID = -8769929452401683835L;

	private String firstName = StringShop.EMPTY_STRING;
	private String lastName = StringShop.EMPTY_STRING;

	private int change;

	private String jour = StringShop.EMPTY_STRING;
	private String mois = StringShop.EMPTY_STRING;
	private String annee = StringShop.EMPTY_STRING;
	private String annuCp = StringShop.EMPTY_STRING;
	private String annuCity = StringShop.EMPTY_STRING;
	private String annuCountry = StringShop.EMPTY_STRING;
	private char annuSexe;
	private int annuConfirm;
	private List listeJour;
	private List listeMois;
	private List listeAnnee;
	private List<CountryData> listePays = new ArrayList<CountryData>();

	private String rabbitName = StringShop.EMPTY_STRING;
	private String rabbitPicture = StringShop.EMPTY_STRING;
	private String rabbitAnnounce = StringShop.EMPTY_STRING;
	private String rabbitMail = StringShop.EMPTY_STRING;

	public String getRabbitAnnounce() {
		return this.rabbitAnnounce;
	}

	public void setRabbitAnnounce(String rabbitAnnounce) {
		this.rabbitAnnounce = rabbitAnnounce;
	}

	public String getRabbitMail() {
		return this.rabbitMail;
	}

	public void setRabbitMail(String rabbitMail) {
		this.rabbitMail = rabbitMail;
	}

	public String getRabbitName() {
		return this.rabbitName;
	}

	public void setRabbitName(String rabbitName) {
		this.rabbitName = rabbitName;
	}

	public String getRabbitPicture() {
		return this.rabbitPicture;
	}

	public void setRabbitPicture(String rabbitPicture) {
		this.rabbitPicture = rabbitPicture;
	}

	public int getChange() {
		return this.change;
	}

	public void setChange(int change) {
		this.change = change;
	}

	public String getJour() {
		return this.jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public String getMois() {
		return this.mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public String getAnnee() {
		return this.annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public String getAnnuCp() {
		return this.annuCp;
	}

	public void setAnnuCp(String annuCp) {
		this.annuCp = annuCp;
	}

	public String getAnnuCity() {
		return this.annuCity;
	}

	public void setAnnuCity(String annuCity) {
		this.annuCity = annuCity;
	}

	public String getAnnuCountry() {
		return this.annuCountry;
	}

	public void setAnnuCountry(String annuCountry) {
		this.annuCountry = annuCountry;
	}

	public int getAnnuConfirm() {
		return this.annuConfirm;
	}

	public void setAnnuConfirm(int annuConfirm) {
		this.annuConfirm = annuConfirm;
	}

	public List getListeJour() {
		return this.listeJour;
	}

	public void setListeJour(List listeJour) {
		this.listeJour = listeJour;
	}

	public List getListeMois() {
		return this.listeMois;
	}

	public void setListeMois(List listeMois) {
		this.listeMois = listeMois;
	}

	public List getListeAnnee() {
		return this.listeAnnee;
	}

	public void setListeAnnee(List listeAnnee) {
		this.listeAnnee = listeAnnee;
	}

	public List<CountryData> getListePays() {
		return this.listePays;
	}

	public void setListePays(List<CountryData> listePays) {
		this.listePays = listePays;
	}

	public char getAnnuSexe() {
		return this.annuSexe;
	}

	public void setAnnuSexe(char annuSexe) {
		this.annuSexe = annuSexe;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
