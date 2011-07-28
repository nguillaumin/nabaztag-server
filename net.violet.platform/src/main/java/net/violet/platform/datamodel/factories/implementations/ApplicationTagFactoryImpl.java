package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.ApplicationTagImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.ApplicationTagFactory;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class ApplicationTagFactoryImpl extends RecordFactoryImpl<ApplicationTag, ApplicationTagImpl> implements ApplicationTagFactory {


	private static final Logger LOGGER = Logger.getLogger(ApplicationTagFactoryImpl.class);

	public ApplicationTagFactoryImpl() {
		super(ApplicationTagImpl.SPECIFICATION);
	}

	public List<ApplicationTag> findAllByLanguage(Lang inLang, int inSkip, int inGetCount) {
		return findAll(" lang_id = ? ", Collections.singletonList((Object) inLang.getId()), " tag_size DESC ", inSkip, inGetCount);
	}

	public List<ApplicationTag> findAllByLanguageAndHardware(Lang inLang, HARDWARE inHardware, int inSkip, int inGetCount) {
		final String[] joinTables = { "application_has_tag", "application", "application_hardware" };
		final String condition = " application.application_id = application_has_tag.application_id and application_has_tag.tag_id=application_tag.tag_id and application_hardware.application_id = application.application_id and application_tag.lang_id = ? and application_hardware.hardware_id = ? and application_visible = ? ";
		return findAll(joinTables, condition, Arrays.asList(new Object[] { inLang.getId(), inHardware.getId(), 1 }), " tag_size DESC ", inSkip, inGetCount);
	}

	public ApplicationTag getTag(String inName, Lang inLang) {
		return find(" tag_name = ? AND lang_id = ? ", Arrays.asList(new Object[] { inName, inLang.getId() }));
	}

	public ApplicationTag createTag(String inName, Lang inLang) {
		final ApplicationTag result = getTag(inName, inLang);
		if (result == null) {
			try {
				return new ApplicationTagImpl(inName, inLang);
			} catch (final SQLException e) {
				ApplicationTagFactoryImpl.LOGGER.fatal(e, e);
			}
		}

		return result;
	}

	public void addTagToApplication(Application inApplication, ApplicationTag inTag) {

		if (inApplication.getTags().contains(inTag)) {
			return;
		}

		inApplication.getTags().add(inTag);

		setNewSize(inTag);
	}

	public void removeTagFromApplication(Application inApplication, ApplicationTag inTag) {
		if (!inApplication.getTags().contains(inTag)) {
			return;
		}

		inApplication.getTags().remove(inTag);

		setNewSize(inTag);
	}

	private void setNewSize(ApplicationTag inTag) {
		final int tagSize = 100 * Factories.APPLICATION.findAllByTag(inTag).size() / Factories.APPLICATION.findAllMapped().size();
		inTag.setSize(tagSize);
	}

}
