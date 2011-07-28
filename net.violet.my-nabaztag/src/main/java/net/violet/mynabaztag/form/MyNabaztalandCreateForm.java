package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.violet.platform.dataobjects.AnimData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.NabcastCategData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.util.StringShop;

public final class MyNabaztalandCreateForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int idNabcast;
	private int mode;
	private List<NabcastCategData> nabcast_categorieList = new ArrayList<NabcastCategData>();
	private Collection<ObjectLangData> langList = new ArrayList<ObjectLangData>();
	private List<MusicData> nabcast_soundList = new ArrayList<MusicData>();
	private List<AnimData> nabcast_animList = new ArrayList<AnimData>();
	private String nabcast_title = StringShop.EMPTY_STRING;
	private String nabcast_description = StringShop.EMPTY_STRING;
	private String colorPicker_value = StringShop.EMPTY_STRING;
	private String nabcast_categorie = "1"; // Arbitrairement si on n'a rien on prend cinéma (théoriquement on n'est jamais dans ce cas)
	private String nabcast_sound = "0";
	private String nabcast_anim = "0";
	private int nabcast_lang;

	private String nabcast_shortcut = StringShop.EMPTY_STRING;

	/**
	 * @return the nabcast_lang
	 */
	public int getNabcast_lang() {
		return this.nabcast_lang;
	}

	/**
	 * @param nabcast_lang the nabcast_lang to set
	 */
	public void setNabcast_lang(int nabcast_lang) {
		this.nabcast_lang = nabcast_lang;
	}

	/**
	 * @return the nabcast_description
	 */
	public String getNabcast_description() {
		return this.nabcast_description;
	}

	/**
	 * @param nabcast_description the nabcast_description to set
	 */
	public void setNabcast_description(String nabcast_description) {
		this.nabcast_description = nabcast_description;
	}

	/**
	 * @return the nabcast_anim
	 */
	public String getNabcast_anim() {
		return this.nabcast_anim;
	}

	/**
	 * @param nabcast_anim the nabcast_anim to set
	 */
	public void setNabcast_anim(String nabcast_anim) {
		this.nabcast_anim = nabcast_anim;
	}

	/**
	 * @return the nabcast_animList
	 */
	public List<AnimData> getNabcast_animList() {
		return this.nabcast_animList;
	}

	/**
	 * @param nabcast_animList the nabcast_animList to set
	 */
	public void setNabcast_animList(List<AnimData> nabcast_animList) {
		this.nabcast_animList = nabcast_animList;
	}

	/**
	 * @return the nabcast_categorie
	 */
	public String getNabcast_categorie() {
		return this.nabcast_categorie;
	}

	/**
	 * @param nabcast_categorie the nabcast_categorie to set
	 */
	public void setNabcast_categorie(String nabcast_categorie) {
		this.nabcast_categorie = nabcast_categorie;
	}

	/**
	 * @return the nabcast_categorieList
	 */
	public List<NabcastCategData> getNabcast_categorieList() {
		return this.nabcast_categorieList;
	}

	/**
	 * @param nabcast_categorieList the nabcast_categorieList to set
	 */
	public void setNabcast_categorieList(List<NabcastCategData> nabcast_categorieList) {
		this.nabcast_categorieList = nabcast_categorieList;
	}

	/**
	 * @return the nabcast_sound
	 */
	public String getNabcast_sound() {
		return this.nabcast_sound;
	}

	/**
	 * @param nabcast_sound the nabcast_sound to set
	 */
	public void setNabcast_sound(String nabcast_sound) {
		this.nabcast_sound = nabcast_sound;
	}

	/**
	 * @return the nabcast_soundList
	 */
	public List<MusicData> getNabcast_soundList() {
		return this.nabcast_soundList;
	}

	/**
	 * @param nabcast_soundList the nabcast_soundList to set
	 */
	public void setNabcast_soundList(List<MusicData> nabcast_soundList) {
		this.nabcast_soundList = nabcast_soundList;
	}

	/**
	 * @return the nabcast_title
	 */
	public String getNabcast_title() {
		return this.nabcast_title;
	}

	/**
	 * @param nabcast_title the nabcast_title to set
	 */
	public void setNabcast_title(String nabcast_title) {
		this.nabcast_title = nabcast_title;
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
	 * @return the colorPicker_value
	 */
	public String getColorPicker_value() {
		return this.colorPicker_value;
	}

	/**
	 * @param colorPicker_value the colorPicker_value to set
	 */
	public void setColorPicker_value(String colorPicker_value) {
		this.colorPicker_value = colorPicker_value;
	}

	public Collection<ObjectLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(Collection<ObjectLangData> langList) {
		this.langList = langList;
	}

	public String getNabcast_shortcut() {
		return this.nabcast_shortcut;
	}

	public void setNabcast_shortcut(String nabcast_shortcut) {
		this.nabcast_shortcut = nabcast_shortcut;
	}

}
