package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.NabcastImpl;
import net.violet.platform.datamodel.NabcastValImpl;
import net.violet.platform.datamodel.Subscriber;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.VObject;

public class NabcastData extends RecordData<Nabcast> {

	private final Subscriber subscriberRef;
	private final int subscribed;

	/**
	 * Construct a new NabcastData object
	 * 
	 * @param inNabcast
	 */
	public NabcastData() {
		this(null, null, null);
	}

	/**
	 * Construct a new NabcastData object from the NabcastImpl
	 * 
	 * @param inNabcast
	 */
	public NabcastData(Nabcast inNabcast, VObject inObject) {
		this(inNabcast, null, inObject);
	}

	/**
	 * Construct a new NabcastData object from the NabcastImpl and the suscriber
	 * 
	 * @param inNabcast
	 */
	private NabcastData(Nabcast inNabcast, Subscriber inSubscriber, VObject inObject) {
		super(inNabcast);

		if (inSubscriber != null) {
			this.subscriberRef = inSubscriber;
		} else {
			this.subscriberRef = null;
		}

		if (inObject != null) {
			final Map<Nabcast, Subscriber> subscribes = inObject.getSubscribedNabcast();

			if ((subscribes != null) && subscribes.containsKey(inNabcast)) {
				this.subscribed = 1;
			} else {
				this.subscribed = 0;
			}

		} else {
			this.subscribed = -1;
		}

	}

	/**
	 * Finds a NabcastImpl from its id
	 * 
	 * @param inId
	 * @return a NabcastData
	 */
	@Deprecated
	public static NabcastData find(long inId) {
		return new NabcastData(NabcastImpl.find(inId), null);
	}

	/**
	 * Finds all the nabcasts created by the given user
	 * 
	 * @param user
	 * @return
	 */
	@Deprecated
	public static List<NabcastData> findAllCreatedByUser(User inUser) {
		if (inUser != null) {
			return NabcastData.generateList(inUser.getCreatedNabcasts(), null);
		}
		return Collections.emptyList();
	}

	/**
	 * Finds all the nabcasts subscribed by the given object
	 * 
	 * @param user
	 * @return
	 */
	@Deprecated
	public static List<NabcastData> findAllSubscribedByObject(VObject inObject) {
		if (inObject != null) {
			return NabcastData.generateList(inObject.getSubscribedNabcast(), inObject);
		}
		return Collections.emptyList();
	}

	/**
	 * Finds all the nabcasts corresponding to the given shortcut
	 * 
	 * @param inShortCut
	 * @return a list of {@link NabcastData}
	 */
	@Deprecated
	public static List<NabcastData> findAllByShortcut(String inShortCut) {
		return NabcastData.generateList(NabcastImpl.findAllByShortcut(inShortCut), null);
	}

	/**
	 * Finds all the nabcasts belonging to the given category
	 * 
	 * @param inShortCut
	 * @return a list of {@link NabcastData}
	 */
	@Deprecated
	public static List<NabcastData> findAllByCateg(long inCategId, VObject inObject, int index, int nbr) {
		final List<NabcastData> nabcastList = new ArrayList<NabcastData>();

		nabcastList.addAll(NabcastData.generateList(NabcastImpl.getListNabcastByCateg(inCategId, index, nbr), inObject));

		return nabcastList;

	}

	/**
	 * Generates a list of NabcastData with the given NabcastImpl list
	 * 
	 * @param inNabcasts MusicImpl list
	 * @param inUser the user
	 * @return
	 */
	private static List<NabcastData> generateList(List<Nabcast> inNabcasts, VObject inObject) {
		final List<NabcastData> nabcastDataList = new ArrayList<NabcastData>();

		for (final Nabcast tempNabcast : inNabcasts) {
			nabcastDataList.add(new NabcastData(tempNabcast, inObject));
		}

		return nabcastDataList;
	}

	/**
	 * Generates a list of NabcastData with the given NabcastImpl Map
	 * 
	 * @param inNabcasts MusicImpl list
	 * @param inUser the user
	 * @return
	 */
	private static List<NabcastData> generateList(Map<Nabcast, Subscriber> inNabcasts, VObject inObject) {
		final List<NabcastData> nabcastDataList = new ArrayList<NabcastData>();

		for (final Nabcast tempNabcast : inNabcasts.keySet()) {
			nabcastDataList.add(new NabcastData(tempNabcast, inNabcasts.get(tempNabcast), inObject));
		}

		return nabcastDataList;
	}

	/**
	 * @return the attribute id
	 */
	public long getId() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_id
	 */
	public long getNabcast_id() {
		return getId();
	}

	/**
	 * @return the attribute nabcast_title
	 */
	public String getNabcast_title() {
		final Nabcast theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getNabcast_title() != null)) {
			return theRecord.getNabcast_title();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nabcast_title
	 */
	public String getNabcast_titre() {
		return getNabcast_title();
	}

	/**
	 * @return the attribute nabcast_categ
	 */
	public String getNabcast_categ() {
		final Nabcast theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getNabcastCateg() != null)) {
			return new NabcastCategData(theRecord.getNabcastCateg()).getNameCategorie();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getNabcast_categ_name() {
		return getNabcast_categ();
	}

	/**
	 * @return the attribute nabcast_desc(ription)
	 */
	public String getNabcast_description() {
		final Nabcast theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getNabcast_desc() != null)) {
			return theRecord.getNabcast_desc();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nabcast_pricate
	 */
	public int getNabcast_private() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNabcast_private();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_mailNotified
	 */
	public int getNabcast_mailNotified() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNabcast_mailnotified();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_mailNotified
	 */
	public int getNabcast_mailnotified() {
		return getNabcast_mailNotified();
	}

	/**
	 * @return the attribute nabcast_update
	 */
	public int getNabcast_update() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNabcast_update();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_color_sign
	 */
	public String getNabcast_color_sign() {
		final Nabcast theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getNabcast_color_sign() != null)) {
			return theRecord.getNabcast_color_sign();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nabcast_anim_sign
	 */
	public long getNabcast_anim_sign() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNabcast_anim_sign();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_music_sign
	 */
	public long getNabcast_music_sign() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNabcast_music_sign();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_nbr_abonne
	 */
	public int getNabcast_nbr_abonne() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getSubscribers().size();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_author
	 */
	public long getNabcast_author() {
		final Nabcast theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getAuthor() != null)) {
			return theRecord.getAuthor().getId();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_author_peusdo
	 */
	public String getNabcast_author_pseudo() {
		final Nabcast theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getAuthor() != null) && (theRecord.getAuthor().getAnnu().getAnnu_prenom() != null)) {
			return theRecord.getAuthor().getAnnu().getAnnu_prenom();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nabcast_hours
	 */
	public int getNabcast_hours() {
		if ((this.subscriberRef != null) && (this.subscriberRef != null)) {
			return this.subscriberRef.getSubscriber_heure();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_minutes
	 */
	public int getNabcast_minutes() {
		if ((this.subscriberRef != null) && (this.subscriberRef != null)) {
			return this.subscriberRef.getSubscriber_min();
		}
		return 0;
	}

	/**
	 * @return tells whether the user is subscribed to the current nabcastRef
	 */
	public int getIsSubscribed() {
		return this.subscribed;
	}

	/**
	 * @return the attribut nabcast_image
	 */
	public long getNabcast_image() {
		final Nabcast theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getAuthor() != null)) {
			return theRecord.getAuthor().getUser_image();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcast_last_music
	 */
	public long getNabcast_last_music() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return new NabcastValData(NabcastValImpl.findLastByNabcast(theRecord), null).getNabcastval_idmusic();
		}
		return 0;
	}

	public String getNabcast_shortcut() {
		final Nabcast theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getNabcast_shortcut() != null)) {
			return theRecord.getNabcast_shortcut();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nabcast_lang
	 */
	public long getNabcast_lang() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNabcast_lang();
		}
		return 0;
	}

	public String getLink() {
		final Nabcast theRecord = getRecord();
		if (theRecord != null) {
			return "myNabaztalandSubscribe.do?idNabcast=" + getId();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	@Override
	public String toString() {
		final Nabcast theRecord = getRecord();
		String str = net.violet.common.StringShop.EMPTY_STRING;
		if (theRecord != null) {
			str += theRecord.toString();
		}
		if (this.subscriberRef != null) {
			str += this.subscriberRef.toString();
		}
		return str;
	}

	public Nabcast getReference() {
		return this.getRecord();
	}

}
