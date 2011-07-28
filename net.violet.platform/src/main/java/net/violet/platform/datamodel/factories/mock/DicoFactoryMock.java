package net.violet.platform.datamodel.factories.mock;

import java.util.ArrayList;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.DicoFactory;
import net.violet.platform.datamodel.mock.DicoMock;

public class DicoFactoryMock extends RecordFactoryMock<Dico, DicoMock> implements DicoFactory {

	public DicoFactoryMock() {
		super(DicoMock.class);
	}

	public List<Dico> createNablivesCache() {
		final List<Dico> dicoEntries = new ArrayList<Dico>();

		for (final Dico theDico : findAll()) {
			if (theDico.getDico_page().startsWith("nablives")) {
				dicoEntries.add(theDico);
			}
		}
		return dicoEntries;
	}

	public List<Dico> findByPage(String inPageName) {
		final List<Dico> dicoEntries = new ArrayList<Dico>();

		for (final Dico Dico : findAll()) {
			if (Dico.getDico_page().equals(inPageName)) {
				dicoEntries.add(Dico);
			}
		}
		return dicoEntries;
	}

	public List<Dico> findByPage(String inPageName, Lang inLang) {
		final List<Dico> dicoEntries = new ArrayList<Dico>();

		for (final Dico Dico : findAll()) {
			if (Dico.getDico_page().equals(inPageName) && Dico.getDicoLang().equals(inLang)) {
				dicoEntries.add(Dico);
			}
		}
		return dicoEntries;
	}

	public Dico findByKeyAndLang(String inKey, Lang inLang) {
		// on se débarasse du LOC_ qui ne doit pas être sauvegardé en base !
		final String searchedkey = inKey.replaceAll(Dico.LOC_KEY, net.violet.common.StringShop.EMPTY_STRING);

		for (final Dico dicoCandidateEntry : findAllMapped().values()) {
			if (dicoCandidateEntry.getDico_key().equals(searchedkey) && dicoCandidateEntry.getDicoLang().equals(inLang)) {
				return dicoCandidateEntry;
			}
		}
		return null;
	}

	public Dico create(String inKey, Lang inLang, String inText, String inPage) {
		return new DicoMock(0L, inKey, inLang, inText, inPage);
	}

	public List<Dico> findAllByKey(String inDicoKey) {
		final List<Dico> dicoEntries = new ArrayList<Dico>();
		for (final Dico Dico : findAll()) {
			if (Dico.getDico_key().equals(inDicoKey)) {
				dicoEntries.add(Dico);
			}
		}
		return dicoEntries;
	}

	public List<Dico> findAllForExport(int theIndex) {
		final List<Dico> dicoEntries = new ArrayList<Dico>();
		for (final Dico Dico : findAll()) {
			dicoEntries.add(Dico);
		}
		return dicoEntries;
	}
}
