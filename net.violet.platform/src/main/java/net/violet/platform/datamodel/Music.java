package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Sounds from Violet and from the users.
 * 
 *
 */
public interface Music extends Record<Music> {

	/**
	 * Types des musiques: mp3 perso (et tts enregistrés).
	 */
	int TYPE_MP3_USER_LIBRARY = 0;
	/**
	 * Types des musiques: clins d'oeil.
	 */
	int TYPE_MP3_LITTLE_WORDS = 1;
	/**
	 * Types des musiques: midi en bibliothèque
	 */
	int TYPE_MP3_LIBRARY = 2;
	/**
	 * Types des musiques: mp3 opération noël
	 */
	int TYPE_MP3_CHRISTMAS = 3;
	/**
	 * Types des items: images des users.
	 */
	int TYPE_IMAGE_USER_LIBRARY = 5;
	/**
	 * Types des musiques: Signature par défaut proposé par le site
	 */
	int TYPE_MP3_SIGNATURE = 7;

	String getMusic_name();

	void setMusicInfo(String name, int share, Lang lang);

	void setMusicProfile(String name, int share);

	/**
	 * Accesseur sur le nom, raccourci.
	 * 
	 * @return le nom, tronqué et avec ... à la fin s'il est trop long.
	 */
	String getMusicShortName();

	/**
	 * Returns the FilesImpl associated with this MusicImpl
	 * 
	 * @return
	 */
	Files getFile();

	MusicStyle getMusicStyle();

	int getMusic_share();

	int getMusic_type();

	long getMusic_lang();

	void setType(int type);

	User getOwner();

}
