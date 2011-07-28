package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.util.StringShop;


public final class MySrvTrafficForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private String depart = StringShop.EMPTY_STRING;
	private String arrivee = StringShop.EMPTY_STRING;
	private List<String> departList = new ArrayList<String>();
	private int isReg;
	private String horraire1 = StringShop.EMPTY_STRING;
	private String horraire2 = StringShop.EMPTY_STRING;
	private int weekEnd;
	private int lumiere;
	private int vocal;

	private String serviceName = StringShop.EMPTY_STRING;

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the isReg
	 */
	public int getIsReg() {
		return this.isReg;
	}

	/**
	 * @param isReg the isReg to set
	 */
	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	/**
	 * @return the departList
	 */
	public List<String> getDepartList() {
		return this.departList;
	}

	/**
	 * @param departList the departList to set
	 */
	public void setDepartList(List<String> departList) {
		this.departList = departList;
	}

	/**
	 * @return the horraire1
	 */
	public String getHorraire1() {
		return this.horraire1;
	}

	/**
	 * @param horraire1 the horraire1 to set
	 */
	public void setHorraire1(String horraire1) {
		this.horraire1 = horraire1;
	}

	/**
	 * @return the horraire2
	 */
	public String getHorraire2() {
		return this.horraire2;
	}

	/**
	 * @param horraire2 the horraire2 to set
	 */
	public void setHorraire2(String horraire2) {
		this.horraire2 = horraire2;
	}

	/**
	 * @return the lumiere
	 */
	public int getLumiere() {
		return this.lumiere;
	}

	/**
	 * @param lumiere the lumiere to set
	 */
	public void setLumiere(int lumiere) {
		this.lumiere = lumiere;
	}

	/**
	 * @return the vocal
	 */
	public int getVocal() {
		return this.vocal;
	}

	/**
	 * @param vocal the vocal to set
	 */
	public void setVocal(int vocal) {
		this.vocal = vocal;
	}

	/**
	 * @return the weekEnd
	 */
	public int getWeekEnd() {
		return this.weekEnd;
	}

	/**
	 * @param weekEnd the weekEnd to set
	 */
	public void setWeekEnd(int weekEnd) {
		this.weekEnd = weekEnd;
	}

	/**
	 * @param arrivee the arrivee to set
	 */
	public void setArrivee(String arrivee) {
		this.arrivee = arrivee;
	}

	/**
	 * @param depart the depart to set
	 */
	public void setDepart(String depart) {
		this.depart = depart;
	}

	/**
	 * @return the arrivee
	 */
	public String getArrivee() {
		return this.arrivee;
	}

	/**
	 * @return the depart
	 */
	public String getDepart() {
		return this.depart;
	}
}
