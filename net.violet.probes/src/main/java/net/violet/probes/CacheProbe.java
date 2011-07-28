package net.violet.probes;

import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

class CacheProbe extends AbstractProbe implements CacheProbeMBean {

	private static final String GROUP = "CacheMapSizesStats";
	private static final String PULSE = "CachePulseStats";

	@CompositeDataField(description = "The amount of elements in the CacheMap", group = CacheProbe.GROUP)
	protected int cacheMapSize;
	@CompositeDataField(description = "The amount of elements in the LinkedMap", group = CacheProbe.GROUP)
	protected int linkedMapSize;
	@CompositeDataField(description = "Milliseconds since latest pulse message", group = CacheProbe.PULSE)
	protected long pulseMessageAge;
	@CompositeDataField(description = "Milliseconds between pulse send and receive time", group = CacheProbe.PULSE)
	protected long pulseMessageDelay;

	protected CacheProbe() throws OpenDataException {
		final Map<String, String> theMap = new HashMap<String, String>();
		theMap.put(CacheProbe.GROUP, "The Composite type for the CacheMap probe");
		theMap.put(CacheProbe.PULSE, "The Composite type for the CachePulse probe");
		initGroups(theMap);
	}

	public synchronized void updateCacheMapsStats(Integer[] inCacheMapSizes, Long[] inMessagePulseStats) {
		this.cacheMapSize = inCacheMapSizes[0];
		this.linkedMapSize = inCacheMapSizes[1];

		final long pulseMessageTime = inMessagePulseStats[0];
		final long now = System.currentTimeMillis();
		this.pulseMessageAge = now - pulseMessageTime;
		this.pulseMessageDelay = inMessagePulseStats[1];
	}

	public synchronized CompositeData getCacheMapSizesStats() throws OpenDataException {
		return getStat(CacheProbe.GROUP);
	}

	public synchronized CompositeData getCachePulseStats() throws OpenDataException {
		return getStat(CacheProbe.PULSE);
	}
}
