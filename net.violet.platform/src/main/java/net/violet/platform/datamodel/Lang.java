package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Languages for the website, TTS, voice recognition, objects
 * 
 *
 */
public interface Lang extends Record<Lang> {

	/**
	 * ID de l'américain.
	 */
	Long LANG_US_ID = 2L;

	long DEFAULT_HELP_LANG_ID = 3; // anglais
	@Deprecated
	long LANG_TYPE_DISABLED = -1;

	long LANG_MOTHER_TONGUE = 0;
	long LANG_TTS = 1;
	long LANG_TYPE_SITE = 1;
	long LANG_TYPE_TTS = 2;
	long LANG_TYPE_ASR = 4;
	long LANG_TYPE_VOICE_OBJECT = 8;

	/**
	 * Accesseur sur le titre de la langue.
	 */
	String getTitle();

	/**
	 * Accesseur sur le code iso de la langue.
	 */
	String getIsoCode();

	/**
	 * Accesseur sur le code IETF court de la langue (2 caractères).
	 */
	String getIETFCode();

	/**
	 * Accesseur sur le type de la langue.
	 */
	@Deprecated
	long getType();

	/**
	 * 1 => langue proposé par le site (affichage, application) 2 => langue
	 * proposé dans le tts 4 => langue proposé dans la reco 8 => langue proposé
	 * pour l'objet
	 */
	int getNewType();

	/**
	 * Accesseur sur la langue de l'aide.
	 */
	long getHelpLangId();
}
