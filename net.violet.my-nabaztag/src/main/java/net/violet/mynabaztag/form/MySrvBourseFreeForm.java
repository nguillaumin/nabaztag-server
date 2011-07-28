package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.FrequenceData;
import net.violet.platform.util.StringShop;

public final class MySrvBourseFreeForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int isReg;

	private String indic = StringShop.EMPTY_STRING;
	private List<FrequenceData> indicList = new ArrayList<FrequenceData>();

	private String horraire1 = StringShop.EMPTY_STRING;
	private String horraire2 = StringShop.EMPTY_STRING;

	private int weekend;
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
	 * @return the indicList
	 */
	public List<FrequenceData> getIndicList() {
		return this.indicList;
	}

	/**
	 * @param indicList the indicList to set
	 */
	public void setIndicList(List<FrequenceData> indicList) {
		this.indicList = indicList;
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
	 * @return the weekend
	 */
	public int getWeekend() {
		return this.weekend;
	}

	/**
	 * @param weekend the weekend to set
	 */
	public void setWeekend(int weekend) {
		this.weekend = weekend;
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
	 * @return the indic
	 */
	public String getIndic() {
		return this.indic;
	}

	/**
	 * @param indic the indic to set
	 */
	public void setIndic(String indic) {
		this.indic = indic;
	}
}
