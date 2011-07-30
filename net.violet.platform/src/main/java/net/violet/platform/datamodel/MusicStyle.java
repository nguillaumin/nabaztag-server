package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Music types
 *
 */
public interface MusicStyle extends Record<MusicStyle> {

	/**
	 * Types des musiques: Catégorie pour les mp3 Perso
	 */
	int CATEGORIE_MP3_PERSO = 29;
	/**
	 * Types des musiques: Catégorie pour les tts perso
	 */
	int CATEGORIE_TTS_PERSO = 51;
	/**
	 * Types des musiques: Catégorie pour les sons reveil
	 */
	int CATEGORIE_REVEIL = 37;
	/**
	 * Types des musiques: Catégorie bonjour dans les clin d'oeil (utilisé dans
	 * le NabThem
	 */
	@Deprecated
	int CATEGORIE_CLIN_BONJOUR = 63;

	long getMusicstyle_id();

	String getMusicstyle_name();

	long getStyle_private();

}
