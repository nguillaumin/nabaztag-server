package net.violet.probes;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;

public interface JabberComponentProbeMBean {

	CompositeData getComponentStats() throws OpenDataException;
}
