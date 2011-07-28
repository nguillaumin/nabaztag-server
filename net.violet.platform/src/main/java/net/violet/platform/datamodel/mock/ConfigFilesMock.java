package net.violet.platform.datamodel.mock;

import net.violet.db.records.AbstractMockRecord;
import net.violet.platform.datamodel.Application;
import net.violet.platform.datamodel.ConfigFiles;
import net.violet.platform.datamodel.Files;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;

public final class ConfigFilesMock extends AbstractMockRecord<ConfigFiles, ConfigFilesMock> implements ConfigFiles {

	public static final MockBuilder<ConfigFiles> BUILDER = new MockBuilder<ConfigFiles>() {

		@Override
		protected ConfigFiles build(String[] inParamValues) {
			return new ConfigFilesMock(inParamValues[0] == null ? null : Long.parseLong(inParamValues[0]), inParamValues[1] == null ? null : Long.parseLong(inParamValues[1]), inParamValues[2] == null ? null : Long.parseLong(inParamValues[2]), inParamValues[3] == null ? null : inParamValues[3].replace("\'", net.violet.common.StringShop.EMPTY_STRING), inParamValues[4] == null ? null : Long.parseLong(inParamValues[4]));
		}
	};

	private final Long lang_id;
	private final Long application_id;
	private final Long file_id;
	private String index;

	/**
	 * @param id
	 * @param lang_id
	 * @param application_id
	 * @param file_id
	 * @param index
	 */
	private ConfigFilesMock(Long id, Long lang_id, Long file_id, String index, Long application_id) {
		super(id);
		this.lang_id = lang_id;
		this.file_id = file_id;
		this.index = index;
		this.application_id = application_id;
	}

	public ConfigFilesMock(Files inFile, String inIndex) {
		super(0);
		this.lang_id = null;
		this.application_id = null;
		this.file_id = inFile.getId();
		this.index = inIndex;
	}

	public Files getFiles() {
		return Factories.FILES.find(this.file_id);
	}

	public String getIndex() {
		return this.index;
	}

	public Lang getLang() {
		if (this.lang_id != null) {
			return Factories.LANG.find(this.lang_id);
		}

		return null;
	}

	public Application getApplication() {
		if (this.application_id != null) {
			return Factories.APPLICATION.find(this.application_id);
		}
		return null;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the lang_id
	 */
	public Long getLang_id() {
		return this.lang_id;
	}

	/**
	 * @return the file_id
	 */
	public Long getFile_id() {
		return this.file_id;
	}
}
