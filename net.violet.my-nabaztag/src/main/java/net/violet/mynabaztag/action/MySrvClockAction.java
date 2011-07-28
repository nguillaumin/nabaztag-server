package net.violet.mynabaztag.action;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MySrvClockForm;
import net.violet.mynabaztag.form.MySrvClockUploadForm;
import net.violet.platform.applications.ClockHandler;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.files.FilesManagerFactory;
import net.violet.platform.struts.DispatchActionWithLog;
import net.violet.platform.util.DicoTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

public final class MySrvClockAction extends DispatchActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MySrvClockAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		ActionForward forward = null;

		if (form instanceof MySrvClockUploadForm) {
			final MySrvClockUploadForm myForm = (MySrvClockUploadForm) form;

			if (!myForm.getDispatch().equals(StringShop.EMPTY_STRING) && !myForm.getDispatch().equals("load")) {
				try {
					forward = dispatchMethod(mapping, myForm, request, response, myForm.getDispatch());
				} catch (final Exception e) {
					MySrvClockAction.LOGGER.fatal(e, e);
				}
			}
		}

		if (form instanceof MySrvClockForm) {
			final MySrvClockForm myForm = (MySrvClockForm) form;

			if (!myForm.getDispatch().equals(StringShop.EMPTY_STRING)) {
				try {
					forward = dispatchMethod(mapping, myForm, request, response, myForm.getDispatch());
				} catch (final Exception e) {
					MySrvClockAction.LOGGER.fatal(e, e);
				}
			}
		}

		if ((forward != null) && !forward.getName().equals(StringShop.EMPTY_STRING)) {
			forward = mapping.findForward(forward.getName());
		} else {
			forward = mapping.getInputForward();
		}

		return forward;
	}

	public ActionForward load(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		final VObject theObject = SessionTools.getRabbitFromSession(session);

		if (theObject == null) {
			return mapping.findForward("login");
		}

		final MySrvClockForm myForm = (MySrvClockForm) form;

		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.CLOCK.getApplication(), theObject);

		if (!subscriptionsList.isEmpty()) {
			myForm.setIsReg(1);
			final SubscriptionData subscription = subscriptionsList.get(0);
			for (final Entry<String, Object> setting : subscription.getSettings().entrySet()) {
				if (ClockHandler.TYPES.equals(setting.getKey())) {
					myForm.setCheckListClockType(((List<String>) setting.getValue()).toArray(new String[0]));
				}
				if (ClockHandler.LANGUAGES.equals(setting.getKey())) {
					final List<String> languagesCodes = (List<String>) setting.getValue();
					final String[] langIds = new String[languagesCodes.size()];
					for (int i = 0; i < langIds.length; i++) {
						langIds[i] = Factories.LANG.findByIsoCode(languagesCodes.get(i)).getId().toString();
					}
					myForm.setCheckListLang(langIds);
				}
			}
		} else {
			myForm.setIsReg(0);
		}

		return new ActionForward(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, false);
	}

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		final HttpSession session = request.getSession(true);

		final VObject theObject = SessionTools.getRabbitFromSession(session);

		if (theObject == null) {
			return mapping.findForward("login");
		}

		final MySrvClockForm myForm = (MySrvClockForm) form;

		try {
			ClockHandler.createOrUpdateSubscription(null, VObjectData.getData(theObject), myForm.getCheckListLang(), myForm.getCheckListClockType());
		} catch (final Exception e) {
			MySrvClockAction.LOGGER.fatal(e, e);
		}

		return load(mapping, form, request, response);
	}

	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);

		final VObject theObject = SessionTools.getRabbitFromSession(session);

		if (theObject == null) {
			return mapping.findForward("login");
		}

		final MySrvClockForm myForm = (MySrvClockForm) form;

		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.CLOCK.getApplication(), theObject);

		if (!subscriptionsList.isEmpty()) {
			final SubscriptionData subscription = subscriptionsList.get(0);

			try {
				ClockHandler.createOrUpdateSubscription(subscription, VObjectData.getData(theObject), myForm.getCheckListLang(), myForm.getCheckListClockType());
			} catch (final Exception e) {
				MySrvClockAction.LOGGER.fatal(e, e);
				return load(mapping, form, request, response);
			}
		}

		final ActionMessages errors = new ActionMessages();
		errors.add("registerSucceed", new ActionMessage("errors.configClock", DicoTools.dico(theObject.getPreferences().getLangPreferences(), "js/modif_srv_ok")));
		saveErrors(request, errors);
		return load(mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		final HttpSession session = request.getSession(true);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		/**
		 * Check if the object really exists
		 */
		if (theObject == null) {
			return mapping.findForward("login");
		}

		for (final SubscriptionData aSubscription : SubscriptionData.findByApplicationAndObject(Application.NativeApplication.CLOCK.getApplication(), theObject)) {
			try {
				SubscriptionManager.deleteSubscription(aSubscription);
			} catch (final Exception e) {
				MySrvClockAction.LOGGER.fatal(e, e);
			}
		}

		return load(mapping, form, request, response);
	}

	public ActionForward uploadMP3(@SuppressWarnings("unused") ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		final MySrvClockUploadForm myForm = (MySrvClockUploadForm) form;

		int hour = myForm.getHour();

		if (!myForm.getMorning()) {
			hour = (hour + 12) % 24;
		}

		final ActionMessages errors = new ActionMessages();

		final String theIsocodePath = switchShortIsocodePath(Factories.LANG.find(Integer.parseInt(myForm.getLang_selected())));
		if (addMP3ForClock(myForm.getMusicFile(), String.valueOf(myForm.getUser_id()), theIsocodePath, String.valueOf(hour))) {
			errors.add("actionSuccess", new ActionMessage("errors.uploadClock", DicoTools.dico(Factories.LANG.find(myForm.getUserLang()), "srv_clock/uploadSuccess")));
		} else {
			errors.add("actionError", new ActionMessage("errors.uploadClock", DicoTools.dico(Factories.LANG.find(myForm.getUserLang()), "srv_clock/uploadErrorCreation")));
		}

		MySrvClockAction.LOGGER.debug("saveErrors");
		saveErrors(request, errors);

		return new ActionForward(StringShop.EMPTY_STRING, StringShop.EMPTY_STRING, false);
	}

	private static final Pattern SHORT_LANGUAGE_REGEX = Pattern.compile("(^[a-z]{2})");

	/**
	 * Permet de garder l'arborescence sur le NAS, code temporaire jusqu'a la
	 * mise en ligne de la nouvelle api On renommera les arborescences des
	 * langues( cas particulier pour l'horloge communautaire)
	 */
	@Deprecated
	public static String switchShortIsocodePath(Lang inLang) {
		String isocodePath = inLang.getIsoCode();
		if (isocodePath.equals("en-US")) {
			isocodePath = "us";
		} else if (isocodePath.equals("en-GB")) {
			isocodePath = "uk";
		} else if (isocodePath.equals("pt-BR")) {
			isocodePath = "br";
		}

		final Matcher theMatcher = SHORT_LANGUAGE_REGEX.matcher(isocodePath);
		if (theMatcher.matches()) {
			isocodePath = theMatcher.group(1);
		}

		return isocodePath;
	}

	private static final SimpleDateFormat CLOCK_DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd-HHmmss");

	private boolean addMP3ForClock(FormFile file, String user_id, String lang, String hour) {
		final StringBuilder hour_mp3 = new StringBuilder("HC_").append(lang.toUpperCase()).append(StringShop.UNDERSCORE).append(hour).append(StringShop.UNDERSCORE).append(CLOCK_DATE_FORMATTER.format(Calendar.getInstance().getTime())).append(StringShop.UNDERSCORE).append(user_id).append(StringShop.MP3_EXT);

		try {
			return (file != null) && FilesManagerFactory.FILE_MANAGER.saveHCFile(hour_mp3.toString(), file.getFileData());
		} catch (final FileNotFoundException e) {
			LOGGER.fatal(e, e);
		} catch (final IOException e) {
			LOGGER.fatal(e, e);
		}

		return false;
	}

}
