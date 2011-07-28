package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.MusicStyle;

public class MusicStyleMock extends AbstractMockRecord<MusicStyle, MusicStyleMock> implements MusicStyle {

	/**
	 * Champs de l'enregistrement.
	 */

	private String musicstyle_name;
	private int style_private;

	protected MusicStyleMock(int inId) {
		super(inId);
		this.style_private = 0;
	}

	public MusicStyleMock(int inId, String inName, boolean isStylePrivate) {
		super(inId);
		this.style_private = 0;
		if (isStylePrivate) {
			this.style_private = 1;
		} else {
			this.style_private = 0;
		}
		this.musicstyle_name = inName;
	}

	public MusicStyleMock(int inId, String inName) {
		super(inId);
		this.style_private = 0;
		this.musicstyle_name = inName;
	}

	public String getMusicstyle_name() {
		return this.musicstyle_name;
	}

	public long getStyle_private() {
		return this.style_private;
	}

	public long getMusicstyle_id() {
		return super.getId();
	}
}
