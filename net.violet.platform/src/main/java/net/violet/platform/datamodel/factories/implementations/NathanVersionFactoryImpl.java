package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.NathanVersionImpl;
import net.violet.platform.datamodel.VObject;
import net.violet.platform.datamodel.factories.NathanVersionFactory;

import org.apache.log4j.Logger;

public class NathanVersionFactoryImpl extends RecordFactoryImpl<NathanVersion, NathanVersionImpl> implements NathanVersionFactory {


	private static final Logger LOGGER = Logger.getLogger(NathanVersionFactoryImpl.class);

	NathanVersionFactoryImpl() {
		super(NathanVersionImpl.SPECIFICATION);
	}

	public List<NathanVersion> findAllByAuthorAndIsbn(VObject inAuthor, String inIsbn) {
		return findAll("version_author = ? AND version_isbn = ?", Arrays.asList(new Object[] { inAuthor.getId(), inIsbn }), null);
	}

	public List<NathanVersion> findOfficialVersions(String inIsbn) {
		return findAll("version_official = ? AND version_isbn = ?", Arrays.asList(new Object[] { true, inIsbn }), null);
	}

	public List<NathanVersion> getPopularVersions(String inIsbn) {
		return findAll("version_official = ? AND version_status = ? AND version_shared = ? AND version_isbn = ?", Arrays.asList(new Object[] { false, NathanVersion.Status.AUTHORIZED.toString(), true, inIsbn }), "version_nb DESC");
	}

	public List<NathanVersion> getRecentVersions(String inIsbn) {
		return findAll("version_official = ? AND version_status = ? AND version_shared = ? AND version_isbn = ?", Arrays.asList(new Object[] { false, NathanVersion.Status.AUTHORIZED.toString(), true, inIsbn }), "version_date DESC");
	}

	public List<NathanVersion> findWaitingVersions() {
		return findAll("version_shared = ? AND version_status = ?", Arrays.asList(new Object[] { true, NathanVersion.Status.FINISHED.toString() }), null);
	}

	public int countByBook(long anIsbn) {
		return findAll("version_isbn = ?", Arrays.asList(new Object[] { anIsbn }), null).size();
	}

	public NathanVersion createNewNathanVersion(VObject author, String isbn) {
		try {
			return new NathanVersionImpl(author, isbn);
		} catch (final SQLException e) {
			NathanVersionFactoryImpl.LOGGER.info(e, e);
		}
		return null;
	}
}
