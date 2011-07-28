package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicImpl;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.MusicFactory;
import net.violet.platform.dataobjects.MusicData;
import net.violet.platform.util.StringShop;

import org.apache.log4j.Logger;

public class MusicFactoryImpl extends RecordFactoryImpl<Music, MusicImpl> implements MusicFactory {

	private static final Logger LOGGER = Logger.getLogger(MusicFactoryImpl.class);

	public MusicFactoryImpl() {
		super(MusicImpl.SPECIFICATION);
	}

	public boolean usesFiles(Files inFile) {
		return count(null, StringShop.FILE_ID_CONDITION, Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

	public long countItemsOfUserByMimeType(User inOwner, String ensembleMimeTypes) {
		final String condition = "music_owner = ? AND music_type in (" + ensembleMimeTypes + ")";
		return count(null, condition, Arrays.asList(new Object[] { inOwner.getId() }), null);
	}

	public Music createNewMusic(User theUser, Files inFile) throws BadMimeTypeException {
		try {
			final int musicType = MusicData.MimeTypes.getMusicTypeFromFileMimeType(inFile.getType());
			return new MusicImpl(theUser, inFile, musicType);
		} catch (final SQLException e) {
			MusicFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public Music createNewMusic(Files inFile, String name, User inOwner, int styleid, int share) {
		if (inFile != null) {
			try {
				return new MusicImpl(inFile, inOwner, null, name, styleid, share, Music.TYPE_MP3_USER_LIBRARY);
			} catch (final SQLException se) {
				MusicFactoryImpl.LOGGER.fatal(se, se);
			}
		}
		return null;
	}

	public Music create(Files inFile, Lang inLang, String name, User inOwner, int styleid, int share, int type) {
		if (inFile != null) {
			try {
				return new MusicImpl(inFile, inOwner, inLang, name, styleid, share, type);
			} catch (final SQLException se) {
				MusicFactoryImpl.LOGGER.fatal(se, se);
			}
		}
		return null;
	}

	public Music findByIdOrUser(long id, User user) {
		return find(" music_id = ? and (music_owner = ? or music_type > ? or music_share > ? )", Arrays.asList(new Object[] { id, user.getId(), Music.TYPE_MP3_USER_LIBRARY, 0 }));
	}

	public Music findNabshareRand() {
		return find(" music_owner > ? and music_share > ? and music_styleid = ? order by rand() ", Arrays.asList(new Object[] { 0, 0, MusicStyle.CATEGORIE_MP3_PERSO }));
	}

	public Music findNabshareRand(Lang inLang) {
		return find(" music_owner > ? and music_lang = ? and music_share > ? and music_styleid = ? order by rand() ", Arrays.asList(new Object[] { 0, inLang.getId(), 0, MusicStyle.CATEGORIE_MP3_PERSO }));
	}

	public Music findRandomClin(long idCateg, Lang inLang) {
		return find(" music_lang = ? and music_styleid = ?  and music_type = ? ORDER BY rand() ", Arrays.asList(new Object[] { inLang.getId(), idCateg, Music.TYPE_MP3_LITTLE_WORDS }));
	}

	public Music findRandomClin(Lang lang) {
		return find(" music_lang = ? and music_type = ? ORDER BY rand() ", Arrays.asList(new Object[] { lang.getId(), Music.TYPE_MP3_LITTLE_WORDS }));
	}

	public Music findRandomInBiblio() {
		return find("music_type = ? order by rand() ", Collections.singletonList((Object) Music.TYPE_MP3_LIBRARY));
	}

	public Music findByFile(Files inFile) {
		return findByKey(1, inFile.getId());
	}

	public List<Music> findAllForSignature(User inUser) {
		final long user_id = inUser.getId();
		final List<Object> theValues = Arrays.asList(new Object[] { Music.TYPE_MP3_SIGNATURE, user_id, MusicStyle.CATEGORIE_MP3_PERSO, MusicStyle.CATEGORIE_TTS_PERSO });

		return findAll("music_type = ? or" + "(  music_owner = ? and   music_styleid IN ( ?, ? ))", theValues, "  music_name ");
	}

	public List<Music> findAllByUserIdAndType(User inOwner, String ensembleMimeTypes, int skip, int count) {
		final String condition = "music_owner = ? and music_type in ( " + ensembleMimeTypes + " )";
		return findAll(condition, Arrays.asList(new Object[] { inOwner.getId() }), "music_id desc", skip, count);
	}

	public List<Music> findRecentsByType(String ensembleMimeTypes, int count) {
		final String[] inJoinTables = new String[] { "files" };
		final String condition = " file_id = id and music_type in ( " + ensembleMimeTypes + " )";
		return findAll(inJoinTables, condition, null, "creation_date DESC ", 0, count);
	}

	public List<Music> findAllClin(Lang inLang, long idCateg) {
		return findAll(" music_lang = ? and music_styleid = ?  and music_type = ? ", Arrays.asList(new Object[] { inLang.getId(), idCateg, Music.TYPE_MP3_LITTLE_WORDS }));
	}

	public List<Music> findAllForAlarmClock(Lang inLang) {
		return findAll(" music_styleid = ? and music_lang = ? ", Arrays.asList(new Object[] { MusicStyle.CATEGORIE_REVEIL, inLang.getId() }));
	}

	public List<Music> findAllForBiblio(int inMusicStyleId) {
		return findAll("music_type = ? and music_styleid = ? ", Arrays.asList(new Object[] { Music.TYPE_MP3_LIBRARY, inMusicStyleId }));
	}

	public List<Music> findAllNabshareByOwner(User user) {
		return findAll(" music_owner = ? and music_share > ? ", Arrays.asList(new Object[] { user.getId(), 0 }));
	}

	public List<Music> findAllPersoByUser(User inUser) {

		final long user_id = inUser.getId();
		final List<Object> theValues = Arrays.asList(new Object[] { user_id, MusicStyle.CATEGORIE_MP3_PERSO, MusicStyle.CATEGORIE_TTS_PERSO });

		return findAll(" music_owner = ? and music_styleid in ( ? , ? )", theValues, "  music_name ");
	}

	public List<Music> findByStyle(long inMusicStyleId) {
		return findAll(" music_styleid = ? ", Collections.singletonList((Object) inMusicStyleId));
	}

	public List<Music> findByTagAndLang(String inTag, Lang inLang) {
		return findAll(new String[] { "tag" }, "music_lang = ? AND music_owner > 0 AND music_share > 0 AND tag_word = ? AND tag_mp3 = music_id ", Arrays.asList(new Object[] { inLang.getId(), inTag }), "music_name");
	}

	public List<Music> findByNabshareCategAndLang(long idNabshareCateg, Lang inLang) {
		return findAll(" music_owner > ? and music_lang = ? and music_share = ? and music_styleid = ?", Arrays.asList(new Object[] { 0, inLang.getId(), idNabshareCateg, MusicStyle.CATEGORIE_MP3_PERSO }), "music_name");
	}

	public List<Music> findByNabshareCategName(String categoryName, int skip, int count) {
		final String[] inJoinTables = new String[] { "categ" };
		final String condition = "music_share = categ_id and categ_type = ?";
		final List<Music> theResult = findAll(inJoinTables, condition, Arrays.asList(new Object[] { categoryName }), "music_name", skip, count);
		return theResult;
	}

	public List<Music> findAllStylesByTypeAndLang(long type, Lang inLang, String categoryName, int skip, int count) {
		final String[] inJoinTables = new String[] { "musicstyle" };
		final String condition = "musicstyle_id = music_styleid" + " AND music_type = ? AND music_lang = ?  AND musicstyle_name = ? ";
		final List<Music> theResult = findAll(inJoinTables, condition, Arrays.asList(new Object[] { type, inLang.getId(), categoryName }), "musicstyle_id", skip, count);
		return theResult;
	}

	public List<Music> findAllSignatures() {
		return findAll("music_type = ?", Collections.singletonList((Object) Music.TYPE_MP3_SIGNATURE), "  music_name ");
	}

}
