package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.FilesImpl;
import net.violet.platform.datamodel.NathanMp3;
import net.violet.platform.datamodel.NathanMp3Impl;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.NathanVersionImpl;
import net.violet.platform.datamodel.factories.NathanMp3Factory;
import net.violet.platform.util.StringShop;

public class NathanMp3FactoryImpl extends RecordFactoryImpl<NathanMp3, NathanMp3Impl> implements NathanMp3Factory {

	public NathanMp3FactoryImpl() {
		super(NathanMp3Impl.SPECIFICATION);
	}

	public boolean usesFiles(Files inFile) {
		return count(null, StringShop.FILE_ID_CONDITION, Collections.singletonList((Object) inFile.getId()), null) > 0;
	}

	public List<NathanMp3> findAllMp3ByVersion(NathanVersion theVersion) {
		return findAll(" version_id = ? ", Collections.singletonList((Object) theVersion.getId()), "fragment_offset");
	}

	public NathanMp3 createNewNathanMp3(NathanVersion inVersion, Files inFile, int offset) {
		try {
			return new NathanMp3Impl((NathanVersionImpl) inVersion, (FilesImpl) inFile, offset);
		} catch (final SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
