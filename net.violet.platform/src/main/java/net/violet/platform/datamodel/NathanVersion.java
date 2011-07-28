package net.violet.platform.datamodel;

import java.sql.Date;
import java.util.List;

import net.violet.db.records.Record;

public interface NathanVersion extends Record<NathanVersion> {

	enum Status {

		FINISHED {

			@Override
			public String toString() {
				return "FINISHED";
			}
		},
		AUTHORIZED {

			@Override
			public String toString() {
				return "AUTHORIZED";
			}
		},
		REFUSED {

			@Override
			public String toString() {
				return "REFUSED";
			}
		},
	}

	/**
	 * @return Nathan Version id.
	 */
	Long getId();

	/**
	 * @return the VObject object which created this NathanVersion.
	 */
	VObject getAuthor();

	/**
	 * @Return Nathan Version description.
	 */
	String getDescription();

	/**
	 * @return when this Nathan Version was created (java.sql.Date).
	 */
	Date getDate();

	/**
	 * @return this Nathan Version status.
	 */
	String getStatus();

	/**
	 * @return true if this Nathan Version is shared by the author, false
	 *         otherwise.
	 */
	boolean getShared();

	/**
	 * @return true if this Nathan Version is an official version, false
	 *         otherwise.
	 */
	boolean getOfficial();

	/**
	 * @return the number of objects using this version in their selection.
	 */
	long getNb();

	/**
	 * Returns the list of the defined tags for this version.
	 * 
	 * @return a list of NathanTag ojects
	 */
	List<NathanTag> getTags();

	/**
	 * @return the Nathan Version isbn (identifying the involved book).
	 */
	String getIsbn();

	/**
	 * Use this method to increase by one the value of the nb field (i.e. the
	 * number of objects using this version).
	 */
	void increaseNb();

	/**
	 * Use this method to decrease by one the value of the nb field(i.e. the
	 * number of objects using this version).
	 */
	void decreaseNb();

	/**
	 * This method is used to modify information about the NathanVersion.
	 * 
	 * @param description version description
	 * @param status version status
	 * @param shared true if the version is shared, false otherwise
	 * @param tagsList list of tags describing this version
	 */
	void setVersionInformation(String description, Status status, boolean shared, List<NathanTag> tagsList);

	/**
	 * Change the value of the status
	 * 
	 * @param status
	 */
	void setStatus(Status status);

	/**
	 * Change the value of the version's preview.
	 * 
	 * @param previewId refers to the Files containing the preview
	 */
	void setPreview(long previewId);

	Long getPreview();

}
