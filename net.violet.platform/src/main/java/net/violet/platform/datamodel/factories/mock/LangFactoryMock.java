package net.violet.platform.datamodel.factories.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.LangFactory;
import net.violet.platform.datamodel.mock.LangMock;

public class LangFactoryMock extends RecordFactoryMock<Lang, LangMock> implements LangFactory {

	private static final void reload() {
		LangMock.BUILDER.generateValuesFromInitFile(6, net.violet.platform.util.Constantes.OS_PATH + "net/violet/platform/datamodel/mock/langInit");
	}

	{
		LangFactoryMock.reload();
	}

	private LangFactoryCommon langCommonFactory = new LangFactoryCommon(super.findAll());

	@Override
	public void loadCache() {
		LangFactoryMock.reload();
		this.langCommonFactory = new LangFactoryCommon(super.findAll());

	}

	public LangFactoryMock() {
		super(LangMock.class);
	}

	public List<Lang> findAllLanguagesForNabcasts() {
		return Collections.emptyList();
	}

	public List<Lang> findAllMotherTongue() {
		return this.langCommonFactory.findAllMotherTongue();
	}

	public Lang findByIsoCode(String inIsoCode) {
		return this.langCommonFactory.findByIsoCode(inIsoCode);
	}

	public List<Lang> getAllASRLanguages() {
		return this.langCommonFactory.getAllASRLanguages();
	}

	public List<Lang> getAllObjectLanguages() {
		return this.langCommonFactory.getAllObjectLanguages();
	}

	public List<Lang> getAllSiteLanguages() {
		return this.langCommonFactory.getAllSiteLanguages();
	}

	public List<Lang> getAllTTSLanguages() {
		return this.langCommonFactory.getAllTTSLanguages();
	}

	public Collection<Lang> getCollectionLangFromIdsArray(String[] ids) {
		return this.langCommonFactory.getCollectionLangFromIdsArray(ids);
	}

	public Lang getParentByIsocode(String inIsocodeLanguage) {
		return this.langCommonFactory.getParentByIsocode(inIsocodeLanguage);
	}
}
