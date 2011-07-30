package net.violet.platform.datamodel;

import java.util.List;

import net.violet.db.records.Record;
import net.violet.vlisp.services.NablifeSrv;

/**
 * Service Nablife. Deprecated, used by the previous site.
 *
 */
public interface NabLife extends Record<NabLife>, NablifeSrv {

	Service getService();

	/**
	 * Accesseur sur la liste des langues.
	 * 
	 * @return la liste des langues.
	 */
	List<Lang> getLangs();

	String getDescription();

	int getEnabled();

	String getImg();

	String getLink();

	String getName();

	long getNbvote();

	String getSetup();

	String getShortcut();

	String getIcone();

}
