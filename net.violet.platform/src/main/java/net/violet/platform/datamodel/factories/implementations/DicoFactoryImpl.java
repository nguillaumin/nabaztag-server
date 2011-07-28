package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.DicoImpl;
import net.violet.platform.datamodel.Lang;
import net.violet.platform.datamodel.factories.DicoFactory;

import org.apache.log4j.Logger;

public class DicoFactoryImpl extends RecordFactoryImpl<Dico, DicoImpl> implements DicoFactory {

	private static final Logger LOGGER = Logger.getLogger(DicoFactoryImpl.class);

	public DicoFactoryImpl() {
		super(DicoImpl.SPECIFICATION);
	}

	public List<Dico> createNablivesCache() {
		return findAll("dico_page like ?", Collections.singletonList((Object) "nablives%"), null);
	}

	/**
	 * Get all the Dico entries with a specific 'page' name
	 * 
	 * @param inPageName NOTE : page name may be an application name for the
	 *            applications widgets
	 * @return
	 */
	public List<Dico> findByPage(String inPageName) {
		return findAll("dico_page = ?", Collections.singletonList((Object) inPageName), null);
	}

	public List<Dico> findByPage(String inPageName, Lang inLang) {
		return findAll("dico_page = ? and dico_lang = ? ", Arrays.asList((Object) inPageName, inLang.getId()), null);
	}

	public Dico findByKeyAndLang(String inKey, Lang inLang) {
		// on se débarasse du LOC_ qui ne doit pas être sauvegardé en base !
		final String key = inKey.replaceAll(Dico.LOC_KEY, net.violet.common.StringShop.EMPTY_STRING);
		return findByKey(1, new Object[] { key, inLang.getId() });
	}

	public Dico create(String inKey, Lang inLang, String inText, String inPage) {
		try {
			return new DicoImpl(inKey, inLang, inText, inPage);
		} catch (final SQLException e) {
			DicoFactoryImpl.LOGGER.fatal(e, e);
		}
		return null;
	}

	public List<Dico> findAllByKey(String inDicoKey) {
		//TODO vérifier cette fonction
		return findAll("dico_key = ?", Collections.singletonList((Object) inDicoKey));
	}

	public List<Dico> findAllForExport(int theIndex) {
		return findAll(new String[] { "lang" }, "dico_lang = lang_id", null, "dico_key,lang_isocode", theIndex, 5000);
	}

}
