package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ApplicationCategory;
import net.violet.platform.datamodel.ApplicationImpl;
import net.violet.platform.datamodel.ApplicationTag;
import net.violet.platform.datamodel.ApplicationTemp;
import net.violet.platform.datamodel.ApplicationTempImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.User;
import net.violet.platform.datamodel.Application.ApplicationClass;
import net.violet.platform.datamodel.Hardware.HARDWARE;
import net.violet.platform.datamodel.factories.ApplicationFactory;

import org.apache.log4j.Logger;

public class ApplicationFactoryImpl extends RecordFactoryImpl<Application, ApplicationImpl> implements ApplicationFactory {

	private static final Logger LOGGER = Logger.getLogger(ApplicationFactoryImpl.class);

	ApplicationFactoryImpl() {
		super(ApplicationImpl.SPECIFICATION);
	}

	public Application findByName(String inApplicationName) {
		return find(" application_name = ? ", Arrays.asList((Object) inApplicationName));
	}

	public List<Application> findByCategory(ApplicationCategory inCategory, int inSkip, int inGetCount) {
		return findAll(" application_category_id = ? and application_visible = ? ", Arrays.asList((Object) inCategory.getId(), 1), null, inSkip, inGetCount);
	}

	public List<Application> findAllByTag(ApplicationTag inTag) {
		final String[] joinTables = { "application_has_tag" };
		final String condition = " application.application_id = application_has_tag.application_id and application_has_tag.tag_id = ? and application_visible = ? ";
		return findAll(joinTables, condition, Arrays.asList((Object) inTag.getId(), 1), null);
	}

	public List<Application> findAllByTagAndLang(ApplicationTag inTag, Lang inLang, int inSkip, int inGetCount) {
		final String[] joinTables = { "application_has_tag", "application_tag" };
		final String condition = " application.application_id = application_has_tag.application_id and application_has_tag.tag_id=application_tag.tag_id and application_tag.tag_id = ? and application_tag.lang_id = ? and application_visible = ? ";
		return findAll(joinTables, condition, Arrays.asList((Object) inTag.getId(), inLang.getId(), 1), null, inSkip, inGetCount);
	}

	public long countByTagAndLang(ApplicationTag inTag, Lang inLang) {
		final String[] joinTables = { "application_has_tag", "application_tag" };
		final String condition = " application.application_id = application_has_tag.application_id and application_has_tag.tag_id=application_tag.tag_id and application_tag.tag_id = ? and application_tag.lang_id = ? and application_visible = ? ";
		return count(joinTables, condition, Arrays.asList((Object) inTag.getId(), inLang.getId(), 1), null);
	}

	public List<Application> findAllByTagAndLangAndHardware(ApplicationTag inTag, Lang inLang, HARDWARE inHardware, int inSkip, int inGetCount) {
		final String[] joinTables = { "application_has_tag", "application_tag", "application_hardware" };
		final String condition = " application.application_id = application_has_tag.application_id and application_has_tag.tag_id=application_tag.tag_id and application_hardware.application_id = application.application_id and application_tag.tag_id = ? and application_tag.lang_id = ? and application_hardware.hardware_id = ? and application_visible = ? ";
		return findAll(joinTables, condition, Arrays.asList((Object) inTag.getId(), inLang.getId(), inHardware.getId(), 1), null, inSkip, inGetCount);
	}

	public long countByTagAndLangAndHardware(ApplicationTag inTag, Lang inLang, HARDWARE inHardware) {
		final String[] joinTables = { "application_has_tag", "application_tag", "application_hardware" };
		final String condition = " application.application_id = application_has_tag.application_id and application_has_tag.tag_id=application_tag.tag_id and application_hardware.application_id = application.application_id and application_tag.tag_id = ? and application_tag.lang_id = ? and application_hardware.hardware_id = ? and application_visible = ? ";
		return count(joinTables, condition, Arrays.asList((Object) inTag.getId(), inLang.getId(), inHardware.getId(), 1), null);
	}

	public List<Application> findAllByLangAndRank(Lang inLang, int inSkip, int inGetCount) {
		final String[] joinTables = { "application_lang" };
		final String condition = " application_lang.application_id = application.application_id AND application_lang.lang_id = ? and application_visible = ? ";
		return findAll(joinTables, condition, Arrays.asList((Object) inLang.getId(), 1), " rank desc ", inSkip, inGetCount);
	}

	public List<Application> findAllByLangAndCateg(Lang inLang, ApplicationCategory inCateg) {
		final String[] joinTables = { "application_lang" };
		final String condition = " application.application_category_id = ? AND application_lang.application_id = application.application_id AND application_lang.lang_id = ? and application_visible = ? ";
		return findAll(joinTables, condition, Arrays.asList((Object) inCateg.getId(), inLang.getId(), 1), " rank desc ");
	}

	public List<Application> findByLangAndApplicationNameStartingWith(Lang inLang, String name) {
		final String[] inJoinTables = { "application_lang" };
		final String condition = " application_lang.application_id = application.application_id AND application_lang.lang_id = ? AND application_name LIKE ?";
		final List<Object> inValues = Arrays.asList((Object) inLang.getId(), (Object) (name + "%"));
		final String inOrderBy = "application_name";
		return findAllDistinct(inJoinTables, condition, inValues, inOrderBy);
	}

	public List<Application> findByCategoryAndHardware(ApplicationCategory inCategory, HARDWARE inHardware, int inSkip, int inGetCount) {
		final String[] joinTables = { "application_hardware" };
		return findAll(joinTables, " application_hardware.application_id = application.application_id AND application.application_category_id = ? and application_hardware.hardware_id = ? and application_visible = ? ", Arrays.asList((Object) inCategory.getId(), inHardware.getId(), 1), null, inSkip, inGetCount);
	}

	public List<Application> findByCategoryAndLang(ApplicationCategory inCategory, Lang inLang, int inSkip, int inGetCount) {
		final String[] joinTables = { "application_lang" };
		return findAll(joinTables, " application_lang.application_id = application.application_id AND application.application_category_id = ? and application_lang.lang_id = ? and application_visible = ? ", Arrays.asList((Object) inCategory.getId(), inLang.getId(), 1), " rank desc ", inSkip, inGetCount);
	}

	public List<Application> findByCategoryAndLangAndHardware(ApplicationCategory inCategory, Lang inLang, HARDWARE inHardware, int inSkip, int inGetCount) {
		final String[] joinTables = { "application_hardware", "application_lang" };
		return findAll(joinTables, " application_hardware.application_id = application.application_id AND application_lang.application_id = application.application_id AND application.application_category_id = ? and application_lang.lang_id = ? and application_hardware.hardware_id = ? and application_visible = ? ", Arrays.asList(new Object[] { inCategory.getId(), inLang.getId(), inHardware.getId(), 1 }), " rank desc ", inSkip, inGetCount);
	}

	public long countByCategory(ApplicationCategory inCategory) {
		return count(null, " application_category_id = ? and application_visible = ? ", Arrays.asList(new Object[] { inCategory.getId(), 1 }), null);
	}

	public long countByCategoryAndHardware(ApplicationCategory inCategory, HARDWARE inHardware) {
		final String[] joinTables = { "application_hardware" };
		return count(joinTables, " application_hardware.application_id = application.application_id AND application.application_category_id = ? and application_hardware.hardware_id = ? and application_visible = ? ", Arrays.asList((Object) inCategory.getId(), inHardware.getId(), 1), null);

	}

	public Application findByLink(String link) {
		final Application theApplication = null;
		final ApplicationTemp temp = ApplicationTempImpl.findByLink(link);
		if (temp != null) {
			return temp.getApplication();
		}
		return theApplication;
	}

	public long countByCategoryAndLang(ApplicationCategory inCategory, Lang inLang) {
		final String[] joinTables = { "application_lang" };
		return count(joinTables, " application_lang.application_id = application.application_id AND application.application_category_id = ? and application_lang.lang_id = ? and application_visible = ? ", Arrays.asList((Object) inCategory.getId(), inLang.getId(), 1), null);
	}

	public long countByCategoryAndLangAndHardware(ApplicationCategory inCategory, Lang inLang, HARDWARE inHardware) {
		final String[] joinTables = { "application_hardware", "application_lang" };
		return count(joinTables, " application_hardware.application_id = application.application_id AND application_lang.application_id = application.application_id AND application.application_category_id = ? and application_lang.lang_id = ? and application_hardware.hardware_id = ? and application_visible = ? ", Arrays.asList((Object) inCategory.getId(), inLang.getId(), inHardware.getId(), 1), null);
	}

	public Application create(User inAuthor, String inName, ApplicationClass inClass, ApplicationCategory inCategory, boolean isInteractive, boolean isVisible, boolean isRemovable) {
		try {
			return new ApplicationImpl(inName, inAuthor, inClass, inCategory, isInteractive, isVisible, isRemovable);
		} catch (final SQLException e) {
			ApplicationFactoryImpl.LOGGER.fatal(e, e);
		}

		return null;
	}

	public List<Application> findAllBySetting(String key, String value) {
		final String condition = " application.application_id = application_setting.application_id AND key = ? AND value = ? ";
		final List<Object> values = Arrays.asList(new Object[] { key, value });
		return findAll(new String[] { "application_setting" }, condition, values, null);
	}
}
