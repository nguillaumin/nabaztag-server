package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.mynabaztag.action.MyNathanHomeAction.OtherNathanData;
import net.violet.mynabaztag.data.PlayerData;
import net.violet.platform.dataobjects.NathanTagData;
import net.violet.platform.dataobjects.NathanVersionData;
import net.violet.platform.util.StringShop;

public class MyNathanHomeForm extends AbstractForm {

	private long appletId;
	private Long isbn = 0L;
	private int isMarkup;
	private List<NathanVersionData> resultList = new ArrayList<NathanVersionData>();
	private String[] checkedTags = new String[0];
	private String dicoRoot = StringShop.EMPTY_STRING;
	private List<PlayerData> mySetting = new ArrayList<PlayerData>();
	private String bookSerial = StringShop.EMPTY_STRING;
	private long versionId;
	private String[] selectedVersions = new String[0];

	private List<NathanTagData> sexTags = new ArrayList<NathanTagData>();
	private List<NathanTagData> interpretationTags = new ArrayList<NathanTagData>();
	private List<NathanTagData> effectsTags = new ArrayList<NathanTagData>();
	private List<NathanTagData> accentTags = new ArrayList<NathanTagData>();

	private String serial = StringShop.EMPTY_STRING;
	private long picture;

	private List<OtherNathanData> otherBooksList;
	private String mObjectLogin;

	public long getAppletId() {
		return this.appletId;
	}

	public void setAppletId(long appletId) {
		this.appletId = appletId;
	}

	public Long getIsbn() {
		return this.isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public void setIsMarkup(int isMarkup) {
		this.isMarkup = isMarkup;
	}

	public int getIsMarkup() {
		return this.isMarkup;
	}

	public void setResultList(List<NathanVersionData> resultList) {
		this.resultList = resultList;
	}

	public List<NathanVersionData> getResultList() {
		return this.resultList;
	}

	public String[] getCheckedTags() {
		return this.checkedTags;
	}

	public void setCheckedTags(String[] checkedTags) {
		this.checkedTags = checkedTags;
	}

	public void setDicoRoot(String dicoRoot) {
		this.dicoRoot = dicoRoot;
	}

	public String getDicoRoot() {
		return this.dicoRoot;
	}

	public List<NathanTagData> getSexTags() {
		return this.sexTags;
	}

	public void setSexTags(List<NathanTagData> sexTags) {
		this.sexTags = sexTags;
	}

	public List<NathanTagData> getInterpretationTags() {
		return this.interpretationTags;
	}

	public void setInterpretationTags(List<NathanTagData> interpretationTags) {
		this.interpretationTags = interpretationTags;
	}

	public List<NathanTagData> getEffectsTags() {
		return this.effectsTags;
	}

	public void setEffectsTags(List<NathanTagData> effectsTags) {
		this.effectsTags = effectsTags;
	}

	public List<NathanTagData> getAccentTags() {
		return this.accentTags;
	}

	public void setAccentTags(List<NathanTagData> inValue) {
		this.accentTags = inValue;
	}

	public void setMySetting(List<PlayerData> inValue) {
		this.mySetting = inValue;
	}

	public List<PlayerData> getMySetting() {
		return this.mySetting;
	}

	public String getBookSerial() {
		return this.bookSerial;
	}

	public void setBookSerial(String bookSerial) {
		this.bookSerial = bookSerial;
	}

	public long getVersionId() {
		return this.versionId;
	}

	public void setVersionId(long versionId) {
		this.versionId = versionId;
	}

	public String[] getSelectedVersions() {
		return this.selectedVersions;
	}

	public void setSelectedVersions(String[] selectedVersions) {
		this.selectedVersions = selectedVersions;
	}

	public void setOtherBooksList(List<OtherNathanData> otherBooksList) {
		this.otherBooksList = otherBooksList;
	}

	public List<OtherNathanData> getOtherBooksList() {
		return this.otherBooksList;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public long getPicture() {
		return this.picture;
	}

	public void setPicture(long img) {
		this.picture = img;
	}

	public String getObjectLogin() {
		return this.mObjectLogin;
	}

	public void setObjectLogin(String inObjectLogin) {
		this.mObjectLogin = inObjectLogin;
	}

}
