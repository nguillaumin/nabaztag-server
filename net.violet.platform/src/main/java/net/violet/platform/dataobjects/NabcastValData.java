package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Nabcast;
import net.violet.platform.datamodel.NabcastVal;
import net.violet.platform.datamodel.NabcastValImpl;
import net.violet.platform.datamodel.User;
import net.violet.platform.util.CCalendar;

public class NabcastValData extends RecordData<NabcastVal> {

	private final String relativeTime;

	/**
	 * Construct a new NabcastData object from the user
	 * 
	 * @param inNabcast
	 */
	public NabcastValData(NabcastVal inNabcastVal, User inUser) {
		super(inNabcastVal);
		if ((inUser != null) && (inNabcastVal != null)) {
			final TimeZone tz = TimeZone.getTimeZone(inUser.getTimezone().getTimezone_javaId());
			final CCalendar myCalendar = new CCalendar((long) (inNabcastVal.getNabcastval_time()) * 1000);
			this.relativeTime = myCalendar.getTimeFormatedRelativeToNow(tz, inUser.use24(), inUser.getAnnu().getLangPreferences());
		} else {
			this.relativeTime = net.violet.common.StringShop.EMPTY_STRING;
		}
	}

	/**
	 * Finds the song of a nabcast
	 * 
	 * @param id
	 * @param nbrnabcast
	 * @param index
	 * @param afterBefore
	 * @param desc
	 * @return
	 */
	public static List<NabcastValData> findSongsNabcast(Nabcast inNabcast, int nbrnabcast, int index, int afterBefore, int desc, User inUser) {
		return NabcastValData.generateList(NabcastValImpl.getSongNabcast(inNabcast.getId(), nbrnabcast, index, afterBefore, desc), inUser);
	}

	/**
	 * Generates a list of NabcastData with the given NabcastValImpl list
	 * 
	 * @param inNabcastVals MusicImpl list
	 * @param inUser the user
	 * @return
	 */
	private static List<NabcastValData> generateList(List<NabcastVal> inNabcastVals, User inUser) {
		final List<NabcastValData> nabcastValDataList = new ArrayList<NabcastValData>();

		for (final NabcastVal tempNabcastVal : inNabcastVals) {
			nabcastValDataList.add(new NabcastValData(tempNabcastVal, inUser));
		}

		return nabcastValDataList;
	}

	/**
	 * @return the attribute nabcastval_titre
	 */
	public String getNabcastval_titre() {
		final NabcastVal theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getNabcastval_title() != null)) {
			return theRecord.getNabcastval_title();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the attribute nabcastval_id
	 */
	public long getNabcastval_id() {
		final NabcastVal theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}
		return 0;

	}

	/**
	 * @return the attribute nabcastval_idmusic
	 */
	public long getNabcastval_idmusic() {
		final NabcastVal theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNabcastval_idmusic();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcastval_nabcast
	 */
	public NabcastData getNabcastval_nabcast() {
		final NabcastVal theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getNabcast() != null)) {
			return new NabcastData(theRecord.getNabcast(), null);
		}
		return new NabcastData();
	}

	/**
	 * @return the attribute nabcastval_time
	 */
	public CCalendar getNabcastDeliveryDate() {
		final NabcastVal theRecord = getRecord();
		CCalendar theResult;
		if ((theRecord != null) && (theRecord.getNabcastval_time() != null)) {
			final long theDeliveryDateInSecs = theRecord.getNabcastval_time().intValue();
			theResult = new CCalendar(theDeliveryDateInSecs * 1000);
		} else {
			theResult = null;
		}
		return theResult;
	}

	/**
	 * @return the attribute nabcastval_date
	 */
	public long getNabcastval_date() {
		final NabcastVal theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNabcastval_date();
		}
		return 0;
	}

	/**
	 * @return the attribute nabcastval_url
	 */
	public String getNabcastval_url() {
		final String theResult;
		final Files theFile = getNabcastFile();
		if (theFile != null) {
			theResult = theFile.getPath();
		} else {
			theResult = net.violet.common.StringShop.EMPTY_STRING;
		}
		return theResult;
	}

	public Files getNabcastFile() {
		final NabcastVal theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getMusic() != null) && (theRecord.getMusic().getFile() != null) && (theRecord.getMusic().getFile().getPath() != null)) {
			return theRecord.getMusic().getFile();
		}
		return null;
	}

	public String getNabcastVal_timerelative() {
		final NabcastVal theRecord = getRecord();
		if ((this.relativeTime != null) && (theRecord != null)) {
			return this.relativeTime;
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getNabcastval_timerelative() {
		return getNabcastVal_timerelative();
	}

}
