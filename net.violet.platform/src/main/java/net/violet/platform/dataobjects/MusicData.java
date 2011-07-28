package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.violet.common.StringShop;
import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.datamodel.Categ;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.Timezone;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.ConfigFiles.SERVICES;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.util.CCalendar;
import net.violet.platform.util.DicoTools;

import org.apache.log4j.Logger;

public class MusicData extends APIData<Music> {

	private static final Logger LOGGER = Logger.getLogger(MusicData.class);

	private final String suffixeMatchMidi = "\\.mid";
	private final String suffixeReplaceMatchMidi = ".mp3";

	private final String music_name;

	public static final List<MusicData> SIGNATURES;

	static {
		SIGNATURES = Collections.unmodifiableList(MusicData.generateList(Factories.MUSIC.findAllSignatures()));
	}

	@Deprecated
	public static enum MimeTypes {
		audio("audio"),
		images("images"),
		all("all");

		private String mimeType;
		private String selector;

		private MimeTypes(String inMimeType) {
			if (inMimeType.equals("audio")) {
				this.mimeType = Music.TYPE_MP3_CHRISTMAS + net.violet.common.StringShop.COMMA + Music.TYPE_MP3_LIBRARY + net.violet.common.StringShop.COMMA + Music.TYPE_MP3_USER_LIBRARY + net.violet.common.StringShop.COMMA + Music.TYPE_MP3_LITTLE_WORDS;
			} else if (inMimeType.equals("images")) {
				this.mimeType = String.valueOf(Music.TYPE_IMAGE_USER_LIBRARY);
			} else {
				this.mimeType = Music.TYPE_MP3_CHRISTMAS + net.violet.common.StringShop.COMMA + Music.TYPE_MP3_LIBRARY + net.violet.common.StringShop.COMMA + Music.TYPE_MP3_USER_LIBRARY + net.violet.common.StringShop.COMMA + Music.TYPE_MP3_LITTLE_WORDS + net.violet.common.StringShop.COMMA + Music.TYPE_IMAGE_USER_LIBRARY;
			}
			this.selector = inMimeType;
		}

		public String getMimeType() {
			return this.mimeType;
		}

		public String getName() {
			return this.selector;
		}

		public static boolean existMimeType(String inName) {
			for (final MimeTypes mimeType : MimeTypes.values()) {
				if (mimeType.getMimeType().equalsIgnoreCase(inName)) {
					return true;
				}
			}
			return false;
		}

		public static Integer getMusicTypeFromFileMimeType(net.violet.platform.datamodel.MimeType.MIME_TYPES inMime) throws BadMimeTypeException {
			if (inMime.equals(net.violet.platform.datamodel.MimeType.MIME_TYPES.A_MPEG)) {
				return Music.TYPE_MP3_USER_LIBRARY;
			}
			if (inMime.equals(net.violet.platform.datamodel.MimeType.MIME_TYPES.JPEG)) {
				return Music.TYPE_IMAGE_USER_LIBRARY;
			}
			throw new BadMimeTypeException(inMime.getLabel());
		}

		public static MimeTypes getMimeTypeByName(String inName) {
			for (final MimeTypes inMimeType : MimeTypes.values()) {
				if (inMimeType.getName().equalsIgnoreCase(inName)) {
					return inMimeType;
				}
			}
			return null;
		}
	}

	/**
	 * TODO chdes : A corriger : ce ne sont pas du tout des mime type qu'on
	 * retourne là !
	 * 
	 * @return
	 */
	public String getMusicMimeType() {
		if (getRecord() != null) {
			final long musicType = getRecord().getMusic_type();

			if ((musicType <= Music.TYPE_MP3_CHRISTMAS) || (musicType == Music.TYPE_MP3_SIGNATURE)) {
				return "audio";
			}
			if (musicType == Music.TYPE_IMAGE_USER_LIBRARY) {
				return "images";
			}
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.MUSIC;
	}

	public enum StatusMusic {
		LITTLE_WORDS("little_words", Music.TYPE_MP3_LITTLE_WORDS),
		LIBRARY("library", Music.TYPE_MP3_LIBRARY),
		NABSHARE("nabshare", Music.TYPE_MP3_USER_LIBRARY);

		private String theStatus;
		private int theType;

		private StatusMusic(String inStatus, int inType) {
			this.theStatus = inStatus;
			this.theType = inType;
		}

		public String getStatus() {
			return this.theStatus;
		}

		public int getType() {
			return this.theType;
		}

		public static StatusMusic getStatusByName(String inName) {
			for (final StatusMusic inStatus : StatusMusic.values()) {
				if (inStatus.getStatus().equalsIgnoreCase(inName)) {
					return inStatus;
				}
			}
			return null;
		}

		public static String getStatusNames() {
			final StringBuilder sbStatus = new StringBuilder(32);
			for (final StatusMusic status : StatusMusic.values()) {
				sbStatus.append(status).append(StringShop.COMMA);
			}
			// get rid of the last separator
			sbStatus.setLength(sbStatus.length() - 1);
			return sbStatus.toString();
		}
	}

	public static MusicData getData(Music inMusic) {
		try {
			return RecordData.getData(inMusic, MusicData.class, Music.class);
		} catch (final InstantiationException e) {
			MusicData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			MusicData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			MusicData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			MusicData.LOGGER.fatal(e, e);
		}

		return null;
	}

	/**
	 * Construct a new MusicData object
	 * 
	 * @param inMusic
	 * @param inLang
	 * @param inUser
	 */
	private MusicData(Music inMusic, Lang inLang) {
		super(inMusic);

		if ((inMusic != null) && (inLang != null) && (inMusic.getMusic_name() != null)) {
			final String label = DicoTools.dico_if(inLang, inMusic.getMusic_name());
			this.music_name = label;
		} else {
			this.music_name = null;
		}
	}

	protected MusicData(Music inMusic) {
		super(inMusic);
		this.music_name = null;
	}

	/**
	 * Finds all the musicRef available to a user
	 * 
	 * @param user
	 * @return
	 */
	@Deprecated
	public static List<MusicData> findAllByUser(User user) {
		if (user != null) {
			return MusicData.generateList(Factories.MUSIC.findAllForSignature(user), user);
		}

		return Collections.emptyList();
	}

	/**
	 * Finds all the personnal musicRef available to a user
	 * 
	 * @param user
	 * @return
	 */
	@Deprecated
	public static List<MusicData> findAllPersoByUser(User user) {
		if (user != null) {
			return MusicData.generateList(Factories.MUSIC.findAllPersoByUser(user), user);
		}

		return Collections.emptyList();
	}

	/**
	 * Finds all the Nabshares available for the given user
	 * 
	 * @param User l'utilisateur
	 * @return
	 */
	@Deprecated
	public static List<MusicData> findAllNabshareByOwner(User inUser) {
		return MusicData.generateList(Factories.MUSIC.findAllNabshareByOwner(inUser), inUser.getAnnu().getLangPreferences());
	}

	/**
	 * Finds all the musicRef available for the given biblio
	 * 
	 * @return
	 */
	@Deprecated
	public static List<MusicData> findAllForBiblio(int inMusicStyle_id) {
		return MusicData.generateList(Factories.MUSIC.findAllForBiblio(inMusicStyle_id));
	}

	/**
	 * Finds all the musicRef available in the given musicRef style
	 * 
	 * @param inLang
	 * @return
	 */
	@Deprecated
	public static List<MusicData> findByStyle(int inMusicStyle_id) {
		return MusicData.generateList(Factories.MUSIC.findByStyle(inMusicStyle_id));
	}

	/**
	 * Finds all the musicRef available to the Alarm clock service
	 * 
	 * @param inLang
	 * @return
	 */
	@Deprecated
	public static List<MusicData> findAllForAlarmClock(Lang inLang) {
		if (inLang != null) {
			return MusicData.generateList(Factories.MUSIC.findAllForAlarmClock(inLang), inLang);
		}

		return Collections.emptyList();
	}

	/**
	 * Finds all the musicRef available to a given category id in the given lang
	 * 
	 * @param inLang
	 * @return
	 */
	@Deprecated
	public static List<MusicData> findByCategAndLang(long inCategId, Lang inLang) {
		if (inLang != null) {
			return MusicData.generateList(Factories.MUSIC.findByNabshareCategAndLang(inCategId, inLang));
		}

		return Collections.emptyList();
	}

	/**
	 * Returns a list of musicRef registered to the given tag available in the
	 * given lang
	 * 
	 * @param inTag
	 * @param inLang
	 * @return
	 */
	@Deprecated
	public static List<MusicData> findByTagAndLang(String inTag, Lang inLang) {
		if (inLang != null) {
			return MusicData.generateList(Factories.MUSIC.findByTagAndLang(inTag, inLang));
		}

		return Collections.emptyList();
	}

	public static MusicData getDefaultMailAlert(Lang inLang) {
		final Map<String, List<ConfigFiles>> dialogConfigFiles = Factories.CONFIG_FILES.findAllByServiceAndLang(SERVICES.MAIL, inLang);
		final Files body = dialogConfigFiles.get("new").get(0).getFiles();
		return new MusicData(Factories.MUSIC.findByFile(body), inLang);
	}

	/**
	 * Returns a random MusicData for the given LangImpl for the NabShare
	 * 
	 * @param inLang
	 * @return
	 */
	@Deprecated
	public static MusicData findNabshareRand(Lang inLang) {
		if (inLang != null) {
			return new MusicData(Factories.MUSIC.findNabshareRand(inLang), inLang);
		}
		return MusicData.getData(null);
	}

	/**
	 * Returns a List of MusicData for the given LangImpl
	 * 
	 * @param inLang
	 * @return
	 */
	@Deprecated
	public static List<MusicData> findAllClin(Lang inLang, long inCategId) {
		if (inLang != null) {
			return MusicData.generateList(Factories.MUSIC.findAllClin(inLang, inCategId), inLang);
		}
		return Collections.emptyList();
	}

	/**
	 * Generates a list of MusicData with the given MusicImpl list & UserImpl
	 * 
	 * @param inMusics MusicImpl list
	 * @param inUser the user
	 * @return
	 */
	private static List<MusicData> generateList(List<Music> inMusics, User inUser) {
		return MusicData.generateList(inMusics, inUser.getAnnu().getLangPreferences());
	}

	private static List<MusicData> generateList(List<Music> inMusics) {
		final List<MusicData> musicDataList = new LinkedList<MusicData>();

		for (final Music tempMusic : inMusics) {
			musicDataList.add(MusicData.getData(tempMusic));
		}

		return musicDataList;
	}

	/**
	 * Generates a list of MusicData with the given MusicImpl list & LangImpl
	 * 
	 * @param inMusics MusicImpl list
	 * @param inLang
	 * @return
	 */
	private static List<MusicData> generateList(List<Music> inMusics, Lang inLang) {
		final List<MusicData> musicDataList = new LinkedList<MusicData>();

		for (final Music tempMusic : inMusics) {
			musicDataList.add(new MusicData(tempMusic, inLang));
		}

		return musicDataList;
	}

	/**
	 * @return the music_name
	 */
	public String getMusic_name() {
		if (this.music_name != null) {
			return this.music_name;
		}

		final Music theRecord = getRecord();

		if ((theRecord != null) && (theRecord.getMusic_name() != null)) {
			return theRecord.getMusic_name();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return getMusic_name();
	}

	/**
	 * @return the music_name
	 */
	public String getMusicName() {
		return getMusic_name();
	}

	/**
	 * @return the attribute music_owner
	 */
	public long getMusic_owner() {
		final Music theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getOwner() != null)) {
			return theRecord.getOwner().getId().longValue();
		}
		return 0;
	}

	/**
	 * @return the music_id
	 */
	public long getMusic_id() {
		final Music theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}

		return 0;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return getMusic_id();
	}

	/**
	 * @return the id
	 */
	public long getMusicId() {
		return getMusic_id();
	}

	/**
	 * @return the music_name_short
	 */
	public String getMusic_name_short() {
		if ((getMusic_name() != null) && (getMusic_name().length() > 40)) {
			return getMusic_name().substring(0, 40) + "...";
		}

		return getMusic_name();
	}

	/**
	 * @return FilesData
	 */
	public FilesData getFile() {
		Files theFiles = null;
		final Music theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getFile() != null)) {
			theFiles = theRecord.getFile();
		}

		return FilesData.getData(theFiles);
	}

	/**
	 * @return music_share
	 */
	public long getMusic_share() {
		final Music theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getMusic_share();
		}

		return 0;
	}

	public int getMusic_type() {
		final Music theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getMusic_type();
		}

		return 0;
	}

	/**
	 * @return music_url
	 */
	public String getMusic_url() {
		final Music theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getFile() != null) && (theRecord.getFile().getPath() != null)) {
			return theRecord.getFile().getPath();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	/**
	 * @return music_url
	 */
	public String getMusicUrl() {
		return getMusic_url();
	}

	/**
	 * @return music_styleid
	 */
	public long getMusic_styleid() {
		final Music theRecord = getRecord();
		if ((theRecord != null) && (theRecord.getMusicStyle() != null)) {
			return theRecord.getMusicStyle().getId();
		}

		return 0;
	}

	public UserData getOwner() {
		final Music theRecord = getRecord();
		if (theRecord != null) {
			return UserData.getData(theRecord.getOwner());
		}

		return null;
	}

	public String getCreationDate() {
		final Music theRecord = getRecord();
		if ((theRecord != null)) {
			final UserData theUser = getOwner();
			if ((theUser != null) && theUser.isValid()) {
				final Timezone theUsertimezone = theUser.getTimezone();
				final Files theFiles = theRecord.getFile();
				final Lang theLang = theUser.getUserLang().getRecord();
				if ((theUsertimezone != null) && (theFiles != null) && (theLang != null)) {
					final TimeZone theUserTimeZone = theUser.getTimezone().getJavaTimeZone();
					final CCalendar theCalendar = new CCalendar(theFiles.getCreationDate().getTime(), theUserTimeZone);
					return theCalendar.getTimeFormatedRelativeToNow(theUserTimeZone, theUser.getUser_24() == 1, theLang);
				}
			}
		}
		return net.violet.platform.util.StringShop.MIDNIGHT;
	}

	public static MusicData findByAPIId(String inAPIId, String inAPIKey) {
		MusicData theResult = null;
		final long theID = APIData.fromObjectID(inAPIId, ObjectClass.MUSIC, inAPIKey);
		if (theID != 0) {
			final Music music = Factories.MUSIC.find(theID);
			if (music != null) {
				theResult = MusicData.getData(music);
			}
		}
		return theResult;
	}

	public static List<MusicData> findByUserAndMimeType(UserData theUserData, MimeTypes mimeType, int skip, int count) {
		final User inOwner = theUserData.getRecord();
		return MusicData.generateList(Factories.MUSIC.findAllByUserIdAndType(inOwner, mimeType.getMimeType(), skip, count));
	}

	public static List<MusicData> findByMimeType(MimeTypes mimeType, int count) {
		return MusicData.generateList(Factories.MUSIC.findRecentsByType(mimeType.getMimeType(), count));
	}

	public static long countAllItemsOfUser(User inOwner, MimeTypes mimeType) {
		final String ensemble = mimeType.getMimeType();
		return Factories.MUSIC.countItemsOfUserByMimeType(inOwner, ensemble);
	}

	public static MusicData createNewItem(User reference, FilesData filesData) throws BadMimeTypeException {
		return MusicData.getData(Factories.MUSIC.createNewMusic(reference, filesData.getRecord()));
	}

	public static MusicData createMp3(FilesData inFiles, String name, User inOwner, int share) {
		return MusicData.createMp3(inFiles, name, inOwner, MusicStyle.CATEGORIE_MP3_PERSO, share);
	}

	public static MusicData createMp3(FilesData inFiles, String name, User inOwner, int inMusicStyleId, int share) {
		return MusicData.getData(Factories.MUSIC.createNewMusic(inFiles.getRecord(), name, inOwner, inMusicStyleId, share));
	}

	public void setMusicProfile(String name, int shared) {
		final Music theRecord = getRecord();
		if (theRecord != null) {
			theRecord.setMusicProfile(name, shared);
		}
	}

	/**
	 * @return musics by category name and lang
	 */
	public static List<MusicData> findAllItemsByCatAndTypeAndLang(StatusMusic statusMusic, ObjectLangData inLangData, String categoryName, int inSkip, int inCount) {

		final long type = statusMusic.getType();
		final List<Music> list;

		if (type == Music.TYPE_MP3_USER_LIBRARY) {
			list = Factories.MUSIC.findByNabshareCategName(categoryName, inSkip, inCount);
		} else if (type == Music.TYPE_MP3_LIBRARY) { // la bibliothèque est stocké en langue fr-FR mais dispo pour tout le monde
			list = Factories.MUSIC.findAllStylesByTypeAndLang(type, ObjectLangData.DEFAULT_BIBLIO_LANGUAGE.getRecord(), categoryName, inSkip, inCount);
		} else {
			list = Factories.MUSIC.findAllStylesByTypeAndLang(type, inLangData.getReference(), categoryName, inSkip, inCount);
		}

		return MusicData.generateList(list);
	}

	/**
	 * @return a rando music by status and lang
	 */
	public static MusicData findRandomByLang(StatusMusic statusMusic, ObjectLangData inLangData) {

		final long type = statusMusic.getType();
		final Music theResult;

		if (type == Music.TYPE_MP3_USER_LIBRARY) {
			theResult = Factories.MUSIC.findNabshareRand();
		} else if (type == Music.TYPE_MP3_LITTLE_WORDS) {
			theResult = Factories.MUSIC.findRandomClin(inLangData.getReference());
		} else if (type == Music.TYPE_MP3_LIBRARY) {
			theResult = Factories.MUSIC.findRandomInBiblio();
		} else {
			theResult = null;
		}

		return MusicData.getData(theResult);
	}

	/**
	 * Retourne une MusicData au hasard, configurée pour la langue donnée.
	 * 
	 * @param inLang langue
	 * @return MusicData ou <code>null</code>
	 */
	// Caution : used by fu***g jsp
	public static MusicData findRandomClin(Lang inLang) {
		MusicData theResult = null;
		if (inLang != null) {
			final Music theMusic = Factories.MUSIC.findRandomClin(inLang);
			if (theMusic != null) {
				theResult = new MusicData(theMusic, inLang);
			}
		}

		return theResult;
	}

	/**
	 * Retourne une MusicData au hasard, configurée pour la langue donnée.
	 * 
	 * @param inLang langue
	 * @return MusicData ou <code>null</code>
	 */
	// Caution : used by fu***g jsp
	public static MusicData findRandom(Lang inLang) {
		MusicData theResult = null;
		if (inLang != null) {
			final Music theMusic = Factories.MUSIC.findRandomInBiblio();
			if (theMusic != null) {
				theResult = new MusicData(theMusic, inLang);
			}
		}

		return theResult;
	}

	public boolean isLibrary() {
		final Music theRecord = getRecord();
		if (theRecord != null) {
			final int theType = theRecord.getMusic_type();
			return (theType == Music.TYPE_MP3_LITTLE_WORDS) || (theType == Music.TYPE_MP3_LIBRARY) || (theType == Music.TYPE_MP3_SIGNATURE);
		}
		return false;
	}

	public String getNabshareCateg() {
		final Music theRecord = getRecord();
		String categName = null;
		if (theRecord != null) {
			final int idNabshareCateg = theRecord.getMusic_share();
			if (idNabshareCateg > 0) {
				final Categ theNabshareCateg = Factories.CATEG.find(idNabshareCateg);
				if (theNabshareCateg != null) {
					categName = theNabshareCateg.getName();
				}
			}
		}
		return categName;
	}

	public String getListenPath() {
		final Music theRecord = getRecord();
		if (theRecord != null) {
			final Files theListenFile = theRecord.getFile();
			if (theListenFile != null) {
				String urlPath = theListenFile.getPath();
				if (net.violet.platform.datamodel.MimeType.MIME_TYPES.A_MIDI.equals(theListenFile.getType())) {
					urlPath = urlPath.replaceAll(this.suffixeMatchMidi, this.suffixeReplaceMatchMidi);
				}
				return urlPath;
			}
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}
}
