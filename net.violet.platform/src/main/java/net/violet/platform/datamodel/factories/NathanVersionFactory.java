package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.NathanVersion;
import net.violet.platform.datamodel.VObject;

public interface NathanVersionFactory extends RecordFactory<NathanVersion> {

	/**
	 * Returns all the versions created by the provided author and with the
	 * correct isbn value.
	 * 
	 * @param inAuthor the author
	 * @param inIsbn the isbn
	 * @return a list of NathanVersion objects, can be empty but not null.
	 */
	List<NathanVersion> findAllByAuthorAndIsbn(VObject inAuthor, String inIsbn);

	/**
	 * Returns a list containing all the official available versions.
	 * 
	 * @param inIsbn
	 * @return a list of NathanVersion objects, can be empty but not null.
	 */
	List<NathanVersion> findOfficialVersions(String inIsbn);

	/**
	 * Returns a list of versions sorted by popularity. Only non-official,
	 * shared and authorized versions are used.
	 * 
	 * @param inIsbn
	 * @return a sorted list of NathanVersion objects, can be empty but not
	 *         null.
	 */
	List<NathanVersion> getPopularVersions(String inIsbn);

	/**
	 * Returns a list of versions sorted by creation date. Only non-official,
	 * shared and authorized versions are used.
	 * 
	 * @param inIsbn
	 * @return a sorted list of NathanVersion objects, can be empty but not
	 *         null.
	 */
	List<NathanVersion> getRecentVersions(String inIsbn);

	/**
	 * Returns a list of versions which are supposed to be shared but have not
	 * been authorized yet.
	 * 
	 * @return a list of NathanVersionData
	 */
	List<NathanVersion> findWaitingVersions();

	/**
	 * Returns the number of existing versions for the specified book.
	 * 
	 * @param anIsbn the isbn of the book
	 * @return the number of versions
	 */
	int countByBook(long anIsbn);

	/**
	 * Cretes a new NathanVersion, the provided object is the author and the
	 * isbn parameter is the involved book number.
	 * 
	 * @param author
	 * @param isbn
	 * @return
	 */
	NathanVersion createNewNathanVersion(VObject author, String isbn);
}
