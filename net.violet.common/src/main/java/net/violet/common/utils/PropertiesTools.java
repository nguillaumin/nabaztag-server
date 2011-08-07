package net.violet.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public final class PropertiesTools extends Properties {

	private static final Logger LOGGER = Logger.getLogger(PropertiesTools.class);

	@Override
	public synchronized void load(InputStream inStream) throws IOException {
		super.load(inStream);
		for (final Entry<Object, Object> aProperty : entrySet()) {
			final String theProperty = aProperty.getValue().toString();
			if (RegexTools.isInt(theProperty)) {
				aProperty.setValue(Integer.parseInt(theProperty));
			} else {
				// Expand any variable as system property
				aProperty.setValue(RegexTools.expandWithSysProp(theProperty));
			}
		}
	}

	public void load(String inPath) {
		load(inPath, true);
	}

	public void load(String inPath, boolean isMandatory) {
		final InputStream in = getClass().getClassLoader().getResourceAsStream(inPath);

		if (in != null) {
			try {
				load(in);
			} catch (final IOException e) {
				PropertiesTools.LOGGER.fatal(e, e);
			} finally {
				IOUtils.closeQuietly(in);
			}
		} else if (isMandatory) {
			throw new RuntimeException(new ClassNotFoundException("Impossible de trouver " + inPath + " dans le classpath"));
		}
	}

	public <T> T getProperties(final String name) {
		return (T) get(name);
	}

	public Map<Object, Object> getAllProperties() {
		return this;
	}
}
