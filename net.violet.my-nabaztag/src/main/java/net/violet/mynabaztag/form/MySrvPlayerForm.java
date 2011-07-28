package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.mynabaztag.data.PlayerData;
import net.violet.platform.util.StringShop;

public class MySrvPlayerForm extends AbstractForm {

	private static final long serialVersionUID = 491597568795993297L;

	private int isMarkup; // il y a t'il eu une lecture du bouquin
	private long sizeVoice;
	private long appletId;
	private long voiceId;
	private long bookId; // id du ZtampImpl
	private long srvId;
	private List<PlayerData> mySetting = new ArrayList<PlayerData>();
	private Long isbn = 0L;
	private String dicoRoot = StringShop.EMPTY_STRING;
	private String bookSerial = StringShop.EMPTY_STRING;

	public String getBookSerial() {
		return this.bookSerial;
	}

	public void setBookSerial(String bookSerial) {
		this.bookSerial = bookSerial;
	}

	public List<PlayerData> getMySetting() {
		return this.mySetting;
	}

	public void setMySetting(List<PlayerData> mySetting) {
		this.mySetting = mySetting;
	}

	public long getSizeVoice() {
		return this.sizeVoice;
	}

	public void setSizeVoice(long sizeVoice) {
		this.sizeVoice = sizeVoice;
	}

	public long getAppletId() {
		return this.appletId;
	}

	public void setAppletId(long appletId) {
		this.appletId = appletId;
	}

	public int getIsMarkup() {
		return this.isMarkup;
	}

	public void setIsMarkup(int isMarkup) {
		this.isMarkup = isMarkup;
	}

	public long getSrvId() {
		return this.srvId;
	}

	public void setSrvId(long srvId) {
		this.srvId = srvId;
	}

	public long getVoiceId() {
		return this.voiceId;
	}

	public void setVoiceId(long voiceId) {
		this.voiceId = voiceId;
	}

	public long getBookId() {
		return this.bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the isbn
	 */
	public Long getIsbn() {
		return this.isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the dicoRoot
	 */
	public String getDicoRoot() {
		return this.dicoRoot;
	}

	/**
	 * @param dicoRoot the dicoRoot to set
	 */
	public void setDicoRoot(String dicoRoot) {
		this.dicoRoot = dicoRoot;
	}

}
