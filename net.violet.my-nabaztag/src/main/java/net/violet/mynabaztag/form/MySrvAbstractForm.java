package net.violet.mynabaztag.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.ServiceFactory;
import net.violet.platform.dataobjects.FrequenceData;
import net.violet.platform.dataobjects.PeriodServiceDataFactory;
import net.violet.platform.dataobjects.TtsLangData;
import net.violet.platform.util.SessionTools;
import net.violet.platform.util.StringShop;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public abstract class MySrvAbstractForm extends AbstractForm {

	private static final Pattern pRegex = Pattern.compile("([0-9][0-9])");

	private String url = StringShop.EMPTY_STRING;
	private String srvFrequencyListening = "0";
	private int srvVoice = 2;
	private boolean subscribed;
	private int scenarioId;
	private long subscriptionId;
	private long applicationId;
	private String srvModeListener = "2";
	private int srvNbNews = 5;
	private String scheduleWE = StringShop.EMPTY_STRING;
	private String scheduleW = StringShop.EMPTY_STRING;
	private String name = StringShop.EMPTY_STRING;
	private List<FrequenceData> periodList = new ArrayList<FrequenceData>();
	private Collection<TtsLangData> langList = new ArrayList<TtsLangData>();
	private boolean isGood;

	private String dispatch;

	public String getDispatch() {
		return this.dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		setScheduleW(StringShop.EMPTY_STRING);
		setScheduleWE(StringShop.EMPTY_STRING);
		final Lang lang = SessionTools.getLangFromSession(request.getSession(true), request);
		setPeriodList(PeriodServiceDataFactory.generateListFrequence(ServiceFactory.SERVICE.RSS.getService(), lang));
		super.reset(mapping, request);
	}

	@Override
	public ActionErrors validate(@SuppressWarnings("unused") ActionMapping mapping, @SuppressWarnings("unused") HttpServletRequest request) {
		final ActionErrors myErrors = new ActionErrors();

		final Matcher myMatcher = MySrvAbstractForm.pRegex.matcher(getScheduleW());

		if (!getScheduleW().equals(StringShop.EMPTY_STRING) && (2 != MySrvAbstractForm.find(myMatcher))) {
			myErrors.add("WScheduleNotValid", new ActionMessage("errors.key", StringShop.EMPTY_STRING));
		}

		myMatcher.reset(getScheduleWE());

		if (!getScheduleWE().equals(StringShop.EMPTY_STRING) && (2 != MySrvAbstractForm.find(myMatcher))) {
			myErrors.add("WEScheduleNotValid", new ActionMessage("errors.key", StringShop.EMPTY_STRING));
		}

		myMatcher.reset(getSrvFrequencyListening());

		if (!getSrvFrequencyListening().equals("0") && (3 != MySrvAbstractForm.find(myMatcher))) {
			myErrors.add("PeriodNotValid", new ActionMessage("errors.key", StringShop.EMPTY_STRING));
		}

		return myErrors;
	}

	/**
	 * Finds the amount of match from a matcher
	 * 
	 * @param myMatcher
	 * @return the amount of match
	 */
	private static int find(Matcher myMatcher) {
		int nbFind = 0;

		while (myMatcher.find()) {
			nbFind++;
		}

		return nbFind;
	}

	public List<FrequenceData> getPeriodList() {
		return this.periodList;
	}

	public void setPeriodList(List<FrequenceData> periodList) {
		this.periodList = periodList;
	}

	public int getScenarioId() {
		return this.scenarioId;
	}

	public void setScenarioId(int podcastId) {
		this.scenarioId = podcastId;
	}

	public String getSrvFrequencyListening() {
		return this.srvFrequencyListening;
	}

	public void setSrvFrequencyListening(String srvFrequencyListening) {
		this.srvFrequencyListening = srvFrequencyListening;
	}

	public String getSrvModeListener() {
		return this.srvModeListener;
	}

	public void setSrvModeListener(String srvModeListener) {
		this.srvModeListener = srvModeListener;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String srvName) {
		this.name = srvName;
	}

	public int getSrvNbNews() {
		return this.srvNbNews;
	}

	public void setSrvNbNews(int srvNbNews) {
		this.srvNbNews = srvNbNews;
	}

	public String getScheduleW() {
		return this.scheduleW;
	}

	public void setScheduleW(String scheduleW) {
		this.scheduleW = scheduleW;
	}

	public String getScheduleWE() {
		return this.scheduleWE;
	}

	public void setScheduleWE(String scheduleWE) {
		this.scheduleWE = scheduleWE;
	}

	public int getSrvVoice() {
		return this.srvVoice;
	}

	public Collection<TtsLangData> getLangList() {
		return this.langList;
	}

	public void setLangList(Collection<TtsLangData> langList) {
		this.langList = langList;
	}

	public void setSrvVoice(int srvVoice) {
		this.srvVoice = srvVoice;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getSubscriptionId() {
		return this.subscriptionId;
	}

	public void setSubscriptionId(long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public boolean isGood() {
		return this.isGood;
	}

	public void setGood(boolean good) {
		this.isGood = good;
	}

	public String getSubscribed() {
		return Boolean.toString(this.subscribed);
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public long getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(long applicationId) {
		this.applicationId = applicationId;
	}

}
