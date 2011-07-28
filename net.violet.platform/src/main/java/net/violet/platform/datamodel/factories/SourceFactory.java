package net.violet.platform.datamodel.factories;

import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactory;
import net.violet.platform.datamodel.Source;

public interface SourceFactory extends RecordFactory<Source> {

	Source findByPathAndVal(String path, long val);

	/**
	 * Recupere les sources pour la BourseFull Test source_srv <> 0 pour eviter
	 * d'effacer nos propres services
	 * 
	 * @param inSourceSrv
	 * @return
	 */
	Source findBySourceSrv(long inSourceSrv);

	Source findByPath(String path);

	List<Source> findByFilteredPath(String filteredPath);

	Map<String, String> myMeteoSource(String path);

	Source createNewSource(String path, int val, long srv, String dico);

	Source createNewSource(String path, int val);

	List<Source> findByRootPath(String rootPath);
}
