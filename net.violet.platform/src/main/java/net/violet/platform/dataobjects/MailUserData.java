package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import net.violet.platform.applications.MailAlertHandler;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.schedulers.KeywordHandler;
import net.violet.platform.util.CipherTools;

public final class MailUserData {

	private final List<MailKeywordData> mailkeywordDataList = new ArrayList<MailKeywordData>();
	private final String identifiant;
	private final String login;
	private final String password;
	private final String source;
	private final String type;
	private final String host;
	private final long passive;
	private final long nbr;
	private final SubscriptionData subscription;
	private final int secure;

	private MailUserData(String inIdentifiant, String inLogin, String inPassword, String inType, String inHost, String inSource, List<MailKeywordData> inKeywords, long inPassive, SubscriptionData inSubscription, int inSecure) {
		this.identifiant = inIdentifiant;
		this.login = inLogin;
		this.password = inPassword;
		this.type = inType;
		this.host = inHost;
		this.source = inSource;
		this.mailkeywordDataList.addAll(inKeywords);
		this.passive = inPassive;
		final Source theSource = Factories.SOURCE.findByPath(inSource);
		this.nbr = (theSource == null) ? 0 : theSource.getSource_val();
		this.subscription = inSubscription;
		this.secure = inSecure;
	}

	/**
	 * Permet de recuperer l'ensemble des comptes surveillés via un id de lapin
	 * 
	 * @param obj_id : id du lapin
	 * @return result : requete reussie ou non
	 */
	public static List<MailUserData> FindAllUserMailAccountsInfo(VObject inObject) {
		final List<SubscriptionData> subscriptionsList = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MAIL.getApplication(), inObject);
		return MailUserData.FindAllUserMailAccountsInfo(subscriptionsList);
	}

	public static List<MailUserData> FindAllUserMailAccountsInfo(List<SubscriptionData> inSubscriptions) {
		if (!inSubscriptions.isEmpty()) {
			return MailUserData.generateListFromSubscriptions(inSubscriptions);
		}
		return Collections.emptyList();
	}

	public static MailUserData FindMailUserDataInfo(VObject inObject, String inSource) {
		MailUserData mailUser = null;
		final List<MailKeywordData> filters = new ArrayList<MailKeywordData>();
		final List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(Application.NativeApplication.MAIL.getApplication(), inObject);
		SubscriptionData subscription = null;
		for (final SubscriptionData sd : subscriptions) {
			if (inSource.equals(sd.getSettings().get(MailAlertHandler.SOURCE))) {
				subscription = sd;
				break;
			}
		}
		if (subscription != null) {
			String login = net.violet.common.StringShop.EMPTY_STRING;
			String password = net.violet.common.StringShop.EMPTY_STRING;
			String type = net.violet.common.StringShop.EMPTY_STRING;
			String host = net.violet.common.StringShop.EMPTY_STRING;
			int secure = 0;
			final String identifiant = "0";
			long passiveWithoutKeyword = 0;
			List<SubscriptionSchedulingData> schedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.Ambiant);
			if (schedulings.isEmpty()) {
				schedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword);
			} else {
				passiveWithoutKeyword = 1;
			}
			for (final Entry<String, Object> setting : subscription.getSettings().entrySet()) {
				if (MailAlertHandler.SECURE.equals(setting.getKey())) {
					secure = 1;
				}
				if (MailAlertHandler.LOGIN.equals(setting.getKey())) {
					login = setting.getValue().toString();
				}
				if (MailAlertHandler.PASSWORD.equals(setting.getKey())) {
					password = setting.getValue().toString();
				}
				if (MailAlertHandler.TYPE.equals(setting.getKey())) {
					type = setting.getValue().toString();
				}
				if (MailAlertHandler.HOST.equals(setting.getKey())) {
					host = setting.getValue().toString();
				}
				if (setting.getKey().contains(MailAlertHandler.FILTER + ".")) {
					final String[] filterData = setting.getKey().split("\\.");
					if (MailAlertHandler.KEYWORD.equals(filterData[2])) {
						int passiveKeyword = 0;
						final String keyword = setting.getValue().toString();
						final String music = subscription.getSettings().get(filterData[0] + "." + filterData[1] + "." + MailAlertHandler.MEDIA).toString();
						// Récup de l'info sur l'alerte passive
						if ((passiveWithoutKeyword == 0) && !schedulings.isEmpty()) {
							if (SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(schedulings.get(0), KeywordHandler.KEYWORD).getValue().equals(keyword)) {
								passiveKeyword = 1;
							}
						}
						//
						filters.add(new MailKeywordData(subscription, keyword, Long.parseLong(music), inSource, passiveKeyword));
					}
				}
			}
			mailUser = new MailUserData(identifiant, login, password, type, host, inSource, filters, passiveWithoutKeyword, subscription, secure);
		}
		return mailUser;
	}

	public static MailUserData getFromSubscription(SubscriptionData inSubscription) {
		final List<MailUserData> mails = MailUserData.generateListFromSubscriptions(Arrays.asList(new SubscriptionData[] { inSubscription, }));
		return (mails.isEmpty()) ? null : mails.get(0);
	}

	private static List<MailUserData> generateListFromSubscriptions(List<SubscriptionData> inSubscriptions) {
		final List<MailUserData> mails = new ArrayList<MailUserData>();
		String login = net.violet.common.StringShop.EMPTY_STRING;
		String password = net.violet.common.StringShop.EMPTY_STRING;
		String type = net.violet.common.StringShop.EMPTY_STRING;
		String host = net.violet.common.StringShop.EMPTY_STRING;
		String source = net.violet.common.StringShop.EMPTY_STRING;
		int secure = 0;
		final String identifiant = "0";
		final List<MailKeywordData> filters = new ArrayList<MailKeywordData>();
		int passiveWithoutKeyword = 0;
		for (final SubscriptionData subscription : inSubscriptions) {
			source = subscription.getSettings().get(MailAlertHandler.SOURCE).toString();
			List<SubscriptionSchedulingData> schedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.Ambiant);
			if (schedulings.isEmpty()) {
				schedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword);
			} else {
				passiveWithoutKeyword = 1;
			}

			for (final Entry<String, Object> setting : subscription.getSettings().entrySet()) {
				if (MailAlertHandler.SECURE.equals(setting.getKey())) {
					secure = 1;
				} else if (MailAlertHandler.LOGIN.equals(setting.getKey())) {
					login = CipherTools.uncipher(setting.getValue().toString());
				} else if (MailAlertHandler.PASSWORD.equals(setting.getKey())) {
					password = CipherTools.uncipher(setting.getValue().toString());
				} else if (MailAlertHandler.TYPE.equals(setting.getKey())) {
					type = setting.getValue().toString();
				} else if (MailAlertHandler.HOST.equals(setting.getKey())) {
					host = setting.getValue().toString();
				} else if (setting.getKey().contains(MailAlertHandler.FILTER + ".")) {
					final String[] filterData = setting.getKey().split("\\.");
					if (MailAlertHandler.KEYWORD.equals(filterData[2])) {
						int passiveKeyword = 0;
						final String keyword = setting.getValue().toString();
						final String music = subscription.getSettings().get(filterData[0] + "." + filterData[1] + "." + MailAlertHandler.MEDIA).toString();
						// Récup de l'info sur l'alerte passive
						if ((passiveWithoutKeyword == 0) && !schedulings.isEmpty()) {
							if (SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(schedulings.get(0), KeywordHandler.KEYWORD).getValue().equals(keyword)) {
								passiveKeyword = 1;
							}
						}
						//

						if (music != null) {
							filters.add(new MailKeywordData(subscription, keyword, Long.parseLong(music), source, passiveKeyword));
						}
					}
				}
			}
			mails.add(new MailUserData(identifiant, login, password, type, host, source, filters, passiveWithoutKeyword, subscription, secure));
		}
		return mails;
	}

	/**
	 * @return the attribute mailUser_login
	 */
	public String getMailUser_login() {
		return this.login;
	}

	/**
	 * @return the attribute identifiant
	 */
	public String getIdentifiant() {
		return this.identifiant;
	}

	/**
	 * @return the attribute mailUser_password
	 */
	public String getMailUser_password() {
		return this.password;
	}

	/**
	 * @return the attribute mailUser_srvSrc
	 */
	public String getMailUser_srvSrc() {
		if (this.source != null) {
			return this.source;
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute mailUser_lastMail
	 */
	public String getMailUser_lastMail() {
		/*
		 * if (mailUserRef != null && mailUserRef.get() != null &&
		 * mailUserRef.get().getMailUser_lastMail() != null) return
		 * mailUserRef.get().getMailUser_lastMail();
		 */
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute mailUser_host
	 */
	public String getMailUser_host() {
		if (this.host != null) {
			return this.host;
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute mailUser_type
	 */
	public String getMailUser_type() {
		if (this.type != null) {
			return this.type;
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute mailUser_passive
	 */
	public long getMailUser_passive() {
		return this.passive;
	}

	/**
	 * @return the attribute mailUser_nbr
	 */
	public long getMailUser_nbr() {
		return this.nbr;
	}

	/**
	 * @return the attribute keywords
	 */
	public List<MailKeywordData> getKeywords() {
		return this.mailkeywordDataList;
	}

	public SubscriptionData getSubscription() {
		return this.subscription;
	}

	public int isSecure() {
		return this.secure;
	}

}
