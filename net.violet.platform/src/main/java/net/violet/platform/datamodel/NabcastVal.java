package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Nabcast posted by users.
 * 
 *
 */
public interface NabcastVal extends Record<NabcastVal> {

	String[] NEW_COLUMNS = new String[] { "nabcastval_title", "nabcastval_idmusic", "nabcastval_nabcast", "nabcastval_date", "nabcastval_time", };

	long getNabcastval_id();

	String getNabcastval_title();

	long getNabcastval_idmusic();

	Nabcast getNabcast();

	Integer getNabcastval_time();

	long getNabcastval_date();

	Music getMusic();

	void setMusic(MusicImpl inMusic);

}
