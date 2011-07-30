package net.violet.platform.datamodel;

import java.util.List;

import net.violet.db.records.Record;

/**
 * Nabcasts created by users. Deprecated, was for the previous version.
 * 
 *
 */
public interface Nabcast extends Record<Nabcast> {

	long getNabcast_anim_sign();

	void setNabcastInfo(long animSign, NabcastCateg categ, String colorSign, String desc, long lang, long musicSign, String shortcut, String title);

	NabcastCateg getNabcastCateg();

	String getNabcast_color_sign();

	String getNabcast_desc();

	long getNabcast_lang();

	long getNabcast_music_sign();

	int getNabcast_postupdate();

	void setPostUpdate(int postUpdate);

	int getNabcast_private();

	void setPrivate(int isPrivate);

	void setUpdate(int inUpdate);

	void setMailNotified(int inNotified);

	String getNabcast_shortcut();

	String getNabcast_title();

	long getNabcast_id();

	int getNabcast_mailnotified();

	int getNabcast_update();

	User getAuthor();

	List<Subscriber> getSubscribers();

}
