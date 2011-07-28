package net.violet.probes;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public interface CacheProbeMBean {

	CompositeData getCacheMapSizesStats() throws OpenDataException;

	CompositeData getCachePulseStats() throws OpenDataException;
}
