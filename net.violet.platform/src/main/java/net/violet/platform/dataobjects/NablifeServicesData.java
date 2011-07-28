package net.violet.platform.dataobjects;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationProfile;
import net.violet.platform.datamodel.ApplicationTemp;
import net.violet.platform.datamodel.ApplicationTempImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.Factories;

public class NablifeServicesData {

	/** Represents a box for nablife */

	// private final SrvListData srvListData;
	private final long id; // application_id
	private final String name;
	private final String desc;
	private final String link;
	private final long type;
	private final String srvImg;
	private final String srvlist_setup;
	private final String srvIcone;
	private final String srvShortcut;
	private final int isSubscribed;

	public NablifeServicesData(VObject inObject, ApplicationTemp inApplicationTemp) {
		if ((inApplicationTemp == null) || (inApplicationTemp.getApplication() == null)) {
			this.id = 0;
			this.name = net.violet.common.StringShop.EMPTY_STRING;
			this.desc = net.violet.common.StringShop.EMPTY_STRING;
			this.link = net.violet.common.StringShop.EMPTY_STRING;
			this.type = 0;
			this.srvImg = net.violet.common.StringShop.EMPTY_STRING;
			this.srvIcone = net.violet.common.StringShop.EMPTY_STRING;
			this.srvShortcut = net.violet.common.StringShop.EMPTY_STRING;
			this.isSubscribed = -1; // utilisé dans les jsp inc_allServices.jsp et inc_nabaztalandHome.jsp
		} else {
			final Application theApplication = inApplicationTemp.getApplication();
			this.id = inApplicationTemp.getId();
			final ApplicationProfile theApplicationProfile = theApplication.getProfile();
			if (theApplicationProfile != null) {
				this.name = theApplicationProfile.getTitle();
				this.desc = theApplicationProfile.getDescription();
			} else {
				this.name = net.violet.common.StringShop.EMPTY_STRING;
				this.desc = net.violet.common.StringShop.EMPTY_STRING;
			}
			final String configUrl = (Application.NativeApplication.RSS_FULL.getName().equals(theApplication.getName()) || Application.NativeApplication.PODCAST_FULL.getName().equals(theApplication.getName())) ? "config" : net.violet.common.StringShop.EMPTY_STRING;

			this.link = theApplication.getLink() + configUrl + "&applicationId=" + theApplication.getId();
			this.type = (theApplication.getCategory() != null) ? theApplication.getCategory().getId() : 0;
			this.srvImg = inApplicationTemp.getImage();
			this.srvIcone = inApplicationTemp.getIcone();
			this.srvShortcut = inApplicationTemp.getShortcut();

			if (inObject == null) {
				this.isSubscribed = -1;
			} else {
				final List<SubscriptionData> subscriptions = SubscriptionData.findByApplicationAndObject(theApplication, inObject);
				this.isSubscribed = (subscriptions.isEmpty()) ? 0 : 1;
			}
		}
		this.srvlist_setup = "CONFIG";
	}

	/**
	 * return all available SrvListData for the given parameters sorted by
	 * category
	 * 
	 * @param idCateg
	 * @param index
	 * @param nbr
	 * @param inObject
	 * @param langCategorie
	 * @return
	 * @throws SQLException
	 */
	public static List<NablifeServicesData> findListServicesByCateg(int idCateg, int index, int nbr, VObject inObject, Lang inLang) {
		final List<NablifeServicesData> nablifeServicesDataList = new ArrayList<NablifeServicesData>();

		final List<Application> applications = Factories.APPLICATION.findAllByLangAndCateg(inLang, Factories.APPLICATION_CATEGORY.find(idCateg));

		nablifeServicesDataList.addAll(NablifeServicesData.generateList(inObject, applications));

		/*
		 * Collections.sort(nablifeServicesDataList, new
		 * Comparator<NablifeServicesData>(){ public int
		 * compare(NablifeServicesData o1, NablifeServicesData o2) { return
		 * (int) (o2.getVote() - o1.getVote()); } });
		 */

		int lastIndex = index + nbr;

		// We make sure that we are going to return the closest amount of
		// SrvList objects to nbr
		if ((nablifeServicesDataList.size() <= lastIndex) && (index != 0)) {
			// If index is not out of bound
			if (index < nablifeServicesDataList.size()) {
				lastIndex = nablifeServicesDataList.size() - 1;
			} else {
				throw new IllegalArgumentException("Index is out of bound : " + index + " lastIndex : " + lastIndex);
			}

			return nablifeServicesDataList.subList(index, lastIndex);
		}

		return nablifeServicesDataList;
	}

	/**
	 * Generates a list of NablifeServicesData with the given SourceImpl list
	 * 
	 * @param inLang
	 * @return
	 */
	private static List<NablifeServicesData> generateList(VObject inObject, List<Application> inApplications) {
		final List<NablifeServicesData> nablifeServicesDataList = new ArrayList<NablifeServicesData>();

		for (final Application application : inApplications) {
			nablifeServicesDataList.add(new NablifeServicesData(inObject, application.getTemp()));
		}

		return nablifeServicesDataList;
	}

	/**
	 * returns the {@link NablifeServicesData} with the given shortcut matching
	 * the {@link SrvList}
	 * 
	 * @param inShortcut
	 * @return
	 */
	public static NablifeServicesData findByShortcut(VObject inObject, String inShortcut) {
		final ApplicationTemp application = ApplicationTempImpl.findByShortcut(inShortcut);
		return (application == null) ? null : new NablifeServicesData(inObject, application);
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return the attribute name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the attribute desc
	 */
	public String getDesc() {
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
		return this.type;
	}

	/**
	 * @return the attribute vote
	 */
	public long getVote() {
		return 0;
	}

	/**
	 * @return the attribute srvType
	 */
	public String getSrvType() {
		return net.violet.common.StringShop.EMPTY_STRING + this.type;
	}

	/**
	 * @return the attribute srvImg
	 */
	public String getSrvImg() {
		return this.srvImg;
	}

	/**
	 * @return the attribute srvlist_setup
	 */
	public String getSrvlist_setup() {
		return this.srvlist_setup;
	}

	/**
	 * @return the attribute isSubscribed
	 */
	public int getIsSubscribed() {
		return this.isSubscribed;
	}

	public static List<NablifeServicesData> findAllNablifeFrontPageByLang(VObject inObject, Lang lang) {

		final List<NablifeServicesData> listNablife = new ArrayList<NablifeServicesData>();

		for (final Application application : Factories.APPLICATION.findAllByLangAndRank(lang, 0, 30)) {
			listNablife.add(new NablifeServicesData(inObject, application.getTemp()));
		}

		return listNablife;
	}

	public String getSrvIcone() {
		return this.srvIcone;
	}

	public String getSrvShortCut() {
		return this.srvShortcut;
	}

}
