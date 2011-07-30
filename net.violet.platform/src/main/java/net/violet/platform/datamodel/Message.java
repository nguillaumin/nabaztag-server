package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.TimeZone;

import net.violet.db.records.Record;
import net.violet.platform.dataobjects.MessageData.Palette;
import net.violet.platform.util.CCalendar;

/**
 * Message sent/received by users
 * 
 *
 */
public interface Message extends Record<Message> {

	/**
	 * Returns the body associated this message, <code>null</code> if the
	 * message does not have a body.
	 * 
	 * @return the body of the message
	 */
	Files getBody();

	boolean isOrphan();

	/**
	 * @return the color
	 */
	Long getColor();

	void setSignatureInfo(Long color, Music music, Anim anim);

	/**
	 * @return the palette
	 */
	Palette getPalette();

	/**
	 * @return the count
	 */
	long getCount();

	void setCount(long inCount);

	/**
	 * @return the text
	 */
	String getText();

	/**
	 * @return the timeOfDelivery
	 */
	CCalendar getTimeOfDelivery();

	CCalendar getTimeOfDelivery(TimeZone inTimeZone);

	// Special test
	void setAllInformation(String text, int eventId, Files file, int count, long color, long palette, CCalendar deliveryTime);

	/**
	 * @return the event_id
	 */
	Integer getEvent_id();

	/**
	 * @param event_id the event_id to set
	 */
	void setEventId(Integer event_id);

	/**
	 * Deletes the event associated with this message
	 * 
	 * @throws SQLException
	 */
	void deleteEvent() throws SQLException;

	/**
	 * Deletes the scheduledMessage associated with this message
	 * 
	 * @throws SQLException
	 */
	void deleteScheduledMessage() throws SQLException;

	/**
	 * @return the Anim
	 */
	Anim getAnim();

	/**
	 * @return the music
	 */
	Music getMusic();

	String getXMPPID();

	/**
	 * Retourne l'id du nabcast, 0 si le nabcast n'existe plus, null si ce
	 * message ne provient pas d'un nabcast.
	 * 
	 * @return <code>null</code>, 0 ou l'id du nabcast.
	 */
	Long getNabcast();

	void setTheNabcast(Long nabcast);

}
