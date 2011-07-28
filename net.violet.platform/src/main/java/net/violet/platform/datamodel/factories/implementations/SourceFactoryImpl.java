package net.violet.platform.datamodel.factories.implementations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactoryImpl;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.SourceImpl;
import net.violet.platform.datamodel.factories.SourceFactory;

import org.apache.log4j.Logger;

public class SourceFactoryImpl extends RecordFactoryImpl<Source, SourceImpl> implements SourceFactory {

	private static final Logger LOGGER = Logger.getLogger(SourceFactoryImpl.class);

	SourceFactoryImpl() {
		super(SourceImpl.SPECIFICATION);
	}

	public Source findByPathAndVal(String path, long val) {
		return findByKey(2, new Object[] { path, val });
	}

	public Source findBySourceSrv(long inSourceSrv) {
		final List<Object> theValues = Arrays.asList(new Object[] { inSourceSrv });
		return find(" source_srv = ? and source_srv <> 0", theValues);
	}

	public Source findByPath(String path) {
		return findByKey(1, path);

	}

	public List<Source> findByFilteredPath(String filteredPath) {
		String condition = "1=1";
		final List<Object> vals = new LinkedList<Object>();
		if (filteredPath != null) {
			condition = "source_path like ?";
			vals.add(filteredPath);
		}
		return findAll(condition, vals);
	}

	public Map<String, String> myMeteoSource(String path) {
		final Map<String, String> result = new HashMap<String, String>();
		final List<Source> sources = new ArrayList<Source>(findAll("source_path = ? or source_path = ?", Arrays.asList(new Object[] { path + ".temp", path + ".weather" }), "source_path desc"));
		int i = 0;
		for (final Source source : sources) {
			if (i == 0) {
				result.put("weather", Long.toString(source.getSource_val()));
			} else if (i == 1) {
				result.put("temp", Long.toString(source.getSource_val()));
			}
			i++;
		}
		return result;
	}

	public Source createNewSource(String path, int val, long srv, String dico) {
		try {
			return new SourceImpl(path, val, 0, srv, dico);
		} catch (final SQLException e) {
			SourceFactoryImpl.LOGGER.info(e, e);
			return null;
		}
	}

	public Source createNewSource(String path, int val) {
		try {
			return new SourceImpl(path, val);
		} catch (final SQLException e) {
			SourceFactoryImpl.LOGGER.fatal(e, e);
			return null;
		}
	}

	public List<Source> findByRootPath(String rootPath) {
		return findAll(" source_path LIKE ?", Arrays.asList(new Object[] { rootPath + "%" }));
	}

}
