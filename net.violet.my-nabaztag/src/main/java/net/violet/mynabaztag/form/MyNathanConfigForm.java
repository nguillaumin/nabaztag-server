package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.NathanTagData;
import net.violet.platform.util.StringShop;

public class MyNathanConfigForm extends AbstractForm {

	private String versionId;
	private String[] checkedTags = new String[0];
	private String description = StringShop.EMPTY_STRING;
	private int isShared;
	private String isbn = StringShop.EMPTY_STRING;
	private String dicoRoot = StringShop.EMPTY_STRING;
	private String appletId = StringShop.EMPTY_STRING;

	private List<NathanTagData> sexTags = new ArrayList<NathanTagData>();
	private List<NathanTagData> interpretationTags = new ArrayList<NathanTagData>();
	private List<NathanTagData> effectsTags = new ArrayList<NathanTagData>();
	private List<NathanTagData> accentTags = new ArrayList<NathanTagData>();

	private long checkedSexTag;
	private long checkedInterpretationTag;
	private long checkedEffectsTag;
	private long checkedAccentTag;

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

	public void setAccentTags(List<NathanTagData> inAccentTags) {
		this.accentTags = inAccentTags;
	}

	public long getCheckedSexTag() {
		return this.checkedSexTag;
	}

	public void setCheckedSexTag(long inValue) {
		this.checkedSexTag = inValue;
	}

	public long getCheckedInterpretationTag() {
		return this.checkedInterpretationTag;
	}

	public void setCheckedInterpretationTag(long checkedInterpretationTag) {
		this.checkedInterpretationTag = checkedInterpretationTag;
	}

	public long getCheckedEffectsTag() {
		return this.checkedEffectsTag;
	}

	public void setCheckedEffectsTag(long checkedEffectsTag) {
		this.checkedEffectsTag = checkedEffectsTag;
	}

	public long getCheckedAccentTag() {
		return this.checkedAccentTag;
	}

	public void setCheckedAccentTag(long checkedAccentTag) {
		this.checkedAccentTag = checkedAccentTag;
	}

	public String getAppletId() {
		return this.appletId;
	}

	public void setAppletId(String appletId) {
		this.appletId = appletId;
	}

	public String getDicoRoot() {
		return this.dicoRoot;
	}

	public void setDicoRoot(String dicoRoot) {
		this.dicoRoot = dicoRoot;
	}

	public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String[] getCheckedTags() {
		return this.checkedTags;
	}

	public void setCheckedTags(String[] checkedTags) {
		this.checkedTags = checkedTags;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsShared() {
		return this.isShared;
	}

	public void setIsShared(int isShared) {
		this.isShared = isShared;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}
