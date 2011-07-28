package net.violet.platform.datamodel;

import java.util.LinkedList;
import java.util.List;

import net.violet.db.records.Record;
import net.violet.platform.datamodel.factories.Factories;

public interface Dico extends Record<Dico> {

	final String LOC_KEY = "^LOC_";

	/**
	 * Les nablives.
	 */
	List<Dico> NABLIVES = new LinkedList<Dico>(Factories.DICO.createNablivesCache());

	String getDico_key();

	String getDico_text();

	String getDico_page();

	Lang getDicoLang();

	/**
	 * @param translation
	 */
	void setText(String translation);

	void update(String inKey, Lang inLang, String inText, String inPage);

}
