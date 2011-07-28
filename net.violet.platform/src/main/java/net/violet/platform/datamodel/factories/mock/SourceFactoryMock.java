package net.violet.platform.datamodel.factories.mock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.violet.db.records.factories.RecordFactoryMock;
import net.violet.platform.datamodel.Source;
import net.violet.platform.datamodel.factories.SourceFactory;
import net.violet.platform.datamodel.mock.SourceMock;

public class SourceFactoryMock extends RecordFactoryMock<Source, SourceMock> implements SourceFactory {

	SourceFactoryMock() {
		super(SourceMock.class);
	}

	public List<Source> findByFilteredPath(String filteredPath) {
		final List<Source> theResult = new LinkedList<Source>();
		for (final Source src : findAll()) {
			if (src.getSource_path().contains(filteredPath.split("%")[0])) {
				theResult.add(src);
			}
		}
		return theResult;
	}

	public Source findByPath(String path) {
		for (final Source src : findAll()) {
			if (src.getSource_path().equals(path)) {
				return src;
			}
		}
		return null;
	}

	public Source findByPathAndVal(String path, long val) {
		for (final Source src : findAll()) {
			if (src.getSource_path().equals(path) && (src.getSource_val() == val)) {
				return src;
			}
		}
		return null;
	}

	public Source findBySourceSrv(long inSourceSrv) {
		for (final Source src : findAll()) {
			if (src.getSource_srv() == inSourceSrv) {
				return src;
			}
		}
		return null;
	}

	public Map<String, String> myMeteoSource(String path) {
		final Map<String, String> result = new HashMap<String, String>();
		result.put("weather", Long.toString(findByPath(path + ".weather").getSource_val()));
		result.put("temp", Long.toString(findByPath(path + ".temp").getSource_val()));
		return result;
	}

	public Source createNewSource(String path, int val, long srv, String dico) {
		return new SourceMock(0, path, val, 0, srv);
	}

	public Source createNewSource(String path, int val) {
		return new SourceMock(0, path, val);
	}

	public List<Source> findByRootPath(String rootPath) {
		final List<Source> result = new LinkedList<Source>();
		for (final Source aSource : findAll()) {
			if (aSource.getSource_path().startsWith(rootPath)) {
				result.add(aSource);
			}
		}
		return result;
	}

}
