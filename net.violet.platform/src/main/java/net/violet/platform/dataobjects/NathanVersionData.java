package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.violet.platform.applets.interactive.NathanPlayer;
import net.violet.platform.datamodel.AppletSettings;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.NathanMp3;
import net.violet.platform.datamodel.NathanTag;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.NathanVersion.Status;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.interactif.config.NathanPlayerConfig;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.StringShop;

public class NathanVersionData extends RecordData<NathanVersion> {

	/**
	 * Cache information
	 */
	private static final CCalendar LAST_REFRESH_CACHE_POPULAR = new CCalendar(false);
	private static Map<String, List<NathanVersion>> popularCacheMap = new HashMap<String, List<NathanVersion>>();

	private static final CCalendar LAST_REFRESH_CACHE_RECENT = new CCalendar(false);
	private static Map<String, List<NathanVersion>> recentCacheMap = new HashMap<String, List<NathanVersion>>();

	private static final int CACHE_TTL = 1200000;

	public NathanVersionData(NathanVersion inVersion) {
		super(inVersion);
	}

	public static NathanVersionData find(long inId) {
		return new NathanVersionData(Factories.NATHAN_VERSION.find(inId));
	}

	/**
	 * Returns all versions created by the specified author.
	 * 
	 * @param inAuthor
	 * @param inIsbn
	 * @return a list of NathanVersionData objects, can be empty but not null.
	 */
	public static List<NathanVersionData> findAllByAuthorAndIsbn(VObjectData inAuthor, String inIsbn) {
		if ((inAuthor != null) && (inIsbn != null)) {
			return NathanVersionData.generateList(Factories.NATHAN_VERSION.findAllByAuthorAndIsbn(Factories.VOBJECT.find(inAuthor.getId()), inIsbn));
		}

		return Collections.emptyList();
	}

	/**
	 * Returns a list of all the official versions.
	 * 
	 * @param inIsbn
	 * @return a list of NathanVersionData objects, can be empty but not null.
	 */
	public static List<NathanVersionData> findAllOfficial(String inIsbn) {
		return NathanVersionData.generateList(Factories.NATHAN_VERSION.findOfficialVersions(inIsbn));
	}

	/**
	 * Returns a list of versions sorted from the most to the less popular. Only
	 * non-official, shared and authorized versions are in the list.
	 * 
	 * @param inIsbn
	 * @return a list of NathanVersionData objects, can be empty but not null.
	 */
	public static List<NathanVersionData> getPopularVersions(String inIsbn) {

		synchronized (NathanVersionData.LAST_REFRESH_CACHE_POPULAR) {
			if ((NathanVersionData.popularCacheMap.get(inIsbn) == null) || (System.currentTimeMillis() - NathanVersionData.LAST_REFRESH_CACHE_POPULAR.getTimeInMillis() > NathanVersionData.CACHE_TTL)) {
				NathanVersionData.popularCacheMap.put(inIsbn, Factories.NATHAN_VERSION.getPopularVersions(inIsbn));
				NathanVersionData.LAST_REFRESH_CACHE_POPULAR.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
			}
		}

		return NathanVersionData.generateList(NathanVersionData.popularCacheMap.get(inIsbn));
		// return generateList(NathanVersionImpl.getPopularVersions(inIsbn));
	}

	/**
	 * Returns a list of versions sorted from the most to the less recent. Only
	 * non-official, shared and authorized versions are in the list.
	 * 
	 * @param inIsbn
	 * @return a list of NathanVersionData objects, can be empty but not null.
	 */
	public static List<NathanVersionData> getRecentVersions(String inIsbn) {

		synchronized (NathanVersionData.LAST_REFRESH_CACHE_RECENT) {
			if ((NathanVersionData.recentCacheMap.get(inIsbn) == null) || (System.currentTimeMillis() - NathanVersionData.LAST_REFRESH_CACHE_RECENT.getTimeInMillis() > NathanVersionData.CACHE_TTL)) {
				NathanVersionData.recentCacheMap.put(inIsbn, Factories.NATHAN_VERSION.getRecentVersions(inIsbn));
				NathanVersionData.LAST_REFRESH_CACHE_RECENT.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
			}
		}

		return NathanVersionData.generateList(NathanVersionData.recentCacheMap.get(inIsbn));
		// return generateList(NathanVersionImpl.getRecentVersions(inIsbn));
	}

	/**
	 * Look for versions matching at least one of the provided tags.
	 * 
	 * @param tagsId a list containing the ids of requested tags
	 * @param inIsbn
	 * @return a set of NathanVersionData matching at least one of the tags, can
	 *         be empty but not null.
	 */
	public static List<NathanVersionData> lookForVersions(List<Long> tagsId, String inIsbn) {

		final Set<NathanVersionData> result = new HashSet<NathanVersionData>();

		for (final NathanVersionData version : NathanVersionData.getPopularVersions(inIsbn)) {
			for (final NathanTagData tag : version.getTags()) {
				if (tagsId.contains(tag.getId())) {
					result.add(version);
					break;
				}
			}
		}

		return new ArrayList<NathanVersionData>(result);
	}

	/**
	 * This method is used to find all the versions belonging to the provided
	 * object selection.
	 * 
	 * @param inObject book object
	 * @param inIsbn
	 * @return a list of NathanVersionData object, can be empty but not null.
	 */
	public static List<NathanVersionData> getObjectSelection(VObjectData inObject, String inIsbn) {

		final List<NathanVersionData> result = new ArrayList<NathanVersionData>();
		final VObject theObject = Factories.VOBJECT.find(inObject.getId());
		final AppletSettings appletSettings = NathanVersionData.getNathanAppletSettings(theObject, inIsbn);

		if (appletSettings != null) {
			final String theValue = appletSettings.getValue();
			if ((theValue != null) && !theValue.equals(StringShop.EMPTY_STRING)) {
				for (final String idString : appletSettings.getValue().split(";")) {
					final int versionId = Integer.parseInt(idString);
					final NathanVersion theVersion = Factories.NATHAN_VERSION.find(versionId);

					if (theVersion == null) {
						NathanVersionData.removeVersionFromSelection(versionId, appletSettings);
					} else {
						result.add(new NathanVersionData(theVersion));
					}
				}
			}
		}

		return result;
	}

	/**
	 * Creates a new NathanVersion object.
	 * 
	 * @param author author of the new version (rabbit object).
	 * @param isbn the isbn of the involved book
	 * @return a NathanVersionData refering to the new NathanVersion, or null
	 */
	public static NathanVersionData createNewVersion(VObjectData author, String isbn) {
		final NathanVersion theVersion = Factories.NATHAN_VERSION.createNewNathanVersion(author.getReference(), isbn);
		return new NathanVersionData(theVersion);
	}

	/**
	 * This method deletes the provided version. It also deletes the associated
	 * Files and NathanMp3 objects.
	 * 
	 * @param versionId
	 */
	public static void deleteVersion(long versionId) {
		final NathanVersion theVersion = Factories.NATHAN_VERSION.find(versionId);

		if (theVersion != null) {

			// deletes mp3
			for (final NathanMp3 mp3 : Factories.NATHAN_MP3.findAllMp3ByVersion(theVersion)) {
				mp3.delete();
			}

			// suppression du mp3 de preview
			final Long idFilesPreview = theVersion.getPreview();
			if (idFilesPreview != null) {
				final Files filePreview = Factories.FILES.find(idFilesPreview);
				if (filePreview != null) {
					filePreview.scheduleDeletion();
				}
			}
			// deletes tags association
			for (final NathanTag tag : Factories.NATHAN_TAG.findAllTags()) {
				theVersion.getTags().remove(tag);
			}

			if (NathanVersionData.recentCacheMap.get(theVersion.getIsbn()) != null) {
				NathanVersionData.recentCacheMap.get(theVersion.getIsbn()).remove(theVersion);
			}

			if (NathanVersionData.popularCacheMap.get(theVersion.getIsbn()) != null) {
				NathanVersionData.popularCacheMap.get(theVersion.getIsbn()).remove(theVersion);
			}

			theVersion.delete();
		}
	}

	/**
	 * Method used to convert a list containing NathanVersion objects into a
	 * list containing NathanVersionData objects.
	 * 
	 * @param inList the list of NathanVersion
	 * @return a list of NathanVersionData objects
	 */
	private static List<NathanVersionData> generateList(List<NathanVersion> inList) {
		if (inList != null) {
			final List<NathanVersionData> result = new ArrayList<NathanVersionData>();
			for (final NathanVersion version : inList) {
				result.add(new NathanVersionData(version));
			}

			return result;
		}

		return Collections.emptyList();
	}

	/**
	 * Method used to remove a version from a selection. Mainly used when you
	 * discover that a version was deleted by its author.
	 * 
	 * @param versionId the no-more existing version to remove.
	 * @param appletSettings the settings used to store the selection.
	 */
	private static void removeVersionFromSelection(long versionId, AppletSettings appletSettings) {

		final List<String> idList = new ArrayList<String>(Arrays.asList(appletSettings.getValue().split(";")));
		idList.remove(((Long) versionId).toString());

		String newString = StringShop.EMPTY_STRING;
		for (final String s : idList) {
			newString += s + ";";
		}

		appletSettings.setValue(newString);
	}

	/**
	 * This method is only used to retrieve the AppletSettings object. It's just
	 * easier to use this method in case we have to change some constantes.
	 * 
	 * @param theObject
	 * @param isbn
	 * @return the AppletSettings object
	 */
	private static AppletSettings getNathanAppletSettings(VObject theObject, String isbn) {
		final NathanPlayerConfig nathanConfig = new NathanPlayerConfig(Long.parseLong(isbn));
		return Factories.APPLET_SETTINGS.getAppletSettingsByObject(theObject, NathanPlayer.APPLET_ID, nathanConfig.getAllVersions());
	}

	public String getDescription() {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getDescription();
		}

		return StringShop.EMPTY_STRING;
	}

	public boolean getOfficial() {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getOfficial();
		}

		return false;
	}

	public boolean getShared() {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getShared();
		}

		return false;
	}

	public String getStatus() {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getStatus();
		}

		return StringShop.EMPTY_STRING;
	}

	public long getNb() {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNb();
		}

		return 0;
	}

	public String getIsbn() {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getIsbn();
		}

		return StringShop.EMPTY_STRING;
	}

	public long getId() {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}

		return 0;
	}

	public String getAuthor() {
		final NathanVersion theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getAuthor() != null)) {
			return theRecord.getAuthor().getObject_login();
		}

		return StringShop.EMPTY_STRING;
	}

	public List<NathanTagData> getTags() {
		final NathanVersion theRecord = getRecord();
		if (theRecord == null) {
			return Collections.emptyList();
		}

		return NathanTagData.generateList(theRecord.getTags());
	}

	public boolean isAuthorized() {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getStatus().equals(NathanVersion.Status.AUTHORIZED.toString());
		}

		return false;
	}

	public String getPreview() {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			final Files theFile = Factories.FILES.find(theRecord.getPreview());
			if (theFile != null) {
				return theFile.getPath();
			}

			return StringShop.EMPTY_STRING;
		}

		return StringShop.EMPTY_STRING;
	}

	public String getImg() {
		final NathanVersion theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getAuthor() != null)) {
			final User theUser = theRecord.getAuthor().getOwner();
			if (theUser.getUser_image() == 1) {
				return Long.toString(theUser.getId());
			}

		}

		// cas particulier pour les versions officiel
		if (getOfficial()) {
			final Application theService = ApplicationImpl.findByAppletId(NathanPlayer.APPLET_ID, new Long(getIsbn()));
			if (theService != null) {
				return theService.getImg().replaceAll("\\.gif", "_s.gif");
			}
		}

		return "-1";
	}

	/**
	 * Adds the current NathanVersion to the provided object's selection.
	 * 
	 * @param inObject the book object
	 * @return true if the version is correctly added, false if the version
	 *         already is in the object selection or if appletSettings can't be
	 *         found.
	 */
	public boolean addToObjectSelection(VObject inObject) {
		final NathanVersion theRecord = getRecord();

		final AppletSettings appletSettings = NathanVersionData.getNathanAppletSettings(inObject, getIsbn());

		if (appletSettings == null) {
			// TODO ce cas peut il arriver ici ?
			return false;
		}

		final List<String> idList = Arrays.asList(appletSettings.getValue().split(";"));
		if (idList.contains(((Long) this.getId()).toString())) {
			return false;
		}

		appletSettings.setValue(appletSettings.getValue() + this.getId() + ";");

		if (theRecord != null) {
			theRecord.increaseNb();
		}

		return true;
	}

	/**
	 * Removes the current NathanVersion from the specified object's selection.
	 * 
	 * @param inObject the book object
	 * @return true if the version is correctly removed, false if the
	 *         appletSettings can't be found.
	 */
	public boolean removeFromObjectSelection(VObject inObject) {
		final NathanVersion theRecord = getRecord();

		final AppletSettings appletSettings = NathanVersionData.getNathanAppletSettings(inObject, getIsbn());

		if (appletSettings == null) {
			// TODO ce cas peut il arriver ici ?
			return false;
		}

		NathanVersionData.removeVersionFromSelection(this.getId(), appletSettings);

		if (theRecord != null) {
			theRecord.decreaseNb();
		}

		return true;
	}

	public void setStatus(Status inStatus) {
		final NathanVersion theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setStatus(inStatus);
		}

	}

}
