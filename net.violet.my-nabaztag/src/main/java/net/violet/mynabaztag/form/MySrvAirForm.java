package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.violet.platform.dataobjects.FrequenceData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.util.StringShop;

public final class MySrvAirForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private List<FrequenceData> villeList = new ArrayList<FrequenceData>();
	private String ville = StringShop.EMPTY_STRING;
	private String horraire1 = StringShop.EMPTY_STRING;
	private String horraire2 = StringShop.EMPTY_STRING;
	private Collection<ObjectLangData> langList = new ArrayList<ObjectLangData>();
	private int langSrv;
	private int isReg;
	private int lumiere;
	private int vocal;

	public Collection<ObjectLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(Collection<ObjectLangData> langList) {
		this.langList = langList;
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
	 * @return the ville
	 */
	public String getVille() {
		return this.ville;
	}

	/**
	 * @param ville the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @return the villeList
	 */
	public List<FrequenceData> getVilleList() {
		return this.villeList;
	}

	/**
	 * @param villeList the villeList to set
	 */
	public void setVilleList(List<FrequenceData> villeList) {
		this.villeList = villeList;
	}

	/**
	 * @return the langSrv
	 */
	public int getLangSrv() {
		return this.langSrv;
	}

	/**
	 * @param langSrv the langSrv to set
	 */
	public void setLangSrv(int langSrv) {
		this.langSrv = langSrv;
	}

	public void setVocal(int vocal) {
		this.vocal = vocal;
	}

	public int getVocal() {
		return this.vocal;
	}
}
