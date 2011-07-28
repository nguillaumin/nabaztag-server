package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MotherTongueLangData;
import net.violet.platform.dataobjects.MusicStyleData;
import net.violet.platform.util.StringShop;

public class MyMessagesClinForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int idClin;
	private String nameClin = StringShop.EMPTY_STRING;
	private String langUser = StringShop.EMPTY_STRING;
	private String langClin = StringShop.EMPTY_STRING;
	private List<MusicStyleData> listeCatClin = new ArrayList<MusicStyleData>();
	private List<MotherTongueLangData> langList = new ArrayList<MotherTongueLangData>();

	public List<MotherTongueLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(List<MotherTongueLangData> langList) {
		this.langList = langList;
	}

	/**
	 * @return Returns the langClin.
	 */
	public String getLangClin() {
		return this.langClin;
	}

	/**
	 * @param langClin The langClin to set.
	 */
	public void setLangClin(String langClin) {
		this.langClin = langClin;
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
	 * @return Returns the listeCatClin.
	 */
	public List<MusicStyleData> getListeCatClin() {
		return this.listeCatClin;
	}

	/**
	 * @param listeCatClin The listeCatClin to set.
	 */
	public void setListeCatClin(List<MusicStyleData> listeCatClin) {
		this.listeCatClin = listeCatClin;
	}

	/**
	 * @return Returns the idClin.
	 */
	public int getIdClin() {
		return this.idClin;
	}

	/**
	 * @param idClin The idClin to set.
	 */
	public void setIdClin(int idClin) {
		this.idClin = idClin;
	}

	/**
	 * @return Returns the nameClin.
	 */
	public String getNameClin() {
		return this.nameClin;
	}

	/**
	 * @param nameClin The nameClin to set.
	 */
	public void setNameClin(String nameClin) {
		this.nameClin = nameClin;
	}
}
