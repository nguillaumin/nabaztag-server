package net.violet.platform.interactif.config;

/**
 * Interface de configuration pour l'application lecture d'un audio book.
 */
public interface PlayerConfig {

	/**
	 * renvoi la bonne table d'index selon la voix
	 * 
	 * @param voiceId : 0 => voice 1 ; 1 => voice 2
	 * @return INDEXFILES
	 */
	int[] getIndexFilesByVoice(int voiceId);

	/**
	 * renvoi le bon fichier à lire
	 * 
	 * @param voiceId : 0 => voice 1 ; 1 => voice 2
	 * @return PATH_STREAM_GALLIMARD
	 */
	String getPathStreamByVoice(int voiceId);

	String getMusicHelpInit();

	String getMusicHelpLong();

	String getMusicHelpShort();

	String getMusicBye();

	String getMusicEndBook();

	int getVoiceSize();

	String getName();

	String getMarkup();

	String getVoice();

	String getCountFinish();

	String getVoice(int inVoiceId);

	int getCountVoice();

	long getIsbn();

}
