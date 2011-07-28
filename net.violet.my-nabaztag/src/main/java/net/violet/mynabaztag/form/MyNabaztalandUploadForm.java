package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.List;

import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.NabcastData;
import net.violet.platform.dataobjects.NabcastValData;

import org.apache.struts.upload.FormFile;

public final class MyNabaztalandUploadForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int user_id;
	private int user_main;
	private String languser = "2";
	private int mode;

	/* Partie listes */
	private int idNabcast;
	private List<NabcastValData> nabcastValSent = new ArrayList<NabcastValData>();
	private List<NabcastValData> nabcastValToSend = new ArrayList<NabcastValData>();
	private NabcastData nabcastData = new NabcastData();
	private int nbrAbonn;
	private int idNabcastVal;

	/* Partie Upload */
	private FormFile song_path;
	private String song_title;
	private FormFile mp3;
	private long idMp3;
	private List<MusicData> mp3List = new ArrayList<MusicData>();
	private int song_start;
	private int song_end = 45;
	private String date_delay;
	private String heure_delay;
	private String minute_delay;

	/**
	 * @return the date_delay
	 */
	public String getDate_delay() {
		return this.date_delay;
	}

	/**
	 * @param date_delay the date_delay to set
	 */
	public void setDate_delay(String date_delay) {
		this.date_delay = date_delay;
	}

	/**
	 * @return the heure_delay
	 */
	public String getHeure_delay() {
		return this.heure_delay;
	}

	/**
	 * @param heure_delay the heure_delay to set
	 */
	public void setHeure_delay(String heure_delay) {
		this.heure_delay = heure_delay;
	}

	/**
	 * @return the idMp3
	 */
	public long getIdMp3() {
		return this.idMp3;
	}

	/**
	 * @param idMp3 the idMp3 to set
	 */
	public void setIdMp3(long idMp3) {
		this.idMp3 = idMp3;
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
	 * @return the idNabcastVal
	 */
	public int getIdNabcastVal() {
		return this.idNabcastVal;
	}

	/**
	 * @param idNabcastVal the idNabcastVal to set
	 */
	public void setIdNabcastVal(int idNabcastVal) {
		this.idNabcastVal = idNabcastVal;
	}

	/**
	 * @return the languser
	 */
	public String getLanguser() {
		return this.languser;
	}

	/**
	 * @param languser the languser to set
	 */
	public void setLanguser(String languser) {
		this.languser = languser;
	}

	/**
	 * @return the minute_delay
	 */
	public String getMinute_delay() {
		return this.minute_delay;
	}

	/**
	 * @param minute_delay the minute_delay to set
	 */
	public void setMinute_delay(String minute_delay) {
		this.minute_delay = minute_delay;
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
	 * @return the mp3
	 */
	public FormFile getMp3() {
		return this.mp3;
	}

	/**
	 * @param mp3 the mp3 to set
	 */
	public void setMp3(FormFile mp3) {
		this.mp3 = mp3;
	}

	/**
	 * @return the mp3List
	 */
	public List<MusicData> getMp3List() {
		return this.mp3List;
	}

	/**
	 * @param mp3List the mp3List to set
	 */
	public void setMp3List(List<MusicData> mp3List) {
		this.mp3List = mp3List;
	}

	/**
	 * @return the nabcastData
	 */
	public NabcastData getNabcastData() {
		return this.nabcastData;
	}

	/**
	 * @param nabcastData the nabcastData to set
	 */
	public void setNabcastData(NabcastData nabcastData) {
		this.nabcastData = nabcastData;
	}

	/**
	 * @return the nabcastValSent
	 */
	public List<NabcastValData> getNabcastValSent() {
		return this.nabcastValSent;
	}

	/**
	 * @param nabcastValSent the nabcastValSent to set
	 */
	public void setNabcastValSent(List<NabcastValData> nabcastValSent) {
		this.nabcastValSent = nabcastValSent;
	}

	/**
	 * @return the nabcastValToSend
	 */
	public List<NabcastValData> getNabcastValToSend() {
		return this.nabcastValToSend;
	}

	/**
	 * @param nabcastValToSend the nabcastValToSend to set
	 */
	public void setNabcastValToSend(List<NabcastValData> nabcastValToSend) {
		this.nabcastValToSend = nabcastValToSend;
	}

	/**
	 * @return the nbrAbonn
	 */
	public int getNbrAbonn() {
		return this.nbrAbonn;
	}

	/**
	 * @param nbrAbonn the nbrAbonn to set
	 */
	public void setNbrAbonn(int nbrAbonn) {
		this.nbrAbonn = nbrAbonn;
	}

	/**
	 * @return the song_end
	 */
	public int getSong_end() {
		return this.song_end;
	}

	/**
	 * @param song_end the song_end to set
	 */
	public void setSong_end(int song_end) {
		this.song_end = song_end;
	}

	/**
	 * @return the song_path
	 */
	public FormFile getSong_path() {
		return this.song_path;
	}

	/**
	 * @param song_path the song_path to set
	 */
	public void setSong_path(FormFile song_path) {
		this.song_path = song_path;
	}

	/**
	 * @return the song_start
	 */
	public int getSong_start() {
		return this.song_start;
	}

	/**
	 * @param song_start the song_start to set
	 */
	public void setSong_start(int song_start) {
		this.song_start = song_start;
	}

	/**
	 * @return the song_title
	 */
	public String getSong_title() {
		return this.song_title;
	}

	/**
	 * @param song_title the song_title to set
	 */
	public void setSong_title(String song_title) {
		this.song_title = song_title;
	}

	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return this.user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the user_main
	 */
	public int getUser_main() {
		return this.user_main;
	}

	/**
	 * @param user_main the user_main to set
	 */
	public void setUser_main(int user_main) {
		this.user_main = user_main;
	}
}
