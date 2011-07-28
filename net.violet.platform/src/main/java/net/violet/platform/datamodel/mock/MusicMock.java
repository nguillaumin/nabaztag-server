package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.api.exceptions.BadMimeTypeException;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Music;
import net.violet.platform.datamodel.MusicStyle;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.factories.Factories;
import net.violet.platform.dataobjects.MusicData;

public class MusicMock extends AbstractMockRecord<Music, MusicMock> implements Music {

	/**
	 * Champs de l'enregistrement.
	 */

	private String name;
	private final Files theFile;
	private final User theUser;
	private final int styleid;
	private int share;
	private long lang;
	private int music_type;

	private MusicStyle musicstyle;

	public MusicMock(long inId, String inName, Files inFile, User inOwner, int inStyleId, int inShare, int mimeType, int inLang) {
		super(inId);
		this.name = inName;
		this.theFile = inFile;
		this.styleid = inStyleId;
		this.share = inShare;
		this.theUser = inOwner;
		this.musicstyle = Factories.MUSIC_STYLE.find(this.styleid);
		this.music_type = mimeType;
		this.lang = inLang;
	}

	public MusicMock(long inId, String inName, Files inFile, User inOwner, int inStyleId, int inShare, int inLang) throws BadMimeTypeException {
		this(inId, inName, inFile, inOwner, inStyleId, inShare, MusicData.MimeTypes.getMusicTypeFromFileMimeType(inFile.getType()), inLang);
	}

	public MusicMock(long inId, Files inFile, String inName, User inOwner, int mimeType) {
		this(inId, inName, inFile, inOwner, 0, 0, mimeType, 0);
	}

	public Files getFile() {
		return this.theFile;
	}

	public String getMusicShortName() {
		return this.name;
	}

	public long getMusic_lang() {
		return this.lang;
	}

	public String getMusic_name() {
		return this.name;
	}

	public int getMusic_share() {
		return this.share;
	}

	public int getMusic_type() {
		return this.music_type;
	}

	public MusicStyle getMusicStyle() {
		if ((this.musicstyle == null) && (this.styleid != 0)) {
			this.musicstyle = Factories.MUSIC_STYLE.find(this.styleid);
		}
		return this.musicstyle;
	}

	public void setMusicInfo(String inName, int inShare, Lang inLang) {
		this.name = inName;
		this.share = inShare;
		this.lang = inLang.getId().longValue();
	}

	public void setMusicProfile(String inName, int inShare) {
		this.name = inName;
		this.share = inShare;
	}

	public void setType(int type) {
		this.music_type = type;
	}

	public User getOwner() {
		return this.theUser;
	}
}
