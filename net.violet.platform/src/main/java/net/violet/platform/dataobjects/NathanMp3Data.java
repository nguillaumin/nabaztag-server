package net.violet.platform.dataobjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.violet.platform.datamodel.NathanMp3;
import net.violet.platform.datamodel.factories.Factories;

public class NathanMp3Data extends RecordData<NathanMp3> {

	public NathanMp3Data(NathanMp3 inMp3) {
		super(inMp3);
	}

	public static NathanMp3Data find(long id) {
		return new NathanMp3Data(Factories.NATHAN_MP3.find(id));
	}

	/**
	 * Returns all mp3 owned by the specified version.
	 * 
	 * @param version
	 * @return
	 */
	public static List<NathanMp3Data> findAllByVersion(NathanVersionData version) {
		return NathanMp3Data.generateList(Factories.NATHAN_MP3.findAllMp3ByVersion(Factories.NATHAN_VERSION.find(version.getId())));
	}

	/**
	 * Convinient method to convert a list containing NathanMp3 objects into a
	 * list containing NathanMp3Data objects. If the provided list is null the
	 * method returns an empty list, not null.
	 * 
	 * @param inList the list to convert.
	 * @return a list of NathanMp3Data objects, built according to the provided
	 *         list.
	 */
	private static List<NathanMp3Data> generateList(List<NathanMp3> inList) {

		if (inList != null) {
			final List<NathanMp3Data> result = new ArrayList<NathanMp3Data>();

			for (final NathanMp3 mp3 : inList) {
				result.add(new NathanMp3Data(mp3));
			}

			return result;
		}

		return Collections.emptyList();
	}

	public long getId() {
		final NathanMp3 theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getId();
		}

		return 0;
	}

	public long getVersionId() {
		final NathanMp3 theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getNathanVersion().getId();
		}

		return 0;
	}

	public long getFileId() {
		final NathanMp3 theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getFile().getId();
		}

		return 0;
	}

	/**
	 * @return the mp3 offset, -1 if there is no value.
	 */
	public long getOffset() {
		final NathanMp3 theRecord = getRecord();
		if (theRecord != null) {
			return theRecord.getOffset();
		}

		return -1;
	}
}
