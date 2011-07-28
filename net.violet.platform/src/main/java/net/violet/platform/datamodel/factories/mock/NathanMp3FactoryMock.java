package net.violet.platform.datamodel.factories.mock;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.NathanMp3;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.factories.NathanMp3Factory;
import net.violet.platform.datamodel.mock.NathanMp3Mock;

public class NathanMp3FactoryMock extends RecordFactoryMock<NathanMp3, NathanMp3Mock> implements NathanMp3Factory {

	NathanMp3FactoryMock() {
		super(NathanMp3Mock.class);
	}

	public boolean usesFiles(Files inFile) {
		for (final NathanMp3 mp3 : findAll()) {
			if (mp3.getFile().equals(inFile)) {
				return true;
			}
		}
		return false;
	}

	public List<NathanMp3> findAllMp3ByVersion(NathanVersion theVersion) {
		final List<NathanMp3> theResult = new LinkedList<NathanMp3>();
		for (final NathanMp3 theMP3 : findAll()) {
			if (theMP3.getNathanVersion() == theVersion) {
				theResult.add(theMP3);
			}
		}
		Collections.sort(theResult, new Comparator<NathanMp3>() {

			public int compare(NathanMp3 o1, NathanMp3 o2) {
				final long theDiff = o1.getOffset() - o2.getOffset();
				final int theResult2;
				if (theDiff < 0) {
					theResult2 = -1;
				} else if (theDiff > 0) {
					theResult2 = 1;
				} else {
					theResult2 = 0;
				}
				return theResult2;
			}
		});
		return theResult;
	}

	public NathanMp3 createNewNathanMp3(NathanVersion inVersion, Files inFile, int offset) {
		return new NathanMp3Mock(0, inVersion, inFile, offset);
	}

}
