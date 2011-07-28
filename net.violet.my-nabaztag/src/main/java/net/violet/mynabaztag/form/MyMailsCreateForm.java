package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.util.StringShop;

public class MyMailsCreateForm extends AbstractForm {

	public static final long serialVersionUID = 1;

	private int passive;
	private String musicUrl = StringShop.EMPTY_STRING;
	private String srv_src = StringShop.EMPTY_STRING;
	private String mail_serveur = StringShop.EMPTY_STRING;
	private String mail_protocol = "pop";
	private int secured;
	private String mail_compte = StringShop.EMPTY_STRING;
	private String mail_password = StringShop.EMPTY_STRING;
	private String keyword = StringShop.EMPTY_STRING;
	private List<MusicData> listeMusiques = new ArrayList<MusicData>();
	private String rabbitName = StringShop.EMPTY_STRING;

	public String identifiant = StringShop.EMPTY_STRING;

	private int add;
	private int delete;

	private int error_add;
	private int error_upd;

	private String[] keywords = {};
	private long[] sounds = {};
	private int[] lights = {};
	private int lumiereFilter;
	private List<MyMailCreateForm> rows = new ArrayList<MyMailCreateForm>();

	private int isReg;

	private int displayConfig;

	public int getDisplayConfig() {
		return this.displayConfig;
	}

	public void setDisplayConfig(int displayConfig) {
		this.displayConfig = displayConfig;
	}

	public void setListeMusiques(List<MusicData> listeMusiques) {
		this.listeMusiques = listeMusiques;
	}

	public List<MusicData> getListeMusiques() {
		return this.listeMusiques;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setMail_password(String mail_password) {
		this.mail_password = mail_password;
	}

	public String getMail_password() {
		return this.mail_password;
	}

	public void setMail_protocol(String mail_protocol) {
		this.mail_protocol = mail_protocol;
	}

	public String getMail_protocol() {
		return this.mail_protocol;
	}

	public void setMail_compte(String mail_compte) {
		this.mail_compte = mail_compte;
	}

	public String getMail_compte() {
		return this.mail_compte;
	}

	public void setMail_serveur(String mail_serveur) {
		this.mail_serveur = mail_serveur;
	}

	public String getMail_serveur() {
		return this.mail_serveur;
	}

	public void setPassive(int passive) {
		this.passive = passive;
	}

	public int getPassive() {
		return this.passive;
	}

	public String getMusicUrl() {
		return this.musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getSrv_src() {
		return this.srv_src;
	}

	public void setSrv_src(String srv_src) {
		this.srv_src = srv_src;
	}

	public int getAdd() {
		return this.add;
	}

	public void setAdd(int add) {
		this.add = add;
	}

	public int getDelete() {
		return this.delete;
	}

	public void setDelete(int delete) {
		this.delete = delete;
	}

	public String[] getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String[] keywordList) {
		this.keywords = keywordList;
	}

	public int getLumiereFilter() {
		return this.lumiereFilter;
	}

	public void setLumiereFilter(int lumiereFilter) {
		this.lumiereFilter = lumiereFilter;
	}

	public long[] getSounds() {
		return this.sounds;
	}

	public void setSounds(long[] soundList) {
		this.sounds = soundList;
	}

	public int getError_add() {
		return this.error_add;
	}

	public void setError_add(int error_add) {
		this.error_add = error_add;
	}

	public int getError_upd() {
		return this.error_upd;
	}

	public void setError_upd(int error_upd) {
		this.error_upd = error_upd;
	}

	public void setRows(List<MyMailCreateForm> rows) {
		this.rows = rows;
	}

	public int getSizeRows() {
		return this.rows.size();
	}

	public List<MyMailCreateForm> getRows() {
		try {
			final int size = this.keywords.length;
			for (int i = 0; i < size; i++) {
				MyMailCreateForm row = new MyMailCreateForm();
				System.out.println("getRows : keywords : " + this.keywords[i]);
				row.setKeywords(this.keywords[i]);
				row.setSounds(this.sounds[i]);
				row.setLight(this.lights[i]);
				this.rows.add(row);
				row = null;
			}
		} catch (final Exception e) {
			System.out.println("Exception : " + e.getMessage() + " causée par " + e.getCause().toString());
		}
		return this.rows;
	}

	public void populateForm(List<MyMailCreateForm> rowData) {
		final int size = rowData.size();
		System.out.println("MyMailsCreateForm Form : size :" + size);
		this.keywords = null;
		this.sounds = null;
		this.lights = null;
		this.keywords = new String[size];
		this.sounds = new long[size];
		this.lights = new int[size];
		final Iterator<MyMailCreateForm> it = rowData.iterator();
		for (int i = 0; i < size; i++) {
			System.out.println("MyMailsCreateForm i : " + i);
			final MyMailCreateForm elt = it.next();
			this.keywords[i] = elt.getKeywords();
			this.sounds[i] = elt.getSounds();
			this.lights[i] = elt.getLight();
		}
	}

	public String getIdentifiant() {
		return this.identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public int getIsReg() {
		return this.isReg;
	}

	public void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	public String getRabbitName() {
		return this.rabbitName;
	}

	public void setRabbitName(String rabbitName) {
		this.rabbitName = rabbitName;
	}

	public int getSecured() {
		return this.secured;
	}

	public void setSecured(int secured) {
		this.secured = secured;
	}

}
