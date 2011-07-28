package net.violet.platform.datamodel.factories;

import java.util.List;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Dico;
import net.violet.platform.datamodel.Lang;

public interface DicoFactory extends RecordFactory<Dico> {

	List<Dico> createNablivesCache();

	/**
	 * Get all the Dico entries with a specific 'page' name
	 * 
	 * @param inPageName NOTE : page name may be an application name for the
	 *            applications widgets
	 * @return
	 */
	List<Dico> findByPage(String inPageName);

	List<Dico> findByPage(String inPageName, Lang inLang);

	Dico findByKeyAndLang(String inKey, Lang inLang);

	Dico create(String inKey, Lang inLang, String inText, String inPage);

	List<Dico> findAllByKey(String inDicoKey);

	List<Dico> findAllForExport(int theIndex);

}
