package net.violet.platform.datamodel.factories;

import java.sql.SQLException;
import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Hardware.HARDWARE;

public interface ApplicationTagFactory extends RecordFactory<ApplicationTag> {

	/**
	 * Returns the list of all existing tags for the given language. The
	 * returned list is sorted from the most popular tag (which has the greatest
	 * size) to the less used one.
	 * 
	 * @param inLang
	 * @param inSkip number of tag to skip
	 * @param inGetCount number of tag to get
	 * @return
	 */
	List<ApplicationTag> findAllByLanguage(Lang inLang, int inSkip, int inGetCount);

	/**
	 * Returns the list of all existing tags for the given language and
	 * hardware. The returned list is sorted from the most popular tag (which
	 * has the greatest size) to the less used one.
	 * 
	 * @param inLang
	 * @param inHardware
	 * @param inSkip number of tag to skip
	 * @param inGetCount number of tag to get
	 * @return
	 */
	List<ApplicationTag> findAllByLanguageAndHardware(Lang inLang, HARDWARE inHardware, int inSkip, int inGetCount);

	/**
	 * Returns a Tag according to the provided name and language. This method
	 * returns the request ApplicationTag object if it exists, otherwise it
	 * returns null.
	 * 
	 * @param inName
	 * @param inLang
	 * @return
	 */
	ApplicationTag getTag(String inName, Lang inLang);

	/**
	 * Creates a new ApplicationTag object and returns it. If the tag already
	 * exists it is not created but the existing object is returned.
	 * 
	 * @param inName
	 * @param inLang
	 * @return
	 * @throws SQLException
	 */
	ApplicationTag createTag(String inName, Lang inLang);

	/**
	 * Links the given application and the given tag. This method is the only
	 * right way to add an ApplicationTag to an Application, this way, the tag
	 * size can be edited.
	 * 
	 * @param inApplication
	 * @param inTag
	 */
	void addTagToApplication(Application inApplication, ApplicationTag inTag);

	/**
	 * Removes the given tag from the given application tags collection. This is
	 * the right way to unlink a tag and an application (to edit the tag size
	 * field).
	 * 
	 * @param inApplication
	 * @param inTag
	 */
	void removeTagFromApplication(Application inApplication, ApplicationTag inTag);

}
