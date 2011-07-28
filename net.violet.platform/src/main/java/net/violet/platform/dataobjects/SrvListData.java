package net.violet.platform.dataobjects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationTemp;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.NabLife;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public final class SrvListData {

	private static final Logger LOGGER = Logger.getLogger(SrvListData.class);

	/** Cache for nablife */

	/** key : langId */
	private static final Map<Lang, Set<SrvListData>> srvListByLangCache = new HashMap<Lang, Set<SrvListData>>();
	/** key : srvList */
	private static final Map<Application, SrvListData> srvListDataBySrvListCache = new HashMap<Application, SrvListData>();
	/** key : srvList shortcut */
	private static final Map<String, SrvListData> srvListByShortCutCache = SrvListData.createCacheSrvListByShortcut();
	/** key : langId-srvCategId */
	private static final Map<String, List<SrvListData>> srvListCache = SrvListData.createCacheSrvList();
	/** key : langId-srvCategId */
	private static final Map<String, List<SrvListData>> srvListDataCache = SrvListData.createListServicesByLangAndCateg();

	// final private SrvList srvList;
	private final NabLife nabLife;
	private final Application application;
	private final String srvSetup;
	private final String link;
	private final String desc;
	private final String name;
	private final long nbVote;
	private final String srvTypeName;
	private final long srvTypeId;
	private final String srvImg;
	private final String srvIcone;
	private final long id;

	/*
	 * private SrvListData(NabLife inNabLife) { nabLife = inNabLife; scenario =
	 * inNabLife.getScenario(); if (scenario==null) { final String tmpLink =
	 * (inNabLife.getLink()==null) ? StringShop.EMPTY_STRING :
	 * inNabLife.getLink(); String separator = "?"; if (tmpLink.contains("?"))
	 * separator = "&"; link= tmpLink+separator+"srvId="+inNabLife.getId(); id =
	 * nabLife.getId(); } else { link= inNabLife.getLink()+scenario.getId(); id
	 * = scenario.getId(); } srvTypeId = inNabLife.getService().getId();
	 * srvTypeName = inNabLife.getService().getLabel(); desc =
	 * inNabLife.getDescription(); name = inNabLife.getName(); srvImg =
	 * inNabLife.getImg(); nbVote= inNabLife.getNbvote(); srvSetup =
	 * inNabLife.getSetup(); srvIcone = (inNabLife.getIcone()==null) ?
	 * Constantes.ICO_SERVICE_LINK : inNabLife.getIcone(); application = null; }
	 */

	private SrvListData(Application application) {
		this.application = application;
		this.srvTypeName = StringShop.EMPTY_STRING;
		this.srvTypeId = 0;
		this.srvSetup = "CONFIG";

		this.name = ((application == null) || (application.getProfile() == null)) ? StringShop.EMPTY_STRING : application.getProfile().getTitle();
		this.desc = ((application == null) || (application.getProfile() == null)) ? StringShop.EMPTY_STRING : application.getProfile().getDescription();
		this.srvImg = ((application == null) || (application.getTemp() == null)) ? StringShop.EMPTY_STRING : application.getTemp().getImage();
		this.srvIcone = ((application == null) || (application.getTemp() == null)) ? StringShop.EMPTY_STRING : application.getTemp().getIcone();
		this.id = (application == null) ? 0 : application.getId();
		this.nabLife = null;
		this.nbVote = 0;
		if ((application != null) && (application.equals(Application.NativeApplication.RSS_FULL.getApplication()) || application.equals(Application.NativeApplication.PODCAST_FULL.getApplication()))) {
			this.link = application.getTemp().getLink() + "config&applicationId=" + application.getId();
		} else if ((application != null) && (application.getTemp() != null)) {
			this.link = application.getTemp().getLink() + "&applicationId=" + application.getId();
		} else {
			this.link = StringShop.EMPTY_STRING;
		}

	}

	/**
	 * Returns the SrvList used to create this srvListData
	 * 
	 * @return
	 */
	NabLife getNabLife() {
		return this.nabLife;
	}

	Application getApplication() {
		return this.application;
	}

	static List<SrvListData> findAllSrvListDataByLangAndSrvCateg(Lang inLang, ApplicationCategory inSrvCateg) {
		final List<SrvListData> srvListDataList = new ArrayList<SrvListData>();

		for (final Application application : Factories.APPLICATION.findAllByLangAndCateg(inLang, Factories.APPLICATION_CATEGORY.find(inSrvCateg.getId()))) {
			srvListDataList.add(new SrvListData(application));
		}

		final String key = SrvListData.getKey(inLang, inSrvCateg);
		if (SrvListData.srvListDataCache.containsKey(key)) {
			srvListDataList.addAll(SrvListData.srvListDataCache.get(key));
		}

		return srvListDataList;
	}

	/**
	 * Return the MAP key for the given lang and srvCateg
	 * 
	 * @param inLang
	 * @param inSrvCateg
	 */
	private static String getKey(Lang inLang, ApplicationCategory inSrvCateg) {
		return ((inLang != null) ? inLang.getId() : 0) + "-" + ((inSrvCateg != null) ? inSrvCateg.getId() : 0);
	}

	/**
	 * Function used to cache the table srvlist: only the services in the old
	 * model
	 * 
	 * @return
	 */
	private static Map<String, List<SrvListData>> createCacheSrvList() {
		final Map<String, List<SrvListData>> srvListTemp = new HashMap<String, List<SrvListData>>();

		final List<SrvListData> tempNabLifeList = new ArrayList<SrvListData>();

		final List<ApplicationTemp> applications = Factories.APPLICATION_TEMP.findAll();

		for (final ApplicationTemp application : Factories.APPLICATION_TEMP.findAll()) {
			tempNabLifeList.add(new SrvListData(application.getApplication()));
		}

		for (final Lang lang : Factories.LANG.findAll()) {
			final Set<SrvListData> tempSet = new HashSet<SrvListData>();
			// for (NabLife nabLife : tempNabLifeList) {
			for (final ApplicationTemp nabLife : applications) {
				if (nabLife.getApplication().getLangs().contains(lang)) {
					if (SrvListData.srvListByLangCache.containsKey(lang)) {
						tempSet.addAll(SrvListData.srvListByLangCache.get(lang));
					}
					final ApplicationCategory categ = nabLife.getApplication().getCategory();
					if (categ != null) {
						final String key = lang.getId() + "-" + categ.getId();
						if (!srvListTemp.containsKey(key)) {
							srvListTemp.put(key, new ArrayList<SrvListData>());
						}
						final SrvListData srvListData = new SrvListData(nabLife.getApplication());
						srvListTemp.get(key).add(srvListData);
						tempSet.add(srvListData);
					}
				}

			}
			SrvListData.srvListByLangCache.put(lang, tempSet);
		}
		return srvListTemp;
	}

	private static Map<String, SrvListData> createCacheSrvListByShortcut() {
		final Map<String, SrvListData> srvListMap = new HashMap<String, SrvListData>();

		for (final ApplicationTemp applicationTemp : Factories.APPLICATION_TEMP.findAll()) {
			SrvListData.LOGGER.debug("Liste des services par shortcut : ");
			final SrvListData srvListData = new SrvListData(applicationTemp.getApplication());
			if (applicationTemp.getShortcut() != null) {
				srvListMap.put(applicationTemp.getShortcut(), srvListData);
				SrvListData.LOGGER.debug(srvListData.toString());
			}
			SrvListData.srvListDataBySrvListCache.put(applicationTemp.getApplication(), srvListData);
		}

		return srvListMap;
	}

	/**
	 * Returns a generated list of SrvListData from the given Set of SrvList
	 * 
	 * @param inSrvList
	 * @return
	 */
	public static List<SrvListData> findAllSrvListDataFromSrvList(Set<NabLife> inSrvList) {
		final List<SrvListData> srvListData = new ArrayList<SrvListData>();

		if ((inSrvList != null) && (inSrvList.size() != 0)) {
			for (final NabLife srvList : inSrvList) {
				if (SrvListData.srvListDataBySrvListCache.containsKey(srvList)) {
					srvListData.add(SrvListData.srvListDataBySrvListCache.get(srvList));
				} else {
					SrvListData.LOGGER.debug("DOESNT CONTAIN " + srvList.getName());
				}
			}
		} else {
			SrvListData.LOGGER.debug("inSrvList NULL");
		}

		return srvListData;
	}

	/**
	 * return all available SrvListData for the given parameters sorted by
	 * category
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Map<String, List<SrvListData>> createListServicesByLangAndCateg() {
		final Map<String, List<SrvListData>> srvListDataMap = new HashMap<String, List<SrvListData>>();

		for (final Lang lang : Factories.LANG.findAll()) {
			for (final ApplicationCategoryData srvCateg : ApplicationCategoryData.findAll()) {
				final List<SrvListData> srvListDataList = new ArrayList<SrvListData>();
				final String key = SrvListData.getKey(lang, srvCateg.getRecord());
				final List<SrvListData> listForOldModel = SrvListData.generateList(srvCateg.getRecord(), lang);
				// LOGGER.debug("List4OldModel size : " +
				// listForOldModel.size());
				if (listForOldModel.size() > 0) {
					srvListDataList.addAll(listForOldModel);
				}

				srvListDataMap.put(key, srvListDataList);
			}
		}
		return srvListDataMap;
	}

	/**
	 * Generates a list of SrvListData with the given SourceImpl list
	 * 
	 * @param inLang
	 * @return
	 */
	private static List<SrvListData> generateList(ApplicationCategory inSrvCateg, Lang inLang) {
		final String key = SrvListData.getKey(inLang, inSrvCateg);
		final List<SrvListData> theSrvList = SrvListData.srvListCache.get(key);
		final List<SrvListData> srvListDataList = new ArrayList<SrvListData>();

		if (theSrvList != null) {
			for (final SrvListData tempNabLife : theSrvList) {
				srvListDataList.add(tempNabLife);
			}
		}

		return srvListDataList;
	}

	static SrvListData findByShortcut(String inShortcut) {
		return SrvListData.srvListByShortCutCache.get(inShortcut);
	}

	/**
	 * @return the attribute id (srvList or scenario)
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return the attribute name
	 */
	public String getName() {
		if ((this.nabLife != null) && (this.nabLife.getName() != null)) {
			return this.nabLife.getName();
		}
		return this.name;
	}

	/**
	 * @return the attribute desc
	 */
	public String getDesc() {
		if ((this.nabLife != null) && (this.nabLife.getDescription() != null)) {
			return this.nabLife.getDescription();
		}
		return this.desc;
	}

	/**
	 * @return the attribute link
	 */
	public String getLink() {
		return this.link;
	}

	/**
	 * @return the attribute type
	 */
	public long getType() {
		if ((this.nabLife != null) && (this.nabLife.getService() != null)) {
			return this.nabLife.getService().getId();
		}
		return this.srvTypeId;
	}

	/**
	 * @return the attribute vote
	 */
	public long getVote() {
		return this.nbVote;
	}

	/**
	 * @return the attribute srvType
	 */
	public String getSrvTypeName() {
		if ((this.nabLife != null) && (this.nabLife.getService() != null) && (this.nabLife.getService().getLabel() != null)) {
			return this.nabLife.getService().getLabel();
		}
		return this.srvTypeName;
	}

	/**
	 * @return the attribute srvImg
	 */
	public String getSrvImg() {
		if ((this.nabLife != null) && (this.nabLife.getImg() != null)) {
			return this.nabLife.getImg();
		}
		return this.srvImg;
	}

	/**
	 * @return the attribute srvlist_setup
	 */
	public String getSrvlist_setup() {
		if ((this.nabLife != null) && (this.nabLife.getSetup() != null)) {
			return this.nabLife.getSetup();
		}
		return this.srvSetup;
	}

	public String getSrvIcone() {
		return this.srvIcone;
	}

	public String getSrvShortCut() {
		if ((this.nabLife != null) && (this.nabLife.getShortcut() != null)) {
			return this.nabLife.getShortcut();
		}
		return StringShop.EMPTY_STRING;
	}

}
