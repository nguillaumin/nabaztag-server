package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.util.StringShop;

public class MyMessagesBiblioChoiceForm extends AbstractForm {

	// test de cette constante
	public static final long serialVersionUID = 1;

	private int idBiblio;
	private String nameBiblio = StringShop.EMPTY_STRING;
	private String langUser = StringShop.EMPTY_STRING;
	private String langBiblio = StringShop.EMPTY_STRING;
	private List<MusicData> listeBiblio = new ArrayList<MusicData>();

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
	public List<MusicData> getListeBiblio() {
		return this.listeBiblio;
	}

	/**
	 * @param listeCatBiblio The listeCatBiblio to set.
	 */
	public void setListeBiblio(List<MusicData> listeBiblio) {
		this.listeBiblio = listeBiblio;
	}

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
}
