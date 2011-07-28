package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MusicStyleData;
import net.violet.platform.util.StringShop;

public class MyMessagesBiblioForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int idBiblio;
	private String nameBiblio = StringShop.EMPTY_STRING;
	private String langUser = StringShop.EMPTY_STRING;
	private String langBiblio = StringShop.EMPTY_STRING;
	private List<MusicStyleData> listeCatBiblio = new ArrayList<MusicStyleData>();

	/**
	 * @return Returns the idBiblio.
	 */
	public int getIdBiblio() {
		return this.idBiblio;
	}

	/**
	 * @param idBiblio The idBiblio to set.
	 */
	public void setIdBiblio(int idBiblio) {
		this.idBiblio = idBiblio;
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
	 * @return Returns the listeCatBiblio.
	 */
	public List<MusicStyleData> getListeCatBiblio() {
		return this.listeCatBiblio;
	}

	/**
	 * @param listeCatBiblio The listeCatBiblio to set.
	 */
	public void setListeCatBiblio(List<MusicStyleData> listeCatBiblio) {
		this.listeCatBiblio = listeCatBiblio;
	}

	/**
	 * @return Returns the nameBiblio.
	 */
	public String getNameBiblio() {
		return this.nameBiblio;
	}

	/**
	 * @param nameBiblio The nameBiblio to set.
	 */
	public void setNameBiblio(String nameBiblio) {
		this.nameBiblio = nameBiblio;
	}

	/**
	 * @return Returns the langBiblio.
	 */
	public String getLangBiblio() {
		return this.langBiblio;
	}

	/**
	 * @param langBiblio The langBiblio to set.
	 */
	public void setLangBiblio(String langBiblio) {
		this.langBiblio = langBiblio;
	}
}
