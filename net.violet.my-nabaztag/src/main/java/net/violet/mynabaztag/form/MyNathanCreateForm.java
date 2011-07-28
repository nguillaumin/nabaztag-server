package net.violet.mynabaztag.form;

import net.violet.platform.util.StringShop;

import org.apache.struts.upload.FormFile;


public class MyNathanCreateForm extends AbstractForm {

	private Long appletId = 0L;
	private Long isbn = 0L;
	private int isMarkup;
	private String[] mp3IdList = new String[0];
	private String mp3Id = StringShop.EMPTY_STRING;
	private FormFile mp3File;
	private String dicoRoot = StringShop.EMPTY_STRING;
	private String serverPath = StringShop.EMPTY_STRING;
	private Integer error;
	private String url;

	public FormFile getMp3File() {
		return this.mp3File;
	}

	public void setMp3File(FormFile mp3File) {
		this.mp3File = mp3File;
	}

	public Long getAppletId() {
		return this.appletId;
	}

	public void setAppletId(Long appletId) {
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

	public String[] getMp3IdList() {
		return this.mp3IdList;
	}

	public void setMp3IdList(String[] mp3IdList) {
		this.mp3IdList = mp3IdList;
	}

	public String getMp3Id() {
		return this.mp3Id;
	}

	public void setMp3Id(String mp3Id) {
		this.mp3Id = mp3Id;
	}

	public void setDicoRoot(String dicoRoot) {
		this.dicoRoot = dicoRoot;
	}

	public String getDicoRoot() {
		return this.dicoRoot;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getServerPath() {
		return this.serverPath;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public Integer getError() {
		return this.error;
	}

	public Object getUrl() {
		return this.url;
	}

	public void setUrl(String shortcut) {
		this.url = shortcut;
	}

}
