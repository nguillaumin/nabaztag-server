package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

public final class MySrvClockUploadForm extends AbstractForm {

	private static final long serialVersionUID = 1L;

	private int hour;
	private String musicName = StringShop.EMPTY_STRING;
	private FormFile musicFile;

	private int user_id;
	private int user_main;
	private int userLang;
	private int idAlter;
	private String dispatch;
	private List<String> listHour;
	private String dico_hour;

	private Collection<ObjectLangData> langList = new ArrayList<ObjectLangData>();
	private String[] checkListLang = {};
	private String lang_selected;
	private boolean morning;
	private int hour_mode;

	private static final Map<String, Object> HOURS_LIST;

	static {
		final Map<String, Object> theMap = new HashMap<String, Object>();
		final List<String> hour = new LinkedList<String>();

		for (int i = 1; i < 25; i++) {
			hour.add(String.valueOf(i));
		}

		hour.add(String.valueOf(0));
		theMap.put("digit", hour);
		theMap.put("dico_key", "services/heures");

		HOURS_LIST = Collections.unmodifiableMap(theMap);
	}

	/**
	 * Used to initialize the class's variables and to set the encoding.
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		System.out.println("reset MySrvClockUploadForm");
		final HttpSession session = request.getSession(true);

		final User theUser = SessionTools.getUserFromSession(request);
		final VObject object = SessionTools.getRabbitFromSession(session);

		/**
		 * Check if the user really exists
		 */
		if ((theUser == null) || (object == null)) {
			setUser_id(0);
			setDispatch("goHome");
		} else {
			setUser_id(theUser.getId().intValue());
			setDispatch(StringShop.EMPTY_STRING);
			setHour(0);
			setLang_selected(String.valueOf(getUserLang()));
			setUserLang(theUser.getLang().getId().intValue());
			setLangList(ObjectLangData.getAllObjectLanguages());
			setUser_main(object.getId().intValue());
			setMorning(true);

			setHour_mode(theUser.getHourMod());

			setListHour(((List<String>) HOURS_LIST.get("digit")).subList(0, getHour_mode()));
			setDico_hour(HOURS_LIST.get("dico_key").toString());

		}

		super.reset(mapping, request);
	}

	@Override
	public ActionErrors validate(@SuppressWarnings("unused") ActionMapping mapping, @SuppressWarnings("unused") HttpServletRequest request) {
		final ActionErrors errors = new ActionErrors();

		if (!getDispatch().equals("goHome") && !getDispatch().equals("load") && !getDispatch().equals(StringShop.EMPTY_STRING)) {
			final Lang theLang = Factories.LANG.find(this.userLang);

			if (!(this.musicFile.getContentType().equals("audio/mpeg")) && !(this.musicFile.getContentType().equals("audio/mp3")) && !(this.musicFile.getContentType().equals("audio/x-mpeg"))) {
				errors.add("fileTypeError", new ActionMessage("errors.uploadClock", DicoTools.dico(theLang, "srv_clock/uploadErrorFileType")));
			}

			if (this.musicFile.getFileSize() > 3000000) {
				errors.add("fileSizeError", new ActionMessage("errors.uploadClock", DicoTools.dico(theLang, "srv_clock/uploadErrorFileSize")));
				// usage:
				// errors.add("errorName", new ActionMessage("errors.key",
				// DicoTools.dico(userLang, "dico_key")));
				// Also add the entry in the myErrors.properties file
			}
		}

		return errors;
	}

	public int getUser_id() {
		return this.user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_main() {
		return this.user_main;
	}

	public void setUser_main(int user_main) {
		this.user_main = user_main;
	}

	public String[] getCheckListLang() {
		return this.checkListLang;
	}

	public void setCheckListLang(String[] checkListLang) {
		this.checkListLang = checkListLang;
	}

	public Collection<ObjectLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(Collection<ObjectLangData> langList) {
		this.langList = langList;
	}

	public int getUserLang() {
		return this.userLang;
	}

	public void setUserLang(int userLang) {
		this.userLang = userLang;
	}

	public int getIdAlter() {
		return this.idAlter;
	}

	public void setIdAlter(int idAlter) {
		this.idAlter = idAlter;
	}

	/**
	 * @return the dispatcher
	 */
	public String getDispatch() {
		return this.dispatch;
	}

	/**
	 * @param dispatcher the dispatcher to set
	 */
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	/**
	 * @return the musicFile
	 */
	public FormFile getMusicFile() {
		return this.musicFile;
	}

	/**
	 * @param musicFile the musicFile to set
	 */
	public void setMusicFile(FormFile musicFile) {
		this.musicFile = musicFile;
	}

	/**
	 * @return the musicName
	 */
	public String getMusicName() {
		return this.musicName;
	}

	/**
	 * @param musicName the musicName to set
	 */
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return this.hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * @return the listHour
	 */
	public List<String> getListHour() {
		return this.listHour;
	}

	/**
	 * @param listHour the listHour to set
	 */
	public void setListHour(List<String> listHour) {
		this.listHour = listHour;
	}

	/**
	 * @return the dico_hour
	 */
	public String getDico_hour() {
		return this.dico_hour;
	}

	/**
	 * @param dico_hour the dico_hour to set
	 */
	public void setDico_hour(String dico_hour) {
		this.dico_hour = dico_hour;
	}

	/**
	 * @return the lang_type
	 */
	public String getLang_selected() {
		return this.lang_selected;
	}

	/**
	 * @param lang_type the lang_type to set
	 */
	public void setLang_selected(String lang_type) {
		this.lang_selected = lang_type;
	}

	/**
	 * @return the isMorning
	 */
	public boolean getMorning() {
		return this.morning;
	}

	/**
	 * @param isMorning the isMorning to set
	 */
	public void setMorning(boolean morning) {
		this.morning = morning;
	}

	/**
	 * @return the hour_mode
	 */
	public int getHour_mode() {
		return this.hour_mode;
	}

	/**
	 * @param hour_mode the hour_mode to set
	 */
	public void setHour_mode(int hour_mode) {
		this.hour_mode = hour_mode;
	}

}
