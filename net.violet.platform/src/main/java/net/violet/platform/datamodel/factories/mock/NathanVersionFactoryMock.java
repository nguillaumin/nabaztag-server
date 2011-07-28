package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.NathanVersionFactory;
import net.violet.platform.datamodel.mock.NathanVersionMock;

public class NathanVersionFactoryMock extends RecordFactoryMock<NathanVersion, NathanVersionMock> implements NathanVersionFactory {

	public NathanVersionFactoryMock() {
		super(NathanVersionMock.class);
	}

	public List<NathanVersion> findAllByAuthorAndIsbn(VObject inAuthor, String inIsbn) {
		final List<NathanVersion> theResult = new LinkedList<NathanVersion>();
		for (final NathanVersion version : findAll()) {
			if (version.getAuthor().equals(inAuthor) && version.getIsbn().equals(inIsbn)) {
				theResult.add(version);
			}
		}
		return theResult;
	}

	public List<NathanVersion> findOfficialVersions(String inIsbn) {
		final List<NathanVersion> theResult = new LinkedList<NathanVersion>();
		for (final NathanVersion version : findAll()) {
			if (version.getIsbn().equals(inIsbn) && version.getOfficial()) {
				theResult.add(version);
			}
		}
		return theResult;
	}

	public List<NathanVersion> getPopularVersions(String inIsbn) {
		final List<NathanVersion> theResult = new LinkedList<NathanVersion>();
		for (final NathanVersion version : findAll()) {
			if (version.getIsbn().equals(inIsbn)) {
				theResult.add(version);
			}
		}
		return theResult;
	}

	public List<NathanVersion> getRecentVersions(String inIsbn) {
		final List<NathanVersion> theResult = new LinkedList<NathanVersion>();
		for (final NathanVersion version : findAll()) {
			if (version.getIsbn().equals(inIsbn)) {
				theResult.add(version);
			}
		}
		return theResult;
	}

	public NathanVersion createNewNathanVersion(VObject author, String isbn) {
		return new NathanVersionMock(0, author, isbn);
	}

	public int countByBook(long anIsbn) {
		int result = 0;
		for (final NathanVersion version : findAll()) {
			if (Long.parseLong(version.getIsbn()) == anIsbn) {
				result++;
			}
		}
		return result;
	}

	public List<NathanVersion> findWaitingVersions() {
		final List<NathanVersion> result = new ArrayList<NathanVersion>();
		for (final NathanVersion version : findAll()) {
			if (version.getShared() && version.getStatus().equals(NathanVersion.Status.FINISHED.toString())) {
				result.add(version);
			}
		}
		return result;
	}

}
