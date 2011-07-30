package net.violet.platform.datamodel;

import net.violet.db.records.Record;

/**
 * Different audio files for a Nathan audio book (RFID)
 */
public interface NathanMp3 extends Record<NathanMp3> {

	/**
	 * @return a long object containing the NathanMp3 id.
	 */
	Long getId();

	/**
	 * @return the NathanVersion associated with this fragment.
	 */
	NathanVersion getNathanVersion();

	/**
	 * @return the Files object this NathanMp3 refers to.
	 */
	Files getFile();

	/**
	 * @return the NathanMp3 offset.
	 */
	int getOffset();

}
