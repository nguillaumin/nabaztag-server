package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import net.violet.platform.applications.AirHandler;
import net.violet.platform.applications.MailAlertHandler;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.SchedulingType;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.schedulers.KeywordHandler;

public final class MailKeywordData {

	private final String keyword;
	private final long musicId;
	private final String source;
	private final int passive;
	private final SubscriptionData subscription;

	public MailKeywordData(SubscriptionData inSubscription, String inKeyword, long inMusicId, String inSource, int inPassive) {
		this.keyword = inKeyword;
		this.musicId = inMusicId;
		this.source = inSource;
		this.passive = inPassive;
		this.subscription = inSubscription;
	}

	/**
	 * Permet de récuperer les accounts Mail via l'id du compte surveillé
	 * 
	 * @param srv_Src : id du compte mail surveillé
	 * @return result : requete reussie ou non
	 */
	public static List<MailKeywordData> findKeywordsInfo(List<SubscriptionData> inSubscriptions, String inSource) {
		final List<MailKeywordData> keywords = new ArrayList<MailKeywordData>();
		for (final SubscriptionData subscription : inSubscriptions) {
			final String source = subscription.getSettings().get(AirHandler.SOURCE_SETTING).toString();
			if (source.equals(inSource)) {
				final List<SubscriptionSchedulingData> schedulings = SubscriptionSchedulingData.findAllBySubscriptionAndType(subscription, SchedulingType.SCHEDULING_TYPE.AmbiantWithKeyword);
				int passive = 0;
				for (final Entry<String, Object> aSetting : subscription.getSettings().entrySet()) {
					if (aSetting.getKey().contains(MailAlertHandler.FILTER + ".")) {
						final List<String> filterData = (List<String>) aSetting.getValue();
						if (MailAlertHandler.KEYWORD.equals(filterData.get(2))) {
							final String keyword = aSetting.getValue().toString();
							final String music = subscription.getSettings().get(filterData.get(0) + "." + filterData.get(1) + "." + MailAlertHandler.MEDIA).toString();
							// Récup de l'info sur l'alerte passive
							if (!schedulings.isEmpty()) {
								if (SubscriptionSchedulingSettingsData.findBySubscriptionSchedulingAndKey(schedulings.get(0), KeywordHandler.KEYWORD).getValue().equals(keyword)) {
									passive = 1;
								}
							}
							//
							keywords.add(new MailKeywordData(subscription, keyword, Long.parseLong(music), inSource, passive));
						}
					}
				}
			}
		}
		return keywords;
	}

	public String getMailKeyword_srvSrc() {
		if (this.source != null) {
			return this.source;
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute mailKeyword_musicName
	 */
	public String getMailKeyword_musicName() {
		final Music music = Factories.MUSIC.find(this.musicId);
		if (music != null) {
			return music.getMusic_name();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getMailKeyword_keyword() {
		if (this.keyword != null) {
			return this.keyword;
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public long getMailKeyword_music_id() {
		return this.musicId;
	}

	public int getMailKeyword_passive() {
		return this.passive;
	}

	public long getSubscriptionId() {
		return this.subscription.getId();
	}

}
