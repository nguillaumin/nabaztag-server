package net.violet.platform.message.profiles;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.Configuration;

/**
 * A simple implementation of the Profile interface.
 */
public class Profile {

	private final Map<String, Double> mQualityMap;

	/**
	 * An empty profile constructor Package visibility, can only be called by
	 * {@link ProfilesManager}
	 * 
	 * @param inConf
	 */
	Profile() {
		this.mQualityMap = new HashMap<String, Double>();
	}

	/**
	 * Package visibility, can only be called by {@link ProfilesManager}
	 * 
	 * @param inConf
	 */
	Profile(Configuration inConf) {
		this.mQualityMap = new HashMap<String, Double>();

		for (final Iterator iterator = inConf.getKeys(); iterator.hasNext();) {
			final String key = iterator.next().toString();
			this.mQualityMap.put(key, inConf.getDouble(key));
		}
	}

	/**
	 * @return the map of content types keys associated to their supported
	 *         quality factor
	 * @see net.violet.platform.message.profiles.Profile#getQualitySupport()
	 */
	public Map<String, Double> getQualitySupport() {
		return this.mQualityMap;
	}

}
