package net.violet.mynabaztag.form;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.ClockCategoriesData;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public final class MySrvClockForm extends AbstractForm {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MySrvClockForm.class);

	private static final long serialVersionUID = 1L;

	private int user_id;
	private int user_main;
	private int isReg;

	private int userLang;
	private int idAlter;
	private String dispatch;
	private List<ClockCategoriesData> listCategories = new ArrayList<ClockCategoriesData>();

	private Collection<ObjectLangData> langList = new ArrayList<ObjectLangData>();
	private String[] checkListLang = {};
	private String[] checkListClockType = {};

	/**
	 * Used to initialize the class's variables and to set the encoding.
	 */

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		final HttpSession session = request.getSession(true);
		final User user = SessionTools.getUserFromSession(request);
		final VObject object = SessionTools.getRabbitFromSession(session);

		/**
		 * Check if the user really exists
		 */
		if ((user == null) || (object == null)) {
			setDispatch("goHome");
			setUser_id(0);
		} else {
			setUser_id(user.getId().intValue());
			setDispatch(StringShop.EMPTY_STRING);
			try {
				setListCategories(ClockCategoriesData.findAllRoot());
			} catch (final SQLException e) {
				MySrvClockForm.LOGGER.fatal(e, e);
			}
			setUserLang(user.getLang().getId().intValue());
			setLangList(ObjectLangData.getAllObjectLanguages());
			setUser_main(object.getId().intValue());
			setCheckListLang(new String[getLangList().size()]);
			setCheckListClockType(new String[getListCategories().size()]);

		}
		super.reset(mapping, request);
	}

	@Override
	public ActionErrors validate(@SuppressWarnings("unused") ActionMapping mapping, @SuppressWarnings("unused") HttpServletRequest request) {
		final ActionErrors errors = new ActionErrors();

		MySrvClockForm.LOGGER.info("disptach = " + this.getDispatch());
		if (!getDispatch().equals("goHome") && !getDispatch().equals("load")) {
			final Lang theLang = Factories.LANG.find(this.userLang);

			// Supprime toutes occurences de StringShop.EMPTY_STRING
			final List<String> types = Arrays.asList(this.checkListClockType);
			types.remove(StringShop.EMPTY_STRING);
			this.checkListClockType = (String[]) types.toArray();
			if ((this.checkListClockType == null) || ((this.checkListClockType != null) && (this.checkListClockType.length == 0))) {
				errors.add("choix_type_horloge", new ActionMessage("errors.configClock", DicoTools.dico(theLang, "js/choix_type_horloge")));
			}

			final List<String> languages = Arrays.asList(this.checkListLang);
			languages.remove(StringShop.EMPTY_STRING);
			this.checkListLang = (String[]) languages.toArray();
			if ((this.checkListLang == null) || ((this.checkListLang != null) && (this.checkListLang.length == 0))) {
				errors.add("choix_langue", new ActionMessage("errors.configClock", DicoTools.dico(theLang, "js/choix_langue")));
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

	public String[] getCheckListClockType() {
		return this.checkListClockType;
	}

	public void setCheckListClockType(String[] checkListClockType) {
		this.checkListClockType = checkListClockType;
	}

	/**
	 * @return the dispatcher
	 */
	public String getDispatch() {
		return this.dispatch;
	}

	public final int getIsReg() {
		return this.isReg;
	}

	public final void setIsReg(int isReg) {
		this.isReg = isReg;
	}

	/**
	 * @param dispatcher the dispatcher to set
	 */
	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	/**
	 * @return the listCategories
	 */
	public List<ClockCategoriesData> getListCategories() {
		return this.listCategories;
	}

	/**
	 * @param listCategories the listCategories to set
	 */
	public void setListCategories(List<ClockCategoriesData> listCategories) {
		this.listCategories = listCategories;
	}

}
