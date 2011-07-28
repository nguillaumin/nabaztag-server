package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.datamodel.Categ;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.MimeType;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.datamodel.factories.MusicFactory;
import net.violet.platform.datamodel.mock.MusicMock;
import net.violet.platform.dataobjects.MusicData;

import org.apache.log4j.Logger;

public class MusicFactoryMock extends RecordFactoryMock<Music, MusicMock> implements MusicFactory {

	private static final Logger LOGGER = Logger.getLogger(MusicFactoryMock.class);

	MusicFactoryMock() {
		super(MusicMock.class);
	}

	public boolean usesFiles(Files inFile) {

		for (final Music aMusic : findAllMapped().values()) {
			if (aMusic.getFile().getId().equals(inFile.getId())) {
				return true;
			}
		}
		return false;
	}

	public List<Music> findAllForSignature(User inUser) {
		final List<Music> theResult = new LinkedList<Music>();
		for (final Music theMusic : findAll()) {
			if ((theMusic.getMusic_type() == Music.TYPE_MP3_SIGNATURE) || (theMusic.getOwner().equals(inUser) && ((theMusic.getMusicStyle().getId().intValue() == MusicStyle.CATEGORIE_MP3_PERSO) || (theMusic.getMusicStyle().getId().intValue() == MusicStyle.CATEGORIE_TTS_PERSO)))) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

	public List<Music> findRecentsByType(String ensemble, int count) {
		final List<Music> theResult = new ArrayList<Music>();
		int cpt = 0;
		for (final Music theMusic : findAll()) {
			final String music_type = Integer.toString(theMusic.getMusic_type());
			if (ensemble.indexOf(music_type) != -1) {
				theResult.add(theMusic);
				cpt++;
			}
			if (cpt >= count) {
				break;
			}
		}

		Collections.sort(theResult, new Comparator<Music>() {

			public int compare(Music o1, Music o2) {
				return o2.getFile().getCreationDate().compareTo(o1.getFile().getCreationDate());
			}
		});
		return theResult;
	}

	public List<Music> findAllByUserIdAndType(User inOwner, String ensemble, int skip, int count) {
		final List<Music> theResult = new ArrayList<Music>();
		final int start = skip;
		for (final Music theMusic : findAllByUserId(inOwner)) {
			final String music_type = Integer.toString(theMusic.getMusic_type());
			if (ensemble.indexOf(music_type) != -1) {
				if (start <= count) {
					theResult.add(theMusic);
				}
			}
		}
		return theResult;
	}

	private List<Music> findAllByUserId(User inOwner) {

		final List<Music> theResult = new ArrayList<Music>();
		for (final Music theMusic : findAll()) {
			if (theMusic.getOwner() == inOwner) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

	public long countItemsOfUserByMimeType(User inOwner, String ensMimeTypes) {
		long result = 0;
		for (final Music theMusic : findAllByUserId(inOwner)) {
			final String music_type = Integer.toString(theMusic.getMusic_type());
			if (ensMimeTypes.indexOf(music_type) >= 0) {
				result++;
			}
		}
		return result;
	}

	public Music createNewMusic(User theUser, Files inFile) throws BadMimeTypeException {
		final MimeType.MIME_TYPES inType = inFile.getType();
		final int mimeType = MusicData.MimeTypes.getMusicTypeFromFileMimeType(inType);
		final Music newMusic = new MusicMock(0L, inFile, net.violet.common.StringShop.EMPTY_STRING, theUser, mimeType);
		return newMusic;
	}

	public List<Music> findAllClin(Lang inLang, long idCateg) {
		final List<Music> theResult = new ArrayList<Music>();
		for (final Music theMusic : findAll()) {
			if ((theMusic.getMusic_lang() == inLang.getId()) && (theMusic.getMusicStyle().getId() == idCateg) && (theMusic.getMusic_type() == Music.TYPE_MP3_LITTLE_WORDS)) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

	public List<Music> findAllForAlarmClock(Lang inLang) {
		final List<Music> theResult = new ArrayList<Music>();
		for (final Music theMusic : findAll()) {
			if ((theMusic.getMusic_lang() == inLang.getId()) && (theMusic.getMusicStyle().getId() == MusicStyle.CATEGORIE_REVEIL)) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

	public List<Music> findAllForBiblio(int inMusicStyleId) {
		final List<Music> theResult = new ArrayList<Music>();
		for (final Music theMusic : findAll()) {
			if ((theMusic.getMusic_type() == Music.TYPE_MP3_LIBRARY) && (theMusic.getMusicStyle().getId() == inMusicStyleId)) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

	public List<Music> findAllNabshareByOwner(User user) {
		final List<Music> theResult = new ArrayList<Music>();
		for (final Music theMusic : findAll()) {
			if (theMusic.getOwner().equals(user) && (theMusic.getMusic_share() > 0)) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

	public List<Music> findAllPersoByUser(User inUser) {
		final List<Music> theResult = new ArrayList<Music>();
		for (final Music theMusic : findAll()) {
			if (theMusic.getOwner().equals(inUser) && ((theMusic.getMusicStyle().getId() == MusicStyle.CATEGORIE_MP3_PERSO) || (theMusic.getMusicStyle().getId() == MusicStyle.CATEGORIE_TTS_PERSO))) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

	public Music findByIdOrUser(long id, User user) {
		Music theResult = null;
		for (final Music theMusic : findAll()) {
			if ((theMusic.getId() == id) && (theMusic.getOwner().equals(user) || (theMusic.getMusic_type() > Music.TYPE_MP3_USER_LIBRARY) || (theMusic.getMusic_share() > 0))) {
				theResult = theMusic;
				break;
			}
		}
		return theResult;
	}

	public List<Music> findByStyle(long inMusicStyleId) {
		final List<Music> theResult = new ArrayList<Music>();
		for (final Music theMusic : findAll()) {
			if (theMusic.getMusicStyle().getId() == inMusicStyleId) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

	public List<Music> findByTagAndLang(String inTag, Lang inLang) {
		throw new UnsupportedOperationException();
	}

	public Music findNabshareRand(Lang inLang) {
		Music theResult = null;
		for (final Music theMusic : findAll()) {
			if ((theMusic.getOwner() == null) && (theMusic.getMusic_lang() == inLang.getId()) && (theMusic.getMusic_share() > 0) && (theMusic.getMusicStyle().getId() == MusicStyle.CATEGORIE_MP3_PERSO)) {
				theResult = theMusic;
				break;
			}
		}
		return theResult;
	}

	public Music findNabshareRand() {
		Music theResult = null;
		for (final Music theMusic : findAll()) {
			if ((theMusic.getOwner() == null) && (theMusic.getMusic_share() > 0) && (theMusic.getMusicStyle().getId() == MusicStyle.CATEGORIE_MP3_PERSO)) {
				theResult = theMusic;
				break;
			}
		}
		return theResult;
	}

	public Music findRandomClin(long idCateg, Lang inLang) {
		Music theResult = null;
		for (final Music theMusic : findAll()) {
			if ((theMusic.getMusic_lang() == inLang.getId()) && (theMusic.getMusicStyle().getId() == idCateg) && (theMusic.getMusic_type() == Music.TYPE_MP3_LITTLE_WORDS)) {
				theResult = theMusic;
				break;
			}
		}
		return theResult;
	}

	public Music findRandomClin(Lang lang) {
		Music theResult = null;
		for (final Music theMusic : findAll()) {
			if ((theMusic.getMusic_lang() == lang.getId()) && (theMusic.getMusic_type() == Music.TYPE_MP3_LITTLE_WORDS)) {
				theResult = theMusic;
				break;
			}
		}
		return theResult;
	}

	public Music findRandomInBiblio() {
		Music theResult = null;
		for (final Music theMusic : findAll()) {
			if (theMusic.getMusic_type() == Music.TYPE_MP3_LIBRARY) {
				theResult = theMusic;
				break;
			}
		}
		return theResult;
	}

	public List<Music> findByNabshareCategAndLang(long idCateg, Lang inLang) {
		final List<Music> theResult = new ArrayList<Music>();
		for (final Music theMusic : findAll()) {
			if ((theMusic.getOwner().getId() > 0) && (theMusic.getMusic_lang() == inLang.getId()) && (theMusic.getMusic_share() > idCateg) && (theMusic.getMusicStyle().getId() == MusicStyle.CATEGORIE_MP3_PERSO)) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

	public Music findByFile(Files inFile) {
		Music theResult = null;
		for (final Music theMusic : findAll()) {
			if (theMusic.getFile().equals(inFile)) {
				theResult = theMusic;
				break;
			}
		}
		return theResult;
	}

	public Music createNewMusic(Files inFile, String name, User inOwner, int styleid, int share) {
		try {
			return new MusicMock(0L, name, inFile, inOwner, styleid, share, 0);
		} catch (final BadMimeTypeException e) {
			MusicFactoryMock.LOGGER.fatal(e, e);
		}
		return null;
	}

	public Music create(Files inFile, Lang inLang, String name, User inOwner, int styleid, int share, int type) {
		return new MusicMock(0L, name, inFile, inOwner, styleid, share, type, inLang.getId().intValue());
	}

	public List<Music> findAllStylesByTypeAndLang(long type, Lang inLang, String categoryName, int skip, int count) {
		final List<Music> theResult = new ArrayList<Music>();
		for (final Music theMusic : findAll()) {
			if ((theMusic.getMusic_lang() == inLang.getId()) && theMusic.getMusicStyle().getMusicstyle_name().equalsIgnoreCase(categoryName) && (theMusic.getMusic_type() == type)) {
				theResult.add(theMusic);
			}
		}
		return getSkipList(theResult, skip, count);
	}

	public List<Music> findByNabshareCategName(String categoryName, int skip, int count) {
		final List<Music> theResult = new ArrayList<Music>();

		Categ theCateg = null;
		for (final Iterator<Categ> iterator = Factories.CATEG.findAll().iterator(); iterator.hasNext() && (theCateg == null);) {
			final Categ aCateg = iterator.next();
			if (aCateg.getName().equals(categoryName)) {
				theCateg = aCateg;
			}
		}

		if (theCateg != null) {
			for (final Music inMusic : findAll()) {
				if (inMusic.getMusic_share() == theCateg.getId().intValue()) {
					theResult.add(inMusic);
				}
			}
		}

		return getSkipList(theResult, skip, count);
	}

	public List<Music> findAllSignatures() {
		final List<Music> theResult = new LinkedList<Music>();
		for (final Music theMusic : findAll()) {
			if ((theMusic.getMusic_type() == Music.TYPE_MP3_SIGNATURE)) {
				theResult.add(theMusic);
			}
		}
		return theResult;
	}

}
