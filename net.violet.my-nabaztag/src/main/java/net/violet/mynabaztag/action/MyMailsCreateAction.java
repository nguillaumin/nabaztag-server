package net.violet.mynabaztag.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.violet.mynabaztag.form.MyMailCreateForm;
import net.violet.mynabaztag.form.MyMailsCreateForm;
import net.violet.mynabaztag.services.MailServices;
import net.violet.platform.applications.MailAlertHandler;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Subscription;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MailKeywordData;
import net.violet.platform.dataobjects.MailUserData;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.VObjectData;
import net.violet.platform.schedulers.KeywordHandler;
import net.violet.platform.schedulers.NewContentWithKeywordAndMediaHandler;
import net.violet.platform.struts.ActionWithLog;
import net.violet.platform.util.MailTools;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class MyMailsCreateAction extends ActionWithLog {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(MyMailsCreateAction.class);

	@Override
	protected ActionForward doExecute(ActionMapping mapping, ActionForm form, HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

		// initialisation des objets
		final MyMailsCreateForm myForm = (MyMailsCreateForm) form;
		final HttpSession session = request.getSession(true);

		final User theUser = SessionTools.getUserFromSession(request);
		final Lang lang = SessionTools.getLangFromSession(session, request);
		final VObject theObject = SessionTools.getRabbitFromSession(session);

		/**
		 * Check if the user really exists
		 */
		if (theUser == null) {
			return mapping.findForward("login");
		}

		if (theObject == null) {
			return mapping.findForward("login");
		}

		String musicUrl = StringShop.EMPTY_STRING;
		int passive = 0;
		final int displayConfig = 1;
		final int error_upd = 0;
		int error_add = 0;

		final MusicData defaultAlert = MusicData.getDefaultMailAlert(lang);

		String srv_src = myForm.getSrv_src();
		String mailKeyword_musicUrl = myForm.getMusicUrl();
		String mail_serveur = myForm.getMail_serveur();
		String mail_protocol = myForm.getMail_protocol();
		if (myForm.getSecured() == 1) {
			mail_protocol = mail_protocol + "s";
		}
		String mail_compte = myForm.getMail_compte();
		String mail_password = myForm.getMail_password();
		int lumiereFilter = myForm.getLumiereFilter();

		final MailUserData mailUser = MailUserData.FindMailUserDataInfo(theObject, srv_src);

		List<MailKeywordData> mailAccounts = ((mailUser != null) && !mailUser.getKeywords().isEmpty()) ? mailUser.getKeywords() : new ArrayList<MailKeywordData>();

		if (mailKeyword_musicUrl.equals("1")) {
			mailKeyword_musicUrl = defaultAlert.getMusic_url();
		} else {
			mailKeyword_musicUrl = StringShop.EMPTY_STRING;
		}

		int isReg = MailServices.isReg(theObject);

		/** AJOUT (Create or Update) **/
		if (myForm.getAdd() == 1) {
			final String[] keys = myForm.getKeywords();
			final long[] sons = myForm.getSounds();
			final int length = keys.length;

			// UPDATE
			final int nb_msg = MailTools.checkNbMail(mail_serveur, mail_compte, mail_password, mail_protocol);

			if (nb_msg >= 0) {

				final String theId = myForm.getIdentifiant();
				final SubscriptionData theSubscription;
				if ((theId != null) && !theId.equals(StringShop.EMPTY_STRING)) {
					theSubscription = SubscriptionData.find(Long.parseLong(theId));
				} else {
					theSubscription = null;
				}

				// Check if this account isn't already watch by this rabbit
				if ((theSubscription != null) || !MailServices.isThisObjectHaveThisAccount(theObject, mail_compte)) {

					final MailAlertHandler.SettingsBuilder theSettings = new MailAlertHandler.SettingsBuilder(mail_compte, mail_password, mail_protocol, mail_serveur, myForm.getSecured() > 0);

					try {
						final List<Map<String, Object>> theSchedulings = new ArrayList<Map<String, Object>>();

						if ((length == 0) || ((length == 1) && keys[0].equals(StringShop.EMPTY_STRING))) {
							if (myForm.getMusicUrl().equals("1")) {
								final Map<String, Object> content = new HashMap<String, Object>();
								content.put("type", SchedulingType.SCHEDULING_TYPE.NewContent.getLabel());
								theSchedulings.add(content);
							}
							if (myForm.getPassive() == 1) {
								final Map<String, Object> ambiant = new HashMap<String, Object>();
								ambiant.put("type", SchedulingType.SCHEDULING_TYPE.Ambiant.getLabel());
								theSchedulings.add(ambiant);
							}
						} else {
							for (int i = 0; i < length; i++) {
								int isPassive = 0;
								if (lumiereFilter == mailAccounts.size() + 1) {
									isPassive = 1;
								}
								if (!keys[i].equals(StringShop.EMPTY_STRING)) {
									theSettings.addKeywordWithPosition(keys[i], String.valueOf(sons[i]), i);
								}

								if (isPassive > 0) {
									// Alerte passive
									final Map<String, Object> ambiant = new HashMap<String, Object>();
									ambiant.put("type", SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword.getLabel());
									ambiant.put(KeywordHandler.KEYWORD, keys[i]);
									theSchedulings.add(ambiant);
								}

								final Map<String, Object> content = new HashMap<String, Object>();
								content.put("type", SchedulingType.SCHEDULING_TYPE.NewContentWithKeywordAndMedia.getLabel());
								content.put(KeywordHandler.KEYWORD, keys[i]);
								content.put(NewContentWithKeywordAndMediaHandler.MEDIA, Long.toString(sons[i]));
								theSchedulings.add(content);
							}
						}

						//just here to not forget updating this class when the handler is done.
						new MailAlertHandler().createOrUpdateServiceSubscription(theSubscription, VObjectData.getData(theObject), theSettings, theSchedulings);

						mailKeyword_musicUrl = "0";
						mail_serveur = StringShop.EMPTY_STRING;
						mail_protocol = StringShop.EMPTY_STRING;
						mail_compte = StringShop.EMPTY_STRING;
						mail_password = StringShop.EMPTY_STRING;
						lumiereFilter = 0;
						musicUrl = StringShop.EMPTY_STRING;
						passive = 0;
						return mapping.getInputForward();
					} catch (final Exception e) {
						MyMailsCreateAction.LOGGER.fatal(e, e);
					}
					error_add = 1;
				} else {
					error_add = 2;
				}
			} else {
				error_add = 1;
			}

		}
		// FIN AJOUT

		// SUPPRESSION
		if (myForm.getDelete() == 1) {

			try {
				final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MAIL.getApplication(), theObject);

				if (!srv_src.equals(StringShop.EMPTY_STRING)) { // delete un service en particulier

					MailServices.delMailAccount(subscriptionsList, srv_src, myForm.getKeyword());
					mailKeyword_musicUrl = "0";
					mail_serveur = StringShop.EMPTY_STRING;
					mail_protocol = StringShop.EMPTY_STRING;
					mail_compte = StringShop.EMPTY_STRING;
					mail_password = StringShop.EMPTY_STRING;
					lumiereFilter = 0;
					musicUrl = StringShop.EMPTY_STRING;
					passive = 0;
					srv_src = StringShop.EMPTY_STRING;
				} else {
					// delete tous les services mails associés a l'utilisateur
					for (final SubscriptionData subscription : subscriptionsList) {
						SubscriptionManager.deleteSubscription(subscription);
					}
				}
			} catch (final Exception e) {
				MyMailsCreateAction.LOGGER.fatal(e, e);
			}
		}
		// FIN SUPPRESSION

		if (!srv_src.equals(StringShop.EMPTY_STRING)) {

			final MailUserData mailData = MailUserData.FindMailUserDataInfo(theObject, srv_src);

			final Map<String, String> account = MailServices.getUserMailAccountInfo(mailData);

			myForm.setIdentifiant(srv_src.substring(srv_src.indexOf("$") + 1, srv_src.indexOf(".")));

			if (!account.isEmpty()) {
				mail_serveur = account.get("mailUser_host");
				mail_protocol = account.get("mailUser_protocol");
				mail_compte = account.get("mailUser_login");
				mail_password = account.get("mailUser_password");
				musicUrl = account.get("musicId");
				try {
					passive = Integer.parseInt(account.get("passive"));
				} catch (final NumberFormatException nfe) {
					MyMailsCreateAction.LOGGER.fatal(nfe, nfe);
				}
			}

			mailAccounts = mailData.getKeywords();

			int size = mailAccounts.size();
			final String[] keywordList = new String[size];
			final long[] soundList = new long[size];
			final int[] lightList = new int[size];

			size = 0;
			for (final MailKeywordData info : mailAccounts) {
				if (!info.getMailKeyword_keyword().equals(StringShop.EMPTY_STRING)) {
					keywordList[size] = info.getMailKeyword_keyword();
					soundList[size] = info.getMailKeyword_music_id();
					lightList[size] = info.getMailKeyword_passive();
					if (lightList[size] > 0) {
						lumiereFilter = size + 1;
					}
					size++;
				}
			}

			final List<MyMailCreateForm> rowData = new Vector<MyMailCreateForm>();
			for (int i = 0; i < size; i++) {
				MyMailCreateForm elt = new MyMailCreateForm();
				elt.setKeywords(keywordList[i]);
				elt.setSounds(soundList[i]);
				elt.setLight(lightList[i]);
				rowData.add(elt);
				elt = null;
			}

			final int sz = rowData.size();
			for (int j = 0; j < sz; j++) {
				rowData.get(j);
			}

			myForm.setRows(rowData);

			myForm.setSrv_src(srv_src);
			myForm.setPassive(passive);
			myForm.setMusicUrl(mailKeyword_musicUrl);
			myForm.setMail_serveur(mail_serveur);
			myForm.setSecured(mailData.isSecure());

			if (mail_protocol.endsWith("s")) {
				mail_protocol = mail_protocol.substring(0, mail_protocol.length() - 1);
				// myForm.setSecured(1);
			}

			myForm.setMail_protocol(mail_protocol);
			myForm.setMail_compte(mail_compte);
			myForm.setMail_password(StringShop.EMPTY_STRING);
			myForm.setLumiereFilter(lumiereFilter);
			myForm.setError_add(error_add);
			myForm.setError_upd(error_upd);
		}

		// suppression de usersrv si l'utilisateur n'a plus de compte mail
		isReg = MailServices.isReg(theObject);
		if (isReg < 1) {
			for (Subscription aSubscription : Factories.SUBSCRIPTION.findByApplicationAndObject(Application.NativeApplication.MAIL.getApplication(), theObject)) {
				aSubscription.delete();
			}
		}

		final List<MusicData> listZik = MusicData.findAllPersoByUser(theUser);
		final List<MusicData> listeMusiques = new ArrayList<MusicData>();
		if (listeMusiques.isEmpty()) {
			// ajout de l'alerte par default puis de la liste de mp3 perso
			listeMusiques.add(defaultAlert);
			listeMusiques.addAll(listZik);
		} else if (listeMusiques.size() < listZik.size() + 1) {
			// On vide et on rempli à nouveau
			listeMusiques.clear();
			listeMusiques.add(defaultAlert);
			listeMusiques.addAll(listZik);
		}

		myForm.setIsReg(isReg);
		// to set the correct button into the jsp page
		musicUrl = ((musicUrl == null) || musicUrl.equals(StringShop.EMPTY_STRING) || musicUrl.equals("0")) ? "0" : "1";

		myForm.setMusicUrl(musicUrl);
		myForm.setSrv_src(srv_src);
		myForm.setAdd(0);
		myForm.setDelete(0);
		myForm.setRabbitName(theObject.getObject_login());
		myForm.setError_add(error_add);
		myForm.setListeMusiques(listeMusiques);
		myForm.setDisplayConfig(displayConfig);
		return mapping.getInputForward();
	}
}
