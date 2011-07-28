package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.NathanMp3;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.factories.common.FilesAccessor;

public interface NathanMp3Factory extends RecordFactory<NathanMp3>, FilesAccessor {

	/**
	 * Finds a NathanMp3 object according to its id
	 * 
	 * @param id the id
	 * @return a NathanMp3 object, <code>null</code> if there is no NathanMp3
	 *         object matching
	 */
	NathanMp3 find(long id);

	/**
	 * Returns all the NathanMp3 objects linked to the specified NathanVersion
	 * object.
	 * 
	 * @param theVersion the version containing the NathanMp3
	 * @return a list of NathanMp3 objects
	 */
	List<NathanMp3> findAllMp3ByVersion(NathanVersion theVersion);

	NathanMp3 createNewNathanMp3(NathanVersion inVersion, Files inFile, int offset);
}
