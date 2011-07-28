package net.violet.platform.datamodel.factories.implementations;

import java.util.Collection;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.LangImpl;
import net.violet.platform.datamodel.factories.LangFactory;

public class LangFactoryImpl extends RecordFactoryImpl<Lang, LangImpl> implements LangFactory {

	private final LangFactoryCommon langCommonFactory = new LangFactoryCommon(findAll("1 = 1", null));

	public LangFactoryImpl() {
		super(LangImpl.SPECIFICATION);
	}

	public List<Lang> findAllLanguagesForNabcasts() {
		return findAll(new String[] { "nabcast" }, "nabcast_lang = lang_id", null, null);
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

	public final List<Lang> findAll() {
		return this.langCommonFactory.findAll();
	}
}
