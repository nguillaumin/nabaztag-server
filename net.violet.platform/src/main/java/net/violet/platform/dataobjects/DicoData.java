package net.violet.platform.dataobjects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import net.violet.platform.api.exceptions.APIErrorMessage;
import net.violet.platform.api.exceptions.NoSuchMessageException;
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.Factories;

import org.apache.log4j.Logger;

public class DicoData extends APIData<Dico> {

	private static final Logger LOGGER = Logger.getLogger(DicoData.class);

	/**
	 * Constructeur à partir d'un Dico
	 */
	public DicoData(Dico inDico) {
		super(inDico);
	}

	public static DicoData getData(Dico inDico) {
		try {
			return RecordData.getData(inDico, DicoData.class, Dico.class);
		} catch (final InstantiationException e) {
			DicoData.LOGGER.fatal(e, e);
		} catch (final IllegalAccessException e) {
			DicoData.LOGGER.fatal(e, e);
		} catch (final InvocationTargetException e) {
			DicoData.LOGGER.fatal(e, e);
		} catch (final NoSuchMethodException e) {
			DicoData.LOGGER.fatal(e, e);
		}

		return null;
	}

	public String getText() {
		final Dico theDico = getRecord();
		if (theDico != null) {
			return theDico.getDico_text();
		}

		return net.violet.common.StringShop.EMPTY_STRING;
	}

	@Override
	protected ObjectClass getObjectClass() {
		return ObjectClass.DICO;
	}

	public static String findByDicoKeyAndLang(String inDicoKey, AbstractLangData inLang) throws NoSuchMessageException {
		return DicoData.findByDicoKeyAndLang(inDicoKey, inLang.getReference());
	}

	public static String findByDicoKeyAndLang(String inDicoKey, Lang inLang) throws NoSuchMessageException {
		return DicoData.findDicoDataByDicoKeyAndLang(inDicoKey, inLang).getText();
	}

	public static DicoData findDicoDataByDicoKeyAndLang(String inDicoKey, Lang inLang) throws NoSuchMessageException {
		final Dico inDico = Factories.DICO.findByKeyAndLang(inDicoKey, inLang);

		if (inDico == null) {
			throw new NoSuchMessageException(APIErrorMessage.NO_SUCH_DICO_KEY);
		}

		return new DicoData(inDico);
	}

	public static List<DicoData> findByDicoKey(String inDicoKey) {
		final List<Dico> dicoList = Factories.DICO.findAllByKey(inDicoKey);

		final List<DicoData> dicoDataList = new ArrayList<DicoData>();
		for (final Dico inDico : dicoList) {
			dicoDataList.add(DicoData.getData(inDico));
		}
		return dicoDataList;
	}

	public static DicoData createLigne(String inKey, AbstractLangData inLangData, String inText, String inPage) {
		final Dico theDico = Factories.DICO.create(inKey, inLangData.getReference(), inText, inPage);
		return DicoData.getData(theDico);
	}

	public static List<DicoData> findAllForExport(int theIndex) {
		final List<DicoData> dicoDataList = new ArrayList<DicoData>();
		for (final Dico inDico : Factories.DICO.findAllForExport(theIndex)) {
			dicoDataList.add(DicoData.getData(inDico));
		}
		return dicoDataList;
	}

	public String getKey() {
		final Dico theDico = getRecord();
		if (theDico != null) {
			return theDico.getDico_key();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public String getPage() {
		final Dico theDico = getRecord();
		if (theDico != null) {
			return theDico.getDico_page();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public Lang getLang() {
		final Dico theDico = getRecord();
		if (theDico != null) {
			return theDico.getDicoLang();
		}
		return null;
	}

	public String getLangISO() {
		final Lang inLang = getLang();
		if (inLang != null) {
			return inLang.getIsoCode();
		}
		return net.violet.common.StringShop.EMPTY_STRING;
	}

	public static DicoData findByAPIId(String inAPIId, String inAPIKey) {
		DicoData theResult = null;
		final long theId = APIData.fromObjectID(inAPIId, ObjectClass.DICO, inAPIKey);
		if (theId != 0) {
			final Dico theDico = Factories.DICO.find(theId);
			if (theDico != null) {
				theResult = DicoData.getData(theDico);
			}
		}
		return theResult;
	}

	public DicoData updateLine(String inKey, AbstractLangData inLang, String inText, String inPage) {
		Lang theLang = null;
		if (inLang != null) {
			theLang = inLang.getReference();
		}
		final Dico theDico = this.getRecord();
		if (theDico != null) {
			theDico.update(inKey, theLang, inText, inPage);
		}

		return DicoData.getData(theDico);
	}
}
