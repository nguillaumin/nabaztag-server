package net.violet.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class DBPropertiesTools {

	private static final Logger LOGGER = Logger.getLogger(DBPropertiesTools.class);

	private final Map<Object, Object> mProperties = new HashMap<Object, Object>();

	public DBPropertiesTools(String inPath, boolean loadAll) {
		final boolean loadedDefault = loadProperties(inPath);
		if (!loadedDefault) {
			throw new RuntimeException(new ClassNotFoundException("Impossible de trouver " + inPath + " dans le classpath"));
		}

		if (loadAll) {
			loadProperties("constante-" + System.getProperty("hostname") + ".properties");
			loadProperties("constante-" + System.getProperty("crawler") + ".properties");
			loadProperties("constante-" + System.getProperty("hostname") + "_" + System.getProperty("port") + ".properties");
		}
	}

	private boolean loadProperties(String inPath) {
		boolean theResult = false;
		final InputStream in = getClass().getClassLoader().getResourceAsStream(inPath);
		if (in != null) {
			try {
				final Properties theProperties = new Properties();
				theProperties.load(in);
				this.mProperties.putAll(theProperties);
				theResult = true;
			} catch (final IOException e) {
				DBPropertiesTools.LOGGER.fatal(e, e);
			} finally {
				try {
					in.close();
				} catch (final IOException e) {
					DBPropertiesTools.LOGGER.fatal(e, e);
				}
			}
		}
		return theResult;
	}

	public String getProperties(final String name) {
		return (String) this.mProperties.get(name);
	}

	public Map<Object, Object> getAllProperties() {
		return this.mProperties;
	}

	public int getPropertiesInt(final String name) {
		final String theProperty = getProperties(name);
		if (null != theProperty) {
			return Integer.parseInt(theProperty);
		}
		return 0;
	}
}
