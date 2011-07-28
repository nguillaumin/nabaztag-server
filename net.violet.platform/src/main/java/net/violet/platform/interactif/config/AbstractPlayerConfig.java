package net.violet.platform.interactif.config;

import java.util.List;

import net.violet.platform.util.StringShop;

public abstract class AbstractPlayerConfig implements PlayerConfig {

	protected static final String PATH_SONG_FR = "broadcast/broad/config/player/fr/";
	protected static final String PATH_SONG_EN = "broadcast/broad/config/player/en/";

	private final String INTERRUPTION_MP3 = "Interruption.mp3";
	private final String INIT_INTRO_MP3 = "Init_Intro.mp3";
	private final String END_ONE_VOICE_MP3 = "EndOneVoice.mp3";
	private final String SHORT_INTRO_MP3 = "Short_Intro.mp3";
	private final String LONG_INTRO_MP3 = "Long_Intro.mp3";
	protected static final String SHORT_INTRO_NATHAN_MP3 = "Short_Intro_Nathan.mp3";
	protected static final String LONG_INTRO_NATHAN_MP3 = "Long_Intro_Nathan.mp3";

	private final String MARKUP = "_markIndex";
	private final String VOICE = "voice";
	private final String COUNT_VOICE = "countVoice_";

	private final long isbn;
	private final String music_bye;
	private final String music_help_init;
	protected String music_help_long;
	protected String music_help_short;
	private final String music_end_book;

	protected final String COUNT_FINISH = "countFinish";

	public AbstractPlayerConfig(long inIsbn, String inPath) {
		this.music_bye = inPath + this.INTERRUPTION_MP3;
		this.music_help_init = inPath + this.INIT_INTRO_MP3;
		this.music_help_long = inPath + this.LONG_INTRO_MP3;
		this.music_help_short = inPath + this.SHORT_INTRO_MP3;
		this.music_end_book = inPath + this.END_ONE_VOICE_MP3;
		this.isbn = inIsbn;
	}

	/**
	 * renvoi la bonne table d'index selon la voix
	 * 
	 * @param voiceId : 0 => voice 1 ; 1 => voice 2
	 * @return INDEXFILES
	 */
	public int[] getIndexFilesByVoice(int voiceId) {
		return getIndexFiles().get(voiceId);
	}

	public String getMusicHelpInit() {
		return this.music_help_init;
	}

	public String getMusicBye() {
		return this.music_bye;
	}

	public String getMusicEndBook() {
		return this.music_end_book;
	}

	public String getMusicHelpLong() {
		return this.music_help_long;
	}

	public String getMusicHelpShort() {
		return this.music_help_short;
	}

	/**
	 * renvoi le bon fichier à lire
	 * 
	 * @param voiceId : 0 => voice 1 ; 1 => voice 2
	 * @return PATH_STREAM_GALLIMARD
	 */
	public String getPathStreamByVoice(int voiceId) {
		return getPathStream().get(voiceId);
	}

	public int getVoiceSize() {
		return getPathStream().size();
	}

	public String getName() {
		return String.valueOf(getIsbn());
	}

	public String getMarkup() {
		return getIsbn() + this.MARKUP;
	}

	public String getVoice() {
		return getIsbn() + StringShop.UNDERSCORE + this.VOICE;
	}

	public String getCountFinish() {
		return getIsbn() + StringShop.UNDERSCORE + this.COUNT_FINISH;
	}

	public String getVoice(int inVoiceId) {
		return getIsbn() + StringShop.UNDERSCORE + this.COUNT_VOICE + inVoiceId;
	}

	public int getCountVoice() {
		return 1;
	}

	abstract List<String> getPathStream();

	abstract List<int[]> getIndexFiles();

	public long getIsbn() {
		return this.isbn;
	}

}
