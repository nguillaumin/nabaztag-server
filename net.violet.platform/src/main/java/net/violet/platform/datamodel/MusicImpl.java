package net.violet.platform.datamodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.violet.db.records.ObjectRecord;
import net.violet.db.records.SQLKey;
import net.violet.db.records.SQLObjectSpecification;
import net.violet.db.records.associations.SingleAssociationNotNull;
import net.violet.platform.dataobjects.ObjectLangData;
import net.violet.platform.util.StringShop;

public class MusicImpl extends ObjectRecord<Music, MusicImpl> implements Music {

	/**
	 * Spécification
	 */
	public static final SQLObjectSpecification<MusicImpl> SPECIFICATION = new SQLObjectSpecification<MusicImpl>("music", MusicImpl.class, new SQLKey[] { new SQLKey("music_id"), new SQLKey(StringShop.FILE_ID) });

	/**
	 * Constructeur protégé à partir d'un id.
	 */
	protected MusicImpl(long id) throws SQLException {
		init(id);
		this.mFile = new SingleAssociationNotNull<Music, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);
		this.mOwner = new SingleAssociationNotNull<Music, User, UserImpl>(this, "music_owner", UserImpl.SPECIFICATION);
	}

	/**
	 * Constructeur par défaut.
	 */
	protected MusicImpl() {
		this.mFile = new SingleAssociationNotNull<Music, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);
		this.mOwner = new SingleAssociationNotNull<Music, User, UserImpl>(this, "music_owner", UserImpl.SPECIFICATION);
	}

	/**
	 * Champs de l'enregistrement.
	 */
	protected long music_id;
	protected long file_id;
	protected String music_name;
	protected long music_owner;
	protected int music_styleid;
	protected int music_share;
	protected int music_type;
	protected long music_lang;

	private MusicStyle musicstyle;

	/**
	 * Liste des colonnes pour le constructeur pour les nouveaux
	 * enregistrements.
	 */
	private static final String[] NEW_COLUMNS = new String[] { StringShop.FILE_ID, "music_name", "music_owner", "music_styleid", "music_share", "music_type", "music_lang", };

	private final SingleAssociationNotNull<Music, Files, FilesImpl> mFile;
	/**
	 * Possesseur de l'objet.
	 */
	private final SingleAssociationNotNull<Music, User, UserImpl> mOwner;

	public MusicImpl(User inOwner, Files inFile, int musicType) throws SQLException {
		this(inFile, inOwner, null, null, 0, 0, musicType);
	}

	public MusicImpl(Files inFileId, User inOwner, Lang lang, String name, int styleid, int share, int inMusicType) throws SQLException {
		this.music_name = name;
		this.file_id = inFileId.getId();
		if (inOwner != null) {
			this.music_owner = inOwner.getId().intValue();

			if (lang == null) {
				final Annu theAnnu = inOwner.getAnnu();
				if (theAnnu != null) {
					final Lang theUserLang = theAnnu.getLangPreferences();
					if (theUserLang != null) {
						this.music_lang = ObjectLangData.getDefaultObjectLanguage(theUserLang.getIsoCode()).getId();
					}
				}
			}
		} else {
			this.music_owner = 0;
		}
		this.music_styleid = styleid;
		this.music_share = share;

		if (lang != null) {
			this.music_lang = lang.getId().longValue();
		}

		this.music_type = inMusicType;
		init(MusicImpl.NEW_COLUMNS);
		this.mFile = new SingleAssociationNotNull<Music, Files, FilesImpl>(this, StringShop.FILE_ID, FilesImpl.SPECIFICATION);
		this.mOwner = new SingleAssociationNotNull<Music, User, UserImpl>(this, "music_owner", UserImpl.SPECIFICATION);
	}

	@Override
	public SQLObjectSpecification<MusicImpl> getSpecification() {
		return MusicImpl.SPECIFICATION;
	}

	public final String getMusic_name() {
		return this.music_name;
	}

	public void setMusicInfo(String name, int share, Lang lang) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setMusic_name(theUpdateMap, name);
		setMusic_share(theUpdateMap, share);
		setMusic_lang(theUpdateMap, lang);
		update(theUpdateMap);
	}

	public void setMusicProfile(String name, int share) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setMusic_name(theUpdateMap, name);
		setMusic_share(theUpdateMap, share);
		update(theUpdateMap);
	}

	private void setMusic_name(Map<String, Object> inUpdateMap, String inValue) {
		if ((inValue != this.music_name) && ((inValue == null) || !inValue.equals(this.music_name))) {
			this.music_name = inValue;
			inUpdateMap.put("music_name", inValue);
		}
	}

	private void setMusic_share(Map<String, Object> inUpdateMap, int inValue) {
		if (inValue != this.music_share) {
			this.music_share = inValue;
			inUpdateMap.put("music_share", inValue);
		}
	}

	private void setMusic_lang(Map<String, Object> inUpdateMap, Lang inValue) {
		final long theUpdateValue = inValue.getId().longValue();
		if (theUpdateValue != this.music_lang) {
			this.music_lang = theUpdateValue;
			inUpdateMap.put("music_lang", theUpdateValue);
		}
	}

	public final String getMusicShortName() {
		String theResult = this.music_name;
		if (theResult != null) {
			theResult = theResult.trim();
			if (theResult.length() > 40) {
				theResult = theResult.substring(0, 40) + "...";
			}
		}
		return theResult;
	}

	public Files getFile() {
		if (this.file_id != 0) {
			return this.mFile.get(this.file_id);
		}
		return null;
	}

	public final MusicStyle getMusicStyle() {
		if (this.musicstyle == null) {
			this.musicstyle = MusicStyleImpl.find(this.music_styleid);
		}
		return this.musicstyle;
	}

	public final int getMusic_share() {
		return this.music_share;
	}

	public final int getMusic_type() {
		return this.music_type;
	}

	public void setType(int type) {
		final Map<String, Object> theUpdateMap = new HashMap<String, Object>();
		setMusic_type(theUpdateMap, type);
		update(theUpdateMap);
	}

	private void setMusic_type(Map<String, Object> inUpdateMap, int inValue) {
		if (inValue != this.music_type) {
			this.music_type = inValue;
			inUpdateMap.put("music_type", inValue);
		}
	}

	public final long getMusic_lang() {
		return this.music_lang;
	}

	@Override
	protected void doDelete() throws SQLException {
		final Files theFile = getFile();

		if (theFile != null) {
			theFile.scheduleDeletion();
		}
		super.doDelete();
	}

	public User getOwner() {
		if (this.music_owner != 0) {
			return this.mOwner.get(this.music_owner);
		}
		return null;
	}

}
