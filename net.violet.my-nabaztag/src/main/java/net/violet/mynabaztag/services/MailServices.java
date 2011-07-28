//TODO extraire la connection : envoie/reception sur socket pour l'externaliser
/** Méthodes pour gérer les Mails
 * @author Guillaume
 * @version 1.0 (2006-10-06)
 */

package net.violet.mynabaztag.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.violet.platform.api.exceptions.InvalidParameterException;
import net.violet.platform.applications.MailAlertHandler;
import net.violet.platform.applications.SubscriptionManager;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MailKeywordData;
import net.violet.platform.dataobjects.MailUserData;
import net.violet.platform.dataobjects.SubscriptionData;
import net.violet.platform.dataobjects.SubscriptionSchedulingData;
import net.violet.platform.schedulers.KeywordHandler;
import net.violet.platform.schedulers.NewContentWithKeywordAndMediaHandler;
import net.violet.platform.util.CipherTools;
import net.violet.platform.util.StringShop;

public class MailServices {

	/**
	 * Permet d'ajouter un compte mail a surveiller
	 * 
	 * @param mailUser_password : implicite
	 * @param mailUser_login : implicite
	 * @param mailUser_type : type du serveur de mail : pop, ssl, imap
	 * @param mailUser_host : l'addresse du serveur mail : pop.wanadoo.fr par
	 *            exemple
	 * @return result : requete reussie ou non
	 */
	public static void addMailAccount(SubscriptionData inSubscription, String mailUser_password, String mailUser_login, String mailUser_type, String mailUser_host) {
		final String source = "$" + inSubscription.getId() + "." + mailUser_type;
		final String cyphLogin = CipherTools.cipher(mailUser_login);
		final String cyphPassword = CipherTools.cipher(mailUser_password);

		final Map<String, Object> settings = inSubscription.getSettings();

		settings.put(MailAlertHandler.SOURCE, source);
		settings.put(MailAlertHandler.LOGIN, cyphLogin);
		settings.put(MailAlertHandler.PASSWORD, cyphPassword);
		settings.put(MailAlertHandler.HOST, mailUser_host);
		settings.put(MailAlertHandler.TYPE, mailUser_type);
		if (Factories.SOURCE.findByPathAndVal(source, 0) == null) {
			Factories.SOURCE.createNewSource(source, 0);
		}

		inSubscription.setSettings(settings);
	}

	/**
	 * Permet de supprimer un compte mail surveillé
	 * 
	 * @param mailUser_srvSrv : ref qui permet de supprimer dans mailUser,
	 *            mailKeyword, srv et source.
	 * @return result : requete reussie ou non
	 * @throws InvalidParameterException
	 */
	public static void delMailAccount(List<SubscriptionData> subscriptions, String src, String word) {
		boolean delAll = true;
		if ((word != null) && !word.equals(StringShop.EMPTY_STRING)) {
			delAll = false;
		}
		for (final SubscriptionData subscription : subscriptions) {
			final String source = subscription.getSettings().get(MailAlertHandler.SOURCE).toString();
			if (source.equals(src)) {
				if (delAll) {
					SubscriptionManager.deleteSubscription(subscription);
				} else {
					// On vire uniquement le keyword
					final MailUserData mailUser = MailUserData.getFromSubscription(subscription);
					final List<MailKeywordData> mailKeywords = mailUser.getKeywords();
					int nbKeywords = mailKeywords.size();
					if (nbKeywords == 1) {
						// un seul keyword
						MailServices.delAllKeywords(subscription);
					} else {
						final Iterator<MailKeywordData> iterator = mailKeywords.iterator();
						while (iterator.hasNext()) {
							// for (final MailKeywordData mailKeyword :
							// mailKeywords) {
							final MailKeywordData mailKeyword = iterator.next();
							if (word != null && word.equals(mailKeyword.getMailKeyword_keyword())) {
								if (mailKeyword.getMailKeyword_passive() > 0) {
									// on vire l'ambiant with keyword
									final List<SubscriptionSchedulingData> ambiantSchedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword);
									for (final SubscriptionSchedulingData ambiantScheduling : ambiantSchedulings) {
										ambiantScheduling.delete();
									}
								}
								// mailKeywords.remove(mailKeyword);
								iterator.remove();
							}
						}
						// on récup le nouveau nombre de keywords
						nbKeywords = mailKeywords.size();
						// on suprime tous les keywords
						MailServices.delAllKeywords(subscription);
						// on rajoute la nouvelle liste
						for (int i = 0; i < nbKeywords; i++) {
							final MailKeywordData mailKeyword = mailKeywords.get(i);
							MailServices.addKeyword(subscription, i, mailKeyword.getMailKeyword_keyword(), mailKeyword.getMailKeyword_passive(), mailKeyword.getMailKeyword_music_id());
						}
					}
				}
				// break;
			}
		}
	}

	/**
	 * Permet d'ajouter un keyword à un compte mail surveillé
	 * 
	 * @param mailUser_srvSrv : ref le compte surveillé
	 * @return result : requete reussie ou non
	 */
	public static void addKeyword(SubscriptionData inSubscription, int position, String keyword, int passive, long inMusicId) {
		final String source = inSubscription.getSettings().get(MailAlertHandler.SOURCE).toString();
		final String keywordKey = MailAlertHandler.FILTER + "." + position + "." + MailAlertHandler.KEYWORD;
		final String musicKey = MailAlertHandler.FILTER + "." + position + "." + MailAlertHandler.MEDIA;
		inSubscription.setSetting(keywordKey, keyword);
		inSubscription.setSetting(musicKey, Long.toString(inMusicId));
		// On ajoute un keyword, on vire l'Ambiant s'il est setté
		final List<SubscriptionSchedulingData> ambiantSchedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(inSubscription, SchedulingType.SCHEDULING_TYPE.Ambiant);
		for (final SubscriptionSchedulingData ambiant : ambiantSchedulings) {
			ambiant.delete();
		}
		// On vire le new content s'il est setté
		final List<SubscriptionSchedulingData> newContentSchedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(inSubscription, SchedulingType.SCHEDULING_TYPE.NewContent);
		for (final SubscriptionSchedulingData newContent : newContentSchedulings) {
			newContent.delete();
		}
		if (passive > 0) {
			// Alerte passive
			final SubscriptionSchedulingData scheduling;
			final List<SubscriptionSchedulingData> schedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(inSubscription, SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword);
			if (!schedulings.isEmpty()) {
				// Il y a déja une alerte passive donc faut virer
				for (final SubscriptionSchedulingData scheduling2Del : schedulings) {
					scheduling2Del.delete();
				}
			}
			// pas encore d'alerte passive settée
			scheduling = SubscriptionSchedulingData.create(inSubscription, SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword);
			scheduling.createSetting(KeywordHandler.KEYWORD, keyword);
		}
		// List<SubscriptionSchedulingData> schedulings =
		// SubscriptionSchedulingData
		// .findAllBySubscriptionAndType(inSubscription,
		// SchedulingType.SCHEDULING_TYPE.NewContentWithKeywordAndMedia);
		final SubscriptionSchedulingData newContentWithKeywordScheduling = SubscriptionSchedulingData.create(inSubscription, SchedulingType.SCHEDULING_TYPE.NewContentWithKeywordAndMedia);
		newContentWithKeywordScheduling.createSetting(KeywordHandler.KEYWORD, keyword);
		newContentWithKeywordScheduling.createSetting(NewContentWithKeywordAndMediaHandler.MEDIA, Long.toString(inMusicId));
		newContentWithKeywordScheduling.createSetting(MailAlertHandler.NEW_CONTENT_FLAG, "0");

		// On cree la nouvelle source
		if (Factories.SOURCE.findByPathAndVal(source + "." + keyword, 0) == null) {
			Factories.SOURCE.createNewSource(source + "." + keyword, 0);
		}
	}

	public static void delAllKeywords(SubscriptionData subscription) {
		boolean deleted = false;
		final String source = subscription.getSettings().get(MailAlertHandler.SOURCE).toString();
		for (final Entry<String, Object> setting : subscription.getSettings().entrySet()) {
			if (setting.getKey().contains(MailAlertHandler.FILTER + ".")) {
				// On vire la source
				final Source src = Factories.SOURCE.findByPath(source + "." + setting.getValue());
				if (src != null) {
					src.delete();
				}

				subscription.removeSetting(setting.getKey());

				if (!deleted) {
					deleted = true;
				}
			}
		}
		for (final SubscriptionSchedulingData scheduling : SubscriptionSchedulingData.findAllBySubscription(subscription)) {
			if (scheduling.getType().equals(SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword) || scheduling.getType().equals(SchedulingType.SCHEDULING_TYPE.NewContentWithKeywordAndMedia) || scheduling.getType().equals(SchedulingType.SCHEDULING_TYPE.NewContent)) {
				scheduling.delete();
				if (!deleted) {
					deleted = true;
				}
			}
		}
		if (deleted) {
			SubscriptionSchedulingData.create(subscription, SchedulingType.SCHEDULING_TYPE.NewContent);
		}
	}

	/**
	 * Permet de recuperer un comptes surveillé via un id de service
	 * 
	 * @param srv_src : id du lapin
	 * @return result : requete reussie ou non
	 */
	public static Map<String, String> getUserMailAccountInfo(MailUserData mailUser) {
		final Map<String, String> result = new HashMap<String, String>();
		if (mailUser != null) {
			result.put("mailUser_srvSrc", mailUser.getMailUser_srvSrc());
			result.put("mailUser_login", CipherTools.uncipher(mailUser.getMailUser_login()));
			result.put("mailUser_password", CipherTools.uncipher(mailUser.getMailUser_password()));
			result.put("mailUser_lastMail", mailUser.getMailUser_lastMail());
			result.put("mailUser_host", mailUser.getMailUser_host());
			result.put("mailUser_protocol", mailUser.getMailUser_type());
			result.put("mailUser_type", mailUser.getMailUser_type());
			if (!SubscriptionSchedulingData.findAllBySubscriptionAndType(mailUser.getSubscription(), SchedulingType.SCHEDULING_TYPE.NewContent).isEmpty() || !SubscriptionSchedulingData.findAllBySubscriptionAndType(mailUser.getSubscription(), SchedulingType.SCHEDULING_TYPE.NewContentWithKeywordAndMedia).isEmpty()) {
				result.put("musicId", "1");
			} else {
				result.put("musicId", "0");
			}
			if (!SubscriptionSchedulingData.findAllBySubscriptionAndType(mailUser.getSubscription(), SchedulingType.SCHEDULING_TYPE.Ambiant).isEmpty() || !SubscriptionSchedulingData.findAllBySubscriptionAndType(mailUser.getSubscription(), SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword).isEmpty()) {
				result.put("passive", "1");
			} else {
				result.put("passive", "0");
			}
		}
		return result;
	}

	/**
	 * Permet de récuperer les accounts Mail via l'id du compte surveillé
	 * 
	 * @param srv_Src : id du compte mail surveillé
	 * @return result : requete reussie ou non
	 */
	public static void updMailAccount(SubscriptionData subscription, String mailUser_password, String mailUser_login, String mailUser_type, String mailUser_host) {
		final String cryptedLogin = (mailUser_login != null) ? CipherTools.cipher(mailUser_login) : null;
		final String cryptedPassword = (mailUser_password != null) ? CipherTools.cipher(mailUser_password) : null;
		for (final Entry<String, Object> setting : subscription.getSettings().entrySet()) {
			if (MailAlertHandler.LOGIN.equals(setting.getKey()) && (setting.getValue() != null) && !setting.getValue().equals(cryptedLogin)) {
				setting.setValue(cryptedLogin);
			}
			if (MailAlertHandler.PASSWORD.equals(setting.getKey()) && (setting.getValue() != null) && !setting.getValue().equals(cryptedPassword)) {
				setting.setValue(cryptedPassword);
			}
			if (MailAlertHandler.HOST.equals(setting.getKey()) && (setting.getValue() != null) && !setting.getValue().equals(mailUser_host)) {
				setting.setValue(mailUser_host);
			}
			if (MailAlertHandler.TYPE.equals(setting.getKey()) && (setting.getValue() != null) && !setting.getValue().equals(mailUser_type)) {
				setting.setValue(mailUser_type);
			}
		}
	}

	public static int isReg(VObject object) {
		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MAIL.getApplication(), object);
		return (subscriptionsList.isEmpty()) ? 0 : 1;
	}

	public static boolean isThisObjectHaveThisAccount(VObject object, String account) {
		final String cipherAccount = CipherTools.cipher(account);
		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MAIL.getApplication(), object);
		for (final SubscriptionData subscription : subscriptionsList) {
			final Object loginSetting = subscription.getSettings().get(MailAlertHandler.LOGIN);
			if ((loginSetting != null) && cipherAccount.equals(loginSetting.toString())) {
				return true;
			}
		}
		return false;
	}
}
